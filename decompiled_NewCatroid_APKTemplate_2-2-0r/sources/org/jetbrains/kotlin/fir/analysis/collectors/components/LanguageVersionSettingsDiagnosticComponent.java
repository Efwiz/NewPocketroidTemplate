package org.jetbrains.kotlin.fir.analysis.collectors.components;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.CheckersComponentKt;
import org.jetbrains.kotlin.fir.analysis.checkers.LanguageVersionSettingsCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.config.FirLanguageVersionSettingsChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;

/* JADX INFO: compiled from: LanguageVersionSettingsDiagnosticComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/collectors/components/LanguageVersionSettingsDiagnosticComponent;", "Lorg/jetbrains/kotlin/fir/analysis/collectors/components/AbstractDiagnosticCollectorComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/LanguageVersionSettingsCheckers;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/fir/analysis/checkers/LanguageVersionSettingsCheckers;)V", "checkSettings", "", "data", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LanguageVersionSettingsDiagnosticComponent extends AbstractDiagnosticCollectorComponent {
    private final LanguageVersionSettingsCheckers checkers;

    public /* synthetic */ LanguageVersionSettingsDiagnosticComponent(FirSession firSession, DiagnosticReporter diagnosticReporter, LanguageVersionSettingsCheckers languageVersionSettingsCheckers, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, diagnosticReporter, (i & 4) != 0 ? CheckersComponentKt.getCheckersComponent(firSession).getLanguageVersionSettingsCheckers() : languageVersionSettingsCheckers);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LanguageVersionSettingsDiagnosticComponent(FirSession session, DiagnosticReporter reporter, LanguageVersionSettingsCheckers checkers) {
        super(session, reporter);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(checkers, "checkers");
        this.checkers = checkers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.collectors.components.AbstractDiagnosticCollectorComponent
    public void checkSettings(CheckerContext data) {
        Function2<Boolean, String, Unit> rawReport;
        Intrinsics.checkNotNullParameter(data, "data");
        DiagnosticReporter reporter = getReporter();
        BaseDiagnosticsCollector baseDiagnosticsCollector = reporter instanceof BaseDiagnosticsCollector ? (BaseDiagnosticsCollector) reporter : null;
        if (baseDiagnosticsCollector == null || (rawReport = baseDiagnosticsCollector.getRawReport()) == null) {
            return;
        }
        Iterator<FirLanguageVersionSettingsChecker> it = this.checkers.getLanguageVersionSettingsCheckers().iterator();
        while (it.hasNext()) {
            it.next().check(data, rawReport);
        }
    }
}
