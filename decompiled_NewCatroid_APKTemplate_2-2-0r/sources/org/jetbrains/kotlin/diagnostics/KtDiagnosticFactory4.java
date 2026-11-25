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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0004\b\u0002\u0010\u0003*\u0004\b\u0003\u0010\u00042\u00020\u0005B)\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\r¢\u0006\u0002\u0010\u000eJW\u0010\u0013\u001a\u001a\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00028\u00012\u0006\u0010\u0019\u001a\u00028\u00022\u0006\u0010\u001a\u001a\u00028\u00032\b\u0010\u001b\u001a\u0004\u0018\u00010\u000bH\u0007¢\u0006\u0002\u0010\u001cR\u0014\u0010\u000f\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory4;", "A", "B", "C", "D", "Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticFactory;", "name", "", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "defaultPositioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "psiType", "Lkotlin/reflect/KClass;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/diagnostics/Severity;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;Lkotlin/reflect/KClass;)V", "ktRenderer", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticRenderer;", "getKtRenderer", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticRenderer;", "on", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters4;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "c", "d", "positioningStrategy", "(Lorg/jetbrains/kotlin/AbstractKtSourceElement;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters4;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtDiagnosticFactory4<A, B, C, D> extends AbstractKtDiagnosticFactory {
    private final KtDiagnosticRenderer ktRenderer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtDiagnosticFactory4(String name2, Severity severity, AbstractSourceElementPositioningStrategy defaultPositioningStrategy, KClass<?> psiType) {
        super(name2, severity, defaultPositioningStrategy, psiType, null);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(defaultPositioningStrategy, "defaultPositioningStrategy");
        Intrinsics.checkNotNullParameter(psiType, "psiType");
        this.ktRenderer = new KtDiagnosticWithParameters4Renderer("{0}, {1}, {2}, {3}", KtDiagnosticRenderers.INSTANCE.getTO_STRING(), KtDiagnosticRenderers.INSTANCE.getTO_STRING(), KtDiagnosticRenderers.INSTANCE.getTO_STRING(), KtDiagnosticRenderers.INSTANCE.getTO_STRING());
    }

    @Override // org.jetbrains.kotlin.diagnostics.AbstractKtDiagnosticFactory
    public KtDiagnosticRenderer getKtRenderer() {
        return this.ktRenderer;
    }

    @InternalDiagnosticFactoryMethod
    public final KtDiagnosticWithParameters4<A, B, C, D> on(AbstractKtSourceElement element, A a, B b, C c, D d, AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (element instanceof KtPsiSourceElement) {
            return new KtPsiDiagnosticWithParameters4((KtPsiSourceElement) element, a, b, c, d, getSeverity(), this, positioningStrategy == null ? getDefaultPositioningStrategy() : positioningStrategy);
        }
        if (element instanceof KtLightSourceElement) {
            return new KtLightDiagnosticWithParameters4((KtLightSourceElement) element, a, b, c, d, getSeverity(), this, positioningStrategy == null ? getDefaultPositioningStrategy() : positioningStrategy);
        }
        return new KtOffsetsOnlyDiagnosticWithParameters4(element, a, b, c, d, getSeverity(), this, positioningStrategy == null ? getDefaultPositioningStrategy() : positioningStrategy);
    }
}
