package com.simplicity.awayfromdesk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ScanScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_screen);
        TextView textView = (TextView) findViewById(R.id.scan_guide);
        textView.setTextSize(40);
        textView.setText("Please place phone next to NFC");
    }
}
