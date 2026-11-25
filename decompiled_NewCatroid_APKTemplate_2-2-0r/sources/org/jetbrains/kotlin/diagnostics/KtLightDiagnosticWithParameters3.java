package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: KtDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0004\b\u0002\u0010\u00032\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u00020\u0005BO\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00028\u0000\u0012\u0006\u0010\t\u001a\u00028\u0001\u0012\u0006\u0010\n\u001a\u00028\u0002\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0018\u0010\r\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010¢\u0006\u0002\u0010\u0011J\t\u0010\u001f\u001a\u00020\u0007HÆ\u0003J\u000e\u0010 \u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u0013J\u000e\u0010!\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\u0013J\u000e\u0010\"\u001a\u00028\u0002HÆ\u0003¢\u0006\u0002\u0010\u0013J\t\u0010#\u001a\u00020\fHÆ\u0003J\u001b\u0010$\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u000eHÆ\u0003J\t\u0010%\u001a\u00020\u0010HÆ\u0003Jx\u0010&\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u00002\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00028\u00002\b\b\u0002\u0010\t\u001a\u00028\u00012\b\b\u0002\u0010\n\u001a\u00028\u00022\b\b\u0002\u0010\u000b\u001a\u00020\f2\u001a\b\u0002\u0010\r\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010HÆ\u0001¢\u0006\u0002\u0010'J\u0013\u0010(\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010+HÖ\u0003J\t\u0010,\u001a\u00020-HÖ\u0001J\t\u0010.\u001a\u00020/HÖ\u0001R\u0016\u0010\b\u001a\u00028\u0000X\u0096\u0004¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\t\u001a\u00028\u0001X\u0096\u0004¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0015\u0010\u0013R\u0016\u0010\n\u001a\u00028\u0002X\u0096\u0004¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0016\u0010\u0013R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R&\u0010\r\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u000f\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001e¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtLightDiagnosticWithParameters3;", "A", "B", "C", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters3;", "Lorg/jetbrains/kotlin/diagnostics/KtLightDiagnostic;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/KtLightSourceElement;", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "c", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "factory", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;", "positioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "(Lorg/jetbrains/kotlin/KtLightSourceElement;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/Severity;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "getA", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getB", "getC", "getElement", "()Lorg/jetbrains/kotlin/KtLightSourceElement;", "getFactory", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;", "getPositioningStrategy", "()Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "getSeverity", "()Lorg/jetbrains/kotlin/diagnostics/Severity;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Lorg/jetbrains/kotlin/KtLightSourceElement;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/Severity;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)Lorg/jetbrains/kotlin/diagnostics/KtLightDiagnosticWithParameters3;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class KtLightDiagnosticWithParameters3<A, B, C> extends KtDiagnosticWithParameters3<A, B, C> implements KtLightDiagnostic {
    private final A a;
    private final B b;
    private final C c;
    private final KtLightSourceElement element;
    private final KtDiagnosticFactory3<A, B, C> factory;
    private final AbstractSourceElementPositioningStrategy positioningStrategy;
    private final Severity severity;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ KtLightDiagnosticWithParameters3 copy$default(KtLightDiagnosticWithParameters3 ktLightDiagnosticWithParameters3, KtLightSourceElement ktLightSourceElement, Object obj, Object obj2, Object obj3, Severity severity, KtDiagnosticFactory3 ktDiagnosticFactory3, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy, int i, Object obj4) {
        if ((i & 1) != 0) {
            ktLightSourceElement = ktLightDiagnosticWithParameters3.element;
        }
        if ((i & 2) != 0) {
            obj = ktLightDiagnosticWithParameters3.a;
        }
        Object obj5 = obj;
        if ((i & 4) != 0) {
            obj2 = ktLightDiagnosticWithParameters3.b;
        }
        Object obj6 = obj2;
        if ((i & 8) != 0) {
            obj3 = ktLightDiagnosticWithParameters3.c;
        }
        Object obj7 = obj3;
        if ((i & 16) != 0) {
            severity = ktLightDiagnosticWithParameters3.severity;
        }
        Severity severity2 = severity;
        if ((i & 32) != 0) {
            ktDiagnosticFactory3 = ktLightDiagnosticWithParameters3.factory;
        }
        KtDiagnosticFactory3 ktDiagnosticFactory4 = ktDiagnosticFactory3;
        if ((i & 64) != 0) {
            abstractSourceElementPositioningStrategy = ktLightDiagnosticWithParameters3.positioningStrategy;
        }
        return ktLightDiagnosticWithParameters3.copy(ktLightSourceElement, obj5, obj6, obj7, severity2, ktDiagnosticFactory4, abstractSourceElementPositioningStrategy);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final KtLightSourceElement getElement() {
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

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final Severity getSeverity() {
        return this.severity;
    }

    public final KtDiagnosticFactory3<A, B, C> component6() {
        return this.factory;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final AbstractSourceElementPositioningStrategy getPositioningStrategy() {
        return this.positioningStrategy;
    }

    public final KtLightDiagnosticWithParameters3<A, B, C> copy(KtLightSourceElement element, A a, B b, C c, Severity severity, KtDiagnosticFactory3<A, B, C> factory, AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        return new KtLightDiagnosticWithParameters3<>(element, a, b, c, severity, factory, positioningStrategy);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KtLightDiagnosticWithParameters3)) {
            return false;
        }
        KtLightDiagnosticWithParameters3 ktLightDiagnosticWithParameters3 = (KtLightDiagnosticWithParameters3) other;
        return Intrinsics.areEqual(this.element, ktLightDiagnosticWithParameters3.element) && Intrinsics.areEqual(this.a, ktLightDiagnosticWithParameters3.a) && Intrinsics.areEqual(this.b, ktLightDiagnosticWithParameters3.b) && Intrinsics.areEqual(this.c, ktLightDiagnosticWithParameters3.c) && this.severity == ktLightDiagnosticWithParameters3.severity && Intrinsics.areEqual(this.factory, ktLightDiagnosticWithParameters3.factory) && Intrinsics.areEqual(this.positioningStrategy, ktLightDiagnosticWithParameters3.positioningStrategy);
    }

    public int hashCode() {
        int iHashCode = this.element.hashCode() * 31;
        A a = this.a;
        int iHashCode2 = (iHashCode + (a == null ? 0 : a.hashCode())) * 31;
        B b = this.b;
        int iHashCode3 = (iHashCode2 + (b == null ? 0 : b.hashCode())) * 31;
        C c = this.c;
        return ((((((iHashCode3 + (c != null ? c.hashCode() : 0)) * 31) + this.severity.hashCode()) * 31) + this.factory.hashCode()) * 31) + this.positioningStrategy.hashCode();
    }

    public String toString() {
        return "KtLightDiagnosticWithParameters3(element=" + this.element + ", a=" + this.a + ", b=" + this.b + ", c=" + this.c + ", severity=" + this.severity + ", factory=" + this.factory + ", positioningStrategy=" + this.positioningStrategy + ')';
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public KtLightSourceElement getElement() {
        return this.element;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnosticWithParameters3, org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters3Marker
    public A getA() {
        return this.a;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnosticWithParameters3, org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters3Marker
    public B getB() {
        return this.b;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnosticWithParameters3, org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters3Marker
    public C getC() {
        return this.c;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public Severity getSeverity() {
        return this.severity;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public KtDiagnosticFactory3<A, B, C> getFactory() {
        return this.factory;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public AbstractSourceElementPositioningStrategy getPositioningStrategy() {
        return this.positioningStrategy;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightDiagnosticWithParameters3(KtLightSourceElement element, A a, B b, C c, Severity severity, KtDiagnosticFactory3<A, B, C> factory, AbstractSourceElementPositioningStrategy positioningStrategy) {
        super(null);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        this.element = element;
        this.a = a;
        this.b = b;
        this.c = c;
        this.severity = severity;
        this.factory = factory;
        this.positioningStrategy = positioningStrategy;
    }
}
