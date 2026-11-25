package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ViewHolderBackgroundBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ViewHolderBackgroundBinding implements ViewBinding {
    public final CheckBox checkbox;
    public final TextView detailsView;
    public final ImageView imageView;
    private final LinearLayout rootView;
    public final TextView titleView;

    private ViewHolderBackgroundBinding(LinearLayout rootView, CheckBox checkbox, TextView detailsView, ImageView imageView, TextView titleView) {
        this.rootView = rootView;
        this.checkbox = checkbox;
        this.detailsView = detailsView;
        this.imageView = imageView;
        this.titleView = titleView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ViewHolderBackgroundBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ViewHolderBackgroundBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.view_holder_background, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ViewHolderBackgroundBinding bind(View rootView) {
        int i = R.id.checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.checkbox);
        if (checkBox != null) {
            i = R.id.details_view;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.details_view);
            if (textView != null) {
                i = R.id.image_view;
                ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.image_view);
                if (imageView != null) {
                    i = R.id.title_view;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.title_view);
                    if (textView2 != null) {
                        return new ViewHolderBackgroundBinding((LinearLayout) rootView, checkBox, textView, imageView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
