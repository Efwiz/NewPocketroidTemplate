package org.eclipse.jdt.internal.compiler.apt.model;

import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;

/* JADX INFO: loaded from: classes14.dex */
public class ArrayTypeImpl extends TypeMirrorImpl implements ArrayType {
    ArrayTypeImpl(BaseProcessingEnvImpl baseProcessingEnvImpl, ArrayBinding arrayBinding) {
        super(baseProcessingEnvImpl, arrayBinding);
    }

    public TypeMirror getComponentType() {
        return this._env.getFactory().newTypeMirror(((ArrayBinding) this._binding).elementsType());
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        return (R) typeVisitor.visitArray(this, p);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    protected AnnotationBinding[] getAnnotationBindings() {
        AnnotationBinding[] typeAnnotations = ((ArrayBinding) this._binding).getTypeAnnotations();
        AnnotationBinding[] annotationBindingArr = Binding.NO_ANNOTATIONS;
        int length = typeAnnotations == null ? 0 : typeAnnotations.length;
        for (int i = 0; i < length; i++) {
            if (typeAnnotations[i] == null) {
                AnnotationBinding[] annotationBindingArr2 = new AnnotationBinding[i];
                System.arraycopy(typeAnnotations, 0, annotationBindingArr2, 0, i);
                return annotationBindingArr2;
            }
        }
        return annotationBindingArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    public TypeKind getKind() {
        return TypeKind.ARRAY;
    }
}
