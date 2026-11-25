package com.huawei.hms.mlsdk.asr.engine;

import android.media.AudioRecord;
import android.os.Bundle;
import android.os.SystemClock;
import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import com.huawei.hms.mlsdk.asr.engine.utils.SmartLogger;
import com.huawei.hms.mlsdk.tts.MLTtsAudioFragment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: compiled from: AsrRecorder.java */
/* JADX INFO: loaded from: classes10.dex */
public class c {
    private Lock a;
    private Condition b;
    private List<InterfaceC0119c> c;
    private byte[] d;
    private final AtomicBoolean e;
    private final AtomicBoolean f;
    private final AtomicBoolean g;
    private AudioRecord h;
    private Thread i;

    /* JADX INFO: compiled from: AsrRecorder.java */
    private class b implements Runnable {
        /* synthetic */ b(a aVar) {
        }

        @Override // java.lang.Runnable
        public void run() {
            AtomicBoolean atomicBoolean;
            while (c.this.e.get()) {
                c.this.a.lock();
                try {
                    int i = c.this.h.read(c.this.d, 0, c.this.d.length);
                    SmartLogger.d("AsrRecorder", "audioRecord read buffer");
                    if (i != -3 && i != -1 && i != -2) {
                        if (!c.this.f.get()) {
                            try {
                                try {
                                    c.this.g.set(true);
                                    SmartLogger.d("AsrRecorder", "InnerRecordRunnable waiting");
                                    c.this.b.await();
                                    SmartLogger.d("AsrRecorder", "InnerRecordRunnable awake");
                                    atomicBoolean = c.this.g;
                                } catch (InterruptedException e) {
                                    SmartLogger.e("AsrRecorder", "await failed" + e.getMessage());
                                    atomicBoolean = c.this.g;
                                }
                                atomicBoolean.set(false);
                            } catch (Throwable th) {
                                c.this.g.set(false);
                                throw th;
                            }
                        }
                        for (InterfaceC0119c interfaceC0119c : c.this.c) {
                            byte[] bArr = c.this.d;
                            AsrEngine.c cVar = (AsrEngine.c) interfaceC0119c;
                            if (AsrEngine.this.mAsrVadDetector != null) {
                                AsrEngine.this.mAsrVadDetector.a(bArr);
                            }
                        }
                    }
                    c.this.a.unlock();
                } catch (Throwable th2) {
                    c.this.a.unlock();
                    throw th2;
                }
            }
            SmartLogger.i("AsrRecorder", "InnerRecordRunnable is over");
        }
    }

    /* JADX INFO: renamed from: com.huawei.hms.mlsdk.asr.engine.c$c, reason: collision with other inner class name */
    /* JADX INFO: compiled from: AsrRecorder.java */
    public interface InterfaceC0119c {
    }

    public c() {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.a = reentrantLock;
        this.b = reentrantLock.newCondition();
        this.c = new ArrayList();
        this.e = new AtomicBoolean(false);
        this.f = new AtomicBoolean(false);
        this.g = new AtomicBoolean(false);
        this.a.lock();
        try {
            int minBufferSize = AudioRecord.getMinBufferSize(MLTtsAudioFragment.SAMPLE_RATE_16K, 16, 2);
            AsrConstants.bufferSize = minBufferSize;
            this.d = new byte[minBufferSize];
            AudioRecord audioRecord = new AudioRecord(1, MLTtsAudioFragment.SAMPLE_RATE_16K, 16, 2, minBufferSize);
            this.h = audioRecord;
            if (audioRecord.getState() == 1) {
                this.h.startRecording();
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putInt(MLAsrCaptureConstants.ASR_SUB_ERROR_CODE, 1001);
            for (InterfaceC0119c interfaceC0119c : this.c) {
                AsrEngine.c cVar = (AsrEngine.c) interfaceC0119c;
                AsrEngine.this.posterExecutor.execute(new AsrEngine.i(AsrEngine.this.callback, new AsrError(40, "Failed to init audio recorder", bundle), AsrEngine.this.mAsrResult));
                SmartLogger.d("AsrEngine", "onError(AsrError error)");
            }
        } finally {
            this.a.unlock();
        }
    }

    private void c() {
        this.a.lock();
        try {
            this.b.signal();
        } finally {
            this.a.unlock();
        }
    }

    public void a(InterfaceC0119c interfaceC0119c) {
        this.c.add(interfaceC0119c);
    }

    public void b() {
        if (this.i == null) {
            a(true);
            this.e.set(true);
            Thread thread = new Thread(new b(null), "InnerRecordRunnable");
            this.i = thread;
            thread.start();
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        Iterator<InterfaceC0119c> it = this.c.iterator();
        while (it.hasNext()) {
            AsrEngine.c cVar = (AsrEngine.c) it.next();
            AsrEngine.this.recordStartTime = Long.valueOf(jElapsedRealtime);
            AsrEngine.this.posterExecutor.execute(new AsrEngine.i(AsrEngine.this.callback, 1));
            SmartLogger.d("AsrEngine", "onStart(long time)");
        }
    }

    public void a(boolean z) {
        this.a.lock();
        try {
            this.f.set(z);
            if (this.g.get()) {
                c();
            }
        } finally {
            this.a.unlock();
        }
    }

    public void a() {
        this.e.set(false);
        a(false);
        c();
        try {
            AudioRecord audioRecord = this.h;
            if (audioRecord != null) {
                audioRecord.stop();
            }
            AudioRecord audioRecord2 = this.h;
            if (audioRecord2 != null) {
                audioRecord2.release();
            }
            this.h = null;
        } catch (IllegalStateException e) {
            SmartLogger.e("AsrRecorder", com.huawei.hms.mlsdk.asr.o.a.a("IllegalStateException:").append(e.getMessage()).toString());
        }
    }
}
