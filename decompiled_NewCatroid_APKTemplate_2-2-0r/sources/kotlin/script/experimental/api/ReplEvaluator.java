package kotlin.script.experimental.api;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.script.experimental.api.CompiledScript;
import kotlin.script.experimental.api.EvaluatedSnippet;
import kotlin.script.experimental.util.LinkedSnippet;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: replEvaluation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000*\f\b\u0000\u0010\u0001*\u00060\u0002j\u0002`\u0003*\b\b\u0001\u0010\u0004*\u00020\u00052\u00020\u0006J2\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\b0\f2\u000e\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\b2\u0006\u0010\u000e\u001a\u00020\u000fH¦@¢\u0006\u0002\u0010\u0010R\u001a\u0010\u0007\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lkotlin/script/experimental/api/ReplEvaluator;", "CompiledSnippetT", "Lkotlin/script/experimental/api/CompiledScript;", "Lkotlin/script/experimental/api/CompiledSnippet;", "EvaluatedSnippetT", "Lkotlin/script/experimental/api/EvaluatedSnippet;", "", "lastEvaluatedSnippet", "Lkotlin/script/experimental/util/LinkedSnippet;", "getLastEvaluatedSnippet", "()Lkotlin/script/experimental/util/LinkedSnippet;", "eval", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "snippet", "configuration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "(Lkotlin/script/experimental/util/LinkedSnippet;Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ReplEvaluator<CompiledSnippetT extends CompiledScript, EvaluatedSnippetT extends EvaluatedSnippet> {
    Object eval(LinkedSnippet<? extends CompiledSnippetT> linkedSnippet, ScriptEvaluationConfiguration scriptEvaluationConfiguration, Continuation<? super ResultWithDiagnostics<? extends LinkedSnippet<EvaluatedSnippetT>>> continuation);

    LinkedSnippet<EvaluatedSnippetT> getLastEvaluatedSnippet();
}
