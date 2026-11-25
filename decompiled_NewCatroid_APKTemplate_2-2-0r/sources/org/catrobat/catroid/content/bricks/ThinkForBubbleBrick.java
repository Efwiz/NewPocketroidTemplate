package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.utils.ShowTextUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ThinkForBubbleBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ThinkForBubbleBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_think_for_bubble;
    }

    public ThinkForBubbleBrick() {
        addAllowedBrickField(Brick.BrickField.STRING, R.id.brick_for_bubble_edit_text_text);
        addAllowedBrickField(Brick.BrickField.DURATION_IN_SECONDS, R.id.brick_for_bubble_edit_text_duration);
    }

    public ThinkForBubbleBrick(String text, float durationInSecondsValue) {
        this(new Formula(text), new Formula(Float.valueOf(durationInSecondsValue)));
    }

    public ThinkForBubbleBrick(Formula text, Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.STRING, text);
        setFormulaWithBrickField(Brick.BrickField.DURATION_IN_SECONDS, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.STRING;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        setSecondsLabel(this.view, Brick.BrickField.DURATION_IN_SECONDS);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createThinkSayForBubbleAction(sprite, sequence, new ShowTextUtils.AndroidStringProvider(CatroidApplication.getAppContext()), getFormulaWithBrickField(Brick.BrickField.STRING), 1));
        sequence.addAction(sprite.getActionFactory().createWaitForBubbleBrickAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.DURATION_IN_SECONDS)));
    }
}
