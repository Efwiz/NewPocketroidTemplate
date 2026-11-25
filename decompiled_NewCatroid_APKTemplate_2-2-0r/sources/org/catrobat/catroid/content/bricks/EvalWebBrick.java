package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/EvalWebBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EvalWebBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_eval_web;
    }

    public EvalWebBrick() {
        addAllowedBrickField(Brick.BrickField.CODE, R.id.brick_eval_web_js);
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_eval_web_name);
    }

    public EvalWebBrick(String js, String name2) {
        this(new Formula(js), new Formula(name2));
    }

    public EvalWebBrick(Formula formula, Formula formula2) {
        this();
        setFormulaWithBrickField(Brick.BrickField.CODE, formula);
        setFormulaWithBrickField(Brick.BrickField.NAME, formula2);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().evalWebAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.CODE), getFormulaWithBrickField(Brick.BrickField.NAME)));
    }
}
