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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ActivityRecyclerBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ActivityRecyclerBinding implements ViewBinding {
    public final ListActionButtonsBinding bottomBar;
    public final FrameLayout fragmentContainer;
    private final LinearLayout rootView;
    public final ToolbarBinding toolbar;
    public final WorkspaceLayout workspaceLayout;

    private ActivityRecyclerBinding(LinearLayout rootView, ListActionButtonsBinding bottomBar, FrameLayout fragmentContainer, ToolbarBinding toolbar, WorkspaceLayout workspaceLayout) {
        this.rootView = rootView;
        this.bottomBar = bottomBar;
        this.fragmentContainer = fragmentContainer;
        this.toolbar = toolbar;
        this.workspaceLayout = workspaceLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityRecyclerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityRecyclerBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_recycler, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityRecyclerBinding bind(View rootView) {
        int i = R.id.bottom_bar;
        View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.bottom_bar);
        if (viewFindChildViewById != null) {
            ListActionButtonsBinding listActionButtonsBindingBind = ListActionButtonsBinding.bind(viewFindChildViewById);
            i = R.id.fragment_container;
            FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(rootView, R.id.fragment_container);
            if (frameLayout != null) {
                i = R.id.toolbar;
                View viewFindChildViewById2 = ViewBindings.findChildViewById(rootView, R.id.toolbar);
                if (viewFindChildViewById2 != null) {
                    ToolbarBinding toolbarBindingBind = ToolbarBinding.bind(viewFindChildViewById2);
                    i = R.id.workspace_layout;
                    WorkspaceLayout workspaceLayout = (WorkspaceLayout) ViewBindings.findChildViewById(rootView, R.id.workspace_layout);
                    if (workspaceLayout != null) {
                        return new ActivityRecyclerBinding((LinearLayout) rootView, listActionButtonsBindingBind, frameLayout, toolbarBindingBind, workspaceLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
