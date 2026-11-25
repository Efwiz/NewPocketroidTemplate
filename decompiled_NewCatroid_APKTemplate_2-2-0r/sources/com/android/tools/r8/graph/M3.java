package com.android.tools.r8.graph;

import com.android.tools.r8.internal.C2044g20;
import com.android.tools.r8.internal.CZ;
import com.android.tools.r8.utils.C3970u0;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class M3 {
    static final /* synthetic */ boolean g = true;
    public final com.android.tools.r8.utils.A1 a;
    private final ConcurrentHashMap b;
    private final ConcurrentHashMap c;
    private final ConcurrentHashMap d;
    private final AbstractC0720p e;
    private final C0689k0 f;

    public M3(com.android.tools.r8.utils.A1 a1, C0689k0 c0689k0) {
        this.b = new ConcurrentHashMap();
        this.c = new ConcurrentHashMap();
        this.d = new ConcurrentHashMap();
        this.a = a1;
        this.f = c0689k0;
        this.e = AbstractC0720p.a(a1);
    }

    public final CZ a(String str) {
        return (CZ) this.b.computeIfAbsent(str, new Function() { // from class: com.android.tools.r8.graph.M3$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CZ.d((String) obj);
            }
        });
    }

    public final CZ b(String str) {
        return (CZ) this.c.computeIfAbsent(str, new Function() { // from class: com.android.tools.r8.graph.M3$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CZ.f((String) obj);
            }
        });
    }

    public final C0737r2 c(String str) {
        String str2;
        C0800x2 c0800x2;
        boolean z = g;
        if (!z && !b(str).b().equals(str)) {
            throw new AssertionError();
        }
        String strU = C3970u0.u(str);
        String[] strArrE = C3970u0.e(str);
        StringBuilder sb = new StringBuilder();
        if (strU.length() == 1) {
            str2 = strU;
        } else {
            if (!z && strU.charAt(0) != 'L' && strU.charAt(0) != '[') {
                throw new AssertionError();
            }
            str2 = "L";
        }
        sb.append(str2);
        for (String str3 : strArrE) {
            if (str3.length() != 1) {
                if (!g && str3.charAt(0) != 'L' && str3.charAt(0) != '[') {
                    throw new AssertionError();
                }
                str3 = "L";
            }
            sb.append(str3);
        }
        C0722p1 c0722p1 = this.a.a;
        C0767v2 c0767v2E = e(strU);
        if (strArrE.length == 0) {
            c0800x2 = C0800x2.s0();
        } else {
            C0767v2[] c0767v2Arr = new C0767v2[strArrE.length];
            for (int i = 0; i < strArrE.length; i++) {
                c0767v2Arr[i] = e(strArrE[i]);
            }
            c0800x2 = new C0800x2(c0767v2Arr);
        }
        return c0722p1.a(d(sb.toString()), c0767v2E, c0800x2);
    }

    public final C0759u2 d(String str) {
        ConcurrentHashMap concurrentHashMap = this.d;
        final C0722p1 c0722p1 = this.a.a;
        Objects.requireNonNull(c0722p1);
        return (C0759u2) concurrentHashMap.computeIfAbsent(str, new Function() { // from class: com.android.tools.r8.graph.M3$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return c0722p1.c((String) obj);
            }
        });
    }

    public final C0767v2 e(String str) {
        if (!g && !b(str).b().equals(str)) {
            throw new AssertionError();
        }
        return this.a.a.c(d(this.e.a(str)));
    }

    public final C0767v2 f(String str) {
        if (g || a(str).c().equals(str)) {
            return e(a(str).b());
        }
        throw new AssertionError();
    }

    public final C0722p1 a() {
        return this.a.a;
    }

    public final C0684j2 b(C0767v2 c0767v2, String str, String str2) {
        return this.a.a.a(c0767v2, c(str2), d(str));
    }

    public final C0800x2 a(String[] strArr) {
        if (strArr.length == 0) {
            return C0800x2.s0();
        }
        C0767v2[] c0767v2Arr = new C0767v2[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            c0767v2Arr[i] = f(strArr[i]);
        }
        return new C0800x2(c0767v2Arr);
    }

    public final void b(C0767v2 c0767v2, I i) {
        if (i == I.c) {
            this.f.b(c0767v2);
        }
    }

    public final Y0 a(C0767v2 c0767v2, String str, String str2) {
        return this.a.a.a(c0767v2, e(str2), d(str));
    }

    public final C0721p0 a(String str, String str2, C0699l2 c0699l2, ArrayList arrayList) {
        C0722p1 c0722p1 = this.a.a;
        C0759u2 c0759u2D = d(str);
        C0737r2 c0737r2C = c(str2);
        c0722p1.getClass();
        return new C0721p0(c0759u2D, c0737r2C, c0699l2, arrayList);
    }

    public M3(com.android.tools.r8.utils.A1 a1) {
        this(a1, C0696l0.a());
    }

    public final void a(C0767v2 c0767v2, I i) {
        if (i == I.c) {
            this.f.a(c0767v2);
        }
    }

    public final void a(final AbstractC0727q0 abstractC0727q0, final I i) {
        if (this.a.D1()) {
            if (C2044g20.a(this.a.a, abstractC0727q0.getType())) {
                c(abstractC0727q0.getType(), i);
            }
            abstractC0727q0.e1().forEach(new Consumer() { // from class: com.android.tools.r8.graph.M3$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(abstractC0727q0, i, (J3) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC0727q0 abstractC0727q0, I i, J3 j3) {
        boolean z = g;
        if (!z) {
            if (C2044g20.a(this.a.a, j3.d())) {
                throw new AssertionError();
            }
        }
        if (C2044g20.a(this.a.a, j3.b())) {
            a(abstractC0727q0.getType(), i);
            if (!z && j3.d() != this.a.a.D2) {
                throw new AssertionError();
            }
            c(abstractC0727q0.getType(), i);
        }
    }

    public final void c(C0767v2 c0767v2, I i) {
        if (i == I.c) {
            this.f.c(c0767v2);
        }
    }
}
