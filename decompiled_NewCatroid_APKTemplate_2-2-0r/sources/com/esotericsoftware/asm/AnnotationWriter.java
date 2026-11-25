package com.esotericsoftware.asm;

/* JADX INFO: loaded from: classes8.dex */
final class AnnotationWriter extends AnnotationVisitor {
    private final ClassWriter a;
    private int b;
    private final boolean c;
    private final ByteVector d;
    private final ByteVector e;
    private final int f;
    AnnotationWriter g;
    AnnotationWriter h;

    AnnotationWriter(ClassWriter classWriter, boolean z, ByteVector byteVector, ByteVector byteVector2, int i) {
        super(327680);
        this.a = classWriter;
        this.c = z;
        this.d = byteVector;
        this.e = byteVector2;
        this.f = i;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x001f  */
    static void a(int i, TypePath typePath, ByteVector byteVector) {
        int i2 = i >>> 24;
        if (i2 != 0 && i2 != 1) {
            switch (i2) {
                case 19:
                case 20:
                case 21:
                    byteVector.putByte(i2);
                    break;
                case 22:
                    byteVector.putShort(i >>> 16);
                    break;
                default:
                    switch (i2) {
                        case 71:
                        case 72:
                        case 73:
                        case 74:
                        case 75:
                            byteVector.putInt(i);
                            break;
                        default:
                            byteVector.b(i2, (i & 16776960) >> 8);
                            break;
                    }
                    break;
            }
        } else {
            byteVector.putShort(i >>> 16);
        }
        if (typePath == null) {
            byteVector.putByte(0);
        } else {
            byteVector.putByteArray(typePath.a, typePath.b, (typePath.a[typePath.b] * 2) + 1);
        }
    }

    static void a(AnnotationWriter[] annotationWriterArr, int i, ByteVector byteVector) {
        int length = ((annotationWriterArr.length - i) * 2) + 1;
        int i2 = i;
        while (true) {
            int iA = 0;
            if (i2 >= annotationWriterArr.length) {
                break;
            }
            AnnotationWriter annotationWriter = annotationWriterArr[i2];
            if (annotationWriter != null) {
                iA = annotationWriter.a();
            }
            length += iA;
            i2++;
        }
        byteVector.putInt(length).putByte(annotationWriterArr.length - i);
        while (i < annotationWriterArr.length) {
            AnnotationWriter annotationWriter2 = null;
            int i3 = 0;
            for (AnnotationWriter annotationWriter3 = annotationWriterArr[i]; annotationWriter3 != null; annotationWriter3 = annotationWriter3.g) {
                i3++;
                annotationWriter3.visitEnd();
                annotationWriter3.h = annotationWriter2;
                annotationWriter2 = annotationWriter3;
            }
            byteVector.putShort(i3);
            while (annotationWriter2 != null) {
                byteVector.putByteArray(annotationWriter2.d.a, 0, annotationWriter2.d.b);
                annotationWriter2 = annotationWriter2.h;
            }
            i++;
        }
    }

    int a() {
        int i = 0;
        for (AnnotationWriter annotationWriter = this; annotationWriter != null; annotationWriter = annotationWriter.g) {
            i += annotationWriter.d.b;
        }
        return i;
    }

    void a(ByteVector byteVector) {
        int i = 2;
        int i2 = 0;
        AnnotationWriter annotationWriter = null;
        for (AnnotationWriter annotationWriter2 = this; annotationWriter2 != null; annotationWriter2 = annotationWriter2.g) {
            i2++;
            i += annotationWriter2.d.b;
            annotationWriter2.visitEnd();
            annotationWriter2.h = annotationWriter;
            annotationWriter = annotationWriter2;
        }
        byteVector.putInt(i);
        byteVector.putShort(i2);
        while (annotationWriter != null) {
            byteVector.putByteArray(annotationWriter.d.a, 0, annotationWriter.d.b);
            annotationWriter = annotationWriter.h;
        }
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
    @Override // com.esotericsoftware.asm.AnnotationVisitor
    public void visit(String str, Object obj) {
        int i;
        ByteVector byteVector;
        int iNewUTF8;
        this.b++;
        if (this.c) {
            this.d.putShort(this.a.newUTF8(str));
        }
        if (obj instanceof String) {
            byteVector = this.d;
            iNewUTF8 = this.a.newUTF8((String) obj);
            i = 115;
        } else {
            i = 66;
            if (obj instanceof Byte) {
                byteVector = this.d;
                iNewUTF8 = this.a.a((int) ((Byte) obj).byteValue()).a;
            } else {
                if (obj instanceof Boolean) {
                    this.d.b(90, this.a.a(((Boolean) obj).booleanValue() ? 1 : 0).a);
                    return;
                }
                if (obj instanceof Character) {
                    this.d.b(67, this.a.a((int) ((Character) obj).charValue()).a);
                    return;
                }
                if (obj instanceof Short) {
                    this.d.b(83, this.a.a((int) ((Short) obj).shortValue()).a);
                    return;
                }
                if (!(obj instanceof Type)) {
                    int i2 = 0;
                    if (obj instanceof byte[]) {
                        byte[] bArr = (byte[]) obj;
                        this.d.b(91, bArr.length);
                        while (i2 < bArr.length) {
                            this.d.b(66, this.a.a((int) bArr[i2]).a);
                            i2++;
                        }
                        return;
                    }
                    if (obj instanceof boolean[]) {
                        boolean[] zArr = (boolean[]) obj;
                        this.d.b(91, zArr.length);
                        while (i2 < zArr.length) {
                            this.d.b(90, this.a.a(zArr[i2] ? 1 : 0).a);
                            i2++;
                        }
                        return;
                    }
                    if (obj instanceof short[]) {
                        short[] sArr = (short[]) obj;
                        this.d.b(91, sArr.length);
                        while (i2 < sArr.length) {
                            this.d.b(83, this.a.a((int) sArr[i2]).a);
                            i2++;
                        }
                        return;
                    }
                    if (obj instanceof char[]) {
                        char[] cArr = (char[]) obj;
                        this.d.b(91, cArr.length);
                        while (i2 < cArr.length) {
                            this.d.b(67, this.a.a((int) cArr[i2]).a);
                            i2++;
                        }
                        return;
                    }
                    if (obj instanceof int[]) {
                        int[] iArr = (int[]) obj;
                        this.d.b(91, iArr.length);
                        while (i2 < iArr.length) {
                            this.d.b(73, this.a.a(iArr[i2]).a);
                            i2++;
                        }
                        return;
                    }
                    if (obj instanceof long[]) {
                        long[] jArr = (long[]) obj;
                        this.d.b(91, jArr.length);
                        while (i2 < jArr.length) {
                            this.d.b(74, this.a.a(jArr[i2]).a);
                            i2++;
                        }
                        return;
                    }
                    if (obj instanceof float[]) {
                        float[] fArr = (float[]) obj;
                        this.d.b(91, fArr.length);
                        while (i2 < fArr.length) {
                            this.d.b(70, this.a.a(fArr[i2]).a);
                            i2++;
                        }
                        return;
                    }
                    if (!(obj instanceof double[])) {
                        Item itemA = this.a.a(obj);
                        this.d.b(".s.IFJDCS".charAt(itemA.b), itemA.a);
                        return;
                    }
                    double[] dArr = (double[]) obj;
                    this.d.b(91, dArr.length);
                    while (i2 < dArr.length) {
                        this.d.b(68, this.a.a(dArr[i2]).a);
                        i2++;
                    }
                    return;
                }
                byteVector = this.d;
                iNewUTF8 = this.a.newUTF8(((Type) obj).getDescriptor());
                i = 99;
            }
        }
        byteVector.b(i, iNewUTF8);
    }

    @Override // com.esotericsoftware.asm.AnnotationVisitor
    public AnnotationVisitor visitAnnotation(String str, String str2) {
        this.b++;
        if (this.c) {
            this.d.putShort(this.a.newUTF8(str));
        }
        this.d.b(64, this.a.newUTF8(str2)).putShort(0);
        ClassWriter classWriter = this.a;
        ByteVector byteVector = this.d;
        return new AnnotationWriter(classWriter, true, byteVector, byteVector, byteVector.b - 2);
    }

    @Override // com.esotericsoftware.asm.AnnotationVisitor
    public AnnotationVisitor visitArray(String str) {
        this.b++;
        if (this.c) {
            this.d.putShort(this.a.newUTF8(str));
        }
        this.d.b(91, 0);
        ClassWriter classWriter = this.a;
        ByteVector byteVector = this.d;
        return new AnnotationWriter(classWriter, false, byteVector, byteVector, byteVector.b - 2);
    }

    @Override // com.esotericsoftware.asm.AnnotationVisitor
    public void visitEnd() {
        ByteVector byteVector = this.e;
        if (byteVector != null) {
            byte[] bArr = byteVector.a;
            int i = this.f;
            int i2 = this.b;
            bArr[i] = (byte) (i2 >>> 8);
            bArr[i + 1] = (byte) i2;
        }
    }

    @Override // com.esotericsoftware.asm.AnnotationVisitor
    public void visitEnum(String str, String str2, String str3) {
        this.b++;
        if (this.c) {
            this.d.putShort(this.a.newUTF8(str));
        }
        this.d.b(101, this.a.newUTF8(str2)).putShort(this.a.newUTF8(str3));
    }
}
