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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPlaySpritesheetBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPlaySpritesheetBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickPlaySpritesheetCols;
    public final TextView brickPlaySpritesheetFramesCount;
    public final BrickLayout brickPlaySpritesheetLayout;
    public final TextView brickPlaySpritesheetRows;
    public final TextView brickPlaySpritesheetSelectedRow;
    public final TextView brickPlaySpritesheetSpeedVal;
    private final LinearLayout rootView;

    private BrickPlaySpritesheetBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickPlaySpritesheetCols, TextView brickPlaySpritesheetFramesCount, BrickLayout brickPlaySpritesheetLayout, TextView brickPlaySpritesheetRows, TextView brickPlaySpritesheetSelectedRow, TextView brickPlaySpritesheetSpeedVal) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickPlaySpritesheetCols = brickPlaySpritesheetCols;
        this.brickPlaySpritesheetFramesCount = brickPlaySpritesheetFramesCount;
        this.brickPlaySpritesheetLayout = brickPlaySpritesheetLayout;
        this.brickPlaySpritesheetRows = brickPlaySpritesheetRows;
        this.brickPlaySpritesheetSelectedRow = brickPlaySpritesheetSelectedRow;
        this.brickPlaySpritesheetSpeedVal = brickPlaySpritesheetSpeedVal;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPlaySpritesheetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPlaySpritesheetBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_play_spritesheet, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPlaySpritesheetBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_play_spritesheet_cols;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_play_spritesheet_cols);
            if (textView != null) {
                i = R.id.brick_play_spritesheet_frames_count;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_play_spritesheet_frames_count);
                if (textView2 != null) {
                    i = R.id.brick_play_spritesheet_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_play_spritesheet_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_play_spritesheet_rows;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_play_spritesheet_rows);
                        if (textView3 != null) {
                            i = R.id.brick_play_spritesheet_selected_row;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_play_spritesheet_selected_row);
                            if (textView4 != null) {
                                i = R.id.brick_play_spritesheet_speed_val;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_play_spritesheet_speed_val);
                                if (textView5 != null) {
                                    return new BrickPlaySpritesheetBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3, textView4, textView5);
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
