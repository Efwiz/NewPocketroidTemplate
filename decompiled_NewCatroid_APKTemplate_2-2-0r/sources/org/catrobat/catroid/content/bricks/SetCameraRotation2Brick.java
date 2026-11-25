package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetCameraRotation2Brick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetCameraRotation2Brick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_camera_rotation2;
    }

    public SetCameraRotation2Brick() {
        addAllowedBrickField(Brick.BrickField.DEGREES, R.id.brick_set_camera_rotation_value);
    }

    public SetCameraRotation2Brick(double degrees) {
        this(new Formula(Double.valueOf(degrees)));
    }

    public SetCameraRotation2Brick(Formula degrees) {
        this();
        setFormulaWithBrickField(Brick.BrickField.DEGREES, degrees);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetCameraRotationAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.DEGREES)));
    }
}
