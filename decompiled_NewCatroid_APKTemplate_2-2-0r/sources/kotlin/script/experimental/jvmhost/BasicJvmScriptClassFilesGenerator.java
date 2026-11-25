package kotlin.script.experimental.jvmhost;

import java.io.File;
import java.util.Map;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.CompiledScript;
import kotlin.script.experimental.api.ErrorHandlingKt;
import kotlin.script.experimental.api.EvaluationResult;
import kotlin.script.experimental.api.ResultValue;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluator;
import kotlin.script.experimental.jvm.impl.KJvmCompiledModule;
import kotlin.script.experimental.jvm.impl.KJvmCompiledModuleInMemory;
import kotlin.script.experimental.jvm.impl.KJvmCompiledScript;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: jvmScriptSaving.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0096B¢\u0006\u0002\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lkotlin/script/experimental/jvmhost/BasicJvmScriptClassFilesGenerator;", "Lkotlin/script/experimental/api/ScriptEvaluator;", ModuleXmlParser.OUTPUT_DIR, "Ljava/io/File;", "(Ljava/io/File;)V", "getOutputDir", "()Ljava/io/File;", "invoke", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/api/EvaluationResult;", "compiledScript", "Lkotlin/script/experimental/api/CompiledScript;", "scriptEvaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "(Lkotlin/script/experimental/api/CompiledScript;Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-scripting-jvm-host-unshaded"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class BasicJvmScriptClassFilesGenerator implements ScriptEvaluator {
    private final File outputDir;

    @Override // kotlin.script.experimental.api.ScriptEvaluator
    public Object invoke(CompiledScript compiledScript, ScriptEvaluationConfiguration scriptEvaluationConfiguration, Continuation<? super ResultWithDiagnostics<EvaluationResult>> continuation) {
        return invoke$suspendImpl(this, compiledScript, scriptEvaluationConfiguration, continuation);
    }

    public BasicJvmScriptClassFilesGenerator(File outputDir) {
        Intrinsics.checkNotNullParameter(outputDir, "outputDir");
        this.outputDir = outputDir;
    }

    public final File getOutputDir() {
        return this.outputDir;
    }

    static /* synthetic */ Object invoke$suspendImpl(BasicJvmScriptClassFilesGenerator basicJvmScriptClassFilesGenerator, CompiledScript compiledScript, ScriptEvaluationConfiguration scriptEvaluationConfiguration, Continuation<? super ResultWithDiagnostics<EvaluationResult>> continuation) {
        try {
            if (!(compiledScript instanceof KJvmCompiledScript)) {
                return JvmScriptSavingKt.failure("Cannot generate classes: unsupported compiled script type " + compiledScript);
            }
            KJvmCompiledModule compiledModule = ((KJvmCompiledScript) compiledScript).getCompiledModule();
            KJvmCompiledModuleInMemory kJvmCompiledModuleInMemory = compiledModule instanceof KJvmCompiledModuleInMemory ? (KJvmCompiledModuleInMemory) compiledModule : null;
            if (kJvmCompiledModuleInMemory == null) {
                return JvmScriptSavingKt.failure("Cannot generate classes: unsupported module type " + ((KJvmCompiledScript) compiledScript).getCompiledModule());
            }
            for (Map.Entry<String, byte[]> entry : kJvmCompiledModuleInMemory.getCompilerOutputFiles().entrySet()) {
                String key = entry.getKey();
                byte[] value = entry.getValue();
                File file = new File(basicJvmScriptClassFilesGenerator.outputDir, key);
                if (!file.getParentFile().isDirectory()) {
                    file.getParentFile().mkdirs();
                }
                FilesKt.writeBytes(file, value);
            }
            return new ResultWithDiagnostics.Success(new EvaluationResult(ResultValue.NotEvaluated.INSTANCE, scriptEvaluationConfiguration), null, 2, null);
        } catch (Throwable th) {
            return new ResultWithDiagnostics.Failure(ErrorHandlingKt.asDiagnostics$default(th, 0, "Cannot generate script classes: " + th.getMessage(), compiledScript.getSourceLocationId(), null, null, 25, null));
        }
    }
}
