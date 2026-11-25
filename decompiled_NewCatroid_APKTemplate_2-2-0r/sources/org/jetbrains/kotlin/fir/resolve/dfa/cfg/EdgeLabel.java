package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import kotlin.Metadata;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: compiled from: ControlFlowGraph.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u0082\u0001\u0005\u0006\u0007\b\t\nø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeLabel;", "", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "", "getLabel", "()Ljava/lang/String;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopConditionEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/NormalPath;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/UncaughtExceptionPath;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface EdgeLabel {
    String getLabel();
}
