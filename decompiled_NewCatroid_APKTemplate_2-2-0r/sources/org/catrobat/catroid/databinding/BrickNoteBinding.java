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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickNoteBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickNoteBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final EditText brickNoteEditText;
    public final BrickLayout brickNoteLayout;
    private final LinearLayout rootView;

    private BrickNoteBinding(LinearLayout rootView, CheckBox brickCheckbox, EditText brickNoteEditText, BrickLayout brickNoteLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickNoteEditText = brickNoteEditText;
        this.brickNoteLayout = brickNoteLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickNoteBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickNoteBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_note, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickNoteBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_note_edit_text;
            EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.brick_note_edit_text);
            if (editText != null) {
                i = R.id.brick_note_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_note_layout);
                if (brickLayout != null) {
                    return new BrickNoteBinding((LinearLayout) rootView, checkBox, editText, brickLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
