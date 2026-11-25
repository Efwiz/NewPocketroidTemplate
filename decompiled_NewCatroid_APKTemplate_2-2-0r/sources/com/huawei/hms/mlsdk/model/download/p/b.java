package com.huawei.hms.mlsdk.model.download.p;

import com.huawei.hms.mlkit.common.ha.HianalyticsLogProvider;
import com.huawei.hms.mlsdk.common.MLApplication;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: compiled from: HaAdapter.java */
/* JADX INFO: loaded from: classes10.dex */
public class b {
    private c a;

    public b() {
        c cVar = new c();
        this.a = cVar;
        cVar.b(System.currentTimeMillis());
    }

    public void a(String str, String str2) {
        this.a.a(System.currentTimeMillis());
        this.a.a((int) (System.currentTimeMillis() - this.a.a()));
        this.a.b(SchemaSymbols.ATTVAL_TRUE_1);
        this.a.c(str2);
        this.a.a(str);
        HianalyticsLogProvider.getInstance().postEvent(MLApplication.getInstance().getAppContext(), 1, this.a);
    }

    public void a(String str) {
        this.a.a(System.currentTimeMillis());
        this.a.a((int) (System.currentTimeMillis() - this.a.a()));
        this.a.b("0");
        this.a.c("");
        this.a.a(str);
        HianalyticsLogProvider.getInstance().postEvent(MLApplication.getInstance().getAppContext(), 1, this.a);
    }
}
