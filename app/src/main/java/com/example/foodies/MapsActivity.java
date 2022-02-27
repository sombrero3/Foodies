package com.example.foodies;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.foodies.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    SearchView searchView;
    Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String language="iw_IL";
//        Locale locale=new Locale(language);
//        Locale.setDefault(locale);
//        Configuration config=new Configuration();
//        config.locale=locale;
//        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        searchView=findViewById(R.id.location_sv);
        continueBtn=findViewById(R.id.map_continue_btn);
        continueBtn.setVisibility(View.INVISIBLE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location=searchView.getQuery().toString();
                Log.d("TAG2", location);
                List<Address> addressList=null;

                Geocoder geocoder=new Geocoder(MapsActivity.this);
                try {
                    addressList=geocoder.getFromLocationName(location, 4);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                    mMap.clear();
                if(addressList.size() == 0) {
                    Toast.makeText(getApplicationContext(), "No address was found", Toast.LENGTH_LONG).show();
                    Log.d("TAG", "TOASTTTTTTTTTTTTTT ");
                }else{
                Log.d("TAG", Integer.toString(addressList.size()));
                for(int i=0;i<addressList.size();i++) {
                    Address address = addressList.get(i);
                    Log.d("TAG", addressList.get(i).getAddressLine(0).toString());
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));


                }
                    searchView.setVisibility(View.INVISIBLE);
                    continueBtn.setVisibility(View.VISIBLE);
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);
    }

    public void onContinueClick(){
        continueBtn.setOnClickListener((v)->{

        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);


        // Add a marker in Tel Aviv and move the camera
        LatLng tel_aviv = new LatLng(32.085708977347345, 34.78195870738348);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tel_aviv));
    }
}