package com.example.john.estacion2;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;


/**
 * Created by john on 13/10/17.
 */

public class FetchSharedPreferences extends IntentService {
    private static final String TAG = "SyncService";
    String data;
    String json_url = "https://estmet.000webhostapp.com/get_preferences_json.php";
    String url_time = "https://estmet.000webhostapp.com/tiempo.txt"; //Solo tengo que añadir dos lineas de codigo al archivo que se altera cuando se programa la alarma
    String JSON_STRING;
    String json_urlEdit = "https://estmet.000webhostapp.com/add_info.php";
    String Temperatura[] = {""};
    String Lluvia[] = {""};
    String Viento[] = {""};
    long lastUpdateTime = 0;
    SharedPreferences prefs;
    SharedPreferences getData;


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public FetchSharedPreferences() {
        super("FetchSharedPreferences");
    }

    public static void SyncImmediately(Context context) {
        Log.i(TAG, "Immediate sync requested. Sending sync intent.");
        Intent startServiceIntent = new Intent(context, FetchSharedPreferences.class);
        context.startService(startServiceIntent);
    }

    public static void ScheduleAlarm(Context context) {
        Log.i(TAG, "Scheduling Preferences alarm, interval: " + 5 * 60 * 1000 / 60000 + " min");
        Intent intent = new Intent(context, SyncAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis(); // Perform initial sync immediately

        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // Perform subsequent syncs every 1 minute
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                5 * 60 * 1000, pIntent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Sync intent received. Fetching data from network.");

        try {
            URL url = new URL(json_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            URL url2 = new URL(url_time);
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection();

            getData = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            lastUpdateTime = getData.getLong("last_modified", 0);
            long currentTime = System.currentTimeMillis();
            long expires = httpURLConnection.getHeaderFieldDate("Expires", currentTime);
            long lastModifiedURL = httpURLConnection2.getLastModified();


            Log.i(TAG, "Internet file last modified: " + new Date(lastModifiedURL));
            Log.i(TAG, "Preferences file last modified: " + new Date(lastUpdateTime));


            // lastUpdateTime represents when the cache was last updated.
            if (lastModifiedURL < lastUpdateTime) {
                // Skip update and write to Database instead
                URL url1 = new URL(json_urlEdit);
                HttpURLConnection httpURLConnection1 = (HttpURLConnection) url1.openConnection();
                httpURLConnection1.setRequestMethod("POST");
                httpURLConnection1.setDoOutput(true);
                OutputStream outputStream = httpURLConnection1.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("indice", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8") + "&" +
                        URLEncoder.encode("Temperatura", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(getDouble(getData, "temp_key", 9999.0)), "UTF-8") + "&" +
                        URLEncoder.encode("Lluvia", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(getDouble(getData, "lluvia_key", 9999.0)), "UTF-8") + "&" +
                        URLEncoder.encode("Viento", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(getDouble(getData, "viento_key", 9999.0)), "UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream1 = httpURLConnection1.getInputStream();
                inputStream1.close();
                httpURLConnection1.disconnect();
                Log.i(TAG, "Database was updated with actual preferences");


            } else {
                // Parse update
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }
                data = stringBuilder.toString().trim();
                JSONObject JO = new JSONObject(data);
                JSONArray Jarray = JO.getJSONArray("server_response");
                int i = 0;
                for (int j = Jarray.length() - 1; j < Jarray.length(); j++) {
                    JSONObject JO2 = Jarray.getJSONObject(j);

                    Temperatura[i] = JO2.getString("Temperatura");
                    Lluvia[i] = JO2.getString("Lluvia");
                    Viento[i] = JO2.getString("Viento");
                    i++;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        try {
            SharedPreferences.Editor editor = prefs.edit();
            /*editor.putString("temp_key", Temperatura[0]);
            editor.putString("lluvia_key", Lluvia[0]);
            editor.putString("viento_key", Viento[0]);*/
            double temp_par = Double.parseDouble(Temperatura[0]);
            double llu_par = Double.parseDouble(Lluvia[0]);
            double vie_par = Double.parseDouble(Viento[0]);
            putDouble(editor, "temp_key", temp_par);
            putDouble(editor, "lluvia_key", llu_par);
            putDouble(editor, "viento_key", vie_par);
            editor.putLong("last_modified", System.currentTimeMillis());
            editor.apply();
            Log.i(TAG, "Preferences were SAVED locally");
        } catch (Exception e) {
            Log.i(TAG, "Preferences were NOT saved locally");

        }
        WakefulBroadcastReceiver.completeWakefulIntent(intent);

    }

    SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }

    public static class SyncAlarmReceiver extends BroadcastReceiver {
        private static final String TAG = "SyncAlarmReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "Broadcast received, sending sync intent to FetchSharedPreferences");
            Intent i = new Intent(context, FetchSharedPreferences.class);
            context.startService(i);
        }
    }

    public static class BootBroadcastReceiver extends WakefulBroadcastReceiver {
        private static final String TAG = "BootBroadcastReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                Log.i(TAG, "Received BOOT_COMPLETED broadcast. Scheduling alarm.");
                FetchSharedPreferences.ScheduleAlarm(context);
            }
        }
    }
}

