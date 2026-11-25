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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetAiBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetAiBinding implements ViewBinding {
    public final TextView brickAiAvoid;
    public final TextView brickAiMode;
    public final TextView brickAiObjId;
    public final TextView brickAiRange;
    public final TextView brickAiSpeed;
    public final TextView brickAiStep;
    public final TextView brickAiStop;
    public final TextView brickAiTarget;
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSetAiLayout;
    private final LinearLayout rootView;

    private BrickSetAiBinding(LinearLayout rootView, TextView brickAiAvoid, TextView brickAiMode, TextView brickAiObjId, TextView brickAiRange, TextView brickAiSpeed, TextView brickAiStep, TextView brickAiStop, TextView brickAiTarget, CheckBox brickCheckbox, BrickLayout brickSetAiLayout) {
        this.rootView = rootView;
        this.brickAiAvoid = brickAiAvoid;
        this.brickAiMode = brickAiMode;
        this.brickAiObjId = brickAiObjId;
        this.brickAiRange = brickAiRange;
        this.brickAiSpeed = brickAiSpeed;
        this.brickAiStep = brickAiStep;
        this.brickAiStop = brickAiStop;
        this.brickAiTarget = brickAiTarget;
        this.brickCheckbox = brickCheckbox;
        this.brickSetAiLayout = brickSetAiLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetAiBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetAiBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_ai, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetAiBinding bind(View rootView) {
        int i = R.id.brick_ai_avoid;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ai_avoid);
        if (textView != null) {
            i = R.id.brick_ai_mode;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ai_mode);
            if (textView2 != null) {
                i = R.id.brick_ai_obj_id;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ai_obj_id);
                if (textView3 != null) {
                    i = R.id.brick_ai_range;
                    TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ai_range);
                    if (textView4 != null) {
                        i = R.id.brick_ai_speed;
                        TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ai_speed);
                        if (textView5 != null) {
                            i = R.id.brick_ai_step;
                            TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ai_step);
                            if (textView6 != null) {
                                i = R.id.brick_ai_stop;
                                TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ai_stop);
                                if (textView7 != null) {
                                    i = R.id.brick_ai_target;
                                    TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ai_target);
                                    if (textView8 != null) {
                                        i = R.id.brick_checkbox;
                                        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                                        if (checkBox != null) {
                                            i = R.id.brick_set_ai_layout;
                                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_ai_layout);
                                            if (brickLayout != null) {
                                                return new BrickSetAiBinding((LinearLayout) rootView, textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, checkBox, brickLayout);
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
