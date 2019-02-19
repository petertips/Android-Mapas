package cl.petertips.mapas;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    double lt, ln;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        /*// AGREGAR línea poligonal
        PolylineOptions rectOptions = new PolylineOptions()
                .add(new LatLng(37.35, -122.0))
                .add(new LatLng(37.45, -122.0))
                .add(new LatLng(37.45, -122.2))
                .add(new LatLng(37.35, -122.2))
                .add(new LatLng(37.35, -122.0));
        Polyline polyline = mMap.addPolyline(rectOptions);

        // AGREGAR polígono
        Polygon polygon = mMap.addPolygon(new PolygonOptions()
                .add(new LatLng(0, 0), new LatLng(0, 5), new LatLng(3, 5), new LatLng(0, 0))
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));*/

        Location x = getLastKnownLocation();
        lt=x.getLatitude();
        ln=x.getLongitude();

        // Instantiates a new CircleOptions object and defines the center and radius
        Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(lt, ln))
                .radius(1000)
                .strokeColor(Color.RED)
                .fillColor(Color.TRANSPARENT));


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lt, ln);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Yo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));



    }
    private Location getLastKnownLocation(){
        if( ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ){
            return null;
        }
        LocationManager locationManager =
                (LocationManager) this.getSystemService( LOCATION_SERVICE );
        List<String> providers = locationManager.getProviders( true );
        Location bestLocation = null;
        for( String provider : providers ){
            Location l = locationManager.getLastKnownLocation( provider );
            if( l == null ){
                continue;
            }
            if( bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy() ){
                bestLocation = l; // Found best last known location;
            }
        }
        return bestLocation;
    }
    @Override
    public void onLocationChanged(Location location) {
        Location x = getLastKnownLocation();
        lt=x.getLatitude();
        ln=x.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
