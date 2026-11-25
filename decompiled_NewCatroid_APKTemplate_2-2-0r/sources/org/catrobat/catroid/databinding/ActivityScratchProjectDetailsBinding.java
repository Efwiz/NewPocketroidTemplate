package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ActivityScratchProjectDetailsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ActivityScratchProjectDetailsBinding implements ViewBinding {
    public final Button convertButton;
    public final TextView creditsTitleView;
    public final TextView creditsView;
    public final TextView dateModifiedView;
    public final TextView dateSharedView;
    public final RelativeLayout datesView;
    public final TextView instructionsView;
    public final TextView ownerView;
    public final TextView privacyWarning;
    public final LinearLayout projectDetailsLayout;
    public final ImageView projectImageView;
    public final TextView projectTitleView;
    public final RecyclerView recyclerViewRemixes;
    public final TextView remixesTitleView;
    private final RelativeLayout rootView;
    public final ToolbarBinding toolbar;

    private ActivityScratchProjectDetailsBinding(RelativeLayout rootView, Button convertButton, TextView creditsTitleView, TextView creditsView, TextView dateModifiedView, TextView dateSharedView, RelativeLayout datesView, TextView instructionsView, TextView ownerView, TextView privacyWarning, LinearLayout projectDetailsLayout, ImageView projectImageView, TextView projectTitleView, RecyclerView recyclerViewRemixes, TextView remixesTitleView, ToolbarBinding toolbar) {
        this.rootView = rootView;
        this.convertButton = convertButton;
        this.creditsTitleView = creditsTitleView;
        this.creditsView = creditsView;
        this.dateModifiedView = dateModifiedView;
        this.dateSharedView = dateSharedView;
        this.datesView = datesView;
        this.instructionsView = instructionsView;
        this.ownerView = ownerView;
        this.privacyWarning = privacyWarning;
        this.projectDetailsLayout = projectDetailsLayout;
        this.projectImageView = projectImageView;
        this.projectTitleView = projectTitleView;
        this.recyclerViewRemixes = recyclerViewRemixes;
        this.remixesTitleView = remixesTitleView;
        this.toolbar = toolbar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static ActivityScratchProjectDetailsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityScratchProjectDetailsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_scratch_project_details, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityScratchProjectDetailsBinding bind(View rootView) {
        int i = R.id.convert_button;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.convert_button);
        if (button != null) {
            i = R.id.credits_title_view;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.credits_title_view);
            if (textView != null) {
                i = R.id.credits_view;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.credits_view);
                if (textView2 != null) {
                    i = R.id.date_modified_view;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.date_modified_view);
                    if (textView3 != null) {
                        i = R.id.date_shared_view;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.date_shared_view);
                        if (textView4 != null) {
                            i = R.id.dates_view;
                            RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(rootView, R.id.dates_view);
                            if (relativeLayout != null) {
                                i = R.id.instructions_view;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.instructions_view);
                                if (textView5 != null) {
                                    i = R.id.owner_view;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.owner_view);
                                    if (textView6 != null) {
                                        i = R.id.privacy_warning;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.privacy_warning);
                                        if (textView7 != null) {
                                            i = R.id.project_details_layout;
                                            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.project_details_layout);
                                            if (linearLayout != null) {
                                                i = R.id.project_image_view;
                                                ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.project_image_view);
                                                if (imageView != null) {
                                                    i = R.id.project_title_view;
                                                    TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.project_title_view);
                                                    if (textView8 != null) {
                                                        i = R.id.recycler_view_remixes;
                                                        RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, R.id.recycler_view_remixes);
                                                        if (recyclerView != null) {
                                                            i = R.id.remixes_title_view;
                                                            TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.remixes_title_view);
                                                            if (textView9 != null) {
                                                                i = R.id.toolbar;
                                                                View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.toolbar);
                                                                if (viewFindChildViewById != null) {
                                                                    return new ActivityScratchProjectDetailsBinding((RelativeLayout) rootView, button, textView, textView2, textView3, textView4, relativeLayout, textView5, textView6, textView7, linearLayout, imageView, textView8, recyclerView, textView9, ToolbarBinding.bind(viewFindChildViewById));
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
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
