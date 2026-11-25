package com.android.tools.r8.origin;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class f extends Origin {
    private final String e;

    public f(String str, Class<?> cls) {
        super(Origin.root());
        this.e = str;
    }

    @Override // com.android.tools.r8.origin.Origin
    public final String part() {
        return "synthesized for " + this.e;
    }
}
