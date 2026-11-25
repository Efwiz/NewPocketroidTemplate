package org.jetbrains.kotlin.scripting.compiler.plugin.repl.reader;

import kotlin.Metadata;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplFromTerminal;
import org.jetbrains.kotlin.utils.PathUtil;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: ReplCommandReader.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/reader/ReplCommandReader;", "", "flushHistory", "", "readLine", "", ES6Iterator.NEXT_METHOD, "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplFromTerminal$WhatNextAfterOneLine;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ReplCommandReader {
    void flushHistory();

    String readLine(ReplFromTerminal.WhatNextAfterOneLine next);
}
