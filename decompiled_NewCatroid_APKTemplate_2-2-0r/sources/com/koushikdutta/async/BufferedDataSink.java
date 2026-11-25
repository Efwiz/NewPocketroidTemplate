package com.koushikdutta.async;

import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.WritableCallback;

/* JADX INFO: loaded from: classes10.dex */
public class BufferedDataSink implements DataSink {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    boolean endPending;
    boolean forceBuffering;
    DataSink mDataSink;
    WritableCallback mWritable;
    ByteBufferList mPendingWrites = new ByteBufferList();
    int mMaxBuffer = Integer.MAX_VALUE;

    public BufferedDataSink(DataSink dataSink) {
        setDataSink(dataSink);
    }

    public boolean isBuffering() {
        return this.mPendingWrites.hasRemaining() || this.forceBuffering;
    }

    public DataSink getDataSink() {
        return this.mDataSink;
    }

    public void forceBuffering(boolean z) {
        this.forceBuffering = z;
        if (z) {
            return;
        }
        writePending();
    }

    public void setDataSink(DataSink dataSink) {
        this.mDataSink = dataSink;
        dataSink.setWriteableCallback(new WritableCallback() { // from class: com.koushikdutta.async.BufferedDataSink.1
            @Override // com.koushikdutta.async.callback.WritableCallback
            public void onWriteable() {
                BufferedDataSink.this.writePending();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writePending() {
        WritableCallback writableCallback;
        if (this.forceBuffering) {
            return;
        }
        if (this.mPendingWrites.hasRemaining()) {
            this.mDataSink.write(this.mPendingWrites);
            if (this.mPendingWrites.remaining() == 0 && this.endPending) {
                this.mDataSink.end();
            }
        }
        if (this.mPendingWrites.hasRemaining() || (writableCallback = this.mWritable) == null) {
            return;
        }
        writableCallback.onWriteable();
    }

    @Override // com.koushikdutta.async.DataSink
    public void write(ByteBufferList byteBufferList) {
        write(byteBufferList, false);
    }

    protected void write(final ByteBufferList byteBufferList, final boolean z) {
        if (getServer().getAffinity() != Thread.currentThread()) {
            getServer().run(new Runnable() { // from class: com.koushikdutta.async.BufferedDataSink.2
                @Override // java.lang.Runnable
                public void run() {
                    BufferedDataSink.this.write(byteBufferList, z);
                }
            });
            return;
        }
        if (!isBuffering()) {
            this.mDataSink.write(byteBufferList);
        }
        if (byteBufferList.remaining() > 0) {
            int iMin = Math.min(byteBufferList.remaining(), this.mMaxBuffer);
            if (z) {
                iMin = byteBufferList.remaining();
            }
            if (iMin > 0) {
                byteBufferList.get(this.mPendingWrites, iMin);
            }
        }
    }

    @Override // com.koushikdutta.async.DataSink
    public void setWriteableCallback(WritableCallback writableCallback) {
        this.mWritable = writableCallback;
    }

    @Override // com.koushikdutta.async.DataSink
    public WritableCallback getWriteableCallback() {
        return this.mWritable;
    }

    public int remaining() {
        return this.mPendingWrites.remaining();
    }

    public int getMaxBuffer() {
        return this.mMaxBuffer;
    }

    public void setMaxBuffer(int i) {
        this.mMaxBuffer = i;
    }

    @Override // com.koushikdutta.async.DataSink
    public boolean isOpen() {
        return this.mDataSink.isOpen();
    }

    @Override // com.koushikdutta.async.DataSink
    public void end() {
        if (getServer().getAffinity() != Thread.currentThread()) {
            getServer().run(new Runnable() { // from class: com.koushikdutta.async.BufferedDataSink.3
                @Override // java.lang.Runnable
                public void run() {
                    BufferedDataSink.this.end();
                }
            });
        } else if (this.mPendingWrites.hasRemaining()) {
            this.endPending = true;
        } else {
            this.mDataSink.end();
        }
    }

    @Override // com.koushikdutta.async.DataSink
    public void setClosedCallback(CompletedCallback completedCallback) {
        this.mDataSink.setClosedCallback(completedCallback);
    }

    @Override // com.koushikdutta.async.DataSink
    public CompletedCallback getClosedCallback() {
        return this.mDataSink.getClosedCallback();
    }

    @Override // com.koushikdutta.async.DataSink
    public AsyncServer getServer() {
        return this.mDataSink.getServer();
    }
}
