package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ApplyTorqueBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ApplyTorqueBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_apply_torque;
    }

    public ApplyTorqueBrick() {
        addAllowedBrickField(Brick.BrickField.TORQUE, R.id.brick_torque_edit_text);
    }

    public ApplyTorqueBrick(int torque) {
        this(new Formula(Integer.valueOf(torque)));
    }

    public ApplyTorqueBrick(Formula torque) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TORQUE, torque);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createApplyTorqueAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TORQUE)));
    }
}
