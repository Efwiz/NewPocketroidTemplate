package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/TurnLeftSpeedBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class TurnLeftSpeedBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_physics_turn_left_speed;
    }

    public TurnLeftSpeedBrick() {
        addAllowedBrickField(Brick.BrickField.PHYSICS_TURN_LEFT_SPEED, R.id.brick_turn_left_speed_edit_text);
    }

    public TurnLeftSpeedBrick(double degreesPerSecond) {
        this(new Formula(Double.valueOf(degreesPerSecond)));
    }

    public TurnLeftSpeedBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.PHYSICS_TURN_LEFT_SPEED, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 3);
        super.addRequiredResources(requiredResourcesSet);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createTurnLeftSpeedAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.PHYSICS_TURN_LEFT_SPEED)));
    }
}
