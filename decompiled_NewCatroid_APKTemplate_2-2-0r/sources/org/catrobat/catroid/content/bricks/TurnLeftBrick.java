package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/TurnLeftBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class TurnLeftBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_turn_left;
    }

    public TurnLeftBrick() {
        addAllowedBrickField(Brick.BrickField.TURN_LEFT_DEGREES, R.id.brick_turn_left_edit_text);
    }

    public TurnLeftBrick(double degreesValue) {
        this(new Formula(Double.valueOf(degreesValue)));
    }

    public TurnLeftBrick(Formula formula) {
        addAllowedBrickField(Brick.BrickField.TURN_LEFT_DEGREES, R.id.brick_turn_left_edit_text);
        setFormulaWithBrickField(Brick.BrickField.TURN_LEFT_DEGREES, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createTurnLeftAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TURN_LEFT_DEGREES)));
    }
}
