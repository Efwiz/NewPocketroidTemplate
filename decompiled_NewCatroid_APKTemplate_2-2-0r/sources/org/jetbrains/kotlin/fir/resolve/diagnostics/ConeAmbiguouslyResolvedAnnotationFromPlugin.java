package org.jetbrains.kotlin.fir.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeAmbiguouslyResolvedAnnotationFromPlugin;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "typeFromCompilerPhase", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "typeFromTypesPhase", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)V", "reason", "", "getReason", "()Ljava/lang/String;", "getTypeFromCompilerPhase", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getTypeFromTypesPhase", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeAmbiguouslyResolvedAnnotationFromPlugin implements ConeDiagnostic {
    private final ConeKotlinType typeFromCompilerPhase;
    private final ConeKotlinType typeFromTypesPhase;

    public ConeAmbiguouslyResolvedAnnotationFromPlugin(ConeKotlinType typeFromCompilerPhase, ConeKotlinType typeFromTypesPhase) {
        Intrinsics.checkNotNullParameter(typeFromCompilerPhase, "typeFromCompilerPhase");
        Intrinsics.checkNotNullParameter(typeFromTypesPhase, "typeFromTypesPhase");
        this.typeFromCompilerPhase = typeFromCompilerPhase;
        this.typeFromTypesPhase = typeFromTypesPhase;
    }

    public final ConeKotlinType getTypeFromCompilerPhase() {
        return this.typeFromCompilerPhase;
    }

    public final ConeKotlinType getTypeFromTypesPhase() {
        return this.typeFromTypesPhase;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "\n            Annotation type resolved differently on compiler annotation and types stages:\n              - compiler annotations: " + this.typeFromCompilerPhase + "\n              - types stage: " + this.typeFromTypesPhase + "\n        ";
    }
}
