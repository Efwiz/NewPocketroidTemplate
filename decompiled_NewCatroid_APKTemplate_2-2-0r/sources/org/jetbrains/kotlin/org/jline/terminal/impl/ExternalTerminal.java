package org.jetbrains.kotlin.org.jline.terminal.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntConsumer;
import org.jetbrains.kotlin.org.jline.terminal.Cursor;
import org.jetbrains.kotlin.org.jline.terminal.Terminal;

/* JADX INFO: loaded from: classes4.dex */
public class ExternalTerminal extends LineDisciplineTerminal {
    protected final AtomicBoolean closed;
    protected final InputStream masterInput;
    protected final Thread pumpThread;

    public ExternalTerminal(String str, String str2, InputStream inputStream, OutputStream outputStream, String str3, Terminal.SignalHandler signalHandler) throws IOException {
        super(str, str2, outputStream, str3, signalHandler);
        this.closed = new AtomicBoolean();
        this.masterInput = inputStream;
        Thread thread = new Thread(new Runnable() { // from class: org.jetbrains.kotlin.org.jline.terminal.impl.ExternalTerminal$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.pump();
            }
        }, toString() + " input pump thread");
        this.pumpThread = thread;
        thread.start();
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.LineDisciplineTerminal, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed.compareAndSet(false, true)) {
            this.pumpThread.interrupt();
            super.close();
        }
    }

    public void pump() {
        while (true) {
            try {
                int i = this.masterInput.read();
                if (i < 0 || this.closed.get()) {
                    break;
                    break;
                }
                processInputByte((char) i);
            } catch (IOException unused) {
                break;
            }
        }
        try {
            close();
        } catch (Throwable unused2) {
        }
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.AbstractTerminal, org.jetbrains.kotlin.org.jline.terminal.Terminal
    public Cursor getCursorPosition(IntConsumer intConsumer) {
        return CursorSupport.getCursorPosition(this, intConsumer);
    }
}
