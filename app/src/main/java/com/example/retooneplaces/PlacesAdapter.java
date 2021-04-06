package com.example.retooneplaces;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.UUID;

public class PlacesAdapter extends RecyclerView.Adapter<PlaceView> {

    private ArrayList<Place> places;
    private String NameOfFolder = "/imagenes";


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
            holder.getNombre().setText(places.get(position).getName());
            holder.getAddress().setText(places.get(position).getAddress());
            holder.getPuntaje().setText(places.get(position).getPuntaje()+"");
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
}
