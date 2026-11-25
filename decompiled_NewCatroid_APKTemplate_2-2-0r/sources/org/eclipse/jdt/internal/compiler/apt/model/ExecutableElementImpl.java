package org.eclipse.jdt.internal.compiler.apt.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import net.bytebuddy.description.method.ParameterDescription;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Argument;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationHolder;
import org.eclipse.jdt.internal.compiler.lookup.AptBinaryLocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodVerifier;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ExecutableElementImpl extends ElementImpl implements ExecutableElement {
    private Name _name;

    ExecutableElementImpl(BaseProcessingEnvImpl baseProcessingEnvImpl, MethodBinding methodBinding) {
        super(baseProcessingEnvImpl, methodBinding);
        this._name = null;
    }

    public <R, P> R accept(ElementVisitor<R, P> elementVisitor, P p) {
        return (R) elementVisitor.visitExecutable(this, p);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    protected AnnotationBinding[] getAnnotationBindings() {
        return ((MethodBinding) this._binding).getAnnotations();
    }

    public AnnotationValue getDefaultValue() {
        MethodBinding methodBinding = (MethodBinding) this._binding;
        Object defaultValue = methodBinding.getDefaultValue();
        if (defaultValue != null) {
            return new AnnotationMemberValue(this._env, defaultValue, methodBinding);
        }
        return null;
    }

    public List<? extends Element> getEnclosedElements() {
        return Collections.emptyList();
    }

    public Element getEnclosingElement() {
        MethodBinding methodBinding = (MethodBinding) this._binding;
        if (methodBinding.declaringClass == null) {
            return null;
        }
        return this._env.getFactory().newElement(methodBinding.declaringClass);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl, org.eclipse.jdt.internal.compiler.apt.model.IElementInfo
    public String getFileName() {
        char[] fileName = ((MethodBinding) this._binding).declaringClass.getFileName();
        if (fileName == null) {
            return null;
        }
        return new String(fileName);
    }

    public ElementKind getKind() {
        MethodBinding methodBinding = (MethodBinding) this._binding;
        if (methodBinding.isConstructor()) {
            return ElementKind.CONSTRUCTOR;
        }
        if (CharOperation.equals(methodBinding.selector, TypeConstants.CLINIT)) {
            return ElementKind.STATIC_INIT;
        }
        if (CharOperation.equals(methodBinding.selector, TypeConstants.INIT)) {
            return ElementKind.INSTANCE_INIT;
        }
        return ElementKind.METHOD;
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public Set<Modifier> getModifiers() {
        return Factory.getModifiers(((MethodBinding) this._binding).modifiers, getKind());
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    PackageElement getPackage() {
        MethodBinding methodBinding = (MethodBinding) this._binding;
        if (methodBinding.declaringClass == null) {
            return null;
        }
        return this._env.getFactory().newPackageElement(methodBinding.declaringClass.fPackage);
    }

    public List<? extends VariableElement> getParameters() {
        ExecutableElementImpl executableElementImpl = this;
        MethodBinding methodBinding = (MethodBinding) executableElementImpl._binding;
        int length = methodBinding.parameters == null ? 0 : methodBinding.parameters.length;
        if (length != 0) {
            AbstractMethodDeclaration abstractMethodDeclarationSourceMethod = methodBinding.sourceMethod();
            ArrayList arrayList = new ArrayList(length);
            if (abstractMethodDeclarationSourceMethod != null) {
                for (Argument argument : abstractMethodDeclarationSourceMethod.arguments) {
                    arrayList.add(new VariableElementImpl(executableElementImpl._env, argument.binding));
                }
            } else {
                AnnotationHolder annotationHolderRetrieveAnnotationHolder = methodBinding.declaringClass.retrieveAnnotationHolder(methodBinding, false);
                AnnotationBinding[][] parameterAnnotations = annotationHolderRetrieveAnnotationHolder != null ? annotationHolderRetrieveAnnotationHolder.getParameterAnnotations() : null;
                TypeBinding[] typeBindingArr = methodBinding.parameters;
                int length2 = typeBindingArr.length;
                int i = 0;
                int i2 = 0;
                while (i < length2) {
                    TypeBinding typeBinding = typeBindingArr[i];
                    char[] charArray = methodBinding.parameterNames.length > i2 ? methodBinding.parameterNames[i2] : null;
                    if (charArray == null) {
                        StringBuilder sb = new StringBuilder(ParameterDescription.NAME_PREFIX);
                        sb.append(i2);
                        charArray = String.valueOf(sb).toCharArray();
                    }
                    arrayList.add(new VariableElementImpl(executableElementImpl._env, new AptBinaryLocalVariableBinding(charArray, typeBinding, 0, parameterAnnotations != null ? parameterAnnotations[i2] : null, methodBinding)));
                    i2++;
                    i++;
                    executableElementImpl = this;
                }
            }
            return Collections.unmodifiableList(arrayList);
        }
        return Collections.emptyList();
    }

    public TypeMirror getReturnType() {
        MethodBinding methodBinding = (MethodBinding) this._binding;
        if (methodBinding.returnType == null) {
            return null;
        }
        return this._env.getFactory().newTypeMirror(methodBinding.returnType);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public Name getSimpleName() {
        MethodBinding methodBinding = (MethodBinding) this._binding;
        if (this._name == null) {
            this._name = new NameImpl(methodBinding.selector);
        }
        return this._name;
    }

    public List<? extends TypeMirror> getThrownTypes() {
        MethodBinding methodBinding = (MethodBinding) this._binding;
        if (methodBinding.thrownExceptions.length == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(methodBinding.thrownExceptions.length);
        for (ReferenceBinding referenceBinding : methodBinding.thrownExceptions) {
            arrayList.add(this._env.getFactory().newTypeMirror(referenceBinding));
        }
        return arrayList;
    }

    public List<? extends TypeParameterElement> getTypeParameters() {
        TypeVariableBinding[] typeVariableBindingArrTypeVariables = ((MethodBinding) this._binding).typeVariables();
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
        MethodBinding methodBinding;
        MethodBinding methodBinding2;
        return (element instanceof ExecutableElementImpl) && (methodBinding = (MethodBinding) this._binding) != (methodBinding2 = (MethodBinding) ((ExecutableElementImpl) element)._binding) && !methodBinding2.isPrivate() && methodBinding.isStatic() && methodBinding2.isStatic() && CharOperation.equals(methodBinding2.selector, methodBinding.selector) && this._env.getLookupEnvironment().methodVerifier().isMethodSubsignature(methodBinding, methodBinding2) && methodBinding.declaringClass.findSuperTypeOriginatingFrom(methodBinding2.declaringClass) != null;
    }

    public boolean isVarArgs() {
        return ((MethodBinding) this._binding).isVarargs();
    }

    public boolean overrides(ExecutableElement executableElement, TypeElement typeElement) {
        MethodBinding methodBinding = (MethodBinding) ((ExecutableElementImpl) executableElement)._binding;
        ReferenceBinding referenceBinding = (ReferenceBinding) ((TypeElementImpl) typeElement)._binding;
        if (((MethodBinding) this._binding) == methodBinding || methodBinding.isStatic() || methodBinding.isPrivate() || ((MethodBinding) this._binding).isStatic()) {
            return false;
        }
        char[] cArr = ((MethodBinding) this._binding).selector;
        if (!CharOperation.equals(cArr, methodBinding.selector)) {
            return false;
        }
        if (referenceBinding.findSuperTypeOriginatingFrom(((MethodBinding) this._binding).declaringClass) == null && ((MethodBinding) this._binding).declaringClass.findSuperTypeOriginatingFrom(referenceBinding) == null) {
            return false;
        }
        MethodBinding methodBinding2 = new MethodBinding((MethodBinding) this._binding, referenceBinding);
        if (methodBinding2.isPrivate()) {
            return false;
        }
        TypeBinding typeBindingFindSuperTypeOriginatingFrom = methodBinding2.declaringClass.findSuperTypeOriginatingFrom(methodBinding.declaringClass);
        if (!(typeBindingFindSuperTypeOriginatingFrom instanceof ReferenceBinding)) {
            return false;
        }
        MethodBinding[] methods = ((ReferenceBinding) typeBindingFindSuperTypeOriginatingFrom).getMethods(cArr);
        LookupEnvironment lookupEnvironment = this._env.getLookupEnvironment();
        if (lookupEnvironment == null) {
            return false;
        }
        MethodVerifier methodVerifier = lookupEnvironment.methodVerifier();
        int length = methods.length;
        for (int i = 0; i < length; i++) {
            if (methods[i].original() == methodBinding) {
                return methodVerifier.doesMethodOverride(methodBinding2, methods[i]);
            }
        }
        return false;
    }

    public TypeMirror getReceiverType() {
        return this._env.getFactory().getReceiverType((MethodBinding) this._binding);
    }

    public boolean isDefault() {
        if (this._binding != null) {
            return ((MethodBinding) this._binding).isDefaultMethod();
        }
        return false;
    }
}
