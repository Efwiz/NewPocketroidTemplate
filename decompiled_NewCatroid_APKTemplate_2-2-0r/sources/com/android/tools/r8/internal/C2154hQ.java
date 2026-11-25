package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import org.eclipse.jgit.lib.FileMode;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hQ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2154hQ {
    private final C0722p1 a;

    C2154hQ(C0722p1 c0722p1) {
        this.a = c0722p1;
    }

    static ArrayList b(EP ep) {
        final ArrayList arrayList = new ArrayList();
        if (ep.k() > 0) {
            Iterator it = ep.e().iterator();
            while (it.hasNext()) {
                ((PP) it.next()).a(new Consumer() { // from class: com.android.tools.r8.internal.hQ$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        arrayList.add((AbstractC3433yP) obj);
                    }
                });
            }
        }
        if (ep.j() > 0) {
            arrayList.addAll(ep.d());
        }
        if (ep.g()) {
            Iterator it2 = ep.b().iterator();
            while (it2.hasNext()) {
                arrayList.addAll(((C3355xP) it2.next()).c());
            }
        }
        return arrayList;
    }

    final C0759u2 a(EP ep) {
        int i;
        C2732or c2732or = new C2732or(16);
        c2732or.a(ep.c());
        c2732or.a(ep.i());
        if (ep.g()) {
            int i2 = Integer.MAX_VALUE;
            int i3 = Integer.MIN_VALUE;
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            for (C3355xP c3355xP : ep.b()) {
                int iB = c3355xP.b();
                if (iB < i2) {
                    i2 = iB;
                }
                if (iB > i3) {
                    i3 = iB;
                }
                C3503zP c3503zPD = c3355xP.d();
                if (c3503zPD.b() == 50) {
                    i6++;
                } else if (!c3503zPD.k()) {
                    i5++;
                }
                if (c3503zPD.m()) {
                    i4++;
                }
            }
            c2732or.a(ep.k());
            c2732or.a(ep.j());
            c2732or.a(i2);
            c2732or.a(i3);
            c2732or.a(ep.i());
            c2732or.a(i6);
            c2732or.a(i5);
            c2732or.a(i4);
            for (C3355xP c3355xP2 : ep.b()) {
                c2732or.a(c3355xP2.b());
                c2732or.a(c3355xP2.d().n());
                if (c3355xP2.e()) {
                    c2732or.a(c3355xP2.a());
                }
            }
        }
        InterfaceC0993Fr interfaceC0993FrListIterator = c2732or.listIterator(0);
        int i7 = 1;
        while (interfaceC0993FrListIterator.hasNext()) {
            int iT = interfaceC0993FrListIterator.t();
            while (true) {
                i = 2;
                if (iT < 55296) {
                    break;
                }
                char c = (char) ((iT & 8191) | FileMode.TYPE_GITLINK);
                iT >>= 13;
                C0759u2[] c0759u2Arr = C0759u2.f;
                if (c != 0 && c <= 127) {
                    i = 1;
                } else if (c > 2047) {
                    i = 3;
                }
                i7 += i;
            }
            char c2 = (char) iT;
            C0759u2[] c0759u2Arr2 = C0759u2.f;
            if (c2 != 0 && c2 <= 127) {
                i = 1;
            } else if (c2 > 2047) {
                i = 3;
            }
            i7 += i;
        }
        byte[] bArr = new byte[i7];
        InterfaceC0993Fr interfaceC0993FrListIterator2 = c2732or.listIterator(0);
        int iA = 0;
        int i8 = 0;
        while (interfaceC0993FrListIterator2.hasNext()) {
            int iT2 = interfaceC0993FrListIterator2.t();
            while (iT2 >= 55296) {
                iA = C0759u2.a((char) ((iT2 & 8191) | FileMode.TYPE_GITLINK), bArr, iA);
                i8++;
                iT2 >>= 13;
            }
            iA = C0759u2.a((char) iT2, bArr, iA);
        }
        bArr[iA] = 0;
        return this.a.b(c2732or.size() + i8, bArr);
    }
}
