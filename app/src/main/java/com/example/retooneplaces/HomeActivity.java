package com.example.retooneplaces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private MapaFragment mapaFragment;
    private  NewPlaceFragment newPlaceFragment;
    private SearchFragment searchFragment;
    private BottomNavigationView navigator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigator= findViewById(R.id.navigator);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        }, 11);



    }
    public void ShowFragments(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==11){

            mapaFragment = MapaFragment.newInstance();
            newPlaceFragment = NewPlaceFragment.newInstance();
            searchFragment = SearchFragment.newInstance();

            ShowFragments(newPlaceFragment);
            mapaFragment.setObserver(newPlaceFragment);
          //  searchFragment.setObserver(newPlaceFragment);


            navigator.setOnNavigationItemSelectedListener(
                    (menuItem)->{
                        switch (menuItem.getItemId()){

                            case R.id.newPlace:
                                ShowFragments(newPlaceFragment);
                                break;

                            case R.id.mapPlace:
                                ShowFragments(mapaFragment);
                                break;

                            case R.id.searchPlace:
                                ShowFragments(searchFragment);
                                break;



                        }
                        return true;
                    }

            );

        }
    }
}