package com.android.tools.r8.internal;

import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import com.android.dx.cf.attrib.AttBootstrapMethods;
import com.android.dx.cf.attrib.AttSourceDebugExtension;
import com.android.dx.cf.attrib.AttSynthetic;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ha, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1031Ha {
    public final int a;
    final byte[] b;
    private final int[] c;
    private final String[] d;
    private final C1064Ic[] e;
    private final int[] f;
    private final int g;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public C1031Ha(byte[] bArr) {
        this(bArr, 0);
        int length = bArr.length;
    }

    private int[] b(int i) {
        char[] cArr = new char[i];
        int iA = a();
        for (int iG = g(iA - 2); iG > 0; iG--) {
            String strC = c(iA, cArr);
            int iD = d(iA + 2);
            int i2 = iA + 6;
            if (AttBootstrapMethods.ATTRIBUTE_NAME.equals(strC)) {
                int iG2 = g(i2);
                int[] iArr = new int[iG2];
                int iG3 = iA + 8;
                for (int i3 = 0; i3 < iG2; i3++) {
                    iArr[i3] = iG3;
                    iG3 += (g(iG3 + 2) * 2) + 4;
                }
                return iArr;
            }
            iA = i2 + iD;
        }
        throw new IllegalArgumentException();
    }

    public final void a(int i, AbstractC1256Pa abstractC1256Pa) {
        a(abstractC1256Pa, new T3[0], i);
    }

    public final int c() {
        return this.g;
    }

    public final int d(int i) {
        byte[] bArr = this.b;
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }

    public final long e(int i) {
        return (((long) d(i)) << 32) | (((long) d(i + 4)) & 4294967295L);
    }

    public final short f(int i) {
        byte[] bArr = this.b;
        return (short) ((bArr[i + 1] & 255) | ((bArr[i] & 255) << 8));
    }

    public final int g(int i) {
        byte[] bArr = this.b;
        return (bArr[i + 1] & 255) | ((bArr[i] & 255) << 8);
    }

    public C1031Ha(byte[] bArr, int i) {
        this(bArr, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void a(AbstractC1256Pa abstractC1256Pa, T3[] t3Arr, int i) {
        AbstractC1256Pa abstractC1256Pa2;
        String str;
        C2248id c2248id;
        int i2;
        String str2;
        int i3;
        int i4;
        String str3;
        String str4;
        int i5;
        String str5;
        String str6;
        boolean z;
        int i6;
        String[] strArr;
        String[] strArr2;
        int i7;
        C2248id c2248id2 = new C2248id();
        c2248id2.a = t3Arr;
        c2248id2.b = i;
        char[] cArr = new char[this.g];
        c2248id2.c = cArr;
        int i8 = this.a;
        int iG = g(i8);
        String strB = b(i8 + 2, cArr);
        String strB2 = b(i8 + 4, cArr);
        int iG2 = g(i8 + 6);
        String[] strArr3 = new String[iG2];
        int i9 = i8 + 8;
        for (int i10 = 0; i10 < iG2; i10++) {
            strArr3[i10] = b(i9, cArr);
            i9 += 2;
        }
        int iA = a();
        int i11 = iG;
        int iG3 = g(iA - 2);
        String strA = null;
        String strC = null;
        String strC2 = null;
        int i12 = 0;
        String strB3 = null;
        int i13 = 0;
        int i14 = 0;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        String strB4 = null;
        int i20 = 0;
        int i21 = 0;
        int i22 = 0;
        T3 t3 = null;
        while (true) {
            C2248id c2248id3 = c2248id2;
            String str7 = "RuntimeInvisibleTypeAnnotations";
            String str8 = "RuntimeInvisibleAnnotations";
            String str9 = "RuntimeVisibleTypeAnnotations";
            int i23 = i9;
            if (iG3 > 0) {
                String strC3 = c(iA, cArr);
                String str10 = strA;
                int iD = d(iA + 2);
                int i24 = iA + 6;
                String str11 = strC;
                if ("SourceFile".equals(strC3)) {
                    strC = c(i24, cArr);
                    i7 = i24;
                    iD = iD;
                    i23 = i23;
                    strA = str10;
                } else {
                    if ("InnerClasses".equals(strC3)) {
                        i7 = i24;
                        i14 = i7;
                    } else if ("EnclosingMethod".equals(strC3)) {
                        i7 = i24;
                        i15 = i7;
                    } else {
                        if ("NestHost".equals(strC3)) {
                            i7 = i24;
                            strB4 = b(i24, cArr);
                        } else if ("NestMembers".equals(strC3)) {
                            i7 = i24;
                            i20 = i7;
                        } else if ("PermittedSubclasses".equals(strC3)) {
                            i7 = i24;
                            i21 = i7;
                        } else {
                            if ("Signature".equals(strC3)) {
                                strC2 = c(i24, cArr);
                            } else if ("RuntimeVisibleAnnotations".equals(strC3)) {
                                i7 = i24;
                                i16 = i7;
                            } else if ("RuntimeVisibleTypeAnnotations".equals(strC3)) {
                                i7 = i24;
                                i18 = i7;
                            } else if ("Deprecated".equals(strC3)) {
                                i11 |= 131072;
                            } else {
                                if (AttSynthetic.ATTRIBUTE_NAME.equals(strC3)) {
                                    i11 |= 4096;
                                } else if (AttSourceDebugExtension.ATTRIBUTE_NAME.equals(strC3)) {
                                    if (iD > this.b.length - i24) {
                                        throw new IllegalArgumentException();
                                    }
                                    i7 = i24;
                                    iD = iD;
                                    strA = a(new char[iD], i24, iD);
                                    strC = str11;
                                } else if ("RuntimeInvisibleAnnotations".equals(strC3)) {
                                    i7 = i24;
                                    i17 = i7;
                                } else if ("RuntimeInvisibleTypeAnnotations".equals(strC3)) {
                                    i7 = i24;
                                    i19 = i7;
                                } else if ("Record".equals(strC3)) {
                                    i11 |= 65536;
                                    i7 = i24;
                                    i22 = i7;
                                } else if ("Module".equals(strC3)) {
                                    i7 = i24;
                                    i12 = i7;
                                } else if ("ModuleMainClass".equals(strC3)) {
                                    i7 = i24;
                                    strB3 = b(i24, cArr);
                                } else if ("ModulePackages".equals(strC3)) {
                                    i7 = i24;
                                    i13 = i7;
                                } else {
                                    if (AttBootstrapMethods.ATTRIBUTE_NAME.equals(strC3)) {
                                        i7 = i24;
                                        iD = iD;
                                        strA = str10;
                                    } else {
                                        i7 = i24;
                                        iD = iD;
                                        T3 t3A = a(t3Arr, strC3, i7, iD, cArr, -1, null);
                                        t3A.c = t3;
                                        t3 = t3A;
                                        strA = str10;
                                        i11 = i11;
                                    }
                                    strC = str11;
                                }
                                iA = i7 + iD;
                                iG3--;
                                c2248id2 = c2248id3;
                                i9 = i23;
                            }
                            i7 = i24;
                        }
                        strA = str10;
                        strC = str11;
                    }
                    strA = str10;
                    strC = str11;
                }
                iA = i7 + iD;
                iG3--;
                c2248id2 = c2248id3;
                i9 = i23;
            } else {
                int i25 = i11;
                T3 t4 = t3;
                String str12 = strC;
                String str13 = strA;
                int[] iArr = this.c;
                String str14 = AttSynthetic.ATTRIBUTE_NAME;
                String str15 = "Deprecated";
                abstractC1256Pa.a(d(iArr[1] - 7), i25, strB, strC2, strB2, strArr3);
                if ((i & 2) != 0 || (str12 == null && str13 == null)) {
                    abstractC1256Pa2 = abstractC1256Pa;
                } else {
                    AbstractC1256Pa abstractC1256Pa3 = abstractC1256Pa;
                    abstractC1256Pa3.a(str12, str13);
                    abstractC1256Pa2 = abstractC1256Pa3;
                }
                int i26 = i12;
                if (i26 != 0) {
                    c2248id = c2248id3;
                    char[] cArr2 = c2248id.c;
                    int i27 = i26 + 6;
                    AbstractC3116uF abstractC3116uFA = abstractC1256Pa2.a(g(i26 + 2), b(i26, cArr2), c(i26 + 4, cArr2));
                    if (abstractC3116uFA == null) {
                        str = "RuntimeInvisibleTypeAnnotations";
                    } else {
                        String str16 = strB3;
                        if (str16 != null) {
                            abstractC3116uFA.a(str16);
                        }
                        int i28 = i13;
                        if (i28 != 0) {
                            int iG4 = g(i28);
                            int i29 = i28 + 2;
                            while (true) {
                                int i30 = iG4 - 1;
                                if (iG4 <= 0) {
                                    break;
                                }
                                abstractC3116uFA.b(b(i29, cArr2));
                                i29 += 2;
                                iG4 = i30;
                            }
                        }
                        int iG5 = g(i27);
                        int i31 = i26 + 8;
                        while (true) {
                            int i32 = iG5 - 1;
                            if (iG5 <= 0) {
                                break;
                            }
                            String strB5 = b(i31, cArr2);
                            int iG6 = g(i31 + 2);
                            String strC4 = c(i31 + 4, cArr2);
                            i31 += 6;
                            abstractC3116uFA.a(iG6, strB5, strC4);
                            iG5 = i32;
                        }
                        int iG7 = g(i31);
                        int i33 = i31 + 2;
                        while (true) {
                            int i34 = iG7 - 1;
                            if (iG7 <= 0) {
                                break;
                            }
                            String strB6 = b(i33, cArr2);
                            int iG8 = g(i33 + 2);
                            int iG9 = g(i33 + 4);
                            i33 += 6;
                            if (iG9 != 0) {
                                String[] strArr4 = new String[iG9];
                                int i35 = i33;
                                for (int i36 = 0; i36 < iG9; i36++) {
                                    strArr4[i36] = b(i35, cArr2);
                                    i35 += 2;
                                }
                                int i37 = i35;
                                strArr2 = strArr4;
                                i33 = i37;
                            } else {
                                strArr2 = null;
                            }
                            abstractC3116uFA.a(strB6, iG8, strArr2);
                            iG7 = i34;
                            str7 = str7;
                        }
                        str = str7;
                        int iG10 = g(i33);
                        int i38 = i33 + 2;
                        while (true) {
                            int i39 = iG10 - 1;
                            if (iG10 <= 0) {
                                break;
                            }
                            String strB7 = b(i38, cArr2);
                            int iG11 = g(i38 + 2);
                            int iG12 = g(i38 + 4);
                            i38 += 6;
                            if (iG12 != 0) {
                                String[] strArr5 = new String[iG12];
                                for (int i40 = 0; i40 < iG12; i40++) {
                                    strArr5[i40] = b(i38, cArr2);
                                    i38 += 2;
                                }
                                strArr = strArr5;
                            } else {
                                strArr = null;
                            }
                            abstractC3116uFA.b(strB7, iG11, strArr);
                            iG10 = i39;
                        }
                        int iG13 = g(i38);
                        int i41 = i38 + 2;
                        while (true) {
                            int i42 = iG13 - 1;
                            if (iG13 <= 0) {
                                break;
                            }
                            abstractC3116uFA.c(b(i41, cArr2));
                            i41 += 2;
                            iG13 = i42;
                        }
                        int iG14 = g(i41);
                        int i43 = i41 + 2;
                        while (true) {
                            int i44 = iG14 - 1;
                            if (iG14 <= 0) {
                                break;
                            }
                            String strB8 = b(i43, cArr2);
                            int iG15 = g(i43 + 2);
                            i43 += 4;
                            String[] strArr6 = new String[iG15];
                            for (int i45 = 0; i45 < iG15; i45++) {
                                strArr6[i45] = b(i43, cArr2);
                                i43 += 2;
                            }
                            abstractC3116uFA.a(strB8, strArr6);
                            iG14 = i44;
                        }
                        abstractC3116uFA.a();
                    }
                } else {
                    str = "RuntimeInvisibleTypeAnnotations";
                    c2248id = c2248id3;
                }
                String str17 = strB4;
                if (str17 != null) {
                    abstractC1256Pa2.a(str17);
                }
                int i46 = i15;
                if (i46 != 0) {
                    String strB9 = b(i46, cArr);
                    int iG16 = g(i46 + 2);
                    abstractC1256Pa2.a(strB9, iG16 == 0 ? null : c(this.c[iG16], cArr), iG16 == 0 ? null : c(this.c[iG16] + 2, cArr));
                }
                int i47 = i16;
                if (i47 != 0) {
                    int iG17 = g(i47);
                    int iA2 = i47 + 2;
                    while (true) {
                        int i48 = iG17 - 1;
                        if (iG17 <= 0) {
                            break;
                        }
                        iA2 = a(abstractC1256Pa2.a(c(iA2, cArr), true), iA2 + 2, true, cArr);
                        iG17 = i48;
                    }
                }
                int i49 = i17;
                if (i49 != 0) {
                    int iG18 = g(i49);
                    int iA3 = i49 + 2;
                    while (true) {
                        int i50 = iG18 - 1;
                        if (iG18 <= 0) {
                            break;
                        }
                        iA3 = a(abstractC1256Pa2.a(c(iA3, cArr), false), iA3 + 2, true, cArr);
                        iG18 = i50;
                    }
                }
                int i51 = i18;
                if (i51 != 0) {
                    int iG19 = g(i51);
                    int iA4 = i51 + 2;
                    while (true) {
                        int i52 = iG19 - 1;
                        if (iG19 <= 0) {
                            break;
                        }
                        int iA5 = a(c2248id, iA4);
                        iA4 = a(abstractC1256Pa2.a(c2248id.h, c2248id.i, c(iA5, cArr), true), iA5 + 2, true, cArr);
                        iG19 = i52;
                    }
                }
                int i53 = i19;
                if (i53 != 0) {
                    int iG20 = g(i53);
                    int iA6 = i53 + 2;
                    while (true) {
                        int i54 = iG20 - 1;
                        if (iG20 <= 0) {
                            break;
                        }
                        int iA7 = a(c2248id, iA6);
                        iA6 = a(abstractC1256Pa2.a(c2248id.h, c2248id.i, c(iA7, cArr), false), iA7 + 2, true, cArr);
                        iG20 = i54;
                    }
                }
                boolean z2 = true;
                T3 t5 = t4;
                while (t5 != null) {
                    T3 t6 = t5.c;
                    t5.c = null;
                    abstractC1256Pa2.a(t5);
                    t5 = t6;
                }
                int i55 = i20;
                T3 t7 = null;
                if (i55 != 0) {
                    int iG21 = g(i55);
                    int i56 = i55 + 2;
                    while (true) {
                        int i57 = iG21 - 1;
                        if (iG21 <= 0) {
                            break;
                        }
                        abstractC1256Pa2.b(b(i56, cArr));
                        i56 += 2;
                        iG21 = i57;
                    }
                }
                int i58 = i21;
                if (i58 != 0) {
                    int iG22 = g(i58);
                    int i59 = i58 + 2;
                    while (true) {
                        int i60 = iG22 - 1;
                        if (iG22 <= 0) {
                            break;
                        }
                        abstractC1256Pa2.c(b(i59, cArr));
                        i59 += 2;
                        iG22 = i60;
                    }
                }
                int i61 = i14;
                if (i61 != 0) {
                    int iG23 = g(i61);
                    int i62 = i61 + 2;
                    while (true) {
                        int i63 = iG23 - 1;
                        if (iG23 <= 0) {
                            break;
                        }
                        abstractC1256Pa2.a(g(i62 + 6), b(i62, cArr), b(i62 + 2, cArr), c(i62 + 4, cArr));
                        i62 += 8;
                        iG23 = i63;
                    }
                }
                int i64 = i22;
                if (i64 != 0) {
                    int iG24 = g(i64);
                    int i65 = i64 + 2;
                    while (true) {
                        int i66 = iG24 - 1;
                        if (iG24 <= 0) {
                            break;
                        }
                        char[] cArr3 = c2248id.c;
                        String strC5 = c(i65, cArr3);
                        String strC6 = c(i65 + 2, cArr3);
                        int iG25 = g(i65 + 4);
                        int i67 = i65 + 6;
                        T3 t3C = t7;
                        int i68 = 0;
                        int i69 = 0;
                        int i70 = 0;
                        int i71 = 0;
                        while (true) {
                            int i72 = iG25 - 1;
                            if (iG25 <= 0) {
                                break;
                            }
                            String strC7 = c(i67, cArr3);
                            int iD2 = d(i67 + 2);
                            int i73 = i67 + 6;
                            if ("Signature".equals(strC7)) {
                                t3C = c(i73, cArr3);
                                str5 = str;
                                z = true;
                                str6 = str8;
                                i6 = i73;
                            } else if ("RuntimeVisibleAnnotations".equals(strC7)) {
                                i68 = i73;
                                str5 = str;
                                z = true;
                                str6 = str8;
                                i6 = i68;
                            } else if ("RuntimeVisibleTypeAnnotations".equals(strC7)) {
                                i70 = i73;
                                str5 = str;
                                z = true;
                                str6 = str8;
                                i6 = i70;
                            } else if (str8.equals(strC7)) {
                                i69 = i73;
                                str5 = str;
                                z = true;
                                str6 = str8;
                                i6 = i69;
                            } else {
                                String str18 = str;
                                if (str18.equals(strC7)) {
                                    str5 = str18;
                                    str6 = str8;
                                    i71 = i73;
                                    z = true;
                                    i6 = i71;
                                } else {
                                    str5 = str18;
                                    str6 = str8;
                                    z = true;
                                    i6 = i73;
                                    T3 t3A2 = a(c2248id.a, strC7, i73, iD2, cArr3, -1, null);
                                    t3A2.c = t7;
                                    t7 = t3A2;
                                    t3C = t3C;
                                }
                            }
                            i67 = i6 + iD2;
                            cArr3 = cArr3;
                            z2 = z;
                            iG25 = i72;
                            str8 = str6;
                            i66 = i66;
                            str = str5;
                            strC6 = strC6;
                            strC5 = strC5;
                            str14 = str14;
                        }
                        char[] cArr4 = cArr3;
                        int i74 = i66;
                        String str19 = str14;
                        String str20 = str;
                        String str21 = str8;
                        boolean z3 = z2;
                        CQ cqB = abstractC1256Pa2.b(strC5, strC6, t3C);
                        if (cqB == null) {
                            i5 = i67;
                        } else {
                            int i75 = i68;
                            if (i75 != 0) {
                                int iG26 = g(i75);
                                int iA8 = i75 + 2;
                                while (true) {
                                    int i76 = iG26 - 1;
                                    if (iG26 <= 0) {
                                        break;
                                    }
                                    iA8 = a(cqB.a(c(iA8, cArr4), z3), iA8 + 2, z3, cArr4);
                                    iG26 = i76;
                                }
                            }
                            int i77 = i69;
                            if (i77 != 0) {
                                int iG27 = g(i77);
                                int iA9 = i77 + 2;
                                while (true) {
                                    int i78 = iG27 - 1;
                                    if (iG27 <= 0) {
                                        break;
                                    }
                                    iA9 = a(cqB.a(c(iA9, cArr4), false), iA9 + 2, z3, cArr4);
                                    iG27 = i78;
                                }
                            }
                            int i79 = i70;
                            if (i79 != 0) {
                                int iG28 = g(i79);
                                int iA10 = i79 + 2;
                                while (true) {
                                    int i80 = iG28 - 1;
                                    if (iG28 <= 0) {
                                        break;
                                    }
                                    int iA11 = a(c2248id, iA10);
                                    iA10 = a(cqB.a(c2248id.h, c2248id.i, c(iA11, cArr4), z3), iA11 + 2, z3, cArr4);
                                    iG28 = i80;
                                }
                            }
                            int i81 = i71;
                            if (i81 != 0) {
                                int iG29 = g(i81);
                                int iA12 = i81 + 2;
                                while (true) {
                                    int i82 = iG29 - 1;
                                    if (iG29 <= 0) {
                                        break;
                                    }
                                    int iA13 = a(c2248id, iA12);
                                    iA12 = a(cqB.a(c2248id.h, c2248id.i, c(iA13, cArr4), false), iA13 + 2, z3, cArr4);
                                    iG29 = i82;
                                    i67 = i67;
                                }
                            }
                            i5 = i67;
                            while (t7 != null) {
                                T3 t8 = t7.c;
                                t7.c = null;
                                cqB.a(t7);
                                t7 = t8;
                            }
                            cqB.a();
                        }
                        z2 = z3;
                        i65 = i5;
                        str8 = str21;
                        iG24 = i74;
                        str = str20;
                        str14 = str19;
                        t7 = null;
                    }
                }
                String str22 = str14;
                String str23 = str;
                String str24 = str8;
                int iG30 = g(i23);
                int i83 = i23 + 2;
                while (true) {
                    int i84 = iG30 - 1;
                    if (iG30 <= 0) {
                        break;
                    }
                    char[] cArr5 = c2248id.c;
                    int iG31 = g(i83);
                    String strC8 = c(i83 + 2, cArr5);
                    String strC9 = c(i83 + 4, cArr5);
                    int iG32 = g(i83 + 6);
                    int i85 = iG31;
                    int i86 = i83 + 8;
                    int i87 = 0;
                    int i88 = 0;
                    int i89 = 0;
                    T3 t9 = null;
                    String strC10 = null;
                    Object objA = null;
                    int i90 = 0;
                    while (true) {
                        int i91 = iG32 - 1;
                        if (iG32 <= 0) {
                            break;
                        }
                        String strC11 = c(i86, cArr5);
                        int iD3 = d(i86 + 2);
                        int i92 = i86 + 6;
                        i87 = i87;
                        if ("ConstantValue".equals(strC11)) {
                            int iG33 = g(i92);
                            if (iG33 == 0) {
                                i87 = i87;
                                i92 = i92;
                                cArr5 = cArr5;
                                str24 = str24;
                                str3 = str15;
                                str4 = str23;
                                objA = null;
                            } else {
                                objA = a(iG33, cArr5);
                                str3 = str15;
                                str4 = str23;
                            }
                        } else if ("Signature".equals(strC11)) {
                            strC10 = c(i92, cArr5);
                            i87 = i87;
                            i92 = i92;
                            cArr5 = cArr5;
                            str24 = str24;
                            str3 = str15;
                            str4 = str23;
                        } else {
                            str3 = str15;
                            if (str3.equals(strC11)) {
                                i85 |= 131072;
                                str3 = str3;
                                str4 = str23;
                            } else {
                                str22 = str22;
                                if (str22.equals(strC11)) {
                                    i85 |= 4096;
                                    i92 = i92;
                                } else if ("RuntimeVisibleAnnotations".equals(strC11)) {
                                    i89 = i92;
                                    i92 = i89;
                                } else if ("RuntimeVisibleTypeAnnotations".equals(strC11)) {
                                    str3 = str3;
                                    i87 = i92;
                                    i92 = i87;
                                    str22 = str22;
                                    str24 = str24;
                                    str4 = str23;
                                    cArr5 = cArr5;
                                } else {
                                    int i93 = i85;
                                    String str25 = str24;
                                    if (str25.equals(strC11)) {
                                        str3 = str3;
                                        i88 = i92;
                                        i92 = i88;
                                        str24 = str25;
                                        i85 = i93;
                                        str4 = str23;
                                        cArr5 = cArr5;
                                    } else {
                                        String str26 = str23;
                                        if (str26.equals(strC11)) {
                                            str3 = str3;
                                            i92 = i92;
                                            i90 = i92;
                                            str4 = str26;
                                            str22 = str22;
                                            str24 = str25;
                                            i85 = i93;
                                            cArr5 = cArr5;
                                        } else {
                                            str3 = str3;
                                            i92 = i92;
                                            str4 = str26;
                                            str24 = str25;
                                            str22 = str22;
                                            cArr5 = cArr5;
                                            T3 t3A3 = a(c2248id.a, strC11, i92, iD3, cArr5, -1, null);
                                            t3A3.c = t9;
                                            t9 = t3A3;
                                            i85 = i93;
                                            i87 = i87;
                                            i88 = i88;
                                            i89 = i89;
                                        }
                                    }
                                }
                                str24 = str24;
                                str4 = str23;
                                cArr5 = cArr5;
                            }
                        }
                        i86 = i92 + iD3;
                        i84 = i84;
                        cArr5 = cArr5;
                        iG32 = i91;
                        str15 = str3;
                        str23 = str4;
                        str24 = str24;
                    }
                    int i94 = i87;
                    int i95 = i88;
                    int i96 = i89;
                    char[] cArr6 = cArr5;
                    int i97 = i84;
                    String str27 = str24;
                    String str28 = str15;
                    String str29 = str23;
                    T3 t10 = t9;
                    int i98 = i85;
                    int i99 = i86;
                    AbstractC2252ii abstractC2252iiA = abstractC1256Pa.a(i98, strC8, strC9, strC10, objA);
                    if (abstractC2252iiA != null) {
                        if (i96 != 0) {
                            int iG34 = g(i96);
                            int iA14 = i96 + 2;
                            while (true) {
                                int i100 = iG34 - 1;
                                if (iG34 <= 0) {
                                    break;
                                }
                                iA14 = a(abstractC2252iiA.a(c(iA14, cArr6), true), iA14 + 2, true, cArr6);
                                iG34 = i100;
                            }
                        }
                        if (i95 != 0) {
                            int iG35 = g(i95);
                            int iA15 = i95 + 2;
                            while (true) {
                                int i101 = iG35 - 1;
                                if (iG35 <= 0) {
                                    break;
                                }
                                iA15 = a(abstractC2252iiA.a(c(iA15, cArr6), false), iA15 + 2, true, cArr6);
                                iG35 = i101;
                            }
                        }
                        if (i94 != 0) {
                            int iG36 = g(i94);
                            int iA16 = i94 + 2;
                            while (true) {
                                int i102 = iG36 - 1;
                                if (iG36 <= 0) {
                                    break;
                                }
                                int iA17 = a(c2248id, iA16);
                                iA16 = a(abstractC2252iiA.a(c2248id.h, c2248id.i, c(iA17, cArr6), true), iA17 + 2, true, cArr6);
                                iG36 = i102;
                            }
                        }
                        int i103 = i90;
                        if (i103 != 0) {
                            int iG37 = g(i103);
                            int iA18 = i103 + 2;
                            while (true) {
                                int i104 = iG37 - 1;
                                if (iG37 <= 0) {
                                    break;
                                }
                                int iA19 = a(c2248id, iA18);
                                iA18 = a(abstractC2252iiA.a(c2248id.h, c2248id.i, c(iA19, cArr6), false), iA19 + 2, true, cArr6);
                                iG37 = i104;
                            }
                        }
                        T3 t11 = t10;
                        while (t11 != null) {
                            T3 t12 = t11.c;
                            t11.c = null;
                            abstractC2252iiA.a(t11);
                            t11 = t12;
                        }
                        abstractC2252iiA.a();
                    }
                    iG30 = i97;
                    i83 = i99;
                    str15 = str28;
                    str23 = str29;
                    str24 = str27;
                }
                String str30 = str24;
                String str31 = str15;
                String str32 = str23;
                int iG38 = g(i83);
                int i105 = i83 + 2;
                while (true) {
                    int i106 = iG38 - 1;
                    if (iG38 <= 0) {
                        abstractC1256Pa.a();
                        return;
                    }
                    char[] cArr7 = c2248id.c;
                    c2248id.d = g(i105);
                    c2248id.e = c(i105 + 2, cArr7);
                    int i107 = i105 + 4;
                    c2248id.f = c(i107, cArr7);
                    int iG39 = g(i105 + 6);
                    int i108 = i105 + 8;
                    int i109 = 0;
                    int i110 = 0;
                    int i111 = 0;
                    int iG40 = 0;
                    T3 t13 = null;
                    boolean z4 = false;
                    String[] strArr7 = null;
                    int i112 = 0;
                    int i113 = 0;
                    int i114 = 0;
                    int i115 = 0;
                    int i116 = 0;
                    int i117 = 0;
                    int i118 = 0;
                    while (true) {
                        int i119 = iG39 - 1;
                        if (iG39 <= 0) {
                            break;
                        }
                        String strC12 = c(i108, cArr7);
                        i109 = i109;
                        int iD4 = d(i108 + 2);
                        i109 = i108 + 6;
                        i110 = i110;
                        if (!"Code".equals(strC12)) {
                            if ("Exceptions".equals(strC12)) {
                                int iG41 = g(i109);
                                String[] strArr8 = new String[iG41];
                                int i120 = i108 + 8;
                                i2 = i111;
                                for (int i121 = 0; i121 < iG41; i121++) {
                                    strArr8[i121] = b(i120, cArr7);
                                    i120 += 2;
                                }
                                i112 = i109;
                                i4 = i112;
                                strArr7 = strArr8;
                            } else {
                                i2 = i111;
                                if ("Signature".equals(strC12)) {
                                    iG40 = g(i109);
                                    i4 = i109;
                                } else {
                                    str2 = str31;
                                    if (str2.equals(strC12)) {
                                        c2248id.d |= 131072;
                                        i4 = i109;
                                        str2 = str2;
                                        i110 = i110;
                                        i111 = i2;
                                        i109 = i109;
                                        i3 = i107;
                                    } else if ("RuntimeVisibleAnnotations".equals(strC12)) {
                                        i4 = i109;
                                        str2 = str2;
                                        str9 = str9;
                                        i110 = i110;
                                        i111 = i2;
                                        str22 = str22;
                                        i3 = i107;
                                    } else if (str9.equals(strC12)) {
                                        i4 = i109;
                                        i116 = i4;
                                        str2 = str2;
                                        i110 = i110;
                                        i111 = i2;
                                        i109 = i109;
                                        i3 = i107;
                                    } else if ("AnnotationDefault".equals(strC12)) {
                                        i110 = i109;
                                        i4 = i110;
                                        str2 = str2;
                                        i111 = i2;
                                        i109 = i109;
                                        i3 = i107;
                                    } else {
                                        String str33 = str22;
                                        if (str33.equals(strC12)) {
                                            c2248id.d |= 4096;
                                            i4 = i109;
                                            str2 = str2;
                                            str9 = str9;
                                            i110 = i110;
                                            str32 = str32;
                                            str30 = str30;
                                            z4 = true;
                                            i109 = i109;
                                            i106 = i106;
                                            i3 = i107;
                                            str22 = str33;
                                            i111 = i2;
                                        } else {
                                            String str34 = str30;
                                            if (str34.equals(strC12)) {
                                                i4 = i109;
                                                i115 = i4;
                                                str2 = str2;
                                                str9 = str9;
                                                str32 = str32;
                                                i109 = i109;
                                                i106 = i106;
                                                i3 = i107;
                                            } else {
                                                i3 = i107;
                                                String str35 = str32;
                                                if (str35.equals(strC12)) {
                                                    i4 = i109;
                                                    i117 = i4;
                                                } else {
                                                    i4 = i109;
                                                    if ("RuntimeVisibleParameterAnnotations".equals(strC12)) {
                                                        i113 = i4;
                                                    } else if ("RuntimeInvisibleParameterAnnotations".equals(strC12)) {
                                                        i114 = i4;
                                                    } else if ("MethodParameters".equals(strC12)) {
                                                        i109 = i109;
                                                        str22 = str33;
                                                        str2 = str2;
                                                        str9 = str9;
                                                        i106 = i106;
                                                        i3 = i3;
                                                        i111 = i4;
                                                        str32 = str35;
                                                        str30 = str34;
                                                        i110 = i110;
                                                    } else {
                                                        str30 = str34;
                                                        str22 = str33;
                                                        str2 = str2;
                                                        str9 = str9;
                                                        i106 = i106;
                                                        i3 = i3;
                                                        str32 = str35;
                                                        T3 t3A4 = a(c2248id.a, strC12, i4, iD4, cArr7, -1, null);
                                                        t3A4.c = t13;
                                                        t13 = t3A4;
                                                        i109 = i109;
                                                        i110 = i110;
                                                        i111 = i2;
                                                        iG40 = iG40;
                                                    }
                                                }
                                                str32 = str35;
                                            }
                                            str30 = str34;
                                            i110 = i110;
                                            str22 = str33;
                                            i111 = i2;
                                        }
                                    }
                                }
                            }
                            i111 = i2;
                            str2 = str31;
                            i109 = i109;
                            i3 = i107;
                        } else if ((c2248id.b & 1) == 0) {
                            i4 = i109;
                            i118 = i4;
                            str2 = str31;
                            i109 = i109;
                            i3 = i107;
                        } else {
                            i2 = i111;
                            str2 = str31;
                            i4 = i109;
                            str2 = str2;
                            i110 = i110;
                            i111 = i2;
                            i109 = i109;
                            i3 = i107;
                        }
                        i108 = i4 + iD4;
                        i107 = i3;
                        iG39 = i119;
                        str30 = str30;
                        str22 = str22;
                        str32 = str32;
                        str31 = str2;
                        str9 = str9;
                        i106 = i106;
                    }
                    int i122 = i109;
                    int i123 = i110;
                    int i124 = i111;
                    int i125 = iG40;
                    String str36 = str9;
                    int i126 = i106;
                    String str37 = str22;
                    String str38 = str31;
                    String str39 = str32;
                    String str40 = str30;
                    T3 t14 = t13;
                    int i127 = i107;
                    int i128 = i108;
                    AbstractC1899eF abstractC1899eFA = abstractC1256Pa.a(c2248id.d, c2248id.e, c2248id.f, i125 == 0 ? null : d(i125, cArr7), strArr7);
                    if (abstractC1899eFA != null) {
                        if (abstractC1899eFA instanceof C1986fF) {
                            C1986fF c1986fF = (C1986fF) abstractC1899eFA;
                            if (c1986fF.a(this, z4, (c2248id.d & 131072) != 0, g(i127), i125, i112)) {
                                c1986fF.f(i105, i128 - i105);
                            }
                        }
                        if (i124 != 0 && (c2248id.b & 2) == 0) {
                            int iC = c(i124);
                            int i129 = i124 + 1;
                            while (true) {
                                int i130 = iC - 1;
                                if (iC <= 0) {
                                    break;
                                }
                                abstractC1899eFA.b(g(i129 + 2), c(i129, cArr7));
                                i129 += 4;
                                iC = i130;
                            }
                        }
                        if (i123 != 0) {
                            AbstractC2509m2 abstractC2509m2A = abstractC1899eFA.a();
                            a(abstractC2509m2A, i123, (String) null, cArr7);
                            if (abstractC2509m2A != null) {
                                abstractC2509m2A.a();
                            }
                        }
                        if (i122 != 0) {
                            int iG42 = g(i122);
                            int iA20 = i122 + 2;
                            while (true) {
                                int i131 = iG42 - 1;
                                if (iG42 <= 0) {
                                    break;
                                }
                                iA20 = a(abstractC1899eFA.a(c(iA20, cArr7), true), iA20 + 2, true, cArr7);
                                iG42 = i131;
                            }
                        }
                        int i132 = i115;
                        if (i132 != 0) {
                            int iG43 = g(i132);
                            int iA21 = i132 + 2;
                            while (true) {
                                int i133 = iG43 - 1;
                                if (iG43 <= 0) {
                                    break;
                                }
                                iA21 = a(abstractC1899eFA.a(c(iA21, cArr7), false), iA21 + 2, true, cArr7);
                                iG43 = i133;
                            }
                        }
                        int i134 = i116;
                        if (i134 != 0) {
                            int iG44 = g(i134);
                            int iA22 = i134 + 2;
                            while (true) {
                                int i135 = iG44 - 1;
                                if (iG44 <= 0) {
                                    break;
                                }
                                int iA23 = a(c2248id, iA22);
                                iA22 = a(abstractC1899eFA.c(c2248id.h, c2248id.i, c(iA23, cArr7), true), iA23 + 2, true, cArr7);
                                iG44 = i135;
                            }
                        }
                        int i136 = i117;
                        if (i136 != 0) {
                            int iG45 = g(i136);
                            int iA24 = i136 + 2;
                            while (true) {
                                int i137 = iG45 - 1;
                                if (iG45 <= 0) {
                                    break;
                                }
                                int iA25 = a(c2248id, iA24);
                                iA24 = a(abstractC1899eFA.c(c2248id.h, c2248id.i, c(iA25, cArr7), false), iA25 + 2, true, cArr7);
                                iG45 = i137;
                            }
                        }
                        if (i113 != 0) {
                            int iA26 = i113 + 1;
                            int i138 = this.b[i113] & 255;
                            abstractC1899eFA.a(i138, true);
                            char[] cArr8 = c2248id.c;
                            for (int i139 = 0; i139 < i138; i139++) {
                                int iG46 = g(iA26);
                                iA26 += 2;
                                while (true) {
                                    int i140 = iG46 - 1;
                                    if (iG46 > 0) {
                                        iA26 = a(abstractC1899eFA.a(i139, c(iA26, cArr8), true), iA26 + 2, true, cArr8);
                                        iG46 = i140;
                                    }
                                }
                            }
                        }
                        if (i114 != 0) {
                            int iA27 = i114 + 1;
                            int i141 = this.b[i114] & 255;
                            abstractC1899eFA.a(i141, false);
                            char[] cArr9 = c2248id.c;
                            for (int i142 = 0; i142 < i141; i142++) {
                                int iG47 = g(iA27);
                                iA27 += 2;
                                while (true) {
                                    int i143 = iG47 - 1;
                                    if (iG47 > 0) {
                                        iA27 = a(abstractC1899eFA.a(i142, c(iA27, cArr9), false), iA27 + 2, true, cArr9);
                                        iG47 = i143;
                                    }
                                }
                            }
                        }
                        T3 t15 = t14;
                        while (t15 != null) {
                            T3 t16 = t15.c;
                            t15.c = null;
                            abstractC1899eFA.a(t15);
                            t15 = t16;
                        }
                        int i144 = i118;
                        if (i144 != 0) {
                            abstractC1899eFA.b();
                            a(abstractC1899eFA, c2248id, i144);
                        }
                        abstractC1899eFA.c();
                    }
                    iG38 = i126;
                    i105 = i128;
                    str30 = str40;
                    str22 = str37;
                    str32 = str39;
                    str31 = str38;
                    str9 = str36;
                }
            }
        }
    }

    public final int c(int i) {
        return this.b[i] & 255;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:18:0x005d A[PHI: r9
      0x005d: PHI (r9v5 int) = (r9v0 int), (r9v1 int), (r9v6 int) binds: [B:12:0x004e, B:22:0x006c, B:17:0x005c] A[DONT_GENERATE, DONT_INLINE]] */
    C1031Ha(byte[] bArr, boolean z) {
        this.b = bArr;
        if (z && f(6) > 64) {
            throw new IllegalArgumentException("Unsupported class file major version " + ((int) f(6)));
        }
        int iG = g(8);
        this.c = new int[iG];
        this.d = new String[iG];
        boolean z2 = false;
        boolean z3 = false;
        int i = 1;
        int i2 = 10;
        int i3 = 0;
        while (i < iG) {
            int i4 = i + 1;
            int i5 = i2 + 1;
            this.c[i] = i5;
            int iG2 = 3;
            switch (bArr[i2]) {
                case 1:
                    iG2 = 3 + g(i5);
                    if (iG2 > i3) {
                        i = i4;
                        i3 = iG2;
                    } else {
                        i = i4;
                    }
                    i2 += iG2;
                    break;
                case 2:
                case 13:
                case 14:
                default:
                    throw new IllegalArgumentException();
                case 3:
                case 4:
                case 9:
                case 10:
                case 11:
                case 12:
                    i = i4;
                    iG2 = 5;
                    i2 += iG2;
                    break;
                case 5:
                case 6:
                    i += 2;
                    iG2 = 9;
                    i2 += iG2;
                    break;
                case 7:
                case 8:
                case 16:
                case 19:
                case 20:
                    i = i4;
                    i2 += iG2;
                    break;
                case 15:
                    iG2 = 4;
                    i = i4;
                    i2 += iG2;
                    break;
                case 17:
                    z2 = true;
                    z3 = true;
                    i = i4;
                    iG2 = 5;
                    i2 += iG2;
                    break;
                case 18:
                    z3 = true;
                    i = i4;
                    iG2 = 5;
                    i2 += iG2;
                    break;
            }
        }
        this.g = i3;
        this.a = i2;
        this.e = z2 ? new C1064Ic[iG] : null;
        this.f = z3 ? b(i3) : null;
    }

    public final String c(int i, char[] cArr) {
        int iG = g(i);
        if (i == 0 || iG == 0) {
            return null;
        }
        return d(iG, cArr);
    }

    final String d(int i, char[] cArr) {
        String[] strArr = this.d;
        String str = strArr[i];
        if (str != null) {
            return str;
        }
        int i2 = this.c[i];
        String strA = a(cArr, i2 + 2, g(i2));
        strArr[i] = strA;
        return strA;
    }

    public final int b() {
        return this.c.length;
    }

    private String b(int i, char[] cArr) {
        return c(this.c[g(i)], cArr);
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached with updateSeq = 40501. Try increasing type updates limit count.
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:79)
        */
    private void a(com.android.tools.r8.internal.AbstractC1899eF r45, com.android.tools.r8.internal.C2248id r46, int r47) {
        /*
            Method dump skipped, instruction units count: 4050
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.C1031Ha.a(com.android.tools.r8.internal.eF, com.android.tools.r8.internal.id, int):void");
    }

    private static C3263wA a(int i, C3263wA[] c3263wAArr) {
        if (c3263wAArr[i] == null) {
            c3263wAArr[i] = new C3263wA();
        }
        C3263wA c3263wA = c3263wAArr[i];
        c3263wA.b = (short) (c3263wA.b & (-2));
        return c3263wA;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x004d A[FALL_THROUGH] */
    private int[] a(AbstractC1899eF abstractC1899eF, C2248id c2248id, int i, boolean z) {
        int i2;
        char[] cArr = c2248id.c;
        int iG = g(i);
        int[] iArr = new int[iG];
        int iA = i + 2;
        for (int i3 = 0; i3 < iG; i3++) {
            iArr[i3] = iA;
            int iD = d(iA);
            int i4 = iD >>> 24;
            if (i4 != 23) {
                switch (i4) {
                    default:
                        switch (i4) {
                            case 64:
                            case 65:
                                int iG2 = g(iA + 1);
                                i2 = iA + 3;
                                while (true) {
                                    int i5 = iG2 - 1;
                                    if (iG2 > 0) {
                                        int iG3 = g(i2);
                                        int iG4 = g(i2 + 2);
                                        i2 += 6;
                                        a(iG3, c2248id.g);
                                        a(iG3 + iG4, c2248id.g);
                                        iG2 = i5;
                                    }
                                    break;
                                }
                                break;
                            case 66:
                            case 67:
                            case 68:
                            case 69:
                            case 70:
                                break;
                            case 71:
                            case 72:
                            case 73:
                            case 74:
                            case 75:
                                i2 = iA + 4;
                                break;
                            default:
                                throw new IllegalArgumentException();
                        }
                    case 16:
                    case 17:
                    case 18:
                        i2 = iA + 3;
                        break;
                }
            } else {
                i2 = iA + 3;
            }
            int iC = c(i2);
            if (i4 == 66) {
                C00 c00 = iC != 0 ? new C00(i2, this.b) : null;
                int i6 = (iC * 2) + 1 + i2;
                iA = a(abstractC1899eF.b(iD & InputDeviceCompat.SOURCE_ANY, c00, c(i6, cArr), z), i6 + 2, true, cArr);
            } else {
                iA = a((AbstractC2509m2) null, (iC * 2) + 3 + i2, true, cArr);
            }
        }
        return iArr;
    }

    private int a(int[] iArr, int i) {
        if (iArr == null || i >= iArr.length || c(iArr[i]) < 67) {
            return -1;
        }
        return g(iArr[i] + 1);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:13:0x0025  */
    /* JADX WARN: Code duplicated, block: B:18:0x0070  */
    private int a(C2248id c2248id, int i) {
        int i2;
        int i3;
        int iD = d(i);
        int i4 = iD >>> 24;
        if (i4 != 0 && i4 != 1) {
            switch (i4) {
                case 16:
                case 17:
                case 18:
                case 23:
                    i2 = iD & InputDeviceCompat.SOURCE_ANY;
                    i3 = i + 3;
                    break;
                case 19:
                case 20:
                case 21:
                    i2 = iD & ViewCompat.MEASURED_STATE_MASK;
                    i3 = i + 1;
                    break;
                case 22:
                    i2 = iD & (-65536);
                    i3 = i + 2;
                    break;
                default:
                    switch (i4) {
                        case 64:
                        case 65:
                            i2 = iD & ViewCompat.MEASURED_STATE_MASK;
                            int iG = g(i + 1);
                            i3 = i + 3;
                            c2248id.j = new C3263wA[iG];
                            c2248id.k = new C3263wA[iG];
                            c2248id.l = new int[iG];
                            for (int i5 = 0; i5 < iG; i5++) {
                                int iG2 = g(i3);
                                int iG3 = g(i3 + 2);
                                int iG4 = g(i3 + 4);
                                i3 += 6;
                                c2248id.j[i5] = a(iG2, c2248id.g);
                                c2248id.k[i5] = a(iG2 + iG3, c2248id.g);
                                c2248id.l[i5] = iG4;
                            }
                            break;
                        case 66:
                            i2 = iD & InputDeviceCompat.SOURCE_ANY;
                            i3 = i + 3;
                            break;
                        case 67:
                        case 68:
                        case 69:
                        case 70:
                            i2 = iD & ViewCompat.MEASURED_STATE_MASK;
                            i3 = i + 3;
                            break;
                        case 71:
                        case 72:
                        case 73:
                        case 74:
                        case 75:
                            i2 = iD & (-16776961);
                            i3 = i + 4;
                            break;
                        default:
                            throw new IllegalArgumentException();
                    }
                    break;
            }
        } else {
            i2 = iD & (-65536);
            i3 = i + 2;
        }
        c2248id.h = i2;
        int iC = c(i3);
        c2248id.i = iC == 0 ? null : new C00(i3, this.b);
        return (iC * 2) + i3 + 1;
    }

    private int a(AbstractC2509m2 abstractC2509m2, int i, boolean z, char[] cArr) {
        int iG = g(i);
        int iA = i + 2;
        if (!z) {
            while (true) {
                int i2 = iG - 1;
                if (iG <= 0) {
                    break;
                }
                iA = a(abstractC2509m2, iA, (String) null, cArr);
                iG = i2;
            }
        } else {
            while (true) {
                int i3 = iG - 1;
                if (iG <= 0) {
                    break;
                }
                iA = a(abstractC2509m2, iA + 2, c(iA, cArr), cArr);
                iG = i3;
            }
        }
        if (abstractC2509m2 != null) {
            abstractC2509m2.a();
        }
        return iA;
    }

    /* JADX WARN: Code duplicated, block: B:98:0x020a  */
    private int a(AbstractC2509m2 abstractC2509m2, int i, String str, char[] cArr) {
        Object obj;
        int i2 = 0;
        if (abstractC2509m2 == null) {
            int i3 = this.b[i] & 255;
            if (i3 == 64) {
                return a((AbstractC2509m2) null, i + 3, true, cArr);
            }
            if (i3 != 91) {
                return i3 != 101 ? i + 3 : i + 5;
            }
            return a((AbstractC2509m2) null, i + 1, false, cArr);
        }
        int i4 = i + 1;
        int i5 = this.b[i] & 255;
        if (i5 == 64) {
            return a(abstractC2509m2.a(str, c(i4, cArr)), i + 3, true, cArr);
        }
        if (i5 == 70) {
            abstractC2509m2.a(str, a(g(i4), cArr));
        } else if (i5 == 83) {
            abstractC2509m2.a(str, Short.valueOf((short) d(this.c[g(i4)])));
        } else if (i5 == 99) {
            abstractC2509m2.a(str, CZ.f(c(i4, cArr)));
        } else {
            if (i5 == 101) {
                abstractC2509m2.a(str, c(i4, cArr), c(i + 3, cArr));
                return i + 5;
            }
            if (i5 == 115) {
                abstractC2509m2.a(str, c(i4, cArr));
            } else if (i5 == 73 || i5 == 74) {
                abstractC2509m2.a(str, a(g(i4), cArr));
            } else if (i5 == 90) {
                if (d(this.c[g(i4)]) == 0) {
                    obj = Boolean.FALSE;
                } else {
                    obj = Boolean.TRUE;
                }
                abstractC2509m2.a(str, obj);
            } else {
                if (i5 == 91) {
                    int iG = g(i4);
                    int i6 = i + 3;
                    if (iG == 0) {
                        return a(abstractC2509m2.a(str), i + 1, false, cArr);
                    }
                    int i7 = this.b[i6] & 255;
                    if (i7 == 70) {
                        float[] fArr = new float[iG];
                        while (i2 < iG) {
                            fArr[i2] = Float.intBitsToFloat(d(this.c[g(i6 + 1)]));
                            i6 += 3;
                            i2++;
                        }
                        abstractC2509m2.a(str, fArr);
                    } else if (i7 == 83) {
                        short[] sArr = new short[iG];
                        while (i2 < iG) {
                            sArr[i2] = (short) d(this.c[g(i6 + 1)]);
                            i6 += 3;
                            i2++;
                        }
                        abstractC2509m2.a(str, sArr);
                    } else if (i7 == 90) {
                        boolean[] zArr = new boolean[iG];
                        for (int i8 = 0; i8 < iG; i8++) {
                            zArr[i8] = d(this.c[g(i6 + 1)]) != 0;
                            i6 += 3;
                        }
                        abstractC2509m2.a(str, zArr);
                    } else if (i7 == 73) {
                        int[] iArr = new int[iG];
                        while (i2 < iG) {
                            iArr[i2] = d(this.c[g(i6 + 1)]);
                            i6 += 3;
                            i2++;
                        }
                        abstractC2509m2.a(str, iArr);
                    } else if (i7 == 74) {
                        long[] jArr = new long[iG];
                        while (i2 < iG) {
                            jArr[i2] = e(this.c[g(i6 + 1)]);
                            i6 += 3;
                            i2++;
                        }
                        abstractC2509m2.a(str, jArr);
                    } else {
                        switch (i7) {
                            case 66:
                                byte[] bArr = new byte[iG];
                                while (i2 < iG) {
                                    bArr[i2] = (byte) d(this.c[g(i6 + 1)]);
                                    i6 += 3;
                                    i2++;
                                }
                                abstractC2509m2.a(str, bArr);
                                break;
                            case 67:
                                char[] cArr2 = new char[iG];
                                while (i2 < iG) {
                                    cArr2[i2] = (char) d(this.c[g(i6 + 1)]);
                                    i6 += 3;
                                    i2++;
                                }
                                abstractC2509m2.a(str, cArr2);
                                break;
                            case 68:
                                double[] dArr = new double[iG];
                                while (i2 < iG) {
                                    dArr[i2] = Double.longBitsToDouble(e(this.c[g(i6 + 1)]));
                                    i6 += 3;
                                    i2++;
                                }
                                abstractC2509m2.a(str, dArr);
                                break;
                            default:
                                return a(abstractC2509m2.a(str), i + 1, false, cArr);
                        }
                    }
                    return i6;
                }
                switch (i5) {
                    case 66:
                        abstractC2509m2.a(str, Byte.valueOf((byte) d(this.c[g(i4)])));
                        break;
                    case 67:
                        abstractC2509m2.a(str, Character.valueOf((char) d(this.c[g(i4)])));
                        break;
                    case 68:
                        abstractC2509m2.a(str, a(g(i4), cArr));
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }
        return i + 3;
    }

    private int a(int i, Object[] objArr, int i2, char[] cArr, C3263wA[] c3263wAArr) {
        int i3 = i + 1;
        switch (this.b[i] & 255) {
            case 0:
                objArr[i2] = JK.a;
                return i3;
            case 1:
                objArr[i2] = JK.b;
                return i3;
            case 2:
                objArr[i2] = JK.c;
                return i3;
            case 3:
                objArr[i2] = JK.d;
                return i3;
            case 4:
                objArr[i2] = JK.e;
                return i3;
            case 5:
                objArr[i2] = JK.f;
                return i3;
            case 6:
                objArr[i2] = JK.g;
                return i3;
            case 7:
                objArr[i2] = b(i3, cArr);
                break;
            case 8:
                objArr[i2] = a(g(i3), c3263wAArr);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return i + 3;
    }

    final int a() {
        int i = this.a;
        int iG = (g(i + 6) * 2) + i + 8;
        int iG2 = g(iG);
        int iD = iG + 2;
        while (true) {
            int i2 = iG2 - 1;
            if (iG2 <= 0) {
                break;
            }
            int iG3 = g(iD + 6);
            iD += 8;
            while (true) {
                int i3 = iG3 - 1;
                if (iG3 > 0) {
                    iD += d(iD + 2) + 6;
                    iG3 = i3;
                }
            }
            iG2 = i2;
        }
        int iG4 = g(iD);
        int iD2 = iD + 2;
        while (true) {
            int i4 = iG4 - 1;
            if (iG4 <= 0) {
                return iD2 + 2;
            }
            int iG5 = g(iD2 + 6);
            iD2 += 8;
            while (true) {
                int i5 = iG5 - 1;
                if (iG5 > 0) {
                    iD2 += d(iD2 + 2) + 6;
                    iG5 = i5;
                }
            }
            iG4 = i4;
        }
    }

    private T3 a(T3[] t3Arr, String str, int i, int i2, char[] cArr, int i3, C3263wA[] c3263wAArr) {
        for (T3 t3 : t3Arr) {
            if (t3.a.equals(str)) {
                return t3.a(this, i, i2);
            }
        }
        return new T3(str).a(this, i, i2);
    }

    public final int a(int i) {
        return this.c[i];
    }

    private String a(char[] cArr, int i, int i2) {
        int i3;
        int i4 = i2 + i;
        byte[] bArr = this.b;
        int i5 = 0;
        while (i < i4) {
            int i6 = i + 1;
            byte b = bArr[i];
            if ((b & 128) == 0) {
                cArr[i5] = (char) (b & 127);
                i5++;
                i = i6;
            } else {
                if ((b & 224) == 192) {
                    i3 = i5 + 1;
                    i += 2;
                    cArr[i5] = (char) (((b & 31) << 6) + (bArr[i6] & 63));
                } else {
                    i3 = i5 + 1;
                    int i7 = i + 2;
                    i += 3;
                    cArr[i5] = (char) (((b & 15) << 12) + ((bArr[i6] & 63) << 6) + (bArr[i7] & 63));
                }
                i5 = i3;
            }
        }
        return new String(cArr, 0, i5);
    }

    public final Object a(int i, char[] cArr) {
        int[] iArr = this.c;
        int i2 = iArr[i];
        byte b = this.b[i2 - 1];
        switch (b) {
            case 3:
                return Integer.valueOf(d(i2));
            case 4:
                return Float.valueOf(Float.intBitsToFloat(d(i2)));
            case 5:
                return Long.valueOf(e(i2));
            case 6:
                return Double.valueOf(Double.longBitsToDouble(e(i2)));
            case 7:
                return CZ.d(c(i2, cArr));
            case 8:
                return c(i2, cArr);
            default:
                switch (b) {
                    case 15:
                        int iC = c(i2);
                        int i3 = this.c[g(i2 + 1)];
                        int i4 = this.c[g(i3 + 2)];
                        return new C1944ek(iC, b(i3, cArr), c(i4, cArr), c(i4 + 2, cArr), this.b[i3 - 1] == 11);
                    case 16:
                        return CZ.c(c(i2, cArr));
                    case 17:
                        C1064Ic c1064Ic = this.e[i];
                        if (c1064Ic != null) {
                            return c1064Ic;
                        }
                        int i5 = iArr[g(i2 + 2)];
                        String strC = c(i5, cArr);
                        String strC2 = c(i5 + 2, cArr);
                        int i6 = this.f[g(i2)];
                        C1944ek c1944ek = (C1944ek) a(g(i6), cArr);
                        int iG = g(i6 + 2);
                        Object[] objArr = new Object[iG];
                        int i7 = i6 + 4;
                        for (int i8 = 0; i8 < iG; i8++) {
                            objArr[i8] = a(g(i7), cArr);
                            i7 += 2;
                        }
                        C1064Ic[] c1064IcArr = this.e;
                        C1064Ic c1064Ic2 = new C1064Ic(strC, strC2, c1944ek, objArr);
                        c1064IcArr[i] = c1064Ic2;
                        return c1064Ic2;
                    default:
                        throw new IllegalArgumentException();
                }
        }
    }
}
