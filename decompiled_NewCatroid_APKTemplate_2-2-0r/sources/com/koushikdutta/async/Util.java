package com.koushikdutta.async;

import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.WritableCallback;
import com.koushikdutta.async.util.Allocator;
import com.koushikdutta.async.util.StreamUtility;
import com.koushikdutta.async.wrapper.AsyncSocketWrapper;
import com.koushikdutta.async.wrapper.DataEmitterWrapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes10.dex */
public class Util {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static boolean SUPRESS_DEBUG_EXCEPTIONS = false;

    public static void emitAllData(DataEmitter dataEmitter, ByteBufferList byteBufferList) {
        int iRemaining;
        DataCallback dataCallback = null;
        while (!dataEmitter.isPaused() && (dataCallback = dataEmitter.getDataCallback()) != null && (iRemaining = byteBufferList.remaining()) > 0) {
            dataCallback.onDataAvailable(dataEmitter, byteBufferList);
            if (iRemaining == byteBufferList.remaining() && dataCallback == dataEmitter.getDataCallback() && !dataEmitter.isPaused()) {
                System.out.println("handler: " + dataCallback);
                byteBufferList.recycle();
                if (!SUPRESS_DEBUG_EXCEPTIONS) {
                    throw new RuntimeException("mDataHandler failed to consume data, yet remains the mDataHandler.");
                }
                return;
            }
        }
        if (byteBufferList.remaining() == 0 || dataEmitter.isPaused()) {
            return;
        }
        System.out.println("handler: " + dataCallback);
        System.out.println("emitter: " + dataEmitter);
        byteBufferList.recycle();
        if (!SUPRESS_DEBUG_EXCEPTIONS) {
            throw new RuntimeException("Not all data was consumed by Util.emitAllData");
        }
    }

    public static void pump(InputStream inputStream, DataSink dataSink, CompletedCallback completedCallback) {
        pump(inputStream, 2147483647L, dataSink, completedCallback);
    }

    public static void pump(final InputStream inputStream, final long j, final DataSink dataSink, final CompletedCallback completedCallback) {
        final CompletedCallback completedCallback2 = new CompletedCallback() { // from class: com.koushikdutta.async.Util.1
            boolean reported;

            @Override // com.koushikdutta.async.callback.CompletedCallback
            public void onCompleted(Exception exc) {
                if (this.reported) {
                    return;
                }
                this.reported = true;
                completedCallback.onCompleted(exc);
            }
        };
        WritableCallback writableCallback = new WritableCallback() { // from class: com.koushikdutta.async.Util.2
            int totalRead = 0;
            ByteBufferList pending = new ByteBufferList();
            Allocator allocator = new Allocator();

            private void cleanup() {
                dataSink.setClosedCallback(null);
                dataSink.setWriteableCallback(null);
                this.pending.recycle();
                StreamUtility.closeQuietly(inputStream);
            }

            @Override // com.koushikdutta.async.callback.WritableCallback
            public void onWriteable() {
                do {
                    try {
                        if (!this.pending.hasRemaining()) {
                            ByteBuffer byteBufferAllocate = this.allocator.allocate();
                            int i = inputStream.read(byteBufferAllocate.array(), 0, (int) Math.min(j - ((long) this.totalRead), byteBufferAllocate.capacity()));
                            if (i != -1 && this.totalRead != j) {
                                this.allocator.track(i);
                                this.totalRead += i;
                                byteBufferAllocate.position(0);
                                byteBufferAllocate.limit(i);
                                this.pending.add(byteBufferAllocate);
                            }
                            cleanup();
                            completedCallback2.onCompleted(null);
                            return;
                        }
                        dataSink.write(this.pending);
                    } catch (Exception e) {
                        cleanup();
                        completedCallback2.onCompleted(e);
                        return;
                    }
                } while (!this.pending.hasRemaining());
            }
        };
        dataSink.setWriteableCallback(writableCallback);
        dataSink.setClosedCallback(completedCallback2);
        writableCallback.onWriteable();
    }

    public static void pump(final DataEmitter dataEmitter, final DataSink dataSink, final CompletedCallback completedCallback) {
        dataEmitter.setDataCallback(new DataCallback() { // from class: com.koushikdutta.async.Util.3
            @Override // com.koushikdutta.async.callback.DataCallback
            public void onDataAvailable(DataEmitter dataEmitter2, ByteBufferList byteBufferList) {
                dataSink.write(byteBufferList);
                if (byteBufferList.remaining() > 0) {
                    dataEmitter2.pause();
                }
            }
        });
        dataSink.setWriteableCallback(new WritableCallback() { // from class: com.koushikdutta.async.Util.4
            @Override // com.koushikdutta.async.callback.WritableCallback
            public void onWriteable() {
                dataEmitter.resume();
            }
        });
        final CompletedCallback completedCallback2 = new CompletedCallback() { // from class: com.koushikdutta.async.Util.5
            boolean reported;

            @Override // com.koushikdutta.async.callback.CompletedCallback
            public void onCompleted(Exception exc) {
                if (this.reported) {
                    return;
                }
                this.reported = true;
                dataEmitter.setDataCallback(null);
                dataEmitter.setEndCallback(null);
                dataSink.setClosedCallback(null);
                dataSink.setWriteableCallback(null);
                completedCallback.onCompleted(exc);
            }
        };
        dataEmitter.setEndCallback(completedCallback2);
        dataSink.setClosedCallback(new CompletedCallback() { // from class: com.koushikdutta.async.Util.6
            @Override // com.koushikdutta.async.callback.CompletedCallback
            public void onCompleted(Exception exc) {
                if (exc == null) {
                    exc = new IOException("sink was closed before emitter ended");
                }
                completedCallback2.onCompleted(exc);
            }
        });
    }

    public static void stream(AsyncSocket asyncSocket, AsyncSocket asyncSocket2, CompletedCallback completedCallback) {
        pump(asyncSocket, asyncSocket2, completedCallback);
        pump(asyncSocket2, asyncSocket, completedCallback);
    }

    public static void pump(File file, DataSink dataSink, final CompletedCallback completedCallback) {
        try {
            if (file == null || dataSink == null) {
                completedCallback.onCompleted(null);
            } else {
                final FileInputStream fileInputStream = new FileInputStream(file);
                pump(fileInputStream, dataSink, new CompletedCallback() { // from class: com.koushikdutta.async.Util.7
                    @Override // com.koushikdutta.async.callback.CompletedCallback
                    public void onCompleted(Exception exc) {
                        try {
                            fileInputStream.close();
                            completedCallback.onCompleted(exc);
                        } catch (IOException e) {
                            completedCallback.onCompleted(e);
                        }
                    }
                });
            }
        } catch (Exception e) {
            completedCallback.onCompleted(e);
        }
    }

    public static void writeAll(final DataSink dataSink, final ByteBufferList byteBufferList, final CompletedCallback completedCallback) {
        WritableCallback writableCallback = new WritableCallback() { // from class: com.koushikdutta.async.Util.8
            @Override // com.koushikdutta.async.callback.WritableCallback
            public void onWriteable() {
                dataSink.write(byteBufferList);
                if (byteBufferList.remaining() != 0 || completedCallback == null) {
                    return;
                }
                dataSink.setWriteableCallback(null);
                completedCallback.onCompleted(null);
            }
        };
        dataSink.setWriteableCallback(writableCallback);
        writableCallback.onWriteable();
    }

    public static void writeAll(DataSink dataSink, byte[] bArr, CompletedCallback completedCallback) {
        ByteBuffer byteBufferObtain = ByteBufferList.obtain(bArr.length);
        byteBufferObtain.put(bArr);
        byteBufferObtain.flip();
        ByteBufferList byteBufferList = new ByteBufferList();
        byteBufferList.add(byteBufferObtain);
        writeAll(dataSink, byteBufferList, completedCallback);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [T extends com.koushikdutta.async.AsyncSocket, com.koushikdutta.async.AsyncSocket, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v4, types: [T extends com.koushikdutta.async.AsyncSocket, com.koushikdutta.async.AsyncSocket, java.lang.Object] */
    public static <T extends AsyncSocket> T getWrappedSocket(AsyncSocket asyncSocket, Class<T> cls) {
        if (cls.isInstance(asyncSocket)) {
            return asyncSocket;
        }
        while (asyncSocket instanceof AsyncSocketWrapper) {
            asyncSocket = (T) ((AsyncSocketWrapper) asyncSocket).getSocket();
            if (cls.isInstance(asyncSocket)) {
                return asyncSocket;
            }
        }
        return null;
    }

    public static DataEmitter getWrappedDataEmitter(DataEmitter dataEmitter, Class cls) {
        if (cls.isInstance(dataEmitter)) {
            return dataEmitter;
        }
        while (dataEmitter instanceof DataEmitterWrapper) {
            dataEmitter = ((AsyncSocketWrapper) dataEmitter).getSocket();
            if (cls.isInstance(dataEmitter)) {
                return dataEmitter;
            }
        }
        return null;
    }

    public static void end(DataEmitter dataEmitter, Exception exc) {
        if (dataEmitter == null) {
            return;
        }
        end(dataEmitter.getEndCallback(), exc);
    }

    public static void end(CompletedCallback completedCallback, Exception exc) {
        if (completedCallback != null) {
            completedCallback.onCompleted(exc);
        }
    }

    public static void writable(DataSink dataSink) {
        if (dataSink == null) {
            return;
        }
        writable(dataSink.getWriteableCallback());
    }

    public static void writable(WritableCallback writableCallback) {
        if (writableCallback != null) {
            writableCallback.onWriteable();
        }
    }
}
