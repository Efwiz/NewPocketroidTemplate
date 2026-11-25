package org.eclipse.jdt.internal.compiler.apt.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ErrorTypeElement extends TypeElementImpl {
    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public <A extends Annotation> A getAnnotation(Class<A> cls) {
        return null;
    }

    ErrorTypeElement(BaseProcessingEnvImpl baseProcessingEnvImpl, ReferenceBinding referenceBinding) {
        super(baseProcessingEnvImpl, referenceBinding, null);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeElementImpl
    public List<? extends TypeMirror> getInterfaces() {
        return Collections.emptyList();
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeElementImpl
    public NestingKind getNestingKind() {
        return NestingKind.TOP_LEVEL;
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeElementImpl
    public Name getQualifiedName() {
        char[] cArrConcatWith;
        ReferenceBinding referenceBinding = (ReferenceBinding) this._binding;
        if (referenceBinding.isMemberType()) {
            cArrConcatWith = CharOperation.concatWith(referenceBinding.enclosingType().compoundName, referenceBinding.sourceName, '.');
            CharOperation.replace(cArrConcatWith, '$', '.');
        } else {
            cArrConcatWith = CharOperation.concatWith(referenceBinding.compoundName, '.');
        }
        return new NameImpl(cArrConcatWith);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeElementImpl
    public TypeMirror getSuperclass() {
        return this._env.getFactory().getNoType(TypeKind.NONE);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeElementImpl
    public List<? extends TypeParameterElement> getTypeParameters() {
        return Collections.emptyList();
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public TypeMirror asType() {
        return this._env.getFactory().getErrorType((ReferenceBinding) this._binding);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return Collections.emptyList();
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public <A extends Annotation> A[] getAnnotationsByType(Class<A> cls) {
        return (A[]) ((Annotation[]) Array.newInstance((Class<?>) cls, 0));
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeElementImpl
    public List<? extends Element> getEnclosedElements() {
        return Collections.emptyList();
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeElementImpl
    public Element getEnclosingElement() {
        return this._env.getFactory().newPackageElement(this._env.getLookupEnvironment().defaultPackage);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeElementImpl
    public ElementKind getKind() {
        return ElementKind.CLASS;
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeElementImpl, org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public Set<Modifier> getModifiers() {
        return Collections.emptySet();
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeElementImpl, org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public Name getSimpleName() {
        return new NameImpl(((ReferenceBinding) this._binding).sourceName());
    }
}
