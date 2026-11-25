package org.catrobat.catroid.formulaeditor.function;

import java.util.Map;
import org.catrobat.catroid.formulaeditor.Functions;
import org.catrobat.catroid.utils.TouchUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/function/TouchFunctionProvider.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class TouchFunctionProvider implements FunctionProvider {
    @Override // org.catrobat.catroid.formulaeditor.function.FunctionProvider
    public void addFunctionsToMap(Map<Functions, FormulaFunction> formulaFunctions) {
        formulaFunctions.put(Functions.MULTI_FINGER_TOUCHED, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.TouchFunctionProvider$$ExternalSyntheticLambda0
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(this.f$0.interpretFunctionFingerTouched(d));
            }
        }));
        formulaFunctions.put(Functions.MULTI_FINGER_X, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.TouchFunctionProvider$$ExternalSyntheticLambda1
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(this.f$0.interpretFunctionMultiFingerX(d));
            }
        }));
        formulaFunctions.put(Functions.MULTI_FINGER_Y, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.TouchFunctionProvider$$ExternalSyntheticLambda2
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(this.f$0.interpretFunctionMultiFingerY(d));
            }
        }));
        formulaFunctions.put(Functions.INDEX_CURRENT_TOUCH, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.TouchFunctionProvider$$ExternalSyntheticLambda3
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(this.f$0.interpretFunctionIndexCurrentTouch(d));
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double interpretFunctionMultiFingerY(double argument) {
        return TouchUtil.getY((int) argument);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double interpretFunctionMultiFingerX(double argument) {
        return TouchUtil.getX((int) argument);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double interpretFunctionFingerTouched(double argument) {
        return TouchUtil.isFingerTouching((int) argument) ? 1.0d : 0.0d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double interpretFunctionIndexCurrentTouch(double argument) {
        return TouchUtil.getIndexOfCurrentTouch((int) argument);
    }
}
