package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import java.util.List;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.flow.ExceptionHandlingFlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.LocalTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.MemberTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;
import org.eclipse.jdt.internal.compiler.parser.Parser;
import org.eclipse.jdt.internal.compiler.problem.AbortMethod;

/* JADX INFO: loaded from: classes14.dex */
public class MethodDeclaration extends AbstractMethodDeclaration {
    public TypeReference returnType;
    public TypeParameter[] typeParameters;

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public boolean isMethod() {
        return true;
    }

    public MethodDeclaration(CompilationResult compilationResult) {
        super(compilationResult);
        this.bits |= 256;
    }

    public void analyseCode(ClassScope classScope, FlowContext flowContext, FlowInfo flowInfo) {
        if (this.ignoreFurtherInvestigation) {
            return;
        }
        try {
            if (this.binding == null) {
                return;
            }
            if (!this.binding.isUsed() && !this.binding.isAbstract() && ((this.binding.isPrivate() || ((this.binding.modifiers & 805306368) == 0 && this.binding.isOrEnclosedByPrivateType())) && !classScope.referenceCompilationUnit().compilationResult.hasSyntaxError)) {
                this.scope.problemReporter().unusedPrivateMethod(this);
            }
            if ((!this.binding.declaringClass.isEnum() || (this.selector != TypeConstants.VALUES && this.selector != TypeConstants.VALUEOF)) && !this.binding.isAbstract() && !this.binding.isNative()) {
                if (this.typeParameters != null && !this.scope.referenceCompilationUnit().compilationResult.hasSyntaxError) {
                    int length = this.typeParameters.length;
                    for (int i = 0; i < length; i++) {
                        TypeParameter typeParameter = this.typeParameters[i];
                        if ((typeParameter.binding.modifiers & 134217728) == 0) {
                            this.scope.problemReporter().unusedTypeParameter(typeParameter);
                        }
                    }
                }
                ExceptionHandlingFlowContext exceptionHandlingFlowContext = new ExceptionHandlingFlowContext(flowContext, this, this.binding.thrownExceptions, null, this.scope, FlowInfo.DEAD_END);
                analyseArguments(classScope.environment(), flowInfo, this.arguments, this.binding);
                if ((this.binding.declaringClass instanceof MemberTypeBinding) && !this.binding.declaringClass.isStatic()) {
                    this.bits &= -257;
                }
                if (this.statements != null) {
                    boolean z = this.scope.compilerOptions().enableSyntacticNullAnalysisForFields;
                    int iComplainIfUnreachable = (flowInfo.reachMode() & 3) == 0 ? 0 : 1;
                    int length2 = this.statements.length;
                    for (int i2 = 0; i2 < length2; i2++) {
                        Statement statement = this.statements[i2];
                        iComplainIfUnreachable = statement.complainIfUnreachable(flowInfo, this.scope, iComplainIfUnreachable, true);
                        if (iComplainIfUnreachable < 2) {
                            flowInfo = statement.analyseCode(this.scope, exceptionHandlingFlowContext, flowInfo);
                        }
                        if (z) {
                            exceptionHandlingFlowContext.expireNullCheckedFieldInfo();
                        }
                    }
                } else {
                    this.bits &= -257;
                }
                TypeBinding typeBinding = this.binding.returnType;
                if (typeBinding == TypeBinding.VOID || isAbstract()) {
                    if ((flowInfo.tagBits & 1) == 0) {
                        this.bits |= 64;
                    }
                } else if (flowInfo != FlowInfo.DEAD_END) {
                    this.scope.problemReporter().shouldReturn(typeBinding, this);
                }
                exceptionHandlingFlowContext.complainIfUnusedExceptionHandlers(this);
                this.scope.checkUnusedParameters(this.binding);
                if (!this.binding.isStatic() && (this.bits & 256) != 0 && !isDefaultMethod() && !this.binding.isOverriding() && !this.binding.isImplementing()) {
                    if (this.binding.isPrivate() || this.binding.isFinal() || this.binding.declaringClass.isFinal()) {
                        this.scope.problemReporter().methodCanBeDeclaredStatic(this);
                    } else {
                        this.scope.problemReporter().methodCanBePotentiallyDeclaredStatic(this);
                    }
                }
                this.scope.checkUnclosedCloseables(flowInfo, null, null, null);
            }
        } catch (AbortMethod unused) {
            this.ignoreFurtherInvestigation = true;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public void getAllAnnotationContexts(int i, List list) {
        TypeReference.AnnotationCollector annotationCollector = new TypeReference.AnnotationCollector(this.returnType, i, list);
        int length = this.annotations.length;
        for (int i2 = 0; i2 < length; i2++) {
            this.annotations[i2].traverse(annotationCollector, (BlockScope) null);
        }
    }

    public boolean hasNullTypeAnnotation(TypeReference.AnnotationPosition annotationPosition) {
        if (TypeReference.containsNullAnnotation(this.annotations)) {
            return true;
        }
        TypeReference typeReference = this.returnType;
        return typeReference != null && typeReference.hasNullTypeAnnotation(annotationPosition);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public boolean isDefaultMethod() {
        return (this.modifiers & 65536) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public void parseStatements(Parser parser, CompilationUnitDeclaration compilationUnitDeclaration) {
        parser.parse(this, compilationUnitDeclaration);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public StringBuffer printReturnType(int i, StringBuffer stringBuffer) {
        TypeReference typeReference = this.returnType;
        return typeReference == null ? stringBuffer : typeReference.printExpression(0, stringBuffer).append(StringUtil.SPACE);
    }

    /* JADX WARN: Code duplicated, block: B:100:0x0186  */
    /* JADX WARN: Code duplicated, block: B:113:0x01af  */
    /* JADX WARN: Code duplicated, block: B:93:0x016c  */
    /* JADX WARN: Code duplicated, block: B:95:0x0171  */
    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public void resolveStatements() {
        if (this.returnType != null && this.binding != null) {
            this.bits |= this.returnType.bits & 1048576;
            this.returnType.resolvedType = this.binding.returnType;
        }
        if (CharOperation.equals(this.scope.enclosingSourceType().sourceName, this.selector)) {
            this.scope.problemReporter().methodWithConstructorName(this);
        }
        TypeReference typeReference = this.returnType;
        boolean z = typeReference != null && (typeReference.resolvedType instanceof TypeVariableBinding);
        TypeParameter[] typeParameterArr = this.typeParameters;
        if (typeParameterArr != null) {
            int length = typeParameterArr.length;
            for (int i = 0; i < length; i++) {
                this.bits = (this.typeParameters[i].bits & 1048576) | this.bits;
                if (z && TypeBinding.equalsEquals(this.typeParameters[i].binding, this.returnType.resolvedType)) {
                    z = false;
                }
            }
        }
        CompilerOptions compilerOptions = this.scope.compilerOptions();
        if (this.binding != null) {
            long j = compilerOptions.complianceLevel;
            if (j >= ClassFileConstants.JDK1_5) {
                int i2 = this.binding.modifiers;
                boolean z2 = (this.binding.tagBits & 562949953421312L) != 0;
                boolean z3 = (this.binding.tagBits & 512) != 0;
                if (!z2 || z3) {
                    if (this.binding.declaringClass.isInterface()) {
                        if (j >= ClassFileConstants.JDK1_6 && compilerOptions.reportMissingOverrideAnnotationForInterfaceMethodImplementation && ((i2 & 268435464) == 268435456 || this.binding.isImplementing())) {
                            this.scope.problemReporter().missingOverrideAnnotationForInterfaceMethodImplementation(this);
                        }
                    } else if ((i2 & 268435464) == 268435456) {
                        this.scope.problemReporter().missingOverrideAnnotation(this);
                    } else if (j >= ClassFileConstants.JDK1_6 && compilerOptions.reportMissingOverrideAnnotationForInterfaceMethodImplementation && this.binding.isImplementing()) {
                        this.scope.problemReporter().missingOverrideAnnotationForInterfaceMethodImplementation(this);
                    }
                } else if ((i2 & 268435464) != 268435456 && (j < ClassFileConstants.JDK1_6 || (536870920 & i2) != 536870912)) {
                    this.scope.problemReporter().methodMustOverride(this, j);
                }
            }
        }
        int iKind = TypeDeclaration.kind(this.scope.referenceType().modifiers);
        if (iKind == 1) {
            if ((this.modifiers & 16777216) != 0) {
                if ((this.modifiers & 256) == 0 && (this.modifiers & 1024) == 0) {
                    this.scope.problemReporter().methodNeedBody(this);
                }
            } else if ((this.modifiers & 256) == 0 || (this.modifiers & 1024) != 0) {
                this.scope.problemReporter().methodNeedingNoBody(this);
            } else if (this.binding == null || this.binding.isStatic() || (this.binding.declaringClass instanceof LocalTypeBinding) || z) {
                this.bits &= -257;
            }
        } else if (iKind != 2) {
            if (iKind == 3 && this.selector != TypeConstants.VALUES && this.selector != TypeConstants.VALUEOF) {
                if ((this.modifiers & 16777216) != 0) {
                    if ((this.modifiers & 256) == 0) {
                        this.scope.problemReporter().methodNeedBody(this);
                    }
                } else if ((this.modifiers & 256) == 0) {
                    this.scope.problemReporter().methodNeedingNoBody(this);
                } else {
                    this.scope.problemReporter().methodNeedingNoBody(this);
                }
            }
        } else if (compilerOptions.sourceLevel >= ClassFileConstants.JDK1_8 && (this.modifiers & 16778240) == 16777216 && (this.modifiers & 65544) != 0) {
            this.scope.problemReporter().methodNeedBody(this);
        }
        super.resolveStatements();
        if (compilerOptions.getSeverity(CompilerOptions.OverridingMethodWithoutSuperInvocation) == 256 || this.binding == null || (this.binding.modifiers & 805306368) != 268435456 || (this.bits & 16) != 0) {
            return;
        }
        this.scope.problemReporter().overridesMethodWithoutSuperInvocation(this.binding);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope)) {
            if (this.javadoc != null) {
                this.javadoc.traverse(aSTVisitor, this.scope);
            }
            if (this.annotations != null) {
                int length = this.annotations.length;
                for (int i = 0; i < length; i++) {
                    this.annotations[i].traverse(aSTVisitor, this.scope);
                }
            }
            TypeParameter[] typeParameterArr = this.typeParameters;
            if (typeParameterArr != null) {
                int length2 = typeParameterArr.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    this.typeParameters[i2].traverse(aSTVisitor, this.scope);
                }
            }
            TypeReference typeReference = this.returnType;
            if (typeReference != null) {
                typeReference.traverse(aSTVisitor, this.scope);
            }
            if (this.arguments != null) {
                int length3 = this.arguments.length;
                for (int i3 = 0; i3 < length3; i3++) {
                    this.arguments[i3].traverse(aSTVisitor, this.scope);
                }
            }
            if (this.thrownExceptions != null) {
                int length4 = this.thrownExceptions.length;
                for (int i4 = 0; i4 < length4; i4++) {
                    this.thrownExceptions[i4].traverse(aSTVisitor, this.scope);
                }
            }
            if (this.statements != null) {
                int length5 = this.statements.length;
                for (int i5 = 0; i5 < length5; i5++) {
                    this.statements[i5].traverse(aSTVisitor, this.scope);
                }
            }
        }
        aSTVisitor.endVisit(this, classScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public TypeParameter[] typeParameters() {
        return this.typeParameters;
    }
}
