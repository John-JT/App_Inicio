package com.example.john.estacion2;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by john on 07/10/17.
 */

public class SettingsActivity extends Activity{

    EditText userNameText;
    EditText TempText;
    EditText LluviaText;
    EditText VientoText;
    SharedPreferences prefs;
    SharedPreferences getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userNameText = (EditText) findViewById(R.id.etUsername);
        TempText = (EditText) findViewById(R.id.etTemp);
        LluviaText = (EditText) findViewById(R.id.etLluvia) ;
        VientoText = (EditText) findViewById(R.id.etViento);
        Button save = (Button) findViewById(R.id.btSave);

        TextView font1 = (TextView) findViewById(R.id.Instrucciones);
        Typeface customfontInst = Typeface.createFromAsset(getAssets(),"fonts/CaviarDreams_BoldItalic.ttf");
        font1.setTypeface(customfontInst);

        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        getData = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    String userName = userNameText.getText().toString();
                    int Temp = Integer.parseInt(TempText.getText().toString());
                    int Lluvia = Integer.parseInt(LluviaText.getText().toString());
                    int Viento = Integer.parseInt(VientoText.getText().toString());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("temp_key", Temp);
                    editor.putInt("lluvia_key", Lluvia);
                    editor.putInt("viento_key", Viento);
                    editor.putString("user_key", userName);
                    editor.apply();
                }catch (Exception e){

                    Context context = getApplicationContext();
                    CharSequence text = "Ingrese los valores de forma adecuada nuevamente";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });






        Button clearData = (Button) findViewById(R.id.btClearData);
        clearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().clear().apply();
            }
        });

    }





}


/*
*
*         save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameText.getText().toString();
                int Temp =  Integer.parseInt(TempText.getText().toString());
                int Lluvia = Integer.parseInt(LluviaText.getText().toString());
                int Viento = Integer.parseInt(VientoText.getText().toString());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("temp_key", Temp);
                editor.putInt("lluvia_key", Lluvia);
                editor.putInt("viento_key", Viento);
                editor.putString("user_key", userName);
                editor.apply();

            }
        });*/