package org.jetbrains.kotlin.cli.common.repl;

import kotlin.Metadata;

/* JADX INFO: compiled from: ReplApi.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J4\u0010\u0002\u001a\u00020\u00032\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ReplAtomicEvalAction;", "", "compileAndEval", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult;", "state", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "codeLine", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "scriptArgs", "Lorg/jetbrains/kotlin/cli/common/repl/ScriptArgsWithTypes;", "invokeWrapper", "Lorg/jetbrains/kotlin/cli/common/repl/InvokeWrapper;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ReplAtomicEvalAction {
    ReplEvalResult compileAndEval(IReplStageState<?> state, ReplCodeLine codeLine, ScriptArgsWithTypes scriptArgs, InvokeWrapper invokeWrapper);

    static /* synthetic */ ReplEvalResult compileAndEval$default(ReplAtomicEvalAction replAtomicEvalAction, IReplStageState iReplStageState, ReplCodeLine replCodeLine, ScriptArgsWithTypes scriptArgsWithTypes, InvokeWrapper invokeWrapper, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: compileAndEval");
        }
        if ((i & 4) != 0) {
            scriptArgsWithTypes = null;
        }
        if ((i & 8) != 0) {
            invokeWrapper = null;
        }
        return replAtomicEvalAction.compileAndEval(iReplStageState, replCodeLine, scriptArgsWithTypes, invokeWrapper);
    }
}
