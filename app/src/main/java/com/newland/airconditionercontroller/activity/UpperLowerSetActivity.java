package com.newland.airconditionercontroller.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newland.airconditionercontroller.Constant;
import com.newland.airconditionercontroller.R;
import com.newland.airconditionercontroller.base.BaseActivity;
import com.newland.airconditionercontroller.bean.DeviceInfo;
import com.newland.airconditionercontroller.utils.DataCache;
import com.newland.airconditionercontroller.utils.LogUtil;
import com.newland.airconditionercontroller.utils.SPHelper;
import com.newland.airconditionercontroller.utils.mmNetWorkBusiness;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;

public class UpperLowerSetActivity extends BaseActivity implements View.OnClickListener{
    private final static String TAG = "UpperLowerSetActivity";

    private EditText mUpperText, mLowerText;
    private Button mSaveBtn, mCancleBtn;

    private NetWorkBusiness mNetWorkBusiness;
    public com.newland.airconditionercontroller.utils.mmNetWorkBusiness mmNetWorkBusiness=new mmNetWorkBusiness();
    private SPHelper spHelper;
    private String mDeviceId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upperlowerset);
        mNetWorkBusiness = new NetWorkBusiness(DataCache.getAccessToken(getApplicationContext()), DataCache.getBaseUrl(getApplicationContext()));
        spHelper = SPHelper.getInstant(getApplicationContext());
        mDeviceId = spHelper.getStringFromSP(getApplicationContext(), Constant.DEVICE_ID);
        initView();
        initEvent();
    }

    private void initView() {
        initHeadView();
        setHeadVisable(true);
        initLeftTitleView("返回");
        setLeftTitleView(true);
        initTitleView("开关灯时间");
        setRithtTitleViewVisable(false);
        setRithtSettingVisable(false);




    }

    private void initEvent() {
        mSaveBtn.setOnClickListener(this);
        mCancleBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                saveSetting();
                break;
            case R.id.cancle:
                finish();
                break;
                default:
                    break;
        }
    }

    private void saveSetting() {
        String upperValue = mUpperText.getText().toString().trim();
        String lowerValue = mLowerText.getText().toString().trim();

        if ("".equals(upperValue)) {
            Toast.makeText(getApplicationContext(), "请填写上限值", Toast.LENGTH_LONG).show();
            return;
        }
        if ("".equals(lowerValue)) {
            Toast.makeText(getApplicationContext(), "请填写下限值", Toast.LENGTH_LONG).show();
            return;
        }


        Toast.makeText(getApplicationContext(), "设置完成", Toast.LENGTH_SHORT).show();
        finish();
    }



}
