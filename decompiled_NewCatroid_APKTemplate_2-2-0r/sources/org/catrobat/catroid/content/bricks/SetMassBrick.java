package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetMassBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetMassBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_physics_set_mass;
    }

    public SetMassBrick() {
        addAllowedBrickField(Brick.BrickField.PHYSICS_MASS, R.id.brick_set_mass_edit_text);
    }

    public SetMassBrick(double mass) {
        this(new Formula(Double.valueOf(mass)));
    }

    public SetMassBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.PHYSICS_MASS, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 3);
        super.addRequiredResources(requiredResourcesSet);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetMassAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.PHYSICS_MASS)));
    }
}
