package com.example.john.estacion2;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /*Nuevo*/
    public static TextView ind1, ind2, ind3, ind4, ind5;
    public static TextView temp1, temp2, temp3, temp4, temp5;
    public static TextView llu1, llu2, llu3, llu4, llu5;
    public static TextView vien1, vien2, vien3, vien4, vien5;
    public static GraphView Graph, Graph1, Graph2;
    public static LineGraphSeries<DataPoint> series1, series2, series3;
    TextView indcol, Tempcol, Llucol, Viencol;


    /*Nuevo*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button Preferencias = (Button) findViewById(R.id.preferencias);
        Button Salir = (Button) findViewById(R.id.salir);

        indcol = (TextView) findViewById(R.id.indiceCol);
        Tempcol = (TextView) findViewById(R.id.TemperaturaCol);
        Llucol = (TextView) findViewById(R.id.LluviaCol);
        Viencol = (TextView) findViewById(R.id.VientoCol);

        Button json = (Button) findViewById(R.id.boton_prueba);

        json.setOnClickListener(this);
        Preferencias.setOnClickListener(this);
        Salir.setOnClickListener(this);

        Typeface customfontInst = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams_BoldItalic.ttf");
        indcol.setTypeface(customfontInst);
        Tempcol.setTypeface(customfontInst);
        Llucol.setTypeface(customfontInst);
        Viencol.setTypeface(customfontInst);


        /*Nuevo*/

        ind1 = (TextView) findViewById(R.id.ind1);
        ind2 = (TextView) findViewById(R.id.ind2);
        ind3 = (TextView) findViewById(R.id.ind3);
        ind4 = (TextView) findViewById(R.id.ind4);
        ind5 = (TextView) findViewById(R.id.ind5);

        temp1 = (TextView) findViewById(R.id.temp1);
        temp2 = (TextView) findViewById(R.id.temp2);
        temp3 = (TextView) findViewById(R.id.temp3);
        temp4 = (TextView) findViewById(R.id.temp4);
        temp5 = (TextView) findViewById(R.id.temp5);

        llu1 = (TextView) findViewById(R.id.llu1);
        llu2 = (TextView) findViewById(R.id.llu2);
        llu3 = (TextView) findViewById(R.id.llu3);
        llu4 = (TextView) findViewById(R.id.llu4);
        llu5 = (TextView) findViewById(R.id.llu5);

        vien1 = (TextView) findViewById(R.id.vien1);
        vien2 = (TextView) findViewById(R.id.vien2);
        vien3 = (TextView) findViewById(R.id.vien3);
        vien4 = (TextView) findViewById(R.id.vien4);
        vien5 = (TextView) findViewById(R.id.vien5);

        /*Nuevo*/

        /*Nuevo*/
        Graph = (GraphView) findViewById(R.id.graph1);
        Graph.setTitle("Temperatura");
        Graph.setTitleTextSize(50);
        Graph.getLegendRenderer().setVisible(true);
        Graph.getLegendRenderer().setWidth(40);
        Graph.getLegendRenderer().setTextSize(40);
        Graph.getLegendRenderer().setSpacing(10);
        Graph.getLegendRenderer().setFixedPosition(50, 25);
        Graph.getLegendRenderer().setBackgroundColor(Color.rgb(226, 219, 152));
        Graph.getViewport().setScalable(true);
        Graph.getViewport().setScalableY(true);
        Graph.getGridLabelRenderer().setHorizontalAxisTitle("Tiempo");

        Graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) + " °C";
                }
            }
        });

        Graph1 = (GraphView) findViewById(R.id.graph2);
        Graph1.setTitle("Lluvia");
        Graph1.setTitleTextSize(50);
        Graph1.getLegendRenderer().setVisible(true);
        Graph1.getLegendRenderer().setWidth(40);
        Graph1.getLegendRenderer().setTextSize(40);
        Graph1.getLegendRenderer().setSpacing(10);
        Graph1.getLegendRenderer().setFixedPosition(50, 25);
        Graph1.getLegendRenderer().setBackgroundColor(Color.rgb(226, 219, 152));
        Graph1.getViewport().setScalable(true);
        Graph1.getViewport().setScalableY(true);
        Graph1.getGridLabelRenderer().setHorizontalAxisTitle("Tiempo");
        Graph1.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) + " %";
                }
            }
        });

        Graph2 = (GraphView) findViewById(R.id.graph3);
        Graph2.setTitle("Viento");
        Graph2.setTitleTextSize(50);
        Graph2.getLegendRenderer().setVisible(true);
        Graph2.getLegendRenderer().setWidth(40);
        Graph2.getLegendRenderer().setTextSize(40);
        Graph2.getLegendRenderer().setSpacing(10);
        Graph2.getLegendRenderer().setFixedPosition(50, 25);
        Graph2.getLegendRenderer().setBackgroundColor(Color.rgb(226, 219, 152));
        Graph2.getViewport().setScalable(true);
        Graph1.getViewport().setScalableY(true);
        Graph2.getGridLabelRenderer().setHorizontalAxisTitle("Tiempo");
        Graph2.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) + " km/h";
                }
            }
        });


        /*Nuevo*/

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            ind1.setText(savedInstanceState.getString("ind1"));
            ind2.setText(savedInstanceState.getString("ind2"));
            ind3.setText(savedInstanceState.getString("ind3"));
            ind4.setText(savedInstanceState.getString("ind4"));
            ind5.setText(savedInstanceState.getString("ind5"));

            temp1.setText(savedInstanceState.getString("temp1"));
            temp2.setText(savedInstanceState.getString("temp2"));
            temp3.setText(savedInstanceState.getString("temp3"));
            temp4.setText(savedInstanceState.getString("temp4"));
            temp5.setText(savedInstanceState.getString("temp5"));

            llu1.setText(savedInstanceState.getString("llu1"));
            llu2.setText(savedInstanceState.getString("llu2"));
            llu3.setText(savedInstanceState.getString("llu3"));
            llu4.setText(savedInstanceState.getString("llu4"));
            llu5.setText(savedInstanceState.getString("llu5"));

            vien1.setText(savedInstanceState.getString("vien1"));
            vien2.setText(savedInstanceState.getString("vien2"));
            vien3.setText(savedInstanceState.getString("vien3"));
            vien4.setText(savedInstanceState.getString("vien4"));
            vien5.setText(savedInstanceState.getString("vien5"));
            try {
                Graph1.addSeries(series1);
                Graph.addSeries(series2);
                Graph2.addSeries(series3);

                Graph.getViewport().setMinX(Integer.parseInt(MainActivity.ind1.getText().toString()));
                Graph.getViewport().setMaxX(Integer.parseInt(MainActivity.ind5.getText().toString()));
                Graph.getViewport().setXAxisBoundsManual(true);

                Graph1.getViewport().setMinX(Integer.parseInt(MainActivity.ind1.getText().toString()));
                Graph1.getViewport().setMaxX(Integer.parseInt(MainActivity.ind5.getText().toString()));
                Graph1.getViewport().setXAxisBoundsManual(true);


                Graph2.getViewport().setMinX(Integer.parseInt(MainActivity.ind1.getText().toString()));
                Graph2.getViewport().setMaxX(Integer.parseInt(MainActivity.ind5.getText().toString()));
                Graph2.getViewport().setXAxisBoundsManual(true);

            } catch (Exception e) {
            }

        } else {
            // Probably initialize members with default values for a new instance

        }
        FetchSharedPreferences.ScheduleAlarm(this);
        FetchAlarma.ProgramarAlarma(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.preferencias:
                Intent intent1 = new Intent("com.example.john.estacion2.PREFERENCES");
                startActivity(intent1);
                break;
            case R.id.salir:
                Graph.removeAllSeries();
                Graph1.removeAllSeries();
                Graph2.removeAllSeries();
                series1 = null;
                series2 = null;
                series3 = null;
                finish();
                break;
            case R.id.boton_prueba:
                getJSON(v);
                Graph.removeAllSeries();
                Graph1.removeAllSeries();
                Graph2.removeAllSeries();
                break;


        }
    }


    /*Tanto el getJSON como BackgroundTask son para la parte de la extraccion de la info
      * de la base de datos por lo tanto de momento no hacen nada ya que hace falta agregar los
       * datos respectivos en el archivo get_json_data.php y tener un dominio determinado*/
    public void getJSON(View view) {
        FetchData process = new FetchData();
        process.execute();

    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("ind1", ind1.getText().toString());
        savedInstanceState.putString("ind2", ind2.getText().toString());
        savedInstanceState.putString("ind3", ind3.getText().toString());
        savedInstanceState.putString("ind4", ind4.getText().toString());
        savedInstanceState.putString("ind5", ind5.getText().toString());

        savedInstanceState.putString("temp1", temp1.getText().toString());
        savedInstanceState.putString("temp2", temp2.getText().toString());
        savedInstanceState.putString("temp3", temp3.getText().toString());
        savedInstanceState.putString("temp4", temp4.getText().toString());
        savedInstanceState.putString("temp5", temp5.getText().toString());

        savedInstanceState.putString("llu1", llu1.getText().toString());
        savedInstanceState.putString("llu2", llu2.getText().toString());
        savedInstanceState.putString("llu3", llu3.getText().toString());
        savedInstanceState.putString("llu4", llu4.getText().toString());
        savedInstanceState.putString("llu5", llu5.getText().toString());

        savedInstanceState.putString("vien1", vien1.getText().toString());
        savedInstanceState.putString("vien2", vien2.getText().toString());
        savedInstanceState.putString("vien3", vien3.getText().toString());
        savedInstanceState.putString("vien4", vien4.getText().toString());
        savedInstanceState.putString("vien5", vien5.getText().toString());


        super.onSaveInstanceState(savedInstanceState);
    }

}

