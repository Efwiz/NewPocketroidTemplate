package org.eclipse.jdt.internal.compiler.apt.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class TypeMirrorImpl implements TypeMirror {
    protected final Binding _binding;
    protected final BaseProcessingEnvImpl _env;

    TypeMirrorImpl(BaseProcessingEnvImpl baseProcessingEnvImpl, Binding binding) {
        this._env = baseProcessingEnvImpl;
        this._binding = binding;
    }

    Binding binding() {
        return this._binding;
    }

    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        return (R) typeVisitor.visit(this, p);
    }

    public TypeKind getKind() {
        int iKind = this._binding.kind();
        if (iKind == 1 || iKind == 2 || iKind == 3 || iKind == 32) {
            throw new IllegalArgumentException("Invalid binding kind: " + this._binding.kind());
        }
        return null;
    }

    public String toString() {
        return new String(this._binding.readableName());
    }

    public int hashCode() {
        Binding binding = this._binding;
        return 31 + (binding == null ? 0 : binding.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof TypeMirrorImpl) && this._binding == ((TypeMirrorImpl) obj)._binding;
    }

    public final AnnotationBinding[] getPackedAnnotationBindings() {
        return Factory.getPackedAnnotationBindings(getAnnotationBindings());
    }

    protected AnnotationBinding[] getAnnotationBindings() {
        return ((TypeBinding) this._binding).getTypeAnnotations();
    }

    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        BaseProcessingEnvImpl baseProcessingEnvImpl = this._env;
        return baseProcessingEnvImpl == null ? Factory.EMPTY_ANNOTATION_MIRRORS : baseProcessingEnvImpl.getFactory().getAnnotationMirrors(getPackedAnnotationBindings());
    }

    public <A extends Annotation> A getAnnotation(Class<A> cls) {
        BaseProcessingEnvImpl baseProcessingEnvImpl = this._env;
        if (baseProcessingEnvImpl == null) {
            return null;
        }
        return (A) baseProcessingEnvImpl.getFactory().getAnnotation(getPackedAnnotationBindings(), cls);
    }

    public <A extends Annotation> A[] getAnnotationsByType(Class<A> cls) {
        BaseProcessingEnvImpl baseProcessingEnvImpl = this._env;
        if (baseProcessingEnvImpl == null) {
            return (A[]) ((Annotation[]) Array.newInstance((Class<?>) cls, 0));
        }
        return (A[]) baseProcessingEnvImpl.getFactory().getAnnotationsByType(Factory.getUnpackedAnnotationBindings(getPackedAnnotationBindings()), cls);
    }
}
