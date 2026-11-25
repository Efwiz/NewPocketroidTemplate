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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCreateTextfieldBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCreateTextfieldBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickCreateTextfieldLayout;
    public final Spinner brickCreateTextfieldSpinner;
    public final TextView createTextfieldAlignment;
    public final TextView createTextfieldBgcolor;
    public final TextView createTextfieldCorner;
    public final TextView createTextfieldDefault;
    public final TextView createTextfieldFont;
    public final TextView createTextfieldHeight;
    public final TextView createTextfieldHintcolor;
    public final TextView createTextfieldHinttext;
    public final TextView createTextfieldLength;
    public final TextView createTextfieldName;
    public final TextView createTextfieldPassword;
    public final TextView createTextfieldTextcolor;
    public final TextView createTextfieldTextsize;
    public final TextView createTextfieldType;
    public final TextView createTextfieldWidth;
    public final TextView createTextfieldX;
    public final TextView createTextfieldY;
    private final LinearLayout rootView;

    private BrickCreateTextfieldBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickCreateTextfieldLayout, Spinner brickCreateTextfieldSpinner, TextView createTextfieldAlignment, TextView createTextfieldBgcolor, TextView createTextfieldCorner, TextView createTextfieldDefault, TextView createTextfieldFont, TextView createTextfieldHeight, TextView createTextfieldHintcolor, TextView createTextfieldHinttext, TextView createTextfieldLength, TextView createTextfieldName, TextView createTextfieldPassword, TextView createTextfieldTextcolor, TextView createTextfieldTextsize, TextView createTextfieldType, TextView createTextfieldWidth, TextView createTextfieldX, TextView createTextfieldY) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickCreateTextfieldLayout = brickCreateTextfieldLayout;
        this.brickCreateTextfieldSpinner = brickCreateTextfieldSpinner;
        this.createTextfieldAlignment = createTextfieldAlignment;
        this.createTextfieldBgcolor = createTextfieldBgcolor;
        this.createTextfieldCorner = createTextfieldCorner;
        this.createTextfieldDefault = createTextfieldDefault;
        this.createTextfieldFont = createTextfieldFont;
        this.createTextfieldHeight = createTextfieldHeight;
        this.createTextfieldHintcolor = createTextfieldHintcolor;
        this.createTextfieldHinttext = createTextfieldHinttext;
        this.createTextfieldLength = createTextfieldLength;
        this.createTextfieldName = createTextfieldName;
        this.createTextfieldPassword = createTextfieldPassword;
        this.createTextfieldTextcolor = createTextfieldTextcolor;
        this.createTextfieldTextsize = createTextfieldTextsize;
        this.createTextfieldType = createTextfieldType;
        this.createTextfieldWidth = createTextfieldWidth;
        this.createTextfieldX = createTextfieldX;
        this.createTextfieldY = createTextfieldY;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCreateTextfieldBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCreateTextfieldBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_create_textfield, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCreateTextfieldBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_create_textfield_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_create_textfield_layout);
            if (brickLayout != null) {
                i = R.id.brick_create_textfield_spinner;
                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_create_textfield_spinner);
                if (spinner != null) {
                    i = R.id.create_textfield_alignment;
                    TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_textfield_alignment);
                    if (textView != null) {
                        i = R.id.create_textfield_bgcolor;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_textfield_bgcolor);
                        if (textView2 != null) {
                            i = R.id.create_textfield_corner;
                            TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_textfield_corner);
                            if (textView3 != null) {
                                i = R.id.create_textfield_default;
                                TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_textfield_default);
                                if (textView4 != null) {
                                    i = R.id.create_textfield_font;
                                    TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_textfield_font);
                                    if (textView5 != null) {
                                        i = R.id.create_textfield_height;
                                        TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_textfield_height);
                                        if (textView6 != null) {
                                            i = R.id.create_textfield_hintcolor;
                                            TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_textfield_hintcolor);
                                            if (textView7 != null) {
                                                i = R.id.create_textfield_hinttext;
                                                TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_textfield_hinttext);
                                                if (textView8 != null) {
                                                    i = R.id.create_textfield_length;
                                                    TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_textfield_length);
                                                    if (textView9 != null) {
                                                        i = R.id.create_textfield_name;
                                                        TextView textView10 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_textfield_name);
                                                        if (textView10 != null) {
                                                            i = R.id.create_textfield_password;
                                                            TextView textView11 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_textfield_password);
                                                            if (textView11 != null) {
                                                                i = R.id.create_textfield_textcolor;
                                                                TextView textView12 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_textfield_textcolor);
                                                                if (textView12 != null) {
                                                                    i = R.id.create_textfield_textsize;
                                                                    TextView textView13 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_textfield_textsize);
                                                                    if (textView13 != null) {
                                                                        i = R.id.create_textfield_type;
                                                                        TextView textView14 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_textfield_type);
                                                                        if (textView14 != null) {
                                                                            i = R.id.create_textfield_width;
                                                                            TextView textView15 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_textfield_width);
                                                                            if (textView15 != null) {
                                                                                i = R.id.create_textfield_x;
                                                                                TextView textView16 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_textfield_x);
                                                                                if (textView16 != null) {
                                                                                    i = R.id.create_textfield_y;
                                                                                    TextView textView17 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_textfield_y);
                                                                                    if (textView17 != null) {
                                                                                        return new BrickCreateTextfieldBinding((LinearLayout) rootView, checkBox, brickLayout, spinner, textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, textView11, textView12, textView13, textView14, textView15, textView16, textView17);
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
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
