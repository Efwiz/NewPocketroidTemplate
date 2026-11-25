package org.eclipse.jdt.internal.compiler.apt.dispatch;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.apt.model.ElementImpl;
import org.eclipse.jdt.internal.compiler.apt.model.Factory;
import org.eclipse.jdt.internal.compiler.apt.util.ManyToMany;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.Argument;
import org.eclipse.jdt.internal.compiler.ast.ConstructorDeclaration;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeParameter;
import org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.AptSourceLocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.CompilationUnitScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class AnnotationDiscoveryVisitor extends ASTVisitor {
    final ManyToMany<TypeElement, Element> _annoToElement = new ManyToMany<>();
    final BaseProcessingEnvImpl _env;
    final Factory _factory;

    public AnnotationDiscoveryVisitor(BaseProcessingEnvImpl baseProcessingEnvImpl) {
        this._env = baseProcessingEnvImpl;
        this._factory = baseProcessingEnvImpl.getFactory();
    }

    @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
    public boolean visit(Argument argument, BlockScope blockScope) {
        Annotation[] annotationArr = argument.annotations;
        ReferenceContext referenceContext = blockScope.referenceContext();
        if (!(referenceContext instanceof AbstractMethodDeclaration)) {
            return false;
        }
        MethodBinding methodBinding = ((AbstractMethodDeclaration) referenceContext).binding;
        if (methodBinding != null) {
            blockScope.referenceType().binding.resolveTypesFor(methodBinding);
            if (argument.binding != null) {
                argument.binding = new AptSourceLocalVariableBinding(argument.binding, methodBinding);
            }
        }
        if (annotationArr == null || argument.binding == null) {
            return false;
        }
        resolveAnnotations(blockScope, annotationArr, argument.binding);
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
    public boolean visit(ConstructorDeclaration constructorDeclaration, ClassScope classScope) {
        Annotation[] annotationArr = constructorDeclaration.annotations;
        if (annotationArr != null) {
            MethodBinding methodBinding = constructorDeclaration.binding;
            if (methodBinding == null) {
                return false;
            }
            ((SourceTypeBinding) methodBinding.declaringClass).resolveTypesFor(methodBinding);
            resolveAnnotations(constructorDeclaration.scope, annotationArr, methodBinding);
        }
        TypeParameter[] typeParameterArr = constructorDeclaration.typeParameters;
        if (typeParameterArr != null) {
            for (TypeParameter typeParameter : typeParameterArr) {
                typeParameter.traverse(this, constructorDeclaration.scope);
            }
        }
        Argument[] argumentArr = constructorDeclaration.arguments;
        if (argumentArr != null) {
            for (Argument argument : argumentArr) {
                argument.traverse(this, constructorDeclaration.scope);
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
    public boolean visit(FieldDeclaration fieldDeclaration, MethodScope methodScope) {
        FieldBinding fieldBinding;
        Annotation[] annotationArr = fieldDeclaration.annotations;
        if (annotationArr == null || (fieldBinding = fieldDeclaration.binding) == null) {
            return false;
        }
        ((SourceTypeBinding) fieldBinding.declaringClass).resolveTypeFor(fieldBinding);
        resolveAnnotations(methodScope, annotationArr, fieldBinding);
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
    public boolean visit(TypeParameter typeParameter, ClassScope classScope) {
        TypeVariableBinding typeVariableBinding;
        Annotation[] annotationArr = typeParameter.annotations;
        if (annotationArr == null || (typeVariableBinding = typeParameter.binding) == null) {
            return false;
        }
        resolveAnnotations(classScope.referenceContext.initializerScope, annotationArr, typeVariableBinding);
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
    public boolean visit(TypeParameter typeParameter, BlockScope blockScope) {
        TypeVariableBinding typeVariableBinding;
        Annotation[] annotationArr = typeParameter.annotations;
        if (annotationArr == null || (typeVariableBinding = typeParameter.binding) == null) {
            return false;
        }
        MethodBinding methodBinding = (MethodBinding) typeVariableBinding.declaringElement;
        ((SourceTypeBinding) methodBinding.declaringClass).resolveTypesFor(methodBinding);
        resolveAnnotations(blockScope, annotationArr, typeVariableBinding);
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
    public boolean visit(MethodDeclaration methodDeclaration, ClassScope classScope) {
        Annotation[] annotationArr = methodDeclaration.annotations;
        if (annotationArr != null) {
            MethodBinding methodBinding = methodDeclaration.binding;
            if (methodBinding == null) {
                return false;
            }
            ((SourceTypeBinding) methodBinding.declaringClass).resolveTypesFor(methodBinding);
            resolveAnnotations(methodDeclaration.scope, annotationArr, methodBinding);
        }
        TypeParameter[] typeParameterArr = methodDeclaration.typeParameters;
        if (typeParameterArr != null) {
            for (TypeParameter typeParameter : typeParameterArr) {
                typeParameter.traverse(this, methodDeclaration.scope);
            }
        }
        Argument[] argumentArr = methodDeclaration.arguments;
        if (argumentArr != null) {
            for (Argument argument : argumentArr) {
                argument.traverse(this, methodDeclaration.scope);
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
    public boolean visit(TypeDeclaration typeDeclaration, ClassScope classScope) {
        SourceTypeBinding sourceTypeBinding = typeDeclaration.binding;
        if (sourceTypeBinding == null) {
            return false;
        }
        Annotation[] annotationArr = typeDeclaration.annotations;
        if (annotationArr == null) {
            return true;
        }
        resolveAnnotations(typeDeclaration.staticInitializerScope, annotationArr, sourceTypeBinding);
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
    public boolean visit(TypeDeclaration typeDeclaration, CompilationUnitScope compilationUnitScope) {
        SourceTypeBinding sourceTypeBinding = typeDeclaration.binding;
        if (sourceTypeBinding == null) {
            return false;
        }
        Annotation[] annotationArr = typeDeclaration.annotations;
        if (annotationArr == null) {
            return true;
        }
        resolveAnnotations(typeDeclaration.staticInitializerScope, annotationArr, sourceTypeBinding);
        return true;
    }

    private void resolveAnnotations(BlockScope blockScope, Annotation[] annotationArr, Binding binding) {
        if ((annotationArr == null ? 0 : annotationArr.length) == 0) {
            return;
        }
        boolean z = blockScope.insideTypeAnnotation;
        blockScope.insideTypeAnnotation = true;
        binding.getAnnotationTagBits();
        blockScope.insideTypeAnnotation = z;
        ElementImpl elementImpl = (ElementImpl) this._factory.newElement(binding);
        for (AnnotationBinding annotationBinding : elementImpl.getPackedAnnotationBindings()) {
            ReferenceBinding annotationType = annotationBinding.getAnnotationType();
            if (annotationBinding != null && Annotation.isAnnotationTargetAllowed(blockScope, annotationType, binding)) {
                this._annoToElement.put(this._factory.newElement(annotationType), elementImpl);
            }
        }
    }
}
