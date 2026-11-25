package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ActivityScratchConverterBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ActivityScratchConverterBinding implements ViewBinding {
    public final LinearLayout bottomBar;
    public final TextView detailsView;
    public final FrameLayout fragmentContainer;
    private final LinearLayout rootView;
    public final TextView titleView;

    private ActivityScratchConverterBinding(LinearLayout rootView, LinearLayout bottomBar, TextView detailsView, FrameLayout fragmentContainer, TextView titleView) {
        this.rootView = rootView;
        this.bottomBar = bottomBar;
        this.detailsView = detailsView;
        this.fragmentContainer = fragmentContainer;
        this.titleView = titleView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityScratchConverterBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityScratchConverterBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_scratch_converter, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityScratchConverterBinding bind(View rootView) {
        int i = R.id.bottom_bar;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.bottom_bar);
        if (linearLayout != null) {
            i = R.id.details_view;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.details_view);
            if (textView != null) {
                i = R.id.fragment_container;
                FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(rootView, R.id.fragment_container);
                if (frameLayout != null) {
                    i = R.id.title_view;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.title_view);
                    if (textView2 != null) {
                        return new ActivityScratchConverterBinding((LinearLayout) rootView, linearLayout, textView, frameLayout, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
