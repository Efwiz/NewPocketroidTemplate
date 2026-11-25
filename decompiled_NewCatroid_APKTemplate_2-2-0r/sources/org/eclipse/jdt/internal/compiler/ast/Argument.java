package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.CatchParameterBinding;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.InvocationSite;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class Argument extends LocalDeclaration {
    private static final char[] SET = FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX.toCharArray();

    @Override // org.eclipse.jdt.internal.compiler.ast.LocalDeclaration
    public boolean isArgument() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.LocalDeclaration
    public boolean isRecoveredFromLoneIdentifier() {
        return false;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Argument(char[] cArr, long j, TypeReference typeReference, int i) {
        int i2 = (int) (j >>> 32);
        int i3 = (int) j;
        super(cArr, i2, i3);
        this.declarationSourceEnd = i3;
        this.modifiers = i;
        this.type = typeReference;
        if (typeReference != null) {
            this.bits |= typeReference.bits & 1048576;
        }
        this.bits |= CompilerOptions.Tasks;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Argument(char[] cArr, long j, TypeReference typeReference, int i, boolean z) {
        int i2 = (int) (j >>> 32);
        int i3 = (int) j;
        super(cArr, i2, i3);
        this.declarationSourceEnd = i3;
        this.modifiers = i;
        this.type = typeReference;
        if (typeReference != null) {
            this.bits |= typeReference.bits & 1048576;
        }
        this.bits |= 1073741830;
    }

    public TypeBinding createBinding(MethodScope methodScope, TypeBinding typeBinding) {
        AbstractMethodDeclaration abstractMethodDeclarationReferenceMethod;
        MethodBinding methodBinding;
        if (this.binding == null) {
            this.binding = new LocalVariableBinding(this, typeBinding, this.modifiers, methodScope);
        } else if (!this.binding.type.isValidBinding() && (abstractMethodDeclarationReferenceMethod = methodScope.referenceMethod()) != null && (methodBinding = abstractMethodDeclarationReferenceMethod.binding) != null) {
            methodBinding.tagBits |= 512;
        }
        if ((this.binding.tagBits & 8589934592L) == 0) {
            resolveAnnotations(methodScope, this.annotations, this.binding, true);
            if (methodScope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_8) {
                Annotation.isTypeUseCompatible(this.type, methodScope, this.annotations);
                methodScope.validateNullAnnotation(this.binding.tagBits, this.type, this.annotations);
            }
        }
        this.binding.declaration = this;
        return this.binding.type;
    }

    public TypeBinding bind(MethodScope methodScope, TypeBinding typeBinding, boolean z) {
        AbstractMethodDeclaration abstractMethodDeclarationReferenceMethod;
        TypeBinding typeBindingCreateBinding = createBinding(methodScope, typeBinding);
        boolean z2 = false;
        Binding binding = methodScope.getBinding(this.f617name, 3, (InvocationSite) this, false);
        if (binding != null && binding.isValidBinding()) {
            if ((binding instanceof LocalVariableBinding) && this.hiddenVariableDepth == 0) {
                if ((this.bits & 2097152) != 0 && methodScope.isLambdaSubscope()) {
                    methodScope.problemReporter().lambdaRedeclaresArgument(this);
                } else {
                    methodScope.problemReporter().redefineArgument(this);
                }
            } else {
                if ((binding instanceof FieldBinding) && (methodScope.isInsideConstructor() || ((abstractMethodDeclarationReferenceMethod = methodScope.referenceMethod()) != null && CharOperation.prefixEquals(SET, abstractMethodDeclarationReferenceMethod.selector)))) {
                    z2 = true;
                }
                methodScope.problemReporter().localVariableHiding(this, binding, z2);
            }
        }
        methodScope.addLocalVariable(this.binding);
        this.binding.useFlag = z ? 1 : 0;
        return typeBindingCreateBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.LocalDeclaration, org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration
    public int getKind() {
        return (this.bits & 4) != 0 ? 5 : 4;
    }

    public boolean isVarArgs() {
        return (this.type == null || (this.type.bits & 16384) == 0) ? false : true;
    }

    public boolean hasElidedType() {
        return (this.bits & 2) != 0;
    }

    public boolean hasNullTypeAnnotation(TypeReference.AnnotationPosition annotationPosition) {
        if (TypeReference.containsNullAnnotation(this.annotations)) {
            return true;
        }
        return this.type != null && this.type.hasNullTypeAnnotation(annotationPosition);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public StringBuffer print(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer);
        printModifiers(this.modifiers, stringBuffer);
        if (this.annotations != null) {
            printAnnotations(this.annotations, stringBuffer);
            stringBuffer.append(StringUtil.SPACE);
        }
        if (this.type == null) {
            stringBuffer.append("<no type> ");
        } else {
            this.type.print(0, stringBuffer).append(StringUtil.SPACE);
        }
        return stringBuffer.append(this.f617name);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration, org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        return print(i, stringBuffer).append(';');
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0030  */
    public TypeBinding resolveForCatch(BlockScope blockScope) {
        boolean z;
        TypeBinding typeBindingResolveType = this.type.resolveType(blockScope, true);
        if (typeBindingResolveType != null) {
            int iKind = typeBindingResolveType.kind();
            if (iKind != 260) {
                if (iKind != 4100) {
                    z = false;
                } else {
                    blockScope.problemReporter().invalidTypeVariableAsException(typeBindingResolveType, this);
                    z = true;
                }
            } else if (typeBindingResolveType.isBoundParameterizedType()) {
                blockScope.problemReporter().invalidParameterizedExceptionType(typeBindingResolveType, this);
                z = true;
            } else {
                z = false;
            }
            if (typeBindingResolveType.findSuperTypeOriginatingFrom(21, true) == null && typeBindingResolveType.isValidBinding()) {
                blockScope.problemReporter().cannotThrowType(this.type, typeBindingResolveType);
                z = true;
            }
        } else {
            z = true;
        }
        Binding binding = blockScope.getBinding(this.f617name, 3, (InvocationSite) this, false);
        if (binding != null && binding.isValidBinding()) {
            if ((binding instanceof LocalVariableBinding) && this.hiddenVariableDepth == 0) {
                blockScope.problemReporter().redefineArgument(this);
            } else {
                blockScope.problemReporter().localVariableHiding(this, binding, false);
            }
        }
        if ((this.type.bits & 536870912) != 0) {
            this.binding = new CatchParameterBinding(this, typeBindingResolveType, this.modifiers | 16, false);
            this.binding.tagBits |= 4096;
        } else {
            this.binding = new CatchParameterBinding(this, typeBindingResolveType, this.modifiers, false);
        }
        resolveAnnotations(blockScope, this.annotations, this.binding, true);
        Annotation.isTypeUseCompatible(this.type, blockScope, this.annotations);
        if (blockScope.compilerOptions().isAnnotationBasedNullAnalysisEnabled && (this.type.hasNullTypeAnnotation(TypeReference.AnnotationPosition.ANY) || TypeReference.containsNullAnnotation(this.annotations))) {
            blockScope.problemReporter().nullAnnotationUnsupportedLocation(this.type);
        }
        blockScope.addLocalVariable(this.binding);
        this.binding.setConstant(Constant.NotAConstant);
        if (z) {
            return null;
        }
        return typeBindingResolveType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.LocalDeclaration, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            if (this.annotations != null) {
                int length = this.annotations.length;
                for (int i = 0; i < length; i++) {
                    this.annotations[i].traverse(aSTVisitor, blockScope);
                }
            }
            if (this.type != null) {
                this.type.traverse(aSTVisitor, blockScope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope)) {
            if (this.annotations != null) {
                int length = this.annotations.length;
                for (int i = 0; i < length; i++) {
                    this.annotations[i].traverse(aSTVisitor, classScope);
                }
            }
            if (this.type != null) {
                this.type.traverse(aSTVisitor, classScope);
            }
        }
        aSTVisitor.endVisit(this, classScope);
    }
}
