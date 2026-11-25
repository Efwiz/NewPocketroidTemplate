package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public enum TN implements InterfaceC2880qs {
    b("BYTE"),
    c("CHAR"),
    d("SHORT"),
    e("INT"),
    f("LONG"),
    g("FLOAT"),
    h("DOUBLE"),
    i("BOOLEAN"),
    j("STRING"),
    k("CLASS"),
    l("ENUM"),
    m("ANNOTATION"),
    n("ARRAY");

    private final int a;

    TN(String str) {
        this.a = i;
    }

    public static TN c(int i2) {
        switch (i2) {
            case 0:
                return b;
            case 1:
                return c;
            case 2:
                return d;
            case 3:
                return e;
            case 4:
                return f;
            case 5:
                return g;
            case 6:
                return h;
            case 7:
                return i;
            case 8:
                return j;
            case 9:
                return k;
            case 10:
                return l;
            case 11:
                return m;
            case 12:
                return n;
            default:
                return null;
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2880qs
    public final int a() {
        return this.a;
    }
}
