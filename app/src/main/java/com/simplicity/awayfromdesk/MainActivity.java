package com.simplicity.awayfromdesk;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity{

    private NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check if NFC is available on device
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(mNfcAdapter != null) {
            //This will refer back to createNdefMessage for what it will send
            //mNfcAdapter.setNdefPushMessageCallback(this, this);
            //pls switch on NFC
            //This will be called if the message is sent successfully
            //mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }

    }

    /** Called when the user clicks the scan button */
    public void scan(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, ScanScreenActivity.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void set(View view){
        Intent intent = new Intent(this, SetStatus.class);
        startActivity(intent);
    }
}
