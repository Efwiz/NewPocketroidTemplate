package kotlin.script.experimental.host;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.CompiledScript;
import kotlin.script.experimental.api.ErrorHandlingKt;
import kotlin.script.experimental.api.EvaluationResult;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompiler;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluator;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.impl.RunSuspendKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: BasicScriptingHost.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J(\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J1\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\u00152\u001c\u0010\u0016\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00150\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0017H\u0016¢\u0006\u0002\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u001a"}, d2 = {"Lkotlin/script/experimental/host/BasicScriptingHost;", "", "compiler", "Lkotlin/script/experimental/api/ScriptCompiler;", "evaluator", "Lkotlin/script/experimental/api/ScriptEvaluator;", "(Lkotlin/script/experimental/api/ScriptCompiler;Lkotlin/script/experimental/api/ScriptEvaluator;)V", "getCompiler", "()Lkotlin/script/experimental/api/ScriptCompiler;", "getEvaluator", "()Lkotlin/script/experimental/api/ScriptEvaluator;", "eval", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/api/EvaluationResult;", "script", "Lkotlin/script/experimental/api/SourceCode;", "compilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "evaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "runInCoroutineContext", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BasicScriptingHost {
    private final ScriptCompiler compiler;
    private final ScriptEvaluator evaluator;

    public BasicScriptingHost(ScriptCompiler compiler, ScriptEvaluator evaluator) {
        Intrinsics.checkNotNullParameter(compiler, "compiler");
        Intrinsics.checkNotNullParameter(evaluator, "evaluator");
        this.compiler = compiler;
        this.evaluator = evaluator;
    }

    public final ScriptCompiler getCompiler() {
        return this.compiler;
    }

    public final ScriptEvaluator getEvaluator() {
        return this.evaluator;
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: kotlin.script.experimental.host.BasicScriptingHost$runInCoroutineContext$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BasicScriptingHost.kt */
    @Metadata(d1 = {"\u0000\u0004\n\u0002\b\u0002\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, ExifInterface.GPS_DIRECTION_TRUE}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "kotlin.script.experimental.host.BasicScriptingHost$runInCoroutineContext$1", f = "BasicScriptingHost.kt", i = {}, l = {36}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C40481<T> extends SuspendLambda implements Function1<Continuation<? super T>, Object> {
        final /* synthetic */ Function1<Continuation<? super T>, Object> $block;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        C40481(Function1<? super Continuation<? super T>, ? extends Object> function1, Continuation<? super C40481> continuation) {
            super(1, continuation);
            this.$block = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Continuation<?> continuation) {
            return new C40481(this.$block, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Continuation<? super T> continuation) {
            return ((C40481) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Function1<Continuation<? super T>, Object> function1 = this.$block;
                this.label = 1;
                obj = function1.invoke(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }
    }

    public <T> T runInCoroutineContext(Function1<? super Continuation<? super T>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return (T) RunSuspendKt.internalScriptingRunSuspend(new C40481(block, null));
    }

    /* JADX INFO: renamed from: kotlin.script.experimental.host.BasicScriptingHost$eval$1, reason: invalid class name */
    /* JADX INFO: compiled from: BasicScriptingHost.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/api/EvaluationResult;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "kotlin.script.experimental.host.BasicScriptingHost$eval$1", f = "BasicScriptingHost.kt", i = {}, l = {47, 48}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function1<Continuation<? super ResultWithDiagnostics<? extends EvaluationResult>>, Object> {
        final /* synthetic */ ScriptCompilationConfiguration $compilationConfiguration;
        final /* synthetic */ ScriptEvaluationConfiguration $evaluationConfiguration;
        final /* synthetic */ SourceCode $script;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(SourceCode sourceCode, ScriptCompilationConfiguration scriptCompilationConfiguration, ScriptEvaluationConfiguration scriptEvaluationConfiguration, Continuation<? super AnonymousClass1> continuation) {
            super(1, continuation);
            this.$script = sourceCode;
            this.$compilationConfiguration = scriptCompilationConfiguration;
            this.$evaluationConfiguration = scriptEvaluationConfiguration;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Continuation<?> continuation) {
            return BasicScriptingHost.this.new AnonymousClass1(this.$script, this.$compilationConfiguration, this.$evaluationConfiguration, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(Continuation<? super ResultWithDiagnostics<? extends EvaluationResult>> continuation) {
            return invoke2((Continuation<? super ResultWithDiagnostics<EvaluationResult>>) continuation);
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(Continuation<? super ResultWithDiagnostics<EvaluationResult>> continuation) {
            return ((AnonymousClass1) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            List<ScriptDiagnostic> list;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = BasicScriptingHost.this.getCompiler().invoke(this.$script, this.$compilationConfiguration, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                } else {
                    if (i != 2) {
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    list = (List) this.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                return ErrorHandlingKt.plus(list, (ResultWithDiagnostics) obj);
            }
            ResultWithDiagnostics resultWithDiagnostics = (ResultWithDiagnostics) obj;
            BasicScriptingHost basicScriptingHost = BasicScriptingHost.this;
            ScriptEvaluationConfiguration.Default r3 = this.$evaluationConfiguration;
            if (resultWithDiagnostics instanceof ResultWithDiagnostics.Success) {
                List<ScriptDiagnostic> reports = resultWithDiagnostics.getReports();
                CompiledScript compiledScript = (CompiledScript) ((ResultWithDiagnostics.Success) resultWithDiagnostics).getValue();
                ScriptEvaluator evaluator = basicScriptingHost.getEvaluator();
                if (r3 == null) {
                    r3 = ScriptEvaluationConfiguration.Default.INSTANCE;
                }
                this.L$0 = reports;
                this.label = 2;
                obj = evaluator.invoke(compiledScript, r3, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
                list = reports;
                return ErrorHandlingKt.plus(list, (ResultWithDiagnostics) obj);
            }
            if (resultWithDiagnostics instanceof ResultWithDiagnostics.Failure) {
                return resultWithDiagnostics;
            }
            throw new NoWhenBranchMatchedException();
        }
    }

    public ResultWithDiagnostics<EvaluationResult> eval(SourceCode script, ScriptCompilationConfiguration compilationConfiguration, ScriptEvaluationConfiguration evaluationConfiguration) {
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(compilationConfiguration, "compilationConfiguration");
        return (ResultWithDiagnostics) runInCoroutineContext(new AnonymousClass1(script, compilationConfiguration, evaluationConfiguration, null));
    }
}
