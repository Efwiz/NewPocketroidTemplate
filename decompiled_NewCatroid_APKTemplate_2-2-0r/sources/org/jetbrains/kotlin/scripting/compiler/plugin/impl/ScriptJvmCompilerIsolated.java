package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.CompiledScript;
import kotlin.script.experimental.api.ErrorHandlingKt;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import org.jetbrains.kotlin.cli.common.environment.UtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.scripting.compiler.plugin.ScriptCompilerProxy;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptJvmCompilerImpls.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/ScriptJvmCompilerIsolated;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/ScriptCompilerProxy;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "(Lkotlin/script/experimental/host/ScriptingHostConfiguration;)V", "getHostConfiguration", "()Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "compile", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/api/CompiledScript;", "script", "Lkotlin/script/experimental/api/SourceCode;", "scriptCompilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptJvmCompilerIsolated implements ScriptCompilerProxy {
    private final ScriptingHostConfiguration hostConfiguration;

    public ScriptJvmCompilerIsolated(ScriptingHostConfiguration hostConfiguration) {
        Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
        this.hostConfiguration = hostConfiguration;
    }

    public final ScriptingHostConfiguration getHostConfiguration() {
        return this.hostConfiguration;
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.ScriptCompilerProxy
    public ResultWithDiagnostics<CompiledScript> compile(final SourceCode script, final ScriptCompilationConfiguration scriptCompilationConfiguration) {
        ResultWithDiagnostics.Failure failure;
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "scriptCompilationConfiguration");
        final Disposable disposableNewDisposable = Disposer.newDisposable();
        Intrinsics.checkNotNullExpressionValue(disposableNewDisposable, "newDisposable(...)");
        final ScriptDiagnosticsMessageCollector scriptDiagnosticsMessageCollector = new ScriptDiagnosticsMessageCollector(null);
        try {
            UtilKt.setIdeaIoUseFallback();
            failure = ScriptJvmCompilerImplsKt.withScriptCompilationCache(script, scriptCompilationConfiguration, scriptDiagnosticsMessageCollector, new Function0<ResultWithDiagnostics<? extends CompiledScript>>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ScriptJvmCompilerIsolated$compile$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final ResultWithDiagnostics<? extends CompiledScript> invoke() throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
                    ResultWithDiagnostics resultWithDiagnosticsRefineBeforeParsing$default = ScriptCompilationKt.refineBeforeParsing$default(scriptCompilationConfiguration, script, null, 2, null);
                    if (resultWithDiagnosticsRefineBeforeParsing$default instanceof ResultWithDiagnostics.Success) {
                        ScriptCompilationConfiguration scriptCompilationConfiguration2 = (ScriptCompilationConfiguration) ((ResultWithDiagnostics.Success) resultWithDiagnosticsRefineBeforeParsing$default).getValue();
                        return ScriptJvmCompilerImplsKt.compileImpl(script, CompilationContextKt.createIsolatedCompilationContext(scriptCompilationConfiguration2, this.getHostConfiguration(), scriptDiagnosticsMessageCollector, disposableNewDisposable), scriptCompilationConfiguration2, scriptDiagnosticsMessageCollector);
                    }
                    if (resultWithDiagnosticsRefineBeforeParsing$default instanceof ResultWithDiagnostics.Failure) {
                        return (ResultWithDiagnostics.Failure) resultWithDiagnosticsRefineBeforeParsing$default;
                    }
                    throw new NoWhenBranchMatchedException();
                }
            });
            boolean z = failure instanceof ResultWithDiagnostics.Failure;
        } catch (Throwable th) {
            try {
                failure = ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, ErrorHandlingKt.asDiagnostics$default(th, 0, null, script.getLocationId(), null, null, 27, null));
            } finally {
                Disposer.dispose(disposableNewDisposable);
            }
        }
        return failure;
    }
}
