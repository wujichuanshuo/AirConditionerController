package com.newland.airconditionercontroller.utils;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.google.gson.Gson;
import com.newland.airconditionercontroller.activity.Main2Activity;
import com.newland.airconditionercontroller.activity.MainActivity;
import com.newland.airconditionercontroller.activity.UpperLowerSetActivity;
import com.newland.airconditionercontroller.activity.msActivity;
import com.newland.airconditionercontroller.bean.DeviceInfo;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.com.newland.nle_sdk.responseEntity.Device;
import cn.com.newland.nle_sdk.responseEntity.DeviceState;
import cn.com.newland.nle_sdk.responseEntity.User;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public class mmNetWorkBusiness {
    int Status=1;
    final Gson gson = new Gson();
    List<Entry> entriess=new ArrayList<>();
    int []data=new int[60];
    void mmNetWorkBusiness(){
        int Status=1;
    }
    void getStatus(int status){
        Status=status;
    }
    public void getDeviceInfo(String mDeviceId, final MainActivity mainActivity) {
        final String TAG = "MainActivity";
        NetWorkBusiness eNetWorkBusiness = new NetWorkBusiness(DataCache.getAccessToken(mainActivity.getApplicationContext()), DataCache.getBaseUrl(mainActivity.getApplicationContext()));
        eNetWorkBusiness.getDeviceInfo(mDeviceId, new Callback<BaseResponseEntity<Device>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponseEntity<Device>> call, @NonNull Response<BaseResponseEntity<Device>> response) {
                BaseResponseEntity<Device> baseResponseEntity = response.body();
                if (baseResponseEntity != null) {
                    LogUtil.d(TAG, "getDeviceInfo, baseResponseEntity: " + gson.toJson(baseResponseEntity));
                    int status = 1;
                    try {
                        JSONObject jsonObject = new JSONObject(gson.toJson(baseResponseEntity));
                        status = (int) jsonObject.get("Status");
                        //调用处理方法
                        //mmNetWorkBusiness.this.getStatus(status);

                        GetDeviceInfo getDeviceInfo = new GetDeviceInfo();
                        getDeviceInfo.displayExistState(status,mainActivity);

                        LogUtil.d(TAG, "getDeviceInfo Status: " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    LogUtil.d(TAG, "getDeviceInfo error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponseEntity<Device>> call, @NonNull Throwable t) {
                LogUtil.d(TAG, "getDeviceInfo status error: \n" + t.getMessage());
            }
        });
    }
    public void getBatchOnLine(String mDeviceId, final MainActivity mainActivity){
        final String TAG="getBatchOnLine";
        NetWorkBusiness eNetWorkBusiness = new NetWorkBusiness(DataCache.getAccessToken(mainActivity.getApplicationContext()), DataCache.getBaseUrl(mainActivity.getApplicationContext()));
        eNetWorkBusiness.getBatchOnLine(mDeviceId, new Callback<BaseResponseEntity<List<DeviceState>>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponseEntity<List<DeviceState>>> call, @NonNull Response<BaseResponseEntity<List<DeviceState>>> response) {
                BaseResponseEntity<List<DeviceState>> baseResponseEntity = response.body();
                if (baseResponseEntity != null) {
                    LogUtil.d(TAG, "get OnLine status success, gson.toJson(baseResponseEntity):" + gson.toJson(baseResponseEntity));
                    boolean value = false;
                    try {
                        //数据转换

                        //1.获取类
                        JSONObject jsonObject = new JSONObject(gson.toJson(baseResponseEntity));
                        //2.获取ResultObject数组
                        JSONArray resultObj = (JSONArray) jsonObject.get("ResultObj");
                        //3.获取数组中的IsOnline属性的值
                        value = resultObj.getJSONObject(0).getBoolean("IsOnline");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    LogUtil.d(TAG, "get OnLine value:" + String.valueOf(value));

                    GetBatchOnLine getBatchOnLine=new GetBatchOnLine();
                    getBatchOnLine.displayOnlineState(value,mainActivity);

                } else {
                    LogUtil.d(TAG, "get OnLine status fail");
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponseEntity<List<DeviceState>>> call, @NonNull Throwable t) {
                LogUtil.d(TAG, "get OnLine status error: \n" + t.getMessage());
            }
        });
    }
    public void getSensor(String mDeviceId, final String API, final MainActivity mainActivity){
        final String TAG="getSensor";
        NetWorkBusiness eNetWorkBusiness = new NetWorkBusiness(DataCache.getAccessToken(mainActivity.getApplicationContext()), DataCache.getBaseUrl(mainActivity.getApplicationContext()));
        eNetWorkBusiness.getSensor(mDeviceId, API, new retrofit2.Callback<BaseResponseEntity>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<BaseResponseEntity> call, @NonNull retrofit2.Response<BaseResponseEntity> response) {
                BaseResponseEntity baseResponseEntity = response.body();
                LogUtil.d(TAG, "get UpperLimit, gson.toJson(baseResponseEntity):" + gson.toJson(baseResponseEntity));
                try {
                    //层层解析
                    JSONObject jsonObject = new JSONObject(gson.toJson(baseResponseEntity));

                    JSONObject resultObj = (JSONObject) jsonObject.get("ResultObj");

                    double value = (double) resultObj.get("Value");

                    LogUtil.d(TAG, "get UpperLimit value:" + value);

                    //java.text.DecimalFormat("0").format(value)格式转换，Integer.parseInt每次构造一个int常量
                    GetSensor getSensor = new GetSensor();
                    switch (API) {
                        case "number_up":
                            getSensor.displaypdrs((int)value,mainActivity);
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<BaseResponseEntity> call, @NonNull Throwable t) {
                LogUtil.d(TAG, "get UpperLimit error: \n" + t.getMessage());
            }
        });
    }

    public void control(String mDeviceId, String API, final int Value, final MainActivity mainActivity){
        NetWorkBusiness eNetWorkBusiness = new NetWorkBusiness(DataCache.getAccessToken(mainActivity.getApplicationContext()), DataCache.getBaseUrl(mainActivity.getApplicationContext()));
        final String TAG="control";
        eNetWorkBusiness.control(mDeviceId, API, Value, new retrofit2.Callback<BaseResponseEntity>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<BaseResponseEntity> call, @NonNull retrofit2.Response<BaseResponseEntity> response) {
                BaseResponseEntity baseResponseEntity = response.body();
                LogUtil.d(TAG, "control PowerCtrl, gson.toJson(baseResponseEntity):" + gson.toJson(baseResponseEntity));

                if (baseResponseEntity != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(gson.toJson(baseResponseEntity));
                        int status = (int) jsonObject.get("Status");
                        LogUtil.d(TAG, "control PowerCtrl Status:" + status);
                        if (0 == status) {
                            Control control=new Control();
                        } else {
                            LogUtil.d(TAG, "return status value is error, open box fail");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    LogUtil.d(TAG, "请求出错 : 请求参数不合法或者服务出错");
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<BaseResponseEntity> call, @NonNull Throwable t) {
                LogUtil.d(TAG, "请求出错 : \n" + t.getMessage());
            }
        });
    }
    public void control(String mDeviceId, String API, final int Value, String AccessToken,String BaseUrl){
        NetWorkBusiness eNetWorkBusiness = new NetWorkBusiness(AccessToken,BaseUrl);
        final String TAG="control";
        eNetWorkBusiness.control(mDeviceId, API, Value, new retrofit2.Callback<BaseResponseEntity>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<BaseResponseEntity> call, @NonNull retrofit2.Response<BaseResponseEntity> response) {
                BaseResponseEntity baseResponseEntity = response.body();
                LogUtil.d(TAG, "control PowerCtrl, gson.toJson(baseResponseEntity):" + gson.toJson(baseResponseEntity));

                if (baseResponseEntity != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(gson.toJson(baseResponseEntity));
                        int status = (int) jsonObject.get("Status");
                        LogUtil.d(TAG, "control PowerCtrl Status:" + status);
                        if (0 == status) {
                            Control control=new Control();
                        } else {
                            LogUtil.d(TAG, "return status value is error, open box fail");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    LogUtil.d(TAG, "请求出错 : 请求参数不合法或者服务出错");
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<BaseResponseEntity> call, @NonNull Throwable t) {
                LogUtil.d(TAG, "请求出错 : \n" + t.getMessage());
            }
        });
    }

    public void control(String mDeviceId, String API, final int Value, UpperLowerSetActivity upperLowerSetActivity){
        final String TAG="UpperLowerSet";
        NetWorkBusiness eNetWorkBusiness = new NetWorkBusiness(DataCache.getAccessToken(upperLowerSetActivity.getApplicationContext()), DataCache.getBaseUrl(upperLowerSetActivity.getApplicationContext()));
        eNetWorkBusiness.control(mDeviceId, API, Value, new retrofit2.Callback<BaseResponseEntity>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<BaseResponseEntity> call, @NonNull retrofit2.Response<BaseResponseEntity> response) {
                BaseResponseEntity baseResponseEntity = response.body();
                LogUtil.d(TAG, "Control Lower, gson.toJson(baseResponseEntity):" + gson.toJson(baseResponseEntity));

                if (baseResponseEntity != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(gson.toJson(baseResponseEntity));
                        int status = (int) jsonObject.get("Status");
                        LogUtil.d(TAG, "Control Lower, Status:" + status);
                        if (0 == status) {
                            LogUtil.d(TAG, "Control Lower success");
                        } else {
                            LogUtil.d(TAG, "return status value is error, Control Lower fail");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    LogUtil.d(TAG, "请求出错 : 请求参数不合法或者服务出错");
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<BaseResponseEntity> call, @NonNull Throwable t) {
                LogUtil.d(TAG, "请求出错 : \n" + t.getMessage());
            }
        });

    }
    public void iniData(){
        for(int i=0;i<data.length;i++){
            data[i]=0;
        }
    }
    public void setData(int a,int b){
        data[a]=b;
    }
    public int[] getDatas(String mDeviceId, final String ApiTags, String Method, String Timeago, final MainActivity mainActivity){
        final String TAG="GetDatas";
        String PageSize="2999";
        Log.d(TAG,"123");

        NetWorkBusiness eNetWorkBusiness = new NetWorkBusiness(DataCache.getAccessToken(mainActivity.getApplicationContext()), DataCache.getBaseUrl(mainActivity.getApplicationContext()));
        eNetWorkBusiness.getSensorData(mDeviceId, ApiTags,Method,Timeago,PageSize, new retrofit2.Callback<BaseResponseEntity>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<BaseResponseEntity> call, @NonNull retrofit2.Response<BaseResponseEntity> response) {
                BaseResponseEntity baseResponseEntity = response.body();
                LogUtil.d(TAG, "get UpperLimit, gson.toJson(baseResponseEntity):" + gson.toJson(baseResponseEntity));
                    entriess=new ArrayList<>();
                    Gson gson=new Gson();
                    DataGsonFormat data=gson.fromJson(gson.toJson(baseResponseEntity),DataGsonFormat.class);
                    List<DataGsonFormat.ResultObjBean.DataPointsBean> DataPoints =data.getResultObj().getDataPoints();
                    int now=60+Integer.parseInt(DataPoints.get(0).getPointDTO().get(0).getRecordTime().substring(14,16));
                    //int j=0;
                    iniData();
                    for(int i=0;i<DataPoints.get(0).getPointDTO().size();i++) {
                        //DataPoints.get(0).getPointDTO().get(i).getValue();
                        Log.d(TAG+"1","value:"+DataPoints.get(0).getPointDTO().get(i).getValue()+"  data:"+DataPoints.get(0).getPointDTO().get(i).getRecordTime()+"\n");

                        int k=Integer.parseInt(DataPoints.get(0).getPointDTO().get(i).getRecordTime().substring(14,16));
                        Log.d(TAG+"2","datavalue:"+k+"\n");
                        //    entries.add(new Entry(i, new Random().nextInt(300)));
                        //Log.d("mm4",entries.toString())
                        Log.d(TAG+"3","datavalue: "+((now-k+60)%60)+" value: "+DataPoints.get(0).getPointDTO().get(i).getValue()+"\n");
                        //MainActivity
                        //setentries(new Entry((now-k)%60,DataPoints.get(0).getPointDTO().get(i).getValue()));
                        setData((now-k)%60,DataPoints.get(0).getPointDTO().get(i).getValue());
                    }
                    //java.text.DecimalFormat("0").format(value)格式转换，Integer.parseInt每次构造一个int常量

            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<BaseResponseEntity> call, @NonNull Throwable t) {
                LogUtil.d(TAG, "get UpperLimit error: \n" + t.getMessage());
            }
        });
        return data;
    }


}
