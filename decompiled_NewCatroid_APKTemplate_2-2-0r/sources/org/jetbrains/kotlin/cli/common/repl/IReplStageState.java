package org.jetbrains.kotlin.cli.common.repl;

import androidx.exifinterface.media.ExifInterface;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ReplState.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J+\u0010\u000f\u001a\u0002H\u0010\"\f\b\u0001\u0010\u0010*\u0006\u0012\u0002\b\u00030\u00002\u000e\u0010\u0011\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00100\u0012H\u0016¢\u0006\u0002\u0010\u0013J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0004H\u0016R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0017À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", ExifInterface.GPS_DIRECTION_TRUE, "", "currentGeneration", "", "getCurrentGeneration", "()I", "history", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageHistory;", "getHistory", "()Lorg/jetbrains/kotlin/cli/common/repl/IReplStageHistory;", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "getLock", "()Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "asState", "StateT", "target", "Ljava/lang/Class;", "(Ljava/lang/Class;)Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "dispose", "", "getNextLineNo", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IReplStageState<T> {
    default void dispose() {
    }

    int getCurrentGeneration();

    IReplStageHistory<T> getHistory();

    ReentrantReadWriteLock getLock();

    default int getNextLineNo() {
        ILineId id2;
        ReplHistoryRecord<T> replHistoryRecordPeek = getHistory().peek();
        if (replHistoryRecordPeek == null || (id2 = replHistoryRecordPeek.getId()) == null) {
            return 0;
        }
        return id2.getNo() + 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    default <StateT extends IReplStageState<?>> StateT asState(Class<? extends StateT> target) {
        Intrinsics.checkNotNullParameter(target, "target");
        if (!target.isAssignableFrom(getClass())) {
            throw new IllegalArgumentException(this + " is not an expected instance of IReplStageState");
        }
        Intrinsics.checkNotNull(this, "null cannot be cast to non-null type StateT of org.jetbrains.kotlin.cli.common.repl.IReplStageState.asState");
        return this;
    }
}
