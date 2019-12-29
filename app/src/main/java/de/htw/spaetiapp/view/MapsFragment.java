package de.htw.spaetiapp.view;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;

import de.htw.spaetiapp.R;
import de.htw.spaetiapp.models.MarkerRepository;
import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.util.ToastResponse;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private View mView;
    private FloatingActionButton editButton;
    private FloatingActionButton deleteButton;
    private Animation scaleUp;
    private Animation scaleDown;
    private Marker selectedMarker;
    //    private HashMap<String, Marker> markerMap;
    private final int MY_REQUEST_INT = 177;
    private final int REQUEST_CHECK_SETTINGS = 100;

    public MapsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_maps, container, false);
        mMapView = mView.findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

        //  markerMap = new HashMap<>();

        // Get the button view
        View locationButton = ((View) mView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        // and next place it, for example, on bottom right (as Google Maps app)
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0, 0, 30, 30);

        editButton = mView.findViewById(R.id.editFloatingActionButton);
        deleteButton = mView.findViewById(R.id.deleteFloatingActionButton);

        scaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);
        editButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);

        displayGreetingToast();

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void displayGreetingToast() {
        Toast.makeText(getContext(), "Hello " + ((MainActivity) getActivity()).loadName(), Toast.LENGTH_LONG).show();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        if (isLocationPermissionGranted()) {
            mGoogleMap.setMyLocationEnabled(true);
        } else {
            requestLocationPermission();
        }

        checkIfLocationIsEnabled();

        SpaetiInfoWindowAdapter adapter = new SpaetiInfoWindowAdapter(this.getActivity());

        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setInfoWindowAdapter(adapter);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapClickListener(this);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.5200, 13.4050), 10));

        setButtonListener();
    }

    private void setButtonListener() {
        setDeleteButtonListener();
        setEditButtonListener();
    }

    private void setEditButtonListener() {
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).updateSpaeti(((Spaeti) selectedMarker.getTag()));
            }
        });
    }

    private void setDeleteButtonListener() {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setDeleteDialog();
            }
        });
    }

    private void setDeleteDialog() {
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setMessage("Are you sure you want to delete this Späti?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((MainActivity) getActivity()).removeSpaeti(((Spaeti) selectedMarker.getTag()).get_id());
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                }).create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getActivity(), android.R.color.holo_green_light));
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getActivity(), android.R.color.holo_green_light));
    }

    void removeMarker(Marker marker, boolean isBroadcast) {
        marker.remove();
        if (!isBroadcast) {
            makeFloatingButtonsDisappear();
        }
    }

    private void requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, MY_REQUEST_INT);
        }
    }

    private boolean isLocationPermissionGranted() {
        return (ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    private void checkIfLocationIsEnabled() {
        final LocationManager manager = (LocationManager) this.getContext().getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            displayLocationSettingsRequest(getContext());
        }
    }

    // https://stackoverflow.com/questions/33251373/turn-on-location-services-without-navigating-to-settings-page
    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    void addMarker(Spaeti obj) {
        Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(obj.getLatitude(), obj.getLongitude())));
        marker.setTag(obj);
        ((MainActivity) getContext()).getAddSpaetiController().addMarkerToMarkerRepo(obj.get_id(), marker);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        selectedMarker = marker;
        if (editButton.getVisibility() != View.VISIBLE && deleteButton.getVisibility() != View.VISIBLE) {
            makeFloatingButtonsAppear();
        }
        marker.showInfoWindow();

        return true;
    }

    private void makeFloatingButtonsAppear() {
        editButton.setVisibility(View.VISIBLE);
        deleteButton.setVisibility(View.VISIBLE);
        editButton.startAnimation(scaleUp);
        deleteButton.startAnimation(scaleUp);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (editButton.getVisibility() != View.INVISIBLE && deleteButton.getVisibility() != View.INVISIBLE) {
            makeFloatingButtonsDisappear();
        }
    }

    private void makeFloatingButtonsDisappear() {
        editButton.startAnimation(scaleDown);
        deleteButton.startAnimation(scaleDown);
        editButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);
    }

    void toastOperationAdd(ToastResponse response) {
        switch (response) {
            case SPAETI_ADD_SUCCESSFUL:
                Toast.makeText(getContext(), "Späti was added successfully", Toast.LENGTH_LONG).show();
                break;
            case SPAETI_ADD_SERVER_UNSUCCESSFUL:
                Toast.makeText(getContext(), "Server error: Späti could not be added", Toast.LENGTH_LONG).show();
                break;
            case SPAETI_DELETE_SUCCESSFUL:
                Toast.makeText(getContext(), "Späti was removed successfully", Toast.LENGTH_LONG).show();
                break;
            case SPAETI_DELETE_REPO_UNSUCCESSFUL:
                Toast.makeText(getContext(), "Repository error: Späti could not be deleted", Toast.LENGTH_LONG).show();
                break;
            case SPAETI_UPDATE_SUCCESSFUL:
                Toast.makeText(getContext(), "Späti was updated successfully", Toast.LENGTH_LONG).show();
                break;
            case SPAETI_UPDATE_SERVER_UNSUCCESSFUL:
                Toast.makeText(getContext(), "Server error: Späti could not be updated", Toast.LENGTH_LONG).show();
                break;
            case SPAETI_ADD_REPO_UNSUCCESSFUL:
                Toast.makeText(getContext(), "Repository error: Späti could not be added", Toast.LENGTH_LONG).show();
                break;
            case SPAETI_UPDATE_REPO_UNSUCCESSFUL:
                Toast.makeText(getContext(), "Repository error: Späti could not be updated", Toast.LENGTH_LONG).show();
                break;
            case SPAETI_DELETE_SERVER_UNSUCCESSFUL:
                Toast.makeText(getContext(), "Server error: Späti could not be deleted", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(getContext(), "An unexpected error has occurred", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
