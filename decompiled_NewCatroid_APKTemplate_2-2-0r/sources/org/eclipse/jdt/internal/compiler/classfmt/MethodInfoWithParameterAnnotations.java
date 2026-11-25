package org.eclipse.jdt.internal.compiler.classfmt;

import org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation;

/* JADX INFO: loaded from: classes14.dex */
class MethodInfoWithParameterAnnotations extends MethodInfoWithAnnotations {
    private AnnotationInfo[][] parameterAnnotations;

    MethodInfoWithParameterAnnotations(MethodInfo methodInfo, AnnotationInfo[] annotationInfoArr, AnnotationInfo[][] annotationInfoArr2) {
        super(methodInfo, annotationInfoArr);
        this.parameterAnnotations = annotationInfoArr2;
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.MethodInfo, org.eclipse.jdt.internal.compiler.env.IBinaryMethod
    public IBinaryAnnotation[] getParameterAnnotations(int i, char[] cArr) {
        try {
            AnnotationInfo[][] annotationInfoArr = this.parameterAnnotations;
            if (annotationInfoArr == null) {
                return null;
            }
            return annotationInfoArr[i];
        } catch (ArrayIndexOutOfBoundsException e) {
            StringBuffer stringBuffer = new StringBuffer("Mismatching number of parameter annotations, ");
            stringBuffer.append(i);
            stringBuffer.append('>');
            stringBuffer.append(this.parameterAnnotations.length - 1);
            stringBuffer.append(" in ");
            stringBuffer.append(getSelector());
            char[] genericSignature = getGenericSignature();
            if (genericSignature != null) {
                stringBuffer.append(genericSignature);
            } else {
                stringBuffer.append(getMethodDescriptor());
            }
            if (cArr != null) {
                stringBuffer.append(" in ").append(cArr);
            }
            throw new IllegalStateException(stringBuffer.toString(), e);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.MethodInfo, org.eclipse.jdt.internal.compiler.env.IBinaryMethod
    public int getAnnotatedParametersCount() {
        AnnotationInfo[][] annotationInfoArr = this.parameterAnnotations;
        if (annotationInfoArr == null) {
            return 0;
        }
        return annotationInfoArr.length;
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.MethodInfoWithAnnotations, org.eclipse.jdt.internal.compiler.classfmt.MethodInfo
    protected void initialize() {
        AnnotationInfo[][] annotationInfoArr = this.parameterAnnotations;
        int length = annotationInfoArr == null ? 0 : annotationInfoArr.length;
        for (int i = 0; i < length; i++) {
            AnnotationInfo[] annotationInfoArr2 = this.parameterAnnotations[i];
            int length2 = annotationInfoArr2 == null ? 0 : annotationInfoArr2.length;
            for (int i2 = 0; i2 < length2; i2++) {
                annotationInfoArr2[i2].initialize();
            }
        }
        super.initialize();
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.MethodInfoWithAnnotations, org.eclipse.jdt.internal.compiler.classfmt.ClassFileStruct
    protected void reset() {
        AnnotationInfo[][] annotationInfoArr = this.parameterAnnotations;
        int length = annotationInfoArr == null ? 0 : annotationInfoArr.length;
        for (int i = 0; i < length; i++) {
            AnnotationInfo[] annotationInfoArr2 = this.parameterAnnotations[i];
            int length2 = annotationInfoArr2 == null ? 0 : annotationInfoArr2.length;
            for (int i2 = 0; i2 < length2; i2++) {
                annotationInfoArr2[i2].reset();
            }
        }
        super.reset();
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.MethodInfoWithAnnotations, org.eclipse.jdt.internal.compiler.classfmt.MethodInfo
    protected void toStringContent(StringBuffer stringBuffer) {
        super.toStringContent(stringBuffer);
        AnnotationInfo[][] annotationInfoArr = this.parameterAnnotations;
        int length = annotationInfoArr == null ? 0 : annotationInfoArr.length;
        for (int i = 0; i < length; i++) {
            stringBuffer.append("param" + (i - 1));
            stringBuffer.append('\n');
            AnnotationInfo[] annotationInfoArr2 = this.parameterAnnotations[i];
            int length2 = annotationInfoArr2 == null ? 0 : annotationInfoArr2.length;
            for (int i2 = 0; i2 < length2; i2++) {
                stringBuffer.append(annotationInfoArr2[i2]);
                stringBuffer.append('\n');
            }
        }
    }
}
