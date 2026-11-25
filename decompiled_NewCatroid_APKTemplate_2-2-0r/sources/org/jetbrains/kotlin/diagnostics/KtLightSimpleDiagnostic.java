package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KtDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u00012\u00020\u0002B%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\t\u0010\u0014\u001a\u00020\u0004HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0016\u001a\u00020\bHÆ\u0003J\t\u0010\u0017\u001a\u00020\nHÆ\u0003J1\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nHÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cHÖ\u0003J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001J\t\u0010\u001f\u001a\u00020 HÖ\u0001R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtLightSimpleDiagnostic;", "Lorg/jetbrains/kotlin/diagnostics/KtSimpleDiagnostic;", "Lorg/jetbrains/kotlin/diagnostics/KtLightDiagnostic;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/KtLightSourceElement;", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "factory", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "positioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "(Lorg/jetbrains/kotlin/KtLightSourceElement;Lorg/jetbrains/kotlin/diagnostics/Severity;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "getElement", "()Lorg/jetbrains/kotlin/KtLightSourceElement;", "getFactory", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "getPositioningStrategy", "()Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "getSeverity", "()Lorg/jetbrains/kotlin/diagnostics/Severity;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class KtLightSimpleDiagnostic extends KtSimpleDiagnostic implements KtLightDiagnostic {
    private final KtLightSourceElement element;
    private final KtDiagnosticFactory0 factory;
    private final AbstractSourceElementPositioningStrategy positioningStrategy;
    private final Severity severity;

    public static /* synthetic */ KtLightSimpleDiagnostic copy$default(KtLightSimpleDiagnostic ktLightSimpleDiagnostic, KtLightSourceElement ktLightSourceElement, Severity severity, KtDiagnosticFactory0 ktDiagnosticFactory0, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy, int i, Object obj) {
        if ((i & 1) != 0) {
            ktLightSourceElement = ktLightSimpleDiagnostic.element;
        }
        if ((i & 2) != 0) {
            severity = ktLightSimpleDiagnostic.severity;
        }
        if ((i & 4) != 0) {
            ktDiagnosticFactory0 = ktLightSimpleDiagnostic.factory;
        }
        if ((i & 8) != 0) {
            abstractSourceElementPositioningStrategy = ktLightSimpleDiagnostic.positioningStrategy;
        }
        return ktLightSimpleDiagnostic.copy(ktLightSourceElement, severity, ktDiagnosticFactory0, abstractSourceElementPositioningStrategy);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final KtLightSourceElement getElement() {
        return this.element;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Severity getSeverity() {
        return this.severity;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final KtDiagnosticFactory0 getFactory() {
        return this.factory;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final AbstractSourceElementPositioningStrategy getPositioningStrategy() {
        return this.positioningStrategy;
    }

    public final KtLightSimpleDiagnostic copy(KtLightSourceElement element, Severity severity, KtDiagnosticFactory0 factory, AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        return new KtLightSimpleDiagnostic(element, severity, factory, positioningStrategy);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KtLightSimpleDiagnostic)) {
            return false;
        }
        KtLightSimpleDiagnostic ktLightSimpleDiagnostic = (KtLightSimpleDiagnostic) other;
        return Intrinsics.areEqual(this.element, ktLightSimpleDiagnostic.element) && this.severity == ktLightSimpleDiagnostic.severity && Intrinsics.areEqual(this.factory, ktLightSimpleDiagnostic.factory) && Intrinsics.areEqual(this.positioningStrategy, ktLightSimpleDiagnostic.positioningStrategy);
    }

    public int hashCode() {
        return (((((this.element.hashCode() * 31) + this.severity.hashCode()) * 31) + this.factory.hashCode()) * 31) + this.positioningStrategy.hashCode();
    }

    public String toString() {
        return "KtLightSimpleDiagnostic(element=" + this.element + ", severity=" + this.severity + ", factory=" + this.factory + ", positioningStrategy=" + this.positioningStrategy + ')';
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public KtLightSourceElement getElement() {
        return this.element;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public Severity getSeverity() {
        return this.severity;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public KtDiagnosticFactory0 getFactory() {
        return this.factory;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnostic
    public AbstractSourceElementPositioningStrategy getPositioningStrategy() {
        return this.positioningStrategy;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightSimpleDiagnostic(KtLightSourceElement element, Severity severity, KtDiagnosticFactory0 factory, AbstractSourceElementPositioningStrategy positioningStrategy) {
        super(null);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        this.element = element;
        this.severity = severity;
        this.factory = factory;
        this.positioningStrategy = positioningStrategy;
    }
}
