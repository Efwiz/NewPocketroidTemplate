package org.eclipse.jdt.internal.compiler.codegen;

import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.ast.UnionTypeReference;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class MultiCatchExceptionLabel extends ExceptionLabel {
    ExceptionLabel[] exceptionLabels;

    public MultiCatchExceptionLabel(CodeStream codeStream, TypeBinding typeBinding) {
        super(codeStream, typeBinding);
    }

    public void initialize(UnionTypeReference unionTypeReference, Annotation[] annotationArr) {
        TypeReference[] typeReferenceArr = unionTypeReference.typeReferences;
        int length = typeReferenceArr.length;
        this.exceptionLabels = new ExceptionLabel[length];
        int i = 0;
        while (i < length) {
            this.exceptionLabels[i] = new ExceptionLabel(this.codeStream, typeReferenceArr[i].resolvedType, typeReferenceArr[i], i == 0 ? annotationArr : null);
            i++;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.ExceptionLabel, org.eclipse.jdt.internal.compiler.codegen.Label
    public void place() {
        int length = this.exceptionLabels.length;
        for (int i = 0; i < length; i++) {
            this.exceptionLabels[i].place();
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.ExceptionLabel
    public void placeEnd() {
        int length = this.exceptionLabels.length;
        for (int i = 0; i < length; i++) {
            this.exceptionLabels[i].placeEnd();
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.ExceptionLabel
    public void placeStart() {
        int length = this.exceptionLabels.length;
        for (int i = 0; i < length; i++) {
            this.exceptionLabels[i].placeStart();
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.ExceptionLabel
    public int getCount() {
        int length = this.exceptionLabels.length;
        int count = 0;
        for (int i = 0; i < length; i++) {
            count += this.exceptionLabels[i].getCount();
        }
        return count;
    }
}
