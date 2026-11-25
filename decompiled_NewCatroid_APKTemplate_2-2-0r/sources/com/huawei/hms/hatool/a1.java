package com.huawei.hms.hatool;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;
import com.beust.jcommander.Parameters;
import com.huawei.secure.android.common.encrypt.hash.SHA;
import java.util.UUID;

/* JADX INFO: loaded from: classes10.dex */
public class a1 {
    public static a1 b;
    public Context a;

    public static class a extends b1 {
        public String a;
        public String b;

        public a(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        @Override // com.huawei.hms.hatool.b1
        public String a() {
            return com.huawei.hms.hatool.a.d(this.a, this.b);
        }

        @Override // com.huawei.hms.hatool.b1
        public String a(String str) {
            return SHA.sha256Encrypt(str);
        }

        @Override // com.huawei.hms.hatool.b1
        public String b() {
            return com.huawei.hms.hatool.a.g(this.a, this.b);
        }

        @Override // com.huawei.hms.hatool.b1
        public String c() {
            return com.huawei.hms.hatool.a.j(this.a, this.b);
        }

        @Override // com.huawei.hms.hatool.b1
        public int d() {
            return (com.huawei.hms.hatool.a.k(this.a, this.b) ? 4 : 0) | (com.huawei.hms.hatool.a.e(this.a, this.b) ? 2 : 0) | (com.huawei.hms.hatool.a.h(this.a, this.b) ? 1 : 0);
        }
    }

    public static a1 a() {
        a1 a1Var;
        synchronized (a1.class) {
            if (b == null) {
                b = new a1();
            }
            a1Var = b;
        }
        return a1Var;
    }

    public String a(String str, String str2) {
        return g.a(this.a, str, str2);
    }

    public String a(boolean z) {
        if (!z) {
            return "";
        }
        String strE = b.e();
        if (TextUtils.isEmpty(strE)) {
            strE = h0.a(this.a, "global_v2", "uuid", "");
            if (TextUtils.isEmpty(strE)) {
                strE = UUID.randomUUID().toString().replace(Parameters.DEFAULT_OPTION_PREFIXES, "");
                h0.b(this.a, "global_v2", "uuid", strE);
            }
            b.h(strE);
        }
        return strE;
    }

    public void a(Context context) {
        if (this.a == null) {
            this.a = context;
        }
    }

    public String b(String str, String str2) {
        return g.b(this.a, str, str2);
    }

    public y0 c(String str, String str2) {
        return new a(str, str2).a(this.a);
    }

    public String d(String str, String str2) {
        return d1.b(str, str2);
    }

    public Pair<String, String> e(String str, String str2) {
        if (!com.huawei.hms.hatool.a.f(str, str2)) {
            return new Pair<>("", "");
        }
        String strP = i.c().b().p();
        String strQ = i.c().b().q();
        if (!TextUtils.isEmpty(strP) && !TextUtils.isEmpty(strQ)) {
            return new Pair<>(strP, strQ);
        }
        Pair<String, String> pairE = c1.e(this.a);
        i.c().b().k((String) pairE.first);
        i.c().b().l((String) pairE.second);
        return pairE;
    }

    public String f(String str, String str2) {
        return d1.a(str, str2);
    }
}
