package org.jetbrains.kotlin.resolve.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.calls.checkers.AdditionalTypeChecker;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.StubTypeForBuilderInference;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: RuntimeAssertions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/RuntimeAssertionsTypeChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/AdditionalTypeChecker;", "()V", "checkType", "", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "expressionType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expressionTypeWithSmartCast", "c", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RuntimeAssertionsTypeChecker implements AdditionalTypeChecker {
    public static final RuntimeAssertionsTypeChecker INSTANCE = new RuntimeAssertionsTypeChecker();

    private RuntimeAssertionsTypeChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.AdditionalTypeChecker
    public void checkType(KtExpression expression, KotlinType expressionType, KotlinType expressionTypeWithSmartCast, ResolutionContext<?> c) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(expressionType, "expressionType");
        Intrinsics.checkNotNullParameter(expressionTypeWithSmartCast, "expressionTypeWithSmartCast");
        Intrinsics.checkNotNullParameter(c, "c");
        if (TypeUtils.noExpectedType(c.expectedType) || (c.expectedType instanceof StubTypeForBuilderInference)) {
            return;
        }
        RuntimeAssertionInfo.Companion companion = RuntimeAssertionInfo.INSTANCE;
        KotlinType expectedType = c.expectedType;
        Intrinsics.checkNotNullExpressionValue(expectedType, "expectedType");
        RuntimeAssertionInfo runtimeAssertionInfoCreate = companion.create(expectedType, expressionType, new RuntimeAssertionsDataFlowExtras(c, expressionType, expression));
        if (runtimeAssertionInfoCreate != null) {
            c.trace.record(JvmBindingContextSlices.RUNTIME_ASSERTION_INFO, expression, runtimeAssertionInfoCreate);
        }
    }
}
