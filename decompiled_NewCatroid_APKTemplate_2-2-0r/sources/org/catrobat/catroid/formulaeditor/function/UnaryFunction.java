package org.catrobat.catroid.formulaeditor.function;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/function/UnaryFunction.class
  classes14.dex
 */
/* JADX INFO: compiled from: UnaryFunction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0006\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J%\u0010\u0005\u001a\u00020\u00062\u0016\u0010\u0007\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\t0\b\"\u0004\u0018\u00010\tH\u0016¢\u0006\u0002\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/formulaeditor/function/UnaryFunction;", "Lorg/catrobat/catroid/formulaeditor/function/FormulaFunction;", "action", "Lorg/catrobat/catroid/formulaeditor/function/UnaryFunctionAction;", "(Lorg/catrobat/catroid/formulaeditor/function/UnaryFunctionAction;)V", "execute", "", "args", "", "", "([Ljava/lang/Double;)Ljava/lang/Object;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnaryFunction implements FormulaFunction {
    private final UnaryFunctionAction action;

    public UnaryFunction(UnaryFunctionAction action) {
        Intrinsics.checkNotNullParameter(action, "action");
        this.action = action;
    }

    @Override // org.catrobat.catroid.formulaeditor.function.FormulaFunction
    public Object execute(Double... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        Double d = (Double) ArraysKt.getOrNull(args, 0);
        if (d == null) {
            return Double.valueOf(0.0d);
        }
        return this.action.execute(d.doubleValue());
    }
}
