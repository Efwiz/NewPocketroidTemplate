package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/MouseEventBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MouseEventBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_mouse_event;
    }

    public MouseEventBrick() {
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_mouse_x_edit);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_mouse_y_edit);
        addAllowedBrickField(Brick.BrickField.VALUE, R.id.brick_mouse_state_edit);
    }

    public MouseEventBrick(String x, String y, int state) {
        this(new Formula(x), new Formula(y), new Formula(Integer.valueOf(state)));
    }

    public MouseEventBrick(Formula x, Formula y, Formula state) {
        this();
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, x);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, y);
        setFormulaWithBrickField(Brick.BrickField.VALUE, state);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createMouseEventAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), getFormulaWithBrickField(Brick.BrickField.VALUE)));
    }
}
