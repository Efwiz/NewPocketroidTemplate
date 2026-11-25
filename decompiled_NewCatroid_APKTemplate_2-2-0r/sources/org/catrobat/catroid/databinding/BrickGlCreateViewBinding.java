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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickGlCreateViewBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickGlCreateViewBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickGlCreateLayout;
    public final TextView brickGlHeight;
    public final TextView brickGlName;
    public final TextView brickGlWidth;
    public final TextView brickGlX;
    public final TextView brickGlY;
    private final LinearLayout rootView;

    private BrickGlCreateViewBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickGlCreateLayout, TextView brickGlHeight, TextView brickGlName, TextView brickGlWidth, TextView brickGlX, TextView brickGlY) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickGlCreateLayout = brickGlCreateLayout;
        this.brickGlHeight = brickGlHeight;
        this.brickGlName = brickGlName;
        this.brickGlWidth = brickGlWidth;
        this.brickGlX = brickGlX;
        this.brickGlY = brickGlY;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickGlCreateViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickGlCreateViewBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_gl_create_view, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickGlCreateViewBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_gl_create_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_gl_create_layout);
            if (brickLayout != null) {
                i = R.id.brick_gl_height;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_gl_height);
                if (textView != null) {
                    i = R.id.brick_gl_name;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_gl_name);
                    if (textView2 != null) {
                        i = R.id.brick_gl_width;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_gl_width);
                        if (textView3 != null) {
                            i = R.id.brick_gl_x;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_gl_x);
                            if (textView4 != null) {
                                i = R.id.brick_gl_y;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_gl_y);
                                if (textView5 != null) {
                                    return new BrickGlCreateViewBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3, textView4, textView5);
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
