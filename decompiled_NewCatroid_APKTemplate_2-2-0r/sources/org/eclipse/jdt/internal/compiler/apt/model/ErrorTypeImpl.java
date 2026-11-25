package org.eclipse.jdt.internal.compiler.apt.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ErrorTypeImpl extends DeclaredTypeImpl implements ErrorType {
    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    public <A extends Annotation> A getAnnotation(Class<A> cls) {
        return null;
    }

    ErrorTypeImpl(BaseProcessingEnvImpl baseProcessingEnvImpl, ReferenceBinding referenceBinding) {
        super(baseProcessingEnvImpl, referenceBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.DeclaredTypeImpl
    public Element asElement() {
        return this._env.getFactory().newElement((ReferenceBinding) this._binding);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.DeclaredTypeImpl
    public TypeMirror getEnclosingType() {
        return NoTypeImpl.NO_TYPE_NONE;
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.DeclaredTypeImpl
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

    @Override // org.eclipse.jdt.internal.compiler.apt.model.DeclaredTypeImpl, org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        return (R) typeVisitor.visitError(this, p);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return Factory.EMPTY_ANNOTATION_MIRRORS;
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    public <A extends Annotation> A[] getAnnotationsByType(Class<A> cls) {
        return (A[]) ((Annotation[]) Array.newInstance((Class<?>) cls, 0));
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.DeclaredTypeImpl, org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    public TypeKind getKind() {
        return TypeKind.ERROR;
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.DeclaredTypeImpl, org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    public String toString() {
        return new String(this._binding.readableName());
    }
}
