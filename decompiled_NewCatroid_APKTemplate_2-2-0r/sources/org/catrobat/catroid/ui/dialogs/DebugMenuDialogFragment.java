package org.catrobat.catroid.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/DebugMenuDialogFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DebugMenuDialogFragment extends DialogFragment {
    public static final String TAG = "DebugMenuDialogFragment";
    private float dX;
    private float dY;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_debug_menu, container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupWindowControls(view);
        populateVariables(view);
    }

    private void setupWindowControls(View view) {
        View viewFindViewById = view.findViewById(R.id.title_bar);
        view.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuDialogFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m5645xe267633e(view2);
            }
        });
        viewFindViewById.setOnTouchListener(new View.OnTouchListener() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuDialogFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return this.f$0.m5646x7fb6c3f(view2, motionEvent);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setupWindowControls$0$org-catrobat-catroid-ui-dialogs-DebugMenuDialogFragment, reason: not valid java name */
    /* synthetic */ void m5645xe267633e(View view) {
        dismiss();
    }

    /* JADX INFO: renamed from: lambda$setupWindowControls$1$org-catrobat-catroid-ui-dialogs-DebugMenuDialogFragment, reason: not valid java name */
    /* synthetic */ boolean m5646x7fb6c3f(View view, MotionEvent motionEvent) {
        Window window = getDialog().getWindow();
        if (window == null) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            this.dX = window.getAttributes().x - motionEvent.getRawX();
            this.dY = window.getAttributes().y - motionEvent.getRawY();
            return true;
        }
        if (action != 2) {
            return false;
        }
        window.getAttributes().x = (int) (motionEvent.getRawX() + this.dX);
        window.getAttributes().y = (int) (motionEvent.getRawY() + this.dY);
        window.getWindowManager().updateViewLayout(window.getDecorView(), window.getAttributes());
        return true;
    }

    private void populateVariables(View view) {
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.variables_container);
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        if (currentProject == null) {
            return;
        }
        addHeader(linearLayout, "Global Variables");
        for (UserVariable userVariable : currentProject.getUserVariables()) {
            addVariableView(linearLayout, userVariable.getName(), userVariable.getValue().toString());
        }
        for (Sprite sprite : currentProject.getSpriteListWithClones()) {
            if (!sprite.getUserVariables().isEmpty()) {
                addHeader(linearLayout, "Sprite: " + sprite.getName());
                for (UserVariable userVariable2 : sprite.getUserVariables()) {
                    addVariableView(linearLayout, userVariable2.getName(), userVariable2.getValue().toString());
                }
            }
        }
    }

    private void addHeader(ViewGroup container, String text) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(16.0f);
        textView.setTextColor(-1);
        textView.setPadding(0, 16, 0, 4);
        container.addView(textView);
    }

    private void addVariableView(ViewGroup container, String name2, String value) {
        TextView textView = new TextView(getContext());
        textView.setText(String.format("  • %s: %s", name2, value));
        textView.setTextSize(14.0f);
        textView.setTextColor(-3355444);
        container.addView(textView);
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialogOnCreateDialog = super.onCreateDialog(savedInstanceState);
        dialogOnCreateDialog.getWindow().requestFeature(1);
        return dialogOnCreateDialog;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setDimAmount(0.0f);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }
}
