package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FragmentBricksEditorBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FragmentBricksEditorBinding implements ViewBinding {
    public final RecyclerView bricksRecyclerView;
    public final FloatingActionButton fabAddBrick;
    private final CoordinatorLayout rootView;

    private FragmentBricksEditorBinding(CoordinatorLayout rootView, RecyclerView bricksRecyclerView, FloatingActionButton fabAddBrick) {
        this.rootView = rootView;
        this.bricksRecyclerView = bricksRecyclerView;
        this.fabAddBrick = fabAddBrick;
    }

    @Override // androidx.viewbinding.ViewBinding
    public CoordinatorLayout getRoot() {
        return this.rootView;
    }

    public static FragmentBricksEditorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentBricksEditorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_bricks_editor, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentBricksEditorBinding bind(View rootView) {
        int i = R.id.bricks_recycler_view;
        RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, R.id.bricks_recycler_view);
        if (recyclerView != null) {
            i = R.id.fab_add_brick;
            FloatingActionButton floatingActionButton = (FloatingActionButton) ViewBindings.findChildViewById(rootView, R.id.fab_add_brick);
            if (floatingActionButton != null) {
                return new FragmentBricksEditorBinding((CoordinatorLayout) rootView, recyclerView, floatingActionButton);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
