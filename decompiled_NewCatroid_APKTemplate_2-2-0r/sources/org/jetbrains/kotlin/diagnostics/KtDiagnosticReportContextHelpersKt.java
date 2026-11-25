package org.jetbrains.kotlin.diagnostics;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: KtDiagnosticReportContextHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000h\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a1\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tR\u00020\u0003¢\u0006\u0002\u0010\n\u001aI\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u000b*\u00020\f*\u00020\u00022\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u000b0\r2\u0006\u0010\u000e\u001a\u0002H\u000b2\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tR\u00020\u0003¢\u0006\u0002\u0010\u000f\u001aa\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u000b*\u00020\f\"\b\b\u0001\u0010\u0010*\u00020\f*\u00020\u00022\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\u00100\u00112\u0006\u0010\u000e\u001a\u0002H\u000b2\u0006\u0010\u0012\u001a\u0002H\u00102\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tR\u00020\u0003¢\u0006\u0002\u0010\u0013\u001ay\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u000b*\u00020\f\"\b\b\u0001\u0010\u0010*\u00020\f\"\b\b\u0002\u0010\u0014*\u00020\f*\u00020\u00022\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0018\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00140\u00152\u0006\u0010\u000e\u001a\u0002H\u000b2\u0006\u0010\u0012\u001a\u0002H\u00102\u0006\u0010\u0016\u001a\u0002H\u00142\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tR\u00020\u0003¢\u0006\u0002\u0010\u0017\u001a\u0091\u0001\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u000b*\u00020\f\"\b\b\u0001\u0010\u0010*\u00020\f\"\b\b\u0002\u0010\u0014*\u00020\f\"\b\b\u0003\u0010\u0018*\u00020\f*\u00020\u00022\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u001e\u0010\u0006\u001a\u001a\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u0002H\u00180\u00192\u0006\u0010\u000e\u001a\u0002H\u000b2\u0006\u0010\u0012\u001a\u0002H\u00102\u0006\u0010\u0016\u001a\u0002H\u00142\u0006\u0010\u001a\u001a\u0002H\u00182\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tR\u00020\u0003¢\u0006\u0002\u0010\u001b\u001a1\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u001c2\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tR\u00020\u0003¢\u0006\u0002\u0010\u001d\u001aI\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u000b*\u00020\f*\u00020\u00022\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u001e2\u0006\u0010\u000e\u001a\u0002H\u000b2\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tR\u00020\u0003¢\u0006\u0002\u0010\u001f\u001aa\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u000b*\u00020\f\"\b\b\u0001\u0010\u0010*\u00020\f*\u00020\u00022\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\u00100 2\u0006\u0010\u000e\u001a\u0002H\u000b2\u0006\u0010\u0012\u001a\u0002H\u00102\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tR\u00020\u0003¢\u0006\u0002\u0010!\u001ay\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u000b*\u00020\f\"\b\b\u0001\u0010\u0010*\u00020\f\"\b\b\u0002\u0010\u0014*\u00020\f*\u00020\u00022\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0018\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00140\"2\u0006\u0010\u000e\u001a\u0002H\u000b2\u0006\u0010\u0012\u001a\u0002H\u00102\u0006\u0010\u0016\u001a\u0002H\u00142\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tR\u00020\u0003¢\u0006\u0002\u0010#\u001a\u0091\u0001\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u000b*\u00020\f\"\b\b\u0001\u0010\u0010*\u00020\f\"\b\b\u0002\u0010\u0014*\u00020\f\"\b\b\u0003\u0010\u0018*\u00020\f*\u00020\u00022\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u001e\u0010\u0006\u001a\u001a\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u0002H\u00180$2\u0006\u0010\u000e\u001a\u0002H\u000b2\u0006\u0010\u0012\u001a\u0002H\u00102\u0006\u0010\u0016\u001a\u0002H\u00142\u0006\u0010\u001a\u001a\u0002H\u00182\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tR\u00020\u0003¢\u0006\u0002\u0010%¨\u0006&"}, d2 = {"reportOn", "", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "factory", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "positioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/AbstractKtSourceElement;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "A", "", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "a", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/AbstractKtSourceElement;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "B", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/AbstractKtSourceElement;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "C", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;", "c", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/AbstractKtSourceElement;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "D", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory4;", "d", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/AbstractKtSourceElement;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory4;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation0;", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/AbstractKtSourceElement;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation0;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation1;", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/AbstractKtSourceElement;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation1;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation2;", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/AbstractKtSourceElement;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation2;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation3;", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/AbstractKtSourceElement;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation3;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation4;", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/AbstractKtSourceElement;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation4;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "frontend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtDiagnosticReportContextHelpersKt {
    public static /* synthetic */ void reportOn$default(DiagnosticContext diagnosticContext, DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactory0 ktDiagnosticFactory0, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy, int i, Object obj) {
        if ((i & 8) != 0) {
            abstractSourceElementPositioningStrategy = null;
        }
        reportOn(diagnosticContext, diagnosticReporter, abstractKtSourceElement, ktDiagnosticFactory0, abstractSourceElementPositioningStrategy);
    }

    public static final void reportOn(DiagnosticContext context_receiver_0, DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactory0 factory, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(factory, "factory");
        KtDiagnosticReportHelpersKt.reportOn(diagnosticReporter, abstractKtSourceElement, factory, context_receiver_0, abstractSourceElementPositioningStrategy);
    }

    public static /* synthetic */ void reportOn$default(DiagnosticContext diagnosticContext, DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactory1 ktDiagnosticFactory1, Object obj, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy, int i, Object obj2) {
        if ((i & 16) != 0) {
            abstractSourceElementPositioningStrategy = null;
        }
        reportOn(diagnosticContext, diagnosticReporter, abstractKtSourceElement, (KtDiagnosticFactory1<Object>) ktDiagnosticFactory1, obj, abstractSourceElementPositioningStrategy);
    }

    public static final <A> void reportOn(DiagnosticContext context_receiver_0, DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactory1<A> factory, A a, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(a, "a");
        KtDiagnosticReportHelpersKt.reportOn(diagnosticReporter, abstractKtSourceElement, factory, a, context_receiver_0, abstractSourceElementPositioningStrategy);
    }

    public static /* synthetic */ void reportOn$default(DiagnosticContext diagnosticContext, DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactory2 ktDiagnosticFactory2, Object obj, Object obj2, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy, int i, Object obj3) {
        if ((i & 32) != 0) {
            abstractSourceElementPositioningStrategy = null;
        }
        reportOn(diagnosticContext, diagnosticReporter, abstractKtSourceElement, (KtDiagnosticFactory2<Object, Object>) ktDiagnosticFactory2, obj, obj2, abstractSourceElementPositioningStrategy);
    }

    public static final <A, B> void reportOn(DiagnosticContext context_receiver_0, DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactory2<A, B> factory, A a, B b, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        KtDiagnosticReportHelpersKt.reportOn(diagnosticReporter, abstractKtSourceElement, factory, a, b, context_receiver_0, abstractSourceElementPositioningStrategy);
    }

    public static final <A, B, C> void reportOn(DiagnosticContext context_receiver_0, DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactory3<A, B, C> factory, A a, B b, C c, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(c, "c");
        KtDiagnosticReportHelpersKt.reportOn(diagnosticReporter, abstractKtSourceElement, factory, a, b, c, context_receiver_0, abstractSourceElementPositioningStrategy);
    }

    public static final <A, B, C, D> void reportOn(DiagnosticContext context_receiver_0, DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactory4<A, B, C, D> factory, A a, B b, C c, D d, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(d, "d");
        KtDiagnosticReportHelpersKt.reportOn(diagnosticReporter, abstractKtSourceElement, factory, a, b, c, d, context_receiver_0, abstractSourceElementPositioningStrategy);
    }

    public static /* synthetic */ void reportOn$default(DiagnosticContext diagnosticContext, DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactoryForDeprecation0 ktDiagnosticFactoryForDeprecation0, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy, int i, Object obj) {
        if ((i & 8) != 0) {
            abstractSourceElementPositioningStrategy = null;
        }
        reportOn(diagnosticContext, diagnosticReporter, abstractKtSourceElement, ktDiagnosticFactoryForDeprecation0, abstractSourceElementPositioningStrategy);
    }

    public static final void reportOn(DiagnosticContext context_receiver_0, DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactoryForDeprecation0 factory, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(factory, "factory");
        KtDiagnosticReportHelpersKt.reportOn(diagnosticReporter, abstractKtSourceElement, factory, context_receiver_0, abstractSourceElementPositioningStrategy);
    }

    public static /* synthetic */ void reportOn$default(DiagnosticContext diagnosticContext, DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactoryForDeprecation1 ktDiagnosticFactoryForDeprecation1, Object obj, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy, int i, Object obj2) {
        if ((i & 16) != 0) {
            abstractSourceElementPositioningStrategy = null;
        }
        reportOn(diagnosticContext, diagnosticReporter, abstractKtSourceElement, (KtDiagnosticFactoryForDeprecation1<Object>) ktDiagnosticFactoryForDeprecation1, obj, abstractSourceElementPositioningStrategy);
    }

    public static final <A> void reportOn(DiagnosticContext context_receiver_0, DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactoryForDeprecation1<A> factory, A a, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(a, "a");
        KtDiagnosticReportHelpersKt.reportOn(diagnosticReporter, abstractKtSourceElement, factory, a, context_receiver_0, abstractSourceElementPositioningStrategy);
    }

    public static /* synthetic */ void reportOn$default(DiagnosticContext diagnosticContext, DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactoryForDeprecation2 ktDiagnosticFactoryForDeprecation2, Object obj, Object obj2, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy, int i, Object obj3) {
        if ((i & 32) != 0) {
            abstractSourceElementPositioningStrategy = null;
        }
        reportOn(diagnosticContext, diagnosticReporter, abstractKtSourceElement, (KtDiagnosticFactoryForDeprecation2<Object, Object>) ktDiagnosticFactoryForDeprecation2, obj, obj2, abstractSourceElementPositioningStrategy);
    }

    public static final <A, B> void reportOn(DiagnosticContext context_receiver_0, DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactoryForDeprecation2<A, B> factory, A a, B b, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        KtDiagnosticReportHelpersKt.reportOn(diagnosticReporter, abstractKtSourceElement, factory, a, b, context_receiver_0, abstractSourceElementPositioningStrategy);
    }

    public static final <A, B, C> void reportOn(DiagnosticContext context_receiver_0, DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactoryForDeprecation3<A, B, C> factory, A a, B b, C c, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(c, "c");
        KtDiagnosticReportHelpersKt.reportOn(diagnosticReporter, abstractKtSourceElement, factory, a, b, c, context_receiver_0, abstractSourceElementPositioningStrategy);
    }

    public static final <A, B, C, D> void reportOn(DiagnosticContext context_receiver_0, DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactoryForDeprecation4<A, B, C, D> factory, A a, B b, C c, D d, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(d, "d");
        KtDiagnosticReportHelpersKt.reportOn(diagnosticReporter, abstractKtSourceElement, factory, a, b, c, d, context_receiver_0, abstractSourceElementPositioningStrategy);
    }
}
