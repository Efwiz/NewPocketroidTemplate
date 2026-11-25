package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ViewHolderSpriteGroupBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ViewHolderSpriteGroupBinding implements ViewBinding {
    public final CheckBox checkbox;
    public final ImageView imageView;
    private final LinearLayout rootView;
    public final ImageButton settingsButton;
    public final TextView titleView;
    public final LinearLayout viewHolderSpriteGroup;

    private ViewHolderSpriteGroupBinding(LinearLayout rootView, CheckBox checkbox, ImageView imageView, ImageButton settingsButton, TextView titleView, LinearLayout viewHolderSpriteGroup) {
        this.rootView = rootView;
        this.checkbox = checkbox;
        this.imageView = imageView;
        this.settingsButton = settingsButton;
        this.titleView = titleView;
        this.viewHolderSpriteGroup = viewHolderSpriteGroup;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ViewHolderSpriteGroupBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ViewHolderSpriteGroupBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.view_holder_sprite_group, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ViewHolderSpriteGroupBinding bind(View rootView) {
        int i = R.id.checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.checkbox);
        if (checkBox != null) {
            i = R.id.image_view;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.image_view);
            if (imageView != null) {
                i = R.id.settings_button;
                ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.settings_button);
                if (imageButton != null) {
                    i = R.id.title_view;
                    TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.title_view);
                    if (textView != null) {
                        LinearLayout linearLayout = (LinearLayout) rootView;
                        return new ViewHolderSpriteGroupBinding(linearLayout, checkBox, imageView, imageButton, textView, linearLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
