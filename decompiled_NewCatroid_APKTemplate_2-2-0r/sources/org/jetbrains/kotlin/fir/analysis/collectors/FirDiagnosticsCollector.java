package org.jetbrains.kotlin.fir.analysis.collectors;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.collectors.components.DiagnosticComponentsFactory;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;

/* JADX INFO: compiled from: FirDiagnosticsCollector.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/collectors/FirDiagnosticsCollector;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/fir/analysis/collectors/SimpleDiagnosticsCollector;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDiagnosticsCollector {
    public static final FirDiagnosticsCollector INSTANCE = new FirDiagnosticsCollector();

    private FirDiagnosticsCollector() {
    }

    public final SimpleDiagnosticsCollector create(final FirSession session, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        return new SimpleDiagnosticsCollector(session, scopeSession, new Function1<DiagnosticReporter, DiagnosticCollectorComponents>() { // from class: org.jetbrains.kotlin.fir.analysis.collectors.FirDiagnosticsCollector.create.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final DiagnosticCollectorComponents invoke(DiagnosticReporter reporter) {
                Intrinsics.checkNotNullParameter(reporter, "reporter");
                return DiagnosticComponentsFactory.INSTANCE.createAllDiagnosticComponents(session, reporter);
            }
        });
    }
}
