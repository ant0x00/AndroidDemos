package com.example.android.basicpermissions.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by libin on 2016/12/9.
 */

public class HomeDepartCityAllListVo extends BaseVo {
    public HomeDepartCityAllListVo(JSONObject jsonObject){
        super(jsonObject);
    }
    private List<HomeDepartCitySortVo> departCityList;

    public List<HomeDepartCitySortVo> getDepartCityList() {
        return departCityList;
    }

    public void setDepartCityList(List<HomeDepartCitySortVo> departCityList) {
        this.departCityList = departCityList;
    }

    @Override
    protected void init(JSONObject jsonOfVo) {
        if(jsonOfVo!=null){
            //加载全部城市
            List<HomeDepartCitySortVo> deparCityList=new ArrayList<HomeDepartCitySortVo>();
            JSONArray departCityArray=jsonOfVo.optJSONArray("city");
            if(departCityArray!=null){

                for(int i=0;i<departCityArray.length();i++){
                    try{
                        HomeDepartCitySortVo departCityItem=new HomeDepartCitySortVo(departCityArray.getJSONObject(i));
                        departCityItem.setIsHot(0);
                        deparCityList.add(departCityItem);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
            //加载热门城市
            JSONArray hotCityArray=jsonOfVo.optJSONArray("hotcity");
            if(hotCityArray!=null){
                for(int i=0;i<hotCityArray.length();i++){
                    try{
                        HomeDepartCitySortVo hotCityItem=new HomeDepartCitySortVo(hotCityArray.getJSONObject(i));
                        hotCityItem.setIsHot(1);
                        deparCityList.add(hotCityItem);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
            setDepartCityList(deparCityList);
        }
    }
}
