package org.jetbrains.kotlin.fir.analysis.diagnostics;

import java.util.Collection;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.rendering.ContextIndependentParameterRenderer;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;

/* JADX INFO: compiled from: FirPlatformIncompatibilityDiagnosticRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 \f2(\u0012$\u0012\"\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u00050\u00020\u0001:\u0001\fB\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ0\u0010\t\u001a\u00020\n2&\u0010\u000b\u001a\"\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u00050\u0002H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/diagnostics/FirPlatformIncompatibilityDiagnosticRenderer;", "Lorg/jetbrains/kotlin/diagnostics/rendering/ContextIndependentParameterRenderer;", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "", "mode", "Lorg/jetbrains/kotlin/fir/analysis/diagnostics/MultiplatformDiagnosticRenderingMode;", "(Lorg/jetbrains/kotlin/fir/analysis/diagnostics/MultiplatformDiagnosticRenderingMode;)V", "render", "", "obj", "Companion", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirPlatformIncompatibilityDiagnosticRenderer implements ContextIndependentParameterRenderer<Map<ExpectActualCompatibility<? extends FirBasedSymbol<?>>, ? extends Collection<? extends FirBasedSymbol<?>>>> {
    public static final FirPlatformIncompatibilityDiagnosticRenderer TEXT = new FirPlatformIncompatibilityDiagnosticRenderer(new MultiplatformDiagnosticRenderingMode());
    private final MultiplatformDiagnosticRenderingMode mode;

    public FirPlatformIncompatibilityDiagnosticRenderer(MultiplatformDiagnosticRenderingMode mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        this.mode = mode;
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.ContextIndependentParameterRenderer
    public /* bridge */ /* synthetic */ String render(Map<ExpectActualCompatibility<? extends FirBasedSymbol<?>>, ? extends Collection<? extends FirBasedSymbol<?>>> map) {
        return render2((Map<ExpectActualCompatibility<FirBasedSymbol<?>>, ? extends Collection<? extends FirBasedSymbol<?>>>) map);
    }

    /* JADX INFO: renamed from: render, reason: avoid collision after fix types in other method */
    public String render2(Map<ExpectActualCompatibility<FirBasedSymbol<?>>, ? extends Collection<? extends FirBasedSymbol<?>>> obj) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        if (obj.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        this.mode.newLine(sb);
        FirPlatformIncompatibilityDiagnosticRendererKt.renderIncompatibilityInformation(sb, obj, "", this.mode);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
