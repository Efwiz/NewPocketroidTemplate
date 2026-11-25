package org.catrobat.catroid.content.bricks.brickspinner;

import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.DuplicateInputTextWatcher;
import org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/brickspinner/UserVariableBrickTextInputDialogBuilder.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class UserVariableBrickTextInputDialogBuilder extends TextInputDialog.Builder {
    public UserVariableBrickTextInputDialogBuilder(final Project project, final Sprite sprite, final UserVariable currentUserVariable, final AppCompatActivity activity, final BrickSpinner<UserVariable> spinner) {
        super(activity);
        View viewInflate = View.inflate(activity, R.layout.dialog_new_user_data, null);
        final RadioButton radioButton = (RadioButton) viewInflate.findViewById(R.id.multiplayer);
        if (SettingsFragment.isMultiplayerVariablesPreferenceEnabled(activity.getApplicationContext())) {
            radioButton.setVisibility(0);
        }
        setView(viewInflate);
        setHint(activity.getString(R.string.data_label)).setTextWatcher(new DuplicateInputTextWatcher(spinner.getItems())).setPositiveButton(activity.getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.content.bricks.brickspinner.UserVariableBrickTextInputDialogBuilder$$ExternalSyntheticLambda0
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                UserVariableBrickTextInputDialogBuilder.lambda$new$0(radioButton, project, sprite, spinner, activity, dialogInterface, str);
            }
        });
        setTitle(R.string.formula_editor_variable_dialog_title);
        setText(createUniqueNameProvider(R.string.default_variable_name).getUniqueName(activity.getString(R.string.default_variable_name), null));
        setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.content.bricks.brickspinner.UserVariableBrickTextInputDialogBuilder$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                spinner.setSelection(currentUserVariable);
            }
        });
        setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: org.catrobat.catroid.content.bricks.brickspinner.UserVariableBrickTextInputDialogBuilder$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                spinner.setSelection(currentUserVariable);
            }
        });
    }

    static /* synthetic */ void lambda$new$0(RadioButton radioButton, Project project, Sprite sprite, BrickSpinner brickSpinner, AppCompatActivity appCompatActivity, DialogInterface dialogInterface, String str) {
        UserVariable userVariable = new UserVariable(str);
        boolean zIsChecked = ((RadioButton) ((Dialog) dialogInterface).findViewById(R.id.global)).isChecked();
        boolean zIsChecked2 = radioButton.isChecked();
        if (zIsChecked) {
            project.addUserVariable(userVariable);
        } else if (zIsChecked2) {
            project.addMultiplayerVariable(userVariable);
        } else {
            sprite.addUserVariable(userVariable);
        }
        brickSpinner.add(userVariable);
        brickSpinner.setSelection(userVariable);
        ScriptFragment scriptFragment = (ScriptFragment) appCompatActivity.getSupportFragmentManager().findFragmentByTag(ScriptFragment.TAG);
        if (scriptFragment != null) {
            scriptFragment.notifyDataSetChanged();
        }
        DataListFragment.refreshActiveInstance();
    }
}
