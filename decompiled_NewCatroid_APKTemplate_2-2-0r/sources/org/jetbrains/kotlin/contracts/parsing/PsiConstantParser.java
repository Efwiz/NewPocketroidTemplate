package org.jetbrains.kotlin.contracts.parsing;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.contracts.description.expressions.BooleanConstantReference;
import org.jetbrains.kotlin.contracts.description.expressions.ConstantReference;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtVisitor;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: PsiConstantParser.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J!\u0010\u0007\u001a\u0004\u0018\u00010\u00022\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u000bJ!\u0010\f\u001a\u0004\u0018\u00010\u00022\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/contracts/parsing/PsiConstantParser;", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "Lorg/jetbrains/kotlin/contracts/description/expressions/ConstantReference;", "", "callContext", "Lorg/jetbrains/kotlin/contracts/parsing/ContractCallContext;", "(Lorg/jetbrains/kotlin/contracts/parsing/ContractCallContext;)V", "visitConstantExpression", "expression", "Lorg/jetbrains/kotlin/psi/KtConstantExpression;", "data", "(Lorg/jetbrains/kotlin/psi/KtConstantExpression;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/description/expressions/ConstantReference;", "visitKtElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/description/expressions/ConstantReference;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PsiConstantParser extends KtVisitor<ConstantReference, Unit> {
    private final ContractCallContext callContext;

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public ConstantReference visitKtElement(KtElement element, Unit data) {
        Intrinsics.checkNotNullParameter(element, "element");
        return null;
    }

    public PsiConstantParser(ContractCallContext callContext) {
        Intrinsics.checkNotNullParameter(callContext, "callContext");
        this.callContext = callContext;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public ConstantReference visitConstantExpression(KtConstantExpression expression, Unit data) {
        CompileTimeConstant compileTimeConstant;
        Intrinsics.checkNotNullParameter(expression, "expression");
        KotlinType type = this.callContext.getBindingContext().getType(expression);
        if (type == null || (compileTimeConstant = (CompileTimeConstant) this.callContext.getBindingContext().get(BindingContext.COMPILE_TIME_VALUE, expression)) == null) {
            return null;
        }
        Object value = compileTimeConstant.getValue(type);
        if (Intrinsics.areEqual(value, (Object) true)) {
            return BooleanConstantReference.INSTANCE.getTRUE();
        }
        if (Intrinsics.areEqual(value, (Object) false)) {
            return BooleanConstantReference.INSTANCE.getFALSE();
        }
        if (value == null) {
            return ConstantReference.INSTANCE.getNULL();
        }
        return null;
    }
}
