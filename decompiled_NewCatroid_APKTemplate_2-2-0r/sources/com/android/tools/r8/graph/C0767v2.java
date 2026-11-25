package com.android.tools.r8.graph;

import com.android.tools.r8.AbstractC0402a;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.WH;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3970u0;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: renamed from: com.android.tools.r8.graph.v2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0767v2 extends AbstractC0744s2 implements V4 {
    public static final C0767v2[] g = new C0767v2[0];
    private static AbstractC1639am h = AbstractC1639am.a("$r8$backportedMethods$utility", "$r8$java8methods$utility", "$r8$twr$utility", "$-DC", "$$ServiceLoaderMethods", "com.android.tools.r8.GeneratedOutlineSupport", "-$$Nest$Constructor", "-$$Lambda$", "-$$LambdaGroup$");
    static final /* synthetic */ boolean i = true;
    public final C0759u2 e;
    private String f = null;

    C0767v2(C0759u2 c0759u2) {
        if (i || !c0759u2.toString().contains(".")) {
            this.e = c0759u2;
        } else {
            throw new AssertionError("Malformed descriptor: " + c0759u2.toString());
        }
    }

    public ClassReference B0() {
        return Reference.classFromDescriptor(b1());
    }

    @Override // com.android.tools.r8.graph.AbstractC0744s2
    public final C0767v2 C() {
        return this;
    }

    public final int C0() {
        if (!i && !V0()) {
            throw new AssertionError();
        }
        byte b = this.e.e[1];
        if (b == 70) {
            return 4;
        }
        if (b == 83) {
            return 2;
        }
        if (b != 90) {
            if (b == 73) {
                return 4;
            }
            if (b == 74) {
                return 8;
            }
            switch (b) {
                case 66:
                    break;
                case 67:
                    return 2;
                case 68:
                    return 8;
                default:
                    throw new C3405y10("Not array of primitives '" + this.e + FormatHelper.QUOTE);
            }
        }
        return 1;
    }

    public C0759u2 D0() {
        return this.e;
    }

    public String E0() {
        if (i || O0() || M0()) {
            return C3970u0.a(b1());
        }
        throw new AssertionError();
    }

    public String F0() {
        return W0() ? r0() : a(false);
    }

    public final int G0() {
        int i2 = 0;
        while (this.e.e[i2] == 91) {
            i2++;
        }
        return i2;
    }

    public String H0() {
        return a(true);
    }

    public String I0() {
        return C3970u0.p(a1());
    }

    public final int J0() {
        if (i || !Y0()) {
            return Z0() ? 2 : 1;
        }
        throw new AssertionError();
    }

    public final String K0() {
        if (i || O0()) {
            return C3970u0.v(b1());
        }
        throw new AssertionError();
    }

    public String L0() {
        return r0();
    }

    public boolean M0() {
        return this.e.w0() == '[';
    }

    public boolean N0() {
        return this.e.w0() == 'Z';
    }

    public boolean O0() {
        return this.e.w0() == 'L';
    }

    public final boolean P0() {
        return this.e.w0() == 'D';
    }

    public final boolean Q0() {
        return this.e.w0() == 'F';
    }

    public boolean R0() {
        return this.e.w0() == 'I';
    }

    public final boolean S0() {
        String strR0 = r0();
        Iterator it = h.iterator();
        while (it.hasNext()) {
            if (strR0.contains((String) it.next())) {
                return true;
            }
        }
        return false;
    }

    public boolean T0() {
        return this.e.w0() == 'J';
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    public final boolean U0() {
        boolean z = this.e.w0() == 'N';
        if (i || !z || this == C0722p1.S5) {
            return z;
        }
        throw new AssertionError();
    }

    public final boolean V0() {
        if (M0()) {
            return C3970u0.a((char) this.e.e[1]);
        }
        return false;
    }

    public final boolean W0() {
        return C3970u0.a(this.e.w0());
    }

    public boolean X0() {
        boolean z = M0() || O0() || U0();
        if (i || z != W0() || Y0()) {
            return z;
        }
        throw new AssertionError();
    }

    public boolean Y0() {
        return this.e.w0() == 'V';
    }

    public final boolean Z0() {
        return P0() || T0();
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final int a(com.android.tools.r8.utils.structural.x xVar, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (C0767v2) xVar);
    }

    public final String a1() {
        String strB1 = b1();
        if (i || (strB1.length() > 1 && strB1.charAt(0) == 'L' && strB1.charAt(strB1.length() - 1) == ';')) {
            return AbstractC0402a.a(strB1, 1, 1);
        }
        throw new AssertionError();
    }

    public final boolean b(C0702m c0702m) {
        C0729q2 c0729q2A;
        if (!O0() || (c0729q2A = C0729q2.a(c0702m.c(this))) == null) {
            return false;
        }
        if (!c0729q2A.isInterface() || c0702m.A().a(c0729q2A)) {
            return !((C3733i) c0702m.f()).b(c0729q2A);
        }
        return false;
    }

    public String b1() {
        return this.e.toString();
    }

    public AbstractC3472z00 c(C0702m<?> c0702m) {
        return AbstractC3472z00.a(this, WH.i(), c0702m);
    }

    public final char c1() {
        char cW0 = this.e.w0();
        if (cW0 == '[') {
            return 'L';
        }
        return cW0;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public String q0() {
        return b1();
    }

    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public String r0() {
        if (this.f == null) {
            if (C0722p1.a((AbstractC0594a1) this)) {
                this.f = this.e.toString();
            } else {
                this.f = C3970u0.b(b1());
            }
        }
        return this.f;
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    public final int s0() {
        return this.e.hashCode();
    }

    public String toString() {
        return r0();
    }

    @Override // com.android.tools.r8.graph.AbstractC0744s2
    public final C0767v2 w0() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0744s2
    public final int a(AbstractC0744s2 abstractC0744s2) {
        abstractC0744s2.getClass();
        if (abstractC0744s2 instanceof C0767v2) {
            return compareTo(abstractC0744s2.w0());
        }
        int iCompareTo = compareTo(abstractC0744s2.C());
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        return -1;
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    public final boolean c(Object obj) {
        if (obj instanceof C0767v2) {
            return this.e.equals(((C0767v2) obj).e);
        }
        return false;
    }

    public final C0767v2 c(String str, C0722p1 c0722p1) {
        if (i || O0()) {
            return c0722p1.e(C3970u0.b(b1(), str));
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this);
    }

    public final boolean a(InterfaceC0694k5 interfaceC0694k5, C0702m c0702m) {
        AbstractC0727q0 abstractC0727q0C = c0702m.c(this);
        return abstractC0727q0C == null || abstractC0727q0C.a(c0702m, interfaceC0694k5);
    }

    public final boolean a(C0767v2 c0767v2) {
        return H0().equals(c0767v2.H0());
    }

    public C0767v2 b(C0722p1 c0722p1) {
        int iG0 = G0();
        if (iG0 == 0) {
            return this;
        }
        C0759u2 c0759u2 = this.e;
        int i2 = c0759u2.d - iG0;
        byte[] bArr = c0759u2.e;
        return c0722p1.c(c0722p1.b(i2, Arrays.copyOfRange(bArr, iG0, bArr.length)));
    }

    @Override // com.android.tools.r8.graph.AbstractC0744s2
    public final Object a(Function function, Function function2, Function function3) {
        return function.apply(this);
    }

    @Override // com.android.tools.r8.graph.AbstractC0744s2
    public final void a(Consumer consumer, Consumer consumer2, Consumer consumer3) {
        consumer.accept(this);
    }

    @Override // com.android.tools.r8.graph.AbstractC0744s2
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l) {
        if (l.a(this)) {
            C0759u2 c0759u2C = c0702m.z().c(this);
            c0759u2C.getClass();
            l.a(c0759u2C);
        }
    }

    @Override // com.android.tools.r8.graph.I3
    public final int a(C0645e5 c0645e5) {
        return c0645e5.b(this);
    }

    public final C0767v2 a(C0722p1 c0722p1, C0767v2 c0767v2) {
        boolean z = i;
        if (!z && !M0()) {
            throw new AssertionError();
        }
        if (!z && c0767v2.M0()) {
            throw new AssertionError();
        }
        int iG0 = G0();
        C0759u2 c0759u2 = c0767v2.e;
        byte[] bArr = new byte[c0759u2.e.length + iG0];
        Arrays.fill(bArr, 0, iG0, (byte) 91);
        byte[] bArr2 = c0759u2.e;
        System.arraycopy(bArr2, 0, bArr, iG0, bArr2.length);
        return c0722p1.c(c0722p1.b(c0759u2.d + iG0, bArr));
    }

    public final C0767v2 b(String str, C0722p1 c0722p1) {
        if (!i && !O0()) {
            throw new AssertionError();
        }
        return c0722p1.e((str.isEmpty() ? "L" : "L" + str + "/") + C3970u0.v(b1()) + ";");
    }

    public final C0767v2 a(String str, C0722p1 c0722p1) {
        if (!i && !O0()) {
            throw new AssertionError();
        }
        return c0722p1.e(AbstractC0402a.a(b1(), 1, 0) + str + ";");
    }

    public final C0767v2 a(C0722p1 c0722p1) {
        if (!i && !M0()) {
            throw new AssertionError();
        }
        C0759u2 c0759u2 = this.e;
        int i2 = c0759u2.d - 1;
        byte[] bArr = c0759u2.e;
        return c0722p1.c(c0722p1.b(i2, Arrays.copyOfRange(bArr, 1, bArr.length)));
    }

    private String a(boolean z) {
        if (!i && !O0()) {
            throw new AssertionError();
        }
        String strB1 = b1();
        int iLastIndexOf = strB1.lastIndexOf(47);
        if (iLastIndexOf == -1) {
            if (z) {
                return "";
            }
            return AbstractC0402a.a(strB1, 1, 1);
        }
        if (z) {
            return strB1.substring(1, iLastIndexOf);
        }
        return AbstractC0402a.a(strB1, 1, iLastIndexOf + 1);
    }
}
