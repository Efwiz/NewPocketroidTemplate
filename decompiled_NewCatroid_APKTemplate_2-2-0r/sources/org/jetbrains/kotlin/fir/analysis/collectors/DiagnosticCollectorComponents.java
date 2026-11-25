package org.jetbrains.kotlin.fir.analysis.collectors;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.analysis.collectors.components.AbstractDiagnosticCollectorComponent;
import org.jetbrains.kotlin.fir.analysis.collectors.components.ReportCommitterDiagnosticComponent;

/* JADX INFO: compiled from: DiagnosticCollectorComponents.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/collectors/DiagnosticCollectorComponents;", "", "regularComponents", "", "Lorg/jetbrains/kotlin/fir/analysis/collectors/components/AbstractDiagnosticCollectorComponent;", "reportCommitter", "Lorg/jetbrains/kotlin/fir/analysis/collectors/components/ReportCommitterDiagnosticComponent;", "(Ljava/util/List;Lorg/jetbrains/kotlin/fir/analysis/collectors/components/ReportCommitterDiagnosticComponent;)V", "getRegularComponents", "()Ljava/util/List;", "getReportCommitter", "()Lorg/jetbrains/kotlin/fir/analysis/collectors/components/ReportCommitterDiagnosticComponent;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DiagnosticCollectorComponents {
    private final List<AbstractDiagnosticCollectorComponent> regularComponents;
    private final ReportCommitterDiagnosticComponent reportCommitter;

    /* JADX WARN: Multi-variable type inference failed */
    public DiagnosticCollectorComponents(List<? extends AbstractDiagnosticCollectorComponent> regularComponents, ReportCommitterDiagnosticComponent reportCommitter) {
        Intrinsics.checkNotNullParameter(regularComponents, "regularComponents");
        Intrinsics.checkNotNullParameter(reportCommitter, "reportCommitter");
        this.regularComponents = regularComponents;
        this.reportCommitter = reportCommitter;
    }

    public final List<AbstractDiagnosticCollectorComponent> getRegularComponents() {
        return this.regularComponents;
    }

    public final ReportCommitterDiagnosticComponent getReportCommitter() {
        return this.reportCommitter;
    }
}
