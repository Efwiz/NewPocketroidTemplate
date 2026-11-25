package org.jetbrains.kotlin.fir.resolve.transformers;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.WhenMissingCase;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;

/* JADX INFO: compiled from: FirWhenExhaustivenessTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenOnNothingExhaustivenessChecker;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenExhaustivenessChecker;", "()V", "computeMissingCases", "", "whenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "subjectType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", FirebaseAnalytics.Param.DESTINATION, "", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "isApplicable", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class WhenOnNothingExhaustivenessChecker extends WhenExhaustivenessChecker {
    public static final WhenOnNothingExhaustivenessChecker INSTANCE = new WhenOnNothingExhaustivenessChecker();

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.WhenExhaustivenessChecker
    public void computeMissingCases(FirWhenExpression whenExpression, ConeKotlinType subjectType, FirSession session, Collection<WhenMissingCase> destination) {
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        Intrinsics.checkNotNullParameter(subjectType, "subjectType");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(destination, "destination");
    }

    private WhenOnNothingExhaustivenessChecker() {
        super(null);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.WhenExhaustivenessChecker
    public boolean isApplicable(ConeKotlinType subjectType, FirSession session) {
        Intrinsics.checkNotNullParameter(subjectType, "subjectType");
        Intrinsics.checkNotNullParameter(session, "session");
        return ConeBuiltinTypeUtilsKt.isNullableNothing(subjectType) || ConeBuiltinTypeUtilsKt.isNothing(subjectType);
    }
}
