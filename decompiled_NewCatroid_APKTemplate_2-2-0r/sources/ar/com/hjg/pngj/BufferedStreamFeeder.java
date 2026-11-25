package ar.com.hjg.pngj;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class BufferedStreamFeeder {
    private static final int DEFAULTSIZE = 8192;
    private byte[] buf;
    private boolean closeStream;
    private boolean eof;
    private boolean failIfNoFeed;
    private int offset;
    private int pendinglen;
    private InputStream stream;

    public BufferedStreamFeeder(InputStream inputStream) {
        this(inputStream, 8192);
    }

    public BufferedStreamFeeder(InputStream inputStream, int i) {
        this.eof = false;
        this.closeStream = true;
        this.failIfNoFeed = false;
        this.stream = inputStream;
        this.buf = new byte[i < 1 ? 8192 : i];
    }

    public InputStream getStream() {
        return this.stream;
    }

    public int feed(IBytesConsumer iBytesConsumer) {
        return feed(iBytesConsumer, -1);
    }

    public int feed(IBytesConsumer iBytesConsumer, int i) {
        int iConsume;
        if (this.pendinglen == 0) {
            refillBuffer();
        }
        if (i <= 0 || i >= this.pendinglen) {
            i = this.pendinglen;
        }
        if (i > 0) {
            iConsume = iBytesConsumer.consume(this.buf, this.offset, i);
            if (iConsume > 0) {
                this.offset += iConsume;
                this.pendinglen -= iConsume;
            }
        } else {
            iConsume = 0;
        }
        if (iConsume >= 1 || !this.failIfNoFeed) {
            return iConsume;
        }
        throw new PngjInputException("failed feed bytes");
    }

    public boolean feedFixed(IBytesConsumer iBytesConsumer, int i) {
        while (i > 0) {
            int iFeed = feed(iBytesConsumer, i);
            if (iFeed < 1) {
                return false;
            }
            i -= iFeed;
        }
        return true;
    }

    protected void refillBuffer() {
        if (this.pendinglen > 0 || this.eof) {
            return;
        }
        try {
            this.offset = 0;
            int i = this.stream.read(this.buf);
            this.pendinglen = i;
            if (i < 0) {
                close();
            }
        } catch (IOException e) {
            throw new PngjInputException(e);
        }
    }

    public boolean hasMoreToFeed() {
        if (this.eof) {
            return this.pendinglen > 0;
        }
        refillBuffer();
        return this.pendinglen > 0;
    }

    public void setCloseStream(boolean z) {
        this.closeStream = z;
    }

    public void close() {
        this.eof = true;
        this.buf = null;
        this.pendinglen = 0;
        this.offset = 0;
        InputStream inputStream = this.stream;
        if (inputStream != null && this.closeStream) {
            try {
                inputStream.close();
            } catch (Exception unused) {
            }
        }
        this.stream = null;
    }

    public void setInputStream(InputStream inputStream) {
        this.stream = inputStream;
        this.eof = false;
    }

    public boolean isEof() {
        return this.eof;
    }

    public void setFailIfNoFeed(boolean z) {
        this.failIfNoFeed = z;
    }
}
