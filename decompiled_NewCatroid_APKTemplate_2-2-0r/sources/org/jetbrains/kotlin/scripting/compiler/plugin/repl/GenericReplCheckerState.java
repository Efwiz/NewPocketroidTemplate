package org.jetbrains.kotlin.scripting.compiler.plugin.repl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.repl.IReplStageState;
import org.jetbrains.kotlin.cli.common.repl.ReplCodeLine;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.messages.DiagnosticMessageHolder;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: GenericCompilerState.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\nB\u0005¢\u0006\u0002\u0010\u0003R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/GenericReplCheckerState;", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "()V", "lastLineState", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/GenericReplCheckerState$LineState;", "getLastLineState", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/GenericReplCheckerState$LineState;", "setLastLineState", "(Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/GenericReplCheckerState$LineState;)V", "LineState", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class GenericReplCheckerState implements IReplStageState<ScriptDescriptor> {
    private LineState lastLineState;

    /* JADX INFO: compiled from: GenericCompilerState.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/GenericReplCheckerState$LineState;", "", "codeLine", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "psiFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "errorHolder", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/messages/DiagnosticMessageHolder;", "(Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;Lorg/jetbrains/kotlin/psi/KtFile;Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/messages/DiagnosticMessageHolder;)V", "getCodeLine", "()Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "getErrorHolder", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/messages/DiagnosticMessageHolder;", "getPsiFile", "()Lorg/jetbrains/kotlin/psi/KtFile;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LineState {
        private final ReplCodeLine codeLine;
        private final DiagnosticMessageHolder errorHolder;
        private final KtFile psiFile;

        public LineState(ReplCodeLine codeLine, KtFile psiFile, DiagnosticMessageHolder errorHolder) {
            Intrinsics.checkNotNullParameter(codeLine, "codeLine");
            Intrinsics.checkNotNullParameter(psiFile, "psiFile");
            Intrinsics.checkNotNullParameter(errorHolder, "errorHolder");
            this.codeLine = codeLine;
            this.psiFile = psiFile;
            this.errorHolder = errorHolder;
        }

        public final ReplCodeLine getCodeLine() {
            return this.codeLine;
        }

        public final KtFile getPsiFile() {
            return this.psiFile;
        }

        public final DiagnosticMessageHolder getErrorHolder() {
            return this.errorHolder;
        }
    }

    public final LineState getLastLineState() {
        return this.lastLineState;
    }

    public final void setLastLineState(LineState lineState) {
        this.lastLineState = lineState;
    }
}
