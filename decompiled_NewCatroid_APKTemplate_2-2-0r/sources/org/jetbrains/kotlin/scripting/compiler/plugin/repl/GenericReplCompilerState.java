package org.jetbrains.kotlin.scripting.compiler.plugin.repl;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.dependencies.ScriptDependencies;
import org.jetbrains.kotlin.cli.common.repl.BasicReplStageHistory;
import org.jetbrains.kotlin.cli.common.repl.IReplStageHistory;
import org.jetbrains.kotlin.cli.common.repl.IReplStageState;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: GenericCompilerState.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0017\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/GenericReplCompilerState;", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/GenericReplCheckerState;", "environment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "(Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;Ljava/util/concurrent/locks/ReentrantReadWriteLock;)V", "analyzerEngine", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase;", "getAnalyzerEngine", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase;", "currentGeneration", "", "getCurrentGeneration", "()I", "history", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCompilerStageHistory;", "getHistory", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCompilerStageHistory;", "lastDependencies", "Lkotlin/script/experimental/dependencies/ScriptDependencies;", "getLastDependencies", "()Lkotlin/script/experimental/dependencies/ScriptDependencies;", "setLastDependencies", "(Lkotlin/script/experimental/dependencies/ScriptDependencies;)V", "getLock", "()Ljava/util/concurrent/locks/ReentrantReadWriteLock;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GenericReplCompilerState extends GenericReplCheckerState implements IReplStageState<ScriptDescriptor> {
    private final ReplCodeAnalyzerBase analyzerEngine;
    private final ReplCompilerStageHistory history;
    private ScriptDependencies lastDependencies;
    private final ReentrantReadWriteLock lock;

    public /* synthetic */ GenericReplCompilerState(KotlinCoreEnvironment kotlinCoreEnvironment, ReentrantReadWriteLock reentrantReadWriteLock, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kotlinCoreEnvironment, (i & 2) != 0 ? new ReentrantReadWriteLock() : reentrantReadWriteLock);
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public ReentrantReadWriteLock getLock() {
        return this.lock;
    }

    public GenericReplCompilerState(KotlinCoreEnvironment environment, ReentrantReadWriteLock lock) {
        Intrinsics.checkNotNullParameter(environment, "environment");
        Intrinsics.checkNotNullParameter(lock, "lock");
        this.lock = lock;
        this.history = new ReplCompilerStageHistory(this);
        this.analyzerEngine = new ReplCodeAnalyzerBase(environment, null, null, 6, null);
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public IReplStageHistory<ScriptDescriptor> getHistory() {
        return this.history;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public int getCurrentGeneration() {
        IReplStageHistory<ScriptDescriptor> history = getHistory();
        Intrinsics.checkNotNull(history, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.common.repl.BasicReplStageHistory<*>");
        return ((BasicReplStageHistory) history).getCurrentGeneration().get();
    }

    public final ReplCodeAnalyzerBase getAnalyzerEngine() {
        return this.analyzerEngine;
    }

    public final ScriptDependencies getLastDependencies() {
        return this.lastDependencies;
    }

    public final void setLastDependencies(ScriptDependencies scriptDependencies) {
        this.lastDependencies = scriptDependencies;
    }
}
