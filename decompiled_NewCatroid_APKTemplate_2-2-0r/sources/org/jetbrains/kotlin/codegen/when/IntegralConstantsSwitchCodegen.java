package org.jetbrains.kotlin.codegen.when;

import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.org.objectweb.asm.Label;

/* JADX INFO: loaded from: classes3.dex */
public class IntegralConstantsSwitchCodegen extends SwitchCodegen {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "codegen";
        } else if (i == 2) {
            objArr[0] = "constant";
        } else if (i == 3) {
            objArr[0] = "entryLabel";
        } else if (i != 4) {
            objArr[0] = "expression";
        } else {
            objArr[0] = "entry";
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/when/IntegralConstantsSwitchCodegen";
        if (i == 2 || i == 3 || i == 4) {
            objArr[2] = "processConstant";
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.codegen.when.SwitchCodegen
    protected void generateSubjectValueToIndex() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IntegralConstantsSwitchCodegen(KtWhenExpression ktWhenExpression, boolean z, boolean z2, ExpressionCodegen expressionCodegen) {
        super(ktWhenExpression, z, z2, expressionCodegen, null);
        if (ktWhenExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (expressionCodegen == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.codegen.when.SwitchCodegen
    protected void processConstant(ConstantValue<?> constantValue, Label label, KtWhenEntry ktWhenEntry) {
        int iCharValue;
        if (constantValue == null) {
            $$$reportNull$$$0(2);
        }
        if (label == null) {
            $$$reportNull$$$0(3);
        }
        if (ktWhenEntry == null) {
            $$$reportNull$$$0(4);
        }
        if (constantValue.getValue() instanceof Number) {
            iCharValue = ((Number) constantValue.getValue()).intValue();
        } else {
            iCharValue = ((Character) constantValue.getValue()).charValue();
        }
        putTransitionOnce(iCharValue, label);
    }
}
