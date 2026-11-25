package org.jetbrains.kotlin.fir.analysis.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.rendering.ContextIndependentParameterRenderer;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRendererKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.renderer.ConeIdShortRenderer;
import org.jetbrains.kotlin.fir.renderer.ConeTypeRenderer;
import org.jetbrains.kotlin.fir.renderer.FirRenderer;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualAnnotationsIncompatibilityType;

/* JADX INFO: compiled from: FirExpectActualAnnotationIncompatibilityDiagnosticRenderers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0006H\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR!\u0010\t\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u000b\u0010\u0002\u001a\u0004\b\f\u0010\b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/diagnostics/FirExpectActualAnnotationIncompatibilityDiagnosticRenderers;", "", "()V", "INCOMPATIBILITY", "Lorg/jetbrains/kotlin/diagnostics/rendering/ContextIndependentParameterRenderer;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualAnnotationsIncompatibilityType;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getINCOMPATIBILITY", "()Lorg/jetbrains/kotlin/diagnostics/rendering/ContextIndependentParameterRenderer;", "SYMBOL_RENDERER", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getSYMBOL_RENDERER$annotations", "getSYMBOL_RENDERER", "renderAnnotation", "", "ann", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirExpectActualAnnotationIncompatibilityDiagnosticRenderers {
    public static final FirExpectActualAnnotationIncompatibilityDiagnosticRenderers INSTANCE = new FirExpectActualAnnotationIncompatibilityDiagnosticRenderers();
    private static final ContextIndependentParameterRenderer<FirBasedSymbol<?>> SYMBOL_RENDERER = DiagnosticParameterRendererKt.Renderer(new Function1<FirBasedSymbol<?>, String>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirExpectActualAnnotationIncompatibilityDiagnosticRenderers$SYMBOL_RENDERER$1
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(FirBasedSymbol<?> it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return new FirRenderer(null, null, null, null, null, null, null, new ConeIdShortRenderer(), null, null, null, null, new ConeTypeRenderer(), null, null, null, null, 126537, null).renderElementAsString(it.getFir(), true);
        }
    });
    private static final ContextIndependentParameterRenderer<ExpectActualAnnotationsIncompatibilityType<? extends FirAnnotation>> INCOMPATIBILITY = DiagnosticParameterRendererKt.Renderer(new Function1<ExpectActualAnnotationsIncompatibilityType<? extends FirAnnotation>, String>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirExpectActualAnnotationIncompatibilityDiagnosticRenderers$INCOMPATIBILITY$1
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(ExpectActualAnnotationsIncompatibilityType<? extends FirAnnotation> incompatibilityType) {
            Intrinsics.checkNotNullParameter(incompatibilityType, "incompatibilityType");
            StringBuilder sbAppend = new StringBuilder("Annotation `").append(FirExpectActualAnnotationIncompatibilityDiagnosticRenderers.INSTANCE.renderAnnotation(incompatibilityType.getExpectAnnotation())).append("` ");
            if (incompatibilityType instanceof ExpectActualAnnotationsIncompatibilityType.MissingOnActual) {
                sbAppend.append("is missing on actual declaration");
            } else if (incompatibilityType instanceof ExpectActualAnnotationsIncompatibilityType.DifferentOnActual) {
                sbAppend.append("has different arguments on actual declaration: `").append(FirExpectActualAnnotationIncompatibilityDiagnosticRenderers.INSTANCE.renderAnnotation((FirAnnotation) ((ExpectActualAnnotationsIncompatibilityType.DifferentOnActual) incompatibilityType).getActualAnnotation())).append("`");
            }
            String string = sbAppend.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }
    });

    public static /* synthetic */ void getSYMBOL_RENDERER$annotations() {
    }

    private FirExpectActualAnnotationIncompatibilityDiagnosticRenderers() {
    }

    public final ContextIndependentParameterRenderer<FirBasedSymbol<?>> getSYMBOL_RENDERER() {
        return SYMBOL_RENDERER;
    }

    public final ContextIndependentParameterRenderer<ExpectActualAnnotationsIncompatibilityType<? extends FirAnnotation>> getINCOMPATIBILITY() {
        return INCOMPATIBILITY;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String renderAnnotation(FirAnnotation ann) {
        return new FirRenderer(null, null, null, null, null, null, null, new ConeIdShortRenderer(), null, null, null, null, new ConeTypeRenderer(), null, null, null, null, 126847, null).renderElementAsString(ann, true);
    }
}
