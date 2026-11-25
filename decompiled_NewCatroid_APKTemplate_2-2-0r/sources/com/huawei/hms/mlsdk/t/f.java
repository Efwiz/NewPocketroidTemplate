package com.huawei.hms.mlsdk.t;

/* JADX INFO: compiled from: SpeechSynthesisController.java */
/* JADX INFO: loaded from: classes10.dex */
class f implements com.huawei.hms.mlsdk.tts.engine.common.b.l {
    f(e eVar) {
    }

    @Override // com.huawei.hms.mlsdk.tts.engine.common.b.l
    public void a(int i) {
        if (i == 0) {
            i0.a("SpeechSynthesisController", "requestSpeakerList success");
        } else if (1 == i) {
            i0.a("SpeechSynthesisController", "requestSpeakerList fail, begin to load sp or local data");
        }
        com.huawei.hms.mlsdk.tts.engine.common.b.e().b();
    }
}
