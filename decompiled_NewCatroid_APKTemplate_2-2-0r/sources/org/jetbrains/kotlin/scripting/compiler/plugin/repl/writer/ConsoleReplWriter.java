package org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ConsoleReplWriter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016J\b\u0010\u0006\u001a\u00020\u0004H\u0016J\b\u0010\u0007\u001a\u00020\u0004H\u0016J\b\u0010\b\u001a\u00020\u0004H\u0016J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/writer/ConsoleReplWriter;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/writer/ReplWriter;", "()V", "notifyCommandSuccess", "", "notifyErrorsReported", "notifyIncomplete", "notifyReadLineEnd", "notifyReadLineStart", "outputCommandResult", "x", "", "outputCompileError", "outputRuntimeError", "printlnHelpMessage", "printlnWelcomeMessage", "sendInternalErrorReport", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConsoleReplWriter implements ReplWriter {
    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void notifyCommandSuccess() {
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void notifyErrorsReported() {
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void notifyIncomplete() {
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void notifyReadLineEnd() {
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void notifyReadLineStart() {
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void sendInternalErrorReport(String x) {
        Intrinsics.checkNotNullParameter(x, "x");
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void printlnWelcomeMessage(String x) {
        Intrinsics.checkNotNullParameter(x, "x");
        System.out.println((Object) x);
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void printlnHelpMessage(String x) {
        Intrinsics.checkNotNullParameter(x, "x");
        System.out.println((Object) x);
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void outputCompileError(String x) {
        Intrinsics.checkNotNullParameter(x, "x");
        System.out.println((Object) x);
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void outputCommandResult(String x) {
        Intrinsics.checkNotNullParameter(x, "x");
        System.out.println((Object) x);
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void outputRuntimeError(String x) {
        Intrinsics.checkNotNullParameter(x, "x");
        System.out.println((Object) x);
    }
}
