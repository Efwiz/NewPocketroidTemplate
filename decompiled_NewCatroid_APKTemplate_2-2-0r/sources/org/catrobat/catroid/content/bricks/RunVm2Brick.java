package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/RunVm2Brick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RunVm2Brick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_run_vm2;
    }

    public RunVm2Brick() {
        addAllowedBrickField(Brick.BrickField.VM_ARGUMENTS, R.id.brick_run_vm_arguments_edit_text);
    }

    public RunVm2Brick(String arguments) {
        this(new Formula(arguments));
    }

    public RunVm2Brick(Formula argumentsFormula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VM_ARGUMENTS, argumentsFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createRunVm2Action(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VM_ARGUMENTS)));
    }
}
