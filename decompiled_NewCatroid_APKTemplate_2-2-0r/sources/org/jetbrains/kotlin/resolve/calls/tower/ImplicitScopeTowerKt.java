package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;

/* JADX INFO: compiled from: ImplicitScopeTower.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001b\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0007¢\u0006\u0002\b\u0005\u001a\u001b\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u0007¢\u0006\u0002\b\u0007¨\u0006\b"}, d2 = {"getResultApplicability", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "diagnostics", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "getResultApplicabilityForConstraintErrors", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "getResultApplicabilityForCallDiagnostics", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ImplicitScopeTowerKt {
    public static final CandidateApplicability getResultApplicabilityForConstraintErrors(Collection<? extends ConstraintSystemError> diagnostics) {
        Object obj;
        CandidateApplicability applicability;
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        Iterator<T> it = diagnostics.iterator();
        if (it.hasNext()) {
            Object next = it.next();
            if (it.hasNext()) {
                CandidateApplicability applicability2 = ((ConstraintSystemError) next).getApplicability();
                do {
                    Object next2 = it.next();
                    CandidateApplicability applicability3 = ((ConstraintSystemError) next2).getApplicability();
                    if (applicability2.compareTo(applicability3) > 0) {
                        next = next2;
                        applicability2 = applicability3;
                    }
                } while (it.hasNext());
            }
            obj = next;
        } else {
            obj = null;
        }
        ConstraintSystemError constraintSystemError = (ConstraintSystemError) obj;
        return (constraintSystemError == null || (applicability = constraintSystemError.getApplicability()) == null) ? CandidateApplicability.RESOLVED : applicability;
    }

    public static final CandidateApplicability getResultApplicabilityForCallDiagnostics(Collection<? extends KotlinCallDiagnostic> diagnostics) {
        Object obj;
        CandidateApplicability candidateApplicability;
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        Iterator<T> it = diagnostics.iterator();
        if (it.hasNext()) {
            Object next = it.next();
            if (it.hasNext()) {
                CandidateApplicability candidateApplicability2 = ((KotlinCallDiagnostic) next).getCandidateApplicability();
                do {
                    Object next2 = it.next();
                    CandidateApplicability candidateApplicability3 = ((KotlinCallDiagnostic) next2).getCandidateApplicability();
                    if (candidateApplicability2.compareTo(candidateApplicability3) > 0) {
                        next = next2;
                        candidateApplicability2 = candidateApplicability3;
                    }
                } while (it.hasNext());
            }
            obj = next;
        } else {
            obj = null;
        }
        KotlinCallDiagnostic kotlinCallDiagnostic = (KotlinCallDiagnostic) obj;
        return (kotlinCallDiagnostic == null || (candidateApplicability = kotlinCallDiagnostic.getCandidateApplicability()) == null) ? CandidateApplicability.RESOLVED : candidateApplicability;
    }
}
