package org.jetbrains.kotlin.diagnostics;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: loaded from: classes4.dex */
public class DiagnosticFactory3<E extends PsiElement, A, B, C> extends DiagnosticFactoryWithPsiElement<E, DiagnosticWithParameters3<E, A, B, C>> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "a";
        } else if (i == 2) {
            objArr[0] = JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME;
        } else if (i != 3) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "c";
        }
        objArr[1] = "org/jetbrains/kotlin/diagnostics/DiagnosticFactory3";
        objArr[2] = "on";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected DiagnosticFactory3(Severity severity, PositioningStrategy<? super E> positioningStrategy) {
        super(severity, positioningStrategy);
    }

    public static <T extends PsiElement, A, B, C> DiagnosticFactory3<T, A, B, C> create(Severity severity) {
        return create(severity, PositioningStrategies.DEFAULT);
    }

    public static <T extends PsiElement, A, B, C> DiagnosticFactory3<T, A, B, C> create(Severity severity, PositioningStrategy<? super T> positioningStrategy) {
        return new DiagnosticFactory3<>(severity, positioningStrategy);
    }

    public ParametrizedDiagnostic<E> on(E e, A a, B b, C c) {
        if (e == null) {
            $$$reportNull$$$0(0);
        }
        if (a == null) {
            $$$reportNull$$$0(1);
        }
        if (b == null) {
            $$$reportNull$$$0(2);
        }
        if (c == null) {
            $$$reportNull$$$0(3);
        }
        return new DiagnosticWithParameters3(e, a, b, c, this, getSeverity());
    }
}
