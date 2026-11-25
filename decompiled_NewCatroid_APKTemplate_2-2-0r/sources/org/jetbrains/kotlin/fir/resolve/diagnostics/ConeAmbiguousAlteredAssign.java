package org.jetbrains.kotlin.fir.resolve.diagnostics;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0019\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeAmbiguousAlteredAssign;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "altererNames", "", "", "(Ljava/util/List;)V", "getAltererNames", "()Ljava/util/List;", "reason", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeAmbiguousAlteredAssign implements ConeDiagnostic {
    private final List<String> altererNames;

    public ConeAmbiguousAlteredAssign(List<String> altererNames) {
        Intrinsics.checkNotNullParameter(altererNames, "altererNames");
        this.altererNames = altererNames;
    }

    public final List<String> getAltererNames() {
        return this.altererNames;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "Assign altered by multiple extensions";
    }
}
