package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.workspace.WorkspaceLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ActivitySpriteBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ActivitySpriteBinding implements ViewBinding {
    public final LinearLayout activitySprite;
    public final ListActionButtonsBinding bottomBar;
    public final FrameLayout fragmentContainer;
    private final LinearLayout rootView;
    public final WorkspaceLayout workspaceLayout;

    private ActivitySpriteBinding(LinearLayout rootView, LinearLayout activitySprite, ListActionButtonsBinding bottomBar, FrameLayout fragmentContainer, WorkspaceLayout workspaceLayout) {
        this.rootView = rootView;
        this.activitySprite = activitySprite;
        this.bottomBar = bottomBar;
        this.fragmentContainer = fragmentContainer;
        this.workspaceLayout = workspaceLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivitySpriteBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivitySpriteBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_sprite, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivitySpriteBinding bind(View rootView) {
        LinearLayout linearLayout = (LinearLayout) rootView;
        int i = R.id.bottom_bar;
        View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.bottom_bar);
        if (viewFindChildViewById != null) {
            ListActionButtonsBinding listActionButtonsBindingBind = ListActionButtonsBinding.bind(viewFindChildViewById);
            i = R.id.fragment_container;
            FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(rootView, R.id.fragment_container);
            if (frameLayout != null) {
                i = R.id.workspace_layout;
                WorkspaceLayout workspaceLayout = (WorkspaceLayout) ViewBindings.findChildViewById(rootView, R.id.workspace_layout);
                if (workspaceLayout != null) {
                    return new ActivitySpriteBinding(linearLayout, linearLayout, listActionButtonsBindingBind, frameLayout, workspaceLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
