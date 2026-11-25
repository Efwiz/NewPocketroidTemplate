package org.jetbrains.kotlin.diagnostics;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.text.MessageFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KtDiagnosticRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0001\u0004\f\r\u000e\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticWithParametersRenderer;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticRenderer;", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "messageFormat", "Ljava/text/MessageFormat;", "render", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters1Renderer;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters2Renderer;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters3Renderer;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters4Renderer;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractKtDiagnosticWithParametersRenderer implements KtDiagnosticRenderer {
    private final String message;
    private final MessageFormat messageFormat;

    public /* synthetic */ AbstractKtDiagnosticWithParametersRenderer(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private AbstractKtDiagnosticWithParametersRenderer(String str) {
        this.message = str;
        this.messageFormat = new MessageFormat(str);
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderer
    public final String getMessage() {
        return this.message;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderer
    public final String render(KtDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        String str = this.messageFormat.format(renderParameters(diagnostic));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }
}
