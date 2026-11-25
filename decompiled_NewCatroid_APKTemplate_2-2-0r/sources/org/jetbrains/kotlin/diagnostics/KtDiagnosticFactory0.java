package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX INFO: compiled from: KtDiagnosticFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t¢\u0006\u0002\u0010\nJ\u001a\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0007H\u0007R\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticFactory;", "name", "", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "defaultPositioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "psiType", "Lkotlin/reflect/KClass;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/diagnostics/Severity;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;Lkotlin/reflect/KClass;)V", "ktRenderer", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticRenderer;", "getKtRenderer", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticRenderer;", "on", "Lorg/jetbrains/kotlin/diagnostics/KtSimpleDiagnostic;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "positioningStrategy", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtDiagnosticFactory0 extends AbstractKtDiagnosticFactory {
    private final KtDiagnosticRenderer ktRenderer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtDiagnosticFactory0(String name2, Severity severity, AbstractSourceElementPositioningStrategy defaultPositioningStrategy, KClass<?> psiType) {
        super(name2, severity, defaultPositioningStrategy, psiType, null);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(defaultPositioningStrategy, "defaultPositioningStrategy");
        Intrinsics.checkNotNullParameter(psiType, "psiType");
        this.ktRenderer = new SimpleKtDiagnosticRenderer("");
    }

    @Override // org.jetbrains.kotlin.diagnostics.AbstractKtDiagnosticFactory
    public KtDiagnosticRenderer getKtRenderer() {
        return this.ktRenderer;
    }

    @InternalDiagnosticFactoryMethod
    public final KtSimpleDiagnostic on(AbstractKtSourceElement element, AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (element instanceof KtPsiSourceElement) {
            KtPsiSourceElement ktPsiSourceElement = (KtPsiSourceElement) element;
            Severity severity = getSeverity();
            if (positioningStrategy == null) {
                positioningStrategy = getDefaultPositioningStrategy();
            }
            return new KtPsiSimpleDiagnostic(ktPsiSourceElement, severity, this, positioningStrategy);
        }
        if (!(element instanceof KtLightSourceElement)) {
            Severity severity2 = getSeverity();
            if (positioningStrategy == null) {
                positioningStrategy = getDefaultPositioningStrategy();
            }
            return new KtOffsetsOnlySimpleDiagnostic(element, severity2, this, positioningStrategy);
        }
        KtLightSourceElement ktLightSourceElement = (KtLightSourceElement) element;
        Severity severity3 = getSeverity();
        if (positioningStrategy == null) {
            positioningStrategy = getDefaultPositioningStrategy();
        }
        return new KtLightSimpleDiagnostic(ktLightSourceElement, severity3, this, positioningStrategy);
    }
}
