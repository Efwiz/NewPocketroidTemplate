package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PlayAnimationBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PlayAnimationBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_play_animation;
    }

    public PlayAnimationBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_play_animation_object_id_value);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_play_animation_name_value);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_play_animation_loops_value);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_play_animation_speed_value);
        addAllowedBrickField(Brick.BrickField.VALUE_5, R.id.brick_play_animation_transition_value);
    }

    public PlayAnimationBrick(String objectId, String animationName, int loops, double speed, double transitionTime) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(objectId));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(animationName));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(Integer.valueOf(loops)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, new Formula(Double.valueOf(speed)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_5, new Formula(Double.valueOf(transitionTime)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPlayAnimationAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4), getFormulaWithBrickField(Brick.BrickField.VALUE_5)));
    }
}
