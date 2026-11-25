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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCreateWebFileBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCreateWebFileBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickCreateWebFileLayout;
    public final TextView createWebFileFile;
    public final TextView createWebFileHeight;
    public final TextView createWebFileName;
    public final TextView createWebFileWidth;
    public final TextView createWebFileX;
    public final TextView createWebFileY;
    private final LinearLayout rootView;

    private BrickCreateWebFileBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickCreateWebFileLayout, TextView createWebFileFile, TextView createWebFileHeight, TextView createWebFileName, TextView createWebFileWidth, TextView createWebFileX, TextView createWebFileY) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickCreateWebFileLayout = brickCreateWebFileLayout;
        this.createWebFileFile = createWebFileFile;
        this.createWebFileHeight = createWebFileHeight;
        this.createWebFileName = createWebFileName;
        this.createWebFileWidth = createWebFileWidth;
        this.createWebFileX = createWebFileX;
        this.createWebFileY = createWebFileY;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCreateWebFileBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCreateWebFileBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_create_web_file, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCreateWebFileBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_create_web_file_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_create_web_file_layout);
            if (brickLayout != null) {
                i = R.id.create_web_file_file;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_web_file_file);
                if (textView != null) {
                    i = R.id.create_web_file_height;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_web_file_height);
                    if (textView2 != null) {
                        i = R.id.create_web_file_name;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_web_file_name);
                        if (textView3 != null) {
                            i = R.id.create_web_file_width;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_web_file_width);
                            if (textView4 != null) {
                                i = R.id.create_web_file_x;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_web_file_x);
                                if (textView5 != null) {
                                    i = R.id.create_web_file_y;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_web_file_y);
                                    if (textView6 != null) {
                                        return new BrickCreateWebFileBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3, textView4, textView5, textView6);
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
