package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Fast2DSetZIndexBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Fast2DSetZIndexBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_fast2d_set_z;
    }

    public Fast2DSetZIndexBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_fast2d_set_z_id);
        addAllowedBrickField(Brick.BrickField.VIBRATE_DURATION, R.id.brick_fast2d_set_z_value);
    }

    public Fast2DSetZIndexBrick(String id2, Double z) {
        this(new Formula(id2), new Formula(z));
    }

    public Fast2DSetZIndexBrick(Formula id2, Formula z) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, id2);
        setFormulaWithBrickField(Brick.BrickField.VIBRATE_DURATION, z);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createFast2DSetZIndexAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.VIBRATE_DURATION)));
    }
}
