package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/RaspiPwmBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RaspiPwmBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_raspi_pwm;
    }

    public RaspiPwmBrick() {
        addAllowedBrickField(Brick.BrickField.RASPI_DIGITAL_PIN_NUMBER, R.id.brick_raspi_pwm_pin_edit_text);
        addAllowedBrickField(Brick.BrickField.RASPI_PWM_FREQUENCY, R.id.brick_raspi_pwm_frequency_edit_text);
        addAllowedBrickField(Brick.BrickField.RASPI_PWM_PERCENTAGE, R.id.brick_raspi_pwm_percentage_edit_text);
    }

    public RaspiPwmBrick(int pinNumber, double pwmFrequency, double pwmPercentage) {
        this(new Formula(Integer.valueOf(pinNumber)), new Formula(Double.valueOf(pwmFrequency)), new Formula(Double.valueOf(pwmPercentage)));
    }

    public RaspiPwmBrick(Formula pinNumber, Formula pwmFrequency, Formula pwmPercentage) {
        this();
        setFormulaWithBrickField(Brick.BrickField.RASPI_DIGITAL_PIN_NUMBER, pinNumber);
        setFormulaWithBrickField(Brick.BrickField.RASPI_PWM_FREQUENCY, pwmFrequency);
        setFormulaWithBrickField(Brick.BrickField.RASPI_PWM_PERCENTAGE, pwmPercentage);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 7);
        super.addRequiredResources(requiredResourcesSet);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.RASPI_DIGITAL_PIN_NUMBER;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSendRaspiPwmValueAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.RASPI_DIGITAL_PIN_NUMBER), getFormulaWithBrickField(Brick.BrickField.RASPI_PWM_FREQUENCY), getFormulaWithBrickField(Brick.BrickField.RASPI_PWM_PERCENTAGE)));
    }
}
