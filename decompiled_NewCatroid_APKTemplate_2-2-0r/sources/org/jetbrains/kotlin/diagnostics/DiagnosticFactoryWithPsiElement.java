package org.jetbrains.kotlin.diagnostics;

import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.Diagnostic;

/* JADX INFO: loaded from: classes4.dex */
public abstract class DiagnosticFactoryWithPsiElement<E extends PsiElement, D extends Diagnostic> extends DiagnosticFactory<D> {
    protected final PositioningStrategy<? super E> positioningStrategy;

    public DiagnosticFactoryWithPsiElement(Severity severity, PositioningStrategy<? super E> positioningStrategy) {
        super(severity);
        this.positioningStrategy = positioningStrategy;
    }

    protected List<TextRange> getTextRanges(ParametrizedDiagnostic<E> parametrizedDiagnostic) {
        return this.positioningStrategy.markDiagnostic(parametrizedDiagnostic);
    }

    protected boolean isValid(ParametrizedDiagnostic<E> parametrizedDiagnostic) {
        return this.positioningStrategy.isValid(parametrizedDiagnostic.getPsiElement());
    }

    public PositioningStrategy<? super E> getPositioningStrategy() {
        return this.positioningStrategy;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Use `cast` from the superclass.")
    @Deprecated
    public D cast(Diagnostic diagnostic) {
        return (D) super.cast((UnboundDiagnostic) diagnostic);
    }
}
