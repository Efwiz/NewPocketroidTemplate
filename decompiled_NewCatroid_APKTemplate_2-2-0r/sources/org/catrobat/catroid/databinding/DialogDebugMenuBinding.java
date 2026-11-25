package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogDebugMenuBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogDebugMenuBinding implements ViewBinding {
    public final ImageButton btnRecordLogs;
    public final ImageButton closeButton;
    private final LinearLayout rootView;
    public final LinearLayout titleBar;
    public final LinearLayout variablesContainer;

    private DialogDebugMenuBinding(LinearLayout rootView, ImageButton btnRecordLogs, ImageButton closeButton, LinearLayout titleBar, LinearLayout variablesContainer) {
        this.rootView = rootView;
        this.btnRecordLogs = btnRecordLogs;
        this.closeButton = closeButton;
        this.titleBar = titleBar;
        this.variablesContainer = variablesContainer;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogDebugMenuBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogDebugMenuBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_debug_menu, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogDebugMenuBinding bind(View rootView) {
        int i = R.id.btn_record_logs;
        ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_record_logs);
        if (imageButton != null) {
            i = R.id.close_button;
            ImageButton imageButton2 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.close_button);
            if (imageButton2 != null) {
                i = R.id.title_bar;
                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.title_bar);
                if (linearLayout != null) {
                    i = R.id.variables_container;
                    LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.variables_container);
                    if (linearLayout2 != null) {
                        return new DialogDebugMenuBinding((LinearLayout) rootView, imageButton, imageButton2, linearLayout, linearLayout2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
