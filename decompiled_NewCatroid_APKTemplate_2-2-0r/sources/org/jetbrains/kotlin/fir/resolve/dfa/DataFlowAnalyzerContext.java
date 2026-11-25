package org.jetbrains.kotlin.fir.resolve.dfa;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphBuilder;

/* JADX INFO: compiled from: FirDataFlowAnalyzer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u001e\u001a\u00020\u0006J\u0006\u0010\u001f\u001a\u00020 R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u0010X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001e\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u0014@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001d\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u001a0\u0019¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowAnalyzerContext;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "assignmentCounter", "", "graphBuilder", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphBuilder;", "getGraphBuilder", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphBuilder;", "preliminaryLoopVisitor", "Lorg/jetbrains/kotlin/fir/resolve/dfa/PreliminaryLoopVisitor;", "getPreliminaryLoopVisitor", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/PreliminaryLoopVisitor;", "variableAssignmentAnalyzer", "Lorg/jetbrains/kotlin/fir/resolve/dfa/FirLocalVariableAssignmentAnalyzer;", "getVariableAssignmentAnalyzer$resolve", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/FirLocalVariableAssignmentAnalyzer;", "<set-?>", "Lorg/jetbrains/kotlin/fir/resolve/dfa/VariableStorageImpl;", "variableStorage", "getVariableStorage", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/VariableStorageImpl;", "variablesClearedBeforeLoop", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Stack;", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;", "getVariablesClearedBeforeLoop", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/Stack;", "newAssignmentIndex", "reset", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DataFlowAnalyzerContext {
    private int assignmentCounter;
    private final ControlFlowGraphBuilder graphBuilder;
    private final PreliminaryLoopVisitor preliminaryLoopVisitor;
    private final FirLocalVariableAssignmentAnalyzer variableAssignmentAnalyzer;
    private VariableStorageImpl variableStorage;
    private final Stack<List<RealVariable>> variablesClearedBeforeLoop;

    public DataFlowAnalyzerContext(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.graphBuilder = new ControlFlowGraphBuilder();
        this.preliminaryLoopVisitor = new PreliminaryLoopVisitor();
        this.variablesClearedBeforeLoop = StackKt.stackOf(new List[0]);
        this.variableAssignmentAnalyzer = new FirLocalVariableAssignmentAnalyzer();
        this.variableStorage = new VariableStorageImpl(session);
    }

    public final ControlFlowGraphBuilder getGraphBuilder() {
        return this.graphBuilder;
    }

    public final PreliminaryLoopVisitor getPreliminaryLoopVisitor() {
        return this.preliminaryLoopVisitor;
    }

    public final Stack<List<RealVariable>> getVariablesClearedBeforeLoop() {
        return this.variablesClearedBeforeLoop;
    }

    /* JADX INFO: renamed from: getVariableAssignmentAnalyzer$resolve, reason: from getter */
    public final FirLocalVariableAssignmentAnalyzer getVariableAssignmentAnalyzer() {
        return this.variableAssignmentAnalyzer;
    }

    public final VariableStorageImpl getVariableStorage() {
        return this.variableStorage;
    }

    public final int newAssignmentIndex() {
        int i = this.assignmentCounter;
        this.assignmentCounter = i + 1;
        return i;
    }

    public final void reset() {
        this.graphBuilder.reset();
        this.preliminaryLoopVisitor.resetState();
        this.variablesClearedBeforeLoop.reset();
        this.variableAssignmentAnalyzer.reset();
        this.variableStorage = this.variableStorage.clear();
    }
}
