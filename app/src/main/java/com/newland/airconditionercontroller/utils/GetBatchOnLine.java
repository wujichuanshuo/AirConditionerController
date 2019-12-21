package com.newland.airconditionercontroller.utils;

import android.view.View;

import com.newland.airconditionercontroller.Constant;
import com.newland.airconditionercontroller.R;
import com.newland.airconditionercontroller.activity.MainActivity;

public class GetBatchOnLine {
    public void displayOnlineState(boolean status, MainActivity mainActivity) {
        mainActivity.isDeviceOnLine = status;
        if (!status) {
            mainActivity.mOnlineLayout.setVisibility(View.VISIBLE);
            mainActivity.mOnlineText.setText(mainActivity.getResources().getString(R.string.online_text));
        } else {
            mainActivity.mOnlineLayout.setVisibility(View.GONE);
        }
    }
}
