package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewbinding.ViewBinding;
import com.google.android.material.tabs.TabLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/LayoutTabsSpriteActivityBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class LayoutTabsSpriteActivityBinding implements ViewBinding {
    private final TabLayout rootView;
    public final TabLayout tabLayout;

    private LayoutTabsSpriteActivityBinding(TabLayout rootView, TabLayout tabLayout) {
        this.rootView = rootView;
        this.tabLayout = tabLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public TabLayout getRoot() {
        return this.rootView;
    }

    public static LayoutTabsSpriteActivityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static LayoutTabsSpriteActivityBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.layout_tabs_sprite_activity, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static LayoutTabsSpriteActivityBinding bind(View rootView) {
        if (rootView == null) {
            throw new NullPointerException("rootView");
        }
        TabLayout tabLayout = (TabLayout) rootView;
        return new LayoutTabsSpriteActivityBinding(tabLayout, tabLayout);
    }
}
