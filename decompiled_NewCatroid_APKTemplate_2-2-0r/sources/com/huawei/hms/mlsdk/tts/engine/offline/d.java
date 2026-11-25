package com.huawei.hms.mlsdk.tts.engine.offline;

import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import com.huawei.hms.mlsdk.t.i;
import com.huawei.hms.mlsdk.t.i0;

/* JADX INFO: compiled from: SynthesisEngineOffline.java */
/* JADX INFO: loaded from: classes10.dex */
class d implements OnSuccessListener<Void> {
    final /* synthetic */ SynthesisEngineOffline.b a;

    d(SynthesisEngineOffline.b bVar) {
        this.a = bVar;
    }

    @Override // com.huawei.hmf.tasks.OnSuccessListener
    public void onSuccess(Void r5) {
        SynthesisEngineOffline.this.m = System.currentTimeMillis();
        i0.a("SynthesisEngine", ((i) SynthesisEngineOffline.this).a.d() + " initialize duration:" + (SynthesisEngineOffline.this.m - SynthesisEngineOffline.this.n) + LanguageCodeUtil.MS);
        SynthesisEngineOffline.a(SynthesisEngineOffline.this);
    }
}
