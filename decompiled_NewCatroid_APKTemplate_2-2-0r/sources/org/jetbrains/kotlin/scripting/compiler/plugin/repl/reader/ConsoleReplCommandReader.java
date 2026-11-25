package org.jetbrains.kotlin.scripting.compiler.plugin.repl.reader;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.org.jline.reader.EndOfFileException;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.jetbrains.kotlin.org.jline.reader.LineReaderBuilder;
import org.jetbrains.kotlin.org.jline.reader.UserInterruptException;
import org.jetbrains.kotlin.org.jline.terminal.TerminalBuilder;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplFromTerminal;
import org.jetbrains.kotlin.utils.PathUtil;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: ConsoleReplCommandReader.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0012\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/reader/ConsoleReplCommandReader;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/reader/ReplCommandReader;", "()V", "lineReader", "Lorg/jetbrains/kotlin/org/jline/reader/LineReader;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "flushHistory", "", "readLine", "", ES6Iterator.NEXT_METHOD, "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplFromTerminal$WhatNextAfterOneLine;", "Companion", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConsoleReplCommandReader implements ReplCommandReader {
    private static final Companion Companion = new Companion(null);
    private final LineReader lineReader;

    public ConsoleReplCommandReader() {
        LineReader lineReaderBuild = LineReaderBuilder.builder().appName(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME).terminal(TerminalBuilder.terminal()).variable(LineReader.HISTORY_FILE, new File(new File(System.getProperty("user.home")), ".kotlinc_history").getAbsolutePath()).build();
        lineReaderBuild.setOpt(LineReader.Option.DISABLE_EVENT_EXPANSION);
        this.lineReader = lineReaderBuild;
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.reader.ReplCommandReader
    public String readLine(ReplFromTerminal.WhatNextAfterOneLine next) {
        Intrinsics.checkNotNullParameter(next, "next");
        try {
            return this.lineReader.readLine(next == ReplFromTerminal.WhatNextAfterOneLine.INCOMPLETE ? "... " : ">>> ");
        } catch (EndOfFileException unused) {
            return null;
        } catch (UserInterruptException unused2) {
            System.out.println((Object) "<interrupted>");
            System.out.flush();
            return "";
        }
    }

    @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.reader.ReplCommandReader
    public void flushHistory() throws IOException {
        this.lineReader.getHistory().save();
    }

    /* JADX INFO: compiled from: ConsoleReplCommandReader.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/reader/ConsoleReplCommandReader$Companion;", "", "()V", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        Logger.getLogger("org.jetbrains.kotlin.org.jline").setLevel(Level.OFF);
    }
}
