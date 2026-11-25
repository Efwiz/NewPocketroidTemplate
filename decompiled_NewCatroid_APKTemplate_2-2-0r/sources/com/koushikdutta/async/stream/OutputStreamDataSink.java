package com.koushikdutta.async.stream;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.WritableCallback;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes10.dex */
public class OutputStreamDataSink implements DataSink {
    Exception closeException;
    boolean closeReported;
    CompletedCallback mClosedCallback;
    OutputStream mStream;
    WritableCallback mWritable;
    WritableCallback outputStreamCallback;
    AsyncServer server;

    public OutputStreamDataSink(AsyncServer asyncServer) {
        this(asyncServer, null);
    }

    @Override // com.koushikdutta.async.DataSink
    public void end() {
        try {
            OutputStream outputStream = this.mStream;
            if (outputStream != null) {
                outputStream.close();
            }
            reportClose(null);
        } catch (IOException e) {
            reportClose(e);
        }
    }

    public OutputStreamDataSink(AsyncServer asyncServer, OutputStream outputStream) {
        this.server = asyncServer;
        setOutputStream(outputStream);
    }

    public void setOutputStream(OutputStream outputStream) {
        this.mStream = outputStream;
    }

    public OutputStream getOutputStream() throws IOException {
        return this.mStream;
    }

    @Override // com.koushikdutta.async.DataSink
    public void write(ByteBufferList byteBufferList) {
        while (byteBufferList.size() > 0) {
            try {
                try {
                    ByteBuffer byteBufferRemove = byteBufferList.remove();
                    getOutputStream().write(byteBufferRemove.array(), byteBufferRemove.arrayOffset() + byteBufferRemove.position(), byteBufferRemove.remaining());
                    ByteBufferList.reclaim(byteBufferRemove);
                } catch (IOException e) {
                    reportClose(e);
                }
            } catch (Throwable th) {
                byteBufferList.recycle();
                throw th;
            }
        }
        byteBufferList.recycle();
    }

    @Override // com.koushikdutta.async.DataSink
    public void setWriteableCallback(WritableCallback writableCallback) {
        this.mWritable = writableCallback;
    }

    @Override // com.koushikdutta.async.DataSink
    public WritableCallback getWriteableCallback() {
        return this.mWritable;
    }

    @Override // com.koushikdutta.async.DataSink
    public boolean isOpen() {
        return this.closeReported;
    }

    public void reportClose(Exception exc) {
        if (this.closeReported) {
            return;
        }
        this.closeReported = true;
        this.closeException = exc;
        CompletedCallback completedCallback = this.mClosedCallback;
        if (completedCallback != null) {
            completedCallback.onCompleted(exc);
        }
    }

    @Override // com.koushikdutta.async.DataSink
    public void setClosedCallback(CompletedCallback completedCallback) {
        this.mClosedCallback = completedCallback;
    }

    @Override // com.koushikdutta.async.DataSink
    public CompletedCallback getClosedCallback() {
        return this.mClosedCallback;
    }

    @Override // com.koushikdutta.async.DataSink
    public AsyncServer getServer() {
        return this.server;
    }

    public void setOutputStreamWritableCallback(WritableCallback writableCallback) {
        this.outputStreamCallback = writableCallback;
    }
}
