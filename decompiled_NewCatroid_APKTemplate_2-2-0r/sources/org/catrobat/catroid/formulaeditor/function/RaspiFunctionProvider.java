package org.catrobat.catroid.formulaeditor.function;

import android.util.Log;
import java.util.Map;
import org.catrobat.catroid.devices.raspberrypi.RPiSocketConnection;
import org.catrobat.catroid.devices.raspberrypi.RaspberryPiService;
import org.catrobat.catroid.formulaeditor.Functions;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/function/RaspiFunctionProvider.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RaspiFunctionProvider implements FunctionProvider {
    private double booleanToDouble(boolean value) {
        return value ? 1.0d : 0.0d;
    }

    @Override // org.catrobat.catroid.formulaeditor.function.FunctionProvider
    public void addFunctionsToMap(Map<Functions, FormulaFunction> formulaFunctions) {
        formulaFunctions.put(Functions.RASPIDIGITAL, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.RaspiFunctionProvider$$ExternalSyntheticLambda0
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(this.f$0.interpretFunctionRaspiDigital(Double.valueOf(d)));
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double interpretFunctionRaspiDigital(Double argument) {
        RPiSocketConnection rPiSocketConnection = RaspberryPiService.getInstance().connection;
        if (argument == null) {
            return 0.0d;
        }
        try {
            return booleanToDouble(rPiSocketConnection.getPin(argument.intValue()));
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "RPi: exception during getPin: " + e);
            return 0.0d;
        }
    }
}
