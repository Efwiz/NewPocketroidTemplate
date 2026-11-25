package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ActivityStandaloneAdvertisingBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ActivityStandaloneAdvertisingBinding implements ViewBinding {
    public final TextView description;
    public final LinearLayout googlePlayStore;
    public final LinearLayout huaweiAppGallery;
    public final TextView playStoreLink;
    public final ImageView pocketCodeImage;
    private final NestedScrollView rootView;
    public final TextView title;
    public final TextView websiteLink;

    private ActivityStandaloneAdvertisingBinding(NestedScrollView rootView, TextView description, LinearLayout googlePlayStore, LinearLayout huaweiAppGallery, TextView playStoreLink, ImageView pocketCodeImage, TextView title, TextView websiteLink) {
        this.rootView = rootView;
        this.description = description;
        this.googlePlayStore = googlePlayStore;
        this.huaweiAppGallery = huaweiAppGallery;
        this.playStoreLink = playStoreLink;
        this.pocketCodeImage = pocketCodeImage;
        this.title = title;
        this.websiteLink = websiteLink;
    }

    @Override // androidx.viewbinding.ViewBinding
    public NestedScrollView getRoot() {
        return this.rootView;
    }

    public static ActivityStandaloneAdvertisingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityStandaloneAdvertisingBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_standalone_advertising, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityStandaloneAdvertisingBinding bind(View rootView) {
        int i = R.id.description;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.description);
        if (textView != null) {
            i = R.id.googlePlayStore;
            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.googlePlayStore);
            if (linearLayout != null) {
                i = R.id.huaweiAppGallery;
                LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.huaweiAppGallery);
                if (linearLayout2 != null) {
                    i = R.id.playStore_link;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.playStore_link);
                    if (textView2 != null) {
                        i = R.id.pocket_code_image;
                        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.pocket_code_image);
                        if (imageView != null) {
                            i = R.id.title;
                            TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.title);
                            if (textView3 != null) {
                                i = R.id.website_link;
                                TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.website_link);
                                if (textView4 != null) {
                                    return new ActivityStandaloneAdvertisingBinding((NestedScrollView) rootView, textView, linearLayout, linearLayout2, textView2, imageView, textView3, textView4);
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
