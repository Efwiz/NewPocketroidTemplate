package org.jetbrains.kotlin.fir.resolve.diagnostics;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tR\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeFunctionCallExpectedError;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeDiagnosticWithCandidates;", "name", "Lorg/jetbrains/kotlin/name/Name;", "hasValueParameters", "", "candidates", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCandidate;", "(Lorg/jetbrains/kotlin/name/Name;ZLjava/util/Collection;)V", "getCandidates", "()Ljava/util/Collection;", "getHasValueParameters", "()Z", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeFunctionCallExpectedError implements ConeDiagnosticWithCandidates {
    private final Collection<AbstractCandidate> candidates;
    private final boolean hasValueParameters;
    private final Name name;

    /* JADX WARN: Multi-variable type inference failed */
    public ConeFunctionCallExpectedError(Name name2, boolean z, Collection<? extends AbstractCandidate> candidates) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        this.name = name2;
        this.hasValueParameters = z;
        this.candidates = candidates;
    }

    public final Name getName() {
        return this.name;
    }

    public final boolean getHasValueParameters() {
        return this.hasValueParameters;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.diagnostics.ConeDiagnosticWithCandidates
    public Collection<AbstractCandidate> getCandidates() {
        return this.candidates;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "Function call expected: " + this.name + '(' + (this.hasValueParameters ? "..." : "") + ')';
    }
}
