package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: KtDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u00032\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0005R\u0012\u0010\u0006\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00028\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u001e\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e\u0082\u0001\u0003\u000f\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters2;", "A", "B", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticWithParameters2Marker;", "()V", "a", "getA", "()Ljava/lang/Object;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "getB", "factory", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", "getFactory", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", "Lorg/jetbrains/kotlin/diagnostics/KtLightDiagnosticWithParameters2;", "Lorg/jetbrains/kotlin/diagnostics/KtOffsetsOnlyDiagnosticWithParameters2;", "Lorg/jetbrains/kotlin/diagnostics/KtPsiDiagnosticWithParameters2;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtDiagnosticWithParameters2<A, B> extends KtDiagnostic implements DiagnosticWithParameters2Marker<A, B> {
    public /* synthetic */ KtDiagnosticWithParameters2(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters2Marker
    public abstract A getA();

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters2Marker
    public abstract B getB();

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public abstract KtDiagnosticFactory2<A, B> getFactory();

    private KtDiagnosticWithParameters2() {
        super(null);
    }
}
