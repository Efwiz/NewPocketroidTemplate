package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FragmentUserDefinedBrickListBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FragmentUserDefinedBrickListBinding implements ViewBinding {
    public final FloatingActionButton buttonAddUserBrick;
    public final LinearLayout empty;
    public final RelativeLayout fragmentUserDefinedBrickList;
    public final ListView list;
    private final RelativeLayout rootView;

    private FragmentUserDefinedBrickListBinding(RelativeLayout rootView, FloatingActionButton buttonAddUserBrick, LinearLayout empty, RelativeLayout fragmentUserDefinedBrickList, ListView list) {
        this.rootView = rootView;
        this.buttonAddUserBrick = buttonAddUserBrick;
        this.empty = empty;
        this.fragmentUserDefinedBrickList = fragmentUserDefinedBrickList;
        this.list = list;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static FragmentUserDefinedBrickListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentUserDefinedBrickListBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_user_defined_brick_list, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentUserDefinedBrickListBinding bind(View rootView) {
        int i = R.id.button_add_user_brick;
        FloatingActionButton floatingActionButton = (FloatingActionButton) ViewBindings.findChildViewById(rootView, R.id.button_add_user_brick);
        if (floatingActionButton != null) {
            i = android.R.id.empty;
            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, android.R.id.empty);
            if (linearLayout != null) {
                RelativeLayout relativeLayout = (RelativeLayout) rootView;
                i = android.R.id.list;
                ListView listView = (ListView) ViewBindings.findChildViewById(rootView, android.R.id.list);
                if (listView != null) {
                    return new FragmentUserDefinedBrickListBinding(relativeLayout, floatingActionButton, linearLayout, relativeLayout, listView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
