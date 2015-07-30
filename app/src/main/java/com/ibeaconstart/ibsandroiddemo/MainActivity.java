package com.ibeaconstart.ibsandroiddemo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ibeaconstart.ibsrestclient.IBSResponseHandler;
import com.ibeaconstart.ibsrestclient.IBSRestClient;
import com.ibeaconstart.ibsrestclient.model.IBSCampaign;
import com.ibeaconstart.ibsrestclient.model.IBSError;

import java.util.List;

public class MainActivity extends ActionBarActivity {
    IBSRestClient restClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restClient = new IBSRestClient(this, "bab4d787f2b3623c60885f9275f8c7fb");

        restClient.getAppCampains(2678400L, new IBSResponseHandler() {

            @Override
            public void getAppCampainsFailed(IBSError error) {
                Log.d("-------->", "Failed: " + error.toString());
            }

            @Override
            public void getAppCampainsSuccess(List<IBSCampaign> campains) {
                Log.d("-------->", "Success: " + campains);
            }
        });
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
