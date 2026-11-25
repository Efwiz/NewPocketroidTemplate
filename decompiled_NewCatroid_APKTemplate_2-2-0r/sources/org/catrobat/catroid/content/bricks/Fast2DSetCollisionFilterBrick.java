package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Fast2DSetCollisionFilterBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Fast2DSetCollisionFilterBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_fast2d_set_collision_filter;
    }

    public Fast2DSetCollisionFilterBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_fast2d_col_filter_id);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_fast2d_col_filter_sensor);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_fast2d_col_filter_group);
    }

    public Fast2DSetCollisionFilterBrick(String id2, Double isSensor, Double group) {
        this(new Formula(id2), new Formula(isSensor), new Formula(group));
    }

    public Fast2DSetCollisionFilterBrick(Formula id2, Formula isSensor, Formula group) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, id2);
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, isSensor);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, group);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createFast2DSetCollisionFilterAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION)));
    }
}
