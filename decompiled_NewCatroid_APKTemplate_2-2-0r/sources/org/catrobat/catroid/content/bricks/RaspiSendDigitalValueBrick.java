package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/RaspiSendDigitalValueBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RaspiSendDigitalValueBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_raspi_send_digital;
    }

    public RaspiSendDigitalValueBrick() {
        addAllowedBrickField(Brick.BrickField.RASPI_DIGITAL_PIN_NUMBER, R.id.brick_raspi_set_digital_pin_edit_text);
        addAllowedBrickField(Brick.BrickField.RASPI_DIGITAL_PIN_VALUE, R.id.brick_raspi_set_digital_value_edit_text);
    }

    public RaspiSendDigitalValueBrick(int pinNumber, int pinValue) {
        this(new Formula(Integer.valueOf(pinNumber)), new Formula(Integer.valueOf(pinValue)));
    }

    public RaspiSendDigitalValueBrick(Formula pinNumber, Formula pinValue) {
        this();
        setFormulaWithBrickField(Brick.BrickField.RASPI_DIGITAL_PIN_NUMBER, pinNumber);
        setFormulaWithBrickField(Brick.BrickField.RASPI_DIGITAL_PIN_VALUE, pinValue);
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
        sequence.addAction(sprite.getActionFactory().createSendDigitalRaspiValueAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.RASPI_DIGITAL_PIN_NUMBER), getFormulaWithBrickField(Brick.BrickField.RASPI_DIGITAL_PIN_VALUE)));
    }
}
