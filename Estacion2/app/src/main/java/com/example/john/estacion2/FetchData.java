package com.example.john.estacion2;

import android.graphics.Color;
import android.os.AsyncTask;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by john on 11/10/17.
 */

public class FetchData extends AsyncTask<Void, Void, Void> {
    String data1;
    String JSON_STRING;
    String Tiempo[] = {"", "", "", "", ""};
    String Temperatura[] = {"", "", "", "", ""};
    String Lluvia[] = {"", "", "", "", ""};
    String Viento[] = {"", "", "", "", ""};


    String json_url;

    public static double MaxArray(String[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {

            result[i] = Double.parseDouble(array[i]);

        }
        double max = result[0];
        for (int j = 0; j < result.length; j++) {

            if (Double.isNaN(result[j])) {
                return Double.NaN;
            }
            if (result[j] > max) {
                max = result[j];
            }
        }
        return max+5;
    }

    public static double MinArray(String[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {

            result[i] = Double.parseDouble(array[i]);

        }
        double min = result[0];
        for (int j = 0; j < result.length; j++) {

            if (Double.isNaN(result[j])) {
                return Double.NaN;
            }
            if (result[j] < min) {
                min = result[j];
            }
        }
        return min-5;
    }

    @Override
    protected void onPreExecute() {
        json_url = "https://estmet.000webhostapp.com/json_get_data.php";
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL(json_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();

            while ((JSON_STRING = bufferedReader.readLine()) != null) {
                stringBuilder.append(JSON_STRING + "\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            data1 = stringBuilder.toString().trim();

            JSONObject JO = new JSONObject(data1);
            JSONArray Jarray = JO.getJSONArray("server_response");
            int i = 0;
            for (int j = Jarray.length() - 5; j < Jarray.length(); j++) {
                JSONObject JO2 = Jarray.getJSONObject(j);

                Tiempo[i] = JO2.getString("indice");
                Temperatura[i] = JO2.getString("Temperatura");
                Lluvia[i] = JO2.getString("Lluvia");
                Viento[i] = JO2.getString("Viento");
                i++;

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.ind1.setText(this.Tiempo[0]);
        MainActivity.ind2.setText(this.Tiempo[1]);
        MainActivity.ind3.setText(this.Tiempo[2]);
        MainActivity.ind4.setText(this.Tiempo[3]);
        MainActivity.ind5.setText(this.Tiempo[4]);

        MainActivity.llu1.setText(this.Lluvia[0]);
        MainActivity.llu2.setText(this.Lluvia[1]);
        MainActivity.llu3.setText(this.Lluvia[2]);
        MainActivity.llu4.setText(this.Lluvia[3]);
        MainActivity.llu5.setText(this.Lluvia[4]);

        MainActivity.temp1.setText(this.Temperatura[0]);
        MainActivity.temp2.setText(this.Temperatura[1]);
        MainActivity.temp3.setText(this.Temperatura[2]);
        MainActivity.temp4.setText(this.Temperatura[3]);
        MainActivity.temp5.setText(this.Temperatura[4]);

        MainActivity.vien1.setText(this.Viento[0]);
        MainActivity.vien2.setText(this.Viento[1]);
        MainActivity.vien3.setText(this.Viento[2]);
        MainActivity.vien4.setText(this.Viento[3]);
        MainActivity.vien5.setText(this.Viento[4]);

        try {
            MainActivity.series1 = new LineGraphSeries<>(new DataPoint[]{
                    new DataPoint(Integer.parseInt(this.Tiempo[0]), Double.parseDouble(this.Lluvia[0])),
                    new DataPoint(Integer.parseInt(this.Tiempo[1]), Double.parseDouble(this.Lluvia[1])),
                    new DataPoint(Integer.parseInt(this.Tiempo[2]), Double.parseDouble(this.Lluvia[2])),
                    new DataPoint(Integer.parseInt(this.Tiempo[3]), Double.parseDouble(this.Lluvia[3])),
                    new DataPoint(Integer.parseInt(this.Tiempo[4]), Double.parseDouble(this.Lluvia[4]))


            });

            MainActivity.series1.setColor(Color.BLUE);
            MainActivity.series1.setDrawBackground(true);
            MainActivity.series1.setBackgroundColor(Color.argb(50, 0, 0, 255));
            MainActivity.series1.setDrawDataPoints(true);
            MainActivity.series1.setTitle("Lluvia");

                /*LineGraphSeries<DataPoint>*/
            MainActivity.series2 = new LineGraphSeries<>(new DataPoint[]{
                    new DataPoint(Integer.parseInt(this.Tiempo[0]), Double.parseDouble(this.Temperatura[0])),
                    new DataPoint(Integer.parseInt(this.Tiempo[1]), Double.parseDouble(this.Temperatura[1])),
                    new DataPoint(Integer.parseInt(this.Tiempo[2]), Double.parseDouble(this.Temperatura[2])),
                    new DataPoint(Integer.parseInt(this.Tiempo[3]), Double.parseDouble(this.Temperatura[3])),
                    new DataPoint(Integer.parseInt(this.Tiempo[4]), Double.parseDouble(this.Temperatura[4]))


            });

            MainActivity.series2.setColor(Color.RED);
            MainActivity.series2.setDrawBackground(true);
            MainActivity.series2.setBackgroundColor(Color.argb(50, 255, 0, 0));
            MainActivity.series2.setDrawDataPoints(true);
            MainActivity.series2.setTitle("Temperatura");

                /*LineGraphSeries<DataPoint>*/
            MainActivity.series3 = new LineGraphSeries<>(new DataPoint[]{
                    new DataPoint(Integer.parseInt(this.Tiempo[0]), Double.parseDouble(this.Viento[0])),
                    new DataPoint(Integer.parseInt(this.Tiempo[1]), Double.parseDouble(this.Viento[1])),
                    new DataPoint(Integer.parseInt(this.Tiempo[2]), Double.parseDouble(this.Viento[2])),
                    new DataPoint(Integer.parseInt(this.Tiempo[3]), Double.parseDouble(this.Viento[3])),
                    new DataPoint(Integer.parseInt(this.Tiempo[4]), Double.parseDouble(this.Viento[4]))


            });

            MainActivity.series3.setColor(Color.GREEN);
            MainActivity.series3.setDrawBackground(true);
            MainActivity.series3.setBackgroundColor(Color.argb(50, 0, 255, 0));
            MainActivity.series3.setDrawDataPoints(true);
            MainActivity.series3.setTitle("Viento");


            MainActivity.Graph1.addSeries(MainActivity.series1);
            MainActivity.Graph.addSeries(MainActivity.series2);
            MainActivity.Graph2.addSeries(MainActivity.series3);

            MainActivity.Graph.getViewport().setMinX(Integer.parseInt(MainActivity.ind1.getText().toString()));
            MainActivity.Graph.getViewport().setMaxX(Integer.parseInt(MainActivity.ind5.getText().toString()));
            MainActivity.Graph.getViewport().setXAxisBoundsManual(true);
            if (MaxArray(Temperatura) == MinArray(Temperatura)) {
                MainActivity.Graph.getViewport().setYAxisBoundsManual(false);
                MainActivity.Graph.getViewport().setScalableY(true);
            } else {
                MainActivity.Graph.getViewport().setYAxisBoundsManual(true);
            }
            MainActivity.Graph.getViewport().setMaxY(MaxArray(Temperatura));
            MainActivity.Graph.getViewport().setMinY(MinArray(Temperatura));


            MainActivity.Graph1.getViewport().setMinX(Integer.parseInt(MainActivity.ind1.getText().toString()));
            MainActivity.Graph1.getViewport().setMaxX(Integer.parseInt(MainActivity.ind5.getText().toString()));
            MainActivity.Graph1.getViewport().setXAxisBoundsManual(true);
            if (MaxArray(Lluvia) == MinArray(Lluvia)) {
                MainActivity.Graph1.getViewport().setYAxisBoundsManual(false);
                MainActivity.Graph1.getViewport().setScalableY(true);
            } else {
                MainActivity.Graph1.getViewport().setYAxisBoundsManual(true);
            }

            MainActivity.Graph1.getViewport().setMaxY(MaxArray(Lluvia));
            MainActivity.Graph1.getViewport().setMinY(MinArray(Lluvia));


            MainActivity.Graph2.getViewport().setMinX(Integer.parseInt(MainActivity.ind1.getText().toString()));
            MainActivity.Graph2.getViewport().setMaxX(Integer.parseInt(MainActivity.ind5.getText().toString()));
            MainActivity.Graph2.getViewport().setXAxisBoundsManual(true);
            if (MaxArray(Viento) == MinArray(Viento)) {
                MainActivity.Graph2.getViewport().setYAxisBoundsManual(false);
                MainActivity.Graph2.getViewport().setScalableY(true);
            } else {
                MainActivity.Graph2.getViewport().setYAxisBoundsManual(true);
            }

            MainActivity.Graph2.getViewport().setMaxY(MaxArray(Viento));
            MainActivity.Graph2.getViewport().setMinY(MinArray(Viento));

        } catch (Exception e) {
        }


    }
}





























