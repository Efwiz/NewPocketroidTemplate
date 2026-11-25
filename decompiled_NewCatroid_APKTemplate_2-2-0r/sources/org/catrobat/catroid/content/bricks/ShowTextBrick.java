package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.utils.ShowTextUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ShowTextBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ShowTextBrick extends UserVariableBrickWithVisualPlacement {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithVisualPlacement
    protected int getSpinnerId() {
        return R.id.show_variable_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_show_variable;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public int getXEditTextId() {
        return R.id.brick_show_variable_edit_text_x;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public int getYEditTextId() {
        return R.id.brick_show_variable_edit_text_y;
    }

    public ShowTextBrick() {
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_show_variable_edit_text_x);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_show_variable_edit_text_y);
    }

    public ShowTextBrick(int xPosition, int yPosition) {
        this(new Formula(Integer.valueOf(xPosition)), new Formula(Integer.valueOf(yPosition)));
    }

    public ShowTextBrick(Formula xPosition, Formula yPosition) {
        this();
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, xPosition);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, yPosition);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.X_POSITION;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        if (this.userVariable == null || this.userVariable.getName() == null) {
            this.userVariable = new UserVariable("NoVariableSet", CatroidApplication.getAppContext().getString(R.string.no_variable_selected));
            this.userVariable.setDummy(true);
        }
        sequence.addAction(sprite.getActionFactory().createShowVariableAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), this.userVariable, new ShowTextUtils.AndroidStringProvider(CatroidApplication.getAppContext())));
    }
}
