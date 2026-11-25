package com.koushikdutta.async.stream;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes10.dex */
public class InputStreamDataEmitter implements DataEmitter {
    DataCallback callback;
    CompletedCallback endCallback;
    InputStream inputStream;
    boolean paused;
    AsyncServer server;
    int mToAlloc = 0;
    ByteBufferList pending = new ByteBufferList();
    Runnable pumper = new Runnable() { // from class: com.koushikdutta.async.stream.InputStreamDataEmitter.2
        @Override // java.lang.Runnable
        public void run() {
            try {
                if (!InputStreamDataEmitter.this.pending.isEmpty()) {
                    InputStreamDataEmitter.this.getServer().run(new Runnable() { // from class: com.koushikdutta.async.stream.InputStreamDataEmitter.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Util.emitAllData(InputStreamDataEmitter.this, InputStreamDataEmitter.this.pending);
                        }
                    });
                    if (!InputStreamDataEmitter.this.pending.isEmpty()) {
                        return;
                    }
                }
                do {
                    ByteBuffer byteBufferObtain = ByteBufferList.obtain(Math.min(Math.max(InputStreamDataEmitter.this.mToAlloc, 4096), 262144));
                    int i = InputStreamDataEmitter.this.inputStream.read(byteBufferObtain.array());
                    if (-1 == i) {
                        InputStreamDataEmitter.this.report(null);
                        return;
                    }
                    InputStreamDataEmitter.this.mToAlloc = i * 2;
                    byteBufferObtain.limit(i);
                    InputStreamDataEmitter.this.pending.add(byteBufferObtain);
                    InputStreamDataEmitter.this.getServer().run(new Runnable() { // from class: com.koushikdutta.async.stream.InputStreamDataEmitter.2.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Util.emitAllData(InputStreamDataEmitter.this, InputStreamDataEmitter.this.pending);
                        }
                    });
                    if (InputStreamDataEmitter.this.pending.remaining() != 0) {
                        return;
                    }
                } while (!InputStreamDataEmitter.this.isPaused());
            } catch (Exception e) {
                InputStreamDataEmitter.this.report(e);
            }
        }
    };

    @Override // com.koushikdutta.async.DataEmitter
    public String charset() {
        return null;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public boolean isChunked() {
        return false;
    }

    public InputStreamDataEmitter(AsyncServer asyncServer, InputStream inputStream) {
        this.server = asyncServer;
        this.inputStream = inputStream;
        doResume();
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void setDataCallback(DataCallback dataCallback) {
        this.callback = dataCallback;
    }

    @Override // com.koushikdutta.async.DataEmitter
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

    /* JADX INFO: Access modifiers changed from: private */
    public void report(final Exception exc) {
        getServer().post(new Runnable() { // from class: com.koushikdutta.async.stream.InputStreamDataEmitter.1
            @Override // java.lang.Runnable
            public void run() {
                Exception e = exc;
                try {
                    InputStreamDataEmitter.this.inputStream.close();
                } catch (Exception e2) {
                    e = e2;
                }
                if (InputStreamDataEmitter.this.endCallback != null) {
                    InputStreamDataEmitter.this.endCallback.onCompleted(e);
                }
            }
        });
    }

    private void doResume() {
        new Thread(this.pumper).start();
    }

    @Override // com.koushikdutta.async.DataEmitter
    public boolean isPaused() {
        return this.paused;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void setEndCallback(CompletedCallback completedCallback) {
        this.endCallback = completedCallback;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public CompletedCallback getEndCallback() {
        return this.endCallback;
    }

    @Override // com.koushikdutta.async.DataEmitter, com.koushikdutta.async.DataSink
    public AsyncServer getServer() {
        return this.server;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void close() {
        report(null);
        try {
            this.inputStream.close();
        } catch (Exception unused) {
        }
    }
}
