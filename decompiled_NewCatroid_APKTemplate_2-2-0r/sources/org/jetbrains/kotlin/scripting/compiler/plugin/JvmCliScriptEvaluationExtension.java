package org.jetbrains.kotlin.scripting.compiler.plugin;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluator;
import kotlin.script.experimental.jvm.BasicJvmScriptEvaluator;
import kotlin.script.experimental.jvm.JvmScriptEvaluationConfigurationBuilder;
import kotlin.script.experimental.jvm.JvmScriptEvaluationKt;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.scripting.compiler.plugin.impl.ScriptJvmCompilerFromEnvironment;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: JvmCliScriptEvaluationExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004H\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0007\u001a\u00020\bH\u0016J\f\u0010\u0014\u001a\u00020\u0013*\u00020\u0015H\u0014¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/JvmCliScriptEvaluationExtension;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/AbstractScriptEvaluationExtension;", "()V", "createEnvironment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "projectEnvironment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment$ProjectEnvironment;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "createScriptCompiler", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/ScriptCompilerProxy;", "environment", "createScriptEvaluator", "Lkotlin/script/experimental/api/ScriptEvaluator;", "isAccepted", "", "arguments", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", "setupScriptConfiguration", "", "platformEvaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration$Builder;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmCliScriptEvaluationExtension extends AbstractScriptEvaluationExtension {
    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.AbstractScriptEvaluationExtension
    protected void platformEvaluationConfiguration(ScriptEvaluationConfiguration.Builder builder) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        builder.invoke(JvmScriptEvaluationKt.getJvm(builder), new Function1<JvmScriptEvaluationConfigurationBuilder, Unit>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.JvmCliScriptEvaluationExtension.platformEvaluationConfiguration.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(JvmScriptEvaluationConfigurationBuilder jvmScriptEvaluationConfigurationBuilder) {
                invoke2(jvmScriptEvaluationConfigurationBuilder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(JvmScriptEvaluationConfigurationBuilder invoke) {
                Intrinsics.checkNotNullParameter(invoke, "$this$invoke");
                invoke.invoke(JvmScriptEvaluationKt.getBaseClassLoader(invoke), JvmCliScriptEvaluationExtensionKt.getPlatformClassLoader());
            }
        });
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.AbstractScriptEvaluationExtension
    public void setupScriptConfiguration(CompilerConfiguration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        configuration.put(JVMConfigurationKeys.RETAIN_OUTPUT_IN_MEMORY, true);
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.AbstractScriptEvaluationExtension
    public KotlinCoreEnvironment createEnvironment(KotlinCoreEnvironment.ProjectEnvironment projectEnvironment, CompilerConfiguration configuration) {
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        return KotlinCoreEnvironment.INSTANCE.createForProduction(projectEnvironment, configuration, EnvironmentConfigFiles.JVM_CONFIG_FILES);
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.AbstractScriptEvaluationExtension
    public ScriptEvaluator createScriptEvaluator() {
        return new BasicJvmScriptEvaluator();
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.AbstractScriptEvaluationExtension
    public ScriptCompilerProxy createScriptCompiler(KotlinCoreEnvironment environment) {
        Intrinsics.checkNotNullParameter(environment, "environment");
        return new ScriptJvmCompilerFromEnvironment(environment);
    }

    @Override // org.jetbrains.kotlin.cli.common.extensions.ScriptEvaluationExtension
    public boolean isAccepted(CommonCompilerArguments arguments) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return (arguments instanceof K2JVMCompilerArguments) && (arguments.getScript() || ((K2JVMCompilerArguments) arguments).getExpression() != null);
    }
}
