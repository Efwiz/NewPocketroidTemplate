package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ActivityLibraryEditorBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ActivityLibraryEditorBinding implements ViewBinding {
    private final LinearLayout rootView;
    public final TabLayout tabLayout;
    public final MaterialToolbar toolbar;
    public final ViewPager2 viewPager;

    private ActivityLibraryEditorBinding(LinearLayout rootView, TabLayout tabLayout, MaterialToolbar toolbar, ViewPager2 viewPager) {
        this.rootView = rootView;
        this.tabLayout = tabLayout;
        this.toolbar = toolbar;
        this.viewPager = viewPager;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityLibraryEditorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityLibraryEditorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_library_editor, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityLibraryEditorBinding bind(View rootView) {
        int i = R.id.tab_layout;
        TabLayout tabLayout = (TabLayout) ViewBindings.findChildViewById(rootView, R.id.tab_layout);
        if (tabLayout != null) {
            i = R.id.toolbar;
            MaterialToolbar materialToolbar = (MaterialToolbar) ViewBindings.findChildViewById(rootView, R.id.toolbar);
            if (materialToolbar != null) {
                i = R.id.view_pager;
                ViewPager2 viewPager2 = (ViewPager2) ViewBindings.findChildViewById(rootView, R.id.view_pager);
                if (viewPager2 != null) {
                    return new ActivityLibraryEditorBinding((LinearLayout) rootView, tabLayout, materialToolbar, viewPager2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
