package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ReadBaseBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ReadBaseBrick extends UserVariableBrickWithFormula {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.brick_read_base_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_read_base;
    }

    public ReadBaseBrick() {
        addAllowedBrickField(Brick.BrickField.FIREBASE_ID, R.id.brick_read_base_edit_base);
        addAllowedBrickField(Brick.BrickField.FIREBASE_KEY, R.id.brick_read_base_edit_key);
    }

    public ReadBaseBrick(String base, String key) {
        this(new Formula(base), new Formula(key));
    }

    public ReadBaseBrick(Formula base, Formula key, UserVariable userVariable) {
        this(base, key);
        this.userVariable = userVariable;
    }

    private ReadBaseBrick(Formula base, Formula key) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FIREBASE_ID, base);
        setFormulaWithBrickField(Brick.BrickField.FIREBASE_KEY, key);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createReadBaseAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FIREBASE_ID), getFormulaWithBrickField(Brick.BrickField.FIREBASE_KEY), this.userVariable));
    }
}
