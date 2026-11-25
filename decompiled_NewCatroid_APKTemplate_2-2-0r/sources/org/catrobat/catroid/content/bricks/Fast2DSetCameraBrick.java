package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Fast2DSetCameraBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Fast2DSetCameraBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_fast2d_set_camera;
    }

    public Fast2DSetCameraBrick() {
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_fast2d_cam_edit_x);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_fast2d_cam_edit_y);
        addAllowedBrickField(Brick.BrickField.SIZE, R.id.brick_fast2d_cam_edit_zoom);
    }

    public Fast2DSetCameraBrick(Double x, Double y, Double zoom) {
        this(new Formula(x), new Formula(y), new Formula(zoom));
    }

    public Fast2DSetCameraBrick(Formula x, Formula y, Formula zoom) {
        this();
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, x);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, y);
        setFormulaWithBrickField(Brick.BrickField.SIZE, zoom);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createFast2DSetCameraAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), getFormulaWithBrickField(Brick.BrickField.SIZE)));
    }
}
