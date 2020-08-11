package com.example.android.basicpermissions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.android.basicpermissions.model.DepartCityInfoVo;
import com.example.android.basicpermissions.model.adapter.PinnedHeaderListViewAdapter;
import com.example.android.basicpermissions.view.AlphabetListView;
import com.example.android.basicpermissions.view.PinnedHeaderListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class CityListActivity extends AppCompatActivity implements Serializable {
    private PinnedHeaderListView lv_depart_city_list;
    private AlphabetListView depart_city_alphabetlistview;
    private HttpPost httpRequest;
    private PinnedHeaderListViewAdapter<DepartCityInfoVo> departCitysAdapter;
    private View headView;
    private TextView svSearchCity;
    private ListView citySearchResult;
    private TreeMap<String, List<DepartCityInfoVo>> departCityMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_list);

        final LayoutInflater inflater = LayoutInflater.from(this);
        headView = inflater.inflate(R.layout.current_location, null, false);

        lv_depart_city_list = findViewById(R.id.lv_depart_city_list);
        depart_city_alphabetlistview = findViewById(R.id.depart_city_alphabetlistview);

        svSearchCity = findViewById(R.id.sv_search_city);
        svSearchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CityListActivity.this, SearchCityActivity.class);
                startActivity(intent);
            }
        });

//        if(departCityMaps != null){
//            handleView(departCityMaps);
//        }

        // 获取意图对象
        Intent intent = getIntent();
        //获取传递的值
        String cityData = intent.getStringExtra("cityList");
        if(cityData != null){
            handleData(cityData);
        }
    }

    private void handleData(String cityData) {
        List<DepartCityInfoVo> departCityList = new ArrayList<DepartCityInfoVo>();
        Gson gson = new Gson();
        departCityList = gson.fromJson(cityData, new TypeToken<List<DepartCityInfoVo>>() {}.getType());
        Log.d("wanglong", departCityList.get(0).getCityId());



        //处理数据
        TreeMap<String, List<DepartCityInfoVo>> departCityMaps = new TreeMap<String, List<DepartCityInfoVo>>();
        for (int i = 0; i < departCityList.size(); i++) {
            String firstLetter;
            String cityEn = departCityList.get(i).getCityNameEn();
            if (!cityEn.isEmpty()) {
                firstLetter = cityEn.substring(0, 1).toUpperCase();
                if (isLetter(firstLetter)) {

                    if (departCityMaps.containsKey(firstLetter)) {
                        departCityMaps.get(firstLetter).add(departCityList.get(i));
                    } else {
                        List<DepartCityInfoVo> cityList = new ArrayList<DepartCityInfoVo>();
                        cityList.add(departCityList.get(i));
                        departCityMaps.put(firstLetter, cityList);
                    }
                }
            }
            Log.d("wanglong", "Just停顿一下");
        }

        handleView(departCityMaps);
    }

    private void handleView(TreeMap<String, List<DepartCityInfoVo>> departCityMaps) {

          lv_depart_city_list.addHeaderView(headView);


        departCitysAdapter = new PinnedHeaderListViewAdapter<DepartCityInfoVo>(CityListActivity.this, departCityMaps, lv_depart_city_list, depart_city_alphabetlistview);
        lv_depart_city_list.setOnScrollListener(departCitysAdapter);
        lv_depart_city_list.setAdapter(departCitysAdapter);
        lv_depart_city_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                DepartCityInfoVo cityInfo = (DepartCityInfoVo) lv_depart_city_list.getItemAtPosition(position);
                intent.putExtra("newCityName",cityInfo.getCityName());
                setResult(100,intent);
                finish();
            }
        });
    }


    private boolean isLetter(String letter) {
        boolean result = false;
        String allLetter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (allLetter.indexOf(letter) > -1) {
            result = true;
        }
        return result;
    }

}