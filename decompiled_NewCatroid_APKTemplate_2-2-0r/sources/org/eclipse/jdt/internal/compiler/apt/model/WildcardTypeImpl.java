package org.eclipse.jdt.internal.compiler.apt.model;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import javax.lang.model.type.WildcardType;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.WildcardBinding;

/* JADX INFO: loaded from: classes14.dex */
public class WildcardTypeImpl extends TypeMirrorImpl implements WildcardType {
    WildcardTypeImpl(BaseProcessingEnvImpl baseProcessingEnvImpl, WildcardBinding wildcardBinding) {
        super(baseProcessingEnvImpl, wildcardBinding);
    }

    public TypeMirror getExtendsBound() {
        TypeBinding typeBinding;
        WildcardBinding wildcardBinding = (WildcardBinding) this._binding;
        if (wildcardBinding.boundKind == 1 && (typeBinding = wildcardBinding.bound) != null) {
            return this._env.getFactory().newTypeMirror(typeBinding);
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    public TypeKind getKind() {
        return TypeKind.WILDCARD;
    }

    public TypeMirror getSuperBound() {
        TypeBinding typeBinding;
        WildcardBinding wildcardBinding = (WildcardBinding) this._binding;
        if (wildcardBinding.boundKind == 2 && (typeBinding = wildcardBinding.bound) != null) {
            return this._env.getFactory().newTypeMirror(typeBinding);
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        return (R) typeVisitor.visitWildcard(this, p);
    }
}
