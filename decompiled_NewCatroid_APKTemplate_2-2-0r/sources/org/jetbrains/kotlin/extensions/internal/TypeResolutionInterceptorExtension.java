package org.jetbrains.kotlin.extensions.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingContext;

/* JADX INFO: compiled from: NonStableExtensionPoints.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H\u0016J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\nH\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/extensions/internal/TypeResolutionInterceptorExtension;", "", "interceptFunctionLiteralDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/AnonymousFunctionDescriptor;", "expression", "Lorg/jetbrains/kotlin/psi/KtLambdaExpression;", "context", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "descriptor", "interceptType", "Lorg/jetbrains/kotlin/types/KotlinType;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "resultType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeResolutionInterceptorExtension {
    default AnonymousFunctionDescriptor interceptFunctionLiteralDescriptor(KtLambdaExpression expression, ExpressionTypingContext context, AnonymousFunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return descriptor;
    }

    default KotlinType interceptType(KtElement element, ExpressionTypingContext context, KotlinType resultType) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(resultType, "resultType");
        return resultType;
    }
}
