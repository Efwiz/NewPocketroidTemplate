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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ViewHolderWithCheckboxBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ViewHolderWithCheckboxBinding implements ViewBinding {
    public final CheckBox checkbox;
    public final TextView detailsView;
    public final ImageView icRipples;
    public final ImageView imageView;
    private final LinearLayout rootView;
    public final ImageButton settingsButton;
    public final TextView titleView;
    public final LinearLayout viewHolderWithCheckbox;

    private ViewHolderWithCheckboxBinding(LinearLayout rootView, CheckBox checkbox, TextView detailsView, ImageView icRipples, ImageView imageView, ImageButton settingsButton, TextView titleView, LinearLayout viewHolderWithCheckbox) {
        this.rootView = rootView;
        this.checkbox = checkbox;
        this.detailsView = detailsView;
        this.icRipples = icRipples;
        this.imageView = imageView;
        this.settingsButton = settingsButton;
        this.titleView = titleView;
        this.viewHolderWithCheckbox = viewHolderWithCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ViewHolderWithCheckboxBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ViewHolderWithCheckboxBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.view_holder_with_checkbox, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ViewHolderWithCheckboxBinding bind(View rootView) {
        int i = R.id.checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.checkbox);
        if (checkBox != null) {
            i = R.id.details_view;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.details_view);
            if (textView != null) {
                i = R.id.ic_ripples;
                ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ic_ripples);
                if (imageView != null) {
                    i = R.id.image_view;
                    ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.image_view);
                    if (imageView2 != null) {
                        i = R.id.settings_button;
                        ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.settings_button);
                        if (imageButton != null) {
                            i = R.id.title_view;
                            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.title_view);
                            if (textView2 != null) {
                                LinearLayout linearLayout = (LinearLayout) rootView;
                                return new ViewHolderWithCheckboxBinding(linearLayout, checkBox, textView, imageView, imageView2, imageButton, textView2, linearLayout);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
