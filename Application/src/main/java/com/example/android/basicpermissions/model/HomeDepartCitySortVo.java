package com.example.android.basicpermissions.model;

import org.json.JSONObject;

/**
 * Created by libin on 2016/12/1.
 * 出发城市实体类
 */

public class HomeDepartCitySortVo extends BaseVo {
    public HomeDepartCitySortVo(JSONObject jsonObject){
        super(jsonObject);
    }
    public HomeDepartCitySortVo(){
    }
    private String cityName;//城市名称
    private String sortLetters;//城市名称首字母
    private int cityId;
    private String url;
    private String phone;
    private int orderNum;
    private int isHot;
    private String fullCityName;
    private String ecityNameEvery;
    private int subStationId;

    public int getSubStationId() {
        return subStationId;
    }

    public void setSubStationId(int subStationId) {
        this.subStationId = subStationId;
    }

    public String getEcityNameEvery() {
        return ecityNameEvery;
    }

    public void setEcityNameEvery(String ecityNameEvery) {
        this.ecityNameEvery = ecityNameEvery;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullCityName() {
        return fullCityName;
    }

    public void setFullCityName(String fullCityName) {
        this.fullCityName = fullCityName;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    @Override
    protected void init(JSONObject jsonOfVo) {
        if(jsonOfVo!=null){
            setCityId(jsonOfVo.optInt("cityID"));
            setFullCityName(jsonOfVo.optString("fullCityName"));
            setSortLetters(jsonOfVo.optString("ecityName"));
            setOrderNum(jsonOfVo.optInt("orderNum"));
            setCityName(jsonOfVo.optString("cityName"));
            setPhone(jsonOfVo.optString("phone"));
            setUrl(jsonOfVo.optString("url"));
            setEcityNameEvery(jsonOfVo.optString("ecityNameEvery"));
            setSubStationId(jsonOfVo.optInt("subStationId"));
        }
    }
}
