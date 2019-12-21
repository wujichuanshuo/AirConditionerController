package com.newland.airconditionercontroller.utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Message;
import android.widget.Toast;

import com.newland.airconditionercontroller.Constant;
import com.newland.airconditionercontroller.R;
import com.newland.airconditionercontroller.activity.MainActivity;
import com.newland.airconditionercontroller.activity.msActivity;
import com.newland.airconditionercontroller.bean.DeviceInfo;

public class GetSensor {

    public void displaypdrs(int value,MainActivity mainActivity){
        mainActivity.pdrs.setText(value+"人");
        mainActivity.ddsj.setText(value*5+"min");
        mainActivity.pdrsval=value;
        mainActivity.ddsjval=value*5;
    }
    }




