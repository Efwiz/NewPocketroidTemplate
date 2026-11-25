package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.internal.AbstractC3135uW;
import com.android.tools.r8.internal.C3089ts;
import com.android.tools.r8.internal.PI;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3932m1;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class o0 {
    static final /* synthetic */ boolean f = true;
    private final n0 a;
    private final Map b;
    private final C0722p1 c;
    private final A1 d;
    private final p0 e;

    o0(ArrayList arrayList, List list, C0702m c0702m, Collection collection, IdentityHashMap identityHashMap, AbstractC3135uW abstractC3135uW, C3932m1 c3932m1) {
        this.a = n0.a(collection, identityHashMap, abstractC3135uW);
        this.b = identityHashMap;
        this.c = c0702m.a();
        this.d = c0702m.Y();
        this.e = new p0(arrayList, list, c0702m, c3932m1);
    }

    /* JADX WARN: Code duplicated, block: B:54:0x013b  */
    public final void a() {
        boolean zStartsWith;
        String strA;
        if (!this.a.a.isEmpty()) {
            if (!f && !this.d.G0().b()) {
                throw new AssertionError();
            }
            r0 r0VarB = this.e.b();
            Iterator it = this.a.a.iterator();
            while (it.hasNext()) {
                r0VarB.a((C0729q2) it.next());
            }
            if (a(r0VarB, this.d)) {
                r0VarB.c.a();
            } else {
                r0VarB.a();
                for (C0729q2 c0729q2 : this.a.a) {
                    r0VarB.a(c0729q2);
                    if (a(r0VarB, this.d)) {
                        r0VarB.c.a();
                    } else {
                        r0VarB.a();
                        p0 p0Var = this.e;
                        r0 r0VarA = p0Var.a();
                        p0Var.c();
                        r0VarA.a(c0729q2);
                        if (!f && !a(r0VarA, this.d)) {
                            throw new AssertionError();
                        }
                        r0VarA.c.a();
                        r0VarB = r0VarA;
                    }
                }
            }
            if (this.d.G0().c()) {
                p0 p0Var2 = this.e;
                p0Var2.b.clear();
                p0Var2.c();
            } else {
                this.e.d();
            }
        }
        PI pi = new PI(0);
        p0 p0Var3 = this.e;
        if (p0Var3.b.isEmpty()) {
            p0Var3.a();
            p0Var3.c();
        }
        r0 r0VarB2 = p0Var3.b();
        List list = this.a.b;
        ArrayList arrayList = new ArrayList();
        int i = 0;
        int i2 = 0;
        String strA2 = null;
        int i3 = 4;
        while (i < list.size()) {
            C0729q2 c0729q3 = (C0729q2) list.get(i);
            String str = (String) this.b.get(c0729q3);
            if (strA2 == null) {
                zStartsWith = false;
            } else if (strA2.endsWith(".*")) {
                zStartsWith = str.startsWith(strA2.substring(0, strA2.length() - 2));
            } else if (str.startsWith(strA2) && str.lastIndexOf(46) == strA2.length()) {
                zStartsWith = true;
            } else {
                zStartsWith = false;
            }
            if (!zStartsWith) {
                if (strA2 != null) {
                    r0VarB2.c.a();
                    boolean z = f;
                    if (!z) {
                        if (!z && pi.containsKey(strA2)) {
                            throw new AssertionError();
                        }
                        pi.a(r0VarB2.g(), strA2);
                    }
                    this.e.d();
                    i3 = 3;
                }
                do {
                    i3++;
                    strA = a(i3, str, false);
                    if (strA2 == null) {
                        break;
                    }
                } while (strA2.startsWith(strA));
                if (!strA.equals("")) {
                    strA2 = a(i3, str, true);
                }
                i2 = i;
            }
            if (strA2 == null) {
                boolean z2 = f;
                if (!z2 && c0729q3.f == null) {
                    throw new AssertionError();
                }
                if (!z2) {
                    l0 l0Var = r0VarB2.c;
                    if (!(l0Var.d.isEmpty() && l0Var.e.isEmpty() && l0Var.f.isEmpty() && l0Var.h.isEmpty() && l0Var.g.isEmpty() && l0Var.i.isEmpty())) {
                        throw new AssertionError();
                    }
                }
                arrayList.add(c0729q3);
            } else {
                if (!f && c0729q3.f == null && c0729q3.d != this.c.Z1) {
                    throw new AssertionError();
                }
                r0VarB2.a(c0729q3);
                if (!a(r0VarB2, this.d)) {
                    int i4 = (i - i2) + 1;
                    l0 l0Var2 = r0VarB2.c;
                    int size = l0Var2.b.a.size() + l0Var2.d.size();
                    r0VarB2.a();
                    if (i4 <= size / 5 || i3 >= 7) {
                        if (!this.e.f.hasNext()) {
                            if (r0VarB2.m()) {
                                while (i2 <= i) {
                                    arrayList.add((C0729q2) list.get(i2));
                                    i2++;
                                }
                                i2 = i + 1;
                            }
                            p0 p0Var4 = this.e;
                            p0Var4.a();
                            p0Var4.c();
                        }
                        i = i2 - 1;
                        r0VarB2 = this.e.b();
                        strA2 = null;
                        i3 = 4;
                    } else {
                        i = i2 - 1;
                        i3++;
                        strA2 = null;
                    }
                }
            }
            i++;
        }
        r0VarB2.c.a();
        boolean z3 = f;
        if (!z3 && strA2 != null) {
            if (!z3 && pi.containsKey(strA2)) {
                throw new AssertionError();
            }
            pi.a(r0VarB2.g(), strA2);
        }
        a(this.e, arrayList);
    }

    private static String a(int i, String str, boolean z) {
        int iIndexOf = 0;
        int i2 = 0;
        int i3 = 0;
        while (iIndexOf != -1) {
            int i4 = i2 + 1;
            if (i2 >= i) {
                i2 = i4;
                break;
            }
            i3 = iIndexOf;
            iIndexOf = str.indexOf(46, iIndexOf + 1);
            i2 = i4;
        }
        String strSubstring = str.substring(0, i3);
        return (!z || i2 < i) ? strSubstring : strSubstring + ".*";
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0028  */
    private static boolean a(r0 r0Var, A1 a1) {
        boolean zA;
        if (a1.n1.C0 > 0) {
            l0 l0Var = r0Var.c;
            if (l0Var.b.a.size() + l0Var.d.size() > a1.n1.C0) {
                zA = true;
            } else {
                zA = r0Var.a(65536);
            }
        } else {
            zA = r0Var.a(65536);
        }
        return !zA;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0060  */
    /* JADX WARN: Code duplicated, block: B:27:0x0092  */
    /* JADX WARN: Code duplicated, block: B:29:0x0098  */
    /* JADX WARN: Code duplicated, block: B:41:0x00e3  */
    /* JADX WARN: Code duplicated, block: B:50:0x0115  */
    /* JADX WARN: Code duplicated, block: B:52:0x011b  */
    private void a(p0 p0Var, ArrayList arrayList) {
        boolean zA;
        boolean zA2;
        boolean zA3;
        boolean zA4;
        if (arrayList.isEmpty()) {
            return;
        }
        p0Var.d();
        r0 r0VarB = p0Var.b();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            if (r0VarB.n()) {
                r0VarB = null;
                while (p0Var.f.hasNext()) {
                    r0VarB = p0Var.b();
                    A1 a1 = this.d;
                    if (a1.n1.C0 > 0) {
                        l0 l0Var = r0VarB.c;
                        if (l0Var.b.a.size() + l0Var.d.size() > a1.n1.C0) {
                            zA4 = true;
                        } else {
                            zA4 = r0VarB.a(65536);
                        }
                    } else {
                        zA4 = r0VarB.a(65536);
                    }
                    if (!zA4) {
                        break;
                    }
                }
                if (r0VarB != null) {
                    A1 a2 = this.d;
                    if (a2.n1.C0 > 0) {
                        l0 l0Var2 = r0VarB.c;
                        if (l0Var2.b.a.size() + l0Var2.d.size() > a2.n1.C0) {
                            zA3 = true;
                        } else {
                            zA3 = r0VarB.a(65536);
                        }
                    } else {
                        zA3 = r0VarB.a(65536);
                    }
                    if (zA3) {
                        r0VarB = p0Var.a();
                        p0Var.c();
                    }
                } else {
                    r0VarB = p0Var.a();
                    p0Var.c();
                }
            }
            r0VarB.a(c0729q2);
            while (r0VarB.n()) {
                r0VarB.a();
                r0VarB = null;
                while (p0Var.f.hasNext()) {
                    r0VarB = p0Var.b();
                    A1 a3 = this.d;
                    if (a3.n1.C0 > 0) {
                        l0 l0Var3 = r0VarB.c;
                        if (l0Var3.b.a.size() + l0Var3.d.size() > a3.n1.C0) {
                            zA2 = true;
                        } else {
                            zA2 = r0VarB.a(65536);
                        }
                    } else {
                        zA2 = r0VarB.a(65536);
                    }
                    if (!zA2) {
                        break;
                    }
                }
                if (r0VarB != null) {
                    A1 a4 = this.d;
                    if (a4.n1.C0 > 0) {
                        l0 l0Var4 = r0VarB.c;
                        if (l0Var4.b.a.size() + l0Var4.d.size() > a4.n1.C0) {
                            zA = true;
                        } else {
                            zA = r0VarB.a(65536);
                        }
                    } else {
                        zA = r0VarB.a(65536);
                    }
                    if (zA) {
                        r0VarB = p0Var.a();
                        p0Var.c();
                    }
                } else {
                    r0VarB = p0Var.a();
                    p0Var.c();
                }
                boolean zM = r0VarB.m();
                r0VarB.a(c0729q2);
                if (zM && r0VarB.n()) {
                    throw new C3089ts("Class " + c0729q2.toString() + " does not fit into a single dex file.");
                }
            }
            r0VarB.c();
        }
    }
}
