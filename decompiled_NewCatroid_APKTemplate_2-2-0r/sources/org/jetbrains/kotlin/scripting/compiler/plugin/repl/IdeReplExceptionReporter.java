package org.jetbrains.kotlin.scripting.compiler.plugin.repl;

import java.io.PrintWriter;
import java.io.StringWriter;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ReplExceptionReporter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/IdeReplExceptionReporter;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplExceptionReporter;", "replWriter", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/writer/ReplWriter;", "(Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/writer/ReplWriter;)V", "report", "", "e", "", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IdeReplExceptionReporter implements ReplExceptionReporter {
    private final ReplWriter replWriter;

    public IdeReplExceptionReporter(ReplWriter replWriter) {
        Intrinsics.checkNotNullParameter(replWriter, "replWriter");
        this.replWriter = replWriter;
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplExceptionReporter
    public void report(Throwable e) {
        Intrinsics.checkNotNullParameter(e, "e");
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        String string = stringWriter.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        if (string.length() == 0) {
            string = "Unknown error";
        }
        this.replWriter.sendInternalErrorReport(string);
    }
}
