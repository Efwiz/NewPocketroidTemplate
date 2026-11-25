package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticRenderer;

/* JADX INFO: compiled from: RenderedDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u001b\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\u0015\u001a\u00020\u0012H\u0016R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0015\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/RenderedDiagnostic;", "D", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "", "diagnostic", "renderer", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticRenderer;", "(Lorg/jetbrains/kotlin/diagnostics/Diagnostic;Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticRenderer;)V", "getDiagnostic", "()Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "factory", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory;", "getFactory", "()Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory;", "getRenderer", "()Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticRenderer;", "text", "", "getText", "()Ljava/lang/String;", "toString", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RenderedDiagnostic<D extends Diagnostic> {
    private final D diagnostic;
    private final DiagnosticRenderer<D> renderer;
    private final String text;

    /* JADX WARN: Multi-variable type inference failed */
    public RenderedDiagnostic(D diagnostic, DiagnosticRenderer<? super D> renderer) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        this.diagnostic = diagnostic;
        this.renderer = renderer;
        this.text = renderer.render(diagnostic);
    }

    public final D getDiagnostic() {
        return this.diagnostic;
    }

    public final DiagnosticRenderer<D> getRenderer() {
        return this.renderer;
    }

    public final String getText() {
        return this.text;
    }

    public final DiagnosticFactory<?> getFactory() {
        return this.diagnostic.getFactory();
    }

    public String toString() {
        return this.text;
    }
}
