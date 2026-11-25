package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Fast2DSetPositionBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Fast2DSetPositionBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_fast2d_set_position;
    }

    public Fast2DSetPositionBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_fast2d_set_pos_edit_id);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_fast2d_set_pos_edit_x);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_fast2d_set_pos_edit_y);
    }

    public Fast2DSetPositionBrick(String entityId, Double x, Double y) {
        this(new Formula(entityId), new Formula(x), new Formula(y));
    }

    public Fast2DSetPositionBrick(Formula entityId, Formula x, Formula y) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, entityId);
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, x);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, y);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createFast2DSetPositionAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION)));
    }
}
