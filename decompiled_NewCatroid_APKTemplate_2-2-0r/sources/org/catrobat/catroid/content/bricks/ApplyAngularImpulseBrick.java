package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ApplyAngularImpulseBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ApplyAngularImpulseBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_apply_angular_impulse;
    }

    public ApplyAngularImpulseBrick() {
        addAllowedBrickField(Brick.BrickField.ANGULAR_IMPULSE, R.id.brick_angular_impulse_edit_text);
    }

    public ApplyAngularImpulseBrick(int impulse) {
        this(new Formula(Integer.valueOf(impulse)));
    }

    public ApplyAngularImpulseBrick(Formula impulse) {
        this();
        setFormulaWithBrickField(Brick.BrickField.ANGULAR_IMPULSE, impulse);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createApplyAngularImpulseAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.ANGULAR_IMPULSE)));
    }
}
