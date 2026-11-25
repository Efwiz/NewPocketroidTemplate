package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetViewPositionBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetViewPositionBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_view_position;
    }

    public SetViewPositionBrick() {
        addAllowedBrickField(Brick.BrickField.VIEW_ID, R.id.brick_set_view_position_id);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_set_view_position_x);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_set_view_position_y);
    }

    public SetViewPositionBrick(String viewId, int x, int y) {
        this(new Formula(viewId), new Formula(Integer.valueOf(x)), new Formula(Integer.valueOf(y)));
    }

    public SetViewPositionBrick(Formula viewId, Formula x, Formula y) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VIEW_ID, viewId);
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, x);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, y);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetViewPositionAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VIEW_ID), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION)));
    }
}
