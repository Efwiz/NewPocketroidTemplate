package org.eclipse.jdt.internal.compiler.lookup;

/* JADX INFO: loaded from: classes14.dex */
public class AptSourceLocalVariableBinding extends LocalVariableBinding {
    private LocalVariableBinding local;
    public MethodBinding methodBinding;

    public AptSourceLocalVariableBinding(LocalVariableBinding localVariableBinding, MethodBinding methodBinding) {
        super(localVariableBinding.f628name, localVariableBinding.type, localVariableBinding.modifiers, true);
        this.constant = localVariableBinding.constant;
        this.declaration = localVariableBinding.declaration;
        this.declaringScope = localVariableBinding.declaringScope;
        this.f627id = localVariableBinding.f627id;
        this.resolvedPosition = localVariableBinding.resolvedPosition;
        this.tagBits = localVariableBinding.tagBits;
        this.useFlag = localVariableBinding.useFlag;
        this.initializationCount = localVariableBinding.initializationCount;
        this.initializationPCs = localVariableBinding.initializationPCs;
        this.methodBinding = methodBinding;
        this.local = localVariableBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding, org.eclipse.jdt.internal.compiler.lookup.VariableBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public AnnotationBinding[] getAnnotations() {
        return this.local.getAnnotations();
    }
}
