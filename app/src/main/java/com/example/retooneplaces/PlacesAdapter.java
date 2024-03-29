package com.example.retooneplaces;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlacesAdapter extends RecyclerView.Adapter<PlaceView> {

    private ArrayList<Place> places;
    public static final String NameOfFolder = "/imagenes";
    private MapaFragment mapaFragment;
    private  FragmentManager fragmentManager;


    public PlacesAdapter() {
         places = new ArrayList<Place>();
      //  places.add(new Place(UUID.randomUUID().toString(), "Calle 48 numero", "Almacen la 14", 4.1 ));

    }

    public void addPlace(Place place){
        places.add(place);
    }

    @NonNull
    @Override
    public PlaceView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.placerow, null);
        ConstraintLayout rowroot = (ConstraintLayout) row;
        PlaceView placeview= new PlaceView(rowroot);
        return placeview;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceView holder, int position) {
             holder.setMapaFragment(mapaFragment);
             holder.setFragmentManager(fragmentManager);

            holder.getNombre().setText(places.get(position).getName());
            holder.getAddress().setText(places.get(position).getAddress());
            holder.getPuntaje().setText(places.get(position).getPuntaje()+"");
            holder.getDistance().setText(places.get(position).getDistanciaPoint()+"");
            String photoPath = Environment.getExternalStorageDirectory()+NameOfFolder+"/"+places.get(position).getImagen();
            Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
            holder.getImagen().setImageBitmap(bitmap);
            holder.getPuntaje().setText(places.get(position).getPuntaje()+"");
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public void setMapaFragment(MapaFragment mapaFragment) {
        this.mapaFragment = mapaFragment;
    }



    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}
