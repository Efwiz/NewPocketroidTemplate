package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetAIBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetAIBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_ai;
    }

    public SetAIBrick() {
        addAllowedBrickField(Brick.BrickField.OBJECT_ID, R.id.brick_ai_obj_id);
        addAllowedBrickField(Brick.BrickField.MODE, R.id.brick_ai_mode);
        addAllowedBrickField(Brick.BrickField.TARGET, R.id.brick_ai_target);
        addAllowedBrickField(Brick.BrickField.SPEED, R.id.brick_ai_speed);
        addAllowedBrickField(Brick.BrickField.DISTANCE, R.id.brick_ai_stop);
        addAllowedBrickField(Brick.BrickField.RANGE, R.id.brick_ai_range);
        addAllowedBrickField(Brick.BrickField.STEP_HEIGHT, R.id.brick_ai_step);
        addAllowedBrickField(Brick.BrickField.AVOID_OBSTACLES, R.id.brick_ai_avoid);
    }

    public SetAIBrick(String objId, int mode, String target, float speed, float stop, float range, float step, int avoid) {
        this(new Formula(objId), new Formula(Integer.valueOf(mode)), new Formula(target), new Formula(Float.valueOf(speed)), new Formula(Float.valueOf(stop)), new Formula(Float.valueOf(range)), new Formula(Float.valueOf(step)), new Formula(Integer.valueOf(avoid)));
    }

    public SetAIBrick(Formula objId, Formula mode, Formula target, Formula speed, Formula stop, Formula range, Formula step, Formula avoid) {
        this();
        setFormulaWithBrickField(Brick.BrickField.OBJECT_ID, objId);
        setFormulaWithBrickField(Brick.BrickField.MODE, mode);
        setFormulaWithBrickField(Brick.BrickField.TARGET, target);
        setFormulaWithBrickField(Brick.BrickField.SPEED, speed);
        setFormulaWithBrickField(Brick.BrickField.DISTANCE, stop);
        setFormulaWithBrickField(Brick.BrickField.RANGE, range);
        setFormulaWithBrickField(Brick.BrickField.STEP_HEIGHT, step);
        setFormulaWithBrickField(Brick.BrickField.AVOID_OBSTACLES, avoid);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetAIAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.OBJECT_ID), getFormulaWithBrickField(Brick.BrickField.MODE), getFormulaWithBrickField(Brick.BrickField.TARGET), getFormulaWithBrickField(Brick.BrickField.SPEED), getFormulaWithBrickField(Brick.BrickField.DISTANCE), getFormulaWithBrickField(Brick.BrickField.RANGE), getFormulaWithBrickField(Brick.BrickField.STEP_HEIGHT), getFormulaWithBrickField(Brick.BrickField.AVOID_OBSTACLES)));
    }
}
