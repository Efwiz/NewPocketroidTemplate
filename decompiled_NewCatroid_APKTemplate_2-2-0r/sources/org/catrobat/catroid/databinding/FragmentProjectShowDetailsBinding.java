package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FragmentProjectShowDetailsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FragmentProjectShowDetailsBinding implements ViewBinding {
    public final TextView authorTitle;
    public final TextView authorValue;
    public final TextView descriptionTitle;
    public final TextView descriptionValue;
    public final ImageView image;
    public final TextView lastAccessTitle;
    public final TextView lastAccessValue;
    public final TextView modeTitle;
    public final TextView modeValue;
    public final LinearLayout moreDetailsLayout;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public final TextView f572name;
    public final TextView notesAndCreditsTitle;
    public final TextView notesAndCreditsValue;
    public final TextView remixOfTitle;
    public final TextView remixOfValue;
    private final LinearLayout rootView;
    public final TextView screenSizeTitle;
    public final TextView screenSizeValue;
    public final TextView sizeTitle;
    public final TextView sizeValue;

    private FragmentProjectShowDetailsBinding(LinearLayout rootView, TextView authorTitle, TextView authorValue, TextView descriptionTitle, TextView descriptionValue, ImageView image, TextView lastAccessTitle, TextView lastAccessValue, TextView modeTitle, TextView modeValue, LinearLayout moreDetailsLayout, TextView name2, TextView notesAndCreditsTitle, TextView notesAndCreditsValue, TextView remixOfTitle, TextView remixOfValue, TextView screenSizeTitle, TextView screenSizeValue, TextView sizeTitle, TextView sizeValue) {
        this.rootView = rootView;
        this.authorTitle = authorTitle;
        this.authorValue = authorValue;
        this.descriptionTitle = descriptionTitle;
        this.descriptionValue = descriptionValue;
        this.image = image;
        this.lastAccessTitle = lastAccessTitle;
        this.lastAccessValue = lastAccessValue;
        this.modeTitle = modeTitle;
        this.modeValue = modeValue;
        this.moreDetailsLayout = moreDetailsLayout;
        this.f572name = name2;
        this.notesAndCreditsTitle = notesAndCreditsTitle;
        this.notesAndCreditsValue = notesAndCreditsValue;
        this.remixOfTitle = remixOfTitle;
        this.remixOfValue = remixOfValue;
        this.screenSizeTitle = screenSizeTitle;
        this.screenSizeValue = screenSizeValue;
        this.sizeTitle = sizeTitle;
        this.sizeValue = sizeValue;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FragmentProjectShowDetailsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentProjectShowDetailsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_project_show_details, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentProjectShowDetailsBinding bind(View rootView) {
        int i = R.id.author_title;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.author_title);
        if (textView != null) {
            i = R.id.author_value;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.author_value);
            if (textView2 != null) {
                i = R.id.description_title;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.description_title);
                if (textView3 != null) {
                    i = R.id.description_value;
                    TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.description_value);
                    if (textView4 != null) {
                        i = R.id.image;
                        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.image);
                        if (imageView != null) {
                            i = R.id.last_access_title;
                            TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.last_access_title);
                            if (textView5 != null) {
                                i = R.id.last_access_value;
                                TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.last_access_value);
                                if (textView6 != null) {
                                    i = R.id.mode_title;
                                    TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.mode_title);
                                    if (textView7 != null) {
                                        i = R.id.mode_value;
                                        TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.mode_value);
                                        if (textView8 != null) {
                                            LinearLayout linearLayout = (LinearLayout) rootView;
                                            i = R.id.f420name;
                                            TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.f420name);
                                            if (textView9 != null) {
                                                i = R.id.notes_and_credits_title;
                                                TextView textView10 = (TextView) ViewBindings.findChildViewById(rootView, R.id.notes_and_credits_title);
                                                if (textView10 != null) {
                                                    i = R.id.notes_and_credits_value;
                                                    TextView textView11 = (TextView) ViewBindings.findChildViewById(rootView, R.id.notes_and_credits_value);
                                                    if (textView11 != null) {
                                                        i = R.id.remix_of_title;
                                                        TextView textView12 = (TextView) ViewBindings.findChildViewById(rootView, R.id.remix_of_title);
                                                        if (textView12 != null) {
                                                            i = R.id.remix_of_value;
                                                            TextView textView13 = (TextView) ViewBindings.findChildViewById(rootView, R.id.remix_of_value);
                                                            if (textView13 != null) {
                                                                i = R.id.screen_size_title;
                                                                TextView textView14 = (TextView) ViewBindings.findChildViewById(rootView, R.id.screen_size_title);
                                                                if (textView14 != null) {
                                                                    i = R.id.screen_size_value;
                                                                    TextView textView15 = (TextView) ViewBindings.findChildViewById(rootView, R.id.screen_size_value);
                                                                    if (textView15 != null) {
                                                                        i = R.id.size_title;
                                                                        TextView textView16 = (TextView) ViewBindings.findChildViewById(rootView, R.id.size_title);
                                                                        if (textView16 != null) {
                                                                            i = R.id.size_value;
                                                                            TextView textView17 = (TextView) ViewBindings.findChildViewById(rootView, R.id.size_value);
                                                                            if (textView17 != null) {
                                                                                return new FragmentProjectShowDetailsBinding(linearLayout, textView, textView2, textView3, textView4, imageView, textView5, textView6, textView7, textView8, linearLayout, textView9, textView10, textView11, textView12, textView13, textView14, textView15, textView16, textView17);
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
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
