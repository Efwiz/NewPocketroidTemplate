package org.jetbrains.kotlin.org.jline.terminal.impl;

import java.io.IOError;
import java.io.IOException;
import java.util.Objects;
import java.util.function.IntConsumer;
import org.jetbrains.kotlin.org.jline.terminal.Attributes;
import org.jetbrains.kotlin.org.jline.terminal.Cursor;
import org.jetbrains.kotlin.org.jline.terminal.Size;
import org.jetbrains.kotlin.org.jline.terminal.Terminal;
import org.jetbrains.kotlin.org.jline.terminal.spi.Pty;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractPosixTerminal extends AbstractTerminal {
    protected final Attributes originalAttributes;
    protected final Pty pty;

    public AbstractPosixTerminal(String str, String str2, Pty pty, Terminal.SignalHandler signalHandler) throws IOException {
        super(str, str2, signalHandler);
        Objects.requireNonNull(pty);
        this.pty = pty;
        this.originalAttributes = pty.getAttr();
    }

    public Pty getPty() {
        return this.pty;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public Attributes getAttributes() {
        try {
            return this.pty.getAttr();
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public void setAttributes(Attributes attributes) {
        try {
            this.pty.setAttr(attributes);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public Size getSize() {
        try {
            return this.pty.getSize();
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public void setSize(Size size) {
        try {
            this.pty.setSize(size);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.pty.setAttr(this.originalAttributes);
        this.pty.close();
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.AbstractTerminal, org.jetbrains.kotlin.org.jline.terminal.Terminal
    public Cursor getCursorPosition(IntConsumer intConsumer) {
        return CursorSupport.getCursorPosition(this, intConsumer);
    }
}
