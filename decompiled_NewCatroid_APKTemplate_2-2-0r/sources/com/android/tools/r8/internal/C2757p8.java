package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.InterfaceC3937n1;
import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.bytebuddy.asm.Advice;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: renamed from: com.android.tools.r8.internal.p8, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2757p8 {
    static final /* synthetic */ boolean i = true;
    private final String a;
    private final List b;
    private final MR c;
    private final List d;
    private final StringBuilder e;
    private final com.android.tools.r8.utils.P2 f;
    private int g;
    private final int h;

    public C2757p8() {
        this.e = new StringBuilder();
        this.g = 0;
        this.a = "";
        this.c = null;
        this.f = com.android.tools.r8.utils.P2.a();
        this.h = 0;
        this.b = Collections.emptyList();
        this.d = Collections.emptyList();
    }

    private ArrayList a(ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList(this.b.size());
        HashSet hashSet = new HashSet();
        ListIterator listIterator = arrayList.listIterator();
        Iterator it = this.b.iterator();
        while (it.hasNext()) {
            final int iA = this.c.a((M7) it.next());
            hashSet.removeIf(new Predicate() { // from class: com.android.tools.r8.internal.p8$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.a(iA, (C0753u.a) obj);
                }
            });
            while (listIterator.hasNext()) {
                C0753u.a aVar = (C0753u.a) listIterator.next();
                int iA2 = this.c.a(aVar.d());
                int iA3 = this.c.a(aVar.a());
                if (iA2 > iA) {
                    listIterator.previous();
                    break;
                }
                if (iA < iA3) {
                    hashSet.add(aVar);
                }
            }
            ArrayList arrayList3 = new ArrayList(hashSet);
            arrayList3.sort(new Comparator() { // from class: com.android.tools.r8.internal.p8$$ExternalSyntheticLambda4
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return C2757p8.a((C0753u.a) obj, (C0753u.a) obj2);
                }
            });
            arrayList2.add(arrayList3);
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int b(C0753u.a aVar, C0753u.a aVar2) {
        int iCompare = Integer.compare(this.c.a(aVar.d()), this.c.a(aVar2.d()));
        if (iCompare != 0) {
            return iCompare;
        }
        int iCompare2 = Integer.compare(this.c.a(aVar2.a()), this.c.a(aVar.a()));
        return iCompare2 != 0 ? iCompare2 : Integer.compare(aVar.b(), aVar2.b());
    }

    private void c() {
        if (this.e.length() > 0) {
            this.e.append('\n');
        }
    }

    public void d() {
        b("nop");
    }

    public void e() {
        b("athrow");
    }

    public void f() {
        b("aconst_null");
    }

    public void g() {
        b("return");
    }

    public void h() {
        b("arraylength");
    }

    public String toString() {
        return this.e.toString();
    }

    private void b(String str) {
        a();
        this.e.append(str);
    }

    public void b(M7 m7) {
        c();
        b();
        this.e.append(a(m7)).append(':');
        MR mr = this.c;
        if (mr != null) {
            a("locals: " + String.join(", ", (Iterable<? extends CharSequence>) ((List) this.d.get(mr.a(m7))).stream().map(new Function() { // from class: com.android.tools.r8.internal.p8$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((C0753u.a) obj).toString();
                }
            }).collect(Collectors.toList())));
        }
    }

    public C2757p8(C0753u c0753u, com.android.tools.r8.graph.W0 w0, com.android.tools.r8.utils.P2 p2) {
        this.e = new StringBuilder();
        this.g = 0;
        this.f = p2;
        this.a = "  ";
        this.h = new StringBuilder().append(c0753u.I0().size()).toString().length();
        this.c = new MR();
        this.b = new ArrayList();
        for (B7 b7 : c0753u.I0()) {
            if (b7 instanceof M7) {
                M7 m7 = (M7) b7;
                this.c.a(this.b.size(), m7);
                this.b.add(m7);
            }
        }
        if (w0 != null) {
            this.e.append(".method ");
            a(w0.getReference());
            c();
        }
        this.e.append(".limit stack ").append(c0753u.L0());
        c();
        this.e.append(".limit locals ").append(c0753u.K0());
        ArrayList<C0753u.a> arrayListA = a(c0753u);
        this.d = a(arrayListA);
        for (C0753u.a aVar : arrayListA) {
            com.android.tools.r8.graph.X xC = aVar.c();
            c();
            this.e.append(".var ").append(aVar.b()).append(" is ").append(xC.a).append(" ").append(xC.b.b1()).append(" from ").append(a(aVar.d())).append(" to ").append(a(aVar.a()));
            C0759u2 c0759u2 = xC.c;
            if (c0759u2 != null) {
                a(c0759u2.toString());
            }
        }
        for (W8 w8 : c0753u.Q0()) {
            for (int i2 = 0; i2 < w8.c.size(); i2++) {
                c();
                C0767v2 c0767v2 = w8.c.get(i2);
                if (!i && c0767v2 == null) {
                    throw new AssertionError();
                }
                this.e.append(".catch ").append(c0767v2.E0()).append(" from ").append(a(w8.a)).append(" to ").append(a(w8.b)).append(" using ").append(a(w8.d.get(i2)));
            }
        }
        Iterator<B7> it = c0753u.I0().iterator();
        while (it.hasNext()) {
            it.next().a(this);
        }
        c();
        if (w0 != null) {
            this.e.append(".end method");
            c();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(int i2, C0753u.a aVar) {
        return this.c.a(aVar.a()) <= i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(C0753u.a aVar, C0753u.a aVar2) {
        return Integer.compare(aVar.b(), aVar2.b());
    }

    private ArrayList a(C0753u c0753u) {
        ArrayList arrayList = new ArrayList(c0753u.J0());
        arrayList.sort(new Comparator() { // from class: com.android.tools.r8.internal.p8$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return this.f$0.b((C0753u.a) obj, (C0753u.a) obj2);
            }
        });
        return arrayList;
    }

    public final void a(C2830q8 c2830q8) {
        String string;
        if (c2830q8.T().length == 0) {
            string = "empty";
        } else {
            string = c2830q8.T()[0].e.toString();
        }
        b("record_field_values(" + string + Constant.AFTER_QUTO);
    }

    private void b() {
        int i2 = this.h;
        if (i2 > 0) {
            int i3 = this.g;
            this.g = i3 + 1;
            this.e.append(String.format("%" + i2 + "d: ", Integer.valueOf(i3)));
        }
    }

    public void a(D8 d8) {
        switch (AbstractC2676o8.a[d8.T().ordinal()]) {
            case 1:
                b("pop");
                return;
            case 2:
                b("pop2");
                return;
            case 3:
                b("dup");
                return;
            case 4:
                b("dup_x1");
                return;
            case 5:
                b("dup_x2");
                return;
            case 6:
                b("dup2");
                return;
            case 7:
                b("dup2_x1");
                return;
            case 8:
                b("dup2_x2");
                return;
            case 9:
                b("swap");
                return;
            default:
                throw new C3405y10("Invalid instruction for CfStackInstruction");
        }
    }

    public void a(C1818d7 c1818d7) {
        a();
        int i2 = AbstractC2676o8.b[c1818d7.Y().ordinal()];
        if (i2 == 1) {
            this.e.append("ldc ").append(c1818d7.V());
            return;
        }
        if (i2 == 2) {
            this.e.append("ldc ").append(c1818d7.U());
            return;
        }
        if (i2 == 3) {
            this.e.append("ldc_w ").append(c1818d7.W());
        } else {
            if (i2 != 4) {
                throw new C3405y10("Unexpected const-number type: " + c1818d7.Y());
            }
            this.e.append("ldc_w ").append(c1818d7.T());
        }
    }

    public void a(X6 x6) {
        a();
        this.e.append("ldc ");
        C0767v2 type = x6.getType();
        if (!type.M0() && !type.O0()) {
            this.e.append(this.f.a(type));
        } else {
            a(type);
        }
    }

    public void a(Y6 y6) {
        a();
        this.e.append("ldc <dynamic> ");
        C0767v2 type = y6.getType();
        if (!type.M0() && !type.O0()) {
            this.e.append(this.f.a(type));
        } else {
            a(type);
        }
    }

    public final void a(C3335x7 c3335x7) {
        a();
        this.e.append("initclass ");
        C0767v2 c0767v2T = c3335x7.T();
        if (!c0767v2T.M0() && !c0767v2T.O0()) {
            this.e.append(this.f.a(c0767v2T));
        } else {
            a(c0767v2T);
        }
    }

    public void a(C3109u8 c3109u8) {
        b(a(c3109u8.V()) + "return");
    }

    public void a(R7 r7) {
        b(r7.T() == EnumC3423yF.ENTER ? "monitorenter" : "monitorexit");
    }

    public void a(C3481z6 c3481z6) {
        b(a(c3481z6.T()));
    }

    public void a(U6 u6) {
        b(a(u6.T()));
    }

    public void a(Q7 q7) {
        b(a(q7.T()));
    }

    public void a(U7 u7) {
        b(a(u7.T()));
    }

    public void a(C1591a8 c1591a8) {
        b(a(c1591a8.T()));
    }

    public void a(C1891e7 c1891e7) {
        a();
        this.e.append("ldc ").append(c1891e7.T());
    }

    public void a(C2134h7 c2134h7) {
        a();
        this.e.append("ldc* ").append(c2134h7.T().toString());
    }

    public void a(B6 b6) {
        a();
        this.e.append(a(b6.T())).append("aload");
    }

    public void a(D6 d6) {
        a();
        this.e.append(a(d6.T())).append("astore");
    }

    public void a(I7 i7) {
        a();
        this.e.append(a(i7.U())).append(StringUtil.SPACE);
        a(i7.T());
    }

    public void a(J7 j7) {
        com.android.tools.r8.graph.G2 g2G0;
        a();
        C0721p0 c0721p0T = j7.T();
        C0699l2 c0699l2 = c0721p0T.f;
        this.e.append(a(186)).append(StringUtil.SPACE);
        this.e.append(c0721p0T.d);
        StringBuilder sb = this.e;
        C0737r2 c0737r2 = c0721p0T.e;
        c0737r2.getClass();
        sb.append(c0737r2.a(AbstractC3649o0.a()));
        if (c0721p0T.g.size() > 1 && (g2G0 = ((com.android.tools.r8.graph.B2) c0721p0T.g.get(1)).G0()) != null) {
            C0699l2 c0699l3 = (C0699l2) g2G0.j1();
            this.e.append(", handle:");
            this.e.append(c0699l3.r0());
            this.e.append(", itf: ").append(c0699l3.f);
        }
        this.e.append(", bsm:");
        a(c0699l2.u0());
    }

    public void a(C2284j7 c2284j7) {
        a();
        this.e.append("; frame: [");
        if (!c2284j7.W().isEmpty()) {
            final int iA = c2284j7.W().a();
            c2284j7.a(new InterfaceC3937n1() { // from class: com.android.tools.r8.internal.p8$$ExternalSyntheticLambda1
                @Override // com.android.tools.r8.utils.InterfaceC3937n1
                public final void a(int i2, Object obj) {
                    this.f$0.a(iA, i2, (InterfaceC1348Si) obj);
                }
            });
        }
        this.e.append("] [");
        String str = "";
        for (InterfaceC3054tM interfaceC3054tM : c2284j7.Z()) {
            this.e.append(str);
            a(interfaceC3054tM);
            str = ", ";
        }
        this.e.append(']');
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i2, int i3, InterfaceC1348Si interfaceC1348Si) {
        this.e.append(i3 == i2 ? "" : ", ").append(i3).append(':');
        a(interfaceC1348Si);
    }

    private void a(InterfaceC1348Si interfaceC1348Si) {
        if (interfaceC1348Si.M()) {
            if (interfaceC1348Si.l()) {
                this.e.append("null");
                return;
            }
            if (interfaceC1348Si.G()) {
                this.e.append(interfaceC1348Si.f().T());
                return;
            } else {
                if (!i && !interfaceC1348Si.N()) {
                    throw new AssertionError();
                }
                C0767v2 c0767v2T = interfaceC1348Si.j().T();
                if (!c0767v2T.M0() && !c0767v2T.O0()) {
                    this.e.append(this.f.a(c0767v2T));
                    return;
                } else {
                    a(c0767v2T);
                    return;
                }
            }
        }
        if (interfaceC1348Si.y()) {
            this.e.append("uninitialized ").append(a(interfaceC1348Si.R()));
        } else {
            this.e.append(interfaceC1348Si);
        }
    }

    public void a(A7 a7) {
        a();
        this.e.append("instanceof ");
        a(a7.getType());
    }

    public void a(M6 m6) {
        a();
        this.e.append("checkcast ");
        a(m6.getType());
    }

    public void a(AbstractC2206i7 abstractC2206i7) {
        a();
        switch (abstractC2206i7.U()) {
            case 178:
                this.e.append("getstatic ");
                break;
            case 179:
                this.e.append("putstatic ");
                break;
            case 180:
                this.e.append("getfield ");
                break;
            case 181:
                this.e.append("putfield ");
                break;
            default:
                throw new C3405y10("Unexpected field-instruction opcode " + abstractC2206i7.U());
        }
        this.e.append(this.f.c(abstractC2206i7.getField()));
        this.e.append(StringUtil.SPACE);
        this.e.append(this.f.a(abstractC2206i7.getField().h));
    }

    public void a(V7 v7) {
        a();
        this.e.append("new ");
        a(v7.getType());
    }

    public void a(W7 w7) {
        a();
        String strSubstring = w7.getType().b1().substring(1);
        if (w7.getType().V0()) {
            this.e.append("newarray ");
            this.e.append(C3970u0.b(strSubstring));
            return;
        }
        this.e.append("anewarray ");
        if (strSubstring.charAt(0) == '[') {
            this.e.append(strSubstring);
        } else {
            this.e.append(CZ.f(strSubstring).c());
        }
    }

    public final void a(X7 x7) {
        a();
        this.e.append("newunboxedenum ");
        a(x7.getType());
    }

    public void a(S7 s7) {
        a();
        this.e.append("multianewarray ");
        a(s7.getType());
        this.e.append(StringUtil.SPACE).append(s7.T());
    }

    public void a(C2051g8 c2051g8) {
        AbstractC1915eM abstractC1915eMU = c2051g8.U();
        a();
        this.e.append(".line ").append(abstractC1915eMU.e());
        if (abstractC1915eMU.k() || abstractC1915eMU.l()) {
            a(abstractC1915eMU.toString());
        }
    }

    public void a(C2899r7 c2899r7) {
        a();
        this.e.append("goto ").append(a(c2899r7.A()));
    }

    public void a(C3039t7 c3039t7) {
        a();
        if (c3039t7.V().a()) {
            this.e.append(PsiKeyword.IF).append(c3039t7.U() == EnumC1076Il.EQ ? "null" : "nonnull");
        } else {
            this.e.append(PsiKeyword.IF).append(c3039t7.U().toString().toLowerCase());
        }
        this.e.append(StringUtil.SPACE).append(a(c3039t7.A()));
    }

    public void a(C3185v7 c3185v7) {
        a();
        this.e.append(c3185v7.V().a() ? "if_acmp" : "if_icmp").append(c3185v7.U().toString().toLowerCase()).append(StringUtil.SPACE).append(a(c3185v7.A()));
    }

    public void a(U8 u8) {
        a();
        U8.a aVarW = u8.W();
        this.e.append(aVarW == U8.a.LOOKUP ? "lookup" : "table").append(PsiKeyword.SWITCH);
        InterfaceC0967Er interfaceC0967Er = (InterfaceC0967Er) u8.V();
        List listX = u8.X();
        for (int i2 = 0; i2 < listX.size(); i2++) {
            a();
            this.e.append("  ").append(aVarW == U8.a.LOOKUP ? interfaceC0967Er.i(i2) : interfaceC0967Er.i(0) + i2).append(": ").append(a((M7) listX.get(i2)));
        }
        a();
        this.e.append("  default: ").append(a(u8.U()));
    }

    public void a(O7 o7) {
        EnumC1812d20 enumC1812d20V = o7.V();
        int iU = o7.U();
        a();
        this.e.append(a(enumC1812d20V)).append("load").append(StringUtil.SPACE).append(iU);
    }

    public void a(R8 r8) {
        EnumC1812d20 enumC1812d20V = r8.V();
        int iT = r8.T();
        a();
        this.e.append(a(enumC1812d20V)).append("store").append(StringUtil.SPACE).append(iT);
    }

    public void a(C3260w7 c3260w7) {
        a();
        this.e.append("iinc ").append(c3260w7.U()).append(StringUtil.SPACE).append(c3260w7.T());
    }

    private static char a(EnumC1812d20 enumC1812d20) {
        int i2 = AbstractC2676o8.b[enumC1812d20.ordinal()];
        if (i2 == 1) {
            return 'i';
        }
        if (i2 == 2) {
            return 'f';
        }
        if (i2 == 3) {
            return 'l';
        }
        if (i2 == 4) {
            return Advice.OffsetMapping.ForOrigin.Renderer.ForDescriptor.SYMBOL;
        }
        if (i2 == 5) {
            return 'a';
        }
        throw new C3405y10("Unexpected type for prefix: " + enumC1812d20);
    }

    public static char a(EnumC2523mE enumC2523mE) {
        switch (enumC2523mE) {
            case OBJECT:
                return 'a';
            case BOOLEAN_OR_BYTE:
                return 'b';
            case CHAR:
                return 'c';
            case SHORT:
                return Advice.OffsetMapping.ForOrigin.Renderer.ForJavaSignature.SYMBOL;
            case INT:
                return 'i';
            case FLOAT:
                return 'f';
            case LONG:
                return 'l';
            case DOUBLE:
                return Advice.OffsetMapping.ForOrigin.Renderer.ForDescriptor.SYMBOL;
            default:
                throw new C3405y10("Unexpected member type for prefix: " + enumC2523mE);
        }
    }

    public void a(Z6 z6) {
        a();
        this.e.append("ldc ");
        this.e.append(z6.T().toString());
    }

    public void a(C1590a7 c1590a7) {
        a();
        this.e.append("ldc ");
        this.e.append(c1590a7.T().toString());
    }

    public final void a(K7 k7) {
        a();
        this.e.append("ret ").append(k7.U());
    }

    private String a(M7 m7) {
        MR mr = this.c;
        if (mr == null) {
            return "L?";
        }
        return "L" + mr.a(m7);
    }

    private void a() {
        c();
        b();
        this.e.append(this.a);
    }

    private void a(String str) {
        this.e.append(" ; ").append(str);
    }

    private void a(C0767v2 c0767v2) {
        if (!i && !c0767v2.M0() && !c0767v2.O0()) {
            throw new AssertionError();
        }
        if (c0767v2 == C0722p1.S5) {
            this.e.append("NULL");
        } else {
            this.e.append(this.f.a(c0767v2));
        }
    }

    private void a(C0684j2 c0684j2) {
        this.e.append(this.f.b(c0684j2));
    }

    private static String a(int i2) {
        return ZM.d[i2].toLowerCase();
    }
}
