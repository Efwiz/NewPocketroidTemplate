package kotlin.script.experimental.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.CompiledScript;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.SourceCode;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: jvmScriptCaching.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0001\u000bJ\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lkotlin/script/experimental/jvm/CompiledJvmScriptsCache;", "", "get", "Lkotlin/script/experimental/api/CompiledScript;", "script", "Lkotlin/script/experimental/api/SourceCode;", "scriptCompilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "store", "", "compiledScript", "NoCache", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CompiledJvmScriptsCache {
    CompiledScript get(SourceCode script, ScriptCompilationConfiguration scriptCompilationConfiguration);

    void store(CompiledScript compiledScript, SourceCode script, ScriptCompilationConfiguration scriptCompilationConfiguration);

    /* JADX INFO: compiled from: jvmScriptCaching.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\f"}, d2 = {"Lkotlin/script/experimental/jvm/CompiledJvmScriptsCache$NoCache;", "Lkotlin/script/experimental/jvm/CompiledJvmScriptsCache;", "()V", "get", "Lkotlin/script/experimental/api/CompiledScript;", "script", "Lkotlin/script/experimental/api/SourceCode;", "scriptCompilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "store", "", "compiledScript", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NoCache implements CompiledJvmScriptsCache {
        public static final NoCache INSTANCE = new NoCache();

        @Override // kotlin.script.experimental.jvm.CompiledJvmScriptsCache
        public CompiledScript get(SourceCode script, ScriptCompilationConfiguration scriptCompilationConfiguration) {
            Intrinsics.checkNotNullParameter(script, "script");
            Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "scriptCompilationConfiguration");
            return null;
        }

        @Override // kotlin.script.experimental.jvm.CompiledJvmScriptsCache
        public void store(CompiledScript compiledScript, SourceCode script, ScriptCompilationConfiguration scriptCompilationConfiguration) {
            Intrinsics.checkNotNullParameter(compiledScript, "compiledScript");
            Intrinsics.checkNotNullParameter(script, "script");
            Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "scriptCompilationConfiguration");
        }

        private NoCache() {
        }
    }
}
