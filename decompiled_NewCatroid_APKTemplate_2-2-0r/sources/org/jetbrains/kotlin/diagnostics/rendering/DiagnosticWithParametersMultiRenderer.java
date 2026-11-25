package org.jetbrains.kotlin.diagnostics.rendering;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters1;

/* JADX INFO: compiled from: diagnosticsWithParameterRenderers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u00012\u0012\u0012\u000e\u0012\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002H\u00010\u00030\u0002B\u001b\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007¢\u0006\u0002\u0010\bJ'\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n2\u0010\u0010\f\u001a\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u00028\u00000\u0003H\u0016¢\u0006\u0002\u0010\rR\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticWithParametersMultiRenderer;", "A", "Lorg/jetbrains/kotlin/diagnostics/rendering/AbstractDiagnosticWithParametersRenderer;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticWithParameters1;", CrashHianalyticsData.MESSAGE, "", "renderer", "Lorg/jetbrains/kotlin/diagnostics/rendering/MultiRenderer;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/diagnostics/rendering/MultiRenderer;)V", "renderParameters", "", "", "diagnostic", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticWithParameters1;)[Ljava/lang/Object;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DiagnosticWithParametersMultiRenderer<A> extends AbstractDiagnosticWithParametersRenderer<DiagnosticWithParameters1<?, A>> {
    private final MultiRenderer<A> renderer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public DiagnosticWithParametersMultiRenderer(String message, MultiRenderer<? super A> renderer) {
        super(message);
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        this.renderer = renderer;
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.AbstractDiagnosticWithParametersRenderer, org.jetbrains.kotlin.diagnostics.rendering.DiagnosticRenderer
    public Object[] renderParameters(DiagnosticWithParameters1<?, A> diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        return this.renderer.render(diagnostic.getA());
    }
}
