package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ScratchSocialBarBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ScratchSocialBarBinding implements ViewBinding {
    private final RelativeLayout rootView;
    public final ImageView scratchProjectFavoritesIcon;
    public final TextView scratchProjectFavoritesText;
    public final ImageView scratchProjectLovesIcon;
    public final TextView scratchProjectLovesText;
    public final ImageView scratchProjectViewsIcon;
    public final TextView scratchProjectViewsText;
    public final RelativeLayout scratchShareBar;

    private ScratchSocialBarBinding(RelativeLayout rootView, ImageView scratchProjectFavoritesIcon, TextView scratchProjectFavoritesText, ImageView scratchProjectLovesIcon, TextView scratchProjectLovesText, ImageView scratchProjectViewsIcon, TextView scratchProjectViewsText, RelativeLayout scratchShareBar) {
        this.rootView = rootView;
        this.scratchProjectFavoritesIcon = scratchProjectFavoritesIcon;
        this.scratchProjectFavoritesText = scratchProjectFavoritesText;
        this.scratchProjectLovesIcon = scratchProjectLovesIcon;
        this.scratchProjectLovesText = scratchProjectLovesText;
        this.scratchProjectViewsIcon = scratchProjectViewsIcon;
        this.scratchProjectViewsText = scratchProjectViewsText;
        this.scratchShareBar = scratchShareBar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static ScratchSocialBarBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ScratchSocialBarBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.scratch_social_bar, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ScratchSocialBarBinding bind(View rootView) {
        int i = R.id.scratch_project_favorites_icon;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.scratch_project_favorites_icon);
        if (imageView != null) {
            i = R.id.scratch_project_favorites_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.scratch_project_favorites_text);
            if (textView != null) {
                i = R.id.scratch_project_loves_icon;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.scratch_project_loves_icon);
                if (imageView2 != null) {
                    i = R.id.scratch_project_loves_text;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.scratch_project_loves_text);
                    if (textView2 != null) {
                        i = R.id.scratch_project_views_icon;
                        ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.scratch_project_views_icon);
                        if (imageView3 != null) {
                            i = R.id.scratch_project_views_text;
                            TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.scratch_project_views_text);
                            if (textView3 != null) {
                                RelativeLayout relativeLayout = (RelativeLayout) rootView;
                                return new ScratchSocialBarBinding(relativeLayout, imageView, textView, imageView2, textView2, imageView3, textView3, relativeLayout);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
