package com.example.ksb12177.myapplication;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class status extends AppCompatActivity {

    NfcAdapter nfcAdapter;
    private TextView textView;

    private SetActivity set;
    private SetDetails sd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        textView = (TextView) findViewById(R.id.textview_status);



        textView.setText("");
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        setOrScan();


    }

    public void setOrScan(){
        Intent nfcIntent = getIntent();
        boolean setVar = set.getSetBool();

        if(setVar){
            if (nfcIntent.hasExtra(NfcAdapter.EXTRA_TAG)) {
                //set
                //Toast.makeText(this, "NfcIntent!", Toast.LENGTH_SHORT).show();

                Tag tag = nfcIntent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                //if here for message being null
                NdefMessage ndefMessage = createNdefMessage(formatMessage());
                writeNdefMessage(tag, ndefMessage);
                set.setSetBool(false);
            }
        }else{
            String string  = handleNfcIntent(nfcIntent);
            textView.setText(string);
        }
    }

    /**
     Method that does the scanning
     */
    private String handleNfcIntent(Intent NfcIntent) {
        String mess="";
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(NfcIntent.getAction())) {
            String type = NfcIntent.getAction();
            // if (MIME_TEXT_PLAIN.equals(type)) {
            Tag tag = NfcIntent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Ndef ndef = Ndef.get(tag);
            NdefMessage ndefMessage = ndef.getCachedNdefMessage();
            NdefRecord[] records = ndefMessage.getRecords();

            for (NdefRecord ndefRecord : records) {
                try {
                    byte[] payload = ndefRecord.getPayload();
                    String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
                    int languageCodeLength = payload[0] & 0063;
                    mess =  new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
                } catch (UnsupportedEncodingException e) {
                    return "unsupport encoding";
                }
            }
            // }

        }
        return mess;
    }


    /**
     * These methods do the creating message and writing to card
     */
    private String formatMessage(){
        Calendar calender = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(calender.getTime());

        sd = set.getSD();
        String location = sd.getLocation();
        int rgid = sd.getShortcut();
        String timeAway = sd.getTimeAway();
        String message="Setting failed";

        if(location.equals("Optional...     ")){
            location="Unavailable";
        }

        if(rgid==-1){
            //no button selected
            message = String.format("Status: Away for %s \n Location: %s \n Set at: %s ",timeAway,location, time);
        }else{
            int id = (rgid+1)*10;
            message = String.format("Status: Away for %d \n Location: %s \n Set at: %s ",id,location, time);
        }

        return message;
    }


    private NdefMessage createNdefMessage(String content) {

        NdefRecord ndefRecord = createTextRecord(content);

        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{ndefRecord});

        return ndefMessage;
    }

    private NdefRecord createTextRecord(String content) {
        try {
            byte[] language;
            language = Locale.getDefault().getLanguage().getBytes("UTF-8");

            final byte[] text = content.getBytes("UTF-8");
            final int languageSize = language.length;
            final int textLength = text.length;
            final ByteArrayOutputStream payload = new ByteArrayOutputStream(1 + languageSize + textLength);

            payload.write((byte) (languageSize & 0x1F));
            payload.write(language, 0, languageSize);
            payload.write(text, 0, textLength);

            return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload.toByteArray());

        } catch (UnsupportedEncodingException e) {
            Log.e("createTextRecord", e.getMessage());
        }
        return null;
    }

    private void writeNdefMessage(Tag tag, NdefMessage ndefMessage) {

        try {

            if (tag == null) {
                Toast.makeText(this, "Tag object cannot be null", Toast.LENGTH_SHORT).show();
                return;
            }

            Ndef ndef = Ndef.get(tag);

            if (ndef == null) {
                // format tag with the ndef format and writes the message.
                formatTag(tag, ndefMessage);
            } else {
                ndef.connect();

                if (!ndef.isWritable()) {
                    Toast.makeText(this, "Tag is not writable!", Toast.LENGTH_SHORT).show();

                    ndef.close();
                    return;
                }

                ndef.writeNdefMessage(ndefMessage);
                ndef.close();

                Toast.makeText(this, "Tag written!", Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e) {
            Log.e("writeNdefMessage", e.getMessage());
        }

    }
    private void formatTag(Tag tag, NdefMessage ndefMessage) {
        try {

            NdefFormatable ndefFormatable = NdefFormatable.get(tag);

            if (ndefFormatable == null) {
                Toast.makeText(this, "Tag is not ndef formatable!", Toast.LENGTH_SHORT).show();
                return;
            }


            ndefFormatable.connect();
            ndefFormatable.format(ndefMessage);
            ndefFormatable.close();

            Toast.makeText(this, "Tag writen!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e("formatTag", e.getMessage());
        }

    }



    /**
     * Helpers
     */
    @Override
    protected void onResume() {
        super.onResume();

        enableForegroundDispatchSystem();
    }

    @Override
    protected void onPause() {
        super.onPause();

        disableForegroundDispatchSystem();
    }

    private void enableForegroundDispatchSystem() {

        Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        IntentFilter[] intentFilters = new IntentFilter[]{};

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
    }

    private void disableForegroundDispatchSystem() {
        nfcAdapter.disableForegroundDispatch(this);
    }

}
