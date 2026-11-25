package org.jetbrains.kotlin.fir.resolve.transformers;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.WhenMissingCase;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirWhenExhaustivenessTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001:\u0002\u0010\u0011B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenOnBooleanExhaustivenessChecker;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenExhaustivenessChecker;", "()V", "computeMissingCases", "", "whenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "subjectType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", FirebaseAnalytics.Param.DESTINATION, "", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "isApplicable", "", "ConditionChecker", "Flags", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class WhenOnBooleanExhaustivenessChecker extends WhenExhaustivenessChecker {
    public static final WhenOnBooleanExhaustivenessChecker INSTANCE = new WhenOnBooleanExhaustivenessChecker();

    private WhenOnBooleanExhaustivenessChecker() {
        super(null);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.WhenExhaustivenessChecker
    public boolean isApplicable(ConeKotlinType subjectType, FirSession session) {
        Intrinsics.checkNotNullParameter(subjectType, "subjectType");
        Intrinsics.checkNotNullParameter(session, "session");
        return Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(subjectType), StandardClassIds.INSTANCE.getBoolean());
    }

    /* JADX INFO: compiled from: FirWhenExhaustivenessTransformer.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenOnBooleanExhaustivenessChecker$Flags;", "", "()V", "containsFalse", "", "getContainsFalse", "()Z", "setContainsFalse", "(Z)V", "containsTrue", "getContainsTrue", "setContainsTrue", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Flags {
        private boolean containsFalse;
        private boolean containsTrue;

        public final boolean getContainsTrue() {
            return this.containsTrue;
        }

        public final void setContainsTrue(boolean z) {
            this.containsTrue = z;
        }

        public final boolean getContainsFalse() {
            return this.containsFalse;
        }

        public final void setContainsFalse(boolean z) {
            this.containsFalse = z;
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.WhenExhaustivenessChecker
    public void computeMissingCases(FirWhenExpression whenExpression, ConeKotlinType subjectType, FirSession session, Collection<WhenMissingCase> destination) {
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        Intrinsics.checkNotNullParameter(subjectType, "subjectType");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Flags flags = new Flags();
        whenExpression.accept(ConditionChecker.INSTANCE, flags);
        if (!flags.getContainsTrue()) {
            destination.add(WhenMissingCase.BooleanIsMissing.TrueIsMissing.INSTANCE);
        }
        if (flags.getContainsFalse()) {
            return;
        }
        destination.add(WhenMissingCase.BooleanIsMissing.FalseIsMissing.INSTANCE);
    }

    /* JADX INFO: compiled from: FirWhenExhaustivenessTransformer.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0002H\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenOnBooleanExhaustivenessChecker$ConditionChecker;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenExhaustivenessChecker$AbstractConditionChecker;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenOnBooleanExhaustivenessChecker$Flags;", "()V", "visitEqualityOperatorCall", "", "equalityOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;", "data", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ConditionChecker extends WhenExhaustivenessChecker.AbstractConditionChecker<Flags> {
        public static final ConditionChecker INSTANCE = new ConditionChecker();

        private ConditionChecker() {
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitEqualityOperatorCall(FirEqualityOperatorCall firEqualityOperatorCall, Object obj) {
            visitEqualityOperatorCall(firEqualityOperatorCall, (Flags) obj);
            return Unit.INSTANCE;
        }

        public void visitEqualityOperatorCall(FirEqualityOperatorCall equalityOperatorCall, Flags data) {
            Intrinsics.checkNotNullParameter(equalityOperatorCall, "equalityOperatorCall");
            Intrinsics.checkNotNullParameter(data, "data");
            FirOperation operation = equalityOperatorCall.getOperation();
            if (operation == FirOperation.EQ || operation == FirOperation.IDENTITY) {
                FirExpression firExpression = equalityOperatorCall.getArgumentList().getArguments().get(1);
                if (firExpression instanceof FirConstExpression) {
                    Object value = ((FirConstExpression) firExpression).getValue();
                    if (Intrinsics.areEqual(value, (Object) true)) {
                        data.setContainsTrue(true);
                    } else if (Intrinsics.areEqual(value, (Object) false)) {
                        data.setContainsFalse(true);
                    }
                }
            }
        }
    }
}
