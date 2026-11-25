package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPutFloatBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPutFloatBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickCreateTableLayout;
    public final TextView brickPutFloatFloat;
    public final TextView brickPutFloatIndex;
    public final TextView brickPutFloatValue;
    private final LinearLayout rootView;

    private BrickPutFloatBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickCreateTableLayout, TextView brickPutFloatFloat, TextView brickPutFloatIndex, TextView brickPutFloatValue) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickCreateTableLayout = brickCreateTableLayout;
        this.brickPutFloatFloat = brickPutFloatFloat;
        this.brickPutFloatIndex = brickPutFloatIndex;
        this.brickPutFloatValue = brickPutFloatValue;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPutFloatBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPutFloatBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_put_float, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPutFloatBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_create_table_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_create_table_layout);
            if (brickLayout != null) {
                i = R.id.brick_put_float_float;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_put_float_float);
                if (textView != null) {
                    i = R.id.brick_put_float_index;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_put_float_index);
                    if (textView2 != null) {
                        i = R.id.brick_put_float_value;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_put_float_value);
                        if (textView3 != null) {
                            return new BrickPutFloatBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
