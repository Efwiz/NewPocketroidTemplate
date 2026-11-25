package org.jetbrains.kotlin.fir.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;

/* JADX INFO: compiled from: ConeSimpleDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/diagnostics/ConeCannotInferTypeParameterType;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "typeParameter", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "reason", "", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;Ljava/lang/String;)V", "getReason", "()Ljava/lang/String;", "getTypeParameter", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeCannotInferTypeParameterType implements ConeDiagnostic {
    private final String reason;
    private final FirTypeParameterSymbol typeParameter;

    public ConeCannotInferTypeParameterType(FirTypeParameterSymbol typeParameter, String reason) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(reason, "reason");
        this.typeParameter = typeParameter;
        this.reason = reason;
    }

    public final FirTypeParameterSymbol getTypeParameter() {
        return this.typeParameter;
    }

    public /* synthetic */ ConeCannotInferTypeParameterType(FirTypeParameterSymbol firTypeParameterSymbol, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firTypeParameterSymbol, (i & 2) != 0 ? "Cannot infer type for parameter " + firTypeParameterSymbol.getName() : str);
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return this.reason;
    }
}
