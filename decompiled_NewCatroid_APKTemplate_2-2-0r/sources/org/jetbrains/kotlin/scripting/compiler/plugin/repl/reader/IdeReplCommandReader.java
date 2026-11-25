package org.jetbrains.kotlin.scripting.compiler.plugin.repl.reader;

import kotlin.Metadata;
import kotlin.io.ConsoleKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplFromTerminal;
import org.jetbrains.kotlin.utils.PathUtil;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: IdeReplCommandReader.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/reader/IdeReplCommandReader;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/reader/ReplCommandReader;", "()V", "flushHistory", "", "readLine", "", ES6Iterator.NEXT_METHOD, "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplFromTerminal$WhatNextAfterOneLine;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IdeReplCommandReader implements ReplCommandReader {
    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.reader.ReplCommandReader
    public void flushHistory() {
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.reader.ReplCommandReader
    public String readLine(ReplFromTerminal.WhatNextAfterOneLine next) {
        Intrinsics.checkNotNullParameter(next, "next");
        return ConsoleKt.readLine();
    }
}
