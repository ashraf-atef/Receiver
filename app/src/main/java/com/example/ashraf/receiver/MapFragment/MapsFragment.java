package com.example.ashraf.receiver.MapFragment;


import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ashraf.receiver.Pubub.MyPubnub;
import com.example.ashraf.receiver.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    public void setMyPubnub(MyPubnub myPubnub) {
        this.myPubnub = myPubnub;
    }


    public MyPubnub myPubnub;
    public GoogleMap mMap;
    public MapView mapView;
    Location myLocation;
    float currentZoom = 16;
    SupportMapFragment mapFragment;
    public Marker senderMarker ;
    public Marker companyMarker ;
    View rootView ;
    Polyline line ;
    public MapsFragmentPresenter mapsFragmentPresenter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        mapsFragmentPresenter=new MapsFragmentPresenterImpl(this);
        mapsFragmentPresenter.initView();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapsFragmentPresenter.initMap(savedInstanceState);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapsFragmentPresenter.onMapReady(googleMap);
        mapsFragmentPresenter.initComponants();
    }

}
