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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCreateWebUrlBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCreateWebUrlBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickCreateWebUrlLayout;
    public final TextView createWebUrlHeight;
    public final TextView createWebUrlName;
    public final TextView createWebUrlUrl;
    public final TextView createWebUrlWidth;
    public final TextView createWebUrlX;
    public final TextView createWebUrlY;
    private final LinearLayout rootView;

    private BrickCreateWebUrlBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickCreateWebUrlLayout, TextView createWebUrlHeight, TextView createWebUrlName, TextView createWebUrlUrl, TextView createWebUrlWidth, TextView createWebUrlX, TextView createWebUrlY) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickCreateWebUrlLayout = brickCreateWebUrlLayout;
        this.createWebUrlHeight = createWebUrlHeight;
        this.createWebUrlName = createWebUrlName;
        this.createWebUrlUrl = createWebUrlUrl;
        this.createWebUrlWidth = createWebUrlWidth;
        this.createWebUrlX = createWebUrlX;
        this.createWebUrlY = createWebUrlY;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCreateWebUrlBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCreateWebUrlBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_create_web_url, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCreateWebUrlBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_create_web_url_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_create_web_url_layout);
            if (brickLayout != null) {
                i = R.id.create_web_url_height;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_web_url_height);
                if (textView != null) {
                    i = R.id.create_web_url_name;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_web_url_name);
                    if (textView2 != null) {
                        i = R.id.create_web_url_url;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_web_url_url);
                        if (textView3 != null) {
                            i = R.id.create_web_url_width;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_web_url_width);
                            if (textView4 != null) {
                                i = R.id.create_web_url_x;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_web_url_x);
                                if (textView5 != null) {
                                    i = R.id.create_web_url_y;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_web_url_y);
                                    if (textView6 != null) {
                                        return new BrickCreateWebUrlBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3, textView4, textView5, textView6);
                                    }
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
