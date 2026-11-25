package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetThirdPersonCameraBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetThirdPersonCameraBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_third_person_camera;
    }

    public SetThirdPersonCameraBrick() {
        addAllowedBrickField(Brick.BrickField.OBJECT_ID, R.id.brick_third_person_camera_object_id);
        addAllowedBrickField(Brick.BrickField.DISTANCE, R.id.brick_third_person_camera_distance);
        addAllowedBrickField(Brick.BrickField.HEIGHT, R.id.brick_third_person_camera_height);
        addAllowedBrickField(Brick.BrickField.PITCH, R.id.brick_third_person_camera_pitch);
    }

    public SetThirdPersonCameraBrick(String objectId, double distance, double height, double pitch) {
        this(new Formula(objectId), new Formula(Double.valueOf(distance)), new Formula(Double.valueOf(height)), new Formula(Double.valueOf(pitch)));
    }

    public SetThirdPersonCameraBrick(Formula objectId, Formula distance, Formula height, Formula pitch) {
        this();
        setFormulaWithBrickField(Brick.BrickField.OBJECT_ID, objectId);
        setFormulaWithBrickField(Brick.BrickField.DISTANCE, distance);
        setFormulaWithBrickField(Brick.BrickField.HEIGHT, height);
        setFormulaWithBrickField(Brick.BrickField.PITCH, pitch);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetThirdPersonCameraAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.OBJECT_ID), getFormulaWithBrickField(Brick.BrickField.DISTANCE), getFormulaWithBrickField(Brick.BrickField.HEIGHT), getFormulaWithBrickField(Brick.BrickField.PITCH)));
    }
}
