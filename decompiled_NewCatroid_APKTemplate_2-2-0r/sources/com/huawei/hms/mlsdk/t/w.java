package com.huawei.hms.mlsdk.t;

import android.media.AudioTrack;
import android.media.audiofx.LoudnessEnhancer;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import com.huawei.hms.mlsdk.tts.MLTtsError;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: compiled from: AudioPlayer.java */
/* JADX INFO: loaded from: classes10.dex */
public class w implements a0 {
    private c a;
    private int b;
    private AudioTrack c;
    private LoudnessEnhancer d;
    private d0 e;
    private e0 f;
    private volatile DataInputStream g;
    private String i;
    private Lock v;
    private Condition w;
    private boolean x;
    private HandlerThread y;
    private Handler z;
    private volatile Queue<y> h = new LinkedBlockingQueue();
    private int j = 0;
    private int k = 0;
    private int l = 0;
    private int m = 0;
    private volatile Map<String, Integer> n = new LinkedHashMap();
    private volatile Queue<c0> o = new LinkedList();
    private boolean p = true;
    private int q = 0;
    private boolean r = false;
    private volatile int s = 0;
    private volatile int t = 0;
    private volatile boolean u = false;

    /* JADX INFO: compiled from: AudioPlayer.java */
    class a extends Handler {
        a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            w.this.c.setNotificationMarkerPosition(0);
            w.this.z.removeMessages(1);
            Bundle bundle = (Bundle) message.obj;
            w.this.a(bundle.getInt("position"), bundle.getString("taskid"));
        }
    }

    /* JADX INFO: compiled from: AudioPlayer.java */
    class b extends Handler {
        b() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            i0.c("AudioPlayer", com.huawei.hms.mlsdk.t.a.a("isNeedPlaying: ").append(w.this.p).toString());
            while (w.this.p && !w.this.u) {
                w.this.v.lock();
                while (true) {
                    try {
                        if ((!w.this.h.isEmpty() && !w.this.r) || w.this.u) {
                            break;
                        }
                        try {
                            w.this.w.await();
                        } catch (InterruptedException e) {
                            i0.b("AudioPlayer", "Thread await failed: " + e.getMessage());
                        }
                    } catch (Throwable th) {
                        w.this.v.unlock();
                        throw th;
                    }
                    w.this.v.unlock();
                    throw th;
                }
                if (w.this.p) {
                    try {
                        if (w.this.g != null && w.this.g.available() != 0) {
                            w.this.g();
                        } else if (!w.this.h.isEmpty()) {
                            String str = "Before play, retry nextFrame " + w.h(w.this);
                        }
                    } catch (IOException e2) {
                        i0.b("AudioPlayer", "Check available failed: " + e2.getMessage());
                    }
                    w.this.v.unlock();
                }
                w.this.v.unlock();
            }
            if (w.this.u) {
                Looper.myLooper().quit();
            }
        }
    }

    /* JADX INFO: compiled from: AudioPlayer.java */
    class c implements Runnable {
        private b a;
        private final CountDownLatch b = new CountDownLatch(1);

        c() {
        }

        public b a() {
            try {
                this.b.await();
            } catch (InterruptedException e) {
                i0.c("AudioPlayer", com.huawei.hms.mlsdk.t.a.a("Exception: ").append(e.getMessage()).toString());
            }
            if (w.this.u) {
                this.a = null;
            }
            return this.a;
        }

        @Override // java.lang.Runnable
        public void run() {
            Looper.prepare();
            this.a = w.this.new b();
            this.b.countDown();
            Looper.loop();
        }
    }

    public w(e0 e0Var) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.v = reentrantLock;
        this.w = reentrantLock.newCondition();
        this.x = true;
        this.y = null;
        this.z = null;
        i0.c("AudioPlayer", "Player init " + e0Var);
        this.f = e0Var;
        this.b = AudioTrack.getMinBufferSize(e0Var.d(), e0Var.b(), e0Var.a());
        HandlerThread handlerThread = new HandlerThread("AudioControllerThread");
        this.y = handlerThread;
        handlerThread.start();
        this.z = new a(this.y.getLooper());
        if (this.b > 0) {
            this.c = new AudioTrack(e0Var.e(), e0Var.d(), e0Var.b(), e0Var.a(), this.b * 2, e0Var.c());
            this.x = false;
        }
    }

    static /* synthetic */ String h(w wVar) {
        y yVarPoll = wVar.h.poll();
        if (yVarPoll == null) {
            return "";
        }
        wVar.g = new DataInputStream(new ByteArrayInputStream(yVarPoll.e()));
        String strF = yVarPoll.f();
        if (wVar.n.containsKey(strF) && wVar.n.get(strF).intValue() != 0 && !yVarPoll.h() && yVarPoll.g()) {
            if (wVar.c.getState() == 0) {
                i0.d("AudioPlayer", "onMarkerReached|STATE_UNINITIALIZED");
            } else {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", wVar.c.getPlaybackHeadPosition());
                    bundle.putString("taskid", wVar.i);
                    Handler handler = wVar.z;
                    handler.sendMessage(Message.obtain(handler, 1, bundle));
                } catch (IllegalStateException e) {
                    i0.b("AudioPlayer", com.huawei.hms.mlsdk.t.a.a("IllegalStateException:").append(e.getMessage()).toString());
                }
            }
        }
        return strF;
    }

    private void f() {
        b bVarA;
        c cVar = this.a;
        if (cVar == null || (bVarA = cVar.a()) == null || bVarA.hasMessages(1)) {
            return;
        }
        bVarA.sendEmptyMessage(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        int i;
        try {
            Process.setThreadPriority(-19);
            if (this.g != null && this.g.available() != 0) {
                if (this.c.getPlayState() != 3) {
                    i0.a("AudioPlayer", "InputStream.available > 0, resume play");
                    this.c.play();
                    this.c.setPositionNotificationPeriod(1600);
                }
                byte[] bArr = new byte[this.b];
                if (this.g == null || (i = this.g.read(bArr)) == 0 || i == -1 || this.r || this.h.isEmpty()) {
                    return;
                }
                this.c.write(bArr, 0, i);
                this.q += i;
            }
        } catch (IOException unused) {
            this.e.a(this.i, 200, "IOException: Failed to read the audio stream.");
        } catch (IllegalStateException e) {
            this.e.a(this.i, 200, "IllegalStateException: play() called on uninitialized AudioTrack.");
            i0.b("AudioPlayer", "Exception e = " + e.getMessage());
        }
    }

    public void b() {
        if (this.x) {
            return;
        }
        this.p = true;
        this.r = false;
        this.a = new c();
        j0.b().a(this.a);
        f();
        this.c.setPlaybackPositionUpdateListener(new x(this));
    }

    public void c() {
        if (this.x) {
            return;
        }
        i0.c("AudioPlayer", com.huawei.hms.mlsdk.t.a.a("Pause play;  current state:").append(this.c.getPlayState()).toString());
        if (this.r) {
            return;
        }
        if (this.g != null) {
            this.g.mark(this.q);
        }
        this.r = true;
        this.p = true;
        try {
            this.s = this.c.getPlaybackHeadPosition();
            this.c.pause();
        } catch (IllegalStateException e) {
            i0.b("AudioPlayer", com.huawei.hms.mlsdk.t.a.a("IllegalStateException:").append(e.getMessage()).toString());
        }
        this.e.b(this.i);
    }

    public void d() {
        if (this.x) {
            return;
        }
        i0.c("AudioPlayer", com.huawei.hms.mlsdk.t.a.a("Resume play;  current state:").append(this.c.getPlayState()).toString());
        f();
        if (this.r) {
            try {
                this.s = this.c.getPlaybackHeadPosition() - this.s;
                String str = "Resume play;  current loss sample:" + this.s;
                this.t += this.s;
            } catch (IllegalStateException e) {
                i0.b("AudioPlayer", com.huawei.hms.mlsdk.t.a.a("IllegalStateException:").append(e.getMessage()).toString());
            }
            this.e.c(this.i);
            this.v.lock();
            try {
                this.r = false;
                this.w.signal();
            } finally {
                this.v.unlock();
            }
        }
    }

    public void e() {
        if (this.x) {
            return;
        }
        i0.c("AudioPlayer", com.huawei.hms.mlsdk.t.a.a("Stop flush play; current state:").append(this.c.getPlayState()).toString());
        i0.c("AudioPlayer", "Clears the playback data.");
        this.h.clear();
        try {
            if (this.g != null) {
                this.g.mark(0);
                this.g.reset();
                this.g.close();
                this.g = null;
            }
        } catch (IOException | RuntimeException e) {
            i0.b("AudioPlayer", com.huawei.hms.mlsdk.t.a.a("Trying to close inputStream failed").append(e.getMessage()).toString());
        }
        this.s = 0;
        this.t = 0;
        this.q = 0;
        this.j = 0;
        this.k = 0;
        this.l = 0;
        this.m = 0;
        this.n.clear();
        this.o.clear();
        this.e.a(this.i);
        this.r = true;
        if (this.c.getState() != 1) {
            i0.b("AudioPlayer", "audioTrack not initialized");
        } else {
            try {
                this.c.stop();
                this.c.setPlaybackHeadPosition(0);
                this.c.flush();
            } catch (IllegalStateException e2) {
                i0.c("AudioPlayer", com.huawei.hms.mlsdk.t.a.a("player stop IllegalStateException:").append(e2.getMessage()).toString());
            }
        }
        this.e.a(this.i, true);
    }

    public void a(int i) {
        AudioTrack audioTrack;
        if (this.x || (audioTrack = this.c) == null) {
            return;
        }
        try {
            LoudnessEnhancer loudnessEnhancer = new LoudnessEnhancer(audioTrack.getAudioSessionId());
            this.d = loudnessEnhancer;
            loudnessEnhancer.setEnabled(true);
            this.d.setTargetGain(i);
            i0.a("AudioPlayer", "AudioTrack Volume:" + i + "毫贝(mB)");
        } catch (IllegalArgumentException | IllegalStateException | UnsupportedOperationException e) {
            i0.b("AudioPlayer", com.huawei.hms.mlsdk.t.a.a("set player volume exception:").append(e.getMessage()).toString());
        }
    }

    public void a(e0 e0Var) {
        this.f = e0Var;
        int minBufferSize = AudioTrack.getMinBufferSize(e0Var.d(), e0Var.b(), e0Var.a());
        this.b = minBufferSize;
        if (minBufferSize > 0) {
            AudioTrack audioTrack = new AudioTrack(e0Var.e(), e0Var.d(), e0Var.b(), e0Var.a(), this.b * 2, e0Var.c());
            this.c = audioTrack;
            this.x = false;
            audioTrack.setPlaybackPositionUpdateListener(new x(this));
        }
        i0.a("AudioPlayer", com.huawei.hms.mlsdk.t.a.a("bufferSize==").append(this.b).toString());
        String str = "audioPlayer sampleRateInHz==" + e0Var.d();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, String str) {
        i0.c("AudioPlayer", "onMarkerReached:" + str + " " + i);
        if (this.n.containsKey(str)) {
            this.k = this.n.get(str).intValue() + this.k;
            this.e.a(str, false);
            this.n.remove(str);
            if (this.n.size() > 0) {
                Iterator<String> it = this.n.keySet().iterator();
                String next = it.hasNext() ? it.next() : null;
                this.i = next;
                this.e.d(next);
            }
        }
    }

    public void a(d0 d0Var) {
        this.e = d0Var;
        if (this.x) {
            d0Var.a("", MLTtsError.ERR_UNKNOWN, "System AudioTrack error!");
        }
    }

    public void a(y yVar) {
        boolean zOffer;
        if (this.x) {
            return;
        }
        i0.c("AudioPlayer", com.huawei.hms.mlsdk.t.a.a("Input audio data; current state:").append(this.c.getPlayState()).append(" taskId = ").append(yVar.f()).append(" len:").append(yVar.e().length).toString());
        this.p = true;
        synchronized (this.h) {
            zOffer = this.h.offer(yVar);
            this.j += yVar.e().length;
        }
        this.v.lock();
        try {
            this.w.signal();
            this.v.unlock();
            String str = "current main cache size:" + this.h.size() + ", result = " + zOffer;
        } catch (Throwable th) {
            this.v.unlock();
            throw th;
        }
    }

    public void a(int i, String str, Bundle bundle) {
        if (this.x) {
            return;
        }
        if (i == 0) {
            i0.c("AudioPlayer", str + " AudioSegment.START taskSample=" + this.n);
            if (this.l == 0 || this.n.size() == 0) {
                this.i = str;
                i0.c("AudioPlayer", com.huawei.hms.mlsdk.t.a.a("Update playing taskId=").append(this.i).toString());
                this.e.d(this.i);
            }
            this.n.put(str, 0);
            return;
        }
        if (i != 1) {
            if (i != 2) {
                return;
            }
            i0.c("AudioPlayer", "Pushing the RANGE_START event to a queue, result: " + this.o.offer(new c0().a(str).a(bundle).a((this.j / 2) + this.l)));
            return;
        }
        if (this.n.containsKey(str)) {
            this.m = this.j / (this.f.a() != 2 ? 1 : 2);
            this.n.put(str, Integer.valueOf(this.m));
            this.l += this.m;
            if (this.j == 0 && this.n.containsKey(str) && this.n.get(str).intValue() == 0) {
                try {
                    Thread.sleep(100L);
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("position", this.c.getPlaybackHeadPosition());
                    bundle2.putString("taskid", str);
                    Handler handler = this.z;
                    handler.sendMessage(Message.obtain(handler, 1, bundle2));
                } catch (IllegalStateException | InterruptedException e) {
                    i0.b("AudioPlayer", com.huawei.hms.mlsdk.t.a.a("Exception e = ").append(e.getMessage()).toString());
                }
            }
            i0.c("AudioPlayer", str + " AudioSegment.END taskSampleSize=" + this.n);
            this.j = 0;
            i0.c("AudioPlayer", str + " has [" + this.m + "] audio segment");
        }
    }

    public void a() {
        if (this.x) {
            return;
        }
        i0.c("AudioPlayer", com.huawei.hms.mlsdk.t.a.a("Destroy play; current state:").append(this.c.getPlayState()).toString());
        this.u = true;
        e();
        this.p = false;
        this.v.lock();
        try {
            this.w.signal();
            this.v.unlock();
            this.c.release();
            this.c.setPlaybackPositionUpdateListener(null);
            LoudnessEnhancer loudnessEnhancer = this.d;
            if (loudnessEnhancer != null) {
                loudnessEnhancer.setEnabled(false);
                this.d.release();
                this.d = null;
            }
            this.y.quit();
        } catch (Throwable th) {
            this.v.unlock();
            throw th;
        }
    }

    static /* synthetic */ boolean a(w wVar, int i) {
        int i2 = wVar.m;
        return i2 != 0 && (((float) ((i2 + wVar.k) - i)) / ((float) wVar.f.d())) * 1000.0f <= 1000.0f;
    }
}
