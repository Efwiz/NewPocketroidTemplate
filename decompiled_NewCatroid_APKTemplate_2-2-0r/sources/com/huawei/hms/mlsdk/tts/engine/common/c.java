package com.huawei.hms.mlsdk.tts.engine.common;

import com.huawei.hms.mlsdk.t.i0;
import com.huawei.hms.mlsdk.tts.MLTtsConstants;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/* JADX INFO: compiled from: SpeakerProvider.java */
/* JADX INFO: loaded from: classes10.dex */
class c implements Callback {
    final /* synthetic */ b.l a;
    final /* synthetic */ OkHttpClient b;
    final /* synthetic */ b c;

    c(b bVar, b.l lVar, OkHttpClient okHttpClient) {
        this.c = bVar;
        this.a = lVar;
        this.b = okHttpClient;
    }

    @Override // okhttp3.Callback
    public void onFailure(Call call, IOException iOException) {
        this.c.a(iOException, this.a);
    }

    @Override // okhttp3.Callback
    public void onResponse(Call call, Response response) throws IOException {
        String strString = response.body().string();
        if (this.c.b(strString)) {
            this.c.b(this.a, this.b);
            return;
        }
        if (this.c.a(MLTtsConstants.TTS_OFFLINE_MODE, strString)) {
            this.c.d.addAll(this.c.c(strString));
            this.c.b(this.a, this.b);
            return;
        }
        i0.b("SpeakerProvider", "Use default List; Voice list parse error.[" + strString + "]");
        b.l lVar = this.a;
        if (lVar != null) {
            lVar.a(1);
        }
    }
}
