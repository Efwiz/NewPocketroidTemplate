package org.jetbrains.kotlin.resolve.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: jvmConstants.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a,\u0010\u0000\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007¨\u0006\t"}, d2 = {"getCompileTimeConstant", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "takeUpConstValsAsConst", "", "shouldInlineConstVals", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmConstantsKt {
    public static final ConstantValue<?> getCompileTimeConstant(KtExpression expression, BindingContext bindingContext, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        CompileTimeConstant<?> constant = ConstantExpressionEvaluator.INSTANCE.getConstant(expression, bindingContext);
        if (constant == null || constant.getUsesNonConstValAsConstant()) {
            return null;
        }
        if (!z2 && !z && constant.getUsesVariableAsConstant()) {
            ConstantsChecker constantsChecker = new ConstantsChecker(bindingContext);
            expression.accept(constantsChecker);
            if (constantsChecker.getContainsKotlinConstVals()) {
                return null;
            }
        }
        KotlinType type = bindingContext.getType(expression);
        if (type == null) {
            return null;
        }
        return constant.toConstantValue(type);
    }
}
