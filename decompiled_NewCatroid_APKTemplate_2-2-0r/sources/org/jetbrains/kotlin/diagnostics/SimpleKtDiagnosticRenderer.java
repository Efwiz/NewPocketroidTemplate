package org.jetbrains.kotlin.diagnostics;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KtDiagnosticRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u0016J\u001f\u0010\n\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\f0\u000b2\u0006\u0010\b\u001a\u00020\tH\u0016¢\u0006\u0002\u0010\rR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/SimpleKtDiagnosticRenderer;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticRenderer;", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "render", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "renderParameters", "", "", "(Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;)[Ljava/lang/Object;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleKtDiagnosticRenderer implements KtDiagnosticRenderer {
    private final String message;

    public SimpleKtDiagnosticRenderer(String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        this.message = message;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderer
    public String getMessage() {
        return this.message;
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderer
    public String render(KtDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        if (!(diagnostic instanceof KtSimpleDiagnostic)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return getMessage();
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderer
    public Object[] renderParameters(KtDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        if (!(diagnostic instanceof KtSimpleDiagnostic)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return new Object[0];
    }
}
