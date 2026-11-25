package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ProjectCategoryItemViewBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ProjectCategoryItemViewBinding implements ViewBinding {
    public final RecyclerView categoryRecyclerView;
    public final TextView categoryTitle;
    private final LinearLayout rootView;

    private ProjectCategoryItemViewBinding(LinearLayout rootView, RecyclerView categoryRecyclerView, TextView categoryTitle) {
        this.rootView = rootView;
        this.categoryRecyclerView = categoryRecyclerView;
        this.categoryTitle = categoryTitle;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ProjectCategoryItemViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ProjectCategoryItemViewBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.project_category_item_view, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ProjectCategoryItemViewBinding bind(View rootView) {
        int i = R.id.categoryRecyclerView;
        RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, R.id.categoryRecyclerView);
        if (recyclerView != null) {
            i = R.id.categoryTitle;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.categoryTitle);
            if (textView != null) {
                return new ProjectCategoryItemViewBinding((LinearLayout) rootView, recyclerView, textView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
