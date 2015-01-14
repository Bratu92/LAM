package com.example.maicol.budgettracking;

/**
 * Created by Maicol on 07/01/2015.
 */
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Map extends Activity {

    Cursor cursor;
    DatabaseHelper dbhelper;
    ArrayList<MapCoordinate> mapcoordinate;
    MapCoordinate mapcoord;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();



        dbhelper = new DatabaseHelper(this);
        cursor = dbhelper.getAllPayment();
        mapcoordinate = new ArrayList<>();

       do{

           String category = cursor.getString(4);
           double lat = cursor.getDouble(8);
           double longt = cursor.getDouble(9);

           System.out.println(lat+" "+longt);

           if(lat != 0.0 && longt != 0.0) {

               mapcoordinate.add(new MapCoordinate(category, lat, longt));
               onMapReady(map);

           }

        }while(cursor.moveToNext());

    }

    public void onMapReady(GoogleMap map){

        for(int i = 0; i < mapcoordinate.size(); i++) {

            mapcoord = mapcoordinate.get(i);

            map.addMarker(new MarkerOptions()
                    .position(new LatLng(mapcoord.getLat(), mapcoord.getLong()))
                    .title(mapcoord.getCategory()));
        }

    }

}