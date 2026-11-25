package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.n2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2583n2 extends AbstractC2509m2 {
    private final NY b;
    private final boolean c;
    private final C1667b6 d;
    private final int e;
    private int f;
    private final C2583n2 g;
    private C2583n2 h;

    C2583n2(NY ny, boolean z, C1667b6 c1667b6, C2583n2 c2583n2) {
        super(589824);
        this.b = ny;
        this.c = z;
        this.d = c1667b6;
        int i = c1667b6.b;
        this.e = i == 0 ? -1 : i - 2;
        this.g = c2583n2;
        if (c2583n2 != null) {
            c2583n2.h = this;
        }
    }

    static C2583n2 a(NY ny, String str, C2583n2 c2583n2) {
        C1667b6 c1667b6 = new C1667b6();
        c1667b6.d(ny.d(str)).d(0);
        return new C2583n2(ny, true, c1667b6, c2583n2);
    }

    final int b(String str) {
        if (str != null) {
            this.b.d(str);
        }
        int i = 8;
        for (C2583n2 c2583n2 = this; c2583n2 != null; c2583n2 = c2583n2.g) {
            i += c2583n2.d.b;
        }
        return i;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:11:0x0020  */
    /* JADX WARN: Code duplicated, block: B:12:0x002a  */
    static C2583n2 a(NY ny, int i, C00 c00, String str, C2583n2 c2583n2) {
        C1667b6 c1667b6 = new C1667b6();
        int i2 = i >>> 24;
        if (i2 != 0 && i2 != 1) {
            switch (i2) {
                case 16:
                case 17:
                case 18:
                case 23:
                    c1667b6.c(i2, (i & 16776960) >> 8);
                    break;
                case 19:
                case 20:
                case 21:
                    c1667b6.b(i2);
                    break;
                case 22:
                    c1667b6.d(i >>> 16);
                    break;
                default:
                    switch (i2) {
                        case 66:
                        case 67:
                        case 68:
                        case 69:
                        case 70:
                            c1667b6.c(i2, (i & 16776960) >> 8);
                            break;
                        case 71:
                        case 72:
                        case 73:
                        case 74:
                        case 75:
                            c1667b6.c(i);
                            break;
                        default:
                            throw new IllegalArgumentException();
                    }
                    break;
            }
        } else {
            c1667b6.d(i >>> 16);
        }
        C00.a(c00, c1667b6);
        c1667b6.d(ny.d(str)).d(0);
        return new C2583n2(ny, true, c1667b6, c2583n2);
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, Object obj) {
        this.f++;
        if (this.c) {
            this.d.d(this.b.d(str));
        }
        if (obj instanceof String) {
            this.d.c(115, this.b.d((String) obj));
            return;
        }
        if (obj instanceof Byte) {
            this.d.c(66, this.b.a((int) ((Byte) obj).byteValue()).a);
            return;
        }
        if (obj instanceof Boolean) {
            this.d.c(90, this.b.a(((Boolean) obj).booleanValue() ? 1 : 0).a);
            return;
        }
        if (obj instanceof Character) {
            this.d.c(67, this.b.a((int) ((Character) obj).charValue()).a);
            return;
        }
        if (obj instanceof Short) {
            this.d.c(83, this.b.a((int) ((Short) obj).shortValue()).a);
            return;
        }
        if (obj instanceof CZ) {
            this.d.c(99, this.b.d(((CZ) obj).b()));
            return;
        }
        int i = 0;
        if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            this.d.c(91, bArr.length);
            int length = bArr.length;
            while (i < length) {
                this.d.c(66, this.b.a((int) bArr[i]).a);
                i++;
            }
            return;
        }
        if (obj instanceof boolean[]) {
            boolean[] zArr = (boolean[]) obj;
            this.d.c(91, zArr.length);
            int length2 = zArr.length;
            while (i < length2) {
                this.d.c(90, this.b.a(zArr[i] ? 1 : 0).a);
                i++;
            }
            return;
        }
        if (obj instanceof short[]) {
            short[] sArr = (short[]) obj;
            this.d.c(91, sArr.length);
            int length3 = sArr.length;
            while (i < length3) {
                this.d.c(83, this.b.a((int) sArr[i]).a);
                i++;
            }
            return;
        }
        if (obj instanceof char[]) {
            char[] cArr = (char[]) obj;
            this.d.c(91, cArr.length);
            int length4 = cArr.length;
            while (i < length4) {
                this.d.c(67, this.b.a((int) cArr[i]).a);
                i++;
            }
            return;
        }
        if (obj instanceof int[]) {
            int[] iArr = (int[]) obj;
            this.d.c(91, iArr.length);
            int length5 = iArr.length;
            while (i < length5) {
                this.d.c(73, this.b.a(iArr[i]).a);
                i++;
            }
            return;
        }
        if (obj instanceof long[]) {
            long[] jArr = (long[]) obj;
            this.d.c(91, jArr.length);
            int length6 = jArr.length;
            while (i < length6) {
                this.d.c(74, this.b.a(jArr[i]).a);
                i++;
            }
            return;
        }
        if (obj instanceof float[]) {
            float[] fArr = (float[]) obj;
            this.d.c(91, fArr.length);
            int length7 = fArr.length;
            while (i < length7) {
                this.d.c(70, this.b.a(fArr[i]).a);
                i++;
            }
            return;
        }
        if (obj instanceof double[]) {
            double[] dArr = (double[]) obj;
            this.d.c(91, dArr.length);
            int length8 = dArr.length;
            while (i < length8) {
                this.d.c(68, this.b.a(dArr[i]).a);
                i++;
            }
            return;
        }
        MY myA = this.b.a(obj);
        this.d.c(".s.IFJDCS".charAt(myA.b), myA.a);
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, String str2, String str3) {
        this.f++;
        if (this.c) {
            this.d.d(this.b.d(str));
        }
        this.d.c(101, this.b.d(str2)).d(this.b.d(str3));
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final AbstractC2509m2 a(String str, String str2) {
        this.f++;
        if (this.c) {
            this.d.d(this.b.d(str));
        }
        this.d.c(64, this.b.d(str2)).d(0);
        return new C2583n2(this.b, true, this.d, null);
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final AbstractC2509m2 a(String str) {
        this.f++;
        if (this.c) {
            this.d.d(this.b.d(str));
        }
        this.d.c(91, 0);
        return new C2583n2(this.b, false, this.d, null);
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a() {
        int i = this.e;
        if (i != -1) {
            byte[] bArr = this.d.a;
            int i2 = this.f;
            bArr[i] = (byte) (i2 >>> 8);
            bArr[i + 1] = (byte) i2;
        }
    }

    static int a(C2583n2 c2583n2, C2583n2 c2583n3, C2583n2 c2583n4, C2583n2 c2583n5) {
        int iB = c2583n2 != null ? c2583n2.b("RuntimeVisibleAnnotations") : 0;
        if (c2583n3 != null) {
            iB += c2583n3.b("RuntimeInvisibleAnnotations");
        }
        if (c2583n4 != null) {
            iB += c2583n4.b("RuntimeVisibleTypeAnnotations");
        }
        return c2583n5 != null ? iB + c2583n5.b("RuntimeInvisibleTypeAnnotations") : iB;
    }

    final void a(int i, C1667b6 c1667b6) {
        int i2 = 2;
        int i3 = 0;
        C2583n2 c2583n2 = null;
        for (C2583n2 c2583n3 = this; c2583n3 != null; c2583n3 = c2583n3.g) {
            c2583n3.a();
            i2 += c2583n3.d.b;
            i3++;
            c2583n2 = c2583n3;
        }
        c1667b6.d(i);
        c1667b6.c(i2);
        c1667b6.d(i3);
        while (c2583n2 != null) {
            C1667b6 c1667b7 = c2583n2.d;
            c1667b6.a(c1667b7.a, 0, c1667b7.b);
            c2583n2 = c2583n2.h;
        }
    }

    static void a(NY ny, C2583n2 c2583n2, C2583n2 c2583n3, C2583n2 c2583n4, C2583n2 c2583n5, C1667b6 c1667b6) {
        if (c2583n2 != null) {
            c2583n2.a(ny.d("RuntimeVisibleAnnotations"), c1667b6);
        }
        if (c2583n3 != null) {
            c2583n3.a(ny.d("RuntimeInvisibleAnnotations"), c1667b6);
        }
        if (c2583n4 != null) {
            c2583n4.a(ny.d("RuntimeVisibleTypeAnnotations"), c1667b6);
        }
        if (c2583n5 != null) {
            c2583n5.a(ny.d("RuntimeInvisibleTypeAnnotations"), c1667b6);
        }
    }

    static void a(int i, C2583n2[] c2583n2Arr, int i2, C1667b6 c1667b6) {
        int iB = (i2 * 2) + 1;
        for (int i3 = 0; i3 < i2; i3++) {
            C2583n2 c2583n2 = c2583n2Arr[i3];
            iB += c2583n2 == null ? 0 : c2583n2.b(null) - 8;
        }
        c1667b6.d(i);
        c1667b6.c(iB);
        c1667b6.b(i2);
        for (int i4 = 0; i4 < i2; i4++) {
            int i5 = 0;
            C2583n2 c2583n3 = null;
            for (C2583n2 c2583n4 = c2583n2Arr[i4]; c2583n4 != null; c2583n4 = c2583n4.g) {
                c2583n4.a();
                i5++;
                c2583n3 = c2583n4;
            }
            c1667b6.d(i5);
            while (c2583n3 != null) {
                C1667b6 c1667b7 = c2583n3.d;
                c1667b6.a(c1667b7.a, 0, c1667b7.b);
                c2583n3 = c2583n3.h;
            }
        }
    }
}
