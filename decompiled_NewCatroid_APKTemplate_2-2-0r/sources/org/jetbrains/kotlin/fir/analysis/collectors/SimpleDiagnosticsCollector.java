package org.jetbrains.kotlin.fir.analysis.collectors;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.context.MutableCheckerContext;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculatorForFullBodyResolve;

/* JADX INFO: compiled from: SimpleDiagnosticsCollector.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\u0010\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\tH\u0014¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/collectors/SimpleDiagnosticsCollector;", "Lorg/jetbrains/kotlin/fir/analysis/collectors/AbstractDiagnosticCollector;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "createComponents", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "Lorg/jetbrains/kotlin/fir/analysis/collectors/DiagnosticCollectorComponents;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lkotlin/jvm/functions/Function1;)V", "createVisitor", "Lorg/jetbrains/kotlin/fir/analysis/collectors/CheckerRunningDiagnosticCollectorVisitor;", "components", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleDiagnosticsCollector extends AbstractDiagnosticCollector {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimpleDiagnosticsCollector(FirSession session, ScopeSession scopeSession, Function1<? super DiagnosticReporter, DiagnosticCollectorComponents> createComponents) {
        super(session, scopeSession, createComponents);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(createComponents, "createComponents");
    }

    @Override // org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollector
    protected CheckerRunningDiagnosticCollectorVisitor createVisitor(DiagnosticCollectorComponents components) {
        Intrinsics.checkNotNullParameter(components, "components");
        return new CheckerRunningDiagnosticCollectorVisitor(new MutableCheckerContext(this, ReturnTypeCalculatorForFullBodyResolve.INSTANCE.getDefault()), components);
    }
}
