package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickAssertUserlistsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickAssertUserlistsBinding implements ViewBinding {
    public final Spinner brickAssertListsActual;
    public final Spinner brickAssertListsExpected;
    public final BrickLayout brickAssertUserlistsLayout;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickAssertUserlistsBinding(LinearLayout rootView, Spinner brickAssertListsActual, Spinner brickAssertListsExpected, BrickLayout brickAssertUserlistsLayout, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickAssertListsActual = brickAssertListsActual;
        this.brickAssertListsExpected = brickAssertListsExpected;
        this.brickAssertUserlistsLayout = brickAssertUserlistsLayout;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickAssertUserlistsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickAssertUserlistsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_assert_userlists, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickAssertUserlistsBinding bind(View rootView) {
        int i = R.id.brick_assert_lists_actual;
        Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_assert_lists_actual);
        if (spinner != null) {
            i = R.id.brick_assert_lists_expected;
            Spinner spinner2 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_assert_lists_expected);
            if (spinner2 != null) {
                i = R.id.brick_assert_userlists_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_assert_userlists_layout);
                if (brickLayout != null) {
                    i = R.id.brick_checkbox;
                    CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                    if (checkBox != null) {
                        return new BrickAssertUserlistsBinding((LinearLayout) rootView, spinner, spinner2, brickLayout, checkBox);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
