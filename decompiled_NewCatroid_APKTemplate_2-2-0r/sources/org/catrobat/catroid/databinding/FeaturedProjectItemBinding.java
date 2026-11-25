package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FeaturedProjectItemBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FeaturedProjectItemBinding implements ViewBinding {
    public final ImageView featuredProjectIV;
    private final LinearLayout rootView;

    private FeaturedProjectItemBinding(LinearLayout rootView, ImageView featuredProjectIV) {
        this.rootView = rootView;
        this.featuredProjectIV = featuredProjectIV;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FeaturedProjectItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FeaturedProjectItemBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.featured_project_item, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FeaturedProjectItemBinding bind(View rootView) {
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.featuredProjectIV);
        if (imageView != null) {
            return new FeaturedProjectItemBinding((LinearLayout) rootView, imageView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(R.id.featuredProjectIV)));
    }
}
