package io.intelia.checkableviewgroup.radioGrouping;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;

import io.intelia.checkableviewgroup.CheckableViewGroup;
import io.intelia.checkableviewgroup.CheckableViewGroupImpl;

/**
 * Created by zone2 on 1/15/18.
 */

public class LinearGroup extends LinearLayoutCompat implements CheckableViewGroup {
    private CheckableViewGroupImpl checkableViewGroupImplementation;


    private void init() {
        checkableViewGroupImplementation = new CheckableViewGroupImpl(this);
        checkableViewGroupImplementation.init();

    }


    public LinearGroup(Context context) {
        super(context);
        init();

    }

    public LinearGroup(Context context, AttributeSet attrs) {
        super(context,attrs);
        init();
        int orientation = attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android", "orientation", VERTICAL);
        setOrientation(orientation);

    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        checkableViewGroupImplementation.addCheckableView((Checkable) child, index, params);

    }

    public void setCheckedId(int id) {
        checkableViewGroupImplementation.setCheckedId(id);
    }

    public void clearChecked() {
        checkableViewGroupImplementation.clearChecked();
    }

    public int getCheckedId() {
        return checkableViewGroupImplementation.getCheckedId();
    }

    public void setOnChildClickListener(OnChildClickLister listener) {
        checkableViewGroupImplementation.setOnChildClickListener(listener);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        checkableViewGroupImplementation.setOnCheckedChangeListener(listener);
    }


}
