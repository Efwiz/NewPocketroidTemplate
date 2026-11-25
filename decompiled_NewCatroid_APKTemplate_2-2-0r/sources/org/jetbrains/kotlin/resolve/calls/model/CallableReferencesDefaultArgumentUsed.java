package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferencesDefaultArgumentUsed;", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceInapplicableDiagnostic;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;", "candidate", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "defaultsCount", "", "(Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;I)V", "getArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;", "getCandidate", "()Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getDefaultsCount", "()I", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallableReferencesDefaultArgumentUsed extends CallableReferenceInapplicableDiagnostic {
    private final CallableReferenceResolutionAtom argument;
    private final CallableDescriptor candidate;
    private final int defaultsCount;

    public final CallableReferenceResolutionAtom getArgument() {
        return this.argument;
    }

    public final CallableDescriptor getCandidate() {
        return this.candidate;
    }

    public final int getDefaultsCount() {
        return this.defaultsCount;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CallableReferencesDefaultArgumentUsed(CallableReferenceResolutionAtom argument, CallableDescriptor candidate, int i) {
        super(argument, null, 2, null);
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        this.argument = argument;
        this.candidate = candidate;
        this.defaultsCount = i;
    }
}
