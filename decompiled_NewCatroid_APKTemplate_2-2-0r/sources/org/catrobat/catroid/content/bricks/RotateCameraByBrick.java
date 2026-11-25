package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/RotateCameraByBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RotateCameraByBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_rotate_camera_by;
    }

    public RotateCameraByBrick() {
        addAllowedBrickField(Brick.BrickField.PITCH, R.id.rotate_pitch_edit);
        addAllowedBrickField(Brick.BrickField.YAW, R.id.rotate_yaw_edit);
        addAllowedBrickField(Brick.BrickField.ROLL, R.id.rotate_roll_edit);
    }

    public RotateCameraByBrick(float pitch, float yaw, float roll) {
        this(new Formula(Float.valueOf(pitch)), new Formula(Float.valueOf(yaw)), new Formula(Float.valueOf(roll)));
    }

    public RotateCameraByBrick(Formula pitch, Formula yaw, Formula roll) {
        this();
        setFormulaWithBrickField(Brick.BrickField.PITCH, pitch);
        setFormulaWithBrickField(Brick.BrickField.YAW, yaw);
        setFormulaWithBrickField(Brick.BrickField.ROLL, roll);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createRotateCameraByAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.PITCH), getFormulaWithBrickField(Brick.BrickField.YAW), getFormulaWithBrickField(Brick.BrickField.ROLL)));
    }
}
