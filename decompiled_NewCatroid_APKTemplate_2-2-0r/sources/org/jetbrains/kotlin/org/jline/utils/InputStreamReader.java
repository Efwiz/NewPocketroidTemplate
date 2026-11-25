package org.jetbrains.kotlin.org.jline.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.MalformedInputException;
import java.nio.charset.UnmappableCharacterException;

/* JADX INFO: loaded from: classes4.dex */
public class InputStreamReader extends Reader {
    ByteBuffer bytes;
    CharsetDecoder decoder;
    private boolean endOfInput;
    private InputStream in;
    char pending;

    public InputStreamReader(InputStream inputStream, String str) throws UnsupportedEncodingException {
        super(inputStream);
        this.endOfInput = false;
        this.bytes = ByteBuffer.allocate(4);
        this.pending = (char) 65535;
        str.getClass();
        this.in = inputStream;
        try {
            this.decoder = Charset.forName(str).newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
            this.bytes.limit(0);
        } catch (IllegalArgumentException e) {
            throw ((UnsupportedEncodingException) new UnsupportedEncodingException(str).initCause(e));
        }
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        synchronized (this.lock) {
            this.decoder = null;
            InputStream inputStream = this.in;
            if (inputStream != null) {
                inputStream.close();
                this.in = null;
            }
        }
    }

    @Override // java.io.Reader
    public int read() throws IOException {
        synchronized (this.lock) {
            if (!isOpen()) {
                throw new ClosedException("InputStreamReader is closed.");
            }
            char c = this.pending;
            if (c != 65535) {
                this.pending = (char) 65535;
                return c;
            }
            char[] cArr = new char[2];
            int i = read(cArr, 0, 2);
            if (i == 2) {
                this.pending = cArr[1];
            }
            if (i <= 0) {
                return -1;
            }
            return cArr[0];
        }
    }

    /* JADX WARN: Code duplicated, block: B:32:0x0075 A[Catch: all -> 0x00f0, TryCatch #0 {, blocks: (B:4:0x0003, B:7:0x000b, B:12:0x0014, B:14:0x0016, B:15:0x0024, B:19:0x002d, B:21:0x0035, B:30:0x0067, B:32:0x0075, B:34:0x0083, B:24:0x003c, B:26:0x0057, B:29:0x005d, B:36:0x0098, B:38:0x009c, B:40:0x00a0, B:41:0x00b2, B:43:0x00b8, B:45:0x00be, B:49:0x00cc, B:48:0x00c6, B:51:0x00ce, B:52:0x00d7, B:53:0x00d8, B:54:0x00e1, B:55:0x00e2, B:56:0x00e7, B:57:0x00e8, B:58:0x00ef), top: B:63:0x0003 }] */
    /* JADX WARN: Code duplicated, block: B:34:0x0083 A[Catch: all -> 0x00f0, TryCatch #0 {, blocks: (B:4:0x0003, B:7:0x000b, B:12:0x0014, B:14:0x0016, B:15:0x0024, B:19:0x002d, B:21:0x0035, B:30:0x0067, B:32:0x0075, B:34:0x0083, B:24:0x003c, B:26:0x0057, B:29:0x005d, B:36:0x0098, B:38:0x009c, B:40:0x00a0, B:41:0x00b2, B:43:0x00b8, B:45:0x00be, B:49:0x00cc, B:48:0x00c6, B:51:0x00ce, B:52:0x00d7, B:53:0x00d8, B:54:0x00e1, B:55:0x00e2, B:56:0x00e7, B:57:0x00e8, B:58:0x00ef), top: B:63:0x0003 }] */
    /* JADX WARN: Code duplicated, block: B:70:0x0098 A[EDGE_INSN: B:70:0x0098->B:36:0x0098 BREAK  A[LOOP:0: B:15:0x0024->B:35:0x0096], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:72:0x0096 A[SYNTHETIC] */
    @Override // java.io.Reader
    public int read(char[] cArr, int i, int i2) throws IOException {
        int iPosition;
        synchronized (this.lock) {
            if (!isOpen()) {
                throw new IOException("InputStreamReader is closed.");
            }
            if (i < 0 || i > cArr.length - i2 || i2 < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (i2 == 0) {
                return 0;
            }
            CharBuffer charBufferWrap = CharBuffer.wrap(cArr, i, i2);
            CoderResult coderResultDecode = CoderResult.UNDERFLOW;
            boolean z = !this.bytes.hasRemaining();
            while (true) {
                iPosition = -1;
                if (charBufferWrap.position() == i) {
                    if (z) {
                        try {
                            if (this.in.available() == 0 && charBufferWrap.position() > i) {
                                break;
                            }
                            int i3 = this.in.read(this.bytes.array(), this.bytes.arrayOffset() + this.bytes.limit(), 1);
                            if (i3 != -1) {
                                if (i3 != 0) {
                                    ByteBuffer byteBuffer = this.bytes;
                                    byteBuffer.limit(byteBuffer.limit() + i3);
                                    coderResultDecode = this.decoder.decode(this.bytes, charBufferWrap, false);
                                    if (coderResultDecode.isUnderflow()) {
                                        break;
                                    }
                                    if (this.bytes.limit() == this.bytes.capacity()) {
                                        this.bytes.compact();
                                        ByteBuffer byteBuffer2 = this.bytes;
                                        byteBuffer2.limit(byteBuffer2.position());
                                        this.bytes.position(0);
                                    }
                                    z = true;
                                } else {
                                    break;
                                }
                            } else {
                                this.endOfInput = true;
                                break;
                            }
                        } catch (IOException unused) {
                        }
                    } else {
                        coderResultDecode = this.decoder.decode(this.bytes, charBufferWrap, false);
                        if (coderResultDecode.isUnderflow()) {
                            break;
                            break;
                        }
                        if (this.bytes.limit() == this.bytes.capacity()) {
                            this.bytes.compact();
                            ByteBuffer byteBuffer3 = this.bytes;
                            byteBuffer3.limit(byteBuffer3.position());
                            this.bytes.position(0);
                        }
                        z = true;
                    }
                } else {
                    break;
                }
            }
            if (coderResultDecode == CoderResult.UNDERFLOW && this.endOfInput) {
                coderResultDecode = this.decoder.decode(this.bytes, charBufferWrap, true);
                this.decoder.flush(charBufferWrap);
                this.decoder.reset();
            }
            if (coderResultDecode.isMalformed()) {
                throw new MalformedInputException(coderResultDecode.length());
            }
            if (coderResultDecode.isUnmappable()) {
                throw new UnmappableCharacterException(coderResultDecode.length());
            }
            if (charBufferWrap.position() - i != 0) {
                iPosition = charBufferWrap.position() - i;
            }
            return iPosition;
        }
    }

    private boolean isOpen() {
        return this.in != null;
    }

    @Override // java.io.Reader
    public boolean ready() throws IOException {
        boolean z;
        synchronized (this.lock) {
            if (this.in == null) {
                throw new IOException("InputStreamReader is closed.");
            }
            try {
                z = this.bytes.hasRemaining() || this.in.available() > 0;
            } catch (IOException unused) {
                return false;
            }
        }
        return z;
    }
}
