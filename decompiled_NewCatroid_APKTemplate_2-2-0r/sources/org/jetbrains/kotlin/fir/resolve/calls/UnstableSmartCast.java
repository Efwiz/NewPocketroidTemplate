package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: ResolutionDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/UnstableSmartCast;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionDiagnostic;", "argument", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "targetType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "isCastToNotNull", "", "(Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Z)V", "getArgument", "()Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "()Z", "getTargetType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnstableSmartCast extends ResolutionDiagnostic {
    private final FirSmartCastExpression argument;
    private final boolean isCastToNotNull;
    private final ConeKotlinType targetType;

    public final FirSmartCastExpression getArgument() {
        return this.argument;
    }

    public final ConeKotlinType getTargetType() {
        return this.targetType;
    }

    /* JADX INFO: renamed from: isCastToNotNull, reason: from getter */
    public final boolean getIsCastToNotNull() {
        return this.isCastToNotNull;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnstableSmartCast(FirSmartCastExpression argument, ConeKotlinType targetType, boolean z) {
        super(CandidateApplicability.UNSTABLE_SMARTCAST);
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(targetType, "targetType");
        this.argument = argument;
        this.targetType = targetType;
        this.isCastToNotNull = z;
    }
}
