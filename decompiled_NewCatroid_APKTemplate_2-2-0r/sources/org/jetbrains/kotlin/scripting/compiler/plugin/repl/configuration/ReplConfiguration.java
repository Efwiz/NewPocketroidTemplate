package org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration;

import kotlin.Metadata;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplExceptionReporter;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.messages.DiagnosticMessageHolder;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.reader.ReplCommandReader;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ReplConfiguration.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0016\u001a\u00020\u0017H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0012\u0010\u0012\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0018À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/configuration/ReplConfiguration;", "", "allowIncompleteLines", "", "getAllowIncompleteLines", "()Z", "commandReader", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/reader/ReplCommandReader;", "getCommandReader", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/reader/ReplCommandReader;", "exceptionReporter", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplExceptionReporter;", "getExceptionReporter", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplExceptionReporter;", "executionInterceptor", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/configuration/SnippetExecutionInterceptor;", "getExecutionInterceptor", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/configuration/SnippetExecutionInterceptor;", "writer", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/writer/ReplWriter;", "getWriter", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/writer/ReplWriter;", "createDiagnosticHolder", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/messages/DiagnosticMessageHolder;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ReplConfiguration {
    DiagnosticMessageHolder createDiagnosticHolder();

    boolean getAllowIncompleteLines();

    ReplCommandReader getCommandReader();

    ReplExceptionReporter getExceptionReporter();

    SnippetExecutionInterceptor getExecutionInterceptor();

    ReplWriter getWriter();
}
