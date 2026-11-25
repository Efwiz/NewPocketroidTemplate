package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickThinkBubbleBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickThinkBubbleBinding implements ViewBinding {
    public final EditText brickBubbleEditText;
    public final CheckBox brickCheckbox;
    public final BrickLayout brickThinkBubbleLayout;
    private final LinearLayout rootView;

    private BrickThinkBubbleBinding(LinearLayout rootView, EditText brickBubbleEditText, CheckBox brickCheckbox, BrickLayout brickThinkBubbleLayout) {
        this.rootView = rootView;
        this.brickBubbleEditText = brickBubbleEditText;
        this.brickCheckbox = brickCheckbox;
        this.brickThinkBubbleLayout = brickThinkBubbleLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickThinkBubbleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickThinkBubbleBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_think_bubble, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickThinkBubbleBinding bind(View rootView) {
        int i = R.id.brick_bubble_edit_text;
        EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.brick_bubble_edit_text);
        if (editText != null) {
            i = R.id.brick_checkbox;
            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
            if (checkBox != null) {
                i = R.id.brick_think_bubble_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_think_bubble_layout);
                if (brickLayout != null) {
                    return new BrickThinkBubbleBinding((LinearLayout) rootView, editText, checkBox, brickLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
