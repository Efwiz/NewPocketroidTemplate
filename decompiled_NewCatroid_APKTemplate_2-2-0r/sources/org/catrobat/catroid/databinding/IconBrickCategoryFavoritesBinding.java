package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/IconBrickCategoryFavoritesBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class IconBrickCategoryFavoritesBinding implements ViewBinding {
    private final LinearLayout rootView;

    private IconBrickCategoryFavoritesBinding(LinearLayout rootView) {
        this.rootView = rootView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static IconBrickCategoryFavoritesBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static IconBrickCategoryFavoritesBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.icon_brick_category_favorites, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static IconBrickCategoryFavoritesBinding bind(View rootView) {
        if (rootView == null) {
            throw new NullPointerException("rootView");
        }
        return new IconBrickCategoryFavoritesBinding((LinearLayout) rootView);
    }
}
