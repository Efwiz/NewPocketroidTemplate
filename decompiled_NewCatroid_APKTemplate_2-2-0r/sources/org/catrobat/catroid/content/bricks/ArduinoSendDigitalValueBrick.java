package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ArduinoSendDigitalValueBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ArduinoSendDigitalValueBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_arduino_send_digital;
    }

    public ArduinoSendDigitalValueBrick() {
        addAllowedBrickField(Brick.BrickField.ARDUINO_DIGITAL_PIN_NUMBER, R.id.brick_arduino_set_digital_pin_edit_text);
        addAllowedBrickField(Brick.BrickField.ARDUINO_DIGITAL_PIN_VALUE, R.id.brick_arduino_set_digital_value_edit_text);
    }

    public ArduinoSendDigitalValueBrick(int pinNumber, int pinValue) {
        this(new Formula(Integer.valueOf(pinNumber)), new Formula(Integer.valueOf(pinValue)));
    }

    public ArduinoSendDigitalValueBrick(Formula pinNumber, Formula pinValue) {
        this();
        setFormulaWithBrickField(Brick.BrickField.ARDUINO_DIGITAL_PIN_NUMBER, pinNumber);
        setFormulaWithBrickField(Brick.BrickField.ARDUINO_DIGITAL_PIN_VALUE, pinValue);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 6);
        super.addRequiredResources(requiredResourcesSet);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.ARDUINO_DIGITAL_PIN_NUMBER;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSendDigitalArduinoValueAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.ARDUINO_DIGITAL_PIN_NUMBER), getFormulaWithBrickField(Brick.BrickField.ARDUINO_DIGITAL_PIN_VALUE)));
    }
}
