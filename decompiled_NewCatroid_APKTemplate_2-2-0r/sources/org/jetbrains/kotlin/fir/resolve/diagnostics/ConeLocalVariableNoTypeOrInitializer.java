package org.jetbrains.kotlin.fir.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeLocalVariableNoTypeOrInitializer;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "variable", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "(Lorg/jetbrains/kotlin/fir/declarations/FirVariable;)V", "reason", "", "getReason", "()Ljava/lang/String;", "getVariable", "()Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeLocalVariableNoTypeOrInitializer implements ConeDiagnostic {
    private final FirVariable variable;

    public ConeLocalVariableNoTypeOrInitializer(FirVariable variable) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        this.variable = variable;
    }

    public final FirVariable getVariable() {
        return this.variable;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "Cannot infer variable type without initializer / getter / delegate";
    }
}
