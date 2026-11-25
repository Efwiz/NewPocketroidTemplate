package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetXBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetXBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_x;
    }

    public SetXBrick() {
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_set_x_edit_text);
    }

    public SetXBrick(int xPositionValue) {
        this(new Formula(Integer.valueOf(xPositionValue)));
    }

    public SetXBrick(Formula xPosition) {
        this();
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, xPosition);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetXAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.X_POSITION)));
    }
}
