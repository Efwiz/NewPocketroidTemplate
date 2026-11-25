package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import net.bytebuddy.description.modifier.ModifierContributor;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class B9 extends AbstractC1899eF {
    private static final EnumC3484z9[] x;
    public int j;
    private int k;
    private int l;
    private int m;
    private boolean n;
    private boolean o;
    private boolean p;
    private int q;
    private final Map r;
    private HashSet s;
    private int t;
    private int u;
    private int v;
    private ArrayList w;

    static {
        EnumC3484z9 enumC3484z9 = EnumC3484z9.a;
        EnumC3484z9 enumC3484z10 = EnumC3484z9.b;
        EnumC3484z9 enumC3484z11 = EnumC3484z9.c;
        EnumC3484z9 enumC3484z12 = EnumC3484z9.g;
        EnumC3484z9 enumC3484z13 = EnumC3484z9.e;
        EnumC3484z9 enumC3484z14 = EnumC3484z9.f;
        EnumC3484z9 enumC3484z15 = EnumC3484z9.d;
        x = new EnumC3484z9[]{enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z10, enumC3484z10, null, null, null, enumC3484z11, enumC3484z11, enumC3484z11, enumC3484z11, enumC3484z11, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z11, enumC3484z11, enumC3484z11, enumC3484z11, enumC3484z11, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, null, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z12, enumC3484z12, enumC3484z12, enumC3484z12, enumC3484z12, enumC3484z12, enumC3484z12, enumC3484z12, enumC3484z12, enumC3484z12, enumC3484z12, enumC3484z12, enumC3484z12, enumC3484z12, enumC3484z12, enumC3484z12, enumC3484z11, null, null, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z9, enumC3484z13, enumC3484z13, enumC3484z13, enumC3484z13, enumC3484z14, enumC3484z14, enumC3484z14, enumC3484z14, null, enumC3484z15, enumC3484z10, enumC3484z15, enumC3484z9, enumC3484z9, enumC3484z15, enumC3484z15, enumC3484z9, enumC3484z9, null, null, enumC3484z12, enumC3484z12};
    }

    protected B9(int i, AbstractC1899eF abstractC1899eF, HashMap map) {
        super(i, abstractC1899eF);
        this.t = -1;
        this.r = map;
        this.s = new HashSet();
        this.w = new ArrayList();
    }

    private void e() {
        if (this.p) {
            throw new IllegalStateException("Cannot visit elements after visitEnd has been called.");
        }
    }

    private void f() {
        if (this.o) {
            throw new IllegalStateException("Cannot visit instructions after visitMaxs has been called.");
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(String str, boolean z) {
        e();
        d(this.j, str);
        return new C2831q9(super.a(str, z), true);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b(int i, String str) {
        if (str != null) {
            a(this.j, 0, -1, str, "name");
        }
        AbstractC3187v9.a(i, ModifierContributor.ForParameter.MASK);
        super.b(i, str);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 c(int i, C00 c00, String str, boolean z) {
        e();
        int iC = new D00(i).c();
        if (iC != 1 && iC != 18 && iC != 20 && iC != 21 && iC != 22 && iC != 23) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(iC));
        }
        AbstractC3187v9.a(i);
        d(this.j, str);
        return new C2831q9(super.c(i, c00, str, z), true);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void d(int i, int i2) {
        d();
        f();
        a(i, EnumC3484z9.c);
        f(i2, "Invalid local variable index");
        super.d(i, i2);
        this.q++;
    }

    static void e(int i, String str) {
        int iA;
        if (str != null && str.length() != 0) {
            if (str.charAt(0) == '(' && str.length() >= 3) {
                if (str.charAt(1) != ')') {
                    iA = 1;
                    do {
                        if (str.charAt(iA) != 'V') {
                            iA = a(i, iA, str, false);
                            if (iA >= str.length()) {
                                break;
                            }
                        } else {
                            throw new IllegalArgumentException("Invalid descriptor: ".concat(str));
                        }
                    } while (str.charAt(iA) != ')');
                } else {
                    iA = 1;
                }
                if (a(i, iA + 1, str, true) != str.length()) {
                    throw new IllegalArgumentException("Invalid descriptor: ".concat(str));
                }
                return;
            }
            throw new IllegalArgumentException("Invalid descriptor: ".concat(str));
        }
        throw new IllegalArgumentException("Invalid method descriptor (must not be null or empty)");
    }

    private static void f(int i, String str) {
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException(str + " (must be an unsigned short): " + i);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a() {
        e();
        return new C2831q9(super.a(), false);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b() {
        if ((this.k & 1024) == 0) {
            this.n = true;
            super.b();
            return;
        }
        throw new UnsupportedOperationException("Abstract methods cannot have code");
    }

    private void d() {
        if (!this.n) {
            throw new IllegalStateException("Cannot visit instructions before visitCode has been called.");
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, boolean z) {
        e();
        if (z) {
            this.l = i;
        } else {
            this.m = i;
        }
        super.a(i, z);
    }

    static void d(int i, String str) {
        if (a(i, 0, str, false) != str.length()) {
            throw new IllegalArgumentException("Invalid descriptor: ".concat(str));
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b(int i, int i2) {
        d();
        f();
        a(i, EnumC3484z9.b);
        if (i != 16) {
            if (i != 17) {
                if (i != 188) {
                    throw new AssertionError();
                }
                if (i2 < 4 || i2 > 11) {
                    throw new IllegalArgumentException(AbstractC1857dg.a("Invalid operand (must be an array type code T_...): ", i2));
                }
            } else if (i2 < -32768 || i2 > 32767) {
                throw new IllegalArgumentException(AbstractC1857dg.a("Invalid operand (must be a signed short): ", i2));
            }
        } else if (i2 < -128 || i2 > 127) {
            throw new IllegalArgumentException(AbstractC1857dg.a("Invalid operand (must be a signed byte): ", i2));
        }
        super.b(i, i2);
        this.q++;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(int i, String str, boolean z) {
        int i2;
        int i3;
        e();
        if ((z && (i3 = this.l) > 0 && i >= i3) || (!z && (i2 = this.m) > 0 && i >= i2)) {
            throw new IllegalArgumentException("Invalid parameter index");
        }
        d(this.j, str);
        return new C2831q9(super.a(i, str, z), true);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void c(int i, String str) {
        d();
        f();
        a(i, EnumC3484z9.d);
        a(this.j, str, ModuleXmlParser.TYPE);
        if (i == 187 && str.charAt(0) == '[') {
            throw new IllegalArgumentException("NEW cannot be used to create arrays: ".concat(str));
        }
        super.c(i, str);
        this.q++;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void c(int i, int i2) {
        d();
        f();
        this.o = true;
        Iterator it = this.s.iterator();
        while (it.hasNext()) {
            if (this.r.get((C3263wA) it.next()) == null) {
                throw new IllegalStateException("Undefined label used");
            }
        }
        for (int i3 = 0; i3 < this.w.size(); i3 += 2) {
            if (((Integer) this.r.get(this.w.get(i3 + 1))).intValue() <= ((Integer) this.r.get(this.w.get(i3))).intValue()) {
                throw new IllegalStateException("Empty try catch block handler range");
            }
        }
        f(i, "Invalid max stack");
        f(i2, "Invalid max locals");
        super.c(i, i2);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(T3 t3) {
        e();
        if (t3 != null) {
            super.a(t3);
            return;
        }
        throw new IllegalArgumentException("Invalid attribute (must not be null)");
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0035 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:23:0x0037  */
    /* JADX WARN: Code duplicated, block: B:24:0x0039  */
    /* JADX WARN: Code duplicated, block: B:33:0x004c A[LOOP:0: B:32:0x004a->B:33:0x004c, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:42:0x0066 A[LOOP:1: B:41:0x0064->B:42:0x0066, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:44:0x0070  */
    /* JADX WARN: Code duplicated, block: B:45:0x0076  */
    /* JADX WARN: Code duplicated, block: B:55:0x0090  */
    /* JADX WARN: Code duplicated, block: B:57:0x00ad  */
    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, int i2, Object[] objArr, int i3, Object[] objArr2) {
        int i4;
        int i5;
        int i6;
        int i7 = this.q;
        if (i7 != this.t) {
            this.t = i7;
            if (i != -1 && i != 0) {
                i4 = 3;
                if (i == 1 || i == 2) {
                    i5 = 0;
                } else if (i == 3) {
                    i4 = 0;
                } else {
                    if (i != 4) {
                        throw new IllegalArgumentException(AbstractC1857dg.a("Invalid frame type ", i));
                    }
                    i4 = 0;
                    i5 = 1;
                }
                if (i2 <= i4) {
                    throw new IllegalArgumentException("Invalid numLocal=" + i2 + " for frame type " + i);
                }
                if (i3 <= i5) {
                    if (i != 2) {
                        if (i2 <= 0 && (objArr == null || objArr.length < i2)) {
                            throw new IllegalArgumentException("Array local[] is shorter than numLocal");
                        }
                        for (i6 = 0; i6 < i2; i6++) {
                            b(objArr[i6]);
                        }
                    }
                    if (i3 <= 0 && (objArr2 == null || objArr2.length < i3)) {
                        throw new IllegalArgumentException("Array stack[] is shorter than numStack");
                    }
                    for (int i8 = 0; i8 < i3; i8++) {
                        b(objArr2[i8]);
                    }
                    if (i == -1) {
                        this.u++;
                    } else {
                        this.v++;
                    }
                    if (this.u <= 0 && this.v > 0) {
                        throw new IllegalArgumentException("Expanded and compressed frames must not be mixed.");
                    }
                    super.a(i, i2, objArr, i3, objArr2);
                    return;
                }
                throw new IllegalArgumentException("Invalid numStack=" + i3 + " for frame type " + i);
            }
            i4 = Integer.MAX_VALUE;
            i5 = i4;
            if (i2 <= i4) {
                throw new IllegalArgumentException("Invalid numLocal=" + i2 + " for frame type " + i);
            }
            if (i3 <= i5) {
                if (i != 2) {
                    if (i2 <= 0) {
                    }
                    while (i6 < i2) {
                        b(objArr[i6]);
                    }
                }
                if (i3 <= 0) {
                }
                while (i8 < i3) {
                    b(objArr2[i8]);
                }
                if (i == -1) {
                    this.u++;
                } else {
                    this.v++;
                }
                if (this.u <= 0) {
                }
                super.a(i, i2, objArr, i3, objArr2);
                return;
            }
            throw new IllegalArgumentException("Invalid numStack=" + i3 + " for frame type " + i);
        }
        throw new IllegalStateException("At most one frame can be visited at a given code location.");
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void c() {
        e();
        this.p = true;
        super.c();
    }

    private void c(Object obj) {
        if (obj instanceof CZ) {
            int iE = ((CZ) obj).e();
            if (iE != 10 && iE != 9 && iE != 11) {
                throw new IllegalArgumentException("Illegal LDC constant value");
            }
            if (iE != 11 && (this.j & 65535) < 49) {
                throw new IllegalArgumentException("ldc of a constant class requires at least version 1.5");
            }
            if (iE == 11 && (this.j & 65535) < 51) {
                throw new IllegalArgumentException("ldc of a method type requires at least version 1.7");
            }
            return;
        }
        if (obj instanceof C1944ek) {
            if ((this.j & 65535) >= 51) {
                C1944ek c1944ek = (C1944ek) obj;
                int iD = c1944ek.d();
                if (iD >= 1 && iD <= 9) {
                    a(this.j, c1944ek.c(), "handle owner");
                    if (iD <= 4) {
                        d(this.j, c1944ek.a());
                    } else {
                        e(this.j, c1944ek.a());
                    }
                    String strB = c1944ek.b();
                    if ("<init>".equals(strB) && iD == 8) {
                        return;
                    }
                    b(this.j, strB, "handle name");
                    return;
                }
                throw new IllegalArgumentException(AbstractC1857dg.a("invalid handle tag ", iD));
            }
            throw new IllegalArgumentException("ldc of a Handle requires at least version 1.7");
        }
        if (obj instanceof C1064Ic) {
            int i = this.j;
            if ((i & 65535) >= 55) {
                C1064Ic c1064Ic = (C1064Ic) obj;
                b(i, c1064Ic.e(), "constant dynamic name");
                d(this.j, c1064Ic.d());
                c(c1064Ic.a());
                int iB = c1064Ic.b();
                for (int i2 = 0; i2 < iB; i2++) {
                    c(c1064Ic.a(i2));
                }
                return;
            }
            throw new IllegalArgumentException("ldc of a ConstantDynamic requires at least version 11");
        }
        if (!(obj instanceof Integer) && !(obj instanceof Float) && !(obj instanceof Long) && !(obj instanceof Double) && !(obj instanceof String)) {
            throw new IllegalArgumentException("Invalid constant: " + obj);
        }
    }

    protected B9(int i, int i2, String str, String str2, AbstractC1899eF abstractC1899eF, HashMap map) {
        this(i, new C3415y9(i, i2, abstractC1899eF, str, str2), map);
        this.k = i2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 b(int i, C00 c00, String str, boolean z) {
        d();
        f();
        int iC = new D00(i).c();
        if (iC == 66) {
            AbstractC3187v9.a(i);
            d(this.j, str);
            return new C2831q9(super.b(i, c00, str, z), true);
        }
        throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(iC));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b(int i, C3263wA c3263wA) {
        d();
        f();
        f(i, "Invalid line number");
        a(c3263wA, true, "start label");
        super.b(i, c3263wA);
    }

    private void b(Object obj) {
        if (obj == JK.a || obj == JK.b || obj == JK.c || obj == JK.e || obj == JK.d || obj == JK.f || obj == JK.g) {
            return;
        }
        if (obj instanceof String) {
            a(this.j, (String) obj, "Invalid stack frame value");
        } else {
            if (obj instanceof C3263wA) {
                a((C3263wA) obj, false, CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME);
                return;
            }
            throw new IllegalArgumentException("Invalid stack frame value: " + obj);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i) {
        d();
        f();
        a(i, EnumC3484z9.a);
        super.a(i);
        this.q++;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, String str, String str2, String str3) {
        d();
        f();
        a(i, EnumC3484z9.e);
        a(this.j, str, "owner");
        a(this.j, 0, -1, str2, "name");
        d(this.j, str3);
        super.a(i, str, str2, str3);
        this.q++;
    }

    static void b(int i, String str, String str2) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("Invalid " + str2 + " (must not be null or empty)");
        }
        int iOffsetByCodePoints = 0;
        if ((i & 65535) >= 49) {
            while (iOffsetByCodePoints < str.length()) {
                if (".;[/<>".indexOf(str.codePointAt(iOffsetByCodePoints)) == -1) {
                    iOffsetByCodePoints = str.offsetByCodePoints(iOffsetByCodePoints, 1);
                } else {
                    throw new IllegalArgumentException("Invalid " + str2 + " (must be a valid unqualified name): " + str);
                }
            }
            return;
        }
        while (iOffsetByCodePoints < str.length()) {
            if (iOffsetByCodePoints == 0) {
                if (Character.isJavaIdentifierStart(str.codePointAt(iOffsetByCodePoints))) {
                    iOffsetByCodePoints = str.offsetByCodePoints(iOffsetByCodePoints, 1);
                } else {
                    throw new IllegalArgumentException("Invalid " + str2 + " (must be a '<init>', '<clinit>' or a valid Java identifier): " + str);
                }
            } else if (Character.isJavaIdentifierPart(str.codePointAt(iOffsetByCodePoints))) {
                iOffsetByCodePoints = str.offsetByCodePoints(iOffsetByCodePoints, 1);
            } else {
                throw new IllegalArgumentException("Invalid " + str2 + " (must be a '<init>', '<clinit>' or a valid Java identifier): " + str);
            }
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, String str, String str2, String str3, boolean z) {
        if (this.h < 327680 && (i & 256) == 0) {
            super.a(i, str, str2, str3, z);
            return;
        }
        int i2 = i & (-257);
        d();
        f();
        a(i2, EnumC3484z9.f);
        if (i2 != 183 || !"<init>".equals(str2)) {
            b(this.j, str2, "name");
        }
        a(this.j, str, "owner");
        e(this.j, str3);
        if (i2 == 182 && z) {
            throw new IllegalArgumentException("INVOKEVIRTUAL can't be used with interfaces");
        }
        if (i2 == 185 && !z) {
            throw new IllegalArgumentException("INVOKEINTERFACE can't be used with classes");
        }
        if (i2 == 183 && z && (this.j & 65535) < 52) {
            throw new IllegalArgumentException("INVOKESPECIAL can't be used with interfaces prior to Java 8");
        }
        super.a(i, str, str2, str3, z);
        this.q++;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(String str, String str2, C1944ek c1944ek, Object... objArr) {
        d();
        f();
        b(this.j, str, "name");
        e(this.j, str2);
        if (c1944ek.d() != 6 && c1944ek.d() != 8) {
            throw new IllegalArgumentException("invalid handle tag " + c1944ek.d());
        }
        for (Object obj : objArr) {
            c(obj);
        }
        super.a(str, str2, c1944ek, objArr);
        this.q++;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, C3263wA c3263wA) {
        d();
        f();
        a(i, EnumC3484z9.g);
        a(c3263wA, false, CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME);
        super.a(i, c3263wA);
        this.q++;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(C3263wA c3263wA) {
        d();
        f();
        a(c3263wA, false, CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME);
        if (this.r.get(c3263wA) == null) {
            this.r.put(c3263wA, Integer.valueOf(this.q));
            super.a(c3263wA);
            return;
        }
        throw new IllegalStateException("Already visited label");
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(Object obj) {
        d();
        f();
        c(obj);
        super.a(obj);
        this.q++;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, int i2) {
        d();
        f();
        f(i, "Invalid local variable index");
        if (i2 >= -32768 && i2 <= 32767) {
            super.a(i, i2);
            this.q++;
            return;
        }
        throw new IllegalArgumentException(AbstractC1857dg.a("Invalid increment (must be a signed short): ", i2));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, int i2, C3263wA c3263wA, C3263wA... c3263wAArr) {
        d();
        f();
        if (i2 >= i) {
            a(c3263wA, false, "default label");
            if (c3263wAArr.length == (i2 - i) + 1) {
                for (int i3 = 0; i3 < c3263wAArr.length; i3++) {
                    a(c3263wAArr[i3], false, AbstractC1857dg.a("label at index ", i3));
                }
                super.a(i, i2, c3263wA, c3263wAArr);
                this.q++;
                return;
            }
            throw new IllegalArgumentException("There must be max - min + 1 labels");
        }
        throw new IllegalArgumentException("Max = " + i2 + " must be greater than or equal to min = " + i);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(C3263wA c3263wA, int[] iArr, C3263wA[] c3263wAArr) {
        f();
        d();
        a(c3263wA, false, "default label");
        if (iArr != null && iArr.length == c3263wAArr.length) {
            for (int i = 0; i < c3263wAArr.length; i++) {
                a(c3263wAArr[i], false, AbstractC1857dg.a("label at index ", i));
            }
            super.a(c3263wA, iArr, c3263wAArr);
            this.q++;
            return;
        }
        throw new IllegalArgumentException("There must be the same number of keys and labels");
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, String str) {
        d();
        f();
        d(this.j, str);
        if (str.charAt(0) != '[') {
            throw new IllegalArgumentException("Invalid descriptor (must be an array type descriptor): ".concat(str));
        }
        if (i >= 1) {
            if (i <= str.lastIndexOf(91) + 1) {
                super.a(i, str);
                this.q++;
                return;
            }
            throw new IllegalArgumentException(AbstractC1857dg.a("Invalid dimensions (must not be greater than numDimensions(descriptor)): ", i));
        }
        throw new IllegalArgumentException(AbstractC1857dg.a("Invalid dimensions (must be greater than 0): ", i));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        d();
        f();
        int iC = new D00(i).c();
        if (iC != 67 && iC != 68 && iC != 69 && iC != 70 && iC != 71 && iC != 72 && iC != 73 && iC != 74 && iC != 75) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(iC));
        }
        AbstractC3187v9.a(i);
        d(this.j, str);
        return new C2831q9(super.a(i, c00, str, z), true);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(C3263wA c3263wA, C3263wA c3263wA2, C3263wA c3263wA3, String str) {
        d();
        f();
        a(c3263wA, false, "start label");
        a(c3263wA2, false, "end label");
        a(c3263wA3, false, "handler label");
        if (this.r.get(c3263wA) == null && this.r.get(c3263wA2) == null && this.r.get(c3263wA3) == null) {
            if (str != null) {
                a(this.j, str, ModuleXmlParser.TYPE);
            }
            super.a(c3263wA, c3263wA2, c3263wA3, str);
            this.w.add(c3263wA);
            this.w.add(c3263wA2);
            return;
        }
        throw new IllegalStateException("Try catch blocks must be visited before their labels");
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(String str, String str2, String str3, C3263wA c3263wA, C3263wA c3263wA2, int i) {
        d();
        f();
        a(this.j, 0, -1, str, "name");
        d(this.j, str2);
        if (str3 != null) {
            AbstractC3187v9.d(str3);
        }
        a(c3263wA, true, "start label");
        a(c3263wA2, true, "end label");
        f(i, "Invalid local variable index");
        if (((Integer) this.r.get(c3263wA2)).intValue() >= ((Integer) this.r.get(c3263wA)).intValue()) {
            super.a(str, str2, str3, c3263wA, c3263wA2, i);
            return;
        }
        throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(int i, C00 c00, C3263wA[] c3263wAArr, C3263wA[] c3263wAArr2, int[] iArr, String str, boolean z) {
        d();
        f();
        int iC = new D00(i).c();
        if (iC != 64 && iC != 65) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(iC));
        }
        AbstractC3187v9.a(i);
        d(this.j, str);
        if (c3263wAArr != null && c3263wAArr2 != null && iArr != null && c3263wAArr2.length == c3263wAArr.length && iArr.length == c3263wAArr.length) {
            for (int i2 = 0; i2 < c3263wAArr.length; i2++) {
                a(c3263wAArr[i2], true, "start label");
                a(c3263wAArr2[i2], true, "end label");
                f(iArr[i2], "Invalid local variable index");
                if (((Integer) this.r.get(c3263wAArr2[i2])).intValue() < ((Integer) this.r.get(c3263wAArr[i2])).intValue()) {
                    throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
                }
            }
            return super.a(i, c00, c3263wAArr, c3263wAArr2, iArr, str, z);
        }
        throw new IllegalArgumentException("Invalid start, end and index arrays (must be non null and of identical length");
    }

    private static void a(int i, EnumC3484z9 enumC3484z9) {
        if (i < 0 || i > 199 || x[i] != enumC3484z9) {
            throw new IllegalArgumentException(AbstractC1857dg.a("Invalid opcode: ", i));
        }
    }

    static void a(int i, int i2, int i3, String str, String str2) {
        if (str == null || (i3 != -1 ? i3 <= i2 : str.length() <= i2)) {
            throw new IllegalArgumentException("Invalid " + str2 + " (must not be null or empty)");
        }
        if (i3 == -1) {
            i3 = str.length();
        }
        if ((i & 65535) >= 49) {
            while (i2 < i3) {
                if (".;[/".indexOf(str.codePointAt(i2)) == -1) {
                    i2 = str.offsetByCodePoints(i2, 1);
                } else {
                    throw new IllegalArgumentException("Invalid " + str2 + " (must not contain . ; [ or /): " + str);
                }
            }
            return;
        }
        int iOffsetByCodePoints = i2;
        while (iOffsetByCodePoints < i3) {
            if (iOffsetByCodePoints == i2) {
                if (Character.isJavaIdentifierStart(str.codePointAt(iOffsetByCodePoints))) {
                    iOffsetByCodePoints = str.offsetByCodePoints(iOffsetByCodePoints, 1);
                } else {
                    throw new IllegalArgumentException("Invalid " + str2 + " (must be a valid Java identifier): " + str);
                }
            } else if (Character.isJavaIdentifierPart(str.codePointAt(iOffsetByCodePoints))) {
                iOffsetByCodePoints = str.offsetByCodePoints(iOffsetByCodePoints, 1);
            } else {
                throw new IllegalArgumentException("Invalid " + str2 + " (must be a valid Java identifier): " + str);
            }
        }
    }

    static void a(int i, String str, String str2) {
        if (str != null && str.length() != 0) {
            int i2 = 0;
            if (str.charAt(0) == '[') {
                d(i, str);
                return;
            }
            while (true) {
                try {
                    int iIndexOf = str.indexOf(47, i2 + 1);
                    if (iIndexOf != -1) {
                        a(i, i2, iIndexOf, str, (String) null);
                        i2 = iIndexOf + 1;
                    } else {
                        a(i, i2, str.length(), str, (String) null);
                        return;
                    }
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid " + str2 + " (must be an internal class name): " + str, e);
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid " + str2 + " (must not be null or empty)");
        }
    }

    private static int a(int i, int i2, String str, boolean z) {
        if (str != null && i2 < str.length()) {
            char cCharAt = str.charAt(i2);
            if (cCharAt != 'F') {
                int i3 = 0;
                if (cCharAt == 'L') {
                    int iIndexOf = str.indexOf(59, i2);
                    if (i2 != -1 && iIndexOf - i2 >= 2) {
                        try {
                            String strSubstring = str.substring(i2 + 1, iIndexOf);
                            while (true) {
                                try {
                                    int iIndexOf2 = strSubstring.indexOf(47, i3 + 1);
                                    if (iIndexOf2 != -1) {
                                        a(i, i3, iIndexOf2, strSubstring, (String) null);
                                        i3 = iIndexOf2 + 1;
                                    } else {
                                        a(i, i3, strSubstring.length(), strSubstring, (String) null);
                                        return iIndexOf + 1;
                                    }
                                } catch (IllegalArgumentException e) {
                                    throw new IllegalArgumentException("Invalid " + ((String) null) + " (must be an internal class name): " + strSubstring, e);
                                }
                                throw new IllegalArgumentException("Invalid descriptor: ".concat(str), e);
                            }
                        } catch (IllegalArgumentException e2) {
                            throw new IllegalArgumentException("Invalid descriptor: ".concat(str), e2);
                        }
                    }
                    throw new IllegalArgumentException("Invalid descriptor: ".concat(str));
                }
                if (cCharAt != 'S') {
                    if (cCharAt == 'V') {
                        if (z) {
                            return i2 + 1;
                        }
                        throw new IllegalArgumentException("Invalid descriptor: ".concat(str));
                    }
                    if (cCharAt != 'I' && cCharAt != 'J' && cCharAt != 'Z') {
                        if (cCharAt != '[') {
                            switch (cCharAt) {
                                case 'B':
                                case 'C':
                                case 'D':
                                    break;
                                default:
                                    throw new IllegalArgumentException("Invalid descriptor: ".concat(str));
                            }
                        } else {
                            do {
                                i2++;
                                if (i2 >= str.length()) {
                                    break;
                                }
                            } while (str.charAt(i2) == '[');
                            if (i2 < str.length()) {
                                return a(i, i2, str, false);
                            }
                            throw new IllegalArgumentException("Invalid descriptor: ".concat(str));
                        }
                    }
                }
            }
            return i2 + 1;
        }
        throw new IllegalArgumentException("Invalid type descriptor (must not be null or empty)");
    }

    private void a(C3263wA c3263wA, boolean z, String str) {
        if (c3263wA != null) {
            if (z && this.r.get(c3263wA) == null) {
                throw new IllegalArgumentException("Invalid " + str + " (must be visited first)");
            }
            this.s.add(c3263wA);
            return;
        }
        throw new IllegalArgumentException("Invalid " + str + " (must not be null)");
    }
}
