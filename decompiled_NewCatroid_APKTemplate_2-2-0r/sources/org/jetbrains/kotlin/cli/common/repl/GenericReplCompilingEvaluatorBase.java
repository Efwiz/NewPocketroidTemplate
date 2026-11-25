package org.jetbrains.kotlin.cli.common.repl;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: GenericReplCompilingEvaluator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001:\u0001!B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ0\u0010\r\u001a\u00020\u000e2\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J4\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u00172\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u001a\u001a\u0004\u0018\u00010\u0007H\u0016J\u0014\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J0\u0010\u001e\u001a\u00020\u000e2\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u001f\u001a\u00020 2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/GenericReplCompilingEvaluatorBase;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplFullEvaluator;", "compiler", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompilerWithoutCheck;", "evaluator", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvaluator;", "fallbackScriptArgs", "Lorg/jetbrains/kotlin/cli/common/repl/ScriptArgsWithTypes;", "(Lorg/jetbrains/kotlin/cli/common/repl/ReplCompilerWithoutCheck;Lorg/jetbrains/kotlin/cli/common/repl/ReplEvaluator;Lorg/jetbrains/kotlin/cli/common/repl/ScriptArgsWithTypes;)V", "getCompiler", "()Lorg/jetbrains/kotlin/cli/common/repl/ReplCompilerWithoutCheck;", "getEvaluator", "()Lorg/jetbrains/kotlin/cli/common/repl/ReplEvaluator;", "compileAndEval", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult;", "state", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "codeLine", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "scriptArgs", "invokeWrapper", "Lorg/jetbrains/kotlin/cli/common/repl/InvokeWrapper;", "compileToEvaluable", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult;", "Lorg/jetbrains/kotlin/cli/common/repl/Evaluable;", "defaultScriptArgs", "createState", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "eval", "compileResult", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult$CompiledClasses;", "DelayedEvaluation", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class GenericReplCompilingEvaluatorBase implements ReplFullEvaluator {
    private final ReplCompilerWithoutCheck compiler;
    private final ReplEvaluator evaluator;
    private final ScriptArgsWithTypes fallbackScriptArgs;

    public GenericReplCompilingEvaluatorBase(ReplCompilerWithoutCheck compiler, ReplEvaluator evaluator, ScriptArgsWithTypes scriptArgsWithTypes) {
        Intrinsics.checkNotNullParameter(compiler, "compiler");
        Intrinsics.checkNotNullParameter(evaluator, "evaluator");
        this.compiler = compiler;
        this.evaluator = evaluator;
        this.fallbackScriptArgs = scriptArgsWithTypes;
    }

    public /* synthetic */ GenericReplCompilingEvaluatorBase(ReplCompilerWithoutCheck replCompilerWithoutCheck, ReplEvaluator replEvaluator, ScriptArgsWithTypes scriptArgsWithTypes, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(replCompilerWithoutCheck, replEvaluator, (i & 4) != 0 ? null : scriptArgsWithTypes);
    }

    public final ReplCompilerWithoutCheck getCompiler() {
        return this.compiler;
    }

    public final ReplEvaluator getEvaluator() {
        return this.evaluator;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.CreateReplStageStateAction
    public IReplStageState<?> createState(ReentrantReadWriteLock lock) {
        Intrinsics.checkNotNullParameter(lock, "lock");
        return new AggregatedReplStageState(this.compiler.createState(lock), this.evaluator.createState(lock), lock);
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.ReplAtomicEvalAction
    public ReplEvalResult compileAndEval(IReplStageState<?> state, ReplCodeLine codeLine, ScriptArgsWithTypes scriptArgs, InvokeWrapper invokeWrapper) {
        ReplEvalResult.Incomplete incompleteEval;
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(codeLine, "codeLine");
        if (StringsKt.trim((CharSequence) codeLine.getCode()).toString().length() == 0) {
            return new ReplEvalResult.UnitResult();
        }
        ReentrantReadWriteLock lock = state.getLock();
        ReentrantReadWriteLock.ReadLock lock2 = lock.readLock();
        int i = 0;
        int readHoldCount = lock.getWriteHoldCount() == 0 ? lock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock2.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            AggregatedReplStageState aggregatedReplStageState = (AggregatedReplStageState) state.asState(AggregatedReplStageState.class);
            ReplCompileResult replCompileResultCompile = this.compiler.compile(state, codeLine);
            if (replCompileResultCompile instanceof ReplCompileResult.Error) {
                ReentrantReadWriteLock lock3 = aggregatedReplStageState.getLock();
                ReentrantReadWriteLock.ReadLock lock4 = lock3.readLock();
                int readHoldCount2 = lock3.getWriteHoldCount() == 0 ? lock3.getReadHoldCount() : 0;
                for (int i3 = 0; i3 < readHoldCount2; i3++) {
                    lock4.unlock();
                }
                ReentrantReadWriteLock.WriteLock writeLock2 = lock3.writeLock();
                writeLock2.lock();
                try {
                    aggregatedReplStageState.getState1().getHistory().size();
                    aggregatedReplStageState.getState2().getHistory().size();
                    GenericReplCompilingEvaluatorKt.adjustHistories(aggregatedReplStageState);
                    Unit unit = Unit.INSTANCE;
                    for (int i4 = 0; i4 < readHoldCount2; i4++) {
                        lock4.lock();
                    }
                    writeLock2.unlock();
                    incompleteEval = new ReplEvalResult.Error.CompileTime(((ReplCompileResult.Error) replCompileResultCompile).getMessage(), ((ReplCompileResult.Error) replCompileResultCompile).getLocation());
                } finally {
                    for (int i5 = 0; i5 < readHoldCount2; i5++) {
                        lock4.lock();
                    }
                    writeLock2.unlock();
                }
            } else if (replCompileResultCompile instanceof ReplCompileResult.Incomplete) {
                incompleteEval = new ReplEvalResult.Incomplete(((ReplCompileResult.Incomplete) replCompileResultCompile).getMessage());
            } else {
                if (!(replCompileResultCompile instanceof ReplCompileResult.CompiledClasses)) {
                    throw new NoWhenBranchMatchedException();
                }
                incompleteEval = eval(state, (ReplCompileResult.CompiledClasses) replCompileResultCompile, scriptArgs, invokeWrapper);
                boolean z = true;
                if (incompleteEval instanceof ReplEvalResult.Error ? true : incompleteEval instanceof ReplEvalResult.HistoryMismatch ? true : incompleteEval instanceof ReplEvalResult.Incomplete) {
                    ReentrantReadWriteLock lock5 = aggregatedReplStageState.getLock();
                    lock2 = lock5.readLock();
                    readHoldCount = lock5.getWriteHoldCount() == 0 ? lock5.getReadHoldCount() : 0;
                    for (int i6 = 0; i6 < readHoldCount; i6++) {
                        lock2.unlock();
                    }
                    writeLock = lock5.writeLock();
                    writeLock.lock();
                    try {
                        if (aggregatedReplStageState.getState1().getHistory().size() > aggregatedReplStageState.getState2().getHistory().size()) {
                            GenericReplCompilingEvaluatorKt.adjustHistories(aggregatedReplStageState);
                            aggregatedReplStageState.getState1().getHistory().size();
                            aggregatedReplStageState.getState2().getHistory().size();
                        }
                        Unit unit2 = Unit.INSTANCE;
                        for (int i7 = 0; i7 < readHoldCount; i7++) {
                            lock2.lock();
                        }
                        writeLock.unlock();
                    } finally {
                        for (int i8 = 0; i8 < readHoldCount; i8++) {
                            lock2.lock();
                        }
                        writeLock.unlock();
                    }
                } else {
                    if (!(incompleteEval instanceof ReplEvalResult.ValueResult)) {
                        z = incompleteEval instanceof ReplEvalResult.UnitResult;
                    }
                    if (!z) {
                        throw new NoWhenBranchMatchedException();
                    }
                }
            }
            return incompleteEval;
        } catch (Throwable th) {
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
            throw th;
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.ReplEvalAction
    public ReplEvalResult eval(IReplStageState<?> state, ReplCompileResult.CompiledClasses compileResult, ScriptArgsWithTypes scriptArgs, InvokeWrapper invokeWrapper) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(compileResult, "compileResult");
        return this.evaluator.eval(state, compileResult, scriptArgs, invokeWrapper);
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.ReplDelayedEvalAction
    public Pair<ReplCompileResult, Evaluable> compileToEvaluable(IReplStageState<?> state, ReplCodeLine codeLine, ScriptArgsWithTypes defaultScriptArgs) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(codeLine, "codeLine");
        ReplCompileResult replCompileResultCompile = this.compiler.compile(state, codeLine);
        if (!(replCompileResultCompile instanceof ReplCompileResult.CompiledClasses)) {
            return new Pair<>(replCompileResultCompile, null);
        }
        ReplCompileResult.CompiledClasses compiledClasses = (ReplCompileResult.CompiledClasses) replCompileResultCompile;
        ReplEvaluator replEvaluator = this.evaluator;
        if (defaultScriptArgs == null) {
            defaultScriptArgs = this.fallbackScriptArgs;
        }
        return new Pair<>(replCompileResultCompile, new DelayedEvaluation(state, compiledClasses, replEvaluator, defaultScriptArgs));
    }

    /* JADX INFO: compiled from: GenericReplCompilingEvaluator.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B+\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\u001c\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\t2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/GenericReplCompilingEvaluatorBase$DelayedEvaluation;", "Lorg/jetbrains/kotlin/cli/common/repl/Evaluable;", "state", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "compiledCode", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult$CompiledClasses;", "evaluator", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvaluator;", "defaultScriptArgs", "Lorg/jetbrains/kotlin/cli/common/repl/ScriptArgsWithTypes;", "(Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult$CompiledClasses;Lorg/jetbrains/kotlin/cli/common/repl/ReplEvaluator;Lorg/jetbrains/kotlin/cli/common/repl/ScriptArgsWithTypes;)V", "getCompiledCode", "()Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult$CompiledClasses;", "eval", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult;", "scriptArgs", "invokeWrapper", "Lorg/jetbrains/kotlin/cli/common/repl/InvokeWrapper;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DelayedEvaluation implements Evaluable {
        private final ReplCompileResult.CompiledClasses compiledCode;
        private final ScriptArgsWithTypes defaultScriptArgs;
        private final ReplEvaluator evaluator;
        private final IReplStageState<?> state;

        public DelayedEvaluation(IReplStageState<?> state, ReplCompileResult.CompiledClasses compiledCode, ReplEvaluator evaluator, ScriptArgsWithTypes scriptArgsWithTypes) {
            Intrinsics.checkNotNullParameter(state, "state");
            Intrinsics.checkNotNullParameter(compiledCode, "compiledCode");
            Intrinsics.checkNotNullParameter(evaluator, "evaluator");
            this.state = state;
            this.compiledCode = compiledCode;
            this.evaluator = evaluator;
            this.defaultScriptArgs = scriptArgsWithTypes;
        }

        @Override // org.jetbrains.kotlin.cli.common.repl.Evaluable
        public ReplCompileResult.CompiledClasses getCompiledCode() {
            return this.compiledCode;
        }

        @Override // org.jetbrains.kotlin.cli.common.repl.Evaluable
        public ReplEvalResult eval(ScriptArgsWithTypes scriptArgs, InvokeWrapper invokeWrapper) {
            ReplEvaluator replEvaluator = this.evaluator;
            IReplStageState<?> iReplStageState = this.state;
            ReplCompileResult.CompiledClasses compiledCode = getCompiledCode();
            if (scriptArgs == null) {
                scriptArgs = this.defaultScriptArgs;
            }
            return replEvaluator.eval(iReplStageState, compiledCode, scriptArgs, invokeWrapper);
        }
    }
}
