package com.koushikdutta.async.http.spdy;

import androidx.core.view.ViewCompat;
import com.koushikdutta.async.BufferedDataSink;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataEmitterReader;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.Protocol;
import com.koushikdutta.async.util.Charsets;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Locale;
import java.util.zip.Deflater;
import org.luaj.vm2.Lua;

/* JADX INFO: loaded from: classes10.dex */
final class Spdy3 implements Variant {
    static final byte[] DICTIONARY;
    static final int FLAG_FIN = 1;
    static final int FLAG_UNIDIRECTIONAL = 2;
    static final int TYPE_DATA = 0;
    static final int TYPE_GOAWAY = 7;
    static final int TYPE_HEADERS = 8;
    static final int TYPE_PING = 6;
    static final int TYPE_RST_STREAM = 3;
    static final int TYPE_SETTINGS = 4;
    static final int TYPE_SYN_REPLY = 2;
    static final int TYPE_SYN_STREAM = 1;
    static final int TYPE_WINDOW_UPDATE = 9;
    static final int VERSION = 3;

    @Override // com.koushikdutta.async.http.spdy.Variant
    public int maxFrameSize() {
        return Lua.MASK_NOT_Bx;
    }

    Spdy3() {
    }

    @Override // com.koushikdutta.async.http.spdy.Variant
    public Protocol getProtocol() {
        return Protocol.SPDY_3;
    }

    static {
        try {
            DICTIONARY = "\u0000\u0000\u0000\u0007options\u0000\u0000\u0000\u0004head\u0000\u0000\u0000\u0004post\u0000\u0000\u0000\u0003put\u0000\u0000\u0000\u0006delete\u0000\u0000\u0000\u0005trace\u0000\u0000\u0000\u0006accept\u0000\u0000\u0000\u000eaccept-charset\u0000\u0000\u0000\u000faccept-encoding\u0000\u0000\u0000\u000faccept-language\u0000\u0000\u0000\raccept-ranges\u0000\u0000\u0000\u0003age\u0000\u0000\u0000\u0005allow\u0000\u0000\u0000\rauthorization\u0000\u0000\u0000\rcache-control\u0000\u0000\u0000\nconnection\u0000\u0000\u0000\fcontent-base\u0000\u0000\u0000\u0010content-encoding\u0000\u0000\u0000\u0010content-language\u0000\u0000\u0000\u000econtent-length\u0000\u0000\u0000\u0010content-location\u0000\u0000\u0000\u000bcontent-md5\u0000\u0000\u0000\rcontent-range\u0000\u0000\u0000\fcontent-type\u0000\u0000\u0000\u0004date\u0000\u0000\u0000\u0004etag\u0000\u0000\u0000\u0006expect\u0000\u0000\u0000\u0007expires\u0000\u0000\u0000\u0004from\u0000\u0000\u0000\u0004host\u0000\u0000\u0000\bif-match\u0000\u0000\u0000\u0011if-modified-since\u0000\u0000\u0000\rif-none-match\u0000\u0000\u0000\bif-range\u0000\u0000\u0000\u0013if-unmodified-since\u0000\u0000\u0000\rlast-modified\u0000\u0000\u0000\blocation\u0000\u0000\u0000\fmax-forwards\u0000\u0000\u0000\u0006pragma\u0000\u0000\u0000\u0012proxy-authenticate\u0000\u0000\u0000\u0013proxy-authorization\u0000\u0000\u0000\u0005range\u0000\u0000\u0000\u0007referer\u0000\u0000\u0000\u000bretry-after\u0000\u0000\u0000\u0006server\u0000\u0000\u0000\u0002te\u0000\u0000\u0000\u0007trailer\u0000\u0000\u0000\u0011transfer-encoding\u0000\u0000\u0000\u0007upgrade\u0000\u0000\u0000\nuser-agent\u0000\u0000\u0000\u0004vary\u0000\u0000\u0000\u0003via\u0000\u0000\u0000\u0007warning\u0000\u0000\u0000\u0010www-authenticate\u0000\u0000\u0000\u0006method\u0000\u0000\u0000\u0003get\u0000\u0000\u0000\u0006status\u0000\u0000\u0000\u0006200 OK\u0000\u0000\u0000\u0007version\u0000\u0000\u0000\bHTTP/1.1\u0000\u0000\u0000\u0003url\u0000\u0000\u0000\u0006public\u0000\u0000\u0000\nset-cookie\u0000\u0000\u0000\nkeep-alive\u0000\u0000\u0000\u0006origin100101201202205206300302303304305306307402405406407408409410411412413414415416417502504505203 Non-Authoritative Information204 No Content301 Moved Permanently400 Bad Request401 Unauthorized403 Forbidden404 Not Found500 Internal Server Error501 Not Implemented503 Service UnavailableJan Feb Mar Apr May Jun Jul Aug Sept Oct Nov Dec 00:00:00 Mon, Tue, Wed, Thu, Fri, Sat, Sun, GMTchunked,text/html,image/png,image/jpg,image/gif,application/xml,application/xhtml+xml,text/plain,text/javascript,publicprivatemax-age=gzip,deflate,sdchcharset=utf-8charset=iso-8859-1,utf-,*,enq=0.".getBytes(Charsets.UTF_8.name());
        } catch (UnsupportedEncodingException unused) {
            throw new AssertionError();
        }
    }

    @Override // com.koushikdutta.async.http.spdy.Variant
    public FrameReader newReader(DataEmitter dataEmitter, FrameReader.Handler handler, boolean z) {
        return new Reader(dataEmitter, handler, z);
    }

    @Override // com.koushikdutta.async.http.spdy.Variant
    public FrameWriter newWriter(BufferedDataSink bufferedDataSink, boolean z) {
        return new Writer(bufferedDataSink, z);
    }

    static final class Reader implements FrameReader {
        private final boolean client;
        private final DataEmitter emitter;
        int flags;
        private final FrameReader.Handler handler;
        boolean inFinished;
        int length;
        private final DataEmitterReader reader;
        int streamId;
        int w1;
        int w2;
        private final HeaderReader headerReader = new HeaderReader();
        private final ByteBufferList emptyList = new ByteBufferList();
        private final DataCallback onFrame = new DataCallback() { // from class: com.koushikdutta.async.http.spdy.Spdy3.Reader.2
            @Override // com.koushikdutta.async.callback.DataCallback
            public void onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList) {
                byteBufferList.order(ByteOrder.BIG_ENDIAN);
                Reader.this.w1 = byteBufferList.getInt();
                Reader.this.w2 = byteBufferList.getInt();
                boolean z = (Reader.this.w1 & Integer.MIN_VALUE) != 0;
                Reader reader = Reader.this;
                reader.flags = (reader.w2 & ViewCompat.MEASURED_STATE_MASK) >>> 24;
                Reader reader2 = Reader.this;
                reader2.length = reader2.w2 & 16777215;
                if (z) {
                    Reader.this.reader.read(Reader.this.length, Reader.this.onFullFrame);
                    return;
                }
                Reader reader3 = Reader.this;
                reader3.streamId = reader3.w1 & Integer.MAX_VALUE;
                Reader reader4 = Reader.this;
                reader4.inFinished = (reader4.flags & 1) != 0;
                dataEmitter.setDataCallback(Reader.this.onDataFrame);
                if (Reader.this.length == 0) {
                    Reader.this.onDataFrame.onDataAvailable(dataEmitter, Reader.this.emptyList);
                }
            }
        };
        ByteBufferList partial = new ByteBufferList();
        private final DataCallback onDataFrame = new DataCallback() { // from class: com.koushikdutta.async.http.spdy.Spdy3.Reader.3
            @Override // com.koushikdutta.async.callback.DataCallback
            public void onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList) {
                int iMin = Math.min(byteBufferList.remaining(), Reader.this.length);
                if (iMin < byteBufferList.remaining()) {
                    byteBufferList.get(Reader.this.partial, iMin);
                    byteBufferList = Reader.this.partial;
                }
                Reader.this.length -= iMin;
                Reader.this.handler.data(Reader.this.length == 0 && Reader.this.inFinished, Reader.this.streamId, byteBufferList);
                if (Reader.this.length == 0) {
                    Reader.this.parseFrameHeader();
                }
            }
        };
        private final DataCallback onFullFrame = new DataCallback() { // from class: com.koushikdutta.async.http.spdy.Spdy3.Reader.4
            @Override // com.koushikdutta.async.callback.DataCallback
            public void onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList) {
                byteBufferList.order(ByteOrder.BIG_ENDIAN);
                int i = (Reader.this.w1 & 2147418112) >>> 16;
                int i2 = Reader.this.w1 & 65535;
                try {
                    if (i != 3) {
                        throw new ProtocolException("version != 3: " + i);
                    }
                    switch (i2) {
                        case 1:
                            Reader reader = Reader.this;
                            reader.readSynStream(byteBufferList, reader.flags, Reader.this.length);
                            break;
                        case 2:
                            Reader reader2 = Reader.this;
                            reader2.readSynReply(byteBufferList, reader2.flags, Reader.this.length);
                            break;
                        case 3:
                            Reader reader3 = Reader.this;
                            reader3.readRstStream(byteBufferList, reader3.flags, Reader.this.length);
                            break;
                        case 4:
                            Reader reader4 = Reader.this;
                            reader4.readSettings(byteBufferList, reader4.flags, Reader.this.length);
                            break;
                        case 5:
                        default:
                            byteBufferList.recycle();
                            break;
                        case 6:
                            Reader reader5 = Reader.this;
                            reader5.readPing(byteBufferList, reader5.flags, Reader.this.length);
                            break;
                        case 7:
                            Reader reader6 = Reader.this;
                            reader6.readGoAway(byteBufferList, reader6.flags, Reader.this.length);
                            break;
                        case 8:
                            Reader reader7 = Reader.this;
                            reader7.readHeaders(byteBufferList, reader7.flags, Reader.this.length);
                            break;
                        case 9:
                            Reader reader8 = Reader.this;
                            reader8.readWindowUpdate(byteBufferList, reader8.flags, Reader.this.length);
                            break;
                    }
                    Reader.this.parseFrameHeader();
                } catch (IOException e) {
                    Reader.this.handler.error(e);
                }
            }
        };

        Reader(DataEmitter dataEmitter, FrameReader.Handler handler, boolean z) {
            this.emitter = dataEmitter;
            this.handler = handler;
            this.client = z;
            dataEmitter.setEndCallback(new CompletedCallback() { // from class: com.koushikdutta.async.http.spdy.Spdy3.Reader.1
                @Override // com.koushikdutta.async.callback.CompletedCallback
                public void onCompleted(Exception exc) {
                }
            });
            this.reader = new DataEmitterReader();
            parseFrameHeader();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void parseFrameHeader() {
            this.emitter.setDataCallback(this.reader);
            this.reader.read(8, this.onFrame);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readSynStream(ByteBufferList byteBufferList, int i, int i2) throws IOException {
            int i3 = byteBufferList.getInt() & Integer.MAX_VALUE;
            int i4 = byteBufferList.getInt() & Integer.MAX_VALUE;
            byteBufferList.getShort();
            this.handler.headers((i & 2) != 0, (i & 1) != 0, i3, i4, this.headerReader.readHeader(byteBufferList, i2 - 10), HeadersMode.SPDY_SYN_STREAM);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readSynReply(ByteBufferList byteBufferList, int i, int i2) throws IOException {
            this.handler.headers(false, (i & 1) != 0, byteBufferList.getInt() & Integer.MAX_VALUE, -1, this.headerReader.readHeader(byteBufferList, i2 - 4), HeadersMode.SPDY_REPLY);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readRstStream(ByteBufferList byteBufferList, int i, int i2) throws IOException {
            if (i2 != 8) {
                throw ioException("TYPE_RST_STREAM length: %d != 8", Integer.valueOf(i2));
            }
            int i3 = byteBufferList.getInt() & Integer.MAX_VALUE;
            int i4 = byteBufferList.getInt();
            ErrorCode errorCodeFromSpdy3Rst = ErrorCode.fromSpdy3Rst(i4);
            if (errorCodeFromSpdy3Rst == null) {
                throw ioException("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(i4));
            }
            this.handler.rstStream(i3, errorCodeFromSpdy3Rst);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readHeaders(ByteBufferList byteBufferList, int i, int i2) throws IOException {
            this.handler.headers(false, false, byteBufferList.getInt() & Integer.MAX_VALUE, -1, this.headerReader.readHeader(byteBufferList, i2 - 4), HeadersMode.SPDY_HEADERS);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readWindowUpdate(ByteBufferList byteBufferList, int i, int i2) throws IOException {
            if (i2 != 8) {
                throw ioException("TYPE_WINDOW_UPDATE length: %d != 8", Integer.valueOf(i2));
            }
            int i3 = byteBufferList.getInt() & Integer.MAX_VALUE;
            long j = byteBufferList.getInt() & Integer.MAX_VALUE;
            if (j == 0) {
                throw ioException("windowSizeIncrement was 0", Long.valueOf(j));
            }
            this.handler.windowUpdate(i3, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readPing(ByteBufferList byteBufferList, int i, int i2) throws IOException {
            if (i2 != 4) {
                throw ioException("TYPE_PING length: %d != 4", Integer.valueOf(i2));
            }
            int i3 = byteBufferList.getInt();
            this.handler.ping(this.client == ((i3 & 1) == 1), i3, 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readGoAway(ByteBufferList byteBufferList, int i, int i2) throws IOException {
            if (i2 != 8) {
                throw ioException("TYPE_GOAWAY length: %d != 8", Integer.valueOf(i2));
            }
            int i3 = byteBufferList.getInt() & Integer.MAX_VALUE;
            int i4 = byteBufferList.getInt();
            ErrorCode errorCodeFromSpdyGoAway = ErrorCode.fromSpdyGoAway(i4);
            if (errorCodeFromSpdyGoAway == null) {
                throw ioException("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(i4));
            }
            this.handler.goAway(i3, errorCodeFromSpdyGoAway, ByteString.EMPTY);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readSettings(ByteBufferList byteBufferList, int i, int i2) throws IOException {
            int i3 = byteBufferList.getInt();
            if (i2 != (i3 * 8) + 4) {
                throw ioException("TYPE_SETTINGS length: %d != 4 + 8 * %d", Integer.valueOf(i2), Integer.valueOf(i3));
            }
            Settings settings = new Settings();
            for (int i4 = 0; i4 < i3; i4++) {
                int i5 = byteBufferList.getInt();
                settings.set(i5 & 16777215, ((-16777216) & i5) >>> 24, byteBufferList.getInt());
            }
            this.handler.settings((i & 1) != 0, settings);
        }

        private static IOException ioException(String str, Object... objArr) throws IOException {
            throw new IOException(String.format(Locale.ENGLISH, str, objArr));
        }
    }

    static final class Writer implements FrameWriter {
        private final boolean client;
        private boolean closed;
        ByteBufferList dataList;
        private final Deflater deflater;
        private ByteBufferList frameHeader = new ByteBufferList();
        ByteBufferList headerBlockList;
        private final BufferedDataSink sink;

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public void ackSettings() {
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public void pushPromise(int i, int i2, List<Header> list) throws IOException {
        }

        Writer(BufferedDataSink bufferedDataSink, boolean z) {
            Deflater deflater = new Deflater();
            this.deflater = deflater;
            this.dataList = new ByteBufferList();
            this.headerBlockList = new ByteBufferList();
            this.sink = bufferedDataSink;
            this.client = z;
            deflater.setDictionary(Spdy3.DICTIONARY);
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void connectionPreface() {
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void synStream(boolean z, boolean z2, int i, int i2, List<Header> list) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            ByteBufferList byteBufferListWriteNameValueBlockToBuffer = writeNameValueBlockToBuffer(list);
            int iRemaining = byteBufferListWriteNameValueBlockToBuffer.remaining() + 10;
            int i3 = (z ? 1 : 0) | (z2 ? 2 : 0);
            ByteBuffer byteBufferOrder = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.putInt(-2147287039);
            byteBufferOrder.putInt(((i3 & 255) << 24) | (iRemaining & 16777215));
            byteBufferOrder.putInt(i & Integer.MAX_VALUE);
            byteBufferOrder.putInt(Integer.MAX_VALUE & i2);
            byteBufferOrder.putShort((short) 0);
            byteBufferOrder.flip();
            this.sink.write(this.frameHeader.add(byteBufferOrder).add(byteBufferListWriteNameValueBlockToBuffer));
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void synReply(boolean z, int i, List<Header> list) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            ByteBufferList byteBufferListWriteNameValueBlockToBuffer = writeNameValueBlockToBuffer(list);
            int iRemaining = byteBufferListWriteNameValueBlockToBuffer.remaining() + 4;
            ByteBuffer byteBufferOrder = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.putInt(-2147287038);
            byteBufferOrder.putInt((((z ? 1 : 0) & 255) << 24) | (iRemaining & 16777215));
            byteBufferOrder.putInt(Integer.MAX_VALUE & i);
            byteBufferOrder.flip();
            this.sink.write(this.frameHeader.add(byteBufferOrder).add(byteBufferListWriteNameValueBlockToBuffer));
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void headers(int i, List<Header> list) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            ByteBufferList byteBufferListWriteNameValueBlockToBuffer = writeNameValueBlockToBuffer(list);
            int iRemaining = byteBufferListWriteNameValueBlockToBuffer.remaining() + 4;
            ByteBuffer byteBufferOrder = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.putInt(-2147287032);
            byteBufferOrder.putInt(iRemaining & 16777215);
            byteBufferOrder.putInt(i & Integer.MAX_VALUE);
            byteBufferOrder.flip();
            this.sink.write(this.frameHeader.add(byteBufferOrder).add(byteBufferListWriteNameValueBlockToBuffer));
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void rstStream(int i, ErrorCode errorCode) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (errorCode.spdyRstCode == -1) {
                throw new IllegalArgumentException();
            }
            ByteBuffer byteBufferOrder = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.putInt(-2147287037);
            byteBufferOrder.putInt(8);
            byteBufferOrder.putInt(i & Integer.MAX_VALUE);
            byteBufferOrder.putInt(errorCode.spdyRstCode);
            byteBufferOrder.flip();
            this.sink.write(this.frameHeader.addAll(byteBufferOrder));
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void data(boolean z, int i, ByteBufferList byteBufferList) throws IOException {
            sendDataFrame(i, z ? 1 : 0, byteBufferList);
        }

        void sendDataFrame(int i, int i2, ByteBufferList byteBufferList) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            int iRemaining = byteBufferList.remaining();
            if (iRemaining > 16777215) {
                throw new IllegalArgumentException("FRAME_TOO_LARGE max size is 16Mib: " + iRemaining);
            }
            ByteBuffer byteBufferOrder = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.putInt(i & Integer.MAX_VALUE);
            byteBufferOrder.putInt(((i2 & 255) << 24) | (16777215 & iRemaining));
            byteBufferOrder.flip();
            this.dataList.add(byteBufferOrder).add(byteBufferList);
            this.sink.write(this.dataList);
        }

        private ByteBufferList writeNameValueBlockToBuffer(List<Header> list) throws IOException {
            if (this.headerBlockList.hasRemaining()) {
                throw new IllegalStateException();
            }
            ByteBuffer byteBufferOrder = ByteBufferList.obtain(8192).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.putInt(list.size());
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ByteString byteString = list.get(i).f215name;
                byteBufferOrder.putInt(byteString.size());
                byteBufferOrder.put(byteString.toByteArray());
                ByteString byteString2 = list.get(i).value;
                byteBufferOrder.putInt(byteString2.size());
                byteBufferOrder.put(byteString2.toByteArray());
                if (byteBufferOrder.remaining() < byteBufferOrder.capacity() / 2) {
                    ByteBuffer byteBufferOrder2 = ByteBufferList.obtain(byteBufferOrder.capacity() * 2).order(ByteOrder.BIG_ENDIAN);
                    byteBufferOrder.flip();
                    byteBufferOrder2.put(byteBufferOrder);
                    ByteBufferList.reclaim(byteBufferOrder);
                    byteBufferOrder = byteBufferOrder2;
                }
            }
            byteBufferOrder.flip();
            this.deflater.setInput(byteBufferOrder.array(), 0, byteBufferOrder.remaining());
            while (!this.deflater.needsInput()) {
                ByteBuffer byteBufferOrder3 = ByteBufferList.obtain(byteBufferOrder.capacity()).order(ByteOrder.BIG_ENDIAN);
                byteBufferOrder3.limit(this.deflater.deflate(byteBufferOrder3.array(), 0, byteBufferOrder3.capacity(), 2));
                this.headerBlockList.add(byteBufferOrder3);
            }
            ByteBufferList.reclaim(byteBufferOrder);
            return this.headerBlockList;
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void settings(Settings settings) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            int size = settings.size();
            ByteBuffer byteBufferOrder = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.putInt(-2147287036);
            byteBufferOrder.putInt(((size * 8) + 4) & 16777215);
            byteBufferOrder.putInt(size);
            for (int i = 0; i <= 10; i++) {
                if (settings.isSet(i)) {
                    byteBufferOrder.putInt(((settings.flags(i) & 255) << 24) | (i & 16777215));
                    byteBufferOrder.putInt(settings.get(i));
                }
            }
            byteBufferOrder.flip();
            this.sink.write(this.frameHeader.addAll(byteBufferOrder));
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void ping(boolean z, int i, int i2) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (z != (this.client != ((i & 1) == 1))) {
                throw new IllegalArgumentException("payload != reply");
            }
            ByteBuffer byteBufferOrder = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.putInt(-2147287034);
            byteBufferOrder.putInt(4);
            byteBufferOrder.putInt(i);
            byteBufferOrder.flip();
            this.sink.write(this.frameHeader.addAll(byteBufferOrder));
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void goAway(int i, ErrorCode errorCode, byte[] bArr) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (errorCode.spdyGoAwayCode == -1) {
                throw new IllegalArgumentException("errorCode.spdyGoAwayCode == -1");
            }
            ByteBuffer byteBufferOrder = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.putInt(-2147287033);
            byteBufferOrder.putInt(8);
            byteBufferOrder.putInt(i);
            byteBufferOrder.putInt(errorCode.spdyGoAwayCode);
            byteBufferOrder.flip();
            this.sink.write(this.frameHeader.addAll(byteBufferOrder));
        }

        @Override // com.koushikdutta.async.http.spdy.FrameWriter
        public synchronized void windowUpdate(int i, long j) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (j == 0 || j > 2147483647L) {
                throw new IllegalArgumentException("windowSizeIncrement must be between 1 and 0x7fffffff: " + j);
            }
            ByteBuffer byteBufferOrder = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.putInt(-2147287031);
            byteBufferOrder.putInt(8);
            byteBufferOrder.putInt(i);
            byteBufferOrder.putInt((int) j);
            byteBufferOrder.flip();
            this.sink.write(this.frameHeader.addAll(byteBufferOrder));
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public synchronized void close() throws IOException {
            this.closed = true;
        }
    }
}
