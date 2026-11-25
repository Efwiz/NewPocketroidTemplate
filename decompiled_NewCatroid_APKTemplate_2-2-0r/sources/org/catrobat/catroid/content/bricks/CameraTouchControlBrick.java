package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CameraTouchControlBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CameraTouchControlBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_camera_touch_control;
    }

    public CameraTouchControlBrick() {
        addAllowedBrickField(Brick.BrickField.ENABLED, R.id.cam_enabled);
        addAllowedBrickField(Brick.BrickField.SENSITIVITY, R.id.cam_sens);
        addAllowedBrickField(Brick.BrickField.X, R.id.cam_area_x);
        addAllowedBrickField(Brick.BrickField.Y, R.id.cam_area_y);
        addAllowedBrickField(Brick.BrickField.WIDTH, R.id.cam_area_w);
        addAllowedBrickField(Brick.BrickField.HEIGHT, R.id.cam_area_h);
    }

    public CameraTouchControlBrick(int enabled, float sensitivity, float x, float y, float w, float h) {
        this(new Formula(Integer.valueOf(enabled)), new Formula(Float.valueOf(sensitivity)), new Formula(Float.valueOf(x)), new Formula(Float.valueOf(y)), new Formula(Float.valueOf(w)), new Formula(Float.valueOf(h)));
    }

    public CameraTouchControlBrick(Formula enabled, Formula sensitivity, Formula x, Formula y, Formula w, Formula h) {
        this();
        setFormulaWithBrickField(Brick.BrickField.ENABLED, enabled);
        setFormulaWithBrickField(Brick.BrickField.SENSITIVITY, sensitivity);
        setFormulaWithBrickField(Brick.BrickField.X, x);
        setFormulaWithBrickField(Brick.BrickField.Y, y);
        setFormulaWithBrickField(Brick.BrickField.WIDTH, w);
        setFormulaWithBrickField(Brick.BrickField.HEIGHT, h);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createCameraTouchControlAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.ENABLED), getFormulaWithBrickField(Brick.BrickField.SENSITIVITY), getFormulaWithBrickField(Brick.BrickField.X), getFormulaWithBrickField(Brick.BrickField.Y), getFormulaWithBrickField(Brick.BrickField.WIDTH), getFormulaWithBrickField(Brick.BrickField.HEIGHT)));
    }
}
