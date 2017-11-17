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
    TextView LLUVIA;
    TextView VIENTO;
    TextView TEMP;
    String temp, lluvia, viento;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        rl1 = findViewById(R.id.rl1);
        userName = findViewById(R.id.tvLogin);
        LLUVIA = findViewById(R.id.tvlluvia);
        VIENTO = findViewById(R.id.tvviento);
        TEMP = findViewById(R.id.tvtemp);
        Button settings = findViewById(R.id.btSettings);


        TextView font1 = findViewById(R.id.Intro);
        Typeface customfontInst = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams_BoldItalic.ttf");
        font1.setTypeface(customfontInst);

        Typeface customfontparam = Typeface.createFromAsset(getAssets(), "fonts/GrandHotel-Regular.otf");
        LLUVIA.setTypeface(customfontparam);
        VIENTO.setTypeface(customfontparam);
        TEMP.setTypeface(customfontparam);
        userName.setTypeface(customfontInst);

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
        String userText = getData.getString("user_key", "Visitante");

        /*lluvia = getData.getString("lluvia_key", "99999");
        temp = getData.getString("temp_key", "99999");
        viento = getData.getString("viento_key", "99999");*/

        double temp_pru = getDouble(getData, "temp_key", 9999.0);
        double llu_pru = getDouble(getData, "lluvia_key", 9999.0);
        double vie_pru = getDouble(getData, "viento_key", 9999.0);


        userName.setText("Bienvenido(a) " + userText);
        LLUVIA.setText(String.valueOf(llu_pru) + "%");
        TEMP.setText(String.valueOf(temp_pru) + " °C");
        VIENTO.setText(String.valueOf(vie_pru) + " km/h");

    }

    SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }

}


