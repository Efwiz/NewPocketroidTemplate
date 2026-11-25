package org.jetbrains.kotlin.resolve.calls;

import androidx.exifinterface.media.ExifInterface;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.diagnostics.ParametrizedDiagnostic;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: DiagnosticReporterImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JQ\u0010\u000b\u001a\u00020\f\"\b\b\u0000\u0010\r*\u00020\b\"\b\b\u0001\u0010\u000e*\u00020\u00062\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u00052%\u0010\u0010\u001a!\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u00020\b\u0012\f\u0012\n\u0012\u0004\u0012\u0002H\r\u0018\u00010\t0\u0007¢\u0006\u0002\b\nH\u0002J;\u0010\u0011\u001a\u00020\f\"\n\b\u0000\u0010\r\u0018\u0001*\u00020\b\"\b\b\u0001\u0010\u000e*\u00020\u00062\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\r0\u00132\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0005H\u0082\bJf\u0010\u0011\u001a\u00020\f\"\n\b\u0000\u0010\r\u0018\u0001*\u00020\b\"\b\b\u0001\u0010\u0014*\u00020\u0001\"\b\b\u0002\u0010\u000e*\u00020\u00062\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u00140\u00162\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u00052\u0019\b\u0004\u0010\u0017\u001a\u0013\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H\u00140\u0018¢\u0006\u0002\b\nH\u0082\bJ\u0091\u0001\u0010\u0011\u001a\u00020\f\"\n\b\u0000\u0010\r\u0018\u0001*\u00020\b\"\b\b\u0001\u0010\u0014*\u00020\u0001\"\b\b\u0002\u0010\u0019*\u00020\u0001\"\b\b\u0003\u0010\u000e*\u00020\u00062\u0018\u0010\u001a\u001a\u0014\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u0002H\u00190\u001b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u00052\u0019\b\u0004\u0010\u0017\u001a\u0013\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H\u00140\u0018¢\u0006\u0002\b\n2\u0019\b\u0004\u0010\u001c\u001a\u0013\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H\u00190\u0018¢\u0006\u0002\b\nH\u0082\bJ-\u0010\u001d\u001a\n\u0012\u0004\u0012\u0002H\r\u0018\u00010\t\"\b\b\u0000\u0010\r*\u00020\b2\u0006\u0010\u001e\u001a\u0002H\r2\u0006\u0010\u001f\u001a\u00020\u0006¢\u0006\u0002\u0010 R=\u0010\u0003\u001a1\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\u0012\u001f\u0012\u001d\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\b\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\u0007¢\u0006\u0002\b\n0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/CallDiagnosticToDiagnostic;", "", "()V", "diagnosticMap", "", "Ljava/lang/Class;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/diagnostics/ParametrizedDiagnostic;", "Lkotlin/ExtensionFunctionType;", "checkPut", "", ExifInterface.LONGITUDE_EAST, "C", "klass", "factory", "put", "factory0", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory0;", "A", "factory1", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory1;", "getA", "Lkotlin/Function1;", "B", "factory2", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory2;", "getB", "toDiagnostic", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "diagnostic", "(Lcom/intellij/psi/PsiElement;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;)Lorg/jetbrains/kotlin/diagnostics/ParametrizedDiagnostic;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallDiagnosticToDiagnostic {
    public static final CallDiagnosticToDiagnostic INSTANCE;
    private static final Map<Class<? extends KotlinCallDiagnostic>, Function2<KotlinCallDiagnostic, PsiElement, ParametrizedDiagnostic<?>>> diagnosticMap;

    private CallDiagnosticToDiagnostic() {
    }

    static {
        CallDiagnosticToDiagnostic callDiagnosticToDiagnostic = new CallDiagnosticToDiagnostic();
        INSTANCE = callDiagnosticToDiagnostic;
        diagnosticMap = new HashMap();
        final DiagnosticFactory2<KtExpression, KotlinType, KotlinType> TYPE_MISMATCH = Errors.TYPE_MISMATCH;
        Intrinsics.checkNotNullExpressionValue(TYPE_MISMATCH, "TYPE_MISMATCH");
        callDiagnosticToDiagnostic.checkPut(TypeMismatchDiagnostic.class, new Function2<TypeMismatchDiagnostic, PsiElement, ParametrizedDiagnostic<KtExpression>>() { // from class: org.jetbrains.kotlin.resolve.calls.CallDiagnosticToDiagnostic$special$$inlined$put$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final ParametrizedDiagnostic<KtExpression> invoke(TypeMismatchDiagnostic checkPut, PsiElement it) {
                Intrinsics.checkNotNullParameter(checkPut, "$this$checkPut");
                Intrinsics.checkNotNullParameter(it, "it");
                if (!(it instanceof KtExpression)) {
                    it = null;
                }
                KtExpression ktExpression = (KtExpression) it;
                if (ktExpression == null) {
                    return null;
                }
                TypeMismatchDiagnostic typeMismatchDiagnostic = checkPut;
                return TYPE_MISMATCH.on(ktExpression, typeMismatchDiagnostic.getExpectedType(), typeMismatchDiagnostic.getActualType());
            }
        });
    }

    private final <E extends PsiElement, C extends KotlinCallDiagnostic> void checkPut(Class<C> klass, Function2<? super C, ? super PsiElement, ? extends ParametrizedDiagnostic<E>> factory) {
        Map<Class<? extends KotlinCallDiagnostic>, Function2<KotlinCallDiagnostic, PsiElement, ParametrizedDiagnostic<?>>> map = diagnosticMap;
        Intrinsics.checkNotNull(factory, "null cannot be cast to non-null type @[ExtensionFunctionType] kotlin.Function2<org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic, com.intellij.psi.PsiElement, org.jetbrains.kotlin.diagnostics.ParametrizedDiagnostic<*>>");
        map.put(klass, (Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity(factory, 2));
    }

    public final <E extends PsiElement> ParametrizedDiagnostic<E> toDiagnostic(E element, KotlinCallDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        Class<?> cls = diagnostic.getClass();
        Function2<KotlinCallDiagnostic, PsiElement, ParametrizedDiagnostic<?>> function2 = diagnosticMap.get(cls);
        if (function2 == null) {
            throw new IllegalStateException(("Illegal call diagnostic class: " + cls.getCanonicalName()).toString());
        }
        return (ParametrizedDiagnostic) function2.invoke(diagnostic, element);
    }
}
