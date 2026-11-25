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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickFileUrlBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickFileUrlBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickFileUrlEditFile;
    public final TextView brickFileUrlEditText;
    public final TextView brickFileUrlLabel;
    public final TextView brickFileUrlLabel2;
    public final BrickLayout brickFileUrlLayout;
    private final LinearLayout rootView;

    private BrickFileUrlBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickFileUrlEditFile, TextView brickFileUrlEditText, TextView brickFileUrlLabel, TextView brickFileUrlLabel2, BrickLayout brickFileUrlLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickFileUrlEditFile = brickFileUrlEditFile;
        this.brickFileUrlEditText = brickFileUrlEditText;
        this.brickFileUrlLabel = brickFileUrlLabel;
        this.brickFileUrlLabel2 = brickFileUrlLabel2;
        this.brickFileUrlLayout = brickFileUrlLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickFileUrlBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickFileUrlBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_file_url, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickFileUrlBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_file_url_edit_file;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_file_url_edit_file);
            if (textView != null) {
                i = R.id.brick_file_url_edit_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_file_url_edit_text);
                if (textView2 != null) {
                    i = R.id.brick_file_url_label;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_file_url_label);
                    if (textView3 != null) {
                        i = R.id.brick_file_url_label2;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_file_url_label2);
                        if (textView4 != null) {
                            i = R.id.brick_file_url_layout;
                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_file_url_layout);
                            if (brickLayout != null) {
                                return new BrickFileUrlBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, brickLayout);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
