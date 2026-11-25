package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ViewHolderBackgroundSpriteBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ViewHolderBackgroundSpriteBinding implements ViewBinding {
    private final LinearLayout rootView;

    private ViewHolderBackgroundSpriteBinding(LinearLayout rootView) {
        this.rootView = rootView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ViewHolderBackgroundSpriteBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ViewHolderBackgroundSpriteBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.view_holder_background_sprite, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ViewHolderBackgroundSpriteBinding bind(View rootView) {
        if (rootView == null) {
            throw new NullPointerException("rootView");
        }
        return new ViewHolderBackgroundSpriteBinding((LinearLayout) rootView);
    }
}
