package com.huawei.hms.mlsdk.t;

/* JADX INFO: compiled from: SpeechSynthesisController.java */
/* JADX INFO: loaded from: classes10.dex */
class g implements com.huawei.hms.mlsdk.tts.engine.common.b.l {
    final /* synthetic */ e a;

    g(e eVar) {
        this.a = eVar;
    }

    @Override // com.huawei.hms.mlsdk.tts.engine.common.b.l
    public void a(int i) {
        if (i == 0) {
            i0.a("SpeechSynthesisController", "requestSpeakerList success");
        } else if (1 == i) {
            i0.a("SpeechSynthesisController", "requestSpeakerList fail, begin to load sp or local data");
        }
        com.huawei.hms.mlsdk.tts.engine.common.b.e().c();
        this.a.b.a(this.a.b.a().a(g0.a(this.a.s.getPerson())).b(com.huawei.hms.mlsdk.tts.engine.common.b.e().a(this.a.s.getPerson())));
        this.a.d.a(new e0().a(2).d(com.huawei.hms.mlsdk.tts.engine.common.b.e().a(this.a.s.getPerson())).b(4).c(1).e(3));
    }
}
