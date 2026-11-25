package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetDampingBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetDampingBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_damping;
    }

    public SetDampingBrick() {
        addAllowedBrickField(Brick.BrickField.LINEAR_DAMPING, R.id.brick_linear_damping_edit_text);
        addAllowedBrickField(Brick.BrickField.ANGULAR_DAMPING, R.id.brick_angular_damping_edit_text);
    }

    public SetDampingBrick(float linear, float angular) {
        this(new Formula(Float.valueOf(linear)), new Formula(Float.valueOf(angular)));
    }

    public SetDampingBrick(Formula linear, Formula angular) {
        this();
        setFormulaWithBrickField(Brick.BrickField.LINEAR_DAMPING, linear);
        setFormulaWithBrickField(Brick.BrickField.ANGULAR_DAMPING, angular);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetDampingAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.LINEAR_DAMPING), getFormulaWithBrickField(Brick.BrickField.ANGULAR_DAMPING)));
    }
}
