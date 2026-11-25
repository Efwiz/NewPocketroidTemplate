package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/RemoveJointBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RemoveJointBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_remove_joint;
    }

    public RemoveJointBrick() {
        addAllowedBrickField(Brick.BrickField.JOINT_NAME, R.id.brick_remove_joint_name_edit);
    }

    public RemoveJointBrick(String jointName) {
        this(new Formula(jointName));
    }

    public RemoveJointBrick(Formula jointNameFormula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.JOINT_NAME, jointNameFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createRemoveJointAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.JOINT_NAME)));
    }
}
