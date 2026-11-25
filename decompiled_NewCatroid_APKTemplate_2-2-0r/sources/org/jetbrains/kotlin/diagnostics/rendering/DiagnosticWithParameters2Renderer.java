package org.jetbrains.kotlin.diagnostics.rendering;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters2;

/* JADX INFO: compiled from: diagnosticsWithParameterRenderers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00040\u0003B-\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\b\u0012\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\b¢\u0006\u0002\u0010\nJ/\u0010\u000b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\r0\f2\u0016\u0010\u000e\u001a\u0012\u0012\u0002\b\u0003\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0004H\u0016¢\u0006\u0002\u0010\u000fR\u0016\u0010\u0007\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticWithParameters2Renderer;", "A", "B", "Lorg/jetbrains/kotlin/diagnostics/rendering/AbstractDiagnosticWithParametersRenderer;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticWithParameters2;", CrashHianalyticsData.MESSAGE, "", "rendererForA", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;", "rendererForB", "(Ljava/lang/String;Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;)V", "renderParameters", "", "", "diagnostic", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticWithParameters2;)[Ljava/lang/Object;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DiagnosticWithParameters2Renderer<A, B> extends AbstractDiagnosticWithParametersRenderer<DiagnosticWithParameters2<?, A, B>> {
    private final DiagnosticParameterRenderer<A> rendererForA;
    private final DiagnosticParameterRenderer<B> rendererForB;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public DiagnosticWithParameters2Renderer(String message, DiagnosticParameterRenderer<? super A> diagnosticParameterRenderer, DiagnosticParameterRenderer<? super B> diagnosticParameterRenderer2) {
        super(message);
        Intrinsics.checkNotNullParameter(message, "message");
        this.rendererForA = diagnosticParameterRenderer;
        this.rendererForB = diagnosticParameterRenderer2;
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.AbstractDiagnosticWithParametersRenderer, org.jetbrains.kotlin.diagnostics.rendering.DiagnosticRenderer
    public Object[] renderParameters(DiagnosticWithParameters2<?, A, B> diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        RenderingContext renderingContextOf = RenderingContext.INSTANCE.of(diagnostic.getA(), diagnostic.getB());
        return new Object[]{DiagnosticParameterRendererKt.renderParameter(diagnostic.getA(), this.rendererForA, renderingContextOf), DiagnosticParameterRendererKt.renderParameter(diagnostic.getB(), this.rendererForB, renderingContextOf)};
    }
}
