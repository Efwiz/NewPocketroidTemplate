package org.jetbrains.kotlin.scripting.compiler.plugin.repl;

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.repl.BasicReplStageHistory;
import org.jetbrains.kotlin.cli.common.repl.ILineId;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: GenericCompilerState.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J$\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0002J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0016J\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u0010\u001a\u00020\nH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCompilerStageHistory;", "Lorg/jetbrains/kotlin/cli/common/repl/BasicReplStageHistory;", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "state", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/GenericReplCompilerState;", "(Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/GenericReplCompilerState;)V", "checkConsistent", "", "removedCompiledLines", "", "Lorg/jetbrains/kotlin/cli/common/repl/ILineId;", "removedAnalyzedLines", "", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/SourceCodeByReplLine;", "reset", "resetTo", "id", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReplCompilerStageHistory extends BasicReplStageHistory<ScriptDescriptor> {
    private final GenericReplCompilerState state;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReplCompilerStageHistory(GenericReplCompilerState state) {
        super(state.getLock());
        Intrinsics.checkNotNullParameter(state, "state");
        this.state = state;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.BasicReplStageHistory, org.jetbrains.kotlin.cli.common.repl.IReplStageHistory
    public Iterable<ILineId> reset() {
        ReentrantReadWriteLock lock = getLock();
        ReentrantReadWriteLock.ReadLock lock2 = lock.readLock();
        int i = 0;
        int readHoldCount = lock.getWriteHoldCount() == 0 ? lock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock2.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            Iterable<ILineId> iterableReset = super.reset();
            checkConsistent(iterableReset, this.state.getAnalyzerEngine().reset());
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            return iterableReset;
        } finally {
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.BasicReplStageHistory, org.jetbrains.kotlin.cli.common.repl.IReplStageHistory
    public Iterable<ILineId> resetTo(ILineId id2) {
        Intrinsics.checkNotNullParameter(id2, "id");
        ReentrantReadWriteLock lock = getLock();
        ReentrantReadWriteLock.ReadLock lock2 = lock.readLock();
        int i = 0;
        int readHoldCount = lock.getWriteHoldCount() == 0 ? lock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock2.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            Iterable<ILineId> iterableResetTo = super.resetTo(id2);
            checkConsistent(iterableResetTo, this.state.getAnalyzerEngine().resetToLine(id2));
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            return iterableResetTo;
        } finally {
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
        }
    }

    private final void checkConsistent(Iterable<? extends ILineId> removedCompiledLines, List<SourceCodeByReplLine> removedAnalyzedLines) {
        for (Pair pair : CollectionsKt.zip(removedCompiledLines, removedAnalyzedLines)) {
            ILineId iLineId = (ILineId) pair.component1();
            SourceCodeByReplLine sourceCodeByReplLine = (SourceCodeByReplLine) pair.component2();
            if (iLineId.getNo() != sourceCodeByReplLine.getNo()) {
                throw new IllegalStateException("History mismatch when resetting lines: " + iLineId.getNo() + " != " + sourceCodeByReplLine);
            }
        }
    }
}
