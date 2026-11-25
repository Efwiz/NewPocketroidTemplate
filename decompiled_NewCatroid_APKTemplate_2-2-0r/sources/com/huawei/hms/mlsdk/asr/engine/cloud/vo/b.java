package com.huawei.hms.mlsdk.asr.engine.cloud.vo;

import java.nio.charset.StandardCharsets;

/* JADX INFO: compiled from: AsrDataRequest.java */
/* JADX INFO: loaded from: classes10.dex */
public class b {
    private String a;
    private long b;
    private byte[] c;

    public b(String str, long j, byte[] bArr) {
        this.a = str;
        this.b = j;
        this.c = bArr == null ? null : (byte[]) bArr.clone();
    }

    public byte[] a() {
        com.huawei.hms.mlsdk.asr.engine.utils.b bVar = new com.huawei.hms.mlsdk.asr.engine.utils.b();
        bVar.a(1);
        byte[] bytes = this.a.getBytes(StandardCharsets.UTF_8);
        bVar.a(bytes.length);
        bVar.a(bytes);
        bVar.a(2);
        bVar.a(this.b);
        bVar.a(3);
        bVar.a(this.c.length);
        bVar.a(this.c);
        return bVar.a();
    }
}
