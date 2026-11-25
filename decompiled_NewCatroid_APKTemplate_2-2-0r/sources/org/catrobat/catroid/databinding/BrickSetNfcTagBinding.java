package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetNfcTagBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetNfcTagBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final EditText brickSetNfcTagEditText;
    public final TextView brickSetNfcTagLabel;
    public final BrickLayout brickSetNfcTagLayout;
    public final Spinner brickSetNfcTagNdefRecordSpinner;
    public final TextView brickSetNfcTagNdefRecordTextview;
    private final LinearLayout rootView;

    private BrickSetNfcTagBinding(LinearLayout rootView, CheckBox brickCheckbox, EditText brickSetNfcTagEditText, TextView brickSetNfcTagLabel, BrickLayout brickSetNfcTagLayout, Spinner brickSetNfcTagNdefRecordSpinner, TextView brickSetNfcTagNdefRecordTextview) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetNfcTagEditText = brickSetNfcTagEditText;
        this.brickSetNfcTagLabel = brickSetNfcTagLabel;
        this.brickSetNfcTagLayout = brickSetNfcTagLayout;
        this.brickSetNfcTagNdefRecordSpinner = brickSetNfcTagNdefRecordSpinner;
        this.brickSetNfcTagNdefRecordTextview = brickSetNfcTagNdefRecordTextview;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetNfcTagBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetNfcTagBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_nfc_tag, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetNfcTagBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_nfc_tag_edit_text;
            EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.brick_set_nfc_tag_edit_text);
            if (editText != null) {
                i = R.id.brick_set_nfc_tag_label;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_nfc_tag_label);
                if (textView != null) {
                    i = R.id.brick_set_nfc_tag_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_nfc_tag_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_set_nfc_tag_ndef_record_spinner;
                        Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_set_nfc_tag_ndef_record_spinner);
                        if (spinner != null) {
                            i = R.id.brick_set_nfc_tag_ndef_record_textview;
                            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_nfc_tag_ndef_record_textview);
                            if (textView2 != null) {
                                return new BrickSetNfcTagBinding((LinearLayout) rootView, checkBox, editText, textView, brickLayout, spinner, textView2);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
