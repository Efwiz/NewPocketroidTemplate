package org.eclipse.jdt.internal.compiler.apt.model;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.type.TypeMirror;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;

/* JADX INFO: loaded from: classes14.dex */
public abstract class ElementImpl implements Element, IElementInfo {
    public final Binding _binding;
    public final BaseProcessingEnvImpl _env;

    protected abstract AnnotationBinding[] getAnnotationBindings();

    @Override // org.eclipse.jdt.internal.compiler.apt.model.IElementInfo
    public String getFileName() {
        return null;
    }

    abstract PackageElement getPackage();

    public boolean hides(Element element) {
        return false;
    }

    protected ElementImpl(BaseProcessingEnvImpl baseProcessingEnvImpl, Binding binding) {
        this._env = baseProcessingEnvImpl;
        this._binding = binding;
    }

    public TypeMirror asType() {
        return this._env.getFactory().newTypeMirror(this._binding);
    }

    public final AnnotationBinding[] getPackedAnnotationBindings() {
        return Factory.getPackedAnnotationBindings(getAnnotationBindings());
    }

    public <A extends Annotation> A getAnnotation(Class<A> cls) {
        A a = (A) this._env.getFactory().getAnnotation(getPackedAnnotationBindings(), cls);
        if (a != null || getKind() != ElementKind.CLASS || cls.getAnnotation(Inherited.class) == null) {
            return a;
        }
        ElementImpl elementImpl = (ElementImpl) this._env.getFactory().newElement(((ReferenceBinding) this._binding).superclass());
        if (elementImpl == null) {
            return null;
        }
        return (A) elementImpl.getAnnotation(cls);
    }

    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return this._env.getFactory().getAnnotationMirrors(getPackedAnnotationBindings());
    }

    public <A extends Annotation> A[] getAnnotationsByType(Class<A> cls) {
        ElementImpl elementImpl;
        A[] aArr = (A[]) this._env.getFactory().getAnnotationsByType(Factory.getUnpackedAnnotationBindings(getPackedAnnotationBindings()), cls);
        return (aArr.length != 0 || getKind() != ElementKind.CLASS || cls.getAnnotation(Inherited.class) == null || (elementImpl = (ElementImpl) this._env.getFactory().newElement(((ReferenceBinding) this._binding).superclass())) == null) ? aArr : (A[]) elementImpl.getAnnotationsByType(cls);
    }

    public Set<Modifier> getModifiers() {
        return Collections.emptySet();
    }

    public Name getSimpleName() {
        return new NameImpl(this._binding.shortReadableName());
    }

    public int hashCode() {
        return this._binding.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ElementImpl elementImpl = (ElementImpl) obj;
        Binding binding = this._binding;
        if (binding == null) {
            if (elementImpl._binding != null) {
                return false;
            }
        } else if (binding != elementImpl._binding) {
            return false;
        }
        return true;
    }

    public String toString() {
        return this._binding.toString();
    }
}
