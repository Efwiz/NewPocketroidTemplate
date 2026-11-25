package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import androidx.viewbinding.ViewBinding;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/SimpleSpinnerDropdownItemWhiteTextBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class SimpleSpinnerDropdownItemWhiteTextBinding implements ViewBinding {
    private final CheckedTextView rootView;
    public final CheckedTextView text1;

    private SimpleSpinnerDropdownItemWhiteTextBinding(CheckedTextView rootView, CheckedTextView text1) {
        this.rootView = rootView;
        this.text1 = text1;
    }

    @Override // androidx.viewbinding.ViewBinding
    public CheckedTextView getRoot() {
        return this.rootView;
    }

    public static SimpleSpinnerDropdownItemWhiteTextBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static SimpleSpinnerDropdownItemWhiteTextBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.simple_spinner_dropdown_item_white_text, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static SimpleSpinnerDropdownItemWhiteTextBinding bind(View rootView) {
        if (rootView == null) {
            throw new NullPointerException("rootView");
        }
        CheckedTextView checkedTextView = (CheckedTextView) rootView;
        return new SimpleSpinnerDropdownItemWhiteTextBinding(checkedTextView, checkedTextView);
    }
}
