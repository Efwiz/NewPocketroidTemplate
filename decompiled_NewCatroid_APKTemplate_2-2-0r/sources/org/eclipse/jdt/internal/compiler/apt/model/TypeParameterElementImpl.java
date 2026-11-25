package org.eclipse.jdt.internal.compiler.apt.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class TypeParameterElementImpl extends ElementImpl implements TypeParameterElement {
    private List<? extends TypeMirror> _bounds;
    private final Element _declaringElement;

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    PackageElement getPackage() {
        return null;
    }

    TypeParameterElementImpl(BaseProcessingEnvImpl baseProcessingEnvImpl, TypeVariableBinding typeVariableBinding, Element element) {
        super(baseProcessingEnvImpl, typeVariableBinding);
        this._bounds = null;
        this._declaringElement = element;
    }

    TypeParameterElementImpl(BaseProcessingEnvImpl baseProcessingEnvImpl, TypeVariableBinding typeVariableBinding) {
        super(baseProcessingEnvImpl, typeVariableBinding);
        this._bounds = null;
        this._declaringElement = this._env.getFactory().newElement(typeVariableBinding.declaringElement);
    }

    public List<? extends TypeMirror> getBounds() {
        if (this._bounds == null) {
            this._bounds = calculateBounds();
        }
        return this._bounds;
    }

    private List<? extends TypeMirror> calculateBounds() {
        boolean zIsTypeVariable;
        int i;
        int length;
        TypeVariableBinding typeVariableBinding = (TypeVariableBinding) this._binding;
        ReferenceBinding referenceBindingSuperclass = typeVariableBinding.superclass();
        TypeBinding typeBinding = typeVariableBinding.firstBound;
        if (typeBinding != null) {
            zIsTypeVariable = typeBinding.isTypeVariable();
            i = 1;
            if (TypeBinding.equalsEquals(typeBinding, referenceBindingSuperclass)) {
                if (typeBinding.isTypeVariable()) {
                    zIsTypeVariable = true;
                }
            } else if (!typeBinding.isArrayType()) {
                typeBinding = null;
                i = 0;
            }
        } else {
            zIsTypeVariable = false;
            i = 0;
        }
        ReferenceBinding[] referenceBindingArrSuperInterfaces = typeVariableBinding.superInterfaces();
        if (referenceBindingArrSuperInterfaces != null) {
            length = referenceBindingArrSuperInterfaces.length;
            i += length;
        } else {
            length = 0;
        }
        ArrayList arrayList = new ArrayList(i);
        if (i != 0) {
            if (typeBinding != null) {
                TypeMirror typeMirrorNewTypeMirror = this._env.getFactory().newTypeMirror(typeBinding);
                if (typeMirrorNewTypeMirror == null) {
                    return Collections.emptyList();
                }
                arrayList.add(typeMirrorNewTypeMirror);
            }
            if (referenceBindingArrSuperInterfaces != null && !zIsTypeVariable) {
                for (int i2 = 0; i2 < length; i2++) {
                    TypeMirror typeMirrorNewTypeMirror2 = this._env.getFactory().newTypeMirror(referenceBindingArrSuperInterfaces[i2]);
                    if (typeMirrorNewTypeMirror2 == null) {
                        return Collections.emptyList();
                    }
                    arrayList.add(typeMirrorNewTypeMirror2);
                }
            }
        } else {
            arrayList.add(this._env.getFactory().newTypeMirror(this._env.getLookupEnvironment().getType(LookupEnvironment.JAVA_LANG_OBJECT)));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public Element getGenericElement() {
        return this._declaringElement;
    }

    public <R, P> R accept(ElementVisitor<R, P> elementVisitor, P p) {
        return (R) elementVisitor.visitTypeParameter(this, p);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    protected AnnotationBinding[] getAnnotationBindings() {
        return ((TypeVariableBinding) this._binding).getTypeAnnotations();
    }

    private boolean shouldEmulateJavacBug() {
        if (this._env.getLookupEnvironment().globalOptions.emulateJavacBug8031744) {
            AnnotationBinding[] annotationBindings = getAnnotationBindings();
            int length = annotationBindings.length;
            int i = 0;
            while (i < length) {
                ReferenceBinding annotationType = annotationBindings[i].getAnnotationType();
                i++;
                for (int i2 = i; i2 < length; i2++) {
                    if (annotationType == annotationBindings[i2].getAnnotationType()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        if (shouldEmulateJavacBug()) {
            return Collections.emptyList();
        }
        return super.getAnnotationMirrors();
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public <A extends Annotation> A[] getAnnotationsByType(Class<A> cls) {
        if (shouldEmulateJavacBug()) {
            return (A[]) ((Annotation[]) Array.newInstance((Class<?>) cls, 0));
        }
        return (A[]) super.getAnnotationsByType(cls);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public <A extends Annotation> A getAnnotation(Class<A> cls) {
        if (shouldEmulateJavacBug()) {
            return null;
        }
        return (A) super.getAnnotation(cls);
    }

    public List<? extends Element> getEnclosedElements() {
        return Collections.emptyList();
    }

    public Element getEnclosingElement() {
        return getGenericElement();
    }

    public ElementKind getKind() {
        return ElementKind.TYPE_PARAMETER;
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public String toString() {
        return new String(this._binding.readableName());
    }
}
