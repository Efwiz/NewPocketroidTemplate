package org.jetbrains.kotlin.cli.common.repl;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BasicReplState.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u000f\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u0010\u001a\u00020\u0007H\u0016R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/BasicReplStageState;", "HistoryItemT", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "(Ljava/util/concurrent/locks/ReentrantReadWriteLock;)V", "currentGeneration", "", "getCurrentGeneration", "()I", "history", "Lorg/jetbrains/kotlin/cli/common/repl/BasicReplStageHistory;", "getHistory", "()Lorg/jetbrains/kotlin/cli/common/repl/BasicReplStageHistory;", "getLock", "()Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "getNextLineNo", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class BasicReplStageState<HistoryItemT> implements IReplStageState<HistoryItemT> {
    private final BasicReplStageHistory<HistoryItemT> history;
    private final ReentrantReadWriteLock lock;

    /* JADX WARN: Multi-variable type inference failed */
    public BasicReplStageState() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public BasicReplStageState(ReentrantReadWriteLock lock) {
        Intrinsics.checkNotNullParameter(lock, "lock");
        this.lock = lock;
        this.history = new BasicReplStageHistory<>(lock);
    }

    public /* synthetic */ BasicReplStageState(ReentrantReadWriteLock reentrantReadWriteLock, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new ReentrantReadWriteLock() : reentrantReadWriteLock);
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public final ReentrantReadWriteLock getLock() {
        return this.lock;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public int getCurrentGeneration() {
        return getHistory().getCurrentGeneration().get();
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public int getNextLineNo() {
        return getHistory().getCurrentLineNumber().getAndIncrement();
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public BasicReplStageHistory<HistoryItemT> getHistory() {
        return this.history;
    }
}
