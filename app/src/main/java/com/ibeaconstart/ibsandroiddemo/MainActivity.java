package com.ibeaconstart.ibsandroiddemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ibeaconstart.ibsandroidsdk.IBSSDK;
import com.ibeaconstart.ibsrestclient.IBSRestClient;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.logging.LogManager;
import org.altbeacon.beacon.logging.Loggers;

import java.util.Collection;
import java.util.LinkedList;


public class MainActivity extends Activity implements BeaconConsumer {
    private static final String TAG = MainActivity.class.getSimpleName();
    IBSRestClient restClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LogManager.setLogger(Loggers.verboseLogger());
        BeaconManager.getInstanceForApplication(this).setBackgroundMode(false);
        BeaconManager.getInstanceForApplication(this).bind(this);

//        IBSSDK.getInstance().initWithListener(this);
//        IBSSDK.getInstance().verifyBluetooth(this);

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
    protected void onDestroy() {
        super.onDestroy();
        BeaconManager.getInstanceForApplication(this).unbind(this);
    }

    protected void startScanning() {
        if(initialized() && dataAvailableOffline()) {
            BeaconManager.getInstanceForApplication(this).bind(this);

        } else {
            showErrorToast();

        }
    }


    protected void stopScanning() {
        if(initialized()) {
            BeaconManager.getInstanceForApplication(this).unbind(this);

        } else {
            showErrorToast();

        }
    }


    @Override
    public void onBeaconServiceConnect() {
        try {
            BeaconManager mBeaconManager = BeaconManager.getInstanceForApplication(this);

            mBeaconManager.setRangeNotifier(new RangeNotifier() {
                @Override
                public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
                    for (Beacon b : collection) {
                        Log.i(TAG, "Detected beacon " + b.toString() + " at distance " + b.getDistance());
                    }
                }
            });

            mBeaconManager.setMonitorNotifier(new MonitorNotifier() {
                @Override
                public void didEnterRegion(Region region) {
                    Log.i(TAG, "didEnterRegion " + region.getUniqueId());
                }

                @Override
                public void didExitRegion(Region region) {
                    Log.i(TAG, "didExitRegion " + region.getUniqueId());
                }

                @Override
                public void didDetermineStateForRegion(int state, Region region) {
                    Log.i(TAG, "didDetermineState" + state + " ForRegion " + region.getUniqueId());
                }
            });


            mBeaconManager.startMonitoringBeaconsInRegion(new Region("Beacon1", Identifier.parse("b9407f30f5f8466eaff925556b57fe6d"), null, null));
            mBeaconManager.startMonitoringBeaconsInRegion(new Region("Beacon2", Identifier.parse("b9407f30f5f8466eaff925556b57fe60"), null, null));

            mBeaconManager.startRangingBeaconsInRegion(new Region("Beacon1", Identifier.parse("b9407f30f5f8466eaff925556b57fe6d"), null, null));
            mBeaconManager.startRangingBeaconsInRegion(new Region("Beacon2", Identifier.parse("b9407f30f5f8466eaff925556b57fe60"), null, null));

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    protected void verifyBluetooth() {
        try {
            if (BeaconManager.getInstanceForApplication(this).checkAvailability()) {
                bluetoothLEActive();

            } else {
                bluetoothNotActivated();

            }
        } catch (RuntimeException e) {
            bluetoothLENotSupported();
        }
    }


    protected void bluetoothLEActive() {
        //TODO implement
    }


    protected void bluetoothNotActivated() {
//        showAlert(R.string.bt_not_enabled_title, com.ibeaconstart.ibsandroidsdk.R.string.bt_not_enabled_message);
    }


    protected void bluetoothLENotSupported() {
        showAlert(com.ibeaconstart.ibsandroidsdk.R.string.bt_le_not_available_title, com.ibeaconstart.ibsandroidsdk.R.string.bt_le_not_available_message);
    }


    /**
     * Helper methods
     */
    private boolean initialized() {
        return restClient != null;
    }


    private boolean dataAvailableOffline() {
//        return appCampaigns != null && appCampaigns.size() > 0;
        return false;
    }


    private void showErrorToast() {
//        Toast.makeText(IBSActivity.this, "Before you can use the SDK you need to call the initialization method!", Toast.LENGTH_LONG).show();
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
