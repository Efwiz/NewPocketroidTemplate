package org.jetbrains.kotlin.fir.diagnostics;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;

/* JADX INFO: compiled from: ConeSimpleDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/diagnostics/ConeAmbiguousSuper;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "candidateTypes", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Ljava/util/List;)V", "getCandidateTypes", "()Ljava/util/List;", "reason", "", "getReason", "()Ljava/lang/String;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeAmbiguousSuper implements ConeDiagnostic {
    private final List<ConeKotlinType> candidateTypes;

    /* JADX WARN: Multi-variable type inference failed */
    public ConeAmbiguousSuper(List<? extends ConeKotlinType> candidateTypes) {
        Intrinsics.checkNotNullParameter(candidateTypes, "candidateTypes");
        this.candidateTypes = candidateTypes;
    }

    public final List<ConeKotlinType> getCandidateTypes() {
        return this.candidateTypes;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "Ambiguous supertype";
    }
}
