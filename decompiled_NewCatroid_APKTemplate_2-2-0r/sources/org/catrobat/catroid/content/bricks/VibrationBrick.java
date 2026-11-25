package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/VibrationBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class VibrationBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_vibration;
    }

    public VibrationBrick() {
        addAllowedBrickField(Brick.BrickField.VIBRATE_DURATION_IN_SECONDS, R.id.brick_vibration_edit_text);
    }

    public VibrationBrick(double vibrateDurationInSeconds) {
        this(new Formula(Double.valueOf(vibrateDurationInSeconds)));
    }

    public VibrationBrick(Formula vibrateDurationInSecondsFormula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VIBRATE_DURATION_IN_SECONDS, vibrateDurationInSecondsFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 9);
        super.addRequiredResources(requiredResourcesSet);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        setSecondsLabel(this.view, Brick.BrickField.VIBRATE_DURATION_IN_SECONDS);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createVibrateAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VIBRATE_DURATION_IN_SECONDS)));
    }
}
