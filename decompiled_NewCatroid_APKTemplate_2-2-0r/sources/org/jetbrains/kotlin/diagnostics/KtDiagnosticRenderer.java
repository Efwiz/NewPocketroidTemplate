package org.jetbrains.kotlin.diagnostics;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;

/* JADX INFO: compiled from: KtDiagnosticRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH&J\u001f\u0010\u000b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\f2\u0006\u0010\t\u001a\u00020\nH&¢\u0006\u0002\u0010\rR\u001a\u0010\u0002\u001a\u00020\u00038&X§\u0004¢\u0006\f\u0012\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u0082\u0001\u0002\u000e\u000fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticRenderer;", "", CrashHianalyticsData.MESSAGE, "", "getMessage$annotations", "()V", "getMessage", "()Ljava/lang/String;", "render", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "renderParameters", "", "(Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;)[Ljava/lang/Object;", "Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticWithParametersRenderer;", "Lorg/jetbrains/kotlin/diagnostics/SimpleKtDiagnosticRenderer;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtDiagnosticRenderer {
    static /* synthetic */ void getMessage$annotations() {
    }

    String getMessage();

    String render(KtDiagnostic diagnostic);

    Object[] renderParameters(KtDiagnostic diagnostic);
}
