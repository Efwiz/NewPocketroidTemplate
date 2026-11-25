package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogNanoEditorBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogNanoEditorBinding implements ViewBinding {
    public final Button nanoBtnExit;
    public final Button nanoBtnSave;
    public final EditText nanoEditorInput;
    public final TextView nanoTitle;
    private final LinearLayout rootView;

    private DialogNanoEditorBinding(LinearLayout rootView, Button nanoBtnExit, Button nanoBtnSave, EditText nanoEditorInput, TextView nanoTitle) {
        this.rootView = rootView;
        this.nanoBtnExit = nanoBtnExit;
        this.nanoBtnSave = nanoBtnSave;
        this.nanoEditorInput = nanoEditorInput;
        this.nanoTitle = nanoTitle;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogNanoEditorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogNanoEditorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_nano_editor, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogNanoEditorBinding bind(View rootView) {
        int i = R.id.nano_btn_exit;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.nano_btn_exit);
        if (button != null) {
            i = R.id.nano_btn_save;
            Button button2 = (Button) ViewBindings.findChildViewById(rootView, R.id.nano_btn_save);
            if (button2 != null) {
                i = R.id.nano_editor_input;
                EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.nano_editor_input);
                if (editText != null) {
                    i = R.id.nano_title;
                    TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.nano_title);
                    if (textView != null) {
                        return new DialogNanoEditorBinding((LinearLayout) rootView, button, button2, editText, textView);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
