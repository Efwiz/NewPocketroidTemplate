package com.huawei.hms.mlsdk.asr.engine;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.gms.auth.api.proxy.AuthApiStatusCodes;
import com.huawei.hms.ml.grs.GrsListener;
import com.huawei.hms.ml.grs.GrsRegionUtils;
import com.huawei.hms.ml.grs.GrsUtils;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import com.huawei.hms.mlsdk.asr.engine.cloud.vo.f;
import com.huawei.hms.mlsdk.asr.engine.utils.SmartLogger;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.tts.MLTtsAudioFragment;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.net.ssl.SSLException;
import okhttp3.Response;
import okio.ByteString;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: AsrProcessor.java */
/* JADX INFO: loaded from: classes10.dex */
public class b {
    private Lock a;
    private Condition b;
    private AsrEngine c;
    private List<e> d;
    private c e;
    private com.huawei.hms.mlsdk.asr.engine.utils.a f;
    private Thread g;
    private final AtomicBoolean h;
    private final AtomicBoolean i;
    private final AtomicBoolean j;
    private long k;
    private volatile boolean l;
    private com.huawei.hms.mlsdk.asr.engine.utils.e m;
    private volatile boolean n;
    private volatile boolean o;
    private long p;
    private long q;
    private volatile boolean r;
    private boolean s;
    private volatile boolean t;
    private String u;
    private long v;
    private AtomicInteger w;
    private AtomicBoolean x;

    /* JADX INFO: renamed from: com.huawei.hms.mlsdk.asr.engine.b$b, reason: collision with other inner class name */
    /* JADX INFO: compiled from: AsrProcessor.java */
    class RunnableC0118b implements Runnable {
        RunnableC0118b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                Thread.sleep(b.this.q);
            } catch (InterruptedException e) {
                SmartLogger.e("AsrProcessor", e.getMessage());
            } finally {
                b.this.a(false);
                b.this.h.set(false);
                SmartLogger.i("AsrProcessor", "after send finish later");
                for (Object next : b.this.d) {
                    if (!b.this.o) {
                        b.this.o = true;
                        SmartLogger.i("AsrProcessor", com.huawei.hms.mlsdk.asr.o.a.a("after send finish ").append(b.this.q).append(" ms later but don't receive response, call onResult").toString());
                        ((AsrEngine.b) next).a(new AsrResult(8));
                    }
                }
            }
        }
    }

    /* JADX INFO: compiled from: AsrProcessor.java */
    private class d implements Runnable {
        private boolean a = true;

        /* synthetic */ d(a aVar) {
        }

        @Override // java.lang.Runnable
        public void run() {
            AtomicBoolean atomicBoolean;
            AtomicBoolean atomicBoolean2;
            while (b.this.h.get()) {
                b.this.a.lock();
                try {
                    if (!b.this.i.get()) {
                        try {
                            try {
                                b.this.j.set(true);
                                SmartLogger.d("AsrProcessor", "InnerProcessRunnable begin waiting because of isActive is false");
                                b.this.b.await();
                                SmartLogger.d("AsrProcessor", "InnerProcessRunnable awake from isActive is false");
                                atomicBoolean = b.this.j;
                            } catch (InterruptedException e) {
                                SmartLogger.e("AsrProcessor", "await failed" + e.getMessage());
                                atomicBoolean = b.this.j;
                            }
                            atomicBoolean.set(false);
                        } catch (Throwable th) {
                            b.this.j.set(false);
                            throw th;
                        }
                    }
                    b.this.e.b();
                    if (b.this.e.a()) {
                        SmartLogger.i("AsrProcessor", "one minutes later after receiving start, and send finish");
                        b.this.b();
                        b.this.a.unlock();
                        break;
                    }
                    byte[] bArrB = b.this.f.b();
                    while (b.this.h.get() && bArrB.length == 0) {
                        try {
                            try {
                                b.this.j.set(true);
                                SmartLogger.d("AsrProcessor", "InnerProcessRunnable waiting: isRunning.get(): " + b.this.h.get() + "block.length: " + bArrB.length);
                                b.this.b.await();
                                SmartLogger.d("AsrProcessor", "InnerProcessRunnable awake from isRunning.get()");
                                bArrB = b.this.f.b();
                                atomicBoolean2 = b.this.j;
                            } catch (InterruptedException e2) {
                                SmartLogger.e("AsrProcessor", "await failed" + e2.getMessage());
                                atomicBoolean2 = b.this.j;
                            }
                            atomicBoolean2.set(false);
                        } catch (Throwable th2) {
                            b.this.j.set(false);
                            throw th2;
                        }
                    }
                    if (b.this.h.get() && b.this.i.get()) {
                        ByteString byteStringOf = ByteString.of(new com.huawei.hms.mlsdk.asr.engine.cloud.vo.b("DATA", b.s(b.this), bArrB).a());
                        SmartLogger.i("AsrProcessor", "send data in thread, messageSeqNo: " + b.this.k);
                        if (b.this.m != null) {
                            b.this.m.a(byteStringOf);
                        }
                        if (this.a) {
                            b.this.p = System.currentTimeMillis();
                            SmartLogger.i("AsrProcessor", "mTimeOfSendFirstData is " + b.this.p);
                            this.a = false;
                            if (b.this.h()) {
                                Iterator it = b.this.d.iterator();
                                while (it.hasNext()) {
                                    ((AsrEngine.b) ((e) it.next())).a(9);
                                }
                            }
                        }
                        int i = ((int) b.this.k) * 200;
                        int i2 = ((int) b.this.k) * 6400;
                        Bundle bundle = new Bundle();
                        bundle.putInt("voiceStreamTime", i);
                        bundle.putInt("uploadVoiceSize", i2);
                        bundle.putLong("sendFinalDataTime", System.currentTimeMillis());
                        com.huawei.hms.mlsdk.asr.o.c.b().a(b.this.u, 5, bundle);
                        com.huawei.hms.mlsdk.asr.o.c.b().a(b.this.u, 6, bundle);
                        com.huawei.hms.mlsdk.asr.o.c.b().a(b.this.u, 11, bundle);
                        b.this.v = System.currentTimeMillis();
                    }
                    b.this.a.unlock();
                } catch (Throwable th3) {
                    b.this.a.unlock();
                    throw th3;
                }
            }
            SmartLogger.i("AsrProcessor", "InnerProcessRunnable is over");
        }
    }

    /* JADX INFO: compiled from: AsrProcessor.java */
    public interface e {
    }

    public b(AsrEngine asrEngine) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.a = reentrantLock;
        this.b = reentrantLock.newCondition();
        this.d = new ArrayList();
        this.h = new AtomicBoolean(false);
        this.i = new AtomicBoolean(false);
        this.j = new AtomicBoolean(false);
        this.k = 0L;
        this.l = false;
        this.m = null;
        this.n = true;
        this.o = false;
        this.p = 0L;
        this.q = 0L;
        this.r = true;
        this.s = false;
        this.t = false;
        this.u = null;
        this.v = 0L;
        this.w = new AtomicInteger(-1);
        this.x = new AtomicBoolean(false);
        this.c = asrEngine;
        this.f = new com.huawei.hms.mlsdk.asr.engine.utils.a(asrEngine.getEngineConfig().getRecognizeDuration() * 32);
        if (g()) {
            Iterator<e> it = this.d.iterator();
            while (it.hasNext()) {
                ((AsrEngine.b) it.next()).a(new AsrError(7, "No network", null));
            }
            this.n = false;
        }
    }

    static /* synthetic */ long s(b bVar) {
        long j = bVar.k + 1;
        bVar.k = j;
        return j;
    }

    /* JADX INFO: compiled from: AsrProcessor.java */
    private class c {
        private Long a;
        private int b = 0;

        /* synthetic */ c(a aVar) {
        }

        void a(Long l) {
            this.a = l;
            this.b = 0;
        }

        void b() {
            this.b = (int) (Long.valueOf(SystemClock.elapsedRealtime()).longValue() - this.a.longValue());
        }

        boolean a() {
            return this.b > b.this.c.getEngineConfig().getMaxAudioDuration();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean g() {
        int iA = com.huawei.hms.mlsdk.asr.engine.utils.d.a();
        return iA == 1 || iA == 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean h() {
        return com.huawei.hms.mlsdk.asr.engine.utils.d.a() == 3;
    }

    private void i() {
        this.a.lock();
        try {
            this.b.signal();
        } finally {
            this.a.unlock();
        }
    }

    synchronized void e() {
        if (!this.l) {
            this.l = true;
            a(false);
            this.h.set(false);
            com.huawei.hms.mlsdk.asr.engine.cloud.vo.a aVar = new com.huawei.hms.mlsdk.asr.engine.cloud.vo.a();
            aVar.a("FINISH");
            StringBuilder sb = new StringBuilder(23);
            sb.append("{ \"command\":\"").append(aVar.a()).append("\"}");
            String string = sb.toString();
            com.huawei.hms.mlsdk.asr.engine.utils.e eVar = this.m;
            if (eVar != null) {
                eVar.a(string);
            }
        }
    }

    public synchronized void f() {
        if (!this.n) {
            Iterator<e> it = this.d.iterator();
            while (it.hasNext()) {
                ((AsrEngine.b) it.next()).a(new AsrError(7, "No network", null));
            }
            return;
        }
        GrsUtils.setGrsListener(new a());
        String grsCountryCode = GrsRegionUtils.getGrsCountryCode();
        SmartLogger.i("AsrProcessor", "Grs-CountryCode:" + grsCountryCode);
        if (grsCountryCode != null) {
            GrsUtils.getAsrVisionSearchUrls(GrsUtils.initGrsVisionSearchClientWithCountry(MLApplication.getInstance().getAppContext(), grsCountryCode));
        } else {
            GrsUtils.getAsrUrls(MLApplication.getInstance().getAppContext(), false);
        }
    }

    public boolean d() {
        return this.n;
    }

    public synchronized void b() {
        if (!this.l) {
            this.l = true;
            a(false);
            this.h.set(false);
            byte[] bArrB = this.f.b();
            while (bArrB != null && bArrB.length > 0) {
                long j = this.k + 1;
                this.k = j;
                ByteString byteStringOf = ByteString.of(new com.huawei.hms.mlsdk.asr.engine.cloud.vo.b("DATA", j, bArrB).a());
                SmartLogger.i("AsrProcessor", "send data in finish");
                com.huawei.hms.mlsdk.asr.engine.utils.e eVar = this.m;
                if (eVar != null) {
                    eVar.a(byteStringOf);
                }
                bArrB = this.f.b();
            }
            com.huawei.hms.mlsdk.asr.engine.cloud.vo.a aVar = new com.huawei.hms.mlsdk.asr.engine.cloud.vo.a();
            aVar.a("FINISH");
            StringBuilder sb = new StringBuilder(23);
            sb.append("{ \"command\":\"").append(aVar.a()).append("\"}");
            String string = sb.toString();
            if (this.m != null) {
                SmartLogger.i("AsrProcessor", "send finish");
                this.m.a(string);
            }
            if (this.q < 2000) {
                this.q = 2000L;
            }
            new Thread(new RunnableC0118b(), "sendFinishThread").start();
        }
    }

    public long c() {
        return this.v;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(int i, String str, Integer num) {
        SmartLogger.i("AsrProcessor", "onAbnormal");
        this.l = true;
        a(false);
        this.h.set(false);
        if (num != null && num.intValue() == 3015) {
            for (e eVar : this.d) {
                SmartLogger.i("AsrProcessor", "one minute timeout");
                ((AsrEngine.b) eVar).a(new AsrResult(8));
            }
            return;
        }
        Bundle bundle = new Bundle();
        if (num != null) {
            bundle.putInt(MLAsrCaptureConstants.ASR_SUB_ERROR_CODE, num.intValue());
        }
        Iterator<e> it = this.d.iterator();
        while (it.hasNext()) {
            ((AsrEngine.b) it.next()).a(new AsrError(i, str, bundle));
        }
    }

    public void a(e eVar) {
        this.d.add(eVar);
    }

    public void a(byte[] bArr) {
        this.f.a(bArr);
        if (!this.j.get() || this.f.c() <= 0) {
            return;
        }
        SmartLogger.d("AsrProcessor", "setInput(byte[] input):notify InnerProcessRunnable");
        i();
    }

    public void a(boolean z) {
        this.a.lock();
        try {
            this.i.set(z);
            if (this.j.get()) {
                i();
            }
        } finally {
            this.a.unlock();
        }
    }

    public synchronized void a() {
        this.t = true;
        com.huawei.hms.mlsdk.asr.engine.utils.a aVar = this.f;
        if (aVar != null) {
            aVar.a();
        }
        a(false);
        this.h.set(false);
        i();
        com.huawei.hms.mlsdk.asr.engine.utils.e eVar = this.m;
        if (eVar != null) {
            eVar.d();
        }
    }

    public void a(String str) {
        this.u = str;
    }

    static /* synthetic */ boolean a(b bVar, int i) {
        com.huawei.hms.mlsdk.asr.engine.utils.e eVar = bVar.m;
        return (eVar == null || eVar.b() || (3018 != i && 3017 != i && 3016 != i)) ? false : true;
    }

    /* JADX INFO: compiled from: AsrProcessor.java */
    class a implements GrsListener {
        a() {
        }

        @Override // com.huawei.hms.ml.grs.GrsListener
        public void onAsynGrsUrls(List<String> list) {
            ArrayList arrayList = new ArrayList();
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                String next = it.next();
                SmartLogger.i("AsrProcessor", "url:" + next, true);
                if (!next.startsWith("ws://") && !next.startsWith("wss://")) {
                    next = com.huawei.hms.mlsdk.asr.o.a.a("wss://", next);
                }
                arrayList.add(b.this.c.isRecognizerLong() ? com.huawei.hms.mlsdk.asr.o.a.a(next, AsrConstants.AsrGrs.ASR_LONG_URL) : com.huawei.hms.mlsdk.asr.o.a.a(next, AsrConstants.AsrGrs.ASR_SHORT_URL));
            }
            if (b.this.m == null) {
                SmartLogger.i("AsrProcessor", "create WebSocketClient");
                b bVar = b.this;
                bVar.m = new com.huawei.hms.mlsdk.asr.engine.utils.e(bVar.c.isRecognizerLong(), arrayList);
            }
            b.this.m.a(new C0117a(System.currentTimeMillis()));
            if (b.this.m.a()) {
                return;
            }
            SmartLogger.i("AsrProcessor", "begin to start connect ");
            b.this.m.c();
        }

        /* JADX INFO: renamed from: com.huawei.hms.mlsdk.asr.engine.b$a$a, reason: collision with other inner class name */
        /* JADX INFO: compiled from: AsrProcessor.java */
        class C0117a implements com.huawei.hms.mlsdk.asr.o.e {
            long a;
            final /* synthetic */ long b;

            C0117a(long j) {
                this.b = j;
            }

            @Override // com.huawei.hms.mlsdk.asr.o.e
            public void a(Response response) {
                SmartLogger.i("AsrProcessor", "onOpen");
                b.this.w.set(-1);
                if (b.this.c.isRecognizerLong() && b.this.x.get()) {
                    Iterator it = b.this.d.iterator();
                    while (it.hasNext()) {
                        ((AsrEngine.b) it.next()).a(43);
                    }
                }
                boolean z = true;
                if (!b.this.x.get()) {
                    b.this.x.set(true);
                }
                f fVar = new f();
                fVar.a("pcm");
                fVar.b(b.this.c.getEngineConfig().getLanguage());
                fVar.b(Integer.valueOf(MLTtsAudioFragment.SAMPLE_RATE_16K));
                fVar.a((Integer) 16);
                fVar.a(b.this.c.getEngineConfig().getEnablePunctuation());
                fVar.c(b.this.c.getEngineConfig().getEnableSilenceDetection());
                fVar.c(b.this.c.getEngineConfig().getSilenceDurationThreshold());
                if (b.this.c.isRecognizerLong()) {
                    fVar.d(Boolean.valueOf(b.this.c.getEngineConfig().isWordTimeOffset()));
                    fVar.b(Boolean.valueOf(b.this.c.getEngineConfig().isSentenceTimeOffset()));
                }
                fVar.c(b.this.c.getEngineConfig().getScenes());
                StringBuilder sb = new StringBuilder(701);
                sb.append("{\"command\":\"START\", \"audioConfig\":{\"encoding\":\"").append(fVar.f()).append("\",\"languageCode\":\"").append(fVar.g()).append("\",\"sampleRate\":").append(fVar.h()).append(",\"bitDepth\":").append(fVar.a());
                if (fVar.b() != null) {
                    sb.append(",\"enablePunctuation\":").append(fVar.b());
                }
                if (fVar.e() != null) {
                    sb.append(",\"enableWordTimeOffset\":").append(fVar.e());
                }
                if (fVar.c() != null) {
                    sb.append(",\"enableSentenceTimeOffset\":").append(fVar.c());
                }
                if (fVar.i() != null) {
                    sb.append(",\"scene\":\"").append(fVar.i()).append("\"");
                }
                if (fVar.d() != null || fVar.j() != null) {
                    sb.append(",\"metadata\":{");
                    if (fVar.d() != null) {
                        sb.append("\"enableSilenceDetection\":").append(fVar.d());
                    } else {
                        z = false;
                    }
                    if (fVar.j() != null) {
                        if (z) {
                            sb.append(",");
                        }
                        sb.append("\"silenceDurationThreshold\":").append(fVar.j());
                    }
                    sb.append("}");
                }
                sb.append("}}");
                String string = sb.toString();
                SmartLogger.i("AsrProcessor", "send start ");
                if (b.this.m != null) {
                    b.this.m.a(string);
                }
            }

            @Override // com.huawei.hms.mlsdk.asr.o.e
            public void a(ByteString byteString) {
            }

            @Override // com.huawei.hms.mlsdk.asr.o.e
            public void b(int i, String str) {
                SmartLogger.i("AsrProcessor", "onClosed: code: " + i + "reason: " + str);
            }

            @Override // com.huawei.hms.mlsdk.asr.o.e
            public void a(String str) {
                SmartLogger.i("AsrProcessor", "onMessage:");
                if (b.this.t) {
                    SmartLogger.i("AsrProcessor", "although receive message, but Destroy() is called");
                    return;
                }
                try {
                    SmartLogger.d("AsrProcessor", str);
                    com.huawei.hms.mlsdk.asr.engine.cloud.vo.e eVarA = com.huawei.hms.mlsdk.asr.engine.cloud.vo.e.a(str);
                    String strC = eVarA.c();
                    if (!"0".equalsIgnoreCase(strC)) {
                        b.this.a(40, "Service unavailable", Integer.valueOf(Integer.parseInt(strC)));
                        return;
                    }
                    if ("START".equalsIgnoreCase(eVarA.a())) {
                        long jCurrentTimeMillis = System.currentTimeMillis() - this.b;
                        Bundle bundle = new Bundle();
                        bundle.putInt("chainBuildingDelay", (int) jCurrentTimeMillis);
                        SmartLogger.i("AsrProcessor", "chainBuildingDelay: " + jCurrentTimeMillis);
                        com.huawei.hms.mlsdk.asr.o.c.b().a(b.this.u, 8, bundle);
                        long jElapsedRealtime = SystemClock.elapsedRealtime();
                        Iterator it = b.this.d.iterator();
                        while (it.hasNext()) {
                            AsrEngine.this.processStartTime = Long.valueOf(jElapsedRealtime);
                        }
                        b.this.e = b.this.new c(null);
                        b.this.e.a(Long.valueOf(jElapsedRealtime));
                        b.this.a(true);
                        if (b.this.g == null) {
                            b.this.h.set(true);
                            b.this.g = new Thread(b.this.new d(null), "InnerProcessRunnable");
                            b.this.g.start();
                            return;
                        }
                        return;
                    }
                    if ("STATUS".equalsIgnoreCase(eVarA.a())) {
                        JSONObject jSONObjectB = eVarA.b();
                        com.huawei.hms.mlsdk.asr.engine.cloud.vo.d dVar = new com.huawei.hms.mlsdk.asr.engine.cloud.vo.d();
                        if (jSONObjectB != null && jSONObjectB.has("silenceDetected")) {
                            dVar.a(jSONObjectB.getBoolean("silenceDetected"));
                        }
                        if (dVar.a()) {
                            b.this.s = true;
                            SmartLogger.i("AsrProcessor", "receive silence=true");
                            Bundle bundle2 = new Bundle();
                            bundle2.putLong("speechEndTime", System.currentTimeMillis());
                            SmartLogger.i("AsrProcessor", "isSilenceDetected speechEndTime: " + System.currentTimeMillis());
                            com.huawei.hms.mlsdk.asr.o.c.b().a(b.this.u, 2, bundle2);
                            long jElapsedRealtime2 = SystemClock.elapsedRealtime();
                            if (b.this.c.isRecognizerLong()) {
                                return;
                            }
                            Iterator it2 = b.this.d.iterator();
                            while (it2.hasNext()) {
                                ((AsrEngine.b) ((e) it2.next())).a(jElapsedRealtime2);
                            }
                            return;
                        }
                        return;
                    }
                    if ("DATA".equalsIgnoreCase(eVarA.a())) {
                        if (b.this.r) {
                            b.this.r = false;
                            Long lE = com.huawei.hms.mlsdk.asr.o.c.b().e(b.this.u);
                            if (lE != null) {
                                SmartLogger.d("AsrProcessor", "getSpeechStartTime: " + lE);
                                long jCurrentTimeMillis2 = System.currentTimeMillis() - lE.longValue();
                                Bundle bundle3 = new Bundle();
                                bundle3.putInt("firstWordCost", (int) jCurrentTimeMillis2);
                                com.huawei.hms.mlsdk.asr.o.c.b().a(b.this.u, 3, bundle3);
                                SmartLogger.i("AsrProcessor", "firstWordCost: " + jCurrentTimeMillis2);
                            }
                            b.this.q = System.currentTimeMillis() - b.this.p;
                            SmartLogger.i("AsrProcessor", "mSendFinishDelayTime is " + b.this.q + LanguageCodeUtil.MS);
                            this.a = System.currentTimeMillis();
                        } else {
                            SmartLogger.d("AsrProcessor", "time of between receive data is: " + (System.currentTimeMillis() - this.a) + LanguageCodeUtil.MS);
                            this.a = System.currentTimeMillis();
                        }
                        com.huawei.hms.mlsdk.asr.engine.cloud.vo.c cVarA = com.huawei.hms.mlsdk.asr.engine.cloud.vo.e.a(eVarA.b());
                        if (b.this.c.isRecognizerLong()) {
                            AsrResult asrResult = new AsrResult(5, null, cVarA.c(), cVarA.e());
                            asrResult.setSentenceOffsetList(cVarA.b());
                            asrResult.setWordOffsetList(cVarA.d());
                            Iterator it3 = b.this.d.iterator();
                            while (it3.hasNext()) {
                                ((AsrEngine.b) ((e) it3.next())).a(asrResult);
                            }
                            return;
                        }
                        if ((cVarA.c() == null || "".equals(cVarA.c())) && !cVarA.a()) {
                            if (b.this.c.hasResult()) {
                                b.this.a(6, "Cannot understand", 1003);
                                return;
                            }
                            return;
                        }
                        if (!cVarA.a()) {
                            Iterator it4 = b.this.d.iterator();
                            while (it4.hasNext()) {
                                ((AsrEngine.b) ((e) it4.next())).a(new AsrResult(5, null, cVarA.c(), false));
                            }
                            return;
                        }
                        Bundle bundle4 = new Bundle();
                        if (!b.this.s && com.huawei.hms.mlsdk.asr.o.c.b().d(b.this.u) != null) {
                            long jCurrentTimeMillis3 = System.currentTimeMillis() - com.huawei.hms.mlsdk.asr.o.c.b().d(b.this.u).longValue();
                            bundle4.putInt("lastWordCost", (int) jCurrentTimeMillis3);
                            SmartLogger.i("AsrProcessor", "lastWordCost: " + jCurrentTimeMillis3);
                            com.huawei.hms.mlsdk.asr.o.c.b().a(b.this.u, 4, bundle4);
                        }
                        SmartLogger.i("AsrProcessor", "result.getIsFinal(): " + cVarA.a() + " isFinished: " + b.this.l);
                        b.this.a(false);
                        b.this.h.set(false);
                        for (e eVar : b.this.d) {
                            if (!b.this.o) {
                                b.this.o = true;
                                Log.d("AsrProcessor", "onMessage: result.mHasCallOnResult()");
                                ((AsrEngine.b) eVar).a(new AsrResult(8, cVarA.c()));
                            }
                        }
                    }
                } catch (JSONException unused) {
                    b.this.a(40, "Service unavailable", 1003);
                }
            }

            @Override // com.huawei.hms.mlsdk.asr.o.e
            public void a() {
                SmartLogger.i("AsrProcessor", "reconnect");
            }

            @Override // com.huawei.hms.mlsdk.asr.o.e
            public void a(int i, String str) {
                b.this.w.set(i);
                SmartLogger.i("AsrProcessor", "closing:" + i + "," + str);
                if (!b.this.c.isRecognizerLong()) {
                    if (i != 0) {
                        if (3001 == i) {
                            b.this.a(44, "Authentication failed.", Integer.valueOf(i));
                            return;
                        } else {
                            b.this.a(40, "Service unavailable", Integer.valueOf(i));
                            return;
                        }
                    }
                    return;
                }
                b.this.l = true;
                b.this.a(false);
                if (b.a(b.this, i)) {
                    for (AsrEngine.b bVar : b.this.d) {
                        bVar.a(new AsrResult(5, null, "", true));
                        bVar.a(42);
                    }
                    b.this.m.a(1000L);
                    return;
                }
                Iterator it = b.this.d.iterator();
                while (it.hasNext()) {
                    ((AsrEngine.b) it.next()).a(new AsrResult(8, null, "", true));
                }
                Bundle bundle = new Bundle();
                if (3001 == i) {
                    bundle.putInt(MLAsrCaptureConstants.ASR_SUB_ERROR_CODE, AuthApiStatusCodes.AUTH_API_ACCESS_FORBIDDEN);
                    Iterator it2 = b.this.d.iterator();
                    while (it2.hasNext()) {
                        ((AsrEngine.b) it2.next()).a(new AsrError(44, str, bundle));
                    }
                    return;
                }
                bundle.putInt(MLAsrCaptureConstants.ASR_SUB_ERROR_CODE, i);
                Iterator it3 = b.this.d.iterator();
                while (it3.hasNext()) {
                    ((AsrEngine.b) it3.next()).a(new AsrError(40, str, bundle));
                }
            }

            @Override // com.huawei.hms.mlsdk.asr.o.e
            public void a(Throwable th, Response response) {
                SmartLogger.e("AsrProcessor", com.huawei.hms.mlsdk.asr.o.a.a("onFailure socket error: ").append(th.getMessage()).append(", ").append(th.getClass().getSimpleName()).toString());
                if (!b.this.c.isRecognizerLong() || b.this.m == null || b.this.m.b() || (!(th instanceof UnknownHostException) && !(th instanceof SSLException) && !(th instanceof ConnectException))) {
                    if (b.this.c.isRecognizerLong() && b.this.i.get()) {
                        b bVar = b.this;
                        if (b.a(bVar, bVar.w.get())) {
                            SmartLogger.i("AsrProcessor", "onFailure socket already reconnect in onClosing");
                            return;
                        }
                    }
                    if (!(th instanceof IOException) || !b.this.g()) {
                        if (b.this.l) {
                            return;
                        }
                        SmartLogger.i("AsrProcessor", th.getMessage() + " isFinished=" + b.this.l);
                        b.this.a(40, "Service unavailable", 1003);
                        return;
                    }
                    b.this.a(7, "No network", null);
                    return;
                }
                for (AsrEngine.b bVar2 : b.this.d) {
                    bVar2.a(new AsrResult(5, null, "", true));
                    bVar2.a(42);
                }
                b.this.m.a(1000L);
            }
        }
    }
}
