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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSquareBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSquareBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSquareEditBorders;
    public final TextView brickSquareEditColor;
    public final TextView brickSquareEditH;
    public final TextView brickSquareEditName;
    public final TextView brickSquareEditRot;
    public final TextView brickSquareEditTrans;
    public final TextView brickSquareEditW;
    public final TextView brickSquareEditX;
    public final TextView brickSquareEditY;
    public final BrickLayout brickSquareLayout;
    private final LinearLayout rootView;

    private BrickSquareBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSquareEditBorders, TextView brickSquareEditColor, TextView brickSquareEditH, TextView brickSquareEditName, TextView brickSquareEditRot, TextView brickSquareEditTrans, TextView brickSquareEditW, TextView brickSquareEditX, TextView brickSquareEditY, BrickLayout brickSquareLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSquareEditBorders = brickSquareEditBorders;
        this.brickSquareEditColor = brickSquareEditColor;
        this.brickSquareEditH = brickSquareEditH;
        this.brickSquareEditName = brickSquareEditName;
        this.brickSquareEditRot = brickSquareEditRot;
        this.brickSquareEditTrans = brickSquareEditTrans;
        this.brickSquareEditW = brickSquareEditW;
        this.brickSquareEditX = brickSquareEditX;
        this.brickSquareEditY = brickSquareEditY;
        this.brickSquareLayout = brickSquareLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSquareBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSquareBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_square, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSquareBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_square_edit_borders;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_square_edit_borders);
            if (textView != null) {
                i = R.id.brick_square_edit_color;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_square_edit_color);
                if (textView2 != null) {
                    i = R.id.brick_square_edit_h;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_square_edit_h);
                    if (textView3 != null) {
                        i = R.id.brick_square_edit_name;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_square_edit_name);
                        if (textView4 != null) {
                            i = R.id.brick_square_edit_rot;
                            TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_square_edit_rot);
                            if (textView5 != null) {
                                i = R.id.brick_square_edit_trans;
                                TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_square_edit_trans);
                                if (textView6 != null) {
                                    i = R.id.brick_square_edit_w;
                                    TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_square_edit_w);
                                    if (textView7 != null) {
                                        i = R.id.brick_square_edit_x;
                                        TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_square_edit_x);
                                        if (textView8 != null) {
                                            i = R.id.brick_square_edit_y;
                                            TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_square_edit_y);
                                            if (textView9 != null) {
                                                i = R.id.brick_square_layout;
                                                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_square_layout);
                                                if (brickLayout != null) {
                                                    return new BrickSquareBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, brickLayout);
                                                }
                                            }
                                        }
                                    }
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
