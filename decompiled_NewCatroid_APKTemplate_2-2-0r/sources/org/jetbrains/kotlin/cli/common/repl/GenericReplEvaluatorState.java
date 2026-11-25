package org.jetbrains.kotlin.cli.common.repl;

import java.io.File;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: GenericEvaluatorState.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B'\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u001a¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/GenericReplEvaluatorState;", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "Lorg/jetbrains/kotlin/cli/common/repl/EvalClassWithInstanceAndLoader;", "baseClasspath", "", "Ljava/io/File;", "baseClassloader", "Ljava/lang/ClassLoader;", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "(Ljava/lang/Iterable;Ljava/lang/ClassLoader;Ljava/util/concurrent/locks/ReentrantReadWriteLock;)V", "currentClasspath", "", "getCurrentClasspath", "()Ljava/util/List;", "currentGeneration", "", "getCurrentGeneration", "()I", "history", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageHistory;", "getHistory", "()Lorg/jetbrains/kotlin/cli/common/repl/IReplStageHistory;", "getLock", "()Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "topClassLoader", "Lorg/jetbrains/kotlin/cli/common/repl/ReplClassLoader;", "getTopClassLoader", "()Lorg/jetbrains/kotlin/cli/common/repl/ReplClassLoader;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class GenericReplEvaluatorState implements IReplStageState<EvalClassWithInstanceAndLoader> {
    private final IReplStageHistory<EvalClassWithInstanceAndLoader> history;
    private final ReentrantReadWriteLock lock;
    private final ReplClassLoader topClassLoader;

    public GenericReplEvaluatorState(Iterable<? extends File> baseClasspath, ClassLoader classLoader, ReentrantReadWriteLock lock) {
        Intrinsics.checkNotNullParameter(baseClasspath, "baseClasspath");
        Intrinsics.checkNotNullParameter(lock, "lock");
        this.lock = lock;
        this.history = new BasicReplStageHistory(getLock());
        this.topClassLoader = GenericEvaluatorStateKt.makeReplClassLoader(classLoader, baseClasspath);
    }

    public /* synthetic */ GenericReplEvaluatorState(Iterable iterable, ClassLoader classLoader, ReentrantReadWriteLock reentrantReadWriteLock, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(iterable, classLoader, (i & 4) != 0 ? new ReentrantReadWriteLock() : reentrantReadWriteLock);
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public ReentrantReadWriteLock getLock() {
        return this.lock;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public IReplStageHistory<EvalClassWithInstanceAndLoader> getHistory() {
        return this.history;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public int getCurrentGeneration() {
        IReplStageHistory<EvalClassWithInstanceAndLoader> history = getHistory();
        Intrinsics.checkNotNull(history, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.common.repl.BasicReplStageHistory<*>");
        return ((BasicReplStageHistory) history).getCurrentGeneration().get();
    }

    public final ReplClassLoader getTopClassLoader() {
        return this.topClassLoader;
    }

    public final List<File> getCurrentClasspath() {
        List<File> listListAllUrlsAsFiles;
        EvalClassWithInstanceAndLoader item;
        ClassLoader classLoader;
        ReentrantReadWriteLock.ReadLock lock = getLock().readLock();
        lock.lock();
        try {
            ReplHistoryRecord<EvalClassWithInstanceAndLoader> replHistoryRecordPeek = getHistory().peek();
            if (replHistoryRecordPeek == null || (item = replHistoryRecordPeek.getItem()) == null || (classLoader = item.getClassLoader()) == null || (listListAllUrlsAsFiles = ReplUtilKt.listAllUrlsAsFiles(classLoader)) == null) {
                listListAllUrlsAsFiles = ReplUtilKt.listAllUrlsAsFiles(this.topClassLoader);
            }
            return listListAllUrlsAsFiles;
        } finally {
            lock.unlock();
        }
    }
}
