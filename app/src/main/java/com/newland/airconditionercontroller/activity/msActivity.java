package com.newland.airconditionercontroller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newland.airconditionercontroller.AirConditionerControllerApplication;
import com.newland.airconditionercontroller.Constant;
import com.newland.airconditionercontroller.R;
import com.newland.airconditionercontroller.base.BaseActivity;
import com.newland.airconditionercontroller.bean.DeviceInfo;
import com.newland.airconditionercontroller.utils.DataCache;
import com.newland.airconditionercontroller.utils.LogUtil;
import com.newland.airconditionercontroller.utils.SPHelper;
import com.newland.airconditionercontroller.utils.mmNetWorkBusiness;
//import com.newland.airconditionercontroller.utils.mmNetWorkBusiness;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.com.newland.nle_sdk.responseEntity.Device;
import cn.com.newland.nle_sdk.responseEntity.DeviceState;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class msActivity extends BaseActivity {

    public TextView mUperLimitText, mUperLimitTitle, mLowerLimitText, mLowerLimitTitle, mCurrentTempText, mCurrentTempTextTitle, mOnlineText;
    public ImageView mAirStateImageView;
    public LinearLayout mOnlineLayout, mCurrentTempLayout;

    public TextView dqmsxs;

    public int mAlarmMaxValue = 30;
    public static final int GET_REMOTE_INFO = 101;
    public static final int flag1 = 111;
    public static final int flag2 = 1100;
    public static final int flag3 = 1110;
    public static final int GET_REMOTE_INFO_DELAY = 1000;
    public String mDeviceId;
    public SPHelper spHelper;
    public NetWorkBusiness mNetWorkBusiness;
    public boolean isDeviceExist = false;
    public boolean isDeviceOnLine = false;
    public boolean isPowerOn = false;
    public boolean isAlarmStatus = false;
    public int mBlueColor, mGrayColor, mAlarmColor;
    public Context mContext;
    public mmNetWorkBusiness mmNetWorkBusiness=new mmNetWorkBusiness();
    public int flag=0;
    public int flagdisplay=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ms);
        mContext = this;
        spHelper = SPHelper.getInstant(getApplicationContext());
        mDeviceId = spHelper.getStringFromSP(getApplicationContext(), Constant.DEVICE_ID);
        mNetWorkBusiness = new NetWorkBusiness(DataCache.getAccessToken(getApplicationContext()), DataCache.getBaseUrl(getApplicationContext()));
        mAlarmMaxValue = Integer.valueOf(spHelper.getStringFromSPDef(AirConditionerControllerApplication.getInstance(), Constant.ALARM_MAX_VALUE, Constant.ALARM_MAX_VALUE_DEFAULT_VALUE));
        initHeadView();
        setHeadVisable(true);
        initTitleView("门锁信息");
        setRithtTitleViewVisable(false);
        //设置界面开启
        setRithtSettingVisable(true);
        dqmsxs=findViewById(R.id.msxs);
        //mmNetWorkBusiness.getSensor(mDeviceId,"door_status",this);

    }
}
