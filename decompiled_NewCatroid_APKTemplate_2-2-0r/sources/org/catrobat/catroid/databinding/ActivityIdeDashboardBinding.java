package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ActivityIdeDashboardBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ActivityIdeDashboardBinding implements ViewBinding {
    public final Button btnCloneExisting;
    public final Button btnCreateFork;
    public final LinearLayout loadingOverlay;
    public final ListView projectsList;
    private final FrameLayout rootView;
    public final TextView tvLoadingText;

    private ActivityIdeDashboardBinding(FrameLayout rootView, Button btnCloneExisting, Button btnCreateFork, LinearLayout loadingOverlay, ListView projectsList, TextView tvLoadingText) {
        this.rootView = rootView;
        this.btnCloneExisting = btnCloneExisting;
        this.btnCreateFork = btnCreateFork;
        this.loadingOverlay = loadingOverlay;
        this.projectsList = projectsList;
        this.tvLoadingText = tvLoadingText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public FrameLayout getRoot() {
        return this.rootView;
    }

    public static ActivityIdeDashboardBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityIdeDashboardBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_ide_dashboard, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityIdeDashboardBinding bind(View rootView) {
        int i = R.id.btn_clone_existing;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_clone_existing);
        if (button != null) {
            i = R.id.btn_create_fork;
            Button button2 = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_create_fork);
            if (button2 != null) {
                i = R.id.loading_overlay;
                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.loading_overlay);
                if (linearLayout != null) {
                    i = R.id.projects_list;
                    ListView listView = (ListView) ViewBindings.findChildViewById(rootView, R.id.projects_list);
                    if (listView != null) {
                        i = R.id.tv_loading_text;
                        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tv_loading_text);
                        if (textView != null) {
                            return new ActivityIdeDashboardBinding((FrameLayout) rootView, button, button2, linearLayout, listView, textView);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
