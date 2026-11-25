package com.koushikdutta.async;

import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.util.StreamUtility;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/* JADX INFO: loaded from: classes10.dex */
public class FileDataEmitter extends DataEmitterBase {
    DataCallback callback;
    FileChannel channel;
    File file;
    boolean paused;
    ByteBufferList pending = new ByteBufferList();
    Runnable pumper = new Runnable() { // from class: com.koushikdutta.async.FileDataEmitter.1
        @Override // java.lang.Runnable
        public void run() {
            try {
                if (FileDataEmitter.this.channel == null) {
                    FileDataEmitter.this.channel = new FileInputStream(FileDataEmitter.this.file).getChannel();
                }
                if (!FileDataEmitter.this.pending.isEmpty()) {
                    FileDataEmitter fileDataEmitter = FileDataEmitter.this;
                    Util.emitAllData(fileDataEmitter, fileDataEmitter.pending);
                    if (!FileDataEmitter.this.pending.isEmpty()) {
                        return;
                    }
                }
                do {
                    ByteBuffer byteBufferObtain = ByteBufferList.obtain(8192);
                    if (-1 == FileDataEmitter.this.channel.read(byteBufferObtain)) {
                        FileDataEmitter.this.report(null);
                        return;
                    }
                    byteBufferObtain.flip();
                    FileDataEmitter.this.pending.add(byteBufferObtain);
                    FileDataEmitter fileDataEmitter2 = FileDataEmitter.this;
                    Util.emitAllData(fileDataEmitter2, fileDataEmitter2.pending);
                    if (FileDataEmitter.this.pending.remaining() != 0) {
                        return;
                    }
                } while (!FileDataEmitter.this.isPaused());
            } catch (Exception e) {
                FileDataEmitter.this.report(e);
            }
        }
    };
    AsyncServer server;

    @Override // com.koushikdutta.async.DataEmitter
    public boolean isChunked() {
        return false;
    }

    public FileDataEmitter(AsyncServer asyncServer, File file) {
        this.server = asyncServer;
        this.file = file;
        boolean zIsAffinityThread = asyncServer.isAffinityThread();
        this.paused = !zIsAffinityThread;
        if (zIsAffinityThread) {
            doResume();
        }
    }

    @Override // com.koushikdutta.async.DataEmitterBase, com.koushikdutta.async.DataEmitter
    public void setDataCallback(DataCallback dataCallback) {
        this.callback = dataCallback;
    }

    @Override // com.koushikdutta.async.DataEmitterBase, com.koushikdutta.async.DataEmitter
    public DataCallback getDataCallback() {
        return this.callback;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void pause() {
        this.paused = true;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void resume() {
        this.paused = false;
        doResume();
    }

    @Override // com.koushikdutta.async.DataEmitterBase
    protected void report(Exception exc) {
        StreamUtility.closeQuietly(this.channel);
        super.report(exc);
    }

    private void doResume() {
        this.server.post(this.pumper);
    }

    @Override // com.koushikdutta.async.DataEmitter
    public boolean isPaused() {
        return this.paused;
    }

    @Override // com.koushikdutta.async.DataEmitter, com.koushikdutta.async.DataSink
    public AsyncServer getServer() {
        return this.server;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void close() {
        try {
            this.channel.close();
        } catch (Exception unused) {
        }
    }
}
