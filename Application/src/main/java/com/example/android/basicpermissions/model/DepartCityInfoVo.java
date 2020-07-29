package com.example.android.basicpermissions.model;

import java.io.Serializable;

public class DepartCityInfoVo implements Serializable {

    private int ProvinceId;
    private int ParentId;
    private String ProvinceName;
    private String ProvinceNameEn;
    private String CityId;
    private String CityName;
    private String CityNameEn;

    public DepartCityInfoVo() {
    }

    public int getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(int provinceId) {
        ProvinceId = provinceId;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int parentId) {
        ParentId = parentId;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String provinceName) {
        ProvinceName = provinceName;
    }

    public String getProvinceNameEn() {
        return ProvinceNameEn;
    }

    public void setProvinceNameEn(String provinceNameEn) {
        ProvinceNameEn = provinceNameEn;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getCityNameEn() {
        return CityNameEn;
    }

    public void setCityNameEn(String cityNameEn) {
        CityNameEn = cityNameEn;
    }
}
