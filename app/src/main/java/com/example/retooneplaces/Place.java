package com.example.retooneplaces;

import android.graphics.Bitmap;

public class Place {

    private String id;
    private String address;
    private String name;
    private double puntaje;
    private Bitmap imagen;
    private double latitud;
    private double longitud;

    public Place(String id, String address, String name, double puntaje, Bitmap imagen, double latitud, double longitud ) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.puntaje = puntaje;
        this.imagen = imagen;
        this.latitud = latitud;
        this.longitud=longitud;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
