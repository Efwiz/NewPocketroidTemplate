package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CutLookBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CutLookBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_cut_look;
    }

    public CutLookBrick() {
        addAllowedBrickField(Brick.BrickField.X1, R.id.brick_cut_look_fromx);
        addAllowedBrickField(Brick.BrickField.Y1, R.id.brick_cut_look_fromy);
        addAllowedBrickField(Brick.BrickField.X2, R.id.brick_cut_look_tox);
        addAllowedBrickField(Brick.BrickField.Y2, R.id.brick_cut_look_toy);
    }

    public CutLookBrick(Integer x1, Integer y1, Integer x2, Integer y2) {
        this(new Formula(x1), new Formula(y1), new Formula(x2), new Formula(y2));
    }

    public CutLookBrick(Formula x1, Formula y1, Formula x2, Formula y2) {
        this();
        setFormulaWithBrickField(Brick.BrickField.X1, x1);
        setFormulaWithBrickField(Brick.BrickField.Y1, y1);
        setFormulaWithBrickField(Brick.BrickField.X2, x2);
        setFormulaWithBrickField(Brick.BrickField.Y2, y2);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createCutLook(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.X1), getFormulaWithBrickField(Brick.BrickField.Y1), getFormulaWithBrickField(Brick.BrickField.X2), getFormulaWithBrickField(Brick.BrickField.Y2)));
    }
}
