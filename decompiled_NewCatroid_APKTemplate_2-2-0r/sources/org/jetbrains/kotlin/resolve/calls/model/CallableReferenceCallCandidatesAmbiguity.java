package org.jetbrains.kotlin.resolve.calls.model;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.components.candidate.CallableReferenceResolutionCandidate;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceCallCandidatesAmbiguity;", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceInapplicableDiagnostic;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;", "candidates", "", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/CallableReferenceResolutionCandidate;", "(Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;Ljava/util/Collection;)V", "getArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;", "getCandidates", "()Ljava/util/Collection;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallableReferenceCallCandidatesAmbiguity extends CallableReferenceInapplicableDiagnostic {
    private final CallableReferenceKotlinCallArgument argument;
    private final Collection<CallableReferenceResolutionCandidate> candidates;

    public final CallableReferenceKotlinCallArgument getArgument() {
        return this.argument;
    }

    public final Collection<CallableReferenceResolutionCandidate> getCandidates() {
        return this.candidates;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CallableReferenceCallCandidatesAmbiguity(CallableReferenceKotlinCallArgument argument, Collection<CallableReferenceResolutionCandidate> candidates) {
        super(argument, null, 2, null);
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        this.argument = argument;
        this.candidates = candidates;
    }
}
