package com.huawei.hms.hatool;

import com.huawei.secure.android.common.encrypt.utils.EncryptUtil;

/* JADX INFO: loaded from: classes10.dex */
public class y {
    public static y b = new y();
    public a a = new a();

    public class a {
        public String a;
        public String b;
        public long c = 0;

        public a() {
        }

        public void a(long j) {
            y.this.a.c = j;
        }

        public void a(String str) {
            y.this.a.b = str;
        }

        public void b(String str) {
            y.this.a.a = str;
        }
    }

    public static y d() {
        return b;
    }

    public String a() {
        return this.a.b;
    }

    public void a(String str, String str2) {
        long jB = b();
        String strB = s0.b(str, str2);
        if (strB == null || strB.isEmpty()) {
            z.e("WorkKeyHandler", "get rsa pubkey config error");
            return;
        }
        if (jB == 0) {
            jB = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - jB <= 43200000) {
            return;
        }
        String strGenerateSecureRandomStr = EncryptUtil.generateSecureRandomStr(16);
        String strA = e.a(strB, strGenerateSecureRandomStr);
        this.a.a(jB);
        this.a.b(strGenerateSecureRandomStr);
        this.a.a(strA);
    }

    public long b() {
        return this.a.c;
    }

    public String c() {
        return this.a.a;
    }
}
