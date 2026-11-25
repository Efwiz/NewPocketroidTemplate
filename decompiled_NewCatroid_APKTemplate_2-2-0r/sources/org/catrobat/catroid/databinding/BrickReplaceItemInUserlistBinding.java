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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickReplaceItemInUserlistBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickReplaceItemInUserlistBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickReplaceItemInUserlistAtIndexEditText;
    public final TextView brickReplaceItemInUserlistLabel;
    public final BrickLayout brickReplaceItemInUserlistLayout;
    public final TextView brickReplaceItemInUserlistTextview;
    public final TextView brickReplaceItemInUserlistValueEditText;
    public final TextView brickReplaceItemInUserlistWithValueTextview;
    public final Spinner replaceItemInUserlistSpinner;
    private final LinearLayout rootView;

    private BrickReplaceItemInUserlistBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickReplaceItemInUserlistAtIndexEditText, TextView brickReplaceItemInUserlistLabel, BrickLayout brickReplaceItemInUserlistLayout, TextView brickReplaceItemInUserlistTextview, TextView brickReplaceItemInUserlistValueEditText, TextView brickReplaceItemInUserlistWithValueTextview, Spinner replaceItemInUserlistSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickReplaceItemInUserlistAtIndexEditText = brickReplaceItemInUserlistAtIndexEditText;
        this.brickReplaceItemInUserlistLabel = brickReplaceItemInUserlistLabel;
        this.brickReplaceItemInUserlistLayout = brickReplaceItemInUserlistLayout;
        this.brickReplaceItemInUserlistTextview = brickReplaceItemInUserlistTextview;
        this.brickReplaceItemInUserlistValueEditText = brickReplaceItemInUserlistValueEditText;
        this.brickReplaceItemInUserlistWithValueTextview = brickReplaceItemInUserlistWithValueTextview;
        this.replaceItemInUserlistSpinner = replaceItemInUserlistSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickReplaceItemInUserlistBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickReplaceItemInUserlistBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_replace_item_in_userlist, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickReplaceItemInUserlistBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_replace_item_in_userlist_at_index_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_replace_item_in_userlist_at_index_edit_text);
            if (textView != null) {
                i = R.id.brick_replace_item_in_userlist_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_replace_item_in_userlist_label);
                if (textView2 != null) {
                    i = R.id.brick_replace_item_in_userlist_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_replace_item_in_userlist_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_replace_item_in_userlist_textview;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_replace_item_in_userlist_textview);
                        if (textView3 != null) {
                            i = R.id.brick_replace_item_in_userlist_value_edit_text;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_replace_item_in_userlist_value_edit_text);
                            if (textView4 != null) {
                                i = R.id.brick_replace_item_in_userlist_with_value_textview;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_replace_item_in_userlist_with_value_textview);
                                if (textView5 != null) {
                                    i = R.id.replace_item_in_userlist_spinner;
                                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.replace_item_in_userlist_spinner);
                                    if (spinner != null) {
                                        return new BrickReplaceItemInUserlistBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3, textView4, textView5, spinner);
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
