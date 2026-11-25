package com.android.tools.r8.internal;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fV, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2005fV {
    private final C1941eh[] a = new C1941eh[5];
    private final C1941eh[] b = new C1941eh[5];
    private final VH[] c = new VH[5];
    private final VH[] d = new VH[5];
    private final ConcurrentHashMap e = new ConcurrentHashMap();
    private final ConcurrentHashMap f = new ConcurrentHashMap();
    private final ConcurrentHashMap g = new ConcurrentHashMap();
    private final ConcurrentHashMap h = new ConcurrentHashMap();

    public C2005fV() {
        int i = 0;
        int i2 = 0;
        while (true) {
            C1941eh[] c1941ehArr = this.a;
            if (i2 >= c1941ehArr.length) {
                break;
            }
            c1941ehArr[i2] = C1941eh.a(i2, false, this);
            i2++;
        }
        int i3 = 0;
        while (true) {
            C1941eh[] c1941ehArr2 = this.b;
            if (i3 >= c1941ehArr2.length) {
                break;
            }
            c1941ehArr2[i3] = C1941eh.a(i3, true, this);
            i3++;
        }
        int i4 = 0;
        while (true) {
            VH[] vhArr = this.c;
            if (i4 >= vhArr.length) {
                break;
            }
            vhArr[i4] = VH.a(i4, WH.b(), this);
            i4++;
        }
        while (true) {
            VH[] vhArr2 = this.d;
            if (i >= vhArr2.length) {
                return;
            }
            vhArr2[i] = VH.a(i, WH.c(), this);
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C1941eh b(int i, boolean z) {
        return C1941eh.a(i, z, this);
    }

    public final C1941eh a(final int i, final boolean z) {
        return (C1941eh) a(i, z ? this.b : this.a, z ? this.f : this.e, new Supplier() { // from class: com.android.tools.r8.internal.fV$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.b(i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ VH b(int i, WH wh) {
        return VH.a(i, wh, this);
    }

    public final VH a(final int i, final WH wh) {
        return (VH) a(i, wh.f() ? this.d : this.c, wh.f() ? this.h : this.g, new Supplier() { // from class: com.android.tools.r8.internal.fV$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.b(i, wh);
            }
        });
    }

    private static ZU a(int i, ZU[] zuArr, ConcurrentHashMap concurrentHashMap, final Supplier supplier) {
        if (i < zuArr.length) {
            return zuArr[i];
        }
        return (ZU) concurrentHashMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: com.android.tools.r8.internal.fV$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2005fV.a(supplier, (Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ZU a(Supplier supplier, Integer num) {
        return (ZU) supplier.get();
    }
}
