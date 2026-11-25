package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetNeutralBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetNeutralBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_neural;
    }

    public SetNeutralBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_set_neutral_edit_name);
        addAllowedBrickField(Brick.BrickField.TEXT, R.id.brick_set_neutral_edit_text);
    }

    public SetNeutralBrick(String value, String value2) {
        this(new Formula(value), new Formula(value2));
    }

    public SetNeutralBrick(Formula formula, Formula formula2) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, formula);
        setFormulaWithBrickField(Brick.BrickField.TEXT, formula2);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createNeutralAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.TEXT)));
    }
}
