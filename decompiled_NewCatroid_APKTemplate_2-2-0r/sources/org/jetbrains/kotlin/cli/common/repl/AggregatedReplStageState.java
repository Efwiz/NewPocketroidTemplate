package org.jetbrains.kotlin.cli.common.repl;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AggregatedReplState.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00040\u0003B+\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ+\u0010\u0017\u001a\u0002H\u0018\"\f\b\u0002\u0010\u0018*\u0006\u0012\u0002\b\u00030\u00032\u000e\u0010\u0019\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00180\u001aH\u0016¢\u0006\u0002\u0010\u001bJ\b\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u000bH\u0016R\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR&\u0010\u000e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00040\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/AggregatedReplStageState;", "T1", "T2", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "Lkotlin/Pair;", "state1", "state2", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "(Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;Ljava/util/concurrent/locks/ReentrantReadWriteLock;)V", "currentGeneration", "", "getCurrentGeneration", "()I", "history", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageHistory;", "getHistory", "()Lorg/jetbrains/kotlin/cli/common/repl/IReplStageHistory;", "getLock", "()Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "getState1", "()Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "getState2", "asState", "StateT", "target", "Ljava/lang/Class;", "(Ljava/lang/Class;)Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "dispose", "", "getNextLineNo", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class AggregatedReplStageState<T1, T2> implements IReplStageState<Pair<? extends T1, ? extends T2>> {
    private final IReplStageHistory<Pair<T1, T2>> history;
    private final ReentrantReadWriteLock lock;
    private final IReplStageState<T1> state1;
    private final IReplStageState<T2> state2;

    public AggregatedReplStageState(IReplStageState<T1> state1, IReplStageState<T2> state2, ReentrantReadWriteLock lock) {
        Intrinsics.checkNotNullParameter(state1, "state1");
        Intrinsics.checkNotNullParameter(state2, "state2");
        Intrinsics.checkNotNullParameter(lock, "lock");
        this.state1 = state1;
        this.state2 = state2;
        this.lock = lock;
        this.history = new AggregatedReplStateHistory(state1.getHistory(), state2.getHistory(), lock);
    }

    public /* synthetic */ AggregatedReplStageState(IReplStageState iReplStageState, IReplStageState iReplStageState2, ReentrantReadWriteLock reentrantReadWriteLock, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(iReplStageState, iReplStageState2, (i & 4) != 0 ? new ReentrantReadWriteLock() : reentrantReadWriteLock);
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public final ReentrantReadWriteLock getLock() {
        return this.lock;
    }

    public final IReplStageState<T1> getState1() {
        return this.state1;
    }

    public final IReplStageState<T2> getState2() {
        return this.state2;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public IReplStageHistory<Pair<T1, T2>> getHistory() {
        return this.history;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public <StateT extends IReplStageState<?>> StateT asState(Class<? extends StateT> target) {
        Intrinsics.checkNotNullParameter(target, "target");
        if (target.isAssignableFrom(this.state1.getClass())) {
            IReplStageState<T1> iReplStageState = this.state1;
            Intrinsics.checkNotNull(iReplStageState, "null cannot be cast to non-null type StateT of org.jetbrains.kotlin.cli.common.repl.AggregatedReplStageState.asState");
            return iReplStageState;
        }
        if (!target.isAssignableFrom(this.state2.getClass())) {
            return (StateT) super.asState(target);
        }
        IReplStageState<T2> iReplStageState2 = this.state2;
        Intrinsics.checkNotNull(iReplStageState2, "null cannot be cast to non-null type StateT of org.jetbrains.kotlin.cli.common.repl.AggregatedReplStageState.asState");
        return iReplStageState2;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public int getNextLineNo() {
        return this.state1.getNextLineNo();
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public int getCurrentGeneration() {
        return this.state1.getCurrentGeneration();
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public void dispose() {
        this.state2.dispose();
        this.state1.dispose();
        super.dispose();
    }
}
