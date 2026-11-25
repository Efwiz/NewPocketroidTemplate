package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.internal.compiler.ast.LocalDeclaration;

/* JADX INFO: loaded from: classes14.dex */
public class CatchParameterBinding extends LocalVariableBinding {
    TypeBinding[] preciseTypes;

    @Override // org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding
    public boolean isCatchParameter() {
        return true;
    }

    public CatchParameterBinding(LocalDeclaration localDeclaration, TypeBinding typeBinding, int i, boolean z) {
        super(localDeclaration, typeBinding, i, z);
        this.preciseTypes = Binding.NO_EXCEPTIONS;
    }

    public TypeBinding[] getPreciseTypes() {
        return this.preciseTypes;
    }

    public void setPreciseType(TypeBinding typeBinding) {
        int length = this.preciseTypes.length;
        for (int i = 0; i < length; i++) {
            if (TypeBinding.equalsEquals(this.preciseTypes[i], typeBinding)) {
                return;
            }
        }
        TypeBinding[] typeBindingArr = this.preciseTypes;
        TypeBinding[] typeBindingArr2 = new TypeBinding[length + 1];
        this.preciseTypes = typeBindingArr2;
        System.arraycopy(typeBindingArr, 0, typeBindingArr2, 0, length);
        this.preciseTypes[length] = typeBinding;
    }
}
