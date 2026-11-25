package org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer;

import java.io.OutputStream;
import java.io.PrintStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.repl.ReplUtilKt;
import org.jetbrains.kotlin.utils.PathUtil;
import org.jetbrains.kotlin.utils.repl.ReplEscapeType;

/* JADX INFO: compiled from: IdeSystemOutWrapperReplWriter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\u0010\f\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0006H\u0016J\b\u0010\b\u001a\u00020\u0006H\u0016J\b\u0010\t\u001a\u00020\u0006H\u0016J\b\u0010\n\u001a\u00020\u0006H\u0016J\u0010\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0016J\u0012\u0010\u0010\u001a\u00020\u00062\b\u0010\f\u001a\u0004\u0018\u00010\u0011H\u0016J\u0010\u0010\u0010\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0012H\u0016J\u0010\u0010\u0010\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0013H\u0016J\u0010\u0010\u0010\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0014H\u0016J\u0010\u0010\u0010\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0015H\u0016J\u0010\u0010\u0010\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0016H\u0016J\u0010\u0010\u0010\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0017H\u0016J\u0010\u0010\u0010\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0016J\u001a\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\r2\b\b\u0002\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010\u001c\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u001d\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0016J\u001a\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\r2\b\b\u0002\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010\u001f\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0016¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/writer/IdeSystemOutWrapperReplWriter;", "Ljava/io/PrintStream;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/writer/ReplWriter;", "standardOut", "(Ljava/io/PrintStream;)V", "notifyCommandSuccess", "", "notifyErrorsReported", "notifyIncomplete", "notifyReadLineEnd", "notifyReadLineStart", "outputCommandResult", "x", "", "outputCompileError", "outputRuntimeError", "print", "", "", "", "", "", "", "", "printWithEscaping", "text", "escapeType", "Lorg/jetbrains/kotlin/utils/repl/ReplEscapeType;", "printlnHelpMessage", "printlnWelcomeMessage", "printlnWithEscaping", "sendInternalErrorReport", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IdeSystemOutWrapperReplWriter extends PrintStream implements ReplWriter {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IdeSystemOutWrapperReplWriter(PrintStream standardOut) {
        super((OutputStream) standardOut, true);
        Intrinsics.checkNotNullParameter(standardOut, "standardOut");
    }

    @Override // java.io.PrintStream
    public void print(boolean x) {
        printWithEscaping$default(this, String.valueOf(x), null, 2, null);
    }

    @Override // java.io.PrintStream
    public void print(char x) {
        printWithEscaping$default(this, String.valueOf(x), null, 2, null);
    }

    @Override // java.io.PrintStream
    public void print(int x) {
        printWithEscaping$default(this, String.valueOf(x), null, 2, null);
    }

    @Override // java.io.PrintStream
    public void print(long x) {
        printWithEscaping$default(this, String.valueOf(x), null, 2, null);
    }

    @Override // java.io.PrintStream
    public void print(float x) {
        printWithEscaping$default(this, String.valueOf(x), null, 2, null);
    }

    @Override // java.io.PrintStream
    public void print(double x) {
        printWithEscaping$default(this, String.valueOf(x), null, 2, null);
    }

    @Override // java.io.PrintStream
    public void print(String x) {
        Intrinsics.checkNotNullParameter(x, "x");
        printWithEscaping$default(this, x, null, 2, null);
    }

    @Override // java.io.PrintStream
    public void print(Object x) {
        printWithEscaping$default(this, String.valueOf(x), null, 2, null);
    }

    static /* synthetic */ void printlnWithEscaping$default(IdeSystemOutWrapperReplWriter ideSystemOutWrapperReplWriter, String str, ReplEscapeType replEscapeType, int i, Object obj) {
        if ((i & 2) != 0) {
            replEscapeType = ReplEscapeType.USER_OUTPUT;
        }
        ideSystemOutWrapperReplWriter.printlnWithEscaping(str, replEscapeType);
    }

    private final void printlnWithEscaping(String text, ReplEscapeType escapeType) {
        printWithEscaping(text + '\n', escapeType);
    }

    static /* synthetic */ void printWithEscaping$default(IdeSystemOutWrapperReplWriter ideSystemOutWrapperReplWriter, String str, ReplEscapeType replEscapeType, int i, Object obj) {
        if ((i & 2) != 0) {
            replEscapeType = ReplEscapeType.USER_OUTPUT;
        }
        ideSystemOutWrapperReplWriter.printWithEscaping(str, replEscapeType);
    }

    private final void printWithEscaping(String text, ReplEscapeType escapeType) {
        super.print(ReplUtilKt.replAddLineBreak(ReplUtilKt.replOutputAsXml(text, escapeType)));
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void printlnWelcomeMessage(String x) {
        Intrinsics.checkNotNullParameter(x, "x");
        printlnWithEscaping(x, ReplEscapeType.INITIAL_PROMPT);
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void printlnHelpMessage(String x) {
        Intrinsics.checkNotNullParameter(x, "x");
        printlnWithEscaping(x, ReplEscapeType.HELP_PROMPT);
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void outputCommandResult(String x) {
        Intrinsics.checkNotNullParameter(x, "x");
        printlnWithEscaping(x, ReplEscapeType.REPL_RESULT);
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void notifyReadLineStart() {
        printlnWithEscaping("", ReplEscapeType.READLINE_START);
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void notifyReadLineEnd() {
        printlnWithEscaping("", ReplEscapeType.READLINE_END);
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void notifyCommandSuccess() {
        printlnWithEscaping("", ReplEscapeType.SUCCESS);
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void notifyIncomplete() {
        printlnWithEscaping("", ReplEscapeType.REPL_INCOMPLETE);
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void outputCompileError(String x) {
        Intrinsics.checkNotNullParameter(x, "x");
        printlnWithEscaping(x, ReplEscapeType.COMPILE_ERROR);
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void outputRuntimeError(String x) {
        Intrinsics.checkNotNullParameter(x, "x");
        printlnWithEscaping(x, ReplEscapeType.RUNTIME_ERROR);
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void sendInternalErrorReport(String x) {
        Intrinsics.checkNotNullParameter(x, "x");
        printlnWithEscaping(x, ReplEscapeType.INTERNAL_ERROR);
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter
    public void notifyErrorsReported() {
        printlnWithEscaping("", ReplEscapeType.ERRORS_REPORTED);
    }
}
