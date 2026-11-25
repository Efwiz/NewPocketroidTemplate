package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/PocketmusicAddTactButtonBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class PocketmusicAddTactButtonBinding implements ViewBinding {
    private final TextView rootView;

    private PocketmusicAddTactButtonBinding(TextView rootView) {
        this.rootView = rootView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public TextView getRoot() {
        return this.rootView;
    }

    public static PocketmusicAddTactButtonBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static PocketmusicAddTactButtonBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.pocketmusic_add_tact_button, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static PocketmusicAddTactButtonBinding bind(View rootView) {
        if (rootView == null) {
            throw new NullPointerException("rootView");
        }
        return new PocketmusicAddTactButtonBinding((TextView) rootView);
    }
}
