package org.jetbrains.kotlin.fir.analysis.checkers.cfa;

import kotlin.Metadata;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;

/* JADX INFO: compiled from: FirControlFlowChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/cfa/FirControlFlowChecker;", "", "()V", "analyze", "", "graph", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirControlFlowChecker {
    public abstract void analyze(ControlFlowGraph graph, DiagnosticReporter reporter, CheckerContext context);
}
