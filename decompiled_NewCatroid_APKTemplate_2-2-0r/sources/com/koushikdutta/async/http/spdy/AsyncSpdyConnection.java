package com.koushikdutta.async.http.spdy;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.BufferedDataSink;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.WritableCallback;
import com.koushikdutta.async.future.SimpleFuture;
import com.koushikdutta.async.http.Protocol;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public class AsyncSpdyConnection implements FrameReader.Handler {
    private static final int OKHTTP_CLIENT_WINDOW_SIZE = 16777216;
    BufferedDataSink bufferedSocket;
    long bytesLeftInWriteWindow;
    private int lastGoodStreamId;
    private int nextPingId;
    private int nextStreamId;
    final Settings okHttpSettings;
    Settings peerSettings;
    private Map<Integer, Ping> pings;
    Protocol protocol;
    FrameReader reader;
    private boolean receivedInitialPeerSettings;
    boolean shutdown;
    AsyncSocket socket;
    int totalWindowRead;
    Variant variant;
    FrameWriter writer;
    Hashtable<Integer, SpdySocket> sockets = new Hashtable<>();
    boolean client = true;

    @Override // com.koushikdutta.async.http.spdy.FrameReader.Handler
    public void alternateService(int i, String str, ByteString byteString, String str2, int i2, long j) {
    }

    @Override // com.koushikdutta.async.http.spdy.FrameReader.Handler
    public void priority(int i, int i2, int i3, boolean z) {
    }

    public SpdySocket newStream(List<Header> list, boolean z, boolean z2) {
        return newStream(0, list, z, z2);
    }

    private SpdySocket newStream(int i, List<Header> list, boolean z, boolean z2) {
        boolean z3 = !z;
        boolean z4 = !z2;
        if (this.shutdown) {
            return null;
        }
        int i2 = this.nextStreamId;
        this.nextStreamId = i2 + 2;
        SpdySocket spdySocket = new SpdySocket(i2, z3, z4, list);
        if (spdySocket.isOpen()) {
            this.sockets.put(Integer.valueOf(i2), spdySocket);
        }
        try {
            if (i == 0) {
                this.writer.synStream(z3, z4, i2, i, list);
            } else {
                if (this.client) {
                    throw new IllegalArgumentException("client streams shouldn't have associated stream IDs");
                }
                this.writer.pushPromise(i, i2, list);
            }
            return spdySocket;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    void updateWindowRead(int i) {
        int i2 = this.totalWindowRead + i;
        this.totalWindowRead = i2;
        if (i2 >= this.okHttpSettings.getInitialWindowSize(65536) / 2) {
            try {
                this.writer.windowUpdate(0, this.totalWindowRead);
                this.totalWindowRead = 0;
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }
    }

    public class SpdySocket implements AsyncSocket {
        long bytesLeftInWriteWindow;
        CompletedCallback closedCallback;
        DataCallback dataCallback;
        CompletedCallback endCallback;

        /* JADX INFO: renamed from: id, reason: collision with root package name */
        final int f214id;
        boolean paused;
        int totalWindowRead;
        WritableCallback writable;
        ByteBufferList pending = new ByteBufferList();
        SimpleFuture<List<Header>> headers = new SimpleFuture<>();
        boolean isOpen = true;
        ByteBufferList writing = new ByteBufferList();

        @Override // com.koushikdutta.async.DataEmitter
        public String charset() {
            return null;
        }

        @Override // com.koushikdutta.async.DataEmitter
        public boolean isChunked() {
            return false;
        }

        public AsyncSpdyConnection getConnection() {
            return AsyncSpdyConnection.this;
        }

        public SimpleFuture<List<Header>> headers() {
            return this.headers;
        }

        void updateWindowRead(int i) {
            int i2 = this.totalWindowRead + i;
            this.totalWindowRead = i2;
            if (i2 >= AsyncSpdyConnection.this.okHttpSettings.getInitialWindowSize(65536) / 2) {
                try {
                    AsyncSpdyConnection.this.writer.windowUpdate(this.f214id, this.totalWindowRead);
                    this.totalWindowRead = 0;
                } catch (IOException e) {
                    throw new AssertionError(e);
                }
            }
            AsyncSpdyConnection.this.updateWindowRead(i);
        }

        public SpdySocket(int i, boolean z, boolean z2, List<Header> list) {
            this.bytesLeftInWriteWindow = AsyncSpdyConnection.this.peerSettings.getInitialWindowSize(65536);
            this.f214id = i;
        }

        public boolean isLocallyInitiated() {
            return AsyncSpdyConnection.this.client == ((this.f214id & 1) == 1);
        }

        public void addBytesToWriteWindow(long j) {
            long j2 = this.bytesLeftInWriteWindow;
            long j3 = j + j2;
            this.bytesLeftInWriteWindow = j3;
            if (j3 <= 0 || j2 > 0) {
                return;
            }
            com.koushikdutta.async.Util.writable(this.writable);
        }

        @Override // com.koushikdutta.async.AsyncSocket, com.koushikdutta.async.DataEmitter, com.koushikdutta.async.DataSink
        public AsyncServer getServer() {
            return AsyncSpdyConnection.this.socket.getServer();
        }

        @Override // com.koushikdutta.async.DataEmitter
        public void setDataCallback(DataCallback dataCallback) {
            this.dataCallback = dataCallback;
        }

        @Override // com.koushikdutta.async.DataEmitter
        public DataCallback getDataCallback() {
            return this.dataCallback;
        }

        @Override // com.koushikdutta.async.DataEmitter
        public void pause() {
            this.paused = true;
        }

        @Override // com.koushikdutta.async.DataEmitter
        public void resume() {
            this.paused = false;
        }

        @Override // com.koushikdutta.async.DataEmitter
        public void close() {
            this.isOpen = false;
        }

        @Override // com.koushikdutta.async.DataEmitter
        public boolean isPaused() {
            return this.paused;
        }

        @Override // com.koushikdutta.async.DataEmitter
        public void setEndCallback(CompletedCallback completedCallback) {
            this.endCallback = completedCallback;
        }

        @Override // com.koushikdutta.async.DataEmitter
        public CompletedCallback getEndCallback() {
            return this.endCallback;
        }

        @Override // com.koushikdutta.async.DataSink
        public void write(ByteBufferList byteBufferList) {
            int iMin = Math.min(byteBufferList.remaining(), (int) Math.min(this.bytesLeftInWriteWindow, AsyncSpdyConnection.this.bytesLeftInWriteWindow));
            if (iMin == 0) {
                return;
            }
            if (iMin < byteBufferList.remaining()) {
                if (this.writing.hasRemaining()) {
                    throw new AssertionError("wtf");
                }
                byteBufferList.get(this.writing, iMin);
                byteBufferList = this.writing;
            }
            try {
                AsyncSpdyConnection.this.writer.data(false, this.f214id, byteBufferList);
                this.bytesLeftInWriteWindow -= (long) iMin;
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.koushikdutta.async.DataSink
        public void setWriteableCallback(WritableCallback writableCallback) {
            this.writable = writableCallback;
        }

        @Override // com.koushikdutta.async.DataSink
        public WritableCallback getWriteableCallback() {
            return this.writable;
        }

        @Override // com.koushikdutta.async.DataSink
        public boolean isOpen() {
            return this.isOpen;
        }

        @Override // com.koushikdutta.async.DataSink
        public void end() {
            try {
                AsyncSpdyConnection.this.writer.data(true, this.f214id, this.writing);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.koushikdutta.async.DataSink
        public void setClosedCallback(CompletedCallback completedCallback) {
            this.closedCallback = completedCallback;
        }

        @Override // com.koushikdutta.async.DataSink
        public CompletedCallback getClosedCallback() {
            return this.closedCallback;
        }

        public void receiveHeaders(List<Header> list, HeadersMode headersMode) {
            this.headers.setComplete(list);
        }
    }

    public AsyncSpdyConnection(AsyncSocket asyncSocket, Protocol protocol) {
        Settings settings = new Settings();
        this.okHttpSettings = settings;
        this.peerSettings = new Settings();
        this.receivedInitialPeerSettings = false;
        this.protocol = protocol;
        this.socket = asyncSocket;
        this.bufferedSocket = new BufferedDataSink(asyncSocket);
        if (protocol == Protocol.SPDY_3) {
            this.variant = new Spdy3();
        } else if (protocol == Protocol.HTTP_2) {
            this.variant = new Http20Draft13();
        }
        this.reader = this.variant.newReader(asyncSocket, this, true);
        this.writer = this.variant.newWriter(this.bufferedSocket, true);
        this.nextStreamId = 1;
        if (protocol == Protocol.HTTP_2) {
            this.nextStreamId += 2;
        }
        this.nextPingId = 1;
        settings.set(7, 0, 16777216);
    }

    public void sendConnectionPreface() throws IOException {
        this.writer.connectionPreface();
        this.writer.settings(this.okHttpSettings);
        int initialWindowSize = this.okHttpSettings.getInitialWindowSize(65536);
        if (initialWindowSize != 65536) {
            this.writer.windowUpdate(0, initialWindowSize - 65536);
        }
    }

    private boolean pushedStream(int i) {
        return this.protocol == Protocol.HTTP_2 && i != 0 && (i & 1) == 0;
    }

    @Override // com.koushikdutta.async.http.spdy.FrameReader.Handler
    public void data(boolean z, int i, ByteBufferList byteBufferList) {
        if (pushedStream(i)) {
            throw new AssertionError("push");
        }
        SpdySocket spdySocket = this.sockets.get(Integer.valueOf(i));
        if (spdySocket == null) {
            try {
                this.writer.rstStream(i, ErrorCode.INVALID_STREAM);
                byteBufferList.recycle();
                return;
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }
        int iRemaining = byteBufferList.remaining();
        byteBufferList.get(spdySocket.pending);
        spdySocket.updateWindowRead(iRemaining);
        com.koushikdutta.async.Util.emitAllData(spdySocket, spdySocket.pending);
        if (z) {
            this.sockets.remove(Integer.valueOf(i));
            spdySocket.close();
            com.koushikdutta.async.Util.end(spdySocket, (Exception) null);
        }
    }

    @Override // com.koushikdutta.async.http.spdy.FrameReader.Handler
    public void headers(boolean z, boolean z2, int i, int i2, List<Header> list, HeadersMode headersMode) {
        if (pushedStream(i)) {
            throw new AssertionError("push");
        }
        if (this.shutdown) {
            return;
        }
        SpdySocket spdySocket = this.sockets.get(Integer.valueOf(i));
        if (spdySocket == null) {
            if (headersMode.failIfStreamAbsent()) {
                try {
                    this.writer.rstStream(i, ErrorCode.INVALID_STREAM);
                    return;
                } catch (IOException e) {
                    throw new AssertionError(e);
                }
            } else {
                if (i > this.lastGoodStreamId && i % 2 != this.nextStreamId % 2) {
                    throw new AssertionError("unexpected receive stream");
                }
                return;
            }
        }
        if (headersMode.failIfStreamPresent()) {
            try {
                this.writer.rstStream(i, ErrorCode.INVALID_STREAM);
                this.sockets.remove(Integer.valueOf(i));
                return;
            } catch (IOException e2) {
                throw new AssertionError(e2);
            }
        }
        spdySocket.receiveHeaders(list, headersMode);
        if (z2) {
            this.sockets.remove(Integer.valueOf(i));
            com.koushikdutta.async.Util.end(spdySocket, (Exception) null);
        }
    }

    @Override // com.koushikdutta.async.http.spdy.FrameReader.Handler
    public void rstStream(int i, ErrorCode errorCode) {
        if (pushedStream(i)) {
            throw new AssertionError("push");
        }
        SpdySocket spdySocketRemove = this.sockets.remove(Integer.valueOf(i));
        if (spdySocketRemove != null) {
            com.koushikdutta.async.Util.end(spdySocketRemove, new IOException(errorCode.toString()));
        }
    }

    @Override // com.koushikdutta.async.http.spdy.FrameReader.Handler
    public void settings(boolean z, Settings settings) {
        long j;
        int initialWindowSize = this.peerSettings.getInitialWindowSize(65536);
        if (z) {
            this.peerSettings.clear();
        }
        this.peerSettings.merge(settings);
        try {
            this.writer.ackSettings();
            int initialWindowSize2 = this.peerSettings.getInitialWindowSize(65536);
            if (initialWindowSize2 == -1 || initialWindowSize2 == initialWindowSize) {
                j = 0;
            } else {
                j = initialWindowSize2 - initialWindowSize;
                if (!this.receivedInitialPeerSettings) {
                    addBytesToWriteWindow(j);
                    this.receivedInitialPeerSettings = true;
                }
            }
            Iterator<SpdySocket> it = this.sockets.values().iterator();
            while (it.hasNext()) {
                it.next().addBytesToWriteWindow(j);
            }
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    void addBytesToWriteWindow(long j) {
        this.bytesLeftInWriteWindow += j;
        Iterator<SpdySocket> it = this.sockets.values().iterator();
        while (it.hasNext()) {
            com.koushikdutta.async.Util.writable(it.next());
        }
    }

    @Override // com.koushikdutta.async.http.spdy.FrameReader.Handler
    public void ackSettings() {
        try {
            this.writer.ackSettings();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private void writePing(boolean z, int i, int i2, Ping ping) throws IOException {
        if (ping != null) {
            ping.send();
        }
        this.writer.ping(z, i, i2);
    }

    private synchronized Ping removePing(int i) {
        Map<Integer, Ping> map;
        map = this.pings;
        return map != null ? map.remove(Integer.valueOf(i)) : null;
    }

    @Override // com.koushikdutta.async.http.spdy.FrameReader.Handler
    public void ping(boolean z, int i, int i2) {
        if (z) {
            Ping pingRemovePing = removePing(i);
            if (pingRemovePing != null) {
                pingRemovePing.receive();
                return;
            }
            return;
        }
        try {
            writePing(true, i, i2, null);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    @Override // com.koushikdutta.async.http.spdy.FrameReader.Handler
    public void goAway(int i, ErrorCode errorCode, ByteString byteString) {
        this.shutdown = true;
        Iterator<Map.Entry<Integer, SpdySocket>> it = this.sockets.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, SpdySocket> next = it.next();
            if (next.getKey().intValue() > i && next.getValue().isLocallyInitiated()) {
                com.koushikdutta.async.Util.end(next.getValue(), new IOException(ErrorCode.REFUSED_STREAM.toString()));
                it.remove();
            }
        }
    }

    @Override // com.koushikdutta.async.http.spdy.FrameReader.Handler
    public void windowUpdate(int i, long j) {
        if (i == 0) {
            addBytesToWriteWindow(j);
            return;
        }
        SpdySocket spdySocket = this.sockets.get(Integer.valueOf(i));
        if (spdySocket != null) {
            spdySocket.addBytesToWriteWindow(j);
        }
    }

    @Override // com.koushikdutta.async.http.spdy.FrameReader.Handler
    public void pushPromise(int i, int i2, List<Header> list) {
        throw new AssertionError("pushPromise");
    }

    @Override // com.koushikdutta.async.http.spdy.FrameReader.Handler
    public void error(Exception exc) {
        this.socket.close();
        Iterator<Map.Entry<Integer, SpdySocket>> it = this.sockets.entrySet().iterator();
        while (it.hasNext()) {
            com.koushikdutta.async.Util.end(it.next().getValue(), exc);
            it.remove();
        }
    }
}
