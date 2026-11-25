package org.catrobat.catroid.content.bricks;

import android.view.View;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetTextBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetTextBrick extends FormulaBrick implements View.OnClickListener {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_text;
    }

    public SetTextBrick() {
        addAllowedBrickField(Brick.BrickField.X_DESTINATION, R.id.brick_set_text_edit_text_x);
        addAllowedBrickField(Brick.BrickField.Y_DESTINATION, R.id.brick_set_text_edit_text_y);
        addAllowedBrickField(Brick.BrickField.STRING, R.id.brick_set_text_edit_text);
    }

    public SetTextBrick(int xDestinationValue, int yDestinationValue, String text) {
        this(new Formula(Integer.valueOf(xDestinationValue)), new Formula(Integer.valueOf(yDestinationValue)), new Formula(text));
    }

    public SetTextBrick(Formula xDestination, Formula yDestination, Formula text) {
        this();
        setFormulaWithBrickField(Brick.BrickField.X_DESTINATION, xDestination);
        setFormulaWithBrickField(Brick.BrickField.Y_DESTINATION, yDestination);
        setFormulaWithBrickField(Brick.BrickField.STRING, text);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetTextAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.X_DESTINATION), getFormulaWithBrickField(Brick.BrickField.Y_DESTINATION), getFormulaWithBrickField(Brick.BrickField.STRING)));
    }
}
