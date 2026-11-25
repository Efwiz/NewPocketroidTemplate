package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/LegoEv3PlayToneBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoEv3PlayToneBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_ev3_play_tone;
    }

    public LegoEv3PlayToneBrick() {
        addAllowedBrickField(Brick.BrickField.LEGO_EV3_FREQUENCY, R.id.brick_ev3_tone_freq_edit_text);
        addAllowedBrickField(Brick.BrickField.LEGO_EV3_DURATION_IN_SECONDS, R.id.brick_ev3_tone_duration_edit_text);
        addAllowedBrickField(Brick.BrickField.LEGO_EV3_VOLUME, R.id.brick_ev3_tone_volume_edit_text);
    }

    public LegoEv3PlayToneBrick(double frequencyValue, double durationValue, double volumeValue) {
        this(new Formula(Double.valueOf(frequencyValue)), new Formula(Double.valueOf(durationValue)), new Formula(Double.valueOf(volumeValue)));
    }

    public LegoEv3PlayToneBrick(Formula frequencyFormula, Formula durationFormula, Formula volumeFormula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.LEGO_EV3_FREQUENCY, frequencyFormula);
        setFormulaWithBrickField(Brick.BrickField.LEGO_EV3_DURATION_IN_SECONDS, durationFormula);
        setFormulaWithBrickField(Brick.BrickField.LEGO_EV3_VOLUME, volumeFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 20);
        super.addRequiredResources(requiredResourcesSet);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        setSecondsLabel(this.view, Brick.BrickField.LEGO_EV3_DURATION_IN_SECONDS);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.LEGO_EV3_DURATION_IN_SECONDS;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createLegoEv3PlayToneAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.LEGO_EV3_FREQUENCY), getFormulaWithBrickField(Brick.BrickField.LEGO_EV3_DURATION_IN_SECONDS), getFormulaWithBrickField(Brick.BrickField.LEGO_EV3_VOLUME)));
    }
}
