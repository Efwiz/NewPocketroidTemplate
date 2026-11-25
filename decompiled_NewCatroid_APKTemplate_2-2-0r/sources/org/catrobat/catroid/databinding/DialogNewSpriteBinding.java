package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogNewSpriteBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogNewSpriteBinding implements ViewBinding {
    public final TextInputLayout input;
    public final TextInputEditText inputEditText;
    public final SwitchCompat placeVisuallySpriteSwitch;
    public final TextView placeVisuallyTextView;
    private final ScrollView rootView;

    private DialogNewSpriteBinding(ScrollView rootView, TextInputLayout input, TextInputEditText inputEditText, SwitchCompat placeVisuallySpriteSwitch, TextView placeVisuallyTextView) {
        this.rootView = rootView;
        this.input = input;
        this.inputEditText = inputEditText;
        this.placeVisuallySpriteSwitch = placeVisuallySpriteSwitch;
        this.placeVisuallyTextView = placeVisuallyTextView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static DialogNewSpriteBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogNewSpriteBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_new_sprite, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogNewSpriteBinding bind(View rootView) {
        int i = R.id.input;
        TextInputLayout textInputLayout = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.input);
        if (textInputLayout != null) {
            i = R.id.input_edit_text;
            TextInputEditText textInputEditText = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.input_edit_text);
            if (textInputEditText != null) {
                i = R.id.place_visually_sprite_switch;
                SwitchCompat switchCompat = (SwitchCompat) ViewBindings.findChildViewById(rootView, R.id.place_visually_sprite_switch);
                if (switchCompat != null) {
                    i = R.id.place_visually_textView;
                    TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.place_visually_textView);
                    if (textView != null) {
                        return new DialogNewSpriteBinding((ScrollView) rootView, textInputLayout, textInputEditText, switchCompat, textView);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
