package com.android.tools.r8.internal;

import com.android.tools.r8.AbstractC0402a;
import net.bytebuddy.implementation.auxiliary.TypeProxy;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Pi, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C1267Pi {
    C3263wA a;
    private int[] b;
    private int[] c;
    private int[] d;
    private int[] e;
    private short f;
    private short g;
    private int h;
    private int[] i;

    C1267Pi(C3263wA c3263wA) {
        this.a = c3263wA;
    }

    private void b(int i, int i2) {
        if (this.d == null) {
            this.d = new int[10];
        }
        int length = this.d.length;
        if (i >= length) {
            int[] iArr = new int[Math.max(i + 1, length * 2)];
            System.arraycopy(this.d, 0, iArr, 0, length);
            this.d = iArr;
        }
        this.d[i] = i2;
    }

    final void a(C1267Pi c1267Pi) {
        this.b = c1267Pi.b;
        this.c = c1267Pi.c;
        this.f = (short) 0;
        this.d = c1267Pi.d;
        this.e = c1267Pi.e;
        this.g = c1267Pi.g;
        this.h = c1267Pi.h;
        this.i = c1267Pi.i;
    }

    static int a(NY ny, Object obj) {
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue() | 4194304;
        }
        if (obj instanceof String) {
            return a(ny, CZ.d((String) obj).b(), 0);
        }
        return ny.b(((C3263wA) obj).e, "") | 12582912;
    }

    private void b(int i) {
        if (this.e == null) {
            this.e = new int[10];
        }
        int length = this.e.length;
        short s = this.g;
        if (s >= length) {
            int[] iArr = new int[Math.max(s + 1, length * 2)];
            System.arraycopy(this.e, 0, iArr, 0, length);
            this.e = iArr;
        }
        int[] iArr2 = this.e;
        short s2 = this.g;
        short s3 = (short) (s2 + 1);
        this.g = s3;
        iArr2[s2] = i;
        short s4 = (short) (this.f + s3);
        C3263wA c3263wA = this.a;
        if (s4 > c3263wA.i) {
            c3263wA.i = s4;
        }
    }

    private static int a(NY ny, String str, int i) {
        char cCharAt = str.charAt(i);
        int iE = 4194306;
        if (cCharAt == 'F') {
            return 4194306;
        }
        if (cCharAt == 'L') {
            return ny.e(AbstractC0402a.a(str, 1, i + 1)) | 8388608;
        }
        if (cCharAt != 'S') {
            if (cCharAt == 'V') {
                return 0;
            }
            if (cCharAt != 'I') {
                if (cCharAt == 'J') {
                    return 4194308;
                }
                if (cCharAt != 'Z') {
                    if (cCharAt != '[') {
                        switch (cCharAt) {
                            case 'B':
                            case 'C':
                                break;
                            case 'D':
                                return 4194307;
                            default:
                                throw new IllegalArgumentException();
                        }
                    } else {
                        int i2 = i + 1;
                        while (str.charAt(i2) == '[') {
                            i2++;
                        }
                        char cCharAt2 = str.charAt(i2);
                        if (cCharAt2 != 'F') {
                            if (cCharAt2 == 'L') {
                                iE = ny.e(AbstractC0402a.a(str, 1, i2 + 1)) | 8388608;
                            } else if (cCharAt2 == 'S') {
                                iE = 4194316;
                            } else if (cCharAt2 == 'Z') {
                                iE = 4194313;
                            } else if (cCharAt2 == 'I') {
                                iE = 4194305;
                            } else if (cCharAt2 != 'J') {
                                switch (cCharAt2) {
                                    case 'B':
                                        iE = 4194314;
                                        break;
                                    case 'C':
                                        iE = 4194315;
                                        break;
                                    case 'D':
                                        iE = 4194307;
                                        break;
                                    default:
                                        throw new IllegalArgumentException();
                                }
                            } else {
                                iE = 4194308;
                            }
                        }
                        return ((i2 - i) << 26) | iE;
                    }
                }
            }
        }
        return 4194305;
    }

    private int b() {
        short s = this.g;
        if (s > 0) {
            int[] iArr = this.e;
            short s2 = (short) (s - 1);
            this.g = s2;
            return iArr[s2];
        }
        short s3 = (short) (this.f - 1);
        this.f = s3;
        return (-s3) | 20971520;
    }

    final void a(NY ny, int i, String str, int i2) {
        int i3;
        int[] iArr = new int[i2];
        this.b = iArr;
        this.c = new int[0];
        if ((i & 8) == 0) {
            i3 = 1;
            if ((i & 262144) == 0) {
                iArr[0] = ny.e(ny.b()) | 8388608;
            } else {
                iArr[0] = 4194310;
            }
        } else {
            i3 = 0;
        }
        for (CZ cz : CZ.a(str)) {
            int iA = a(ny, cz.b(), 0);
            int[] iArr2 = this.b;
            int i4 = i3 + 1;
            iArr2[i3] = iA;
            if (iA == 4194308 || iA == 4194307) {
                i3 += 2;
                iArr2[i4] = 4194304;
            } else {
                i3 = i4;
            }
        }
        while (i3 < i2) {
            this.b[i3] = 4194304;
            i3++;
        }
    }

    final void a(NY ny, int i, Object[] objArr, int i2, Object[] objArr2) {
        int i3 = 0;
        for (int i4 = 0; i4 < i; i4++) {
            int i5 = i3 + 1;
            this.b[i3] = a(ny, objArr[i4]);
            Object obj = objArr[i4];
            if (obj == JK.e || obj == JK.d) {
                i3 += 2;
                this.b[i5] = 4194304;
            } else {
                i3 = i5;
            }
        }
        while (true) {
            int[] iArr = this.b;
            if (i3 >= iArr.length) {
                break;
            }
            iArr[i3] = 4194304;
            i3++;
        }
        int i6 = 0;
        for (int i7 = 0; i7 < i2; i7++) {
            Object obj2 = objArr2[i7];
            if (obj2 == JK.e || obj2 == JK.d) {
                i6++;
            }
        }
        this.c = new int[i6 + i2];
        int i8 = 0;
        for (int i9 = 0; i9 < i2; i9++) {
            int i10 = i8 + 1;
            this.c[i8] = a(ny, objArr2[i9]);
            Object obj3 = objArr2[i9];
            if (obj3 == JK.e || obj3 == JK.d) {
                i8 += 2;
                this.c[i10] = 4194304;
            } else {
                i8 = i10;
            }
        }
        this.g = (short) 0;
        this.h = 0;
    }

    final int a() {
        return this.c.length;
    }

    private void a(NY ny, String str) {
        int i = 0;
        if (str.charAt(0) == '(') {
            int iMax = 1;
            while (str.charAt(iMax) != ')') {
                while (str.charAt(iMax) == '[') {
                    iMax++;
                }
                int i2 = iMax + 1;
                iMax = str.charAt(iMax) == 'L' ? Math.max(i2, str.indexOf(59, i2) + 1) : i2;
            }
            i = 1 + iMax;
        }
        int iA = a(ny, str, i);
        if (iA != 0) {
            b(iA);
            if (iA == 4194308 || iA == 4194307) {
                b(4194304);
            }
        }
    }

    private void a(int i) {
        short s = this.g;
        if (s >= i) {
            this.g = (short) (s - i);
        } else {
            this.f = (short) (this.f - (i - s));
            this.g = (short) 0;
        }
    }

    private void a(String str) {
        char cCharAt = str.charAt(0);
        if (cCharAt == '(') {
            a((CZ.b(str) >> 2) - 1);
        } else if (cCharAt != 'J' && cCharAt != 'D') {
            a(1);
        } else {
            a(2);
        }
    }

    /* JADX WARN: Code duplicated, block: B:19:0x003b  */
    /* JADX WARN: Code duplicated, block: B:22:0x0045  */
    /* JADX WARN: Code duplicated, block: B:23:0x0051 A[LOOP:0: B:7:0x000d->B:23:0x0051, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:25:0x0037 A[SYNTHETIC] */
    private int a(NY ny, int i) {
        int i2;
        int iE;
        if (i == 4194310 || ((-4194304) & i) == 12582912) {
            for (int i3 = 0; i3 < this.h; i3++) {
                int i4 = this.i[i3];
                int i5 = (-67108864) & i4;
                int i6 = 62914560 & i4;
                int i7 = i4 & 1048575;
                if (i6 == 16777216) {
                    i2 = this.b[i7];
                } else {
                    if (i6 == 20971520) {
                        int[] iArr = this.c;
                        i2 = iArr[iArr.length - i7];
                    }
                    if (i == i4) {
                        if (i == 4194310) {
                            iE = ny.e(ny.b());
                        } else {
                            iE = ny.e(ny.c(i & 1048575).e);
                        }
                        return iE | 8388608;
                    }
                }
                i4 = i2 + i5;
                if (i == i4) {
                    if (i == 4194310) {
                        iE = ny.e(ny.b());
                    } else {
                        iE = ny.e(ny.c(i & 1048575).e);
                    }
                    return iE | 8388608;
                }
            }
        }
        return i;
    }

    /* JADX WARN: Code duplicated, block: B:128:0x027d  */
    /* JADX WARN: Code duplicated, block: B:130:0x0293  */
    /* JADX WARN: Code duplicated, block: B:137:0x02dd  */
    /* JADX WARN: Code duplicated, block: B:139:0x02f3  */
    /* JADX WARN: Code duplicated, block: B:38:0x00bd  */
    /* JADX WARN: Code duplicated, block: B:39:0x00c5  */
    /* JADX WARN: Code duplicated, block: B:40:0x00ca  */
    /* JADX WARN: Code duplicated, block: B:41:0x00d2  */
    void a(int i, int i2, MY my, NY ny) {
        int i3;
        int i4;
        int i5;
        switch (i) {
            case 0:
                return;
            case 1:
                b(4194309);
                return;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 16:
            case 17:
                b(4194305);
                return;
            case 9:
            case 10:
                b(4194308);
                b(4194304);
                return;
            case 11:
            case 12:
            case 13:
                b(4194306);
                return;
            case 14:
            case 15:
                b(4194307);
                b(4194304);
                return;
            case 18:
                int i6 = my.b;
                switch (i6) {
                    case 3:
                        b(4194305);
                        return;
                    case 4:
                        b(4194306);
                        return;
                    case 5:
                        b(4194308);
                        b(4194304);
                        return;
                    case 6:
                        b(4194307);
                        b(4194304);
                        return;
                    case 7:
                        b(ny.e(TypeProxy.SilentConstruction.Appender.JAVA_LANG_CLASS_INTERNAL_NAME) | 8388608);
                        return;
                    case 8:
                        b(ny.e("java/lang/String") | 8388608);
                        return;
                    default:
                        switch (i6) {
                            case 15:
                                b(ny.e("java/lang/invoke/MethodHandle") | 8388608);
                                return;
                            case 16:
                                b(ny.e("java/lang/invoke/MethodType") | 8388608);
                                return;
                            case 17:
                                a(ny, my.e);
                                return;
                            default:
                                throw new AssertionError();
                        }
                }
            default:
                switch (i) {
                    case 21:
                        b(4194305);
                        return;
                    case 22:
                        b(4194308);
                        b(4194304);
                        return;
                    case 23:
                        b(4194306);
                        return;
                    case 24:
                        b(4194307);
                        b(4194304);
                        return;
                    case 25:
                        int[] iArr = this.d;
                        if (iArr == null || i2 >= iArr.length) {
                            i3 = i2 | 16777216;
                        } else {
                            i3 = iArr[i2];
                            if (i3 == 0) {
                                i3 = i2 | 16777216;
                                iArr[i2] = i3;
                            }
                        }
                        b(i3);
                        return;
                    default:
                        switch (i) {
                            case 46:
                            case 51:
                            case 52:
                            case 53:
                                a(2);
                                b(4194305);
                                return;
                            case 47:
                                a(2);
                                b(4194308);
                                b(4194304);
                                return;
                            case 48:
                                a(2);
                                b(4194306);
                                return;
                            case 49:
                                a(2);
                                b(4194307);
                                b(4194304);
                                return;
                            case 50:
                                a(1);
                                int iB = b();
                                if (iB != 4194309) {
                                    iB -= 67108864;
                                }
                                b(iB);
                                return;
                            case 54:
                            case 56:
                            case 58:
                                b(i2, b());
                                if (i2 > 0) {
                                    int i7 = i2 - 1;
                                    int[] iArr2 = this.d;
                                    if (iArr2 == null || i7 >= iArr2.length) {
                                        i4 = i7 | 16777216;
                                    } else {
                                        i4 = iArr2[i7];
                                        if (i4 == 0) {
                                            i4 = i7 | 16777216;
                                            iArr2[i7] = i4;
                                        }
                                    }
                                    if (i4 == 4194308 || i4 == 4194307) {
                                        b(i7, 4194304);
                                        return;
                                    }
                                    int i8 = i4 & 62914560;
                                    if (i8 == 16777216 || i8 == 20971520) {
                                        b(i7, i4 | 1048576);
                                        return;
                                    }
                                    return;
                                }
                                return;
                            case 55:
                            case 57:
                                a(1);
                                b(i2, b());
                                b(i2 + 1, 4194304);
                                if (i2 > 0) {
                                    int i9 = i2 - 1;
                                    int[] iArr3 = this.d;
                                    if (iArr3 == null || i9 >= iArr3.length) {
                                        i5 = i9 | 16777216;
                                    } else {
                                        i5 = iArr3[i9];
                                        if (i5 == 0) {
                                            i5 = i9 | 16777216;
                                            iArr3[i9] = i5;
                                        }
                                    }
                                    if (i5 == 4194308 || i5 == 4194307) {
                                        b(i9, 4194304);
                                        return;
                                    }
                                    int i10 = i5 & 62914560;
                                    if (i10 == 16777216 || i10 == 20971520) {
                                        b(i9, i5 | 1048576);
                                        return;
                                    }
                                    return;
                                }
                                return;
                            default:
                                switch (i) {
                                    case 79:
                                    case 81:
                                    case 83:
                                    case 84:
                                    case 85:
                                    case 86:
                                        a(3);
                                        return;
                                    case 80:
                                    case 82:
                                        a(4);
                                        return;
                                    case 87:
                                    case 153:
                                    case 154:
                                    case 155:
                                    case 156:
                                    case 157:
                                    case 158:
                                    case 170:
                                    case 171:
                                    case 172:
                                    case 174:
                                    case 176:
                                    case 191:
                                    case 194:
                                    case 195:
                                        break;
                                    case 88:
                                    case 159:
                                    case 160:
                                    case 161:
                                    case 162:
                                    case 163:
                                    case 164:
                                    case 165:
                                    case 166:
                                    case 173:
                                    case 175:
                                        a(2);
                                        return;
                                    case 89:
                                        int iB2 = b();
                                        b(iB2);
                                        b(iB2);
                                        return;
                                    case 90:
                                        int iB3 = b();
                                        int iB4 = b();
                                        b(iB3);
                                        b(iB4);
                                        b(iB3);
                                        return;
                                    case 91:
                                        int iB5 = b();
                                        int iB6 = b();
                                        int iB7 = b();
                                        b(iB5);
                                        b(iB7);
                                        b(iB6);
                                        b(iB5);
                                        return;
                                    case 92:
                                        int iB8 = b();
                                        int iB9 = b();
                                        b(iB9);
                                        b(iB8);
                                        b(iB9);
                                        b(iB8);
                                        return;
                                    case 93:
                                        int iB10 = b();
                                        int iB11 = b();
                                        int iB12 = b();
                                        b(iB11);
                                        b(iB10);
                                        b(iB12);
                                        b(iB11);
                                        b(iB10);
                                        return;
                                    case 94:
                                        int iB13 = b();
                                        int iB14 = b();
                                        int iB15 = b();
                                        int iB16 = b();
                                        b(iB14);
                                        b(iB13);
                                        b(iB16);
                                        b(iB15);
                                        b(iB14);
                                        b(iB13);
                                        return;
                                    case 95:
                                        int iB17 = b();
                                        int iB18 = b();
                                        b(iB17);
                                        b(iB18);
                                        return;
                                    case 96:
                                    case 100:
                                    case 104:
                                    case 108:
                                    case 112:
                                    case 120:
                                    case 122:
                                    case 124:
                                    case 126:
                                    case 128:
                                    case 130:
                                    case 136:
                                    case 142:
                                    case 149:
                                    case 150:
                                        a(2);
                                        b(4194305);
                                        return;
                                    case 97:
                                    case 101:
                                    case 105:
                                    case 109:
                                    case 113:
                                    case 127:
                                    case 129:
                                    case 131:
                                        a(4);
                                        b(4194308);
                                        b(4194304);
                                        return;
                                    case 98:
                                    case 102:
                                    case 106:
                                    case 110:
                                    case 114:
                                    case 137:
                                    case 144:
                                        a(2);
                                        b(4194306);
                                        return;
                                    case 99:
                                    case 103:
                                    case 107:
                                    case 111:
                                    case 115:
                                        a(4);
                                        b(4194307);
                                        b(4194304);
                                        return;
                                    case 116:
                                    case 117:
                                    case 118:
                                    case 119:
                                    case 145:
                                    case 146:
                                    case 147:
                                    case 167:
                                    case 177:
                                        return;
                                    case 121:
                                    case 123:
                                    case 125:
                                        a(3);
                                        b(4194308);
                                        b(4194304);
                                        return;
                                    case 132:
                                        b(i2, 4194305);
                                        return;
                                    case 133:
                                    case 140:
                                        a(1);
                                        b(4194308);
                                        b(4194304);
                                        return;
                                    case 134:
                                        a(1);
                                        b(4194306);
                                        return;
                                    case 135:
                                    case 141:
                                        a(1);
                                        b(4194307);
                                        b(4194304);
                                        return;
                                    case 138:
                                        a(2);
                                        b(4194307);
                                        b(4194304);
                                        return;
                                    case 139:
                                    case 190:
                                    case 193:
                                        a(1);
                                        b(4194305);
                                        return;
                                    case 143:
                                        a(2);
                                        b(4194308);
                                        b(4194304);
                                        return;
                                    case 148:
                                    case 151:
                                    case 152:
                                        a(4);
                                        b(4194305);
                                        return;
                                    case 168:
                                    case 169:
                                        throw new IllegalArgumentException("JSR/RET are not supported with computeFrames option");
                                    case 178:
                                        a(ny, my.e);
                                        return;
                                    case 179:
                                        a(my.e);
                                        return;
                                    case 180:
                                        a(1);
                                        a(ny, my.e);
                                        return;
                                    case 181:
                                        a(my.e);
                                        b();
                                        return;
                                    case 182:
                                    case 183:
                                    case 184:
                                    case 185:
                                        a(my.e);
                                        if (i != 184) {
                                            int iB19 = b();
                                            if (i == 183 && my.d.charAt(0) == '<') {
                                                if (this.i == null) {
                                                    this.i = new int[2];
                                                }
                                                int length = this.i.length;
                                                int i11 = this.h;
                                                if (i11 >= length) {
                                                    int[] iArr4 = new int[Math.max(i11 + 1, length * 2)];
                                                    System.arraycopy(this.i, 0, iArr4, 0, length);
                                                    this.i = iArr4;
                                                }
                                                int[] iArr5 = this.i;
                                                int i12 = this.h;
                                                this.h = i12 + 1;
                                                iArr5[i12] = iB19;
                                            }
                                        }
                                        a(ny, my.e);
                                        return;
                                    case 186:
                                        a(my.e);
                                        a(ny, my.e);
                                        return;
                                    case 187:
                                        b(ny.b(i2, my.e) | 12582912);
                                        return;
                                    case 188:
                                        b();
                                        switch (i2) {
                                            case 4:
                                                b(71303177);
                                                return;
                                            case 5:
                                                b(71303179);
                                                return;
                                            case 6:
                                                b(71303170);
                                                return;
                                            case 7:
                                                b(71303171);
                                                return;
                                            case 8:
                                                b(71303178);
                                                return;
                                            case 9:
                                                b(71303180);
                                                return;
                                            case 10:
                                                b(71303169);
                                                return;
                                            case 11:
                                                b(71303172);
                                                return;
                                            default:
                                                throw new IllegalArgumentException();
                                        }
                                    case 189:
                                        String str = my.e;
                                        b();
                                        if (str.charAt(0) == '[') {
                                            a(ny, "[".concat(str));
                                            return;
                                        } else {
                                            b(ny.e(str) | 75497472);
                                            return;
                                        }
                                    case 192:
                                        String str2 = my.e;
                                        b();
                                        if (str2.charAt(0) == '[') {
                                            a(ny, str2);
                                            return;
                                        } else {
                                            b(ny.e(str2) | 8388608);
                                            return;
                                        }
                                    default:
                                        switch (i) {
                                            case 197:
                                                a(i2);
                                                a(ny, my.e);
                                                return;
                                            case 198:
                                            case 199:
                                                break;
                                            default:
                                                throw new IllegalArgumentException();
                                        }
                                        break;
                                }
                                a(1);
                                return;
                        }
                }
        }
    }

    private int a(int i, int i2) {
        int i3 = (-67108864) & i;
        int i4 = 62914560 & i;
        if (i4 == 16777216) {
            int i5 = i3 + this.b[i & 1048575];
            if ((i & 1048576) == 0 || !(i5 == 4194308 || i5 == 4194307)) {
                return i5;
            }
            return 4194304;
        }
        if (i4 != 20971520) {
            return i;
        }
        int i6 = i3 + this.c[i2 - (i & 1048575)];
        if ((i & 1048576) == 0 || !(i6 == 4194308 || i6 == 4194307)) {
            return i6;
        }
        return 4194304;
    }

    final boolean a(NY ny, C1267Pi c1267Pi, int i) {
        boolean zA;
        int iA;
        int i2;
        int length = this.b.length;
        int length2 = this.c.length;
        boolean zA2 = true;
        if (c1267Pi.b == null) {
            c1267Pi.b = new int[length];
            zA = true;
        } else {
            zA = false;
        }
        int i3 = 0;
        while (i3 < length) {
            int[] iArr = this.d;
            if (iArr == null || i3 >= iArr.length || (i2 = iArr[i3]) == 0) {
                iA = this.b[i3];
            } else {
                iA = a(i2, length2);
            }
            if (this.i != null) {
                iA = a(ny, iA);
            }
            zA |= a(ny, iA, c1267Pi.b, i3);
            i3++;
        }
        if (i > 0) {
            for (int i4 = 0; i4 < length; i4++) {
                zA |= a(ny, this.b[i4], c1267Pi.b, i4);
            }
            if (c1267Pi.c == null) {
                c1267Pi.c = new int[1];
            } else {
                zA2 = zA;
            }
            return a(ny, i, c1267Pi.c, 0) | zA2;
        }
        int length3 = this.c.length + this.f;
        if (c1267Pi.c == null) {
            c1267Pi.c = new int[this.g + length3];
        } else {
            zA2 = zA;
        }
        for (int i5 = 0; i5 < length3; i5++) {
            int iA2 = this.c[i5];
            if (this.i != null) {
                iA2 = a(ny, iA2);
            }
            zA2 |= a(ny, iA2, c1267Pi.c, i5);
        }
        for (int i6 = 0; i6 < this.g; i6++) {
            int iA3 = a(this.e[i6], length2);
            if (this.i != null) {
                iA3 = a(ny, iA3);
            }
            zA2 |= a(ny, iA3, c1267Pi.c, length3 + i6);
        }
        return zA2;
    }

    /* JADX WARN: Code duplicated, block: B:39:0x0064  */
    private static boolean a(NY ny, int i, int[] iArr, int i2) {
        int iMin;
        int iE;
        int i3 = iArr[i2];
        if (i3 == i) {
            return false;
        }
        if ((67108863 & i) == 4194309) {
            if (i3 == 4194309) {
                return false;
            }
            i = 4194309;
        }
        if (i3 == 0) {
            iArr[i2] = i;
            return true;
        }
        int i4 = i3 & (-67108864);
        if (i4 != 0 || (i3 & 62914560) == 8388608) {
            if (i == 4194309) {
                return false;
            }
            if ((i & (-4194304)) != ((-4194304) & i3)) {
                int i5 = i & (-67108864);
                if (i5 != 0 || (i & 62914560) == 8388608) {
                    if (i5 != 0 && (i & 62914560) != 8388608) {
                        i5 -= 67108864;
                    }
                    if (i4 != 0 && (i3 & 62914560) != 8388608) {
                        i4 -= 67108864;
                    }
                    iMin = Math.min(i5, i4) | 8388608;
                    iE = ny.e(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME);
                } else {
                    i = 4194304;
                }
            } else if ((i3 & 62914560) == 8388608) {
                i = (i & (-67108864)) | 8388608 | ny.b(i & 1048575, 1048575 & i3);
            } else {
                iMin = ((i & (-67108864)) - 67108864) | 8388608;
                iE = ny.e(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME);
            }
            i = iMin | iE;
        } else if (i3 != 4194309 || ((i & (-67108864)) == 0 && (i & 62914560) != 8388608)) {
            i = 4194304;
        }
        if (i == i3) {
            return false;
        }
        iArr[i2] = i;
        return true;
    }

    final void a(C1986fF c1986fF) {
        int[] iArr = this.b;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int i5 = 2;
            if (i2 >= iArr.length) {
                break;
            }
            int i6 = iArr[i2];
            if (i6 != 4194308 && i6 != 4194307) {
                i5 = 1;
            }
            i2 += i5;
            i3++;
            if (i6 != 4194304) {
                i4 += i3;
                i3 = 0;
            }
        }
        int[] iArr2 = this.c;
        int i7 = 0;
        int i8 = 0;
        while (i7 < iArr2.length) {
            int i9 = iArr2[i7];
            i7 += (i9 == 4194308 || i9 == 4194307) ? 2 : 1;
            i8++;
        }
        c1986fF.a(this.a.e, i4, i8);
        int i10 = 3;
        int i11 = 0;
        while (true) {
            int i12 = i4 - 1;
            if (i4 <= 0) {
                break;
            }
            int i13 = iArr[i11];
            i11 += (i13 == 4194308 || i13 == 4194307) ? 2 : 1;
            c1986fF.g(i10, i13);
            i4 = i12;
            i10++;
        }
        while (true) {
            int i14 = i8 - 1;
            if (i8 > 0) {
                int i15 = iArr2[i];
                i += (i15 == 4194308 || i15 == 4194307) ? 2 : 1;
                c1986fF.g(i10, i15);
                i10++;
                i8 = i14;
            } else {
                c1986fF.h();
                return;
            }
        }
    }
}
