package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/DestroyJointBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DestroyJointBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_destroy_joint;
    }

    public DestroyJointBrick() {
        addAllowedBrickField(Brick.BrickField.JOINT_ID, R.id.brick_joint_id_edit_text);
    }

    public DestroyJointBrick(String jointId) {
        this(new Formula(jointId));
    }

    public DestroyJointBrick(Formula jointId) {
        this();
        setFormulaWithBrickField(Brick.BrickField.JOINT_ID, jointId);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createDestroyJointAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.JOINT_ID)));
    }
}
