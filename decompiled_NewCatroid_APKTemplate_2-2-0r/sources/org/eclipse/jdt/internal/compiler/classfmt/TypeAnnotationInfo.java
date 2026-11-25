package org.eclipse.jdt.internal.compiler.classfmt;

import java.util.Arrays;
import org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation;
import org.eclipse.jdt.internal.compiler.env.IBinaryTypeAnnotation;

/* JADX INFO: loaded from: classes14.dex */
public class TypeAnnotationInfo extends ClassFileStruct implements IBinaryTypeAnnotation {
    private AnnotationInfo annotation;
    private int info;
    private int info2;
    int readOffset;
    private int targetType;
    private int[] typePath;

    TypeAnnotationInfo(byte[] bArr, int[] iArr, int i) {
        super(bArr, iArr, i);
        this.targetType = 0;
        this.readOffset = 0;
    }

    TypeAnnotationInfo(byte[] bArr, int[] iArr, int i, boolean z, boolean z2) {
        this(bArr, iArr, i);
        this.readOffset = 0;
        int iU1At = u1At(0);
        this.targetType = iU1At;
        if (iU1At == 0 || iU1At == 1) {
            this.info = u1At(1);
            this.readOffset += 2;
        } else {
            switch (iU1At) {
                case 16:
                    this.info = u2At(1);
                    this.readOffset += 3;
                    break;
                case 17:
                case 18:
                    this.info = u1At(1);
                    this.info2 = u1At(2);
                    this.readOffset += 3;
                    break;
                case 19:
                case 20:
                case 21:
                    this.readOffset++;
                    break;
                case 22:
                    this.info = u1At(1);
                    this.readOffset += 2;
                    break;
                case 23:
                    this.info = u2At(1);
                    this.readOffset += 3;
                    break;
                default:
                    throw new IllegalStateException("Target type not handled " + this.targetType);
            }
        }
        int iU1At2 = u1At(this.readOffset);
        this.readOffset++;
        if (iU1At2 == 0) {
            this.typePath = NO_TYPE_PATH;
        } else {
            this.typePath = new int[iU1At2 * 2];
            int i2 = 0;
            for (int i3 = 0; i3 < iU1At2; i3++) {
                int[] iArr2 = this.typePath;
                int i4 = i2 + 1;
                int i5 = this.readOffset;
                this.readOffset = i5 + 1;
                iArr2[i2] = u1At(i5);
                int[] iArr3 = this.typePath;
                i2 += 2;
                int i6 = this.readOffset;
                this.readOffset = i6 + 1;
                iArr3[i4] = u1At(i6);
            }
        }
        AnnotationInfo annotationInfo = new AnnotationInfo(bArr, this.constantPoolOffsets, this.structOffset + this.readOffset, z, z2);
        this.annotation = annotationInfo;
        this.readOffset += annotationInfo.readOffset;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryTypeAnnotation
    public IBinaryAnnotation getAnnotation() {
        return this.annotation;
    }

    protected void initialize() {
        this.annotation.initialize();
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.ClassFileStruct
    protected void reset() {
        this.annotation.reset();
        super.reset();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.annotation);
        stringBuffer.append(" target_type=");
        stringBuffer.append(this.targetType);
        stringBuffer.append(", info=").append(this.info);
        stringBuffer.append(", info2=").append(this.info2);
        if (this.typePath != NO_TYPE_PATH) {
            stringBuffer.append(", location=[");
            int length = this.typePath.length;
            for (int i = 0; i < length; i += 2) {
                if (i > 0) {
                    stringBuffer.append(", ");
                }
                int i2 = this.typePath[i];
                if (i2 == 0) {
                    stringBuffer.append("ARRAY");
                } else if (i2 == 1) {
                    stringBuffer.append("INNER_TYPE");
                } else if (i2 == 2) {
                    stringBuffer.append("WILDCARD");
                } else if (i2 == 3) {
                    stringBuffer.append("TYPE_ARGUMENT(").append(this.typePath[i + 1]).append(')');
                }
            }
            stringBuffer.append(']');
        }
        return stringBuffer.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryTypeAnnotation
    public int getTargetType() {
        return this.targetType;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryTypeAnnotation
    public int getSupertypeIndex() {
        return this.info;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryTypeAnnotation
    public int getTypeParameterIndex() {
        return this.info;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryTypeAnnotation
    public int getBoundIndex() {
        return this.info2;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryTypeAnnotation
    public int getMethodFormalParameterIndex() {
        return this.info;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryTypeAnnotation
    public int getThrowsTypeIndex() {
        return this.info;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryTypeAnnotation
    public int[] getTypePath() {
        return this.typePath;
    }

    public int hashCode() {
        int i = ((((this.targetType + 31) * 31) + this.info) * 31) + this.info2;
        int[] iArr = this.typePath;
        if (iArr != null) {
            int length = iArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                i = (i * 31) + this.typePath[i2];
            }
        }
        return i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TypeAnnotationInfo typeAnnotationInfo = (TypeAnnotationInfo) obj;
        if (this.targetType == typeAnnotationInfo.targetType && this.info == typeAnnotationInfo.info && this.info2 == typeAnnotationInfo.info2 && Arrays.equals(this.typePath, typeAnnotationInfo.typePath)) {
            return this.annotation.equals(typeAnnotationInfo.annotation);
        }
        return false;
    }
}
