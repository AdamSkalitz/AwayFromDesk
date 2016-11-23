package com.example.ksb12177.myapplication;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.ksb12177.myapplication.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openScanActivity(View view){
        Intent intent = new Intent(this, scan.class);
        startActivity(intent);
    }

    public void openSetActivity(View view){
        Intent intent = new Intent(this, SetActivity.class);
        startActivity(intent);
    }

    public void getHelp(MenuItem menuitem){
        Intent intent = new Intent(this, HELP.class);
        String message = "TroubleShooting: \n " +
                         "If NFC is not working \n" +
                         "make sure that the NFC \n " +
                         "capabilities are switched \n " +
                         "on. \n" +
                         "Try to keep the phone \n" +
                         "in the same orientation as \n" +
                         "the card. \n  " +
                         "Lift the phone away from \n" +
                         "and place it down a few times \n\n" +
                         "Scanning: " +
                         "To scan a card, simply \n" +
                         "place the phone near the\n " +
                         "card when on any screen \n\n" +
                         "Setting: " +
                         "Press the Set Status button \n" +
                         "on the home screen the fill \n" +
                         "the options, a shortcut or a \n" +
                         "time away must be selected   \n" +
                         "other wise the button won't work \n" +
                         ",press the Confirm Status button\n" +
                         "and finally place phone on NFC card";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
