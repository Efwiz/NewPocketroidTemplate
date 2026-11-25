package com.android.tools.r8.internal;

import java.util.Arrays;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3394xu extends S4 {
    public static final C3394xu g = new C3394xu(1, 8, 0);
    private final boolean f;

    static {
        new C3394xu(new int[0]);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C3394xu(int[] iArr, boolean z) {
        super(Arrays.copyOf(iArr, iArr.length));
        AbstractC0936Ds.c(iArr, "versionArray");
        this.f = z;
    }

    public final boolean d() {
        boolean zA;
        if (a() == 1 && b() == 0) {
            return false;
        }
        if (this.f) {
            zA = a(g);
        } else {
            int iA = a();
            C3394xu c3394xu = g;
            zA = iA == c3394xu.a() && b() <= c3394xu.b() + 1;
        }
        return zA;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public C3394xu(int... iArr) {
        this(iArr, false);
        AbstractC0936Ds.c(iArr, "numbers");
    }
}
