package kotlin.script.experimental.api;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.script.experimental.api.CompiledScript;
import kotlin.script.experimental.util.LinkedSnippet;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: replCompilation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000*\f\b\u0000\u0010\u0001*\u00060\u0002j\u0002`\u00032\u00020\u0004J0\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00060\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000fH¦@¢\u0006\u0002\u0010\u0010J*\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00060\n2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0096@¢\u0006\u0002\u0010\u0012R\u001a\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0013À\u0006\u0001"}, d2 = {"Lkotlin/script/experimental/api/ReplCompiler;", "CompiledSnippetT", "Lkotlin/script/experimental/api/CompiledScript;", "Lkotlin/script/experimental/api/CompiledSnippet;", "", "lastCompiledSnippet", "Lkotlin/script/experimental/util/LinkedSnippet;", "getLastCompiledSnippet", "()Lkotlin/script/experimental/util/LinkedSnippet;", "compile", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "snippets", "", "Lkotlin/script/experimental/api/SourceCode;", "configuration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "(Ljava/lang/Iterable;Lkotlin/script/experimental/api/ScriptCompilationConfiguration;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "snippet", "(Lkotlin/script/experimental/api/SourceCode;Lkotlin/script/experimental/api/ScriptCompilationConfiguration;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ReplCompiler<CompiledSnippetT extends CompiledScript> {
    Object compile(Iterable<? extends SourceCode> iterable, ScriptCompilationConfiguration scriptCompilationConfiguration, Continuation<? super ResultWithDiagnostics<? extends LinkedSnippet<CompiledSnippetT>>> continuation);

    default Object compile(SourceCode sourceCode, ScriptCompilationConfiguration scriptCompilationConfiguration, Continuation<? super ResultWithDiagnostics<? extends LinkedSnippet<CompiledSnippetT>>> continuation) {
        return compile$suspendImpl(this, sourceCode, scriptCompilationConfiguration, continuation);
    }

    LinkedSnippet<CompiledSnippetT> getLastCompiledSnippet();

    static /* synthetic */ <CompiledSnippetT extends CompiledScript> Object compile$suspendImpl(ReplCompiler<CompiledSnippetT> replCompiler, SourceCode sourceCode, ScriptCompilationConfiguration scriptCompilationConfiguration, Continuation<? super ResultWithDiagnostics<? extends LinkedSnippet<CompiledSnippetT>>> continuation) {
        return replCompiler.compile(CollectionsKt.listOf(sourceCode), scriptCompilationConfiguration, continuation);
    }
}
