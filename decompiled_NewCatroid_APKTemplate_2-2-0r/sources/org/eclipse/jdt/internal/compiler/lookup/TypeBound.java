package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.core.compiler.CharOperation;

/* JADX INFO: loaded from: classes14.dex */
public class TypeBound extends ReductionResult {
    boolean isSoft;
    InferenceVariable left;
    long nullHints;

    static TypeBound createBoundOrDependency(InferenceSubstitution inferenceSubstitution, TypeBinding typeBinding, InferenceVariable inferenceVariable) {
        return new TypeBound(inferenceVariable, inferenceSubstitution.substitute(inferenceSubstitution, typeBinding), 2, true);
    }

    TypeBound(InferenceVariable inferenceVariable, TypeBinding typeBinding, int i) {
        this(inferenceVariable, typeBinding, i, false);
    }

    TypeBound(InferenceVariable inferenceVariable, TypeBinding typeBinding, int i, boolean z) {
        this.left = inferenceVariable;
        this.right = safeType(typeBinding);
        long j = inferenceVariable.tagBits | this.right.tagBits;
        long j2 = TagBits.AnnotationNullMASK;
        if ((j & TagBits.AnnotationNullMASK) != 0) {
            if ((inferenceVariable.tagBits & TagBits.AnnotationNullMASK) == (this.right.tagBits & TagBits.AnnotationNullMASK)) {
                this.left = (InferenceVariable) inferenceVariable.withoutToplevelNullAnnotation();
                this.right = this.right.withoutToplevelNullAnnotation();
            } else {
                if (i == 2) {
                    j2 = 72057594037927936L;
                } else if (i == 3) {
                    j2 = 36028797018963968L;
                } else if (i != 4) {
                    j2 = 0;
                }
                inferenceVariable.prototype().nullHints |= j2 & this.right.tagBits;
            }
        }
        this.relation = i;
        this.isSoft = z;
    }

    private TypeBinding safeType(TypeBinding typeBinding) {
        MethodBinding methodBinding;
        return (typeBinding == null || !typeBinding.isLocalType() || (methodBinding = ((LocalTypeBinding) typeBinding.original()).enclosingMethod) == null || !CharOperation.prefixEquals(TypeConstants.ANONYMOUS_METHOD, methodBinding.selector)) ? typeBinding : typeBinding.superclass();
    }

    boolean isBound() {
        return this.right.isProperType(true);
    }

    public int hashCode() {
        return this.left.hashCode() + this.right.hashCode() + this.relation;
    }

    public boolean equals(Object obj) {
        if (obj instanceof TypeBound) {
            TypeBound typeBound = (TypeBound) obj;
            if (this.relation == typeBound.relation && TypeBinding.equalsEquals(this.left, typeBound.left) && TypeBinding.equalsEquals(this.right, typeBound.right)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        boolean zIsProperType = this.right.isProperType(true);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(zIsProperType ? "TypeBound  " : "Dependency ");
        stringBuffer.append(this.left.sourceName);
        stringBuffer.append(relationToString(this.relation));
        stringBuffer.append(this.right.readableName());
        return stringBuffer.toString();
    }
}
