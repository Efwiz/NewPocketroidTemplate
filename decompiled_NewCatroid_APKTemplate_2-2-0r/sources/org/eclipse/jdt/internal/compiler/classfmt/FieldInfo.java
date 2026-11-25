package org.eclipse.jdt.internal.compiler.classfmt;

import io.netty.util.internal.StringUtil;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.codegen.AttributeNamesConstants;
import org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation;
import org.eclipse.jdt.internal.compiler.env.IBinaryField;
import org.eclipse.jdt.internal.compiler.env.IBinaryTypeAnnotation;
import org.eclipse.jdt.internal.compiler.impl.BooleanConstant;
import org.eclipse.jdt.internal.compiler.impl.ByteConstant;
import org.eclipse.jdt.internal.compiler.impl.CharConstant;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.impl.DoubleConstant;
import org.eclipse.jdt.internal.compiler.impl.FloatConstant;
import org.eclipse.jdt.internal.compiler.impl.IntConstant;
import org.eclipse.jdt.internal.compiler.impl.LongConstant;
import org.eclipse.jdt.internal.compiler.impl.ShortConstant;
import org.eclipse.jdt.internal.compiler.impl.StringConstant;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class FieldInfo extends ClassFileStruct implements IBinaryField, Comparable {
    protected int accessFlags;
    protected int attributeBytes;
    protected Constant constant;
    protected char[] descriptor;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected char[] f621name;
    protected char[] signature;
    protected int signatureUtf8Offset;
    protected long tagBits;
    protected Object wrappedConstantValue;

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryField
    public IBinaryAnnotation[] getAnnotations() {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryField
    public IBinaryTypeAnnotation[] getTypeAnnotations() {
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:38:0x00a0 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:39:0x00a2  */
    /* JADX WARN: Code duplicated, block: B:40:0x00a4  */
    /* JADX WARN: Code duplicated, block: B:41:0x00b2  */
    /* JADX WARN: Code duplicated, block: B:42:0x00b4 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:43:0x00b6  */
    /* JADX WARN: Code duplicated, block: B:44:0x00b8  */
    /* JADX WARN: Code duplicated, block: B:53:0x00c5 A[SYNTHETIC] */
    public static FieldInfo createField(byte[] bArr, int[] iArr, int i) {
        AnnotationInfo[] annotationInfoArrDecodeAnnotations;
        TypeAnnotationInfo[] typeAnnotationInfoArr;
        TypeAnnotationInfo[] typeAnnotationInfoArrDecodeTypeAnnotations;
        FieldInfo fieldInfo = new FieldInfo(bArr, iArr, i);
        int iU2At = fieldInfo.u2At(6);
        int iU4At = 8;
        TypeAnnotationInfo[] typeAnnotationInfoArr2 = null;
        AnnotationInfo[] annotationInfoArr = null;
        for (int i2 = 0; i2 < iU2At; i2++) {
            int i3 = fieldInfo.constantPoolOffsets[fieldInfo.u2At(iU4At)] - fieldInfo.structOffset;
            char[] cArrUtf8At = fieldInfo.utf8At(i3 + 3, fieldInfo.u2At(i3 + 1));
            if (cArrUtf8At.length > 0) {
                char c = cArrUtf8At[0];
                if (c != 'R') {
                    if (c == 'S' && CharOperation.equals(AttributeNamesConstants.SignatureName, cArrUtf8At)) {
                        fieldInfo.signatureUtf8Offset = fieldInfo.constantPoolOffsets[fieldInfo.u2At(iU4At + 6)] - fieldInfo.structOffset;
                    }
                } else {
                    if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.RuntimeVisibleAnnotationsName)) {
                        annotationInfoArrDecodeAnnotations = fieldInfo.decodeAnnotations(iU4At, true);
                    } else {
                        if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.RuntimeInvisibleAnnotationsName)) {
                            annotationInfoArrDecodeAnnotations = fieldInfo.decodeAnnotations(iU4At, false);
                        } else {
                            if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.RuntimeVisibleTypeAnnotationsName)) {
                                typeAnnotationInfoArrDecodeTypeAnnotations = fieldInfo.decodeTypeAnnotations(iU4At, true);
                            } else if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.RuntimeInvisibleTypeAnnotationsName)) {
                                typeAnnotationInfoArrDecodeTypeAnnotations = fieldInfo.decodeTypeAnnotations(iU4At, false);
                            } else {
                                annotationInfoArrDecodeAnnotations = null;
                                typeAnnotationInfoArr = null;
                            }
                            typeAnnotationInfoArr = typeAnnotationInfoArrDecodeTypeAnnotations;
                            annotationInfoArrDecodeAnnotations = null;
                        }
                        if (annotationInfoArrDecodeAnnotations != null) {
                            if (annotationInfoArr == null) {
                                annotationInfoArr = annotationInfoArrDecodeAnnotations;
                            } else {
                                int length = annotationInfoArr.length;
                                AnnotationInfo[] annotationInfoArr2 = new AnnotationInfo[annotationInfoArrDecodeAnnotations.length + length];
                                System.arraycopy(annotationInfoArr, 0, annotationInfoArr2, 0, length);
                                System.arraycopy(annotationInfoArrDecodeAnnotations, 0, annotationInfoArr2, length, annotationInfoArrDecodeAnnotations.length);
                                annotationInfoArr = annotationInfoArr2;
                            }
                        } else if (typeAnnotationInfoArr == null) {
                            if (typeAnnotationInfoArr2 == null) {
                                typeAnnotationInfoArr2 = typeAnnotationInfoArr;
                            } else {
                                int length2 = typeAnnotationInfoArr2.length;
                                TypeAnnotationInfo[] typeAnnotationInfoArr3 = new TypeAnnotationInfo[typeAnnotationInfoArr.length + length2];
                                System.arraycopy(typeAnnotationInfoArr2, 0, typeAnnotationInfoArr3, 0, length2);
                                System.arraycopy(typeAnnotationInfoArr, 0, typeAnnotationInfoArr3, length2, typeAnnotationInfoArr.length);
                                typeAnnotationInfoArr2 = typeAnnotationInfoArr3;
                            }
                        }
                    }
                    typeAnnotationInfoArr = null;
                    if (annotationInfoArrDecodeAnnotations != null) {
                        if (annotationInfoArr == null) {
                            annotationInfoArr = annotationInfoArrDecodeAnnotations;
                        } else {
                            int length3 = annotationInfoArr.length;
                            AnnotationInfo[] annotationInfoArr3 = new AnnotationInfo[annotationInfoArrDecodeAnnotations.length + length3];
                            System.arraycopy(annotationInfoArr, 0, annotationInfoArr3, 0, length3);
                            System.arraycopy(annotationInfoArrDecodeAnnotations, 0, annotationInfoArr3, length3, annotationInfoArrDecodeAnnotations.length);
                            annotationInfoArr = annotationInfoArr3;
                        }
                    } else if (typeAnnotationInfoArr == null) {
                        if (typeAnnotationInfoArr2 == null) {
                            typeAnnotationInfoArr2 = typeAnnotationInfoArr;
                        } else {
                            int length4 = typeAnnotationInfoArr2.length;
                            TypeAnnotationInfo[] typeAnnotationInfoArr4 = new TypeAnnotationInfo[typeAnnotationInfoArr.length + length4];
                            System.arraycopy(typeAnnotationInfoArr2, 0, typeAnnotationInfoArr4, 0, length4);
                            System.arraycopy(typeAnnotationInfoArr, 0, typeAnnotationInfoArr4, length4, typeAnnotationInfoArr.length);
                            typeAnnotationInfoArr2 = typeAnnotationInfoArr4;
                        }
                    }
                }
            }
            iU4At = (int) (((long) iU4At) + fieldInfo.u4At(iU4At + 2) + 6);
        }
        fieldInfo.attributeBytes = iU4At;
        if (typeAnnotationInfoArr2 != null) {
            return new FieldInfoWithTypeAnnotation(fieldInfo, annotationInfoArr, typeAnnotationInfoArr2);
        }
        return annotationInfoArr != null ? new FieldInfoWithAnnotation(fieldInfo, annotationInfoArr) : fieldInfo;
    }

    protected FieldInfo(byte[] bArr, int[] iArr, int i) {
        super(bArr, iArr, i);
        this.accessFlags = -1;
        this.signatureUtf8Offset = -1;
    }

    private AnnotationInfo[] decodeAnnotations(int i, boolean z) {
        int iU2At = u2At(i + 6);
        if (iU2At > 0) {
            int i2 = i + 8;
            AnnotationInfo[] annotationInfoArr = null;
            int i3 = 0;
            for (int i4 = 0; i4 < iU2At; i4++) {
                AnnotationInfo annotationInfo = new AnnotationInfo(this.reference, this.constantPoolOffsets, i2 + this.structOffset, z, false);
                i2 += annotationInfo.readOffset;
                long j = annotationInfo.standardAnnotationTagBits;
                if (j != 0) {
                    this.tagBits = j | this.tagBits;
                } else {
                    if (annotationInfoArr == null) {
                        annotationInfoArr = new AnnotationInfo[iU2At - i4];
                    }
                    annotationInfoArr[i3] = annotationInfo;
                    i3++;
                }
            }
            if (annotationInfoArr != null) {
                if (i3 == annotationInfoArr.length) {
                    return annotationInfoArr;
                }
                AnnotationInfo[] annotationInfoArr2 = new AnnotationInfo[i3];
                System.arraycopy(annotationInfoArr, 0, annotationInfoArr2, 0, i3);
                return annotationInfoArr2;
            }
        }
        return null;
    }

    TypeAnnotationInfo[] decodeTypeAnnotations(int i, boolean z) {
        int iU2At = u2At(i + 6);
        if (iU2At <= 0) {
            return null;
        }
        int i2 = i + 8;
        TypeAnnotationInfo[] typeAnnotationInfoArr = new TypeAnnotationInfo[iU2At];
        for (int i3 = 0; i3 < iU2At; i3++) {
            TypeAnnotationInfo typeAnnotationInfo = new TypeAnnotationInfo(this.reference, this.constantPoolOffsets, i2 + this.structOffset, z, false);
            i2 += typeAnnotationInfo.readOffset;
            typeAnnotationInfoArr[i3] = typeAnnotationInfo;
        }
        return typeAnnotationInfoArr;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return new String(getName()).compareTo(new String(((FieldInfo) obj).getName()));
    }

    public boolean equals(Object obj) {
        if (obj instanceof FieldInfo) {
            return CharOperation.equals(getName(), ((FieldInfo) obj).getName());
        }
        return false;
    }

    public int hashCode() {
        return CharOperation.hashCode(getName());
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryField
    public Constant getConstant() {
        if (this.constant == null) {
            readConstantAttribute();
        }
        return this.constant;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryField
    public char[] getGenericSignature() {
        int i = this.signatureUtf8Offset;
        if (i == -1) {
            return null;
        }
        if (this.signature == null) {
            this.signature = utf8At(i + 3, u2At(i + 1));
        }
        return this.signature;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IGenericField
    public int getModifiers() {
        if (this.accessFlags == -1) {
            this.accessFlags = u2At(0);
            readModifierRelatedAttributes();
        }
        return this.accessFlags;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryField
    public char[] getName() {
        if (this.f621name == null) {
            int i = this.constantPoolOffsets[u2At(2)] - this.structOffset;
            this.f621name = utf8At(i + 3, u2At(i + 1));
        }
        return this.f621name;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryField
    public long getTagBits() {
        return this.tagBits;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryField
    public char[] getTypeName() {
        if (this.descriptor == null) {
            int i = this.constantPoolOffsets[u2At(4)] - this.structOffset;
            this.descriptor = utf8At(i + 3, u2At(i + 1));
        }
        return this.descriptor;
    }

    public Object getWrappedConstantValue() {
        if (this.wrappedConstantValue == null && hasConstant()) {
            Constant constant = getConstant();
            switch (constant.typeID()) {
                case 2:
                    this.wrappedConstantValue = Character.valueOf(constant.charValue());
                    break;
                case 3:
                    this.wrappedConstantValue = Byte.valueOf(constant.byteValue());
                    break;
                case 4:
                    this.wrappedConstantValue = Short.valueOf(constant.shortValue());
                    break;
                case 5:
                    this.wrappedConstantValue = Util.toBoolean(constant.booleanValue());
                    break;
                case 7:
                    this.wrappedConstantValue = Long.valueOf(constant.longValue());
                    break;
                case 8:
                    this.wrappedConstantValue = new Double(constant.doubleValue());
                    break;
                case 9:
                    this.wrappedConstantValue = new Float(constant.floatValue());
                    break;
                case 10:
                    this.wrappedConstantValue = Integer.valueOf(constant.intValue());
                    break;
                case 11:
                    this.wrappedConstantValue = constant.stringValue();
                    break;
            }
        }
        return this.wrappedConstantValue;
    }

    public boolean hasConstant() {
        return getConstant() != Constant.NotAConstant;
    }

    protected void initialize() {
        getModifiers();
        getName();
        getConstant();
        getTypeName();
        getGenericSignature();
        reset();
    }

    public boolean isSynthetic() {
        return (getModifiers() & 4096) != 0;
    }

    private void readConstantAttribute() {
        int iU2At = u2At(6);
        int iU4At = 8;
        boolean z = false;
        for (int i = 0; i < iU2At; i++) {
            int i2 = this.constantPoolOffsets[u2At(iU4At)] - this.structOffset;
            if (CharOperation.equals(utf8At(i2 + 3, u2At(i2 + 1)), AttributeNamesConstants.ConstantValueName)) {
                int i3 = this.constantPoolOffsets[u2At(iU4At + 6)] - this.structOffset;
                int iU1At = u1At(i3);
                if (iU1At == 3) {
                    char[] typeName = getTypeName();
                    if (typeName.length == 1) {
                        char c = typeName[0];
                        if (c == 'B') {
                            this.constant = ByteConstant.fromValue((byte) i4At(i3 + 1));
                        } else if (c == 'C') {
                            this.constant = CharConstant.fromValue((char) i4At(i3 + 1));
                        } else if (c == 'I') {
                            this.constant = IntConstant.fromValue(i4At(i3 + 1));
                        } else if (c == 'S') {
                            this.constant = ShortConstant.fromValue((short) i4At(i3 + 1));
                        } else if (c != 'Z') {
                            this.constant = Constant.NotAConstant;
                        } else {
                            this.constant = BooleanConstant.fromValue(i4At(i3 + 1) == 1);
                        }
                    } else {
                        this.constant = Constant.NotAConstant;
                    }
                } else if (iU1At == 4) {
                    this.constant = FloatConstant.fromValue(floatAt(i3 + 1));
                } else if (iU1At == 5) {
                    this.constant = LongConstant.fromValue(i8At(i3 + 1));
                } else if (iU1At == 6) {
                    this.constant = DoubleConstant.fromValue(doubleAt(i3 + 1));
                } else if (iU1At == 8) {
                    int i4 = this.constantPoolOffsets[u2At(i3 + 1)] - this.structOffset;
                    this.constant = StringConstant.fromValue(String.valueOf(utf8At(i4 + 3, u2At(i4 + 1))));
                }
                z = true;
            }
            iU4At = (int) (((long) iU4At) + u4At(iU4At + 2) + 6);
        }
        if (z) {
            return;
        }
        this.constant = Constant.NotAConstant;
    }

    private void readModifierRelatedAttributes() {
        int iU2At = u2At(6);
        int iU4At = 8;
        for (int i = 0; i < iU2At; i++) {
            int i2 = this.constantPoolOffsets[u2At(iU4At)] - this.structOffset;
            char[] cArrUtf8At = utf8At(i2 + 3, u2At(i2 + 1));
            if (cArrUtf8At.length != 0) {
                char c = cArrUtf8At[0];
                if (c == 'D') {
                    if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.DeprecatedName)) {
                        this.accessFlags |= 1048576;
                    }
                } else if (c == 'S' && CharOperation.equals(cArrUtf8At, AttributeNamesConstants.SyntheticName)) {
                    this.accessFlags |= 4096;
                }
            }
            iU4At = (int) (((long) iU4At) + u4At(iU4At + 2) + 6);
        }
    }

    public int sizeInBytes() {
        return this.attributeBytes;
    }

    public void throwFormatException() throws ClassFormatException {
        throw new ClassFormatException(17);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(getClass().getName());
        toStringContent(stringBuffer);
        return stringBuffer.toString();
    }

    protected void toStringContent(StringBuffer stringBuffer) {
        int modifiers = getModifiers();
        stringBuffer.append('{').append(String.valueOf((1048576 & modifiers) != 0 ? "deprecated " : Util.EMPTY_STRING) + ((modifiers & 1) == 1 ? "public " : Util.EMPTY_STRING) + ((modifiers & 2) == 2 ? "private " : Util.EMPTY_STRING) + ((modifiers & 4) == 4 ? "protected " : Util.EMPTY_STRING) + ((modifiers & 8) == 8 ? "static " : Util.EMPTY_STRING) + ((modifiers & 16) == 16 ? "final " : Util.EMPTY_STRING) + ((modifiers & 64) == 64 ? "volatile " : Util.EMPTY_STRING) + ((modifiers & 128) == 128 ? "transient " : Util.EMPTY_STRING)).append(getTypeName()).append(StringUtil.SPACE).append(getName()).append(StringUtil.SPACE).append(getConstant()).append('}').toString();
    }
}
