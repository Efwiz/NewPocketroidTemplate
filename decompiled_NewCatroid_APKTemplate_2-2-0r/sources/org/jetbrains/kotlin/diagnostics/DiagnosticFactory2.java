package org.jetbrains.kotlin.diagnostics;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: loaded from: classes4.dex */
public class DiagnosticFactory2<E extends PsiElement, A, B> extends DiagnosticFactoryWithPsiElement<E, DiagnosticWithParameters2<E, A, B>> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "a";
        } else if (i != 2) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME;
        }
        objArr[1] = "org/jetbrains/kotlin/diagnostics/DiagnosticFactory2";
        objArr[2] = "on";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public ParametrizedDiagnostic<E> on(E e, A a, B b) {
        if (e == null) {
            $$$reportNull$$$0(0);
        }
        if (a == null) {
            $$$reportNull$$$0(1);
        }
        if (b == null) {
            $$$reportNull$$$0(2);
        }
        return new DiagnosticWithParameters2(e, a, b, this, getSeverity());
    }

    private DiagnosticFactory2(Severity severity, PositioningStrategy<? super E> positioningStrategy) {
        super(severity, positioningStrategy);
    }

    public static <T extends PsiElement, A, B> DiagnosticFactory2<T, A, B> create(Severity severity, PositioningStrategy<? super T> positioningStrategy) {
        return new DiagnosticFactory2<>(severity, positioningStrategy);
    }

    public static <T extends PsiElement, A, B> DiagnosticFactory2<T, A, B> create(Severity severity) {
        return new DiagnosticFactory2<>(severity, PositioningStrategies.DEFAULT);
    }
}
