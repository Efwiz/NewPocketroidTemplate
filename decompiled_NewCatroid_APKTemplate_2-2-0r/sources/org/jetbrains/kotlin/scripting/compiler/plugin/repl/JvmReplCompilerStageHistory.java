package org.jetbrains.kotlin.scripting.compiler.plugin.repl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.repl.BasicReplStageHistory;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.JvmReplCompilerState.Compilation;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: jvmReplCompilation.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u00020\u00040\u0003B\u0013\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/JvmReplCompilerStageHistory;", "CompilationT", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/JvmReplCompilerState$Compilation;", "Lorg/jetbrains/kotlin/cli/common/repl/BasicReplStageHistory;", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "state", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/JvmReplCompilerState;", "(Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/JvmReplCompilerState;)V", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmReplCompilerStageHistory<CompilationT extends JvmReplCompilerState.Compilation> extends BasicReplStageHistory<ScriptDescriptor> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmReplCompilerStageHistory(JvmReplCompilerState<CompilationT> state) {
        super(state.getLock());
        Intrinsics.checkNotNullParameter(state, "state");
    }
}
