package org.jetbrains.kotlin.org.jline.terminal.impl;

import org.jetbrains.kotlin.org.jline.terminal.Terminal;

/* JADX INFO: loaded from: classes4.dex */
public final class NativeSignalHandler implements Terminal.SignalHandler {
    public static final NativeSignalHandler SIG_DFL = new NativeSignalHandler();
    public static final NativeSignalHandler SIG_IGN = new NativeSignalHandler();

    private NativeSignalHandler() {
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal.SignalHandler
    public void handle(Terminal.Signal signal) {
        throw new UnsupportedOperationException();
    }
}
