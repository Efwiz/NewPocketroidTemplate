package com.huawei.hms.mlsdk.t;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Pair;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.tts.MLTtsAudioFragment;
import com.huawei.hms.mlsdk.tts.MLTtsConfig;
import com.huawei.hms.mlsdk.tts.MLTtsConstants;
import com.huawei.hms.mlsdk.tts.MLTtsError;
import com.huawei.hms.mlsdk.tts.MLTtsSpeaker;
import com.huawei.hms.mlsdk.tts.MLTtsWarn;
import com.huawei.hms.mlsdk.tts.engine.offline.SynthesisEngineOffline;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: compiled from: SpeechSynthesisController.java */
/* JADX INFO: loaded from: classes10.dex */
public class e {
    private long A;
    private boolean B;
    private boolean a;
    m b;
    SynthesisEngineOffline c;
    w d;
    v e;
    private InterfaceC0123e f;
    private com.huawei.hms.mlsdk.t.d g;
    private volatile boolean h;
    private volatile boolean i;
    private volatile boolean j;
    private volatile int k;
    private boolean l;
    private boolean m;
    private boolean n;
    private final Map<String, Integer> o;
    private volatile Queue<y> p;
    private Map<Integer, Integer> q;
    private Map<Integer, Integer> r;
    private MLTtsConfig s;
    private g t;
    private HandlerThread u;
    private Handler v;
    private Lock w;
    private Condition x;
    private k y;
    private d0 z;

    /* JADX INFO: compiled from: SpeechSynthesisController.java */
    class a implements k {
        a() {
        }

        @Override // com.huawei.hms.mlsdk.t.k
        public void a(String str, y yVar) {
            e.this.a(yVar);
        }

        @Override // com.huawei.hms.mlsdk.t.k
        public void b(String str, int i, String str2) {
            e.this.f.onDispatchWarn(str, new MLTtsWarn.Builder().setWarnId(i).setWarnMsg(str2).setExtension(null).build());
        }

        @Override // com.huawei.hms.mlsdk.t.k
        public void a(String str, int i, Bundle bundle) {
            if (i == 0) {
                i0.c("SpeechSynthesisController", "Receive START taskId [" + str + "]");
                e.this.a(new y.b().a(str).a(new byte[0]).a(true).b(bundle.getBoolean("playExter")).a());
                return;
            }
            if (i == 1) {
                boolean z = bundle.getBoolean("interrupted");
                boolean z2 = bundle.getBoolean("playExter");
                i0.c("SpeechSynthesisController", "Receive END taskId [" + str + "] interrupted [" + z + "]");
                e.this.a(new y.b().a(str).a(new byte[0]).b(z ? -1 : 1).b(z2).a());
                return;
            }
            if (i != 2) {
                return;
            }
            int i2 = bundle.getInt("startIndex");
            int i3 = bundle.getInt("endIndex");
            boolean z3 = bundle.getBoolean("playExter");
            i0.c("SpeechSynthesisController", "Receive RANGE_START [" + str + "] setRangeStartIndex[" + i2 + "] setRangeLen[" + i3 + "]");
            e.this.a(new y.b().a(str).a(new byte[0]).d(true).e(i2).d(i3).b(z3).a());
        }

        @Override // com.huawei.hms.mlsdk.t.k
        public void b(String str) {
            e.this.f.onDispatchSynthesisEnd(str);
            e.this.m = true;
            e.this.k();
        }

        @Override // com.huawei.hms.mlsdk.t.k
        public void a(String str, int i, String str2) {
            int iIntValue;
            i0.b("SpeechSynthesisController", "Receive engine error taskId[" + str + "] errorCode[" + i + "] errorMsg[" + str2 + "]");
            e.this.m = true;
            if (e.this.q.containsKey(Integer.valueOf(i))) {
                iIntValue = ((Integer) e.this.q.get(Integer.valueOf(i))).intValue();
            } else {
                iIntValue = String.valueOf(i).startsWith("7") ? MLTtsError.ERR_SPEECH_SYNTHESIS_FAILED : MLTtsError.ERR_UNKNOWN;
            }
            e.this.f.onDispatchError(str, new MLTtsError.Builder().setErrorId(iIntValue).setErrorMsg(str2).setExtension(i < 1000 ? "0" + i : String.valueOf(i)).build());
            Bundle bundle = new Bundle();
            bundle.putBoolean("interrupted", true);
            a(str, 1, bundle);
        }

        @Override // com.huawei.hms.mlsdk.t.k
        public void a(String str) {
            e.this.f.onDispatchSynthesisStart(str);
            e.this.m = false;
        }
    }

    /* JADX INFO: compiled from: SpeechSynthesisController.java */
    class c extends Handler {
        c(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 4:
                    Object obj = message.obj;
                    if (obj instanceof h) {
                        h hVar = (h) obj;
                        i0.c("SpeechSynthesisController", "handle add speak text event");
                        e.a(e.this, hVar);
                        e.this.k();
                        if (!hVar.f()) {
                            e.this.d.d();
                        }
                    }
                    break;
                case 5:
                    e.this.d.c();
                    break;
                case 6:
                    e.this.k();
                    e.this.d.d();
                    break;
                case 7:
                    if (e.this.a) {
                        e.this.c.b();
                    } else {
                        e.this.b.b();
                    }
                    i0.c("SpeechSynthesisController", "handle stop event");
                    e.this.h();
                    e.this.d.e();
                    break;
                case 8:
                    Bundle data = message.getData();
                    String string = data.getString("taskId");
                    String string2 = data.getString("errMsg");
                    int i = data.getInt("errId");
                    e.this.f.onDispatchError(string, new MLTtsError.Builder().setErrorId(i).setErrorMsg(string2).setExtension(message.obj).build());
                    break;
            }
        }
    }

    /* JADX INFO: renamed from: com.huawei.hms.mlsdk.t.e$e, reason: collision with other inner class name */
    /* JADX INFO: compiled from: SpeechSynthesisController.java */
    public interface InterfaceC0123e {
        void onDispatchAudioAvailable(String str, MLTtsAudioFragment mLTtsAudioFragment, int i, Pair<Integer, Integer> pair, Bundle bundle);

        void onDispatchError(String str, MLTtsError mLTtsError);

        void onDispatchPause(String str);

        void onDispatchRangeStart(String str, int i, int i2);

        void onDispatchResume(String str);

        void onDispatchStart(String str);

        void onDispatchStop(String str, boolean z);

        void onDispatchSynthesisComplete(String str, boolean z);

        void onDispatchSynthesisEnd(String str);

        void onDispatchSynthesisStart(String str);

        void onDispatchWarn(String str, MLTtsWarn mLTtsWarn);
    }

    /* JADX INFO: compiled from: SpeechSynthesisController.java */
    private class f extends Handler {
        /* synthetic */ f(a aVar) {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            i0.c("SpeechSynthesisController", com.huawei.hms.mlsdk.t.a.a("ControllerHandler handleMessage: ").append(message.what).toString());
            if (message.what != 2) {
                return;
            }
            while (e.this.h && !e.this.i) {
                e.this.w.lock();
                while (!e.this.j() && !e.i(e.this) && !e.this.i) {
                    try {
                        try {
                            e.this.n = false;
                            e.this.x.await();
                            e.this.n = true;
                        } catch (InterruptedException e) {
                            String str = "ControllerHandler: await failed " + e.getMessage();
                        }
                    } catch (Throwable th) {
                        e.this.w.unlock();
                        throw th;
                    }
                }
                if (!e.this.h) {
                    e.this.w.unlock();
                    break;
                }
                if (e.this.j()) {
                    e.l(e.this);
                } else if (e.i(e.this)) {
                    e.this.l();
                } else {
                    i0.b("SpeechSynthesisController", "It should never go here!!");
                }
                e.this.w.unlock();
            }
            i0.c("SpeechSynthesisController", "ControllerHandler run over");
            if (e.this.i) {
                Looper.myLooper().quit();
            }
        }
    }

    /* JADX INFO: compiled from: SpeechSynthesisController.java */
    class g implements Runnable {
        private f a;
        private final CountDownLatch b = new CountDownLatch(1);

        g() {
        }

        public f a() {
            try {
                this.b.await();
            } catch (InterruptedException e) {
                i0.c("SpeechSynthesisController", com.huawei.hms.mlsdk.t.a.a("Exception:").append(e.getMessage()).toString());
            }
            if (e.this.i) {
                return null;
            }
            return this.a;
        }

        @Override // java.lang.Runnable
        public void run() {
            Looper.prepare();
            this.a = e.this.new f(null);
            this.b.countDown();
            Looper.loop();
        }
    }

    public e(InterfaceC0123e interfaceC0123e, MLTtsConfig mLTtsConfig) {
        this.a = false;
        this.h = false;
        this.i = false;
        this.j = false;
        this.k = 0;
        this.l = true;
        this.m = true;
        this.n = false;
        this.o = new HashMap();
        this.p = new ConcurrentLinkedQueue();
        this.q = new HashMap();
        this.r = new HashMap();
        this.u = null;
        this.v = null;
        ReentrantLock reentrantLock = new ReentrantLock();
        this.w = reentrantLock;
        this.x = reentrantLock.newCondition();
        this.y = new a();
        this.z = new b();
        this.A = 0L;
        a(interfaceC0123e, mLTtsConfig, false);
    }

    private boolean c(int i) {
        return (i & 1) == 1;
    }

    private void i() {
        Map<Integer, Integer> map = this.q;
        Integer numValueOf = Integer.valueOf(MLTtsError.ERR_AUTHORIZE_FAILED);
        map.put(7001, numValueOf);
        this.q.put(7010, Integer.valueOf(MLTtsError.ERR_AUTHORIZE_TOKEN_INVALIDE));
        this.q.put(7002, Integer.valueOf(MLTtsError.ERR_ILLEGAL_PARAMETER));
        Map<Integer, Integer> map2 = this.q;
        Integer numValueOf2 = Integer.valueOf(MLTtsError.ERR_SPEECH_SYNTHESIS_FAILED);
        map2.put(7003, numValueOf2);
        this.q.put(7008, numValueOf2);
        this.q.put(7009, numValueOf2);
        this.q.put(7006, numValueOf2);
        this.q.put(7011, numValueOf);
        this.q.put(7005, Integer.valueOf(MLTtsError.ERR_INSUFFICIENT_BALANCE));
        this.q.put(100, numValueOf2);
        this.q.put(101, numValueOf2);
        this.q.put(102, numValueOf2);
        Map<Integer, Integer> map3 = this.q;
        Integer numValueOf3 = Integer.valueOf(MLTtsError.ERR_INTERNAL);
        map3.put(103, numValueOf3);
        Map<Integer, Integer> map4 = this.q;
        Integer numValueOf4 = Integer.valueOf(MLTtsError.ERR_NET_CONNECT_FAILED);
        map4.put(104, numValueOf4);
        this.q.put(105, numValueOf4);
        this.q.put(198, numValueOf3);
        this.q.put(199, Integer.valueOf(MLTtsError.ERR_UNKNOWN));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void l() {
        if (this.p.isEmpty() || this.j) {
            return;
        }
        i0.a("SpeechSynthesisController", com.huawei.hms.mlsdk.t.a.a("isAudioDrain=").append(this.l).append("; isEngineFree=").append(this.m).toString());
        y yVarPoll = this.p.poll();
        if (yVarPoll == null) {
            return;
        }
        String strF = yVarPoll.f();
        if (yVarPoll.g()) {
            if (!yVarPoll.k()) {
                this.d.a(1, strF, (Bundle) null);
                this.d.a(yVarPoll);
            }
            if (c(strF)) {
                i0.c("SpeechSynthesisController", "TaskId:" + strF + "is Exist");
                synchronized (this.o) {
                    if (!yVarPoll.h()) {
                        this.f.onDispatchSynthesisComplete(strF, yVarPoll.h());
                    }
                    i0.c("SpeechSynthesisController", "processSegment one audioSegment.isEndInterrupted(): " + yVarPoll.h());
                    this.o.remove(strF);
                }
                return;
            }
            return;
        }
        if (yVarPoll.i()) {
            this.k = 0;
            if (yVarPoll.k()) {
                return;
            }
            this.d.a(0, strF, (Bundle) null);
            return;
        }
        if (yVarPoll.j() && !yVarPoll.k()) {
            Bundle bundle = new Bundle();
            bundle.putInt("rangeStart", yVarPoll.d());
            bundle.putInt("rangeEnd", yVarPoll.c());
            this.d.a(2, strF, bundle);
            return;
        }
        if (!c(strF) || !this.h) {
            i0.c("SpeechSynthesisController", "The Task " + strF + " to be processed has been canceled!");
            String str = "processSegment two audioSegment.isEndInterrupted(): " + yVarPoll.h();
            return;
        }
        this.j = true;
        if (yVarPoll.a() != 2) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            this.e = null;
            v vVar = new v();
            this.e = vVar;
            vVar.a(yVarPoll);
            this.e.a(new d(jCurrentTimeMillis));
            this.e.a();
            return;
        }
        if (yVarPoll.l()) {
            MLTtsAudioFragment mLTtsAudioFragmentBuild = new MLTtsAudioFragment.Builder().setAudioData(yVarPoll.e()).setSampleRateInHz(MLTtsAudioFragment.SAMPLE_RATE_16K).setChannelInfo(4).setAudioFormat(2).build();
            Pair<Integer, Integer> pair = new Pair<>(Integer.valueOf(yVarPoll.d()), Integer.valueOf(yVarPoll.c()));
            InterfaceC0123e interfaceC0123e = this.f;
            if (interfaceC0123e != null) {
                interfaceC0123e.onDispatchAudioAvailable(strF, mLTtsAudioFragmentBuild, yVarPoll.b(), pair, new Bundle());
            }
        }
        if (!yVarPoll.k()) {
            this.d.a(yVarPoll);
            if (this.B) {
                this.B = false;
                i0.c("SpeechSynthesisController", com.huawei.hms.mlsdk.t.a.a("Time cost for the first frame playback delay: ").append(System.currentTimeMillis() - this.A).append(LanguageCodeUtil.MS).toString());
            }
        }
        this.j = false;
        k();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        this.h = false;
        i0.c("SpeechSynthesisController", "Data clear eventManager[" + this.g.a() + "] taskHistory[" + this.o.size() + "]");
        this.p.clear();
        synchronized (this.o) {
            for (String str : this.o.keySet()) {
                i0.c("SpeechSynthesisController", "clearData():");
                this.f.onDispatchSynthesisComplete(str, true);
            }
            this.o.clear();
        }
        v vVar = this.e;
        if (vVar != null) {
            vVar.b();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean j() {
        i0.a("SpeechSynthesisController", com.huawei.hms.mlsdk.t.a.a("isAudioDrain=").append(this.l).append(" ; hasNextEvent[").append(this.g.e()).append("]; isEngineFree=").append(this.m).toString());
        h hVarC = this.g.c();
        if (hVarC == null) {
            i0.c("SpeechSynthesisController", "isReadyToStartSynthesis(), head is null");
            return false;
        }
        if (this.g.b() && this.m) {
            return hVarC.f() || this.l;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        f fVarA;
        this.h = true;
        g gVar = this.t;
        if (gVar != null && (fVarA = gVar.a()) != null && !fVarA.hasMessages(2)) {
            fVarA.sendEmptyMessage(2);
        }
        if (this.n) {
            return;
        }
        i0.a("SpeechSynthesisController", "notifyStartThread");
        this.w.lock();
        try {
            this.x.signal();
        } finally {
            this.w.unlock();
        }
    }

    public void f() {
        com.huawei.hms.mlsdk.t.b.b().a(MLApplication.getInstance().getAppContext());
        if (this.a) {
            com.huawei.hms.mlsdk.tts.engine.common.a aVarA = new com.huawei.hms.mlsdk.tts.engine.common.a().c(this.s.getPerson()).a(1.0f).b(MLTtsAudioFragment.SAMPLE_RATE_16K).b(this.s.getSpeed()).c(this.s.getVolume()).b(this.s.getLanguage()).a(2);
            this.c.a(this.y);
            this.c.a(aVarA);
            i();
            com.huawei.hms.mlsdk.tts.engine.common.b.e().a(new com.huawei.hms.mlsdk.t.f(this));
        } else {
            com.huawei.hms.mlsdk.tts.engine.common.a aVarC = new com.huawei.hms.mlsdk.tts.engine.common.a().c(this.s.getPerson()).a(1.0f).b(this.s.getSpeed()).c(this.s.getVolume());
            aVarC.b(com.huawei.hms.mlsdk.tts.engine.common.b.e().a(aVarC.d()));
            aVarC.a(g0.a(aVarC.d()));
            this.b.a(this.y);
            this.b.a(aVarC);
            i();
            com.huawei.hms.mlsdk.tts.engine.common.b.e().b(new com.huawei.hms.mlsdk.t.g(this));
        }
        this.d.a(this.z);
        this.d.b();
        this.r.put(299, Integer.valueOf(MLTtsError.ERR_UNKNOWN));
        this.r.put(202, Integer.valueOf(MLTtsError.ERR_AUDIO_PLAYER_FAILED));
        this.r.put(200, Integer.valueOf(MLTtsError.ERR_AUDIO_PLAYER_FAILED));
        this.r.put(201, Integer.valueOf(MLTtsError.ERR_AUDIO_PLAYER_FAILED));
        this.t = new g();
        j0.b().a(this.t);
        k();
        HandlerThread handlerThread = new HandlerThread("ControllerThread");
        this.u = handlerThread;
        handlerThread.start();
        this.v = new c(this.u.getLooper());
    }

    public void g() {
        if (b("null")) {
            b(7);
        }
    }

    public Set<MLTtsSpeaker> b() {
        if (this.a) {
            return com.huawei.hms.mlsdk.tts.engine.common.b.e().b();
        }
        return com.huawei.hms.mlsdk.tts.engine.common.b.e().c();
    }

    public void c() {
        if (b("null")) {
            b(5);
        }
    }

    public void d() {
        if (b("null")) {
            b(6);
        }
    }

    public void e() {
        if (b("null")) {
            this.i = true;
            i0.c("SpeechSynthesisController", "use shutdown method to destroy engine");
            com.huawei.hms.mlsdk.t.b.b().a();
            if (this.a) {
                this.c.a();
            } else {
                this.b.b();
            }
            h();
            this.d.a();
            this.u.quit();
            j0.b().a();
            this.h = false;
            this.w.lock();
            try {
                this.x.signal();
                this.w.unlock();
                this.t = null;
            } catch (Throwable th) {
                this.w.unlock();
                throw th;
            }
        }
    }

    public String a(String str, int i) {
        String str2;
        StringBuilder sbAppend = new StringBuilder().append(String.valueOf(System.currentTimeMillis()));
        StringBuilder sb = new StringBuilder(3);
        try {
            SecureRandom instanceStrong = SecureRandom.getInstanceStrong();
            for (int i2 = 0; i2 < 3; i2++) {
                sb.append("0123456789".charAt(instanceStrong.nextInt(10)));
            }
        } catch (NoSuchAlgorithmException e) {
            i0.c("Utils", com.huawei.hms.mlsdk.t.a.a("generateRandomString exception e: ").append(e.getMessage()).toString());
        }
        String string = sbAppend.append(sb.toString()).toString();
        if (!b(string)) {
            i0.c("SpeechSynthesisController", "the engine does not init");
            return string;
        }
        if (this.a && !f0.a()) {
            try {
                Class<?> cls = Class.forName("android.os.SystemProperties");
                str2 = (String) cls.getDeclaredMethod("get", String.class).invoke(cls, "ro.product.manufacturer");
            } catch (ClassNotFoundException unused) {
                i0.b("EmuiUtil", Constant.CLASS_NOT_FIND_EXCEPTION);
                str2 = "";
            } catch (IllegalAccessException unused2) {
                i0.b("EmuiUtil", "IllegalAccessException");
                str2 = "";
            } catch (NoSuchMethodException unused3) {
                i0.b("EmuiUtil", Constant.NO_SUCH_METHOD_EXCEPTION);
                str2 = "";
            } catch (InvocationTargetException unused4) {
                i0.b("EmuiUtil", "InvocationTargetException");
                str2 = "";
            }
            i0.c("EmuiUtil", "Get Manufacturer: " + str2);
            if (!"HUAWEI".equals(str2) && !"HONOR".equals(str2)) {
                Bundle bundle = new Bundle();
                bundle.putString("taskId", string);
                bundle.putInt("errId", MLTtsError.ERR_AUTHORIZE_FAILED);
                bundle.putString("errMsg", "Not supported on non-Huawei phones.");
                a(8, "0106", bundle);
                return string;
            }
        }
        if (!TextUtils.isEmpty(str)) {
            if (!str.equals(new String(str.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8))) {
                i0.d("SpeechSynthesisController", "Text not UTF-8");
            } else {
                if (this.s.getSynthesizeMode().equals(MLTtsConstants.TTS_OFFLINE_MODE) && !TextUtils.isEmpty(this.s.getLanguage()) && !TextUtils.isEmpty(this.s.getPerson())) {
                    if (!this.s.getPerson().toLowerCase(Locale.ENGLISH).contains(this.s.getLanguage().toLowerCase(Locale.ENGLISH))) {
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("taskId", string);
                        bundle2.putInt("errId", MLTtsError.ERR_ILLEGAL_PARAMETER);
                        bundle2.putString("errMsg", "The language doesn't match the speaker.");
                        a(8, "0106", bundle2);
                        return string;
                    }
                }
                if (c(i)) {
                    i0.c("SpeechSynthesisController", "init flush mode, clear data");
                    b(7);
                }
                boolean z = (i & 2) == 2;
                boolean z2 = (i & 4) == 4;
                if (z && !z2) {
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("taskId", string);
                    bundle3.putInt("errId", MLTtsError.ERR_ILLEGAL_PARAMETER);
                    bundle3.putString("errMsg", "Incorrect input mode.");
                    a(8, "0106", bundle3);
                    return string;
                }
                h hVarA = new h.a().c(string).d(str).a(this.s.getLanguage()).b(this.s.getPerson()).a(z).c(z2).b(c(i)).a();
                if (c(i)) {
                    i0.c("SpeechSynthesisController", "execute flush mode, add text");
                    Message messageObtain = Message.obtain();
                    messageObtain.what = 4;
                    messageObtain.obj = hVarA;
                    this.v.sendMessageDelayed(messageObtain, 100L);
                } else {
                    Message messageObtain2 = Message.obtain();
                    messageObtain2.what = 4;
                    messageObtain2.obj = hVarA;
                    this.v.sendMessage(messageObtain2);
                }
                return string;
            }
        }
        Bundle bundle4 = new Bundle();
        bundle4.putString("taskId", string);
        bundle4.putInt("errId", MLTtsError.ERR_ILLEGAL_PARAMETER);
        bundle4.putString("errMsg", "Input text error.");
        a(8, "0106", bundle4);
        return string;
    }

    /* JADX INFO: compiled from: SpeechSynthesisController.java */
    class b implements d0 {
        Set<String> a = new HashSet();
        boolean b;

        b() {
        }

        @Override // com.huawei.hms.mlsdk.t.d0
        public void a(String str, boolean z) {
            i0.a("SpeechSynthesisController", "AudioPlay onStop " + str);
            e.this.l = true;
            if (this.a.contains(str)) {
                e.this.f.onDispatchStop(str, z);
                this.a.remove(str);
            }
            e.this.k();
        }

        @Override // com.huawei.hms.mlsdk.t.d0
        public void b(String str) {
            if (this.a.contains(str)) {
                this.b = e.this.l;
                e.this.l = false;
                e.this.f.onDispatchPause(str);
            }
        }

        @Override // com.huawei.hms.mlsdk.t.d0
        public void c(String str) {
            if (this.a.contains(str)) {
                e.this.l = this.b;
                e.this.f.onDispatchResume(str);
            }
        }

        @Override // com.huawei.hms.mlsdk.t.d0
        public void d(String str) {
            i0.a("SpeechSynthesisController", "AudioPlay onStart " + str);
            e.this.l = false;
            if (this.a.contains(str)) {
                return;
            }
            e.this.f.onDispatchStart(str);
            this.a.add(str);
        }

        @Override // com.huawei.hms.mlsdk.t.d0
        public void onRangeStart(String str, int i, int i2) {
            i0.a("SpeechSynthesisController", "onRangeStart taskId[" + str + "] start[" + i + ", " + i2 + "]");
            e.this.f.onDispatchRangeStart(str, i, i2);
        }

        @Override // com.huawei.hms.mlsdk.t.d0
        public void a(String str, int i, String str2) {
            i0.a("SpeechSynthesisController", "Receive player error taskId[" + str + "] errorCode[" + i + "] errorMsg[" + str2 + "]");
            int iIntValue = e.this.r.containsKey(Integer.valueOf(i)) ? ((Integer) e.this.r.get(Integer.valueOf(i))).intValue() : MLTtsError.ERR_UNKNOWN;
            if (!e.this.r.containsKey(Integer.valueOf(i))) {
                str2 = "Unknown Error";
            }
            e.this.f.onDispatchError(str, new MLTtsError.Builder().setErrorId(iIntValue).setErrorMsg(str2).build());
        }

        @Override // com.huawei.hms.mlsdk.t.d0
        public void a(String str) {
            e.this.l = true;
            e.this.k();
        }
    }

    /* JADX INFO: compiled from: SpeechSynthesisController.java */
    class d implements z.a {
        int a = -1;
        int b = 0;
        final /* synthetic */ long c;

        d(long j) {
            this.c = j;
        }

        @Override // com.huawei.hms.mlsdk.t.z.a
        public void a(String str) {
            e.this.j = false;
            e.this.k += this.a;
            i0.a("SpeechSynthesisController", com.huawei.hms.mlsdk.t.a.a("Decoder completed; currentIndex[").append(e.this.k).append("]time=[").append(System.currentTimeMillis() - this.c).append("]").toString());
            e.this.k();
        }

        @Override // com.huawei.hms.mlsdk.t.z.a
        public void a(y yVar) {
            int iB;
            int i;
            int i2;
            if (yVar.l() || g0.c(e.this.s.getPerson())) {
                MLTtsAudioFragment mLTtsAudioFragmentBuild = new MLTtsAudioFragment.Builder().setAudioData(yVar.e()).setSampleRateInHz(MLTtsAudioFragment.SAMPLE_RATE_16K).setChannelInfo(4).setAudioFormat(2).build();
                Pair<Integer, Integer> pair = new Pair<>(Integer.valueOf(yVar.d()), Integer.valueOf(yVar.c()));
                this.a++;
                if (!g0.c(e.this.s.getPerson())) {
                    if (e.this.f != null && yVar.l()) {
                        e.this.f.onDispatchAudioAvailable(yVar.f(), mLTtsAudioFragmentBuild, e.this.k + yVar.b() + this.a, pair, new Bundle());
                    }
                    iB = 0;
                } else {
                    iB = yVar.b() + this.a + e.this.k;
                    if (iB > 0) {
                        if (yVar.e().length == 94) {
                            this.b = 0;
                        }
                        this.b++;
                    }
                    if ((iB <= 4 || (i = this.b) <= 0 || i > 4) && yVar.e().length != 1058) {
                        if (e.this.f != null && yVar.l()) {
                            e.this.f.onDispatchAudioAvailable(yVar.f(), mLTtsAudioFragmentBuild, yVar.b() + this.a + e.this.k, pair, new Bundle());
                        }
                    } else {
                        this.a--;
                    }
                }
            } else {
                iB = 0;
            }
            if (yVar.k()) {
                return;
            }
            if (!g0.c(e.this.s.getPerson())) {
                e.this.d.a(yVar);
                if (e.this.B) {
                    e.this.B = false;
                    i0.c("SpeechSynthesisController", com.huawei.hms.mlsdk.t.a.a("Time cost for the first frame playback delay: ").append(System.currentTimeMillis() - e.this.A).append(LanguageCodeUtil.MS).toString());
                    return;
                }
                return;
            }
            if ((iB <= 4 || (i2 = this.b) <= 0 || i2 > 4) && yVar.e().length != 1058) {
                e.this.d.a(yVar);
                if (e.this.B) {
                    e.this.B = false;
                    i0.c("SpeechSynthesisController", com.huawei.hms.mlsdk.t.a.a("Time cost for the first frame playback delay: ").append(System.currentTimeMillis() - e.this.A).append(LanguageCodeUtil.MS).toString());
                }
            }
        }

        @Override // com.huawei.hms.mlsdk.t.z.a
        public void a(String str, int i, String str2) {
            i0.b("SpeechSynthesisController", "Receive player error taskId[" + str + "] errorCode[" + i + "] errorMsg[" + str2 + "]");
            e.this.f.onDispatchError(str, new MLTtsError.Builder().setErrorId(MLTtsError.ERR_AUDIO_PLAYER_FAILED).setErrorMsg(str2).setExtension("0" + i).build());
        }
    }

    private void b(int i) {
        Message messageObtain = Message.obtain();
        messageObtain.what = i;
        this.v.sendMessage(messageObtain);
    }

    private boolean c(String str) {
        return this.o.containsKey(str);
    }

    private boolean b(String str) {
        if (!this.i) {
            return true;
        }
        this.f.onDispatchError(str, new MLTtsError.Builder().setErrorId(MLTtsError.ERR_SPEECH_SYNTHESIS_FAILED).setErrorMsg("The engine has been shutdown.").build());
        return false;
    }

    static /* synthetic */ boolean i(e eVar) {
        return (eVar.p.isEmpty() || eVar.j) ? false : true;
    }

    private void a(InterfaceC0123e interfaceC0123e, MLTtsConfig mLTtsConfig, boolean z) {
        this.f = interfaceC0123e;
        if (z) {
            MLTtsConfig synthesizeMode = new MLTtsConfig().setVolume(1.0f).setSpeed(1.0f).setSynthesizeMode(MLTtsConstants.TTS_OFFLINE_MODE);
            this.s = synthesizeMode;
            synthesizeMode.attach(mLTtsConfig);
            this.c = new SynthesisEngineOffline();
            this.d = new w(new e0().a(2).d(MLTtsAudioFragment.SAMPLE_RATE_16K).b(4).c(1).e(3));
        } else {
            MLTtsConfig synthesizeMode2 = new MLTtsConfig().setVolume(1.0f).setSpeed(1.0f).setLanguage(MLTtsConstants.TTS_ZH_HANS).setPerson(MLTtsConstants.TTS_SPEAKER_FEMALE_ZH).setSynthesizeMode("online");
            this.s = synthesizeMode2;
            synthesizeMode2.attach(mLTtsConfig);
            this.b = new m();
            this.d = new w(new e0().a(2).d(com.huawei.hms.mlsdk.tts.engine.common.b.e().a(this.s.getPerson())).b(4).c(1).e(3));
        }
        this.g = new com.huawei.hms.mlsdk.t.d();
    }

    public void a(MLTtsConfig mLTtsConfig) {
        if (mLTtsConfig == null) {
            return;
        }
        if (!this.a && !mLTtsConfig.getPerson().equals(this.s.getPerson())) {
            i0.c("SpeechSynthesisController", "updateConfig, change speaker");
            g();
            this.d.a(new e0().a(2).d(com.huawei.hms.mlsdk.tts.engine.common.b.e().a(mLTtsConfig.getPerson())).b(4).c(1).e(3));
        }
        this.s.attach(mLTtsConfig);
        if (this.a) {
            this.c.a(new com.huawei.hms.mlsdk.tts.engine.common.a().a(1.0f).b(MLTtsAudioFragment.SAMPLE_RATE_16K).b(this.s.getSpeed()).c(this.s.getVolume()).c(this.s.getPerson()).b(this.s.getLanguage()).a(2));
        } else {
            com.huawei.hms.mlsdk.tts.engine.common.a aVarC = new com.huawei.hms.mlsdk.tts.engine.common.a().a(1.0f).b(this.s.getSpeed()).c(this.s.getVolume()).c(this.s.getPerson());
            aVarC.b(com.huawei.hms.mlsdk.tts.engine.common.b.e().a(this.s.getPerson()));
            aVarC.a(g0.a(aVarC.d()));
            this.b.a(aVarC);
        }
    }

    static /* synthetic */ void l(e eVar) {
        h hVarD;
        if (eVar.h && (hVarD = eVar.g.d()) != null) {
            if (eVar.a) {
                eVar.c.a(hVarD);
            } else {
                eVar.b.a(hVarD);
            }
            eVar.A = System.currentTimeMillis();
            eVar.B = true;
            Bundle bundle = new Bundle();
            bundle.putString("language", hVarD.a());
            bundle.putString("voiceName", eVar.s.getPerson());
            bundle.putInt("textLen", hVarD.d().length());
            com.huawei.hms.mlsdk.t.b.b().a(hVarD.c(), 0, bundle);
        }
    }

    public void a(int i) {
        w wVar = this.d;
        if (wVar != null) {
            wVar.a(i);
        }
    }

    public Set<MLTtsSpeaker> a() {
        return com.huawei.hms.mlsdk.tts.engine.common.b.e().a();
    }

    public int a(String str) {
        return com.huawei.hms.mlsdk.tts.engine.common.b.e().a(str, this.a);
    }

    private void a(int i, Object obj, Bundle bundle) {
        Message messageObtain = Message.obtain();
        messageObtain.what = i;
        messageObtain.obj = obj;
        messageObtain.setData(bundle);
        this.v.sendMessage(messageObtain);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(y yVar) {
        String strF = yVar.f();
        if (!c(strF)) {
            i0.a("SpeechSynthesisController", "addSegment Task " + strF + " has been clear, no need add audio");
            return;
        }
        i0.a("SpeechSynthesisController", com.huawei.hms.mlsdk.t.a.a("queueOfAudioSegment size: ").append(this.p.size()).append(", isSuccess = ").append(this.p.offer(yVar)).toString());
        k();
    }

    static /* synthetic */ void a(e eVar, h hVar) {
        i0.a("SpeechSynthesisController", "eventManager size[" + eVar.g.a(hVar) + "]");
        eVar.o.put(hVar.c(), Integer.valueOf(hVar.d().length()));
        com.huawei.hms.mlsdk.t.b.b().a(hVar.c());
    }

    public e(InterfaceC0123e interfaceC0123e, MLTtsConfig mLTtsConfig, boolean z) {
        this.a = false;
        this.h = false;
        this.i = false;
        this.j = false;
        this.k = 0;
        this.l = true;
        this.m = true;
        this.n = false;
        this.o = new HashMap();
        this.p = new ConcurrentLinkedQueue();
        this.q = new HashMap();
        this.r = new HashMap();
        this.u = null;
        this.v = null;
        ReentrantLock reentrantLock = new ReentrantLock();
        this.w = reentrantLock;
        this.x = reentrantLock.newCondition();
        this.y = new a();
        this.z = new b();
        this.A = 0L;
        this.a = z;
        a(interfaceC0123e, mLTtsConfig, z);
    }
}
