package org.antlr.runtime;

import java.io.IOException;
import java.io.Reader;

/* JADX INFO: loaded from: classes12.dex */
public class ANTLRReaderStream extends ANTLRStringStream {
    public static final int INITIAL_BUFFER_SIZE = 1024;
    public static final int READ_BUFFER_SIZE = 1024;

    public ANTLRReaderStream() {
    }

    public ANTLRReaderStream(Reader reader) throws IOException {
        this(reader, 1024, 1024);
    }

    public ANTLRReaderStream(Reader reader, int i) throws IOException {
        this(reader, i, 1024);
    }

    public ANTLRReaderStream(Reader reader, int i, int i2) throws IOException {
        load(reader, i, i2);
    }

    public void load(Reader reader, int i, int i2) throws IOException {
        int i3;
        if (reader == null) {
            return;
        }
        if (i <= 0) {
            i = 1024;
        }
        if (i2 <= 0) {
            i2 = 1024;
        }
        try {
            this.data = new char[i];
            int i4 = 0;
            do {
                if (i4 + i2 > this.data.length) {
                    char[] cArr = new char[this.data.length * 2];
                    System.arraycopy(this.data, 0, cArr, 0, this.data.length);
                    this.data = cArr;
                }
                i3 = reader.read(this.data, i4, i2);
                i4 += i3;
            } while (i3 != -1);
            this.n = i4 + 1;
        } finally {
            reader.close();
        }
    }
}
