package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ImplicitScopeTower.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/UsedSmartCastForDispatchReceiver;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ResolutionDiagnostic;", "smartCastType", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/types/KotlinType;)V", "getSmartCastType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UsedSmartCastForDispatchReceiver extends ResolutionDiagnostic {
    private final KotlinType smartCastType;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UsedSmartCastForDispatchReceiver(KotlinType smartCastType) {
        super(CandidateApplicability.RESOLVED);
        Intrinsics.checkNotNullParameter(smartCastType, "smartCastType");
        this.smartCastType = smartCastType;
    }

    public final KotlinType getSmartCastType() {
        return this.smartCastType;
    }
}
