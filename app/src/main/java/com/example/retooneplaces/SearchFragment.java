package com.example.retooneplaces;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.slider.Slider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class SearchFragment extends Fragment {

    private static final String LIST_KEY="list_key";

    private RecyclerView placesNewList;
    private LinearLayoutManager linear;
    private PlacesAdapter adapter;
    private SharedPreferences preferences;
    private EditText search;



    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =inflater.inflate(R.layout.fragment_search, container, false);
        placesNewList = root.findViewById(R.id.placesNewList);
        linear = new LinearLayoutManager(getActivity());
        placesNewList.setLayoutManager(linear);
        adapter = new PlacesAdapter();
        placesNewList.setAdapter(adapter);
        search = root.findViewById(R.id.search);



        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void afterTextChanged(Editable s) {
                if(!search.getText().toString().equals("")){
                    ArrayList<Place> placesArray = adapter.getPlaces();
                    List<Place> lista = placesArray.stream().filter(i -> i.getName().toLowerCase().contains(
                            search.getText().toString())).collect(Collectors.toList());
                    adapter.setPlaces((ArrayList) lista);}
                else{
                    adapter.setPlaces(leerPlaces());
                }


            }
        });

        ArrayList<Place> lista=leerPlaces();

        if(lista!=null){
        adapter.setPlaces(leerPlaces());}
        else{

            adapter.setPlaces(new ArrayList<Place>());

        }

        return root;
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