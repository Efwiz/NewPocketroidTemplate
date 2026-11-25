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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.KtDiagnostic;
import org.jetbrains.kotlin.diagnostics.Severity;

/* JADX INFO: compiled from: SimpleDiagnosticsCollector.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B!\u0012\u001a\b\u0002\u0010\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\u0002\u0010\u0007J\u001a\u0010\u001a\u001a\u00020\u00062\b\u0010\u001b\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016R\"\u0010\b\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR(\u0010\u0010\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\r0\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001e\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0004@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R&\u0010\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/impl/SimpleDiagnosticsCollector;", "Lorg/jetbrains/kotlin/diagnostics/impl/BaseDiagnosticsCollector;", "rawReport", "Lkotlin/Function2;", "", "", "", "(Lkotlin/jvm/functions/Function2;)V", "_diagnosticsByFilePath", "", "", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "diagnostics", "", "getDiagnostics", "()Ljava/util/List;", "diagnosticsByFilePath", "", "getDiagnosticsByFilePath", "()Ljava/util/Map;", "<set-?>", "hasErrors", "getHasErrors", "()Z", "getRawReport", "()Lkotlin/jvm/functions/Function2;", "report", "diagnostic", "context", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleDiagnosticsCollector extends BaseDiagnosticsCollector {
    private final Map<String, List<KtDiagnostic>> _diagnosticsByFilePath;
    private boolean hasErrors;
    private final Function2<Boolean, String, Unit> rawReport;

    /* JADX WARN: Multi-variable type inference failed */
    public SimpleDiagnosticsCollector() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SimpleDiagnosticsCollector(Function2<? super Boolean, ? super String, Unit> rawReport) {
        Intrinsics.checkNotNullParameter(rawReport, "rawReport");
        this.rawReport = rawReport;
        this._diagnosticsByFilePath = new LinkedHashMap();
    }

    public /* synthetic */ SimpleDiagnosticsCollector(AnonymousClass1 anonymousClass1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new Function2<Boolean, String, Unit>() { // from class: org.jetbrains.kotlin.diagnostics.impl.SimpleDiagnosticsCollector.1
            public final void invoke(boolean z, String str) {
                Intrinsics.checkNotNullParameter(str, "<anonymous parameter 1>");
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(Boolean bool, String str) {
                invoke(bool.booleanValue(), str);
                return Unit.INSTANCE;
            }
        } : anonymousClass1);
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
        if (diagnostic != null) {
            Map<String, List<KtDiagnostic>> map = this._diagnosticsByFilePath;
            String containingFilePath = context.getContainingFilePath();
            ArrayList arrayList = map.get(containingFilePath);
            if (arrayList == null) {
                arrayList = new ArrayList();
                map.put(containingFilePath, arrayList);
            }
            arrayList.add(diagnostic);
            if (getHasErrors() || diagnostic.getSeverity() != Severity.ERROR) {
                return;
            }
            this.hasErrors = true;
        }
    }
}
