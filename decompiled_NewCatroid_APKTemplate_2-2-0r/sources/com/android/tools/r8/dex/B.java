package com.android.tools.r8.dex;

import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.dex.code.B1;
import com.android.tools.r8.dex.code.D1;
import com.android.tools.r8.graph.A2;
import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0720p;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.B0;
import com.android.tools.r8.graph.B2;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0649f0;
import com.android.tools.r8.graph.C0653f2;
import com.android.tools.r8.graph.C0656f5;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0660g2;
import com.android.tools.r8.graph.C0663g5;
import com.android.tools.r8.graph.C0667h2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0706m3;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0772w0;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.graph.C2;
import com.android.tools.r8.graph.D2;
import com.android.tools.r8.graph.E2;
import com.android.tools.r8.graph.EnumC0691k2;
import com.android.tools.r8.graph.F2;
import com.android.tools.r8.graph.G2;
import com.android.tools.r8.graph.H2;
import com.android.tools.r8.graph.I2;
import com.android.tools.r8.graph.J0;
import com.android.tools.r8.graph.K0;
import com.android.tools.r8.graph.Q2;
import com.android.tools.r8.graph.R0;
import com.android.tools.r8.graph.S0;
import com.android.tools.r8.graph.T0;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.internal.C1303Qq;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C1525Yd;
import com.android.tools.r8.internal.C1684bJ;
import com.android.tools.r8.internal.C3085to;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.V5;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.P0;
import com.android.tools.r8.utils.Q1;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ShortBuffer;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.jf.dexlib2.dexbacked.raw.ItemType;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class B<T extends AbstractC0727q0> {
    static final /* synthetic */ boolean n = true;
    private final Origin a;
    private C b;
    private final ArrayList c;
    private final int d;
    private int[] e;
    private final com.android.tools.r8.graph.I f;
    private final A1 g;
    private C1684bJ h;
    private C0656f5 i;
    private C1303Qq j;
    private C1303Qq k;
    private C1303Qq l;
    private final C0722p1 m;

    public B(C c, com.android.tools.r8.graph.I i, A1 a1) {
        this(c, i, a1, 0, null);
    }

    public static List<D> a(Path path) throws IOException {
        return a(new PathOrigin(path), Files.newInputStream(path, new OpenOption[0]));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:11:0x0033  */
    public C0658g0 b() {
        int iE = this.b.e();
        int[] iArr = new int[iE];
        for (int i = 0; i < iE; i++) {
            iArr[i] = this.b.e();
        }
        C0633d0[] c0633d0Arr = new C0633d0[iE];
        int i2 = 0;
        for (int i3 = 0; i3 < iE; i3++) {
            C0633d0 c0633d0A = a(iArr[i3]);
            if (c0633d0A.a == 0) {
                if (C0633d0.a(this.g, c0633d0A.b.a)) {
                    c0633d0Arr[i2] = c0633d0A;
                    i2++;
                }
            } else {
                c0633d0Arr[i2] = c0633d0A;
                i2++;
            }
        }
        if (i2 < iE) {
            C0633d0[] c0633d0Arr2 = new C0633d0[i2];
            System.arraycopy(c0633d0Arr, 0, c0633d0Arr2, 0, i2);
            c0633d0Arr = c0633d0Arr2;
        }
        C0767v2 c0767v2B = C0658g0.b(c0633d0Arr);
        if (c0767v2B == null) {
            return C0658g0.a(c0633d0Arr);
        }
        throw new C1367Tb("Multiple annotations of type `" + c0767v2B.r0() + "`");
    }

    private C0663g5 c(int i) {
        return (C0663g5) a(i, new Supplier() { // from class: com.android.tools.r8.dex.B$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.c();
            }
        });
    }

    private C0587y d(int i) {
        return (C0587y) (i == 0 ? new Supplier() { // from class: com.android.tools.r8.dex.B$$ExternalSyntheticLambda12
            @Override // java.util.function.Supplier
            public final Object get() {
                return C0587y.a();
            }
        }.get() : a(i, new Supplier() { // from class: com.android.tools.r8.dex.B$$ExternalSyntheticLambda11
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.d();
            }
        }));
    }

    private S0 e(int i) {
        return (S0) a(i, new Supplier() { // from class: com.android.tools.r8.dex.B$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.g();
            }
        });
    }

    private R0 f() {
        C c = this.b;
        c.getClass();
        int iB = Q1.b(c);
        C c2 = this.b;
        c2.getClass();
        int iB2 = Q1.b(c2);
        C0649f0[] c0649f0Arr = new C0649f0[iB2];
        for (int i = 0; i < iB2; i++) {
            C c3 = this.b;
            c3.getClass();
            int iB3 = Q1.b(c3);
            c0649f0Arr[i] = new C0649f0(this.i.e(iB3), h());
        }
        return new R0(this.i.f(iB), c0649f0Arr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public S0 g() {
        C c = this.b;
        c.getClass();
        int iB = Q1.b(c);
        B2[] b2Arr = new B2[iB];
        for (int i = 0; i < iB; i++) {
            b2Arr[i] = h();
        }
        return new S0(b2Arr);
    }

    private C0800x2 h(int i) {
        return i == 0 ? C0800x2.s0() : (C0800x2) a(i, new Supplier() { // from class: com.android.tools.r8.dex.B$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.k();
            }
        });
    }

    private ArrayList i() {
        this.b.b(this.b.a(this.d + 52));
        int iE = this.b.e();
        ArrayList arrayList = new ArrayList(iE);
        int i = 0;
        for (int i2 = 0; i2 < iE; i2++) {
            int iH = this.b.h();
            int iH2 = this.b.h();
            int iE2 = this.b.e();
            int iE3 = this.b.e();
            if (iE3 + iE2 > this.b.a()) {
                throw new C1367Tb("The dex file had an offset + size that pointed past the end of the dex file.\nSection type: " + D.b(iH) + "\nSection offset: " + iE3 + "\nSection size: " + iE2 + "\nFile size: " + this.b.a(), this.a);
            }
            arrayList.add(new D(iH, iH2, iE2, iE3));
        }
        while (true) {
            int i3 = iE - 1;
            if (i >= i3) {
                ((D) arrayList.get(i3)).a(this.b.a());
                return arrayList;
            }
            D d = (D) arrayList.get(i);
            i++;
            d.a(((D) arrayList.get(i)).c);
        }
    }

    private void j() {
        D dF = f(1);
        int i = dF.b;
        this.e = new int[i];
        if (i == 0) {
            return;
        }
        this.b.b(dF.c);
        for (int i2 = 0; i2 < dF.b; i2++) {
            this.e[i2] = this.b.e();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0800x2 k() {
        int iE = this.b.e();
        C0767v2[] c0767v2Arr = new C0767v2[iE];
        for (int i = 0; i < iE; i++) {
            c0767v2Arr[i] = this.i.f(this.b.h());
        }
        return new C0800x2(c0767v2Arr);
    }

    final void l() {
        C0721p0 c0721p0A;
        AbstractC0642e2 abstractC0642e2A;
        C0699l2 c0699l2A;
        C0684j2 c0684j2A;
        C0737r2 c0737r2A;
        Y0 y0A;
        byte b;
        this.i.m(this.e.length);
        int i = 0;
        while (true) {
            int[] iArr = this.e;
            if (i >= iArr.length) {
                break;
            }
            C0656f5 c0656f5 = this.i;
            this.b.b(iArr[i]);
            C c = this.b;
            c.getClass();
            int iB = Q1.b(c);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            do {
                b = this.b.b();
                byteArrayOutputStream.write(b);
            } while (b != 0);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            c0656f5.a(i, U.a(byteArray) ? this.m.a(iB, byteArray) : this.m.b(iB, byteArray));
            i++;
        }
        C0576n c0576n = new C0576n();
        for (int length = this.e.length - 1; length >= 0; length--) {
            C0759u2 c0759u2E = this.i.e(length);
            if (C0576n.a(c0759u2E)) {
                break;
            }
            c0576n.b(c0759u2E);
        }
        this.h = c0576n.a();
        D dF = f(2);
        if (!n) {
            a(dF);
        }
        AbstractC0720p abstractC0720pA = AbstractC0720p.a(this.g);
        this.i.n(dF.b);
        int i2 = 0;
        while (true) {
            C0767v2 c0767v2C = null;
            if (i2 >= dF.b) {
                break;
            }
            D dF2 = f(2);
            if (i2 < dF2.b) {
                c0767v2C = this.m.c(this.i.e(this.b.a((i2 * 4) + dF2.c)));
            }
            this.i.a(i2, abstractC0720pA.b(c0767v2C));
            i2++;
        }
        D dF3 = f(4);
        if (!n && dF3.b >= 2) {
            int i3 = dF3.c;
            this.b.b(i3);
            int iH = this.b.h();
            int iH2 = this.b.h();
            int iE = this.b.e();
            int i4 = 1;
            while (i4 < dF3.b) {
                this.b.b((i4 * 8) + i3);
                int iH3 = this.b.h();
                int iH4 = this.b.h();
                int iE2 = this.b.e();
                boolean z = iH3 != iH ? iH3 > iH : !(iE2 != iE ? iE2 <= iE : iH4 <= iH2);
                if (!n && !z) {
                    throw new AssertionError(String.format("Out-of-order field ids (field #%s: `%s`, field #%s: `%s`)", Integer.valueOf(i4 - 1), this.m.a(this.i.f(iH), this.i.f(iH2), this.i.e(iE)).r0(), Integer.valueOf(i4), this.m.a(this.i.f(iH3), this.i.f(iH4), this.i.e(iE2)).r0()));
                }
                i4++;
                iH = iH3;
                iH2 = iH4;
                iE = iE2;
            }
        }
        this.i.i(dF3.b);
        for (int i5 = 0; i5 < dF3.b; i5++) {
            C0656f5 c0656f6 = this.i;
            D dF4 = f(4);
            if (i5 >= dF4.b) {
                y0A = null;
            } else {
                this.b.b((i5 * 8) + dF4.c);
                y0A = this.m.a(this.i.f(this.b.h()), this.i.f(this.b.h()), this.i.e(this.b.e()));
            }
            c0656f6.a(i5, y0A);
        }
        D dF5 = f(3);
        this.i.l(dF5.b);
        for (int i6 = 0; i6 < dF5.b; i6++) {
            C0656f5 c0656f7 = this.i;
            D dF6 = f(3);
            if (i6 >= dF6.b) {
                c0737r2A = null;
            } else {
                this.b.b((i6 * 12) + dF6.c);
                c0737r2A = this.m.a(this.i.e(this.b.e()), this.i.f(this.b.e()), h(this.b.e()));
            }
            c0656f7.a(i6, c0737r2A);
        }
        D dF7 = f(5);
        if (!n && dF7.b >= 2) {
            int i7 = dF7.c;
            this.b.b(i7);
            int iH5 = this.b.h();
            int iH6 = this.b.h();
            int iE3 = this.b.e();
            int i8 = 1;
            while (i8 < dF7.b) {
                this.b.b((i8 * 8) + i7);
                int iH7 = this.b.h();
                int iH8 = this.b.h();
                int iE4 = this.b.e();
                boolean z2 = iH7 != iH5 ? iH7 > iH5 : !(iE4 != iE3 ? iE4 <= iE3 : iH8 <= iH6);
                if (!n && !z2) {
                    throw new AssertionError(String.format("Out-of-order method ids (method #%s: `%s`, method #%s: `%s`)", Integer.valueOf(i8 - 1), this.m.a(this.i.f(iH5), this.i.d(iH6), this.i.e(iE3)).r0(), Integer.valueOf(i8), this.m.a(this.i.f(iH7), this.i.d(iH8), this.i.e(iE4)).r0()));
                }
                i8++;
                iH5 = iH7;
                iH6 = iH8;
                iE3 = iE4;
            }
        }
        this.i.k(dF7.b);
        for (int i9 = 0; i9 < dF7.b; i9++) {
            C0656f5 c0656f8 = this.i;
            D dF8 = f(5);
            if (i9 >= dF8.b) {
                c0684j2A = null;
            } else {
                this.b.b((i9 * 8) + dF8.c);
                c0684j2A = this.m.a(this.i.f(this.b.h()), this.i.d(this.b.h()), this.i.e(this.b.e()));
            }
            c0656f8.a(i9, c0684j2A);
        }
        D dF9 = f(8);
        this.i.j(dF9.b);
        for (int i10 = 0; i10 < dF9.b; i10++) {
            C0656f5 c0656f9 = this.i;
            D dF10 = f(8);
            if (i10 >= dF10.b) {
                c0699l2A = null;
            } else {
                this.b.b((i10 * 8) + dF10.c);
                EnumC0691k2 enumC0691k2C = EnumC0691k2.c(this.b.h());
                this.b.h();
                int iH9 = this.b.h();
                switch (enumC0691k2C.ordinal()) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        abstractC0642e2A = this.i.a(iH9);
                        break;
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        abstractC0642e2A = this.i.b(iH9);
                        break;
                    default:
                        throw new AssertionError("Method handle type unsupported in a dex file.");
                }
                this.b.h();
                c0699l2A = this.m.a(enumC0691k2C, abstractC0642e2A, enumC0691k2C == EnumC0691k2.j, (C0684j2) null);
            }
            c0656f9.a(i10, c0699l2A);
        }
        D dF11 = f(7);
        this.i.g(dF11.b);
        for (int i11 = 0; i11 < dF11.b; i11++) {
            C0656f5 c0656f10 = this.i;
            D dF12 = f(7);
            if (i11 >= dF12.b) {
                c0721p0A = null;
            } else {
                B2[] b2Arr = e(this.b.a((i11 * 4) + dF12.c)).a;
                boolean z3 = n;
                if (!z3) {
                    B2 b2 = b2Arr[0];
                    b2.getClass();
                    if (!(b2 instanceof G2)) {
                        throw new AssertionError();
                    }
                }
                if (!z3 && !b2Arr[1].e1()) {
                    throw new AssertionError();
                }
                if (!z3) {
                    B2 b3 = b2Arr[2];
                    b3.getClass();
                    if (!(b3 instanceof H2)) {
                        throw new AssertionError();
                    }
                }
                C0722p1 c0722p1 = this.m;
                C0759u2 c0759u2 = (C0759u2) b2Arr[1].K0().b;
                C0737r2 c0737r2 = (C0737r2) b2Arr[2].H0().b;
                C0699l2 c0699l2 = (C0699l2) b2Arr[0].G0().b;
                List listAsList = Arrays.asList((B2[]) Arrays.copyOfRange(b2Arr, 3, b2Arr.length));
                c0722p1.getClass();
                c0721p0A = C0722p1.a(c0759u2, c0737r2, c0699l2, listAsList);
            }
            c0656f10.a(i11, c0721p0A);
        }
    }

    public B(C c, com.android.tools.r8.graph.I i, A1 a1, int i2, B b) {
        this.i = new C0656f5();
        this.j = new C1303Qq();
        this.k = new C1303Qq();
        this.l = new C1303Qq();
        if (!n && c.d() == null) {
            throw new AssertionError();
        }
        this.a = c.d();
        this.b = c;
        this.d = i2;
        this.m = a1.a;
        c.k();
        this.c = i();
        if (b == null) {
            j();
        } else {
            this.e = b.e;
        }
        this.f = i;
        this.g = a1;
    }

    public static ArrayList a(Origin origin, InputStream inputStream) {
        return new B(new C(origin, V5.a(inputStream)), com.android.tools.r8.graph.I.c, new A1()).c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0663g5 c() {
        int iE = this.b.e();
        int[] iArr = new int[iE];
        for (int i = 0; i < iE; i++) {
            iArr[i] = this.b.e();
        }
        C0658g0[] c0658g0Arr = new C0658g0[iE];
        for (int i2 = 0; i2 < iE; i2++) {
            c0658g0Arr[i2] = b(iArr[i2]);
        }
        return C0663g5.a(c0658g0Arr, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public J0 e() {
        return a(true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private B2 h() {
        E2 e2;
        byte b = this.b.b();
        int i = (b & 255) >> 5;
        boolean z = b & 31;
        if (z == 0) {
            e2 = E2.b;
        } else if (z == 6) {
            e2 = E2.f;
        } else if (z == 2) {
            e2 = E2.c;
        } else if (z == 3) {
            e2 = E2.d;
        } else if (z == 4) {
            e2 = E2.e;
        } else if (z == 16) {
            e2 = E2.g;
        } else if (z != 17) {
            switch (z ? 1 : 0) {
                case 21:
                    e2 = E2.i;
                    break;
                case 22:
                    e2 = E2.j;
                    break;
                case 23:
                    e2 = E2.k;
                    break;
                case 24:
                    e2 = E2.l;
                    break;
                case 25:
                    e2 = E2.m;
                    break;
                case 26:
                    e2 = E2.n;
                    break;
                case 27:
                    e2 = E2.o;
                    break;
                case 28:
                    e2 = E2.p;
                    break;
                case 29:
                    e2 = E2.q;
                    break;
                case 30:
                    e2 = E2.r;
                    break;
                case 31:
                    e2 = E2.s;
                    break;
                default:
                    E2 e3 = E2.b;
                    throw new C3405y10();
            }
        } else {
            e2 = E2.h;
        }
        switch (e2.ordinal()) {
            case 0:
                if (n || i == 0) {
                    return B2.c.a((byte) P0.c(this.b, 1));
                }
                throw new AssertionError();
            case 1:
                return B2.i.a((short) P0.c(this.b, i + 1));
            case 2:
                return B2.d.a((char) P0.d(this.b, i + 1));
            case 3:
                return B2.g.j((int) P0.c(this.b, i + 1));
            case 4:
                return B2.h.a(P0.c(this.b, i + 1));
            case 5:
                return B2.f.a(P0.b(this.b, i + 1));
            case 6:
                return B2.e.a(P0.a(this.b, i + 1));
            case 7:
                return new H2(this.i.d((int) P0.d(this.b, i + 1)));
            case 8:
                return new G2(this.i.c((int) P0.d(this.b, i + 1)));
            case 9:
                return new B2.j(this.i.e((int) P0.d(this.b, i + 1)));
            case 10:
                return new B2.k(this.i.f((int) P0.d(this.b, i + 1)));
            case 11:
                Y0 y0A = this.i.a((int) P0.d(this.b, i + 1));
                a(y0A.f);
                return new D2(y0A);
            case 12:
                C0684j2 c0684j2B = this.i.b((int) P0.d(this.b, i + 1));
                a(c0684j2B.f);
                return new F2(c0684j2B);
            case 13:
                return new C2(this.i.a((int) P0.d(this.b, i + 1)));
            case 14:
                if (!n && i != 0) {
                    throw new AssertionError();
                }
                C c = this.b;
                c.getClass();
                int iB = Q1.b(c);
                B2[] b2Arr = new B2[iB];
                for (int i2 = 0; i2 < iB; i2++) {
                    b2Arr[i2] = h();
                }
                return new B2.a(b2Arr);
            case 15:
                if (n || i == 0) {
                    return new A2(f());
                }
                throw new AssertionError();
            case 16:
                if (n || i == 0) {
                    return I2.b;
                }
                throw new AssertionError();
            case 17:
                return B2.b.a(i != 0);
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    private void a(C0759u2 c0759u2) {
        if (this.g.b0() || c0759u2.c(this.g.B0())) {
            return;
        }
        throw new C1367Tb("Space characters in SimpleName '" + c0759u2.B0() + "' are not allowed prior to DEX version 040");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0587y d() {
        C0653f2[] c0653f2Arr;
        C0660g2[] c0660g2Arr;
        int iE = this.b.e();
        int iE2 = this.b.e();
        int iE3 = this.b.e();
        int iE4 = this.b.e();
        C0667h2[] c0667h2Arr = null;
        if (iE2 == 0) {
            c0653f2Arr = null;
        } else {
            int[] iArr = new int[iE2];
            int[] iArr2 = new int[iE2];
            for (int i = 0; i < iE2; i++) {
                iArr[i] = this.b.e();
                iArr2[i] = this.b.e();
            }
            int i2 = this.b.i();
            c0653f2Arr = new C0653f2[iE2];
            for (int i3 = 0; i3 < iE2; i3++) {
                c0653f2Arr[i3] = new C0653f2(this.i.a(iArr[i3]), b(iArr2[i3]));
            }
            this.b.b(i2);
        }
        if (iE3 == 0) {
            c0660g2Arr = null;
        } else {
            int[] iArr3 = new int[iE3];
            int[] iArr4 = new int[iE3];
            for (int i4 = 0; i4 < iE3; i4++) {
                iArr3[i4] = this.b.e();
                iArr4[i4] = this.b.e();
            }
            int i5 = this.b.i();
            c0660g2Arr = new C0660g2[iE3];
            for (int i6 = 0; i6 < iE3; i6++) {
                c0660g2Arr[i6] = new C0660g2(this.i.b(iArr3[i6]), b(iArr4[i6]));
            }
            this.b.b(i5);
        }
        if (iE4 != 0) {
            int[] iArr5 = new int[iE4];
            int[] iArr6 = new int[iE4];
            for (int i7 = 0; i7 < iE4; i7++) {
                iArr5[i7] = this.b.e();
                iArr6[i7] = this.b.e();
            }
            int i8 = this.b.i();
            C0667h2[] c0667h2Arr2 = new C0667h2[iE4];
            for (int i9 = 0; i9 < iE4; i9++) {
                C0684j2 c0684j2B = this.i.b(iArr5[i9]);
                c0667h2Arr2[i9] = new C0667h2(c0684j2B, c(iArr6[i9]).l(c0684j2B.h.f.size()));
            }
            this.b.b(i8);
            c0667h2Arr = c0667h2Arr2;
        }
        return new C0587y(b(iE), c0653f2Arr, c0660g2Arr, c0667h2Arr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public J0.a g(int i) {
        this.b.b(i);
        return a(false).t0();
    }

    private Object a(int i, Supplier supplier) {
        if (i == 0) {
            return null;
        }
        Object obj = this.k.get(i);
        if (obj != null) {
            return obj;
        }
        this.b.b(i);
        Object obj2 = supplier.get();
        this.k.a(i, obj2);
        if (n || this.k.get(i) == obj2) {
            return obj2;
        }
        throw new AssertionError();
    }

    private D f(int i) {
        for (D d : this.c) {
            if (d.a == i) {
                return d;
            }
        }
        return new D(i, 0, 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0633d0 a() {
        return new C0633d0(this.b.b(), f());
    }

    private C0633d0 a(int i) {
        return (C0633d0) a(i, new Supplier() { // from class: com.android.tools.r8.dex.B$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.a();
            }
        });
    }

    private J0 a(int i, B1[] b1Arr) {
        J0 j0 = (J0) a(i, new Supplier() { // from class: com.android.tools.r8.dex.B$$ExternalSyntheticLambda9
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.e();
            }
        });
        if (j0 != null && j0.z0()) {
            if (C1525Yd.a(b1Arr).u() > j0.u0().B0()) {
                return (J0) this.l.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: com.android.tools.r8.dex.B$$ExternalSyntheticLambda10
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return this.f$0.g(((Integer) obj).intValue());
                    }
                });
            }
        }
        return j0;
    }

    private C0658g0 b(int i) {
        Object objA;
        Supplier supplier = new Supplier() { // from class: com.android.tools.r8.dex.B$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.b();
            }
        };
        B$$ExternalSyntheticLambda0 b$$ExternalSyntheticLambda0 = new B$$ExternalSyntheticLambda0();
        if (i == 0) {
            objA = b$$ExternalSyntheticLambda0.get();
        } else {
            objA = a(i, supplier);
        }
        return (C0658g0) objA;
    }

    /* JADX WARN: Code duplicated, block: B:83:0x0199 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:89:0x0056 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    private J0 a(boolean z) {
        C c = this.b;
        c.getClass();
        int iB = Q1.b(c);
        boolean zEquals = z && iB == 1;
        C c2 = this.b;
        c2.getClass();
        int iB2 = Q1.b(c2);
        C0759u2[] c0759u2Arr = new C0759u2[iB2];
        for (int i = 0; i < iB2; i++) {
            int iG = this.b.g();
            if (iG != -1) {
                c0759u2Arr[i] = this.i.e(iG);
                zEquals = false;
            }
        }
        ArrayList arrayList = new ArrayList();
        int i2 = this.b.b.c().get() & 255;
        if (!AbstractC0575m.c && i2 < 0) {
            throw new AssertionError();
        }
        while (i2 != 0) {
            switch (i2) {
                case 1:
                    C0722p1 c0722p1 = this.m;
                    C c3 = this.b;
                    c3.getClass();
                    arrayList.add(c0722p1.b(Q1.b(c3)));
                    zEquals = false;
                    i2 = this.b.b.c().get() & 255;
                    if (AbstractC0575m.c && i2 < 0) {
                        throw new AssertionError();
                    }
                    break;
                    break;
                case 2:
                    C0722p1 c0722p2 = this.m;
                    C c4 = this.b;
                    c4.getClass();
                    arrayList.add(c0722p2.a(Q1.a(c4)));
                    zEquals = false;
                    i2 = this.b.b.c().get() & 255;
                    if (AbstractC0575m.c) {
                    }
                    break;
                case 3:
                    C c5 = this.b;
                    c5.getClass();
                    int iB3 = Q1.b(c5);
                    int iG2 = this.b.g();
                    int iG3 = this.b.g();
                    arrayList.add(new B0.d(iB3, iG2 == -1 ? null : this.i.e(iG2), iG3 == -1 ? null : this.i.f(iG3), null));
                    zEquals = false;
                    i2 = this.b.b.c().get() & 255;
                    if (AbstractC0575m.c) {
                    }
                    break;
                case 4:
                    C c6 = this.b;
                    c6.getClass();
                    int iB4 = Q1.b(c6);
                    int iG4 = this.b.g();
                    int iG5 = this.b.g();
                    int iG6 = this.b.g();
                    arrayList.add(new B0.d(iB4, iG4 == -1 ? null : this.i.e(iG4), iG5 == -1 ? null : this.i.f(iG5), iG6 != -1 ? this.i.e(iG6) : null));
                    zEquals = false;
                    i2 = this.b.b.c().get() & 255;
                    if (AbstractC0575m.c) {
                    }
                    break;
                case 5:
                    C0722p1 c0722p3 = this.m;
                    C c7 = this.b;
                    c7.getClass();
                    arrayList.add(c0722p3.d(Q1.b(c7)));
                    zEquals = false;
                    i2 = this.b.b.c().get() & 255;
                    if (AbstractC0575m.c) {
                    }
                    break;
                case 6:
                    C0722p1 c0722p4 = this.m;
                    C c8 = this.b;
                    c8.getClass();
                    arrayList.add(c0722p4.e(Q1.b(c8)));
                    zEquals = false;
                    i2 = this.b.b.c().get() & 255;
                    if (AbstractC0575m.c) {
                    }
                    break;
                case 7:
                    arrayList.add(this.m.f());
                    zEquals = false;
                    i2 = this.b.b.c().get() & 255;
                    if (AbstractC0575m.c) {
                    }
                    break;
                case 8:
                    arrayList.add(this.m.e());
                    zEquals = false;
                    i2 = this.b.b.c().get() & 255;
                    if (AbstractC0575m.c) {
                    }
                    break;
                case 9:
                    int iG7 = this.b.g();
                    C0759u2 c0759u2E = iG7 != -1 ? this.i.e(iG7) : null;
                    if (this.g.K) {
                        arrayList.add(this.m.a(c0759u2E));
                    }
                    zEquals = false;
                    i2 = this.b.b.c().get() & 255;
                    if (AbstractC0575m.c) {
                    }
                    break;
                default:
                    if (!n && (i2 < 10 || i2 > 255)) {
                        throw new AssertionError();
                    }
                    B0.b bVarC = this.m.c(i2);
                    arrayList.add(bVarC);
                    if (zEquals) {
                        if (arrayList.size() == 1) {
                            zEquals = bVarC.equals(this.m.r);
                        } else {
                            zEquals = bVarC.equals(this.m.s);
                        }
                    }
                    i2 = this.b.b.c().get() & 255;
                    if (AbstractC0575m.c) {
                    }
                    break;
                    break;
            }
        }
        if (zEquals) {
            return new K0(iB2, arrayList.size() - 1);
        }
        return new J0.a(iB, c0759u2Arr, (B0[]) arrayList.toArray(B0.a));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private T0[] a(int i, C0653f2[] c0653f2Arr, B2[] b2Arr) {
        String strA;
        String str;
        T0[] t0Arr = new T0[i];
        B2 b2 = null;
        A a = new A(c0653f2Arr, new B$$ExternalSyntheticLambda0());
        int i2 = 0;
        int iB = 0;
        while (i2 < i) {
            C c = this.b;
            c.getClass();
            iB += Q1.b(c);
            Y0 y0A = this.i.a(iB);
            C c2 = this.b;
            c2.getClass();
            Q2 q2J = Q2.j(Q1.b(c2));
            B2 b3 = (!q2J.o() || b2Arr == null || i2 >= b2Arr.length) ? b2 : b2Arr[i2];
            C0658g0 c0658g0B = (C0658g0) a.a(y0A);
            C0706m3.e eVarP = C0706m3.e.p();
            if (!this.g.o) {
                C0722p1 c0722p1 = this.m;
                C0633d0[] c0633d0Arr = C0633d0.c;
                C0633d0 c0633d0A = c0658g0B.a(c0722p1.W4);
                if (c0633d0A == null) {
                    str = b2;
                } else {
                    strA = C0633d0.a(c0633d0A);
                }
                if (str != 0) {
                    str = strA;
                    c0658g0B = c0658g0B.b(this.m.W4);
                    eVarP = C0706m3.b(y0A.f.toString(), str, this.a, this.m, this.g.e);
                } else {
                    str = strA;
                }
            }
            t0Arr[i2] = T0.Q0().a(y0A).a(q2J).a(eVarP).a(c0658g0B).a(b3).d().a();
            i2++;
            b2 = null;
        }
        return t0Arr;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0048  */
    private W0[] a(int i, C0660g2[] c0660g2Arr, C0667h2[] c0667h2Arr, boolean z) {
        int i2;
        C0725p4 c0725p4;
        C0772w0 c0772w0;
        C0772w0.b[] bVarArr;
        int i3;
        int i4;
        int i5;
        int i6;
        C0772w0.b[] bVarArr2;
        int i7;
        int iB;
        int i8 = i;
        W0[] w0Arr = new W0[i8];
        A a = new A(c0660g2Arr, new B$$ExternalSyntheticLambda0());
        A a2 = new A(c0667h2Arr, new Supplier() { // from class: com.android.tools.r8.dex.B$$ExternalSyntheticLambda6
            @Override // java.util.function.Supplier
            public final Object get() {
                return C0663g5.t0();
            }
        });
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        while (i10 < i8) {
            C c = this.b;
            c.getClass();
            int iB2 = i11 + Q1.b(c);
            C c2 = this.b;
            c2.getClass();
            C0725p4 c0725p4G = C0725p4.g(Q1.b(c2));
            C c3 = this.b;
            c3.getClass();
            int iB3 = Q1.b(c3);
            if (z) {
                w0Arr = w0Arr;
                a = a;
                a2 = a2;
                i10 = i10;
                i2 = iB2;
                c0725p4 = c0725p4G;
                c0772w0 = null;
            } else {
                if (iB3 != 0) {
                    if (this.j == null) {
                        this.j = new C1303Qq(16);
                    }
                    if (this.f == com.android.tools.r8.graph.I.e || f(ItemType.CODE_ITEM).b == 0 || this.j.a(iB3)) {
                        a = a;
                        a2 = a2;
                        i2 = iB2;
                        c0725p4 = c0725p4G;
                        i7 = iB3;
                    } else {
                        int i12 = this.b.i();
                        this.b.b(iB3);
                        C c4 = this.b;
                        c4.getClass();
                        boolean z2 = AbstractC0575m.c;
                        int iPosition = c4.b.c().position();
                        c4.b.c(iPosition + ((4 - (iPosition % 4)) & 3));
                        int iH = this.b.h();
                        int iH2 = this.b.h();
                        int iH3 = this.b.h();
                        int iH4 = this.b.h();
                        int iE = this.b.e();
                        int iE2 = this.b.e();
                        short[] sArr = new short[iE2];
                        C0772w0.a[] aVarArr = new C0772w0.a[iH4];
                        C0772w0.b[] bVarArr3 = new C0772w0.b[i9];
                        if (iE2 != 0) {
                            while (i9 < iE2) {
                                sArr[i9] = this.b.b.g();
                                i9++;
                                bVarArr3 = bVarArr3;
                            }
                            bVarArr = bVarArr3;
                            if (iE2 % 2 != 0) {
                                this.b.h();
                            }
                            if (iH4 > 0) {
                                C3085to c3085to = new C3085to();
                                int i13 = 0;
                                while (i13 < iH4) {
                                    aVarArr[i13] = new C0772w0.a(this.b.e(), this.b.h(), this.b.h());
                                    i13++;
                                    a2 = a2;
                                    a = a;
                                    c0725p4G = c0725p4G;
                                    iB2 = iB2;
                                }
                                a = a;
                                a2 = a2;
                                i2 = iB2;
                                c0725p4 = c0725p4G;
                                int i14 = this.b.i();
                                C c5 = this.b;
                                c5.getClass();
                                int iB4 = Q1.b(c5);
                                C0772w0.b[] bVarArr4 = new C0772w0.b[iB4];
                                int i15 = 0;
                                while (i15 < iB4) {
                                    c3085to.b(this.b.i() - i14, i15);
                                    C c6 = this.b;
                                    c6.getClass();
                                    int iA = Q1.a(c6);
                                    int i16 = i14;
                                    int iAbs = Math.abs(iA);
                                    int i17 = iB4;
                                    C0772w0.b.a[] aVarArr2 = new C0772w0.b.a[iAbs];
                                    int i18 = i12;
                                    int i19 = 0;
                                    while (i19 < iAbs) {
                                        int i20 = iAbs;
                                        C c7 = this.b;
                                        c7.getClass();
                                        int iB5 = Q1.b(c7);
                                        int i21 = iB3;
                                        C c8 = this.b;
                                        c8.getClass();
                                        aVarArr2[i19] = new C0772w0.b.a(Q1.b(c8), this.i.f(iB5));
                                        i19++;
                                        iAbs = i20;
                                        iH2 = iH2;
                                        iB3 = i21;
                                        iH = iH;
                                    }
                                    int i22 = iB3;
                                    int i23 = iH;
                                    int i24 = iH2;
                                    if (iA <= 0) {
                                        C c9 = this.b;
                                        c9.getClass();
                                        iB = Q1.b(c9);
                                    } else {
                                        iB = -1;
                                    }
                                    bVarArr4[i15] = new C0772w0.b(aVarArr2, iB);
                                    i15++;
                                    i14 = i16;
                                    iB4 = i17;
                                    iH2 = i24;
                                    i12 = i18;
                                    iB3 = i22;
                                    iH = i23;
                                }
                                i3 = iB3;
                                i4 = i12;
                                i5 = iH;
                                i6 = iH2;
                                for (int i25 = 0; i25 < iH4; i25++) {
                                    C0772w0.a aVar = aVarArr[i25];
                                    aVar.d = c3085to.get(aVar.a);
                                }
                                bVarArr2 = bVarArr4;
                            }
                            i9 = 0;
                            B1[] b1ArrA = new D1().a(ShortBuffer.wrap(sArr), 0, iE2, this.i);
                            int i26 = this.b.i();
                            J0 j0A = a(iE, b1ArrA);
                            this.b.b(i26);
                            C0772w0 c0772w1 = new C0772w0(i5, i6, iH3, b1ArrA, aVarArr, bVarArr2, j0A);
                            i7 = i3;
                            this.j.a(i7, c0772w1);
                            this.b.b(i4);
                        } else {
                            bVarArr = bVarArr3;
                        }
                        i2 = iB2;
                        c0725p4 = c0725p4G;
                        i3 = iB3;
                        i4 = i12;
                        i5 = iH;
                        i6 = iH2;
                        bVarArr2 = bVarArr;
                        i9 = 0;
                        B1[] b1ArrA2 = new D1().a(ShortBuffer.wrap(sArr), 0, iE2, this.i);
                        int i27 = this.b.i();
                        J0 j0A2 = a(iE, b1ArrA2);
                        this.b.b(i27);
                        C0772w0 c0772w2 = new C0772w0(i5, i6, iH3, b1ArrA2, aVarArr, bVarArr2, j0A2);
                        i7 = i3;
                        this.j.a(i7, c0772w2);
                        this.b.b(i4);
                    }
                } else {
                    a = a;
                    a2 = a2;
                    i2 = iB2;
                    c0725p4 = c0725p4G;
                    i7 = iB3;
                }
                if (!n && i7 != 0 && this.j.get(i7) == null) {
                    throw new AssertionError();
                }
                c0772w0 = (C0772w0) this.j.get(i7);
            }
            int i28 = i2;
            C0684j2 c0684j2B = this.i.b(i28);
            C0725p4 c0725p5 = c0725p4;
            c0725p5.a(this.m, c0684j2B);
            a = a;
            C0658g0 c0658g0B = (C0658g0) a.a(c0684j2B);
            C0706m3.g gVarD = C0706m3.g.d();
            if (!this.g.o) {
                C0722p1 c0722p1 = this.m;
                C0633d0[] c0633d0Arr = C0633d0.c;
                C0633d0 c0633d0A = c0658g0B.a(c0722p1.W4);
                String strA = c0633d0A == null ? null : C0633d0.a(c0633d0A);
                if (strA != null) {
                    c0658g0B = c0658g0B.b(this.m.W4);
                    gVarD = C0706m3.c(c0684j2B.f.toString(), strA, this.a, this.m, this.g.e);
                }
            }
            W0.a aVarA = W0.S0().a(c0684j2B).a(c0725p5).a(gVarD).a(c0658g0B);
            A a3 = a2;
            w0Arr[i10] = aVarA.a((C0663g5) a3.a(c0684j2B)).a(c0772w0).c().a();
            i10++;
            i8 = i;
            a2 = a3;
            i11 = i28;
            w0Arr = w0Arr;
        }
        return w0Arr;
    }

    final void a(Consumer consumer, AbstractC0720p abstractC0720p) {
        int[] iArr;
        int i;
        int[] iArr2;
        final Long l;
        T0[] t0Arr;
        T0[] t0Arr2;
        W0[] w0ArrA;
        W0[] w0ArrA2;
        D dF = f(6);
        int i2 = dF.b;
        this.i.h(i2);
        if (i2 == 0) {
            return;
        }
        this.b.b(dF.c);
        int[] iArr3 = new int[i2];
        int[] iArr4 = new int[i2];
        int[] iArr5 = new int[i2];
        int[] iArr6 = new int[i2];
        int[] iArr7 = new int[i2];
        int[] iArr8 = new int[i2];
        int[] iArr9 = new int[i2];
        int[] iArr10 = new int[i2];
        boolean z = false;
        for (int i3 = 0; i3 < i2; i3++) {
            iArr3[i3] = this.b.e();
            iArr4[i3] = this.b.e();
            iArr5[i3] = this.b.c();
            iArr6[i3] = this.b.e();
            iArr7[i3] = this.b.c();
            iArr8[i3] = this.b.e();
            iArr9[i3] = this.b.e();
            iArr10[i3] = this.b.e();
        }
        int i4 = 0;
        while (i4 < i2) {
            int i5 = iArr5[i4];
            C0767v2 c0767v2F = i5 == -1 ? null : this.i.f(i5);
            int i6 = iArr7[i4];
            C0759u2 c0759u2E = i6 == -1 ? null : this.i.e(i6);
            C0767v2 c0767v2F2 = this.i.f(iArr3[i4]);
            com.android.tools.r8.graph.E eJ = com.android.tools.r8.graph.E.j(iArr4[i4]);
            if (eJ.a(C0582t.b, z)) {
                T0[] t0Arr3 = T0.m;
                W0[] w0Arr = W0.s;
                C0587y c0587yD = d(iArr8[i4]);
                C1684bJ c1684bJ = this.h;
                if (c1684bJ == null || c1684bJ.isEmpty()) {
                    iArr = iArr3;
                    i = i2;
                    iArr2 = iArr4;
                    l = null;
                } else {
                    iArr = iArr3;
                    String strB1 = abstractC0720p.a(c0767v2F2).b1();
                    i = i2;
                    iArr2 = iArr4;
                    l = (Long) this.h.getOrDefault(strB1, null);
                    if (!this.g.I.test(strB1, l)) {
                        iArr5 = iArr5;
                        iArr7 = iArr7;
                        iArr8 = iArr8;
                    }
                    i4++;
                    iArr3 = iArr;
                    i2 = i;
                    iArr4 = iArr2;
                    iArr5 = iArr5;
                    iArr7 = iArr7;
                    iArr8 = iArr8;
                    z = false;
                }
                if (iArr9[i4] != 0) {
                    S0 s0E = e(iArr10[i4]);
                    this.b.b(iArr9[i4]);
                    int iF = this.b.f();
                    int iF2 = this.b.f();
                    int iF3 = this.b.f();
                    int iF4 = this.b.f();
                    T0[] t0ArrA = a(iF, c0587yD.b, s0E != null ? s0E.a : null);
                    T0[] t0ArrA2 = a(iF2, c0587yD.b, null);
                    w0ArrA = a(iF3, c0587yD.c, c0587yD.d, this.f != com.android.tools.r8.graph.I.c);
                    w0ArrA2 = a(iF4, c0587yD.c, c0587yD.d, this.f != com.android.tools.r8.graph.I.c);
                    t0Arr = t0ArrA;
                    t0Arr2 = t0ArrA2;
                } else {
                    t0Arr = t0Arr3;
                    t0Arr2 = t0Arr;
                    w0ArrA = w0Arr;
                    w0ArrA2 = w0ArrA;
                }
                C0588z c0588z = new C0588z(c0767v2F2, this.a, c0587yD.a, this.g);
                consumer.accept(this.f.a(c0767v2F2, ProgramResource.Kind.DEX, this.a, eJ, c0767v2F, h(iArr6[i4]), c0759u2E, c0588z.f, c0588z.g, c0588z.h, c0588z.i, c0588z.b(), c0588z.c(), c0588z.e, c0588z.a(), t0Arr, t0Arr2, w0ArrA, w0ArrA2, this.m.i(), l == null ? new B$$ExternalSyntheticLambda1() : new C0729q2.a() { // from class: com.android.tools.r8.dex.B$$ExternalSyntheticLambda2
                    @Override // com.android.tools.r8.graph.C0729q2.a
                    public final long a(C0729q2 c0729q2) {
                        return B.a(l, c0729q2);
                    }
                }, null));
                i4++;
                iArr3 = iArr;
                i2 = i;
                iArr4 = iArr2;
                iArr5 = iArr5;
                iArr7 = iArr7;
                iArr8 = iArr8;
                z = false;
            } else {
                throw new C1367Tb("Class " + c0767v2F2.r0() + " has illegal access flags. Found: " + eJ, this.a);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ long a(Long l, C0729q2 c0729q2) {
        return l.longValue();
    }

    private void a(D d) {
        String str;
        if (d.b >= 2) {
            int i = d.c;
            this.b.b(i);
            int iE = this.b.e();
            int i2 = 1;
            while (i2 < d.b) {
                this.b.b((i2 * 4) + i);
                int iE2 = this.b.e();
                boolean z = iE2 > iE;
                if (!n && !z) {
                    if (this.i.e(iE).equals(this.i.e(iE2))) {
                        str = "Duplicate";
                    } else {
                        str = "Out-of-order";
                    }
                    throw new AssertionError(String.format(str.concat(" type ids (type #%s: `%s` string #%s, type #%s: `%s` string #%s)"), Integer.valueOf(i2 - 1), this.i.e(iE), Integer.valueOf(iE), Integer.valueOf(i2), this.i.e(iE2), Integer.valueOf(iE2)));
                }
                i2++;
                iE = iE2;
            }
        }
    }
}
