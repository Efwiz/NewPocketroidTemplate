package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/PlaceableSpriteViewBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class PlaceableSpriteViewBinding implements ViewBinding {
    public final ImageView handleScaleRotate;
    private final FrameLayout rootView;
    public final View spriteBorder;
    public final ImageView spriteImageView;

    private PlaceableSpriteViewBinding(FrameLayout rootView, ImageView handleScaleRotate, View spriteBorder, ImageView spriteImageView) {
        this.rootView = rootView;
        this.handleScaleRotate = handleScaleRotate;
        this.spriteBorder = spriteBorder;
        this.spriteImageView = spriteImageView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public FrameLayout getRoot() {
        return this.rootView;
    }

    public static PlaceableSpriteViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static PlaceableSpriteViewBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.placeable_sprite_view, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static PlaceableSpriteViewBinding bind(View rootView) {
        int i = R.id.handle_scale_rotate;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.handle_scale_rotate);
        if (imageView != null) {
            i = R.id.sprite_border;
            View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.sprite_border);
            if (viewFindChildViewById != null) {
                i = R.id.sprite_image_view;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.sprite_image_view);
                if (imageView2 != null) {
                    return new PlaceableSpriteViewBinding((FrameLayout) rootView, imageView, viewFindChildViewById, imageView2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
