package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.card.MaterialCardView;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ProjectPictureListitemBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ProjectPictureListitemBinding implements ViewBinding {
    public final ImageView projectImageView;
    private final MaterialCardView rootView;

    private ProjectPictureListitemBinding(MaterialCardView rootView, ImageView projectImageView) {
        this.rootView = rootView;
        this.projectImageView = projectImageView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public MaterialCardView getRoot() {
        return this.rootView;
    }

    public static ProjectPictureListitemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ProjectPictureListitemBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.project_picture_listitem, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ProjectPictureListitemBinding bind(View rootView) {
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.project_image_view);
        if (imageView != null) {
            return new ProjectPictureListitemBinding((MaterialCardView) rootView, imageView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(R.id.project_image_view)));
    }
}
