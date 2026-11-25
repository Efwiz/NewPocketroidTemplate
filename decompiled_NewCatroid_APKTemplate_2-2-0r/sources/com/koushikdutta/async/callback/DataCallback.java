package com.koushikdutta.async.callback;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;

/* JADX INFO: loaded from: classes10.dex */
public interface DataCallback {
    void onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList);

    public static class NullDataCallback implements DataCallback {
        @Override // com.koushikdutta.async.callback.DataCallback
        public void onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList) {
            byteBufferList.recycle();
        }
    }
}
