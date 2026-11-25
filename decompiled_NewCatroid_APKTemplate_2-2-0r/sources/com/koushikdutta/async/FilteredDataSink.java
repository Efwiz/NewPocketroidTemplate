package com.koushikdutta.async;

/* JADX INFO: loaded from: classes10.dex */
public class FilteredDataSink extends BufferedDataSink {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public ByteBufferList filter(ByteBufferList byteBufferList) {
        return byteBufferList;
    }

    public FilteredDataSink(DataSink dataSink) {
        super(dataSink);
        setMaxBuffer(0);
    }

    @Override // com.koushikdutta.async.BufferedDataSink, com.koushikdutta.async.DataSink
    public final void write(ByteBufferList byteBufferList) {
        if (!isBuffering() || getMaxBuffer() == Integer.MAX_VALUE) {
            super.write(filter(byteBufferList), true);
            if (byteBufferList != null) {
                byteBufferList.recycle();
            }
        }
    }
}
