package io.intelia.checkableviewgroup;

import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;

/**
 * Created by zone2 on 1/14/18.
 */

public interface CheckableViewGroup<T extends Checkable> {


    void setCheckedId(@IdRes int id);

    void clearChecked();

    int getCheckedId();


    void setOnChildClickListener(OnChildClickLister listener);

    void setOnCheckedChangeListener(OnCheckedChangeListener listener);

    /**
     * <p>Interface definition for a callback to be invoked when the checked
     * radio button changed in this group.</p>
     */
    interface OnCheckedChangeListener {
        /**
         * <p>Called when the checked radio button has changed. When the
         * selection is cleared, checkedId is -1.</p>
         *
         * @param group     the group in which the checked radio button has changed
         * @param checkedId the unique identifier of the newly checked radio button
         */
        void onCheckedChanged(ViewGroup group, @IdRes int checkedId);
    }

    interface OnCheckedChangeTrackListener {
        /**
         * Called when the checked state of a compound button has changed.
         *
         * @param v         The  view whose state has changed.
         * @param isChecked The new checked state of buttonView.
         */
        void onCheckedChanged(View v, boolean isChecked);
    }


    interface OnChildClickLister extends View.OnClickListener {
        void onClick(View v);
    }
}




