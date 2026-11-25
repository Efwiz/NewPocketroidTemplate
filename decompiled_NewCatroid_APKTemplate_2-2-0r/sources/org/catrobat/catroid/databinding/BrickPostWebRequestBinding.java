package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPostWebRequestBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPostWebRequestBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickPostWebRequestEditBody;
    public final TextView brickPostWebRequestEditHeader;
    public final TextView brickPostWebRequestEditUrl;
    public final BrickLayout brickPostWebRequestLayout;
    public final Spinner postWebRequestSpinner;
    private final LinearLayout rootView;

    private BrickPostWebRequestBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickPostWebRequestEditBody, TextView brickPostWebRequestEditHeader, TextView brickPostWebRequestEditUrl, BrickLayout brickPostWebRequestLayout, Spinner postWebRequestSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickPostWebRequestEditBody = brickPostWebRequestEditBody;
        this.brickPostWebRequestEditHeader = brickPostWebRequestEditHeader;
        this.brickPostWebRequestEditUrl = brickPostWebRequestEditUrl;
        this.brickPostWebRequestLayout = brickPostWebRequestLayout;
        this.postWebRequestSpinner = postWebRequestSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPostWebRequestBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPostWebRequestBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_post_web_request, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPostWebRequestBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_post_web_request_edit_body;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_post_web_request_edit_body);
            if (textView != null) {
                i = R.id.brick_post_web_request_edit_header;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_post_web_request_edit_header);
                if (textView2 != null) {
                    i = R.id.brick_post_web_request_edit_url;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_post_web_request_edit_url);
                    if (textView3 != null) {
                        i = R.id.brick_post_web_request_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_post_web_request_layout);
                        if (brickLayout != null) {
                            i = R.id.post_web_request_spinner;
                            Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.post_web_request_spinner);
                            if (spinner != null) {
                                return new BrickPostWebRequestBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout, spinner);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
