package com.koushikdutta.async;

import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;

/* JADX INFO: loaded from: classes10.dex */
public abstract class DataEmitterBase implements DataEmitter {
    CompletedCallback endCallback;
    private boolean ended;
    DataCallback mDataCallback;

    @Override // com.koushikdutta.async.DataEmitter
    public String charset() {
        return null;
    }

    protected void report(Exception exc) {
        if (this.ended) {
            return;
        }
        this.ended = true;
        if (getEndCallback() != null) {
            getEndCallback().onCompleted(exc);
        }
    }

    @Override // com.koushikdutta.async.DataEmitter
    public final void setEndCallback(CompletedCallback completedCallback) {
        this.endCallback = completedCallback;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public final CompletedCallback getEndCallback() {
        return this.endCallback;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void setDataCallback(DataCallback dataCallback) {
        this.mDataCallback = dataCallback;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public DataCallback getDataCallback() {
        return this.mDataCallback;
    }
}
