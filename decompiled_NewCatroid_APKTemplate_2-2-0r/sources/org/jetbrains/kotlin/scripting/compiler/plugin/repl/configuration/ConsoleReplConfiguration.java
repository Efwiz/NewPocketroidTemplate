package org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration;

import kotlin.Metadata;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplExceptionReporter;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.messages.ConsoleDiagnosticMessageHolder;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.reader.ConsoleReplCommandReader;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ConsoleReplWriter;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ConsoleReplConfiguration.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/configuration/ConsoleReplConfiguration;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/configuration/ReplConfiguration;", "()V", "allowIncompleteLines", "", "getAllowIncompleteLines", "()Z", "commandReader", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/reader/ConsoleReplCommandReader;", "getCommandReader", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/reader/ConsoleReplCommandReader;", "exceptionReporter", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplExceptionReporter$DoNothing;", "getExceptionReporter", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplExceptionReporter$DoNothing;", "executionInterceptor", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/configuration/SnippetExecutionInterceptor$Plain;", "getExecutionInterceptor", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/configuration/SnippetExecutionInterceptor$Plain;", "writer", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/writer/ConsoleReplWriter;", "getWriter", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/writer/ConsoleReplWriter;", "createDiagnosticHolder", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/messages/ConsoleDiagnosticMessageHolder;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConsoleReplConfiguration implements ReplConfiguration {
    private final ConsoleReplWriter writer = new ConsoleReplWriter();
    private final ConsoleReplCommandReader commandReader = new ConsoleReplCommandReader();

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration
    public boolean getAllowIncompleteLines() {
        return true;
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration
    public ConsoleReplWriter getWriter() {
        return this.writer;
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration
    public ReplExceptionReporter.Companion getExceptionReporter() {
        return ReplExceptionReporter.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration
    public ConsoleReplCommandReader getCommandReader() {
        return this.commandReader;
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration
    public SnippetExecutionInterceptor.Companion getExecutionInterceptor() {
        return SnippetExecutionInterceptor.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration
    public ConsoleDiagnosticMessageHolder createDiagnosticHolder() {
        return new ConsoleDiagnosticMessageHolder();
    }
}
