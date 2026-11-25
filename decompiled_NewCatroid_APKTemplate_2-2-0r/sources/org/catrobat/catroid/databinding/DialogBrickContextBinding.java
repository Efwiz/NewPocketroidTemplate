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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogBrickContextBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogBrickContextBinding implements ViewBinding {
    public final TextView brickDescription;
    public final FrameLayout brickViewContainer;
    public final View divider2;
    private final LinearLayout rootView;
    public final LinearLayout warningLayout;
    public final TextView warningMessage;
    public final TextView warningTitle;

    private DialogBrickContextBinding(LinearLayout rootView, TextView brickDescription, FrameLayout brickViewContainer, View divider2, LinearLayout warningLayout, TextView warningMessage, TextView warningTitle) {
        this.rootView = rootView;
        this.brickDescription = brickDescription;
        this.brickViewContainer = brickViewContainer;
        this.divider2 = divider2;
        this.warningLayout = warningLayout;
        this.warningMessage = warningMessage;
        this.warningTitle = warningTitle;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogBrickContextBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogBrickContextBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_brick_context, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogBrickContextBinding bind(View rootView) {
        int i = R.id.brick_description;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_description);
        if (textView != null) {
            i = R.id.brick_view_container;
            FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(rootView, R.id.brick_view_container);
            if (frameLayout != null) {
                i = R.id.divider2;
                View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.divider2);
                if (viewFindChildViewById != null) {
                    i = R.id.warning_layout;
                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.warning_layout);
                    if (linearLayout != null) {
                        i = R.id.warning_message;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.warning_message);
                        if (textView2 != null) {
                            i = R.id.warning_title;
                            TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.warning_title);
                            if (textView3 != null) {
                                return new DialogBrickContextBinding((LinearLayout) rootView, textView, frameLayout, viewFindChildViewById, linearLayout, textView2, textView3);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
