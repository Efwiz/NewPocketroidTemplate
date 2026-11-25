package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: KtDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0007\b\u0004¢\u0006\u0002\u0010\u0004R\u0012\u0010\u0005\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u0082\u0001\u0003\f\r\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters1;", "A", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticWithParameters1Marker;", "()V", "a", "getA", "()Ljava/lang/Object;", "factory", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "getFactory", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "Lorg/jetbrains/kotlin/diagnostics/KtLightDiagnosticWithParameters1;", "Lorg/jetbrains/kotlin/diagnostics/KtOffsetsOnlyDiagnosticWithParameters1;", "Lorg/jetbrains/kotlin/diagnostics/KtPsiDiagnosticWithParameters1;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtDiagnosticWithParameters1<A> extends KtDiagnostic implements DiagnosticWithParameters1Marker<A> {
    public /* synthetic */ KtDiagnosticWithParameters1(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters1Marker
    public abstract A getA();

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public abstract KtDiagnosticFactory1<A> getFactory();

    private KtDiagnosticWithParameters1() {
        super(null);
    }
}
