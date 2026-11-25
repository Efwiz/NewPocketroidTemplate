package org.jetbrains.kotlin.fir.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: ExhaustivenessStatus.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0003¨\u0006\u0005"}, d2 = {"isExhaustive", "", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;)Z", "isProperlyExhaustive", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExhaustivenessStatusKt {
    public static final boolean isExhaustive(FirWhenExpression firWhenExpression) {
        Intrinsics.checkNotNullParameter(firWhenExpression, "<this>");
        return Intrinsics.areEqual(firWhenExpression.getExhaustivenessStatus(), ExhaustivenessStatus.ProperlyExhaustive.INSTANCE) || Intrinsics.areEqual(firWhenExpression.getExhaustivenessStatus(), ExhaustivenessStatus.ExhaustiveAsNothing.INSTANCE);
    }

    public static final boolean isProperlyExhaustive(FirWhenExpression firWhenExpression) {
        Intrinsics.checkNotNullParameter(firWhenExpression, "<this>");
        return Intrinsics.areEqual(firWhenExpression.getExhaustivenessStatus(), ExhaustivenessStatus.ProperlyExhaustive.INSTANCE);
    }
}
