package com.example.android.basicpermissions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

public class SearchCityActivity extends AppCompatActivity {

    private SearchView svSearchCity;
    private ListView citySearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);


        svSearchCity = findViewById(R.id.sv_search_city);
        citySearchResult = findViewById(R.id.lv_city_search_result);


        svSearchCity.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                citySearchResult.setVisibility(View.VISIBLE);
                return false;
            }


        });
    }
}