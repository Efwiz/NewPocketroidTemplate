package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FragmentScratchProgramsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FragmentScratchProgramsBinding implements ViewBinding {
    public final LinearLayout programsFinished;
    public final LinearLayout programsInProgress;
    public final RecyclerView recyclerViewFinished;
    public final RecyclerView recyclerViewInProgress;
    private final RelativeLayout rootView;

    private FragmentScratchProgramsBinding(RelativeLayout rootView, LinearLayout programsFinished, LinearLayout programsInProgress, RecyclerView recyclerViewFinished, RecyclerView recyclerViewInProgress) {
        this.rootView = rootView;
        this.programsFinished = programsFinished;
        this.programsInProgress = programsInProgress;
        this.recyclerViewFinished = recyclerViewFinished;
        this.recyclerViewInProgress = recyclerViewInProgress;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static FragmentScratchProgramsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentScratchProgramsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_scratch_programs, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentScratchProgramsBinding bind(View rootView) {
        int i = R.id.programs_finished;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.programs_finished);
        if (linearLayout != null) {
            i = R.id.programs_in_progress;
            LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.programs_in_progress);
            if (linearLayout2 != null) {
                i = R.id.recycler_view_finished;
                RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, R.id.recycler_view_finished);
                if (recyclerView != null) {
                    i = R.id.recycler_view_in_progress;
                    RecyclerView recyclerView2 = (RecyclerView) ViewBindings.findChildViewById(rootView, R.id.recycler_view_in_progress);
                    if (recyclerView2 != null) {
                        return new FragmentScratchProgramsBinding((RelativeLayout) rootView, linearLayout, linearLayout2, recyclerView, recyclerView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
