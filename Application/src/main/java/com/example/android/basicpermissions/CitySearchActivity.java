package com.example.android.basicpermissions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

public class CitySearchActivity extends AppCompatActivity {
    private PinnedHeaderListView lv_depart_city_list;
    private AlphabetListView depart_city_alphabetlistview;
    private HttpPost httpRequest;
    private PinnedHeaderListViewAdapter<DepartCityInfoVo> departCitysAdapter;
    private View headView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_search_list);

        final LayoutInflater inflater = LayoutInflater.from(this);
        headView = inflater.inflate(R.layout.current_location, null, false);

        lv_depart_city_list = findViewById(R.id.lv_depart_city_list);
        depart_city_alphabetlistview = findViewById(R.id.depart_city_alphabetlistview);


        getData();

//        departCitysAdapter = new PinnedHeaderListViewAdapter<DepartCityInfoVo>(this, departCityMaps, lvDepartCityList, alphabetListView);
    }


    private void getData() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
//        String url ="http://hotelmicro.aoyou.com/app/Hotel/GetHotelCityList";
        String url = "http://mservicetest.aoyou.com/api40/Hotel/GetHotelCityList";

        httpRequest = new HttpPost();
        httpRequest.addHeader("User-Agent", "android500519/ECE595BFD194AD6922B291694D8A1B31/ffffffff-a642-d802-ffff-fffff3f3e46b/0/1507bfd3f7f5b36231b");
        httpRequest.addHeader("Accept-Encoding", "gzip");
        JSONObject jsonParam = new JSONObject();

        // Request a string response from the provided URL.
        VolleyHttpRequest jsonObj = new VolleyHttpRequest(httpRequest.getAllHeaders(), url, jsonParam,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
//                        Log.d("wanglong", response.toString());
                        try {
                            if (response.getInt("ReturnCode") == 0) {
                                Log.d("wanglong", "0开始数据转换...");
                                List<DepartCityInfoVo> departCityList = new ArrayList<DepartCityInfoVo>();

                                if(null!=response.getString("Data")){

                                    JSONArray tmp = response.getJSONArray("Data");
                                    Log.d("wanglong", "用jsonarray进行获取");
                                    String jsonObj = response.getString("Data");
                                    Gson gson = new Gson();
                                    departCityList = gson.fromJson(jsonObj, new TypeToken<List<DepartCityInfoVo>>() {}.getType());
                                    Log.d("wanglong", departCityList.get(0).getCityId());
                                    handleView(departCityList);

                                }

                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
            }
        }
        );

        // Add the request to the RequestQueue.
        queue.add(jsonObj);
    }

    private void handleView(List<DepartCityInfoVo> departCityList) {

        //处理数据
        TreeMap<String, List<DepartCityInfoVo>> departCityMaps = new TreeMap<String, List<DepartCityInfoVo>>();
        for (int i = 0; i < departCityList.size(); i++){
            String firstLetter;
            String cityEn = departCityList.get(i).getCityNameEn();
            if(!cityEn.isEmpty()){
                firstLetter = cityEn.substring(0,1).toUpperCase();
                if(isLetter(firstLetter)){

                    if(departCityMaps.containsKey(firstLetter)){
                        departCityMaps.get(firstLetter).add(departCityList.get(i));
                    } else {
                        List<DepartCityInfoVo> cityList = new ArrayList<DepartCityInfoVo>();
                        cityList.add(departCityList.get(i));
                        departCityMaps.put(firstLetter,cityList);
                    }
                }
            }
            Log.d("wanglong","Just停顿一下");
        }



        lv_depart_city_list.addHeaderView(headView);


        departCitysAdapter = new PinnedHeaderListViewAdapter<DepartCityInfoVo>(CitySearchActivity.this, departCityMaps, lv_depart_city_list, depart_city_alphabetlistview);
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