package org.eclipse.jdt.internal.compiler.ast;

import java.util.HashMap;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.codegen.ConstantPool;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.flow.ExceptionHandlingFlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ImplicitNullAnnotationVerifier;
import org.eclipse.jdt.internal.compiler.lookup.InferenceContext18;
import org.eclipse.jdt.internal.compiler.lookup.InvocationSite;
import org.eclipse.jdt.internal.compiler.lookup.LocalTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedGenericMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.PolyTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.SyntheticArgumentBinding;
import org.eclipse.jdt.internal.compiler.lookup.SyntheticMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.parser.Parser;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes14.dex */
public class ReferenceExpression extends FunctionalExpression implements IPolyExpression, InvocationSite {
    private static final char[] ImplicitArgName = " arg".toCharArray();
    private static final String SecretReceiverVariableName = " rec_";
    private boolean checkingPotentialCompatibility;
    private HashMap<TypeBinding, ReferenceExpression> copiesPerTargetType;
    private int depth;
    private MethodBinding exactMethodBinding;
    private TypeBinding[] freeParameters;
    public boolean haveReceiver;
    private HashMap<ParameterizedGenericMethodBinding, InferenceContext18> inferenceContexts;
    public Expression lhs;
    public int nameSourceStart;
    public TypeBinding receiverType;
    public LocalVariableBinding receiverVariable;
    public TypeBinding[] resolvedTypeArguments;
    public char[] selector;
    MethodBinding syntheticAccessor;
    public char[] text;
    public TypeReference[] typeArguments;
    private boolean typeArgumentsHaveErrors;
    private boolean receiverPrecedesParameters = false;
    private MethodBinding[] potentialMethods = Binding.NO_METHODS;
    protected ReferenceExpression original = this;

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public void setActualReceiverType(ReferenceBinding referenceBinding) {
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public void setFieldIndex(int i) {
    }

    public void initialize(CompilationResult compilationResult, Expression expression, TypeReference[] typeReferenceArr, char[] cArr, int i) {
        super.setCompilationResult(compilationResult);
        this.lhs = expression;
        this.typeArguments = typeReferenceArr;
        this.selector = cArr;
        this.sourceStart = expression.sourceStart;
        this.sourceEnd = i;
    }

    private ReferenceExpression copy() {
        Parser parser = new Parser(this.enclosingScope.problemReporter(), false);
        ICompilationUnit compilationUnit = this.compilationResult.getCompilationUnit();
        ReferenceExpression referenceExpression = (ReferenceExpression) parser.parseExpression(compilationUnit != null ? compilationUnit.getContents() : this.text, compilationUnit != null ? this.sourceStart : 0, (this.sourceEnd - this.sourceStart) + 1, this.enclosingScope.referenceCompilationUnit(), false);
        referenceExpression.original = this;
        referenceExpression.sourceStart = this.sourceStart;
        referenceExpression.sourceEnd = this.sourceEnd;
        return referenceExpression;
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [org.eclipse.jdt.internal.compiler.ast.ReferenceExpression$1] */
    private boolean shouldGenerateSecretReceiverVariable() {
        if (!isMethodReference() || !this.haveReceiver) {
            return false;
        }
        if (this.lhs instanceof Invocation) {
            return true;
        }
        return new ASTVisitor() { // from class: org.eclipse.jdt.internal.compiler.ast.ReferenceExpression.1
            boolean accessesnonFinalOuterLocals;

            @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
            public boolean visit(SingleNameReference singleNameReference, BlockScope blockScope) {
                Binding binding = blockScope.getBinding(singleNameReference.getName(), ReferenceExpression.this);
                if (!(binding instanceof LocalVariableBinding)) {
                    return false;
                }
                LocalVariableBinding localVariableBinding = (LocalVariableBinding) binding;
                if (localVariableBinding.isFinal() || localVariableBinding.isEffectivelyFinal()) {
                    return false;
                }
                this.accessesnonFinalOuterLocals = true;
                return false;
            }

            public boolean accessesnonFinalOuterLocals() {
                ReferenceExpression.this.lhs.traverse(this, ReferenceExpression.this.enclosingScope);
                return this.accessesnonFinalOuterLocals;
            }
        }.accessesnonFinalOuterLocals();
    }

    public void generateImplicitLambda(BlockScope blockScope, CodeStream codeStream, boolean z) {
        ReferenceExpression referenceExpressionCopy = copy();
        int length = this.descriptor.parameters.length;
        LambdaExpression lambdaExpression = new LambdaExpression(this.compilationResult, false, (this.binding.modifiers & 1073741824) != 0);
        Argument[] argumentArr = new Argument[length];
        for (int i = 0; i < length; i++) {
            argumentArr[i] = new Argument(CharOperation.append(ImplicitArgName, Integer.toString(i).toCharArray()), 0L, null, 0, true);
        }
        lambdaExpression.setArguments(argumentArr);
        lambdaExpression.setExpressionContext(this.expressionContext);
        lambdaExpression.setExpectedType(this.expectedType);
        boolean z2 = this.receiverPrecedesParameters;
        int i2 = length - (z2 ? 1 : 0);
        SingleNameReference[] singleNameReferenceArr = new SingleNameReference[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            singleNameReferenceArr[i3] = new SingleNameReference(CharOperation.append(ImplicitArgName, Integer.toString(i3 + (z2 ? 1 : 0)).toCharArray()), 0L);
        }
        boolean zShouldGenerateSecretReceiverVariable = shouldGenerateSecretReceiverVariable();
        if (isMethodReference()) {
            if (zShouldGenerateSecretReceiverVariable) {
                this.lhs.generateCode(blockScope, codeStream, true);
                codeStream.store(this.receiverVariable, false);
                codeStream.addVariable(this.receiverVariable);
            }
            MessageSend messageSend = new MessageSend();
            messageSend.selector = this.selector;
            Expression singleNameReference = zShouldGenerateSecretReceiverVariable ? new SingleNameReference(this.receiverVariable.f628name, 0L) : referenceExpressionCopy.lhs;
            if (this.receiverPrecedesParameters) {
                singleNameReference = new SingleNameReference(CharOperation.append(ImplicitArgName, Integer.toString(0).toCharArray()), 0L);
            }
            messageSend.receiver = singleNameReference;
            messageSend.typeArguments = referenceExpressionCopy.typeArguments;
            messageSend.arguments = singleNameReferenceArr;
            lambdaExpression.setBody(messageSend);
        } else if (isArrayConstructorReference()) {
            ArrayAllocationExpression arrayAllocationExpression = new ArrayAllocationExpression();
            arrayAllocationExpression.dimensions = new Expression[]{singleNameReferenceArr[0]};
            Expression expression = this.lhs;
            if (expression instanceof ArrayTypeReference) {
                ArrayTypeReference arrayTypeReference = (ArrayTypeReference) expression;
                arrayAllocationExpression.type = arrayTypeReference.dimensions == 1 ? new SingleTypeReference(arrayTypeReference.token, 0L) : new ArrayTypeReference(arrayTypeReference.token, arrayTypeReference.dimensions - 1, 0L);
            } else {
                ArrayQualifiedTypeReference arrayQualifiedTypeReference = (ArrayQualifiedTypeReference) expression;
                arrayAllocationExpression.type = arrayQualifiedTypeReference.dimensions == 1 ? new QualifiedTypeReference(arrayQualifiedTypeReference.tokens, arrayQualifiedTypeReference.sourcePositions) : new ArrayQualifiedTypeReference(arrayQualifiedTypeReference.tokens, arrayQualifiedTypeReference.dimensions - 1, arrayQualifiedTypeReference.sourcePositions);
            }
            lambdaExpression.setBody(arrayAllocationExpression);
        } else {
            AllocationExpression allocationExpression = new AllocationExpression();
            Expression expression2 = this.lhs;
            if (expression2 instanceof TypeReference) {
                allocationExpression.type = (TypeReference) expression2;
            } else if (expression2 instanceof SingleNameReference) {
                allocationExpression.type = new SingleTypeReference(((SingleNameReference) this.lhs).token, 0L);
            } else if (expression2 instanceof QualifiedNameReference) {
                allocationExpression.type = new QualifiedTypeReference(((QualifiedNameReference) this.lhs).tokens, new long[((QualifiedNameReference) this.lhs).tokens.length]);
            } else {
                throw new IllegalStateException("Unexpected node type");
            }
            allocationExpression.typeArguments = referenceExpressionCopy.typeArguments;
            allocationExpression.arguments = singleNameReferenceArr;
            lambdaExpression.setBody(allocationExpression);
        }
        lambdaExpression.resolve(blockScope);
        IErrorHandlingPolicy iErrorHandlingPolicySwitchErrorHandlingPolicy = blockScope.problemReporter().switchErrorHandlingPolicy(silentErrorHandlingPolicy);
        try {
            lambdaExpression.analyseCode(blockScope, new ExceptionHandlingFlowContext(null, this, Binding.NO_EXCEPTIONS, null, blockScope, FlowInfo.DEAD_END), UnconditionalFlowInfo.fakeInitializedFlowInfo(blockScope.outerMostMethodScope().analysisIndex, blockScope.referenceType().maxFieldCount));
            blockScope.problemReporter().switchErrorHandlingPolicy(iErrorHandlingPolicySwitchErrorHandlingPolicy);
            SyntheticArgumentBinding[] syntheticArgumentBindingArrSyntheticOuterLocalVariables = this.receiverType.syntheticOuterLocalVariables();
            int length2 = syntheticArgumentBindingArrSyntheticOuterLocalVariables == null ? 0 : syntheticArgumentBindingArrSyntheticOuterLocalVariables.length;
            for (int i4 = 0; i4 < length2; i4++) {
                lambdaExpression.addSyntheticArgument(syntheticArgumentBindingArrSyntheticOuterLocalVariables[i4].actualOuterLocalVariable);
            }
            lambdaExpression.generateCode(blockScope, codeStream, z);
            if (zShouldGenerateSecretReceiverVariable) {
                codeStream.removeVariable(this.receiverVariable);
                this.receiverVariable = null;
            }
        } catch (Throwable th) {
            blockScope.problemReporter().switchErrorHandlingPolicy(iErrorHandlingPolicySwitchErrorHandlingPolicy);
            throw th;
        }
    }

    private boolean shouldGenerateImplicitLambda(BlockScope blockScope) {
        if (this.binding.isVarargs()) {
            return true;
        }
        return (isConstructorReference() && this.receiverType.syntheticOuterLocalVariables() != null && this.shouldCaptureInstance) || requiresBridges();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i;
        this.actualMethodBinding = this.binding;
        if (shouldGenerateImplicitLambda(blockScope)) {
            generateImplicitLambda(blockScope, codeStream, z);
            return;
        }
        SourceTypeBinding sourceTypeBindingEnclosingSourceType = blockScope.enclosingSourceType();
        if (this.receiverType.isArrayType()) {
            if (isConstructorReference()) {
                SyntheticMethodBinding syntheticMethodBindingAddSyntheticArrayMethod = sourceTypeBindingEnclosingSourceType.addSyntheticArrayMethod((ArrayBinding) this.receiverType, 14);
                this.binding = syntheticMethodBindingAddSyntheticArrayMethod;
                this.actualMethodBinding = syntheticMethodBindingAddSyntheticArrayMethod;
            } else if (CharOperation.equals(this.selector, TypeConstants.CLONE)) {
                SyntheticMethodBinding syntheticMethodBindingAddSyntheticArrayMethod2 = sourceTypeBindingEnclosingSourceType.addSyntheticArrayMethod((ArrayBinding) this.receiverType, 15);
                this.binding = syntheticMethodBindingAddSyntheticArrayMethod2;
                this.actualMethodBinding = syntheticMethodBindingAddSyntheticArrayMethod2;
            }
        } else if (this.syntheticAccessor != null) {
            if (this.lhs.isSuper() || isMethodReference()) {
                this.binding = this.syntheticAccessor;
            }
        } else if (this.binding != null && isMethodReference() && TypeBinding.notEquals(this.binding.declaringClass, this.lhs.resolvedType.erasure()) && !this.binding.declaringClass.canBeSeenBy(blockScope)) {
            this.binding = new MethodBinding(this.binding, (ReferenceBinding) this.lhs.resolvedType.erasure());
        }
        int i2 = codeStream.position;
        StringBuffer stringBuffer = new StringBuffer("(");
        if (this.haveReceiver) {
            this.lhs.generateCode(blockScope, codeStream, true);
            if (this.lhs.isSuper() && !this.actualMethodBinding.isPrivate()) {
                Expression expression = this.lhs;
                if (!(expression instanceof QualifiedSuperReference) || ((QualifiedSuperReference) expression).qualification.resolvedType.isInterface()) {
                    stringBuffer.append(sourceTypeBindingEnclosingSourceType.signature());
                } else {
                    stringBuffer.append(((QualifiedSuperReference) this.lhs).currentCompatibleType.signature());
                }
            } else {
                stringBuffer.append(this.receiverType.signature());
            }
            i = 1;
        } else {
            i = 0;
            if (isConstructorReference()) {
                ReferenceBinding[] referenceBindingArrSyntheticEnclosingInstanceTypes = Binding.UNINITIALIZED_REFERENCE_TYPES;
                if (this.receiverType.isNestedType()) {
                    referenceBindingArrSyntheticEnclosingInstanceTypes = ((ReferenceBinding) this.receiverType).syntheticEnclosingInstanceTypes();
                    if (referenceBindingArrSyntheticEnclosingInstanceTypes != null) {
                        int length = referenceBindingArrSyntheticEnclosingInstanceTypes.length;
                        for (ReferenceBinding referenceBinding : referenceBindingArrSyntheticEnclosingInstanceTypes) {
                            stringBuffer.append(referenceBinding.signature());
                            codeStream.generateOuterAccess(blockScope.getEmulationPath(referenceBinding, false, true), this, referenceBinding, blockScope);
                        }
                        i = length;
                    } else {
                        referenceBindingArrSyntheticEnclosingInstanceTypes = Binding.NO_REFERENCE_TYPES;
                    }
                }
                if (this.syntheticAccessor != null) {
                    this.binding = sourceTypeBindingEnclosingSourceType.addSyntheticFactoryMethod(this.binding, this.syntheticAccessor, referenceBindingArrSyntheticEnclosingInstanceTypes);
                    this.syntheticAccessor = null;
                }
            }
        }
        stringBuffer.append(")L");
        stringBuffer.append(this.resolvedType.constantPoolName());
        stringBuffer.append(';');
        if (this.isSerializable) {
            sourceTypeBindingEnclosingSourceType.addSyntheticMethod(this);
        }
        int iRecordBootstrapMethod = codeStream.classFile.recordBootstrapMethod(this);
        char[] cArr = this.descriptor.selector;
        char[] charArray = stringBuffer.toString().toCharArray();
        boolean zIsConstructorReference = isConstructorReference();
        Expression expression2 = this.lhs;
        codeStream.invokeDynamic(iRecordBootstrapMethod, i, 1, cArr, charArray, zIsConstructorReference, expression2 instanceof TypeReference ? (TypeReference) expression2 : null, this.typeArguments);
        if (!z) {
            codeStream.pop();
        }
        codeStream.recordPositionsFrom(i2, this.sourceStart);
    }

    public void manageSyntheticAccessIfNecessary(BlockScope blockScope, FlowInfo flowInfo) {
        if ((flowInfo.tagBits & 1) == 0 && this.binding != null && this.binding.isValidBinding()) {
            MethodBinding methodBindingOriginal = this.binding.original();
            if (methodBindingOriginal.isVarargs()) {
                return;
            }
            SourceTypeBinding sourceTypeBindingEnclosingSourceType = blockScope.enclosingSourceType();
            if (isConstructorReference()) {
                ReferenceBinding referenceBinding = methodBindingOriginal.declaringClass;
                if (methodBindingOriginal.isPrivate()) {
                    ReferenceBinding referenceBinding2 = methodBindingOriginal.declaringClass;
                    if (TypeBinding.notEquals(sourceTypeBindingEnclosingSourceType, referenceBinding2)) {
                        if ((referenceBinding2.tagBits & 16) != 0) {
                            methodBindingOriginal.tagBits |= 512;
                            return;
                        } else {
                            this.syntheticAccessor = ((SourceTypeBinding) referenceBinding2).addSyntheticMethod(methodBindingOriginal, false);
                            blockScope.problemReporter().needToEmulateMethodAccess(methodBindingOriginal, this);
                            return;
                        }
                    }
                    return;
                }
                return;
            }
            if (this.binding.isPrivate()) {
                if (TypeBinding.notEquals(sourceTypeBindingEnclosingSourceType, methodBindingOriginal.declaringClass)) {
                    this.syntheticAccessor = ((SourceTypeBinding) methodBindingOriginal.declaringClass).addSyntheticMethod(methodBindingOriginal, false);
                    blockScope.problemReporter().needToEmulateMethodAccess(methodBindingOriginal, this);
                    return;
                }
                return;
            }
            if (this.lhs.isSuper()) {
                Expression expression = this.lhs;
                if (expression instanceof QualifiedSuperReference) {
                    QualifiedSuperReference qualifiedSuperReference = (QualifiedSuperReference) expression;
                    if (!qualifiedSuperReference.qualification.resolvedType.isInterface()) {
                        sourceTypeBindingEnclosingSourceType = (SourceTypeBinding) qualifiedSuperReference.currentCompatibleType;
                    }
                }
                this.syntheticAccessor = sourceTypeBindingEnclosingSourceType.addSyntheticMethod(methodBindingOriginal, true);
                blockScope.problemReporter().needToEmulateMethodAccess(methodBindingOriginal, this);
                return;
            }
            if (!this.binding.isProtected() || (this.bits & ASTNode.DepthMASK) == 0 || methodBindingOriginal.declaringClass.getPackage() == sourceTypeBindingEnclosingSourceType.getPackage()) {
                return;
            }
            this.syntheticAccessor = ((SourceTypeBinding) sourceTypeBindingEnclosingSourceType.enclosingTypeAt((this.bits & ASTNode.DepthMASK) >> 5)).addSyntheticMethod(methodBindingOriginal, isSuperAccess());
            blockScope.problemReporter().needToEmulateMethodAccess(methodBindingOriginal, this);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        if (this.haveReceiver) {
            this.lhs.checkNPE(blockScope, flowContext, flowInfo);
            this.lhs.analyseCode(blockScope, flowContext, flowInfo, true);
        } else if (isConstructorReference()) {
            TypeBinding typeBindingLeafComponentType = this.receiverType.leafComponentType();
            if (typeBindingLeafComponentType.isNestedType() && (typeBindingLeafComponentType instanceof ReferenceBinding)) {
                ReferenceBinding referenceBinding = (ReferenceBinding) typeBindingLeafComponentType;
                if (!referenceBinding.isStatic()) {
                    blockScope.tagAsAccessingEnclosingInstanceStateOf(referenceBinding, false);
                    this.shouldCaptureInstance = true;
                    ReferenceBinding referenceBinding2 = (ReferenceBinding) typeBindingLeafComponentType.erasure();
                    if (referenceBinding2.isLocalType()) {
                        ((LocalTypeBinding) referenceBinding2).addInnerEmulationDependent(blockScope, false);
                    }
                }
            }
        }
        manageSyntheticAccessIfNecessary(blockScope, flowInfo);
        return flowInfo;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode, org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean checkingPotentialCompatibility() {
        return this.checkingPotentialCompatibility;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode, org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public void acceptPotentiallyCompatibleMethods(MethodBinding[] methodBindingArr) {
        if (this.checkingPotentialCompatibility) {
            this.potentialMethods = methodBindingArr;
        }
    }

    /* JADX WARN: Code duplicated, block: B:183:0x0306  */
    @Override // org.eclipse.jdt.internal.compiler.ast.FunctionalExpression, org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        TypeBinding typeBindingResolveType;
        MethodBinding constructor;
        int i;
        MethodBinding method;
        TypeBinding typeBindingCapture;
        TypeBinding typeBindingFindSuperTypeOriginatingFrom;
        CompilerOptions compilerOptions = blockScope.compilerOptions();
        if (this.constant != Constant.NotAConstant) {
            this.constant = Constant.NotAConstant;
            this.enclosingScope = blockScope;
            if (this.original == this) {
                recordFunctionalType(blockScope);
            }
            this.lhs.bits |= 1073741824;
            typeBindingResolveType = this.lhs.resolveType(blockScope);
            this.lhs.computeConversion(blockScope, typeBindingResolveType, typeBindingResolveType);
            TypeReference[] typeReferenceArr = this.typeArguments;
            if (typeReferenceArr != null) {
                int length = typeReferenceArr.length;
                this.typeArgumentsHaveErrors = compilerOptions.sourceLevel < ClassFileConstants.JDK1_5;
                this.resolvedTypeArguments = new TypeBinding[length];
                for (int i2 = 0; i2 < length; i2++) {
                    TypeReference typeReference = this.typeArguments[i2];
                    TypeBinding[] typeBindingArr = this.resolvedTypeArguments;
                    TypeBinding typeBindingResolveType2 = typeReference.resolveType(blockScope, true);
                    typeBindingArr[i2] = typeBindingResolveType2;
                    if (typeBindingResolveType2 == null) {
                        this.typeArgumentsHaveErrors = true;
                    }
                    if (this.typeArgumentsHaveErrors && (typeReference instanceof Wildcard)) {
                        blockScope.problemReporter().illegalUsageOfWildcard(typeReference);
                    }
                }
                if (this.typeArgumentsHaveErrors || typeBindingResolveType == null) {
                    this.resolvedType = null;
                    return null;
                }
                if (isConstructorReference() && typeBindingResolveType.isRawType()) {
                    blockScope.problemReporter().rawConstructorReferenceNotWithExplicitTypeArguments(this.typeArguments);
                    this.resolvedType = null;
                    return null;
                }
            }
            if (this.typeArgumentsHaveErrors || typeBindingResolveType == null) {
                this.resolvedType = null;
                return null;
            }
            if (typeBindingResolveType.problemId() == 21) {
                typeBindingResolveType = typeBindingResolveType.closestMatch();
            }
            if (typeBindingResolveType == null || !typeBindingResolveType.isValidBinding()) {
                this.resolvedType = null;
                return null;
            }
            this.receiverType = typeBindingResolveType;
            this.haveReceiver = true;
            Expression expression = this.lhs;
            if (expression instanceof NameReference) {
                if ((expression.bits & 7) == 4) {
                    this.haveReceiver = false;
                } else if (isConstructorReference()) {
                    blockScope.problemReporter().invalidType(this.lhs, new ProblemReferenceBinding(((NameReference) this.lhs).getName(), null, 1));
                    this.resolvedType = null;
                    return null;
                }
            } else if (expression instanceof TypeReference) {
                this.haveReceiver = false;
            }
            if (!this.haveReceiver && !this.lhs.isSuper() && !isArrayConstructorReference()) {
                this.receiverType = typeBindingResolveType.capture(blockScope, this.sourceStart, this.sourceEnd);
            }
            if (!typeBindingResolveType.isRawType()) {
                MethodBinding exactMethod = isMethodReference() ? blockScope.getExactMethod(typeBindingResolveType, this.selector, this) : blockScope.getExactConstructor(typeBindingResolveType, this);
                this.exactMethodBinding = exactMethod;
                this.binding = exactMethod;
            }
            if (isConstructorReference() && !typeBindingResolveType.canBeInstantiated()) {
                blockScope.problemReporter().cannotInstantiate(this.lhs, typeBindingResolveType);
                this.resolvedType = null;
                return null;
            }
            Expression expression2 = this.lhs;
            if ((expression2 instanceof TypeReference) && ((TypeReference) expression2).hasNullTypeAnnotation(TypeReference.AnnotationPosition.ANY)) {
                blockScope.problemReporter().nullAnnotationUnsupportedLocation((TypeReference) this.lhs);
            }
            if (isConstructorReference() && typeBindingResolveType.isArrayType()) {
                TypeBinding typeBindingLeafComponentType = typeBindingResolveType.leafComponentType();
                if (!typeBindingLeafComponentType.isReifiable()) {
                    blockScope.problemReporter().illegalGenericArray(typeBindingLeafComponentType, this);
                    this.resolvedType = null;
                    return null;
                }
                if (this.typeArguments != null) {
                    blockScope.problemReporter().invalidTypeArguments(this.typeArguments);
                    this.resolvedType = null;
                    return null;
                }
                MethodBinding exactConstructor = blockScope.getExactConstructor(typeBindingResolveType, this);
                this.exactMethodBinding = exactConstructor;
                this.binding = exactConstructor;
            }
            if (isMethodReference() && this.haveReceiver && this.original == this) {
                LocalVariableBinding localVariableBinding = new LocalVariableBinding((SecretReceiverVariableName + this.nameSourceStart).toCharArray(), this.lhs.resolvedType, 0, false);
                this.receiverVariable = localVariableBinding;
                blockScope.addLocalVariable(localVariableBinding);
                this.receiverVariable.setConstant(Constant.NotAConstant);
                this.receiverVariable.useFlag = 1;
            }
            if (this.expectedType == null && this.expressionContext == ExpressionContext.INVOCATION_CONTEXT) {
                return new PolyTypeBinding(this);
            }
        } else {
            typeBindingResolveType = this.lhs.resolvedType;
            if (this.typeArgumentsHaveErrors || typeBindingResolveType == null) {
                this.resolvedType = null;
                return null;
            }
        }
        super.resolveType(blockScope);
        if (this.descriptor == null || !this.descriptor.isValidBinding()) {
            this.resolvedType = null;
            return null;
        }
        TypeBinding[] typeBindingArrDescriptorParametersAsArgumentExpressions = descriptorParametersAsArgumentExpressions();
        if (typeBindingResolveType.isBaseType()) {
            blockScope.problemReporter().errorNoMethodFor(this.lhs, typeBindingResolveType, this.selector, typeBindingArrDescriptorParametersAsArgumentExpressions);
            this.resolvedType = null;
            return null;
        }
        int length2 = typeBindingArrDescriptorParametersAsArgumentExpressions.length;
        if (isConstructorReference() && typeBindingResolveType.isArrayType()) {
            if (length2 != 1 || blockScope.parameterCompatibilityLevel(typeBindingArrDescriptorParametersAsArgumentExpressions[0], TypeBinding.INT) == -1) {
                blockScope.problemReporter().invalidArrayConstructorReference(this, typeBindingResolveType, typeBindingArrDescriptorParametersAsArgumentExpressions);
                this.resolvedType = null;
                return null;
            }
            if (this.descriptor.returnType.isProperType(true) && !typeBindingResolveType.isCompatibleWith(this.descriptor.returnType) && this.descriptor.returnType.f626id != 6) {
                blockScope.problemReporter().constructedArrayIncompatible(this, typeBindingResolveType, this.descriptor.returnType);
                this.resolvedType = null;
                return null;
            }
            checkNullAnnotations(blockScope);
            return this.resolvedType;
        }
        boolean zIsMethodReference = isMethodReference();
        this.depth = 0;
        this.freeParameters = typeBindingArrDescriptorParametersAsArgumentExpressions;
        if (zIsMethodReference) {
            constructor = blockScope.getMethod(this.receiverType, this.selector, typeBindingArrDescriptorParametersAsArgumentExpressions, this);
        } else {
            constructor = blockScope.getConstructor((ReferenceBinding) this.receiverType, typeBindingArrDescriptorParametersAsArgumentExpressions, this);
        }
        int i3 = this.depth;
        if (constructor != null && constructor.isValidBinding() && constructor.isStatic() && (this.haveReceiver || this.receiverType.isParameterizedTypeWithActualArguments())) {
            blockScope.problemReporter().methodMustBeAccessedStatically(this, constructor);
            this.resolvedType = null;
            return null;
        }
        if (this.lhs.isSuper() && this.lhs.resolvedType.isInterface()) {
            blockScope.checkAppropriateMethodAgainstSupers(this.selector, constructor, this.descriptor.parameters, this);
        }
        this.receiverPrecedesParameters = false;
        if (this.haveReceiver || !zIsMethodReference || length2 <= 0) {
            i = 0;
            method = null;
        } else {
            TypeBinding typeBinding = typeBindingArrDescriptorParametersAsArgumentExpressions[0];
            if (typeBinding.isCompatibleWith(this.receiverType, blockScope)) {
                TypeBinding typeBindingCapture2 = this.receiverType;
                if (typeBindingCapture2.isRawType() && (typeBindingFindSuperTypeOriginatingFrom = typeBinding.findSuperTypeOriginatingFrom(this.receiverType)) != null) {
                    typeBindingCapture2 = typeBindingFindSuperTypeOriginatingFrom.capture(blockScope, this.sourceStart, this.sourceEnd);
                }
                TypeBinding[] typeBindingArr2 = Binding.NO_PARAMETERS;
                if (length2 > 1) {
                    int i4 = length2 - 1;
                    typeBindingArr2 = new TypeBinding[i4];
                    System.arraycopy(typeBindingArrDescriptorParametersAsArgumentExpressions, 1, typeBindingArr2, 0, i4);
                }
                this.depth = 0;
                this.freeParameters = typeBindingArr2;
                method = blockScope.getMethod(typeBindingCapture2, this.selector, typeBindingArr2, this);
                i = this.depth;
                this.depth = 0;
            } else {
                i = 0;
                method = null;
            }
        }
        if (constructor != null && constructor.isValidBinding() && constructor.isStatic() && method != null && method.isValidBinding() && !method.isStatic()) {
            blockScope.problemReporter().methodReferenceSwingsBothWays(this, method, constructor);
            this.resolvedType = null;
            return null;
        }
        if (constructor != null && constructor.isValidBinding() && (method == null || !method.isValidBinding() || method.isStatic())) {
            this.binding = constructor;
            this.bits &= -8161;
            if (i3 > 0) {
                this.bits |= (i3 & 255) << 5;
            }
            if (!this.haveReceiver && !constructor.isStatic() && !constructor.isConstructor()) {
                blockScope.problemReporter().methodMustBeAccessedWithInstance(this, constructor);
                this.resolvedType = null;
                return null;
            }
        } else if (method != null && method.isValidBinding() && (constructor == null || !constructor.isValidBinding() || !constructor.isStatic())) {
            this.binding = method;
            this.receiverPrecedesParameters = true;
            this.bits &= -8161;
            if (i > 0) {
                this.bits = ((i & 255) << 5) | this.bits;
            }
            if (method.isStatic()) {
                blockScope.problemReporter().methodMustBeAccessedStatically(this, method);
                this.resolvedType = null;
                return null;
            }
        } else {
            this.binding = null;
            this.bits &= -8161;
        }
        if (this.binding == null) {
            blockScope.problemReporter().danglingReference(this, this.receiverType, isConstructorReference() ? this.receiverType.sourceName() : this.selector, typeBindingArrDescriptorParametersAsArgumentExpressions);
            this.resolvedType = null;
            return null;
        }
        if (this.binding.isAbstract() && this.lhs.isSuper()) {
            blockScope.problemReporter().cannotDireclyInvokeAbstractMethod(this, this.binding);
        }
        if (this.binding.isStatic()) {
            if (TypeBinding.notEquals(this.binding.declaringClass, this.receiverType)) {
                blockScope.problemReporter().indirectAccessToStaticMethod(this, this.binding);
            }
        } else {
            AbstractMethodDeclaration abstractMethodDeclarationSourceMethod = this.binding.sourceMethod();
            if (abstractMethodDeclarationSourceMethod != null && abstractMethodDeclarationSourceMethod.isMethod()) {
                abstractMethodDeclarationSourceMethod.bits &= -257;
            }
        }
        if (isMethodUseDeprecated(this.binding, blockScope, true)) {
            blockScope.problemReporter().deprecatedMethod(this.binding, this);
        }
        if (this.typeArguments != null && this.binding.original().typeVariables == Binding.NO_TYPE_VARIABLES) {
            blockScope.problemReporter().unnecessaryTypeArgumentsForMethodInvocation(this.binding, this.resolvedTypeArguments, this.typeArguments);
        }
        if ((this.binding.tagBits & 128) != 0) {
            blockScope.problemReporter().missingTypeInMethod(this, this.binding);
        }
        ReferenceBinding[] referenceBindingArr = this.binding.thrownExceptions;
        ReferenceBinding[] referenceBindingArr2 = this.descriptor.thrownExceptions;
        int length3 = referenceBindingArr.length;
        for (int i5 = 0; i5 < length3; i5++) {
            if (!referenceBindingArr[i5].isUncheckedException(false)) {
                int length4 = referenceBindingArr2.length;
                int i6 = 0;
                while (true) {
                    if (i6 < length4) {
                        if (referenceBindingArr[i5].isCompatibleWith(referenceBindingArr2[i6], blockScope)) {
                            break;
                        }
                        i6++;
                    } else {
                        blockScope.problemReporter().unhandledException((TypeBinding) referenceBindingArr[i5], this);
                        break;
                    }
                }
            }
        }
        checkNullAnnotations(blockScope);
        this.freeParameters = null;
        if (checkInvocationArguments(blockScope, null, this.receiverType, this.binding, null, typeBindingArrDescriptorParametersAsArgumentExpressions, false, this)) {
            this.bits |= 65536;
        }
        if (this.descriptor.returnType.f626id != 6) {
            if (this.binding == blockScope.environment().arrayClone || this.binding.isConstructor()) {
                typeBindingCapture = this.receiverType;
            } else if ((this.bits & 65536) != 0 && this.resolvedTypeArguments == null) {
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
            if (this.descriptor.returnType.isProperType(true) && !typeBindingCapture.isCompatibleWith(this.descriptor.returnType, blockScope) && !isBoxingCompatible(typeBindingCapture, this.descriptor.returnType, this, blockScope)) {
                blockScope.problemReporter().incompatibleReturnType(this, this.binding, this.descriptor.returnType);
                this.binding = null;
                this.resolvedType = null;
            }
        }
        return this.resolvedType;
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0078  */
    /* JADX WARN: Code duplicated, block: B:29:0x0082  */
    /* JADX WARN: Code duplicated, block: B:32:0x00a6  */
    /* JADX WARN: Code duplicated, block: B:33:0x00b4  */
    /* JADX WARN: Code duplicated, block: B:35:0x00cf  */
    /* JADX WARN: Code duplicated, block: B:40:0x00da A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:42:? A[RETURN, SYNTHETIC] */
    protected void checkNullAnnotations(BlockScope blockScope) {
        boolean z;
        int i;
        TypeBinding typeBindingCreateAnnotatedType;
        NullAnnotationMatching nullAnnotationMatchingAnalyse;
        TypeBinding typeBinding;
        TypeBinding parameter;
        NullAnnotationMatching nullAnnotationMatchingAnalyse2;
        CompilerOptions compilerOptions = blockScope.compilerOptions();
        if (compilerOptions.isAnnotationBasedNullAnalysisEnabled) {
            if (this.expectedType == null || !NullAnnotationMatching.hasContradictions(this.expectedType)) {
                if ((this.binding.tagBits & 4096) == 0) {
                    new ImplicitNullAnnotationVerifier(blockScope.environment(), compilerOptions.inheritNullAnnotations).checkImplicitNullAnnotations(this.binding, null, false, blockScope);
                }
                int length = this.binding.parameters.length;
                int length2 = this.descriptor.parameters.length;
                if (this.receiverPrecedesParameters) {
                    length2--;
                }
                if (!this.binding.isVarargs()) {
                    length2 = Math.min(length, length2);
                } else {
                    if (length2 == length) {
                        int i2 = length - 1;
                        if (this.descriptor.parameters[i2].isCompatibleWith(this.binding.parameters[i2])) {
                        }
                        for (i = 0; i < length2; i++) {
                            typeBinding = this.descriptor.parameters[(this.receiverPrecedesParameters ? 1 : 0) + i];
                            parameter = InferenceContext18.getParameter(this.binding.parameters, i, z);
                            nullAnnotationMatchingAnalyse2 = NullAnnotationMatching.analyse(parameter, typeBinding, 1);
                            if (nullAnnotationMatchingAnalyse2.isAnyMismatch()) {
                                blockScope.problemReporter().referenceExpressionArgumentNullityMismatch(this, parameter, typeBinding, this.descriptor, i, nullAnnotationMatchingAnalyse2);
                            }
                        }
                        typeBindingCreateAnnotatedType = this.binding.returnType;
                        if (!this.binding.isConstructor() || this.binding == blockScope.environment().arrayClone) {
                            typeBindingCreateAnnotatedType = blockScope.environment().createAnnotatedType(this.receiverType, new AnnotationBinding[]{blockScope.environment().getNonNullAnnotation()});
                        }
                        nullAnnotationMatchingAnalyse = NullAnnotationMatching.analyse(this.descriptor.returnType, typeBindingCreateAnnotatedType, 1);
                        if (nullAnnotationMatchingAnalyse.isAnyMismatch()) {
                            blockScope.problemReporter().illegalReturnRedefinition(this, this.descriptor, nullAnnotationMatchingAnalyse.isUnchecked(), typeBindingCreateAnnotatedType);
                        }
                    }
                    z = true;
                    while (i < length2) {
                        typeBinding = this.descriptor.parameters[(this.receiverPrecedesParameters ? 1 : 0) + i];
                        parameter = InferenceContext18.getParameter(this.binding.parameters, i, z);
                        nullAnnotationMatchingAnalyse2 = NullAnnotationMatching.analyse(parameter, typeBinding, 1);
                        if (nullAnnotationMatchingAnalyse2.isAnyMismatch()) {
                            blockScope.problemReporter().referenceExpressionArgumentNullityMismatch(this, parameter, typeBinding, this.descriptor, i, nullAnnotationMatchingAnalyse2);
                        }
                    }
                    typeBindingCreateAnnotatedType = this.binding.returnType;
                    if (!this.binding.isConstructor()) {
                        typeBindingCreateAnnotatedType = blockScope.environment().createAnnotatedType(this.receiverType, new AnnotationBinding[]{blockScope.environment().getNonNullAnnotation()});
                    } else {
                        typeBindingCreateAnnotatedType = blockScope.environment().createAnnotatedType(this.receiverType, new AnnotationBinding[]{blockScope.environment().getNonNullAnnotation()});
                    }
                    nullAnnotationMatchingAnalyse = NullAnnotationMatching.analyse(this.descriptor.returnType, typeBindingCreateAnnotatedType, 1);
                    if (nullAnnotationMatchingAnalyse.isAnyMismatch()) {
                        blockScope.problemReporter().illegalReturnRedefinition(this, this.descriptor, nullAnnotationMatchingAnalyse.isUnchecked(), typeBindingCreateAnnotatedType);
                    }
                }
                z = false;
                while (i < length2) {
                    typeBinding = this.descriptor.parameters[(this.receiverPrecedesParameters ? 1 : 0) + i];
                    parameter = InferenceContext18.getParameter(this.binding.parameters, i, z);
                    nullAnnotationMatchingAnalyse2 = NullAnnotationMatching.analyse(parameter, typeBinding, 1);
                    if (nullAnnotationMatchingAnalyse2.isAnyMismatch()) {
                        blockScope.problemReporter().referenceExpressionArgumentNullityMismatch(this, parameter, typeBinding, this.descriptor, i, nullAnnotationMatchingAnalyse2);
                    }
                }
                typeBindingCreateAnnotatedType = this.binding.returnType;
                if (!this.binding.isConstructor()) {
                    typeBindingCreateAnnotatedType = blockScope.environment().createAnnotatedType(this.receiverType, new AnnotationBinding[]{blockScope.environment().getNonNullAnnotation()});
                } else {
                    typeBindingCreateAnnotatedType = blockScope.environment().createAnnotatedType(this.receiverType, new AnnotationBinding[]{blockScope.environment().getNonNullAnnotation()});
                }
                nullAnnotationMatchingAnalyse = NullAnnotationMatching.analyse(this.descriptor.returnType, typeBindingCreateAnnotatedType, 1);
                if (nullAnnotationMatchingAnalyse.isAnyMismatch()) {
                    blockScope.problemReporter().illegalReturnRedefinition(this, this.descriptor, nullAnnotationMatchingAnalyse.isUnchecked(), typeBindingCreateAnnotatedType);
                }
            }
        }
    }

    private TypeBinding[] descriptorParametersAsArgumentExpressions() {
        if (this.descriptor == null || this.descriptor.parameters == null || this.descriptor.parameters.length == 0) {
            return Binding.NO_PARAMETERS;
        }
        if (this.expectedType.isParameterizedType()) {
            return ((ParameterizedTypeBinding) this.expectedType).getSingleAbstractMethod(this.enclosingScope, true, this.sourceStart, this.sourceEnd).parameters;
        }
        return this.descriptor.parameters;
    }

    private ReferenceExpression cachedResolvedCopy(TypeBinding typeBinding) {
        HashMap<TypeBinding, ReferenceExpression> map = this.copiesPerTargetType;
        ReferenceExpression referenceExpression = map != null ? map.get(typeBinding) : null;
        if (referenceExpression != null) {
            return referenceExpression;
        }
        IErrorHandlingPolicy iErrorHandlingPolicySwitchErrorHandlingPolicy = this.enclosingScope.problemReporter().switchErrorHandlingPolicy(silentErrorHandlingPolicy);
        try {
            ReferenceExpression referenceExpressionCopy = copy();
            if (referenceExpressionCopy == null) {
                return null;
            }
            referenceExpressionCopy.setExpressionContext(this.expressionContext);
            referenceExpressionCopy.setExpectedType(typeBinding);
            referenceExpressionCopy.resolveType(this.enclosingScope);
            if (this.copiesPerTargetType == null) {
                this.copiesPerTargetType = new HashMap<>();
            }
            this.copiesPerTargetType.put(typeBinding, referenceExpressionCopy);
            return referenceExpressionCopy;
        } finally {
            this.enclosingScope.problemReporter().switchErrorHandlingPolicy(iErrorHandlingPolicySwitchErrorHandlingPolicy);
        }
    }

    public void registerInferenceContext(ParameterizedGenericMethodBinding parameterizedGenericMethodBinding, InferenceContext18 inferenceContext18) {
        if (this.inferenceContexts == null) {
            this.inferenceContexts = new HashMap<>();
        }
        this.inferenceContexts.put(parameterizedGenericMethodBinding, inferenceContext18);
    }

    public InferenceContext18 getInferenceContext(ParameterizedMethodBinding parameterizedMethodBinding) {
        HashMap<ParameterizedGenericMethodBinding, InferenceContext18> map = this.inferenceContexts;
        if (map == null) {
            return null;
        }
        return map.get(parameterizedMethodBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public ReferenceExpression resolveExpressionExpecting(TypeBinding typeBinding, Scope scope, InferenceContext18 inferenceContext18) {
        if (this.exactMethodBinding != null) {
            MethodBinding singleAbstractMethod = typeBinding.getSingleAbstractMethod(scope, true);
            if (singleAbstractMethod == null || singleAbstractMethod.problemId() == 17) {
                return null;
            }
            int length = singleAbstractMethod.parameters.length;
            int length2 = this.exactMethodBinding.parameters.length;
            if (!this.haveReceiver && isMethodReference() && !this.exactMethodBinding.isStatic()) {
                length2++;
            }
            if (length == length2) {
                return this;
            }
            return null;
        }
        ReferenceExpression referenceExpressionCachedResolvedCopy = cachedResolvedCopy(typeBinding);
        if (referenceExpressionCachedResolvedCopy == null || referenceExpressionCachedResolvedCopy.resolvedType == null || !referenceExpressionCachedResolvedCopy.resolvedType.isValidBinding() || referenceExpressionCachedResolvedCopy.binding == null || !referenceExpressionCachedResolvedCopy.binding.isValidBinding()) {
            return null;
        }
        return referenceExpressionCachedResolvedCopy;
    }

    public boolean isConstructorReference() {
        return CharOperation.equals(this.selector, ConstantPool.Init);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public boolean isExactMethodReference() {
        return this.exactMethodBinding != null;
    }

    public MethodBinding getExactMethod() {
        return this.exactMethodBinding;
    }

    public boolean isMethodReference() {
        return !CharOperation.equals(this.selector, ConstantPool.Init);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.FunctionalExpression, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isPertinentToApplicability(TypeBinding typeBinding, MethodBinding methodBinding) {
        if (isExactMethodReference()) {
            return super.isPertinentToApplicability(typeBinding, methodBinding);
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public TypeBinding[] genericTypeArguments() {
        return this.resolvedTypeArguments;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public InferenceContext18 freshInferenceContext(Scope scope) {
        if (this.expressionContext != ExpressionContext.VANILLA_CONTEXT) {
            return new InferenceContext18(scope, createPseudoExpressions(this.freeParameters), this, null);
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean isSuperAccess() {
        return this.lhs.isSuper();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean isTypeAccess() {
        return !this.haveReceiver;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public void setDepth(int i) {
        this.depth = i;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        this.lhs.print(0, stringBuffer);
        stringBuffer.append("::");
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
        if (isConstructorReference()) {
            stringBuffer.append(PsiKeyword.NEW);
        } else {
            stringBuffer.append(this.selector);
        }
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.lhs.traverse(aSTVisitor, blockScope);
            TypeReference[] typeReferenceArr = this.typeArguments;
            int length = typeReferenceArr == null ? 0 : typeReferenceArr.length;
            for (int i = 0; i < length; i++) {
                this.typeArguments[i].traverse(aSTVisitor, blockScope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    public Expression[] createPseudoExpressions(TypeBinding[] typeBindingArr) {
        Expression[] expressionArr = new Expression[typeBindingArr.length];
        long j = (((long) this.sourceStart) << 32) + ((long) this.sourceEnd);
        for (int i = 0; i < typeBindingArr.length; i++) {
            expressionArr[i] = new SingleNameReference(("fakeArg" + i).toCharArray(), j);
            expressionArr[i].resolvedType = typeBindingArr[i];
        }
        return expressionArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isPotentiallyCompatibleWith(TypeBinding typeBinding, Scope scope) {
        TypeBinding typeBinding2;
        boolean zIsConstructorReference = isConstructorReference();
        if (zIsConstructorReference) {
            TypeBinding typeBinding3 = this.receiverType;
            if (typeBinding3 == null) {
                return false;
            }
            if (typeBinding3.isArrayType() && !this.receiverType.leafComponentType().isReifiable()) {
                return false;
            }
        }
        if (!super.isPertinentToApplicability(typeBinding, null)) {
            return true;
        }
        MethodBinding singleAbstractMethod = typeBinding.getSingleAbstractMethod(this.enclosingScope, true);
        if (singleAbstractMethod == null || !singleAbstractMethod.isValidBinding() || this.typeArgumentsHaveErrors || (typeBinding2 = this.receiverType) == null || !typeBinding2.isValidBinding()) {
            return false;
        }
        int length = singleAbstractMethod.parameters.length;
        TypeBinding[] typeBindingArr = new TypeBinding[length];
        for (int i = 0; i < length; i++) {
            typeBindingArr[i] = new ReferenceBinding() { // from class: org.eclipse.jdt.internal.compiler.ast.ReferenceExpression.2
                {
                    this.compoundName = CharOperation.NO_CHAR_CHAR;
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
                public TypeBinding findSuperTypeOriginatingFrom(TypeBinding typeBinding4) {
                    return typeBinding4;
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
                public boolean isCompatibleWith(TypeBinding typeBinding4, Scope scope2) {
                    return true;
                }

                public String toString() {
                    return "(wildcard)";
                }
            };
        }
        this.freeParameters = typeBindingArr;
        this.checkingPotentialCompatibility = true;
        try {
            MethodBinding compileTimeDeclaration = getCompileTimeDeclaration(scope, zIsConstructorReference, typeBindingArr);
            if (compileTimeDeclaration != null && compileTimeDeclaration.isValidBinding()) {
                this.potentialMethods = new MethodBinding[]{compileTimeDeclaration};
            }
            int length2 = this.potentialMethods.length;
            for (int i2 = 0; i2 < length2; i2++) {
                if (!this.potentialMethods[i2].isStatic() && !this.potentialMethods[i2].isConstructor()) {
                    if (this.haveReceiver) {
                        this.checkingPotentialCompatibility = false;
                        this.potentialMethods = Binding.NO_METHODS;
                        return true;
                    }
                }
                if (!this.haveReceiver) {
                    this.checkingPotentialCompatibility = false;
                    this.potentialMethods = Binding.NO_METHODS;
                    return true;
                }
            }
            if (this.haveReceiver || length == 0) {
                this.checkingPotentialCompatibility = false;
                this.potentialMethods = Binding.NO_METHODS;
            } else {
                int i3 = length - 1;
                TypeBinding[] typeBindingArr2 = new TypeBinding[i3];
                System.arraycopy(typeBindingArr, 1, typeBindingArr2, 0, i3);
                this.freeParameters = typeBindingArr2;
                MethodBinding compileTimeDeclaration2 = getCompileTimeDeclaration(scope, false, typeBindingArr2);
                if (compileTimeDeclaration2 != null && compileTimeDeclaration2.isValidBinding()) {
                    this.potentialMethods = new MethodBinding[]{compileTimeDeclaration2};
                }
                int length3 = this.potentialMethods.length;
                for (int i4 = 0; i4 < length3; i4++) {
                    if (!this.potentialMethods[i4].isStatic()) {
                        this.checkingPotentialCompatibility = false;
                        this.potentialMethods = Binding.NO_METHODS;
                        return true;
                    }
                }
                this.checkingPotentialCompatibility = false;
                this.potentialMethods = Binding.NO_METHODS;
            }
            return false;
        } finally {
            this.checkingPotentialCompatibility = false;
            this.potentialMethods = Binding.NO_METHODS;
            this.freeParameters = null;
        }
    }

    MethodBinding getCompileTimeDeclaration(Scope scope, boolean z, TypeBinding[] typeBindingArr) {
        MethodBinding methodBinding = this.exactMethodBinding;
        if (methodBinding != null) {
            return methodBinding;
        }
        if (this.receiverType.isArrayType()) {
            return scope.findMethodForArray((ArrayBinding) this.receiverType, this.selector, Binding.NO_PARAMETERS, this);
        }
        if (z) {
            return scope.getConstructor((ReferenceBinding) this.receiverType, typeBindingArr, this);
        }
        return scope.getMethod(this.receiverType, this.selector, typeBindingArr, this);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isCompatibleWith(TypeBinding typeBinding, Scope scope) {
        ReferenceExpression referenceExpressionCachedResolvedCopy = cachedResolvedCopy(typeBinding);
        return (referenceExpressionCachedResolvedCopy == null || referenceExpressionCachedResolvedCopy.resolvedType == null || !referenceExpressionCachedResolvedCopy.resolvedType.isValidBinding() || referenceExpressionCachedResolvedCopy.binding == null || !referenceExpressionCachedResolvedCopy.binding.isValidBinding()) ? false : true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean sIsMoreSpecific(TypeBinding typeBinding, TypeBinding typeBinding2, Scope scope) {
        MethodBinding singleAbstractMethod;
        if (super.sIsMoreSpecific(typeBinding, typeBinding2, scope)) {
            return true;
        }
        if (this.exactMethodBinding != null && typeBinding2.findSuperTypeOriginatingFrom(typeBinding) == null && (singleAbstractMethod = typeBinding.capture(this.enclosingScope, this.sourceStart, this.sourceEnd).getSingleAbstractMethod(this.enclosingScope, true)) != null && singleAbstractMethod.isValidBinding()) {
            TypeBinding typeBinding3 = singleAbstractMethod.returnType;
            MethodBinding singleAbstractMethod2 = typeBinding2.getSingleAbstractMethod(this.enclosingScope, true);
            if (singleAbstractMethod2 != null && singleAbstractMethod2.isValidBinding()) {
                TypeBinding typeBinding4 = singleAbstractMethod2.returnType;
                TypeBinding[] typeBindingArr = singleAbstractMethod.parameters;
                TypeBinding[] typeBindingArr2 = singleAbstractMethod2.parameters;
                for (int i = 0; i < typeBindingArr.length; i++) {
                    if (TypeBinding.notEquals(typeBindingArr[i], typeBindingArr2[i])) {
                        return false;
                    }
                }
                if (typeBinding4.f626id == 6) {
                    return true;
                }
                if (typeBinding3.f626id == 6) {
                    return false;
                }
                if (typeBinding3.isCompatibleWith(typeBinding4, scope)) {
                    return true;
                }
                return typeBinding3.isBaseType() != typeBinding4.isBaseType() && typeBinding3.isBaseType() == this.exactMethodBinding.returnType.isBaseType();
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.FunctionalExpression
    public MethodBinding getMethodBinding() {
        if (this.actualMethodBinding == null) {
            this.actualMethodBinding = this.binding;
        }
        return this.actualMethodBinding;
    }

    public boolean isArrayConstructorReference() {
        return isConstructorReference() && this.lhs.resolvedType != null && this.lhs.resolvedType.isArrayType();
    }
}
