package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class V9 {
    private static final V9 e = new R9();
    static final /* synthetic */ boolean f = true;
    private final C0702m a;
    private final C2409kl b;
    private C0827Af c;
    private int d;

    private V9() {
        this.c = null;
        this.d = -1;
        this.a = null;
        this.b = null;
    }

    public static V9 c() {
        return e;
    }

    public boolean a(C0767v2 c0767v2, AbstractC1305Qs abstractC1305Qs) {
        S9 s9;
        AbstractC2412ko next;
        com.android.tools.r8.graph.o5 o5VarH = this.b.h();
        C3332x4 c3332x4H = abstractC1305Qs.h();
        Iterator<AbstractC2412ko> it = c3332x4H.p().iterator();
        while (it.hasNext() && (next = it.next()) != abstractC1305Qs) {
            if (next.a(c0767v2, o5VarH, this.a, U9.b, S9.a)) {
                return true;
            }
        }
        if (this.c == null) {
            this.c = new C0827Af(this.b, 2);
        }
        for (C3332x4 c3332x4 : this.c.a(c3332x4H)) {
            if (c3332x4.D() && c3332x4.l() != null) {
                if (this.d < 0) {
                    int iA = this.b.A();
                    this.d = iA;
                    this.b.a(iA, c3332x4H);
                }
                Iterator it2 = c3332x4.n().iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        s9 = S9.a;
                        break;
                    }
                    if (((C3332x4) ((C2969s6) it2.next()).b).c(this.d)) {
                        s9 = S9.b;
                        break;
                    }
                }
            } else {
                s9 = S9.a;
            }
            S9 s10 = s9;
            InterfaceC2488lo interfaceC2488loR = c3332x4.R();
            while (interfaceC2488loR.hasNext()) {
                AbstractC2412ko next2 = interfaceC2488loR.next();
                if (next2.a(c0767v2, o5VarH, this.a, U9.b, s10)) {
                    return true;
                }
                if (c3332x4.D() && next2.g()) {
                    if (f || StreamSupport.stream(Spliterators.spliteratorUnknownSize(interfaceC2488loR, 0), false).noneMatch(new A4$$ExternalSyntheticLambda5())) {
                        break;
                    }
                    throw new AssertionError();
                }
            }
        }
        return false;
    }

    public final void b() {
        this.c = null;
        int i = this.d;
        if (i >= 0) {
            this.b.b(i);
            this.d = -1;
        }
    }

    public V9(C0702m c0702m, C2409kl c2409kl) {
        this.c = null;
        this.d = -1;
        this.a = c0702m;
        this.b = c2409kl;
    }

    public final void a() {
        int i = this.d;
        if (i >= 0) {
            this.b.b(i);
            this.d = -1;
        }
    }
}
