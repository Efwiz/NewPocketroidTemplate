package org.catrobat.catroid.formulaeditor.function;

import java.util.Map;
import org.catrobat.catroid.formulaeditor.Functions;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/function/MathFunctionProvider.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MathFunctionProvider implements FunctionProvider {
    @Override // org.catrobat.catroid.formulaeditor.function.FunctionProvider
    public void addFunctionsToMap(Map<Functions, FormulaFunction> formulaFunctions) {
        formulaFunctions.put(Functions.SIN, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda0
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(Math.sin(Math.toRadians(d)));
            }
        }));
        formulaFunctions.put(Functions.COS, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda2
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(Math.cos(Math.toRadians(d)));
            }
        }));
        formulaFunctions.put(Functions.TAN, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda6
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(Math.tan(Math.toRadians(d)));
            }
        }));
        formulaFunctions.put(Functions.ARCTAN2, new BinaryFunction(new BinaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda7
            @Override // org.catrobat.catroid.formulaeditor.function.BinaryFunctionAction
            public final Object execute(double d, double d2) {
                return Double.valueOf(this.f$0.interpretFunctionArcTan2(d, d2));
            }
        }));
        formulaFunctions.put(Functions.LN, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda8
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(Math.log(d));
            }
        }));
        formulaFunctions.put(Functions.LOG, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda9
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(Math.log10(d));
            }
        }));
        formulaFunctions.put(Functions.SQRT, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda10
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(Math.sqrt(d));
            }
        }));
        formulaFunctions.put(Functions.ABS, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda12
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(Math.abs(d));
            }
        }));
        formulaFunctions.put(Functions.ROUND, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda13
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(Math.round(d));
            }
        }));
        formulaFunctions.put(Functions.ROUNDTO, new BinaryFunction(new BinaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda14
            @Override // org.catrobat.catroid.formulaeditor.function.BinaryFunctionAction
            public final Object execute(double d, double d2) {
                return Double.valueOf(this.f$0.interpretFunctionRoundTo(d, d2));
            }
        }));
        formulaFunctions.put(Functions.PI, new FormulaFunction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda11
            @Override // org.catrobat.catroid.formulaeditor.function.FormulaFunction
            public final Object execute(Double[] dArr) {
                return Double.valueOf(3.141592653589793d);
            }
        });
        formulaFunctions.put(Functions.ARCSIN, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda15
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(Math.toDegrees(Math.asin(d)));
            }
        }));
        formulaFunctions.put(Functions.ARCCOS, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda16
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(Math.toDegrees(Math.acos(d)));
            }
        }));
        formulaFunctions.put(Functions.ARCTAN, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda17
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(Math.toDegrees(Math.atan(d)));
            }
        }));
        formulaFunctions.put(Functions.EXP, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda18
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(Math.exp(d));
            }
        }));
        formulaFunctions.put(Functions.POWER, new BinaryFunction(new BinaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda19
            @Override // org.catrobat.catroid.formulaeditor.function.BinaryFunctionAction
            public final Object execute(double d, double d2) {
                return Double.valueOf(Math.pow(d, d2));
            }
        }));
        formulaFunctions.put(Functions.FLOOR, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda20
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(Math.floor(d));
            }
        }));
        formulaFunctions.put(Functions.CEIL, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda21
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(Math.ceil(d));
            }
        }));
        formulaFunctions.put(Functions.MAX, new BinaryFunction(new BinaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda22
            @Override // org.catrobat.catroid.formulaeditor.function.BinaryFunctionAction
            public final Object execute(double d, double d2) {
                return Double.valueOf(Math.max(d, d2));
            }
        }));
        formulaFunctions.put(Functions.MIN, new BinaryFunction(new BinaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda1
            @Override // org.catrobat.catroid.formulaeditor.function.BinaryFunctionAction
            public final Object execute(double d, double d2) {
                return Double.valueOf(Math.min(d, d2));
            }
        }));
        formulaFunctions.put(Functions.TRUE, new FormulaFunction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda3
            @Override // org.catrobat.catroid.formulaeditor.function.FormulaFunction
            public final Object execute(Double[] dArr) {
                return Double.valueOf(1.0d);
            }
        });
        formulaFunctions.put(Functions.FALSE, new FormulaFunction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda4
            @Override // org.catrobat.catroid.formulaeditor.function.FormulaFunction
            public final Object execute(Double[] dArr) {
                return Double.valueOf(0.0d);
            }
        });
        formulaFunctions.put(Functions.MOD, new BinaryFunction(new BinaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.MathFunctionProvider$$ExternalSyntheticLambda5
            @Override // org.catrobat.catroid.formulaeditor.function.BinaryFunctionAction
            public final Object execute(double d, double d2) {
                return Double.valueOf(this.f$0.interpretFunctionMod(d, d2));
            }
        }));
    }

    public static double roundTo(double value, double places) {
        double dPow = Math.pow(10.0d, places);
        return Math.round(value * dPow) / dPow;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double interpretFunctionMod(double dividend, double divisor) {
        if (dividend == 0.0d || divisor == 0.0d) {
            return dividend;
        }
        if (divisor > 0.0d) {
            while (dividend < 0.0d) {
                dividend += Math.abs(divisor);
            }
        } else if (dividend > 0.0d) {
            return (dividend % divisor) + divisor;
        }
        return dividend % divisor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double interpretFunctionArcTan2(double firstArgument, double secondArgument) {
        if (firstArgument == 0.0d && secondArgument == 0.0d) {
            return (Math.random() * 360.0d) - 180.0d;
        }
        return Math.toDegrees(Math.atan2(firstArgument, secondArgument));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double interpretFunctionRoundTo(double firstArgument, double secondArgument) {
        return roundTo(firstArgument, secondArgument);
    }
}
