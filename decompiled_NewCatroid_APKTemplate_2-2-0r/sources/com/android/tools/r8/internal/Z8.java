package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Z8 implements com.android.tools.r8.utils.structural.x<Z8> {
    public static final Z8 b;
    public static final Z8 c;
    public static final Z8 d;
    public static final Z8 e;
    public static final Z8 f;
    public static final Z8 g;
    public static final Z8 h;
    public static final Z8 i;
    public static final Z8 j;
    public static final Z8 k;
    public static final Z8 l;
    private static Z8[] m = null;
    static final /* synthetic */ boolean n = true;
    private final int a;

    private Z8(int i2) {
        this.a = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.internal.Z8$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Z8) obj).b();
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.internal.Z8$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Z8) obj).c();
            }
        });
    }

    public static Z8 c(int i2) {
        return new Z8(i2);
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    public int b() {
        return this.a & 65535;
    }

    public int d() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        return com.android.tools.r8.utils.structural.k.a(this, obj);
    }

    public final int hashCode() {
        int i2 = com.android.tools.r8.utils.structural.l.d;
        return com.android.tools.r8.utils.structural.l.a(this, (com.android.tools.r8.utils.structural.y<Z8>) p());
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.internal.Z8$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                Z8.a(zVar);
            }
        };
    }

    public String toString() {
        StringBuilder sbAppend;
        if (c() != 0) {
            int iB = b();
            sbAppend = new StringBuilder().append(iB).append(".").append(c());
        } else {
            sbAppend = new StringBuilder().append(b());
        }
        return sbAppend.toString();
    }

    public static Iterable<Z8> a() {
        Z8[] z8Arr = m;
        return c(z8Arr[0], z8Arr[19]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(Z8 z8, Z8 z9) {
        return z9.b(z8);
    }

    public int c() {
        return this.a >>> 16;
    }

    static {
        Z8 z8 = new Z8(196653);
        b = z8;
        Z8 z9 = new Z8(46);
        c = z9;
        Z8 z10 = new Z8(47);
        Z8 z11 = new Z8(48);
        d = z11;
        Z8 z12 = new Z8(49);
        e = z12;
        Z8 z13 = new Z8(50);
        f = z13;
        Z8 z14 = new Z8(51);
        g = z14;
        Z8 z15 = new Z8(52);
        h = z15;
        Z8 z16 = new Z8(53);
        i = z16;
        Z8 z17 = new Z8(54);
        Z8 z18 = new Z8(55);
        j = z18;
        Z8 z19 = new Z8(56);
        Z8 z20 = new Z8(57);
        Z8 z21 = new Z8(58);
        Z8 z22 = new Z8(59);
        k = z22;
        Z8 z23 = new Z8(60);
        Z8 z24 = new Z8(61);
        Z8 z25 = new Z8(62);
        Z8 z26 = new Z8(63);
        Z8 z27 = new Z8(64);
        l = z27;
        m = new Z8[]{z8, z9, z10, z11, z12, z13, z14, z15, z16, z17, z18, z19, z20, z21, z22, z23, z24, z25, z26, z27};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Z8 z8, Z8 z9) {
        return z9.a(z8);
    }

    public static Iterable<Z8> c(final Z8 z8, final Z8 z9) {
        boolean z = n;
        if (!z && !z8.b(z9)) {
            throw new AssertionError();
        }
        if (!z && z8.c() == 65535) {
            throw new AssertionError("This method does not handle preview versions");
        }
        if (!z && z9.c() == 65535) {
            throw new AssertionError("This method does not handle preview versions");
        }
        return (Iterable) Arrays.stream(m).filter(new Predicate() { // from class: com.android.tools.r8.internal.Z8$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Z8.a(this.f$0, (Z8) obj);
            }
        }).filter(new Predicate() { // from class: com.android.tools.r8.internal.Z8$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Z8.b(this.f$0, (Z8) obj);
            }
        }).collect(Collectors.toList());
    }
}
