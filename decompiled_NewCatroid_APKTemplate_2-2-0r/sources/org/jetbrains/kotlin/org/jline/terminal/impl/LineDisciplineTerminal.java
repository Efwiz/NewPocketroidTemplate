package org.jetbrains.kotlin.org.jline.terminal.impl;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.util.Objects;
import org.jetbrains.kotlin.org.jline.terminal.Attributes;
import org.jetbrains.kotlin.org.jline.terminal.Size;
import org.jetbrains.kotlin.org.jline.terminal.Terminal;
import org.jetbrains.kotlin.org.jline.utils.InputStreamReader;
import org.jetbrains.kotlin.org.jline.utils.NonBlockingReader;

/* JADX INFO: loaded from: classes4.dex */
public class LineDisciplineTerminal extends AbstractTerminal {
    protected final Attributes attributes;
    protected final OutputStream masterOutput;
    protected final Size size;
    protected final InputStream slaveInput;
    protected final OutputStream slaveInputPipe;
    protected final OutputStream slaveOutput;
    protected final NonBlockingReader slaveReader;
    protected final PrintWriter slaveWriter;

    public LineDisciplineTerminal(String str, String str2, OutputStream outputStream, String str3, Terminal.SignalHandler signalHandler) throws IOException {
        super(str, str2, signalHandler);
        PipedInputStream pipedInputStream = new PipedInputStream(1024);
        this.slaveInputPipe = new PipedOutputStream(pipedInputStream);
        FilterInputStream filterInputStream = new FilterInputStream(pipedInputStream) { // from class: org.jetbrains.kotlin.org.jline.terminal.impl.LineDisciplineTerminal.1
        };
        this.slaveInput = filterInputStream;
        this.slaveReader = new NonBlockingReader(getName(), new InputStreamReader(filterInputStream, str3));
        FilteringOutputStream filteringOutputStream = new FilteringOutputStream();
        this.slaveOutput = filteringOutputStream;
        this.slaveWriter = new PrintWriter(new OutputStreamWriter(filteringOutputStream, str3));
        this.masterOutput = outputStream;
        this.attributes = ExecPty.doGetAttr("speed 9600 baud; 24 rows; 80 columns;\nlflags: icanon isig iexten echo echoe -echok echoke -echonl echoctl\n\t-echoprt -altwerase -noflsh -tostop -flusho pendin -nokerninfo\n\t-extproc\niflags: -istrip icrnl -inlcr -igncr ixon -ixoff ixany imaxbel iutf8\n\t-ignbrk brkint -inpck -ignpar -parmrk\noflags: opost onlcr -oxtabs -onocr -onlret\ncflags: cread cs8 -parenb -parodd hupcl -clocal -cstopb -crtscts -dsrflow\n\t-dtrflow -mdmbuf\ncchars: discard = ^O; dsusp = ^Y; eof = ^D; eol = <undef>;\n\teol2 = <undef>; erase = ^?; intr = ^C; kill = ^U; lnext = ^V;\n\tmin = 1; quit = ^\\; reprint = ^R; start = ^Q; status = ^T;\n\tstop = ^S; susp = ^Z; time = 0; werase = ^W;\n");
        this.size = new Size(160, 50);
        parseInfoCmp();
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public NonBlockingReader reader() {
        return this.slaveReader;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public PrintWriter writer() {
        return this.slaveWriter;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public Attributes getAttributes() {
        Attributes attributes = new Attributes();
        attributes.copy(this.attributes);
        return attributes;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public void setAttributes(Attributes attributes) {
        this.attributes.copy(attributes);
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public Size getSize() {
        Size size = new Size();
        size.copy(this.size);
        return size;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public void setSize(Size size) {
        this.size.copy(size);
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.AbstractTerminal
    public void raise(Terminal.Signal signal) {
        Objects.requireNonNull(signal);
        echoSignal(signal);
        super.raise(signal);
    }

    public void processInputByte(int i) throws IOException {
        if (this.attributes.getLocalFlag(Attributes.LocalFlag.ISIG)) {
            if (i == this.attributes.getControlChar(Attributes.ControlChar.VINTR)) {
                raise(Terminal.Signal.INT);
                return;
            }
            if (i == this.attributes.getControlChar(Attributes.ControlChar.VQUIT)) {
                raise(Terminal.Signal.QUIT);
                return;
            } else if (i == this.attributes.getControlChar(Attributes.ControlChar.VSUSP)) {
                raise(Terminal.Signal.TSTP);
                return;
            } else if (i == this.attributes.getControlChar(Attributes.ControlChar.VSTATUS)) {
                raise(Terminal.Signal.INFO);
            }
        }
        if (i == 13) {
            if (this.attributes.getInputFlag(Attributes.InputFlag.IGNCR)) {
                return;
            }
            if (this.attributes.getInputFlag(Attributes.InputFlag.ICRNL)) {
                i = 10;
            }
        } else if (i == 10 && this.attributes.getInputFlag(Attributes.InputFlag.INLCR)) {
            i = 13;
        }
        if (this.attributes.getLocalFlag(Attributes.LocalFlag.ECHO)) {
            processOutputByte(i);
            this.masterOutput.flush();
        }
        this.slaveInputPipe.write(i);
        this.slaveInputPipe.flush();
    }

    protected void processOutputByte(int i) throws IOException {
        if (this.attributes.getOutputFlag(Attributes.OutputFlag.OPOST) && i == 10 && this.attributes.getOutputFlag(Attributes.OutputFlag.ONLCR)) {
            this.masterOutput.write(13);
            this.masterOutput.write(10);
        } else {
            this.masterOutput.write(i);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            this.slaveReader.close();
            try {
                this.slaveInputPipe.close();
            } finally {
                this.slaveWriter.close();
            }
        } catch (Throwable th) {
            try {
                this.slaveInputPipe.close();
                throw th;
            } finally {
                this.slaveWriter.close();
            }
        }
    }

    private class FilteringOutputStream extends OutputStream {
        private FilteringOutputStream() {
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            LineDisciplineTerminal.this.processOutputByte(i);
            flush();
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            int i3;
            bArr.getClass();
            if (i < 0 || i > bArr.length || i2 < 0 || (i3 = i + i2) > bArr.length || i3 < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (i2 == 0) {
                return;
            }
            for (int i4 = 0; i4 < i2; i4++) {
                LineDisciplineTerminal.this.processOutputByte(bArr[i + i4]);
            }
            flush();
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() throws IOException {
            LineDisciplineTerminal.this.masterOutput.flush();
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            LineDisciplineTerminal.this.masterOutput.close();
        }
    }
}
