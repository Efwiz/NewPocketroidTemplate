package org.eclipse.jdt.internal.compiler.classfmt;

import net.bytebuddy.description.method.ParameterDescription;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.codegen.AttributeNamesConstants;
import org.eclipse.jdt.internal.compiler.codegen.ConstantPool;
import org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation;
import org.eclipse.jdt.internal.compiler.env.IBinaryMethod;
import org.eclipse.jdt.internal.compiler.env.IBinaryTypeAnnotation;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class MethodInfo extends ClassFileStruct implements IBinaryMethod, Comparable {
    protected int accessFlags;
    protected char[][] argumentNames;
    protected int attributeBytes;
    protected char[] descriptor;
    protected char[][] exceptionNames;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected char[] f622name;
    protected char[] signature;
    protected int signatureUtf8Offset;
    protected long tagBits;
    private static final char[][] noException = CharOperation.NO_CHAR_CHAR;
    private static final char[][] noArgumentNames = CharOperation.NO_CHAR_CHAR;
    private static final char[] ARG = ParameterDescription.NAME_PREFIX.toCharArray();

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryMethod
    public int getAnnotatedParametersCount() {
        return 0;
    }

    public IBinaryAnnotation[] getAnnotations() {
        return null;
    }

    public Object getDefaultValue() {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryMethod
    public IBinaryAnnotation[] getParameterAnnotations(int i, char[] cArr) {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryMethod
    public IBinaryTypeAnnotation[] getTypeAnnotations() {
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:51:0x00cd A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:52:0x00cf  */
    /* JADX WARN: Code duplicated, block: B:53:0x00d2  */
    /* JADX WARN: Code duplicated, block: B:54:0x00e0 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:55:0x00e2  */
    /* JADX WARN: Code duplicated, block: B:57:0x00e5  */
    /* JADX WARN: Code duplicated, block: B:58:0x00e7  */
    /* JADX WARN: Code duplicated, block: B:61:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:63:0x00ef  */
    /* JADX WARN: Code duplicated, block: B:64:0x00f1  */
    /* JADX WARN: Code duplicated, block: B:66:0x00f4  */
    /* JADX WARN: Code duplicated, block: B:68:0x00f8  */
    /* JADX WARN: Code duplicated, block: B:69:0x00fb  */
    /* JADX WARN: Code duplicated, block: B:71:0x010d  */
    /* JADX WARN: Code duplicated, block: B:72:0x010f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:73:0x0111  */
    /* JADX WARN: Code duplicated, block: B:74:0x0113  */
    /* JADX WARN: Code duplicated, block: B:87:0x012c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:94:0x010a A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v2 */
    public static MethodInfo createMethod(byte[] bArr, int[] iArr, int i) {
        AnnotationInfo[] annotationInfoArrDecodeMethodAnnotations;
        AnnotationInfo[][] annotationInfoArr;
        TypeAnnotationInfo[] typeAnnotationInfoArrDecodeTypeAnnotations;
        Object[] objArr;
        AnnotationInfo[][] annotationInfoArrDecodeParamAnnotations;
        int length;
        int i2;
        AnnotationInfo[] annotationInfoArr2;
        int length2;
        AnnotationInfo[] annotationInfoArr3;
        MethodInfo methodInfo = new MethodInfo(bArr, iArr, i);
        int iU2At = methodInfo.u2At(6);
        int iU4At = 8;
        TypeAnnotationInfo[] typeAnnotationInfoArr = 0;
        AnnotationInfo[][] annotationInfoArr4 = null;
        AnnotationInfo[] annotationInfoArr5 = null;
        int i3 = 0;
        while (i3 < iU2At) {
            int i4 = methodInfo.constantPoolOffsets[methodInfo.u2At(iU4At)] - methodInfo.structOffset;
            char[] cArrUtf8At = methodInfo.utf8At(i4 + 3, methodInfo.u2At(i4 + 1));
            if (cArrUtf8At.length > 0) {
                char c = cArrUtf8At[0];
                if (c != 'M') {
                    if (c != 'R') {
                        if (c == 'S' && CharOperation.equals(AttributeNamesConstants.SignatureName, cArrUtf8At)) {
                            methodInfo.signatureUtf8Offset = methodInfo.constantPoolOffsets[methodInfo.u2At(iU4At + 6)] - methodInfo.structOffset;
                        }
                    } else {
                        if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.RuntimeVisibleAnnotationsName)) {
                            annotationInfoArrDecodeMethodAnnotations = decodeMethodAnnotations(iU4At, true, methodInfo);
                        } else {
                            if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.RuntimeInvisibleAnnotationsName)) {
                                annotationInfoArrDecodeMethodAnnotations = decodeMethodAnnotations(iU4At, false, methodInfo);
                            } else {
                                if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.RuntimeVisibleParameterAnnotationsName)) {
                                    annotationInfoArrDecodeParamAnnotations = decodeParamAnnotations(iU4At, true, methodInfo);
                                } else if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.RuntimeInvisibleParameterAnnotationsName)) {
                                    annotationInfoArrDecodeParamAnnotations = decodeParamAnnotations(iU4At, false, methodInfo);
                                } else {
                                    if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.RuntimeVisibleTypeAnnotationsName)) {
                                        typeAnnotationInfoArrDecodeTypeAnnotations = decodeTypeAnnotations(iU4At, true, methodInfo);
                                    } else if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.RuntimeInvisibleTypeAnnotationsName)) {
                                        typeAnnotationInfoArrDecodeTypeAnnotations = decodeTypeAnnotations(iU4At, false, methodInfo);
                                    } else {
                                        annotationInfoArrDecodeMethodAnnotations = null;
                                        annotationInfoArr = null;
                                        objArr = annotationInfoArr;
                                    }
                                    annotationInfoArr = null;
                                    objArr = typeAnnotationInfoArrDecodeTypeAnnotations;
                                    annotationInfoArrDecodeMethodAnnotations = null;
                                }
                                objArr = 0;
                                annotationInfoArr = annotationInfoArrDecodeParamAnnotations;
                                annotationInfoArrDecodeMethodAnnotations = null;
                            }
                            if (annotationInfoArrDecodeMethodAnnotations != null) {
                                if (annotationInfoArr5 == null) {
                                    annotationInfoArr5 = annotationInfoArrDecodeMethodAnnotations;
                                } else {
                                    int length3 = annotationInfoArr5.length;
                                    AnnotationInfo[] annotationInfoArr6 = new AnnotationInfo[annotationInfoArrDecodeMethodAnnotations.length + length3];
                                    System.arraycopy(annotationInfoArr5, 0, annotationInfoArr6, 0, length3);
                                    System.arraycopy(annotationInfoArrDecodeMethodAnnotations, 0, annotationInfoArr6, length3, annotationInfoArrDecodeMethodAnnotations.length);
                                    annotationInfoArr5 = annotationInfoArr6;
                                }
                            } else if (annotationInfoArr != null) {
                                length = annotationInfoArr.length;
                                if (annotationInfoArr4 == null) {
                                    annotationInfoArr4 = annotationInfoArr;
                                } else {
                                    for (i2 = 0; i2 < length; i2++) {
                                        annotationInfoArr2 = annotationInfoArr[i2];
                                        if (annotationInfoArr2 == null) {
                                            length2 = 0;
                                        } else {
                                            length2 = annotationInfoArr2.length;
                                        }
                                        if (length2 <= 0) {
                                            annotationInfoArr3 = annotationInfoArr4[i2];
                                            if (annotationInfoArr3 == null) {
                                                annotationInfoArr4[i2] = annotationInfoArr2;
                                            } else {
                                                int length4 = annotationInfoArr3.length;
                                                AnnotationInfo[] annotationInfoArr7 = new AnnotationInfo[length4 + length2];
                                                System.arraycopy(annotationInfoArr3, 0, annotationInfoArr7, 0, length4);
                                                System.arraycopy(annotationInfoArr[i2], 0, annotationInfoArr7, length4, length2);
                                                annotationInfoArr4[i2] = annotationInfoArr7;
                                            }
                                        }
                                    }
                                }
                            } else if (objArr == 0) {
                                if (typeAnnotationInfoArr == 0) {
                                    typeAnnotationInfoArr = objArr;
                                } else {
                                    int length5 = typeAnnotationInfoArr.length;
                                    TypeAnnotationInfo[] typeAnnotationInfoArr2 = new TypeAnnotationInfo[objArr.length + length5];
                                    System.arraycopy(typeAnnotationInfoArr, 0, typeAnnotationInfoArr2, 0, length5);
                                    System.arraycopy(objArr, 0, typeAnnotationInfoArr2, length5, objArr.length);
                                    typeAnnotationInfoArr = typeAnnotationInfoArr2;
                                }
                            }
                        }
                        annotationInfoArr = null;
                        objArr = annotationInfoArr;
                        if (annotationInfoArrDecodeMethodAnnotations != null) {
                            if (annotationInfoArr5 == null) {
                                annotationInfoArr5 = annotationInfoArrDecodeMethodAnnotations;
                            } else {
                                int length6 = annotationInfoArr5.length;
                                AnnotationInfo[] annotationInfoArr8 = new AnnotationInfo[annotationInfoArrDecodeMethodAnnotations.length + length6];
                                System.arraycopy(annotationInfoArr5, 0, annotationInfoArr8, 0, length6);
                                System.arraycopy(annotationInfoArrDecodeMethodAnnotations, 0, annotationInfoArr8, length6, annotationInfoArrDecodeMethodAnnotations.length);
                                annotationInfoArr5 = annotationInfoArr8;
                            }
                        } else if (annotationInfoArr != null) {
                            length = annotationInfoArr.length;
                            if (annotationInfoArr4 == null) {
                                annotationInfoArr4 = annotationInfoArr;
                            } else {
                                while (i2 < length) {
                                    annotationInfoArr2 = annotationInfoArr[i2];
                                    if (annotationInfoArr2 == null) {
                                        length2 = 0;
                                    } else {
                                        length2 = annotationInfoArr2.length;
                                    }
                                    if (length2 <= 0) {
                                        annotationInfoArr3 = annotationInfoArr4[i2];
                                        if (annotationInfoArr3 == null) {
                                            annotationInfoArr4[i2] = annotationInfoArr2;
                                        } else {
                                            int length7 = annotationInfoArr3.length;
                                            AnnotationInfo[] annotationInfoArr9 = new AnnotationInfo[length7 + length2];
                                            System.arraycopy(annotationInfoArr3, 0, annotationInfoArr9, 0, length7);
                                            System.arraycopy(annotationInfoArr[i2], 0, annotationInfoArr9, length7, length2);
                                            annotationInfoArr4[i2] = annotationInfoArr9;
                                        }
                                    }
                                }
                            }
                        } else if (objArr == 0) {
                            if (typeAnnotationInfoArr == 0) {
                                typeAnnotationInfoArr = objArr;
                            } else {
                                int length8 = typeAnnotationInfoArr.length;
                                TypeAnnotationInfo[] typeAnnotationInfoArr3 = new TypeAnnotationInfo[objArr.length + length8];
                                System.arraycopy(typeAnnotationInfoArr, 0, typeAnnotationInfoArr3, 0, length8);
                                System.arraycopy(objArr, 0, typeAnnotationInfoArr3, length8, objArr.length);
                                typeAnnotationInfoArr = typeAnnotationInfoArr3;
                            }
                        }
                    }
                } else if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.MethodParametersName)) {
                    methodInfo.decodeMethodParameters(iU4At, methodInfo);
                }
            }
            iU4At = (int) (((long) iU4At) + methodInfo.u4At(iU4At + 2) + 6);
            i3++;
            typeAnnotationInfoArr = typeAnnotationInfoArr;
        }
        methodInfo.attributeBytes = iU4At;
        if (typeAnnotationInfoArr != 0) {
            return new MethodInfoWithTypeAnnotations(methodInfo, annotationInfoArr5, annotationInfoArr4, typeAnnotationInfoArr);
        }
        if (annotationInfoArr4 != null) {
            return new MethodInfoWithParameterAnnotations(methodInfo, annotationInfoArr5, annotationInfoArr4);
        }
        return annotationInfoArr5 != null ? new MethodInfoWithAnnotations(methodInfo, annotationInfoArr5) : methodInfo;
    }

    static AnnotationInfo[] decodeAnnotations(int i, boolean z, int i2, MethodInfo methodInfo) {
        AnnotationInfo[] annotationInfoArr = new AnnotationInfo[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            AnnotationInfo annotationInfo = new AnnotationInfo(methodInfo.reference, methodInfo.constantPoolOffsets, i + methodInfo.structOffset, z, false);
            annotationInfoArr[i3] = annotationInfo;
            i += annotationInfo.readOffset;
        }
        return annotationInfoArr;
    }

    static AnnotationInfo[] decodeMethodAnnotations(int i, boolean z, MethodInfo methodInfo) {
        int iU2At = methodInfo.u2At(i + 6);
        if (iU2At <= 0) {
            return null;
        }
        AnnotationInfo[] annotationInfoArrDecodeAnnotations = decodeAnnotations(i + 8, z, iU2At, methodInfo);
        if (!z) {
            return annotationInfoArrDecodeAnnotations;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < iU2At; i3++) {
            long j = annotationInfoArrDecodeAnnotations[i3].standardAnnotationTagBits;
            methodInfo.tagBits |= j;
            if (j != 0) {
                annotationInfoArrDecodeAnnotations[i3] = null;
                i2++;
            }
        }
        if (i2 == 0) {
            return annotationInfoArrDecodeAnnotations;
        }
        if (i2 == iU2At) {
            return null;
        }
        AnnotationInfo[] annotationInfoArr = new AnnotationInfo[iU2At - i2];
        int i4 = 0;
        for (int i5 = 0; i5 < iU2At; i5++) {
            AnnotationInfo annotationInfo = annotationInfoArrDecodeAnnotations[i5];
            if (annotationInfo != null) {
                annotationInfoArr[i4] = annotationInfo;
                i4++;
            }
        }
        return annotationInfoArr;
    }

    static TypeAnnotationInfo[] decodeTypeAnnotations(int i, boolean z, MethodInfo methodInfo) {
        int iU2At = methodInfo.u2At(i + 6);
        if (iU2At <= 0) {
            return null;
        }
        int i2 = i + 8;
        TypeAnnotationInfo[] typeAnnotationInfoArr = new TypeAnnotationInfo[iU2At];
        for (int i3 = 0; i3 < iU2At; i3++) {
            TypeAnnotationInfo typeAnnotationInfo = new TypeAnnotationInfo(methodInfo.reference, methodInfo.constantPoolOffsets, i2 + methodInfo.structOffset, z, false);
            i2 += typeAnnotationInfo.readOffset;
            typeAnnotationInfoArr[i3] = typeAnnotationInfo;
        }
        return typeAnnotationInfoArr;
    }

    static AnnotationInfo[][] decodeParamAnnotations(int i, boolean z, MethodInfo methodInfo) {
        int iU1At = methodInfo.u1At(i + 6);
        AnnotationInfo[][] annotationInfoArr = null;
        if (iU1At > 0) {
            int i2 = i + 7;
            for (int i3 = 0; i3 < iU1At; i3++) {
                int iU2At = methodInfo.u2At(i2);
                i2 += 2;
                if (iU2At > 0) {
                    if (annotationInfoArr == null) {
                        annotationInfoArr = new AnnotationInfo[iU1At][];
                    }
                    AnnotationInfo[][] annotationInfoArr2 = annotationInfoArr;
                    AnnotationInfo[] annotationInfoArrDecodeAnnotations = decodeAnnotations(i2, z, iU2At, methodInfo);
                    annotationInfoArr2[i3] = annotationInfoArrDecodeAnnotations;
                    for (AnnotationInfo annotationInfo : annotationInfoArrDecodeAnnotations) {
                        i2 += annotationInfo.readOffset;
                    }
                    annotationInfoArr = annotationInfoArr2;
                }
            }
        }
        return annotationInfoArr;
    }

    protected MethodInfo(byte[] bArr, int[] iArr, int i) {
        super(bArr, iArr, i);
        this.accessFlags = -1;
        this.signatureUtf8Offset = -1;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        MethodInfo methodInfo = (MethodInfo) obj;
        int iCompareTo = new String(getSelector()).compareTo(new String(methodInfo.getSelector()));
        return iCompareTo != 0 ? iCompareTo : new String(getMethodDescriptor()).compareTo(new String(methodInfo.getMethodDescriptor()));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof MethodInfo)) {
            return false;
        }
        MethodInfo methodInfo = (MethodInfo) obj;
        return CharOperation.equals(getSelector(), methodInfo.getSelector()) && CharOperation.equals(getMethodDescriptor(), methodInfo.getMethodDescriptor());
    }

    public int hashCode() {
        return CharOperation.hashCode(getSelector()) + CharOperation.hashCode(getMethodDescriptor());
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IGenericMethod
    public char[][] getArgumentNames() {
        if (this.argumentNames == null) {
            readCodeAttribute();
        }
        return this.argumentNames;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryMethod
    public char[][] getExceptionTypeNames() {
        if (this.exceptionNames == null) {
            readExceptionAttributes();
        }
        return this.exceptionNames;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryMethod
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

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryMethod
    public char[] getMethodDescriptor() {
        if (this.descriptor == null) {
            int i = this.constantPoolOffsets[u2At(4)] - this.structOffset;
            this.descriptor = utf8At(i + 3, u2At(i + 1));
        }
        return this.descriptor;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IGenericMethod
    public int getModifiers() {
        if (this.accessFlags == -1) {
            this.accessFlags = u2At(0);
            readModifierRelatedAttributes();
        }
        return this.accessFlags;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryMethod
    public char[] getSelector() {
        if (this.f622name == null) {
            int i = this.constantPoolOffsets[u2At(2)] - this.structOffset;
            this.f622name = utf8At(i + 3, u2At(i + 1));
        }
        return this.f622name;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryMethod
    public long getTagBits() {
        return this.tagBits;
    }

    protected void initialize() {
        getModifiers();
        getSelector();
        getMethodDescriptor();
        getExceptionTypeNames();
        getGenericSignature();
        getArgumentNames();
        reset();
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryMethod
    public boolean isClinit() {
        char[] selector = getSelector();
        return selector[0] == '<' && selector.length == 8;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IGenericMethod
    public boolean isConstructor() {
        char[] selector = getSelector();
        return selector[0] == '<' && selector.length == 6;
    }

    public boolean isSynthetic() {
        return (getModifiers() & 4096) != 0;
    }

    private void readExceptionAttributes() {
        int iU2At = u2At(6);
        int iU4At = 8;
        for (int i = 0; i < iU2At; i++) {
            int i2 = this.constantPoolOffsets[u2At(iU4At)] - this.structOffset;
            if (CharOperation.equals(utf8At(i2 + 3, u2At(i2 + 1)), AttributeNamesConstants.ExceptionsName)) {
                int iU2At2 = u2At(iU4At + 6);
                iU4At += 8;
                if (iU2At2 == 0) {
                    this.exceptionNames = noException;
                } else {
                    this.exceptionNames = new char[iU2At2][];
                    for (int i3 = 0; i3 < iU2At2; i3++) {
                        int i4 = this.constantPoolOffsets[u2At((this.constantPoolOffsets[u2At(iU4At)] - this.structOffset) + 1)] - this.structOffset;
                        this.exceptionNames[i3] = utf8At(i4 + 3, u2At(i4 + 1));
                        iU4At += 2;
                    }
                }
            } else {
                iU4At = (int) (((long) iU4At) + u4At(iU4At + 2) + 6);
            }
        }
        if (this.exceptionNames == null) {
            this.exceptionNames = noException;
        }
    }

    private void readModifierRelatedAttributes() {
        int iU2At = u2At(6);
        int iU4At = 8;
        for (int i = 0; i < iU2At; i++) {
            int i2 = this.constantPoolOffsets[u2At(iU4At)] - this.structOffset;
            char[] cArrUtf8At = utf8At(i2 + 3, u2At(i2 + 1));
            if (cArrUtf8At.length != 0) {
                char c = cArrUtf8At[0];
                if (c != 'A') {
                    if (c != 'D') {
                        if (c == 'S') {
                            if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.SyntheticName)) {
                                this.accessFlags |= 4096;
                            }
                        } else if (c == 'V' && CharOperation.equals(cArrUtf8At, AttributeNamesConstants.VarargsName)) {
                            this.accessFlags |= 128;
                        }
                    } else if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.DeprecatedName)) {
                        this.accessFlags |= 1048576;
                    }
                } else if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.AnnotationDefaultName)) {
                    this.accessFlags |= 131072;
                }
            }
            iU4At = (int) (((long) iU4At) + u4At(iU4At + 2) + 6);
        }
    }

    public int sizeInBytes() {
        return this.attributeBytes;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        toString(stringBuffer);
        return stringBuffer.toString();
    }

    void toString(StringBuffer stringBuffer) {
        stringBuffer.append(getClass().getName());
        toStringContent(stringBuffer);
    }

    protected void toStringContent(StringBuffer stringBuffer) {
        int modifiers = getModifiers();
        char[] genericSignature = getGenericSignature();
        if (genericSignature == null) {
            genericSignature = getMethodDescriptor();
        }
        stringBuffer.append('{').append(String.valueOf((1048576 & modifiers) != 0 ? "deprecated " : Util.EMPTY_STRING) + ((modifiers & 1) == 1 ? "public " : Util.EMPTY_STRING) + ((modifiers & 2) == 2 ? "private " : Util.EMPTY_STRING) + ((modifiers & 4) == 4 ? "protected " : Util.EMPTY_STRING) + ((modifiers & 8) == 8 ? "static " : Util.EMPTY_STRING) + ((modifiers & 16) == 16 ? "final " : Util.EMPTY_STRING) + ((modifiers & 64) == 64 ? "bridge " : Util.EMPTY_STRING) + ((modifiers & 128) == 128 ? "varargs " : Util.EMPTY_STRING)).append(getSelector()).append(genericSignature).append('}');
    }

    private void readCodeAttribute() {
        int iU2At = u2At(6);
        if (iU2At != 0) {
            int iU4At = 8;
            for (int i = 0; i < iU2At; i++) {
                int i2 = this.constantPoolOffsets[u2At(iU4At)] - this.structOffset;
                if (CharOperation.equals(utf8At(i2 + 3, u2At(i2 + 1)), AttributeNamesConstants.CodeName)) {
                    decodeCodeAttribute(iU4At);
                    if (this.argumentNames == null) {
                        this.argumentNames = noArgumentNames;
                        return;
                    }
                    return;
                }
                iU4At = (int) (((long) iU4At) + u4At(iU4At + 2) + 6);
            }
        }
        this.argumentNames = noArgumentNames;
    }

    private void decodeCodeAttribute(int i) {
        int i2 = i + 10;
        int iU4At = (int) u4At(i2);
        int i3 = i2 + iU4At + 4;
        int iU2At = u2At(i3);
        int i4 = i3 + 2;
        if (iU2At != 0) {
            for (int i5 = 0; i5 < iU2At; i5++) {
                i4 += 8;
            }
        }
        int iU2At2 = u2At(i4);
        int iU4At2 = i4 + 2;
        for (int i6 = 0; i6 < iU2At2; i6++) {
            int i7 = this.constantPoolOffsets[u2At(iU4At2)] - this.structOffset;
            if (CharOperation.equals(utf8At(i7 + 3, u2At(i7 + 1)), AttributeNamesConstants.LocalVariableTableName)) {
                decodeLocalVariableAttribute(iU4At2, iU4At);
            }
            iU4At2 = (int) (((long) iU4At2) + u4At(iU4At2 + 2) + 6);
        }
    }

    private void decodeLocalVariableAttribute(int i, int i2) {
        int iU2At = u2At(i + 6);
        if (iU2At != 0) {
            int i3 = i + 8;
            this.argumentNames = new char[iU2At][];
            int i4 = 0;
            for (int i5 = 0; i5 < iU2At && u2At(i3) == 0; i5++) {
                int i6 = this.constantPoolOffsets[u2At(i3 + 4)] - this.structOffset;
                char[] cArrUtf8At = utf8At(i6 + 3, u2At(i6 + 1));
                if (!CharOperation.equals(cArrUtf8At, ConstantPool.This)) {
                    this.argumentNames[i4] = cArrUtf8At;
                    i4++;
                }
                i3 += 10;
            }
            char[][] cArr = this.argumentNames;
            if (i4 != cArr.length) {
                char[][] cArr2 = new char[i4][];
                this.argumentNames = cArr2;
                System.arraycopy(cArr, 0, cArr2, 0, i4);
            }
        }
    }

    private void decodeMethodParameters(int i, MethodInfo methodInfo) {
        int iU1At = u1At(i + 6);
        if (iU1At != 0) {
            int i2 = i + 7;
            this.argumentNames = new char[iU1At][];
            for (int i3 = 0; i3 < iU1At; i3++) {
                int iU2At = u2At(i2);
                if (iU2At != 0) {
                    int i4 = this.constantPoolOffsets[iU2At] - this.structOffset;
                    this.argumentNames[i3] = utf8At(i4 + 3, u2At(i4 + 1));
                } else {
                    this.argumentNames[i3] = CharOperation.concat(ARG, String.valueOf(i3).toCharArray());
                }
                i2 += 4;
            }
        }
    }
}
