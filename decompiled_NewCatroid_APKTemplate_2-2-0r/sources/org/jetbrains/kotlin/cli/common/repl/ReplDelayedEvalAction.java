package org.jetbrains.kotlin.cli.common.repl;

import kotlin.Metadata;
import kotlin.Pair;

/* JADX INFO: compiled from: ReplApi.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J6\u0010\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00032\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u00072\u0006\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ReplDelayedEvalAction;", "", "compileToEvaluable", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult;", "Lorg/jetbrains/kotlin/cli/common/repl/Evaluable;", "state", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "codeLine", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "defaultScriptArgs", "Lorg/jetbrains/kotlin/cli/common/repl/ScriptArgsWithTypes;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ReplDelayedEvalAction {
    Pair<ReplCompileResult, Evaluable> compileToEvaluable(IReplStageState<?> state, ReplCodeLine codeLine, ScriptArgsWithTypes defaultScriptArgs);

    static /* synthetic */ Pair compileToEvaluable$default(ReplDelayedEvalAction replDelayedEvalAction, IReplStageState iReplStageState, ReplCodeLine replCodeLine, ScriptArgsWithTypes scriptArgsWithTypes, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: compileToEvaluable");
        }
        if ((i & 4) != 0) {
            scriptArgsWithTypes = null;
        }
        return replDelayedEvalAction.compileToEvaluable(iReplStageState, replCodeLine, scriptArgsWithTypes);
    }
}
