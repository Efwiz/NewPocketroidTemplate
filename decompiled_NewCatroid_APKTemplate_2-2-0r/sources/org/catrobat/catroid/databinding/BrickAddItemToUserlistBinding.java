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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickAddItemToUserlistBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickAddItemToUserlistBinding implements ViewBinding {
    public final Spinner addItemToUserlistSpinner;
    public final TextView brickAddItemToUserlistEditText;
    public final TextView brickAddItemToUserlistLabel;
    public final BrickLayout brickAddItemToUserlistLayout;
    public final TextView brickAddItemToUserlistTextview;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickAddItemToUserlistBinding(LinearLayout rootView, Spinner addItemToUserlistSpinner, TextView brickAddItemToUserlistEditText, TextView brickAddItemToUserlistLabel, BrickLayout brickAddItemToUserlistLayout, TextView brickAddItemToUserlistTextview, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.addItemToUserlistSpinner = addItemToUserlistSpinner;
        this.brickAddItemToUserlistEditText = brickAddItemToUserlistEditText;
        this.brickAddItemToUserlistLabel = brickAddItemToUserlistLabel;
        this.brickAddItemToUserlistLayout = brickAddItemToUserlistLayout;
        this.brickAddItemToUserlistTextview = brickAddItemToUserlistTextview;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickAddItemToUserlistBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickAddItemToUserlistBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_add_item_to_userlist, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickAddItemToUserlistBinding bind(View rootView) {
        int i = R.id.add_item_to_userlist_spinner;
        Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.add_item_to_userlist_spinner);
        if (spinner != null) {
            i = R.id.brick_add_item_to_userlist_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_add_item_to_userlist_edit_text);
            if (textView != null) {
                i = R.id.brick_add_item_to_userlist_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_add_item_to_userlist_label);
                if (textView2 != null) {
                    i = R.id.brick_add_item_to_userlist_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_add_item_to_userlist_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_add_item_to_userlist_textview;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_add_item_to_userlist_textview);
                        if (textView3 != null) {
                            i = R.id.brick_checkbox;
                            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                            if (checkBox != null) {
                                return new BrickAddItemToUserlistBinding((LinearLayout) rootView, spinner, textView, textView2, brickLayout, textView3, checkBox);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
