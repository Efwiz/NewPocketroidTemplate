package org.jetbrains.kotlin.diagnostics;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;

/* JADX INFO: compiled from: KtDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0011R\u0012\u0010\u0012\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0012\u0010\u0016\u001a\u00020\u0017X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b8F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e\u0082\u0001\u0005\u001f !\"#¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticMarker;", "()V", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "getElement", "()Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "factory", "Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticFactory;", "getFactory", "()Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticFactory;", "factoryName", "", "getFactoryName", "()Ljava/lang/String;", "isValid", "", "()Z", "positioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "getPositioningStrategy", "()Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "getSeverity", "()Lorg/jetbrains/kotlin/diagnostics/Severity;", "textRanges", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getTextRanges", "()Ljava/util/List;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters1;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters2;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters3;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters4;", "Lorg/jetbrains/kotlin/diagnostics/KtSimpleDiagnostic;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtDiagnostic implements DiagnosticMarker {
    public /* synthetic */ KtDiagnostic(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract AbstractKtSourceElement getElement();

    public abstract AbstractKtDiagnosticFactory getFactory();

    public abstract AbstractSourceElementPositioningStrategy getPositioningStrategy();

    public abstract Severity getSeverity();

    private KtDiagnostic() {
    }

    public final List<TextRange> getTextRanges() {
        return getPositioningStrategy().markDiagnostic(this);
    }

    public final boolean isValid() {
        return getPositioningStrategy().isValid(getElement());
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticMarker
    public String getFactoryName() {
        return getFactory().getName();
    }
}
