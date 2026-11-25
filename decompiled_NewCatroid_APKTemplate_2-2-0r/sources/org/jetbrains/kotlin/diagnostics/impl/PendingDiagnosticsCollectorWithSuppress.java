package org.jetbrains.kotlin.diagnostics.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.KtDiagnostic;
import org.jetbrains.kotlin.diagnostics.Severity;

/* JADX INFO: compiled from: PendingDiagnosticsCollectorWithSuppress.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001f\u0012\u0018\u0010\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\u0002\u0010\u0007J\u001a\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\u001a\u0010 \u001a\u00020\u00062\b\u0010!\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016R\"\u0010\b\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR(\u0010\u0010\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\r0\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001e\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0004@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\"\u0010\u0018\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/impl/PendingDiagnosticsCollectorWithSuppress;", "Lorg/jetbrains/kotlin/diagnostics/impl/BaseDiagnosticsCollector;", "rawReport", "Lkotlin/Function2;", "", "", "", "(Lkotlin/jvm/functions/Function2;)V", "_diagnosticsByFilePath", "", "", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "diagnostics", "", "getDiagnostics", "()Ljava/util/List;", "diagnosticsByFilePath", "", "getDiagnosticsByFilePath", "()Ljava/util/Map;", "<set-?>", "hasErrors", "getHasErrors", "()Z", "pendingDiagnosticsByFilePath", "getRawReport", "()Lkotlin/jvm/functions/Function2;", "checkAndCommitReportsOn", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "context", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;", "report", "diagnostic", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PendingDiagnosticsCollectorWithSuppress extends BaseDiagnosticsCollector {
    private final Map<String, List<KtDiagnostic>> _diagnosticsByFilePath;
    private boolean hasErrors;
    private final Map<String, List<KtDiagnostic>> pendingDiagnosticsByFilePath;
    private final Function2<Boolean, String, Unit> rawReport;

    /* JADX WARN: Multi-variable type inference failed */
    public PendingDiagnosticsCollectorWithSuppress(Function2<? super Boolean, ? super String, Unit> rawReport) {
        Intrinsics.checkNotNullParameter(rawReport, "rawReport");
        this.rawReport = rawReport;
        this.pendingDiagnosticsByFilePath = new LinkedHashMap();
        this._diagnosticsByFilePath = new LinkedHashMap();
    }

    @Override // org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector
    public Function2<Boolean, String, Unit> getRawReport() {
        return this.rawReport;
    }

    @Override // org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector
    public List<KtDiagnostic> getDiagnostics() {
        Map<String, List<KtDiagnostic>> map = this._diagnosticsByFilePath;
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<String, List<KtDiagnostic>>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, it.next().getValue());
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector
    public Map<String, List<KtDiagnostic>> getDiagnosticsByFilePath() {
        return this._diagnosticsByFilePath;
    }

    @Override // org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector
    public boolean getHasErrors() {
        return this.hasErrors;
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticReporter
    public void report(KtDiagnostic diagnostic, DiagnosticContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (diagnostic == null || context.isDiagnosticSuppressed(diagnostic)) {
            return;
        }
        Map<String, List<KtDiagnostic>> map = this.pendingDiagnosticsByFilePath;
        String containingFilePath = context.getContainingFilePath();
        ArrayList arrayList = map.get(containingFilePath);
        if (arrayList == null) {
            arrayList = new ArrayList();
            map.put(containingFilePath, arrayList);
        }
        arrayList.add(diagnostic);
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticReporter
    public void checkAndCommitReportsOn(AbstractKtSourceElement element, DiagnosticContext context) {
        Intrinsics.checkNotNullParameter(element, "element");
        boolean z = context == null;
        for (Map.Entry<String, List<KtDiagnostic>> entry : this.pendingDiagnosticsByFilePath.entrySet()) {
            String key = entry.getKey();
            List<KtDiagnostic> value = entry.getValue();
            Map<String, List<KtDiagnostic>> map = this._diagnosticsByFilePath;
            ArrayList arrayList = map.get(key);
            if (arrayList == null) {
                arrayList = new ArrayList();
                map.put(key, arrayList);
            }
            List<KtDiagnostic> list = arrayList;
            Iterator<KtDiagnostic> it = value.iterator();
            while (it.hasNext()) {
                KtDiagnostic next = it.next();
                if (context != null && context.isDiagnosticSuppressed(next)) {
                    if (Intrinsics.areEqual(next.getElement(), element) || (next.getElement().getStartOffset() >= element.getStartOffset() && next.getElement().getEndOffset() <= element.getEndOffset())) {
                        it.remove();
                    }
                } else if (Intrinsics.areEqual(next.getElement(), element) || z) {
                    it.remove();
                    list.add(next);
                    if (!getHasErrors() && next.getSeverity() == Severity.ERROR) {
                        this.hasErrors = true;
                    }
                }
            }
        }
    }
}
