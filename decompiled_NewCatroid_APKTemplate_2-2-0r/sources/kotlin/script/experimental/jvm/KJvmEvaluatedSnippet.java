package kotlin.script.experimental.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.CompiledScript;
import kotlin.script.experimental.api.EvaluatedSnippet;
import kotlin.script.experimental.api.ResultValue;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: BasicJvmReplEvaluator.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B!\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0018\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lkotlin/script/experimental/jvm/KJvmEvaluatedSnippet;", "Lkotlin/script/experimental/api/EvaluatedSnippet;", "compiledSnippet", "Lkotlin/script/experimental/api/CompiledScript;", "Lkotlin/script/experimental/api/CompiledSnippet;", "configuration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "result", "Lkotlin/script/experimental/api/ResultValue;", "(Lkotlin/script/experimental/api/CompiledScript;Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;Lkotlin/script/experimental/api/ResultValue;)V", "getCompiledSnippet", "()Lkotlin/script/experimental/api/CompiledScript;", "getConfiguration", "()Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "getResult", "()Lkotlin/script/experimental/api/ResultValue;", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KJvmEvaluatedSnippet implements EvaluatedSnippet {
    private final CompiledScript compiledSnippet;
    private final ScriptEvaluationConfiguration configuration;
    private final ResultValue result;

    public KJvmEvaluatedSnippet(CompiledScript compiledSnippet, ScriptEvaluationConfiguration configuration, ResultValue result) {
        Intrinsics.checkNotNullParameter(compiledSnippet, "compiledSnippet");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(result, "result");
        this.compiledSnippet = compiledSnippet;
        this.configuration = configuration;
        this.result = result;
    }

    @Override // kotlin.script.experimental.api.EvaluatedSnippet
    public CompiledScript getCompiledSnippet() {
        return this.compiledSnippet;
    }

    @Override // kotlin.script.experimental.api.EvaluatedSnippet
    public ScriptEvaluationConfiguration getConfiguration() {
        return this.configuration;
    }

    @Override // kotlin.script.experimental.api.EvaluatedSnippet
    public ResultValue getResult() {
        return this.result;
    }
}
