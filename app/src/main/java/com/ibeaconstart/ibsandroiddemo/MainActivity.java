package com.ibeaconstart.ibsandroiddemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.ibeaconstart.ibsandroidsdk.IBSSDK;
import com.ibeaconstart.ibsandroidsdk.IBSSDKListener;
import com.ibeaconstart.ibsrestclient.IBSRestClient;



public class MainActivity extends ActionBarActivity implements IBSSDKListener {
    IBSRestClient restClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IBSSDK.getInstance().initWithListener(this);
        IBSSDK.getInstance().verifyBluetooth(this);

//        restClient = new IBSRestClient(this, "bab4d787f2b3623c60885f9275f8c7fb");
//
//        restClient.getAppCampains(2678400L, new IBSResponseHandler() {
//
//            @Override
//            public void getAppCampainsFailed(IBSError error) {
//                Log.d("-------->", "Failed: " + error.toString());
//            }
//
//            @Override
//            public void getAppCampainsSuccess(List<IBSCampaign> campains) {
//                Log.d("-------->", "Success: " + campains);
//            }
//        });
    }

    @Override
    public void bluetoothNotActivated() {
        showAlert(R.string.bt_not_enabled_title, R.string.bt_not_enabled_message);
    }

    @Override
    public void bluetoothLENotSupported() {
        showAlert(R.string.bt_le_not_available_title, R.string.bt_le_not_available_message);
    }

    private void showAlert(int titleId, int messageId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titleId);
        builder.setMessage(messageId);
        builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.show();
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
