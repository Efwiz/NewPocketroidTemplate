package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/AdaptedCallableReferenceIsUsedWithReflection;", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceInapplicableDiagnostic;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;", "(Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;)V", "getArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AdaptedCallableReferenceIsUsedWithReflection extends CallableReferenceInapplicableDiagnostic {
    private final CallableReferenceResolutionAtom argument;

    public final CallableReferenceResolutionAtom getArgument() {
        return this.argument;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AdaptedCallableReferenceIsUsedWithReflection(CallableReferenceResolutionAtom argument) {
        super(argument, CandidateApplicability.RESOLVED_WITH_ERROR);
        Intrinsics.checkNotNullParameter(argument, "argument");
        this.argument = argument;
    }
}
