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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickChangeTempoBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickChangeTempoBinding implements ViewBinding {
    public final BrickLayout brickChangeTempoByLayout;
    public final TextView brickChangeTempoEditText;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickChangeTempoBinding(LinearLayout rootView, BrickLayout brickChangeTempoByLayout, TextView brickChangeTempoEditText, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickChangeTempoByLayout = brickChangeTempoByLayout;
        this.brickChangeTempoEditText = brickChangeTempoEditText;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickChangeTempoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickChangeTempoBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_change_tempo, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickChangeTempoBinding bind(View rootView) {
        int i = R.id.brick_change_tempo_by_layout;
        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_change_tempo_by_layout);
        if (brickLayout != null) {
            i = R.id.brick_change_tempo_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_change_tempo_edit_text);
            if (textView != null) {
                i = R.id.brick_checkbox;
                CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                if (checkBox != null) {
                    return new BrickChangeTempoBinding((LinearLayout) rootView, brickLayout, textView, checkBox);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
