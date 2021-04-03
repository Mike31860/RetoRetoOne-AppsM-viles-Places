package com.example.retooneplaces;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class PlaceView extends RecyclerView.ViewHolder{

    private ConstraintLayout root;
    private TextView nombre;
    private TextView address;
    private TextView puntaje;
    private ImageView imagen;

    public PlaceView(ConstraintLayout root) {
        super(root);
        this.root= root;
        this.nombre=root.findViewById(R.id.nombre);
        this.address=root.findViewById(R.id.direccion);
        this.puntaje =root.findViewById(R.id.puntaje);
        this.imagen= root.findViewById(R.id.foto);

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
}
