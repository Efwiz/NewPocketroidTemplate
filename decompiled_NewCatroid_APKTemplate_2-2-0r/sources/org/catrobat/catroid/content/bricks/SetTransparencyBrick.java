package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetTransparencyBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetTransparencyBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_transparency;
    }

    public SetTransparencyBrick() {
        addAllowedBrickField(Brick.BrickField.TRANSPARENCY, R.id.brick_set_transparency_to_edit_text);
    }

    public SetTransparencyBrick(double transparency) {
        this(new Formula(Double.valueOf(transparency)));
    }

    public SetTransparencyBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TRANSPARENCY, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetTransparencyAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TRANSPARENCY)));
    }
}
