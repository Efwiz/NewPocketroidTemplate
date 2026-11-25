package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPlayNoteForBeatsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPlayNoteForBeatsBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickPlayNoteForBeatsBeatsEditText;
    public final BrickLayout brickPlayNoteForBeatsLayout;
    public final TextView brickPlayNoteForBeatsNoteEditText;
    private final LinearLayout rootView;

    private BrickPlayNoteForBeatsBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickPlayNoteForBeatsBeatsEditText, BrickLayout brickPlayNoteForBeatsLayout, TextView brickPlayNoteForBeatsNoteEditText) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickPlayNoteForBeatsBeatsEditText = brickPlayNoteForBeatsBeatsEditText;
        this.brickPlayNoteForBeatsLayout = brickPlayNoteForBeatsLayout;
        this.brickPlayNoteForBeatsNoteEditText = brickPlayNoteForBeatsNoteEditText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPlayNoteForBeatsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPlayNoteForBeatsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_play_note_for_beats, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPlayNoteForBeatsBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_play_note_for_beats_beats_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_play_note_for_beats_beats_edit_text);
            if (textView != null) {
                i = R.id.brick_play_note_for_beats_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_play_note_for_beats_layout);
                if (brickLayout != null) {
                    i = R.id.brick_play_note_for_beats_note_edit_text;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_play_note_for_beats_note_edit_text);
                    if (textView2 != null) {
                        return new BrickPlayNoteForBeatsBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
