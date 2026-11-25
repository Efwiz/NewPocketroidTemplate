package org.jetbrains.kotlin.scripting.compiler.plugin.repl.messages;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.messages.GroupingMessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageCollectorBasedReporter;
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer;
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ConsoleDiagnosticMessageHolder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\n\u001a\u00020\u000bH\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/messages/ConsoleDiagnosticMessageHolder;", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollectorBasedReporter;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/messages/DiagnosticMessageHolder;", "()V", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/GroupingMessageCollector;", "getMessageCollector", "()Lorg/jetbrains/kotlin/cli/common/messages/GroupingMessageCollector;", "outputStream", "Ljava/io/ByteArrayOutputStream;", "renderMessage", "", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConsoleDiagnosticMessageHolder implements MessageCollectorBasedReporter, DiagnosticMessageHolder {
    private final GroupingMessageCollector messageCollector;
    private final ByteArrayOutputStream outputStream;

    public ConsoleDiagnosticMessageHolder() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.outputStream = byteArrayOutputStream;
        this.messageCollector = new GroupingMessageCollector(new PrintingMessageCollector(new PrintStream(byteArrayOutputStream), MessageRenderer.WITHOUT_PATHS, false), false);
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollectorBasedReporter
    public GroupingMessageCollector getMessageCollector() {
        return this.messageCollector;
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.messages.DiagnosticMessageHolder
    public String renderMessage() throws UnsupportedEncodingException {
        getMessageCollector().flush();
        String string = this.outputStream.toString("UTF-8");
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
