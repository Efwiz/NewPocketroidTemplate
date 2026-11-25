package org.eclipse.jdt.internal.compiler.lookup;

/* JADX INFO: loaded from: classes14.dex */
public class PolymorphicMethodBinding extends MethodBinding {
    protected MethodBinding polymorphicMethod;

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    public boolean isPolymorphic() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    public boolean isVarargs() {
        return false;
    }

    public PolymorphicMethodBinding(MethodBinding methodBinding, TypeBinding[] typeBindingArr) {
        super(methodBinding.modifiers, methodBinding.selector, methodBinding.returnType, typeBindingArr, methodBinding.thrownExceptions, methodBinding.declaringClass);
        this.polymorphicMethod = methodBinding;
        this.tagBits = methodBinding.tagBits;
    }

    public PolymorphicMethodBinding(MethodBinding methodBinding, TypeBinding typeBinding, TypeBinding[] typeBindingArr) {
        super(methodBinding.modifiers, methodBinding.selector, typeBinding, typeBindingArr, methodBinding.thrownExceptions, methodBinding.declaringClass);
        this.polymorphicMethod = methodBinding;
        this.tagBits = methodBinding.tagBits;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    public MethodBinding original() {
        return this.polymorphicMethod;
    }

    public boolean matches(TypeBinding[] typeBindingArr, TypeBinding typeBinding) {
        int length = this.parameters == null ? 0 : this.parameters.length;
        if ((typeBindingArr == null ? 0 : typeBindingArr.length) != length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (TypeBinding.notEquals(this.parameters[i], typeBindingArr[i])) {
                return false;
            }
        }
        TypeBinding typeBinding2 = this.returnType;
        if (typeBinding == null) {
            return typeBinding2 == null;
        }
        return (typeBinding2 == null || TypeBinding.notEquals(typeBinding, typeBinding2)) ? false : true;
    }
}
