package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptJvmCompilerImpls.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"org/jetbrains/kotlin/scripting/compiler/plugin/impl/ScriptJvmCompilerImplsKt$doCompileWithK2$orderedRawFir$CycleDetected", "", "node", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "(Lorg/jetbrains/kotlin/fir/declarations/FirFile;)V", "getNode", "()Lorg/jetbrains/kotlin/fir/declarations/FirFile;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptJvmCompilerImplsKt$doCompileWithK2$orderedRawFir$CycleDetected extends Throwable {
    private final FirFile node;

    public ScriptJvmCompilerImplsKt$doCompileWithK2$orderedRawFir$CycleDetected(FirFile node) {
        Intrinsics.checkNotNullParameter(node, "node");
        this.node = node;
    }

    public final FirFile getNode() {
        return this.node;
    }
}
