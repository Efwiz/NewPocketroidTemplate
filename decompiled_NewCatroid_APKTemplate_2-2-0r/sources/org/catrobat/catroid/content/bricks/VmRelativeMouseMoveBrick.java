package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/VmRelativeMouseMoveBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class VmRelativeMouseMoveBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_vm_mouse_relative;
    }

    public VmRelativeMouseMoveBrick() {
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_vm_mouse_edit_dx);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_vm_mouse_edit_dy);
        addAllowedBrickField(Brick.BrickField.MASK, R.id.brick_vm_mouse_mask);
    }

    public VmRelativeMouseMoveBrick(int dx, int dy, int mask) {
        this(new Formula(Integer.valueOf(dx)), new Formula(Integer.valueOf(dy)), new Formula(Integer.valueOf(mask)));
    }

    public VmRelativeMouseMoveBrick(Formula dx, Formula dy, Formula mask) {
        this();
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, dx);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, dy);
        setFormulaWithBrickField(Brick.BrickField.MASK, mask);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createVmRelativeMouseMoveAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), getFormulaWithBrickField(Brick.BrickField.MASK)));
    }
}
