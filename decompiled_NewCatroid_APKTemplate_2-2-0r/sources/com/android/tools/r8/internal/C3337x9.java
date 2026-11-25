package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.x9, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3337x9 extends V1 {
    private final AbstractC3462ys q;
    private C0863Bn r;
    private int s;

    C3337x9(F4 f4) {
        super(f4);
        this.q = f4;
    }

    private void b(int i) throws W1 {
        for (AbstractC3097u abstractC3097uA = this.r.j(i).a(); abstractC3097uA != null; abstractC3097uA = abstractC3097uA.a()) {
            if (abstractC3097uA.b() >= 0 || (abstractC3097uA instanceof C1295Qi)) {
                int i2 = i + 1;
                if (a()[i2] == null) {
                    throw new W1(null, AbstractC1857dg.a("Expected stack map frame at instruction ", i2));
                }
                return;
            }
        }
    }

    @Override // com.android.tools.r8.internal.V1
    protected final void c(ME me) throws W1 {
        this.r = me.x;
        this.s = CZ.b(me.l) >> 2;
        C1238Oi[] c1238OiArrA = a();
        C1238Oi c1238Oi = c1238OiArrA[0];
        ListIterator it = me.x.iterator();
        int i = -1;
        int i2 = 0;
        C1238Oi c1238OiA = c1238Oi;
        while (it.hasNext()) {
            AbstractC3097u abstractC3097u = (AbstractC3097u) it.next();
            boolean z = abstractC3097u instanceof C1295Qi;
            if (z) {
                try {
                    c1238OiA = a(c1238OiA, (C1295Qi) abstractC3097u);
                    for (int i3 = i + 1; i3 <= i2; i3++) {
                        a()[i3] = c1238OiA;
                    }
                } catch (W1 e) {
                    throw new W1(e.a, AbstractC1588a5.a("Error at instruction ", i2, ": ").append(e.getMessage()).toString(), e);
                }
            }
            if (abstractC3097u.b() >= 0 || z) {
                i = i2;
            }
            i2++;
        }
        for (int i4 = 0; i4 < this.r.size(); i4++) {
            C1238Oi c1238Oi2 = c1238OiArrA[i4];
            try {
                AbstractC3097u abstractC3097uJ = me.x.j(i4);
                int iB = abstractC3097uJ.b();
                int iC = abstractC3097uJ.c();
                if (iC == 8 || iC == 15 || iC == 14) {
                    a(i4 + 1, c1238Oi2, false);
                } else {
                    c1238Oi.a(c1238Oi2).a(abstractC3097uJ, this.q);
                    if (abstractC3097uJ instanceof C1871du) {
                        if (iB == 168) {
                            throw new W1(abstractC3097uJ, "JSR instructions are unsupported");
                        }
                        a(this.r.b(((C1871du) abstractC3097uJ).g), c1238Oi, true);
                        if (iB == 167) {
                            b(i4);
                        } else {
                            a(i4 + 1, c1238Oi, false);
                        }
                    } else if (abstractC3097uJ instanceof C2056gD) {
                        C2056gD c2056gD = (C2056gD) abstractC3097uJ;
                        a(this.r.b(c2056gD.g), c1238Oi, true);
                        for (int i5 = 0; i5 < c2056gD.i.size(); i5++) {
                            a(this.r.b((C3338xA) c2056gD.i.get(i5)), c1238Oi, true);
                        }
                        b(i4);
                    } else if (abstractC3097uJ instanceof YY) {
                        YY yy = (YY) abstractC3097uJ;
                        a(this.r.b(yy.i), c1238Oi, true);
                        for (int i6 = 0; i6 < yy.j.size(); i6++) {
                            a(this.r.b((C3338xA) yy.j.get(i6)), c1238Oi, true);
                        }
                        b(i4);
                    } else {
                        if (iB == 169) {
                            throw new W1(abstractC3097uJ, "RET instructions are unsupported");
                        }
                        if (iB == 191 || (iB >= 172 && iB <= 177)) {
                            b(i4);
                        } else {
                            a(i4 + 1, c1238Oi, false);
                        }
                    }
                }
                List<C3443yZ> listA = a(i4);
                if (listA != null) {
                    for (C3443yZ c3443yZ : listA) {
                        String str = c3443yZ.d;
                        CZ czD = str == null ? CZ.d("java/lang/Throwable") : CZ.d(str);
                        C1238Oi c1238Oi3 = new C1238Oi(c1238Oi2);
                        c1238Oi3.a();
                        this.q.getClass();
                        c1238Oi3.a(F4.a(czD));
                        a(this.r.b(c3443yZ.c), c1238Oi3, true);
                    }
                }
                for (AbstractC3097u abstractC3097uA = this.r.j(i4).a(); abstractC3097uA != null; abstractC3097uA = abstractC3097uA.a()) {
                    if (abstractC3097uA.b() < 0 && !(abstractC3097uA instanceof C1295Qi)) {
                    }
                }
                return;
            } catch (W1 e2) {
                throw new W1(e2.a, AbstractC1588a5.a("Error at instruction ", i4, ": ").append(e2.getMessage()).toString(), e2);
            } catch (RuntimeException e3) {
                throw new W1(null, AbstractC1588a5.a("Error at instruction ", i4, ": ").append(e3.getMessage()).toString(), e3);
            }
        }
    }

    private E4 a(C1295Qi c1295Qi, Object obj) throws W1 {
        if (obj == JK.a) {
            ((F4) this.q).getClass();
            return F4.a((CZ) null);
        }
        if (obj == JK.b) {
            AbstractC3462ys abstractC3462ys = this.q;
            CZ cz = CZ.j;
            ((F4) abstractC3462ys).getClass();
            return F4.a(cz);
        }
        if (obj == JK.c) {
            AbstractC3462ys abstractC3462ys2 = this.q;
            CZ cz2 = CZ.k;
            ((F4) abstractC3462ys2).getClass();
            return F4.a(cz2);
        }
        if (obj == JK.e) {
            AbstractC3462ys abstractC3462ys3 = this.q;
            CZ cz3 = CZ.l;
            ((F4) abstractC3462ys3).getClass();
            return F4.a(cz3);
        }
        if (obj == JK.d) {
            AbstractC3462ys abstractC3462ys4 = this.q;
            CZ cz4 = CZ.m;
            ((F4) abstractC3462ys4).getClass();
            return F4.a(cz4);
        }
        if (obj == JK.f) {
            return this.q.a(new C0903Cn(1));
        }
        if (obj == JK.g) {
            AbstractC3462ys abstractC3462ys5 = this.q;
            CZ czD = CZ.d("dummy");
            ((F4) abstractC3462ys5).getClass();
            return F4.a(czD);
        }
        if (obj instanceof String) {
            AbstractC3462ys abstractC3462ys6 = this.q;
            CZ czD2 = CZ.d((String) obj);
            ((F4) abstractC3462ys6).getClass();
            return F4.a(czD2);
        }
        if (obj instanceof C3338xA) {
            AbstractC3097u abstractC3097uA = (C3338xA) obj;
            while (abstractC3097uA != null && abstractC3097uA.b() < 0) {
                abstractC3097uA = abstractC3097uA.a();
            }
            if (abstractC3097uA != null && abstractC3097uA.b() == 187) {
                AbstractC3462ys abstractC3462ys7 = this.q;
                CZ czD3 = CZ.d(((A00) abstractC3097uA).g);
                ((F4) abstractC3462ys7).getClass();
                return F4.a(czD3);
            }
            throw new W1(c1295Qi, "LabelNode does not designate a NEW instruction");
        }
        throw new W1(c1295Qi, "Illegal stack map frame value " + obj);
    }

    /* JADX WARN: Code duplicated, block: B:34:0x00d7  */
    /* JADX WARN: Code duplicated, block: B:42:? A[RETURN, SYNTHETIC] */
    private void a(int i, C1238Oi c1238Oi, boolean z) throws W1 {
        String string;
        C1238Oi c1238Oi2 = a()[i];
        if (c1238Oi2 == null) {
            if (!z) {
                a()[i] = new C1238Oi(c1238Oi);
                return;
            }
            throw new W1(null, AbstractC1857dg.a("Expected stack map frame at instruction ", i));
        }
        int iB = c1238Oi.b();
        if (iB == c1238Oi2.b()) {
            for (int i2 = 0; i2 < iB; i2++) {
                AbstractC3462ys abstractC3462ys = this.q;
                R10 r10A = c1238Oi.a(i2);
                R10 r10A2 = c1238Oi2.a(i2);
                ((F4) abstractC3462ys).getClass();
                E4 e4 = (E4) r10A;
                if (!e4.equals((E4) r10A2)) {
                    e4 = E4.b;
                }
                if (!e4.equals(c1238Oi2.a(i2))) {
                    string = AbstractC1588a5.a("incompatible types at local ", i2, ": ").append(c1238Oi.a(i2)).append(" and ").append(c1238Oi2.a(i2)).toString();
                    if (string == null) {
                        throw new W1(null, "Stack map frame incompatible with frame at instruction " + i + " (" + string + Constant.AFTER_QUTO);
                    }
                    return;
                }
            }
            int iC = c1238Oi.c();
            if (iC != c1238Oi2.c()) {
                string = "incompatible stack heights";
            } else {
                for (int i3 = 0; i3 < iC; i3++) {
                    AbstractC3462ys abstractC3462ys2 = this.q;
                    R10 r10B = c1238Oi.b(i3);
                    R10 r10B2 = c1238Oi2.b(i3);
                    ((F4) abstractC3462ys2).getClass();
                    E4 e5 = (E4) r10B;
                    if (!e5.equals((E4) r10B2)) {
                        e5 = E4.b;
                    }
                    if (!e5.equals(c1238Oi2.b(i3))) {
                        string = AbstractC1588a5.a("incompatible types at stack item ", i3, ": ").append(c1238Oi.b(i3)).append(" and ").append(c1238Oi2.b(i3)).toString();
                    }
                }
                string = null;
            }
            if (string == null) {
                throw new W1(null, "Stack map frame incompatible with frame at instruction " + i + " (" + string + Constant.AFTER_QUTO);
            }
            return;
        }
        throw new AssertionError();
    }

    /* JADX WARN: Code duplicated, block: B:45:0x00b5 A[LOOP:0: B:43:0x00af->B:45:0x00b5, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:48:0x00cb  */
    /* JADX WARN: Code duplicated, block: B:52:0x00dc A[LOOP:1: B:50:0x00d6->B:52:0x00dc, LOOP_END] */
    private C1238Oi a(C1238Oi c1238Oi, C1295Qi c1295Qi) throws W1 {
        List listEmptyList;
        Iterator it;
        C1238Oi c1238Oi2 = new C1238Oi(c1238Oi);
        List listEmptyList2 = c1295Qi.h;
        if (listEmptyList2 == null) {
            listEmptyList2 = Collections.emptyList();
        }
        int i = this.s;
        int i2 = c1295Qi.g;
        if (i2 != -1 && i2 != 0) {
            if (i2 != 1) {
                if (i2 == 2) {
                    Iterator it2 = listEmptyList2.iterator();
                    while (it2.hasNext()) {
                        it2.next();
                        if (i > 0) {
                            i = (i <= 1 || ((E4) c1238Oi2.a(i + (-2))).a() != 2) ? i - 1 : i - 2;
                        } else {
                            throw new W1(c1295Qi, "Cannot chop more locals than defined");
                        }
                    }
                } else if (i2 != 3 && i2 != 4) {
                    throw new W1(c1295Qi, "Illegal frame type " + c1295Qi.g);
                }
            }
            this.s = i;
            while (i < c1238Oi2.b()) {
                ((F4) this.q).getClass();
                c1238Oi2.a(i, F4.a((CZ) null));
                i++;
            }
            listEmptyList = c1295Qi.i;
            if (listEmptyList == null) {
                listEmptyList = Collections.emptyList();
            }
            c1238Oi2.a();
            it = listEmptyList.iterator();
            while (it.hasNext()) {
                c1238Oi2.a(a(c1295Qi, it.next()));
            }
            return c1238Oi2;
        }
        i = 0;
        Iterator it3 = listEmptyList2.iterator();
        while (it3.hasNext()) {
            E4 e4A = a(c1295Qi, it3.next());
            if (e4A.a() + i <= c1238Oi2.b()) {
                int i3 = i + 1;
                c1238Oi2.a(i, e4A);
                if (e4A.a() == 2) {
                    i += 2;
                    ((F4) this.q).getClass();
                    c1238Oi2.a(i3, F4.a((CZ) null));
                } else {
                    i = i3;
                }
            } else {
                throw new W1(c1295Qi, "Cannot append more locals than maxLocals");
            }
        }
        this.s = i;
        while (i < c1238Oi2.b()) {
            ((F4) this.q).getClass();
            c1238Oi2.a(i, F4.a((CZ) null));
            i++;
        }
        listEmptyList = c1295Qi.i;
        if (listEmptyList == null) {
            listEmptyList = Collections.emptyList();
        }
        c1238Oi2.a();
        it = listEmptyList.iterator();
        while (it.hasNext()) {
            c1238Oi2.a(a(c1295Qi, it.next()));
        }
        return c1238Oi2;
    }
}
