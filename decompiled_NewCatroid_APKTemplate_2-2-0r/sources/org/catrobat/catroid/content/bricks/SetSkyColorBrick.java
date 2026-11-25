package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetSkyColorBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetSkyColorBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_sky_color;
    }

    public SetSkyColorBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_set_sky_color_r);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_set_sky_color_g);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_set_sky_color_b);
    }

    public SetSkyColorBrick(double r, double g, double b) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(Double.valueOf(r)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(Double.valueOf(g)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(Double.valueOf(b)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetSkyColorAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3)));
    }
}
