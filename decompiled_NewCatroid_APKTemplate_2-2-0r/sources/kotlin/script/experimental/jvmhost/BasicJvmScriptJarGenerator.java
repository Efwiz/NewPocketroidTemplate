package kotlin.script.experimental.jvmhost;

import java.io.File;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.CompiledScript;
import kotlin.script.experimental.api.ErrorHandlingKt;
import kotlin.script.experimental.api.EvaluationResult;
import kotlin.script.experimental.api.ResultValue;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluator;
import kotlin.script.experimental.jvm.impl.KJvmCompiledScript;

/* JADX INFO: compiled from: jvmScriptSaving.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0096B¢\u0006\u0002\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lkotlin/script/experimental/jvmhost/BasicJvmScriptJarGenerator;", "Lkotlin/script/experimental/api/ScriptEvaluator;", "outputJar", "Ljava/io/File;", "(Ljava/io/File;)V", "getOutputJar", "()Ljava/io/File;", "invoke", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/api/EvaluationResult;", "compiledScript", "Lkotlin/script/experimental/api/CompiledScript;", "scriptEvaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "(Lkotlin/script/experimental/api/CompiledScript;Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-scripting-jvm-host-unshaded"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class BasicJvmScriptJarGenerator implements ScriptEvaluator {
    private final File outputJar;

    @Override // kotlin.script.experimental.api.ScriptEvaluator
    public Object invoke(CompiledScript compiledScript, ScriptEvaluationConfiguration scriptEvaluationConfiguration, Continuation<? super ResultWithDiagnostics<EvaluationResult>> continuation) {
        return invoke$suspendImpl(this, compiledScript, scriptEvaluationConfiguration, continuation);
    }

    public BasicJvmScriptJarGenerator(File outputJar) {
        Intrinsics.checkNotNullParameter(outputJar, "outputJar");
        this.outputJar = outputJar;
    }

    public final File getOutputJar() {
        return this.outputJar;
    }

    static /* synthetic */ Object invoke$suspendImpl(BasicJvmScriptJarGenerator basicJvmScriptJarGenerator, CompiledScript compiledScript, ScriptEvaluationConfiguration scriptEvaluationConfiguration, Continuation<? super ResultWithDiagnostics<EvaluationResult>> continuation) {
        try {
            if (!(compiledScript instanceof KJvmCompiledScript)) {
                return JvmScriptSavingKt.failure("Cannot generate jar: unsupported compiled script type " + compiledScript);
            }
            JvmScriptSavingKt.saveToJar((KJvmCompiledScript) compiledScript, basicJvmScriptJarGenerator.outputJar);
            return new ResultWithDiagnostics.Success(new EvaluationResult(ResultValue.NotEvaluated.INSTANCE, scriptEvaluationConfiguration), null, 2, null);
        } catch (Throwable th) {
            return new ResultWithDiagnostics.Failure(ErrorHandlingKt.asDiagnostics$default(th, 0, "Cannot generate script jar: " + th.getMessage(), compiledScript.getSourceLocationId(), null, null, 25, null));
        }
    }
}
