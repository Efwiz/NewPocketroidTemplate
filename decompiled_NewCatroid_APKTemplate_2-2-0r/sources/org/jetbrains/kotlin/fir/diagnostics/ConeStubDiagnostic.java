package org.jetbrains.kotlin.fir.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ConeDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/diagnostics/ConeStubDiagnostic;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "original", "(Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;)V", "getOriginal", "()Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "reason", "", "getReason", "()Ljava/lang/String;", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeStubDiagnostic implements ConeDiagnostic {
    private final ConeDiagnostic original;

    public ConeStubDiagnostic(ConeDiagnostic original) {
        Intrinsics.checkNotNullParameter(original, "original");
        this.original = original;
    }

    public final ConeDiagnostic getOriginal() {
        return this.original;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return this.original.getReason();
    }
}
