package com.android.tools.r8.origin;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class b extends Origin {
    public static final b e = new b();

    private b() {
        super(Origin.root());
    }

    @Override // com.android.tools.r8.origin.Origin
    public final String part() {
        return "R8";
    }
}
