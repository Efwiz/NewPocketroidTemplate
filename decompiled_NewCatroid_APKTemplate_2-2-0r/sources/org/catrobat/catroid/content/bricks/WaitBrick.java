package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/WaitBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WaitBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_wait;
    }

    public WaitBrick() {
        addAllowedBrickField(Brick.BrickField.TIME_TO_WAIT_IN_SECONDS, R.id.brick_wait_edit_text);
    }

    public WaitBrick(int timeToWaitInMillisecondsValue) {
        this(new Formula(Double.valueOf(((double) timeToWaitInMillisecondsValue) / 1000.0d)));
    }

    public WaitBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TIME_TO_WAIT_IN_SECONDS, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        setSecondsLabel(this.view, Brick.BrickField.TIME_TO_WAIT_IN_SECONDS);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createWaitAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TIME_TO_WAIT_IN_SECONDS)));
    }
}
