package org.jetbrains.kotlin.scripting.compiler.plugin.repl;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.script.experimental.api.ResultValue;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.jvm.KJvmEvaluatedSnippet;
import kotlin.script.experimental.jvm.util.RuntimeExceptionReportingKt;
import kotlin.script.experimental.util.LinkedSnippet;
import org.jetbrains.kotlin.cli.common.repl.ReplEvalResult;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: ReplInterpreter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplInterpreter$eval$evalRes$1", f = "ReplInterpreter.kt", i = {}, l = {173, 185}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class ReplInterpreter$eval$evalRes$1 extends SuspendLambda implements Function1<Continuation<? super ReplEvalResult>, Object> {
    final /* synthetic */ SourceCode $snippet;
    int label;
    final /* synthetic */ ReplInterpreter this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ReplInterpreter$eval$evalRes$1(ReplInterpreter replInterpreter, SourceCode sourceCode, Continuation<? super ReplInterpreter$eval$evalRes$1> continuation) {
        super(1, continuation);
        this.this$0 = replInterpreter;
        this.$snippet = sourceCode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Continuation<?> continuation) {
        return new ReplInterpreter$eval$evalRes$1(this.this$0, this.$snippet, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Continuation<? super ReplEvalResult> continuation) {
        return ((ReplInterpreter$eval$evalRes$1) create(continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code duplicated, block: B:33:0x00bd  */
    /* JADX WARN: Code duplicated, block: B:35:0x00d3  */
    /* JADX WARN: Code duplicated, block: B:36:0x00db  */
    /* JADX WARN: Code duplicated, block: B:38:0x00df  */
    /* JADX WARN: Code duplicated, block: B:39:0x00fb  */
    /* JADX WARN: Code duplicated, block: B:41:0x00ff  */
    /* JADX WARN: Code duplicated, block: B:42:0x010e  */
    /* JADX WARN: Code duplicated, block: B:43:0x0118  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object next;
        ResultWithDiagnostics resultWithDiagnostics;
        ResultValue result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            obj = this.this$0.compiler.compile(CollectionsKt.listOf(this.$snippet), this.this$0.compilationConfiguration, this);
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
                ResultKt.throwOnFailure(obj);
            }
            resultWithDiagnostics = (ResultWithDiagnostics) obj;
            if (resultWithDiagnostics instanceof ResultWithDiagnostics.Success) {
                ReplInterpreter.eval$reportToMessageCollector(resultWithDiagnostics, this.this$0, this.$snippet);
                return new ReplEvalResult.Error.Runtime("", null, 2, null);
            }
            result = ((KJvmEvaluatedSnippet) ((LinkedSnippet) ((ResultWithDiagnostics.Success) resultWithDiagnostics).getValue()).get()).getResult();
            if (result instanceof ResultValue.Unit) {
                return new ReplEvalResult.UnitResult();
            }
            if (result instanceof ResultValue.Value) {
                return result instanceof ResultValue.Error ? new ReplEvalResult.Error.Runtime(RuntimeExceptionReportingKt.renderError((ResultValue.Error) result), null, 2, null) : new ReplEvalResult.Error.Runtime("Error: snippet is not evaluated", null, 2, null);
            }
            ResultValue.Value value = (ResultValue.Value) result;
            return new ReplEvalResult.ValueResult(value.getName(), value.getValue(), value.getType(), result.getScriptInstance());
        }
        ResultWithDiagnostics resultWithDiagnostics2 = (ResultWithDiagnostics) obj;
        if (resultWithDiagnostics2 instanceof ResultWithDiagnostics.Failure) {
            Iterator<T> it = resultWithDiagnostics2.getReports().iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (((ScriptDiagnostic) next).getCode() != -3);
            ScriptDiagnostic scriptDiagnostic = (ScriptDiagnostic) next;
            if (scriptDiagnostic == null) {
                ReplInterpreter.eval$reportToMessageCollector(resultWithDiagnostics2, this.this$0, this.$snippet);
                return new ReplEvalResult.Error.CompileTime("", null, 2, null);
            }
            return new ReplEvalResult.Incomplete(scriptDiagnostic.getMessage());
        }
        if (!(resultWithDiagnostics2 instanceof ResultWithDiagnostics.Success)) {
            throw new NoWhenBranchMatchedException();
        }
        ReplInterpreter.eval$reportToMessageCollector(resultWithDiagnostics2, this.this$0, this.$snippet);
        this.label = 2;
        obj = this.this$0.evaluator.eval((LinkedSnippet) ((ResultWithDiagnostics.Success) resultWithDiagnostics2).getValue(), this.this$0.evaluationConfiguration, this);
        if (obj == coroutine_suspended) {
            return coroutine_suspended;
        }
        resultWithDiagnostics = (ResultWithDiagnostics) obj;
        if (resultWithDiagnostics instanceof ResultWithDiagnostics.Success) {
            ReplInterpreter.eval$reportToMessageCollector(resultWithDiagnostics, this.this$0, this.$snippet);
            return new ReplEvalResult.Error.Runtime("", null, 2, null);
        }
        result = ((KJvmEvaluatedSnippet) ((LinkedSnippet) ((ResultWithDiagnostics.Success) resultWithDiagnostics).getValue()).get()).getResult();
        if (result instanceof ResultValue.Unit) {
            return new ReplEvalResult.UnitResult();
        }
        if (result instanceof ResultValue.Value) {
            if (result instanceof ResultValue.Error) {
            }
        }
        ResultValue.Value value2 = (ResultValue.Value) result;
        return new ReplEvalResult.ValueResult(value2.getName(), value2.getValue(), value2.getType(), result.getScriptInstance());
    }
}
