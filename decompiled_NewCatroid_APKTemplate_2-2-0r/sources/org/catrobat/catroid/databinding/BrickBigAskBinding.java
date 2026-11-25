package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickBigAskBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickBigAskBinding implements ViewBinding {
    public final TextView brickAskLabel;
    public final TextView brickAskLabel2;
    public final TextView brickAskLabel3;
    public final TextView brickAskLabel4;
    public final TextView brickAskLabel5;
    public final TextView brickAskStoreLabel;
    public final TextView brickBigAskCanel;
    public final TextView brickBigAskClue;
    public final TextView brickBigAskDefault;
    public final BrickLayout brickBigAskLayout;
    public final TextView brickBigAskOk;
    public final TextView brickBigAskQuestion;
    public final Spinner brickBigAskSpinner;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickBigAskBinding(LinearLayout rootView, TextView brickAskLabel, TextView brickAskLabel2, TextView brickAskLabel3, TextView brickAskLabel4, TextView brickAskLabel5, TextView brickAskStoreLabel, TextView brickBigAskCanel, TextView brickBigAskClue, TextView brickBigAskDefault, BrickLayout brickBigAskLayout, TextView brickBigAskOk, TextView brickBigAskQuestion, Spinner brickBigAskSpinner, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickAskLabel = brickAskLabel;
        this.brickAskLabel2 = brickAskLabel2;
        this.brickAskLabel3 = brickAskLabel3;
        this.brickAskLabel4 = brickAskLabel4;
        this.brickAskLabel5 = brickAskLabel5;
        this.brickAskStoreLabel = brickAskStoreLabel;
        this.brickBigAskCanel = brickBigAskCanel;
        this.brickBigAskClue = brickBigAskClue;
        this.brickBigAskDefault = brickBigAskDefault;
        this.brickBigAskLayout = brickBigAskLayout;
        this.brickBigAskOk = brickBigAskOk;
        this.brickBigAskQuestion = brickBigAskQuestion;
        this.brickBigAskSpinner = brickBigAskSpinner;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickBigAskBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickBigAskBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_big_ask, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickBigAskBinding bind(View rootView) {
        int i = R.id.brick_ask_label;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ask_label);
        if (textView != null) {
            i = R.id.brick_ask_label2;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ask_label2);
            if (textView2 != null) {
                i = R.id.brick_ask_label3;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ask_label3);
                if (textView3 != null) {
                    i = R.id.brick_ask_label4;
                    TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ask_label4);
                    if (textView4 != null) {
                        i = R.id.brick_ask_label5;
                        TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ask_label5);
                        if (textView5 != null) {
                            i = R.id.brick_ask_store_label;
                            TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ask_store_label);
                            if (textView6 != null) {
                                i = R.id.brick_big_ask_canel;
                                TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_big_ask_canel);
                                if (textView7 != null) {
                                    i = R.id.brick_big_ask_clue;
                                    TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_big_ask_clue);
                                    if (textView8 != null) {
                                        i = R.id.brick_big_ask_default;
                                        TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_big_ask_default);
                                        if (textView9 != null) {
                                            i = R.id.brick_big_ask_layout;
                                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_big_ask_layout);
                                            if (brickLayout != null) {
                                                i = R.id.brick_big_ask_ok;
                                                TextView textView10 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_big_ask_ok);
                                                if (textView10 != null) {
                                                    i = R.id.brick_big_ask_question;
                                                    TextView textView11 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_big_ask_question);
                                                    if (textView11 != null) {
                                                        i = R.id.brick_big_ask_spinner;
                                                        Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_big_ask_spinner);
                                                        if (spinner != null) {
                                                            i = R.id.brick_checkbox;
                                                            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                                                            if (checkBox != null) {
                                                                return new BrickBigAskBinding((LinearLayout) rootView, textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, brickLayout, textView10, textView11, spinner, checkBox);
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
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
