package org.jetbrains.kotlin.diagnostics;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRendererKt;
import org.jetbrains.kotlin.diagnostics.rendering.RenderingContext;

/* JADX INFO: compiled from: KtDiagnosticRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0004\b\u0002\u0010\u0003*\u0004\b\u0003\u0010\u00042\u00020\u0005BM\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\t\u0012\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\t\u0012\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00028\u0002\u0018\u00010\t\u0012\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00028\u0003\u0018\u00010\t¢\u0006\u0002\u0010\rJ\u001f\u0010\u000e\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016¢\u0006\u0002\u0010\u0013R\u0016\u0010\b\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n\u0012\u0004\u0012\u00028\u0002\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n\u0012\u0004\u0012\u00028\u0003\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters4Renderer;", "A", "B", "C", "D", "Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticWithParametersRenderer;", CrashHianalyticsData.MESSAGE, "", "rendererForA", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;", "rendererForB", "rendererForC", "rendererForD", "(Ljava/lang/String;Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;)V", "renderParameters", "", "", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "(Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;)[Ljava/lang/Object;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtDiagnosticWithParameters4Renderer<A, B, C, D> extends AbstractKtDiagnosticWithParametersRenderer {
    private final DiagnosticParameterRenderer<A> rendererForA;
    private final DiagnosticParameterRenderer<B> rendererForB;
    private final DiagnosticParameterRenderer<C> rendererForC;
    private final DiagnosticParameterRenderer<D> rendererForD;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public KtDiagnosticWithParameters4Renderer(String message, DiagnosticParameterRenderer<? super A> diagnosticParameterRenderer, DiagnosticParameterRenderer<? super B> diagnosticParameterRenderer2, DiagnosticParameterRenderer<? super C> diagnosticParameterRenderer3, DiagnosticParameterRenderer<? super D> diagnosticParameterRenderer4) {
        super(message, null);
        Intrinsics.checkNotNullParameter(message, "message");
        this.rendererForA = diagnosticParameterRenderer;
        this.rendererForB = diagnosticParameterRenderer2;
        this.rendererForC = diagnosticParameterRenderer3;
        this.rendererForD = diagnosticParameterRenderer4;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderer
    public Object[] renderParameters(KtDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        if (!(diagnostic instanceof KtDiagnosticWithParameters4)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        KtDiagnosticWithParameters4 ktDiagnosticWithParameters4 = (KtDiagnosticWithParameters4) diagnostic;
        RenderingContext renderingContextOf = RenderingContext.INSTANCE.of(ktDiagnosticWithParameters4.getA(), ktDiagnosticWithParameters4.getB(), ktDiagnosticWithParameters4.getC(), ktDiagnosticWithParameters4.getD());
        return new Object[]{DiagnosticParameterRendererKt.renderParameter(ktDiagnosticWithParameters4.getA(), this.rendererForA, renderingContextOf), DiagnosticParameterRendererKt.renderParameter(ktDiagnosticWithParameters4.getB(), this.rendererForB, renderingContextOf), DiagnosticParameterRendererKt.renderParameter(ktDiagnosticWithParameters4.getC(), this.rendererForC, renderingContextOf), DiagnosticParameterRendererKt.renderParameter(ktDiagnosticWithParameters4.getD(), this.rendererForD, renderingContextOf)};
    }
}
