package com.huawei.hms.mlsdk.model.download.p;

import java.io.File;
import java.util.Map;

/* JADX INFO: compiled from: DownloadMangerListener.java */
/* JADX INFO: loaded from: classes10.dex */
public class d implements f {
    protected Map<String, i> a;
    private f b;

    public d(Map<String, i> map, f fVar) {
        this.b = fVar;
        this.a = map;
    }

    @Override // com.huawei.hms.mlsdk.model.download.p.f
    public void a(long j, long j2) {
        f fVar = this.b;
        if (fVar != null) {
            fVar.a(j, j2);
        }
    }

    @Override // com.huawei.hms.mlsdk.model.download.p.f
    public void a(String str, Exception exc) {
        f fVar = this.b;
        if (fVar != null) {
            fVar.a(str, exc);
        }
        this.a.remove(str);
    }

    @Override // com.huawei.hms.mlsdk.model.download.p.f
    public void a(String str, File file) {
        f fVar = this.b;
        if (fVar != null) {
            fVar.a(str, file);
        }
        this.a.remove(str);
    }
}
