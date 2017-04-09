package gatech.water_app.controller.Controller;

import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.Manifest;
import android.widget.Toast;

import java.util.ArrayList;

import gatech.water_app.R;
import gatech.water_app.model.ServerConnector;
import gatech.water_app.model.User;
import gatech.water_app.model.WaterReportTask;
import gatech.water_app.model.WaterSourceReport;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    private GoogleMap mMap;
    private User loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle extras = getIntent().getExtras();
//        username = extras.getString("username");
//        password = extras.getString("pass");

        loginUser =(User)extras.getSerializable("user");
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {

            mMap.setMyLocationEnabled(true);
        }



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
        mMap = googleMap;
       // mMap.setOnMyLocationButtonClickListener((GoogleMap.OnMyLocationButtonClickListener) this);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        new HTTPGetReportTask().execute(loginUser);
    }

    private class HTTPGetReportTask extends AsyncTask<Object, Integer, ArrayList<WaterSourceReport>> {
        protected ArrayList<WaterSourceReport> doInBackground(Object[] params) {
            try {
                User castedUser = (User) params[0];

                return ServerConnector.getSourceReports(castedUser);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(ArrayList<WaterSourceReport> result) {
            if (result != null) {
                populateMap(result);
                Log.d("SourceView", "It appears that the request was successful ");
            }
        }
    }

    private void populateMap(ArrayList<WaterSourceReport> list) {

        final ArrayList<WaterSourceReport> sourceList = list;
        // mMap.setOnMyLocationButtonClickListener((GoogleMap.OnMyLocationButtonClickListener) this);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        //Place markers on the map

        for (int i = 0; i < list.size(); i++){
            mMap.addMarker(new MarkerOptions().position(new LatLng(list.get(i).getLocation().getLatitude(),
                    list.get(i).getLocation().getLongitude())).title(list.get(i).getLocation().getProvider()));
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            public boolean onMarkerClick(Marker marker) {
                String markerMessage = WaterReportTask.getSourceReportInfo(marker.getPosition(), sourceList);
                Toast.makeText(getApplicationContext(), markerMessage, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

}
