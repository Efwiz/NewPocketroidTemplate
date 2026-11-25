package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/JumpingSumoMoveBackwardBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class JumpingSumoMoveBackwardBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_jumping_sumo_move_backward;
    }

    public JumpingSumoMoveBackwardBrick() {
        addAllowedBrickField(Brick.BrickField.JUMPING_SUMO_TIME_TO_DRIVE_IN_SECONDS, R.id.brick_jumping_sumo_move_backward_edit_text_second);
        addAllowedBrickField(Brick.BrickField.JUMPING_SUMO_SPEED, R.id.brick_jumping_sumo_move_backward_edit_text_power);
    }

    public JumpingSumoMoveBackwardBrick(int durationInMilliseconds, int powerInPercent) {
        this(new Formula(Double.valueOf(((double) durationInMilliseconds) / 1000.0d)), new Formula(Integer.valueOf(powerInPercent)));
    }

    public JumpingSumoMoveBackwardBrick(Formula durationInSeconds, Formula powerInPercent) {
        this();
        setFormulaWithBrickField(Brick.BrickField.JUMPING_SUMO_TIME_TO_DRIVE_IN_SECONDS, durationInSeconds);
        setFormulaWithBrickField(Brick.BrickField.JUMPING_SUMO_SPEED, powerInPercent);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.JUMPING_SUMO_TIME_TO_DRIVE_IN_SECONDS;
    }
}
