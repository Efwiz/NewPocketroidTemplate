package com.huawei.hms.mlsdk.tts.engine.offline;

import com.huawei.hms.mlkit.tts.common.TtsOptionsParcel;
import com.huawei.hms.mlsdk.t.i0;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: SynthesisEngineOffline.java */
/* JADX INFO: loaded from: classes10.dex */
class e implements Callable<Void> {
    final /* synthetic */ TtsOptionsParcel a;
    final /* synthetic */ SynthesisEngineOffline.b b;

    e(SynthesisEngineOffline.b bVar, TtsOptionsParcel ttsOptionsParcel) {
        this.b = bVar;
        this.a = ttsOptionsParcel;
    }

    @Override // java.util.concurrent.Callable
    public Void call() throws Exception {
        SynthesisEngineOffline.this.n = System.currentTimeMillis();
        if (SynthesisEngineOffline.this.i.a(this.a) < 0) {
            i0.b("SynthesisEngine", "RemoteOnDeviceTts.getInstance().initialize() failed");
            return null;
        }
        SynthesisEngineOffline.this.j = true;
        SynthesisEngineOffline.this.i.b(this.a);
        return null;
    }
}
