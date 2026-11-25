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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickStoreCsvIntoUserlistBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickStoreCsvIntoUserlistBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickStoreCsvIntoUserlistColumnEditText;
    public final TextView brickStoreCsvIntoUserlistCsvEditText;
    public final BrickLayout brickStoreCsvIntoUserlistLayout;
    public final Spinner brickStoreCsvIntoUserlistSpinner;
    private final LinearLayout rootView;

    private BrickStoreCsvIntoUserlistBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickStoreCsvIntoUserlistColumnEditText, TextView brickStoreCsvIntoUserlistCsvEditText, BrickLayout brickStoreCsvIntoUserlistLayout, Spinner brickStoreCsvIntoUserlistSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickStoreCsvIntoUserlistColumnEditText = brickStoreCsvIntoUserlistColumnEditText;
        this.brickStoreCsvIntoUserlistCsvEditText = brickStoreCsvIntoUserlistCsvEditText;
        this.brickStoreCsvIntoUserlistLayout = brickStoreCsvIntoUserlistLayout;
        this.brickStoreCsvIntoUserlistSpinner = brickStoreCsvIntoUserlistSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickStoreCsvIntoUserlistBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickStoreCsvIntoUserlistBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_store_csv_into_userlist, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickStoreCsvIntoUserlistBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_store_csv_into_userlist_column_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_store_csv_into_userlist_column_edit_text);
            if (textView != null) {
                i = R.id.brick_store_csv_into_userlist_csv_edit_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_store_csv_into_userlist_csv_edit_text);
                if (textView2 != null) {
                    i = R.id.brick_store_csv_into_userlist_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_store_csv_into_userlist_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_store_csv_into_userlist_spinner;
                        Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_store_csv_into_userlist_spinner);
                        if (spinner != null) {
                            return new BrickStoreCsvIntoUserlistBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, spinner);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
