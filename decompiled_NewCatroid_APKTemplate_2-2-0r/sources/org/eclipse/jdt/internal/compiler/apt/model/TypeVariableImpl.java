package org.eclipse.jdt.internal.compiler.apt.model;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.TypeVisitor;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class TypeVariableImpl extends TypeMirrorImpl implements TypeVariable {
    TypeVariableImpl(BaseProcessingEnvImpl baseProcessingEnvImpl, TypeVariableBinding typeVariableBinding) {
        super(baseProcessingEnvImpl, typeVariableBinding);
    }

    public Element asElement() {
        return this._env.getFactory().newElement(this._binding);
    }

    public TypeMirror getLowerBound() {
        return this._env.getFactory().getNullType();
    }

    public TypeMirror getUpperBound() {
        TypeVariableBinding typeVariableBinding = (TypeVariableBinding) this._binding;
        TypeBinding typeBinding = typeVariableBinding.firstBound;
        ReferenceBinding[] referenceBindingArr = typeVariableBinding.superInterfaces;
        if (typeBinding == null || referenceBindingArr.length == 0) {
            return this._env.getFactory().newTypeMirror(typeVariableBinding.upperBound());
        }
        if (typeBinding != null && referenceBindingArr.length == 1 && TypeBinding.equalsEquals(referenceBindingArr[0], typeBinding)) {
            return this._env.getFactory().newTypeMirror(typeVariableBinding.upperBound());
        }
        return this._env.getFactory().newTypeMirror((TypeVariableBinding) this._binding);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        return (R) typeVisitor.visitTypeVariable(this, p);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    public TypeKind getKind() {
        return TypeKind.TYPEVAR;
    }
}
