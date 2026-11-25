package org.eclipse.jdt.internal.compiler.ast;

import java.util.HashMap;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ImplicitNullAnnotationVerifier;
import org.eclipse.jdt.internal.compiler.lookup.InferenceContext18;
import org.eclipse.jdt.internal.compiler.lookup.LocalTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.NestedTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedGenericMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.PolyTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.RawTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.SyntheticArgumentBinding;
import org.eclipse.jdt.internal.compiler.lookup.SyntheticFactoryMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;
import org.eclipse.jdt.internal.compiler.util.SimpleLookupTable;

/* JADX INFO: loaded from: classes14.dex */
public class AllocationExpression extends Expression implements IPolyExpression, Invocation {
    public boolean argsContainCast;
    public Expression[] arguments;
    public MethodBinding binding;
    public FakedTrackingVariable closeTracker;
    public FieldDeclaration enumConstant;
    public TypeBinding[] genericTypeArguments;
    private SimpleLookupTable inferenceContexts;
    public boolean inferredReturnType;
    private InferenceContext18 outerInferenceContext;
    public HashMap<TypeBinding, MethodBinding> solutionsPerTargetType;
    MethodBinding syntheticAccessor;
    public TypeReference type;
    public TypeReference[] typeArguments;
    protected TypeBinding typeExpected;
    public ExpressionContext expressionContext = ExpressionContext.VANILLA_CONTEXT;
    public TypeBinding[] argumentTypes = Binding.NO_PARAMETERS;
    public boolean argumentsHaveErrors = false;

    public Expression enclosingInstance() {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean isSuperAccess() {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean isTypeAccess() {
        return true;
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

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        checkCapturedLocalInitializationIfNecessary((ReferenceBinding) this.binding.declaringClass.erasure(), blockScope, flowInfo);
        if (this.arguments != null) {
            boolean z = blockScope.compilerOptions().analyseResourceLeaks;
            boolean z2 = z && (this.resolvedType instanceof ReferenceBinding) && ((ReferenceBinding) this.resolvedType).hasTypeBit(4);
            int length = this.arguments.length;
            for (int i = 0; i < length; i++) {
                flowInfo = this.arguments[i].analyseCode(blockScope, flowContext, flowInfo).unconditionalInits();
                if (z && !z2) {
                    flowInfo = FakedTrackingVariable.markPassedToOutside(blockScope, this.arguments[i], flowInfo, flowContext, false);
                }
                this.arguments[i].checkNPEbyUnboxing(blockScope, flowContext, flowInfo);
            }
            analyseArguments(blockScope, flowContext, flowInfo, this.binding, this.arguments);
        }
        ReferenceBinding[] referenceBindingArrConvertToRawTypes = this.binding.thrownExceptions;
        if (referenceBindingArrConvertToRawTypes.length != 0) {
            if ((this.bits & 65536) != 0 && this.genericTypeArguments == null) {
                referenceBindingArrConvertToRawTypes = blockScope.environment().convertToRawTypes(this.binding.thrownExceptions, true, true);
            }
            flowContext.checkExceptionHandlers(referenceBindingArrConvertToRawTypes, this, flowInfo.unconditionalCopy(), blockScope);
        }
        if (blockScope.compilerOptions().analyseResourceLeaks && FakedTrackingVariable.isAnyCloseable(this.resolvedType)) {
            FakedTrackingVariable.analyseCloseableAllocation(blockScope, flowInfo, this);
        }
        ReferenceBinding referenceBinding = this.binding.declaringClass;
        MethodScope methodScope = blockScope.methodScope();
        if ((referenceBinding.isMemberType() && !referenceBinding.isStatic()) || (referenceBinding.isLocalType() && !methodScope.isStatic && methodScope.isLambdaScope())) {
            blockScope.tagAsAccessingEnclosingInstanceStateOf(this.binding.declaringClass.enclosingType(), false);
        }
        manageEnclosingInstanceAccessIfNecessary(blockScope, flowInfo);
        manageSyntheticAccessIfNecessary(blockScope, flowInfo);
        flowContext.recordAbruptExit();
        return flowInfo;
    }

    public void checkCapturedLocalInitializationIfNecessary(ReferenceBinding referenceBinding, BlockScope blockScope, FlowInfo flowInfo) {
        SyntheticArgumentBinding[] syntheticArgumentBindingArrSyntheticOuterLocalVariables;
        if ((referenceBinding.tagBits & TagBits.AnonymousTypeMask) != TagBits.LocalTypeMask || blockScope.isDefinedInType(referenceBinding) || (syntheticArgumentBindingArrSyntheticOuterLocalVariables = ((NestedTypeBinding) referenceBinding).syntheticOuterLocalVariables()) == null) {
            return;
        }
        for (SyntheticArgumentBinding syntheticArgumentBinding : syntheticArgumentBindingArrSyntheticOuterLocalVariables) {
            LocalVariableBinding localVariableBinding = syntheticArgumentBinding.actualOuterLocalVariable;
            if (localVariableBinding != null && localVariableBinding.declaration != null && !flowInfo.isDefinitelyAssigned(localVariableBinding)) {
                blockScope.problemReporter().uninitializedLocalVariable(localVariableBinding, this);
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        cleanUpInferenceContexts();
        if (!z) {
            blockScope.problemReporter().unusedObjectAllocation(this);
        }
        int i = codeStream.position;
        MethodBinding methodBindingOriginal = this.binding.original();
        ReferenceBinding referenceBinding = methodBindingOriginal.declaringClass;
        codeStream.new_(this.type, referenceBinding);
        boolean z2 = (this.implicitConversion & 1024) != 0;
        if (z || z2) {
            codeStream.dup();
        }
        TypeReference typeReference = this.type;
        if (typeReference != null) {
            codeStream.recordPositionsFrom(i, typeReference.sourceStart);
        } else {
            codeStream.ldc(String.valueOf(this.enumConstant.f617name));
            codeStream.generateInlinedValue(this.enumConstant.binding.f627id);
        }
        if (referenceBinding.isNestedType()) {
            codeStream.generateSyntheticEnclosingInstanceValues(blockScope, referenceBinding, enclosingInstance(), this);
        }
        generateArguments(this.binding, this.arguments, blockScope, codeStream);
        if (referenceBinding.isNestedType()) {
            codeStream.generateSyntheticOuterArgumentValues(blockScope, referenceBinding, this);
        }
        MethodBinding methodBinding = this.syntheticAccessor;
        if (methodBinding == null) {
            codeStream.invoke(Opcodes.OPC_invokespecial, methodBindingOriginal, null, this.typeArguments);
        } else {
            int length = methodBinding.parameters.length - methodBindingOriginal.parameters.length;
            for (int i2 = 0; i2 < length; i2++) {
                codeStream.aconst_null();
            }
            codeStream.invoke(Opcodes.OPC_invokespecial, this.syntheticAccessor, null, this.typeArguments);
        }
        if (z) {
            codeStream.generateImplicitConversion(this.implicitConversion);
        } else if (z2) {
            codeStream.generateImplicitConversion(this.implicitConversion);
            int i3 = postConversionType(blockScope).f626id;
            if (i3 == 7 || i3 == 8) {
                codeStream.pop2();
            } else {
                codeStream.pop();
            }
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public TypeBinding[] genericTypeArguments() {
        return this.genericTypeArguments;
    }

    public void manageEnclosingInstanceAccessIfNecessary(BlockScope blockScope, FlowInfo flowInfo) {
        if ((flowInfo.tagBits & 1) != 0) {
            return;
        }
        ReferenceBinding referenceBinding = (ReferenceBinding) this.binding.declaringClass.erasure();
        if (referenceBinding.isNestedType()) {
            if (blockScope.enclosingSourceType().isLocalType() || blockScope.isLambdaSubscope()) {
                if (referenceBinding.isLocalType()) {
                    ((LocalTypeBinding) referenceBinding).addInnerEmulationDependent(blockScope, false);
                } else {
                    blockScope.propagateInnerEmulation(referenceBinding, false);
                }
            }
        }
    }

    public void manageSyntheticAccessIfNecessary(BlockScope blockScope, FlowInfo flowInfo) {
        if ((flowInfo.tagBits & 1) != 0) {
            return;
        }
        MethodBinding methodBindingOriginal = this.binding.original();
        if (methodBindingOriginal.isPrivate()) {
            SourceTypeBinding sourceTypeBindingEnclosingSourceType = blockScope.enclosingSourceType();
            ReferenceBinding referenceBinding = methodBindingOriginal.declaringClass;
            if (TypeBinding.notEquals(sourceTypeBindingEnclosingSourceType, referenceBinding)) {
                if ((referenceBinding.tagBits & 16) != 0 && blockScope.compilerOptions().complianceLevel >= ClassFileConstants.JDK1_4) {
                    methodBindingOriginal.tagBits |= 512;
                } else {
                    this.syntheticAccessor = ((SourceTypeBinding) referenceBinding).addSyntheticMethod(methodBindingOriginal, isSuperAccess());
                    blockScope.problemReporter().needToEmulateMethodAccess(methodBindingOriginal, this);
                }
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        if (this.type != null) {
            stringBuffer.append("new ");
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
        TypeReference typeReference = this.type;
        if (typeReference != null) {
            typeReference.printExpression(0, stringBuffer);
        }
        stringBuffer.append('(');
        if (this.arguments != null) {
            for (int i3 = 0; i3 < this.arguments.length; i3++) {
                if (i3 > 0) {
                    stringBuffer.append(", ");
                }
                this.arguments[i3].printExpression(0, stringBuffer);
            }
        }
        return stringBuffer.append(')');
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        MethodBinding methodBindingCreateParameterizedGenericMethod;
        TypeReference typeReference = this.type;
        int i = 0;
        boolean z = (typeReference == null || (typeReference.bits & 524288) == 0) ? false : true;
        CompilerOptions compilerOptions = blockScope.compilerOptions();
        long j = compilerOptions.sourceLevel;
        if (this.constant != Constant.NotAConstant) {
            this.constant = Constant.NotAConstant;
            TypeReference typeReference2 = this.type;
            if (typeReference2 == null) {
                this.resolvedType = blockScope.enclosingReceiverType();
            } else {
                this.resolvedType = typeReference2.resolveType(blockScope, true);
            }
            if (this.type != null) {
                checkIllegalNullAnnotation(blockScope, this.resolvedType);
                if (this.type instanceof ParameterizedQualifiedTypeReference) {
                    ReferenceBinding referenceBindingEnclosingType = (ReferenceBinding) this.resolvedType;
                    if (referenceBindingEnclosingType == null) {
                        return referenceBindingEnclosingType;
                    }
                    while ((referenceBindingEnclosingType.modifiers & 8) == 0 && !referenceBindingEnclosingType.isRawType()) {
                        referenceBindingEnclosingType = referenceBindingEnclosingType.enclosingType();
                        if (referenceBindingEnclosingType == null) {
                            ParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference = (ParameterizedQualifiedTypeReference) this.type;
                            for (int length = parameterizedQualifiedTypeReference.typeArguments.length - 2; length >= 0; length--) {
                                if (parameterizedQualifiedTypeReference.typeArguments[length] != null) {
                                    blockScope.problemReporter().illegalQualifiedParameterizedTypeAllocation(this.type, this.resolvedType);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
            }
            TypeReference[] typeReferenceArr = this.typeArguments;
            if (typeReferenceArr != null) {
                int length2 = typeReferenceArr.length;
                this.argumentsHaveErrors = j < ClassFileConstants.JDK1_5;
                this.genericTypeArguments = new TypeBinding[length2];
                for (int i2 = 0; i2 < length2; i2++) {
                    TypeReference typeReference3 = this.typeArguments[i2];
                    TypeBinding[] typeBindingArr = this.genericTypeArguments;
                    TypeBinding typeBindingResolveType = typeReference3.resolveType(blockScope, true);
                    typeBindingArr[i2] = typeBindingResolveType;
                    if (typeBindingResolveType == null) {
                        this.argumentsHaveErrors = true;
                    }
                    if (this.argumentsHaveErrors && (typeReference3 instanceof Wildcard)) {
                        blockScope.problemReporter().illegalUsageOfWildcard(typeReference3);
                    }
                }
                if (z) {
                    blockScope.problemReporter().diamondNotWithExplicitTypeArguments(this.typeArguments);
                    return null;
                }
                if (this.argumentsHaveErrors) {
                    Expression[] expressionArr = this.arguments;
                    if (expressionArr != null) {
                        int length3 = expressionArr.length;
                        while (i < length3) {
                            this.arguments[i].resolveType(blockScope);
                            i++;
                        }
                    }
                    return null;
                }
            }
            Expression[] expressionArr2 = this.arguments;
            if (expressionArr2 != null) {
                this.argumentsHaveErrors = false;
                int length4 = expressionArr2.length;
                this.argumentTypes = new TypeBinding[length4];
                for (int i3 = 0; i3 < length4; i3++) {
                    Expression expression = this.arguments[i3];
                    if (expression instanceof CastExpression) {
                        expression.bits |= 32;
                        this.argsContainCast = true;
                    }
                    expression.setExpressionContext(ExpressionContext.INVOCATION_CONTEXT);
                    if (this.arguments[i3].resolvedType != null) {
                        blockScope.problemReporter().genericInferenceError("Argument was unexpectedly found resolved", this);
                    }
                    TypeBinding[] typeBindingArr2 = this.argumentTypes;
                    TypeBinding typeBindingResolveType2 = expression.resolveType(blockScope);
                    typeBindingArr2[i3] = typeBindingResolveType2;
                    if (typeBindingResolveType2 == null) {
                        this.argumentsHaveErrors = true;
                    }
                }
                if (this.argumentsHaveErrors) {
                    if (z) {
                        return null;
                    }
                    if (this.resolvedType instanceof ReferenceBinding) {
                        TypeBinding[] typeBindingArr3 = new TypeBinding[length4];
                        while (true) {
                            length4--;
                            if (length4 < 0) {
                                break;
                            }
                            TypeBinding typeBinding = this.argumentTypes[length4];
                            if (typeBinding == null) {
                                typeBinding = TypeBinding.NULL;
                            }
                            typeBindingArr3[length4] = typeBinding;
                        }
                        MethodBinding methodBindingFindMethod = blockScope.findMethod((ReferenceBinding) this.resolvedType, TypeConstants.INIT, typeBindingArr3, this, false);
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
                    }
                    return this.resolvedType;
                }
            }
            if (this.resolvedType == null || !this.resolvedType.isValidBinding()) {
                return null;
            }
            if (this.type != null && !this.resolvedType.canBeInstantiated()) {
                blockScope.problemReporter().cannotInstantiate(this.type, this.resolvedType);
                return this.resolvedType;
            }
        }
        if (z) {
            MethodBinding methodBindingInferConstructorOfElidedParameterizedType = inferConstructorOfElidedParameterizedType(blockScope);
            this.binding = methodBindingInferConstructorOfElidedParameterizedType;
            if (methodBindingInferConstructorOfElidedParameterizedType == null || !methodBindingInferConstructorOfElidedParameterizedType.isValidBinding()) {
                blockScope.problemReporter().cannotInferElidedTypes(this);
                this.resolvedType = null;
                return null;
            }
            if (this.typeExpected == null && compilerOptions.sourceLevel >= ClassFileConstants.JDK1_8 && this.expressionContext.definesTargetType()) {
                return new PolyTypeBinding(this);
            }
            TypeReference typeReference4 = this.type;
            ReferenceBinding referenceBinding = this.binding.declaringClass;
            typeReference4.resolvedType = referenceBinding;
            this.resolvedType = referenceBinding;
            resolvePolyExpressionArguments(this, this.binding, this.argumentTypes, blockScope);
        } else {
            this.binding = findConstructorBinding(blockScope, this, (ReferenceBinding) this.resolvedType, this.argumentTypes);
        }
        if (!this.binding.isValidBinding()) {
            if (this.binding.declaringClass == null) {
                this.binding.declaringClass = (ReferenceBinding) this.resolvedType;
            }
            TypeReference typeReference5 = this.type;
            if (typeReference5 != null && !typeReference5.resolvedType.isValidBinding()) {
                return null;
            }
            blockScope.problemReporter().invalidConstructor(this, this.binding);
            return this.resolvedType;
        }
        if ((this.binding.tagBits & 128) != 0) {
            blockScope.problemReporter().missingTypeInConstructor(this, this.binding);
        }
        if (isMethodUseDeprecated(this.binding, blockScope, true)) {
            blockScope.problemReporter().deprecatedMethod(this.binding, this);
        }
        if (checkInvocationArguments(blockScope, null, this.resolvedType, this.binding, this.arguments, this.argumentTypes, this.argsContainCast, this)) {
            this.bits |= 65536;
        }
        if (this.typeArguments != null && this.binding.original().typeVariables == Binding.NO_TYPE_VARIABLES) {
            blockScope.problemReporter().unnecessaryTypeArgumentsForMethodInvocation(this.binding, this.genericTypeArguments, this.typeArguments);
        }
        if (!z && this.resolvedType.isParameterizedTypeWithActualArguments()) {
            checkTypeArgumentRedundancy((ParameterizedTypeBinding) this.resolvedType, blockScope);
        }
        if (compilerOptions.isAnnotationBasedNullAnalysisEnabled) {
            if ((this.binding.tagBits & 4096) == 0) {
                new ImplicitNullAnnotationVerifier(blockScope.environment(), compilerOptions.inheritNullAnnotations).checkImplicitNullAnnotations(this.binding, null, false, blockScope);
            }
            if (compilerOptions.sourceLevel >= ClassFileConstants.JDK1_8) {
                MethodBinding methodBinding = this.binding;
                if ((methodBinding instanceof ParameterizedGenericMethodBinding) && this.typeArguments != null) {
                    TypeVariableBinding[] typeVariableBindingArrTypeVariables = methodBinding.original().typeVariables();
                    while (true) {
                        TypeReference[] typeReferenceArr2 = this.typeArguments;
                        if (i >= typeReferenceArr2.length) {
                            break;
                        }
                        typeReferenceArr2[i].checkNullConstraints(blockScope, (ParameterizedGenericMethodBinding) this.binding, typeVariableBindingArrTypeVariables, i);
                        i++;
                    }
                }
            }
        }
        if (compilerOptions.sourceLevel >= ClassFileConstants.JDK1_8 && this.binding.getTypeAnnotations() != Binding.NO_ANNOTATIONS) {
            this.resolvedType = blockScope.environment().createAnnotatedType(this.resolvedType, this.binding.getTypeAnnotations());
        }
        return this.resolvedType;
    }

    void checkIllegalNullAnnotation(BlockScope blockScope, TypeBinding typeBinding) {
        Annotation annotationFindAnnotation;
        if (typeBinding != null) {
            long j = typeBinding.tagBits & TagBits.AnnotationNullMASK;
            if (j == 0 || (annotationFindAnnotation = this.type.findAnnotation(j)) == null) {
                return;
            }
            blockScope.problemReporter().nullAnnotationUnsupportedLocation(annotationFindAnnotation);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isBoxingCompatibleWith(TypeBinding typeBinding, Scope scope) {
        if (isPolyExpression()) {
            return false;
        }
        return isCompatibleWith(scope.boxing(typeBinding), scope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isCompatibleWith(TypeBinding typeBinding, Scope scope) {
        MethodBinding methodBinding;
        if (!this.argumentsHaveErrors && (methodBinding = this.binding) != null && methodBinding.isValidBinding() && typeBinding != null && scope != null) {
            TypeBinding typeBinding2 = this.resolvedType;
            if (isPolyExpression()) {
                TypeBinding typeBinding3 = this.typeExpected;
                try {
                    HashMap<TypeBinding, MethodBinding> map = this.solutionsPerTargetType;
                    MethodBinding methodBindingInferConstructorOfElidedParameterizedType = map != null ? map.get(typeBinding) : null;
                    if (methodBindingInferConstructorOfElidedParameterizedType == null) {
                        this.typeExpected = typeBinding;
                        methodBindingInferConstructorOfElidedParameterizedType = inferConstructorOfElidedParameterizedType(scope);
                        if (methodBindingInferConstructorOfElidedParameterizedType == null || !methodBindingInferConstructorOfElidedParameterizedType.isValidBinding()) {
                            this.typeExpected = typeBinding3;
                            return false;
                        }
                    }
                    ReferenceBinding referenceBinding = methodBindingInferConstructorOfElidedParameterizedType.declaringClass;
                    this.typeExpected = typeBinding3;
                    typeBinding2 = referenceBinding;
                } catch (Throwable th) {
                    this.typeExpected = typeBinding3;
                    throw th;
                }
            }
            if (typeBinding2 != null && typeBinding2.isCompatibleWith(typeBinding, scope)) {
                return true;
            }
        }
        return false;
    }

    public MethodBinding inferConstructorOfElidedParameterizedType(Scope scope) {
        TypeBinding typeBinding = this.typeExpected;
        if (typeBinding != null && this.binding != null) {
            HashMap<TypeBinding, MethodBinding> map = this.solutionsPerTargetType;
            MethodBinding methodBinding = map != null ? map.get(typeBinding) : null;
            if (methodBinding != null) {
                return methodBinding;
            }
        }
        ReferenceBinding referenceBindingGenericType = ((ParameterizedTypeBinding) this.resolvedType).genericType();
        ReferenceBinding referenceBindingEnclosingType = this.resolvedType.enclosingType();
        MethodBinding staticFactory = scope.getStaticFactory(scope.environment().createParameterizedType(referenceBindingGenericType, referenceBindingGenericType.typeVariables(), referenceBindingEnclosingType), referenceBindingEnclosingType, this.argumentTypes, this);
        if (!(staticFactory instanceof ParameterizedGenericMethodBinding) || !staticFactory.isValidBinding()) {
            return null;
        }
        ParameterizedGenericMethodBinding parameterizedGenericMethodBinding = (ParameterizedGenericMethodBinding) staticFactory;
        this.inferredReturnType = parameterizedGenericMethodBinding.inferredReturnType;
        SyntheticFactoryMethodBinding syntheticFactoryMethodBinding = (SyntheticFactoryMethodBinding) staticFactory.original();
        TypeVariableBinding[] typeVariableBindingArrTypeVariables = syntheticFactoryMethodBinding.getConstructor().typeVariables();
        TypeBinding[] typeBindingArr = typeVariableBindingArrTypeVariables != null ? new TypeBinding[typeVariableBindingArrTypeVariables.length] : Binding.NO_TYPES;
        if (typeBindingArr.length > 0) {
            System.arraycopy(parameterizedGenericMethodBinding.typeArguments, syntheticFactoryMethodBinding.typeVariables().length - typeBindingArr.length, typeBindingArr, 0, typeBindingArr.length);
        }
        MethodBinding methodBindingApplyTypeArgumentsOnConstructor = syntheticFactoryMethodBinding.applyTypeArgumentsOnConstructor(((ParameterizedTypeBinding) staticFactory.returnType).arguments, typeBindingArr, parameterizedGenericMethodBinding.inferredWithUncheckedConversion);
        if ((methodBindingApplyTypeArgumentsOnConstructor instanceof ParameterizedGenericMethodBinding) && scope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_8 && this.expressionContext == ExpressionContext.INVOCATION_CONTEXT && this.typeExpected == null) {
            methodBindingApplyTypeArgumentsOnConstructor = ParameterizedGenericMethodBinding.computeCompatibleMethod18(methodBindingApplyTypeArgumentsOnConstructor.shallowOriginal(), this.argumentTypes, scope, this);
        }
        TypeBinding typeBinding2 = this.typeExpected;
        if (typeBinding2 != null) {
            registerResult(typeBinding2, methodBindingApplyTypeArgumentsOnConstructor);
        }
        return methodBindingApplyTypeArgumentsOnConstructor;
    }

    public TypeBinding[] inferElidedTypes(Scope scope) {
        ReferenceBinding referenceBindingGenericType = ((ParameterizedTypeBinding) this.resolvedType).genericType();
        ReferenceBinding referenceBindingEnclosingType = this.resolvedType.enclosingType();
        MethodBinding staticFactory = scope.getStaticFactory(scope.environment().createParameterizedType(referenceBindingGenericType, referenceBindingGenericType.typeVariables(), referenceBindingEnclosingType), referenceBindingEnclosingType, this.argumentTypes, this);
        if (!(staticFactory instanceof ParameterizedGenericMethodBinding) || !staticFactory.isValidBinding()) {
            return null;
        }
        this.inferredReturnType = ((ParameterizedGenericMethodBinding) staticFactory).inferredReturnType;
        return ((ParameterizedTypeBinding) staticFactory.returnType).arguments;
    }

    public void checkTypeArgumentRedundancy(ParameterizedTypeBinding parameterizedTypeBinding, BlockScope blockScope) {
        if (blockScope.problemReporter().computeSeverity(IProblem.RedundantSpecificationOfTypeArguments) == 256 || blockScope.compilerOptions().sourceLevel < ClassFileConstants.JDK1_7 || parameterizedTypeBinding.arguments == null || this.genericTypeArguments != null || this.type == null) {
            return;
        }
        if (this.argumentTypes == Binding.NO_PARAMETERS) {
            TypeBinding typeBinding = this.typeExpected;
            if (typeBinding instanceof ParameterizedTypeBinding) {
                ParameterizedTypeBinding parameterizedTypeBinding2 = (ParameterizedTypeBinding) typeBinding;
                if (parameterizedTypeBinding2.arguments != null && parameterizedTypeBinding.arguments.length == parameterizedTypeBinding2.arguments.length) {
                    int i = 0;
                    while (i < parameterizedTypeBinding.arguments.length && !TypeBinding.notEquals(parameterizedTypeBinding.arguments[i], parameterizedTypeBinding2.arguments[i])) {
                        i++;
                    }
                    if (i == parameterizedTypeBinding.arguments.length) {
                        blockScope.problemReporter().redundantSpecificationOfTypeArguments(this.type, parameterizedTypeBinding.arguments);
                        return;
                    }
                }
            }
        }
        int i2 = this.type.bits;
        try {
            this.type.bits |= 524288;
            TypeBinding[] typeBindingArrInferElidedTypes = inferElidedTypes(blockScope);
            this.type.bits = i2;
            if (typeBindingArrInferElidedTypes == null) {
                return;
            }
            for (int i3 = 0; i3 < typeBindingArrInferElidedTypes.length; i3++) {
                if (TypeBinding.notEquals(typeBindingArrInferElidedTypes[i3], parameterizedTypeBinding.arguments[i3])) {
                    return;
                }
            }
            blockScope.problemReporter().redundantSpecificationOfTypeArguments(this.type, parameterizedTypeBinding.arguments);
        } catch (Throwable th) {
            this.type.bits = i2;
            throw th;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            TypeReference[] typeReferenceArr = this.typeArguments;
            if (typeReferenceArr != null) {
                int length = typeReferenceArr.length;
                for (int i = 0; i < length; i++) {
                    this.typeArguments[i].traverse(aSTVisitor, blockScope);
                }
            }
            TypeReference typeReference = this.type;
            if (typeReference != null) {
                typeReference.traverse(aSTVisitor, blockScope);
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

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public void setExpectedType(TypeBinding typeBinding) {
        this.typeExpected = typeBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public void setExpressionContext(ExpressionContext expressionContext) {
        this.expressionContext = expressionContext;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isPolyExpression() {
        return isPolyExpression(this.binding);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isPolyExpression(MethodBinding methodBinding) {
        TypeReference typeReference;
        return ((this.expressionContext != ExpressionContext.ASSIGNMENT_CONTEXT && this.expressionContext != ExpressionContext.INVOCATION_CONTEXT) || (typeReference = this.type) == null || (typeReference.bits & 524288) == 0) ? false : true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement, org.eclipse.jdt.internal.compiler.ast.IPolyExpression, org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public TypeBinding invocationTargetType() {
        return this.typeExpected;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public boolean statementExpression() {
        return (this.bits & ASTNode.ParenthesizedMASK) == 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Invocation
    public MethodBinding binding() {
        return this.binding;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Invocation
    public Expression[] arguments() {
        return this.arguments;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Invocation
    public void registerInferenceContext(ParameterizedGenericMethodBinding parameterizedGenericMethodBinding, InferenceContext18 inferenceContext18) {
        if (this.inferenceContexts == null) {
            this.inferenceContexts = new SimpleLookupTable();
        }
        this.inferenceContexts.put(parameterizedGenericMethodBinding, inferenceContext18);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Invocation
    public void registerResult(TypeBinding typeBinding, MethodBinding methodBinding) {
        if (methodBinding == null || !methodBinding.isConstructor()) {
            return;
        }
        if (this.solutionsPerTargetType == null) {
            this.solutionsPerTargetType = new HashMap<>();
        }
        this.solutionsPerTargetType.put(typeBinding, methodBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Invocation
    public InferenceContext18 getInferenceContext(ParameterizedMethodBinding parameterizedMethodBinding) {
        SimpleLookupTable simpleLookupTable = this.inferenceContexts;
        if (simpleLookupTable == null) {
            return null;
        }
        return (InferenceContext18) simpleLookupTable.get(parameterizedMethodBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Invocation
    public void cleanUpInferenceContexts() {
        SimpleLookupTable simpleLookupTable = this.inferenceContexts;
        if (simpleLookupTable == null) {
            return;
        }
        for (Object obj : simpleLookupTable.valueTable) {
            if (obj != null) {
                ((InferenceContext18) obj).cleanUp();
            }
        }
        this.inferenceContexts = null;
        this.outerInferenceContext = null;
        this.solutionsPerTargetType = null;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement, org.eclipse.jdt.internal.compiler.ast.IPolyExpression, org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public ExpressionContext getExpressionContext() {
        return this.expressionContext;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public InferenceContext18 freshInferenceContext(Scope scope) {
        return new InferenceContext18(scope, this.arguments, this, this.outerInferenceContext);
    }
}
