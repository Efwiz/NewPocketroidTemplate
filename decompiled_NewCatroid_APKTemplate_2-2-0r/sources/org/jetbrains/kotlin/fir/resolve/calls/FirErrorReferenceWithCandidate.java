package org.jetbrains.kotlin.fir.resolve.calls;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirNamedReferenceWithCandidate.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B'\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/FirErrorReferenceWithCandidate;", "Lorg/jetbrains/kotlin/fir/resolve/calls/FirNamedReferenceWithCandidate;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "name", "Lorg/jetbrains/kotlin/name/Name;", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;)V", "getDiagnostic", "()Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "isError", "", "()Z", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirErrorReferenceWithCandidate extends FirNamedReferenceWithCandidate {
    private final ConeDiagnostic diagnostic;

    @Override // org.jetbrains.kotlin.fir.resolve.calls.FirNamedReferenceWithCandidate
    public boolean isError() {
        return true;
    }

    public final ConeDiagnostic getDiagnostic() {
        return this.diagnostic;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirErrorReferenceWithCandidate(KtSourceElement ktSourceElement, Name name2, Candidate candidate, ConeDiagnostic diagnostic) {
        super(ktSourceElement, name2, candidate);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        this.diagnostic = diagnostic;
    }
}
