package com.newland.airconditionercontroller.activity.marker;

import android.content.Context;

import com.github.mikephil.charting.components.MarkerView;
import com.newland.airconditionercontroller.R;

/**
 * @author Lai
 * @time 2018/5/26 14:31
 * @describe describe
 */

public class PositionMarker extends MarkerView {

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     */
    public PositionMarker(Context context) {
        super(context, R.layout.item_chart_post);
    }
}
