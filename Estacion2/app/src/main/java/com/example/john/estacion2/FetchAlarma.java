package com.example.john.estacion2;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

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
 * Created by john on 18/10/17.
 */

public class FetchAlarma extends IntentService {
    private static final String Etiqueta = "NotificationService";
    String data;
    String json_url = "https://estmet.000webhostapp.com/json_get_data.php";
    String Temperatura[] = {""};
    String Lluvia[] = {""};
    String Viento[] = {""};
    String JSON_STRING;
    SharedPreferences getData;

    public FetchAlarma() {
        super("FetchAlarma");
    }

    /*public static void FetchImmediately(Context context) {
        Log.i(Etiqueta, "Pedido de Sincronizacion Inmediata. Mandando intento de sincronizacion.");
        Intent startServiceIntent = new Intent(context, FetchAlarma.class);
        context.startService(startServiceIntent);
    }*/

    public static void ProgramarAlarma(Context context) {
        Log.i(Etiqueta, "Programando alarma, intervalo: " + 60 * 1000 / 60000 + " min");
        Intent intent = new Intent(context, FetchAlarma.ReceptorAlarma.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis(); // Perform initial sync immediately

        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // Perform subsequent syncs every 1 minute
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                60 * 1000, pIntent);
    }

    public void ShowNotificationTemperature() {
        Intent Notiintent = new Intent(this, WebViewActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(WebViewActivity.class);
        stackBuilder.addNextIntent(Notiintent);
        PendingIntent contentIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(this);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.termosettings)
                .setContentTitle("Notificacion Temperatura")
                .setContentText("Se ha igualado el valor " + Temperatura[0] + "°C de temperatura programado.")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Se ha igualado el valor " + Temperatura[0] + "°C de temperatura programado."));


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, b.build());


    }

    public void ShowNotificationLluvia() {
        Intent Notiintent = new Intent(this, WebViewActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(WebViewActivity.class);
        stackBuilder.addNextIntent(Notiintent);
        PendingIntent contentIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(this);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.lluviasettings)
                .setContentTitle("Notificacion Lluvia")
                .setContentText("Se ha igualado el valor " + Lluvia[0] + " de intensidad de lluvia programado.")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Se ha igualado el valor " + Lluvia[0] + " de intensidad de lluvia programado."));


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, b.build());


    }

    public void ShowNotificationViento() {
        Intent Notiintent = new Intent(this, WebViewActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(WebViewActivity.class);
        stackBuilder.addNextIntent(Notiintent);
        PendingIntent contentIntent = stackBuilder.getPendingIntent(2, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(this);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.vientosettings)
                .setContentTitle("Notificacion Viento")
                .setContentText("Se ha igualado el valor " + Viento[0] + " km/h de intensidad de viento programado.")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Se ha igualado el valor " + Viento[0] + " km/h de intensidad de viento programado."));


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(2, b.build());


    }


    public void SupShowNotificationTemperature() {
        Intent Notiintent = new Intent(this, WebViewActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(WebViewActivity.class);
        stackBuilder.addNextIntent(Notiintent);
        PendingIntent contentIntent = stackBuilder.getPendingIntent(3, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(this);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.termosettings)
                .setContentTitle("Notificacion Temperatura")
                .setContentText("Se ha superado el valor " + getDouble(getData, "temp_key", 9999.0) + "°C de temperatura programado.")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Se ha superado el valor " + getDouble(getData, "temp_key", 9999.0) + "°C de temperatura programado. Y el actual es de " +Temperatura[0]+"°C"));


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(3, b.build());


    }

    public void SupShowNotificationLluvia() {
        Intent Notiintent = new Intent(this, WebViewActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(WebViewActivity.class);
        stackBuilder.addNextIntent(Notiintent);
        PendingIntent contentIntent = stackBuilder.getPendingIntent(4, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(this);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.lluviasettings)
                .setContentTitle("Notificacion Lluvia")
                .setContentText("Se ha superado el valor " + getDouble(getData, "lluvia_key", 9999.0) + "% de intensidad de lluvia programado.")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Se ha superado el valor " + getDouble(getData, "lluvia_key", 9999.0) + "% de intensidad de lluvia programado. Y el actual es de " + Lluvia[0]+"%"));


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(4, b.build());


    }

    public void SupShowNotificationViento() {
        Intent Notiintent = new Intent(this, WebViewActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(WebViewActivity.class);
        stackBuilder.addNextIntent(Notiintent);
        PendingIntent contentIntent = stackBuilder.getPendingIntent(5, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(this);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.vientosettings)
                .setContentTitle("Notificacion Viento")
                .setContentText("Se ha superado el valor " + getDouble(getData, "viento_key", 9999.0) + " km/h de intensidad de viento programado.")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Se ha superado el valor " + getDouble(getData, "viento_key", 9999.0) + " km/h de intensidad de viento programado.Y el actual es de "+ Viento[0]+" km/h"));


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(5, b.build());


    }

    @Override
    public void onHandleIntent(Intent intent) {

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
            data = stringBuilder.toString().trim();

            JSONObject JO = new JSONObject(data);
            JSONArray Jarray = JO.getJSONArray("server_response");
            for (int j = Jarray.length() - 1; j < Jarray.length(); j++) {

                JSONObject JO2 = Jarray.getJSONObject(j);

                Temperatura[0] = JO2.getString("Temperatura");
                Lluvia[0] = JO2.getString("Lluvia");
                Viento[0] = JO2.getString("Viento");
            }
            getData = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            if (Double.parseDouble(Temperatura[0]) == getDouble(getData, "temp_key", 9999.0)) {
                Log.i(Etiqueta, "Notificacion por Temperatura");
                ShowNotificationTemperature();
            } else if (Double.parseDouble(Temperatura[0]) >= getDouble(getData, "temp_key", 9999.0)) {
                SupShowNotificationTemperature();
            } else {
                Log.i(Etiqueta, "No hubo Notificacion por Temperatura");
            }
            if (Double.parseDouble(Lluvia[0]) == getDouble(getData, "lluvia_key", 9999.0)) {
                Log.i(Etiqueta, "Notificacion por Lluvia");
                ShowNotificationLluvia();
            } else if (Double.parseDouble(Lluvia[0]) >= getDouble(getData, "lluvia_key", 9999.0)) {
                SupShowNotificationLluvia();
            } else {
                Log.i(Etiqueta, "No hubo Notificacion por Lluvia");
            }
            if (Double.parseDouble(Viento[0]) == getDouble(getData, "viento_key", 9999.0)) {
                Log.i(Etiqueta, "Notificacion por Viento");
                ShowNotificationViento();
            } else if (Double.parseDouble(Viento[0]) >= getDouble(getData, "viento_key", 9999.0)) {
                SupShowNotificationViento();
            } else {
                Log.i(Etiqueta, "No hubo Notificacion por Viento");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }

    public static class ReceptorAlarma extends BroadcastReceiver {
        private static final String Etiqueta = "ReceptorAlarma";

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(Etiqueta, "Broadcast received, sending sync intent to FetchAlarma");
            Intent i = new Intent(context, FetchAlarma.class);
            context.startService(i);
        }
    }

    public static class TransmisorArranque extends WakefulBroadcastReceiver {
        private static final String Etiqueta = "ReceptorArranque";

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(Etiqueta, "Transmision BOOT_COMPLETED recibida. Programando alarma.");
            FetchAlarma.ProgramarAlarma(context);
        }
    }

}


