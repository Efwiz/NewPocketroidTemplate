package com.huawei.hms.mlsdk.tts.engine.offline;

import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Tasks;
import com.huawei.hms.mlkit.tts.common.TtsCancelParcel;
import com.huawei.hms.mlkit.tts.common.TtsOptionsParcel;
import com.huawei.hms.mlkit.tts.common.TtsTextParcel;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.model.download.MLLocalModelManager;
import com.huawei.hms.mlsdk.t.h;
import com.huawei.hms.mlsdk.t.i;
import com.huawei.hms.mlsdk.t.i0;
import com.huawei.hms.mlsdk.t.j0;
import com.huawei.hms.mlsdk.t.k;
import com.huawei.hms.mlsdk.tts.MLTtsAudioFragment;
import com.huawei.hms.mlsdk.tts.MLTtsLocalModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes10.dex */
public class SynthesisEngineOffline extends i {
    private MLLocalModelManager e;
    private com.huawei.hms.mlsdk.tts.client.b i;
    private final Lock k;
    private Condition l;
    private long m;
    private long n;
    private final List<String> d = new ArrayList();
    private int f = -1;
    private int g = 0;
    private int h = 0;
    private boolean j = false;

    class a implements OnFailureListener {
        a() {
        }

        @Override // com.huawei.hmf.tasks.OnFailureListener
        public void onFailure(Exception exc) {
            i0.a("SynthesisEngine", com.huawei.hms.mlsdk.t.a.a("Exception:").append(exc.getMessage()).toString());
            SynthesisEngineOffline.a(SynthesisEngineOffline.this);
        }
    }

    class b implements OnSuccessListener<File> {
        b() {
        }

        @Override // com.huawei.hmf.tasks.OnSuccessListener
        public void onSuccess(File file) {
            File file2 = file;
            if (file2 == null) {
                i0.a("SynthesisEngine", "file not found");
                SynthesisEngineOffline.a(SynthesisEngineOffline.this);
                return;
            }
            try {
                ((i) SynthesisEngineOffline.this).a.a(file2.getCanonicalPath());
                i0.a("SynthesisEngine", "filePath==" + ((i) SynthesisEngineOffline.this).a.b());
                Bundle bundle = MLApplication.getInstance().toBundle();
                bundle.putString("com.huawei.hms.client.service.name:ml-computer-tts", "ml-computer-tts3.7.0.303");
                TtsOptionsParcel ttsOptionsParcel = new TtsOptionsParcel(bundle, ((i) SynthesisEngineOffline.this).a.d(), ((i) SynthesisEngineOffline.this).a.c(), ((i) SynthesisEngineOffline.this).a.g(), ((i) SynthesisEngineOffline.this).a.h(), ((i) SynthesisEngineOffline.this).a.e(), "", ((i) SynthesisEngineOffline.this).a.b());
                SynthesisEngineOffline synthesisEngineOffline = SynthesisEngineOffline.this;
                synthesisEngineOffline.i = com.huawei.hms.mlsdk.tts.client.c.b(((i) synthesisEngineOffline).a.d());
                if (SynthesisEngineOffline.this.i != null) {
                    Tasks.callInBackground(new e(this, ttsOptionsParcel)).addOnSuccessListener(new com.huawei.hms.mlsdk.tts.engine.offline.d(this)).addOnFailureListener(new com.huawei.hms.mlsdk.tts.engine.offline.c(this));
                }
            } catch (IOException | RuntimeException e) {
                i0.a("SynthesisEngine", com.huawei.hms.mlsdk.t.a.a("IOException: ").append(e.getMessage()).toString());
                SynthesisEngineOffline.a(SynthesisEngineOffline.this);
            }
        }
    }

    class c implements com.huawei.hms.mlsdk.tts.engine.offline.b {
        final /* synthetic */ h a;

        c(h hVar) {
            this.a = hVar;
        }

        @Override // com.huawei.hms.mlsdk.tts.engine.offline.b
        public void a(byte[] bArr, int i, int i2) {
            SynthesisEngineOffline.h(SynthesisEngineOffline.this);
            if (SynthesisEngineOffline.this.f == 0) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("playExter", this.a.f());
                SynthesisEngineOffline.this.a(this.a.c(), 0, bundle);
                SynthesisEngineOffline.this.a(i, i2, this.a);
            }
            if (!this.a.f() && SynthesisEngineOffline.this.g != i && SynthesisEngineOffline.this.h != i2) {
                SynthesisEngineOffline.this.a(i, i2, this.a);
            }
            i0.a("SynthesisEngine", com.huawei.hms.mlsdk.t.a.a("audio result = ").append(Arrays.toString(bArr)).toString());
            SynthesisEngineOffline synthesisEngineOffline = SynthesisEngineOffline.this;
            synthesisEngineOffline.a(this.a, bArr, 2, i, i2, synthesisEngineOffline.f);
        }
    }

    class d implements Runnable {
        private String a;
        private h b;

        public d(h hVar) {
            this.b = hVar;
            this.a = hVar.c();
        }

        @Override // java.lang.Runnable
        public void run() {
            if (SynthesisEngineOffline.this.a(this.a)) {
                return;
            }
            SynthesisEngineOffline.this.b(this.b);
            synchronized (SynthesisEngineOffline.this.d) {
                if (SynthesisEngineOffline.this.d.contains(this.a)) {
                    if (((i) SynthesisEngineOffline.this).b != null) {
                        ((i) SynthesisEngineOffline.this).b.b(this.a);
                    }
                    SynthesisEngineOffline.this.d.remove(this.a);
                }
            }
        }
    }

    public SynthesisEngineOffline() {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.k = reentrantLock;
        this.l = reentrantLock.newCondition();
        this.m = 0L;
        this.e = MLLocalModelManager.getInstance();
        this.a = new com.huawei.hms.mlsdk.tts.engine.common.a().a(1.0f).b(MLTtsAudioFragment.SAMPLE_RATE_16K).b(1.0f).c(1.0f);
    }

    static /* synthetic */ int h(SynthesisEngineOffline synthesisEngineOffline) {
        int i = synthesisEngineOffline.f;
        synthesisEngineOffline.f = i + 1;
        return i;
    }

    public void b() {
        i0.c("SynthesisEngine", com.huawei.hms.mlsdk.t.a.a("stopSpeechSynthesis taskRecording:").append(this.d.size()).toString());
        synchronized (this.d) {
            for (int i = 0; i < this.d.size(); i++) {
                this.c.add(this.d.get(i));
                if (this.b != null) {
                    this.b.b(this.d.get(i));
                }
            }
            com.huawei.hms.mlsdk.tts.client.b bVar = this.i;
            if (bVar != null) {
                bVar.a(new TtsCancelParcel(this.c));
            }
            this.d.clear();
        }
    }

    public void a(com.huawei.hms.mlsdk.tts.engine.common.a aVar) {
        this.a = aVar;
        this.m = 0L;
        if (TextUtils.isEmpty(this.a.d())) {
            return;
        }
        this.e.getRecentModelFile(new MLTtsLocalModel.Factory(this.a.d()).create()).addOnSuccessListener(new b()).addOnFailureListener(new a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(h hVar) {
        com.huawei.hms.mlsdk.tts.client.b bVar;
        String strC = hVar.c();
        TtsTextParcel ttsTextParcel = new TtsTextParcel(strC, hVar.a(), hVar.b(), hVar.d());
        if (!a(hVar.c()) && (bVar = this.i) != null) {
            bVar.a(MLApplication.getInstance().getAppContext(), ttsTextParcel);
        }
        boolean zA = a(strC);
        Bundle bundle = new Bundle();
        bundle.putBoolean("interrupted", zA);
        bundle.putBoolean("playExter", hVar.f());
        a(hVar.c(), 1, bundle);
    }

    public void a(h hVar) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j = this.m;
        if (j == 0 || jCurrentTimeMillis <= j) {
            this.k.lock();
            try {
                try {
                    this.l.await();
                } catch (InterruptedException e) {
                    i0.a("SynthesisEngine", "InterruptedException:" + e.getMessage());
                }
                this.k.unlock();
            } catch (Throwable th) {
                this.k.unlock();
                throw th;
            }
        }
        this.f = -1;
        this.g = 0;
        this.h = 0;
        i0.a("SynthesisEngine", "startSpeechSynthesis|event=" + hVar);
        k kVar = this.b;
        if (kVar != null) {
            kVar.a(hVar.c());
        }
        synchronized (this.d) {
            this.d.add(hVar.c());
        }
        c cVar = new c(hVar);
        com.huawei.hms.mlsdk.tts.client.b bVar = this.i;
        if (bVar != null) {
            bVar.a(cVar);
        }
        j0.b().a(new d(hVar));
    }

    static /* synthetic */ void a(SynthesisEngineOffline synthesisEngineOffline) {
        synthesisEngineOffline.k.lock();
        try {
            synthesisEngineOffline.l.signal();
        } finally {
            synthesisEngineOffline.k.unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, int i2, h hVar) {
        Bundle bundle = new Bundle();
        bundle.putInt("startIndex", i);
        bundle.putInt("endIndex", i2);
        bundle.putBoolean("playExter", hVar.f());
        a(hVar.c(), 2, bundle);
        this.g = i;
        this.h = i2;
    }

    public void a() {
        if (this.j) {
            com.huawei.hms.mlsdk.tts.client.a.a();
            this.j = false;
        }
    }
}
