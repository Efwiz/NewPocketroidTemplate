package kotlin.script.experimental.jvmhost.repl;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.script.experimental.api.EvaluationResult;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.jvm.impl.KJvmCompiledScript;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: legacyReplEvaluation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/api/EvaluationResult;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "kotlin.script.experimental.jvmhost.repl.JvmReplEvaluator$eval$1$res$1", f = "legacyReplEvaluation.kt", i = {}, l = {62}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class JvmReplEvaluator$eval$1$res$1 extends SuspendLambda implements Function1<Continuation<? super ResultWithDiagnostics<? extends EvaluationResult>>, Object> {
    final /* synthetic */ KJvmCompiledScript $compiledScript;
    final /* synthetic */ ScriptEvaluationConfiguration $currentConfiguration;
    int label;
    final /* synthetic */ JvmReplEvaluator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    JvmReplEvaluator$eval$1$res$1(JvmReplEvaluator jvmReplEvaluator, KJvmCompiledScript kJvmCompiledScript, ScriptEvaluationConfiguration scriptEvaluationConfiguration, Continuation<? super JvmReplEvaluator$eval$1$res$1> continuation) {
        super(1, continuation);
        this.this$0 = jvmReplEvaluator;
        this.$compiledScript = kJvmCompiledScript;
        this.$currentConfiguration = scriptEvaluationConfiguration;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Continuation<?> continuation) {
        return new JvmReplEvaluator$eval$1$res$1(this.this$0, this.$compiledScript, this.$currentConfiguration, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Object invoke(Continuation<? super ResultWithDiagnostics<? extends EvaluationResult>> continuation) {
        return invoke2((Continuation<? super ResultWithDiagnostics<EvaluationResult>>) continuation);
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final Object invoke2(Continuation<? super ResultWithDiagnostics<EvaluationResult>> continuation) {
        return ((JvmReplEvaluator$eval$1$res$1) create(continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            obj = this.this$0.getScriptEvaluator().invoke(this.$compiledScript, this.$currentConfiguration, this);
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
