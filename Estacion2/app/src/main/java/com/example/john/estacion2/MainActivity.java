package com.example.john.estacion2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button Preferencias = (Button) findViewById(R.id.preferencias);
        Button Salir = (Button) findViewById(R.id.salir);

        Preferencias.setOnClickListener(this);
        Salir.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.preferencias:
                Intent intent1 = new Intent("com.example.john.estacion2.PREFERENCES");
                startActivity(intent1);
                break;
            case R.id.salir:
                finish();
                break;


        }
    }
}
