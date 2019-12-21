package com.newland.airconditionercontroller.activity.marker;

import com.github.mikephil.charting.data.Entry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Serializables implements Serializable {

    private static final long serialVersionUID = 1L;
    private int []data;
    public Serializables(){}

    public Serializables(int []data){
        this.data=data;
    }
    public int [] getData() {
        return data;
    }
    public void setData(int []data) {
        this.data = data;
    }

}
