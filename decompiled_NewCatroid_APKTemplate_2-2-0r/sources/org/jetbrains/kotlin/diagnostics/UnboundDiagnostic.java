package org.jetbrains.kotlin.diagnostics;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;

/* JADX INFO: compiled from: UnboundDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0016\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0018\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0012À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/UnboundDiagnostic;", "", "factory", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory;", "getFactory", "()Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory;", "isValid", "", "()Z", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "getSeverity", "()Lorg/jetbrains/kotlin/diagnostics/Severity;", "textRanges", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getTextRanges", "()Ljava/util/List;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface UnboundDiagnostic {
    DiagnosticFactory<?> getFactory();

    Severity getSeverity();

    List<TextRange> getTextRanges();

    boolean isValid();
}
