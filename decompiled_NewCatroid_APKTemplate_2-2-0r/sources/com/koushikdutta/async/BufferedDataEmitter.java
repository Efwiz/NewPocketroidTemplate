package com.koushikdutta.async;

import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;

/* JADX INFO: loaded from: classes10.dex */
public class BufferedDataEmitter implements DataEmitter {
    DataCallback mDataCallback;
    DataEmitter mEmitter;
    CompletedCallback mEndCallback;
    Exception mEndException;
    boolean mEnded = false;
    ByteBufferList mBuffers = new ByteBufferList();

    @Override // com.koushikdutta.async.DataEmitter
    public boolean isChunked() {
        return false;
    }

    public BufferedDataEmitter(DataEmitter dataEmitter) {
        this.mEmitter = dataEmitter;
        dataEmitter.setDataCallback(new DataCallback() { // from class: com.koushikdutta.async.BufferedDataEmitter.1
            @Override // com.koushikdutta.async.callback.DataCallback
            public void onDataAvailable(DataEmitter dataEmitter2, ByteBufferList byteBufferList) {
                byteBufferList.get(BufferedDataEmitter.this.mBuffers);
                BufferedDataEmitter.this.onDataAvailable();
            }
        });
        this.mEmitter.setEndCallback(new CompletedCallback() { // from class: com.koushikdutta.async.BufferedDataEmitter.2
            @Override // com.koushikdutta.async.callback.CompletedCallback
            public void onCompleted(Exception exc) {
                BufferedDataEmitter.this.mEnded = true;
                BufferedDataEmitter.this.mEndException = exc;
                if (BufferedDataEmitter.this.mBuffers.remaining() != 0 || BufferedDataEmitter.this.mEndCallback == null) {
                    return;
                }
                BufferedDataEmitter.this.mEndCallback.onCompleted(exc);
            }
        });
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void close() {
        this.mEmitter.close();
    }

    public void onDataAvailable() {
        CompletedCallback completedCallback;
        if (this.mDataCallback != null && !isPaused() && this.mBuffers.remaining() > 0) {
            this.mDataCallback.onDataAvailable(this, this.mBuffers);
        }
        if (!this.mEnded || this.mBuffers.hasRemaining() || (completedCallback = this.mEndCallback) == null) {
            return;
        }
        completedCallback.onCompleted(this.mEndException);
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void setDataCallback(DataCallback dataCallback) {
        if (this.mDataCallback != null) {
            throw new RuntimeException("Buffered Data Emitter callback may only be set once");
        }
        this.mDataCallback = dataCallback;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public DataCallback getDataCallback() {
        return this.mDataCallback;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void pause() {
        this.mEmitter.pause();
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void resume() {
        this.mEmitter.resume();
        onDataAvailable();
    }

    @Override // com.koushikdutta.async.DataEmitter
    public boolean isPaused() {
        return this.mEmitter.isPaused();
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void setEndCallback(CompletedCallback completedCallback) {
        this.mEndCallback = completedCallback;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public CompletedCallback getEndCallback() {
        return this.mEndCallback;
    }

    @Override // com.koushikdutta.async.DataEmitter, com.koushikdutta.async.DataSink
    public AsyncServer getServer() {
        return this.mEmitter.getServer();
    }

    @Override // com.koushikdutta.async.DataEmitter
    public String charset() {
        return this.mEmitter.charset();
    }
}
