package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetHeightBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetHeightBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_height;
    }

    public SetHeightBrick() {
        addAllowedBrickField(Brick.BrickField.SIZE, R.id.brick_set_height_edit_text);
    }

    public SetHeightBrick(double size) {
        this(new Formula(Double.valueOf(size)));
    }

    public SetHeightBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.SIZE, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetHeightAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.SIZE)));
    }
}
