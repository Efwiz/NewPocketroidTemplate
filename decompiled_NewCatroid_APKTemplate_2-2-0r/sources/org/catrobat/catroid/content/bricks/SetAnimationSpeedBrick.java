package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetAnimationSpeedBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetAnimationSpeedBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_animation_speed;
    }

    public SetAnimationSpeedBrick() {
        addAllowedBrickField(Brick.BrickField.OBJECT_ID, R.id.anim_obj_id);
        addAllowedBrickField(Brick.BrickField.SPEED, R.id.anim_speed);
    }

    public SetAnimationSpeedBrick(String id2, float speed) {
        this(new Formula(id2), new Formula(Float.valueOf(speed)));
    }

    public SetAnimationSpeedBrick(Formula id2, Formula speed) {
        this();
        setFormulaWithBrickField(Brick.BrickField.OBJECT_ID, id2);
        setFormulaWithBrickField(Brick.BrickField.SPEED, speed);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetAnimationSpeedAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.OBJECT_ID), getFormulaWithBrickField(Brick.BrickField.SPEED)));
    }
}
