package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: KtDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0004\b\u0002\u0010\u0003*\u0004\b\u0003\u0010\u00042\u00020\u00052\u001a\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u00040\u0006B\u0007\b\u0004¢\u0006\u0002\u0010\u0007R\u0012\u0010\b\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00028\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\nR\u0012\u0010\r\u001a\u00028\u0002X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\nR\u0012\u0010\u000f\u001a\u00028\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\nR*\u0010\u0011\u001a\u001a\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\u0082\u0001\u0003\u0015\u0016\u0017¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters4;", "A", "B", "C", "D", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticWithParameters4Marker;", "()V", "a", "getA", "()Ljava/lang/Object;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "getB", "c", "getC", "d", "getD", "factory", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory4;", "getFactory", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory4;", "Lorg/jetbrains/kotlin/diagnostics/KtLightDiagnosticWithParameters4;", "Lorg/jetbrains/kotlin/diagnostics/KtOffsetsOnlyDiagnosticWithParameters4;", "Lorg/jetbrains/kotlin/diagnostics/KtPsiDiagnosticWithParameters4;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtDiagnosticWithParameters4<A, B, C, D> extends KtDiagnostic implements DiagnosticWithParameters4Marker<A, B, C, D> {
    public /* synthetic */ KtDiagnosticWithParameters4(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters4Marker
    public abstract A getA();

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters4Marker
    public abstract B getB();

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters4Marker
    public abstract C getC();

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters4Marker
    public abstract D getD();

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public abstract KtDiagnosticFactory4<A, B, C, D> getFactory();

    private KtDiagnosticWithParameters4() {
        super(null);
    }
}
