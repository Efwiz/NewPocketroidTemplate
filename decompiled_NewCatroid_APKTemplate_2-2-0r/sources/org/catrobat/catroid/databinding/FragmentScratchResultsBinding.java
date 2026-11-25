package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FragmentScratchResultsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FragmentScratchResultsBinding implements ViewBinding {
    public final RecyclerView recyclerView;
    private final LinearLayout rootView;
    public final SearchView searchViewScratch;

    private FragmentScratchResultsBinding(LinearLayout rootView, RecyclerView recyclerView, SearchView searchViewScratch) {
        this.rootView = rootView;
        this.recyclerView = recyclerView;
        this.searchViewScratch = searchViewScratch;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FragmentScratchResultsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentScratchResultsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_scratch_results, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentScratchResultsBinding bind(View rootView) {
        int i = R.id.recycler_view;
        RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, R.id.recycler_view);
        if (recyclerView != null) {
            i = R.id.search_view_scratch;
            SearchView searchView = (SearchView) ViewBindings.findChildViewById(rootView, R.id.search_view_scratch);
            if (searchView != null) {
                return new FragmentScratchResultsBinding((LinearLayout) rootView, recyclerView, searchView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
