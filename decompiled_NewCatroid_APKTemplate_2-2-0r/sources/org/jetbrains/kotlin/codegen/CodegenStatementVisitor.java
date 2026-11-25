package org.jetbrains.kotlin.codegen;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtIfExpression;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtTryExpression;
import org.jetbrains.kotlin.psi.KtVisitor;
import org.jetbrains.kotlin.psi.KtWhenExpression;

/* JADX INFO: loaded from: classes3.dex */
public class CodegenStatementVisitor extends KtVisitor<StackValue, StackValue> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final ExpressionCodegen codegen;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
                objArr[0] = "expression";
                break;
            case 3:
                objArr[0] = "function";
                break;
            default:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/CodegenStatementVisitor";
        switch (i) {
            case 1:
                objArr[2] = "visitIfExpression";
                break;
            case 2:
                objArr[2] = "visitTryExpression";
                break;
            case 3:
                objArr[2] = "visitNamedFunction";
                break;
            case 4:
                objArr[2] = "visitWhenExpression";
                break;
            case 5:
                objArr[2] = "visitBlockExpression";
                break;
            case 6:
                objArr[2] = "visitLabeledExpression";
                break;
            default:
                objArr[2] = "visitKtElement";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public CodegenStatementVisitor(ExpressionCodegen expressionCodegen) {
        this.codegen = expressionCodegen;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitKtElement(KtElement ktElement, StackValue stackValue) {
        if (ktElement == null) {
            $$$reportNull$$$0(0);
        }
        return (StackValue) ktElement.accept(this.codegen, stackValue);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitIfExpression(KtIfExpression ktIfExpression, StackValue stackValue) {
        if (ktIfExpression == null) {
            $$$reportNull$$$0(1);
        }
        return this.codegen.generateIfExpression(ktIfExpression, true);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitTryExpression(KtTryExpression ktTryExpression, StackValue stackValue) {
        if (ktTryExpression == null) {
            $$$reportNull$$$0(2);
        }
        return this.codegen.generateTryExpression(ktTryExpression, true);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitNamedFunction(KtNamedFunction ktNamedFunction, StackValue stackValue) {
        if (ktNamedFunction == null) {
            $$$reportNull$$$0(3);
        }
        return this.codegen.visitNamedFunction(ktNamedFunction, stackValue, true);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitWhenExpression(KtWhenExpression ktWhenExpression, StackValue stackValue) {
        if (ktWhenExpression == null) {
            $$$reportNull$$$0(4);
        }
        return this.codegen.generateWhenExpression(ktWhenExpression, true);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitBlockExpression(KtBlockExpression ktBlockExpression, StackValue stackValue) {
        if (ktBlockExpression == null) {
            $$$reportNull$$$0(5);
        }
        return this.codegen.generateBlock(ktBlockExpression, true);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitLabeledExpression(KtLabeledExpression ktLabeledExpression, StackValue stackValue) {
        if (ktLabeledExpression == null) {
            $$$reportNull$$$0(6);
        }
        return (StackValue) ktLabeledExpression.getBaseExpression().accept(this, stackValue);
    }
}
