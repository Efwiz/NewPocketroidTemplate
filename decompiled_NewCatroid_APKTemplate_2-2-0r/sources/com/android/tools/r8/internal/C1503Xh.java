package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Xh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1503Xh {
    static final /* synthetic */ boolean b = true;
    private final C0702m a;

    C1503Xh(C0702m c0702m) {
        this.a = c0702m;
    }

    public final void a(AbstractC1182Mh abstractC1182Mh, C2133h6.a aVar, com.android.tools.r8.graph.o5 o5Var) {
        final Set setC;
        if (abstractC1182Mh instanceof QW) {
            S10 s10B = abstractC1182Mh.F0().b();
            com.android.tools.r8.utils.O3 o3E = com.android.tools.r8.utils.O3.e();
            if (!s10B.z() && !s10B.C()) {
                o3E.a((Iterable) s10B.h0());
                setC = OU.c();
                loop0: while (o3E.c()) {
                    AbstractC2412ko abstractC2412ko = (AbstractC2412ko) o3E.f();
                    abstractC2412ko.getClass();
                    if (!(abstractC2412ko instanceof N3)) {
                        if (abstractC2412ko.Z1()) {
                            AbstractC1332Rs abstractC1332RsC0 = abstractC2412ko.c0();
                            int i = 1;
                            while (true) {
                                if (i < ((ArrayList) abstractC1332RsC0.J2()).size()) {
                                    if (abstractC1332RsC0.c(i).m() != s10B) {
                                        i++;
                                    }
                                } else {
                                    if (!b && abstractC1332RsC0.U2().m() != s10B) {
                                        throw new AssertionError();
                                    }
                                    com.android.tools.r8.graph.o5 o5VarA = AbstractC0748t0.a(abstractC1332RsC0.h(this.a, o5Var));
                                    if (o5VarA != null) {
                                        setC.add(o5VarA.getReference());
                                    }
                                }
                            }
                        }
                        setC = null;
                        break;
                    }
                    S10 s10B2 = abstractC2412ko.b();
                    if (s10B2.z() || s10B2.C()) {
                        setC = null;
                        break;
                    }
                    o3E.a((Iterable) s10B2.h0());
                }
            } else {
                setC = null;
                break;
            }
            if (setC == null || setC.isEmpty()) {
                return;
            }
            aVar.a(abstractC1182Mh, new Consumer() { // from class: com.android.tools.r8.internal.Xh$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((C1817d6) obj).a(setC);
                }
            });
        }
    }
}
