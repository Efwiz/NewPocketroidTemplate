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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickDeleteItemOfUserlistBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickDeleteItemOfUserlistBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickDeleteItemOfUserlistEditText;
    public final TextView brickDeleteItemOfUserlistLabel;
    public final BrickLayout brickDeleteItemOfUserlistLayout;
    public final TextView brickDeleteItemOfUserlistTextview;
    public final Spinner deleteItemOfUserlistSpinner;
    private final LinearLayout rootView;

    private BrickDeleteItemOfUserlistBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickDeleteItemOfUserlistEditText, TextView brickDeleteItemOfUserlistLabel, BrickLayout brickDeleteItemOfUserlistLayout, TextView brickDeleteItemOfUserlistTextview, Spinner deleteItemOfUserlistSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickDeleteItemOfUserlistEditText = brickDeleteItemOfUserlistEditText;
        this.brickDeleteItemOfUserlistLabel = brickDeleteItemOfUserlistLabel;
        this.brickDeleteItemOfUserlistLayout = brickDeleteItemOfUserlistLayout;
        this.brickDeleteItemOfUserlistTextview = brickDeleteItemOfUserlistTextview;
        this.deleteItemOfUserlistSpinner = deleteItemOfUserlistSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickDeleteItemOfUserlistBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickDeleteItemOfUserlistBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_delete_item_of_userlist, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickDeleteItemOfUserlistBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_delete_item_of_userlist_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_delete_item_of_userlist_edit_text);
            if (textView != null) {
                i = R.id.brick_delete_item_of_userlist_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_delete_item_of_userlist_label);
                if (textView2 != null) {
                    i = R.id.brick_delete_item_of_userlist_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_delete_item_of_userlist_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_delete_item_of_userlist_textview;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_delete_item_of_userlist_textview);
                        if (textView3 != null) {
                            i = R.id.delete_item_of_userlist_spinner;
                            Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.delete_item_of_userlist_spinner);
                            if (spinner != null) {
                                return new BrickDeleteItemOfUserlistBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3, spinner);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
