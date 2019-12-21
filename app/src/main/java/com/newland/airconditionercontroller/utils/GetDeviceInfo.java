package com.newland.airconditionercontroller.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newland.airconditionercontroller.R;
import com.newland.airconditionercontroller.activity.MainActivity;

import cn.com.newland.nle_sdk.util.NetWorkBusiness;

public class GetDeviceInfo {
    public void displayExistState(int status, MainActivity mainActivity){
            if (status == 0) {
                mainActivity.isDeviceExist = true;
                //mainActivity.mOnlineLayout.setVisibility(View.GONE);
                mainActivity.queryRemoteInfo();
            } else {
                mainActivity.isDeviceExist = false;
                //mainActivity.mOnlineLayout.setVisibility(View.VISIBLE);
                mainActivity.mOnlineText.setText(mainActivity.getResources().getString(R.string.device_not_exist_text));
            }
    }

}
