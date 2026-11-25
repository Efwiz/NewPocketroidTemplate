package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ChangeYByNBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ChangeYByNBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_change_y;
    }

    public ChangeYByNBrick() {
        addAllowedBrickField(Brick.BrickField.Y_POSITION_CHANGE, R.id.brick_change_y_edit_text);
    }

    public ChangeYByNBrick(int yMovementValue) {
        this(new Formula(Integer.valueOf(yMovementValue)));
    }

    public ChangeYByNBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION_CHANGE, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createChangeYByNAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.Y_POSITION_CHANGE)));
    }
}
