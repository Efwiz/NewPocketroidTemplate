package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/PreferenceBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class PreferenceBinding implements ViewBinding {
    public final FrameLayout contentFrame;
    private final LinearLayout rootView;

    private PreferenceBinding(LinearLayout rootView, FrameLayout contentFrame) {
        this.rootView = rootView;
        this.contentFrame = contentFrame;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static PreferenceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static PreferenceBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.preference, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static PreferenceBinding bind(View rootView) {
        FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(rootView, R.id.content_frame);
        if (frameLayout != null) {
            return new PreferenceBinding((LinearLayout) rootView, frameLayout);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(R.id.content_frame)));
    }
}
