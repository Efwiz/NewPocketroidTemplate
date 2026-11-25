package org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration;

import java.io.InputStream;
import java.io.PrintStream;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.IdeReplExceptionReporter;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplExceptionReporter;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.messages.IdeDiagnosticMessageHolder;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.reader.IdeReplCommandReader;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.reader.ReplCommandReader;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.reader.ReplSystemInWrapper;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.IdeSystemOutWrapperReplWriter;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: IdeReplConfiguration.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u0018X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/configuration/IdeReplConfiguration;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/configuration/ReplConfiguration;", "()V", "allowIncompleteLines", "", "getAllowIncompleteLines", "()Z", "commandReader", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/reader/ReplCommandReader;", "getCommandReader", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/reader/ReplCommandReader;", "exceptionReporter", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplExceptionReporter;", "getExceptionReporter", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplExceptionReporter;", "executionInterceptor", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/configuration/SnippetExecutionInterceptor;", "getExecutionInterceptor", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/configuration/SnippetExecutionInterceptor;", "sinWrapper", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/reader/ReplSystemInWrapper;", "getSinWrapper", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/reader/ReplSystemInWrapper;", "writer", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/writer/ReplWriter;", "getWriter", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/writer/ReplWriter;", "createDiagnosticHolder", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/messages/IdeDiagnosticMessageHolder;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IdeReplConfiguration implements ReplConfiguration {
    private final ReplCommandReader commandReader;
    private final ReplExceptionReporter exceptionReporter;
    private final SnippetExecutionInterceptor executionInterceptor = new SnippetExecutionInterceptor() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.IdeReplConfiguration$executionInterceptor$1
        @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.SnippetExecutionInterceptor
        public <T> T execute(Function0<? extends T> block) {
            Intrinsics.checkNotNullParameter(block, "block");
            try {
                this.this$0.getSinWrapper().setReplScriptExecuting(true);
                return block.invoke();
            } finally {
                this.this$0.getSinWrapper().setReplScriptExecuting(false);
            }
        }
    };
    private final ReplSystemInWrapper sinWrapper;
    private final ReplWriter writer;

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration
    public boolean getAllowIncompleteLines() {
        return false;
    }

    public IdeReplConfiguration() {
        PrintStream out = System.out;
        Intrinsics.checkNotNullExpressionValue(out, "out");
        IdeSystemOutWrapperReplWriter ideSystemOutWrapperReplWriter = new IdeSystemOutWrapperReplWriter(out);
        System.setOut(ideSystemOutWrapperReplWriter);
        InputStream in = System.in;
        Intrinsics.checkNotNullExpressionValue(in, "in");
        IdeSystemOutWrapperReplWriter ideSystemOutWrapperReplWriter2 = ideSystemOutWrapperReplWriter;
        ReplSystemInWrapper replSystemInWrapper = new ReplSystemInWrapper(in, ideSystemOutWrapperReplWriter2);
        this.sinWrapper = replSystemInWrapper;
        System.setIn(replSystemInWrapper);
        this.writer = ideSystemOutWrapperReplWriter2;
        this.exceptionReporter = new IdeReplExceptionReporter(getWriter());
        this.commandReader = new IdeReplCommandReader();
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration
    public SnippetExecutionInterceptor getExecutionInterceptor() {
        return this.executionInterceptor;
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration
    public IdeDiagnosticMessageHolder createDiagnosticHolder() {
        return new IdeDiagnosticMessageHolder();
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration
    public ReplWriter getWriter() {
        return this.writer;
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration
    public ReplExceptionReporter getExceptionReporter() {
        return this.exceptionReporter;
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration
    public ReplCommandReader getCommandReader() {
        return this.commandReader;
    }

    public final ReplSystemInWrapper getSinWrapper() {
        return this.sinWrapper;
    }
}
