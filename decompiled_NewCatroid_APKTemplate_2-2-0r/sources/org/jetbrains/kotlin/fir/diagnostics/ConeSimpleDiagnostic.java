package org.jetbrains.kotlin.fir.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ConeSimpleDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/diagnostics/ConeSimpleDiagnostic;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "reason", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/fir/diagnostics/DiagnosticKind;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/fir/diagnostics/DiagnosticKind;)V", "getKind", "()Lorg/jetbrains/kotlin/fir/diagnostics/DiagnosticKind;", "getReason", "()Ljava/lang/String;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeSimpleDiagnostic implements ConeDiagnostic {
    private final DiagnosticKind kind;
    private final String reason;

    public ConeSimpleDiagnostic(String reason, DiagnosticKind kind) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.reason = reason;
        this.kind = kind;
    }

    public /* synthetic */ ConeSimpleDiagnostic(String str, DiagnosticKind diagnosticKind, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? DiagnosticKind.Other : diagnosticKind);
    }

    public final DiagnosticKind getKind() {
        return this.kind;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return this.reason;
    }
}
