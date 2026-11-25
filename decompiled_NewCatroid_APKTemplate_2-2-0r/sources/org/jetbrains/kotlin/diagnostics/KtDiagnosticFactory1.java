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
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B)\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u0010\u000bJ-\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00028\u00002\b\u0010\u0015\u001a\u0004\u0018\u00010\bH\u0007¢\u0006\u0002\u0010\u0016R\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "A", "Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticFactory;", "name", "", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "defaultPositioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "psiType", "Lkotlin/reflect/KClass;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/diagnostics/Severity;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;Lkotlin/reflect/KClass;)V", "ktRenderer", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticRenderer;", "getKtRenderer", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticRenderer;", "on", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters1;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "a", "positioningStrategy", "(Lorg/jetbrains/kotlin/AbstractKtSourceElement;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters1;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtDiagnosticFactory1<A> extends AbstractKtDiagnosticFactory {
    private final KtDiagnosticRenderer ktRenderer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtDiagnosticFactory1(String name2, Severity severity, AbstractSourceElementPositioningStrategy defaultPositioningStrategy, KClass<?> psiType) {
        super(name2, severity, defaultPositioningStrategy, psiType, null);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(defaultPositioningStrategy, "defaultPositioningStrategy");
        Intrinsics.checkNotNullParameter(psiType, "psiType");
        this.ktRenderer = new KtDiagnosticWithParameters1Renderer("{0}", KtDiagnosticRenderers.INSTANCE.getTO_STRING());
    }

    @Override // org.jetbrains.kotlin.diagnostics.AbstractKtDiagnosticFactory
    public KtDiagnosticRenderer getKtRenderer() {
        return this.ktRenderer;
    }

    @InternalDiagnosticFactoryMethod
    public final KtDiagnosticWithParameters1<A> on(AbstractKtSourceElement element, A a, AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (element instanceof KtPsiSourceElement) {
            KtPsiSourceElement ktPsiSourceElement = (KtPsiSourceElement) element;
            Severity severity = getSeverity();
            if (positioningStrategy == null) {
                positioningStrategy = getDefaultPositioningStrategy();
            }
            return new KtPsiDiagnosticWithParameters1(ktPsiSourceElement, a, severity, this, positioningStrategy);
        }
        if (element instanceof KtLightSourceElement) {
            KtLightSourceElement ktLightSourceElement = (KtLightSourceElement) element;
            Severity severity2 = getSeverity();
            if (positioningStrategy == null) {
                positioningStrategy = getDefaultPositioningStrategy();
            }
            return new KtLightDiagnosticWithParameters1(ktLightSourceElement, a, severity2, this, positioningStrategy);
        }
        Severity severity3 = getSeverity();
        if (positioningStrategy == null) {
            positioningStrategy = getDefaultPositioningStrategy();
        }
        return new KtOffsetsOnlyDiagnosticWithParameters1(element, a, severity3, this, positioningStrategy);
    }
}
