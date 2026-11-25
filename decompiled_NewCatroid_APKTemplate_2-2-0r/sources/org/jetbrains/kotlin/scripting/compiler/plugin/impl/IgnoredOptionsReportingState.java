package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: errorReporting.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/IgnoredOptionsReportingState;", "", "()V", "currentArguments", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;", "getCurrentArguments", "()Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;", "setCurrentArguments", "(Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;)V", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IgnoredOptionsReportingState {
    private K2JVMCompilerArguments currentArguments = new K2JVMCompilerArguments();

    public final K2JVMCompilerArguments getCurrentArguments() {
        return this.currentArguments;
    }

    public final void setCurrentArguments(K2JVMCompilerArguments k2JVMCompilerArguments) {
        Intrinsics.checkNotNullParameter(k2JVMCompilerArguments, "<set-?>");
        this.currentArguments = k2JVMCompilerArguments;
    }
}
