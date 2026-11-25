package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ToolbarBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ToolbarBinding implements ViewBinding {
    private final Toolbar rootView;
    public final Toolbar toolbar;

    private ToolbarBinding(Toolbar rootView, Toolbar toolbar) {
        this.rootView = rootView;
        this.toolbar = toolbar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public Toolbar getRoot() {
        return this.rootView;
    }

    public static ToolbarBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ToolbarBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.toolbar, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ToolbarBinding bind(View rootView) {
        if (rootView == null) {
            throw new NullPointerException("rootView");
        }
        Toolbar toolbar = (Toolbar) rootView;
        return new ToolbarBinding(toolbar, toolbar);
    }
}
