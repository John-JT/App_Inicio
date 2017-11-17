package com.example.john.estacion2;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by john on 07/10/17.
 */

public class SettingsActivity extends Activity {

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

        userNameText = findViewById(R.id.etUsername);
        TempText = findViewById(R.id.etTemp);
        LluviaText = findViewById(R.id.etLluvia);
        VientoText = findViewById(R.id.etViento);
        Button save = findViewById(R.id.btSave);

        TextView font1 = findViewById(R.id.Instrucciones);
        Typeface customfontInst = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams_BoldItalic.ttf");
        font1.setTypeface(customfontInst);

        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        getData = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String userName = userNameText.getText().toString();
                    String Temp = TempText.getText().toString();
                    String Lluvia = LluviaText.getText().toString();
                    String Viento = VientoText.getText().toString();
                    SharedPreferences.Editor editor = prefs.edit();
                    double temp_par = Double.parseDouble(Temp);
                    double llu_par = Double.parseDouble(Lluvia);
                    double vie_par = Double.parseDouble(Viento);
                    putDouble(editor, "temp_key", temp_par);
                    putDouble(editor, "lluvia_key", llu_par);
                    putDouble(editor, "viento_key", vie_par);
                    editor.putString("user_key", userName);
                    editor.putLong("last_modified", System.currentTimeMillis());
                    editor.apply();
                    Context context = getApplicationContext();
                    CharSequence text = "Los datos han sido guardados en sus preferencias";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    FetchSharedPreferences.SyncImmediately(getApplicationContext()); //Prueba de Escritura
                    Intent intent1 = new Intent(getApplicationContext(),PreferenceActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                } catch (Exception e) {

                    Context context = getApplicationContext();
                    CharSequence text = "Ingrese los valores de forma adecuada nuevamente";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });


        Button clearData = findViewById(R.id.btClearData);
        clearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().clear().apply();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putLong("last_modified", System.currentTimeMillis());
                editor.apply();
                Context context = getApplicationContext();
                CharSequence text = "Los datos han sido borrados de sus preferencias y han vuelto a su valor predeterminado";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                FetchSharedPreferences.SyncImmediately(getApplicationContext()); //Prueba de Escritura
                Intent intent1 = new Intent(getApplicationContext(),PreferenceActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
            }
        });

    }

    SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }


}

