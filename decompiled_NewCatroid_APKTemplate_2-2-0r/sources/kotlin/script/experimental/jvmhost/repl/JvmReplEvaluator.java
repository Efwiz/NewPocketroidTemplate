package kotlin.script.experimental.jvmhost.repl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.script.experimental.api.EvaluationResult;
import kotlin.script.experimental.api.ResultValue;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluationKt;
import kotlin.script.experimental.api.ScriptEvaluator;
import kotlin.script.experimental.impl.RunSuspendKt;
import kotlin.script.experimental.jvm.BasicJvmScriptEvaluator;
import kotlin.script.experimental.jvm.JvmScriptEvaluationConfigurationBuilder;
import kotlin.script.experimental.jvm.JvmScriptEvaluationKt;
import kotlin.script.experimental.jvm.impl.KJvmCompiledScript;
import kotlin.script.experimental.util.LinkedSnippetImpl;
import kotlin.script.experimental.util.PropertiesCollection;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.cli.common.repl.IReplStageHistory;
import org.jetbrains.kotlin.cli.common.repl.IReplStageState;
import org.jetbrains.kotlin.cli.common.repl.InvokeWrapper;
import org.jetbrains.kotlin.cli.common.repl.ReplCompileResult;
import org.jetbrains.kotlin.cli.common.repl.ReplEvalResult;
import org.jetbrains.kotlin.cli.common.repl.ReplEvaluator;
import org.jetbrains.kotlin.cli.common.repl.ReplHistoryRecord;
import org.jetbrains.kotlin.cli.common.repl.ScriptArgsWithTypes;

/* JADX INFO: compiled from: legacyReplEvaluation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0014\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J0\u0010\u000f\u001a\u00020\u00102\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\f2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0018"}, d2 = {"Lkotlin/script/experimental/jvmhost/repl/JvmReplEvaluator;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvaluator;", "baseScriptEvaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "scriptEvaluator", "Lkotlin/script/experimental/api/ScriptEvaluator;", "(Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;Lkotlin/script/experimental/api/ScriptEvaluator;)V", "getBaseScriptEvaluationConfiguration", "()Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "getScriptEvaluator", "()Lkotlin/script/experimental/api/ScriptEvaluator;", "createState", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "eval", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult;", "state", "compileResult", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult$CompiledClasses;", "scriptArgs", "Lorg/jetbrains/kotlin/cli/common/repl/ScriptArgsWithTypes;", "invokeWrapper", "Lorg/jetbrains/kotlin/cli/common/repl/InvokeWrapper;", "kotlin-scripting-jvm-host-unshaded"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmReplEvaluator implements ReplEvaluator {
    private final ScriptEvaluationConfiguration baseScriptEvaluationConfiguration;
    private final ScriptEvaluator scriptEvaluator;

    public JvmReplEvaluator(ScriptEvaluationConfiguration baseScriptEvaluationConfiguration, ScriptEvaluator scriptEvaluator) {
        Intrinsics.checkNotNullParameter(baseScriptEvaluationConfiguration, "baseScriptEvaluationConfiguration");
        Intrinsics.checkNotNullParameter(scriptEvaluator, "scriptEvaluator");
        this.baseScriptEvaluationConfiguration = baseScriptEvaluationConfiguration;
        this.scriptEvaluator = scriptEvaluator;
    }

    public final ScriptEvaluationConfiguration getBaseScriptEvaluationConfiguration() {
        return this.baseScriptEvaluationConfiguration;
    }

    public /* synthetic */ JvmReplEvaluator(ScriptEvaluationConfiguration scriptEvaluationConfiguration, BasicJvmScriptEvaluator basicJvmScriptEvaluator, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(scriptEvaluationConfiguration, (i & 2) != 0 ? new BasicJvmScriptEvaluator() : basicJvmScriptEvaluator);
    }

    public final ScriptEvaluator getScriptEvaluator() {
        return this.scriptEvaluator;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.CreateReplStageStateAction
    public IReplStageState<?> createState(ReentrantReadWriteLock lock) {
        Intrinsics.checkNotNullParameter(lock, "lock");
        return new JvmReplEvaluatorState(this.baseScriptEvaluationConfiguration, lock);
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.ReplEvalAction
    public ReplEvalResult eval(IReplStageState<?> state, ReplCompileResult.CompiledClasses compileResult, final ScriptArgsWithTypes scriptArgs, InvokeWrapper invokeWrapper) {
        Object next;
        ReplEvalResult.Error.Runtime runtime;
        Pair pair;
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(compileResult, "compileResult");
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
            IReplStageHistory<Pair<? extends KClass<?>, ? extends Object>> history = ((JvmReplEvaluatorState) state.asState(JvmReplEvaluatorState.class)).getHistory();
            Intrinsics.checkNotNull(history, "null cannot be cast to non-null type kotlin.script.experimental.jvmhost.repl.ReplStageHistoryWithReplace<kotlin.Pair<kotlin.reflect.KClass<*>?, kotlin.Any?>>");
            ReplStageHistoryWithReplace replStageHistoryWithReplace = (ReplStageHistoryWithReplace) history;
            Object data = compileResult.getData();
            Exception exc = null;
            LinkedSnippetImpl linkedSnippetImpl = data instanceof LinkedSnippetImpl ? (LinkedSnippetImpl) data : null;
            if (linkedSnippetImpl == null) {
                ReplEvalResult.Error.CompileTime compileTime = new ReplEvalResult.Error.CompileTime("Unable to access compiled list script: " + compileResult.getData(), null, 2, null);
                while (i < readHoldCount) {
                    lock2.lock();
                    i++;
                }
                writeLock.unlock();
                return compileTime;
            }
            Object obj = linkedSnippetImpl.get();
            KJvmCompiledScript kJvmCompiledScript = obj instanceof KJvmCompiledScript ? (KJvmCompiledScript) obj : null;
            if (kJvmCompiledScript == null) {
                ReplEvalResult.Error.CompileTime compileTime2 = new ReplEvalResult.Error.CompileTime("Unable to access compiled script: " + linkedSnippetImpl.get(), null, 2, null);
                while (i < readHoldCount) {
                    lock2.lock();
                    i++;
                }
                writeLock.unlock();
                return compileTime2;
            }
            ReplHistoryRecord<T> replHistoryRecordPeek = replStageHistoryWithReplace.peek();
            final KClass kClass = (replHistoryRecordPeek == 0 || (pair = (Pair) replHistoryRecordPeek.getItem()) == null) ? null : (KClass) pair.getFirst();
            final List list = SequencesKt.toList(SequencesKt.map(replStageHistoryWithReplace.previousItems(compileResult.getLineId()), new Function1<Pair<? extends KClass<?>, ? extends Object>, Object>() { // from class: kotlin.script.experimental.jvmhost.repl.JvmReplEvaluator$eval$1$historyBeforeSnippet$1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Pair<? extends KClass<?>, ? extends Object> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.getSecond();
                }
            }));
            ResultWithDiagnostics resultWithDiagnostics = (ResultWithDiagnostics) RunSuspendKt.internalScriptingRunSuspend(new JvmReplEvaluator$eval$1$res$1(this, kJvmCompiledScript, new ScriptEvaluationConfiguration(new ScriptEvaluationConfiguration[]{this.baseScriptEvaluationConfiguration}, new Function1<ScriptEvaluationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.jvmhost.repl.JvmReplEvaluator$eval$1$currentConfiguration$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ScriptEvaluationConfiguration.Builder builder) {
                    invoke2(builder);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ScriptEvaluationConfiguration.Builder $receiver) {
                    Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                    ScriptEvaluationConfiguration.Builder builder = $receiver;
                    $receiver.put(ScriptEvaluationKt.getPreviousSnippets(builder), list);
                    if (kClass != null) {
                        JvmScriptEvaluationConfigurationBuilder jvm = JvmScriptEvaluationKt.getJvm(builder);
                        final KClass<?> kClass2 = kClass;
                        $receiver.invoke(jvm, new Function1<JvmScriptEvaluationConfigurationBuilder, Unit>() { // from class: kotlin.script.experimental.jvmhost.repl.JvmReplEvaluator$eval$1$currentConfiguration$1.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(JvmScriptEvaluationConfigurationBuilder jvmScriptEvaluationConfigurationBuilder) {
                                invoke2(jvmScriptEvaluationConfigurationBuilder);
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(JvmScriptEvaluationConfigurationBuilder invoke) {
                                Intrinsics.checkNotNullParameter(invoke, "$this$invoke");
                                invoke.invoke(JvmScriptEvaluationKt.getBaseClassLoader(invoke), JvmClassMappingKt.getJavaClass((KClass) kClass2).getClassLoader());
                            }
                        });
                    }
                    if (scriptArgs != null) {
                        PropertiesCollection.Key<List<Object>> constructorArgs = ScriptEvaluationKt.getConstructorArgs(builder);
                        Object[] scriptArgs2 = scriptArgs.getScriptArgs();
                        $receiver.invoke((PropertiesCollection.Key) constructorArgs, Arrays.copyOf(scriptArgs2, scriptArgs2.length));
                    }
                }
            }), null));
            if (resultWithDiagnostics instanceof ResultWithDiagnostics.Success) {
                ResultValue returnValue = ((EvaluationResult) ((ResultWithDiagnostics.Success) resultWithDiagnostics).getValue()).getReturnValue();
                if (returnValue instanceof ResultValue.Error) {
                    replStageHistoryWithReplace.replaceOrPush(compileResult.getLineId(), TuplesKt.to(returnValue.getScriptClass(), null));
                    String message = ((ResultValue.Error) returnValue).getError().getMessage();
                    if (message == null) {
                        message = "unknown error";
                    }
                    Throwable error = ((ResultValue.Error) returnValue).getError();
                    Exception exc2 = error instanceof Exception ? (Exception) error : null;
                    if (exc2 == null) {
                        Throwable wrappingException = ((ResultValue.Error) returnValue).getWrappingException();
                        if (wrappingException instanceof Exception) {
                            exc = (Exception) wrappingException;
                        }
                    } else {
                        exc = exc2;
                    }
                    runtime = new ReplEvalResult.Error.Runtime(message, exc);
                } else if (returnValue instanceof ResultValue.Value) {
                    replStageHistoryWithReplace.replaceOrPush(compileResult.getLineId(), TuplesKt.to(returnValue.getScriptClass(), returnValue.getScriptInstance()));
                    runtime = new ReplEvalResult.ValueResult(((ResultValue.Value) returnValue).getName(), ((ResultValue.Value) returnValue).getValue(), ((ResultValue.Value) returnValue).getType(), null, 8, null);
                } else if (returnValue instanceof ResultValue.Unit) {
                    replStageHistoryWithReplace.replaceOrPush(compileResult.getLineId(), TuplesKt.to(returnValue.getScriptClass(), returnValue.getScriptInstance()));
                    runtime = new ReplEvalResult.UnitResult();
                } else {
                    throw new IllegalStateException("Unexpected snippet result value " + returnValue);
                }
            } else {
                String strJoinToString$default = CollectionsKt.joinToString$default(resultWithDiagnostics.getReports(), "\n", null, null, 0, null, new Function1<ScriptDiagnostic, CharSequence>() { // from class: kotlin.script.experimental.jvmhost.repl.JvmReplEvaluator$eval$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(ScriptDiagnostic it) {
                        String str;
                        Intrinsics.checkNotNullParameter(it, "it");
                        StringBuilder sbAppend = new StringBuilder().append(it.getMessage());
                        Throwable exception = it.getException();
                        if (exception == null || (str = ": " + exception) == null) {
                            str = "";
                        }
                        return sbAppend.append(str).toString();
                    }
                }, 30, null);
                Iterator<T> it = resultWithDiagnostics.getReports().iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (((ScriptDiagnostic) next).getException() == null);
                ScriptDiagnostic scriptDiagnostic = (ScriptDiagnostic) next;
                Throwable exception = scriptDiagnostic != null ? scriptDiagnostic.getException() : null;
                runtime = new ReplEvalResult.Error.Runtime(strJoinToString$default, exception instanceof Exception ? (Exception) exception : null);
            }
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
            return runtime;
        } catch (Throwable th) {
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
            throw th;
        }
    }
}
