package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment;
import org.catrobat.catroid.visualplacement.VisualPlacementActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/VisualPlacementBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class VisualPlacementBrick extends FormulaBrick {
    public abstract Brick.BrickField getXBrickField();

    public abstract int getXEditTextId();

    public abstract Brick.BrickField getYBrickField();

    public abstract int getYEditTextId();

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public void showFormulaEditorToEditFormula(View view) {
        AppCompatActivity activityFromView = UiUtils.getActivityFromView(view);
        if (activityFromView == null) {
            return;
        }
        Fragment fragmentFindFragmentByTag = activityFromView.getSupportFragmentManager().findFragmentByTag(FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG);
        if (fragmentFindFragmentByTag == null) {
            fragmentFindFragmentByTag = activityFromView.getSupportFragmentManager().findFragmentByTag(ScriptFragment.TAG);
        }
        if (fragmentFindFragmentByTag == null) {
            fragmentFindFragmentByTag = activityFromView.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        }
        if (fragmentFindFragmentByTag instanceof FormulaEditorFragment) {
            super.showFormulaEditorToEditFormula(view);
            if (isVisualPlacement(view)) {
                showDialog(view, fragmentFindFragmentByTag);
                return;
            }
            return;
        }
        if (fragmentFindFragmentByTag instanceof ScriptFragment) {
            if (isVisualPlacement(view)) {
                showDialog(view, fragmentFindFragmentByTag);
            } else {
                super.showFormulaEditorToEditFormula(view);
            }
        }
    }

    private void showDialog(final View view, final Fragment currentFragment) {
        Context context = view.getContext();
        new AlertDialog.Builder(context).setItems(new String[]{context.getString(R.string.brick_option_place_visually), context.getString(R.string.brick_context_dialog_formula_edit_brick)}, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.content.bricks.VisualPlacementBrick$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m4996xf80b2e98(currentFragment, view, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: renamed from: lambda$showDialog$0$org-catrobat-catroid-content-bricks-VisualPlacementBrick, reason: not valid java name */
    /* synthetic */ void m4996xf80b2e98(Fragment fragment, View view, DialogInterface dialogInterface, int i) {
        if (i == 0) {
            placeVisually(getXBrickField(), getYBrickField());
        } else if (i == 1 && (fragment instanceof ScriptFragment)) {
            super.showFormulaEditorToEditFormula(view);
        }
    }

    public void placeVisually(Brick.BrickField brickFieldX, Brick.BrickField brickFieldY) {
        startVisualPlacementActivity(generateIntentForVisualPlacement(brickFieldX, brickFieldY));
    }

    public Intent generateIntentForVisualPlacement(Brick.BrickField brickFieldX, Brick.BrickField brickFieldY) {
        int iIntValue;
        int iIntValue2;
        Formula formulaWithBrickField = getFormulaWithBrickField(brickFieldX);
        Formula formulaWithBrickField2 = getFormulaWithBrickField(brickFieldY);
        Intent intent = new Intent(this.view.getContext(), (Class<?>) VisualPlacementActivity.class);
        intent.putExtra(SpriteActivity.EXTRA_BRICK_HASH, hashCode());
        try {
            ProjectManager projectManager = ProjectManager.getInstance();
            Scope scope = new Scope(projectManager.getCurrentProject(), projectManager.getCurrentSprite(), null);
            iIntValue = formulaWithBrickField.interpretInteger(scope).intValue();
            iIntValue2 = formulaWithBrickField2.interpretInteger(scope).intValue();
        } catch (InterpretationException unused) {
            iIntValue = 0;
            iIntValue2 = 0;
        }
        intent.putExtra(SpriteActivity.EXTRA_X_TRANSFORM, iIntValue);
        intent.putExtra(SpriteActivity.EXTRA_Y_TRANSFORM, iIntValue2);
        intent.putExtra(SpriteActivity.EXTRA_ROTATION, ProjectManager.getInstance().getCurrentSprite().look.getMotionDirectionInUserInterfaceDimensionUnit());
        return intent;
    }

    private void startVisualPlacementActivity(Intent intent) {
        AppCompatActivity activityFromView = UiUtils.getActivityFromView(this.view);
        if (activityFromView == null) {
            return;
        }
        activityFromView.startActivityForResult(intent, 2019);
    }

    public void setCoordinates(int x, int y) {
        setFormulaWithBrickField(getXBrickField(), new Formula(Integer.valueOf(x)));
        setFormulaWithBrickField(getYBrickField(), new Formula(Integer.valueOf(y)));
    }

    public boolean isVisualPlacement(View view) {
        return isCorrectTextField(view) && areAllBrickFieldsNumbers();
    }

    public boolean areAllBrickFieldsNumbers() {
        return isBrickFieldANumber(getXBrickField()) && isBrickFieldANumber(getYBrickField());
    }

    private boolean isCorrectTextField(View view) {
        return view.getId() == getXEditTextId() || view.getId() == getYEditTextId();
    }
}
