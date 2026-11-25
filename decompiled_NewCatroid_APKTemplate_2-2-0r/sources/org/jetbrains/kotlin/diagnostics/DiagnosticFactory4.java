package org.jetbrains.kotlin.diagnostics;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: loaded from: classes4.dex */
public class DiagnosticFactory4<E extends PsiElement, A, B, C, D> extends DiagnosticFactoryWithPsiElement<E, DiagnosticWithParameters4<E, A, B, C, D>> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "a";
        } else if (i == 2) {
            objArr[0] = JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME;
        } else if (i == 3) {
            objArr[0] = "c";
        } else if (i != 4) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "d";
        }
        objArr[1] = "org/jetbrains/kotlin/diagnostics/DiagnosticFactory4";
        objArr[2] = "on";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected DiagnosticFactory4(Severity severity, PositioningStrategy<? super E> positioningStrategy) {
        super(severity, positioningStrategy);
    }

    public static <T extends PsiElement, A, B, C, D> DiagnosticFactory4<T, A, B, C, D> create(Severity severity) {
        return create(severity, PositioningStrategies.DEFAULT);
    }

    public static <T extends PsiElement, A, B, C, D> DiagnosticFactory4<T, A, B, C, D> create(Severity severity, PositioningStrategy<? super T> positioningStrategy) {
        return new DiagnosticFactory4<>(severity, positioningStrategy);
    }

    public ParametrizedDiagnostic<E> on(E e, A a, B b, C c, D d) {
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
        if (d == null) {
            $$$reportNull$$$0(4);
        }
        return new DiagnosticWithParameters4(e, a, b, c, d, this, getSeverity());
    }
}
