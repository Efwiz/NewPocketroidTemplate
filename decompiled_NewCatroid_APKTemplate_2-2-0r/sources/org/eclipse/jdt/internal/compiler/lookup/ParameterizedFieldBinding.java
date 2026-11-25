package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.internal.compiler.impl.Constant;

/* JADX INFO: loaded from: classes14.dex */
public class ParameterizedFieldBinding extends FieldBinding {
    public FieldBinding originalField;

    /* JADX WARN: Illegal instructions before constructor call */
    public ParameterizedFieldBinding(ParameterizedTypeBinding parameterizedTypeBinding, FieldBinding fieldBinding) {
        TypeBinding typeBindingSubstitute;
        TypeBinding typeBinding;
        char[] cArr = fieldBinding.f628name;
        if ((fieldBinding.modifiers & 16384) != 0) {
            typeBinding = parameterizedTypeBinding;
        } else {
            if ((fieldBinding.modifiers & 8) != 0) {
                typeBindingSubstitute = fieldBinding.type;
            } else {
                typeBindingSubstitute = Scope.substitute(parameterizedTypeBinding, fieldBinding.type);
            }
            typeBinding = typeBindingSubstitute;
        }
        super(cArr, typeBinding, fieldBinding.modifiers, parameterizedTypeBinding, null);
        this.originalField = fieldBinding;
        this.tagBits = fieldBinding.tagBits;
        this.f627id = fieldBinding.f627id;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.FieldBinding, org.eclipse.jdt.internal.compiler.lookup.VariableBinding
    public Constant constant() {
        return this.originalField.constant();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.FieldBinding
    public FieldBinding original() {
        return this.originalField.original();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.VariableBinding
    public void setConstant(Constant constant) {
        this.originalField.setConstant(constant);
    }
}
