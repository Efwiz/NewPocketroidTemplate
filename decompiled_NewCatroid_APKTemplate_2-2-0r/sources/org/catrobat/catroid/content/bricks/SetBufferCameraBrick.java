package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetBufferCameraBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetBufferCameraBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_buffer_cam;
    }

    public SetBufferCameraBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_buffer_name);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_buffer_x);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_buffer_y);
        addAllowedBrickField(Brick.BrickField.ZOOM, R.id.brick_buffer_zoom);
        addAllowedBrickField(Brick.BrickField.DEGREES, R.id.brick_buffer_rot);
    }

    public SetBufferCameraBrick(String n, String x, String y, String z, String r) {
        this(new Formula(n), new Formula(x), new Formula(y), new Formula(z), new Formula(r));
    }

    public SetBufferCameraBrick(Formula n, Formula x, Formula y, Formula z, Formula r) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, n);
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, x);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, y);
        setFormulaWithBrickField(Brick.BrickField.ZOOM, z);
        setFormulaWithBrickField(Brick.BrickField.DEGREES, r);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetBufferCameraAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), getFormulaWithBrickField(Brick.BrickField.ZOOM), getFormulaWithBrickField(Brick.BrickField.DEGREES)));
    }
}
