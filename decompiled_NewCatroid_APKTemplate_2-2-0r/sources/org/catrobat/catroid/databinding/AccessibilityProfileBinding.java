package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/AccessibilityProfileBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class AccessibilityProfileBinding implements ViewBinding {
    public final ImageView imageView;
    public final RadioButton radioButton;
    private final LinearLayout rootView;
    public final TextView subtitleView;
    public final TextView titleView;

    private AccessibilityProfileBinding(LinearLayout rootView, ImageView imageView, RadioButton radioButton, TextView subtitleView, TextView titleView) {
        this.rootView = rootView;
        this.imageView = imageView;
        this.radioButton = radioButton;
        this.subtitleView = subtitleView;
        this.titleView = titleView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static AccessibilityProfileBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static AccessibilityProfileBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.accessibility_profile, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static AccessibilityProfileBinding bind(View rootView) {
        int i = R.id.image_view;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.image_view);
        if (imageView != null) {
            i = R.id.radio_button;
            RadioButton radioButton = (RadioButton) ViewBindings.findChildViewById(rootView, R.id.radio_button);
            if (radioButton != null) {
                i = R.id.subtitle_view;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.subtitle_view);
                if (textView != null) {
                    i = R.id.title_view;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.title_view);
                    if (textView2 != null) {
                        return new AccessibilityProfileBinding((LinearLayout) rootView, imageView, radioButton, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
