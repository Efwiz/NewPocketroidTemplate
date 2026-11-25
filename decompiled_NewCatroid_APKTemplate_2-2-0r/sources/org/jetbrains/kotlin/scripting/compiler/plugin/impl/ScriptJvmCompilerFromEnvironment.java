package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

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
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.scripting.compiler.plugin.ScriptCompilerProxy;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptJvmCompilerImpls.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/ScriptJvmCompilerFromEnvironment;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/ScriptCompilerProxy;", "environment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "(Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;)V", "getEnvironment", "()Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "compile", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/api/CompiledScript;", "script", "Lkotlin/script/experimental/api/SourceCode;", "scriptCompilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptJvmCompilerFromEnvironment implements ScriptCompilerProxy {
    private final KotlinCoreEnvironment environment;

    public ScriptJvmCompilerFromEnvironment(KotlinCoreEnvironment environment) {
        Intrinsics.checkNotNullParameter(environment, "environment");
        this.environment = environment;
    }

    public final KotlinCoreEnvironment getEnvironment() {
        return this.environment;
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.ScriptCompilerProxy
    public ResultWithDiagnostics<CompiledScript> compile(final SourceCode script, final ScriptCompilationConfiguration scriptCompilationConfiguration) {
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "scriptCompilationConfiguration");
        final ScriptDiagnosticsMessageCollector scriptDiagnosticsMessageCollector = new ScriptDiagnosticsMessageCollector(null);
        try {
            return ScriptJvmCompilerImplsKt.withScriptCompilationCache(script, scriptCompilationConfiguration, scriptDiagnosticsMessageCollector, new Function0<ResultWithDiagnostics<? extends CompiledScript>>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ScriptJvmCompilerFromEnvironment$compile$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final ResultWithDiagnostics<? extends CompiledScript> invoke() {
                    ResultWithDiagnostics resultWithDiagnosticsRefineBeforeParsing$default = ScriptCompilationKt.refineBeforeParsing$default(scriptCompilationConfiguration, script, null, 2, null);
                    if (resultWithDiagnosticsRefineBeforeParsing$default instanceof ResultWithDiagnostics.Success) {
                        ScriptCompilationConfiguration scriptCompilationConfiguration2 = (ScriptCompilationConfiguration) ((ResultWithDiagnostics.Success) resultWithDiagnosticsRefineBeforeParsing$default).getValue();
                        SharedScriptCompilationContext sharedScriptCompilationContextCreateCompilationContextFromEnvironment = CompilationContextKt.createCompilationContextFromEnvironment(scriptCompilationConfiguration2, this.getEnvironment(), scriptDiagnosticsMessageCollector);
                        MessageCollector messageCollector = (MessageCollector) this.getEnvironment().getConfiguration().get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
                        try {
                            this.getEnvironment().getConfiguration().put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, scriptDiagnosticsMessageCollector);
                            return ScriptJvmCompilerImplsKt.compileImpl(script, sharedScriptCompilationContextCreateCompilationContextFromEnvironment, scriptCompilationConfiguration2, scriptDiagnosticsMessageCollector);
                        } finally {
                            if (messageCollector != null) {
                                this.getEnvironment().getConfiguration().put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, messageCollector);
                            }
                        }
                    }
                    if (resultWithDiagnosticsRefineBeforeParsing$default instanceof ResultWithDiagnostics.Failure) {
                        return (ResultWithDiagnostics.Failure) resultWithDiagnosticsRefineBeforeParsing$default;
                    }
                    throw new NoWhenBranchMatchedException();
                }
            });
        } catch (Throwable th) {
            return ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, ErrorHandlingKt.asDiagnostics$default(th, 0, null, script.getLocationId(), null, null, 27, null));
        }
    }
}
