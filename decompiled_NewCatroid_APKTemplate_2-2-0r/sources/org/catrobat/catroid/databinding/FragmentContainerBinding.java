package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.viewbinding.ViewBinding;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FragmentContainerBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FragmentContainerBinding implements ViewBinding {
    public final FrameLayout fragmentContainerView;
    private final FrameLayout rootView;

    private FragmentContainerBinding(FrameLayout rootView, FrameLayout fragmentContainerView) {
        this.rootView = rootView;
        this.fragmentContainerView = fragmentContainerView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public FrameLayout getRoot() {
        return this.rootView;
    }

    public static FragmentContainerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentContainerBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_container, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentContainerBinding bind(View rootView) {
        if (rootView == null) {
            throw new NullPointerException("rootView");
        }
        FrameLayout frameLayout = (FrameLayout) rootView;
        return new FragmentContainerBinding(frameLayout, frameLayout);
    }
}
