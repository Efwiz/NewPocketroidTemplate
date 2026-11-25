package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0653f2;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0660g2;
import com.android.tools.r8.graph.C0667h2;

/* JADX INFO: renamed from: com.android.tools.r8.dex.y, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0587y {
    private static final C0667h2[] e;
    private static final C0653f2[] f;
    private static final C0660g2[] g = new C0660g2[0];
    private static final C0587y h;
    public final C0658g0 a;
    public final C0653f2[] b;
    public final C0660g2[] c;
    public final C0667h2[] d;

    static {
        C0667h2[] c0667h2Arr = new C0667h2[0];
        e = c0667h2Arr;
        C0653f2[] c0653f2Arr = new C0653f2[0];
        f = c0653f2Arr;
        h = new C0587y(C0658g0.t0(), c0653f2Arr, new C0660g2[0], c0667h2Arr);
    }

    C0587y(C0658g0 c0658g0, C0653f2[] c0653f2Arr, C0660g2[] c0660g2Arr, C0667h2[] c0667h2Arr) {
        this.a = c0658g0 == null ? C0658g0.t0() : c0658g0;
        this.b = c0653f2Arr == null ? f : c0653f2Arr;
        this.c = c0660g2Arr == null ? g : c0660g2Arr;
        this.d = c0667h2Arr == null ? e : c0667h2Arr;
    }

    public static C0587y a() {
        return h;
    }
}
