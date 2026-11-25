package org.eclipse.jdt.internal.compiler.ast;

import java.util.HashMap;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.ImplicitNullAnnotationVerifier;
import org.eclipse.jdt.internal.compiler.lookup.InferenceContext18;
import org.eclipse.jdt.internal.compiler.lookup.InferenceVariable;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MissingTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.PackageBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedGenericMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.PolyParameterizedGenericMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.PolyTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.PolymorphicMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.RawTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;
import org.eclipse.jdt.internal.compiler.util.SimpleLookupTable;

/* JADX INFO: loaded from: classes14.dex */
public class MessageSend extends Expression implements IPolyExpression, Invocation {
    private static final int FALSE_ASSERTION = 2;
    private static final int NONNULL_ASSERTION = 4;
    private static final int NULL_ASSERTION = 3;
    private static final int TRUE_ASSERTION = 1;
    public TypeBinding actualReceiverType;
    protected boolean argsContainCast;
    public Expression[] arguments;
    public MethodBinding binding;
    public TypeBinding expectedType;
    public TypeBinding[] genericTypeArguments;
    private SimpleLookupTable inferenceContexts;
    public long nameSourcePosition;
    private InferenceContext18 outerInferenceContext;
    public Expression receiver;
    private boolean receiverIsType;
    public char[] selector;
    private HashMap<TypeBinding, MethodBinding> solutionsPerTargetType;
    public MethodBinding syntheticAccessor;
    public TypeReference[] typeArguments;
    public TypeBinding valueCast;
    public ExpressionContext expressionContext = ExpressionContext.VANILLA_CONTEXT;
    public TypeBinding[] argumentTypes = Binding.NO_PARAMETERS;
    public boolean argumentsHaveErrors = false;

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public void setFieldIndex(int i) {
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        Expression expression;
        FlowInfo flowInfoAnalyseBooleanAssertion;
        boolean zIsStatic = this.binding.isStatic();
        boolean z = !zIsStatic;
        boolean z2 = (flowContext.tagBits & 4096) != 0;
        FlowInfo flowInfoUnconditionalInits = this.receiver.analyseCode(blockScope, flowContext, flowInfo, z).unconditionalInits();
        boolean z3 = blockScope.compilerOptions().analyseResourceLeaks;
        if (z3) {
            if (!zIsStatic) {
                if (CharOperation.equals(TypeConstants.CLOSE, this.selector)) {
                    recordCallingClose(blockScope, flowContext, flowInfoUnconditionalInits, this.receiver);
                }
            } else {
                Expression[] expressionArr = this.arguments;
                if (expressionArr != null && expressionArr.length > 0 && FakedTrackingVariable.isAnyCloseable(expressionArr[0].resolvedType)) {
                    for (int i = 0; i < TypeConstants.closeMethods.length; i++) {
                        TypeConstants.CloseMethodRecord closeMethodRecord = TypeConstants.closeMethods[i];
                        if (CharOperation.equals(closeMethodRecord.selector, this.selector) && CharOperation.equals(closeMethodRecord.typeName, this.binding.declaringClass.compoundName)) {
                            int iMin = Math.min(closeMethodRecord.numCloseableArgs, this.arguments.length);
                            for (int i2 = 0; i2 < iMin; i2++) {
                                recordCallingClose(blockScope, flowContext, flowInfoUnconditionalInits, this.arguments[i2]);
                            }
                            break;
                        }
                    }
                }
            }
        }
        if (!zIsStatic) {
            this.receiver.checkNPE(blockScope, flowContext, flowInfoUnconditionalInits, (this.bits & 16) != 0 ? 3 : 2);
        }
        Expression[] expressionArr2 = this.arguments;
        if (expressionArr2 != null) {
            FlowInfo flowInfo2 = flowInfoUnconditionalInits;
            int i3 = 0;
            for (int length = expressionArr2.length; i3 < length; length = length) {
                Expression expression2 = this.arguments[i3];
                expression2.checkNPEbyUnboxing(blockScope, flowContext, flowInfo2);
                int iDetectAssertionUtility = detectAssertionUtility(i3);
                if (iDetectAssertionUtility == 1) {
                    expression = expression2;
                    i3 = i3;
                    flowInfoAnalyseBooleanAssertion = analyseBooleanAssertion(blockScope, expression, flowContext, flowInfo2, z2, true);
                } else if (iDetectAssertionUtility == 2) {
                    expression = expression2;
                    i3 = i3;
                    flowInfoAnalyseBooleanAssertion = analyseBooleanAssertion(blockScope, expression, flowContext, flowInfo2, z2, false);
                } else if (iDetectAssertionUtility == 3) {
                    expression = expression2;
                    flowInfoAnalyseBooleanAssertion = analyseNullAssertion(blockScope, expression, flowContext, flowInfo2, true);
                } else if (iDetectAssertionUtility == 4) {
                    expression = expression2;
                    flowInfoAnalyseBooleanAssertion = analyseNullAssertion(blockScope, expression2, flowContext, flowInfo2, false);
                } else {
                    flowInfoAnalyseBooleanAssertion = expression2.analyseCode(blockScope, flowContext, flowInfo2).unconditionalInits();
                    expression = expression2;
                }
                if (z3) {
                    flowInfoAnalyseBooleanAssertion = FakedTrackingVariable.markPassedToOutside(blockScope, expression, flowInfoAnalyseBooleanAssertion, flowContext, false);
                }
                flowInfo2 = flowInfoAnalyseBooleanAssertion;
                i3++;
            }
            analyseArguments(blockScope, flowContext, flowInfo2, this.binding, this.arguments);
            flowInfoUnconditionalInits = flowInfo2;
        }
        ReferenceBinding[] referenceBindingArrConvertToRawTypes = this.binding.thrownExceptions;
        if (referenceBindingArrConvertToRawTypes != Binding.NO_EXCEPTIONS) {
            if ((this.bits & 65536) != 0 && this.genericTypeArguments == null) {
                referenceBindingArrConvertToRawTypes = blockScope.environment().convertToRawTypes(this.binding.thrownExceptions, true, true);
            }
            flowContext.checkExceptionHandlers(referenceBindingArrConvertToRawTypes, this, flowInfoUnconditionalInits.copy(), blockScope);
        }
        manageSyntheticAccessIfNecessary(blockScope, flowInfoUnconditionalInits);
        flowContext.recordAbruptExit();
        flowContext.expireNullCheckedFieldInfo();
        return flowInfoUnconditionalInits;
    }

    private void recordCallingClose(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, Expression expression) {
        FakedTrackingVariable closeTrackingVariable = FakedTrackingVariable.getCloseTrackingVariable(expression, flowInfo, flowContext);
        if (closeTrackingVariable != null) {
            if (closeTrackingVariable.methodScope == blockScope.methodScope()) {
                closeTrackingVariable.markClose(flowInfo, flowContext);
            } else {
                closeTrackingVariable.markClosedInNestedMethod();
            }
        }
    }

    private int detectAssertionUtility(int i) {
        TypeBinding[] typeBindingArr = this.binding.original().parameters;
        if (i >= typeBindingArr.length) {
            return 0;
        }
        TypeBinding typeBinding = typeBindingArr[i];
        ReferenceBinding referenceBinding = this.binding.declaringClass;
        if (referenceBinding == null || typeBinding == null) {
            return 0;
        }
        switch (referenceBinding.f626id) {
            case 68:
                if (typeBinding.f626id == 5) {
                    return 1;
                }
                return (typeBinding.f626id == 1 && CharOperation.equals(TypeConstants.IS_NOTNULL, this.selector)) ? 4 : 0;
            case 69:
            case 70:
                if (typeBinding.f626id == 5) {
                    if (CharOperation.equals(TypeConstants.ASSERT_TRUE, this.selector)) {
                        return 1;
                    }
                    return CharOperation.equals(TypeConstants.ASSERT_FALSE, this.selector) ? 2 : 0;
                }
                if (typeBinding.f626id != 1) {
                    return 0;
                }
                if (CharOperation.equals(TypeConstants.ASSERT_NOTNULL, this.selector)) {
                    return 4;
                }
                return CharOperation.equals(TypeConstants.ASSERT_NULL, this.selector) ? 3 : 0;
            case 71:
                if (typeBinding.f626id == 5) {
                    return CharOperation.equals(TypeConstants.IS_TRUE, this.selector) ? 1 : 0;
                }
                return (typeBinding.f626id == 1 && CharOperation.equals(TypeConstants.NOT_NULL, this.selector)) ? 4 : 0;
            case 72:
                if (typeBinding.f626id == 5) {
                    return CharOperation.equals(TypeConstants.IS_TRUE, this.selector) ? 1 : 0;
                }
                return (typeBinding.isTypeVariable() && CharOperation.equals(TypeConstants.NOT_NULL, this.selector)) ? 4 : 0;
            case 73:
                if (typeBinding.f626id == 5) {
                    return (CharOperation.equals(TypeConstants.CHECK_ARGUMENT, this.selector) || CharOperation.equals(TypeConstants.CHECK_STATE, this.selector)) ? 1 : 0;
                }
                return (typeBinding.isTypeVariable() && CharOperation.equals(TypeConstants.CHECK_NOT_NULL, this.selector)) ? 4 : 0;
            case 74:
                return (typeBinding.isTypeVariable() && CharOperation.equals(TypeConstants.REQUIRE_NON_NULL, this.selector)) ? 4 : 0;
            default:
                return 0;
        }
    }

    private FlowInfo analyseBooleanAssertion(BlockScope blockScope, Expression expression, FlowContext flowContext, FlowInfo flowInfo, boolean z, boolean z2) {
        UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits;
        FlowInfo flowInfoInitsWhenTrue;
        Constant constantOptimizedBooleanConstant = expression.optimizedBooleanConstant();
        boolean z3 = false;
        boolean z4 = constantOptimizedBooleanConstant != Constant.NotAConstant && constantOptimizedBooleanConstant.booleanValue();
        if (constantOptimizedBooleanConstant != Constant.NotAConstant && !constantOptimizedBooleanConstant.booleanValue()) {
            z3 = true;
        }
        int i = flowContext.tagBits;
        flowContext.tagBits |= 4096;
        if (!z2) {
            flowContext.tagBits |= 4;
        }
        FlowInfo flowInfoAnalyseCode = expression.analyseCode(blockScope, flowContext, flowInfo.copy());
        flowContext.extendTimeToLiveForNullCheckedField(2);
        flowContext.tagBits = i;
        if (z2) {
            unconditionalFlowInfoUnconditionalInits = flowInfoAnalyseCode.initsWhenTrue().unconditionalInits();
            flowInfoInitsWhenTrue = flowInfoAnalyseCode.initsWhenFalse();
        } else {
            unconditionalFlowInfoUnconditionalInits = flowInfoAnalyseCode.initsWhenFalse().unconditionalInits();
            flowInfoInitsWhenTrue = flowInfoAnalyseCode.initsWhenTrue();
            boolean z5 = z3;
            z3 = z4;
            z4 = z5;
        }
        if (z4) {
            flowInfoInitsWhenTrue.setReachMode(1);
        }
        return !z3 ? flowInfo.mergedWith(flowInfoInitsWhenTrue.nullInfoLessUnconditionalCopy()).addInitializationsFrom(unconditionalFlowInfoUnconditionalInits.discardInitializationInfo()) : flowInfo;
    }

    private FlowInfo analyseNullAssertion(BlockScope blockScope, Expression expression, FlowContext flowContext, FlowInfo flowInfo, boolean z) {
        Reference reference;
        FieldBinding fieldBindingLastFieldBinding;
        boolean z2 = expression.nullStatus(flowInfo, flowContext) == (z ? 4 : 2);
        UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits = expression.analyseCode(blockScope, flowContext, flowInfo).unconditionalInits();
        LocalVariableBinding localVariableBinding = expression.localVariableBinding();
        if (localVariableBinding != null) {
            if (z) {
                unconditionalFlowInfoUnconditionalInits.markAsDefinitelyNull(localVariableBinding);
            } else {
                unconditionalFlowInfoUnconditionalInits.markAsDefinitelyNonNull(localVariableBinding);
            }
        } else if (!z && (expression instanceof Reference) && blockScope.compilerOptions().enableSyntacticNullAnalysisForFields && (fieldBindingLastFieldBinding = (reference = (Reference) expression).lastFieldBinding()) != null && (fieldBindingLastFieldBinding.type.tagBits & 2) == 0) {
            flowContext.recordNullCheckedFieldReference(reference, 3);
        }
        if (z2) {
            unconditionalFlowInfoUnconditionalInits.setReachMode(2);
        }
        return unconditionalFlowInfoUnconditionalInits;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public boolean checkNPE(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, int i) {
        int iNullStatus = nullStatus(flowInfo, flowContext);
        if ((iNullStatus & 16) != 0) {
            if (this.binding.returnType.isTypeVariable() && iNullStatus == 48 && blockScope.environment().globalOptions.pessimisticNullAnalysisForFreeTypeVariablesEnabled) {
                blockScope.problemReporter().methodReturnTypeFreeTypeVariableReference(this.binding, this);
                return true;
            }
            blockScope.problemReporter().messageSendPotentialNullReference(this.binding, this);
            return true;
        }
        if ((this.resolvedType.tagBits & 72057594037927936L) == 0) {
            return true;
        }
        NullAnnotationMatching nullAnnotationMatchingOkNonNullStatus = NullAnnotationMatching.okNonNullStatus(this);
        if (!nullAnnotationMatchingOkNonNullStatus.wantToReport()) {
            return true;
        }
        nullAnnotationMatchingOkNonNullStatus.report(blockScope);
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void computeConversion(Scope scope, TypeBinding typeBinding, TypeBinding typeBinding2) {
        if (typeBinding == null || typeBinding2 == null) {
            return;
        }
        MethodBinding methodBinding = this.binding;
        if (methodBinding != null && methodBinding.isValidBinding()) {
            TypeBinding typeBinding3 = this.binding.original().returnType;
            if (typeBinding3.leafComponentType().isTypeVariable()) {
                this.valueCast = typeBinding3.genericCast((typeBinding2.isBaseType() || !typeBinding.isBaseType()) ? typeBinding : typeBinding2);
            } else if (this.binding == scope.environment().arrayClone && typeBinding.f626id != 1 && scope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_5) {
                this.valueCast = typeBinding;
            }
            TypeBinding typeBinding4 = this.valueCast;
            if (typeBinding4 instanceof ReferenceBinding) {
                ReferenceBinding referenceBinding = (ReferenceBinding) typeBinding4;
                if (!referenceBinding.canBeSeenBy(scope)) {
                    scope.problemReporter().invalidType(this, new ProblemReferenceBinding(CharOperation.splitOn('.', referenceBinding.shortReadableName()), referenceBinding, 2));
                }
            }
        }
        super.computeConversion(scope, typeBinding, typeBinding2);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        cleanUpInferenceContexts();
        int i = codeStream.position;
        MethodBinding methodBindingOriginal = this.binding;
        if (!(methodBindingOriginal instanceof PolymorphicMethodBinding)) {
            methodBindingOriginal = methodBindingOriginal.original();
        }
        boolean zIsStatic = methodBindingOriginal.isStatic();
        if (zIsStatic) {
            this.receiver.generateCode(blockScope, codeStream, false);
        } else if ((this.bits & ASTNode.DepthMASK) != 0 && this.receiver.isImplicitThis()) {
            ReferenceBinding referenceBindingEnclosingTypeAt = blockScope.enclosingSourceType().enclosingTypeAt((this.bits & ASTNode.DepthMASK) >> 5);
            codeStream.generateOuterAccess(blockScope.getEmulationPath(referenceBindingEnclosingTypeAt, true, false), this, referenceBindingEnclosingTypeAt, blockScope);
        } else {
            this.receiver.generateCode(blockScope, codeStream, true);
            if ((this.bits & 262144) != 0) {
                codeStream.checkcast(this.actualReceiverType);
            }
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
        generateArguments(this.binding, this.arguments, blockScope, codeStream);
        int i2 = codeStream.position;
        MethodBinding methodBinding = this.syntheticAccessor;
        if (methodBinding == null) {
            TypeBinding constantPoolDeclaringClass = CodeStream.getConstantPoolDeclaringClass(blockScope, methodBindingOriginal, this.actualReceiverType, this.receiver.isImplicitThis());
            if (zIsStatic) {
                codeStream.invoke(Opcodes.OPC_invokestatic, methodBindingOriginal, constantPoolDeclaringClass, this.typeArguments);
            } else if (this.receiver.isSuper() || methodBindingOriginal.isPrivate()) {
                codeStream.invoke(Opcodes.OPC_invokespecial, methodBindingOriginal, constantPoolDeclaringClass, this.typeArguments);
            } else if (constantPoolDeclaringClass.isInterface()) {
                codeStream.invoke(Opcodes.OPC_invokeinterface, methodBindingOriginal, constantPoolDeclaringClass, this.typeArguments);
            } else {
                codeStream.invoke(Opcodes.OPC_invokevirtual, methodBindingOriginal, constantPoolDeclaringClass, this.typeArguments);
            }
        } else {
            codeStream.invoke(Opcodes.OPC_invokestatic, methodBinding, null, this.typeArguments);
        }
        TypeBinding typeBinding = this.valueCast;
        if (typeBinding != null) {
            codeStream.checkcast(typeBinding);
        }
        if (z) {
            codeStream.generateImplicitConversion(this.implicitConversion);
        } else {
            boolean z2 = (this.implicitConversion & 1024) != 0;
            if (z2) {
                codeStream.generateImplicitConversion(this.implicitConversion);
            }
            int i3 = (z2 ? postConversionType(blockScope) : methodBindingOriginal.returnType).f626id;
            if (i3 != 6) {
                if (i3 == 7 || i3 == 8) {
                    codeStream.pop2();
                } else {
                    codeStream.pop();
                }
            }
        }
        codeStream.recordPositionsFrom(i2, (int) (this.nameSourcePosition >>> 32));
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public TypeBinding[] genericTypeArguments() {
        return this.genericTypeArguments;
    }

    public boolean isSuperAccess() {
        return this.receiver.isSuper();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean isTypeAccess() {
        Expression expression = this.receiver;
        return expression != null && expression.isTypeReference();
    }

    public void manageSyntheticAccessIfNecessary(BlockScope blockScope, FlowInfo flowInfo) {
        if ((flowInfo.tagBits & 1) != 0) {
            return;
        }
        MethodBinding methodBindingOriginal = this.binding.original();
        if (this.binding.isPrivate()) {
            if (TypeBinding.notEquals(blockScope.enclosingSourceType(), methodBindingOriginal.declaringClass)) {
                this.syntheticAccessor = ((SourceTypeBinding) methodBindingOriginal.declaringClass).addSyntheticMethod(methodBindingOriginal, false);
                blockScope.problemReporter().needToEmulateMethodAccess(methodBindingOriginal, this);
                return;
            }
            return;
        }
        if (this.receiver instanceof QualifiedSuperReference) {
            if (this.actualReceiverType.isInterface()) {
                return;
            }
            this.syntheticAccessor = ((SourceTypeBinding) ((QualifiedSuperReference) this.receiver).currentCompatibleType).addSyntheticMethod(methodBindingOriginal, isSuperAccess());
            blockScope.problemReporter().needToEmulateMethodAccess(methodBindingOriginal, this);
            return;
        }
        if (!this.binding.isProtected() || (this.bits & ASTNode.DepthMASK) == 0) {
            return;
        }
        PackageBinding packageBinding = methodBindingOriginal.declaringClass.getPackage();
        SourceTypeBinding sourceTypeBindingEnclosingSourceType = blockScope.enclosingSourceType();
        if (packageBinding != sourceTypeBindingEnclosingSourceType.getPackage()) {
            this.syntheticAccessor = ((SourceTypeBinding) sourceTypeBindingEnclosingSourceType.enclosingTypeAt((this.bits & ASTNode.DepthMASK) >> 5)).addSyntheticMethod(methodBindingOriginal, isSuperAccess());
            blockScope.problemReporter().needToEmulateMethodAccess(methodBindingOriginal, this);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public int nullStatus(FlowInfo flowInfo, FlowContext flowContext) {
        if ((this.implicitConversion & 512) != 0) {
            return 4;
        }
        if (!this.binding.isValidBinding()) {
            return 1;
        }
        long j = this.binding.tagBits;
        if ((j & TagBits.AnnotationNullMASK) == 0) {
            j = this.binding.returnType.tagBits & TagBits.AnnotationNullMASK;
        }
        if (j == 0 && this.binding.returnType.isFreeTypeVariable()) {
            return 48;
        }
        return FlowInfo.tagBitsToNullStatus(j);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding postConversionType(Scope scope) {
        TypeBinding typeBinding = this.resolvedType;
        TypeBinding typeBinding2 = this.valueCast;
        if (typeBinding2 != null) {
            typeBinding = typeBinding2;
        }
        switch ((this.implicitConversion & 255) >> 4) {
            case 2:
                typeBinding = TypeBinding.CHAR;
                break;
            case 3:
                typeBinding = TypeBinding.BYTE;
                break;
            case 4:
                typeBinding = TypeBinding.SHORT;
                break;
            case 5:
                typeBinding = TypeBinding.BOOLEAN;
                break;
            case 7:
                typeBinding = TypeBinding.LONG;
                break;
            case 8:
                typeBinding = TypeBinding.DOUBLE;
                break;
            case 9:
                typeBinding = TypeBinding.FLOAT;
                break;
            case 10:
                typeBinding = TypeBinding.INT;
                break;
        }
        return (this.implicitConversion & 512) != 0 ? scope.environment().computeBoxingType(typeBinding) : typeBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        if (!this.receiver.isImplicitThis()) {
            this.receiver.printExpression(0, stringBuffer).append('.');
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
        stringBuffer.append(this.selector).append('(');
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

    /* JADX WARN: Code duplicated, block: B:145:0x0229  */
    /* JADX WARN: Code duplicated, block: B:148:0x0230 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:149:0x0232  */
    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        TypeBinding typeBindingCapture;
        boolean z;
        MethodBinding methodBindingFindMethod;
        MethodBinding methodBindingCreateParameterizedGenericMethod;
        int i = 0;
        if (this.constant != Constant.NotAConstant) {
            this.constant = Constant.NotAConstant;
            long j = blockScope.compilerOptions().sourceLevel;
            Expression expression = this.receiver;
            if (expression instanceof CastExpression) {
                expression.bits |= 32;
                z = true;
            } else {
                z = false;
            }
            TypeBinding typeBindingResolveType = this.receiver.resolveType(blockScope);
            this.actualReceiverType = typeBindingResolveType;
            if (typeBindingResolveType instanceof InferenceVariable) {
                return null;
            }
            Expression expression2 = this.receiver;
            this.receiverIsType = (expression2 instanceof NameReference) && (((NameReference) expression2).bits & 4) != 0;
            if (z && this.actualReceiverType != null && TypeBinding.equalsEquals(((CastExpression) this.receiver).expression.resolvedType, this.actualReceiverType)) {
                blockScope.problemReporter().unnecessaryCast((CastExpression) this.receiver);
            }
            TypeReference[] typeReferenceArr = this.typeArguments;
            if (typeReferenceArr != null) {
                int length = typeReferenceArr.length;
                this.argumentsHaveErrors = j < ClassFileConstants.JDK1_5;
                this.genericTypeArguments = new TypeBinding[length];
                for (int i2 = 0; i2 < length; i2++) {
                    TypeReference typeReference = this.typeArguments[i2];
                    TypeBinding[] typeBindingArr = this.genericTypeArguments;
                    TypeBinding typeBindingResolveType2 = typeReference.resolveType(blockScope, true);
                    typeBindingArr[i2] = typeBindingResolveType2;
                    if (typeBindingResolveType2 == null) {
                        this.argumentsHaveErrors = true;
                    }
                    if (this.argumentsHaveErrors && (typeReference instanceof Wildcard)) {
                        blockScope.problemReporter().illegalUsageOfWildcard(typeReference);
                    }
                }
                if (this.argumentsHaveErrors) {
                    Expression[] expressionArr = this.arguments;
                    if (expressionArr != null) {
                        int length2 = expressionArr.length;
                        while (i < length2) {
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
                int length3 = expressionArr2.length;
                this.argumentTypes = new TypeBinding[length3];
                for (int i3 = 0; i3 < length3; i3++) {
                    Expression expression3 = this.arguments[i3];
                    if (expression3.resolvedType != null) {
                        blockScope.problemReporter().genericInferenceError("Argument was unexpectedly found resolved", this);
                    }
                    if (expression3 instanceof CastExpression) {
                        expression3.bits |= 32;
                        this.argsContainCast = true;
                    }
                    expression3.setExpressionContext(ExpressionContext.INVOCATION_CONTEXT);
                    TypeBinding[] typeBindingArr2 = this.argumentTypes;
                    TypeBinding typeBindingResolveType3 = expression3.resolveType(blockScope);
                    typeBindingArr2[i3] = typeBindingResolveType3;
                    if (typeBindingResolveType3 == null) {
                        this.argumentsHaveErrors = true;
                    }
                }
                if (this.argumentsHaveErrors) {
                    if (this.actualReceiverType instanceof ReferenceBinding) {
                        TypeBinding[] typeBindingArr3 = new TypeBinding[length3];
                        while (true) {
                            length3--;
                            if (length3 < 0) {
                                break;
                            }
                            TypeBinding typeBinding = this.argumentTypes[length3];
                            if (typeBinding == null) {
                                typeBinding = TypeBinding.NULL;
                            }
                            typeBindingArr3[length3] = typeBinding;
                        }
                        if (this.receiver.isImplicitThis()) {
                            methodBindingFindMethod = blockScope.getImplicitMethod(this.selector, typeBindingArr3, this);
                        } else {
                            methodBindingFindMethod = blockScope.findMethod((ReferenceBinding) this.actualReceiverType, this.selector, typeBindingArr3, this, false);
                        }
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
                    return null;
                }
            }
            TypeBinding typeBinding2 = this.actualReceiverType;
            if (typeBinding2 == null) {
                return null;
            }
            if (typeBinding2.isBaseType()) {
                blockScope.problemReporter().errorNoMethodFor(this, this.actualReceiverType, this.argumentTypes);
                return null;
            }
        }
        TypeBinding typeBindingFindMethodBinding = findMethodBinding(blockScope);
        if (typeBindingFindMethodBinding != null && typeBindingFindMethodBinding.isPolyType()) {
            this.resolvedType = this.binding.returnType.capture(blockScope, this.sourceStart, this.sourceEnd);
            return typeBindingFindMethodBinding;
        }
        if (!this.binding.isValidBinding()) {
            if (this.binding.declaringClass == null) {
                TypeBinding typeBinding3 = this.actualReceiverType;
                if (typeBinding3 instanceof ReferenceBinding) {
                    this.binding.declaringClass = (ReferenceBinding) typeBinding3;
                } else {
                    blockScope.problemReporter().errorNoMethodFor(this, this.actualReceiverType, this.argumentTypes);
                    return null;
                }
            }
            ReferenceBinding referenceBinding = this.binding.declaringClass;
            if (referenceBinding == null || !referenceBinding.isAnonymousType() || !(referenceBinding.superclass() instanceof MissingTypeBinding)) {
                blockScope.problemReporter().invalidMethod(this, this.binding, blockScope);
            }
            MethodBinding methodBinding = ((ProblemMethodBinding) this.binding).closestMatch;
            int iProblemId = this.binding.problemId();
            if (iProblemId == 2 || iProblemId == 10) {
                if (methodBinding != null) {
                    this.resolvedType = methodBinding.returnType;
                }
            } else if (iProblemId == 23) {
                if (this.expressionContext == ExpressionContext.INVOCATION_CONTEXT) {
                    if (methodBinding != null) {
                        this.resolvedType = methodBinding.returnType;
                    }
                }
            } else if (iProblemId != 25) {
                if (iProblemId != 27) {
                    if (iProblemId == 6 || iProblemId == 7 || iProblemId == 8) {
                        if (methodBinding != null) {
                            this.resolvedType = methodBinding.returnType;
                        }
                    }
                } else if (this.expressionContext == ExpressionContext.INVOCATION_CONTEXT) {
                    if (methodBinding != null) {
                        this.resolvedType = methodBinding.returnType;
                    }
                }
            } else if (methodBinding != null && methodBinding.returnType != null) {
                this.resolvedType = methodBinding.returnType.withoutToplevelNullAnnotation();
            }
            if (methodBinding != null) {
                this.binding = methodBinding;
                MethodBinding methodBindingOriginal2 = methodBinding.original();
                if (methodBindingOriginal2.isOrEnclosedByPrivateType() && !blockScope.isDefinedInMethod(methodBindingOriginal2)) {
                    methodBindingOriginal2.modifiers |= 134217728;
                }
            }
            if (this.resolvedType == null || (this.resolvedType.tagBits & 128) != 0) {
                return null;
            }
            return this.resolvedType;
        }
        CompilerOptions compilerOptions = blockScope.compilerOptions();
        if (compilerOptions.complianceLevel <= ClassFileConstants.JDK1_6 && this.binding.isPolymorphic()) {
            blockScope.problemReporter().polymorphicMethodNotBelow17(this);
            return null;
        }
        if (compilerOptions.isAnnotationBasedNullAnalysisEnabled) {
            if ((this.binding.tagBits & 4096) == 0) {
                new ImplicitNullAnnotationVerifier(blockScope.environment(), compilerOptions.inheritNullAnnotations).checkImplicitNullAnnotations(this.binding, null, false, blockScope);
            }
            if (compilerOptions.sourceLevel >= ClassFileConstants.JDK1_8) {
                MethodBinding methodBinding2 = this.binding;
                if ((methodBinding2 instanceof ParameterizedGenericMethodBinding) && this.typeArguments != null) {
                    TypeVariableBinding[] typeVariableBindingArrTypeVariables = methodBinding2.original().typeVariables();
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
        if ((this.bits & 16) != 0 && this.binding.isPolymorphic()) {
            this.binding = blockScope.environment().updatePolymorphicMethodReturnType((PolymorphicMethodBinding) this.binding, TypeBinding.VOID);
        }
        if ((this.binding.tagBits & 128) != 0) {
            blockScope.problemReporter().missingTypeInMethod(this, this.binding);
        }
        if (!this.binding.isStatic()) {
            if (this.receiverIsType) {
                blockScope.problemReporter().mustUseAStaticMethod(this, this.binding);
                if (this.actualReceiverType.isRawType() && (this.receiver.bits & 1073741824) == 0 && compilerOptions.getSeverity(CompilerOptions.RawTypeReference) != 256) {
                    blockScope.problemReporter().rawTypeReference(this.receiver, this.actualReceiverType);
                }
            } else {
                TypeBinding typeBinding4 = this.actualReceiverType;
                TypeBinding erasureCompatibleType = typeBinding4.getErasureCompatibleType(this.binding.declaringClass);
                this.actualReceiverType = erasureCompatibleType;
                this.receiver.computeConversion(blockScope, erasureCompatibleType, erasureCompatibleType);
                if (TypeBinding.notEquals(this.actualReceiverType, typeBinding4) && TypeBinding.notEquals(this.receiver.postConversionType(blockScope), this.actualReceiverType)) {
                    this.bits |= 262144;
                }
            }
        } else {
            if (!this.receiver.isImplicitThis() && !this.receiver.isSuper() && !this.receiverIsType) {
                blockScope.problemReporter().nonStaticAccessToStaticMethod(this, this.binding);
            }
            if (!this.receiver.isImplicitThis() && TypeBinding.notEquals(this.binding.declaringClass, this.actualReceiverType)) {
                blockScope.problemReporter().indirectAccessToStaticMethod(this, this.binding);
            }
        }
        if (checkInvocationArguments(blockScope, this.receiver, this.actualReceiverType, this.binding, this.arguments, this.argumentTypes, this.argsContainCast, this)) {
            this.bits |= 65536;
        }
        if (this.binding.isAbstract() && this.receiver.isSuper()) {
            blockScope.problemReporter().cannotDireclyInvokeAbstractMethod(this, this.binding);
        }
        if (isMethodUseDeprecated(this.binding, blockScope, true)) {
            blockScope.problemReporter().deprecatedMethod(this.binding, this);
        }
        if (this.binding == blockScope.environment().arrayClone && compilerOptions.sourceLevel >= ClassFileConstants.JDK1_5) {
            this.resolvedType = this.actualReceiverType;
        } else {
            if ((this.bits & 65536) != 0 && this.genericTypeArguments == null) {
                typeBindingCapture = this.binding.returnType;
                if (typeBindingCapture != null) {
                    typeBindingCapture = blockScope.environment().convertToRawType(typeBindingCapture.erasure(), true);
                }
            } else {
                typeBindingCapture = this.binding.returnType;
                if (typeBindingCapture != null) {
                    typeBindingCapture = typeBindingCapture.capture(blockScope, this.sourceStart, this.sourceEnd);
                }
            }
            this.resolvedType = typeBindingCapture;
        }
        if (this.receiver.isSuper() && compilerOptions.getSeverity(CompilerOptions.OverridingMethodWithoutSuperInvocation) != 256) {
            ReferenceContext referenceContext = blockScope.methodScope().referenceContext;
            if (referenceContext instanceof AbstractMethodDeclaration) {
                AbstractMethodDeclaration abstractMethodDeclaration = (AbstractMethodDeclaration) referenceContext;
                MethodBinding methodBinding3 = abstractMethodDeclaration.binding;
                if (methodBinding3.isOverriding() && CharOperation.equals(this.binding.selector, methodBinding3.selector) && this.binding.areParametersEqual(methodBinding3)) {
                    abstractMethodDeclaration.bits |= 16;
                }
            }
        }
        if (this.receiver.isSuper() && this.actualReceiverType.isInterface()) {
            blockScope.checkAppropriateMethodAgainstSupers(this.selector, this.binding, this.argumentTypes, this);
        }
        if (this.typeArguments != null && this.binding.original().typeVariables == Binding.NO_TYPE_VARIABLES) {
            blockScope.problemReporter().unnecessaryTypeArgumentsForMethodInvocation(this.binding, this.genericTypeArguments, this.typeArguments);
        }
        if ((this.resolvedType.tagBits & 128) == 0) {
            return this.resolvedType;
        }
        return null;
    }

    protected TypeBinding findMethodBinding(BlockScope blockScope) {
        MethodBinding method;
        ReferenceContext referenceContext = blockScope.methodScope().referenceContext;
        if (referenceContext instanceof LambdaExpression) {
            this.outerInferenceContext = ((LambdaExpression) referenceContext).inferenceContext;
        }
        TypeBinding typeBinding = this.expectedType;
        if (typeBinding != null && (this.binding instanceof PolyParameterizedGenericMethodBinding)) {
            this.binding = this.solutionsPerTargetType.get(typeBinding);
        }
        if (this.binding == null) {
            if (this.receiver.isImplicitThis()) {
                method = blockScope.getImplicitMethod(this.selector, this.argumentTypes, this);
            } else {
                method = blockScope.getMethod(this.actualReceiverType, this.selector, this.argumentTypes, this);
            }
            this.binding = method;
            if (method instanceof PolyParameterizedGenericMethodBinding) {
                this.solutionsPerTargetType = new HashMap<>();
                return new PolyTypeBinding(this);
            }
        }
        resolvePolyExpressionArguments(this, this.binding, this.argumentTypes, blockScope);
        return this.binding.returnType;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public void setActualReceiverType(ReferenceBinding referenceBinding) {
        if (referenceBinding == null) {
            return;
        }
        this.actualReceiverType = referenceBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public void setDepth(int i) {
        this.bits &= -8161;
        if (i > 0) {
            this.bits = ((i & 255) << 5) | this.bits;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public void setExpectedType(TypeBinding typeBinding) {
        this.expectedType = typeBinding;
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
    public boolean isBoxingCompatibleWith(TypeBinding typeBinding, Scope scope) {
        MethodBinding methodBinding;
        if (this.argumentsHaveErrors || (methodBinding = this.binding) == null || !methodBinding.isValidBinding() || typeBinding == null || scope == null) {
            return false;
        }
        if (isPolyExpression() && !typeBinding.isPrimitiveOrBoxedPrimitiveType()) {
            return false;
        }
        TypeBinding typeBinding2 = this.expectedType;
        try {
            HashMap<TypeBinding, MethodBinding> map = this.solutionsPerTargetType;
            MethodBinding methodBindingComputeCompatibleMethod18 = map != null ? map.get(typeBinding) : null;
            if (methodBindingComputeCompatibleMethod18 == null) {
                this.expectedType = typeBinding;
                methodBindingComputeCompatibleMethod18 = isPolyExpression() ? ParameterizedGenericMethodBinding.computeCompatibleMethod18(this.binding.shallowOriginal(), this.argumentTypes, scope, this) : this.binding;
                registerResult(typeBinding, methodBindingComputeCompatibleMethod18);
            }
            if (methodBindingComputeCompatibleMethod18 != null && methodBindingComputeCompatibleMethod18.isValidBinding() && methodBindingComputeCompatibleMethod18.returnType != null && methodBindingComputeCompatibleMethod18.returnType.isValidBinding()) {
                return super.isBoxingCompatible(methodBindingComputeCompatibleMethod18.returnType.capture(scope, this.sourceStart, this.sourceEnd), typeBinding, this, scope);
            }
            return false;
        } finally {
            this.expectedType = typeBinding2;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isCompatibleWith(TypeBinding typeBinding, Scope scope) {
        MethodBinding methodBinding;
        TypeBinding typeBinding2;
        boolean z = false;
        if (this.argumentsHaveErrors || (methodBinding = this.binding) == null || !methodBinding.isValidBinding() || typeBinding == null || scope == null) {
            return false;
        }
        TypeBinding typeBinding3 = this.expectedType;
        try {
            HashMap<TypeBinding, MethodBinding> map = this.solutionsPerTargetType;
            MethodBinding methodBindingComputeCompatibleMethod18 = map != null ? map.get(typeBinding) : null;
            if (methodBindingComputeCompatibleMethod18 == null) {
                this.expectedType = typeBinding;
                methodBindingComputeCompatibleMethod18 = isPolyExpression() ? ParameterizedGenericMethodBinding.computeCompatibleMethod18(this.binding.shallowOriginal(), this.argumentTypes, scope, this) : this.binding;
                registerResult(typeBinding, methodBindingComputeCompatibleMethod18);
            }
            if (methodBindingComputeCompatibleMethod18 != null && methodBindingComputeCompatibleMethod18.isValidBinding() && (typeBinding2 = methodBindingComputeCompatibleMethod18.returnType) != null && typeBinding2.isValidBinding()) {
                if (methodBindingComputeCompatibleMethod18 == scope.environment().arrayClone) {
                    typeBinding2 = this.actualReceiverType;
                }
                if (typeBinding2 != null && typeBinding2.capture(scope, this.sourceStart, this.sourceEnd).isCompatibleWith(typeBinding, scope)) {
                    z = true;
                }
                return z;
            }
            return false;
        } finally {
            this.expectedType = typeBinding3;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isPolyExpression(MethodBinding methodBinding) {
        if (this.expressionContext != ExpressionContext.ASSIGNMENT_CONTEXT && this.expressionContext != ExpressionContext.INVOCATION_CONTEXT) {
            return false;
        }
        TypeReference[] typeReferenceArr = this.typeArguments;
        if (typeReferenceArr != null && typeReferenceArr.length > 0) {
            return false;
        }
        if (this.constant != Constant.NotAConstant) {
            throw new UnsupportedOperationException("Unresolved MessageSend can't be queried if it is a polyexpression");
        }
        if (methodBinding != null) {
            if ((methodBinding instanceof ParameterizedGenericMethodBinding) && ((ParameterizedGenericMethodBinding) methodBinding).inferredReturnType) {
                return true;
            }
            if (methodBinding.returnType != null) {
                MethodBinding methodBindingOriginal = methodBinding.original();
                return methodBindingOriginal.returnType.mentionsAny(methodBindingOriginal.typeVariables(), -1);
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean sIsMoreSpecific(TypeBinding typeBinding, TypeBinding typeBinding2, Scope scope) {
        if (super.sIsMoreSpecific(typeBinding, typeBinding2, scope)) {
            return true;
        }
        return isPolyExpression() && !typeBinding.isBaseType() && typeBinding2.isBaseType();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement, org.eclipse.jdt.internal.compiler.ast.IPolyExpression, org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public TypeBinding invocationTargetType() {
        return this.expectedType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.receiver.traverse(aSTVisitor, blockScope);
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

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public boolean statementExpression() {
        return (this.bits & ASTNode.ParenthesizedMASK) == 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode, org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean receiverIsImplicitThis() {
        return this.receiver.isImplicitThis();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Invocation
    public MethodBinding binding() {
        return this.binding;
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

    @Override // org.eclipse.jdt.internal.compiler.ast.Invocation
    public Expression[] arguments() {
        return this.arguments;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement, org.eclipse.jdt.internal.compiler.ast.IPolyExpression, org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public ExpressionContext getExpressionContext() {
        return this.expressionContext;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public InferenceContext18 freshInferenceContext(Scope scope) {
        return new InferenceContext18(scope, this.arguments, this, this.outerInferenceContext);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode, org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean isQualifiedSuper() {
        return this.receiver.isQualifiedSuper();
    }
}
