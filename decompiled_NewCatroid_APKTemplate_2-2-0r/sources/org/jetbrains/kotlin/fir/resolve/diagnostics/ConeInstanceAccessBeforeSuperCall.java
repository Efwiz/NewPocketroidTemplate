package org.jetbrains.kotlin.fir.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeInstanceAccessBeforeSuperCall;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "target", "", "(Ljava/lang/String;)V", "reason", "getReason", "()Ljava/lang/String;", "getTarget", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeInstanceAccessBeforeSuperCall implements ConeDiagnostic {
    private final String target;

    public ConeInstanceAccessBeforeSuperCall(String target) {
        Intrinsics.checkNotNullParameter(target, "target");
        this.target = target;
    }

    public final String getTarget() {
        return this.target;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "Cannot access ''" + this.target + "'' before the instance has been initialized";
    }
}
