package com.flyeek.dev.demo.location.gmslocation;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.util.List;
import java.util.Locale;

public class FetchAddressIntentService extends IntentService {

    public static final int SUCCESS_RESULT = 0;

    public static final int FAILURE_RESULT = 1;

    public static final String PACKAGE_NAME = "com.zuiapps.lookgood.main.service";

    public static final String RECEIVER = PACKAGE_NAME + ".receiver";

    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".result_data_key";

    public static final String RESULT_ADDRESS_DATA_KEY = PACKAGE_NAME + ".result_address_data_key";

    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".location_data_extra";

    private static final String TAG = "FetchAddress";

    protected ResultReceiver mReceiver;

    public FetchAddressIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String errorMessage = "";

        mReceiver = intent.getParcelableExtra(RECEIVER);

        if (mReceiver == null) {
            Log.d(TAG, "No receiver received. There is nowhere to send the results.");
            return;
        }

        Location location = intent.getParcelableExtra(LOCATION_DATA_EXTRA);

        if (location == null) {
            errorMessage = "location == null";
            Log.d(TAG, errorMessage);
            deliverResultToReceiver(FAILURE_RESULT, errorMessage);
            return;
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "addresses == null || addresses.size() == 0";
                Log.e(TAG, errorMessage);
            }
            deliverResultToReceiver(FAILURE_RESULT, errorMessage);
        } else {
            Address address = addresses.get(0);
            String addMsg = address.getCountryName() + "," + address.getAdminArea();

            Bundle bundle = new Bundle();
            bundle.putParcelable(RESULT_ADDRESS_DATA_KEY, address);
            bundle.putString(RESULT_DATA_KEY, addMsg);
            mReceiver.send(SUCCESS_RESULT, bundle);
        }
    }

    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(RESULT_DATA_KEY, message);
        mReceiver.send(resultCode, bundle);
    }
}
