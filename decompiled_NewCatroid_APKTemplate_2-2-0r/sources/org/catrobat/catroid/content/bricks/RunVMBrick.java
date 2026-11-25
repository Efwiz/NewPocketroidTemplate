package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/RunVMBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RunVMBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_run_vm;
    }

    public RunVMBrick() {
        addAllowedBrickField(Brick.BrickField.VM_MEMORY, R.id.brick_memory_edit);
        addAllowedBrickField(Brick.BrickField.VM_CPU, R.id.brick_cpu_edit);
        addAllowedBrickField(Brick.BrickField.VM_HDA, R.id.brick_hda_edit);
        addAllowedBrickField(Brick.BrickField.VM_CDROM, R.id.brick_cdrom_edit);
    }

    public RunVMBrick(String memory, String cpu, String hda, String cdrom) {
        this(new Formula(memory), new Formula(cpu), new Formula(hda), new Formula(cdrom));
    }

    public RunVMBrick(Formula memory, Formula cpu, Formula hda, Formula cdrom) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VM_MEMORY, memory);
        setFormulaWithBrickField(Brick.BrickField.VM_CPU, cpu);
        setFormulaWithBrickField(Brick.BrickField.VM_HDA, hda);
        setFormulaWithBrickField(Brick.BrickField.VM_CDROM, cdrom);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createRunVMAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VM_MEMORY), getFormulaWithBrickField(Brick.BrickField.VM_CPU), getFormulaWithBrickField(Brick.BrickField.VM_HDA), getFormulaWithBrickField(Brick.BrickField.VM_CDROM)));
    }
}
