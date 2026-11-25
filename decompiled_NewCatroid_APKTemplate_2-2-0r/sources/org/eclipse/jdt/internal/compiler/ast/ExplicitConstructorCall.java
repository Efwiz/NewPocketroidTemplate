package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.InferenceContext18;
import org.eclipse.jdt.internal.compiler.lookup.LocalTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedGenericMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.RawTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.lookup.VariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ExplicitConstructorCall extends Statement implements Invocation {
    public static final int ImplicitSuper = 1;
    public static final int Super = 2;
    public static final int This = 3;
    public int accessMode;
    public Expression[] arguments;
    public MethodBinding binding;
    public TypeBinding[] genericTypeArguments;
    public VariableBinding[][] implicitArguments;
    public Expression qualification;
    MethodBinding syntheticAccessor;
    public TypeReference[] typeArguments;
    public int typeArgumentsSourceStart;

    @Override // org.eclipse.jdt.internal.compiler.ast.Invocation
    public void cleanUpInferenceContexts() {
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Invocation
    public InferenceContext18 getInferenceContext(ParameterizedMethodBinding parameterizedMethodBinding) {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean isTypeAccess() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Invocation
    public void registerInferenceContext(ParameterizedGenericMethodBinding parameterizedGenericMethodBinding, InferenceContext18 inferenceContext18) {
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Invocation
    public void registerResult(TypeBinding typeBinding, MethodBinding methodBinding) {
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public void setActualReceiverType(ReferenceBinding referenceBinding) {
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public void setDepth(int i) {
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public void setFieldIndex(int i) {
    }

    public ExplicitConstructorCall(int i) {
        this.accessMode = i;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        try {
            ((MethodScope) blockScope).isConstructorCall = true;
            Expression expression = this.qualification;
            if (expression != null) {
                flowInfo = expression.analyseCode(blockScope, flowContext, flowInfo).unconditionalInits();
            }
            if (this.arguments != null) {
                boolean z = blockScope.compilerOptions().analyseResourceLeaks;
                int length = this.arguments.length;
                for (int i = 0; i < length; i++) {
                    flowInfo = this.arguments[i].analyseCode(blockScope, flowContext, flowInfo).unconditionalInits();
                    if (z) {
                        flowInfo = FakedTrackingVariable.markPassedToOutside(blockScope, this.arguments[i], flowInfo, flowContext, false);
                    }
                    this.arguments[i].checkNPEbyUnboxing(blockScope, flowContext, flowInfo);
                }
                analyseArguments(blockScope, flowContext, flowInfo, this.binding, this.arguments);
            }
            ReferenceBinding[] referenceBindingArrConvertToRawTypes = this.binding.thrownExceptions;
            if (referenceBindingArrConvertToRawTypes != Binding.NO_EXCEPTIONS) {
                if ((this.bits & 65536) != 0 && this.genericTypeArguments == null) {
                    referenceBindingArrConvertToRawTypes = blockScope.environment().convertToRawTypes(this.binding.thrownExceptions, true, true);
                }
                flowContext.checkExceptionHandlers(referenceBindingArrConvertToRawTypes, this.accessMode == 1 ? (ASTNode) blockScope.methodScope().referenceContext : this, flowInfo, blockScope);
            }
            manageEnclosingInstanceAccessIfNecessary(blockScope, flowInfo);
            manageSyntheticAccessIfNecessary(blockScope, flowInfo);
            return flowInfo;
        } finally {
            ((MethodScope) blockScope).isConstructorCall = false;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
        if ((this.bits & Integer.MIN_VALUE) == 0) {
            return;
        }
        try {
            ((MethodScope) blockScope).isConstructorCall = true;
            int i = codeStream.position;
            codeStream.aload_0();
            MethodBinding methodBindingOriginal = this.binding.original();
            ReferenceBinding referenceBinding = methodBindingOriginal.declaringClass;
            if (referenceBinding.erasure().f626id == 41 || referenceBinding.isEnum()) {
                codeStream.aload_1();
                codeStream.iload_2();
            }
            if (referenceBinding.isNestedType()) {
                codeStream.generateSyntheticEnclosingInstanceValues(blockScope, referenceBinding, (this.bits & 8192) != 0 ? null : this.qualification, this);
            }
            generateArguments(this.binding, this.arguments, blockScope, codeStream);
            if (referenceBinding.isNestedType()) {
                codeStream.generateSyntheticOuterArgumentValues(blockScope, referenceBinding, this);
            }
            MethodBinding methodBinding = this.syntheticAccessor;
            if (methodBinding != null) {
                int length = methodBinding.parameters.length - methodBindingOriginal.parameters.length;
                for (int i2 = 0; i2 < length; i2++) {
                    codeStream.aconst_null();
                }
                codeStream.invoke(Opcodes.OPC_invokespecial, this.syntheticAccessor, null, this.typeArguments);
            } else {
                codeStream.invoke(Opcodes.OPC_invokespecial, methodBindingOriginal, null, this.typeArguments);
            }
            codeStream.recordPositionsFrom(i, this.sourceStart);
        } finally {
            ((MethodScope) blockScope).isConstructorCall = false;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public TypeBinding[] genericTypeArguments() {
        return this.genericTypeArguments;
    }

    public boolean isImplicitSuper() {
        return this.accessMode == 1;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean isSuperAccess() {
        return this.accessMode != 3;
    }

    void manageEnclosingInstanceAccessIfNecessary(BlockScope blockScope, FlowInfo flowInfo) {
        ReferenceBinding referenceBinding = (ReferenceBinding) this.binding.declaringClass.erasure();
        if ((flowInfo.tagBits & 1) == 0 && referenceBinding.isNestedType() && blockScope.enclosingSourceType().isLocalType()) {
            if (referenceBinding.isLocalType()) {
                ((LocalTypeBinding) referenceBinding).addInnerEmulationDependent(blockScope, this.qualification != null);
            } else {
                blockScope.propagateInnerEmulation(referenceBinding, this.qualification != null);
            }
        }
    }

    public void manageSyntheticAccessIfNecessary(BlockScope blockScope, FlowInfo flowInfo) {
        if ((flowInfo.tagBits & 1) == 0) {
            MethodBinding methodBindingOriginal = this.binding.original();
            if (!this.binding.isPrivate() || this.accessMode == 3) {
                return;
            }
            ReferenceBinding referenceBinding = methodBindingOriginal.declaringClass;
            if ((referenceBinding.tagBits & 16) != 0 && blockScope.compilerOptions().complianceLevel >= ClassFileConstants.JDK1_4) {
                methodBindingOriginal.tagBits |= 512;
            } else {
                this.syntheticAccessor = ((SourceTypeBinding) referenceBinding).addSyntheticMethod(methodBindingOriginal, isSuperAccess());
                blockScope.problemReporter().needToEmulateMethodAccess(methodBindingOriginal, this);
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer);
        Expression expression = this.qualification;
        if (expression != null) {
            expression.printExpression(0, stringBuffer).append('.');
        }
        if (this.typeArguments != null) {
            stringBuffer.append('<');
            int length = this.typeArguments.length - 1;
            for (int i2 = 0; i2 < length; i2++) {
                this.typeArguments[i2].print(0, stringBuffer);
                stringBuffer.append(", ");
            }
            this.typeArguments[length].print(0, stringBuffer);
            stringBuffer.append('>');
        }
        if (this.accessMode == 3) {
            stringBuffer.append("this(");
        } else {
            stringBuffer.append("super(");
        }
        if (this.arguments != null) {
            for (int i3 = 0; i3 < this.arguments.length; i3++) {
                if (i3 > 0) {
                    stringBuffer.append(", ");
                }
                this.arguments[i3].printExpression(0, stringBuffer);
            }
        }
        return stringBuffer.append(");");
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0040 A[PHI: r2
      0x0040: PHI (r2v10 org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding) = 
      (r2v9 org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding)
      (r2v50 org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding)
      (r2v50 org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding)
      (r2v50 org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding)
     binds: [B:11:0x0023, B:13:0x002f, B:15:0x0033, B:17:0x003b] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
        ReferenceBinding referenceBinding;
        boolean z;
        boolean z2;
        MethodBinding methodBindingCreateParameterizedGenericMethod;
        MethodScope methodScope = blockScope.methodScope();
        try {
            AbstractMethodDeclaration abstractMethodDeclarationReferenceMethod = methodScope.referenceMethod();
            boolean z3 = true;
            if (abstractMethodDeclarationReferenceMethod != null && abstractMethodDeclarationReferenceMethod.isConstructor() && ((ConstructorDeclaration) abstractMethodDeclarationReferenceMethod).constructorCall == this) {
                methodScope.isConstructorCall = true;
                ReferenceBinding referenceBindingEnclosingReceiverType = blockScope.enclosingReceiverType();
                if (this.accessMode != 3) {
                    referenceBindingEnclosingReceiverType = referenceBindingEnclosingReceiverType.superclass();
                    TypeReference typeReference = blockScope.referenceType().superclass;
                    if (typeReference == null || typeReference.resolvedType == null || typeReference.resolvedType.isValidBinding()) {
                        referenceBinding = referenceBindingEnclosingReceiverType;
                        z = false;
                    } else {
                        referenceBinding = referenceBindingEnclosingReceiverType;
                        z = true;
                    }
                } else {
                    referenceBinding = referenceBindingEnclosingReceiverType;
                    z = false;
                }
                if (referenceBinding != null) {
                    if (this.accessMode == 2 && referenceBinding.erasure().f626id == 41) {
                        blockScope.problemReporter().cannotInvokeSuperConstructorInEnum(this, methodScope.referenceMethod().binding);
                    }
                    if (this.qualification != null) {
                        if (this.accessMode != 2) {
                            blockScope.problemReporter().unnecessaryEnclosingInstanceSpecification(this.qualification, referenceBinding);
                        }
                        if (!z) {
                            ReferenceBinding referenceBindingEnclosingType = referenceBinding.enclosingType();
                            if (referenceBindingEnclosingType == null) {
                                blockScope.problemReporter().unnecessaryEnclosingInstanceSpecification(this.qualification, referenceBinding);
                                this.bits |= 8192;
                            } else {
                                TypeBinding typeBindingResolveTypeExpecting = this.qualification.resolveTypeExpecting(blockScope, referenceBindingEnclosingType);
                                this.qualification.computeConversion(blockScope, typeBindingResolveTypeExpecting, typeBindingResolveTypeExpecting);
                            }
                        }
                    }
                }
                long j = blockScope.compilerOptions().sourceLevel;
                TypeReference[] typeReferenceArr = this.typeArguments;
                if (typeReferenceArr != null) {
                    boolean z4 = j < ClassFileConstants.JDK1_5;
                    int length = typeReferenceArr.length;
                    this.genericTypeArguments = new TypeBinding[length];
                    for (int i = 0; i < length; i++) {
                        TypeReference typeReference2 = this.typeArguments[i];
                        TypeBinding[] typeBindingArr = this.genericTypeArguments;
                        TypeBinding typeBindingResolveType = typeReference2.resolveType(blockScope, true);
                        typeBindingArr[i] = typeBindingResolveType;
                        if (typeBindingResolveType == null) {
                            z4 = true;
                        }
                        if (z4 && (typeReference2 instanceof Wildcard)) {
                            blockScope.problemReporter().illegalUsageOfWildcard(typeReference2);
                        }
                    }
                    if (z4) {
                        Expression[] expressionArr = this.arguments;
                        if (expressionArr != null) {
                            int length2 = expressionArr.length;
                            for (int i2 = 0; i2 < length2; i2++) {
                                this.arguments[i2].resolveType(blockScope);
                            }
                        }
                        methodScope.isConstructorCall = false;
                        return;
                    }
                }
                TypeBinding[] typeBindingArr2 = Binding.NO_PARAMETERS;
                Expression[] expressionArr2 = this.arguments;
                if (expressionArr2 != null) {
                    int length3 = expressionArr2.length;
                    TypeBinding[] typeBindingArr3 = new TypeBinding[length3];
                    boolean z5 = false;
                    boolean z6 = false;
                    for (int i3 = 0; i3 < length3; i3++) {
                        Expression expression = this.arguments[i3];
                        if (expression instanceof CastExpression) {
                            expression.bits |= 32;
                            z6 = true;
                        }
                        expression.setExpressionContext(ExpressionContext.INVOCATION_CONTEXT);
                        TypeBinding typeBindingResolveType2 = expression.resolveType(blockScope);
                        typeBindingArr3[i3] = typeBindingResolveType2;
                        if (typeBindingResolveType2 == null) {
                            z5 = true;
                        }
                    }
                    if (z5) {
                        if (referenceBinding == null) {
                            methodScope.isConstructorCall = false;
                            return;
                        }
                        TypeBinding[] typeBindingArr4 = new TypeBinding[length3];
                        while (true) {
                            length3--;
                            if (length3 < 0) {
                                break;
                            }
                            TypeBinding typeBinding = typeBindingArr3[length3];
                            if (typeBinding == null) {
                                typeBinding = TypeBinding.NULL;
                            }
                            typeBindingArr4[length3] = typeBinding;
                        }
                        MethodBinding methodBindingFindMethod = blockScope.findMethod(referenceBinding, TypeConstants.INIT, typeBindingArr4, this, false);
                        this.binding = methodBindingFindMethod;
                        if (methodBindingFindMethod != null && !methodBindingFindMethod.isValidBinding() && (methodBindingCreateParameterizedGenericMethod = ((ProblemMethodBinding) this.binding).closestMatch) != null) {
                            if (methodBindingCreateParameterizedGenericMethod.original().typeVariables != Binding.NO_TYPE_VARIABLES) {
                                methodBindingCreateParameterizedGenericMethod = blockScope.environment().createParameterizedGenericMethod(methodBindingCreateParameterizedGenericMethod.original(), (RawTypeBinding) null);
                            }
                            this.binding = methodBindingCreateParameterizedGenericMethod;
                            MethodBinding methodBindingOriginal = methodBindingCreateParameterizedGenericMethod.original();
                            if (methodBindingOriginal.isOrEnclosedByPrivateType() && !blockScope.isDefinedInMethod(methodBindingOriginal)) {
                                methodBindingOriginal.modifiers |= 134217728;
                            }
                        }
                        methodScope.isConstructorCall = false;
                        return;
                    }
                    typeBindingArr2 = typeBindingArr3;
                    z2 = z6;
                } else if (referenceBinding.erasure().f626id == 41) {
                    z2 = false;
                    typeBindingArr2 = new TypeBinding[]{blockScope.getJavaLangString(), TypeBinding.INT};
                } else {
                    z2 = false;
                }
                if (referenceBinding == null) {
                    methodScope.isConstructorCall = false;
                    return;
                }
                MethodBinding methodBindingFindConstructorBinding = findConstructorBinding(blockScope, this, referenceBinding, typeBindingArr2);
                this.binding = methodBindingFindConstructorBinding;
                if (methodBindingFindConstructorBinding.isValidBinding()) {
                    if ((this.binding.tagBits & 128) != 0 && !methodScope.enclosingSourceType().isAnonymousType()) {
                        blockScope.problemReporter().missingTypeInConstructor(this, this.binding);
                    }
                    MethodBinding methodBinding = this.binding;
                    if (this.accessMode == 1) {
                        z3 = false;
                    }
                    if (isMethodUseDeprecated(methodBinding, blockScope, z3)) {
                        blockScope.problemReporter().deprecatedMethod(this.binding, this);
                    }
                    if (checkInvocationArguments(blockScope, null, referenceBinding, this.binding, this.arguments, typeBindingArr2, z2, this)) {
                        this.bits |= 65536;
                    }
                    if (this.binding.isOrEnclosedByPrivateType()) {
                        this.binding.original().modifiers |= 134217728;
                    }
                    if (this.typeArguments != null && this.binding.original().typeVariables == Binding.NO_TYPE_VARIABLES) {
                        blockScope.problemReporter().unnecessaryTypeArgumentsForMethodInvocation(this.binding, this.genericTypeArguments, this.typeArguments);
                    }
                } else {
                    if (this.binding.declaringClass == null) {
                        this.binding.declaringClass = referenceBinding;
                    }
                    if (z) {
                        methodScope.isConstructorCall = false;
                        return;
                    }
                    blockScope.problemReporter().invalidConstructor(this, this.binding);
                }
                methodScope.isConstructorCall = false;
                return;
            }
            blockScope.problemReporter().invalidExplicitConstructorCall(this);
            Expression expression2 = this.qualification;
            if (expression2 != null) {
                expression2.resolveType(blockScope);
            }
            TypeReference[] typeReferenceArr2 = this.typeArguments;
            if (typeReferenceArr2 != null) {
                int length4 = typeReferenceArr2.length;
                for (int i4 = 0; i4 < length4; i4++) {
                    this.typeArguments[i4].resolveType(blockScope, true);
                }
            }
            Expression[] expressionArr3 = this.arguments;
            if (expressionArr3 != null) {
                int length5 = expressionArr3.length;
                for (int i5 = 0; i5 < length5; i5++) {
                    this.arguments[i5].resolveType(blockScope);
                }
            }
            methodScope.isConstructorCall = false;
        } catch (Throwable th) {
            methodScope.isConstructorCall = false;
            throw th;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            Expression expression = this.qualification;
            if (expression != null) {
                expression.traverse(aSTVisitor, blockScope);
            }
            TypeReference[] typeReferenceArr = this.typeArguments;
            if (typeReferenceArr != null) {
                int length = typeReferenceArr.length;
                for (int i = 0; i < length; i++) {
                    this.typeArguments[i].traverse(aSTVisitor, blockScope);
                }
            }
            Expression[] expressionArr = this.arguments;
            if (expressionArr != null) {
                int length2 = expressionArr.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    this.arguments[i2].traverse(aSTVisitor, blockScope);
                }
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Invocation
    public MethodBinding binding() {
        return this.binding;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Invocation
    public Expression[] arguments() {
        return this.arguments;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public InferenceContext18 freshInferenceContext(Scope scope) {
        return new InferenceContext18(scope, this.arguments, this, null);
    }
}
