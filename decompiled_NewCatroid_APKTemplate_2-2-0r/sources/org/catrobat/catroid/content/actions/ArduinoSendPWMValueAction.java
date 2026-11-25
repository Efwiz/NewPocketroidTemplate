package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.devices.arduino.Arduino;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ArduinoSendPWMValueAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ArduinoSendPWMValueAction extends TemporalAction {
    private int pin;
    private Formula pinNumber;
    private Formula pinValue;
    private boolean restart = false;
    private Scope scope;
    private int value;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void begin() {
        Integer numInterpretInteger;
        Integer numInterpretInteger2;
        try {
            Formula formula = this.pinNumber;
            numInterpretInteger = formula == null ? 0 : formula.interpretInteger(this.scope);
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
            numInterpretInteger = 0;
        }
        try {
            Formula formula2 = this.pinValue;
            numInterpretInteger2 = formula2 == null ? 0 : formula2.interpretInteger(this.scope);
        } catch (InterpretationException e2) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e2);
            numInterpretInteger2 = 0;
        }
        if (!this.restart && numInterpretInteger.intValue() >= 0 && numInterpretInteger.intValue() < 14) {
            this.pin = numInterpretInteger.intValue();
            this.value = numInterpretInteger2.intValue();
        }
        this.restart = false;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Arduino arduino = (Arduino) ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).getDevice(BluetoothDevice.ARDUINO);
        if (arduino != null) {
            arduino.setAnalogArduinoPin(this.pin, this.value);
        }
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setPinNumber(Formula newPinNumber) {
        this.pinNumber = newPinNumber;
    }

    public void setPinValue(Formula newpinValue) {
        this.pinValue = newpinValue;
    }
}
