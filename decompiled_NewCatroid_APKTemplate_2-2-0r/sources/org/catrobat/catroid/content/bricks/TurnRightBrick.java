package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/TurnRightBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class TurnRightBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_turn_right;
    }

    public TurnRightBrick() {
        addAllowedBrickField(Brick.BrickField.TURN_RIGHT_DEGREES, R.id.brick_turn_right_edit_text);
    }

    public TurnRightBrick(double degreesValue) {
        this(new Formula(Double.valueOf(degreesValue)));
    }

    public TurnRightBrick(Formula formula) {
        addAllowedBrickField(Brick.BrickField.TURN_RIGHT_DEGREES, R.id.brick_turn_right_edit_text);
        setFormulaWithBrickField(Brick.BrickField.TURN_RIGHT_DEGREES, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createTurnRightAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TURN_RIGHT_DEGREES)));
    }
}
