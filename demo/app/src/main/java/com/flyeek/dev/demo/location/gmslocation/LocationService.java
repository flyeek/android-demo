package com.flyeek.dev.demo.location.gmslocation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

/**
 * Created by flyeek on 10/12/15.
 */
public class LocationService implements GoogleApiClient.ConnectionCallbacks
        ,GoogleApiClient.OnConnectionFailedListener
        ,ResultCallback<LocationSettingsResult> {

    public static final int REQUEST_CHECK_SETTINGS = 0x1;

    public static final int LOCATION_SETTINGS_ENABLED = 0;
    public static final int LOCATION_SETTINGS_NEED_GRANT = 1;
    public static final int LOCATION_SETTINGS_UNAVAILABLE = 2;

    private static final String TAG = "LocationService";
    private static LocationService mInstance;

    private Context mContext;
    private Activity mActivity;

    private Listener mListener;
    private GoogleApiClient mGoogleApiClient;
    private LocationSettingsRequest mLocationSettingsRequest;
    private AddressResultReceiver mResultReceiver;

    private LocationService() {
    }

    public static LocationService getInstance() {
        if (mInstance == null) {
            synchronized (LocationService.class) {
                if (mInstance == null) {
                    mInstance = new LocationService();
                }
            }
        }

        return mInstance;
    }

    public void initialize(Activity activity, Listener listener) {
        mContext = activity.getApplicationContext();
        mActivity = activity;
        mListener = listener;

        mResultReceiver = new AddressResultReceiver(new Handler());
        buildGoogleApiClient();
        buildLocationSettingsRequest();
    }

    private synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(mInstance)
                .addOnConnectionFailedListener(mInstance)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void buildLocationSettingsRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        mLocationSettingsRequest = builder.build();
    }

    public void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(mInstance);
    }

    public void getLocation() {
        if (Geocoder.isPresent()) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            Intent intent = new Intent(mContext, FetchAddressIntentService.class);
            intent.putExtra(FetchAddressIntentService.RECEIVER, mResultReceiver);
            intent.putExtra(FetchAddressIntentService.LOCATION_DATA_EXTRA, location);

            mContext.startService(intent);
        } else {
            mListener.onLocationAcquireResult(null);
        }
    }

    public void release() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Connection success");
        mListener.onInitialized(true);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
        mListener.onInitialized(false);
    }

    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                mListener.onLocationSettingsCheckResult(LOCATION_SETTINGS_ENABLED);
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");
                mListener.onLocationSettingsCheckResult(LOCATION_SETTINGS_NEED_GRANT);
                try {
                    status.startResolutionForResult(mActivity, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog " + "not created.");
                mListener.onLocationSettingsCheckResult(LOCATION_SETTINGS_UNAVAILABLE);
                break;
        }
    }

    public interface Listener {

        void onInitialized(boolean isSuccess);

        void onLocationSettingsCheckResult(int result);

        void onLocationAcquireResult(Address address);
    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            Log.d(TAG, "onReceiveResult " + resultData.get(FetchAddressIntentService.RESULT_DATA_KEY));
            if (resultCode == FetchAddressIntentService.SUCCESS_RESULT) {
                Address address = resultData.getParcelable(FetchAddressIntentService.RESULT_ADDRESS_DATA_KEY);
                mListener.onLocationAcquireResult(address);
            } else {
                mListener.onLocationAcquireResult(null);
            }
        }
    }

}
