package com.esotericsoftware.asm;

import com.android.dx.cf.attrib.AttSynthetic;

/* JADX INFO: loaded from: classes8.dex */
final class FieldWriter extends FieldVisitor {
    private final ClassWriter b;
    private final int c;
    private final int d;
    private final int e;
    private int f;
    private int g;
    private AnnotationWriter h;
    private AnnotationWriter i;
    private Attribute j;
    private AnnotationWriter k;
    private AnnotationWriter l;

    FieldWriter(ClassWriter classWriter, int i, String str, String str2, String str3, Object obj) {
        super(327680);
        if (classWriter.B == null) {
            classWriter.B = this;
        } else {
            classWriter.C.fv = this;
        }
        classWriter.C = this;
        this.b = classWriter;
        this.c = i;
        this.d = classWriter.newUTF8(str);
        this.e = classWriter.newUTF8(str2);
        if (str3 != null) {
            this.f = classWriter.newUTF8(str3);
        }
        if (obj != null) {
            this.g = classWriter.a(obj).a;
        }
    }

    int a() {
        int iA;
        if (this.g != 0) {
            this.b.newUTF8("ConstantValue");
            iA = 16;
        } else {
            iA = 8;
        }
        if ((this.c & 4096) != 0 && ((this.b.b & 65535) < 49 || (this.c & 262144) != 0)) {
            this.b.newUTF8(AttSynthetic.ATTRIBUTE_NAME);
            iA += 6;
        }
        if ((this.c & 131072) != 0) {
            this.b.newUTF8("Deprecated");
            iA += 6;
        }
        if (this.f != 0) {
            this.b.newUTF8("Signature");
            iA += 8;
        }
        if (this.h != null) {
            this.b.newUTF8("RuntimeVisibleAnnotations");
            iA += this.h.a() + 8;
        }
        if (this.i != null) {
            this.b.newUTF8("RuntimeInvisibleAnnotations");
            iA += this.i.a() + 8;
        }
        if (this.k != null) {
            this.b.newUTF8("RuntimeVisibleTypeAnnotations");
            iA += this.k.a() + 8;
        }
        if (this.l != null) {
            this.b.newUTF8("RuntimeInvisibleTypeAnnotations");
            iA += this.l.a() + 8;
        }
        Attribute attribute = this.j;
        return attribute != null ? iA + attribute.a(this.b, null, 0, -1, -1) : iA;
    }

    void a(ByteVector byteVector) {
        int i = this.c;
        byteVector.putShort(i & (~(((i & 262144) / 64) | 393216))).putShort(this.d).putShort(this.e);
        int iA = this.g != 0 ? 1 : 0;
        if ((this.c & 4096) != 0 && ((this.b.b & 65535) < 49 || (this.c & 262144) != 0)) {
            iA++;
        }
        if ((this.c & 131072) != 0) {
            iA++;
        }
        if (this.f != 0) {
            iA++;
        }
        if (this.h != null) {
            iA++;
        }
        if (this.i != null) {
            iA++;
        }
        if (this.k != null) {
            iA++;
        }
        if (this.l != null) {
            iA++;
        }
        Attribute attribute = this.j;
        if (attribute != null) {
            iA += attribute.a();
        }
        byteVector.putShort(iA);
        if (this.g != 0) {
            byteVector.putShort(this.b.newUTF8("ConstantValue"));
            byteVector.putInt(2).putShort(this.g);
        }
        if ((this.c & 4096) != 0 && ((this.b.b & 65535) < 49 || (this.c & 262144) != 0)) {
            byteVector.putShort(this.b.newUTF8(AttSynthetic.ATTRIBUTE_NAME)).putInt(0);
        }
        if ((this.c & 131072) != 0) {
            byteVector.putShort(this.b.newUTF8("Deprecated")).putInt(0);
        }
        if (this.f != 0) {
            byteVector.putShort(this.b.newUTF8("Signature"));
            byteVector.putInt(2).putShort(this.f);
        }
        if (this.h != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeVisibleAnnotations"));
            this.h.a(byteVector);
        }
        if (this.i != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeInvisibleAnnotations"));
            this.i.a(byteVector);
        }
        if (this.k != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeVisibleTypeAnnotations"));
            this.k.a(byteVector);
        }
        if (this.l != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeInvisibleTypeAnnotations"));
            this.l.a(byteVector);
        }
        Attribute attribute2 = this.j;
        if (attribute2 != null) {
            attribute2.a(this.b, null, 0, -1, -1, byteVector);
        }
    }

    @Override // com.esotericsoftware.asm.FieldVisitor
    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        byteVector.putShort(this.b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, 2);
        if (z) {
            annotationWriter.g = this.h;
            this.h = annotationWriter;
        } else {
            annotationWriter.g = this.i;
            this.i = annotationWriter;
        }
        return annotationWriter;
    }

    @Override // com.esotericsoftware.asm.FieldVisitor
    public void visitAttribute(Attribute attribute) {
        attribute.a = this.j;
        this.j = attribute;
    }

    @Override // com.esotericsoftware.asm.FieldVisitor
    public void visitEnd() {
    }

    @Override // com.esotericsoftware.asm.FieldVisitor
    public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        AnnotationWriter.a(i, typePath, byteVector);
        byteVector.putShort(this.b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, byteVector.b - 2);
        if (z) {
            annotationWriter.g = this.k;
            this.k = annotationWriter;
        } else {
            annotationWriter.g = this.l;
            this.l = annotationWriter;
        }
        return annotationWriter;
    }
}
