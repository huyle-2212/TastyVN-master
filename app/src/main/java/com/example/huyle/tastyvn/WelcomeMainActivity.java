package com.example.huyle.tastyvn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class WelcomeMainActivity extends AppCompatActivity {

    Button buttonNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome__main);
        buttonNext = (Button) findViewById(R.id.button);


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(WelcomeMainActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
