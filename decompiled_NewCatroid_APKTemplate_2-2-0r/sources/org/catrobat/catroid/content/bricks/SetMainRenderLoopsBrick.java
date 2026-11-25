package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetMainRenderLoopsBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetMainRenderLoopsBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_main_render_loops;
    }

    public SetMainRenderLoopsBrick() {
        addAllowedBrickField(Brick.BrickField.IF_CONDITION, R.id.brick_render_loop_2d);
        addAllowedBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, R.id.brick_render_loop_fast2d);
        addAllowedBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, R.id.brick_render_loop_3d);
    }

    public SetMainRenderLoopsBrick(float r2d, float rfast2d, float r3d) {
        this();
        setFormulaWithBrickField(Brick.BrickField.IF_CONDITION, new Formula(Float.valueOf(r2d)));
        setFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, new Formula(Float.valueOf(rfast2d)));
        setFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, new Formula(Float.valueOf(r3d)));
    }

    public SetMainRenderLoopsBrick(Formula r2d, Formula rfast2d, Formula r3d) {
        this();
        setFormulaWithBrickField(Brick.BrickField.IF_CONDITION, r2d);
        setFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, rfast2d);
        setFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, r3d);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetMainRenderLoopsAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.IF_CONDITION), getFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE), getFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE)));
    }
}
