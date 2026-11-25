package org.eclipse.jgit.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes14.dex */
public class UnionInputStream extends InputStream {
    private static final InputStream EOF = new InputStream() { // from class: org.eclipse.jgit.util.io.UnionInputStream.1
        @Override // java.io.InputStream
        public int read() throws IOException {
            return -1;
        }
    };
    private final LinkedList<InputStream> streams = new LinkedList<>();

    public UnionInputStream() {
    }

    public UnionInputStream(InputStream... inputStreamArr) {
        for (InputStream inputStream : inputStreamArr) {
            add(inputStream);
        }
    }

    private InputStream head() {
        return this.streams.isEmpty() ? EOF : this.streams.getFirst();
    }

    private void pop() throws IOException {
        if (this.streams.isEmpty()) {
            return;
        }
        this.streams.removeFirst().close();
    }

    public void add(InputStream inputStream) {
        this.streams.add(inputStream);
    }

    public boolean isEmpty() {
        return this.streams.isEmpty();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        while (true) {
            InputStream inputStreamHead = head();
            int i = inputStreamHead.read();
            if (i >= 0) {
                return i;
            }
            if (inputStreamHead == EOF) {
                return -1;
            }
            pop();
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (i2 == 0) {
            return 0;
        }
        while (true) {
            InputStream inputStreamHead = head();
            int i3 = inputStreamHead.read(bArr, i, i2);
            if (i3 > 0) {
                return i3;
            }
            if (inputStreamHead == EOF) {
                return -1;
            }
            pop();
        }
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return head().available();
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        long j2 = 0;
        while (0 < j) {
            InputStream inputStreamHead = head();
            long jSkip = inputStreamHead.skip(j);
            if (0 < jSkip) {
                j2 += jSkip;
                j -= jSkip;
            } else {
                if (inputStreamHead == EOF) {
                    return j2;
                }
                if (inputStreamHead.read() < 0) {
                    pop();
                    if (0 < j2) {
                        break;
                    }
                } else {
                    j2++;
                    j--;
                }
            }
        }
        return j2;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        Iterator<InputStream> it = this.streams.iterator();
        IOException e = null;
        while (it.hasNext()) {
            try {
                it.next().close();
            } catch (IOException e2) {
                e = e2;
            }
            it.remove();
        }
        if (e != null) {
            throw e;
        }
    }
}
