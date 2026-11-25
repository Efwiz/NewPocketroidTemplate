package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: KtDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0003\u0007\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtSimpleDiagnostic;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "()V", "factory", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "getFactory", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "Lorg/jetbrains/kotlin/diagnostics/KtLightSimpleDiagnostic;", "Lorg/jetbrains/kotlin/diagnostics/KtOffsetsOnlySimpleDiagnostic;", "Lorg/jetbrains/kotlin/diagnostics/KtPsiSimpleDiagnostic;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtSimpleDiagnostic extends KtDiagnostic {
    public /* synthetic */ KtSimpleDiagnostic(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public abstract KtDiagnosticFactory0 getFactory();

    private KtSimpleDiagnostic() {
        super(null);
    }
}
