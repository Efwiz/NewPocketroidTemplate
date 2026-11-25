package org.eclipse.jdt.internal.compiler.apt.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class TypeElementImpl extends ElementImpl implements TypeElement {
    private final ElementKind _kindHint;

    private static final class SourceLocationComparator implements Comparator<Element> {
        private static /* synthetic */ int[] $SWITCH_TABLE$javax$lang$model$element$ElementKind;
        private final IdentityHashMap<ElementImpl, Integer> sourceStartCache;

        static /* synthetic */ int[] $SWITCH_TABLE$javax$lang$model$element$ElementKind() {
            int[] iArr = $SWITCH_TABLE$javax$lang$model$element$ElementKind;
            if (iArr != null) {
                return iArr;
            }
            int[] iArr2 = new int[ElementKind.values().length];
            try {
                iArr2[ElementKind.ANNOTATION_TYPE.ordinal()] = 4;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr2[ElementKind.CLASS.ordinal()] = 3;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr2[ElementKind.CONSTRUCTOR.ordinal()] = 12;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[ElementKind.ENUM.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[ElementKind.ENUM_CONSTANT.ordinal()] = 6;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[ElementKind.EXCEPTION_PARAMETER.ordinal()] = 10;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[ElementKind.FIELD.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[ElementKind.INSTANCE_INIT.ordinal()] = 14;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[ElementKind.INTERFACE.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr2[ElementKind.LOCAL_VARIABLE.ordinal()] = 9;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr2[ElementKind.METHOD.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr2[ElementKind.OTHER.ordinal()] = 16;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr2[ElementKind.PACKAGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr2[ElementKind.PARAMETER.ordinal()] = 8;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr2[ElementKind.RESOURCE_VARIABLE.ordinal()] = 17;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr2[ElementKind.STATIC_INIT.ordinal()] = 13;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr2[ElementKind.TYPE_PARAMETER.ordinal()] = 15;
            } catch (NoSuchFieldError unused17) {
            }
            $SWITCH_TABLE$javax$lang$model$element$ElementKind = iArr2;
            return iArr2;
        }

        private SourceLocationComparator() {
            this.sourceStartCache = new IdentityHashMap<>();
        }

        /* synthetic */ SourceLocationComparator(SourceLocationComparator sourceLocationComparator) {
            this();
        }

        @Override // java.util.Comparator
        public int compare(Element element, Element element2) {
            return getSourceStart((ElementImpl) element) - getSourceStart((ElementImpl) element2);
        }

        private int getSourceStart(ElementImpl elementImpl) {
            Integer numValueOf = this.sourceStartCache.get(elementImpl);
            if (numValueOf == null) {
                numValueOf = Integer.valueOf(determineSourceStart(elementImpl));
                this.sourceStartCache.put(elementImpl, numValueOf);
            }
            return numValueOf.intValue();
        }

        private int determineSourceStart(ElementImpl elementImpl) {
            FieldDeclaration fieldDeclarationSourceField;
            int i = $SWITCH_TABLE$javax$lang$model$element$ElementKind()[elementImpl.getKind().ordinal()];
            if (i != 11 && i != 12) {
                switch (i) {
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        Binding binding = ((TypeElementImpl) elementImpl)._binding;
                        if (binding instanceof SourceTypeBinding) {
                            return ((TypeDeclaration) ((SourceTypeBinding) binding).scope.referenceContext()).sourceStart;
                        }
                        return -1;
                    case 6:
                    case 7:
                        Binding binding2 = ((VariableElementImpl) elementImpl)._binding;
                        if (!(binding2 instanceof FieldBinding) || (fieldDeclarationSourceField = ((FieldBinding) binding2).sourceField()) == null) {
                            return -1;
                        }
                        return fieldDeclarationSourceField.sourceStart;
                    default:
                        return -1;
                }
            }
            Binding binding3 = ((ExecutableElementImpl) elementImpl)._binding;
            if (binding3 instanceof MethodBinding) {
                return ((MethodBinding) binding3).sourceStart();
            }
            return -1;
        }
    }

    TypeElementImpl(BaseProcessingEnvImpl baseProcessingEnvImpl, ReferenceBinding referenceBinding, ElementKind elementKind) {
        super(baseProcessingEnvImpl, referenceBinding);
        this._kindHint = elementKind;
    }

    public <R, P> R accept(ElementVisitor<R, P> elementVisitor, P p) {
        return (R) elementVisitor.visitType(this, p);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    protected AnnotationBinding[] getAnnotationBindings() {
        return ((ReferenceBinding) this._binding).getAnnotations();
    }

    public List<? extends Element> getEnclosedElements() {
        ReferenceBinding referenceBinding = (ReferenceBinding) this._binding;
        ArrayList arrayList = new ArrayList(referenceBinding.fieldCount() + referenceBinding.methods().length + referenceBinding.memberTypes().length);
        int i = 0;
        for (MethodBinding methodBinding : referenceBinding.methods()) {
            arrayList.add(new ExecutableElementImpl(this._env, methodBinding));
        }
        for (FieldBinding fieldBinding : referenceBinding.fields()) {
            if (!fieldBinding.isSynthetic()) {
                arrayList.add(new VariableElementImpl(this._env, fieldBinding));
            }
        }
        ReferenceBinding[] referenceBindingArrMemberTypes = referenceBinding.memberTypes();
        int length = referenceBindingArrMemberTypes.length;
        while (true) {
            SourceLocationComparator sourceLocationComparator = null;
            if (i < length) {
                arrayList.add(new TypeElementImpl(this._env, referenceBindingArrMemberTypes[i], null));
                i++;
            } else {
                Collections.sort(arrayList, new SourceLocationComparator(sourceLocationComparator));
                return Collections.unmodifiableList(arrayList);
            }
        }
    }

    public Element getEnclosingElement() {
        ReferenceBinding referenceBinding = (ReferenceBinding) this._binding;
        if (referenceBinding.enclosingType() == null) {
            return this._env.getFactory().newPackageElement(referenceBinding.fPackage);
        }
        return this._env.getFactory().newElement(referenceBinding.enclosingType());
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl, org.eclipse.jdt.internal.compiler.apt.model.IElementInfo
    public String getFileName() {
        char[] fileName = ((ReferenceBinding) this._binding).getFileName();
        if (fileName == null) {
            return null;
        }
        return new String(fileName);
    }

    public List<? extends TypeMirror> getInterfaces() {
        ReferenceBinding referenceBinding = (ReferenceBinding) this._binding;
        if (referenceBinding.superInterfaces() == null || referenceBinding.superInterfaces().length == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(referenceBinding.superInterfaces().length);
        for (ReferenceBinding referenceBinding2 : referenceBinding.superInterfaces()) {
            TypeMirror typeMirrorNewTypeMirror = this._env.getFactory().newTypeMirror(referenceBinding2);
            if (typeMirrorNewTypeMirror.getKind() == TypeKind.ERROR) {
                if (this._env.getSourceVersion().compareTo(SourceVersion.RELEASE_6) > 0) {
                    arrayList.add(typeMirrorNewTypeMirror);
                }
            } else {
                arrayList.add(typeMirrorNewTypeMirror);
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public ElementKind getKind() {
        ElementKind elementKind = this._kindHint;
        if (elementKind != null) {
            return elementKind;
        }
        ReferenceBinding referenceBinding = (ReferenceBinding) this._binding;
        if (referenceBinding.isEnum()) {
            return ElementKind.ENUM;
        }
        if (referenceBinding.isAnnotationType()) {
            return ElementKind.ANNOTATION_TYPE;
        }
        if (referenceBinding.isInterface()) {
            return ElementKind.INTERFACE;
        }
        if (referenceBinding.isClass()) {
            return ElementKind.CLASS;
        }
        throw new IllegalArgumentException("TypeElement " + new String(referenceBinding.shortReadableName()) + " has unexpected attributes " + referenceBinding.modifiers);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public Set<Modifier> getModifiers() {
        ReferenceBinding referenceBinding = (ReferenceBinding) this._binding;
        int i = referenceBinding.modifiers;
        if (referenceBinding.isInterface() && referenceBinding.isNestedType()) {
            i |= 8;
        }
        return Factory.getModifiers(i, getKind(), referenceBinding.isBinaryBinding());
    }

    public NestingKind getNestingKind() {
        ReferenceBinding referenceBinding = (ReferenceBinding) this._binding;
        if (referenceBinding.isAnonymousType()) {
            return NestingKind.ANONYMOUS;
        }
        if (referenceBinding.isLocalType()) {
            return NestingKind.LOCAL;
        }
        if (referenceBinding.isMemberType()) {
            return NestingKind.MEMBER;
        }
        return NestingKind.TOP_LEVEL;
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    PackageElement getPackage() {
        return this._env.getFactory().newPackageElement(((ReferenceBinding) this._binding).fPackage);
    }

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

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public Name getSimpleName() {
        return new NameImpl(((ReferenceBinding) this._binding).sourceName());
    }

    public TypeMirror getSuperclass() {
        ReferenceBinding referenceBinding = (ReferenceBinding) this._binding;
        ReferenceBinding referenceBindingSuperclass = referenceBinding.superclass();
        if (referenceBindingSuperclass == null || referenceBinding.isInterface()) {
            return this._env.getFactory().getNoType(TypeKind.NONE);
        }
        return this._env.getFactory().newTypeMirror(referenceBindingSuperclass);
    }

    public List<? extends TypeParameterElement> getTypeParameters() {
        TypeVariableBinding[] typeVariableBindingArrTypeVariables = ((ReferenceBinding) this._binding).typeVariables();
        if (typeVariableBindingArrTypeVariables.length == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(typeVariableBindingArrTypeVariables.length);
        for (TypeVariableBinding typeVariableBinding : typeVariableBindingArrTypeVariables) {
            arrayList.add(this._env.getFactory().newTypeParameterElement(typeVariableBinding, this));
        }
        return Collections.unmodifiableList(arrayList);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public boolean hides(Element element) {
        if (!(element instanceof TypeElementImpl)) {
            return false;
        }
        ReferenceBinding referenceBinding = (ReferenceBinding) ((TypeElementImpl) element)._binding;
        if (referenceBinding.isPrivate()) {
            return false;
        }
        ReferenceBinding referenceBinding2 = (ReferenceBinding) this._binding;
        return !TypeBinding.equalsEquals(referenceBinding, referenceBinding2) && referenceBinding.isMemberType() && referenceBinding2.isMemberType() && CharOperation.equals(referenceBinding.sourceName, referenceBinding2.sourceName) && referenceBinding2.enclosingType().findSuperTypeOriginatingFrom(referenceBinding.enclosingType()) != null;
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public String toString() {
        ReferenceBinding referenceBinding = (ReferenceBinding) this._binding;
        char[] cArrConcatWith = CharOperation.concatWith(referenceBinding.compoundName, '.');
        if (referenceBinding.isNestedType()) {
            CharOperation.replace(cArrConcatWith, '$', '.');
            return new String(cArrConcatWith);
        }
        return new String(cArrConcatWith);
    }
}
