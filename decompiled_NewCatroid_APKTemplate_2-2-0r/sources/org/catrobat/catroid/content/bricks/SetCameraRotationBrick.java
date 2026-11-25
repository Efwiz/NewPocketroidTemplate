package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetCameraRotationBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetCameraRotationBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_camera_rotation;
    }

    public SetCameraRotationBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_set_camera_rotation_yaw);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_set_camera_rotation_pitch);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_set_camera_rotation_roll);
    }

    public SetCameraRotationBrick(double y, double p, double r) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(Double.valueOf(y)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(Double.valueOf(p)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(Double.valueOf(r)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetCameraRotationAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3)));
    }
}
