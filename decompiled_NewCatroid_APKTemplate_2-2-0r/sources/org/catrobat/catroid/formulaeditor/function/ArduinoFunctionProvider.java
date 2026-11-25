package org.catrobat.catroid.formulaeditor.function;

import java.util.Map;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.devices.arduino.Arduino;
import org.catrobat.catroid.formulaeditor.Functions;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/function/ArduinoFunctionProvider.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ArduinoFunctionProvider implements FunctionProvider {
    @Override // org.catrobat.catroid.formulaeditor.function.FunctionProvider
    public void addFunctionsToMap(Map<Functions, FormulaFunction> formulaFunctions) {
        formulaFunctions.put(Functions.ARDUINODIGITAL, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.ArduinoFunctionProvider$$ExternalSyntheticLambda0
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(this.f$0.interpretFunctionArduinoDigital(d));
            }
        }));
        formulaFunctions.put(Functions.ARDUINOANALOG, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.ArduinoFunctionProvider$$ExternalSyntheticLambda1
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(this.f$0.interpretFunctionArduinoAnalog(d));
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double interpretFunctionArduinoAnalog(double argument) {
        Arduino arduino = getArduino();
        if (arduino == null || argument < 0.0d || argument > 5.0d) {
            return 0.0d;
        }
        return arduino.getAnalogArduinoPin((int) argument);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double interpretFunctionArduinoDigital(double argument) {
        Arduino arduino = getArduino();
        if (arduino == null || argument < 0.0d || argument > 13.0d) {
            return 0.0d;
        }
        return arduino.getDigitalArduinoPin((int) argument);
    }

    private Arduino getArduino() {
        return (Arduino) ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).getDevice(BluetoothDevice.ARDUINO);
    }
}
