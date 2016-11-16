package com.simplicity.awayfromdesk;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ScanScreenActivity extends AppCompatActivity {

    private ArrayList<String> messagesReceivedArray = new ArrayList<>();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_screen);
        textView = (TextView) findViewById(R.id.scan_guide);
        textView.setTextSize(40);
        textView.setText("Please place phone next to NFC");
    }


    private void handleNfcIntent(Intent NfcIntent) {
        //textView.setText("sdfdsfsdf");
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(NfcIntent.getAction())) {
            Parcelable[] receivedArray =
                    NfcIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if(receivedArray != null) {
                messagesReceivedArray.clear();
                NdefMessage receivedMessage = (NdefMessage) receivedArray[0];
                NdefRecord[] attachedRecords = receivedMessage.getRecords();

                for (NdefRecord record:attachedRecords) {
                    String string = new String(record.getPayload());
                    //Make sure we don't pass along our AAR (Android Application Record)
                    if (string.equals(getPackageName())) { continue; }
                    messagesReceivedArray.add(string);
                }
                //Toast.makeText(this, "Received " + messagesReceivedArray.size() +
                //" Messages", Toast.LENGTH_LONG).show();
                textView.setText(receivedArray[0].toString());
            }
            else {
                textView.setText("Failed");
            }
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        handleNfcIntent(intent);
    }


}
