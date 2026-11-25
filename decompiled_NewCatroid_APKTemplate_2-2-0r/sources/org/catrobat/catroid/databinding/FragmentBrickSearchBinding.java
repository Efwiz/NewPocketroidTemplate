package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FragmentBrickSearchBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FragmentBrickSearchBinding implements ViewBinding {
    public final RelativeLayout fragmentBrickSearch;
    public final ListView list;
    public final TextView noRecentlyUsed;
    public final ProgressBar progressBar;
    public final TextView recentUsedHeader;
    private final RelativeLayout rootView;

    private FragmentBrickSearchBinding(RelativeLayout rootView, RelativeLayout fragmentBrickSearch, ListView list, TextView noRecentlyUsed, ProgressBar progressBar, TextView recentUsedHeader) {
        this.rootView = rootView;
        this.fragmentBrickSearch = fragmentBrickSearch;
        this.list = list;
        this.noRecentlyUsed = noRecentlyUsed;
        this.progressBar = progressBar;
        this.recentUsedHeader = recentUsedHeader;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static FragmentBrickSearchBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentBrickSearchBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_brick_search, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentBrickSearchBinding bind(View rootView) {
        RelativeLayout relativeLayout = (RelativeLayout) rootView;
        int i = android.R.id.list;
        ListView listView = (ListView) ViewBindings.findChildViewById(rootView, android.R.id.list);
        if (listView != null) {
            i = R.id.no_recently_used;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.no_recently_used);
            if (textView != null) {
                i = R.id.progress_bar;
                ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(rootView, R.id.progress_bar);
                if (progressBar != null) {
                    i = R.id.recent_used_header;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.recent_used_header);
                    if (textView2 != null) {
                        return new FragmentBrickSearchBinding(relativeLayout, relativeLayout, listView, textView, progressBar, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
