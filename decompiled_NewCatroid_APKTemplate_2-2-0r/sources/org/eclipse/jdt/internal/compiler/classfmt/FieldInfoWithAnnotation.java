package org.eclipse.jdt.internal.compiler.classfmt;

import org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation;

/* JADX INFO: loaded from: classes14.dex */
public class FieldInfoWithAnnotation extends FieldInfo {
    private AnnotationInfo[] annotations;

    FieldInfoWithAnnotation(FieldInfo fieldInfo, AnnotationInfo[] annotationInfoArr) {
        super(fieldInfo.reference, fieldInfo.constantPoolOffsets, fieldInfo.structOffset);
        this.accessFlags = fieldInfo.accessFlags;
        this.attributeBytes = fieldInfo.attributeBytes;
        this.constant = fieldInfo.constant;
        this.constantPoolOffsets = fieldInfo.constantPoolOffsets;
        this.descriptor = fieldInfo.descriptor;
        this.f621name = fieldInfo.f621name;
        this.signature = fieldInfo.signature;
        this.signatureUtf8Offset = fieldInfo.signatureUtf8Offset;
        this.tagBits = fieldInfo.tagBits;
        this.wrappedConstantValue = fieldInfo.wrappedConstantValue;
        this.annotations = annotationInfoArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.FieldInfo, org.eclipse.jdt.internal.compiler.env.IBinaryField
    public IBinaryAnnotation[] getAnnotations() {
        return this.annotations;
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.FieldInfo
    protected void initialize() {
        AnnotationInfo[] annotationInfoArr = this.annotations;
        if (annotationInfoArr != null) {
            int length = annotationInfoArr.length;
            for (int i = 0; i < length; i++) {
                this.annotations[i].initialize();
            }
        }
        super.initialize();
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.ClassFileStruct
    protected void reset() {
        AnnotationInfo[] annotationInfoArr = this.annotations;
        if (annotationInfoArr != null) {
            int length = annotationInfoArr.length;
            for (int i = 0; i < length; i++) {
                this.annotations[i].reset();
            }
        }
        super.reset();
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.FieldInfo
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(getClass().getName());
        if (this.annotations != null) {
            stringBuffer.append('\n');
            int i = 0;
            while (true) {
                AnnotationInfo[] annotationInfoArr = this.annotations;
                if (i >= annotationInfoArr.length) {
                    break;
                }
                stringBuffer.append(annotationInfoArr[i]);
                stringBuffer.append('\n');
                i++;
            }
        }
        toStringContent(stringBuffer);
        return stringBuffer.toString();
    }
}
