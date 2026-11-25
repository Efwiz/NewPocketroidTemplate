package org.catrobat.catroid.content.strategy;

import android.content.DialogInterface;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import java.util.Objects;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.pocketmusic.ui.NotePickerDialog;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/strategy/ShowNotePickerFormulaEditorStrategy.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ShowNotePickerFormulaEditorStrategy implements ShowFormulaEditorStrategy {
    private static final int OPTION_FORMULA_EDIT_BRICK = 1;
    private static final int OPTION_PICK_NOTE = 0;

    @Override // org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy
    public void showFormulaEditorToEditFormula(View view, ShowFormulaEditorStrategy.Callback callback) {
        if (isViewInScriptFragment(view)) {
            showSelectEditDialog(view, callback);
        } else {
            callback.showFormulaEditor(view);
        }
    }

    private boolean isViewInScriptFragment(View view) {
        AppCompatActivity activityFromView = UiUtils.getActivityFromView(view);
        if (activityFromView == null) {
            return false;
        }
        return activityFromView.getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof ScriptFragment;
    }

    private void showSelectEditDialog(final View view, final ShowFormulaEditorStrategy.Callback callback) {
        new AlertDialog.Builder(view.getContext()).setItems(R.array.brick_select_note_picker, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.content.strategy.ShowNotePickerFormulaEditorStrategy$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5003x5113f8d4(callback, view, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: renamed from: lambda$showSelectEditDialog$0$org-catrobat-catroid-content-strategy-ShowNotePickerFormulaEditorStrategy, reason: not valid java name */
    /* synthetic */ void m5003x5113f8d4(ShowFormulaEditorStrategy.Callback callback, View view, DialogInterface dialogInterface, int i) {
        switchSelectEditDialogOption(callback, view, i);
    }

    private void switchSelectEditDialogOption(ShowFormulaEditorStrategy.Callback callback, View view, int which) {
        if (which != 0) {
            if (which == 1) {
                callback.showFormulaEditor(view);
                return;
            }
            throw new IllegalArgumentException();
        }
        AppCompatActivity activityFromView = UiUtils.getActivityFromView(view);
        if (activityFromView == null) {
            return;
        }
        FragmentManager supportFragmentManager = activityFromView.getSupportFragmentManager();
        if (supportFragmentManager.isStateSaved()) {
            return;
        }
        showNotePicker(callback, supportFragmentManager);
    }

    private void showNotePicker(final ShowFormulaEditorStrategy.Callback callback, FragmentManager fragmentManager) {
        NotePickerDialog notePickerDialogNewInstance = NotePickerDialog.newInstance(callback.getValue());
        Objects.requireNonNull(callback);
        notePickerDialogNewInstance.addOnNotePickedListener(new NotePickerDialog.OnNotePickedListener() { // from class: org.catrobat.catroid.content.strategy.ShowNotePickerFormulaEditorStrategy$$ExternalSyntheticLambda0
            @Override // org.catrobat.catroid.pocketmusic.ui.NotePickerDialog.OnNotePickedListener
            public final void noteChanged(int i) {
                callback.setValue(i);
            }
        });
        notePickerDialogNewInstance.setStyle(0, R.style.AlertDialogWithTitle);
        notePickerDialogNewInstance.show(fragmentManager, (String) null);
    }
}
