package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/AlertDialogLayoutBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class AlertDialogLayoutBinding implements ViewBinding {
    private final TextView rootView;
    public final TextView titleOptionItem;

    private AlertDialogLayoutBinding(TextView rootView, TextView titleOptionItem) {
        this.rootView = rootView;
        this.titleOptionItem = titleOptionItem;
    }

    @Override // androidx.viewbinding.ViewBinding
    public TextView getRoot() {
        return this.rootView;
    }

    public static AlertDialogLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static AlertDialogLayoutBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.alert_dialog_layout, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static AlertDialogLayoutBinding bind(View rootView) {
        if (rootView == null) {
            throw new NullPointerException("rootView");
        }
        TextView textView = (TextView) rootView;
        return new AlertDialogLayoutBinding(textView, textView);
    }
}
