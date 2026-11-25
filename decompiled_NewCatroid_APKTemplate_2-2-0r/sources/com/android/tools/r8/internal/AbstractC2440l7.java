package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.l7, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2440l7 {
    static final /* synthetic */ boolean a = true;

    public static void a(int i, InterfaceC1348Si interfaceC1348Si, C2413kp c2413kp) {
        boolean z = a;
        if (!z && interfaceC1348Si.k()) {
            throw new AssertionError();
        }
        InterfaceC1348Si interfaceC1348Si2 = (InterfaceC1348Si) c2413kp.a(i, interfaceC1348Si);
        if (interfaceC1348Si2 != null && interfaceC1348Si2.n()) {
            InterfaceC1348Si interfaceC1348Si3 = (InterfaceC1348Si) c2413kp.a(i - 1, InterfaceC1348Si.B());
            if (!z && interfaceC1348Si3 != interfaceC1348Si2.p().U()) {
                throw new AssertionError();
            }
        }
        if (interfaceC1348Si.I()) {
            if (!z && !interfaceC1348Si.w()) {
                throw new AssertionError();
            }
            interfaceC1348Si2 = (InterfaceC1348Si) c2413kp.a(i + 1, interfaceC1348Si.p().T());
        }
        if (interfaceC1348Si2 == null || !interfaceC1348Si2.w()) {
            return;
        }
        InterfaceC1348Si interfaceC1348Si4 = (InterfaceC1348Si) c2413kp.a(interfaceC1348Si.t() + i, InterfaceC1348Si.B());
        if (!z && interfaceC1348Si4 != interfaceC1348Si2.p().T()) {
            throw new AssertionError();
        }
    }

    public static void a(InterfaceC1456Vp interfaceC1456Vp) {
        YJ it = interfaceC1456Vp.b().iterator();
        while (it.hasNext()) {
            InterfaceC2806pp interfaceC2806pp = (InterfaceC2806pp) it.next();
            int iA = interfaceC2806pp.a();
            InterfaceC1348Si interfaceC1348Si = (InterfaceC1348Si) interfaceC2806pp.getValue();
            if (interfaceC1348Si.w()) {
                if (!a && interfaceC1456Vp.get(iA + 1) != interfaceC1348Si.p().T()) {
                    throw new AssertionError();
                }
            } else if (interfaceC1348Si.n()) {
                if (!a && interfaceC1456Vp.get(iA - 1) != interfaceC1348Si.p().U()) {
                    throw new AssertionError();
                }
            } else if (!a && interfaceC1348Si.k()) {
                throw new AssertionError();
            }
        }
    }
}
