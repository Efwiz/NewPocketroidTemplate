package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogNewUserDataBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogNewUserDataBinding implements ViewBinding {
    public final RadioButton global;
    public final TextInputLayout input;
    public final TextInputEditText inputEditText;
    public final RadioButton local;
    public final CheckBox makeList;
    public final RadioButton multiplayer;
    public final RadioGroup radioGroup;
    private final ScrollView rootView;

    private DialogNewUserDataBinding(ScrollView rootView, RadioButton global, TextInputLayout input, TextInputEditText inputEditText, RadioButton local, CheckBox makeList, RadioButton multiplayer, RadioGroup radioGroup) {
        this.rootView = rootView;
        this.global = global;
        this.input = input;
        this.inputEditText = inputEditText;
        this.local = local;
        this.makeList = makeList;
        this.multiplayer = multiplayer;
        this.radioGroup = radioGroup;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static DialogNewUserDataBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogNewUserDataBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_new_user_data, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogNewUserDataBinding bind(View rootView) {
        int i = R.id.global;
        RadioButton radioButton = (RadioButton) ViewBindings.findChildViewById(rootView, R.id.global);
        if (radioButton != null) {
            i = R.id.input;
            TextInputLayout textInputLayout = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.input);
            if (textInputLayout != null) {
                i = R.id.input_edit_text;
                TextInputEditText textInputEditText = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.input_edit_text);
                if (textInputEditText != null) {
                    i = R.id.local;
                    RadioButton radioButton2 = (RadioButton) ViewBindings.findChildViewById(rootView, R.id.local);
                    if (radioButton2 != null) {
                        i = R.id.make_list;
                        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.make_list);
                        if (checkBox != null) {
                            i = R.id.multiplayer;
                            RadioButton radioButton3 = (RadioButton) ViewBindings.findChildViewById(rootView, R.id.multiplayer);
                            if (radioButton3 != null) {
                                i = R.id.radio_group;
                                RadioGroup radioGroup = (RadioGroup) ViewBindings.findChildViewById(rootView, R.id.radio_group);
                                if (radioGroup != null) {
                                    return new DialogNewUserDataBinding((ScrollView) rootView, radioButton, textInputLayout, textInputEditText, radioButton2, checkBox, radioButton3, radioGroup);
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
