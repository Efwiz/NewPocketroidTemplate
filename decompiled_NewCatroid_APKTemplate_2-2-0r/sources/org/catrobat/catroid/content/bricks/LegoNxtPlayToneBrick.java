package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/LegoNxtPlayToneBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoNxtPlayToneBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_nxt_play_tone;
    }

    public LegoNxtPlayToneBrick() {
        addAllowedBrickField(Brick.BrickField.LEGO_NXT_FREQUENCY, R.id.nxt_tone_freq_edit_text);
        addAllowedBrickField(Brick.BrickField.LEGO_NXT_DURATION_IN_SECONDS, R.id.nxt_tone_duration_edit_text);
    }

    public LegoNxtPlayToneBrick(double frequencyValue, double durationValue) {
        this(new Formula(Double.valueOf(frequencyValue)), new Formula(Double.valueOf(durationValue)));
    }

    public LegoNxtPlayToneBrick(Formula frequencyFormula, Formula durationFormula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.LEGO_NXT_FREQUENCY, frequencyFormula);
        setFormulaWithBrickField(Brick.BrickField.LEGO_NXT_DURATION_IN_SECONDS, durationFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.LEGO_NXT_DURATION_IN_SECONDS;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        setSecondsLabel(this.view, Brick.BrickField.LEGO_NXT_DURATION_IN_SECONDS);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 2);
        super.addRequiredResources(requiredResourcesSet);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createLegoNxtPlayToneAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.LEGO_NXT_FREQUENCY), getFormulaWithBrickField(Brick.BrickField.LEGO_NXT_DURATION_IN_SECONDS)));
    }
}
