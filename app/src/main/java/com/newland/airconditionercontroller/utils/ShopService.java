package com.newland.airconditionercontroller.utils;

import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ShopService {
    @GET("devices/{deviceId}/Datas/Grouping")
    Call<BaseResponseEntity> getSensorDataGrouping(
            @Path("deviceId") String deviceId,
            @Query("ApiTags") String ApiTags,
            @Query("GroupBy") String GroupBy,
            @Query("Func") String Func,
            @Query("StartDate") String StartDate,
            @Query("EndDate") String EndDate,
            @Header("AccessToken") String AccessToken);
    @GET("Devices/{deviceId}")
    Call<BaseResponseEntity> getSensorData(
            @Path("deviceId") String deviceId,
            @Header("AccessToken") String AccessToken);
}
