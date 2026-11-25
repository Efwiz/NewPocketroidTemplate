package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/IconBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class IconBinding implements ViewBinding {
    public final LinearLayout icon;
    private final LinearLayout rootView;

    private IconBinding(LinearLayout rootView, LinearLayout icon) {
        this.rootView = rootView;
        this.icon = icon;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static IconBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static IconBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.icon, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static IconBinding bind(View rootView) {
        if (rootView == null) {
            throw new NullPointerException("rootView");
        }
        LinearLayout linearLayout = (LinearLayout) rootView;
        return new IconBinding(linearLayout, linearLayout);
    }
}
