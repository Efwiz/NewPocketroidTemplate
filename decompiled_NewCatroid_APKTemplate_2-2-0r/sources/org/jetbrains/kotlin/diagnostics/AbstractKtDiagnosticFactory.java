package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.KClass;

/* JADX INFO: compiled from: KtDiagnosticFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B+\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t¢\u0006\u0002\u0010\nJ\b\u0010\u0017\u001a\u00020\u0003H\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0015\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u0082\u0001\u0005\u0018\u0019\u001a\u001b\u001c¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticFactory;", "", "name", "", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "defaultPositioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "psiType", "Lkotlin/reflect/KClass;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/diagnostics/Severity;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;Lkotlin/reflect/KClass;)V", "getDefaultPositioningStrategy", "()Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "ktRenderer", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticRenderer;", "getKtRenderer", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticRenderer;", "getName", "()Ljava/lang/String;", "getPsiType", "()Lkotlin/reflect/KClass;", "getSeverity", "()Lorg/jetbrains/kotlin/diagnostics/Severity;", "toString", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory4;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractKtDiagnosticFactory {
    private final AbstractSourceElementPositioningStrategy defaultPositioningStrategy;
    private final String name;
    private final KClass<?> psiType;
    private final Severity severity;

    public /* synthetic */ AbstractKtDiagnosticFactory(String str, Severity severity, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy, KClass kClass, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, severity, abstractSourceElementPositioningStrategy, kClass);
    }

    public abstract KtDiagnosticRenderer getKtRenderer();

    private AbstractKtDiagnosticFactory(String str, Severity severity, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy, KClass<?> kClass) {
        this.name = str;
        this.severity = severity;
        this.defaultPositioningStrategy = abstractSourceElementPositioningStrategy;
        this.psiType = kClass;
    }

    public final String getName() {
        return this.name;
    }

    public final Severity getSeverity() {
        return this.severity;
    }

    public final AbstractSourceElementPositioningStrategy getDefaultPositioningStrategy() {
        return this.defaultPositioningStrategy;
    }

    public final KClass<?> getPsiType() {
        return this.psiType;
    }

    public String toString() {
        return this.name;
    }
}
