package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CameraLookAtBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CameraLookAtBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_camera_look_at;
    }

    public CameraLookAtBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE, R.id.brick_camera_look_at_x);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_camera_look_at_y);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_camera_look_at_z);
    }

    public CameraLookAtBrick(double x, double y, double z) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE, new Formula(Double.valueOf(x)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(Double.valueOf(y)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(Double.valueOf(z)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createCameraLookAtAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3)));
    }
}
