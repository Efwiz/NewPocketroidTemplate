package kotlin.script.experimental.api;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptEvaluation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J&\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH¦B¢\u0006\u0002\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lkotlin/script/experimental/api/ScriptEvaluator;", "", "invoke", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/api/EvaluationResult;", "compiledScript", "Lkotlin/script/experimental/api/CompiledScript;", "scriptEvaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "(Lkotlin/script/experimental/api/CompiledScript;Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ScriptEvaluator {
    Object invoke(CompiledScript compiledScript, ScriptEvaluationConfiguration scriptEvaluationConfiguration, Continuation<? super ResultWithDiagnostics<EvaluationResult>> continuation);

    static /* synthetic */ Object invoke$default(ScriptEvaluator scriptEvaluator, CompiledScript compiledScript, ScriptEvaluationConfiguration scriptEvaluationConfiguration, Continuation continuation, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: invoke");
        }
        if ((i & 2) != 0) {
            scriptEvaluationConfiguration = ScriptEvaluationConfiguration.Default.INSTANCE;
        }
        return scriptEvaluator.invoke(compiledScript, scriptEvaluationConfiguration, continuation);
    }
}
