package org.catrobat.catroid.content.strategy;

import android.content.DialogInterface;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import java.util.Objects;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment;
import org.catrobat.catroid.utils.ProjectManagerExtensionsKt;
import org.catrobat.paintroid.colorpicker.ColorPickerDialog;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/strategy/ShowColorPickerFormulaEditorStrategy.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ShowColorPickerFormulaEditorStrategy implements ShowFormulaEditorStrategy {
    private static final int OPTION_FORMULA_EDIT_BRICK = 1;
    private static final int OPTION_PICK_COLOR = 0;

    @Override // org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy
    public void showFormulaEditorToEditFormula(View view, ShowFormulaEditorStrategy.Callback callback) {
        if (isInCorrectFragment(view, callback)) {
            showSelectEditDialog(view, callback);
        } else {
            callback.showFormulaEditor(view);
        }
    }

    private boolean isInCorrectFragment(View view, ShowFormulaEditorStrategy.Callback callback) {
        AppCompatActivity activityFromView = UiUtils.getActivityFromView(view);
        if (activityFromView == null) {
            return false;
        }
        Fragment fragmentFindFragmentById = activityFromView.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        boolean z = fragmentFindFragmentById instanceof FormulaEditorFragment;
        if (z) {
            callback.showFormulaEditor(view);
        }
        return (fragmentFindFragmentById instanceof ScriptFragment) || z;
    }

    private void showSelectEditDialog(final View view, final ShowFormulaEditorStrategy.Callback callback) {
        new AlertDialog.Builder(view.getContext()).setItems(R.array.brick_select_color_picker, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.content.strategy.ShowColorPickerFormulaEditorStrategy$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5002x68388267(callback, view, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: renamed from: lambda$showSelectEditDialog$0$org-catrobat-catroid-content-strategy-ShowColorPickerFormulaEditorStrategy, reason: not valid java name */
    /* synthetic */ void m5002x68388267(ShowFormulaEditorStrategy.Callback callback, View view, DialogInterface dialogInterface, int i) {
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
        showColorPicker(callback, supportFragmentManager);
    }

    private void showColorPicker(ShowFormulaEditorStrategy.Callback callback, FragmentManager fragmentManager) {
        ColorPickerDialog colorPickerDialogNewInstance = ColorPickerDialog.INSTANCE.newInstance(callback.getValue(), true, true);
        colorPickerDialogNewInstance.setBitmap(ProjectManagerExtensionsKt.getProjectBitmap(ProjectManager.getInstance()));
        Objects.requireNonNull(callback);
        colorPickerDialogNewInstance.addOnColorPickedListener(new ShowColorPickerFormulaEditorStrategy$$ExternalSyntheticLambda1(callback));
        colorPickerDialogNewInstance.show(fragmentManager, (String) null);
    }
}
