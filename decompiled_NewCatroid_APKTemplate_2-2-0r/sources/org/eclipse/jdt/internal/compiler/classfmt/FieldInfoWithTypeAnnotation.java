package org.eclipse.jdt.internal.compiler.classfmt;

import org.eclipse.jdt.internal.compiler.env.IBinaryTypeAnnotation;

/* JADX INFO: loaded from: classes14.dex */
public final class FieldInfoWithTypeAnnotation extends FieldInfoWithAnnotation {
    private TypeAnnotationInfo[] typeAnnotations;

    FieldInfoWithTypeAnnotation(FieldInfo fieldInfo, AnnotationInfo[] annotationInfoArr, TypeAnnotationInfo[] typeAnnotationInfoArr) {
        super(fieldInfo, annotationInfoArr);
        this.typeAnnotations = typeAnnotationInfoArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.FieldInfo, org.eclipse.jdt.internal.compiler.env.IBinaryField
    public IBinaryTypeAnnotation[] getTypeAnnotations() {
        return this.typeAnnotations;
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.FieldInfoWithAnnotation, org.eclipse.jdt.internal.compiler.classfmt.FieldInfo
    protected void initialize() {
        int length = this.typeAnnotations.length;
        for (int i = 0; i < length; i++) {
            this.typeAnnotations[i].initialize();
        }
        super.initialize();
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.FieldInfoWithAnnotation, org.eclipse.jdt.internal.compiler.classfmt.ClassFileStruct
    protected void reset() {
        TypeAnnotationInfo[] typeAnnotationInfoArr = this.typeAnnotations;
        if (typeAnnotationInfoArr != null) {
            int length = typeAnnotationInfoArr.length;
            for (int i = 0; i < length; i++) {
                this.typeAnnotations[i].reset();
            }
        }
        super.reset();
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.FieldInfoWithAnnotation, org.eclipse.jdt.internal.compiler.classfmt.FieldInfo
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(getClass().getName());
        if (this.typeAnnotations != null) {
            stringBuffer.append("\ntype annotations:");
            int i = 0;
            while (true) {
                TypeAnnotationInfo[] typeAnnotationInfoArr = this.typeAnnotations;
                if (i >= typeAnnotationInfoArr.length) {
                    break;
                }
                stringBuffer.append(typeAnnotationInfoArr[i]);
                stringBuffer.append('\n');
                i++;
            }
        }
        toStringContent(stringBuffer);
        return stringBuffer.toString();
    }
}
