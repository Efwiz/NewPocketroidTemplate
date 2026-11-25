package kotlin.script.experimental.api;

import kotlin.Metadata;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: replEvaluation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0016\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000fÀ\u0006\u0001"}, d2 = {"Lkotlin/script/experimental/api/EvaluatedSnippet;", "", "compiledSnippet", "Lkotlin/script/experimental/api/CompiledScript;", "Lkotlin/script/experimental/api/CompiledSnippet;", "getCompiledSnippet", "()Lkotlin/script/experimental/api/CompiledScript;", "configuration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "getConfiguration", "()Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "result", "Lkotlin/script/experimental/api/ResultValue;", "getResult", "()Lkotlin/script/experimental/api/ResultValue;", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface EvaluatedSnippet {
    CompiledScript getCompiledSnippet();

    ScriptEvaluationConfiguration getConfiguration();

    ResultValue getResult();
}
