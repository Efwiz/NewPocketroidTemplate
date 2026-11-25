package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetColorBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetColorBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_color_to;
    }

    public SetColorBrick() {
        addAllowedBrickField(Brick.BrickField.COLOR, R.id.brick_set_color_edit_text);
    }

    public SetColorBrick(double color) {
        this(new Formula(Double.valueOf(color)));
    }

    public SetColorBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.COLOR, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetColorAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.COLOR)));
    }
}
