package org.pfccap.education.utilities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import org.pfccap.education.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double lat;
    double lng;

    @BindView(R.id.utilitiesMapsTxt)
    EditText mapAddress;

    @BindView(R.id.utilitiesMapBtnBack)
    Button btnGetAddress;

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ButterKnife.bind(this);

        btnGetAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("Address", mapAddress.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
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
        mMap.getUiSettings().setZoomControlsEnabled(true);

        checkPermission();

        if (lat != 0.0 && lng != 0.0) {
            LatLng cali = new LatLng(lat, lng);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(cali));
            CameraPosition camPos = new CameraPosition.Builder()
                    .target(cali)   //Centramos el mapa en Madrid
                    .zoom(17)         //Establecemos el zoom en 18
                    //           .bearing(45)      //Establecemos la orientación con el noreste arriba
                    //     .tilt(70)         //Bajamos el punto de vista de la cámara 70 grados
                    .build();

            CameraUpdate camUpd3 =
                    CameraUpdateFactory.newCameraPosition(camPos);

            mMap.animateCamera(camUpd3);
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(lat, lng, 1);
                if (!list.isEmpty()) {
                    Address address = list.get(0);
                    //  Toast.makeText(MapsActivity.this, address.getAddressLine(0), Toast.LENGTH_SHORT).show();
                    String resultado = "";
                    String direccion = address.getAddressLine(0);
                    Pattern pattern = Pattern.compile("\\s*(.*?)\\sa\\s");
                    Matcher matcher = pattern.matcher(direccion);
                    if (matcher.find()) {
                        resultado = matcher.group(1);
                        mapAddress.setText(resultado);
                    } else {
                        mapAddress.setText(address.getAddressLine(0));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            //me posiscion en cali mientras epero respuesta del gps
            LatLng cali = new LatLng(3.41667, -76.55);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(cali));
            CameraPosition camPos = new CameraPosition.Builder()
                    .target(cali)   //Centramos el mapa en Madrid
                    .zoom(15)         //Establecemos el zoom en 18
                    .build();

            CameraUpdate camUpd3 =
                    CameraUpdateFactory.newCameraPosition(camPos);

            mMap.animateCamera(camUpd3);
        }

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            public void onCameraChange(CameraPosition position) {
                lat = position.target.latitude;
                lng = position.target.longitude;
                try {
                    Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                    List<Address> list = geocoder.getFromLocation(position.target.latitude, position.target.longitude, 1);
                    if (!list.isEmpty()) {
                        Address address = list.get(0);
                        //  Toast.makeText(MapsActivity.this, address.getAddressLine(0), Toast.LENGTH_SHORT).show();
                        String resultado = "";
                        String direccion = address.getAddressLine(0);
                        Pattern pattern = Pattern.compile("\\s*(.*?)\\sa\\s");
                        Matcher matcher = pattern.matcher(direccion);
                        if (matcher.find()) {
                            resultado = matcher.group(1);
                            mapAddress.setText(resultado);
                        } else {
                            mapAddress.setText(address.getAddressLine(0));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void checkPermission(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mMap.setMyLocationEnabled(true);
        } else if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);
        } else if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(REQUEST_CODE_ASK_PERMISSIONS == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermission();
            } else {
                Toast.makeText(this, "No has aceptado permisos!", Toast.LENGTH_LONG).show();
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
