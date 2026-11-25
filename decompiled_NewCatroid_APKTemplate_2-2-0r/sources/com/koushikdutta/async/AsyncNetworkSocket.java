package com.koushikdutta.async;

import android.util.Log;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.WritableCallback;
import com.koushikdutta.async.util.Allocator;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/* JADX INFO: loaded from: classes10.dex */
public class AsyncNetworkSocket implements AsyncSocket {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    Allocator allocator;
    boolean closeReported;
    private ChannelWrapper mChannel;
    CompletedCallback mClosedHander;
    private CompletedCallback mCompletedCallback;
    DataCallback mDataHandler;
    boolean mEndReported;
    private SelectionKey mKey;
    Exception mPendingEndException;
    private AsyncServer mServer;
    WritableCallback mWriteableHandler;
    InetSocketAddress socketAddress;
    private ByteBufferList pending = new ByteBufferList();
    boolean mPaused = false;

    @Override // com.koushikdutta.async.DataEmitter
    public String charset() {
        return null;
    }

    AsyncNetworkSocket() {
    }

    @Override // com.koushikdutta.async.DataSink
    public void end() {
        this.mChannel.shutdownOutput();
    }

    @Override // com.koushikdutta.async.DataEmitter
    public boolean isChunked() {
        return this.mChannel.isChunked();
    }

    void attach(SocketChannel socketChannel, InetSocketAddress inetSocketAddress) throws IOException {
        this.socketAddress = inetSocketAddress;
        this.allocator = new Allocator();
        this.mChannel = new SocketChannelWrapper(socketChannel);
    }

    void attach(DatagramChannel datagramChannel) throws IOException {
        this.mChannel = new DatagramChannelWrapper(datagramChannel);
        this.allocator = new Allocator(8192);
    }

    ChannelWrapper getChannel() {
        return this.mChannel;
    }

    public void onDataWritable() {
        if (!this.mChannel.isChunked()) {
            SelectionKey selectionKey = this.mKey;
            selectionKey.interestOps(selectionKey.interestOps() & (-5));
        }
        WritableCallback writableCallback = this.mWriteableHandler;
        if (writableCallback != null) {
            writableCallback.onWriteable();
        }
    }

    void setup(AsyncServer asyncServer, SelectionKey selectionKey) {
        this.mServer = asyncServer;
        this.mKey = selectionKey;
    }

    @Override // com.koushikdutta.async.DataSink
    public void write(final ByteBufferList byteBufferList) {
        if (this.mServer.getAffinity() != Thread.currentThread()) {
            this.mServer.run(new Runnable() { // from class: com.koushikdutta.async.AsyncNetworkSocket.1
                @Override // java.lang.Runnable
                public void run() {
                    AsyncNetworkSocket.this.write(byteBufferList);
                }
            });
            return;
        }
        if (this.mChannel.isConnected()) {
            try {
                int iRemaining = byteBufferList.remaining();
                ByteBuffer[] allArray = byteBufferList.getAllArray();
                this.mChannel.write(allArray);
                byteBufferList.addAll(allArray);
                handleRemaining(byteBufferList.remaining());
                this.mServer.onDataSent(iRemaining - byteBufferList.remaining());
            } catch (IOException e) {
                closeInternal();
                reportEndPending(e);
                reportClose(e);
            }
        }
    }

    private void handleRemaining(int i) throws IOException {
        if (!this.mKey.isValid()) {
            throw new IOException(new CancelledKeyException());
        }
        if (i > 0) {
            SelectionKey selectionKey = this.mKey;
            selectionKey.interestOps(selectionKey.interestOps() | 4);
        } else {
            SelectionKey selectionKey2 = this.mKey;
            selectionKey2.interestOps(selectionKey2.interestOps() & (-5));
        }
    }

    int onReadable() {
        boolean z;
        spitPending();
        int i = 0;
        if (this.mPaused) {
            return 0;
        }
        try {
            ByteBuffer byteBufferAllocate = this.allocator.allocate();
            long j = this.mChannel.read(byteBufferAllocate);
            if (j < 0) {
                closeInternal();
                z = true;
            } else {
                z = false;
                i = (int) (((long) 0) + j);
            }
            if (j > 0) {
                this.allocator.track(j);
                byteBufferAllocate.flip();
                this.pending.add(byteBufferAllocate);
                Util.emitAllData(this, this.pending);
            } else {
                ByteBufferList.reclaim(byteBufferAllocate);
            }
            if (z) {
                reportEndPending(null);
                reportClose(null);
            }
        } catch (Exception e) {
            closeInternal();
            reportEndPending(e);
            reportClose(e);
        }
        return i;
    }

    protected void reportClose(Exception exc) {
        if (this.closeReported) {
            return;
        }
        this.closeReported = true;
        CompletedCallback completedCallback = this.mClosedHander;
        if (completedCallback != null) {
            completedCallback.onCompleted(exc);
            this.mClosedHander = null;
        }
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void close() {
        closeInternal();
        reportClose(null);
    }

    public void closeInternal() {
        this.mKey.cancel();
        try {
            this.mChannel.close();
        } catch (IOException unused) {
        }
    }

    @Override // com.koushikdutta.async.DataSink
    public void setWriteableCallback(WritableCallback writableCallback) {
        this.mWriteableHandler = writableCallback;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void setDataCallback(DataCallback dataCallback) {
        this.mDataHandler = dataCallback;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public DataCallback getDataCallback() {
        return this.mDataHandler;
    }

    @Override // com.koushikdutta.async.DataSink
    public void setClosedCallback(CompletedCallback completedCallback) {
        this.mClosedHander = completedCallback;
    }

    @Override // com.koushikdutta.async.DataSink
    public CompletedCallback getClosedCallback() {
        return this.mClosedHander;
    }

    @Override // com.koushikdutta.async.DataSink
    public WritableCallback getWriteableCallback() {
        return this.mWriteableHandler;
    }

    void reportEnd(Exception exc) {
        if (this.mEndReported) {
            return;
        }
        this.mEndReported = true;
        CompletedCallback completedCallback = this.mCompletedCallback;
        if (completedCallback != null) {
            completedCallback.onCompleted(exc);
        } else if (exc != null) {
            Log.e(AsyncServer.LOGTAG, "Unhandled exception", exc);
        }
    }

    void reportEndPending(Exception exc) {
        if (this.pending.hasRemaining()) {
            this.mPendingEndException = exc;
        } else {
            reportEnd(exc);
        }
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void setEndCallback(CompletedCallback completedCallback) {
        this.mCompletedCallback = completedCallback;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public CompletedCallback getEndCallback() {
        return this.mCompletedCallback;
    }

    @Override // com.koushikdutta.async.DataSink
    public boolean isOpen() {
        return this.mChannel.isConnected() && this.mKey.isValid();
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void pause() {
        if (this.mServer.getAffinity() != Thread.currentThread()) {
            this.mServer.run(new Runnable() { // from class: com.koushikdutta.async.AsyncNetworkSocket.2
                @Override // java.lang.Runnable
                public void run() {
                    AsyncNetworkSocket.this.pause();
                }
            });
        } else {
            if (this.mPaused) {
                return;
            }
            this.mPaused = true;
            try {
                SelectionKey selectionKey = this.mKey;
                selectionKey.interestOps(selectionKey.interestOps() & (-2));
            } catch (Exception unused) {
            }
        }
    }

    private void spitPending() {
        if (this.pending.hasRemaining()) {
            Util.emitAllData(this, this.pending);
        }
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void resume() {
        if (this.mServer.getAffinity() != Thread.currentThread()) {
            this.mServer.run(new Runnable() { // from class: com.koushikdutta.async.AsyncNetworkSocket.3
                @Override // java.lang.Runnable
                public void run() {
                    AsyncNetworkSocket.this.resume();
                }
            });
            return;
        }
        if (this.mPaused) {
            this.mPaused = false;
            try {
                SelectionKey selectionKey = this.mKey;
                selectionKey.interestOps(selectionKey.interestOps() | 1);
            } catch (Exception unused) {
            }
            spitPending();
            if (isOpen()) {
                return;
            }
            reportEndPending(this.mPendingEndException);
        }
    }

    @Override // com.koushikdutta.async.DataEmitter
    public boolean isPaused() {
        return this.mPaused;
    }

    @Override // com.koushikdutta.async.AsyncSocket, com.koushikdutta.async.DataEmitter, com.koushikdutta.async.DataSink
    public AsyncServer getServer() {
        return this.mServer;
    }

    public InetSocketAddress getRemoteAddress() {
        return this.socketAddress;
    }

    public int getLocalPort() {
        return this.mChannel.getLocalPort();
    }

    public Object getSocket() {
        return getChannel().getSocket();
    }
}
