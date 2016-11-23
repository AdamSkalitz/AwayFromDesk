package com.example.ksb12177.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SetActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";

    private static boolean setValue=false;
    //private static String stringTimeAway="";
    //private static String stringLocation="";

    private EditText editTextTimeAway;
    private EditText editTextLocation;
    private RadioGroup radioGroupShortcut;
    private Button buttonSetStatus;

    private static SetDetails sd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        editTextTimeAway = (EditText) findViewById(R.id.editText_timeaway);
        editTextLocation = (EditText) findViewById(R.id.editText_location);
        radioGroupShortcut = (RadioGroup) findViewById(R.id.radioGroup);
        buttonSetStatus = (Button) findViewById(R.id.button_set_status);
        buttonSetStatus.setEnabled(false);
        editTextTimeAway.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                int radioButtonId = radioGroupShortcut.getCheckedRadioButtonId();
                View radioButton = radioGroupShortcut.findViewById(radioButtonId);
                int id = radioGroupShortcut.indexOfChild(radioButton);
                boolean why = editTextTimeAway.getText().toString().equals("");
                if(((id!=-1)||(why))){
                    buttonSetStatus.setEnabled(true);
                }
            }
        });
    }


    public void setStatus(View view){
        String stringTimeAway =  editTextTimeAway.getText().toString();
        int radioButtonId = radioGroupShortcut.getCheckedRadioButtonId();
        View radioButton = radioGroupShortcut.findViewById(radioButtonId);
        int id = radioGroupShortcut.indexOfChild(radioButton);

        if((id!=-1)&&(!editTextTimeAway.getText().toString().equals(""))){
            Toast.makeText(this, "failed to add message, start again", Toast.LENGTH_SHORT).show();
            return;
        }
        String stringLocation = editTextLocation.getText().toString();
        sd.setTimeAway(stringTimeAway);
        sd.setLocation(stringLocation);
        sd.setShortcut(id);
        setValue=true;
        Toast.makeText(this, "Message added successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, scan.class);
        startActivity(intent);
    }

    public static SetDetails getSD(){
        return sd;
    }
    public static boolean getSetBool(){
        return setValue;
    }
    public static void setSetBool(boolean bool){
        setValue=bool;
    }

    public void endisableButton(View view){
        int radioButtonId = radioGroupShortcut.getCheckedRadioButtonId();
        View radioButton = radioGroupShortcut.findViewById(radioButtonId);
        int id = radioGroupShortcut.indexOfChild(radioButton);
        boolean why = editTextTimeAway.getText().toString().equals("               ");
        if(((id!=-1)|| why)){
            buttonSetStatus.setEnabled(true);
        }
    }

    public void simSetError(View view) {
        new AlertDialog.Builder(SetActivity.this)
                .setTitle("Set Error")
                .setMessage("Unable to set successfully :(")
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}
