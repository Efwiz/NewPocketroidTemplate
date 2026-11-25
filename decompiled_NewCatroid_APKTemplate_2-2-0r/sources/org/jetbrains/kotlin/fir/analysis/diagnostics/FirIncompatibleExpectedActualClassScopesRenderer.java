package org.jetbrains.kotlin.fir.analysis.diagnostics;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.rendering.ContextIndependentParameterRenderer;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;

/* JADX INFO: compiled from: FirPlatformIncompatibilityDiagnosticRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 \u000e2>\u0012:\u00128\u00124\u00122\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012$\u0012\"\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0006\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u00070\u00050\u00030\u00020\u0001:\u0001\u000eB\r\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJF\u0010\u000b\u001a\u00020\f2<\u0010\r\u001a8\u00124\u00122\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012$\u0012\"\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0006\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u00070\u00050\u00030\u0002H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/diagnostics/FirIncompatibleExpectedActualClassScopesRenderer;", "Lorg/jetbrains/kotlin/diagnostics/rendering/ContextIndependentParameterRenderer;", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible;", "", "mode", "Lorg/jetbrains/kotlin/fir/analysis/diagnostics/MultiplatformDiagnosticRenderingMode;", "(Lorg/jetbrains/kotlin/fir/analysis/diagnostics/MultiplatformDiagnosticRenderingMode;)V", "render", "", "obj", "Companion", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirIncompatibleExpectedActualClassScopesRenderer implements ContextIndependentParameterRenderer<List<? extends Pair<? extends FirBasedSymbol<?>, ? extends Map<ExpectActualCompatibility.Incompatible<? extends FirBasedSymbol<?>>, ? extends Collection<? extends FirBasedSymbol<?>>>>>> {
    public static final FirIncompatibleExpectedActualClassScopesRenderer TEXT = new FirIncompatibleExpectedActualClassScopesRenderer(new MultiplatformDiagnosticRenderingMode());
    private final MultiplatformDiagnosticRenderingMode mode;

    public FirIncompatibleExpectedActualClassScopesRenderer(MultiplatformDiagnosticRenderingMode mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        this.mode = mode;
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.ContextIndependentParameterRenderer
    public /* bridge */ /* synthetic */ String render(List<? extends Pair<? extends FirBasedSymbol<?>, ? extends Map<ExpectActualCompatibility.Incompatible<? extends FirBasedSymbol<?>>, ? extends Collection<? extends FirBasedSymbol<?>>>>> list) {
        return render2((List<? extends Pair<? extends FirBasedSymbol<?>, ? extends Map<ExpectActualCompatibility.Incompatible<FirBasedSymbol<?>>, ? extends Collection<? extends FirBasedSymbol<?>>>>>) list);
    }

    /* JADX INFO: renamed from: render, reason: avoid collision after fix types in other method */
    public String render2(List<? extends Pair<? extends FirBasedSymbol<?>, ? extends Map<ExpectActualCompatibility.Incompatible<FirBasedSymbol<?>>, ? extends Collection<? extends FirBasedSymbol<?>>>>> obj) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        if (obj.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        this.mode.newLine(sb);
        FirPlatformIncompatibilityDiagnosticRendererKt.renderIncompatibleClassScopes(sb, obj, "", this.mode);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
