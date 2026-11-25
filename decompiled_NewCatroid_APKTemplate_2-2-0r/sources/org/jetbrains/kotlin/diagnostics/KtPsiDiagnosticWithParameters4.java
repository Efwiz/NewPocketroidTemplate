package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: KtDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0004\b\u0002\u0010\u0003*\u0004\b\u0003\u0010\u00042\u001a\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u00040\u00052\u00020\u0006B]\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00028\u0000\u0012\u0006\u0010\n\u001a\u00028\u0001\u0012\u0006\u0010\u000b\u001a\u00028\u0002\u0012\u0006\u0010\f\u001a\u00028\u0003\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012¢\u0006\u0002\u0010\u0013J\t\u0010\"\u001a\u00020\bHÆ\u0003J\u000e\u0010#\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u0015J\u000e\u0010$\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\u0015J\u000e\u0010%\u001a\u00028\u0002HÆ\u0003¢\u0006\u0002\u0010\u0015J\u000e\u0010&\u001a\u00028\u0003HÆ\u0003¢\u0006\u0002\u0010\u0015J\t\u0010'\u001a\u00020\u000eHÆ\u0003J!\u0010(\u001a\u001a\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0010HÆ\u0003J\t\u0010)\u001a\u00020\u0012HÆ\u0003J\u008e\u0001\u0010*\u001a\u001a\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u00002\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00028\u00002\b\b\u0002\u0010\n\u001a\u00028\u00012\b\b\u0002\u0010\u000b\u001a\u00028\u00022\b\b\u0002\u0010\f\u001a\u00028\u00032\b\b\u0002\u0010\r\u001a\u00020\u000e2 \b\u0002\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0012HÆ\u0001¢\u0006\u0002\u0010+J\u0013\u0010,\u001a\u00020-2\b\u0010.\u001a\u0004\u0018\u00010/HÖ\u0003J\t\u00100\u001a\u000201HÖ\u0001J\t\u00102\u001a\u000203HÖ\u0001R\u0016\u0010\t\u001a\u00028\u0000X\u0096\u0004¢\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\n\u001a\u00028\u0001X\u0096\u0004¢\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0017\u0010\u0015R\u0016\u0010\u000b\u001a\u00028\u0002X\u0096\u0004¢\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0018\u0010\u0015R\u0016\u0010\f\u001a\u00028\u0003X\u0096\u0004¢\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0019\u0010\u0015R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR,\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u0011\u001a\u00020\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtPsiDiagnosticWithParameters4;", "A", "B", "C", "D", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters4;", "Lorg/jetbrains/kotlin/diagnostics/KtPsiDiagnostic;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/KtPsiSourceElement;", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "c", "d", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "factory", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory4;", "positioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "(Lorg/jetbrains/kotlin/KtPsiSourceElement;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/Severity;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory4;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "getA", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getB", "getC", "getD", "getElement", "()Lorg/jetbrains/kotlin/KtPsiSourceElement;", "getFactory", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory4;", "getPositioningStrategy", "()Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "getSeverity", "()Lorg/jetbrains/kotlin/diagnostics/Severity;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Lorg/jetbrains/kotlin/KtPsiSourceElement;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/Severity;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory4;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)Lorg/jetbrains/kotlin/diagnostics/KtPsiDiagnosticWithParameters4;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class KtPsiDiagnosticWithParameters4<A, B, C, D> extends KtDiagnosticWithParameters4<A, B, C, D> implements KtPsiDiagnostic {
    private final A a;
    private final B b;
    private final C c;
    private final D d;
    private final KtPsiSourceElement element;
    private final KtDiagnosticFactory4<A, B, C, D> factory;
    private final AbstractSourceElementPositioningStrategy positioningStrategy;
    private final Severity severity;

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final KtPsiSourceElement getElement() {
        return this.element;
    }

    public final A component2() {
        return this.a;
    }

    public final B component3() {
        return this.b;
    }

    public final C component4() {
        return this.c;
    }

    public final D component5() {
        return this.d;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final Severity getSeverity() {
        return this.severity;
    }

    public final KtDiagnosticFactory4<A, B, C, D> component7() {
        return this.factory;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final AbstractSourceElementPositioningStrategy getPositioningStrategy() {
        return this.positioningStrategy;
    }

    public final KtPsiDiagnosticWithParameters4<A, B, C, D> copy(KtPsiSourceElement element, A a, B b, C c, D d, Severity severity, KtDiagnosticFactory4<A, B, C, D> factory, AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        return new KtPsiDiagnosticWithParameters4<>(element, a, b, c, d, severity, factory, positioningStrategy);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KtPsiDiagnosticWithParameters4)) {
            return false;
        }
        KtPsiDiagnosticWithParameters4 ktPsiDiagnosticWithParameters4 = (KtPsiDiagnosticWithParameters4) other;
        return Intrinsics.areEqual(this.element, ktPsiDiagnosticWithParameters4.element) && Intrinsics.areEqual(this.a, ktPsiDiagnosticWithParameters4.a) && Intrinsics.areEqual(this.b, ktPsiDiagnosticWithParameters4.b) && Intrinsics.areEqual(this.c, ktPsiDiagnosticWithParameters4.c) && Intrinsics.areEqual(this.d, ktPsiDiagnosticWithParameters4.d) && this.severity == ktPsiDiagnosticWithParameters4.severity && Intrinsics.areEqual(this.factory, ktPsiDiagnosticWithParameters4.factory) && Intrinsics.areEqual(this.positioningStrategy, ktPsiDiagnosticWithParameters4.positioningStrategy);
    }

    public int hashCode() {
        int iHashCode = this.element.hashCode() * 31;
        A a = this.a;
        int iHashCode2 = (iHashCode + (a == null ? 0 : a.hashCode())) * 31;
        B b = this.b;
        int iHashCode3 = (iHashCode2 + (b == null ? 0 : b.hashCode())) * 31;
        C c = this.c;
        int iHashCode4 = (iHashCode3 + (c == null ? 0 : c.hashCode())) * 31;
        D d = this.d;
        return ((((((iHashCode4 + (d != null ? d.hashCode() : 0)) * 31) + this.severity.hashCode()) * 31) + this.factory.hashCode()) * 31) + this.positioningStrategy.hashCode();
    }

    public String toString() {
        return "KtPsiDiagnosticWithParameters4(element=" + this.element + ", a=" + this.a + ", b=" + this.b + ", c=" + this.c + ", d=" + this.d + ", severity=" + this.severity + ", factory=" + this.factory + ", positioningStrategy=" + this.positioningStrategy + ')';
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public KtPsiSourceElement getElement() {
        return this.element;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnosticWithParameters4, org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters4Marker
    public A getA() {
        return this.a;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnosticWithParameters4, org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters4Marker
    public B getB() {
        return this.b;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnosticWithParameters4, org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters4Marker
    public C getC() {
        return this.c;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnosticWithParameters4, org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters4Marker
    public D getD() {
        return this.d;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public Severity getSeverity() {
        return this.severity;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public KtDiagnosticFactory4<A, B, C, D> getFactory() {
        return this.factory;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public AbstractSourceElementPositioningStrategy getPositioningStrategy() {
        return this.positioningStrategy;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtPsiDiagnosticWithParameters4(KtPsiSourceElement element, A a, B b, C c, D d, Severity severity, KtDiagnosticFactory4<A, B, C, D> factory, AbstractSourceElementPositioningStrategy positioningStrategy) {
        super(null);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        this.element = element;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.severity = severity;
        this.factory = factory;
        this.positioningStrategy = positioningStrategy;
        KtDiagnosticKt.checkPsiTypeConsistency(this);
    }
}
