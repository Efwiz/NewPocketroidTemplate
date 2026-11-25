package org.eclipse.jdt.internal.compiler.lookup;

/* JADX INFO: loaded from: classes14.dex */
public class AptBinaryLocalVariableBinding extends LocalVariableBinding {
    AnnotationBinding[] annotationBindings;
    public MethodBinding methodBinding;

    public AptBinaryLocalVariableBinding(char[] cArr, TypeBinding typeBinding, int i, AnnotationBinding[] annotationBindingArr, MethodBinding methodBinding) {
        super(cArr, typeBinding, i, true);
        this.annotationBindings = annotationBindingArr == null ? Binding.NO_ANNOTATIONS : annotationBindingArr;
        this.methodBinding = methodBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding, org.eclipse.jdt.internal.compiler.lookup.VariableBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public AnnotationBinding[] getAnnotations() {
        return this.annotationBindings;
    }
}
