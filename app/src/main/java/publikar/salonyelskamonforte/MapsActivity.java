package publikar.salonyelskamonforte;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import maps.GMapV2Direction;
import maps.GetDirectionAsyncTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private LatLngBounds latLngBounds;
    private Polyline polyline;
    private Button btnNavegar;
    private int width, height;
   // private static final LatLng MERIDA = new LatLng(20.96778, -89.62167);
    private static final LatLng SALON = new LatLng(21.0025435, -89.6312312);
    private LatLng MIUBICACION = null;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
       // btnNavegar = (Button) findViewById(R.id.btnNavegar);
        getScreenDim();
        encontrarDireccion(MIUBICACION.latitude, MIUBICACION.longitude,
                SALON.latitude, SALON.longitude, GMapV2Direction.MODE_DRIVING);
       /* btnNavegar.setOnClickListener(new View.OnClickListener() {
         //   @Override
          public void onClick(View v) {

            }
        });
*/

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

        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        requestpermission();
        mMap.addMarker(new MarkerOptions().position(SALON).title("Salón Yelska Monforte"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(SALON));
    }



    private void requestpermission() {

        ActivityCompat.requestPermissions(MapsActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0) {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    public void encontrarDireccion(double fromPositionLat, double fromPositionLong,
                                   double toPositionLat, double toPositionLong,
                                   String mode) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(GetDirectionAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionLat));
        map.put(GetDirectionAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionLong));
        map.put(GetDirectionAsyncTask.DESTINATION_LAT, String.valueOf(toPositionLat));
        map.put(GetDirectionAsyncTask.DESTINATION_LONG, String.valueOf(toPositionLong));
        map.put(GetDirectionAsyncTask.DIRECTIONS_MODE, String.valueOf(mode));

        GetDirectionAsyncTask asyncTask = new GetDirectionAsyncTask(this);
        asyncTask.execute(map);

    }

    public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints) {
        PolylineOptions linea = new PolylineOptions().width(5).color(Color.RED);

        for (int i = 0; i < directionPoints.size(); i++) {
            linea.add(directionPoints.get(i));
        }

        if (polyline != null) {
            polyline.remove();
        }
        polyline = mMap.addPolyline(linea);
        latLngBounds = getLatLngBoundsObject(MIUBICACION, SALON);
        latLngBounds = getLatLngBoundsObject(MIUBICACION, SALON);
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, width, height, 150));


    }

    private void getScreenDim() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        MapsActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;
        height = displaymetrics.heightPixels;
    }

    private LatLngBounds getLatLngBoundsObject(LatLng firstLocation,
                                               LatLng secondLocation) {
        if (firstLocation != null && secondLocation != null) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(firstLocation).include(secondLocation);
            return builder.build();
        }

        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            MIUBICACION=new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}