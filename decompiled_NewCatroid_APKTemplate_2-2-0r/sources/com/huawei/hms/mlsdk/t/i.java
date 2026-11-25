package com.huawei.hms.mlsdk.t;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: BaseSynthesisEngine.java */
/* JADX INFO: loaded from: classes10.dex */
public abstract class i implements j {
    protected com.huawei.hms.mlsdk.tts.engine.common.a a;
    protected k b;
    protected List<String> c = new ArrayList();

    public void a(k kVar) {
        this.b = kVar;
    }

    protected boolean a(String str) {
        List<String> list = this.c;
        return list != null && list.contains(str);
    }

    protected void a(String str, int i, Bundle bundle) {
        k kVar = this.b;
        if (kVar == null) {
            i0.d("SynthesisEngine", "Listener has been clear");
        } else {
            kVar.a(str, i, bundle);
        }
    }

    protected void a(h hVar, byte[] bArr, int i, int i2, int i3, int i4) {
        if (this.b == null) {
            i0.d("SynthesisEngine", "Listener has been clear");
        } else {
            this.b.a(hVar.c(), new y.b().a(hVar.c()).a(bArr).a(i).e(i2).d(i3).c(i4).b(hVar.f()).c(hVar.g()).a());
        }
    }
}
