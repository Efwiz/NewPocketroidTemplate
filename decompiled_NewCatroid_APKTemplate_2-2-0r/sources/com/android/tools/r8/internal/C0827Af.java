package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Af, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0827Af implements InterfaceC3105u4 {
    static final /* synthetic */ boolean f = true;
    private final C3332x4[] a;
    private C3332x4[] b;
    private final int d;
    private final C3332x4 c = new C3332x4();
    private boolean e = false;

    public C0827Af(C2409kl c2409kl, int i) {
        int i2 = 0;
        boolean z = f;
        if (!z && i == 0) {
            throw new AssertionError();
        }
        if (!z && i != 2 && !c2409kl.p().isEmpty()) {
            throw new AssertionError();
        }
        AbstractC1639am abstractC1639amD = c2409kl.D();
        AbstractC2963s10 it = abstractC1639amD.iterator();
        while (it.hasNext()) {
            C3332x4 c3332x4 = (C3332x4) it.next();
            if (c3332x4.m().u2()) {
                this.c.r().add(c3332x4);
            }
        }
        int size = c2409kl.d.size();
        if (i == 2) {
            this.a = new C3332x4[size + 1];
            int iA = c2409kl.A();
            AbstractC2963s10 it2 = abstractC1639amD.iterator();
            while (it2.hasNext()) {
                C3332x4 c3332x5 = (C3332x4) it2.next();
                this.a[i2] = c3332x5;
                c3332x5.e(iA);
                i2++;
            }
            this.a[i2] = this.c;
            int i3 = i2 + 1;
            this.d = i3;
            for (C3332x4 c3332x6 : c2409kl.d) {
                if (!c3332x6.c(iA)) {
                    this.a[i3] = c3332x6;
                    i3++;
                }
            }
            c2409kl.b(iA);
        } else {
            int i4 = size + 1;
            C3332x4[] c3332x4Arr = (C3332x4[]) abstractC1639amD.toArray(new C3332x4[i4]);
            this.a = c3332x4Arr;
            c3332x4Arr[size] = this.c;
            this.d = i4;
        }
        d();
        a();
        if (f) {
            return;
        }
        a(c2409kl.d);
    }

    private void d() {
        int i = 0;
        while (true) {
            C3332x4[] c3332x4Arr = this.a;
            if (i >= c3332x4Arr.length) {
                return;
            }
            c3332x4Arr[i].g(i);
            i++;
        }
    }

    public final Iterable a(final C3332x4 c3332x4) {
        final EnumC3519zf enumC3519zf = EnumC3519zf.a;
        if (f || !this.e) {
            return new Iterable() { // from class: com.android.tools.r8.internal.Af$$ExternalSyntheticLambda0
                @Override // java.lang.Iterable
                public final Iterator iterator() {
                    return this.f$0.a(c3332x4, enumC3519zf);
                }
            };
        }
        throw new AssertionError();
    }

    public final C3332x4[] b() {
        return this.a;
    }

    public final Iterable c() {
        if (f || !this.e) {
            return a(this.c);
        }
        throw new AssertionError();
    }

    public final void e() {
        this.e = true;
    }

    public final void f() {
        this.e = true;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Dominators\n");
        for (C3332x4 c3332x4 : this.a) {
            sb.append(c3332x4.v());
            sb.append(": ");
            sb.append(this.b[c3332x4.v()].v());
            sb.append("\n");
        }
        return sb.toString();
    }

    public final boolean a(C3332x4 c3332x4, C3332x4 c3332x5) {
        boolean z = f;
        if (!z && this.e) {
            throw new AssertionError();
        }
        if (c3332x4 == c3332x5) {
            return true;
        }
        if (!z && this.e) {
            throw new AssertionError();
        }
        if (c3332x4.v() != 0 && c3332x4 != this.c) {
            do {
                if (!f && this.e) {
                    throw new AssertionError();
                }
                c3332x4 = this.b[c3332x4.v()];
                if (c3332x4.v() < c3332x5.v()) {
                }
            } while (c3332x4 != c3332x5);
            return true;
        }
        return false;
    }

    public final C3332x4 a(LinkedHashSet linkedHashSet) {
        if (!f && this.e) {
            throw new AssertionError();
        }
        if (linkedHashSet.size() == 0) {
            return null;
        }
        Iterator it = linkedHashSet.iterator();
        C3332x4 c3332x4 = (C3332x4) it.next();
        while (it.hasNext()) {
            C3332x4 c3332x5 = (C3332x4) it.next();
            while (c3332x4 != c3332x5) {
                while (c3332x4.v() > c3332x5.v()) {
                    c3332x4 = this.b[c3332x4.v()];
                }
                while (c3332x5.v() > c3332x4.v()) {
                    c3332x5 = this.b[c3332x5.v()];
                }
            }
        }
        return c3332x4;
    }

    public final Collection a(C3332x4 c3332x4, Collection collection) {
        if (!f && this.e) {
            throw new AssertionError();
        }
        for (int iV = c3332x4.v(); iV < this.d; iV++) {
            C3332x4 c3332x5 = this.a[iV];
            if (a(c3332x5, c3332x4)) {
                collection.add(c3332x5);
            }
        }
        return collection;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Iterator a(C3332x4 c3332x4, EnumC3519zf enumC3519zf) {
        C3449yf c3449yf = new C3449yf(this, c3332x4);
        if (enumC3519zf == EnumC3519zf.a) {
            C3332x4 c3332x5 = (C3332x4) c3449yf.next();
            if (!f && c3332x5 != c3332x4) {
                throw new AssertionError();
            }
        }
        return c3449yf;
    }

    private void a() {
        C3332x4[] c3332x4Arr = this.a;
        C3332x4[] c3332x4Arr2 = new C3332x4[c3332x4Arr.length];
        this.b = c3332x4Arr2;
        c3332x4Arr2[0] = c3332x4Arr[0];
        boolean z = true;
        while (z) {
            int i = 1;
            z = false;
            while (true) {
                C3332x4[] c3332x4Arr3 = this.a;
                if (i < c3332x4Arr3.length) {
                    C3332x4 c3332x4 = c3332x4Arr3[i];
                    C3332x4 c3332x5 = null;
                    int i2 = -1;
                    for (int i3 = 0; c3332x5 == null && i3 < c3332x4.y().size(); i3++) {
                        C3332x4 c3332x6 = c3332x4.y().get(i3);
                        if (this.b[c3332x6.v()] != null) {
                            i2 = i3;
                            c3332x5 = c3332x6;
                        }
                    }
                    for (int i4 = 0; i4 < c3332x4.y().size(); i4++) {
                        C3332x4 c3332x7 = c3332x4.y().get(i4);
                        if (i4 != i2 && this.b[c3332x7.v()] != null) {
                            while (c3332x7 != c3332x5) {
                                while (true) {
                                    if (!(c3332x7.v() > c3332x5.v())) {
                                        break;
                                    } else {
                                        c3332x7 = this.b[c3332x7.v()];
                                    }
                                }
                                while (true) {
                                    if (c3332x5.v() > c3332x7.v()) {
                                        c3332x5 = this.b[c3332x5.v()];
                                    }
                                }
                            }
                            c3332x5 = c3332x7;
                        }
                    }
                    if (this.b[c3332x4.v()] != c3332x5) {
                        this.b[c3332x4.v()] = c3332x5;
                        z = true;
                    }
                    i++;
                }
            }
        }
    }

    private void a(LinkedList linkedList) {
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            ((C3332x4) it.next()).a(this);
        }
    }
}
