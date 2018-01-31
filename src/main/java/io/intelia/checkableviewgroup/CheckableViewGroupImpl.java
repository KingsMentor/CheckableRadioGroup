package io.intelia.checkableviewgroup;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;

/**
 * Created by zone2 on 1/14/18.
 */

public class CheckableViewGroupImpl<T extends Checkable> implements CheckableViewGroup<T>, View.OnClickListener {


    private int mCheckedId = -1;
    private ViewGroup mViewGroup;
    private boolean mProtectFromCheckedChange = false;
    private OnCheckedChangeTrackListener mChildOnCheckedChangeListener;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private OnChildClickLister mOnChildClickLister;

    private CheckableViewGroupImpl() {

    }

    public CheckableViewGroupImpl(ViewGroup parent) {
        this();
        mViewGroup = parent;
    }

    public void init() {
        mChildOnCheckedChangeListener = new CheckedStateTracker();

    }

    public void addCheckableView(T child, int index, ViewGroup.LayoutParams params) {

        final View checked = (View) child;
        checked.setOnClickListener(this);
        checked.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    v.performClick();
            }
        });
        if (((Checkable) checked).isChecked()) {
            mProtectFromCheckedChange = true;
            if (mCheckedId != -1) {
                setCheckedStateForView(mCheckedId, false);
            }
            mProtectFromCheckedChange = false;
            setCheckedId(checked.getId());
        }
    }

    public void setCheckedId(int id) {
        if (id != -1 && (id == mCheckedId)) {
            setCheckedStateForView(id, true);
            mOnCheckedChangeListener.onCheckedChanged(mViewGroup, mCheckedId);
            return;
        }

        if (mCheckedId != -1) {
            setCheckedStateForView(mCheckedId, false);
        }

        if (id != -1) {
            setCheckedStateForView(id, true);
        }
        mCheckedId = id;
        setCheckedStateForView(id, true);
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(mViewGroup, mCheckedId);
        }
    }

    public void clearChecked() {
        setCheckedId(-1);
    }

    public int getCheckedId() {
        return mCheckedId;
    }

    public void setOnChildClickListener(OnChildClickLister listener) {
        mOnChildClickLister = listener;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    private void setCheckedStateForView(int viewId, boolean checked) {
        View checkedView = mViewGroup.findViewById(viewId);
        if (checkedView != null)
            ((Checkable) checkedView).setChecked(checked);
        else throw new NullPointerException("It is manadatory for a child to have an id");
    }

    public void onClick(View v) {
        if (mOnChildClickLister != null) {
            mOnChildClickLister.onClick(v);
        }

        mChildOnCheckedChangeListener.onCheckedChanged(v, ((Checkable) v).isChecked());
    }




    private class CheckedStateTracker implements OnCheckedChangeTrackListener {
        @Override
        public void onCheckedChanged(View v, boolean isChecked) {
            // prevents from infinite recursion
            if (mProtectFromCheckedChange) {
                return;
            }
            if((v.getId() == mCheckedId)) {
                return;
            }

            mProtectFromCheckedChange = true;
            if (mCheckedId != -1) {
                setCheckedStateForView(v.getId(), false);
            }
            mProtectFromCheckedChange = false;

            int id = v.getId();
            setCheckedId(id);
        }
    }
}
