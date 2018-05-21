package com.example.huyle.tastyvn;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import static android.Manifest.permission.CAMERA;

/**
 * Created by NGHIA on 5/15/2018.
 */
public class QRCode extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView ScannerView;
    private int CurrentAPIVersion = Build.VERSION.SDK_INT;
    private boolean cameraAccept;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);
        if( CurrentAPIVersion >= Build.VERSION_CODES.M){
            if(checkPermission())
                Toast.makeText(getApplicationContext(), "Permission already granted!", Toast.LENGTH_LONG).show();
            else
                RequestPermission();}
    }

    @Override
    public void onResume() {
        super.onResume();

        if( android.os.Build.VERSION.SDK_INT != Build.VERSION_CODES.M)
        {
            if( checkPermission()){
                if( ScannerView == null)
                {
                    ScannerView = new ZXingScannerView(this);
                    setContentView(ScannerView);
                }
                ScannerView.setResultHandler(this);
                ScannerView.startCamera();
            }

            else RequestPermission();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ScannerView.stopCamera();
    }

    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void RequestPermission()
    {
        Log.d("ABC","def");
        ActivityCompat.requestPermissions(this,new String[]{CAMERA},REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    cameraAccept = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccept)
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera"
                                , Toast.LENGTH_LONG).show();
                    else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera"
                                , Toast.LENGTH_LONG).show();
                        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if(shouldShowRequestPermissionRationale(CAMERA)){
                                showMessageOkCancel("You need to allow access to permission ",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        }
                                );
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOkCancel(String message, DialogInterface.OnClickListener okListener)
    {
        new AlertDialog.Builder(QRCode.this)
                .setMessage(message)
                .setPositiveButton("OK",okListener)
                .setNegativeButton("Cancel",okListener)
                .create().show();
    }

    @Override
    public void handleResult(Result result) {

        final String myResult = result.getText().toString();
        Log.d("ResuLtScanner",result.getText());
        Log.d("resultScanner",result.getBarcodeFormat().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(QRCode.this,MainActivity.class);
                        i.putExtra("table_addr",myResult);
                        startActivity(i);
                    }
                }
        );
        builder.setMessage(result.getText().toString());
        AlertDialog alert = builder.create();
        alert.show();
    }
}
