package com.android.tools.r8.androidapi;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.internal.AbstractC1660b2;
import com.android.tools.r8.internal.C1589a6;
import com.android.tools.r8.internal.InterfaceC1811d2;
import com.android.tools.r8.internal.Z1;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.EnumC3891f;
import com.android.tools.r8.utils.InterfaceC3910i3;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class e implements InterfaceC1811d2 {
    private static final byte[] e = new byte[0];
    private static volatile AbstractC1660b2 f = null;
    static final /* synthetic */ boolean g = true;
    private final ConcurrentHashMap a = new ConcurrentHashMap();
    private final ConcurrentHashMap b = new ConcurrentHashMap();
    private final A1 c;
    private final DiagnosticsHandler d;

    public e(List<a> list, A1 a1, DiagnosticsHandler diagnosticsHandler) {
        this.c = a1;
        this.d = diagnosticsHandler;
        list.forEach(new Consumer() { // from class: com.android.tools.r8.androidapi.e$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((a) obj);
            }
        });
        if (!g && !list.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.androidapi.e$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.b((a) obj);
            }
        })) {
            throw new AssertionError();
        }
    }

    public static byte[] a() {
        return e;
    }

    private static byte b(int i) {
        if (g || C1589a6.a(i)) {
            return (byte) i;
        }
        throw new AssertionError();
    }

    public static byte[] a(AbstractC0744s2 abstractC0744s2, InterfaceC3910i3<C0759u2, Integer, IOException> interfaceC3910i3) throws IOException {
        abstractC0744s2.getClass();
        if (abstractC0744s2 instanceof C0767v2) {
            int iIntValue = interfaceC3910i3.apply(abstractC0744s2.w0().D0()).intValue();
            return iIntValue < 0 ? e : new byte[]{0, a(iIntValue), b(iIntValue)};
        }
        int iIntValue2 = interfaceC3910i3.apply(abstractC0744s2.u0().B0().D0()).intValue();
        if (iIntValue2 < 0) {
            return e;
        }
        int iIntValue3 = interfaceC3910i3.apply(abstractC0744s2.u0().C0()).intValue();
        if (iIntValue3 < 0) {
            return e;
        }
        if (abstractC0744s2.x0()) {
            int iIntValue4 = interfaceC3910i3.apply(abstractC0744s2.t0().getType().D0()).intValue();
            return (iIntValue2 < 0 || iIntValue3 < 0 || iIntValue4 < 0) ? e : new byte[]{1, a(iIntValue2), b(iIntValue2), a(iIntValue3), b(iIntValue3), a(iIntValue4), b(iIntValue4)};
        }
        if (!g && !abstractC0744s2.y0()) {
            throw new AssertionError();
        }
        C0684j2 c0684j2V0 = abstractC0744s2.v0();
        if (iIntValue2 < 0 || iIntValue3 < 0) {
            return e;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(2);
        byteArrayOutputStream.write(a(iIntValue2));
        byteArrayOutputStream.write(b(iIntValue2));
        byteArrayOutputStream.write(a(iIntValue3));
        byteArrayOutputStream.write(b(iIntValue3));
        Iterator<C0767v2> it = c0684j2V0.h.f.iterator();
        while (it.hasNext()) {
            int iIntValue5 = interfaceC3910i3.apply(it.next().D0()).intValue();
            if (iIntValue5 < 0) {
                return e;
            }
            byteArrayOutputStream.write(a(iIntValue5));
            byteArrayOutputStream.write(b(iIntValue5));
        }
        int iIntValue6 = interfaceC3910i3.apply(c0684j2V0.I0().D0()).intValue();
        if (iIntValue6 < 0) {
            return e;
        }
        byteArrayOutputStream.write(a(iIntValue6));
        byteArrayOutputStream.write(b(iIntValue6));
        return byteArrayOutputStream.toByteArray();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean b(a aVar) {
        return aVar.a().isEqualTo(b(aVar.b()));
    }

    private EnumC3891f b(AbstractC0744s2 abstractC0744s2) {
        EnumC3891f enumC3891f = (EnumC3891f) this.a.computeIfAbsent(abstractC0744s2, new Function() { // from class: com.android.tools.r8.androidapi.e$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((AbstractC0744s2) obj);
            }
        });
        if (enumC3891f == EnumC3891f.ANDROID_PLATFORM) {
            return null;
        }
        return enumC3891f;
    }

    private static byte a(int i) {
        if (g || C1589a6.a(i)) {
            return (byte) (i >> 8);
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(a aVar) {
        this.a.put(aVar.b(), aVar.a());
    }

    @Override // com.android.tools.r8.internal.InterfaceC1811d2
    public EnumC3891f a(C0767v2 c0767v2) {
        return b(c0767v2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1811d2
    public EnumC3891f a(C0684j2 c0684j2) {
        return b(c0684j2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1811d2
    public EnumC3891f a(Y0 y0) {
        return b(y0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public EnumC3891f a(AbstractC0744s2 abstractC0744s2) {
        byte[] bArrA;
        if (f == null) {
            A1 a1 = this.c;
            DiagnosticsHandler diagnosticsHandler = this.d;
            if (f == null) {
                synchronized (AbstractC1660b2.class) {
                    if (f == null) {
                        f = AbstractC1660b2.a(a1, diagnosticsHandler);
                    }
                }
            }
        }
        AbstractC1660b2 abstractC1660b2 = f;
        abstractC1660b2.getClass();
        if (abstractC1660b2 instanceof Z1) {
            return EnumC3891f.ANDROID_PLATFORM;
        }
        try {
            bArrA = a(abstractC0744s2, (InterfaceC3910i3<C0759u2, Integer, IOException>) new InterfaceC3910i3() { // from class: com.android.tools.r8.androidapi.e$$ExternalSyntheticLambda2
                @Override // com.android.tools.r8.utils.InterfaceC3910i3
                public final Object apply(Object obj) {
                    return this.f$0.a((C0759u2) obj);
                }
            });
        } catch (Exception unused) {
            bArrA = a();
        }
        if (bArrA == a()) {
            return EnumC3891f.ANDROID_PLATFORM;
        }
        byte bA = f.a(bArrA, abstractC0744s2);
        if (bA <= 0) {
            return EnumC3891f.ANDROID_PLATFORM;
        }
        return EnumC3891f.c(bA);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer a(final C0759u2 c0759u2) {
        return (Integer) this.b.computeIfAbsent(c0759u2, new Function() { // from class: com.android.tools.r8.androidapi.e$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return e.a(c0759u2, (C0759u2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer a(C0759u2 c0759u2, C0759u2 c0759u3) {
        return Integer.valueOf(f.b(c0759u2));
    }
}
