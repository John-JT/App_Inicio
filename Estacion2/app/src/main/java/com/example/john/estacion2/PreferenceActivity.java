package com.example.john.estacion2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class PreferenceActivity extends Activity {

    SharedPreferences getData;
    RelativeLayout rl1;
    TextView userName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        rl1 = (RelativeLayout) findViewById(R.id.rl1);
        userName = (TextView) findViewById(R.id.tvLogin);
        Button settings = (Button) findViewById(R.id.btSettings);

        TextView font1 = (TextView) findViewById(R.id.Intro);
        Typeface customfontInst = Typeface.createFromAsset(getAssets(),"fonts/CaviarDreams_BoldItalic.ttf");
        font1.setTypeface(customfontInst);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("com.example.john.estacion2.SETTINGS");
                startActivity(i);
            }
        });

        getSettings();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSettings();
    }

    private void getSettings() {
        getData = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        int lluvia = getData.getInt("lluvia_key",99999);
        int temp = getData.getInt("temp_key",99999);
        int viento = getData.getInt("viento_key",99999);
        String userText = getData.getString("user_key", "Visitante");
     /*   if (parameterText.equals("white")) {

        } else if (parameterText.equals("slate")) {

        } else {

        }*/
        userName.setText ("Bienvenido(a) " + userText);
    }

}




