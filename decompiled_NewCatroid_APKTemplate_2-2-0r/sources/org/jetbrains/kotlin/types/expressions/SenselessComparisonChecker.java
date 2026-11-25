package org.jetbrains.kotlin.types.expressions;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.smartcasts.Nullability;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;

/* JADX INFO: compiled from: SenselessComparisonChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JV\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b2\u0014\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\r2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\rH\u0007¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/SenselessComparisonChecker;", "", "()V", "checkSenselessComparisonWithNull", "", "expression", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "left", "Lorg/jetbrains/kotlin/psi/KtExpression;", "right", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "getType", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/types/KotlinType;", "getNullability", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/Nullability;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SenselessComparisonChecker {
    public static final SenselessComparisonChecker INSTANCE = new SenselessComparisonChecker();

    /* JADX INFO: compiled from: SenselessComparisonChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Nullability.values().length];
            try {
                iArr[Nullability.NULL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Nullability.NOT_NULL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Nullability.IMPOSSIBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private SenselessComparisonChecker() {
    }

    @JvmStatic
    public static final void checkSenselessComparisonWithNull(KtBinaryExpression expression, KtExpression left, KtExpression right, ResolutionContext<?> context, Function1<? super KtExpression, ? extends KotlinType> getType, Function1<? super DataFlowValue, ? extends Nullability> getNullability) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(getType, "getType");
        Intrinsics.checkNotNullParameter(getNullability, "getNullability");
        if (KtPsiUtil.isNullConstant(left)) {
            left = right;
        } else if (!KtPsiUtil.isNullConstant(right)) {
            return;
        }
        KotlinType kotlinTypeInvoke = getType.invoke(left);
        if (kotlinTypeInvoke == null || KotlinTypeKt.isError(kotlinTypeInvoke)) {
            return;
        }
        KtOperationReferenceExpression operationReference = expression.getOperationReference();
        Intrinsics.checkNotNullExpressionValue(operationReference, "getOperationReference(...)");
        DataFlowValue dataFlowValueCreateDataFlowValue = context.dataFlowValueFactory.createDataFlowValue(left, kotlinTypeInvoke, context);
        boolean z = false;
        boolean z2 = Intrinsics.areEqual(operationReference.getReferencedNameElementType(), KtTokens.EQEQ) || Intrinsics.areEqual(operationReference.getReferencedNameElementType(), KtTokens.EQEQEQ);
        int i = WhenMappings.$EnumSwitchMapping$0[getNullability.invoke(dataFlowValueCreateDataFlowValue).ordinal()];
        if (i == 1) {
            z = z2;
        } else if (i != 2) {
            if (i != 3) {
                return;
            }
        } else if (!z2) {
            z = true;
        }
        context.trace.report(Errors.SENSELESS_COMPARISON.on(expression, expression, Boolean.valueOf(z)));
    }
}
