package org.jetbrains.kotlin.fir.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: ConeSimpleDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/diagnostics/ConeNotAnnotationContainer;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "text", "", "(Ljava/lang/String;)V", "reason", "getReason", "()Ljava/lang/String;", "getText", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeNotAnnotationContainer implements ConeDiagnostic {
    private final String text;

    public ConeNotAnnotationContainer(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        this.text = text;
    }

    public final String getText() {
        return this.text;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "Strange annotated expression: " + this.text;
    }
}
