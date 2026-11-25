package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/UnstableSmartCastResolutionError;", "Lorg/jetbrains/kotlin/resolve/calls/model/UnstableSmartCast;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;", "targetType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "(Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;Lorg/jetbrains/kotlin/types/UnwrappedType;)V", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnstableSmartCastResolutionError extends UnstableSmartCast {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnstableSmartCastResolutionError(ExpressionKotlinCallArgument argument, UnwrappedType targetType) {
        super(argument, targetType, CandidateApplicability.UNSTABLE_SMARTCAST, null);
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(targetType, "targetType");
    }
}
