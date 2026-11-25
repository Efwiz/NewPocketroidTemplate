package com.esotericsoftware.asm;

import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import com.android.dx.cf.attrib.AttBootstrapMethods;
import com.android.dx.cf.attrib.AttSourceDebugExtension;
import com.android.dx.cf.attrib.AttSynthetic;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes8.dex */
public class ClassReader {
    public static final int EXPAND_FRAMES = 8;
    public static final int SKIP_CODE = 1;
    public static final int SKIP_DEBUG = 2;
    public static final int SKIP_FRAMES = 4;
    private final int[] a;
    public final byte[] b;
    private final String[] c;
    private final int d;
    public final int header;

    public ClassReader(InputStream inputStream) throws IOException {
        this(a(inputStream, false));
    }

    public ClassReader(String str) throws IOException {
        this(a(ClassLoader.getSystemResourceAsStream(new StringBuffer().append(str.replace('.', '/')).append(".class").toString()), true));
    }

    public ClassReader(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }

    public ClassReader(byte[] bArr, int i, int i2) {
        this.b = bArr;
        if (readShort(i + 6) > 52) {
            throw new IllegalArgumentException();
        }
        int[] iArr = new int[readUnsignedShort(i + 8)];
        this.a = iArr;
        int length = iArr.length;
        this.c = new String[length];
        int i3 = i + 10;
        int i4 = 0;
        int i5 = 1;
        while (i5 < length) {
            int i6 = i3 + 1;
            this.a[i5] = i6;
            byte b = bArr[i3];
            int unsignedShort = 3;
            if (b == 1) {
                unsignedShort = 3 + readUnsignedShort(i6);
                if (unsignedShort > i4) {
                    i4 = unsignedShort;
                }
            } else if (b != 15) {
                unsignedShort = 5;
                if (b != 18 && b != 3 && b != 4) {
                    if (b != 5 && b != 6) {
                        switch (b) {
                            case 9:
                            case 10:
                            case 11:
                            case 12:
                                break;
                            default:
                                continue;
                        }
                    } else {
                        i5++;
                        unsignedShort = 9;
                    }
                }
            } else {
                unsignedShort = 4;
            }
            i3 += unsignedShort;
            i5++;
        }
        this.d = i4;
        this.header = i3;
    }

    private int a() {
        int i = this.header;
        int unsignedShort = i + 8 + (readUnsignedShort(i + 6) * 2);
        for (int unsignedShort2 = readUnsignedShort(unsignedShort); unsignedShort2 > 0; unsignedShort2--) {
            for (int unsignedShort3 = readUnsignedShort(unsignedShort + 8); unsignedShort3 > 0; unsignedShort3--) {
                unsignedShort += readInt(unsignedShort + 12) + 6;
            }
            unsignedShort += 8;
        }
        int i2 = unsignedShort + 2;
        for (int unsignedShort4 = readUnsignedShort(i2); unsignedShort4 > 0; unsignedShort4--) {
            for (int unsignedShort5 = readUnsignedShort(i2 + 8); unsignedShort5 > 0; unsignedShort5--) {
                i2 += readInt(i2 + 12) + 6;
            }
            i2 += 8;
        }
        return i2 + 2;
    }

    private int a(int i, boolean z, boolean z2, Context context) {
        int iA;
        int i2;
        char[] cArr = context.c;
        Label[] labelArr = context.h;
        if (z) {
            int i3 = i + 1;
            i2 = this.b[i] & 255;
            iA = i3;
        } else {
            context.o = -1;
            iA = i;
            i2 = 255;
        }
        int i4 = 0;
        context.r = 0;
        if (i2 < 64) {
            context.p = 3;
            context.t = 0;
        } else if (i2 < 128) {
            i2 -= 64;
            iA = a(context.u, 0, iA, cArr, labelArr);
            context.p = 4;
            context.t = 1;
        } else {
            int unsignedShort = readUnsignedShort(iA);
            int i5 = iA + 2;
            if (i2 == 247) {
                iA = a(context.u, 0, i5, cArr, labelArr);
                context.p = 4;
                context.t = 1;
            } else {
                if (i2 >= 248 && i2 < 251) {
                    context.p = 2;
                    context.r = 251 - i2;
                    context.q -= context.r;
                } else if (i2 != 251) {
                    if (i2 >= 255) {
                        context.p = 0;
                        int unsignedShort2 = readUnsignedShort(i5);
                        int iA2 = iA + 4;
                        context.r = unsignedShort2;
                        context.q = unsignedShort2;
                        int i6 = 0;
                        while (unsignedShort2 > 0) {
                            iA2 = a(context.s, i6, iA2, cArr, labelArr);
                            unsignedShort2--;
                            i6++;
                        }
                        int unsignedShort3 = readUnsignedShort(iA2);
                        iA = iA2 + 2;
                        context.t = unsignedShort3;
                        while (true) {
                            int i7 = i4;
                            if (unsignedShort3 <= 0) {
                                break;
                            }
                            i4 = i7 + 1;
                            iA = a(context.u, i7, iA, cArr, labelArr);
                            unsignedShort3--;
                        }
                    } else {
                        int i8 = i2 - 251;
                        int i9 = z2 ? context.q : 0;
                        iA = i5;
                        int i10 = i8;
                        while (i10 > 0) {
                            iA = a(context.s, i9, iA, cArr, labelArr);
                            i10--;
                            i9++;
                        }
                        context.p = 1;
                        context.r = i8;
                        context.q += context.r;
                        context.t = 0;
                    }
                } else {
                    context.p = 3;
                }
                context.t = 0;
                iA = i5;
            }
            i2 = unsignedShort;
        }
        context.o += i2 + 1;
        readLabel(context.o, labelArr);
        return iA;
    }

    /* JADX WARN: Code duplicated, block: B:97:0x01ea  */
    private int a(int i, char[] cArr, String str, AnnotationVisitor annotationVisitor) {
        Object type;
        Object sh;
        int i2 = 0;
        if (annotationVisitor == null) {
            int i3 = this.b[i] & 255;
            if (i3 == 64) {
                return a(i + 3, cArr, true, (AnnotationVisitor) null);
            }
            if (i3 != 91) {
                return i3 != 101 ? i + 3 : i + 5;
            }
            return a(i + 1, cArr, false, (AnnotationVisitor) null);
        }
        int i4 = i + 1;
        int i5 = this.b[i] & 255;
        if (i5 == 64) {
            return a(i + 3, cArr, true, annotationVisitor.visitAnnotation(str, readUTF8(i4, cArr)));
        }
        if (i5 == 70) {
            type = readConst(readUnsignedShort(i4), cArr);
            annotationVisitor.visit(str, type);
        } else {
            if (i5 != 83) {
                if (i5 == 99) {
                    type = Type.getType(readUTF8(i4, cArr));
                } else {
                    if (i5 == 101) {
                        annotationVisitor.visitEnum(str, readUTF8(i4, cArr), readUTF8(i + 3, cArr));
                        return i + 5;
                    }
                    if (i5 == 115) {
                        type = readUTF8(i4, cArr);
                    } else if (i5 == 73 || i5 == 74) {
                        type = readConst(readUnsignedShort(i4), cArr);
                    } else if (i5 == 90) {
                        type = readInt(this.a[readUnsignedShort(i4)]) == 0 ? Boolean.FALSE : Boolean.TRUE;
                    } else {
                        if (i5 == 91) {
                            int unsignedShort = readUnsignedShort(i4);
                            int i6 = i + 3;
                            if (unsignedShort == 0) {
                                return a(i + 1, cArr, false, annotationVisitor.visitArray(str));
                            }
                            int i7 = i + 4;
                            int i8 = this.b[i6] & 255;
                            if (i8 == 70) {
                                float[] fArr = new float[unsignedShort];
                                while (i2 < unsignedShort) {
                                    fArr[i2] = Float.intBitsToFloat(readInt(this.a[readUnsignedShort(i7)]));
                                    i7 += 3;
                                    i2++;
                                }
                                annotationVisitor.visit(str, fArr);
                            } else if (i8 == 83) {
                                short[] sArr = new short[unsignedShort];
                                while (i2 < unsignedShort) {
                                    sArr[i2] = (short) readInt(this.a[readUnsignedShort(i7)]);
                                    i7 += 3;
                                    i2++;
                                }
                                annotationVisitor.visit(str, sArr);
                            } else if (i8 == 90) {
                                boolean[] zArr = new boolean[unsignedShort];
                                for (int i9 = 0; i9 < unsignedShort; i9++) {
                                    zArr[i9] = readInt(this.a[readUnsignedShort(i7)]) != 0;
                                    i7 += 3;
                                }
                                annotationVisitor.visit(str, zArr);
                            } else if (i8 == 73) {
                                int[] iArr = new int[unsignedShort];
                                while (i2 < unsignedShort) {
                                    iArr[i2] = readInt(this.a[readUnsignedShort(i7)]);
                                    i7 += 3;
                                    i2++;
                                }
                                annotationVisitor.visit(str, iArr);
                            } else if (i8 != 74) {
                                switch (i8) {
                                    case 66:
                                        byte[] bArr = new byte[unsignedShort];
                                        while (i2 < unsignedShort) {
                                            bArr[i2] = (byte) readInt(this.a[readUnsignedShort(i7)]);
                                            i7 += 3;
                                            i2++;
                                        }
                                        annotationVisitor.visit(str, bArr);
                                        break;
                                    case 67:
                                        char[] cArr2 = new char[unsignedShort];
                                        while (i2 < unsignedShort) {
                                            cArr2[i2] = (char) readInt(this.a[readUnsignedShort(i7)]);
                                            i7 += 3;
                                            i2++;
                                        }
                                        annotationVisitor.visit(str, cArr2);
                                        break;
                                    case 68:
                                        double[] dArr = new double[unsignedShort];
                                        while (i2 < unsignedShort) {
                                            dArr[i2] = Double.longBitsToDouble(readLong(this.a[readUnsignedShort(i7)]));
                                            i7 += 3;
                                            i2++;
                                        }
                                        annotationVisitor.visit(str, dArr);
                                        break;
                                    default:
                                        return a(i + 1, cArr, false, annotationVisitor.visitArray(str));
                                }
                            } else {
                                long[] jArr = new long[unsignedShort];
                                while (i2 < unsignedShort) {
                                    jArr[i2] = readLong(this.a[readUnsignedShort(i7)]);
                                    i7 += 3;
                                    i2++;
                                }
                                annotationVisitor.visit(str, jArr);
                            }
                            return i7 - 1;
                        }
                        switch (i5) {
                            case 66:
                                sh = new Byte((byte) readInt(this.a[readUnsignedShort(i4)]));
                                break;
                            case 67:
                                sh = new Character((char) readInt(this.a[readUnsignedShort(i4)]));
                                break;
                            case 68:
                                type = readConst(readUnsignedShort(i4), cArr);
                                break;
                            default:
                                return i4;
                        }
                    }
                }
                annotationVisitor.visit(str, type);
            } else {
                sh = new Short((short) readInt(this.a[readUnsignedShort(i4)]));
            }
            annotationVisitor.visit(str, sh);
        }
        return i + 3;
    }

    private int a(int i, char[] cArr, boolean z, AnnotationVisitor annotationVisitor) {
        int unsignedShort = readUnsignedShort(i);
        int iA = i + 2;
        if (z) {
            while (unsignedShort > 0) {
                iA = a(iA + 2, cArr, readUTF8(iA, cArr), annotationVisitor);
                unsignedShort--;
            }
        } else {
            while (unsignedShort > 0) {
                iA = a(iA, cArr, (String) null, annotationVisitor);
                unsignedShort--;
            }
        }
        if (annotationVisitor != null) {
            annotationVisitor.visitEnd();
        }
        return iA;
    }

    private int a(ClassVisitor classVisitor, Context context, int i) {
        int i2;
        Context context2 = context;
        char[] cArr = context2.c;
        int unsignedShort = readUnsignedShort(i);
        String utf8 = readUTF8(i + 2, cArr);
        String utf9 = readUTF8(i + 4, cArr);
        int i3 = i + 6;
        int i4 = i3;
        int i5 = unsignedShort;
        int unsignedShort2 = readUnsignedShort(i3);
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        Attribute attributeA = null;
        String utf10 = null;
        Object obj = null;
        while (unsignedShort2 > 0) {
            String utf11 = readUTF8(i4 + 2, cArr);
            if ("ConstantValue".equals(utf11)) {
                int unsignedShort3 = readUnsignedShort(i4 + 8);
                obj = unsignedShort3 == 0 ? null : readConst(unsignedShort3, cArr);
            } else if ("Signature".equals(utf11)) {
                utf10 = readUTF8(i4 + 8, cArr);
            } else {
                if ("Deprecated".equals(utf11)) {
                    i2 = 131072;
                } else if (AttSynthetic.ATTRIBUTE_NAME.equals(utf11)) {
                    i2 = 266240;
                } else if ("RuntimeVisibleAnnotations".equals(utf11)) {
                    i9 = i4 + 8;
                } else if ("RuntimeVisibleTypeAnnotations".equals(utf11)) {
                    i7 = i4 + 8;
                } else if ("RuntimeInvisibleAnnotations".equals(utf11)) {
                    i8 = i4 + 8;
                } else if ("RuntimeInvisibleTypeAnnotations".equals(utf11)) {
                    i6 = i4 + 8;
                } else {
                    Attribute attribute = attributeA;
                    int i10 = i6;
                    int i11 = i7;
                    i8 = i8;
                    int i12 = i9;
                    attributeA = a(context2.a, utf11, i4 + 8, readInt(i4 + 4), cArr, -1, null);
                    if (attributeA != null) {
                        attributeA.a = attribute;
                    } else {
                        attributeA = attribute;
                    }
                    i9 = i12;
                    i6 = i10;
                    i7 = i11;
                }
                i5 |= i2;
            }
            i4 += readInt(i4 + 4) + 6;
            unsignedShort2--;
            context2 = context;
        }
        Attribute attribute2 = attributeA;
        int i13 = i6;
        int i14 = i7;
        int i15 = i8;
        int i16 = i9;
        int i17 = i4 + 2;
        FieldVisitor fieldVisitorVisitField = classVisitor.visitField(i5, utf8, utf9, utf10, obj);
        if (fieldVisitorVisitField == null) {
            return i17;
        }
        if (i16 != 0) {
            int iA = i16 + 2;
            for (int unsignedShort4 = readUnsignedShort(i16); unsignedShort4 > 0; unsignedShort4--) {
                iA = a(iA + 2, cArr, true, fieldVisitorVisitField.visitAnnotation(readUTF8(iA, cArr), true));
            }
        }
        if (i15 != 0) {
            int iA2 = i15 + 2;
            for (int unsignedShort5 = readUnsignedShort(i15); unsignedShort5 > 0; unsignedShort5--) {
                iA2 = a(iA2 + 2, cArr, true, fieldVisitorVisitField.visitAnnotation(readUTF8(iA2, cArr), false));
            }
        }
        if (i14 != 0) {
            int iA3 = i14 + 2;
            for (int unsignedShort6 = readUnsignedShort(i14); unsignedShort6 > 0; unsignedShort6--) {
                int iA4 = a(context, iA3);
                iA3 = a(iA4 + 2, cArr, true, fieldVisitorVisitField.visitTypeAnnotation(context.i, context.j, readUTF8(iA4, cArr), true));
            }
        }
        if (i13 != 0) {
            int iA5 = i13 + 2;
            for (int unsignedShort7 = readUnsignedShort(i13); unsignedShort7 > 0; unsignedShort7--) {
                int iA6 = a(context, iA5);
                iA5 = a(iA6 + 2, cArr, true, fieldVisitorVisitField.visitTypeAnnotation(context.i, context.j, readUTF8(iA6, cArr), false));
            }
        }
        while (attribute2 != null) {
            Attribute attribute3 = attribute2.a;
            attribute2.a = null;
            fieldVisitorVisitField.visitAttribute(attribute2);
            attribute2 = attribute3;
        }
        fieldVisitorVisitField.visitEnd();
        return i17;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0075  */
    private int a(Context context, int i) {
        int i2;
        int i3;
        int i4 = readInt(i);
        int i5 = i4 >>> 24;
        if (i5 == 0 || i5 == 1) {
            i2 = i4 & (-65536);
            i3 = i + 2;
        } else {
            int i6 = ViewCompat.MEASURED_STATE_MASK;
            if (i5 != 64 && i5 != 65) {
                switch (i5) {
                    case 19:
                    case 20:
                    case 21:
                        i2 = i4 & ViewCompat.MEASURED_STATE_MASK;
                        i3 = i + 1;
                        break;
                    case 22:
                        i2 = i4 & (-65536);
                        i3 = i + 2;
                        break;
                    default:
                        switch (i5) {
                            case 71:
                            case 72:
                            case 73:
                            case 74:
                            case 75:
                                i2 = i4 & (-16776961);
                                i3 = i + 4;
                                break;
                            default:
                                if (i5 < 67) {
                                    i6 = InputDeviceCompat.SOURCE_ANY;
                                }
                                i2 = i4 & i6;
                                i3 = i + 3;
                                break;
                        }
                        break;
                }
            } else {
                i2 = i4 & ViewCompat.MEASURED_STATE_MASK;
                int unsignedShort = readUnsignedShort(i + 1);
                context.l = new Label[unsignedShort];
                context.m = new Label[unsignedShort];
                context.n = new int[unsignedShort];
                i3 = i + 3;
                for (int i7 = 0; i7 < unsignedShort; i7++) {
                    int unsignedShort2 = readUnsignedShort(i3);
                    int unsignedShort3 = readUnsignedShort(i3 + 2);
                    context.l[i7] = readLabel(unsignedShort2, context.h);
                    context.m[i7] = readLabel(unsignedShort2 + unsignedShort3, context.h);
                    context.n[i7] = readUnsignedShort(i3 + 4);
                    i3 += 6;
                }
            }
        }
        int i8 = readByte(i3);
        context.i = i2;
        context.j = i8 == 0 ? null : new TypePath(this.b, i3);
        return i3 + 1 + (i8 * 2);
    }

    private int a(Object[] objArr, int i, int i2, char[] cArr, Label[] labelArr) {
        int i3 = i2 + 1;
        switch (this.b[i2] & 255) {
            case 0:
                objArr[i] = Opcodes.TOP;
                return i3;
            case 1:
                objArr[i] = Opcodes.INTEGER;
                return i3;
            case 2:
                objArr[i] = Opcodes.FLOAT;
                return i3;
            case 3:
                objArr[i] = Opcodes.DOUBLE;
                return i3;
            case 4:
                objArr[i] = Opcodes.LONG;
                return i3;
            case 5:
                objArr[i] = Opcodes.NULL;
                return i3;
            case 6:
                objArr[i] = Opcodes.UNINITIALIZED_THIS;
                return i3;
            case 7:
                objArr[i] = readClass(i3, cArr);
                break;
            default:
                objArr[i] = readLabel(readUnsignedShort(i3), labelArr);
                break;
        }
        return i2 + 3;
    }

    private Attribute a(Attribute[] attributeArr, String str, int i, int i2, char[] cArr, int i3, Label[] labelArr) {
        for (int i4 = 0; i4 < attributeArr.length; i4++) {
            if (attributeArr[i4].type.equals(str)) {
                return attributeArr[i4].read(this, i, i2, cArr, i3, labelArr);
            }
        }
        return new Attribute(str).read(this, i, i2, null, -1, null);
    }

    private String a(int i, int i2, char[] cArr) {
        int i3;
        int i4 = i2 + i;
        byte[] bArr = this.b;
        int i5 = 0;
        char c = 0;
        char c2 = 0;
        while (i < i4) {
            int i6 = i + 1;
            byte b = bArr[i];
            if (c == 0) {
                int i7 = b & 255;
                if (i7 < 128) {
                    cArr[i5] = (char) i7;
                    i5++;
                } else if (i7 >= 224 || i7 <= 191) {
                    c2 = (char) (b & 15);
                    c = 2;
                } else {
                    i3 = b & 31;
                    c2 = (char) i3;
                    c = 1;
                }
            } else if (c == 1) {
                cArr[i5] = (char) ((b & 63) | (c2 << 6));
                i5++;
                c = 0;
            } else if (c == 2) {
                i3 = (b & 63) | (c2 << 6);
                c2 = (char) i3;
                c = 1;
            }
            i = i6;
        }
        return new String(cArr, 0, i5);
    }

    private void a(ClassWriter classWriter, Item[] itemArr, char[] cArr) {
        int iA = a();
        for (int unsignedShort = readUnsignedShort(iA); unsignedShort > 0; unsignedShort--) {
            if (AttBootstrapMethods.ATTRIBUTE_NAME.equals(readUTF8(iA + 2, cArr))) {
                int unsignedShort2 = readUnsignedShort(iA + 8);
                int i = iA + 10;
                int i2 = i;
                for (int i3 = 0; i3 < unsignedShort2; i3++) {
                    int i4 = (i2 - iA) - 10;
                    int iHashCode = readConst(readUnsignedShort(i2), cArr).hashCode();
                    for (int unsignedShort3 = readUnsignedShort(i2 + 2); unsignedShort3 > 0; unsignedShort3--) {
                        iHashCode ^= readConst(readUnsignedShort(i2 + 4), cArr).hashCode();
                        i2 += 2;
                    }
                    i2 += 4;
                    Item item = new Item(i3);
                    item.a(i4, iHashCode & Integer.MAX_VALUE);
                    int length = item.j % itemArr.length;
                    item.k = itemArr[length];
                    itemArr[length] = item;
                }
                int i5 = readInt(iA + 4);
                ByteVector byteVector = new ByteVector(i5 + 62);
                byteVector.putByteArray(this.b, i, i5 - 2);
                classWriter.z = unsignedShort2;
                classWriter.A = byteVector;
                return;
            }
            iA += readInt(iA + 4) + 6;
        }
    }

    private void a(Context context) {
        int i;
        String str = context.g;
        Object[] objArr = context.s;
        int i2 = 0;
        if ((context.e & 8) == 0) {
            if ("<init>".equals(context.f)) {
                objArr[0] = Opcodes.UNINITIALIZED_THIS;
            } else {
                objArr[0] = readClass(this.header + 2, context.c);
            }
            i2 = 1;
        }
        int i3 = 1;
        while (true) {
            int i4 = i3 + 1;
            char cCharAt = str.charAt(i3);
            if (cCharAt == 'F') {
                i = i2 + 1;
                objArr[i2] = Opcodes.FLOAT;
            } else if (cCharAt != 'L') {
                if (cCharAt != 'S' && cCharAt != 'I') {
                    if (cCharAt == 'J') {
                        i = i2 + 1;
                        objArr[i2] = Opcodes.LONG;
                    } else if (cCharAt != 'Z') {
                        if (cCharAt != '[') {
                            switch (cCharAt) {
                                case 'B':
                                case 'C':
                                    break;
                                case 'D':
                                    i = i2 + 1;
                                    objArr[i2] = Opcodes.DOUBLE;
                                    break;
                                default:
                                    context.q = i2;
                                    return;
                            }
                        } else {
                            while (str.charAt(i4) == '[') {
                                i4++;
                            }
                            if (str.charAt(i4) == 'L') {
                                do {
                                    i4++;
                                } while (str.charAt(i4) != ';');
                            }
                            int i5 = i4 + 1;
                            objArr[i2] = str.substring(i3, i5);
                            i3 = i5;
                            i2++;
                        }
                    }
                }
                i = i2 + 1;
                objArr[i2] = Opcodes.INTEGER;
            } else {
                int i6 = i4;
                while (str.charAt(i6) != ';') {
                    i6++;
                }
                objArr[i2] = str.substring(i4, i6);
                i2++;
                i3 = i6 + 1;
            }
            i2 = i;
            i3 = i4;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v24 */
    /* JADX WARN: Type inference failed for: r15v25 */
    /* JADX WARN: Type inference failed for: r15v26, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r15v55 */
    private void a(MethodVisitor methodVisitor, Context context, int i) {
        int i2;
        Label[] labelArr;
        Context context2;
        MethodVisitor methodVisitor2;
        char[] cArr;
        int[] iArr;
        String utf8;
        MethodVisitor methodVisitor3;
        int i3;
        int i4;
        boolean z;
        char c;
        boolean z2;
        boolean z3;
        boolean z4;
        ?? r15;
        int i5;
        boolean z5;
        int i6;
        int i7;
        Label[] labelArr2;
        int unsignedShort;
        int[] iArr2;
        int[] iArr3;
        int i8;
        String str;
        boolean z6;
        Label[] labelArr3;
        int i9;
        int i10;
        int[] iArr4;
        int[] iArr5;
        boolean z7;
        Attribute attribute;
        MethodVisitor methodVisitor4 = methodVisitor;
        Context context3 = context;
        byte[] bArr = this.b;
        char[] cArr2 = context3.c;
        int unsignedShort2 = readUnsignedShort(i);
        int unsignedShort3 = readUnsignedShort(i + 2);
        int i11 = readInt(i + 4);
        int i12 = i + 8;
        int i13 = i12 + i11;
        Label[] labelArr4 = new Label[i11 + 2];
        context3.h = labelArr4;
        readLabel(i11 + 1, labelArr4);
        int i14 = i12;
        while (i14 < i13) {
            int i15 = i14 - i12;
            switch (ClassWriter.a[(bArr[i14] & 255) == true ? 1 : 0]) {
                case 0:
                case 4:
                    i14++;
                    break;
                case 1:
                case 3:
                case 11:
                    i14 += 2;
                    break;
                case 2:
                case 5:
                case 6:
                case 12:
                case 13:
                    i14 += 3;
                    break;
                case 7:
                case 8:
                    i14 += 5;
                    break;
                case 9:
                    readLabel(i15 + readShort(i14 + 1), labelArr4);
                    i14 += 3;
                    break;
                case 10:
                    readLabel(i15 + readInt(i14 + 1), labelArr4);
                    i14 += 5;
                    break;
                case 14:
                    int i16 = (i14 + 4) - (i15 & 3);
                    readLabel(readInt(i16) + i15, labelArr4);
                    for (int i17 = (readInt(i16 + 8) - readInt(i16 + 4)) + 1; i17 > 0; i17--) {
                        readLabel(readInt(i16 + 12) + i15, labelArr4);
                        i16 += 4;
                    }
                    i14 = i16 + 12;
                    break;
                case 15:
                    int i18 = (i14 + 4) - (i15 & 3);
                    readLabel(readInt(i18) + i15, labelArr4);
                    for (int i19 = readInt(i18 + 4); i19 > 0; i19--) {
                        readLabel(readInt(i18 + 12) + i15, labelArr4);
                        i18 += 8;
                    }
                    i14 = i18 + 8;
                    break;
                case 16:
                default:
                    break;
                case 17:
                    i14 = (bArr[i14 + 1] & 255) == 132 ? i14 + 6 : i14 + 4;
                    break;
            }
        }
        int unsignedShort4 = readUnsignedShort(i14);
        while (unsignedShort4 > 0) {
            Label label = readLabel(readUnsignedShort(i14 + 2), labelArr4);
            Label label2 = readLabel(readUnsignedShort(i14 + 4), labelArr4);
            Label label3 = readLabel(readUnsignedShort(i14 + 6), labelArr4);
            i14 += 8;
            methodVisitor4.visitTryCatchBlock(label, label2, label3, readUTF8(this.a[readUnsignedShort(i14)], cArr2));
            unsignedShort4--;
            i13 = i13;
        }
        int i20 = i13;
        int i21 = i14 + 2;
        boolean z8 = (context3.b & 8) != 0;
        int i22 = i21;
        int unsignedShort5 = readUnsignedShort(i21);
        int[] iArrA = null;
        int[] iArr6 = null;
        boolean z9 = true;
        int i23 = 0;
        int i24 = 0;
        int i25 = 0;
        int i26 = 0;
        Attribute attribute2 = null;
        int unsignedShort6 = 0;
        int unsignedShort7 = -1;
        int unsignedShort8 = -1;
        while (unsignedShort5 > 0) {
            String utf9 = readUTF8(i22 + 2, cArr2);
            if ("LocalVariableTable".equals(utf9)) {
                if ((context3.b & 2) == 0) {
                    int i27 = i22 + 8;
                    int unsignedShort9 = readUnsignedShort(i27);
                    int i28 = i22;
                    while (unsignedShort9 > 0) {
                        int[] iArr7 = iArrA;
                        int i29 = i28 + 10;
                        int[] iArr8 = iArr6;
                        int unsignedShort10 = readUnsignedShort(i29);
                        if (labelArr4[unsignedShort10] == null) {
                            readLabel(unsignedShort10, labelArr4).a |= 1;
                        }
                        int unsignedShort11 = unsignedShort10 + readUnsignedShort(i28 + 12);
                        if (labelArr4[unsignedShort11] == null) {
                            readLabel(unsignedShort11, labelArr4).a |= 1;
                        }
                        unsignedShort9--;
                        i28 = i29;
                        iArrA = iArr7;
                        iArr6 = iArr8;
                        i27 = i27;
                    }
                    z8 = z8;
                    labelArr4 = labelArr4;
                    i11 = i11;
                    i20 = i20;
                    i26 = i27;
                } else {
                    iArr2 = iArrA;
                    iArr3 = iArr6;
                }
                i22 += readInt(i22 + 4) + 6;
                unsignedShort5--;
                z8 = z8;
                i11 = i11;
                labelArr4 = labelArr4;
                i20 = i20;
                methodVisitor4 = methodVisitor;
            } else {
                iArr2 = iArrA;
                iArr3 = iArr6;
                if ("LocalVariableTypeTable".equals(utf9)) {
                    i25 = i22 + 8;
                } else {
                    if ("LineNumberTable".equals(utf9)) {
                        if ((context3.b & 2) == 0) {
                            int i30 = i22;
                            for (int unsignedShort12 = readUnsignedShort(i22 + 8); unsignedShort12 > 0; unsignedShort12--) {
                                int unsignedShort13 = readUnsignedShort(i30 + 10);
                                if (labelArr4[unsignedShort13] == null) {
                                    readLabel(unsignedShort13, labelArr4).a |= 1;
                                }
                                Label label4 = labelArr4[unsignedShort13];
                                while (label4.b > 0) {
                                    if (label4.k == null) {
                                        label4.k = new Label();
                                    }
                                    label4 = label4.k;
                                }
                                label4.b = readUnsignedShort(i30 + 12);
                                i30 += 4;
                            }
                        }
                    } else if ("RuntimeVisibleTypeAnnotations".equals(utf9)) {
                        int[] iArrA2 = a(methodVisitor4, context3, i22 + 8, true);
                        unsignedShort7 = (iArrA2.length == 0 || readByte(iArrA2[0]) < 67) ? -1 : readUnsignedShort(iArrA2[0] + 1);
                        z8 = z8;
                        labelArr4 = labelArr4;
                        i11 = i11;
                        i20 = i20;
                        iArr6 = iArrA2;
                        iArrA = iArr2;
                    } else if ("RuntimeInvisibleTypeAnnotations".equals(utf9)) {
                        iArrA = a(methodVisitor4, context3, i22 + 8, false);
                        unsignedShort8 = (iArrA.length == 0 || readByte(iArrA[0]) < 67) ? -1 : readUnsignedShort(iArrA[0] + 1);
                        z8 = z8;
                        iArr6 = iArr3;
                    } else if ("StackMapTable".equals(utf9)) {
                        if ((context3.b & 4) == 0) {
                            i23 = i22 + 10;
                            i24 = readInt(i22 + 4);
                            unsignedShort6 = readUnsignedShort(i22 + 8);
                        }
                    } else if ("StackMap".equals(utf9)) {
                        if ((context3.b & 4) == 0) {
                            i23 = i22 + 10;
                            i24 = readInt(i22 + 4);
                            unsignedShort6 = readUnsignedShort(i22 + 8);
                            z9 = false;
                        }
                        iArrA = iArr2;
                        iArr6 = iArr3;
                    } else {
                        int i31 = 0;
                        Attribute attribute3 = attribute2;
                        while (i31 < context3.a.length) {
                            if (context3.a[i31].type.equals(utf9)) {
                                iArr4 = iArr2;
                                z7 = z9;
                                attribute = attribute3;
                                i8 = i31;
                                str = utf9;
                                z6 = z8;
                                iArr5 = iArr3;
                                i9 = i11;
                                labelArr3 = labelArr4;
                                i10 = i20;
                                attribute3 = context3.a[i31].read(this, i22 + 8, readInt(i22 + 4), cArr2, i, labelArr3);
                                if (attribute3 != null) {
                                    attribute3.a = attribute;
                                }
                                i31 = i8 + 1;
                                z8 = z6;
                                i11 = i9;
                                labelArr4 = labelArr3;
                                utf9 = str;
                                z9 = z7;
                                iArr2 = iArr4;
                                iArr3 = iArr5;
                                i20 = i10;
                            } else {
                                i8 = i31;
                                str = utf9;
                                z6 = z8;
                                labelArr3 = labelArr4;
                                i9 = i11;
                                i10 = i20;
                                iArr4 = iArr2;
                                iArr5 = iArr3;
                                z7 = z9;
                                attribute = attribute3;
                            }
                            attribute3 = attribute;
                            i31 = i8 + 1;
                            z8 = z6;
                            i11 = i9;
                            labelArr4 = labelArr3;
                            utf9 = str;
                            z9 = z7;
                            iArr2 = iArr4;
                            iArr3 = iArr5;
                            i20 = i10;
                        }
                        z8 = z8;
                        labelArr4 = labelArr4;
                        i11 = i11;
                        i20 = i20;
                        attribute2 = attribute3;
                        z9 = z9;
                        iArrA = iArr2;
                        iArr6 = iArr3;
                    }
                    i22 += readInt(i22 + 4) + 6;
                    unsignedShort5--;
                    z8 = z8;
                    i11 = i11;
                    labelArr4 = labelArr4;
                    i20 = i20;
                    methodVisitor4 = methodVisitor;
                }
                iArrA = iArr2;
                iArr6 = iArr3;
                i22 += readInt(i22 + 4) + 6;
                unsignedShort5--;
                z8 = z8;
                i11 = i11;
                labelArr4 = labelArr4;
                i20 = i20;
                methodVisitor4 = methodVisitor;
            }
            iArrA = iArr2;
            iArr6 = iArr3;
            i22 += readInt(i22 + 4) + 6;
            unsignedShort5--;
            z8 = z8;
            i11 = i11;
            labelArr4 = labelArr4;
            i20 = i20;
            methodVisitor4 = methodVisitor;
        }
        int[] iArr9 = iArrA;
        int[] iArr10 = iArr6;
        boolean z10 = z8;
        Label[] labelArr5 = labelArr4;
        boolean z11 = z9;
        int i32 = i11;
        int i33 = i20;
        if (i23 != 0) {
            context3.o = -1;
            context3.p = 0;
            context3.q = 0;
            context3.r = 0;
            context3.t = 0;
            context3.s = new Object[unsignedShort3];
            context3.u = new Object[unsignedShort2];
            if (z10) {
                a(context3);
            }
            int i34 = i23;
            while (i34 < (i23 + i24) - 2) {
                if (bArr[i34] != 8 || (unsignedShort = readUnsignedShort(i34 + 1)) < 0) {
                    i7 = i32;
                } else {
                    i7 = i32;
                    if (unsignedShort < i7 && (bArr[i12 + unsignedShort] & 255) == 187) {
                        labelArr2 = labelArr5;
                        readLabel(unsignedShort, labelArr2);
                    }
                    i34++;
                    labelArr5 = labelArr2;
                    i32 = i7;
                }
                labelArr2 = labelArr5;
                i34++;
                labelArr5 = labelArr2;
                i32 = i7;
            }
            i2 = i32;
            labelArr = labelArr5;
            context2 = context3;
        } else {
            i2 = i32;
            labelArr = labelArr5;
            context2 = null;
        }
        int i35 = i12;
        int i36 = 0;
        int i37 = 0;
        while (true) {
            int i38 = i33;
            if (i35 >= i38) {
                MethodVisitor methodVisitor5 = methodVisitor;
                Label[] labelArr6 = labelArr;
                int i39 = i2;
                int i40 = unsignedShort3;
                int[] iArr11 = iArr9;
                int[] iArr12 = iArr10;
                int i41 = 0;
                Label label5 = labelArr6[i39];
                if (label5 != null) {
                    methodVisitor5.visitLabel(label5);
                }
                if ((context3.b & 2) == 0 && i26 != 0) {
                    int i42 = i25;
                    if (i42 != 0) {
                        int i43 = i42 + 2;
                        int unsignedShort14 = readUnsignedShort(i42) * 3;
                        int[] iArr13 = new int[unsignedShort14];
                        int i44 = i43;
                        while (unsignedShort14 > 0) {
                            iArr13[unsignedShort14 - 1] = i44 + 6;
                            iArr13[unsignedShort14 - 2] = readUnsignedShort(i44 + 8);
                            unsignedShort14 -= 3;
                            iArr13[unsignedShort14] = readUnsignedShort(i44);
                            i44 += 10;
                        }
                        iArr = iArr13;
                    } else {
                        iArr = null;
                    }
                    int i45 = i26 + 2;
                    int unsignedShort15 = readUnsignedShort(i26);
                    while (unsignedShort15 > 0) {
                        int unsignedShort16 = readUnsignedShort(i45);
                        int unsignedShort17 = readUnsignedShort(i45 + 2);
                        int unsignedShort18 = readUnsignedShort(i45 + 8);
                        if (iArr == null) {
                            utf8 = null;
                            break;
                            break;
                        }
                        int i46 = 0;
                        while (true) {
                            if (i46 >= iArr.length) {
                                utf8 = null;
                                break;
                            } else {
                                if (iArr[i46] == unsignedShort16 && iArr[i46 + 1] == unsignedShort18) {
                                    utf8 = readUTF8(iArr[i46 + 2], cArr2);
                                    break;
                                }
                                i46 += 3;
                            }
                        }
                        methodVisitor.visitLocalVariable(readUTF8(i45 + 4, cArr2), readUTF8(i45 + 6, cArr2), utf8, labelArr6[unsignedShort16], labelArr6[unsignedShort16 + unsignedShort17], unsignedShort18);
                        i45 += 10;
                        unsignedShort15--;
                        iArr11 = iArr11;
                    }
                }
                int[] iArr14 = iArr11;
                int i47 = 32;
                if (iArr12 != null) {
                    int i48 = 0;
                    while (i48 < iArr12.length) {
                        if ((readByte(iArr12[i48]) >> 1) == i47) {
                            int iA = a(context3, iArr12[i48]);
                            int i49 = iA + 2;
                            int i50 = context3.i;
                            TypePath typePath = context3.j;
                            Label[] labelArr7 = context3.l;
                            Label[] labelArr8 = context3.m;
                            int[] iArr15 = context3.n;
                            String utf10 = readUTF8(iA, cArr2);
                            methodVisitor2 = methodVisitor;
                            cArr = cArr2;
                            a(i49, cArr, true, methodVisitor.visitLocalVariableAnnotation(i50, typePath, labelArr7, labelArr8, iArr15, utf10, true));
                        } else {
                            methodVisitor2 = methodVisitor5;
                            cArr = cArr2;
                        }
                        i48++;
                        cArr2 = cArr;
                        methodVisitor5 = methodVisitor2;
                        context3 = context3;
                        unsignedShort2 = unsignedShort2;
                        iArr12 = iArr12;
                        i41 = i41;
                        i47 = 32;
                    }
                }
                MethodVisitor methodVisitor6 = methodVisitor5;
                int i51 = i41;
                int i52 = unsignedShort2;
                char[] cArr3 = cArr2;
                Context context4 = context3;
                if (iArr14 != null) {
                    int i53 = i51;
                    while (i53 < iArr14.length) {
                        if ((readByte(iArr14[i53]) >> 1) == 32) {
                            int iA2 = a(context4, iArr14[i53]);
                            a(iA2 + 2, cArr3, true, methodVisitor.visitLocalVariableAnnotation(context4.i, context4.j, context4.l, context4.m, context4.n, readUTF8(iA2, cArr3), false));
                        }
                        i53++;
                        context4 = context;
                    }
                }
                Attribute attribute4 = attribute2;
                while (attribute4 != null) {
                    Attribute attribute5 = attribute4.a;
                    attribute4.a = null;
                    methodVisitor6.visitAttribute(attribute4);
                    attribute4 = attribute5;
                }
                methodVisitor6.visitMaxs(i52, i40);
                return;
            }
            int i54 = i35 - i12;
            Label label6 = labelArr[i54];
            if (label6 != null) {
                label6.k = null;
                methodVisitor3 = methodVisitor;
                methodVisitor3.visitLabel(label6);
                i3 = i38;
                if ((context3.b & 2) == 0 && label6.b > 0) {
                    methodVisitor3.visitLineNumber(label6.b, label6);
                    for (Label label7 = label6.k; label7 != null; label7 = label7.k) {
                        methodVisitor3.visitLineNumber(label7.b, label6);
                    }
                }
            } else {
                methodVisitor3 = methodVisitor;
                i3 = i38;
            }
            Context context5 = context2;
            int iA3 = i23;
            while (true) {
                if (context5 != null) {
                    if (context5.o != i54) {
                        i6 = -1;
                        i4 = context5.o != -1 ? -1 : -1;
                    } else {
                        i6 = -1;
                    }
                    if (context5.o != i6) {
                        if (!z11 || z10) {
                            methodVisitor.visitFrame(-1, context5.q, context5.s, context5.t, context5.u);
                        } else {
                            methodVisitor.visitFrame(context5.p, context5.r, context5.s, context5.t, context5.u);
                        }
                    }
                    if (unsignedShort6 > 0) {
                        iA3 = a(iA3, z11, z10, context5);
                        unsignedShort6--;
                    } else {
                        context5 = null;
                    }
                }
            }
            int i55 = i54;
            int i56 = bArr[i35] & 255;
            switch (ClassWriter.a[i56]) {
                case 0:
                    context5 = context5;
                    iA3 = iA3;
                    z10 = z10;
                    z = true;
                    c = '\b';
                    z11 = z11;
                    methodVisitor3.visitInsn(i56);
                    i35++;
                    z5 = z;
                    r15 = z5;
                    break;
                case 1:
                    z2 = true;
                    c = '\b';
                    methodVisitor3.visitIntInsn(i56, bArr[i35 + 1]);
                    i35 += 2;
                    z5 = z2;
                    r15 = z5;
                    break;
                case 2:
                    context5 = context5;
                    iA3 = iA3;
                    z10 = z10;
                    z3 = true;
                    c = '\b';
                    methodVisitor3.visitIntInsn(i56, readShort(i35 + 1));
                    i35 += 3;
                    z5 = z3;
                    r15 = z5;
                    break;
                case 3:
                    z2 = true;
                    c = '\b';
                    methodVisitor3.visitVarInsn(i56, bArr[i35 + 1] & 255);
                    i35 += 2;
                    z5 = z2;
                    r15 = z5;
                    break;
                case 4:
                    context5 = context5;
                    iA3 = iA3;
                    z10 = z10;
                    z = true;
                    z = true;
                    c = '\b';
                    z11 = z11;
                    if (i56 > 54) {
                        int i57 = i56 - 59;
                        methodVisitor3.visitVarInsn((i57 >> 2) + 54, i57 & 3);
                    } else {
                        int i58 = i56 - 26;
                        methodVisitor3.visitVarInsn((i58 >> 2) + 21, i58 & 3);
                    }
                    i35++;
                    z5 = z;
                    r15 = z5;
                    break;
                case 5:
                    context5 = context5;
                    iA3 = iA3;
                    z10 = z10;
                    z3 = true;
                    c = '\b';
                    methodVisitor3.visitTypeInsn(i56, readClass(i35 + 1, cArr2));
                    i35 += 3;
                    z5 = z3;
                    r15 = z5;
                    break;
                case 6:
                case 7:
                    context5 = context5;
                    c = '\b';
                    int i59 = this.a[readUnsignedShort(i35 + 1)];
                    boolean z12 = bArr[i59 + (-1)] == 11;
                    String str2 = readClass(i59, cArr2);
                    int i60 = this.a[readUnsignedShort(i59 + 2)];
                    String utf11 = readUTF8(i60, cArr2);
                    String utf12 = readUTF8(i60 + 2, cArr2);
                    if (i56 < 182) {
                        methodVisitor3.visitFieldInsn(i56, str2, utf11, utf12);
                        z4 = true;
                    } else {
                        z4 = true;
                        methodVisitor.visitMethodInsn(i56, str2, utf11, utf12, z12);
                    }
                    z3 = z4;
                    if (i56 == 185) {
                        i35 += 5;
                        z5 = z4;
                    } else {
                        i35 += 3;
                        z5 = z3;
                    }
                    r15 = z5;
                    break;
                case 8:
                    c = '\b';
                    int i61 = this.a[readUnsignedShort(i35 + 1)];
                    int i62 = context3.d[readUnsignedShort(i61)];
                    Handle handle = (Handle) readConst(readUnsignedShort(i62), cArr2);
                    int unsignedShort19 = readUnsignedShort(i62 + 2);
                    Object[] objArr = new Object[unsignedShort19];
                    int i63 = i62 + 4;
                    for (int i64 = 0; i64 < unsignedShort19; i64++) {
                        objArr[i64] = readConst(readUnsignedShort(i63), cArr2);
                        i63 += 2;
                    }
                    int i65 = this.a[readUnsignedShort(i61 + 2)];
                    methodVisitor3.visitInvokeDynamicInsn(readUTF8(i65, cArr2), readUTF8(i65 + 2, cArr2), handle, objArr);
                    i35 += 5;
                    iA3 = iA3;
                    z11 = z11;
                    z10 = z10;
                    r15 = 1;
                    break;
                case 9:
                    c = '\b';
                    methodVisitor3.visitJumpInsn(i56, labelArr[readShort(i35 + 1) + i55]);
                    i35 += 3;
                    iA3 = iA3;
                    z11 = z11;
                    z10 = z10;
                    r15 = 1;
                    break;
                case 10:
                    c = '\b';
                    methodVisitor3.visitJumpInsn(i56 - 33, labelArr[readInt(i35 + 1) + i55]);
                    i35 += 5;
                    iA3 = iA3;
                    z11 = z11;
                    z10 = z10;
                    r15 = 1;
                    break;
                case 11:
                    z11 = z11;
                    context5 = context5;
                    c = '\b';
                    methodVisitor3.visitLdcInsn(readConst(bArr[i35 + 1] & 255, cArr2));
                    i35 += 2;
                    iA3 = iA3;
                    z11 = z11;
                    z10 = z10;
                    r15 = 1;
                    break;
                case 12:
                    c = '\b';
                    methodVisitor3.visitLdcInsn(readConst(readUnsignedShort(i35 + 1), cArr2));
                    i35 += 3;
                    iA3 = iA3;
                    z11 = z11;
                    z10 = z10;
                    r15 = 1;
                    break;
                case 13:
                    c = '\b';
                    methodVisitor3.visitIincInsn(bArr[i35 + 1] & 255, bArr[i35 + 2]);
                    i35 += 3;
                    iA3 = iA3;
                    z11 = z11;
                    z10 = z10;
                    r15 = 1;
                    break;
                case 14:
                    z11 = z11;
                    c = '\b';
                    int i66 = (i35 + 4) - (i55 & 3);
                    int i67 = readInt(i66) + i55;
                    int i68 = readInt(i66 + 4);
                    int i69 = readInt(i66 + 8);
                    int i70 = (i69 - i68) + 1;
                    Label[] labelArr9 = new Label[i70];
                    i35 = i66 + 12;
                    context5 = context5;
                    for (int i71 = 0; i71 < i70; i71++) {
                        labelArr9[i71] = labelArr[i55 + readInt(i35)];
                        i35 += 4;
                    }
                    methodVisitor3.visitTableSwitchInsn(i68, i69, labelArr[i67], labelArr9);
                    iA3 = iA3;
                    z11 = z11;
                    z10 = z10;
                    r15 = 1;
                    break;
                case 15:
                    int i72 = (i35 + 4) - (i55 & 3);
                    int i73 = readInt(i72) + i55;
                    int i74 = readInt(i72 + 4);
                    int[] iArr16 = new int[i74];
                    Label[] labelArr10 = new Label[i74];
                    c = '\b';
                    int i75 = i72 + 8;
                    z11 = z11;
                    int i76 = 0;
                    while (i76 < i74) {
                        iArr16[i76] = readInt(i75);
                        labelArr10[i76] = labelArr[readInt(i75 + 4) + i55];
                        i75 += 8;
                        i76++;
                        i74 = i74;
                    }
                    methodVisitor3.visitLookupSwitchInsn(labelArr[i73], iArr16, labelArr10);
                    i35 = i75;
                    context5 = context5;
                    iA3 = iA3;
                    z11 = z11;
                    z10 = z10;
                    r15 = 1;
                    break;
                case 16:
                default:
                    context5 = context5;
                    iA3 = iA3;
                    z10 = z10;
                    z5 = true;
                    c = '\b';
                    z11 = z11;
                    methodVisitor3.visitMultiANewArrayInsn(readClass(i35 + 1, cArr2), bArr[i35 + 3] & 255);
                    i35 += 4;
                    r15 = z5;
                    break;
                case 17:
                    int i77 = bArr[i35 + 1] & 255;
                    if (i77 == 132) {
                        methodVisitor3.visitIincInsn(readUnsignedShort(i35 + 2), readShort(i35 + 4));
                        i5 = i35 + 6;
                    } else {
                        methodVisitor3.visitVarInsn(i77, readUnsignedShort(i35 + 2));
                        i5 = i35 + 4;
                    }
                    context5 = context5;
                    iA3 = iA3;
                    z10 = z10;
                    r15 = 1;
                    c = '\b';
                    i35 = i5;
                    z11 = z11;
                    break;
            }
            while (iArr10 != 0 && i36 < iArr10.length && unsignedShort7 <= i55) {
                if (unsignedShort7 == i55) {
                    int iA4 = a(context3, iArr10[i36]);
                    a(iA4 + 2, cArr2, (boolean) r15, methodVisitor3.visitInsnAnnotation(context3.i, context3.j, readUTF8(iA4, cArr2), r15));
                }
                i36++;
                unsignedShort7 = (i36 >= iArr10.length || readByte(iArr10[i36]) < 67) ? i4 : readUnsignedShort(iArr10[i36] + r15);
            }
            int i78 = i37;
            int unsignedShort20 = unsignedShort8;
            int[] iArr17 = iArr9;
            while (iArr17 != null && i78 < iArr17.length && unsignedShort20 <= i55) {
                if (unsignedShort20 == i55) {
                    int iA5 = a(context3, iArr17[i78]);
                    a(iA5 + 2, cArr2, true, methodVisitor3.visitInsnAnnotation(context3.i, context3.j, readUTF8(iA5, cArr2), false));
                }
                i78++;
                unsignedShort20 = (i78 >= iArr17.length || readByte(iArr17[i78]) < 67) ? i4 : readUnsignedShort(iArr17[i78] + 1);
                i36 = i36;
                unsignedShort7 = unsignedShort7;
                i55 = i55;
            }
            i36 = i36;
            i37 = i78;
            unsignedShort8 = unsignedShort20;
            iArr9 = iArr17;
            iArr10 = iArr10;
            unsignedShort7 = unsignedShort7;
            labelArr = labelArr;
            i2 = i2;
            unsignedShort3 = unsignedShort3;
            i33 = i3;
            z10 = z10;
            i23 = iA3;
            context2 = context5;
            z11 = z11;
        }
    }

    private static byte[] a(InputStream inputStream, boolean z) throws IOException {
        if (inputStream == null) {
            throw new IOException("Class not found");
        }
        try {
            byte[] bArr = new byte[inputStream.available()];
            int i = 0;
            while (true) {
                int i2 = inputStream.read(bArr, i, bArr.length - i);
                if (i2 == -1) {
                    if (i < bArr.length) {
                        byte[] bArr2 = new byte[i];
                        System.arraycopy(bArr, 0, bArr2, 0, i);
                        bArr = bArr2;
                    }
                    return bArr;
                }
                i += i2;
                if (i == bArr.length) {
                    int i3 = inputStream.read();
                    if (i3 < 0) {
                        return bArr;
                    }
                    byte[] bArr3 = new byte[bArr.length + 1000];
                    System.arraycopy(bArr, 0, bArr3, 0, i);
                    int i4 = i + 1;
                    bArr3[i] = (byte) i3;
                    i = i4;
                    bArr = bArr3;
                }
            }
        } finally {
            if (z) {
                inputStream.close();
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0055  */
    private int[] a(MethodVisitor methodVisitor, Context context, int i, boolean z) {
        int i2;
        char[] cArr = context.c;
        int unsignedShort = readUnsignedShort(i);
        int[] iArr = new int[unsignedShort];
        int iA = i + 2;
        for (int i3 = 0; i3 < unsignedShort; i3++) {
            iArr[i3] = iA;
            int i4 = readInt(iA);
            int i5 = i4 >>> 24;
            if (i5 == 0 || i5 == 1) {
                i2 = iA + 2;
            } else {
                if (i5 != 64 && i5 != 65) {
                    switch (i5) {
                        case 19:
                        case 20:
                        case 21:
                            i2 = iA + 1;
                            break;
                        case 22:
                            i2 = iA + 2;
                            break;
                        default:
                            switch (i5) {
                                case 71:
                                case 72:
                                case 73:
                                case 74:
                                case 75:
                                    i2 = iA + 4;
                                    break;
                            }
                            break;
                    }
                } else {
                    for (int unsignedShort2 = readUnsignedShort(iA + 1); unsignedShort2 > 0; unsignedShort2--) {
                        int unsignedShort3 = readUnsignedShort(iA + 3);
                        int unsignedShort4 = readUnsignedShort(iA + 5);
                        readLabel(unsignedShort3, context.h);
                        readLabel(unsignedShort3 + unsignedShort4, context.h);
                        iA += 6;
                    }
                }
                i2 = iA + 3;
            }
            int i6 = readByte(i2);
            if (i5 == 66) {
                TypePath typePath = i6 != 0 ? new TypePath(this.b, i2) : null;
                int i7 = i2 + (i6 * 2) + 1;
                iA = a(i7 + 2, cArr, true, methodVisitor.visitTryCatchAnnotation(i4, typePath, readUTF8(i7, cArr), z));
            } else {
                iA = a(i2 + 3 + (i6 * 2), cArr, true, (AnnotationVisitor) null);
            }
        }
        return iArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:70:0x01b1, code lost:
    
        if (r1.j == 0) goto L80;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int b(com.esotericsoftware.asm.ClassVisitor r32, com.esotericsoftware.asm.Context r33, int r34) {
        /*
            Method dump skipped, instruction units count: 700
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.asm.ClassReader.b(com.esotericsoftware.asm.ClassVisitor, com.esotericsoftware.asm.Context, int):int");
    }

    private void b(MethodVisitor methodVisitor, Context context, int i, boolean z) {
        int iA = i + 1;
        int i2 = this.b[i] & 255;
        int length = Type.getArgumentTypes(context.g).length - i2;
        int i3 = 0;
        while (i3 < length) {
            AnnotationVisitor annotationVisitorVisitParameterAnnotation = methodVisitor.visitParameterAnnotation(i3, "Ljava/lang/Synthetic;", false);
            if (annotationVisitorVisitParameterAnnotation != null) {
                annotationVisitorVisitParameterAnnotation.visitEnd();
            }
            i3++;
        }
        char[] cArr = context.c;
        while (i3 < i2 + length) {
            iA += 2;
            for (int unsignedShort = readUnsignedShort(iA); unsignedShort > 0; unsignedShort--) {
                iA = a(iA + 2, cArr, true, methodVisitor.visitParameterAnnotation(i3, readUTF8(iA, cArr), z));
            }
            i3++;
        }
    }

    void a(ClassWriter classWriter) {
        char[] cArr = new char[this.d];
        int length = this.a.length;
        Item[] itemArr = new Item[length];
        int i = 1;
        while (i < length) {
            int i2 = this.a[i];
            byte b = this.b[i2 - 1];
            Item item = new Item(i);
            if (b == 1) {
                String[] strArr = this.c;
                String strA = strArr[i];
                if (strA == null) {
                    int i3 = this.a[i];
                    strA = a(i3 + 2, readUnsignedShort(i3), cArr);
                    strArr[i] = strA;
                }
                item.a(b, strA, null, null);
            } else if (b == 15) {
                int i4 = this.a[readUnsignedShort(i2 + 1)];
                int i5 = this.a[readUnsignedShort(i4 + 2)];
                item.a(readByte(i2) + 20, readClass(i4, cArr), readUTF8(i5, cArr), readUTF8(i5 + 2, cArr));
            } else if (b == 18) {
                if (classWriter.A == null) {
                    a(classWriter, itemArr, cArr);
                }
                int i6 = this.a[readUnsignedShort(i2 + 2)];
                item.a(readUTF8(i6, cArr), readUTF8(i6 + 2, cArr), readUnsignedShort(i2));
            } else if (b == 3) {
                item.a(readInt(i2));
            } else if (b != 4) {
                if (b == 5) {
                    item.a(readLong(i2));
                } else if (b != 6) {
                    switch (b) {
                        case 9:
                        case 10:
                        case 11:
                            int i7 = this.a[readUnsignedShort(i2 + 2)];
                            item.a(b, readClass(i2, cArr), readUTF8(i7, cArr), readUTF8(i7 + 2, cArr));
                            break;
                        case 12:
                            item.a(b, readUTF8(i2, cArr), readUTF8(i2 + 2, cArr), null);
                            break;
                        default:
                            item.a(b, readUTF8(i2, cArr), null, null);
                            break;
                    }
                } else {
                    item.a(Double.longBitsToDouble(readLong(i2)));
                }
                i++;
            } else {
                item.a(Float.intBitsToFloat(readInt(i2)));
            }
            int i8 = item.j % length;
            item.k = itemArr[i8];
            itemArr[i8] = item;
            i++;
        }
        int i9 = this.a[1] - 1;
        classWriter.d.putByteArray(this.b, i9, this.header - i9);
        classWriter.e = itemArr;
        classWriter.f = (int) (((double) length) * 0.75d);
        classWriter.c = length;
    }

    public void accept(ClassVisitor classVisitor, int i) {
        accept(classVisitor, new Attribute[0], i);
    }

    public void accept(ClassVisitor classVisitor, Attribute[] attributeArr, int i) {
        int i2;
        String str;
        String str2;
        String[] strArr;
        String str3;
        Attribute attribute;
        int i3;
        int i4 = this.header;
        char[] cArr = new char[this.d];
        Context context = new Context();
        context.a = attributeArr;
        context.b = i;
        context.c = cArr;
        int unsignedShort = readUnsignedShort(i4);
        String str4 = readClass(i4 + 2, cArr);
        String str5 = readClass(i4 + 4, cArr);
        int unsignedShort2 = readUnsignedShort(i4 + 6);
        String[] strArr2 = new String[unsignedShort2];
        int i5 = i4 + 8;
        for (int i6 = 0; i6 < unsignedShort2; i6++) {
            strArr2[i6] = readClass(i5, cArr);
            i5 += 2;
        }
        int iA = a();
        int i7 = iA;
        int i8 = unsignedShort;
        int unsignedShort3 = readUnsignedShort(iA);
        int i9 = 0;
        String utf8 = null;
        String strA = null;
        String utf9 = null;
        String utf10 = null;
        String str6 = null;
        String utf11 = null;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        Attribute attribute2 = null;
        while (unsignedShort3 > 0) {
            String utf12 = readUTF8(i7 + 2, cArr);
            if ("SourceFile".equals(utf12)) {
                utf9 = readUTF8(i7 + 8, cArr);
            } else if ("InnerClasses".equals(utf12)) {
                i13 = i7 + 8;
            } else if ("EnclosingMethod".equals(utf12)) {
                String str7 = readClass(i7 + 8, cArr);
                int unsignedShort4 = readUnsignedShort(i7 + 10);
                if (unsignedShort4 != 0) {
                    utf11 = readUTF8(this.a[unsignedShort4], cArr);
                    utf8 = readUTF8(this.a[unsignedShort4] + 2, cArr);
                }
                str6 = str7;
            } else if ("Signature".equals(utf12)) {
                utf10 = readUTF8(i7 + 8, cArr);
            } else if ("RuntimeVisibleAnnotations".equals(utf12)) {
                i9 = i7 + 8;
            } else if ("RuntimeVisibleTypeAnnotations".equals(utf12)) {
                i11 = i7 + 8;
            } else {
                if ("Deprecated".equals(utf12)) {
                    i3 = 131072;
                } else {
                    if (AttSynthetic.ATTRIBUTE_NAME.equals(utf12)) {
                        i3 = 266240;
                    } else if (AttSourceDebugExtension.ATTRIBUTE_NAME.equals(utf12)) {
                        int i14 = readInt(i7 + 4);
                        strA = a(i7 + 8, i14, new char[i14]);
                    } else if ("RuntimeInvisibleAnnotations".equals(utf12)) {
                        i10 = i7 + 8;
                    } else if ("RuntimeInvisibleTypeAnnotations".equals(utf12)) {
                        i12 = i7 + 8;
                    } else {
                        if (AttBootstrapMethods.ATTRIBUTE_NAME.equals(utf12)) {
                            int unsignedShort5 = readUnsignedShort(i7 + 8);
                            int[] iArr = new int[unsignedShort5];
                            int unsignedShort6 = i7 + 10;
                            int i15 = 0;
                            while (i15 < unsignedShort5) {
                                iArr[i15] = unsignedShort6;
                                unsignedShort6 += (readUnsignedShort(unsignedShort6 + 2) + 2) << 1;
                                i15++;
                                i9 = i9;
                            }
                            context.d = iArr;
                            str = utf8;
                            str2 = strA;
                            str3 = utf9;
                            strArr = strArr2;
                            attribute = attribute2;
                            i2 = i9;
                        } else {
                            i2 = i9;
                            str = utf8;
                            str2 = strA;
                            strArr = strArr2;
                            str3 = utf9;
                            Attribute attributeA = a(attributeArr, utf12, i7 + 8, readInt(i7 + 4), cArr, -1, null);
                            attribute = attribute2;
                            if (attributeA != null) {
                                attributeA.a = attribute;
                                attribute2 = attributeA;
                            }
                            utf9 = str3;
                            i9 = i2;
                            utf8 = str;
                            strA = str2;
                        }
                        attribute2 = attribute;
                        utf9 = str3;
                        i9 = i2;
                        utf8 = str;
                        strA = str2;
                    }
                    i7 += readInt(i7 + 4) + 6;
                    unsignedShort3--;
                    strArr2 = strArr;
                }
                i8 |= i3;
            }
            strArr = strArr2;
            i7 += readInt(i7 + 4) + 6;
            unsignedShort3--;
            strArr2 = strArr;
        }
        int i16 = i9;
        String str8 = utf8;
        String str9 = strA;
        String str10 = utf9;
        Attribute attribute3 = attribute2;
        classVisitor.visit(readInt(this.a[1] - 7), i8, str4, utf10, str5, strArr2);
        if ((i & 2) == 0 && (str10 != null || str9 != null)) {
            classVisitor.visitSource(str10, str9);
        }
        String str11 = str6;
        if (str11 != null) {
            classVisitor.visitOuterClass(str11, utf11, str8);
        }
        if (i16 != 0) {
            int iA2 = i16 + 2;
            for (int unsignedShort7 = readUnsignedShort(i16); unsignedShort7 > 0; unsignedShort7--) {
                iA2 = a(iA2 + 2, cArr, true, classVisitor.visitAnnotation(readUTF8(iA2, cArr), true));
            }
        }
        int i17 = i10;
        if (i17 != 0) {
            int iA3 = i17 + 2;
            for (int unsignedShort8 = readUnsignedShort(i17); unsignedShort8 > 0; unsignedShort8--) {
                iA3 = a(iA3 + 2, cArr, true, classVisitor.visitAnnotation(readUTF8(iA3, cArr), false));
            }
        }
        int i18 = i11;
        if (i18 != 0) {
            int iA4 = i18 + 2;
            for (int unsignedShort9 = readUnsignedShort(i18); unsignedShort9 > 0; unsignedShort9--) {
                int iA5 = a(context, iA4);
                iA4 = a(iA5 + 2, cArr, true, classVisitor.visitTypeAnnotation(context.i, context.j, readUTF8(iA5, cArr), true));
            }
        }
        int i19 = i12;
        if (i19 != 0) {
            int iA6 = i19 + 2;
            for (int unsignedShort10 = readUnsignedShort(i19); unsignedShort10 > 0; unsignedShort10--) {
                int iA7 = a(context, iA6);
                iA6 = a(iA7 + 2, cArr, true, classVisitor.visitTypeAnnotation(context.i, context.j, readUTF8(iA7, cArr), false));
            }
        }
        while (attribute3 != null) {
            Attribute attribute4 = attribute3.a;
            attribute3.a = null;
            classVisitor.visitAttribute(attribute3);
            attribute3 = attribute4;
        }
        int i20 = i13;
        if (i20 != 0) {
            int i21 = i20 + 2;
            for (int unsignedShort11 = readUnsignedShort(i20); unsignedShort11 > 0; unsignedShort11--) {
                classVisitor.visitInnerClass(readClass(i21, cArr), readClass(i21 + 2, cArr), readUTF8(i21 + 4, cArr), readUnsignedShort(i21 + 6));
                i21 += 8;
            }
        }
        int iA8 = this.header + 10 + (unsignedShort2 * 2);
        for (int unsignedShort12 = readUnsignedShort(iA8 - 2); unsignedShort12 > 0; unsignedShort12--) {
            iA8 = a(classVisitor, context, iA8);
        }
        int iB = iA8 + 2;
        for (int unsignedShort13 = readUnsignedShort(iA8); unsignedShort13 > 0; unsignedShort13--) {
            iB = b(classVisitor, context, iB);
        }
        classVisitor.visitEnd();
    }

    public int getAccess() {
        return readUnsignedShort(this.header);
    }

    public String getClassName() {
        return readClass(this.header + 2, new char[this.d]);
    }

    public String[] getInterfaces() {
        int i = this.header + 6;
        int unsignedShort = readUnsignedShort(i);
        String[] strArr = new String[unsignedShort];
        if (unsignedShort > 0) {
            char[] cArr = new char[this.d];
            for (int i2 = 0; i2 < unsignedShort; i2++) {
                i += 2;
                strArr[i2] = readClass(i, cArr);
            }
        }
        return strArr;
    }

    public int getItem(int i) {
        return this.a[i];
    }

    public int getItemCount() {
        return this.a.length;
    }

    public int getMaxStringLength() {
        return this.d;
    }

    public String getSuperName() {
        return readClass(this.header + 4, new char[this.d]);
    }

    public int readByte(int i) {
        return this.b[i] & 255;
    }

    public String readClass(int i, char[] cArr) {
        return readUTF8(this.a[readUnsignedShort(i)], cArr);
    }

    public Object readConst(int i, char[] cArr) {
        int i2 = this.a[i];
        byte b = this.b[i2 - 1];
        if (b == 16) {
            return Type.getMethodType(readUTF8(i2, cArr));
        }
        switch (b) {
            case 3:
                return new Integer(readInt(i2));
            case 4:
                return new Float(Float.intBitsToFloat(readInt(i2)));
            case 5:
                return new Long(readLong(i2));
            case 6:
                return new Double(Double.longBitsToDouble(readLong(i2)));
            case 7:
                return Type.getObjectType(readUTF8(i2, cArr));
            case 8:
                return readUTF8(i2, cArr);
            default:
                int i3 = readByte(i2);
                int[] iArr = this.a;
                int i4 = iArr[readUnsignedShort(i2 + 1)];
                boolean z = this.b[i4 + (-1)] == 11;
                String str = readClass(i4, cArr);
                int i5 = iArr[readUnsignedShort(i4 + 2)];
                return new Handle(i3, str, readUTF8(i5, cArr), readUTF8(i5 + 2, cArr), z);
        }
    }

    public int readInt(int i) {
        byte[] bArr = this.b;
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }

    protected Label readLabel(int i, Label[] labelArr) {
        if (labelArr[i] == null) {
            labelArr[i] = new Label();
        }
        return labelArr[i];
    }

    public long readLong(int i) {
        return (((long) readInt(i)) << 32) | (((long) readInt(i + 4)) & 4294967295L);
    }

    public short readShort(int i) {
        byte[] bArr = this.b;
        return (short) ((bArr[i + 1] & 255) | ((bArr[i] & 255) << 8));
    }

    public String readUTF8(int i, char[] cArr) {
        int unsignedShort = readUnsignedShort(i);
        if (i == 0 || unsignedShort == 0) {
            return null;
        }
        String[] strArr = this.c;
        String str = strArr[unsignedShort];
        if (str != null) {
            return str;
        }
        int i2 = this.a[unsignedShort];
        String strA = a(i2 + 2, readUnsignedShort(i2), cArr);
        strArr[unsignedShort] = strA;
        return strA;
    }

    public int readUnsignedShort(int i) {
        byte[] bArr = this.b;
        return (bArr[i + 1] & 255) | ((bArr[i] & 255) << 8);
    }
}
