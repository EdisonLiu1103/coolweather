package com.example.edison.coolweather.util;

import android.text.TextUtils;

import com.example.edison.coolweather.db.City;
import com.example.edison.coolweather.db.County;
import com.example.edison.coolweather.db.Province;
import com.example.edison.coolweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by edison on 2018/5/3.
 */

public class Utility {

    /**
     * 解析和处理服务器返回的省级数据
     */
    public static boolean handleProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allProvinces = new JSONArray(response);
                for(int i = 0; i < allProvinces.length(); i++){
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();

                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * 解析和处理服务器返回的市级数据
     */

    public static boolean handleCityResponse(String response, int provinceId){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allCities = new JSONArray(response);
                for(int i = 0; i < allCities.length(); i++){
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityCode(cityObject.getInt("id"));
                    city.setCityName(cityObject.getString("name"));
                    city.setProvinceId(provinceId);
                    city.save();
                        }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return  false;
    }

    /**
     * 解析和处理服务器返回的县级数据
     */


    public static boolean handleCountryResponse(String response, int cityId){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allCountise = new JSONArray(response);
                for(int i = 0; i < allCountise.length(); i++){
                    JSONObject countryObject = allCountise.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countryObject.getString("name"));
                    county.setWeatherId(countryObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 将返回的JSON数据解析或Weather实体类
     */

    public static Weather handleWeatherResponse(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

}