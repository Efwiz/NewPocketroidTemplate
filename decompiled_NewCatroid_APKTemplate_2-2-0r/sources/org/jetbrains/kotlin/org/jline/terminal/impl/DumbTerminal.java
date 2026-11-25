package org.jetbrains.kotlin.org.jline.terminal.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import org.jetbrains.kotlin.org.jline.terminal.Attributes;
import org.jetbrains.kotlin.org.jline.terminal.Size;
import org.jetbrains.kotlin.org.jline.terminal.Terminal;
import org.jetbrains.kotlin.org.jline.utils.NonBlockingReader;

/* JADX INFO: loaded from: classes4.dex */
public class DumbTerminal extends AbstractTerminal {
    private final Attributes attributes;
    private final InputStream input;
    private final OutputStream output;
    private final NonBlockingReader reader;
    private final Size size;
    private final PrintWriter writer;

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
    }

    public DumbTerminal(String str, String str2, final InputStream inputStream, OutputStream outputStream, String str3, Terminal.SignalHandler signalHandler) throws IOException {
        super(str, str2, signalHandler);
        InputStream inputStream2 = new InputStream() { // from class: org.jetbrains.kotlin.org.jline.terminal.impl.DumbTerminal.1
            @Override // java.io.InputStream
            public int read() throws IOException {
                while (true) {
                    int i = inputStream.read();
                    if (DumbTerminal.this.attributes.getLocalFlag(Attributes.LocalFlag.ISIG)) {
                        if (i != DumbTerminal.this.attributes.getControlChar(Attributes.ControlChar.VINTR)) {
                            if (i != DumbTerminal.this.attributes.getControlChar(Attributes.ControlChar.VQUIT)) {
                                if (i != DumbTerminal.this.attributes.getControlChar(Attributes.ControlChar.VSUSP)) {
                                    if (i == DumbTerminal.this.attributes.getControlChar(Attributes.ControlChar.VSTATUS)) {
                                        DumbTerminal.this.raise(Terminal.Signal.INFO);
                                    }
                                } else {
                                    DumbTerminal.this.raise(Terminal.Signal.TSTP);
                                }
                            } else {
                                DumbTerminal.this.raise(Terminal.Signal.QUIT);
                            }
                        } else {
                            DumbTerminal.this.raise(Terminal.Signal.INT);
                        }
                    }
                    if (i == 13) {
                        if (!DumbTerminal.this.attributes.getInputFlag(Attributes.InputFlag.IGNCR)) {
                            if (DumbTerminal.this.attributes.getInputFlag(Attributes.InputFlag.ICRNL)) {
                                return 10;
                            }
                            return i;
                        }
                    } else {
                        if (i == 10 && DumbTerminal.this.attributes.getInputFlag(Attributes.InputFlag.INLCR)) {
                            return 13;
                        }
                        return i;
                    }
                }
            }

            @Override // java.io.InputStream
            public int read(byte[] bArr, int i, int i2) throws IOException {
                bArr.getClass();
                if (i < 0 || i2 < 0 || i2 > bArr.length - i) {
                    throw new IndexOutOfBoundsException();
                }
                if (i2 == 0) {
                    return 0;
                }
                int i3 = read();
                if (i3 == -1) {
                    return -1;
                }
                bArr[i] = (byte) i3;
                return 1;
            }
        };
        this.input = inputStream2;
        this.output = outputStream;
        this.reader = new NonBlockingReader(getName(), new InputStreamReader(inputStream2, str3));
        this.writer = new PrintWriter(new OutputStreamWriter(outputStream, str3));
        Attributes attributes = new Attributes();
        this.attributes = attributes;
        attributes.setControlChar(Attributes.ControlChar.VERASE, 127);
        attributes.setControlChar(Attributes.ControlChar.VWERASE, 23);
        attributes.setControlChar(Attributes.ControlChar.VKILL, 21);
        attributes.setControlChar(Attributes.ControlChar.VLNEXT, 22);
        this.size = new Size();
        parseInfoCmp();
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public NonBlockingReader reader() {
        return this.reader;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public PrintWriter writer() {
        return this.writer;
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
}
