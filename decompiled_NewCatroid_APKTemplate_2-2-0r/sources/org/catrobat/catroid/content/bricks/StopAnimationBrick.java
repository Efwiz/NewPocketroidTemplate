package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/StopAnimationBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class StopAnimationBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_stop_animation;
    }

    public StopAnimationBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_stop_animation_object_id_value);
    }

    public StopAnimationBrick(String objectId) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(objectId));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createStopAnimationAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1)));
    }
}
