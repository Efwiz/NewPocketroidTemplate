package org.jetbrains.kotlin.diagnostics.rendering;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.text.MessageFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.UnboundDiagnostic;

/* JADX INFO: compiled from: diagnosticsWithParameterRenderers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0000*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u000f\b\u0004\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0015\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000bJ\u001f\u0010\f\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u000e0\r2\u0006\u0010\n\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000fR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/AbstractDiagnosticWithParametersRenderer;", "D", "Lorg/jetbrains/kotlin/diagnostics/UnboundDiagnostic;", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticRenderer;", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;)V", "messageFormat", "Ljava/text/MessageFormat;", "render", "diagnostic", "(Lorg/jetbrains/kotlin/diagnostics/UnboundDiagnostic;)Ljava/lang/String;", "renderParameters", "", "", "(Lorg/jetbrains/kotlin/diagnostics/UnboundDiagnostic;)[Ljava/lang/Object;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractDiagnosticWithParametersRenderer<D extends UnboundDiagnostic> implements DiagnosticRenderer<D> {
    private final MessageFormat messageFormat;

    @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticRenderer
    public Object[] renderParameters(D diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        return new Object[0];
    }

    protected AbstractDiagnosticWithParametersRenderer(String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        this.messageFormat = new MessageFormat(message);
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticRenderer
    public String render(D diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        String str = this.messageFormat.format(renderParameters(diagnostic));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }
}
