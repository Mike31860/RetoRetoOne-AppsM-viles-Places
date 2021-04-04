package com.example.retooneplaces;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class NewPlaceFragment extends Fragment implements View.OnClickListener, MapaFragment.onLocationDataListener{
    public  static final  int PERMISSION_CALLBACK =11;
    public  static final  int GALLERY_CALLBACK =13;
    public  static final  int CAMERA_CALLBACK =12;
    private static final String LIST_KEY="list_key";

    private EditText nameText;
    private Button imageAdd;
    private Button registerButton;
    private File file;
    private Button locationId;
    private TextView direccionRealId;
    private ImageView imageView;
    private SharedPreferences preferences;
    private ArrayList<Place> places;
    private Gson gson;

    //States
    private String Direccion ="";
    private String nameImage = "";
    private double longitud=0.0;
    private double latitud=0.0;
    private Bitmap imagenBitmap;

    public NewPlaceFragment() {
        // Required empty public constructor
    }


    public static NewPlaceFragment newInstance() {
        NewPlaceFragment fragment = new NewPlaceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =inflater.inflate(R.layout.fragment_new_place, container, false);
        nameText = root.findViewById(R.id.nameText);
        imageAdd = root.findViewById(R.id.imageAdd);
        imageView = root.findViewById(R.id.imageView);
        registerButton = root.findViewById(R.id.registerButton);
        locationId=root.findViewById(R.id.locationId);
        direccionRealId= root.findViewById(R.id.direccionRealId);
        registerButton.setOnClickListener(this);
        imageAdd.setOnClickListener(this);
        nameText.setOnClickListener(this);
        locationId.setOnClickListener(this);
        direccionRealId.setText(this.Direccion);
        ArrayList<Place> lista=leerPlaces();
        if(lista!=null){
            places = leerPlaces();
        }else{
            places= new ArrayList<Place>();
        }

       //  places = new ArrayList<Place>();
        gson = new Gson();

     //   imageView.setImageBitmap(this.imagenBitmap);



        return root;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.registerButton:

                String id= UUID.randomUUID().toString();
                String PlaceName= nameText.getText().toString();

                String address= Direccion;
                double punta = 0.0;

                saveInformationPlace(new Place(id, address, PlaceName, punta, imagenBitmap, latitud, longitud));

                break;
            case R.id.imageAdd:

                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
                builder.setTitle("¿Qué deseas hacer?")
                        .setMessage("Puedes subir una foto de tu galeria o tomar una foto desde tu cámara")
                        .setNegativeButton("Abrir Camara", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                Long consecutivo=System.currentTimeMillis()/1000;
                                nameImage = consecutivo+".jpg";
                                String namePhoto=consecutivo.toString()+nameImage;
                                file = new File(getActivity().getExternalFilesDir(null)+"/"+namePhoto);
                                Log.e(">>>>",""+file);
                                Uri uri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName(), file);
                                i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                startActivityForResult(i, CAMERA_CALLBACK);
                            }
                        })
                        .setPositiveButton("Abrir Galeria", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent j = new Intent(Intent.ACTION_GET_CONTENT);
                                 j.setType("image/*");
                                startActivityForResult(j, GALLERY_CALLBACK);
                            }
                        });

                builder.create().show();

                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA_CALLBACK && resultCode == getActivity().RESULT_OK){
            Bitmap imagene= BitmapFactory.decodeFile(file.getPath());
            Bitmap thumbnail = Bitmap.createScaledBitmap(
                    imagene, imagene.getWidth()/4, imagene.getHeight()/4, true
            );
            Matrix matrix = new Matrix();
           // matrix.postRotate(90);
            Bitmap rotatedImage =  Bitmap.createBitmap(thumbnail, 0 , 0, thumbnail.getWidth(), thumbnail.getHeight(), matrix, true);
            this.imagenBitmap = rotatedImage;
            imageView.setImageBitmap(rotatedImage);
          //  imagenBitmap =rotatedImage;
        }
        else if(requestCode == GALLERY_CALLBACK && resultCode ==  getActivity().RESULT_OK){
                Uri uri = data.getData();
            String path = UtilRetoOne.getPath(getActivity(), uri);
            Bitmap imagene= BitmapFactory.decodeFile(path);
            imageView.setImageBitmap(imagene);
            this.imagenBitmap=imagene;
        }
    }


    @Override
    public void OnLocationData(List<Address> algo) {
        this.latitud=algo.get(0).getLatitude();
        this.longitud=algo.get(0).getLongitude();
        this.Direccion=algo.get(0).getAddressLine(0);
    }


    public void saveInformationPlace( Place place){

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        places.add(place);
        String jsonString = gson.toJson(places);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LIST_KEY,jsonString);
        editor.apply();


    }

    public ArrayList<Place> leerPlaces(){

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String jsonString= pref.getString(LIST_KEY, "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Place>>(){}.getType();
        ArrayList<Place> lista= gson.fromJson(jsonString, type);

        return lista;
    }



}