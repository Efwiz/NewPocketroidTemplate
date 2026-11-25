package com.huawei.hms.mlsdk.tts.engine.offline;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hms.mlsdk.t.i0;

/* JADX INFO: compiled from: SynthesisEngineOffline.java */
/* JADX INFO: loaded from: classes10.dex */
class c implements OnFailureListener {
    final /* synthetic */ SynthesisEngineOffline.b a;

    c(SynthesisEngineOffline.b bVar) {
        this.a = bVar;
    }

    @Override // com.huawei.hmf.tasks.OnFailureListener
    public void onFailure(Exception exc) {
        i0.a("SynthesisEngine", com.huawei.hms.mlsdk.t.a.a("Exception:").append(exc.getMessage()).toString());
        SynthesisEngineOffline.a(SynthesisEngineOffline.this);
    }
}
