package org.eclipse.jdt.internal.compiler.classfmt;

import org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation;

/* JADX INFO: loaded from: classes14.dex */
public class MethodInfoWithAnnotations extends MethodInfo {
    protected AnnotationInfo[] annotations;

    MethodInfoWithAnnotations(MethodInfo methodInfo, AnnotationInfo[] annotationInfoArr) {
        super(methodInfo.reference, methodInfo.constantPoolOffsets, methodInfo.structOffset);
        this.annotations = annotationInfoArr;
        this.accessFlags = methodInfo.accessFlags;
        this.attributeBytes = methodInfo.attributeBytes;
        this.descriptor = methodInfo.descriptor;
        this.exceptionNames = methodInfo.exceptionNames;
        this.f622name = methodInfo.f622name;
        this.signature = methodInfo.signature;
        this.signatureUtf8Offset = methodInfo.signatureUtf8Offset;
        this.tagBits = methodInfo.tagBits;
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.MethodInfo, org.eclipse.jdt.internal.compiler.env.IBinaryMethod
    public IBinaryAnnotation[] getAnnotations() {
        return this.annotations;
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.MethodInfo
    protected void initialize() {
        AnnotationInfo[] annotationInfoArr = this.annotations;
        int length = annotationInfoArr == null ? 0 : annotationInfoArr.length;
        for (int i = 0; i < length; i++) {
            AnnotationInfo annotationInfo = this.annotations[i];
            if (annotationInfo != null) {
                annotationInfo.initialize();
            }
        }
        super.initialize();
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.ClassFileStruct
    protected void reset() {
        AnnotationInfo[] annotationInfoArr = this.annotations;
        int length = annotationInfoArr == null ? 0 : annotationInfoArr.length;
        for (int i = 0; i < length; i++) {
            AnnotationInfo annotationInfo = this.annotations[i];
            if (annotationInfo != null) {
                annotationInfo.reset();
            }
        }
        super.reset();
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.MethodInfo
    protected void toStringContent(StringBuffer stringBuffer) {
        super.toStringContent(stringBuffer);
        AnnotationInfo[] annotationInfoArr = this.annotations;
        int length = annotationInfoArr == null ? 0 : annotationInfoArr.length;
        for (int i = 0; i < length; i++) {
            stringBuffer.append(this.annotations[i]);
            stringBuffer.append('\n');
        }
    }
}
