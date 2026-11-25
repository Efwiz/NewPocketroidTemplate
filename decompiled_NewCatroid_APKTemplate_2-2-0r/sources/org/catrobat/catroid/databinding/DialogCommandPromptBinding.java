package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogCommandPromptBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogCommandPromptBinding implements ViewBinding {
    public final ImageButton btnHistoryDown;
    public final ImageButton btnHistoryUp;
    public final ProgressBar progressBar;
    private final LinearLayout rootView;
    public final NestedScrollView scrollView;
    public final ImageButton sendButton;
    public final EditText terminalInput;
    public final TextView terminalOutput;
    public final TextView terminalPrompt;

    private DialogCommandPromptBinding(LinearLayout rootView, ImageButton btnHistoryDown, ImageButton btnHistoryUp, ProgressBar progressBar, NestedScrollView scrollView, ImageButton sendButton, EditText terminalInput, TextView terminalOutput, TextView terminalPrompt) {
        this.rootView = rootView;
        this.btnHistoryDown = btnHistoryDown;
        this.btnHistoryUp = btnHistoryUp;
        this.progressBar = progressBar;
        this.scrollView = scrollView;
        this.sendButton = sendButton;
        this.terminalInput = terminalInput;
        this.terminalOutput = terminalOutput;
        this.terminalPrompt = terminalPrompt;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogCommandPromptBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogCommandPromptBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_command_prompt, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogCommandPromptBinding bind(View rootView) {
        int i = R.id.btnHistoryDown;
        ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btnHistoryDown);
        if (imageButton != null) {
            i = R.id.btnHistoryUp;
            ImageButton imageButton2 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btnHistoryUp);
            if (imageButton2 != null) {
                i = R.id.progressBar;
                ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(rootView, R.id.progressBar);
                if (progressBar != null) {
                    i = R.id.scrollView;
                    NestedScrollView nestedScrollView = (NestedScrollView) ViewBindings.findChildViewById(rootView, R.id.scrollView);
                    if (nestedScrollView != null) {
                        i = R.id.sendButton;
                        ImageButton imageButton3 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.sendButton);
                        if (imageButton3 != null) {
                            i = R.id.terminalInput;
                            EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.terminalInput);
                            if (editText != null) {
                                i = R.id.terminalOutput;
                                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.terminalOutput);
                                if (textView != null) {
                                    i = R.id.terminalPrompt;
                                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.terminalPrompt);
                                    if (textView2 != null) {
                                        return new DialogCommandPromptBinding((LinearLayout) rootView, imageButton, imageButton2, progressBar, nestedScrollView, imageButton3, editText, textView, textView2);
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
