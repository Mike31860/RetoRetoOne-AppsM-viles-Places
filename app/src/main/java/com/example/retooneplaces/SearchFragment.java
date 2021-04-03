package com.example.retooneplaces;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

    private static final String LIST_KEY="list_key";

    private RecyclerView placesNewList;
    private LinearLayoutManager linear;
    private PlacesAdapter adapter;
    private SharedPreferences preferences;




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
        ArrayList<Place> lista=leerPlaces();

        if(lista!=null){
        adapter.setPlaces(leerPlaces());} else{

            adapter.setPlaces(new ArrayList<Place>());

        }
    //    observer.onListDataListener(adapter.getPlaces());

      //  leerPlaces();

     //   placesNewList.set
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



//    public void setObserver(SearchFragment.onListDataListener obseerver) {
//        this.observer = obseerver;
//    }

    public interface onListDataListener{
        void onListDataListener(ArrayList<Place> place);

    }
}