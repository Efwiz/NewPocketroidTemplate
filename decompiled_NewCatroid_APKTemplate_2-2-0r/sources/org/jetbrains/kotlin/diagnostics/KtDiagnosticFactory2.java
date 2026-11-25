package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: KtDiagnosticFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003B)\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b¢\u0006\u0002\u0010\fJ;\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00028\u00012\b\u0010\u0017\u001a\u0004\u0018\u00010\tH\u0007¢\u0006\u0002\u0010\u0018R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", "A", "B", "Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticFactory;", "name", "", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "defaultPositioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "psiType", "Lkotlin/reflect/KClass;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/diagnostics/Severity;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;Lkotlin/reflect/KClass;)V", "ktRenderer", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticRenderer;", "getKtRenderer", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticRenderer;", "on", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters2;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "positioningStrategy", "(Lorg/jetbrains/kotlin/AbstractKtSourceElement;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters2;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtDiagnosticFactory2<A, B> extends AbstractKtDiagnosticFactory {
    private final KtDiagnosticRenderer ktRenderer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtDiagnosticFactory2(String name2, Severity severity, AbstractSourceElementPositioningStrategy defaultPositioningStrategy, KClass<?> psiType) {
        super(name2, severity, defaultPositioningStrategy, psiType, null);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(defaultPositioningStrategy, "defaultPositioningStrategy");
        Intrinsics.checkNotNullParameter(psiType, "psiType");
        this.ktRenderer = new KtDiagnosticWithParameters2Renderer("{0}, {1}", KtDiagnosticRenderers.INSTANCE.getTO_STRING(), KtDiagnosticRenderers.INSTANCE.getTO_STRING());
    }

    @Override // org.jetbrains.kotlin.diagnostics.AbstractKtDiagnosticFactory
    public KtDiagnosticRenderer getKtRenderer() {
        return this.ktRenderer;
    }

    @InternalDiagnosticFactoryMethod
    public final KtDiagnosticWithParameters2<A, B> on(AbstractKtSourceElement element, A a, B b, AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (element instanceof KtPsiSourceElement) {
            KtPsiSourceElement ktPsiSourceElement = (KtPsiSourceElement) element;
            Severity severity = getSeverity();
            if (positioningStrategy == null) {
                positioningStrategy = getDefaultPositioningStrategy();
            }
            return new KtPsiDiagnosticWithParameters2(ktPsiSourceElement, a, b, severity, this, positioningStrategy);
        }
        if (element instanceof KtLightSourceElement) {
            KtLightSourceElement ktLightSourceElement = (KtLightSourceElement) element;
            Severity severity2 = getSeverity();
            if (positioningStrategy == null) {
                positioningStrategy = getDefaultPositioningStrategy();
            }
            return new KtLightDiagnosticWithParameters2(ktLightSourceElement, a, b, severity2, this, positioningStrategy);
        }
        Severity severity3 = getSeverity();
        if (positioningStrategy == null) {
            positioningStrategy = getDefaultPositioningStrategy();
        }
        return new KtOffsetsOnlyDiagnosticWithParameters2(element, a, b, severity3, this, positioningStrategy);
    }
}
