package com.example.huyle.tastyvn;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huyle.tastyvn.Detail.FavouriteDetail;
import com.example.huyle.tastyvn.Detail.MainCourseDetail;
import com.example.huyle.tastyvn.Interface.ItemClickListener;
import com.example.huyle.tastyvn.Model.Favourite;
import com.example.huyle.tastyvn.ViewHolder.FoodViewHolder;
import com.example.huyle.tastyvn.Model.Favourite;
import com.example.huyle.tastyvn.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Favourites.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Favourites#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Favourites extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    FirebaseDatabase database;
    DatabaseReference category;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Favourite,FoodViewHolder> adapter;

    public Favourites() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Favourites.
     */
    // TODO: Rename and change types and number of parameters
    public static Favourites newInstance(String param1, String param2) {
        Favourites fragment = new Favourites();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(container == null){
            return null;
        }
        View view  = inflater.inflate(R.layout.fragment_main_course,container,false);
        database = FirebaseDatabase.getInstance();
        category = database.getReference("Favourite");
        recycler_menu = (RecyclerView) view.findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        recycler_menu.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadMenu();
        return view;
    }

    private void loadMenu(){
        adapter = new FirebaseRecyclerAdapter<Favourite, FoodViewHolder>(Favourite.class,R.layout.menu_item, FoodViewHolder.class,category) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Favourite model, int position) {
                viewHolder.txtMenuName.setText(model.getName());
                Picasso.with(getContext()).load(model.getImage())
                        .into(viewHolder.imageView);
                final Favourite clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodDetail = new Intent(Favourites.this.getActivity(), FavouriteDetail.class);
                        foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
