package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceInapplicableDiagnostic;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;", "applicability", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "(Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;)V", "report", "", "reporter", "Lorg/jetbrains/kotlin/resolve/calls/model/DiagnosticReporter;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CallableReferenceInapplicableDiagnostic extends KotlinCallDiagnostic {
    private final CallableReferenceResolutionAtom argument;

    public /* synthetic */ CallableReferenceInapplicableDiagnostic(CallableReferenceResolutionAtom callableReferenceResolutionAtom, CandidateApplicability candidateApplicability, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(callableReferenceResolutionAtom, (i & 2) != 0 ? CandidateApplicability.INAPPLICABLE : candidateApplicability);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CallableReferenceInapplicableDiagnostic(CallableReferenceResolutionAtom argument, CandidateApplicability applicability) {
        super(applicability);
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(applicability, "applicability");
        this.argument = argument;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic
    public void report(DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        CallableReferenceResolutionAtom callableReferenceResolutionAtom = this.argument;
        if (callableReferenceResolutionAtom instanceof CallableReferenceKotlinCall) {
            reporter.onCall(this);
        } else if (callableReferenceResolutionAtom instanceof CallableReferenceKotlinCallArgument) {
            reporter.onCallArgument((KotlinCallArgument) callableReferenceResolutionAtom, this);
        }
    }
}
