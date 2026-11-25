package org.eclipse.jdt.internal.compiler.apt.dispatch;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import org.eclipse.jdt.internal.compiler.apt.model.Factory;
import org.eclipse.jdt.internal.compiler.apt.model.TypeElementImpl;
import org.eclipse.jdt.internal.compiler.apt.util.ManyToMany;
import org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class RoundEnvImpl implements RoundEnvironment {
    private final ManyToMany<TypeElement, Element> _annoToUnit;
    private final ReferenceBinding[] _binaryTypes;
    private final Factory _factory;
    private final boolean _isLastRound;
    private final BaseProcessingEnvImpl _processingEnv;
    private Set<Element> _rootElements = null;
    private final CompilationUnitDeclaration[] _units;

    public RoundEnvImpl(CompilationUnitDeclaration[] compilationUnitDeclarationArr, ReferenceBinding[] referenceBindingArr, boolean z, BaseProcessingEnvImpl baseProcessingEnvImpl) {
        this._processingEnv = baseProcessingEnvImpl;
        this._isLastRound = z;
        this._units = compilationUnitDeclarationArr;
        this._factory = baseProcessingEnvImpl.getFactory();
        AnnotationDiscoveryVisitor annotationDiscoveryVisitor = new AnnotationDiscoveryVisitor(baseProcessingEnvImpl);
        if (compilationUnitDeclarationArr != null) {
            for (CompilationUnitDeclaration compilationUnitDeclaration : compilationUnitDeclarationArr) {
                compilationUnitDeclaration.scope.suppressImportErrors = true;
                compilationUnitDeclaration.traverse(annotationDiscoveryVisitor, compilationUnitDeclaration.scope);
                compilationUnitDeclaration.scope.suppressImportErrors = false;
            }
        }
        this._annoToUnit = annotationDiscoveryVisitor._annoToElement;
        if (referenceBindingArr != null) {
            collectAnnotations(referenceBindingArr);
        }
        this._binaryTypes = referenceBindingArr;
    }

    private void collectAnnotations(ReferenceBinding[] referenceBindingArr) {
        for (ReferenceBinding referenceBindingGenericType : referenceBindingArr) {
            if (referenceBindingGenericType instanceof ParameterizedTypeBinding) {
                referenceBindingGenericType = ((ParameterizedTypeBinding) referenceBindingGenericType).genericType();
            }
            for (AnnotationBinding annotationBinding : Factory.getPackedAnnotationBindings(referenceBindingGenericType.getAnnotations())) {
                this._annoToUnit.put(this._factory.newElement(annotationBinding.getAnnotationType()), this._factory.newElement(referenceBindingGenericType));
            }
            for (FieldBinding fieldBinding : referenceBindingGenericType.fields()) {
                for (AnnotationBinding annotationBinding2 : Factory.getPackedAnnotationBindings(fieldBinding.getAnnotations())) {
                    this._annoToUnit.put(this._factory.newElement(annotationBinding2.getAnnotationType()), this._factory.newElement(fieldBinding));
                }
            }
            for (MethodBinding methodBinding : referenceBindingGenericType.methods()) {
                for (AnnotationBinding annotationBinding3 : Factory.getPackedAnnotationBindings(methodBinding.getAnnotations())) {
                    this._annoToUnit.put(this._factory.newElement(annotationBinding3.getAnnotationType()), this._factory.newElement(methodBinding));
                }
            }
            collectAnnotations(referenceBindingGenericType.memberTypes());
        }
    }

    public Set<TypeElement> getRootAnnotations() {
        return Collections.unmodifiableSet(this._annoToUnit.getKeySet());
    }

    public boolean errorRaised() {
        return this._processingEnv.errorRaised();
    }

    public Set<? extends Element> getElementsAnnotatedWith(TypeElement typeElement) {
        if (typeElement.getKind() != ElementKind.ANNOTATION_TYPE) {
            throw new IllegalArgumentException("Argument must represent an annotation type");
        }
        Binding binding = ((TypeElementImpl) typeElement)._binding;
        if (0 != (binding.getAnnotationTagBits() & 281474976710656L)) {
            HashSet hashSet = new HashSet(this._annoToUnit.getValues(typeElement));
            ReferenceBinding referenceBinding = (ReferenceBinding) binding;
            Iterator it = ElementFilter.typesIn(getRootElements()).iterator();
            while (it.hasNext()) {
                addAnnotatedElements(referenceBinding, (ReferenceBinding) ((TypeElementImpl) ((TypeElement) it.next()))._binding, hashSet);
            }
            return Collections.unmodifiableSet(hashSet);
        }
        return Collections.unmodifiableSet(this._annoToUnit.getValues(typeElement));
    }

    private void addAnnotatedElements(ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2, Set<Element> set) {
        if (referenceBinding2.isClass() && inheritsAnno(referenceBinding2, referenceBinding)) {
            set.add(this._factory.newElement(referenceBinding2));
        }
        for (ReferenceBinding referenceBinding3 : referenceBinding2.memberTypes()) {
            addAnnotatedElements(referenceBinding, referenceBinding3, set);
        }
    }

    private boolean inheritsAnno(ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2) {
        do {
            if (referenceBinding instanceof ParameterizedTypeBinding) {
                referenceBinding = ((ParameterizedTypeBinding) referenceBinding).genericType();
            }
            for (AnnotationBinding annotationBinding : Factory.getPackedAnnotationBindings(referenceBinding.getAnnotations())) {
                if (annotationBinding.getAnnotationType() == referenceBinding2) {
                    return true;
                }
            }
            referenceBinding = referenceBinding.superclass();
        } while (referenceBinding != null);
        return false;
    }

    public Set<? extends Element> getElementsAnnotatedWith(Class<? extends Annotation> cls) {
        String canonicalName = cls.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Argument must represent an annotation type");
        }
        TypeElement typeElement = this._processingEnv.getElementUtils().getTypeElement(canonicalName);
        if (typeElement == null) {
            return Collections.emptySet();
        }
        return getElementsAnnotatedWith(typeElement);
    }

    public Set<? extends Element> getRootElements() {
        if (this._units == null) {
            return Collections.emptySet();
        }
        if (this._rootElements == null) {
            HashSet hashSet = new HashSet(this._units.length);
            for (CompilationUnitDeclaration compilationUnitDeclaration : this._units) {
                if (compilationUnitDeclaration.scope != null && compilationUnitDeclaration.scope.topLevelTypes != null) {
                    for (SourceTypeBinding sourceTypeBinding : compilationUnitDeclaration.scope.topLevelTypes) {
                        Element elementNewElement = this._factory.newElement(sourceTypeBinding);
                        if (elementNewElement == null) {
                            throw new IllegalArgumentException("Top-level type binding could not be converted to element: " + sourceTypeBinding);
                        }
                        hashSet.add(elementNewElement);
                    }
                }
            }
            ReferenceBinding[] referenceBindingArr = this._binaryTypes;
            if (referenceBindingArr != null) {
                for (ReferenceBinding referenceBinding : referenceBindingArr) {
                    Element elementNewElement2 = this._factory.newElement(referenceBinding);
                    if (elementNewElement2 == null) {
                        throw new IllegalArgumentException("Top-level type binding could not be converted to element: " + referenceBinding);
                    }
                    hashSet.add(elementNewElement2);
                }
            }
            this._rootElements = hashSet;
        }
        return this._rootElements;
    }

    public boolean processingOver() {
        return this._isLastRound;
    }
}
