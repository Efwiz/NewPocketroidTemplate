package org.jetbrains.kotlin.org.fusesource.jansi;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: loaded from: classes4.dex */
public class HtmlAnsiOutputStream extends AnsiOutputStream {
    private final List<String> closingAttributes;
    private boolean concealOn;
    private static final String[] ANSI_COLOR_MAP = {"black", "red", "green", "yellow", "blue", "magenta", "cyan", "white"};
    private static final byte[] BYTES_QUOT = "&quot;".getBytes();
    private static final byte[] BYTES_AMP = "&amp;".getBytes();
    private static final byte[] BYTES_LT = "&lt;".getBytes();
    private static final byte[] BYTES_GT = "&gt;".getBytes();

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream, java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        closeAttributes();
        super.close();
    }

    public HtmlAnsiOutputStream(OutputStream outputStream) {
        super(outputStream);
        this.concealOn = false;
        this.closingAttributes = new ArrayList();
    }

    private void write(String str) throws IOException {
        ((AnsiOutputStream) this).out.write(str.getBytes());
    }

    private void writeAttribute(String str) throws IOException {
        write("<" + str + ">");
        this.closingAttributes.add(0, str.split(" ", 2)[0]);
    }

    private void closeAttributes() throws IOException {
        Iterator<String> it = this.closingAttributes.iterator();
        while (it.hasNext()) {
            write("</" + it.next() + ">");
        }
        this.closingAttributes.clear();
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream, java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i) throws IOException {
        if (i == 34) {
            this.out.write(BYTES_QUOT);
            return;
        }
        if (i == 38) {
            this.out.write(BYTES_AMP);
            return;
        }
        if (i == 60) {
            this.out.write(BYTES_LT);
        } else if (i == 62) {
            this.out.write(BYTES_GT);
        } else {
            super.write(i);
        }
    }

    public void writeLine(byte[] bArr, int i, int i2) throws IOException {
        write(bArr, i, i2);
        closeAttributes();
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processSetAttribute(int i) throws IOException {
        if (i == 1) {
            writeAttribute(JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME);
            return;
        }
        if (i == 4) {
            writeAttribute("u");
            return;
        }
        if (i == 8) {
            write("\u001b[8m");
            this.concealOn = true;
        } else if (i == 22) {
            closeAttributes();
        } else {
            if (i != 24) {
                return;
            }
            closeAttributes();
        }
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processAttributeRest() throws IOException {
        if (this.concealOn) {
            write("\u001b[0m");
            this.concealOn = false;
        }
        closeAttributes();
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processSetForegroundColor(int i, boolean z) throws IOException {
        writeAttribute("span style=\"color: " + ANSI_COLOR_MAP[i] + ";\"");
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processSetBackgroundColor(int i, boolean z) throws IOException {
        writeAttribute("span style=\"background-color: " + ANSI_COLOR_MAP[i] + ";\"");
    }
}
