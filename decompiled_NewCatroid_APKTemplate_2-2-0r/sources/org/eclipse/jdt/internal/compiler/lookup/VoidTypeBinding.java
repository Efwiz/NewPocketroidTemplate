package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class VoidTypeBinding extends BaseTypeBinding {
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

    VoidTypeBinding() {
        super(6, TypeConstants.VOID, new char[]{Util.C_VOID});
    }
}
