package com.esotericsoftware.asm;

import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jdt.core.compiler.IProblem;
import org.luaj.vm2.Lua;

/* JADX INFO: loaded from: classes8.dex */
final class Frame {
    static final int[] a;
    Label b;
    int[] c;
    int[] d;
    private int[] e;
    private int[] f;
    private int g;
    private int h;
    private int[] i;

    static {
        _clinit_();
        int[] iArr = new int[202];
        for (int i = 0; i < 202; i++) {
            iArr[i] = "EFFFFFFFFGGFFFGGFFFEEFGFGFEEEEEEEEEEEEEEEEEEEEDEDEDDDDDCDCDEEEEEEEEEEEEEEEEEEEEBABABBBBDCFFFGGGEDCDCDCDCDCDCDCDCDCDCEEEEDDDDDDDCDCDCEFEFDDEEFFDEDEEEBDDBBDDDDDDCCCCCCCCEFEDDDCDCDEEEEEEEEEEFEEEEEEDDEEDDEE".charAt(i) - 'E';
        }
        a = iArr;
    }

    Frame() {
    }

    static /* synthetic */ void _clinit_() {
    }

    private int a() {
        int i = this.g;
        if (i > 0) {
            int[] iArr = this.f;
            int i2 = i - 1;
            this.g = i2;
            return iArr[i2];
        }
        Label label = this.b;
        int i3 = label.f - 1;
        label.f = i3;
        return (-i3) | 50331648;
    }

    private int a(int i) {
        int[] iArr = this.e;
        if (iArr == null || i >= iArr.length) {
            return i | 33554432;
        }
        int i2 = iArr[i];
        if (i2 != 0) {
            return i2;
        }
        int i3 = i | 33554432;
        iArr[i] = i3;
        return i3;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x004d A[LOOP:0: B:10:0x0022->B:21:0x004d, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:23:0x004c A[SYNTHETIC] */
    private int a(ClassWriter classWriter, int i) {
        String str;
        int i2;
        if (i != 16777222) {
            if (((-1048576) & i) == 25165824) {
                str = classWriter.H[1048575 & i].g;
            }
            return i;
        }
        str = classWriter.I;
        int iC = classWriter.c(str) | 24117248;
        for (int i3 = 0; i3 < this.h; i3++) {
            int i4 = this.i[i3];
            int i5 = (-268435456) & i4;
            int i6 = 251658240 & i4;
            if (i6 == 33554432) {
                i2 = this.c[i4 & Lua.MASK_NOT_B];
            } else {
                if (i6 == 50331648) {
                    int[] iArr = this.d;
                    i2 = iArr[iArr.length - (i4 & Lua.MASK_NOT_B)];
                }
                if (i == i4) {
                    return iC;
                }
            }
            i4 = i2 + i5;
            if (i == i4) {
                return iC;
            }
        }
        return i;
    }

    private void a(int i, int i2) {
        if (this.e == null) {
            this.e = new int[10];
        }
        int length = this.e.length;
        if (i >= length) {
            int[] iArr = new int[Math.max(i + 1, length * 2)];
            System.arraycopy(this.e, 0, iArr, 0, length);
            this.e = iArr;
        }
        this.e[i] = i2;
    }

    private void a(ClassWriter classWriter, String str) {
        int iB = b(classWriter, str);
        if (iB != 0) {
            b(iB);
            if (iB == 16777220 || iB == 16777219) {
                b(16777216);
            }
        }
    }

    private void a(String str) {
        char cCharAt = str.charAt(0);
        if (cCharAt == '(') {
            c((Type.getArgumentsAndReturnSizes(str) >> 2) - 1);
        } else if (cCharAt == 'J' || cCharAt == 'D') {
            c(2);
        } else {
            c(1);
        }
    }

    private static boolean a(ClassWriter classWriter, int i, int[] iArr, int i2) {
        int iMin;
        int i3 = iArr[i2];
        if (i3 == i) {
            return false;
        }
        if ((268435455 & i) == 16777221) {
            if (i3 == 16777221) {
                return false;
            }
            i = 16777221;
        }
        if (i3 == 0) {
            iArr[i2] = i;
            return true;
        }
        int i4 = i3 & 267386880;
        int iC = 16777216;
        if (i4 == 24117248 || (i3 & (-268435456)) != 0) {
            if (i == 16777221) {
                return false;
            }
            if ((i & (-1048576)) != ((-1048576) & i3)) {
                int i5 = i & 267386880;
                if (i5 == 24117248 || (i & (-268435456)) != 0) {
                    int i6 = i & (-268435456);
                    int i7 = ((i6 == 0 || i5 == 24117248) ? 0 : -268435456) + i6;
                    int i8 = i3 & (-268435456);
                    iMin = Math.min(i7, ((i8 == 0 || i4 == 24117248) ? 0 : -268435456) + i8);
                    iC = iMin | 24117248 | classWriter.c(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME);
                }
            } else if (i4 == 24117248) {
                iC = (i & (-268435456)) | 24117248 | classWriter.a(i & 1048575, 1048575 & i3);
            } else {
                iMin = (i3 & (-268435456)) - 268435456;
                iC = iMin | 24117248 | classWriter.c(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME);
            }
        } else if (i3 == 16777221) {
            if ((i & 267386880) != 24117248 && (i & (-268435456)) == 0) {
                i = 16777216;
            }
            iC = i;
        }
        if (i3 == iC) {
            return false;
        }
        iArr[i2] = iC;
        return true;
    }

    private static int b(ClassWriter classWriter, String str) {
        int iIndexOf = str.charAt(0) == '(' ? str.indexOf(41) + 1 : 0;
        char cCharAt = str.charAt(iIndexOf);
        int iC = IProblem.UndefinedType;
        if (cCharAt == 'F') {
            return IProblem.UndefinedType;
        }
        if (cCharAt == 'L') {
            return classWriter.c(str.substring(iIndexOf + 1, str.length() - 1)) | 24117248;
        }
        if (cCharAt != 'S') {
            if (cCharAt == 'V') {
                return 0;
            }
            if (cCharAt != 'Z' && cCharAt != 'I') {
                if (cCharAt == 'J') {
                    return IProblem.AmbiguousType;
                }
                switch (cCharAt) {
                    case 'B':
                    case 'C':
                        break;
                    case 'D':
                        return IProblem.NotVisibleType;
                    default:
                        int i = iIndexOf + 1;
                        while (str.charAt(i) == '[') {
                            i++;
                        }
                        char cCharAt2 = str.charAt(i);
                        if (cCharAt2 != 'F') {
                            if (cCharAt2 == 'S') {
                                iC = 16777228;
                            } else if (cCharAt2 == 'Z') {
                                iC = 16777225;
                            } else if (cCharAt2 == 'I') {
                                iC = 16777217;
                            } else if (cCharAt2 != 'J') {
                                switch (cCharAt2) {
                                    case 'B':
                                        iC = 16777226;
                                        break;
                                    case 'C':
                                        iC = 16777227;
                                        break;
                                    case 'D':
                                        iC = 16777219;
                                        break;
                                    default:
                                        iC = classWriter.c(str.substring(i + 1, str.length() - 1)) | 24117248;
                                        break;
                                }
                            } else {
                                iC = 16777220;
                            }
                        }
                        return ((i - iIndexOf) << 28) | iC;
                }
            }
        }
        return IProblem.ObjectHasNoSuperclass;
    }

    private void b(int i) {
        if (this.f == null) {
            this.f = new int[10];
        }
        int length = this.f.length;
        int i2 = this.g;
        if (i2 >= length) {
            int[] iArr = new int[Math.max(i2 + 1, length * 2)];
            System.arraycopy(this.f, 0, iArr, 0, length);
            this.f = iArr;
        }
        int[] iArr2 = this.f;
        int i3 = this.g;
        this.g = i3 + 1;
        iArr2[i3] = i;
        int i4 = this.b.f + this.g;
        if (i4 > this.b.g) {
            this.b.g = i4;
        }
    }

    private void c(int i) {
        int i2 = this.g;
        if (i2 >= i) {
            this.g = i2 - i;
            return;
        }
        this.b.f -= i - this.g;
        this.g = 0;
    }

    private void d(int i) {
        if (this.i == null) {
            this.i = new int[2];
        }
        int length = this.i.length;
        int i2 = this.h;
        if (i2 >= length) {
            int[] iArr = new int[Math.max(i2 + 1, length * 2)];
            System.arraycopy(this.i, 0, iArr, 0, length);
            this.i = iArr;
        }
        int[] iArr2 = this.i;
        int i3 = this.h;
        this.h = i3 + 1;
        iArr2[i3] = i;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:106:0x0225  */
    /* JADX WARN: Code duplicated, block: B:107:0x0229  */
    /* JADX WARN: Code duplicated, block: B:108:0x022d  */
    /* JADX WARN: Code duplicated, block: B:110:0x0234  */
    /* JADX WARN: Code duplicated, block: B:49:0x00f6  */
    /* JADX WARN: Code duplicated, block: B:51:0x00fe  */
    /* JADX WARN: Code duplicated, block: B:94:0x01f8  */
    /* JADX WARN: Code duplicated, block: B:96:0x0201  */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x004b, code lost:
    
        if (r1.charAt(0) == '[') goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void a(int r17, int r18, com.esotericsoftware.asm.ClassWriter r19, com.esotericsoftware.asm.Item r20) {
        /*
            Method dump skipped, instruction units count: 938
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.asm.Frame.a(int, int, com.esotericsoftware.asm.ClassWriter, com.esotericsoftware.asm.Item):void");
    }

    void a(ClassWriter classWriter, int i, Type[] typeArr, int i2) {
        int i3;
        int[] iArr = new int[i2];
        this.c = iArr;
        this.d = new int[0];
        if ((i & 8) == 0) {
            i3 = 1;
            if ((i & 524288) == 0) {
                iArr[0] = classWriter.c(classWriter.I) | 24117248;
            } else {
                iArr[0] = 16777222;
            }
        } else {
            i3 = 0;
        }
        for (Type type : typeArr) {
            int iB = b(classWriter, type.getDescriptor());
            int[] iArr2 = this.c;
            int i4 = i3 + 1;
            iArr2[i3] = iB;
            if (iB == 16777220 || iB == 16777219) {
                i3 += 2;
                iArr2[i4] = 16777216;
            } else {
                i3 = i4;
            }
        }
        while (i3 < i2) {
            this.c[i3] = 16777216;
            i3++;
        }
    }

    /* JADX WARN: Code duplicated, block: B:71:0x0105  */
    /* JADX WARN: Code duplicated, block: B:83:0x0109 A[SYNTHETIC] */
    boolean a(ClassWriter classWriter, Frame frame, int i) {
        boolean zA;
        boolean zA2;
        int iA;
        boolean z;
        int i2;
        int length = this.c.length;
        int length2 = this.d.length;
        if (frame.c == null) {
            frame.c = new int[length];
            zA = true;
        } else {
            zA = false;
        }
        int i3 = 0;
        while (true) {
            int iA2 = 16777216;
            if (i3 >= length) {
                break;
            }
            int[] iArr = this.e;
            if (iArr == null || i3 >= iArr.length || (i2 = iArr[i3]) == 0) {
                iA2 = this.c[i3];
            } else {
                int i4 = i2 & (-268435456);
                int i5 = 251658240 & i2;
                if (i5 == 16777216) {
                    iA2 = i2;
                } else {
                    int i6 = i4 + (i5 == 33554432 ? this.c[i2 & Lua.MASK_NOT_B] : this.d[length2 - (i2 & Lua.MASK_NOT_B)]);
                    if ((i2 & 8388608) == 0 || (i6 != 16777220 && i6 != 16777219)) {
                        iA2 = i6;
                    }
                }
            }
            if (this.i != null) {
                iA2 = a(classWriter, iA2);
            }
            zA |= a(classWriter, iA2, frame.c, i3);
            i3++;
        }
        if (i > 0) {
            for (int i7 = 0; i7 < length; i7++) {
                zA |= a(classWriter, this.c[i7], frame.c, i7);
            }
            if (frame.d == null) {
                frame.d = new int[1];
                z = true;
            } else {
                z = zA;
            }
            return a(classWriter, i, frame.d, 0) | z;
        }
        int length3 = this.d.length + this.b.f;
        if (frame.d == null) {
            frame.d = new int[this.g + length3];
            zA2 = true;
        } else {
            zA2 = zA;
        }
        for (int i8 = 0; i8 < length3; i8++) {
            int iA3 = this.d[i8];
            if (this.i != null) {
                iA3 = a(classWriter, iA3);
            }
            zA2 |= a(classWriter, iA3, frame.d, i8);
        }
        for (int i9 = 0; i9 < this.g; i9++) {
            int i10 = this.f[i9];
            int i11 = i10 & (-268435456);
            int i12 = i10 & 251658240;
            if (i12 == 16777216) {
                iA = i10;
            } else {
                iA = i11 + (i12 == 33554432 ? this.c[i10 & Lua.MASK_NOT_B] : this.d[length2 - (i10 & Lua.MASK_NOT_B)]);
                if ((i10 & 8388608) != 0) {
                    if (iA == 16777220 || iA == 16777219) {
                        iA = 16777216;
                    }
                }
                if (this.i != null) {
                    iA = a(classWriter, iA);
                }
                zA2 |= a(classWriter, iA, frame.d, length3 + i9);
            }
            if (this.i != null) {
                iA = a(classWriter, iA);
            }
            zA2 |= a(classWriter, iA, frame.d, length3 + i9);
        }
        return zA2;
    }
}
