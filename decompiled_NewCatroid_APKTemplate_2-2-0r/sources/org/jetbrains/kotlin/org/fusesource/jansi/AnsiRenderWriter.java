package org.jetbrains.kotlin.org.fusesource.jansi;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class AnsiRenderWriter extends PrintWriter {
    public AnsiRenderWriter(OutputStream outputStream) {
        super(outputStream);
    }

    public AnsiRenderWriter(OutputStream outputStream, boolean z) {
        super(outputStream, z);
    }

    public AnsiRenderWriter(Writer writer) {
        super(writer);
    }

    public AnsiRenderWriter(Writer writer, boolean z) {
        super(writer, z);
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(String str) {
        if (AnsiRenderer.test(str)) {
            super.write(AnsiRenderer.render(str));
        } else {
            super.write(str);
        }
    }

    @Override // java.io.PrintWriter
    public PrintWriter format(String str, Object... objArr) {
        print(String.format(str, objArr));
        return this;
    }

    @Override // java.io.PrintWriter
    public PrintWriter format(Locale locale, String str, Object... objArr) {
        print(String.format(locale, str, objArr));
        return this;
    }
}
