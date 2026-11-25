package org.jetbrains.kotlin.org.jline.terminal.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.kotlin.org.jline.terminal.Terminal;
import org.jetbrains.kotlin.org.jline.terminal.spi.Pty;
import org.jetbrains.kotlin.org.jline.utils.InputStreamReader;
import org.jetbrains.kotlin.org.jline.utils.NonBlockingReader;
import org.jetbrains.kotlin.org.jline.utils.ShutdownHooks;
import org.jetbrains.kotlin.org.jline.utils.Signals;

/* JADX INFO: loaded from: classes4.dex */
public class PosixSysTerminal extends AbstractPosixTerminal {
    protected final ShutdownHooks.Task closer;
    protected final InputStream input;
    protected final Map<Terminal.Signal, Object> nativeHandlers;
    protected final OutputStream output;
    protected final NonBlockingReader reader;
    protected final PrintWriter writer;

    public PosixSysTerminal(String str, String str2, Pty pty, String str3, boolean z, Terminal.SignalHandler signalHandler) throws IOException {
        super(str, str2, pty, signalHandler);
        this.nativeHandlers = new HashMap();
        Objects.requireNonNull(str3);
        InputStream slaveInput = pty.getSlaveInput();
        this.input = slaveInput;
        OutputStream slaveOutput = pty.getSlaveOutput();
        this.output = slaveOutput;
        this.reader = new NonBlockingReader(getName(), new InputStreamReader(slaveInput, str3));
        this.writer = new PrintWriter(new OutputStreamWriter(slaveOutput, str3));
        parseInfoCmp();
        if (z) {
            for (final Terminal.Signal signal : Terminal.Signal.values()) {
                if (signalHandler == Terminal.SignalHandler.SIG_DFL) {
                    this.nativeHandlers.put(signal, Signals.registerDefault(signal.name()));
                } else {
                    this.nativeHandlers.put(signal, Signals.register(signal.name(), new Runnable() { // from class: org.jetbrains.kotlin.org.jline.terminal.impl.PosixSysTerminal$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m7387x36358631(signal);
                        }
                    }));
                }
            }
        }
        ShutdownHooks.Task task = new ShutdownHooks.Task() { // from class: org.jetbrains.kotlin.org.jline.terminal.impl.PosixSysTerminal$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.org.jline.utils.ShutdownHooks.Task
            public final void run() throws IOException {
                this.f$0.close();
            }
        };
        this.closer = task;
        ShutdownHooks.add(task);
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-org-jline-terminal-impl-PosixSysTerminal, reason: not valid java name */
    /* synthetic */ void m7387x36358631(Terminal.Signal signal) {
        raise(signal);
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.AbstractTerminal, org.jetbrains.kotlin.org.jline.terminal.Terminal
    public Terminal.SignalHandler handle(final Terminal.Signal signal, Terminal.SignalHandler signalHandler) {
        Terminal.SignalHandler signalHandlerHandle = super.handle(signal, signalHandler);
        if (signalHandlerHandle != signalHandler) {
            if (signalHandler == Terminal.SignalHandler.SIG_DFL) {
                Signals.registerDefault(signal.name());
            } else {
                Signals.register(signal.name(), new Runnable() { // from class: org.jetbrains.kotlin.org.jline.terminal.impl.PosixSysTerminal$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m7386xaf0accd4(signal);
                    }
                });
            }
        }
        return signalHandlerHandle;
    }

    /* JADX INFO: renamed from: lambda$handle$1$org-jetbrains-kotlin-org-jline-terminal-impl-PosixSysTerminal, reason: not valid java name */
    /* synthetic */ void m7386xaf0accd4(Terminal.Signal signal) {
        raise(signal);
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public NonBlockingReader reader() {
        return this.reader;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public PrintWriter writer() {
        return this.writer;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.AbstractPosixTerminal, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        ShutdownHooks.remove(this.closer);
        for (Map.Entry<Terminal.Signal, Object> entry : this.nativeHandlers.entrySet()) {
            Signals.unregister(entry.getKey().name(), entry.getValue());
        }
        super.close();
        this.reader.shutdown();
    }
}
