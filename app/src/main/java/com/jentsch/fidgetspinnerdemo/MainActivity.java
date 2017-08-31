package com.jentsch.fidgetspinnerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jentsch.fidgetspinnerview.FidgetSpinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FidgetSpinner f = (FidgetSpinner)findViewById(R.id.fidgetspinner);
        f.setImageDrawable(R.drawable.spinner);
    }
}
