package org.jetbrains.kotlin.fir.analysis.collectors.components;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.collectors.DiagnosticCollectorComponents;

/* JADX INFO: compiled from: DiagnosticComponentsFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/collectors/components/DiagnosticComponentsFactory;", "", "()V", "createAllDiagnosticComponents", "Lorg/jetbrains/kotlin/fir/analysis/collectors/DiagnosticCollectorComponents;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DiagnosticComponentsFactory {
    public static final DiagnosticComponentsFactory INSTANCE = new DiagnosticComponentsFactory();

    private DiagnosticComponentsFactory() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final DiagnosticCollectorComponents createAllDiagnosticComponents(FirSession session, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        int i = 4;
        DefaultConstructorMarker defaultConstructorMarker = null;
        return new DiagnosticCollectorComponents(CollectionsKt.listOf((Object[]) new AbstractDiagnosticCollectorComponent[]{new DeclarationCheckersDiagnosticComponent(session, reporter, null, i, defaultConstructorMarker), new ExpressionCheckersDiagnosticComponent(session, reporter, 0 == true ? 1 : 0, i, defaultConstructorMarker), new TypeCheckersDiagnosticComponent(session, reporter, 0 == true ? 1 : 0, i, defaultConstructorMarker), new ErrorNodeDiagnosticCollectorComponent(session, reporter), new ControlFlowAnalysisDiagnosticComponent(session, reporter, 0 == true ? 1 : 0, i, defaultConstructorMarker), new LanguageVersionSettingsDiagnosticComponent(session, reporter, 0 == true ? 1 : 0, i, defaultConstructorMarker)}), new ReportCommitterDiagnosticComponent(session, reporter));
    }
}
