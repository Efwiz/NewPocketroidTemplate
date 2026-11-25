package org.eclipse.jdt.internal.compiler.classfmt;

import org.eclipse.jdt.internal.compiler.env.IBinaryTypeAnnotation;

/* JADX INFO: loaded from: classes14.dex */
class MethodInfoWithTypeAnnotations extends MethodInfoWithParameterAnnotations {
    private TypeAnnotationInfo[] typeAnnotations;

    MethodInfoWithTypeAnnotations(MethodInfo methodInfo, AnnotationInfo[] annotationInfoArr, AnnotationInfo[][] annotationInfoArr2, TypeAnnotationInfo[] typeAnnotationInfoArr) {
        super(methodInfo, annotationInfoArr, annotationInfoArr2);
        this.typeAnnotations = typeAnnotationInfoArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.MethodInfo, org.eclipse.jdt.internal.compiler.env.IBinaryMethod
    public IBinaryTypeAnnotation[] getTypeAnnotations() {
        return this.typeAnnotations;
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.MethodInfoWithParameterAnnotations, org.eclipse.jdt.internal.compiler.classfmt.MethodInfoWithAnnotations, org.eclipse.jdt.internal.compiler.classfmt.MethodInfo
    protected void initialize() {
        TypeAnnotationInfo[] typeAnnotationInfoArr = this.typeAnnotations;
        int length = typeAnnotationInfoArr == null ? 0 : typeAnnotationInfoArr.length;
        for (int i = 0; i < length; i++) {
            this.typeAnnotations[i].initialize();
        }
        super.initialize();
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.MethodInfoWithParameterAnnotations, org.eclipse.jdt.internal.compiler.classfmt.MethodInfoWithAnnotations, org.eclipse.jdt.internal.compiler.classfmt.ClassFileStruct
    protected void reset() {
        TypeAnnotationInfo[] typeAnnotationInfoArr = this.typeAnnotations;
        int length = typeAnnotationInfoArr == null ? 0 : typeAnnotationInfoArr.length;
        for (int i = 0; i < length; i++) {
            this.typeAnnotations[i].reset();
        }
        super.reset();
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.MethodInfoWithParameterAnnotations, org.eclipse.jdt.internal.compiler.classfmt.MethodInfoWithAnnotations, org.eclipse.jdt.internal.compiler.classfmt.MethodInfo
    protected void toStringContent(StringBuffer stringBuffer) {
        super.toStringContent(stringBuffer);
        stringBuffer.append("type annotations = \n");
        TypeAnnotationInfo[] typeAnnotationInfoArr = this.typeAnnotations;
        int length = typeAnnotationInfoArr == null ? 0 : typeAnnotationInfoArr.length;
        for (int i = 0; i < length; i++) {
            stringBuffer.append(this.typeAnnotations[i].toString());
            stringBuffer.append('\n');
        }
    }
}
