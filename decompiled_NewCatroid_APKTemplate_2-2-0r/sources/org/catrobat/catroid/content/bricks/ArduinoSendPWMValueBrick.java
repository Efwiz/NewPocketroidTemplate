package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.Operators;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ArduinoSendPWMValueBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ArduinoSendPWMValueBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_arduino_send_analog;
    }

    public ArduinoSendPWMValueBrick() {
        addAllowedBrickField(Brick.BrickField.ARDUINO_ANALOG_PIN_NUMBER, R.id.brick_arduino_set_analog_pin_edit_text);
        addAllowedBrickField(Brick.BrickField.ARDUINO_ANALOG_PIN_VALUE, R.id.brick_arduino_set_analog_value_edit_text);
    }

    public ArduinoSendPWMValueBrick(int pinNumber, int pinValue) {
        this(new Formula(Integer.valueOf(pinNumber)), new Formula(Integer.valueOf(pinValue)));
    }

    public ArduinoSendPWMValueBrick(Formula pinNumber, Formula pinValue) {
        this();
        setFormulaWithBrickField(Brick.BrickField.ARDUINO_ANALOG_PIN_NUMBER, pinNumber);
        setFormulaWithBrickField(Brick.BrickField.ARDUINO_ANALOG_PIN_VALUE, pinValue);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 6);
        super.addRequiredResources(requiredResourcesSet);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSendPWMArduinoValueAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.ARDUINO_ANALOG_PIN_NUMBER), getFormulaWithBrickField(Brick.BrickField.ARDUINO_ANALOG_PIN_VALUE)));
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.ARDUINO_ANALOG_PIN_NUMBER;
    }

    public void updateArduinoValues994to995() {
        FormulaElement root = getFormulaWithBrickField(Brick.BrickField.ARDUINO_ANALOG_PIN_VALUE).getRoot();
        FormulaElement formulaElement = new FormulaElement(FormulaElement.ElementType.OPERATOR, Operators.MULT.toString(), null);
        formulaElement.setLeftChild(new FormulaElement(FormulaElement.ElementType.NUMBER, "2.55", null));
        formulaElement.setRightChild(root);
        setFormulaWithBrickField(Brick.BrickField.ARDUINO_ANALOG_PIN_VALUE, new Formula(formulaElement));
    }
}
