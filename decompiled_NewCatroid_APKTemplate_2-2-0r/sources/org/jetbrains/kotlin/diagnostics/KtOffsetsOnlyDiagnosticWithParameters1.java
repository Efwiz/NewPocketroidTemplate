package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KtDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u0003B3\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00028\u0000\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\t\u0010\u0019\u001a\u00020\u0005HÆ\u0003J\u000e\u0010\u001a\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u000fJ\t\u0010\u001b\u001a\u00020\bHÆ\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\nHÆ\u0003J\t\u0010\u001d\u001a\u00020\fHÆ\u0003JL\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00028\u00002\b\b\u0002\u0010\u0007\u001a\u00020\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\n2\b\b\u0002\u0010\u000b\u001a\u00020\fHÆ\u0001¢\u0006\u0002\u0010\u001fJ\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010#HÖ\u0003J\t\u0010$\u001a\u00020%HÖ\u0001J\t\u0010&\u001a\u00020'HÖ\u0001R\u0016\u0010\u0006\u001a\u00028\u0000X\u0096\u0004¢\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtOffsetsOnlyDiagnosticWithParameters1;", "A", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters1;", "Lorg/jetbrains/kotlin/diagnostics/KtOffsetsOnlyDiagnostic;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "a", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "factory", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "positioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "(Lorg/jetbrains/kotlin/AbstractKtSourceElement;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/Severity;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "getA", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getElement", "()Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "getFactory", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "getPositioningStrategy", "()Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "getSeverity", "()Lorg/jetbrains/kotlin/diagnostics/Severity;", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Lorg/jetbrains/kotlin/AbstractKtSourceElement;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/Severity;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)Lorg/jetbrains/kotlin/diagnostics/KtOffsetsOnlyDiagnosticWithParameters1;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class KtOffsetsOnlyDiagnosticWithParameters1<A> extends KtDiagnosticWithParameters1<A> implements KtOffsetsOnlyDiagnostic {
    private final A a;
    private final AbstractKtSourceElement element;
    private final KtDiagnosticFactory1<A> factory;
    private final AbstractSourceElementPositioningStrategy positioningStrategy;
    private final Severity severity;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ KtOffsetsOnlyDiagnosticWithParameters1 copy$default(KtOffsetsOnlyDiagnosticWithParameters1 ktOffsetsOnlyDiagnosticWithParameters1, AbstractKtSourceElement abstractKtSourceElement, Object obj, Severity severity, KtDiagnosticFactory1 ktDiagnosticFactory1, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy, int i, Object obj2) {
        if ((i & 1) != 0) {
            abstractKtSourceElement = ktOffsetsOnlyDiagnosticWithParameters1.element;
        }
        if ((i & 2) != 0) {
            obj = ktOffsetsOnlyDiagnosticWithParameters1.a;
        }
        Object obj3 = obj;
        if ((i & 4) != 0) {
            severity = ktOffsetsOnlyDiagnosticWithParameters1.severity;
        }
        Severity severity2 = severity;
        if ((i & 8) != 0) {
            ktDiagnosticFactory1 = ktOffsetsOnlyDiagnosticWithParameters1.factory;
        }
        KtDiagnosticFactory1 ktDiagnosticFactory2 = ktDiagnosticFactory1;
        if ((i & 16) != 0) {
            abstractSourceElementPositioningStrategy = ktOffsetsOnlyDiagnosticWithParameters1.positioningStrategy;
        }
        return ktOffsetsOnlyDiagnosticWithParameters1.copy(abstractKtSourceElement, obj3, severity2, ktDiagnosticFactory2, abstractSourceElementPositioningStrategy);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final AbstractKtSourceElement getElement() {
        return this.element;
    }

    public final A component2() {
        return this.a;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Severity getSeverity() {
        return this.severity;
    }

    public final KtDiagnosticFactory1<A> component4() {
        return this.factory;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final AbstractSourceElementPositioningStrategy getPositioningStrategy() {
        return this.positioningStrategy;
    }

    public final KtOffsetsOnlyDiagnosticWithParameters1<A> copy(AbstractKtSourceElement element, A a, Severity severity, KtDiagnosticFactory1<A> factory, AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        return new KtOffsetsOnlyDiagnosticWithParameters1<>(element, a, severity, factory, positioningStrategy);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KtOffsetsOnlyDiagnosticWithParameters1)) {
            return false;
        }
        KtOffsetsOnlyDiagnosticWithParameters1 ktOffsetsOnlyDiagnosticWithParameters1 = (KtOffsetsOnlyDiagnosticWithParameters1) other;
        return Intrinsics.areEqual(this.element, ktOffsetsOnlyDiagnosticWithParameters1.element) && Intrinsics.areEqual(this.a, ktOffsetsOnlyDiagnosticWithParameters1.a) && this.severity == ktOffsetsOnlyDiagnosticWithParameters1.severity && Intrinsics.areEqual(this.factory, ktOffsetsOnlyDiagnosticWithParameters1.factory) && Intrinsics.areEqual(this.positioningStrategy, ktOffsetsOnlyDiagnosticWithParameters1.positioningStrategy);
    }

    public int hashCode() {
        int iHashCode = this.element.hashCode() * 31;
        A a = this.a;
        return ((((((iHashCode + (a == null ? 0 : a.hashCode())) * 31) + this.severity.hashCode()) * 31) + this.factory.hashCode()) * 31) + this.positioningStrategy.hashCode();
    }

    public String toString() {
        return "KtOffsetsOnlyDiagnosticWithParameters1(element=" + this.element + ", a=" + this.a + ", severity=" + this.severity + ", factory=" + this.factory + ", positioningStrategy=" + this.positioningStrategy + ')';
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public AbstractKtSourceElement getElement() {
        return this.element;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnosticWithParameters1, org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters1Marker
    public A getA() {
        return this.a;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public Severity getSeverity() {
        return this.severity;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public KtDiagnosticFactory1<A> getFactory() {
        return this.factory;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public AbstractSourceElementPositioningStrategy getPositioningStrategy() {
        return this.positioningStrategy;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtOffsetsOnlyDiagnosticWithParameters1(AbstractKtSourceElement element, A a, Severity severity, KtDiagnosticFactory1<A> factory, AbstractSourceElementPositioningStrategy positioningStrategy) {
        super(null);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        this.element = element;
        this.a = a;
        this.severity = severity;
        this.factory = factory;
        this.positioningStrategy = positioningStrategy;
    }
}
