package org.jetbrains.kotlin.diagnostics;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: KtDiagnosticReportHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a'\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\b\u0012\u0004\u0012\u0002H\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006\u001a0\u0010\u0007\u001a\u00020\b*\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u001aM\u0010\u0007\u001a\u00020\b\"\b\b\u0000\u0010\u0010*\u00020\u0011*\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00100\u00122\u0006\u0010\u0013\u001a\u0002H\u00102\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010\u0014\u001ae\u0010\u0007\u001a\u00020\b\"\b\b\u0000\u0010\u0010*\u00020\u0011\"\b\b\u0001\u0010\u0015*\u00020\u0011*\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00150\u00162\u0006\u0010\u0013\u001a\u0002H\u00102\u0006\u0010\u0017\u001a\u0002H\u00152\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010\u0018\u001a}\u0010\u0007\u001a\u00020\b\"\b\b\u0000\u0010\u0010*\u00020\u0011\"\b\b\u0001\u0010\u0015*\u00020\u0011\"\b\b\u0002\u0010\u0019*\u00020\u0011*\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0018\u0010\f\u001a\u0014\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u00190\u001a2\u0006\u0010\u0013\u001a\u0002H\u00102\u0006\u0010\u0017\u001a\u0002H\u00152\u0006\u0010\u001b\u001a\u0002H\u00192\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010\u001c\u001a\u0095\u0001\u0010\u0007\u001a\u00020\b\"\b\b\u0000\u0010\u0010*\u00020\u0011\"\b\b\u0001\u0010\u0015*\u00020\u0011\"\b\b\u0002\u0010\u0019*\u00020\u0011\"\b\b\u0003\u0010\u001d*\u00020\u0011*\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u001e\u0010\f\u001a\u001a\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u0019\u0012\u0004\u0012\u0002H\u001d0\u001e2\u0006\u0010\u0013\u001a\u0002H\u00102\u0006\u0010\u0017\u001a\u0002H\u00152\u0006\u0010\u001b\u001a\u0002H\u00192\u0006\u0010\u001f\u001a\u0002H\u001d2\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010 \u001a0\u0010\u0007\u001a\u00020\b*\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020!2\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u001aM\u0010\u0007\u001a\u00020\b\"\b\b\u0000\u0010\u0010*\u00020\u0011*\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00100\"2\u0006\u0010\u0013\u001a\u0002H\u00102\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010#\u001ae\u0010\u0007\u001a\u00020\b\"\b\b\u0000\u0010\u0010*\u00020\u0011\"\b\b\u0001\u0010\u0015*\u00020\u0011*\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00150$2\u0006\u0010\u0013\u001a\u0002H\u00102\u0006\u0010\u0017\u001a\u0002H\u00152\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010%\u001a}\u0010\u0007\u001a\u00020\b\"\b\b\u0000\u0010\u0010*\u00020\u0011\"\b\b\u0001\u0010\u0015*\u00020\u0011\"\b\b\u0002\u0010\u0019*\u00020\u0011*\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0018\u0010\f\u001a\u0014\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u00190&2\u0006\u0010\u0013\u001a\u0002H\u00102\u0006\u0010\u0017\u001a\u0002H\u00152\u0006\u0010\u001b\u001a\u0002H\u00192\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010'\u001a\u0095\u0001\u0010\u0007\u001a\u00020\b\"\b\b\u0000\u0010\u0010*\u00020\u0011\"\b\b\u0001\u0010\u0015*\u00020\u0011\"\b\b\u0002\u0010\u0019*\u00020\u0011\"\b\b\u0003\u0010\u001d*\u00020\u0011*\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u001e\u0010\f\u001a\u001a\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u0019\u0012\u0004\u0012\u0002H\u001d0(2\u0006\u0010\u0013\u001a\u0002H\u00102\u0006\u0010\u0017\u001a\u0002H\u00152\u0006\u0010\u001b\u001a\u0002H\u00192\u0006\u0010\u001f\u001a\u0002H\u001d2\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010)\u001a\f\u0010*\u001a\u00020\u000b*\u0004\u0018\u00010\u000b¨\u0006+"}, d2 = {"chooseFactory", "F", "Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticFactory;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation;", "context", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;", "(Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation;Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;)Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticFactory;", "reportOn", "", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "factory", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "positioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "A", "", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "a", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/AbstractKtSourceElement;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "B", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/AbstractKtSourceElement;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "C", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;", "c", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/AbstractKtSourceElement;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "D", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory4;", "d", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/AbstractKtSourceElement;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory4;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation0;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation1;", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/AbstractKtSourceElement;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation1;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation2;", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/AbstractKtSourceElement;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation2;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation3;", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/AbstractKtSourceElement;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation3;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation4;", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/AbstractKtSourceElement;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation4;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;)V", "requireNotNull", "frontend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtDiagnosticReportHelpersKt {
    public static /* synthetic */ void reportOn$default(DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactory0 ktDiagnosticFactory0, DiagnosticContext diagnosticContext, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy, int i, Object obj) {
        if ((i & 8) != 0) {
            abstractSourceElementPositioningStrategy = null;
        }
        reportOn(diagnosticReporter, abstractKtSourceElement, ktDiagnosticFactory0, diagnosticContext, abstractSourceElementPositioningStrategy);
    }

    public static final void reportOn(DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactory0 factory, DiagnosticContext context, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(context, "context");
        diagnosticReporter.report(factory.on(requireNotNull(abstractKtSourceElement), abstractSourceElementPositioningStrategy), context);
    }

    public static /* synthetic */ void reportOn$default(DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactory1 ktDiagnosticFactory1, Object obj, DiagnosticContext diagnosticContext, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy, int i, Object obj2) {
        if ((i & 16) != 0) {
            abstractSourceElementPositioningStrategy = null;
        }
        reportOn(diagnosticReporter, abstractKtSourceElement, (KtDiagnosticFactory1<Object>) ktDiagnosticFactory1, obj, diagnosticContext, abstractSourceElementPositioningStrategy);
    }

    public static final <A> void reportOn(DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactory1<A> factory, A a, DiagnosticContext context, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(context, "context");
        diagnosticReporter.report(factory.on(requireNotNull(abstractKtSourceElement), a, abstractSourceElementPositioningStrategy), context);
    }

    public static /* synthetic */ void reportOn$default(DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactory2 ktDiagnosticFactory2, Object obj, Object obj2, DiagnosticContext diagnosticContext, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy, int i, Object obj3) {
        if ((i & 32) != 0) {
            abstractSourceElementPositioningStrategy = null;
        }
        reportOn(diagnosticReporter, abstractKtSourceElement, (KtDiagnosticFactory2<Object, Object>) ktDiagnosticFactory2, obj, obj2, diagnosticContext, abstractSourceElementPositioningStrategy);
    }

    public static final <A, B> void reportOn(DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactory2<A, B> factory, A a, B b, DiagnosticContext context, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(context, "context");
        diagnosticReporter.report(factory.on(requireNotNull(abstractKtSourceElement), a, b, abstractSourceElementPositioningStrategy), context);
    }

    public static final <A, B, C> void reportOn(DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactory3<A, B, C> factory, A a, B b, C c, DiagnosticContext context, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(context, "context");
        diagnosticReporter.report(factory.on(requireNotNull(abstractKtSourceElement), a, b, c, abstractSourceElementPositioningStrategy), context);
    }

    public static final <A, B, C, D> void reportOn(DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactory4<A, B, C, D> factory, A a, B b, C c, D d, DiagnosticContext context, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(d, "d");
        Intrinsics.checkNotNullParameter(context, "context");
        diagnosticReporter.report(factory.on(requireNotNull(abstractKtSourceElement), a, b, c, d, abstractSourceElementPositioningStrategy), context);
    }

    public static final AbstractKtSourceElement requireNotNull(AbstractKtSourceElement abstractKtSourceElement) {
        if (abstractKtSourceElement != null) {
            return abstractKtSourceElement;
        }
        throw new IllegalArgumentException("source must not be null".toString());
    }

    public static /* synthetic */ void reportOn$default(DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactoryForDeprecation0 ktDiagnosticFactoryForDeprecation0, DiagnosticContext diagnosticContext, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy, int i, Object obj) {
        if ((i & 8) != 0) {
            abstractSourceElementPositioningStrategy = null;
        }
        reportOn(diagnosticReporter, abstractKtSourceElement, ktDiagnosticFactoryForDeprecation0, diagnosticContext, abstractSourceElementPositioningStrategy);
    }

    public static final void reportOn(DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactoryForDeprecation0 factory, DiagnosticContext context, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(context, "context");
        reportOn(diagnosticReporter, abstractKtSourceElement, (KtDiagnosticFactory0) chooseFactory(factory, context), context, abstractSourceElementPositioningStrategy);
    }

    public static /* synthetic */ void reportOn$default(DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactoryForDeprecation1 ktDiagnosticFactoryForDeprecation1, Object obj, DiagnosticContext diagnosticContext, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy, int i, Object obj2) {
        if ((i & 16) != 0) {
            abstractSourceElementPositioningStrategy = null;
        }
        reportOn(diagnosticReporter, abstractKtSourceElement, (KtDiagnosticFactoryForDeprecation1<Object>) ktDiagnosticFactoryForDeprecation1, obj, diagnosticContext, abstractSourceElementPositioningStrategy);
    }

    public static final <A> void reportOn(DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactoryForDeprecation1<A> factory, A a, DiagnosticContext context, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(context, "context");
        reportOn(diagnosticReporter, abstractKtSourceElement, (KtDiagnosticFactory1) chooseFactory(factory, context), a, context, abstractSourceElementPositioningStrategy);
    }

    public static /* synthetic */ void reportOn$default(DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactoryForDeprecation2 ktDiagnosticFactoryForDeprecation2, Object obj, Object obj2, DiagnosticContext diagnosticContext, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy, int i, Object obj3) {
        if ((i & 32) != 0) {
            abstractSourceElementPositioningStrategy = null;
        }
        reportOn(diagnosticReporter, abstractKtSourceElement, (KtDiagnosticFactoryForDeprecation2<Object, Object>) ktDiagnosticFactoryForDeprecation2, obj, obj2, diagnosticContext, abstractSourceElementPositioningStrategy);
    }

    public static final <A, B> void reportOn(DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactoryForDeprecation2<A, B> factory, A a, B b, DiagnosticContext context, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(context, "context");
        reportOn(diagnosticReporter, abstractKtSourceElement, (KtDiagnosticFactory2) chooseFactory(factory, context), a, b, context, abstractSourceElementPositioningStrategy);
    }

    public static final <A, B, C> void reportOn(DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactoryForDeprecation3<A, B, C> factory, A a, B b, C c, DiagnosticContext context, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(context, "context");
        reportOn(diagnosticReporter, abstractKtSourceElement, (KtDiagnosticFactory3) chooseFactory(factory, context), a, b, c, context, abstractSourceElementPositioningStrategy);
    }

    public static final <A, B, C, D> void reportOn(DiagnosticReporter diagnosticReporter, AbstractKtSourceElement abstractKtSourceElement, KtDiagnosticFactoryForDeprecation4<A, B, C, D> factory, A a, B b, C c, D d, DiagnosticContext context, AbstractSourceElementPositioningStrategy abstractSourceElementPositioningStrategy) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(d, "d");
        Intrinsics.checkNotNullParameter(context, "context");
        reportOn(diagnosticReporter, abstractKtSourceElement, (KtDiagnosticFactory4) chooseFactory(factory, context), a, b, c, d, context, abstractSourceElementPositioningStrategy);
    }

    public static final <F extends AbstractKtDiagnosticFactory> F chooseFactory(KtDiagnosticFactoryForDeprecation<F> ktDiagnosticFactoryForDeprecation, DiagnosticContext context) {
        Intrinsics.checkNotNullParameter(ktDiagnosticFactoryForDeprecation, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.getLanguageVersionSettings().supportsFeature(ktDiagnosticFactoryForDeprecation.getDeprecatingFeature())) {
            return (F) ktDiagnosticFactoryForDeprecation.getErrorFactory();
        }
        return (F) ktDiagnosticFactoryForDeprecation.getWarningFactory();
    }
}
