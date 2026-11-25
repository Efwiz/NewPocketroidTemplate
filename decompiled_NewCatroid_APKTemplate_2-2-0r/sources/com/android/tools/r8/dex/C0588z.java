package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0670h5;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0706m3;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.J3;
import com.android.tools.r8.graph.O2;
import com.android.tools.r8.graph.W4;
import com.android.tools.r8.graph.X4;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3979v2;
import com.android.tools.r8.utils.V1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.dex.z, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0588z {
    static final /* synthetic */ boolean j = true;
    private final C0658g0 a;
    private O2 b;
    private ArrayList c;
    private ArrayList d = null;
    private C0706m3.b e;
    private W4 f;
    private List g;
    private List h;
    private List i;

    public C0588z(C0767v2 c0767v2, Origin origin, C0658g0 c0658g0, A1 a1) {
        J3 j3 = null;
        this.b = null;
        this.c = null;
        this.e = C0706m3.b.f();
        this.g = Collections.emptyList();
        this.h = Collections.emptyList();
        this.i = Collections.emptyList();
        this.a = c0658g0;
        C0722p1 c0722p1J0 = a1.j0();
        int i = 0;
        C0767v2 c0767v2A = null;
        C0684j2 c0684j2B = null;
        ArrayList arrayList = null;
        while (true) {
            C0633d0[] c0633d0Arr = c0658g0.c;
            if (i >= c0633d0Arr.length) {
                break;
            }
            C0633d0 c0633d0 = c0633d0Arr[i];
            if (C0633d0.h(c0633d0, c0722p1J0)) {
                a(i);
                c0767v2A = C0633d0.a(c0633d0, c0722p1J0);
            } else if (C0633d0.i(c0633d0, c0722p1J0)) {
                a(i);
                c0684j2B = C0633d0.b(c0633d0, c0722p1J0);
            } else if (C0633d0.j(c0633d0, c0722p1J0)) {
                a(i);
                if (this.c == null) {
                    this.c = new ArrayList(c0658g0.c.length - i);
                }
                C3979v2 c3979v2C = C0633d0.c(c0633d0, c0722p1J0);
                this.c.add(new J3(((Integer) c3979v2C.b()).intValue(), c0767v2, null, (C0759u2) c3979v2C.a()));
            } else if (C0633d0.k(c0633d0, c0722p1J0)) {
                a(i);
                ArrayList arrayListD = C0633d0.d(c0633d0, c0722p1J0);
                if (arrayList == null) {
                    arrayList = arrayListD;
                } else if (arrayListD != null) {
                    arrayList.addAll(arrayListD);
                }
            } else if (C0633d0.p(c0633d0, c0722p1J0) && !a1.o) {
                a(i);
                this.e = C0706m3.a(c0767v2.F0(), C0633d0.a(c0633d0), origin, c0722p1J0, a1.e);
            } else if (C0633d0.l(c0633d0, c0722p1J0)) {
                a(i);
                this.f = new W4(C0633d0.e(c0633d0, c0722p1J0));
            } else if (C0633d0.m(c0633d0, c0722p1J0)) {
                a(i);
                ArrayList arrayListF = C0633d0.f(c0633d0, c0722p1J0);
                if (arrayListF != null) {
                    this.g = new ArrayList(arrayListF.size());
                    Iterator it = arrayListF.iterator();
                    while (it.hasNext()) {
                        this.g.add(new X4((C0767v2) it.next()));
                    }
                }
            } else if (C0633d0.n(c0633d0, c0722p1J0)) {
                a(i);
                ArrayList arrayListG = C0633d0.g(c0633d0, c0722p1J0);
                if (arrayListG != null) {
                    this.h = V1.a((Collection) arrayListG, new Function() { // from class: com.android.tools.r8.dex.z$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return new C0670h5((C0767v2) obj);
                        }
                    });
                }
            } else if (C0633d0.o(c0633d0, c0722p1J0)) {
                a(i);
                ArrayList arrayListA = C0633d0.a(c0767v2, c0633d0, c0722p1J0, origin);
                if (arrayListA != null) {
                    this.i = arrayListA;
                }
            } else {
                a(c0633d0);
            }
            i++;
        }
        if (c0767v2A != null || c0684j2B != null) {
            if (!j && c0767v2A != null && c0684j2B != null) {
                throw new AssertionError();
            }
            if (c0684j2B != null) {
                this.b = new O2(c0684j2B);
            } else {
                ArrayList<J3> arrayList2 = this.c;
                if (arrayList2 != null) {
                    for (J3 j4 : arrayList2) {
                        if (c0767v2 == j4.b()) {
                            if (!j4.f()) {
                                break;
                            }
                            j3 = j4;
                            break;
                        }
                    }
                }
                if (j3 == null) {
                    this.b = new O2(c0767v2A);
                } else {
                    if (!j && this.c == null) {
                        throw new AssertionError();
                    }
                    this.c.remove(j3);
                    this.c.add(new J3(j3.a(), c0767v2, c0767v2A, j3.c()));
                }
            }
        }
        if (arrayList != null) {
            if (this.c == null) {
                this.c = new ArrayList(arrayList.size());
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                this.c.add(J3.a((C0767v2) it2.next(), c0767v2));
            }
        }
    }

    public final C0658g0 a() {
        ArrayList arrayList = this.d;
        if (arrayList != null) {
            return arrayList.size() == 0 ? C0658g0.t0() : C0658g0.a((C0633d0[]) this.d.toArray(C0633d0.c));
        }
        return this.a;
    }

    public final O2 b() {
        return this.b;
    }

    public final List c() {
        ArrayList arrayList = this.c;
        return arrayList == null ? Collections.emptyList() : arrayList;
    }

    private void a(int i) {
        if (this.d == null) {
            ArrayList arrayList = new ArrayList(this.a.c.length);
            this.d = arrayList;
            arrayList.addAll(Arrays.asList(this.a.c).subList(0, i));
        }
    }

    private void a(C0633d0 c0633d0) {
        ArrayList arrayList = this.d;
        if (arrayList != null) {
            arrayList.add(c0633d0);
        }
    }
}
