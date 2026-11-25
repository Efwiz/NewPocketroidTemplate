package com.android.tools.r8.graph;

import com.android.tools.r8.internal.B7;
import com.android.tools.r8.internal.M7;
import com.android.tools.r8.internal.MR;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C {
    static final /* synthetic */ boolean d = true;
    private final C0753u a;
    private final C0753u b;
    private com.android.tools.r8.utils.structural.u c = null;

    public C(C0753u c0753u, C0753u c0753u2) {
        this.a = c0753u;
        this.b = c0753u2;
    }

    public static void a(B7 b7, B7 b8) {
        boolean z = d;
        if (!z && b7.getClass() != b8.getClass()) {
            throw new AssertionError();
        }
        if (!z && b7.z() != b8.z()) {
            throw new AssertionError();
        }
        if (!z && !b7.toString().equals(b8.toString())) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static MR a(C0753u c0753u) {
        MR mr = new MR();
        for (B7 b7 : c0753u.I0()) {
            b7.getClass();
            if (b7 instanceof M7) {
                mr.a(mr.size(), b7.q());
            }
        }
        return mr;
    }

    public final com.android.tools.r8.utils.structural.u a() {
        if (this.c == null) {
            this.c = new C0805y(this);
        }
        return this.c;
    }
}
