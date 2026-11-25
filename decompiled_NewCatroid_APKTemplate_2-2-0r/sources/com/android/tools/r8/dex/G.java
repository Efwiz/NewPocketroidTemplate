package com.android.tools.r8.dex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.ToIntFunction;
import org.jf.dexlib2.dexbacked.raw.ItemType;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class G {
    static final /* synthetic */ boolean x = true;
    final int a;
    final int b;
    final int c;
    final int d;
    final int e;
    final int f;
    final int g;
    final int h;
    final int i;
    final int j;
    final int k;
    private int l;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;

    private G(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
        this.l = -1;
        this.m = -1;
        this.n = -1;
        this.o = -1;
        this.p = -1;
        this.q = -1;
        this.r = -1;
        this.s = -1;
        this.t = -1;
        this.u = -1;
        this.v = -1;
        this.w = -1;
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = i4;
        this.e = i5;
        this.f = i6;
        this.g = i7;
        this.h = i8;
        this.i = i9;
        this.j = i10;
        this.k = i11;
        boolean z = x;
        if (!z && i2 > i3) {
            throw new AssertionError();
        }
        if (!z && i3 > i4) {
            throw new AssertionError();
        }
        if (!z && i4 > i5) {
            throw new AssertionError();
        }
        if (!z && i5 > i6) {
            throw new AssertionError();
        }
        if (!z && i6 > i7) {
            throw new AssertionError();
        }
        if (!z && i7 > i10) {
            throw new AssertionError();
        }
        if (!z && i8 > i10) {
            throw new AssertionError();
        }
        if (!z && i9 > i10) {
            throw new AssertionError();
        }
    }

    public final void a(int i) {
    }

    public final void b(int i) {
        if (!x && this.s != -1) {
            throw new AssertionError();
        }
        this.s = i;
    }

    public final int c() {
        if (x || a(this.o, false)) {
            return this.o;
        }
        throw new AssertionError();
    }

    public final void d(int i) {
        if (!x && this.q != -1) {
            throw new AssertionError();
        }
        this.q = i;
    }

    public final void e(int i) {
        if (!x && this.p != -1) {
            throw new AssertionError();
        }
        this.p = i;
    }

    public final void f(int i) {
        if (!x && this.t != -1) {
            throw new AssertionError();
        }
        this.t = i;
    }

    public final void g(int i) {
        if (!x && this.u != -1) {
            throw new AssertionError();
        }
        this.u = i;
    }

    public final void h(int i) {
        if (!x && this.o != -1) {
            throw new AssertionError();
        }
        this.o = i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Header: ");
        sb.append(this.b - this.a).append("\nStringIds: ");
        sb.append(this.c - this.b).append("\ntypeIds: ");
        sb.append(this.d - this.c).append("\nprotoIds: ");
        sb.append(this.e - this.d).append("\nfieldIds: ");
        sb.append(this.f - this.e).append("\nmethodIds: ");
        sb.append(this.g - this.f).append("\nclassDefs: ");
        sb.append(this.h - this.g).append("\ncallSiteIds: ");
        sb.append(this.i - this.h).append("\nmethodHandleIds: ");
        sb.append(this.j - this.i).append("\ncode: ");
        sb.append(this.m - this.l).append("\ndebugInfo: ");
        sb.append(this.n - this.m).append("\ntypeList: ");
        int i = this.o;
        if (i <= 0) {
            i = this.p;
        }
        sb.append(i - this.n).append("\nstringData: ");
        int i2 = this.o;
        sb.append(i2 > 0 ? this.p - i2 : 0).append("\nannotations: ");
        sb.append(this.t - this.p).append("\nclassData: ");
        sb.append(this.u - this.t).append("\nencodedArrays: ");
        sb.append(this.v - this.r).append("\nannotationSets: ");
        sb.append(this.r - this.q).append("\nannotationSetRefLists: ");
        sb.append(this.s - this.r).append("\nannotationDirectories: ");
        sb.append(this.v - this.s).append("\nmap: ");
        sb.append(this.w - this.v).append("\nendOfFile: ");
        sb.append(this.w).append("\n");
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(int i, boolean z) {
        return i != -1 && (!z || i % 4 == 0);
    }

    public final ArrayList a(K k, int i, int i2, int i3, int i4) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new H(0, i, 1));
        arrayList.add(new H(1, i3, k.c.m().size()));
        arrayList.add(new H(2, this.c, k.c.n().size()));
        arrayList.add(new H(3, this.d, k.c.l().size()));
        arrayList.add(new H(4, this.e, k.c.e().size()));
        arrayList.add(new H(5, this.f, k.c.k().size()));
        arrayList.add(new H(6, this.g, k.c.c().length));
        arrayList.add(new H(7, this.h, k.c.b().size()));
        arrayList.add(new H(8, this.i, k.c.j().size()));
        boolean z = x;
        if (!z && !a(this.l, true)) {
            throw new AssertionError();
        }
        arrayList.add(new H(ItemType.CODE_ITEM, this.l, k.f.a.keySet().size()));
        if (!z && !a(this.m, false)) {
            throw new AssertionError();
        }
        arrayList.add(new H(ItemType.DEBUG_INFO_ITEM, this.m, k.f.b.keySet().size()));
        if (!z && !a(this.n, true)) {
            throw new AssertionError();
        }
        arrayList.add(new H(4097, this.n, k.f.c.keySet().size()));
        if (i4 == 0) {
            i2 = 0;
        }
        arrayList.add(new H(8194, i4, i2));
        if (!z && !a(this.p, false)) {
            throw new AssertionError();
        }
        arrayList.add(new H(8196, this.p, k.f.e.keySet().size()));
        if (!z && !a(this.t, false)) {
            throw new AssertionError();
        }
        arrayList.add(new H(8192, this.t, k.f.i.keySet().size()));
        if (!z && !a(this.u, false)) {
            throw new AssertionError();
        }
        arrayList.add(new H(8197, this.u, k.f.j.keySet().size()));
        if (!z && !a(this.q, true)) {
            throw new AssertionError();
        }
        arrayList.add(new H(4099, this.q, k.f.f.keySet().size()));
        if (!z && !a(this.r, true)) {
            throw new AssertionError();
        }
        arrayList.add(new H(4098, this.r, k.f.g.keySet().size()));
        if (!z && !a(this.s, true)) {
            throw new AssertionError();
        }
        arrayList.add(new H(ItemType.ANNOTATION_DIRECTORY_ITEM, this.s, k.f.h.keySet().size()));
        arrayList.add(new H(4096, this.v, 1));
        arrayList.sort(Comparator.comparingInt(new ToIntFunction() { // from class: com.android.tools.r8.dex.G$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((H) obj).a();
            }
        }));
        return arrayList;
    }

    public final int b() {
        return this.v;
    }

    public final void c(int i) {
        if (!x && this.r != -1) {
            throw new AssertionError();
        }
        this.r = i;
    }

    public final int a() {
        return this.w;
    }
}
