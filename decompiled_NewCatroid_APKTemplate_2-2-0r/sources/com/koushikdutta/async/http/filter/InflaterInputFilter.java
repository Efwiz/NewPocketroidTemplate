package com.koushikdutta.async.http.filter;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.FilteredDataEmitter;
import com.koushikdutta.async.Util;
import java.nio.ByteBuffer;
import java.util.zip.Inflater;

/* JADX INFO: loaded from: classes10.dex */
public class InflaterInputFilter extends FilteredDataEmitter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Inflater mInflater;
    ByteBufferList transformed;

    @Override // com.koushikdutta.async.DataEmitterBase
    protected void report(Exception exc) {
        this.mInflater.end();
        if (exc != null && this.mInflater.getRemaining() > 0) {
            exc = new DataRemainingException("data still remaining in inflater", exc);
        }
        super.report(exc);
    }

    @Override // com.koushikdutta.async.FilteredDataEmitter, com.koushikdutta.async.callback.DataCallback
    public void onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList) {
        try {
            ByteBuffer byteBufferObtain = ByteBufferList.obtain(byteBufferList.remaining() * 2);
            while (byteBufferList.size() > 0) {
                ByteBuffer byteBufferRemove = byteBufferList.remove();
                if (byteBufferRemove.hasRemaining()) {
                    byteBufferRemove.remaining();
                    this.mInflater.setInput(byteBufferRemove.array(), byteBufferRemove.arrayOffset() + byteBufferRemove.position(), byteBufferRemove.remaining());
                    do {
                        byteBufferObtain.position(byteBufferObtain.position() + this.mInflater.inflate(byteBufferObtain.array(), byteBufferObtain.arrayOffset() + byteBufferObtain.position(), byteBufferObtain.remaining()));
                        if (!byteBufferObtain.hasRemaining()) {
                            byteBufferObtain.flip();
                            this.transformed.add(byteBufferObtain);
                            byteBufferObtain = ByteBufferList.obtain(byteBufferObtain.capacity() * 2);
                        }
                        if (this.mInflater.needsInput()) {
                            break;
                        }
                    } while (!this.mInflater.finished());
                }
                ByteBufferList.reclaim(byteBufferRemove);
            }
            byteBufferObtain.flip();
            this.transformed.add(byteBufferObtain);
            Util.emitAllData(this, this.transformed);
        } catch (Exception e) {
            report(e);
        }
    }

    public InflaterInputFilter() {
        this(new Inflater());
    }

    public InflaterInputFilter(Inflater inflater) {
        this.transformed = new ByteBufferList();
        this.mInflater = inflater;
    }
}
