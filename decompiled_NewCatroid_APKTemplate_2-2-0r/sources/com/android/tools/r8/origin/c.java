package com.android.tools.r8.origin;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class c extends Origin {
    private static final c e = new c(Origin.root());

    protected c(Origin origin) {
        super(origin);
    }

    public static Origin a() {
        return e;
    }

    @Override // com.android.tools.r8.origin.Origin
    public final String part() {
        return "<synthetic>";
    }
}
