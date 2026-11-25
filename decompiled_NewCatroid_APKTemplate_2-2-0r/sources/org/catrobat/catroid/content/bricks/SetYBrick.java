package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetYBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetYBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_y;
    }

    public SetYBrick() {
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_set_y_edit_text);
    }

    public SetYBrick(int yPositionValue) {
        this(new Formula(Integer.valueOf(yPositionValue)));
    }

    public SetYBrick(Formula yPosition) {
        this();
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, yPosition);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetYAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.Y_POSITION)));
    }
}
