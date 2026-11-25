package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: KtDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0004\b\u0002\u0010\u00032\u00020\u00042\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0006R\u0012\u0010\u0007\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00028\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u0012\u0010\f\u001a\u00028\u0002X¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\tR$\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011\u0082\u0001\u0003\u0012\u0013\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters3;", "A", "B", "C", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticWithParameters3Marker;", "()V", "a", "getA", "()Ljava/lang/Object;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "getB", "c", "getC", "factory", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;", "getFactory", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;", "Lorg/jetbrains/kotlin/diagnostics/KtLightDiagnosticWithParameters3;", "Lorg/jetbrains/kotlin/diagnostics/KtOffsetsOnlyDiagnosticWithParameters3;", "Lorg/jetbrains/kotlin/diagnostics/KtPsiDiagnosticWithParameters3;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtDiagnosticWithParameters3<A, B, C> extends KtDiagnostic implements DiagnosticWithParameters3Marker<A, B, C> {
    public /* synthetic */ KtDiagnosticWithParameters3(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters3Marker
    public abstract A getA();

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters3Marker
    public abstract B getB();

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters3Marker
    public abstract C getC();

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public abstract KtDiagnosticFactory3<A, B, C> getFactory();

    private KtDiagnosticWithParameters3() {
        super(null);
    }
}
