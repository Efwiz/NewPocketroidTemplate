package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/BakeByPrefixBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BakeByPrefixBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_bake_by_prefix;
    }

    public BakeByPrefixBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_bake_prefix_edit);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_bake_result_edit);
    }

    public BakeByPrefixBrick(String prefix, String resultName) {
        this(new Formula(prefix), new Formula(resultName));
    }

    public BakeByPrefixBrick(Formula prefix, Formula resultName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, prefix);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, resultName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createBakeByPrefixAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2)));
    }
}
