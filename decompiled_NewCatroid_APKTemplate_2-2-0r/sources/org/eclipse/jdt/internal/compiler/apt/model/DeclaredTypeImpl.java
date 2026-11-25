package org.eclipse.jdt.internal.compiler.apt.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class DeclaredTypeImpl extends TypeMirrorImpl implements DeclaredType {
    private final ElementKind _elementKindHint;

    DeclaredTypeImpl(BaseProcessingEnvImpl baseProcessingEnvImpl, ReferenceBinding referenceBinding) {
        super(baseProcessingEnvImpl, referenceBinding);
        this._elementKindHint = null;
    }

    DeclaredTypeImpl(BaseProcessingEnvImpl baseProcessingEnvImpl, ReferenceBinding referenceBinding, ElementKind elementKind) {
        super(baseProcessingEnvImpl, referenceBinding);
        this._elementKindHint = elementKind;
    }

    public Element asElement() {
        return this._env.getFactory().newElement((ReferenceBinding) this._binding, this._elementKindHint);
    }

    public TypeMirror getEnclosingType() {
        ReferenceBinding referenceBindingEnclosingType = ((ReferenceBinding) this._binding).enclosingType();
        if (referenceBindingEnclosingType != null) {
            return this._env.getFactory().newTypeMirror(referenceBindingEnclosingType);
        }
        return this._env.getFactory().getNoType(TypeKind.NONE);
    }

    public List<? extends TypeMirror> getTypeArguments() {
        ReferenceBinding referenceBinding = (ReferenceBinding) this._binding;
        int i = 0;
        if (referenceBinding.isParameterizedType()) {
            TypeBinding[] typeBindingArr = ((ParameterizedTypeBinding) this._binding).arguments;
            int length = typeBindingArr == null ? 0 : typeBindingArr.length;
            if (length == 0) {
                return Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList(length);
            int length2 = typeBindingArr.length;
            while (i < length2) {
                arrayList.add(this._env.getFactory().newTypeMirror(typeBindingArr[i]));
                i++;
            }
            return Collections.unmodifiableList(arrayList);
        }
        if (referenceBinding.isGenericType()) {
            TypeVariableBinding[] typeVariableBindingArrTypeVariables = referenceBinding.typeVariables();
            ArrayList arrayList2 = new ArrayList(typeVariableBindingArrTypeVariables.length);
            int length3 = typeVariableBindingArrTypeVariables.length;
            while (i < length3) {
                arrayList2.add(this._env.getFactory().newTypeMirror(typeVariableBindingArrTypeVariables[i]));
                i++;
            }
            return Collections.unmodifiableList(arrayList2);
        }
        return Collections.emptyList();
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        return (R) typeVisitor.visitDeclared(this, p);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    public TypeKind getKind() {
        return TypeKind.DECLARED;
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    public String toString() {
        return new String(this._binding.readableName());
    }
}
