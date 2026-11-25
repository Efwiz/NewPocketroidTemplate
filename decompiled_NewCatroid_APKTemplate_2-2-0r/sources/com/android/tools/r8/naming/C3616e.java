package com.android.tools.r8.naming;

/* JADX INFO: renamed from: com.android.tools.r8.naming.e, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3616e implements I {
    static final /* synthetic */ boolean f = true;
    private final String a;
    private final char[] b;
    private int c;
    private int d;
    final /* synthetic */ C3618f e;

    C3616e(C3618f c3618f, String str) {
        this(c3618f, str, String.valueOf('/'));
    }

    @Override // com.android.tools.r8.naming.I
    public final int a() {
        int i = this.d;
        this.d = i + 1;
        return i;
    }

    @Override // com.android.tools.r8.naming.I
    public final int b() {
        return this.c;
    }

    @Override // com.android.tools.r8.naming.I
    public final int c() {
        int i = this.c;
        this.c = i + 1;
        return i;
    }

    C3616e(C3618f c3618f, String str, String str2) {
        this.e = c3618f;
        this.c = 0;
        this.d = 1;
        this.a = str;
        this.b = ("L" + str + (str.isEmpty() ? "" : str2)).toCharArray();
    }
}
