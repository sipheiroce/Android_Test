package com.sipheiroce.helloworld;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    private LocationManager manager;
    private Location location;
    private TextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        view = (TextView) findViewById(R.id.this_is_id_name);
        Toast.makeText(getBaseContext(), "你好", Toast.LENGTH_LONG).show();

        String url = "http://api.bart.gov/api/etd.aspx?cmd=etd&orig=CIVC&key=MW9S-E7SL-26DU-VV8V";
        HttpClient Client = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String SetServerString = "";
        try {
            SetServerString = Client.execute(httpget, responseHandler);
            view.setText(SetServerString);
        } catch(IOException e) {
            view.setText("fail:" + e.getMessage());
        }


        //updateView(location);
        /*manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                2000, 8, new LocationListener() {

                    @Override
                    public void onLocationChanged(Location location) {
                        // 当GPS定位信息发生改变时，更新位置
                        updateView(location);
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        updateView(null);
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        // 当GPS LocationProvider可用时，更新位置
                        updateView(manager.getLastKnownLocation(provider));

                    }

                    @Override
                    public void onStatusChanged(String provider, int status,
                                                Bundle extras) {
                    }
                });*/

    }

    private void updateView(Location location) {
        if (location != null) {

            String str = "经度:" + location.getLatitude() +
                         "\n纬度:" + location.getLongitude() +
                         "\n高度:" + location.getAltitude() +
                         "\n速度:" + location.getSpeed() +
                         "\n方向:" + location.getBearing() +
                         "\n精度:" + location.getAccuracy();

            view.setText(str);
        } else {
            // 如果传入的Location对象为空则清空EditText
            view.setText("no data");
        }
    }

    private String requestURL(String url){
        /*HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = httpclient.execute(new HttpGet(url));
        StatusLine statusLine = response.getStatusLine();
        if(statusLine.getStatusCode() == HttpStatus.SC_OK){
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            response.getEntity().writeTo(out);
            String responseString = out.toString();
            out.close();
            return responseString;
            //..more logic
        } else{
            //Closes the connection.
            response.getEntity().getContent().close();
            throw new IOException(statusLine.getReasonPhrase());
        }*/
        return "XX";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
