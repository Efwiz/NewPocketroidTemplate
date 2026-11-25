package org.jetbrains.kotlin.scripting.compiler.plugin.repl.messages;

import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.messages.DiagnosticMessageReporter;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: DiagnosticMessageHolder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0004À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/messages/DiagnosticMessageHolder;", "Lorg/jetbrains/kotlin/cli/common/messages/DiagnosticMessageReporter;", "renderMessage", "", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DiagnosticMessageHolder extends DiagnosticMessageReporter {
    String renderMessage();
}
