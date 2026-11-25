package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/NotCallableMemberReference;", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceInapplicableDiagnostic;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;", "candidate", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)V", "getArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;", "getCandidate", "()Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NotCallableMemberReference extends CallableReferenceInapplicableDiagnostic {
    private final CallableReferenceResolutionAtom argument;
    private final CallableDescriptor candidate;

    public final CallableReferenceResolutionAtom getArgument() {
        return this.argument;
    }

    public final CallableDescriptor getCandidate() {
        return this.candidate;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotCallableMemberReference(CallableReferenceResolutionAtom argument, CallableDescriptor candidate) {
        super(argument, null, 2, null);
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        this.argument = argument;
        this.candidate = candidate;
    }
}
