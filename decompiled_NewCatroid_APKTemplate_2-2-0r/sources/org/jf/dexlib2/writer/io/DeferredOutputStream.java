package org.jf.dexlib2.writer.io;

import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes6.dex */
public abstract class DeferredOutputStream extends OutputStream {
    public abstract void writeTo(OutputStream outputStream) throws IOException;
}
