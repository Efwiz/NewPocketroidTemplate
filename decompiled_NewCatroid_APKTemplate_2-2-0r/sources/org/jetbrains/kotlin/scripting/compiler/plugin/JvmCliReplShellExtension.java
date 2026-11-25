package org.jetbrains.kotlin.scripting.compiler.plugin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.extensions.ShellExtension;
import org.jetbrains.kotlin.com.intellij.core.JavaCoreProjectEnvironment;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplFromTerminal;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: JvmCliReplShellExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J \u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/JvmCliReplShellExtension;", "Lorg/jetbrains/kotlin/cli/common/extensions/ShellExtension;", "()V", "isAccepted", "", "arguments", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", "run", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "projectEnvironment", "Lorg/jetbrains/kotlin/com/intellij/core/JavaCoreProjectEnvironment;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmCliReplShellExtension implements ShellExtension {
    @Override // org.jetbrains.kotlin.cli.common.extensions.ShellExtension
    public boolean isAccepted(CommonCompilerArguments arguments) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return true;
    }

    @Override // org.jetbrains.kotlin.cli.common.extensions.ShellExtension
    public ExitCode run(CommonCompilerArguments arguments, CompilerConfiguration configuration, JavaCoreProjectEnvironment projectEnvironment) throws Exception {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        ReplFromTerminal.INSTANCE.run(projectEnvironment, configuration);
        return ExitCode.OK;
    }
}
