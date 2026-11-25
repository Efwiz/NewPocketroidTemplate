package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FragmentBrickAddBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FragmentBrickAddBinding implements ViewBinding {
    public final LinearLayout addBrickFragmentList;
    public final ListView list;
    private final LinearLayout rootView;

    private FragmentBrickAddBinding(LinearLayout rootView, LinearLayout addBrickFragmentList, ListView list) {
        this.rootView = rootView;
        this.addBrickFragmentList = addBrickFragmentList;
        this.list = list;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FragmentBrickAddBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentBrickAddBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_brick_add, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentBrickAddBinding bind(View rootView) {
        LinearLayout linearLayout = (LinearLayout) rootView;
        ListView listView = (ListView) ViewBindings.findChildViewById(rootView, android.R.id.list);
        if (listView != null) {
            return new FragmentBrickAddBinding(linearLayout, linearLayout, listView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(android.R.id.list)));
    }
}
