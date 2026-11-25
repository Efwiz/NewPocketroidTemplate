package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SplitBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SplitBrick extends UserListBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserListBrick
    protected int getSpinnerId() {
        return R.id.split_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_split;
    }

    public SplitBrick() {
        addAllowedBrickField(Brick.BrickField.TEXT, R.id.brick_split_edit_text);
        addAllowedBrickField(Brick.BrickField.VALUE, R.id.brick_split_edit_simbol);
    }

    public SplitBrick(String value, String value2) {
        this(new Formula(value), new Formula(value2));
    }

    public SplitBrick(Formula formula, Formula formula2) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TEXT, formula);
        setFormulaWithBrickField(Brick.BrickField.VALUE, formula2);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSplitAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TEXT), getFormulaWithBrickField(Brick.BrickField.VALUE), this.userList));
    }
}
