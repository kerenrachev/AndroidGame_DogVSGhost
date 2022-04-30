package com.example.androidgame_dogvsghost.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidgame_dogvsghost.R;
import com.example.androidgame_dogvsghost.utils.MyLoaction;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.androidgame_dogvsghost.databinding.ActivityFragmentGoogleMapsBinding;

public class Fragment_GoogleMaps extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AppCompatActivity appCompatActivity;
    
    public Fragment_GoogleMaps(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }
    public Fragment_GoogleMaps(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_google_maps, container, false);
        findViews(view);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        return view;
    }

    private void findViews(View view) {
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


    }

    public void setMapLocation(MyLoaction location) {
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(location.getLat(), location.getLng());
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}