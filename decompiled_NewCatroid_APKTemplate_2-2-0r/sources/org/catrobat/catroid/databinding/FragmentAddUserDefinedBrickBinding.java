package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FragmentAddUserDefinedBrickBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FragmentAddUserDefinedBrickBinding implements ViewBinding {
    public final Button buttonAddInput;
    public final Button buttonAddLabel;
    public final ScrollView fragmentAddUserDefinedBrick;
    private final ScrollView rootView;
    public final LinearLayout userBrickSpace;

    private FragmentAddUserDefinedBrickBinding(ScrollView rootView, Button buttonAddInput, Button buttonAddLabel, ScrollView fragmentAddUserDefinedBrick, LinearLayout userBrickSpace) {
        this.rootView = rootView;
        this.buttonAddInput = buttonAddInput;
        this.buttonAddLabel = buttonAddLabel;
        this.fragmentAddUserDefinedBrick = fragmentAddUserDefinedBrick;
        this.userBrickSpace = userBrickSpace;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static FragmentAddUserDefinedBrickBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentAddUserDefinedBrickBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_add_user_defined_brick, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentAddUserDefinedBrickBinding bind(View rootView) {
        int i = R.id.button_add_input;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.button_add_input);
        if (button != null) {
            i = R.id.button_add_label;
            Button button2 = (Button) ViewBindings.findChildViewById(rootView, R.id.button_add_label);
            if (button2 != null) {
                ScrollView scrollView = (ScrollView) rootView;
                i = R.id.user_brick_space;
                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.user_brick_space);
                if (linearLayout != null) {
                    return new FragmentAddUserDefinedBrickBinding(scrollView, button, button2, scrollView, linearLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
