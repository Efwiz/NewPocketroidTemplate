package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/VisualPlacementLayoutBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class VisualPlacementLayoutBinding implements ViewBinding {
    public final FrameLayout frameContainer;
    private final LinearLayout rootView;
    public final Toolbar transparentToolbar;

    private VisualPlacementLayoutBinding(LinearLayout rootView, FrameLayout frameContainer, Toolbar transparentToolbar) {
        this.rootView = rootView;
        this.frameContainer = frameContainer;
        this.transparentToolbar = transparentToolbar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static VisualPlacementLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static VisualPlacementLayoutBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.visual_placement_layout, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static VisualPlacementLayoutBinding bind(View rootView) {
        int i = R.id.frame_container;
        FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(rootView, R.id.frame_container);
        if (frameLayout != null) {
            i = R.id.transparent_toolbar;
            Toolbar toolbar = (Toolbar) ViewBindings.findChildViewById(rootView, R.id.transparent_toolbar);
            if (toolbar != null) {
                return new VisualPlacementLayoutBinding((LinearLayout) rootView, frameLayout, toolbar);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
