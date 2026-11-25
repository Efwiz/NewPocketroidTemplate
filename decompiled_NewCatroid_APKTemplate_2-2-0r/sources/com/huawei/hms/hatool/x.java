package com.huawei.hms.hatool;

import com.beust.jcommander.Parameters;
import java.util.ArrayList;
import java.util.UUID;

/* JADX INFO: loaded from: classes10.dex */
public class x {
    public String a;
    public String b;
    public String c;
    public String d;
    public long e;

    public x(String str, String str2, String str3, String str4, long j) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = j;
    }

    public void a() {
        z.c("StreamEventHandler", "Begin to handle stream events...");
        q qVar = new q();
        qVar.b(this.c);
        qVar.d(this.b);
        qVar.a(this.d);
        qVar.c(String.valueOf(this.e));
        if ("oper".equals(this.b) && a.i(this.a, "oper")) {
            g0 g0VarA = f0.a().a(this.a, this.e);
            String strA = g0VarA.a();
            Boolean boolValueOf = Boolean.valueOf(g0VarA.b());
            qVar.f(strA);
            qVar.e(String.valueOf(boolValueOf));
        }
        String strReplace = UUID.randomUUID().toString().replace(Parameters.DEFAULT_OPTION_PREFIXES, "");
        ArrayList arrayList = new ArrayList();
        arrayList.add(qVar);
        new u(this.a, this.b, b.g(), arrayList, strReplace).a();
    }
}
