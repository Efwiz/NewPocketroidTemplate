package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetBufferCamera3DBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetBufferCamera3DBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_buffer_cam_3d;
    }

    public SetBufferCamera3DBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_buffer_cam_3d_name);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_buffer_cam_3d_x);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_buffer_cam_3d_y);
        addAllowedBrickField(Brick.BrickField.Z_POSITION, R.id.brick_buffer_cam_3d_z);
        addAllowedBrickField(Brick.BrickField.DEGREES, R.id.brick_buffer_cam_3d_yaw);
        addAllowedBrickField(Brick.BrickField.DEGREES_BY, R.id.brick_buffer_cam_3d_pitch);
        addAllowedBrickField(Brick.BrickField.ROTATION, R.id.brick_buffer_cam_3d_roll);
        addAllowedBrickField(Brick.BrickField.SIZE, R.id.brick_buffer_cam_3d_fov);
    }

    public SetBufferCamera3DBrick(Formula name2, Formula x, Formula y, Formula z, Formula yaw, Formula pitch, Formula roll, Formula fov) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, name2);
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, x);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, y);
        setFormulaWithBrickField(Brick.BrickField.Z_POSITION, z);
        setFormulaWithBrickField(Brick.BrickField.DEGREES, yaw);
        setFormulaWithBrickField(Brick.BrickField.DEGREES_BY, pitch);
        setFormulaWithBrickField(Brick.BrickField.ROTATION, roll);
        setFormulaWithBrickField(Brick.BrickField.SIZE, fov);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetBufferCamera3DAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), getFormulaWithBrickField(Brick.BrickField.Z_POSITION), getFormulaWithBrickField(Brick.BrickField.DEGREES), getFormulaWithBrickField(Brick.BrickField.DEGREES_BY), getFormulaWithBrickField(Brick.BrickField.ROTATION), getFormulaWithBrickField(Brick.BrickField.SIZE)));
    }
}
