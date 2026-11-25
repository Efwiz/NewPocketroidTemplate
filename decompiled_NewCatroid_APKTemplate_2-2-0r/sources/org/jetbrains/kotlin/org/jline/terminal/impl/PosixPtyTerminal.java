package org.jetbrains.kotlin.org.jline.terminal.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.kotlin.org.jline.terminal.Terminal;
import org.jetbrains.kotlin.org.jline.terminal.spi.Pty;
import org.jetbrains.kotlin.org.jline.utils.ClosedException;
import org.jetbrains.kotlin.org.jline.utils.InputStreamReader;
import org.jetbrains.kotlin.org.jline.utils.NonBlockingReader;

/* JADX INFO: loaded from: classes4.dex */
public class PosixPtyTerminal extends AbstractPosixTerminal {
    private final InputStreamReader innerReader;
    private final InputStreamWrapper input;
    private final Thread inputPumpThread;
    private final OutputStream output;
    private final Thread outputPumpThread;
    private final NonBlockingReader reader;
    private final PrintWriter writer;

    public PosixPtyTerminal(String str, String str2, Pty pty, InputStream inputStream, OutputStream outputStream, String str3, Terminal.SignalHandler signalHandler) throws IOException {
        super(str, str2, pty, signalHandler);
        Objects.requireNonNull(inputStream);
        Objects.requireNonNull(outputStream);
        InputStreamWrapper inputStreamWrapper = new InputStreamWrapper(pty.getSlaveInput());
        this.input = inputStreamWrapper;
        OutputStream slaveOutput = pty.getSlaveOutput();
        this.output = slaveOutput;
        InputStreamReader inputStreamReader = new InputStreamReader(inputStreamWrapper, str3);
        this.innerReader = inputStreamReader;
        this.reader = new NonBlockingReader(str, inputStreamReader);
        this.writer = new PrintWriter(new OutputStreamWriter(slaveOutput, str3));
        PumpThread pumpThread = new PumpThread(inputStream, getPty().getMasterOutput());
        this.inputPumpThread = pumpThread;
        PumpThread pumpThread2 = new PumpThread(getPty().getMasterInput(), outputStream);
        this.outputPumpThread = pumpThread2;
        parseInfoCmp();
        pumpThread.start();
        pumpThread2.start();
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public NonBlockingReader reader() {
        return this.reader;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public PrintWriter writer() {
        return this.writer;
    }

    private class InputStreamWrapper extends InputStream {
        private final AtomicBoolean closed = new AtomicBoolean();
        private final InputStream in;

        protected InputStreamWrapper(InputStream inputStream) {
            this.in = inputStream;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            if (this.closed.get()) {
                throw new ClosedException();
            }
            return this.in.read();
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.closed.set(true);
        }
    }

    private class PumpThread extends Thread {
        private final InputStream in;
        private final OutputStream out;

        public PumpThread(InputStream inputStream, OutputStream outputStream) {
            this.in = inputStream;
            this.out = outputStream;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    int i = this.in.read();
                    if (i < 0) {
                        PosixPtyTerminal.this.input.close();
                        return;
                    } else {
                        this.out.write(i);
                        this.out.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
}
