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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCutLookBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCutLookBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickCutLookFromx;
    public final TextView brickCutLookFromy;
    public final TextView brickCutLookTox;
    public final TextView brickCutLookToy;
    public final BrickLayout brickShowToastLayout;
    private final LinearLayout rootView;

    private BrickCutLookBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickCutLookFromx, TextView brickCutLookFromy, TextView brickCutLookTox, TextView brickCutLookToy, BrickLayout brickShowToastLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickCutLookFromx = brickCutLookFromx;
        this.brickCutLookFromy = brickCutLookFromy;
        this.brickCutLookTox = brickCutLookTox;
        this.brickCutLookToy = brickCutLookToy;
        this.brickShowToastLayout = brickShowToastLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCutLookBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCutLookBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_cut_look, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCutLookBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_cut_look_fromx;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_cut_look_fromx);
            if (textView != null) {
                i = R.id.brick_cut_look_fromy;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_cut_look_fromy);
                if (textView2 != null) {
                    i = R.id.brick_cut_look_tox;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_cut_look_tox);
                    if (textView3 != null) {
                        i = R.id.brick_cut_look_toy;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_cut_look_toy);
                        if (textView4 != null) {
                            i = R.id.brick_show_toast_layout;
                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_show_toast_layout);
                            if (brickLayout != null) {
                                return new BrickCutLookBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, brickLayout);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
