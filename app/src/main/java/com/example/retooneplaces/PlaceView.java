package com.example.retooneplaces;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class PlaceView extends RecyclerView.ViewHolder  implements View.OnClickListener {

    private ConstraintLayout root;
    private TextView nombre;
    private TextView address;
    private TextView puntaje;
    private ImageView imagen;
    private ImageButton viewButton;
    private MapaFragment mapaFragment;
    private TextView distance;
    private FragmentManager fragmentManager;

    public PlaceView(ConstraintLayout root) {
        super(root);
        this.root= root;
        this.nombre=root.findViewById(R.id.nombre);
        this.address=root.findViewById(R.id.direccion);
        this.puntaje =root.findViewById(R.id.puntaje);
        this.imagen= root.findViewById(R.id.foto);
        this.distance = root.findViewById(R.id.distance);
        viewButton = root.findViewById(R.id.viewButton);
        viewButton.setOnClickListener(this);


    }

    public ConstraintLayout getRoot() {
        return root;
    }

    public void setRoot(ConstraintLayout root) {
        this.root = root;
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getAddress() {
        return address;
    }

    public void setAddress(TextView address) {
        this.address = address;
    }

    public TextView getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(TextView puntaje) {
        this.puntaje = puntaje;
    }

    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(ImageView imagen) {
        this.imagen = imagen;
    }

    public TextView getDistance() {
        return distance;
    }

    public void setDistance(TextView distance) {
        this.distance = distance;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.viewButton:
                    ShowFragments();
                break;

        }

    }
    public void ShowFragments(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, mapaFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    public void setMapaFragment(MapaFragment mapaFragment) {
        this.mapaFragment = mapaFragment;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}
