package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: ResolutionDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ArgumentTypeMismatch;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionDiagnostic;", "expectedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "actualType", "argument", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "isMismatchDueToNullability", "", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Z)V", "getActualType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getArgument", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getExpectedType", "()Z", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArgumentTypeMismatch extends ResolutionDiagnostic {
    private final ConeKotlinType actualType;
    private final FirExpression argument;
    private final ConeKotlinType expectedType;
    private final boolean isMismatchDueToNullability;

    public final ConeKotlinType getExpectedType() {
        return this.expectedType;
    }

    public final ConeKotlinType getActualType() {
        return this.actualType;
    }

    public final FirExpression getArgument() {
        return this.argument;
    }

    /* JADX INFO: renamed from: isMismatchDueToNullability, reason: from getter */
    public final boolean getIsMismatchDueToNullability() {
        return this.isMismatchDueToNullability;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ArgumentTypeMismatch(ConeKotlinType expectedType, ConeKotlinType actualType, FirExpression argument, boolean z) {
        super(CandidateApplicability.INAPPLICABLE);
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        Intrinsics.checkNotNullParameter(actualType, "actualType");
        Intrinsics.checkNotNullParameter(argument, "argument");
        this.expectedType = expectedType;
        this.actualType = actualType;
        this.argument = argument;
        this.isMismatchDueToNullability = z;
    }
}
