package org.jetbrains.kotlin.fir.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ConeSimpleDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/diagnostics/ConeRecursiveTypeParameterDuringErasureError;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "typeParameterName", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/name/Name;)V", "reason", "", "getReason", "()Ljava/lang/String;", "getTypeParameterName", "()Lorg/jetbrains/kotlin/name/Name;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeRecursiveTypeParameterDuringErasureError implements ConeDiagnostic {
    private final Name typeParameterName;

    public ConeRecursiveTypeParameterDuringErasureError(Name typeParameterName) {
        Intrinsics.checkNotNullParameter(typeParameterName, "typeParameterName");
        this.typeParameterName = typeParameterName;
    }

    public final Name getTypeParameterName() {
        return this.typeParameterName;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "self-recursive type parameter " + this.typeParameterName;
    }
}
