package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FragmentAddUserDataToUserBrickBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FragmentAddUserDataToUserBrickBinding implements ViewBinding {
    public final LinearLayout bottomConstraint;
    public final TextView brickUserDefinedAddUserDataDescription;
    public final ScrollView fragmentAddUserDataToUserBrick;
    private final ScrollView rootView;
    public final LinearLayout userBrickSpace;
    public final TextInputEditText userDataUserBrickEditField;
    public final TextInputLayout userDataUserBrickTextLayout;

    private FragmentAddUserDataToUserBrickBinding(ScrollView rootView, LinearLayout bottomConstraint, TextView brickUserDefinedAddUserDataDescription, ScrollView fragmentAddUserDataToUserBrick, LinearLayout userBrickSpace, TextInputEditText userDataUserBrickEditField, TextInputLayout userDataUserBrickTextLayout) {
        this.rootView = rootView;
        this.bottomConstraint = bottomConstraint;
        this.brickUserDefinedAddUserDataDescription = brickUserDefinedAddUserDataDescription;
        this.fragmentAddUserDataToUserBrick = fragmentAddUserDataToUserBrick;
        this.userBrickSpace = userBrickSpace;
        this.userDataUserBrickEditField = userDataUserBrickEditField;
        this.userDataUserBrickTextLayout = userDataUserBrickTextLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static FragmentAddUserDataToUserBrickBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentAddUserDataToUserBrickBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_add_user_data_to_user_brick, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentAddUserDataToUserBrickBinding bind(View rootView) {
        int i = R.id.bottom_constraint;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.bottom_constraint);
        if (linearLayout != null) {
            i = R.id.brick_user_defined_add_user_data_description;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_user_defined_add_user_data_description);
            if (textView != null) {
                ScrollView scrollView = (ScrollView) rootView;
                i = R.id.user_brick_space;
                LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.user_brick_space);
                if (linearLayout2 != null) {
                    i = R.id.user_data_user_brick_edit_field;
                    TextInputEditText textInputEditText = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.user_data_user_brick_edit_field);
                    if (textInputEditText != null) {
                        i = R.id.user_data_user_brick_text_layout;
                        TextInputLayout textInputLayout = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.user_data_user_brick_text_layout);
                        if (textInputLayout != null) {
                            return new FragmentAddUserDataToUserBrickBinding(scrollView, linearLayout, textView, scrollView, linearLayout2, textInputEditText, textInputLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
