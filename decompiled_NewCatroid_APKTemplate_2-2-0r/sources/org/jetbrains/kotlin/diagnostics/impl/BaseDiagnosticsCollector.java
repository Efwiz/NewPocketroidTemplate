package org.jetbrains.kotlin.diagnostics.impl;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnostic;

/* JADX INFO: compiled from: BaseDiagnosticsCollector.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R&\u0010\b\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R$\u0010\u0011\u001a\u0014\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00130\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/impl/BaseDiagnosticsCollector;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "()V", "diagnostics", "", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "getDiagnostics", "()Ljava/util/List;", "diagnosticsByFilePath", "", "", "getDiagnosticsByFilePath", "()Ljava/util/Map;", "hasErrors", "", "getHasErrors", "()Z", "rawReport", "Lkotlin/Function2;", "", "getRawReport", "()Lkotlin/jvm/functions/Function2;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BaseDiagnosticsCollector extends DiagnosticReporter {
    public abstract List<KtDiagnostic> getDiagnostics();

    public abstract Map<String, List<KtDiagnostic>> getDiagnosticsByFilePath();

    public abstract boolean getHasErrors();

    public abstract Function2<Boolean, String, Unit> getRawReport();
}
