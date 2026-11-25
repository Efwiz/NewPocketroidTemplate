package com.koushikdutta.async;

import com.koushikdutta.async.callback.DataCallback;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes10.dex */
public class LineEmitter implements DataCallback {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    Charset charset;
    ByteBufferList data;
    StringCallback mLineCallback;

    public interface StringCallback {
        void onStringAvailable(String str);
    }

    public LineEmitter() {
        this(null);
    }

    public LineEmitter(Charset charset) {
        this.data = new ByteBufferList();
        this.charset = charset;
    }

    public void setLineCallback(StringCallback stringCallback) {
        this.mLineCallback = stringCallback;
    }

    public StringCallback getLineCallback() {
        return this.mLineCallback;
    }

    @Override // com.koushikdutta.async.callback.DataCallback
    public void onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(byteBufferList.remaining());
        while (byteBufferList.remaining() > 0) {
            byte b = byteBufferList.get();
            if (b == 10) {
                byteBufferAllocate.flip();
                this.data.add(byteBufferAllocate);
                this.mLineCallback.onStringAvailable(this.data.readString(this.charset));
                this.data = new ByteBufferList();
                return;
            }
            byteBufferAllocate.put(b);
        }
        byteBufferAllocate.flip();
        this.data.add(byteBufferAllocate);
    }
}
