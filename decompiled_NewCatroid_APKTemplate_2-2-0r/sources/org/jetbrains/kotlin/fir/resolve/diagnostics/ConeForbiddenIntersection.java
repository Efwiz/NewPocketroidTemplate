package org.jetbrains.kotlin.fir.resolve.diagnostics;

import kotlin.Metadata;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeForbiddenIntersection;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "()V", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeForbiddenIntersection implements ConeDiagnostic {
    public static final ConeForbiddenIntersection INSTANCE = new ConeForbiddenIntersection();

    private ConeForbiddenIntersection() {
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "Such an intersection type is not allowed";
    }
}
