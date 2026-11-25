package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FormulaEditorKeyboardPcBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FormulaEditorKeyboardPcBinding implements ViewBinding {
    public final Button formulaEditorKeyboard0;
    public final Button formulaEditorKeyboard1;
    public final Button formulaEditorKeyboard2;
    public final Button formulaEditorKeyboard3;
    public final Button formulaEditorKeyboard4;
    public final Button formulaEditorKeyboard5;
    public final Button formulaEditorKeyboard6;
    public final Button formulaEditorKeyboard7;
    public final Button formulaEditorKeyboard8;
    public final Button formulaEditorKeyboard9;
    public final Button formulaEditorKeyboardBracketClose;
    public final Button formulaEditorKeyboardBracketOpen;
    public final ImageButton formulaEditorKeyboardColorPicker;
    public final Button formulaEditorKeyboardCompute;
    public final ImageButton formulaEditorKeyboardCopy;
    public final Button formulaEditorKeyboardData;
    public final Button formulaEditorKeyboardDecimalMark;
    public final ImageButton formulaEditorKeyboardDelete;
    public final Button formulaEditorKeyboardDivide;
    public final Button formulaEditorKeyboardFunction;
    public final ImageButton formulaEditorKeyboardFunctionalButtonToggle;
    public final Button formulaEditorKeyboardLogic;
    public final Button formulaEditorKeyboardMinus;
    public final Button formulaEditorKeyboardMult;
    public final Button formulaEditorKeyboardObject;
    public final ImageButton formulaEditorKeyboardPaste;
    public final Button formulaEditorKeyboardPlus;
    public final Button formulaEditorKeyboardSensors;
    public final Button formulaEditorKeyboardString;
    public final LinearLayout formulaEditorKeyboardview;
    private final LinearLayout rootView;

    private FormulaEditorKeyboardPcBinding(LinearLayout rootView, Button formulaEditorKeyboard0, Button formulaEditorKeyboard1, Button formulaEditorKeyboard2, Button formulaEditorKeyboard3, Button formulaEditorKeyboard4, Button formulaEditorKeyboard5, Button formulaEditorKeyboard6, Button formulaEditorKeyboard7, Button formulaEditorKeyboard8, Button formulaEditorKeyboard9, Button formulaEditorKeyboardBracketClose, Button formulaEditorKeyboardBracketOpen, ImageButton formulaEditorKeyboardColorPicker, Button formulaEditorKeyboardCompute, ImageButton formulaEditorKeyboardCopy, Button formulaEditorKeyboardData, Button formulaEditorKeyboardDecimalMark, ImageButton formulaEditorKeyboardDelete, Button formulaEditorKeyboardDivide, Button formulaEditorKeyboardFunction, ImageButton formulaEditorKeyboardFunctionalButtonToggle, Button formulaEditorKeyboardLogic, Button formulaEditorKeyboardMinus, Button formulaEditorKeyboardMult, Button formulaEditorKeyboardObject, ImageButton formulaEditorKeyboardPaste, Button formulaEditorKeyboardPlus, Button formulaEditorKeyboardSensors, Button formulaEditorKeyboardString, LinearLayout formulaEditorKeyboardview) {
        this.rootView = rootView;
        this.formulaEditorKeyboard0 = formulaEditorKeyboard0;
        this.formulaEditorKeyboard1 = formulaEditorKeyboard1;
        this.formulaEditorKeyboard2 = formulaEditorKeyboard2;
        this.formulaEditorKeyboard3 = formulaEditorKeyboard3;
        this.formulaEditorKeyboard4 = formulaEditorKeyboard4;
        this.formulaEditorKeyboard5 = formulaEditorKeyboard5;
        this.formulaEditorKeyboard6 = formulaEditorKeyboard6;
        this.formulaEditorKeyboard7 = formulaEditorKeyboard7;
        this.formulaEditorKeyboard8 = formulaEditorKeyboard8;
        this.formulaEditorKeyboard9 = formulaEditorKeyboard9;
        this.formulaEditorKeyboardBracketClose = formulaEditorKeyboardBracketClose;
        this.formulaEditorKeyboardBracketOpen = formulaEditorKeyboardBracketOpen;
        this.formulaEditorKeyboardColorPicker = formulaEditorKeyboardColorPicker;
        this.formulaEditorKeyboardCompute = formulaEditorKeyboardCompute;
        this.formulaEditorKeyboardCopy = formulaEditorKeyboardCopy;
        this.formulaEditorKeyboardData = formulaEditorKeyboardData;
        this.formulaEditorKeyboardDecimalMark = formulaEditorKeyboardDecimalMark;
        this.formulaEditorKeyboardDelete = formulaEditorKeyboardDelete;
        this.formulaEditorKeyboardDivide = formulaEditorKeyboardDivide;
        this.formulaEditorKeyboardFunction = formulaEditorKeyboardFunction;
        this.formulaEditorKeyboardFunctionalButtonToggle = formulaEditorKeyboardFunctionalButtonToggle;
        this.formulaEditorKeyboardLogic = formulaEditorKeyboardLogic;
        this.formulaEditorKeyboardMinus = formulaEditorKeyboardMinus;
        this.formulaEditorKeyboardMult = formulaEditorKeyboardMult;
        this.formulaEditorKeyboardObject = formulaEditorKeyboardObject;
        this.formulaEditorKeyboardPaste = formulaEditorKeyboardPaste;
        this.formulaEditorKeyboardPlus = formulaEditorKeyboardPlus;
        this.formulaEditorKeyboardSensors = formulaEditorKeyboardSensors;
        this.formulaEditorKeyboardString = formulaEditorKeyboardString;
        this.formulaEditorKeyboardview = formulaEditorKeyboardview;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FormulaEditorKeyboardPcBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FormulaEditorKeyboardPcBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.formula_editor_keyboard_pc, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FormulaEditorKeyboardPcBinding bind(View rootView) {
        int i = R.id.formula_editor_keyboard_0;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_0);
        if (button != null) {
            i = R.id.formula_editor_keyboard_1;
            Button button2 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_1);
            if (button2 != null) {
                i = R.id.formula_editor_keyboard_2;
                Button button3 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_2);
                if (button3 != null) {
                    i = R.id.formula_editor_keyboard_3;
                    Button button4 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_3);
                    if (button4 != null) {
                        i = R.id.formula_editor_keyboard_4;
                        Button button5 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_4);
                        if (button5 != null) {
                            i = R.id.formula_editor_keyboard_5;
                            Button button6 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_5);
                            if (button6 != null) {
                                i = R.id.formula_editor_keyboard_6;
                                Button button7 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_6);
                                if (button7 != null) {
                                    i = R.id.formula_editor_keyboard_7;
                                    Button button8 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_7);
                                    if (button8 != null) {
                                        i = R.id.formula_editor_keyboard_8;
                                        Button button9 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_8);
                                        if (button9 != null) {
                                            i = R.id.formula_editor_keyboard_9;
                                            Button button10 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_9);
                                            if (button10 != null) {
                                                i = R.id.formula_editor_keyboard_bracket_close;
                                                Button button11 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_bracket_close);
                                                if (button11 != null) {
                                                    i = R.id.formula_editor_keyboard_bracket_open;
                                                    Button button12 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_bracket_open);
                                                    if (button12 != null) {
                                                        i = R.id.formula_editor_keyboard_color_picker;
                                                        ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_color_picker);
                                                        if (imageButton != null) {
                                                            i = R.id.formula_editor_keyboard_compute;
                                                            Button button13 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_compute);
                                                            if (button13 != null) {
                                                                i = R.id.formula_editor_keyboard_copy;
                                                                ImageButton imageButton2 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_copy);
                                                                if (imageButton2 != null) {
                                                                    i = R.id.formula_editor_keyboard_data;
                                                                    Button button14 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_data);
                                                                    if (button14 != null) {
                                                                        i = R.id.formula_editor_keyboard_decimal_mark;
                                                                        Button button15 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_decimal_mark);
                                                                        if (button15 != null) {
                                                                            i = R.id.formula_editor_keyboard_delete;
                                                                            ImageButton imageButton3 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_delete);
                                                                            if (imageButton3 != null) {
                                                                                i = R.id.formula_editor_keyboard_divide;
                                                                                Button button16 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_divide);
                                                                                if (button16 != null) {
                                                                                    i = R.id.formula_editor_keyboard_function;
                                                                                    Button button17 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_function);
                                                                                    if (button17 != null) {
                                                                                        i = R.id.formula_editor_keyboard_functional_button_toggle;
                                                                                        ImageButton imageButton4 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_functional_button_toggle);
                                                                                        if (imageButton4 != null) {
                                                                                            i = R.id.formula_editor_keyboard_logic;
                                                                                            Button button18 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_logic);
                                                                                            if (button18 != null) {
                                                                                                i = R.id.formula_editor_keyboard_minus;
                                                                                                Button button19 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_minus);
                                                                                                if (button19 != null) {
                                                                                                    i = R.id.formula_editor_keyboard_mult;
                                                                                                    Button button20 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_mult);
                                                                                                    if (button20 != null) {
                                                                                                        i = R.id.formula_editor_keyboard_object;
                                                                                                        Button button21 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_object);
                                                                                                        if (button21 != null) {
                                                                                                            i = R.id.formula_editor_keyboard_paste;
                                                                                                            ImageButton imageButton5 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_paste);
                                                                                                            if (imageButton5 != null) {
                                                                                                                i = R.id.formula_editor_keyboard_plus;
                                                                                                                Button button22 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_plus);
                                                                                                                if (button22 != null) {
                                                                                                                    i = R.id.formula_editor_keyboard_sensors;
                                                                                                                    Button button23 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_sensors);
                                                                                                                    if (button23 != null) {
                                                                                                                        i = R.id.formula_editor_keyboard_string;
                                                                                                                        Button button24 = (Button) ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboard_string);
                                                                                                                        if (button24 != null) {
                                                                                                                            LinearLayout linearLayout = (LinearLayout) rootView;
                                                                                                                            return new FormulaEditorKeyboardPcBinding(linearLayout, button, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12, imageButton, button13, imageButton2, button14, button15, imageButton3, button16, button17, imageButton4, button18, button19, button20, button21, imageButton5, button22, button23, button24, linearLayout);
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
