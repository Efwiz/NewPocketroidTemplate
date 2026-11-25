package com.esotericsoftware.asm;

import com.android.dx.cf.attrib.AttSynthetic;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes8.dex */
class MethodWriter extends MethodVisitor {
    private ByteVector $;
    private int A;
    private Handler B;
    private Handler C;
    private int D;
    private ByteVector E;
    private int F;
    private ByteVector G;
    private int H;
    private ByteVector I;
    private Attribute J;
    private boolean K;
    private int L;
    private final int M;
    private Label N;
    private Label O;
    private Label P;
    private int Q;
    private int R;
    private int S;
    private int T;
    private AnnotationWriter U;
    private AnnotationWriter V;
    private AnnotationWriter W;
    private AnnotationWriter X;
    private int Y;
    private int Z;
    final ClassWriter b;
    private int c;
    private final int d;
    private final int e;
    private final String f;
    String g;
    int h;
    int i;
    int j;
    int[] k;
    private ByteVector l;
    private AnnotationWriter m;
    private AnnotationWriter n;
    private AnnotationWriter[] o;
    private AnnotationWriter[] p;
    private Attribute q;
    private ByteVector r;
    private int s;
    private int t;
    private int u;
    private ByteVector v;
    private int w;
    private int[] x;
    private int[] z;

    MethodWriter(ClassWriter classWriter, int i, String str, String str2, String str3, String[] strArr, boolean z, boolean z2) {
        super(327680);
        this.r = new ByteVector();
        if (classWriter.D == null) {
            classWriter.D = this;
        } else {
            classWriter.E.mv = this;
        }
        classWriter.E = this;
        this.b = classWriter;
        this.c = i;
        if ("<init>".equals(str)) {
            this.c |= 524288;
        }
        this.d = classWriter.newUTF8(str);
        this.e = classWriter.newUTF8(str2);
        this.f = str2;
        this.g = str3;
        if (strArr != null && strArr.length > 0) {
            int length = strArr.length;
            this.j = length;
            this.k = new int[length];
            for (int i2 = 0; i2 < this.j; i2++) {
                this.k[i2] = classWriter.newClass(strArr[i2]);
            }
        }
        this.M = z2 ? 0 : z ? 1 : 2;
        if (z || z2) {
            int argumentsAndReturnSizes = Type.getArgumentsAndReturnSizes(this.f) >> 2;
            argumentsAndReturnSizes = (i & 8) != 0 ? argumentsAndReturnSizes - 1 : argumentsAndReturnSizes;
            this.t = argumentsAndReturnSizes;
            this.T = argumentsAndReturnSizes;
            Label label = new Label();
            this.N = label;
            label.a |= 8;
            visitLabel(this.N);
        }
    }

    private int a(int i, int i2, int i3) {
        int i4 = i2 + 3 + i3;
        int[] iArr = this.z;
        if (iArr == null || iArr.length < i4) {
            this.z = new int[i4];
        }
        int[] iArr2 = this.z;
        iArr2[0] = i;
        iArr2[1] = i2;
        iArr2[2] = i3;
        return 3;
    }

    static int a(byte[] bArr, int i) {
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }

    static int a(int[] iArr, int[] iArr2, int i, int i2) {
        int i3 = i2 - i;
        for (int i4 = 0; i4 < iArr.length; i4++) {
            int i5 = iArr[i4];
            if (i < i5 && i5 <= i2) {
                i3 += iArr2[i4];
            } else if (i2 < i5 && i5 <= i) {
                i3 -= iArr2[i4];
            }
        }
        return i3;
    }

    private void a(int i, int i2) {
        char c;
        ByteVector byteVectorPutByte;
        int iNewClass;
        while (i < i2) {
            int i3 = this.z[i];
            int i4 = (-268435456) & i3;
            if (i4 == 0) {
                int i5 = i3 & 1048575;
                int i6 = i3 & 267386880;
                if (i6 != 24117248) {
                    if (i6 != 25165824) {
                        this.v.putByte(i5);
                    } else {
                        byteVectorPutByte = this.v.putByte(8);
                        iNewClass = this.b.H[i5].c;
                    }
                    i++;
                } else {
                    byteVectorPutByte = this.v.putByte(7);
                    ClassWriter classWriter = this.b;
                    iNewClass = classWriter.newClass(classWriter.H[i5].g);
                }
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                int i7 = i4 >> 28;
                while (true) {
                    int i8 = i7 - 1;
                    if (i7 > 0) {
                        stringBuffer.append('[');
                        i7 = i8;
                    } else {
                        if ((i3 & 267386880) == 24117248) {
                            stringBuffer.append('L');
                            stringBuffer.append(this.b.H[i3 & 1048575].g);
                            c = ';';
                        } else {
                            int i9 = i3 & 15;
                            if (i9 == 1) {
                                c = 'I';
                            } else if (i9 == 2) {
                                c = Util.C_FLOAT;
                            } else if (i9 != 3) {
                                switch (i9) {
                                    case 9:
                                        c = 'Z';
                                        break;
                                    case 10:
                                        c = Util.C_BYTE;
                                        break;
                                    case 11:
                                        c = Util.C_CHAR;
                                        break;
                                    case 12:
                                        c = Util.C_SHORT;
                                        break;
                                    default:
                                        c = Util.C_LONG;
                                        break;
                                }
                            } else {
                                c = Util.C_DOUBLE;
                            }
                        }
                        stringBuffer.append(c);
                        byteVectorPutByte = this.v.putByte(7);
                        iNewClass = this.b.newClass(stringBuffer.toString());
                    }
                }
            }
            byteVectorPutByte.putShort(iNewClass);
            i++;
        }
    }

    private void a(int i, Label label) {
        Edge edge = new Edge();
        edge.a = i;
        edge.b = label;
        edge.c = this.P.j;
        this.P.j = edge;
    }

    private void a(Label label, Label[] labelArr) {
        Label label2 = this.P;
        if (label2 != null) {
            if (this.M == 0) {
                label2.h.a(171, 0, (ClassWriter) null, (Item) null);
                a(0, label);
                label.a().a |= 16;
                for (int i = 0; i < labelArr.length; i++) {
                    a(0, labelArr[i]);
                    labelArr[i].a().a |= 16;
                }
            } else {
                int i2 = this.Q - 1;
                this.Q = i2;
                a(i2, label);
                for (Label label3 : labelArr) {
                    a(this.Q, label3);
                }
            }
            e();
        }
    }

    private void a(Object obj) {
        ByteVector byteVectorPutByte;
        int iNewClass;
        if (obj instanceof String) {
            byteVectorPutByte = this.v.putByte(7);
            iNewClass = this.b.newClass((String) obj);
        } else if (obj instanceof Integer) {
            this.v.putByte(((Integer) obj).intValue());
            return;
        } else {
            byteVectorPutByte = this.v.putByte(8);
            iNewClass = ((Label) obj).c;
        }
        byteVectorPutByte.putShort(iNewClass);
    }

    static void a(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) (i2 >>> 8);
        bArr[i + 1] = (byte) i2;
    }

    static void a(int[] iArr, int[] iArr2, Label label) {
        if ((label.a & 4) == 0) {
            label.c = a(iArr, iArr2, 0, label.c);
            label.a |= 4;
        }
    }

    static short b(byte[] bArr, int i) {
        return (short) ((bArr[i + 1] & 255) | ((bArr[i] & 255) << 8));
    }

    private void b() {
        if (this.x != null) {
            if (this.v == null) {
                this.v = new ByteVector();
            }
            c();
            this.u++;
        }
        this.x = this.z;
        this.z = null;
    }

    private void b(Frame frame) {
        int[] iArr = frame.c;
        int[] iArr2 = frame.d;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 < iArr.length) {
            int i5 = iArr[i2];
            i4++;
            if (i5 != 16777216) {
                i3 += i4;
                i4 = 0;
            }
            if (i5 == 16777220 || i5 == 16777219) {
                i2++;
            }
            i2++;
        }
        int i6 = 0;
        int i7 = 0;
        while (i6 < iArr2.length) {
            int i8 = iArr2[i6];
            i7++;
            if (i8 == 16777220 || i8 == 16777219) {
                i6++;
            }
            i6++;
        }
        int iA = a(frame.b.c, i3, i7);
        int i9 = 0;
        while (i3 > 0) {
            int i10 = iArr[i9];
            int i11 = iA + 1;
            this.z[iA] = i10;
            if (i10 == 16777220 || i10 == 16777219) {
                i9++;
            }
            i9++;
            i3--;
            iA = i11;
        }
        while (i < iArr2.length) {
            int i12 = iArr2[i];
            int i13 = iA + 1;
            this.z[iA] = i12;
            if (i12 == 16777220 || i12 == 16777219) {
                i++;
            }
            i++;
            iA = i13;
        }
        b();
    }

    static int c(byte[] bArr, int i) {
        return (bArr[i + 1] & 255) | ((bArr[i] & 255) << 8);
    }

    private void c() {
        int i;
        char c;
        ByteVector byteVectorPutByte;
        int[] iArr = this.z;
        int i2 = iArr[1];
        int i3 = iArr[2];
        if ((this.b.b & 65535) < 50) {
            this.v.putShort(this.z[0]).putShort(i2);
            int i4 = i2 + 3;
            a(3, i4);
            this.v.putShort(i3);
            a(i4, i3 + i4);
            return;
        }
        int[] iArr2 = this.x;
        int i5 = iArr2[1];
        int i6 = this.u == 0 ? this.z[0] : (this.z[0] - iArr2[0]) - 1;
        if (i3 == 0) {
            i = i2 - i5;
            switch (i) {
                case -3:
                case -2:
                case -1:
                    i5 = i2;
                    c = 248;
                    break;
                case 0:
                    c = i6 < 64 ? (char) 0 : (char) 251;
                    break;
                case 1:
                case 2:
                case 3:
                    c = 252;
                    break;
                default:
                    c = 255;
                    break;
            }
        } else if (i2 == i5 && i3 == 1) {
            c = i6 < 63 ? '@' : (char) 247;
            i = 0;
        } else {
            i = 0;
            c = 255;
        }
        if (c != 255) {
            int i7 = 3;
            for (int i8 = 0; i8 < i5; i8++) {
                if (this.z[i7] != this.x[i7]) {
                    c = 255;
                } else {
                    i7++;
                }
            }
        }
        if (c == 0) {
            this.v.putByte(i6);
            return;
        }
        if (c == '@') {
            this.v.putByte(i6 + 64);
        } else {
            if (c != 247) {
                if (c == 248) {
                    byteVectorPutByte = this.v.putByte(i + 251);
                } else {
                    if (c != 251) {
                        if (c == 252) {
                            this.v.putByte(i + 251).putShort(i6);
                            a(i5 + 3, i2 + 3);
                            return;
                        }
                        this.v.putByte(255).putShort(i6).putShort(i2);
                        int i9 = i2 + 3;
                        a(3, i9);
                        this.v.putShort(i3);
                        a(i9, i3 + i9);
                        return;
                    }
                    byteVectorPutByte = this.v.putByte(251);
                }
                byteVectorPutByte.putShort(i6);
                return;
            }
            this.v.putByte(247).putShort(i6);
        }
        a(i2 + 3, i2 + 4);
    }

    private void d() {
        int i;
        char c;
        int iB;
        int iB2;
        int i2;
        int i3;
        byte[] bArr = this.r.a;
        int[] iArr = new int[0];
        int[] iArr2 = new int[0];
        boolean[] zArr = new boolean[this.r.b];
        int i4 = 3;
        do {
            if (i4 == 3) {
                i4 = 2;
            }
            int iA = 0;
            while (true) {
                i = 218;
                c = 132;
                if (iA < bArr.length) {
                    int i5 = bArr[iA] & 255;
                    switch (ClassWriter.a[i5]) {
                        case 0:
                        case 4:
                            iA++;
                            i2 = 0;
                            break;
                        case 1:
                        case 3:
                        case 11:
                            iA += 2;
                            i2 = 0;
                            break;
                        case 2:
                        case 5:
                        case 6:
                        case 12:
                        case 13:
                            iA += 3;
                            i2 = 0;
                            break;
                        case 7:
                        case 8:
                        case 10:
                            iA += 5;
                            i2 = 0;
                            break;
                        case 9:
                            if (i5 > 201) {
                                i5 = i5 < 218 ? i5 - 49 : i5 - 20;
                                iB2 = c(bArr, iA + 1);
                            } else {
                                iB2 = b(bArr, iA + 1);
                            }
                            int iA2 = a(iArr, iArr2, iA, iB2 + iA);
                            if ((iA2 < -32768 || iA2 > 32767) && !zArr[iA]) {
                                int i6 = (i5 == 167 || i5 == 168) ? 2 : 5;
                                zArr[iA] = true;
                                i2 = i6;
                            } else {
                                i2 = 0;
                            }
                            iA += 3;
                            break;
                        case 14:
                            if (i4 != 1) {
                                if (zArr[iA]) {
                                    i2 = 0;
                                } else {
                                    i3 = iA & 3;
                                    zArr[iA] = true;
                                }
                                int i7 = (iA + 4) - (iA & 3);
                                iA = i7 + (((a(bArr, i7 + 8) - a(bArr, i7 + 4)) + 1) * 4) + 12;
                            } else {
                                i3 = -(a(iArr, iArr2, 0, iA) & 3);
                            }
                            i2 = i3;
                            int i8 = (iA + 4) - (iA & 3);
                            iA = i8 + (((a(bArr, i8 + 8) - a(bArr, i8 + 4)) + 1) * 4) + 12;
                            break;
                        case 15:
                            if (i4 == 1) {
                                i2 = -(a(iArr, iArr2, 0, iA) & 3);
                            } else if (zArr[iA]) {
                                i2 = 0;
                            } else {
                                i2 = iA & 3;
                                zArr[iA] = true;
                            }
                            int i9 = (iA + 4) - (iA & 3);
                            iA = i9 + (a(bArr, i9 + 4) * 8) + 8;
                            break;
                        case 17:
                            if ((bArr[iA + 1] & 255) == 132) {
                                iA += 6;
                            }
                            i2 = 0;
                        case 16:
                        default:
                            iA += 4;
                            i2 = 0;
                            break;
                    }
                    if (i2 != 0) {
                        int[] iArr3 = new int[iArr.length + 1];
                        int[] iArr4 = new int[iArr2.length + 1];
                        System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
                        System.arraycopy(iArr2, 0, iArr4, 0, iArr2.length);
                        iArr3[iArr.length] = iA;
                        iArr4[iArr2.length] = i2;
                        if (i2 > 0) {
                            i4 = 3;
                        }
                        iArr = iArr3;
                        iArr2 = iArr4;
                    }
                } else if (i4 < 3) {
                    i4--;
                }
            }
        } while (i4 != 0);
        ByteVector byteVector = new ByteVector(this.r.b);
        int i10 = 0;
        while (i10 < this.r.b) {
            int i11 = bArr[i10] & 255;
            switch (ClassWriter.a[i11]) {
                case 0:
                case 4:
                    byteVector.putByte(i11);
                    i10++;
                    continue;
                    i = 218;
                    c = 132;
                    break;
                case 1:
                case 3:
                case 11:
                    byteVector.putByteArray(bArr, i10, 2);
                    i10 += 2;
                    continue;
                    i = 218;
                    c = 132;
                    break;
                case 2:
                case 5:
                case 6:
                case 12:
                case 13:
                    byteVector.putByteArray(bArr, i10, 3);
                    i10 += 3;
                    continue;
                    i = 218;
                    c = 132;
                    break;
                case 7:
                case 8:
                    byteVector.putByteArray(bArr, i10, 5);
                    i10 += 5;
                    continue;
                    i = 218;
                    c = 132;
                    break;
                case 9:
                    if (i11 > 201) {
                        i11 = i11 < i ? i11 - 49 : i11 - 20;
                        iB = c(bArr, i10 + 1);
                    } else {
                        iB = b(bArr, i10 + 1);
                    }
                    int iA3 = a(iArr, iArr2, i10, iB + i10);
                    if (zArr[i10]) {
                        if (i11 == 167) {
                            byteVector.putByte(200);
                        } else if (i11 == 168) {
                            byteVector.putByte(201);
                        } else {
                            byteVector.putByte(i11 <= 166 ? ((i11 + 1) ^ 1) - 1 : i11 ^ 1);
                            byteVector.putShort(8);
                            byteVector.putByte(200);
                            iA3 -= 3;
                        }
                        byteVector.putInt(iA3);
                    } else {
                        byteVector.putByte(i11);
                        byteVector.putShort(iA3);
                    }
                    i10 += 3;
                    break;
                case 10:
                    int iA4 = a(iArr, iArr2, i10, a(bArr, i10 + 1) + i10);
                    byteVector.putByte(i11);
                    byteVector.putInt(iA4);
                    i10 += 5;
                    break;
                case 14:
                    int i12 = (i10 + 4) - (i10 & 3);
                    byteVector.putByte(170);
                    byteVector.putByteArray(null, 0, (4 - (byteVector.b % 4)) % 4);
                    byteVector.putInt(a(iArr, iArr2, i10, a(bArr, i12) + i10));
                    int iA5 = a(bArr, i12 + 4);
                    byteVector.putInt(iA5);
                    int i13 = i12 + 12;
                    byteVector.putInt(a(bArr, i12 + 8));
                    for (int iA6 = (a(bArr, i12 + 8) - iA5) + 1; iA6 > 0; iA6--) {
                        int iA7 = a(bArr, i13) + i10;
                        i13 += 4;
                        byteVector.putInt(a(iArr, iArr2, i10, iA7));
                    }
                    i10 = i13;
                    break;
                case 15:
                    int i14 = (i10 + 4) - (i10 & 3);
                    byteVector.putByte(171);
                    byteVector.putByteArray(null, 0, (4 - (byteVector.b % 4)) % 4);
                    byteVector.putInt(a(iArr, iArr2, i10, a(bArr, i14) + i10));
                    int iA8 = a(bArr, i14 + 4);
                    int i15 = i14 + 8;
                    byteVector.putInt(iA8);
                    while (iA8 > 0) {
                        byteVector.putInt(a(bArr, i15));
                        int iA9 = a(bArr, i15 + 4) + i10;
                        i15 += 8;
                        byteVector.putInt(a(iArr, iArr2, i10, iA9));
                        iA8--;
                    }
                    i10 = i15;
                    break;
                case 16:
                default:
                    byteVector.putByteArray(bArr, i10, 4);
                    i10 += 4;
                    continue;
                    i = 218;
                    c = 132;
                    break;
                case 17:
                    if ((bArr[i10 + 1] & 255) == c) {
                        byteVector.putByteArray(bArr, i10, 6);
                        i10 += 6;
                    } else {
                        byteVector.putByteArray(bArr, i10, 4);
                        i10 += 4;
                    }
                    break;
            }
            i = 218;
            c = 132;
        }
        if (this.M == 0) {
            for (Label label = this.N; label != null; label = label.i) {
                int i16 = label.c - 3;
                if (i16 >= 0 && zArr[i16]) {
                    label.a |= 16;
                }
                a(iArr, iArr2, label);
            }
            if (this.b.H != null) {
                for (int i17 = 0; i17 < this.b.H.length; i17++) {
                    Item item = this.b.H[i17];
                    if (item != null && item.b == 31) {
                        item.c = a(iArr, iArr2, 0, item.c);
                    }
                }
            }
        } else if (this.u > 0) {
            this.b.L = true;
        }
        for (Handler handler = this.B; handler != null; handler = handler.f) {
            a(iArr, iArr2, handler.a);
            a(iArr, iArr2, handler.b);
            a(iArr, iArr2, handler.c);
        }
        int i18 = 0;
        while (i18 < 2) {
            ByteVector byteVector2 = i18 == 0 ? this.E : this.G;
            if (byteVector2 != null) {
                byte[] bArr2 = byteVector2.a;
                for (int i19 = 0; i19 < byteVector2.b; i19 += 10) {
                    int iC = c(bArr2, i19);
                    int iA10 = a(iArr, iArr2, 0, iC);
                    a(bArr2, i19, iA10);
                    int i20 = i19 + 2;
                    a(bArr2, i20, a(iArr, iArr2, 0, iC + c(bArr2, i20)) - iA10);
                }
            }
            i18++;
        }
        ByteVector byteVector3 = this.I;
        if (byteVector3 != null) {
            byte[] bArr3 = byteVector3.a;
            for (int i21 = 0; i21 < this.I.b; i21 += 4) {
                a(bArr3, i21, a(iArr, iArr2, 0, c(bArr3, i21)));
            }
        }
        for (Attribute attribute = this.J; attribute != null; attribute = attribute.a) {
            Label[] labels = attribute.getLabels();
            if (labels != null) {
                for (int length = labels.length - 1; length >= 0; length--) {
                    a(iArr, iArr2, labels[length]);
                }
            }
        }
        this.r = byteVector;
    }

    private void e() {
        if (this.M == 0) {
            Label label = new Label();
            label.h = new Frame();
            label.h.b = label;
            label.a(this, this.r.b, this.r.a);
            this.O.i = label;
            this.O = label;
        } else {
            this.P.g = this.R;
        }
        this.P = null;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:25:0x005a. Please report as an issue. */
    private void f() {
        int i;
        int i2;
        int i3;
        int iA = a(0, this.f.length() + 1, 0);
        int i4 = this.c;
        if ((i4 & 8) == 0) {
            if ((i4 & 524288) == 0) {
                int[] iArr = this.z;
                i3 = iA + 1;
                ClassWriter classWriter = this.b;
                iArr[iA] = classWriter.c(classWriter.I) | 24117248;
            } else {
                i3 = iA + 1;
                this.z[iA] = 6;
            }
            iA = i3;
        }
        int i5 = 1;
        while (true) {
            int i6 = i5 + 1;
            char cCharAt = this.f.charAt(i5);
            if (cCharAt == 'F') {
                i = iA + 1;
                this.z[iA] = 2;
            } else if (cCharAt != 'L') {
                if (cCharAt != 'S' && cCharAt != 'I') {
                    if (cCharAt == 'J') {
                        i = iA + 1;
                        this.z[iA] = 4;
                    } else if (cCharAt != 'Z') {
                        if (cCharAt != '[') {
                            switch (cCharAt) {
                                case 'B':
                                case 'C':
                                    break;
                                case 'D':
                                    i2 = iA + 1;
                                    this.z[iA] = 3;
                                    break;
                            }
                            this.z[1] = iA - 3;
                            b();
                            return;
                        }
                        while (this.f.charAt(i6) == '[') {
                            i6++;
                        }
                        if (this.f.charAt(i6) == 'L') {
                            do {
                                i6++;
                            } while (this.f.charAt(i6) != ';');
                        }
                        i2 = iA + 1;
                        i6++;
                        this.z[iA] = this.b.c(this.f.substring(i5, i6)) | 24117248;
                        i5 = i6;
                        iA = i2;
                    }
                }
                i = iA + 1;
                this.z[iA] = 1;
            } else {
                int i7 = i6;
                while (this.f.charAt(i7) != ';') {
                    i7++;
                }
                this.z[iA] = this.b.c(this.f.substring(i6, i7)) | 24117248;
                iA++;
                i5 = i7 + 1;
            }
            iA = i;
            i5 = i6;
        }
    }

    final int a() {
        int length;
        if (this.h != 0) {
            return this.i + 6;
        }
        if (this.r.b <= 0) {
            length = 8;
        } else {
            if (this.r.b > 65535) {
                throw new RuntimeException("Method code too large!");
            }
            this.b.newUTF8("Code");
            length = this.r.b + 18 + (this.A * 8) + 8;
            if (this.E != null) {
                this.b.newUTF8("LocalVariableTable");
                length += this.E.b + 8;
            }
            if (this.G != null) {
                this.b.newUTF8("LocalVariableTypeTable");
                length += this.G.b + 8;
            }
            if (this.I != null) {
                this.b.newUTF8("LineNumberTable");
                length += this.I.b + 8;
            }
            if (this.v != null) {
                this.b.newUTF8((this.b.b & 65535) >= 50 ? "StackMapTable" : "StackMap");
                length += this.v.b + 8;
            }
            if (this.W != null) {
                this.b.newUTF8("RuntimeVisibleTypeAnnotations");
                length += this.W.a() + 8;
            }
            if (this.X != null) {
                this.b.newUTF8("RuntimeInvisibleTypeAnnotations");
                length += this.X.a() + 8;
            }
            Attribute attribute = this.J;
            if (attribute != null) {
                length += attribute.a(this.b, this.r.a, this.r.b, this.s, this.t);
            }
        }
        if (this.j > 0) {
            this.b.newUTF8("Exceptions");
            length += (this.j * 2) + 8;
        }
        if ((this.c & 4096) != 0 && ((65535 & this.b.b) < 49 || (this.c & 262144) != 0)) {
            this.b.newUTF8(AttSynthetic.ATTRIBUTE_NAME);
            length += 6;
        }
        if ((this.c & 131072) != 0) {
            this.b.newUTF8("Deprecated");
            length += 6;
        }
        if (this.g != null) {
            this.b.newUTF8("Signature");
            this.b.newUTF8(this.g);
            length += 8;
        }
        if (this.$ != null) {
            this.b.newUTF8("MethodParameters");
            length += this.$.b + 7;
        }
        if (this.l != null) {
            this.b.newUTF8("AnnotationDefault");
            length += this.l.b + 6;
        }
        if (this.m != null) {
            this.b.newUTF8("RuntimeVisibleAnnotations");
            length += this.m.a() + 8;
        }
        if (this.n != null) {
            this.b.newUTF8("RuntimeInvisibleAnnotations");
            length += this.n.a() + 8;
        }
        if (this.U != null) {
            this.b.newUTF8("RuntimeVisibleTypeAnnotations");
            length += this.U.a() + 8;
        }
        if (this.V != null) {
            this.b.newUTF8("RuntimeInvisibleTypeAnnotations");
            length += this.V.a() + 8;
        }
        if (this.o != null) {
            this.b.newUTF8("RuntimeVisibleParameterAnnotations");
            AnnotationWriter[] annotationWriterArr = this.o;
            length += ((annotationWriterArr.length - this.S) * 2) + 7;
            for (int length2 = annotationWriterArr.length - 1; length2 >= this.S; length2--) {
                AnnotationWriter annotationWriter = this.o[length2];
                length += annotationWriter == null ? 0 : annotationWriter.a();
            }
        }
        if (this.p != null) {
            this.b.newUTF8("RuntimeInvisibleParameterAnnotations");
            AnnotationWriter[] annotationWriterArr2 = this.p;
            length += ((annotationWriterArr2.length - this.S) * 2) + 7;
            for (int length3 = annotationWriterArr2.length - 1; length3 >= this.S; length3--) {
                AnnotationWriter annotationWriter2 = this.p[length3];
                length += annotationWriter2 == null ? 0 : annotationWriter2.a();
            }
        }
        Attribute attribute2 = this.q;
        return attribute2 != null ? length + attribute2.a(this.b, null, 0, -1, -1) : length;
    }

    /* JADX WARN: Failed to calculate best type for var: r1v73 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r1v73 ??, new type: com.esotericsoftware.asm.ByteVector
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.calculateFromBounds(FixTypesVisitor.java:159)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.setBestType(FixTypesVisitor.java:136)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:241)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 6 more
     */
    /* JADX WARN: Failed to calculate best type for var: r1v73 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r1v73 ??, new type: com.esotericsoftware.asm.ByteVector
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r1v88 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r1v88 ??, new type: com.esotericsoftware.asm.ByteVector
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.calculateFromBounds(FixTypesVisitor.java:159)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.setBestType(FixTypesVisitor.java:136)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:241)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 6 more
     */
    /* JADX WARN: Failed to calculate best type for var: r1v88 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r1v88 ??, new type: com.esotericsoftware.asm.ByteVector
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r23v0 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r23v0 ??, new type: com.esotericsoftware.asm.ByteVector
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r2v43 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r2v43 ??, new type: int
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.calculateFromBounds(FixTypesVisitor.java:159)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.setBestType(FixTypesVisitor.java:136)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:241)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 6 more
     */
    /* JADX WARN: Failed to calculate best type for var: r2v43 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r2v43 ??, new type: int
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r2v44 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r2v44 ??, new type: int
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to calculate best type for var: r2v45 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r2v45 ??, new type: int
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to set immutable type for var: r23v0 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r23v0 ??, new type: com.esotericsoftware.asm.ByteVector
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.applyWithWiderIgnSame(TypeUpdate.java:73)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setImmutableType(TypeInferenceVisitor.java:111)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:102)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:102)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 6 more
     */
    /*  JADX ERROR: Types fix failed
        jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r2v43 ??, new type: int
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:186)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:245)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
        Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
        	... 5 more
        */
    final void a(com.esotericsoftware.asm.ByteVector r23) {
        /*
            Method dump skipped, instruction units count: 1001
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.asm.MethodWriter.a(com.esotericsoftware.asm.ByteVector):void");
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        byteVector.putShort(this.b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, 2);
        if (z) {
            annotationWriter.g = this.m;
            this.m = annotationWriter;
        } else {
            annotationWriter.g = this.n;
            this.n = annotationWriter;
        }
        return annotationWriter;
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public AnnotationVisitor visitAnnotationDefault() {
        this.l = new ByteVector();
        return new AnnotationWriter(this.b, false, this.l, null, 0);
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitAttribute(Attribute attribute) {
        if (attribute.isCodeAttribute()) {
            attribute.a = this.J;
            this.J = attribute;
        } else {
            attribute.a = this.q;
            this.q = attribute;
        }
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitCode() {
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitEnd() {
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitFieldInsn(int i, String str, String str2, String str3) {
        int i2;
        int i3;
        this.Y = this.r.b;
        Item itemA = this.b.a(str, str2, str3);
        Label label = this.P;
        if (label != null) {
            if (this.M == 0) {
                label.h.a(i, 0, this.b, itemA);
            } else {
                char cCharAt = str3.charAt(0);
                int i4 = -2;
                switch (i) {
                    case 178:
                        i2 = this.Q + ((cCharAt == 'D' || cCharAt == 'J') ? 2 : 1);
                        break;
                    case 179:
                        i3 = this.Q;
                        if (cCharAt != 'D' && cCharAt != 'J') {
                            i4 = -1;
                        }
                        i2 = i4 + i3;
                        break;
                    case 180:
                        i2 = this.Q + ((cCharAt == 'D' || cCharAt == 'J') ? 1 : 0);
                        break;
                    default:
                        i3 = this.Q;
                        if (cCharAt == 'D' || cCharAt == 'J') {
                            i4 = -3;
                        }
                        i2 = i4 + i3;
                        break;
                }
                if (i2 > this.R) {
                    this.R = i2;
                }
                this.Q = i2;
            }
        }
        this.r.b(i, itemA.a);
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitFrame(int i, int i2, Object[] objArr, int i3, Object[] objArr2) {
        int i4;
        ByteVector byteVector;
        int i5;
        int i6;
        if (this.M == 0) {
            return;
        }
        int i7 = 0;
        if (i == -1) {
            if (this.x == null) {
                f();
            }
            this.T = i2;
            int iA = a(this.r.b, i2, i3);
            for (int i8 = 0; i8 < i2; i8++) {
                Object obj = objArr[i8];
                if (obj instanceof String) {
                    i6 = iA + 1;
                    this.z[iA] = 24117248 | this.b.c((String) obj);
                } else {
                    if (obj instanceof Integer) {
                        i6 = iA + 1;
                        this.z[iA] = ((Integer) obj).intValue();
                    } else {
                        this.z[iA] = this.b.a("", ((Label) obj).c) | 25165824;
                        iA++;
                    }
                }
                iA = i6;
            }
            while (i7 < i3) {
                Object obj2 = objArr2[i7];
                if (obj2 instanceof String) {
                    i5 = iA + 1;
                    this.z[iA] = this.b.c((String) obj2) | 24117248;
                } else if (obj2 instanceof Integer) {
                    i5 = iA + 1;
                    this.z[iA] = ((Integer) obj2).intValue();
                } else {
                    i5 = iA + 1;
                    this.z[iA] = this.b.a("", ((Label) obj2).c) | 25165824;
                }
                iA = i5;
                i7++;
            }
            b();
        } else {
            if (this.v == null) {
                this.v = new ByteVector();
                i4 = this.r.b;
            } else {
                i4 = (this.r.b - this.w) - 1;
                if (i4 < 0) {
                    if (i != 3) {
                        throw new IllegalStateException();
                    }
                    return;
                }
            }
            if (i == 0) {
                this.T = i2;
                this.v.putByte(255).putShort(i4).putShort(i2);
                for (int i9 = 0; i9 < i2; i9++) {
                    a(objArr[i9]);
                }
                this.v.putShort(i3);
                while (i7 < i3) {
                    a(objArr2[i7]);
                    i7++;
                }
            } else if (i != 1) {
                int i10 = 251;
                if (i == 2) {
                    this.T -= i2;
                    byteVector = this.v;
                    i10 = 251 - i2;
                } else if (i == 3) {
                    byteVector = this.v;
                    if (i4 < 64) {
                        byteVector.putByte(i4);
                    }
                } else if (i == 4) {
                    ByteVector byteVector2 = this.v;
                    if (i4 < 64) {
                        byteVector2.putByte(i4 + 64);
                    } else {
                        byteVector2.putByte(247).putShort(i4);
                    }
                    a(objArr2[0]);
                }
                byteVector.putByte(i10).putShort(i4);
            } else {
                this.T += i2;
                this.v.putByte(i2 + 251).putShort(i4);
                while (i7 < i2) {
                    a(objArr[i7]);
                    i7++;
                }
            }
            this.w = this.r.b;
            this.u++;
        }
        this.s = Math.max(this.s, i3);
        this.t = Math.max(this.t, this.T);
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitIincInsn(int i, int i2) {
        int i3;
        this.Y = this.r.b;
        Label label = this.P;
        if (label != null && this.M == 0) {
            label.h.a(132, i, (ClassWriter) null, (Item) null);
        }
        if (this.M != 2 && (i3 = i + 1) > this.t) {
            this.t = i3;
        }
        if (i > 255 || i2 > 127 || i2 < -128) {
            this.r.putByte(196).b(132, i).putShort(i2);
        } else {
            this.r.putByte(132).a(i, i2);
        }
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitInsn(int i) {
        this.Y = this.r.b;
        this.r.putByte(i);
        Label label = this.P;
        if (label != null) {
            if (this.M == 0) {
                label.h.a(i, 0, (ClassWriter) null, (Item) null);
            } else {
                int i2 = this.Q + Frame.a[i];
                if (i2 > this.R) {
                    this.R = i2;
                }
                this.Q = i2;
            }
            if ((i < 172 || i > 177) && i != 191) {
                return;
            }
            e();
        }
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public AnnotationVisitor visitInsnAnnotation(int i, TypePath typePath, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        AnnotationWriter.a((i & (-16776961)) | (this.Y << 8), typePath, byteVector);
        byteVector.putShort(this.b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, byteVector.b - 2);
        if (z) {
            annotationWriter.g = this.W;
            this.W = annotationWriter;
        } else {
            annotationWriter.g = this.X;
            this.X = annotationWriter;
        }
        return annotationWriter;
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitIntInsn(int i, int i2) {
        this.Y = this.r.b;
        Label label = this.P;
        if (label != null) {
            if (this.M == 0) {
                label.h.a(i, i2, (ClassWriter) null, (Item) null);
            } else if (i != 188) {
                int i3 = this.Q + 1;
                if (i3 > this.R) {
                    this.R = i3;
                }
                this.Q = i3;
            }
        }
        if (i == 17) {
            this.r.b(i, i2);
        } else {
            this.r.a(i, i2);
        }
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitInvokeDynamicInsn(String str, String str2, Handle handle, Object... objArr) {
        this.Y = this.r.b;
        Item itemA = this.b.a(str, str2, handle, objArr);
        int argumentsAndReturnSizes = itemA.c;
        Label label = this.P;
        if (label != null) {
            if (this.M == 0) {
                label.h.a(186, 0, this.b, itemA);
            } else {
                if (argumentsAndReturnSizes == 0) {
                    argumentsAndReturnSizes = Type.getArgumentsAndReturnSizes(str2);
                    itemA.c = argumentsAndReturnSizes;
                }
                int i = (this.Q - (argumentsAndReturnSizes >> 2)) + (argumentsAndReturnSizes & 3) + 1;
                if (i > this.R) {
                    this.R = i;
                }
                this.Q = i;
            }
        }
        this.r.b(186, itemA.a);
        this.r.putShort(0);
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitJumpInsn(int i, Label label) {
        this.Y = this.r.b;
        Label label2 = this.P;
        Label label3 = null;
        if (label2 != null) {
            if (this.M == 0) {
                label2.h.a(i, 0, (ClassWriter) null, (Item) null);
                label.a().a |= 16;
                a(0, label);
                if (i != 167) {
                    label3 = new Label();
                }
            } else if (i == 168) {
                if ((label.a & 512) == 0) {
                    label.a |= 512;
                    this.L++;
                }
                this.P.a |= 128;
                a(this.Q + 1, label);
                label3 = new Label();
            } else {
                int i2 = this.Q + Frame.a[i];
                this.Q = i2;
                a(i2, label);
            }
        }
        if ((label.a & 2) == 0 || label.c - this.r.b >= -32768) {
            this.r.putByte(i);
            ByteVector byteVector = this.r;
            label.a(this, byteVector, byteVector.b - 1, false);
        } else {
            if (i == 167) {
                this.r.putByte(200);
            } else if (i == 168) {
                this.r.putByte(201);
            } else {
                if (label3 != null) {
                    label3.a |= 16;
                }
                this.r.putByte(i <= 166 ? ((i + 1) ^ 1) - 1 : i ^ 1);
                this.r.putShort(8);
                this.r.putByte(200);
            }
            ByteVector byteVector2 = this.r;
            label.a(this, byteVector2, byteVector2.b - 1, true);
        }
        if (this.P != null) {
            if (label3 != null) {
                visitLabel(label3);
            }
            if (i == 167) {
                e();
            }
        }
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitLabel(Label label) {
        Label label2;
        this.K |= label.a(this, this.r.b, this.r.a);
        if ((label.a & 1) != 0) {
            return;
        }
        int i = this.M;
        if (i == 0) {
            if (this.P != null) {
                if (label.c == this.P.c) {
                    this.P.a |= label.a & 16;
                    label.h = this.P.h;
                    return;
                }
                a(0, label);
            }
            this.P = label;
            if (label.h == null) {
                label.h = new Frame();
                label.h.b = label;
            }
            if (this.O != null) {
                if (label.c == this.O.c) {
                    this.O.a |= label.a & 16;
                    label.h = this.O.h;
                    this.P = this.O;
                    return;
                }
                label2 = this.O;
                label2.i = label;
            }
        } else {
            if (i != 1) {
                return;
            }
            Label label3 = this.P;
            if (label3 != null) {
                label3.g = this.R;
                a(this.Q, label);
            }
            this.P = label;
            this.Q = 0;
            this.R = 0;
            label2 = this.O;
            if (label2 != null) {
                label2.i = label;
            }
        }
        this.O = label;
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitLdcInsn(Object obj) {
        ByteVector byteVector;
        int i;
        this.Y = this.r.b;
        Item itemA = this.b.a(obj);
        Label label = this.P;
        if (label != null) {
            if (this.M == 0) {
                label.h.a(18, 0, this.b, itemA);
            } else {
                int i2 = (itemA.b == 5 || itemA.b == 6) ? this.Q + 2 : this.Q + 1;
                if (i2 > this.R) {
                    this.R = i2;
                }
                this.Q = i2;
            }
        }
        int i3 = itemA.a;
        if (itemA.b == 5 || itemA.b == 6) {
            byteVector = this.r;
            i = 20;
        } else if (i3 < 256) {
            this.r.a(18, i3);
            return;
        } else {
            byteVector = this.r;
            i = 19;
        }
        byteVector.b(i, i3);
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitLineNumber(int i, Label label) {
        if (this.I == null) {
            this.I = new ByteVector();
        }
        this.H++;
        this.I.putShort(label.c);
        this.I.putShort(i);
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitLocalVariable(String str, String str2, String str3, Label label, Label label2, int i) {
        if (str3 != null) {
            if (this.G == null) {
                this.G = new ByteVector();
            }
            this.F++;
            this.G.putShort(label.c).putShort(label2.c - label.c).putShort(this.b.newUTF8(str)).putShort(this.b.newUTF8(str3)).putShort(i);
        }
        if (this.E == null) {
            this.E = new ByteVector();
        }
        this.D++;
        this.E.putShort(label.c).putShort(label2.c - label.c).putShort(this.b.newUTF8(str)).putShort(this.b.newUTF8(str2)).putShort(i);
        if (this.M != 2) {
            char cCharAt = str2.charAt(0);
            int i2 = i + ((cCharAt == 'J' || cCharAt == 'D') ? 2 : 1);
            if (i2 > this.t) {
                this.t = i2;
            }
        }
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public AnnotationVisitor visitLocalVariableAnnotation(int i, TypePath typePath, Label[] labelArr, Label[] labelArr2, int[] iArr, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        byteVector.putByte(i >>> 24).putShort(labelArr.length);
        for (int i2 = 0; i2 < labelArr.length; i2++) {
            byteVector.putShort(labelArr[i2].c).putShort(labelArr2[i2].c - labelArr[i2].c).putShort(iArr[i2]);
        }
        if (typePath == null) {
            byteVector.putByte(0);
        } else {
            byteVector.putByteArray(typePath.a, typePath.b, (typePath.a[typePath.b] * 2) + 1);
        }
        byteVector.putShort(this.b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, byteVector.b - 2);
        if (z) {
            annotationWriter.g = this.W;
            this.W = annotationWriter;
        } else {
            annotationWriter.g = this.X;
            this.X = annotationWriter;
        }
        return annotationWriter;
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitLookupSwitchInsn(Label label, int[] iArr, Label[] labelArr) {
        this.Y = this.r.b;
        int i = this.r.b;
        this.r.putByte(171);
        ByteVector byteVector = this.r;
        byteVector.putByteArray(null, 0, (4 - (byteVector.b % 4)) % 4);
        label.a(this, this.r, i, true);
        this.r.putInt(labelArr.length);
        for (int i2 = 0; i2 < labelArr.length; i2++) {
            this.r.putInt(iArr[i2]);
            labelArr[i2].a(this, this.r, i, true);
        }
        a(label, labelArr);
    }

    /*  JADX ERROR: JadxOverflowException in pass: LoopRegionVisitor
        jadx.core.utils.exceptions.JadxOverflowException: LoopRegionVisitor.assignOnlyInLoop endless recursion
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitMaxs(int r14, int r15) {
        /*
            Method dump skipped, instruction units count: 507
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.asm.MethodWriter.visitMaxs(int, int):void");
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitMethodInsn(int i, String str, String str2, String str3, boolean z) {
        this.Y = this.r.b;
        Item itemA = this.b.a(str, str2, str3, z);
        int argumentsAndReturnSizes = itemA.c;
        Label label = this.P;
        if (label != null) {
            if (this.M == 0) {
                label.h.a(i, 0, this.b, itemA);
            } else {
                if (argumentsAndReturnSizes == 0) {
                    argumentsAndReturnSizes = Type.getArgumentsAndReturnSizes(str3);
                    itemA.c = argumentsAndReturnSizes;
                }
                int i2 = i == 184 ? (this.Q - (argumentsAndReturnSizes >> 2)) + (argumentsAndReturnSizes & 3) + 1 : (this.Q - (argumentsAndReturnSizes >> 2)) + (argumentsAndReturnSizes & 3);
                if (i2 > this.R) {
                    this.R = i2;
                }
                this.Q = i2;
            }
        }
        if (i != 185) {
            this.r.b(i, itemA.a);
            return;
        }
        if (argumentsAndReturnSizes == 0) {
            argumentsAndReturnSizes = Type.getArgumentsAndReturnSizes(str3);
            itemA.c = argumentsAndReturnSizes;
        }
        this.r.b(185, itemA.a).a(argumentsAndReturnSizes >> 2, 0);
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitMultiANewArrayInsn(String str, int i) {
        this.Y = this.r.b;
        Item itemA = this.b.a(str);
        Label label = this.P;
        if (label != null) {
            if (this.M == 0) {
                label.h.a(197, i, this.b, itemA);
            } else {
                this.Q += 1 - i;
            }
        }
        this.r.b(197, itemA.a).putByte(i);
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitParameter(String str, int i) {
        if (this.$ == null) {
            this.$ = new ByteVector();
        }
        this.Z++;
        this.$.putShort(str == null ? 0 : this.b.newUTF8(str)).putShort(i);
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public AnnotationVisitor visitParameterAnnotation(int i, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        if ("Ljava/lang/Synthetic;".equals(str)) {
            this.S = Math.max(this.S, i + 1);
            return new AnnotationWriter(this.b, false, byteVector, null, 0);
        }
        byteVector.putShort(this.b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, 2);
        if (z) {
            if (this.o == null) {
                this.o = new AnnotationWriter[Type.getArgumentTypes(this.f).length];
            }
            annotationWriter.g = this.o[i];
            this.o[i] = annotationWriter;
        } else {
            if (this.p == null) {
                this.p = new AnnotationWriter[Type.getArgumentTypes(this.f).length];
            }
            annotationWriter.g = this.p[i];
            this.p[i] = annotationWriter;
        }
        return annotationWriter;
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitTableSwitchInsn(int i, int i2, Label label, Label... labelArr) {
        this.Y = this.r.b;
        int i3 = this.r.b;
        this.r.putByte(170);
        ByteVector byteVector = this.r;
        byteVector.putByteArray(null, 0, (4 - (byteVector.b % 4)) % 4);
        label.a(this, this.r, i3, true);
        this.r.putInt(i).putInt(i2);
        for (Label label2 : labelArr) {
            label2.a(this, this.r, i3, true);
        }
        a(label, labelArr);
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public AnnotationVisitor visitTryCatchAnnotation(int i, TypePath typePath, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        AnnotationWriter.a(i, typePath, byteVector);
        byteVector.putShort(this.b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, byteVector.b - 2);
        if (z) {
            annotationWriter.g = this.W;
            this.W = annotationWriter;
        } else {
            annotationWriter.g = this.X;
            this.X = annotationWriter;
        }
        return annotationWriter;
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitTryCatchBlock(Label label, Label label2, Label label3, String str) {
        this.A++;
        Handler handler = new Handler();
        handler.a = label;
        handler.b = label2;
        handler.c = label3;
        handler.d = str;
        handler.e = str != null ? this.b.newClass(str) : 0;
        Handler handler2 = this.C;
        if (handler2 == null) {
            this.B = handler;
        } else {
            handler2.f = handler;
        }
        this.C = handler;
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        AnnotationWriter.a(i, typePath, byteVector);
        byteVector.putShort(this.b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, byteVector.b - 2);
        if (z) {
            annotationWriter.g = this.U;
            this.U = annotationWriter;
        } else {
            annotationWriter.g = this.V;
            this.V = annotationWriter;
        }
        return annotationWriter;
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitTypeInsn(int i, String str) {
        this.Y = this.r.b;
        Item itemA = this.b.a(str);
        Label label = this.P;
        if (label != null) {
            if (this.M == 0) {
                label.h.a(i, this.r.b, this.b, itemA);
            } else if (i == 187) {
                int i2 = this.Q + 1;
                if (i2 > this.R) {
                    this.R = i2;
                }
                this.Q = i2;
            }
        }
        this.r.b(i, itemA.a);
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitVarInsn(int i, int i2) {
        this.Y = this.r.b;
        Label label = this.P;
        if (label != null) {
            if (this.M == 0) {
                label.h.a(i, i2, (ClassWriter) null, (Item) null);
            } else if (i == 169) {
                label.a |= 256;
                this.P.f = this.Q;
                e();
            } else {
                int i3 = this.Q + Frame.a[i];
                if (i3 > this.R) {
                    this.R = i3;
                }
                this.Q = i3;
            }
        }
        if (this.M != 2) {
            int i4 = (i == 22 || i == 24 || i == 55 || i == 57) ? i2 + 2 : i2 + 1;
            if (i4 > this.t) {
                this.t = i4;
            }
        }
        if (i2 >= 4 || i == 169) {
            ByteVector byteVector = this.r;
            if (i2 >= 256) {
                byteVector.putByte(196).b(i, i2);
            } else {
                byteVector.a(i, i2);
            }
        } else {
            this.r.putByte((i < 54 ? ((i - 21) << 2) + 26 : ((i - 54) << 2) + 59) + i2);
        }
        if (i < 54 || this.M != 0 || this.A <= 0) {
            return;
        }
        visitLabel(new Label());
    }
}
