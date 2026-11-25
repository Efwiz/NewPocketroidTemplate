package kotlin.script.experimental.jvmhost;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.CompiledScript;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.jvm.CompiledJvmScriptsCache;
import kotlin.script.experimental.jvm.impl.KJvmCompiledScript;

/* JADX INFO: compiled from: CompiledScriptJarsCache.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B!\u0012\u001a\u0010\u0002\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0003¢\u0006\u0002\u0010\u0007J\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u0005H\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u0005H\u0016R%\u0010\u0002\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0011"}, d2 = {"Lkotlin/script/experimental/jvmhost/CompiledScriptJarsCache;", "Lkotlin/script/experimental/jvm/CompiledJvmScriptsCache;", "scriptToFile", "Lkotlin/Function2;", "Lkotlin/script/experimental/api/SourceCode;", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "Ljava/io/File;", "(Lkotlin/jvm/functions/Function2;)V", "getScriptToFile", "()Lkotlin/jvm/functions/Function2;", "get", "Lkotlin/script/experimental/api/CompiledScript;", "script", "scriptCompilationConfiguration", "store", "", "compiledScript", "kotlin-scripting-jvm-host-unshaded"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class CompiledScriptJarsCache implements CompiledJvmScriptsCache {
    private final Function2<SourceCode, ScriptCompilationConfiguration, File> scriptToFile;

    /* JADX WARN: Multi-variable type inference failed */
    public CompiledScriptJarsCache(Function2<? super SourceCode, ? super ScriptCompilationConfiguration, ? extends File> scriptToFile) {
        Intrinsics.checkNotNullParameter(scriptToFile, "scriptToFile");
        this.scriptToFile = scriptToFile;
    }

    public final Function2<SourceCode, ScriptCompilationConfiguration, File> getScriptToFile() {
        return this.scriptToFile;
    }

    @Override // kotlin.script.experimental.jvm.CompiledJvmScriptsCache
    public CompiledScript get(SourceCode script, ScriptCompilationConfiguration scriptCompilationConfiguration) {
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "scriptCompilationConfiguration");
        File fileInvoke = this.scriptToFile.invoke(script, scriptCompilationConfiguration);
        if (fileInvoke == null) {
            throw new IllegalArgumentException("Unable to find a mapping to a file for the script " + script);
        }
        if (!fileInvoke.exists()) {
            return null;
        }
        CompiledScript compiledScriptLoadScriptFromJar$default = JvmScriptSavingKt.loadScriptFromJar$default(fileInvoke, false, 1, null);
        if (compiledScriptLoadScriptFromJar$default != null) {
            return compiledScriptLoadScriptFromJar$default;
        }
        fileInvoke.delete();
        return null;
    }

    @Override // kotlin.script.experimental.jvm.CompiledJvmScriptsCache
    public void store(CompiledScript compiledScript, SourceCode script, ScriptCompilationConfiguration scriptCompilationConfiguration) {
        Intrinsics.checkNotNullParameter(compiledScript, "compiledScript");
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "scriptCompilationConfiguration");
        File fileInvoke = this.scriptToFile.invoke(script, scriptCompilationConfiguration);
        if (fileInvoke == null) {
            throw new IllegalArgumentException("Unable to find a mapping to a file for the script " + script);
        }
        KJvmCompiledScript kJvmCompiledScript = compiledScript instanceof KJvmCompiledScript ? (KJvmCompiledScript) compiledScript : null;
        if (kJvmCompiledScript == null) {
            throw new IllegalArgumentException("Unsupported script type " + compiledScript.getClass().getName());
        }
        JvmScriptSavingKt.saveToJar(kJvmCompiledScript, fileInvoke);
    }
}
