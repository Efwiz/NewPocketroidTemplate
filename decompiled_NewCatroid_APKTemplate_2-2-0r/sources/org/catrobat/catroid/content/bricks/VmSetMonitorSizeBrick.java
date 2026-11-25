package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/VmSetMonitorSizeBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class VmSetMonitorSizeBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_vm_monitor_size;
    }

    public VmSetMonitorSizeBrick() {
        addAllowedBrickField(Brick.BrickField.WIDTH, R.id.brick_vm_monitor_edit_width);
        addAllowedBrickField(Brick.BrickField.HEIGHT, R.id.brick_vm_monitor_edit_height);
    }

    public VmSetMonitorSizeBrick(int width, int height) {
        this(new Formula(Integer.valueOf(width)), new Formula(Integer.valueOf(height)));
    }

    public VmSetMonitorSizeBrick(Formula width, Formula height) {
        this();
        setFormulaWithBrickField(Brick.BrickField.WIDTH, width);
        setFormulaWithBrickField(Brick.BrickField.HEIGHT, height);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createVmSetMonitorSizeAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.WIDTH), getFormulaWithBrickField(Brick.BrickField.HEIGHT)));
    }
}
