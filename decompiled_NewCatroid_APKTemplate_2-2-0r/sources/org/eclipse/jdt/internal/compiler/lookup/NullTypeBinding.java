package org.eclipse.jdt.internal.compiler.lookup;

/* JADX INFO: loaded from: classes14.dex */
public class NullTypeBinding extends BaseTypeBinding {
    @Override // org.eclipse.jdt.internal.compiler.lookup.BaseTypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding clone(TypeBinding typeBinding) {
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.BaseTypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public void setTypeAnnotations(AnnotationBinding[] annotationBindingArr, boolean z) {
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.BaseTypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding unannotated() {
        return this;
    }

    NullTypeBinding() {
        super(12, TypeConstants.NULL, new char[]{'N'});
    }
}
