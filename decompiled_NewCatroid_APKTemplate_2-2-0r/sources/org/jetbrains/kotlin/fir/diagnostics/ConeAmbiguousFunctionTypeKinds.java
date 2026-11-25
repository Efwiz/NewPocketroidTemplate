package org.jetbrains.kotlin.fir.diagnostics;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;

/* JADX INFO: compiled from: ConeSimpleDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/diagnostics/ConeAmbiguousFunctionTypeKinds;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "kinds", "", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "(Ljava/util/List;)V", "getKinds", "()Ljava/util/List;", "reason", "", "getReason", "()Ljava/lang/String;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeAmbiguousFunctionTypeKinds implements ConeDiagnostic {
    private final List<FunctionTypeKind> kinds;

    /* JADX WARN: Multi-variable type inference failed */
    public ConeAmbiguousFunctionTypeKinds(List<? extends FunctionTypeKind> kinds) {
        Intrinsics.checkNotNullParameter(kinds, "kinds");
        this.kinds = kinds;
    }

    public final List<FunctionTypeKind> getKinds() {
        return this.kinds;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "There are multiple function kinds for functional type ref";
    }
}
