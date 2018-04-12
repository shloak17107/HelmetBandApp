package com.example.shloakaggarwal.helmetbandapp;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.Manifest;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button connectionButton;
    private Button receivingButton;
    private Button callButton;
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callButton = (Button) findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                final String phoneNo = "7982374567";
                final String message = "hi";

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNo));

                callButton.setEnabled(false);
                if(checkPermission(Manifest.permission.SEND_SMS)) {
                    callButton.setEnabled(true);
                }else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.SEND_SMS},
                            SEND_SMS_PERMISSION_REQUEST_CODE);
                }
                callButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!TextUtils.isEmpty(message) && !TextUtils.isEmpty(phoneNo)) {

                            if(checkPermission(Manifest.permission.SEND_SMS)) {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(phoneNo, null, message, null, null);
                            }else {
                                Toast.makeText(MainActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);

            }
        });

        connectionButton = (Button) findViewById(R.id.connectionButton);
        connectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenConnectionActivity();
            }
        });

        receivingButton = (Button) findViewById(R.id.receivingButton);
        receivingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenReceivingActivity();
            }
        });
    }

    private boolean checkPermission(String permission) {
        int checkPermission = ContextCompat.checkSelfPermission(this, permission);
        return (checkPermission == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS_PERMISSION_REQUEST_CODE: {
                if(grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    callButton.setEnabled(true);
                }
                return;
            }
        }
    }

    private void OpenConnectionActivity() {
        Intent openconnectionactivityIntent = new Intent(this, Connection.class);
        startActivity(openconnectionactivityIntent);
    }

    private void OpenReceivingActivity() {
        Intent openreceivingactivityIntent = new Intent(this, Receiving.class);
        startActivity(openreceivingactivityIntent);
    }
}
