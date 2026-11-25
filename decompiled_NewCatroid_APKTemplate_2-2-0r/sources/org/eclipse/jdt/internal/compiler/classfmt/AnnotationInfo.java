package org.eclipse.jdt.internal.compiler.classfmt;

import java.util.Arrays;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.codegen.ConstantPool;
import org.eclipse.jdt.internal.compiler.env.ClassSignature;
import org.eclipse.jdt.internal.compiler.env.EnumConstantSignature;
import org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation;
import org.eclipse.jdt.internal.compiler.env.IBinaryElementValuePair;
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
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: loaded from: classes14.dex */
public class AnnotationInfo extends ClassFileStruct implements IBinaryAnnotation {
    static Object[] EmptyValueArray = new Object[0];
    private ElementValuePairInfo[] pairs;
    int readOffset;
    long standardAnnotationTagBits;
    private char[] typename;

    AnnotationInfo(byte[] bArr, int[] iArr, int i) {
        super(bArr, iArr, i);
        this.standardAnnotationTagBits = 0L;
        this.readOffset = 0;
    }

    AnnotationInfo(byte[] bArr, int[] iArr, int i, boolean z, boolean z2) {
        this(bArr, iArr, i);
        if (z2) {
            decodeAnnotation();
        } else {
            this.readOffset = scanAnnotation(0, z, true);
        }
    }

    private void decodeAnnotation() {
        this.readOffset = 0;
        int i = this.constantPoolOffsets[u2At(0)] - this.structOffset;
        this.typename = utf8At(i + 3, u2At(i + 1));
        int iU2At = u2At(2);
        this.readOffset += 4;
        this.pairs = iU2At == 0 ? ElementValuePairInfo.NoMembers : new ElementValuePairInfo[iU2At];
        for (int i2 = 0; i2 < iU2At; i2++) {
            int i3 = this.constantPoolOffsets[u2At(this.readOffset)] - this.structOffset;
            char[] cArrUtf8At = utf8At(i3 + 3, u2At(i3 + 1));
            this.readOffset += 2;
            this.pairs[i2] = new ElementValuePairInfo(cArrUtf8At, decodeDefaultValue());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v24, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v19, types: [org.eclipse.jdt.internal.compiler.env.ClassSignature] */
    /* JADX WARN: Type inference failed for: r1v20 */
    /* JADX WARN: Type inference failed for: r1v56, types: [java.lang.Object[]] */
    Object decodeDefaultValue() {
        ?? classSignature;
        int iU1At = u1At(this.readOffset);
        int i = this.readOffset + 1;
        this.readOffset = i;
        if (iU1At == 64) {
            AnnotationInfo annotationInfo = new AnnotationInfo(this.reference, this.constantPoolOffsets, this.readOffset + this.structOffset, false, true);
            this.readOffset += annotationInfo.readOffset;
            return annotationInfo;
        }
        if (iU1At == 70) {
            Constant constantFromValue = FloatConstant.fromValue(floatAt((this.constantPoolOffsets[u2At(this.readOffset)] - this.structOffset) + 1));
            this.readOffset += 2;
            return constantFromValue;
        }
        if (iU1At == 83) {
            Constant constantFromValue2 = ShortConstant.fromValue((short) i4At((this.constantPoolOffsets[u2At(this.readOffset)] - this.structOffset) + 1));
            this.readOffset += 2;
            return constantFromValue2;
        }
        if (iU1At == 99) {
            int i2 = this.constantPoolOffsets[u2At(this.readOffset)] - this.structOffset;
            classSignature = new ClassSignature(utf8At(i2 + 3, u2At(i2 + 1)));
            this.readOffset += 2;
        } else {
            if (iU1At == 101) {
                int i3 = this.constantPoolOffsets[u2At(this.readOffset)] - this.structOffset;
                char[] cArrUtf8At = utf8At(i3 + 3, u2At(i3 + 1));
                this.readOffset += 2;
                int i4 = this.constantPoolOffsets[u2At(this.readOffset)] - this.structOffset;
                char[] cArrUtf8At2 = utf8At(i4 + 3, u2At(i4 + 1));
                this.readOffset += 2;
                return new EnumConstantSignature(cArrUtf8At, cArrUtf8At2);
            }
            if (iU1At == 115) {
                int i5 = this.constantPoolOffsets[u2At(this.readOffset)] - this.structOffset;
                Constant constantFromValue3 = StringConstant.fromValue(String.valueOf(utf8At(i5 + 3, u2At(i5 + 1))));
                this.readOffset += 2;
                return constantFromValue3;
            }
            if (iU1At == 73) {
                Constant constantFromValue4 = IntConstant.fromValue(i4At((this.constantPoolOffsets[u2At(this.readOffset)] - this.structOffset) + 1));
                this.readOffset += 2;
                return constantFromValue4;
            }
            if (iU1At == 74) {
                Constant constantFromValue5 = LongConstant.fromValue(i8At((this.constantPoolOffsets[u2At(this.readOffset)] - this.structOffset) + 1));
                this.readOffset += 2;
                return constantFromValue5;
            }
            if (iU1At == 90) {
                Constant constantFromValue6 = BooleanConstant.fromValue(i4At((this.constantPoolOffsets[u2At(this.readOffset)] - this.structOffset) + 1) == 1);
                this.readOffset += 2;
                return constantFromValue6;
            }
            if (iU1At != 91) {
                switch (iU1At) {
                    case 66:
                        Constant constantFromValue7 = ByteConstant.fromValue((byte) i4At((this.constantPoolOffsets[u2At(this.readOffset)] - this.structOffset) + 1));
                        this.readOffset += 2;
                        return constantFromValue7;
                    case 67:
                        Constant constantFromValue8 = CharConstant.fromValue((char) i4At((this.constantPoolOffsets[u2At(this.readOffset)] - this.structOffset) + 1));
                        this.readOffset += 2;
                        return constantFromValue8;
                    case 68:
                        Constant constantFromValue9 = DoubleConstant.fromValue(doubleAt((this.constantPoolOffsets[u2At(this.readOffset)] - this.structOffset) + 1));
                        this.readOffset += 2;
                        return constantFromValue9;
                    default:
                        throw new IllegalStateException("Unrecognized tag " + ((char) iU1At));
                }
            }
            int iU2At = u2At(i);
            this.readOffset += 2;
            if (iU2At == 0) {
                return EmptyValueArray;
            }
            classSignature = new Object[iU2At];
            for (int i6 = 0; i6 < iU2At; i6++) {
                classSignature[i6] = decodeDefaultValue();
            }
        }
        return classSignature;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation
    public IBinaryElementValuePair[] getElementValuePairs() {
        if (this.pairs == null) {
            initialize();
        }
        return this.pairs;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation
    public char[] getTypeName() {
        return this.typename;
    }

    void initialize() {
        if (this.pairs == null) {
            decodeAnnotation();
        }
    }

    private int readRetentionPolicy(int i) {
        int iU1At = u1At(i);
        int i2 = i + 1;
        if (iU1At == 64) {
            return scanAnnotation(i2, false, false);
        }
        if (iU1At != 70 && iU1At != 83 && iU1At != 99) {
            if (iU1At == 101) {
                int i3 = this.constantPoolOffsets[u2At(i2)] - this.structOffset;
                char[] cArrUtf8At = utf8At(i3 + 3, u2At(i3 + 1));
                int i4 = i + 3;
                if (cArrUtf8At.length == 38 && CharOperation.equals(cArrUtf8At, ConstantPool.JAVA_LANG_ANNOTATION_RETENTIONPOLICY)) {
                    int i5 = this.constantPoolOffsets[u2At(i4)] - this.structOffset;
                    this.standardAnnotationTagBits |= Annotation.getRetentionPolicy(utf8At(i5 + 3, u2At(i5 + 1)));
                }
                return i + 5;
            }
            if (iU1At != 115 && iU1At != 73 && iU1At != 74 && iU1At != 90) {
                if (iU1At == 91) {
                    int iU2At = u2At(i2);
                    int iScanElementValue = i + 3;
                    for (int i6 = 0; i6 < iU2At; i6++) {
                        iScanElementValue = scanElementValue(iScanElementValue);
                    }
                    return iScanElementValue;
                }
                switch (iU1At) {
                    case 66:
                    case 67:
                    case 68:
                        break;
                    default:
                        throw new IllegalStateException();
                }
            }
        }
        return i + 3;
    }

    private int readTargetValue(int i) {
        int iU1At = u1At(i);
        int i2 = i + 1;
        if (iU1At == 64) {
            return scanAnnotation(i2, false, false);
        }
        if (iU1At != 70 && iU1At != 83 && iU1At != 99) {
            if (iU1At == 101) {
                int i3 = this.constantPoolOffsets[u2At(i2)] - this.structOffset;
                char[] cArrUtf8At = utf8At(i3 + 3, u2At(i3 + 1));
                int i4 = i + 3;
                if (cArrUtf8At.length == 34 && CharOperation.equals(cArrUtf8At, ConstantPool.JAVA_LANG_ANNOTATION_ELEMENTTYPE)) {
                    int i5 = this.constantPoolOffsets[u2At(i4)] - this.structOffset;
                    this.standardAnnotationTagBits |= Annotation.getTargetElementType(utf8At(i5 + 3, u2At(i5 + 1)));
                }
                return i + 5;
            }
            if (iU1At != 115 && iU1At != 73 && iU1At != 74 && iU1At != 90) {
                if (iU1At == 91) {
                    int iU2At = u2At(i2);
                    int targetValue = i + 3;
                    if (iU2At == 0) {
                        this.standardAnnotationTagBits |= 34359738368L;
                        return targetValue;
                    }
                    for (int i6 = 0; i6 < iU2At; i6++) {
                        targetValue = readTargetValue(targetValue);
                    }
                    return targetValue;
                }
                switch (iU1At) {
                    case 66:
                    case 67:
                    case 68:
                        break;
                    default:
                        throw new IllegalStateException();
                }
            }
        }
        return i + 3;
    }

    private int scanAnnotation(int i, boolean z, boolean z2) {
        int i2 = this.constantPoolOffsets[u2At(i)] - this.structOffset;
        char[] cArrUtf8At = utf8At(i2 + 3, u2At(i2 + 1));
        if (z2) {
            this.typename = cArrUtf8At;
        }
        int iU2At = u2At(i + 2);
        int iScanElementValue = i + 4;
        if (z && z2) {
            int length = cArrUtf8At.length;
            if (length != 22) {
                if (length != 23) {
                    if (length != 29) {
                        if (length != 52) {
                            if (length == 32) {
                                if (CharOperation.equals(cArrUtf8At, ConstantPool.JAVA_LANG_ANNOTATION_RETENTION)) {
                                    return readRetentionPolicy(i + 6);
                                }
                                if (CharOperation.equals(cArrUtf8At, ConstantPool.JAVA_LANG_ANNOTATION_INHERITED)) {
                                    this.standardAnnotationTagBits |= 281474976710656L;
                                    return iScanElementValue;
                                }
                            } else if (length == 33 && CharOperation.equals(cArrUtf8At, ConstantPool.JAVA_LANG_ANNOTATION_DOCUMENTED)) {
                                this.standardAnnotationTagBits |= 140737488355328L;
                                return iScanElementValue;
                            }
                        } else if (CharOperation.equals(cArrUtf8At, ConstantPool.JAVA_LANG_INVOKE_METHODHANDLE_POLYMORPHICSIGNATURE)) {
                            this.standardAnnotationTagBits |= 4503599627370496L;
                            return iScanElementValue;
                        }
                    } else if (CharOperation.equals(cArrUtf8At, ConstantPool.JAVA_LANG_ANNOTATION_TARGET)) {
                        return readTargetValue(i + 6);
                    }
                } else if (CharOperation.equals(cArrUtf8At, ConstantPool.JAVA_LANG_SAFEVARARGS)) {
                    this.standardAnnotationTagBits |= 2251799813685248L;
                    return iScanElementValue;
                }
            } else if (CharOperation.equals(cArrUtf8At, ConstantPool.JAVA_LANG_DEPRECATED)) {
                this.standardAnnotationTagBits |= 70368744177664L;
                return iScanElementValue;
            }
        }
        for (int i3 = 0; i3 < iU2At; i3++) {
            iScanElementValue = scanElementValue(iScanElementValue + 2);
        }
        return iScanElementValue;
    }

    private int scanElementValue(int i) {
        int iU1At = u1At(i);
        int i2 = i + 1;
        if (iU1At == 64) {
            return scanAnnotation(i2, false, false);
        }
        if (iU1At != 70 && iU1At != 83 && iU1At != 99) {
            if (iU1At == 101) {
                return i + 5;
            }
            if (iU1At != 115 && iU1At != 73 && iU1At != 74 && iU1At != 90) {
                if (iU1At == 91) {
                    int iU2At = u2At(i2);
                    int iScanElementValue = i + 3;
                    for (int i3 = 0; i3 < iU2At; i3++) {
                        iScanElementValue = scanElementValue(iScanElementValue);
                    }
                    return iScanElementValue;
                }
                switch (iU1At) {
                    case 66:
                    case 67:
                    case 68:
                        break;
                    default:
                        throw new IllegalStateException();
                }
            }
        }
        return i + 3;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT);
        stringBuffer.append(this.typename);
        if (this.pairs != null) {
            stringBuffer.append("(\n\t");
            int length = this.pairs.length;
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    stringBuffer.append(",\n\t");
                }
                stringBuffer.append(this.pairs[i]);
            }
            stringBuffer.append(')');
        }
        return stringBuffer.toString();
    }

    public int hashCode() {
        return ((Util.hashCode(this.pairs) + 31) * 31) + CharOperation.hashCode(this.typename);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AnnotationInfo annotationInfo = (AnnotationInfo) obj;
        return Arrays.equals(this.pairs, annotationInfo.pairs) && Arrays.equals(this.typename, annotationInfo.typename);
    }
}
