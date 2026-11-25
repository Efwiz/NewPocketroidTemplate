package org.eclipse.jdt.internal.compiler.apt.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.TypeVisitor;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ExecutableTypeImpl extends TypeMirrorImpl implements ExecutableType {
    ExecutableTypeImpl(BaseProcessingEnvImpl baseProcessingEnvImpl, MethodBinding methodBinding) {
        super(baseProcessingEnvImpl, methodBinding);
    }

    public List<? extends TypeMirror> getParameterTypes() {
        MethodBinding methodBinding = (MethodBinding) this._binding;
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        int length = typeBindingArr.length;
        if (methodBinding.isConstructor() && methodBinding.declaringClass.isEnum() && methodBinding.declaringClass.isBinaryBinding() && (methodBinding.modifiers & 1073741824) == 0) {
            if (length == 2) {
                return Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 2; i < length; i++) {
                arrayList.add(this._env.getFactory().newTypeMirror(typeBindingArr[i]));
            }
            return Collections.unmodifiableList(arrayList);
        }
        if (length != 0) {
            ArrayList arrayList2 = new ArrayList();
            for (TypeBinding typeBinding : typeBindingArr) {
                arrayList2.add(this._env.getFactory().newTypeMirror(typeBinding));
            }
            return Collections.unmodifiableList(arrayList2);
        }
        return Collections.emptyList();
    }

    public TypeMirror getReturnType() {
        return this._env.getFactory().newTypeMirror(((MethodBinding) this._binding).returnType);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    protected AnnotationBinding[] getAnnotationBindings() {
        return ((MethodBinding) this._binding).returnType.getTypeAnnotations();
    }

    public List<? extends TypeMirror> getThrownTypes() {
        ArrayList arrayList = new ArrayList();
        ReferenceBinding[] referenceBindingArr = ((MethodBinding) this._binding).thrownExceptions;
        if (referenceBindingArr.length != 0) {
            for (ReferenceBinding referenceBinding : referenceBindingArr) {
                arrayList.add(this._env.getFactory().newTypeMirror(referenceBinding));
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public List<? extends TypeVariable> getTypeVariables() {
        ArrayList arrayList = new ArrayList();
        TypeVariableBinding[] typeVariableBindingArrTypeVariables = ((MethodBinding) this._binding).typeVariables();
        if (typeVariableBindingArrTypeVariables.length != 0) {
            for (TypeVariableBinding typeVariableBinding : typeVariableBindingArrTypeVariables) {
                arrayList.add(this._env.getFactory().newTypeMirror(typeVariableBinding));
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        return (R) typeVisitor.visitExecutable(this, p);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    public TypeKind getKind() {
        return TypeKind.EXECUTABLE;
    }

    public TypeMirror getReceiverType() {
        return this._env.getFactory().getReceiverType((MethodBinding) this._binding);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    public String toString() {
        return new String(((MethodBinding) this._binding).returnType.readableName());
    }
}
