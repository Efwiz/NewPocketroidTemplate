package com.huawei.hms.hatool;

/* JADX INFO: loaded from: classes10.dex */
public class n0 implements o0 {
    public String a;
    public String b;

    public n0(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        s0.a(this.a, this.b);
    }
}
