package com.koushikdutta.async.http.spdy;

import com.koushikdutta.async.BufferedDataSink;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataEmitterReader;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.Protocol;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX INFO: loaded from: classes10.dex */
final class Http20Draft13 implements Variant {
    static final byte FLAG_ACK = 1;
    static final byte FLAG_COMPRESSED = 32;
    static final byte FLAG_END_HEADERS = 4;
    static final byte FLAG_END_PUSH_PROMISE = 4;
    static final byte FLAG_END_SEGMENT = 2;
    static final byte FLAG_END_STREAM = 1;
    static final byte FLAG_NONE = 0;
    static final byte FLAG_PADDED = 8;
    static final byte FLAG_PRIORITY = 32;
    static final int MAX_FRAME_SIZE = 16383;
    static final byte TYPE_CONTINUATION = 9;
    static final byte TYPE_DATA = 0;
    static final byte TYPE_GOAWAY = 7;
    static final byte TYPE_HEADERS = 1;
    static final byte TYPE_PING = 6;
    static final byte TYPE_PRIORITY = 2;
    static final byte TYPE_PUSH_PROMISE = 5;
    static final byte TYPE_RST_STREAM = 3;
    static final byte TYPE_SETTINGS = 4;
    static final byte TYPE_WINDOW_UPDATE = 8;
    private static final Logger logger = Logger.getLogger(Http20Draft13.class.getName());
    private static final ByteString CONNECTION_PREFACE = ByteString.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");

    @Override // com.koushikdutta.async.http.spdy.Variant
    public int maxFrameSize() {
        return 16383;
    }

    Http20Draft13() {
    }

    @Override // com.koushikdutta.async.http.spdy.Variant
    public Protocol getProtocol() {
        return Protocol.HTTP_2;
    }

    @Override // com.koushikdutta.async.http.spdy.Variant
    public FrameReader newReader(DataEmitter dataEmitter, FrameReader.Handler handler, boolean z) {
        return new Reader(dataEmitter, handler, 4096, z);
    }

    @Override // com.koushikdutta.async.http.spdy.Variant
    public FrameWriter newWriter(BufferedDataSink bufferedDataSink, boolean z) {
        return new Writer(bufferedDataSink, z);
    }

    static final class Reader implements FrameReader {
        private final boolean client;
        int continuingStreamId;
        private final DataEmitter emitter;
        byte flags;
        private final FrameReader.Handler handler;
        final HpackDraft08.Reader hpackReader;
        short length;
        byte pendingHeaderType;
        int promisedStreamId;
        int streamId;
        byte type;
        int w1;
        int w2;
        private final DataCallback onFrame = new DataCallback() { // from class: com.koushikdutta.async.http.spdy.Http20Draft13.Reader.1
            @Override // com.koushikdutta.async.callback.DataCallback
            public void onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList) {
                byteBufferList.order(ByteOrder.BIG_ENDIAN);
                Reader.this.w1 = byteBufferList.getInt();
                Reader.this.w2 = byteBufferList.getInt();
                Reader reader = Reader.this;
                reader.length = (short) ((reader.w1 & 1073676288) >> 16);
                Reader reader2 = Reader.this;
                reader2.type = (byte) ((reader2.w1 & 65280) >> 8);
                Reader reader3 = Reader.this;
                reader3.flags = (byte) (reader3.w1 & 255);
                Reader reader4 = Reader.this;
                reader4.streamId = reader4.w2 & Integer.MAX_VALUE;
                if (Http20Draft13.logger.isLoggable(Level.FINE)) {
                    Http20Draft13.logger.fine(FrameLogger.formatHeader(true, Reader.this.streamId, Reader.this.length, Reader.this.type, Reader.this.flags));
                }
                Reader.this.reader.read(Reader.this.length, Reader.this.onFullFrame);
            }
        };
        private final DataCallback onFullFrame = new DataCallback() { // from class: com.koushikdutta.async.http.spdy.Http20Draft13.Reader.2
            @Override // com.koushikdutta.async.callback.DataCallback
            public void onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList) {
                try {
                    switch (Reader.this.type) {
                        case 0:
                            Reader reader = Reader.this;
                            reader.readData(byteBufferList, reader.length, Reader.this.flags, Reader.this.streamId);
                            break;
                        case 1:
                            Reader reader2 = Reader.this;
                            reader2.readHeaders(byteBufferList, reader2.length, Reader.this.flags, Reader.this.streamId);
                            break;
                        case 2:
                            Reader reader3 = Reader.this;
                            reader3.readPriority(byteBufferList, reader3.length, Reader.this.flags, Reader.this.streamId);
                            break;
                        case 3:
                            Reader reader4 = Reader.this;
                            reader4.readRstStream(byteBufferList, reader4.length, Reader.this.flags, Reader.this.streamId);
                            break;
                        case 4:
                            Reader reader5 = Reader.this;
                            reader5.readSettings(byteBufferList, reader5.length, Reader.this.flags, Reader.this.streamId);
                            break;
                        case 5:
                            Reader reader6 = Reader.this;
                            reader6.readPushPromise(byteBufferList, reader6.length, Reader.this.flags, Reader.this.streamId);
                            break;
                        case 6:
                            Reader reader7 = Reader.this;
                            reader7.readPing(byteBufferList, reader7.length, Reader.this.flags, Reader.this.streamId);
                            break;
                        case 7:
                            Reader reader8 = Reader.this;
                            reader8.readGoAway(byteBufferList, reader8.length, Reader.this.flags, Reader.this.streamId);
                            break;
                        case 8:
                            Reader reader9 = Reader.this;
                            reader9.readWindowUpdate(byteBufferList, reader9.length, Reader.this.flags, Reader.this.streamId);
                            break;
                        case 9:
                            Reader reader10 = Reader.this;
                            reader10.readContinuation(byteBufferList, reader10.length, Reader.this.flags, Reader.this.streamId);
                            break;
                        default:
                            byteBufferList.recycle();
                            break;
                    }
                    Reader.this.parseFrameHeader();
                } catch (IOException e) {
                    Reader.this.handler.error(e);
                }
            }
        };
        private final DataEmitterReader reader = new DataEmitterReader();

        Reader(DataEmitter dataEmitter, FrameReader.Handler handler, int i, boolean z) {
            this.emitter = dataEmitter;
            this.client = z;
            this.hpackReader = new HpackDraft08.Reader(i);
            this.handler = handler;
            parseFrameHeader();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void parseFrameHeader() {
            this.emitter.setDataCallback(this.reader);
            this.reader.read(8, this.onFrame);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readHeaders(ByteBufferList byteBufferList, short s, byte b, int i) throws IOException {
            if (i == 0) {
                throw Http20Draft13.ioException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
            }
            short s2 = (b & 8) != 0 ? (short) (byteBufferList.get() & 255) : (short) 0;
            if ((b & 32) != 0) {
                readPriority(byteBufferList, i);
                s = (short) (s - 5);
            }
            short sLengthWithoutPadding = Http20Draft13.lengthWithoutPadding(s, b, s2);
            this.pendingHeaderType = this.type;
            readHeaderBlock(byteBufferList, sLengthWithoutPadding, s2, b, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readContinuation(ByteBufferList byteBufferList, short s, byte b, int i) throws IOException {
            if (i != this.continuingStreamId) {
                throw new IOException("continuation stream id mismatch");
            }
            readHeaderBlock(byteBufferList, s, (short) 0, b, i);
        }

        private void readHeaderBlock(ByteBufferList byteBufferList, short s, short s2, byte b, int i) throws IOException {
            byteBufferList.skip(s2);
            this.hpackReader.refill(byteBufferList);
            this.hpackReader.readHeaders();
            this.hpackReader.emitReferenceSet();
            if ((b & 4) != 0) {
                byte b2 = this.pendingHeaderType;
                if (b2 == 1) {
                    this.handler.headers(false, (b & 1) != 0, i, -1, this.hpackReader.getAndReset(), HeadersMode.HTTP_20_HEADERS);
                    return;
                } else {
                    if (b2 == 5) {
                        this.handler.pushPromise(i, this.promisedStreamId, this.hpackReader.getAndReset());
                        return;
                    }
                    throw new AssertionError("unknown header type");
                }
            }
            this.continuingStreamId = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readData(ByteBufferList byteBufferList, short s, byte b, int i) throws IOException {
            boolean z = (b & 1) != 0;
            if ((b & 32) != 0) {
                throw Http20Draft13.ioException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
            }
            short s2 = (b & 8) != 0 ? (short) (byteBufferList.get() & 255) : (short) 0;
            Http20Draft13.lengthWithoutPadding(s, b, s2);
            this.handler.data(z, i, byteBufferList);
            byteBufferList.skip(s2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readPriority(ByteBufferList byteBufferList, short s, byte b, int i) throws IOException {
            if (s != 5) {
                throw Http20Draft13.ioException("TYPE_PRIORITY length: %d != 5", Short.valueOf(s));
            }
            if (i == 0) {
                throw Http20Draft13.ioException("TYPE_PRIORITY streamId == 0", new Object[0]);
            }
            readPriority(byteBufferList, i);
        }

        private void readPriority(ByteBufferList byteBufferList, int i) throws IOException {
            int i2 = byteBufferList.getInt();
            boolean z = (Integer.MIN_VALUE & i2) != 0;
            this.handler.priority(i, i2 & Integer.MAX_VALUE, (byteBufferList.get() & 255) + 1, z);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readRstStream(ByteBufferList byteBufferList, short s, byte b, int i) throws IOException {
            if (s != 4) {
                throw Http20Draft13.ioException("TYPE_RST_STREAM length: %d != 4", Short.valueOf(s));
            }
            if (i == 0) {
                throw Http20Draft13.ioException("TYPE_RST_STREAM streamId == 0", new Object[0]);
            }
            int i2 = byteBufferList.getInt();
            ErrorCode errorCodeFromHttp2 = ErrorCode.fromHttp2(i2);
            if (errorCodeFromHttp2 == null) {
                throw Http20Draft13.ioException("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(i2));
            }
            this.handler.rstStream(i, errorCodeFromHttp2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readSettings(ByteBufferList byteBufferList, short s, byte b, int i) throws IOException {
            if (i != 0) {
                throw Http20Draft13.ioException("TYPE_SETTINGS streamId != 0", new Object[0]);
            }
            if ((b & 1) != 0) {
                if (s != 0) {
                    throw Http20Draft13.ioException("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
                }
                this.handler.ackSettings();
                return;
            }
            if (s % 6 != 0) {
                throw Http20Draft13.ioException("TYPE_SETTINGS length %% 6 != 0: %s", Short.valueOf(s));
            }
            Settings settings = new Settings();
            for (int i2 = 0; i2 < s; i2 += 6) {
                short s2 = byteBufferList.getShort();
                int i3 = byteBufferList.getInt();
                if (s2 != 1) {
                    if (s2 == 2) {
                        if (i3 != 0 && i3 != 1) {
                            throw Http20Draft13.ioException("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                        }
                    } else if (s2 == 3) {
                        s2 = 4;
                    } else if (s2 != 4) {
                        if (s2 != 5) {
                            throw Http20Draft13.ioException("PROTOCOL_ERROR invalid settings id: %s", Short.valueOf(s2));
                        }
                    } else {
                        if (i3 < 0) {
                            throw Http20Draft13.ioException("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                        }
                        s2 = 7;
                    }
                }
                settings.set(s2, 0, i3);
            }
            this.handler.settings(false, settings);
            if (settings.getHeaderTableSize() >= 0) {
                this.hpackReader.maxHeaderTableByteCountSetting(settings.getHeaderTableSize());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readPushPromise(ByteBufferList byteBufferList, short s, byte b, int i) throws IOException {
            if (i == 0) {
                throw Http20Draft13.ioException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
            }
            short s2 = (b & 8) != 0 ? (short) (byteBufferList.get() & 255) : (short) 0;
            this.promisedStreamId = byteBufferList.getInt() & Integer.MAX_VALUE;
            short sLengthWithoutPadding = Http20Draft13.lengthWithoutPadding((short) (s - 4), b, s2);
            this.pendingHeaderType = (byte) 5;
            readHeaderBlock(byteBufferList, sLengthWithoutPadding, s2, b, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readPing(ByteBufferList byteBufferList, short s, byte b, int i) throws IOException {
            if (s != 8) {
                throw Http20Draft13.ioException("TYPE_PING length != 8: %s", Short.valueOf(s));
            }
            if (i != 0) {
                throw Http20Draft13.ioException("TYPE_PING streamId != 0", new Object[0]);
            }
            this.handler.ping((b & 1) != 0, byteBufferList.getInt(), byteBufferList.getInt());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readGoAway(ByteBufferList byteBufferList, short s, byte b, int i) throws IOException {
            if (s < 8) {
                throw Http20Draft13.ioException("TYPE_GOAWAY length < 8: %s", Short.valueOf(s));
            }
            if (i != 0) {
                throw Http20Draft13.ioException("TYPE_GOAWAY streamId != 0", new Object[0]);
            }
            int i2 = byteBufferList.getInt();
            int i3 = byteBufferList.getInt();
            int i4 = s - 8;
            ErrorCode errorCodeFromHttp2 = ErrorCode.fromHttp2(i3);
            if (errorCodeFromHttp2 == null) {
                throw Http20Draft13.ioException("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(i3));
            }
            ByteString byteStringOf = ByteString.EMPTY;
            if (i4 > 0) {
                byteStringOf = ByteString.of(byteBufferList.getBytes(i4));
            }
            this.handler.goAway(i2, errorCodeFromHttp2, byteStringOf);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readWindowUpdate(ByteBufferList byteBufferList, short s, byte b, int i) throws IOException {
            if (s != 4) {
                throw Http20Draft13.ioException("TYPE_WINDOW_UPDATE length !=4: %s", Short.valueOf(s));
            }
            long j = ((long) byteBufferList.getInt()) & 2147483647L;
            if (j == 0) {
                throw Http20Draft13.ioException("windowSizeIncrement was 0", Long.valueOf(j));
            }
            this.handler.windowUpdate(i, j);
        }
    }

    static final class Writer implements FrameWriter {
        private final boolean client;
        private boolean closed;
        private final ByteBufferList frameHeader = new ByteBufferList();
        private final HpackDraft08.Writer hpackWriter = new HpackDraft08.Writer();
        private final BufferedDataSink sink;

        Writer(BufferedDataSink bufferedDataSink, boolean z) {
            this.sink = bufferedDataSink;
            this.client = z;
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void ackSettings() throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            frameHeader(0, 0, (byte) 4, (byte) 1);
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void connectionPreface() throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (this.client) {
                if (Http20Draft13.logger.isLoggable(Level.FINE)) {
                    Http20Draft13.logger.fine(String.format(Locale.ENGLISH, ">> CONNECTION %s", Http20Draft13.CONNECTION_PREFACE.hex()));
                }
                this.sink.write(new ByteBufferList(Http20Draft13.CONNECTION_PREFACE.toByteArray()));
            }
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void synStream(boolean z, boolean z2, int i, int i2, List<Header> list) throws IOException {
            try {
                if (z2) {
                    throw new UnsupportedOperationException();
                }
                if (this.closed) {
                    throw new IOException("closed");
                }
                headers(z, i, list);
            } catch (Throwable th) {
                throw th;
            }
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void synReply(boolean z, int i, List<Header> list) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            headers(z, i, list);
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void headers(int i, List<Header> list) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            headers(false, i, list);
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void pushPromise(int i, int i2, List<Header> list) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            ByteBufferList byteBufferListWriteHeaders = this.hpackWriter.writeHeaders(list);
            long jRemaining = byteBufferListWriteHeaders.remaining();
            int iMin = (int) Math.min(16379L, jRemaining);
            long j = iMin;
            frameHeader(i, iMin + 4, (byte) 5, jRemaining == j ? (byte) 4 : (byte) 0);
            ByteBuffer byteBufferOrder = ByteBufferList.obtain(8192).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.putInt(i2 & Integer.MAX_VALUE);
            byteBufferOrder.flip();
            this.frameHeader.add(byteBufferOrder);
            byteBufferListWriteHeaders.get(this.frameHeader, iMin);
            this.sink.write(this.frameHeader);
            if (jRemaining > j) {
                writeContinuationFrames(byteBufferListWriteHeaders, i);
            }
        }

        void headers(boolean z, int i, List<Header> list) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            ByteBufferList byteBufferListWriteHeaders = this.hpackWriter.writeHeaders(list);
            long jRemaining = byteBufferListWriteHeaders.remaining();
            int iMin = (int) Math.min(16383L, jRemaining);
            long j = iMin;
            byte b = jRemaining == j ? (byte) 4 : (byte) 0;
            if (z) {
                b = (byte) (b | 1);
            }
            frameHeader(i, iMin, (byte) 1, b);
            byteBufferListWriteHeaders.get(this.frameHeader, iMin);
            this.sink.write(this.frameHeader);
            if (jRemaining > j) {
                writeContinuationFrames(byteBufferListWriteHeaders, i);
            }
        }

        private void writeContinuationFrames(ByteBufferList byteBufferList, int i) throws IOException {
            while (byteBufferList.hasRemaining()) {
                int iMin = Math.min(16383, byteBufferList.remaining());
                frameHeader(i, iMin, (byte) 9, byteBufferList.remaining() - iMin == 0 ? (byte) 4 : (byte) 0);
                byteBufferList.get(this.frameHeader, iMin);
                this.sink.write(this.frameHeader);
            }
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void rstStream(int i, ErrorCode errorCode) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (errorCode.spdyRstCode == -1) {
                throw new IllegalArgumentException();
            }
            frameHeader(i, 4, (byte) 3, (byte) 0);
            ByteBuffer byteBufferOrder = ByteBufferList.obtain(8192).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.putInt(errorCode.httpCode);
            byteBufferOrder.flip();
            this.sink.write(this.frameHeader.add(byteBufferOrder));
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void data(boolean z, int i, ByteBufferList byteBufferList) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            dataFrame(i, z ? (byte) 1 : (byte) 0, byteBufferList);
        }

        void dataFrame(int i, byte b, ByteBufferList byteBufferList) throws IOException {
            frameHeader(i, byteBufferList.remaining(), (byte) 0, b);
            this.sink.write(byteBufferList);
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void settings(Settings settings) throws IOException {
            int i;
            if (this.closed) {
                throw new IOException("closed");
            }
            int i2 = 0;
            frameHeader(0, settings.size() * 6, (byte) 4, (byte) 0);
            ByteBuffer byteBufferOrder = ByteBufferList.obtain(8192).order(ByteOrder.BIG_ENDIAN);
            while (i2 < 10) {
                if (settings.isSet(i2)) {
                    if (i2 == 4) {
                        i = 3;
                    } else {
                        i = i2 == 7 ? 4 : i2;
                    }
                    byteBufferOrder.putShort((short) i);
                    byteBufferOrder.putInt(settings.get(i2));
                }
                i2++;
            }
            byteBufferOrder.flip();
            this.sink.write(this.frameHeader.add(byteBufferOrder));
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void ping(boolean z, int i, int i2) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            frameHeader(0, 8, (byte) 6, z ? (byte) 1 : (byte) 0);
            ByteBuffer byteBufferOrder = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.putInt(i);
            byteBufferOrder.putInt(i2);
            byteBufferOrder.flip();
            this.sink.write(this.frameHeader.add(byteBufferOrder));
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void goAway(int i, ErrorCode errorCode, byte[] bArr) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (errorCode.httpCode == -1) {
                throw Http20Draft13.illegalArgument("errorCode.httpCode == -1", new Object[0]);
            }
            frameHeader(0, bArr.length + 8, (byte) 7, (byte) 0);
            ByteBuffer byteBufferOrder = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.putInt(i);
            byteBufferOrder.putInt(errorCode.httpCode);
            byteBufferOrder.put(bArr);
            byteBufferOrder.flip();
            this.sink.write(this.frameHeader.add(byteBufferOrder));
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void windowUpdate(int i, long j) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (j == 0 || j > 2147483647L) {
                throw Http20Draft13.illegalArgument("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", Long.valueOf(j));
            }
            frameHeader(i, 4, (byte) 8, (byte) 0);
            ByteBuffer byteBufferOrder = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.putInt((int) j);
            byteBufferOrder.flip();
            this.sink.write(this.frameHeader.add(byteBufferOrder));
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public synchronized void close() throws IOException {
            this.closed = true;
        }

        void frameHeader(int i, int i2, byte b, byte b2) throws IOException {
            if (Http20Draft13.logger.isLoggable(Level.FINE)) {
                Http20Draft13.logger.fine(FrameLogger.formatHeader(false, i, i2, b, b2));
            }
            if (i2 > 16383) {
                throw Http20Draft13.illegalArgument("FRAME_SIZE_ERROR length > %d: %d", 16383, Integer.valueOf(i2));
            }
            if ((Integer.MIN_VALUE & i) != 0) {
                throw Http20Draft13.illegalArgument("reserved bit set: %s", Integer.valueOf(i));
            }
            ByteBuffer byteBufferOrder = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.putInt(((i2 & 16383) << 16) | ((b & 255) << 8) | (b2 & 255));
            byteBufferOrder.putInt(i & Integer.MAX_VALUE);
            byteBufferOrder.flip();
            this.sink.write(this.frameHeader.add(byteBufferOrder));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IllegalArgumentException illegalArgument(String str, Object... objArr) {
        throw new IllegalArgumentException(String.format(Locale.ENGLISH, str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IOException ioException(String str, Object... objArr) throws IOException {
        throw new IOException(String.format(Locale.ENGLISH, str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static short lengthWithoutPadding(short s, byte b, short s2) throws IOException {
        if ((b & 8) != 0) {
            s = (short) (s - 1);
        }
        if (s2 <= s) {
            return (short) (s - s2);
        }
        throw ioException("PROTOCOL_ERROR padding %s > remaining length %s", Short.valueOf(s2), Short.valueOf(s));
    }

    static final class FrameLogger {
        private static final String[] TYPES = {"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};
        private static final String[] FLAGS = new String[64];
        private static final String[] BINARY = new String[256];

        FrameLogger() {
        }

        static String formatHeader(boolean z, int i, int i2, byte b, byte b2) {
            String[] strArr = TYPES;
            return String.format(Locale.ENGLISH, "%s 0x%08x %5d %-13s %s", z ? "<<" : ">>", Integer.valueOf(i), Integer.valueOf(i2), b < strArr.length ? strArr[b] : String.format(Locale.ENGLISH, "0x%02x", Byte.valueOf(b)), formatFlags(b, b2));
        }

        static String formatFlags(byte b, byte b2) {
            if (b2 == 0) {
                return "";
            }
            if (b != 2 && b != 3) {
                if (b == 4 || b == 6) {
                    return b2 == 1 ? "ACK" : BINARY[b2];
                }
                if (b != 7 && b != 8) {
                    String[] strArr = FLAGS;
                    String str = b2 < strArr.length ? strArr[b2] : BINARY[b2];
                    if (b != 5 || (b2 & 4) == 0) {
                        return (b != 0 || (b2 & 32) == 0) ? str : str.replace("PRIORITY", "COMPRESSED");
                    }
                    return str.replace("HEADERS", "PUSH_PROMISE");
                }
            }
            return BINARY[b2];
        }

        static {
            int i = 0;
            int i2 = 0;
            while (true) {
                String[] strArr = BINARY;
                if (i2 >= strArr.length) {
                    break;
                }
                strArr[i2] = String.format(Locale.ENGLISH, "%8s", Integer.toBinaryString(i2)).replace(StringUtil.SPACE, ExternalAnnotationProvider.NULLABLE);
                i2++;
            }
            String[] strArr2 = FLAGS;
            strArr2[0] = "";
            strArr2[1] = "END_STREAM";
            strArr2[2] = "END_SEGMENT";
            strArr2[3] = "END_STREAM|END_SEGMENT";
            int[] iArr = {1, 2, 3};
            strArr2[8] = "PADDED";
            for (int i3 = 0; i3 < 3; i3++) {
                int i4 = iArr[i3];
                String[] strArr3 = FLAGS;
                strArr3[i4 | 8] = strArr3[i4] + "|PADDED";
            }
            String[] strArr4 = FLAGS;
            strArr4[4] = "END_HEADERS";
            strArr4[32] = "PRIORITY";
            strArr4[36] = "END_HEADERS|PRIORITY";
            int[] iArr2 = {4, 32, 36};
            for (int i5 = 0; i5 < 3; i5++) {
                int i6 = iArr2[i5];
                for (int i7 = 0; i7 < 3; i7++) {
                    int i8 = iArr[i7];
                    String[] strArr5 = FLAGS;
                    int i9 = i8 | i6;
                    strArr5[i9] = strArr5[i8] + '|' + strArr5[i6];
                    strArr5[i9 | 8] = strArr5[i8] + '|' + strArr5[i6] + "|PADDED";
                }
            }
            while (true) {
                String[] strArr6 = FLAGS;
                if (i >= strArr6.length) {
                    return;
                }
                if (strArr6[i] == null) {
                    strArr6[i] = BINARY[i];
                }
                i++;
            }
        }
    }
}
