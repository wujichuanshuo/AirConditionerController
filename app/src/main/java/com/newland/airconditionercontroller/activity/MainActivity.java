package com.newland.airconditionercontroller.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.google.gson.Gson;
import com.newland.airconditionercontroller.AirConditionerControllerApplication;
import com.newland.airconditionercontroller.Constant;
import com.newland.airconditionercontroller.R;
import com.newland.airconditionercontroller.activity.marker.Serializables;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.com.newland.nle_sdk.responseEntity.Device;
import cn.com.newland.nle_sdk.responseEntity.DeviceState;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivity extends BaseActivity {

    public static String TAG = "MainActivity";
    List<Entry> entriess = new ArrayList<>();
    public Button ms, lswdgz, kg, mskg;
    public TextView dqwd, dqgz;


    public TextView mUperLimitText, mUperLimitTitle, mLowerLimitText, mLowerLimitTitle, mCurrentTempText, mCurrentTempTextTitle, mOnlineText;
    public ImageView mAirStateImageView;
    public LinearLayout currentTemp_layout1,currentTemp_layout;
    public LinearLayout mOnlineLayout, mCurrentTempLayout;
    public ImageView imageView;
    public TextView pdrs, ddsj;
    public Button zxjz, lssj;
    public int pdrsval=20, ddsjval=100;
    public int data[] = new int[60];
    public int kgval, mskgval;
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
    public mmNetWorkBusiness mmNetWorkBusiness = new mmNetWorkBusiness();
    public int flag = 0;
    public int flagOfMsg = 0;
    public int flagdisplay = 0;


    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case flag1:
                    queryRemoteInfo();
                    break;
                case flag2:
                    break;
                case flag3:
                    queryRemoteInfo2();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        spHelper = SPHelper.getInstant(getApplicationContext());
        mDeviceId = spHelper.getStringFromSP(getApplicationContext(), Constant.DEVICE_ID);
        mNetWorkBusiness = new NetWorkBusiness(DataCache.getAccessToken(getApplicationContext()), DataCache.getBaseUrl(getApplicationContext()));
        mAlarmMaxValue = Integer.valueOf(spHelper.getStringFromSPDef(AirConditionerControllerApplication.getInstance(), Constant.ALARM_MAX_VALUE, Constant.ALARM_MAX_VALUE_DEFAULT_VALUE));
        initView();
        initEvent();
        getDeviceInfo();
        queryRemoteInfo();
    }


    private void initView() {
        initHeadView();
        setHeadVisable(true);
        initTitleView(this.getString(R.string.app_title));
        setRithtTitleViewVisable(false);
        //设置界面开启
        setRithtSettingVisable(true);
        pdrs = findViewById(R.id.pdrs);
        ddsj = findViewById(R.id.ddsj);
        zxjz = findViewById(R.id.zxjz);
        lssj = findViewById(R.id.lssj);
        currentTemp_layout=findViewById(R.id.currentTemp_layout);
        currentTemp_layout1=findViewById(R.id.currentTemp_layout1);
        zxjz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "前方还剩 " + pdrsval + " 人", Toast.LENGTH_SHORT).show();
            }
        });
        lssj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Main2Activity.class);
                Bundle bundle = new Bundle();
                Serializables serializables = new Serializables(data);
                bundle.putSerializable("serializables", serializables);
                intent.putExtras(bundle);
                startActivity(intent);
                /*
                * Intent intent =this.getIntent();
                    user=(User)intent.getSerializableExtra("user");*/
            }
        });


    }


    private void initEvent() {

    }


    //各种查询开始
    private void getDeviceInfo() {
        LogUtil.d(TAG, "getDeviceInfo");
        mDeviceId = spHelper.getStringFromSP(getApplicationContext(), Constant.DEVICE_ID);
    }


    public void queryRemoteInfo() {
        LogUtil.d(TAG, "queryRemoteInfo");
        final Gson gson = new Gson();
        mmNetWorkBusiness.getSensor(mDeviceId, "number_up", this);
        setData(mmNetWorkBusiness.getDatas(mDeviceId, "number_up", "1", "60", this));
        LogUtil.d("mm7", flagOfMsg + "sss");
        if (pdrsval < 4&&flagOfMsg!=1) {
            flagOfMsg=1;
            Bundle bundle = new Bundle();
            Message message = Message.obtain();
            message.setData(bundle);   //message.obj=bundle  传值也行
            message.what = flag2;
            mHandler.sendMessage(message);
            Vibrator vibrator = (Vibrator)this.getSystemService(this.VIBRATOR_SERVICE);
            vibrator.vibrate((long)100.0);
            showAlterDialog(this);
        }
        else
            mHandler.sendEmptyMessageDelayed(flag1, GET_REMOTE_INFO_DELAY);
    }


    //实时更新模块
    //mHandler.sendEmptyMessageDelayed(flag1, GET_REMOTE_INFO_DELAY);


    public void queryRemoteInfo2() {
        LogUtil.d(TAG, "queryRemoteInfo");
        final Gson gson = new Gson();
        mmNetWorkBusiness.getSensor(mDeviceId,"number_up",this);
        setData(mmNetWorkBusiness.getDatas(mDeviceId,"number_up","1","60",this));
        LogUtil.d("mm7",flagOfMsg+"sss");
        Bundle bundle = new Bundle();
        Message message = Message.obtain();
        message.setData(bundle);
        message.what = flag3;
        mHandler.sendMessage(message);
        //实时更新模块
        //mHandler.sendEmptyMessageDelayed(flag1, GET_REMOTE_INFO_DELAY);
    }

    @Override
    protected void setRightSetting() {
        super.setRightSetting();
        Intent intent = new Intent(MainActivity.this, UpperLowerSetActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    public void setData(int[] data) {
        this.data = data;
        for(int i=0;i<60;i++)
            if(data[i]==0)
        this.data[i]=0;

    }
    @SuppressLint("MissingPermission")
    private void showAlterDialog(MainActivity mainActivity){
        final String AccessToken=DataCache.getAccessToken(mainActivity.getApplicationContext());
        final String BaseUrl=DataCache.getBaseUrl(mainActivity.getApplicationContext());
        final AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(MainActivity.this);
        LogUtil.d("mm7",mainActivity.flagOfMsg+"sss");
        Vibrator vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{0,1000}, -1);
        LogUtil.d("mm7",mainActivity.flagOfMsg+"sss");
        alterDiaglog.setTitle("提示");//文字
        alterDiaglog.setMessage("前方还有3人");//提示消息


        //积极的选择
        alterDiaglog.setPositiveButton("取消提醒", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"取消提醒",Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                Message message = Message.obtain();
                message.setData(bundle);
                message.what = flag1;
                mHandler.sendMessage(message);
            }
        });
        //消极的选择
        alterDiaglog.setNegativeButton("放弃排队", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mmNetWorkBusiness.control(mDeviceId,"number_down",1,AccessToken,BaseUrl);

                Toast.makeText(MainActivity.this,"放弃排队",Toast.LENGTH_SHORT).show();

            }
        });

        //显示
        alterDiaglog.show();
    }
    public void setbg(){

    }

}
