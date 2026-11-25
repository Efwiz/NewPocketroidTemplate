package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FragmentProjectFilesBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FragmentProjectFilesBinding implements ViewBinding {
    public final FloatingActionButton projectFilesAdd;
    public final FloatingActionButton projectFilesCmd;
    public final RecyclerView recyclerViewFiles;
    private final RelativeLayout rootView;

    private FragmentProjectFilesBinding(RelativeLayout rootView, FloatingActionButton projectFilesAdd, FloatingActionButton projectFilesCmd, RecyclerView recyclerViewFiles) {
        this.rootView = rootView;
        this.projectFilesAdd = projectFilesAdd;
        this.projectFilesCmd = projectFilesCmd;
        this.recyclerViewFiles = recyclerViewFiles;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static FragmentProjectFilesBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentProjectFilesBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_project_files, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentProjectFilesBinding bind(View rootView) {
        int i = R.id.project_files_add;
        FloatingActionButton floatingActionButton = (FloatingActionButton) ViewBindings.findChildViewById(rootView, R.id.project_files_add);
        if (floatingActionButton != null) {
            i = R.id.project_files_cmd;
            FloatingActionButton floatingActionButton2 = (FloatingActionButton) ViewBindings.findChildViewById(rootView, R.id.project_files_cmd);
            if (floatingActionButton2 != null) {
                i = R.id.recycler_view_files;
                RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, R.id.recycler_view_files);
                if (recyclerView != null) {
                    return new FragmentProjectFilesBinding((RelativeLayout) rootView, floatingActionButton, floatingActionButton2, recyclerView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
