package com.example.retooneplaces;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MapaFragment extends Fragment implements OnMapReadyCallback {

    private static final double MIN_METERS = 100;
    private static final String LIST_KEY="list_key";
    private GoogleMap mMap;
    private onLocationDataListener observer;
    private LocationManager ubication;
    private Marker myMarker;
    private Geocoder geocoder;
    private ArrayList<Place> places = new ArrayList<Place>();
    private bottomSheetPlace bottomSheetPlace;


    public MapaFragment() {
        // Required empty public constructor
    }


    public static MapaFragment newInstance() {
        MapaFragment fragment = new MapaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(getActivity());
        ubication =(LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        return view;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        leerPlaces();
        if(places==null){
            places = new ArrayList<Place>();
        }
        mMap.setMyLocationEnabled(true);
        myMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(0,0)));
        getLocation();
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + " " + latLng.longitude);

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        latLng, 16
                ));
                mMap.addMarker(markerOptions);
                try {
                    List<Address> address= geocoder.getFromLocation(latLng.latitude,  latLng.longitude, 1);
                    observer.OnLocationData( address );
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    @SuppressLint("MissingPermission")
    public void getLocation(){
        ubication.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 2, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                LatLng position= new LatLng(location.getLatitude(),location.getLongitude());
                myMarker.setPosition(position);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,16));

                updateDistance();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        });
//        ubication.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 2, new LocationListener() {
//
//            @Override
//            public void onLocationChanged(@NonNull Location location) {
//
//            }
//        });

    }


    private void updateDistance(){

        float[] distanceResult = new float[1];
        for (Place place: places) {
            Location.distanceBetween(myMarker.getPosition().latitude, myMarker.getPosition().longitude,
                    place.getLatitud(), place.getLongitud(), distanceResult);
            double distance = distanceResult[0];
            place.setDistanciaPoint((int) distance);
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
            Gson gson = new Gson();
            String jsonString = gson.toJson(places);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(LIST_KEY,jsonString);
            editor.apply();
            if(distance<=MIN_METERS){
                bottomSheetPlace(place);
            }

        }
    }

    public void setObserver(onLocationDataListener obseerver) {
        this.observer = obseerver;
    }

    public interface onLocationDataListener {
        void OnLocationData(List<Address> algo);
    }



    public void leerPlaces(){

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String jsonString= pref.getString(LIST_KEY, "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Place>>(){}.getType();
        places= gson.fromJson(jsonString, type);
        if(places!=null) {
            for (int i = 0; i < places.size(); i++) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(places.get(i).getLatitud(), places.get(i).getLongitud())));
            }
        }

    }

    public void update( String id, double puntaje){


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean equals = false;
        int i = 0;
        for (; !equals ; i++) {
            if(places.get(i).getId().equals(id)){
                places.get(i).setPuntaje(puntaje);
                equals= true;
            }
        }

        Gson gson = new Gson();
        String jsonString = gson.toJson(places);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LIST_KEY,jsonString);
        editor.apply();

    }



    public void bottomSheetPlace(Place place){

        bottomSheetPlace = new bottomSheetPlace(getActivity(),R.style.BottomSheetTheme);

        View sheetView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.bottom_sheet_layout,
                getActivity().findViewById(R.id.bottom_sheet));

        bottomSheetPlace.initialize(sheetView);
        bottomSheetPlace.getBottomPlace().setText(place.getName());
        bottomSheetPlace.getBottomAddress().setText(place.getAddress());

        String photoPath = Environment.getExternalStorageDirectory()+PlacesAdapter.NameOfFolder+"/"+place.getImagen();
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
        bottomSheetPlace.getBottomImage().setImageBitmap(bitmap);
        
        bottomSheetPlace.getBottomRatingBar().setRating((float) place.getPuntaje());


        sheetView.findViewById(R.id.bottomButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RatingBar rb = sheetView.findViewById(R.id.bottomRatingBar);
                update(place.getId(),rb.getRating());
                bottomSheetPlace.dismiss();
            }
        });

        bottomSheetPlace.setContentView(sheetView);
        bottomSheetPlace.show();
    }


}