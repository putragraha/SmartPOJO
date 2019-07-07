package com.nsystem.smartpojo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.nsystem.smartpojo.pojo.SmartUser;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SmartUser smartUser = new SmartUser();
        smartUser.setid(1);
        smartUser.setusername("Putra");

        TextView textView = (TextView) findViewById(R.id.textview);
        textView.setText("Hi " + smartUser.getusername());
    }
}
