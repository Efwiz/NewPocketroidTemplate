package org.jetbrains.kotlin.org.jline.terminal.spi;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.jetbrains.kotlin.org.jline.terminal.Attributes;
import org.jetbrains.kotlin.org.jline.terminal.Size;

/* JADX INFO: loaded from: classes4.dex */
public interface Pty extends Closeable {
    Attributes getAttr() throws IOException;

    InputStream getMasterInput() throws IOException;

    OutputStream getMasterOutput() throws IOException;

    Size getSize() throws IOException;

    InputStream getSlaveInput() throws IOException;

    OutputStream getSlaveOutput() throws IOException;

    void setAttr(Attributes attributes) throws IOException;

    void setSize(Size size) throws IOException;
}
