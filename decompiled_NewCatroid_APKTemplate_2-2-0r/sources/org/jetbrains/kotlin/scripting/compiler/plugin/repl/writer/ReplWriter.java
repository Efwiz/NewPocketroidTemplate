package org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer;

import kotlin.Metadata;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ReplWriter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0003H&J\b\u0010\u0006\u001a\u00020\u0003H&J\b\u0010\u0007\u001a\u00020\u0003H&J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH&J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH&J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH&J\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH&J\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH&J\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/writer/ReplWriter;", "", "notifyCommandSuccess", "", "notifyErrorsReported", "notifyIncomplete", "notifyReadLineEnd", "notifyReadLineStart", "outputCommandResult", "x", "", "outputCompileError", "outputRuntimeError", "printlnHelpMessage", "printlnWelcomeMessage", "sendInternalErrorReport", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ReplWriter {
    void notifyCommandSuccess();

    void notifyErrorsReported();

    void notifyIncomplete();

    void notifyReadLineEnd();

    void notifyReadLineStart();

    void outputCommandResult(String x);

    void outputCompileError(String x);

    void outputRuntimeError(String x);

    void printlnHelpMessage(String x);

    void printlnWelcomeMessage(String x);

    void sendInternalErrorReport(String x);
}
