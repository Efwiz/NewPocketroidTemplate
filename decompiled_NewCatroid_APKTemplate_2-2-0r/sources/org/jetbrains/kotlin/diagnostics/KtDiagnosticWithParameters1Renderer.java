package org.jetbrains.kotlin.diagnostics;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRendererKt;
import org.jetbrains.kotlin.diagnostics.rendering.RenderingContext;

/* JADX INFO: compiled from: KtDiagnosticRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u001f\u0010\b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\n0\t2\u0006\u0010\u000b\u001a\u00020\fH\u0016¢\u0006\u0002\u0010\rR\u0016\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters1Renderer;", "A", "Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticWithParametersRenderer;", CrashHianalyticsData.MESSAGE, "", "rendererForA", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;)V", "renderParameters", "", "", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "(Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;)[Ljava/lang/Object;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtDiagnosticWithParameters1Renderer<A> extends AbstractKtDiagnosticWithParametersRenderer {
    private final DiagnosticParameterRenderer<A> rendererForA;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public KtDiagnosticWithParameters1Renderer(String message, DiagnosticParameterRenderer<? super A> diagnosticParameterRenderer) {
        super(message, null);
        Intrinsics.checkNotNullParameter(message, "message");
        this.rendererForA = diagnosticParameterRenderer;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderer
    public Object[] renderParameters(KtDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        if (!(diagnostic instanceof KtDiagnosticWithParameters1)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        KtDiagnosticWithParameters1 ktDiagnosticWithParameters1 = (KtDiagnosticWithParameters1) diagnostic;
        return new Object[]{DiagnosticParameterRendererKt.renderParameter(ktDiagnosticWithParameters1.getA(), this.rendererForA, RenderingContext.INSTANCE.of(ktDiagnosticWithParameters1.getA()))};
    }
}
