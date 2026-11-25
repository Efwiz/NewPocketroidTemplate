package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: KtDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\u00020\u0004BA\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00028\u0000\u0012\u0006\u0010\b\u001a\u00028\u0001\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\f\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ\t\u0010\u001c\u001a\u00020\u0006HÆ\u0003J\u000e\u0010\u001d\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u0011J\u000e\u0010\u001e\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\u0011J\t\u0010\u001f\u001a\u00020\nHÆ\u0003J\u0015\u0010 \u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\fHÆ\u0003J\t\u0010!\u001a\u00020\u000eHÆ\u0003Jb\u0010\"\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00028\u00002\b\b\u0002\u0010\b\u001a\u00028\u00012\b\b\u0002\u0010\t\u001a\u00020\n2\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\f2\b\b\u0002\u0010\r\u001a\u00020\u000eHÆ\u0001¢\u0006\u0002\u0010#J\u0013\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010'HÖ\u0003J\t\u0010(\u001a\u00020)HÖ\u0001J\t\u0010*\u001a\u00020+HÖ\u0001R\u0016\u0010\u0007\u001a\u00028\u0000X\u0096\u0004¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\b\u001a\u00028\u0001X\u0096\u0004¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0013\u0010\u0011R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R \u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtOffsetsOnlyDiagnosticWithParameters2;", "A", "B", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters2;", "Lorg/jetbrains/kotlin/diagnostics/KtOffsetsOnlyDiagnostic;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "factory", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", "positioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "(Lorg/jetbrains/kotlin/AbstractKtSourceElement;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/Severity;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "getA", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getB", "getElement", "()Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "getFactory", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", "getPositioningStrategy", "()Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "getSeverity", "()Lorg/jetbrains/kotlin/diagnostics/Severity;", "component1", "component2", "component3", "component4", "component5", "component6", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Lorg/jetbrains/kotlin/AbstractKtSourceElement;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/Severity;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)Lorg/jetbrains/kotlin/diagnostics/KtOffsetsOnlyDiagnosticWithParameters2;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class KtOffsetsOnlyDiagnosticWithParameters2<A, B> extends KtDiagnosticWithParameters2<A, B> implements KtOffsetsOnlyDiagnostic {
    private final A a;
    private final B b;
    private final AbstractKtSourceElement element;
    private final KtDiagnosticFactory2<A, B> factory;
    private final AbstractSourceElementPositioningStrategy positioningStrategy;
    private final Severity severity;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ KtOffsetsOnlyDiagnosticWithParameters2 copy$default(KtOffsetsOnlyDiagnosticWithParameters2 ktOffsetsOnlyDiagnosticWithParameters2, AbstractKtSourceElement abstractKtSourceElement, Object obj, Object obj2, Severity severity, KtDiagnosticFactory2 ktDiagnosticFactory2, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy, int i, Object obj3) {
        if ((i & 1) != 0) {
            abstractKtSourceElement = ktOffsetsOnlyDiagnosticWithParameters2.element;
        }
        if ((i & 2) != 0) {
            obj = ktOffsetsOnlyDiagnosticWithParameters2.a;
        }
        Object obj4 = obj;
        if ((i & 4) != 0) {
            obj2 = ktOffsetsOnlyDiagnosticWithParameters2.b;
        }
        Object obj5 = obj2;
        if ((i & 8) != 0) {
            severity = ktOffsetsOnlyDiagnosticWithParameters2.severity;
        }
        Severity severity2 = severity;
        if ((i & 16) != 0) {
            ktDiagnosticFactory2 = ktOffsetsOnlyDiagnosticWithParameters2.factory;
        }
        KtDiagnosticFactory2 ktDiagnosticFactory3 = ktDiagnosticFactory2;
        if ((i & 32) != 0) {
            abstractSourceElementPositioningStrategy = ktOffsetsOnlyDiagnosticWithParameters2.positioningStrategy;
        }
        return ktOffsetsOnlyDiagnosticWithParameters2.copy(abstractKtSourceElement, obj4, obj5, severity2, ktDiagnosticFactory3, abstractSourceElementPositioningStrategy);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final AbstractKtSourceElement getElement() {
        return this.element;
    }

    public final A component2() {
        return this.a;
    }

    public final B component3() {
        return this.b;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final Severity getSeverity() {
        return this.severity;
    }

    public final KtDiagnosticFactory2<A, B> component5() {
        return this.factory;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final AbstractSourceElementPositioningStrategy getPositioningStrategy() {
        return this.positioningStrategy;
    }

    public final KtOffsetsOnlyDiagnosticWithParameters2<A, B> copy(AbstractKtSourceElement element, A a, B b, Severity severity, KtDiagnosticFactory2<A, B> factory, AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        return new KtOffsetsOnlyDiagnosticWithParameters2<>(element, a, b, severity, factory, positioningStrategy);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KtOffsetsOnlyDiagnosticWithParameters2)) {
            return false;
        }
        KtOffsetsOnlyDiagnosticWithParameters2 ktOffsetsOnlyDiagnosticWithParameters2 = (KtOffsetsOnlyDiagnosticWithParameters2) other;
        return Intrinsics.areEqual(this.element, ktOffsetsOnlyDiagnosticWithParameters2.element) && Intrinsics.areEqual(this.a, ktOffsetsOnlyDiagnosticWithParameters2.a) && Intrinsics.areEqual(this.b, ktOffsetsOnlyDiagnosticWithParameters2.b) && this.severity == ktOffsetsOnlyDiagnosticWithParameters2.severity && Intrinsics.areEqual(this.factory, ktOffsetsOnlyDiagnosticWithParameters2.factory) && Intrinsics.areEqual(this.positioningStrategy, ktOffsetsOnlyDiagnosticWithParameters2.positioningStrategy);
    }

    public int hashCode() {
        int iHashCode = this.element.hashCode() * 31;
        A a = this.a;
        int iHashCode2 = (iHashCode + (a == null ? 0 : a.hashCode())) * 31;
        B b = this.b;
        return ((((((iHashCode2 + (b != null ? b.hashCode() : 0)) * 31) + this.severity.hashCode()) * 31) + this.factory.hashCode()) * 31) + this.positioningStrategy.hashCode();
    }

    public String toString() {
        return "KtOffsetsOnlyDiagnosticWithParameters2(element=" + this.element + ", a=" + this.a + ", b=" + this.b + ", severity=" + this.severity + ", factory=" + this.factory + ", positioningStrategy=" + this.positioningStrategy + ')';
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public AbstractKtSourceElement getElement() {
        return this.element;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnosticWithParameters2, org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters2Marker
    public A getA() {
        return this.a;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnosticWithParameters2, org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters2Marker
    public B getB() {
        return this.b;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public Severity getSeverity() {
        return this.severity;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public KtDiagnosticFactory2<A, B> getFactory() {
        return this.factory;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public AbstractSourceElementPositioningStrategy getPositioningStrategy() {
        return this.positioningStrategy;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtOffsetsOnlyDiagnosticWithParameters2(AbstractKtSourceElement element, A a, B b, Severity severity, KtDiagnosticFactory2<A, B> factory, AbstractSourceElementPositioningStrategy positioningStrategy) {
        super(null);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        this.element = element;
        this.a = a;
        this.b = b;
        this.severity = severity;
        this.factory = factory;
        this.positioningStrategy = positioningStrategy;
    }
}
