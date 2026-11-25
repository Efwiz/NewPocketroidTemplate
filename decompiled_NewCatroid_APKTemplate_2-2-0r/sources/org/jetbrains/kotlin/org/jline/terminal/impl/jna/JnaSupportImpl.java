package org.jetbrains.kotlin.org.jline.terminal.impl.jna;

import java.io.IOException;
import org.jetbrains.kotlin.org.jline.terminal.Attributes;
import org.jetbrains.kotlin.org.jline.terminal.Size;
import org.jetbrains.kotlin.org.jline.terminal.Terminal;
import org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.JnaWinSysTerminal;
import org.jetbrains.kotlin.org.jline.terminal.spi.JnaSupport;
import org.jetbrains.kotlin.org.jline.terminal.spi.Pty;

/* JADX INFO: loaded from: classes4.dex */
public class JnaSupportImpl implements JnaSupport {
    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.JnaSupport
    public Pty current() throws IOException {
        return JnaNativePty.current();
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.JnaSupport
    public Pty open(Attributes attributes, Size size) throws IOException {
        return JnaNativePty.open(attributes, size);
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.JnaSupport
    public Terminal winSysTerminal(String str, boolean z, Terminal.SignalHandler signalHandler) throws IOException {
        return new JnaWinSysTerminal(str, z, signalHandler);
    }
}
