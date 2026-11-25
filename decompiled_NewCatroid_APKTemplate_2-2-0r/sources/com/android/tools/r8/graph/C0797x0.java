package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.AbstractC2178hm;
import java.util.Map;
import java.util.TreeSet;

/* JADX INFO: renamed from: com.android.tools.r8.graph.x0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0797x0 {
    static final /* synthetic */ boolean h = true;
    public final boolean a;
    public final int b;
    public final C0759u2 c;
    public final boolean d;
    public final boolean e;
    public final Map<Integer, X> f;
    private final AbstractC1915eM g;

    public C0797x0(boolean z, int i, C0759u2 c0759u2, boolean z2, boolean z3, AbstractC2178hm abstractC2178hm, AbstractC1915eM abstractC1915eM) {
        this.a = z;
        this.b = i;
        this.c = c0759u2;
        this.d = z2;
        this.e = z3;
        this.f = abstractC2178hm;
        this.g = abstractC1915eM;
        if (!h && abstractC1915eM == null) {
            throw new AssertionError();
        }
    }

    public final String a(boolean z) {
        StringBuilder sb = new StringBuilder();
        if (z) {
            sb.append("pc ");
        }
        sb.append(com.android.tools.r8.utils.Y2.a(this.b, 2));
        if (this.c != null) {
            sb.append(", file ").append(this.c);
        }
        sb.append(", ").append(this.g);
        if (this.d) {
            sb.append(", prologue_end = true");
        }
        if (this.e) {
            sb.append(", epilogue_begin = true");
        }
        if (!this.f.isEmpty()) {
            sb.append(", locals: [");
            boolean z2 = true;
            for (Integer num : new TreeSet(this.f.keySet())) {
                if (z2) {
                    z2 = false;
                } else {
                    sb.append(", ");
                }
                sb.append(num).append(" -> ").append(this.f.get(num));
            }
            sb.append("]");
        }
        return sb.toString();
    }

    public AbstractC1915eM b() {
        return this.g;
    }

    public final String toString() {
        return a(true);
    }

    public int a() {
        return this.g.e();
    }
}
