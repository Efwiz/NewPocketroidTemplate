package org.jetbrains.kotlin.org.jline.terminal.spi;

import java.io.IOException;
import org.jetbrains.kotlin.org.jline.terminal.Attributes;
import org.jetbrains.kotlin.org.jline.terminal.Size;
import org.jetbrains.kotlin.org.jline.terminal.Terminal;

/* JADX INFO: loaded from: classes4.dex */
public interface JansiSupport {
    Pty current() throws IOException;

    Pty open(Attributes attributes, Size size) throws IOException;

    Terminal winSysTerminal(String str, boolean z, Terminal.SignalHandler signalHandler) throws IOException;
}
