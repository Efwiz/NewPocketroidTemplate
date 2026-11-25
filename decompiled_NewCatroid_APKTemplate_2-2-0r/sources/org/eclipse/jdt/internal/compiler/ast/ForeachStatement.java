package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.LoopingFlowContext;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.CaptureBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ForeachStatement extends Statement {
    private static final int ARRAY = 0;
    private static final int GENERIC_ITERABLE = 2;
    private static final int RAW_ITERABLE = 1;
    public Statement action;
    private BranchLabel breakLabel;
    public Expression collection;
    private TypeBinding collectionElementType;
    public LocalVariableBinding collectionVariable;
    private BranchLabel continueLabel;
    public LocalDeclaration elementVariable;
    public LocalVariableBinding indexVariable;
    private TypeBinding iteratorReceiverType;
    private int kind;
    public LocalVariableBinding maxVariable;
    public BlockScope scope;
    private static final char[] SecretIteratorVariableName = " iterator".toCharArray();
    private static final char[] SecretIndexVariableName = " index".toCharArray();
    private static final char[] SecretCollectionVariableName = " collection".toCharArray();
    private static final char[] SecretMaxVariableName = " max".toCharArray();
    public int elementVariableImplicitWidening = -1;
    int postCollectionInitStateIndex = -1;
    int mergedInitStateIndex = -1;

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean doesNotCompleteNormally() {
        return false;
    }

    public ForeachStatement(LocalDeclaration localDeclaration, int i) {
        this.elementVariable = localDeclaration;
        this.sourceStart = i;
        this.kind = -1;
    }

    /* JADX WARN: Code duplicated, block: B:36:0x0115  */
    /* JADX WARN: Code duplicated, block: B:39:0x011a  */
    /* JADX WARN: Code duplicated, block: B:40:0x011f  */
    /* JADX WARN: Code duplicated, block: B:43:0x0126  */
    /* JADX WARN: Code duplicated, block: B:45:0x012e  */
    /* JADX WARN: Code duplicated, block: B:48:0x013f  */
    /* JADX WARN: Code duplicated, block: B:50:0x0147  */
    /* JADX WARN: Code duplicated, block: B:54:0x0162  */
    /* JADX WARN: Code duplicated, block: B:55:0x0165  */
    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        FlowInfo flowInfoInitsWhenFalse;
        int i;
        int i2;
        int i3;
        FlowInfo flowInfoAddInitializationsFrom;
        FlowInfo flowInfoCopy;
        this.breakLabel = new BranchLabel();
        this.continueLabel = new BranchLabel();
        int i4 = (flowInfo.reachMode() & 3) != 0 ? 1 : 0;
        this.collection.checkNPE(blockScope, flowContext, flowInfo, 1);
        FlowInfo flowInfoAnalyseCode = this.elementVariable.analyseCode(this.scope, flowContext, flowInfo);
        FlowInfo flowInfoAnalyseCode2 = this.collection.analyseCode(this.scope, flowContext, flowInfoAnalyseCode.copy());
        LocalVariableBinding localVariableBinding = this.elementVariable.binding;
        flowInfoAnalyseCode2.markAsDefinitelyAssigned(localVariableBinding);
        this.postCollectionInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfoAnalyseCode2);
        LoopingFlowContext loopingFlowContext = new LoopingFlowContext(flowContext, flowInfoAnalyseCode, this, this.breakLabel, this.continueLabel, this.scope, true);
        UnconditionalFlowInfo unconditionalFlowInfoNullInfoLessUnconditionalCopy = flowInfoAnalyseCode2.nullInfoLessUnconditionalCopy();
        unconditionalFlowInfoNullInfoLessUnconditionalCopy.markAsDefinitelyUnknown(localVariableBinding);
        if (blockScope.compilerOptions().isAnnotationBasedNullAnalysisEnabled) {
            int iCheckAssignment = NullAnnotationMatching.checkAssignment(blockScope, flowContext, localVariableBinding, null, FlowInfo.tagBitsToNullStatus(this.collectionElementType.tagBits), this.collection, this.collectionElementType);
            if ((localVariableBinding.type.tagBits & 2) == 0) {
                unconditionalFlowInfoNullInfoLessUnconditionalCopy.markNullStatus(localVariableBinding, iCheckAssignment);
            }
        }
        Statement statement = this.action;
        if (statement != null && (!statement.isEmptyBlock() || blockScope.compilerOptions().complianceLevel > ClassFileConstants.JDK1_3)) {
            if (this.action.complainIfUnreachable(unconditionalFlowInfoNullInfoLessUnconditionalCopy, this.scope, i4, true) < 2) {
                unconditionalFlowInfoNullInfoLessUnconditionalCopy = this.action.analyseCode(this.scope, loopingFlowContext, unconditionalFlowInfoNullInfoLessUnconditionalCopy).unconditionalCopy();
            }
            flowInfoInitsWhenFalse = flowInfoAnalyseCode.unconditionalCopy().addInitializationsFrom(flowInfoAnalyseCode2.initsWhenFalse());
            if ((unconditionalFlowInfoNullInfoLessUnconditionalCopy.tagBits & loopingFlowContext.initsOnContinue.tagBits & 1) != 0) {
                this.continueLabel = null;
            } else {
                unconditionalFlowInfoNullInfoLessUnconditionalCopy = unconditionalFlowInfoNullInfoLessUnconditionalCopy.mergedWith(loopingFlowContext.initsOnContinue);
                loopingFlowContext.complainOnDeferredFinalChecks(this.scope, unconditionalFlowInfoNullInfoLessUnconditionalCopy);
                flowInfoInitsWhenFalse.addPotentialInitializationsFrom(unconditionalFlowInfoNullInfoLessUnconditionalCopy);
            }
        } else {
            flowInfoInitsWhenFalse = flowInfoAnalyseCode2.initsWhenFalse();
        }
        Statement statement2 = this.action;
        if (statement2 != null && !statement2.isEmptyBlock()) {
            i = 1;
            if ((this.action.bits & 1) == 0) {
                i2 = 0;
            }
            i3 = this.kind;
            if (i3 != 0) {
                if (i3 != i || i3 == 2) {
                    this.indexVariable.useFlag = i;
                }
            } else if (i2 != 0 || localVariableBinding.resolvedPosition != -1) {
                this.collectionVariable.useFlag = i;
                if (this.continueLabel != null) {
                    this.indexVariable.useFlag = i;
                    this.maxVariable.useFlag = i;
                }
            }
            loopingFlowContext.complainOnDeferredNullChecks(blockScope, unconditionalFlowInfoNullInfoLessUnconditionalCopy);
            if (loopingFlowContext.hasEscapingExceptions()) {
                flowInfoCopy = flowInfoAnalyseCode.copy();
                if (this.continueLabel != null) {
                    flowInfoCopy = flowInfoCopy.mergedWith(flowInfoCopy.unconditionalCopy().addNullInfoFrom(unconditionalFlowInfoNullInfoLessUnconditionalCopy).unconditionalInits());
                }
                loopingFlowContext.simulateThrowAfterLoopBack(flowInfoCopy);
            }
            if ((loopingFlowContext.initsOnBreak.tagBits & 3) != 0) {
                flowInfoAddInitializationsFrom = loopingFlowContext.initsOnBreak;
            } else {
                flowInfoAddInitializationsFrom = flowInfoAnalyseCode.addInitializationsFrom(loopingFlowContext.initsOnBreak);
            }
            UnconditionalFlowInfo unconditionalFlowInfoMergedOptimizedBranches = FlowInfo.mergedOptimizedBranches(flowInfoAddInitializationsFrom, false, flowInfoInitsWhenFalse, false, true);
            unconditionalFlowInfoMergedOptimizedBranches.resetAssignmentInfo(this.elementVariable.binding);
            this.mergedInitStateIndex = blockScope.methodScope().recordInitializationStates(unconditionalFlowInfoMergedOptimizedBranches);
            return unconditionalFlowInfoMergedOptimizedBranches;
        }
        i = 1;
        i2 = i;
        i3 = this.kind;
        if (i3 != 0) {
            if (i3 != i) {
                this.indexVariable.useFlag = i;
            } else {
                this.indexVariable.useFlag = i;
            }
        } else if (i2 != 0) {
            this.collectionVariable.useFlag = i;
            if (this.continueLabel != null) {
                this.indexVariable.useFlag = i;
                this.maxVariable.useFlag = i;
            }
        } else {
            this.collectionVariable.useFlag = i;
            if (this.continueLabel != null) {
                this.indexVariable.useFlag = i;
                this.maxVariable.useFlag = i;
            }
        }
        loopingFlowContext.complainOnDeferredNullChecks(blockScope, unconditionalFlowInfoNullInfoLessUnconditionalCopy);
        if (loopingFlowContext.hasEscapingExceptions()) {
            flowInfoCopy = flowInfoAnalyseCode.copy();
            if (this.continueLabel != null) {
                flowInfoCopy = flowInfoCopy.mergedWith(flowInfoCopy.unconditionalCopy().addNullInfoFrom(unconditionalFlowInfoNullInfoLessUnconditionalCopy).unconditionalInits());
            }
            loopingFlowContext.simulateThrowAfterLoopBack(flowInfoCopy);
        }
        if ((loopingFlowContext.initsOnBreak.tagBits & 3) != 0) {
            flowInfoAddInitializationsFrom = loopingFlowContext.initsOnBreak;
        } else {
            flowInfoAddInitializationsFrom = flowInfoAnalyseCode.addInitializationsFrom(loopingFlowContext.initsOnBreak);
        }
        UnconditionalFlowInfo unconditionalFlowInfoMergedOptimizedBranches2 = FlowInfo.mergedOptimizedBranches(flowInfoAddInitializationsFrom, false, flowInfoInitsWhenFalse, false, true);
        unconditionalFlowInfoMergedOptimizedBranches2.resetAssignmentInfo(this.elementVariable.binding);
        this.mergedInitStateIndex = blockScope.methodScope().recordInitializationStates(unconditionalFlowInfoMergedOptimizedBranches2);
        return unconditionalFlowInfoMergedOptimizedBranches2;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
        if ((this.bits & Integer.MIN_VALUE) == 0) {
            return;
        }
        int i = codeStream.position;
        Statement statement = this.action;
        boolean z = statement == null || statement.isEmptyBlock() || (this.action.bits & 1) != 0;
        if (z && this.elementVariable.binding.resolvedPosition == -1 && this.kind == 0) {
            this.collection.generateCode(this.scope, codeStream, false);
            codeStream.exitUserScope(this.scope);
            int i2 = this.mergedInitStateIndex;
            if (i2 != -1) {
                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i2);
                codeStream.addDefinitelyAssignedVariables(blockScope, this.mergedInitStateIndex);
            }
            codeStream.recordPositionsFrom(i, this.sourceStart);
            return;
        }
        int i3 = this.kind;
        if (i3 == 0) {
            this.collection.generateCode(this.scope, codeStream, true);
            codeStream.store(this.collectionVariable, true);
            codeStream.addVariable(this.collectionVariable);
            if (this.continueLabel != null) {
                codeStream.arraylength();
                codeStream.store(this.maxVariable, false);
                codeStream.addVariable(this.maxVariable);
                codeStream.iconst_0();
                codeStream.store(this.indexVariable, false);
                codeStream.addVariable(this.indexVariable);
            }
        } else if (i3 == 1 || i3 == 2) {
            this.collection.generateCode(this.scope, codeStream, true);
            codeStream.invokeIterableIterator(this.iteratorReceiverType);
            codeStream.store(this.indexVariable, false);
            codeStream.addVariable(this.indexVariable);
        }
        BranchLabel branchLabel = new BranchLabel(codeStream);
        branchLabel.tagBits |= 2;
        BranchLabel branchLabel2 = new BranchLabel(codeStream);
        branchLabel2.tagBits |= 2;
        this.breakLabel.initialize(codeStream);
        BranchLabel branchLabel3 = this.continueLabel;
        if (branchLabel3 == null) {
            branchLabel2.place();
            int i4 = codeStream.position;
            int i5 = this.kind;
            if (i5 == 0) {
                codeStream.arraylength();
                codeStream.ifeq(this.breakLabel);
            } else if (i5 == 1 || i5 == 2) {
                codeStream.load(this.indexVariable);
                codeStream.invokeJavaUtilIteratorHasNext();
                codeStream.ifeq(this.breakLabel);
            }
            codeStream.recordPositionsFrom(i4, this.elementVariable.sourceStart);
        } else {
            branchLabel3.initialize(codeStream);
            this.continueLabel.tagBits |= 2;
            codeStream.goto_(branchLabel2);
        }
        branchLabel.place();
        int i6 = this.kind;
        if (i6 != 0) {
            if (i6 == 1 || i6 == 2) {
                codeStream.load(this.indexVariable);
                codeStream.invokeJavaUtilIteratorNext();
                if (this.elementVariable.binding.type.f626id != 1) {
                    if (this.elementVariableImplicitWidening != -1) {
                        codeStream.checkcast(this.collectionElementType);
                        codeStream.generateImplicitConversion(this.elementVariableImplicitWidening);
                    } else {
                        codeStream.checkcast(this.elementVariable.binding.type);
                    }
                }
                if (this.elementVariable.binding.resolvedPosition == -1) {
                    int i7 = this.elementVariable.binding.type.f626id;
                    if (i7 == 7 || i7 == 8) {
                        codeStream.pop2();
                    } else {
                        codeStream.pop();
                    }
                } else {
                    codeStream.store(this.elementVariable.binding, false);
                    codeStream.addVisibleLocalVariable(this.elementVariable.binding);
                    int i8 = this.postCollectionInitStateIndex;
                    if (i8 != -1) {
                        codeStream.addDefinitelyAssignedVariables(blockScope, i8);
                    }
                }
            }
        } else if (this.elementVariable.binding.resolvedPosition != -1) {
            codeStream.load(this.collectionVariable);
            if (this.continueLabel == null) {
                codeStream.iconst_0();
            } else {
                codeStream.load(this.indexVariable);
            }
            codeStream.arrayAt(this.collectionElementType.f626id);
            int i9 = this.elementVariableImplicitWidening;
            if (i9 != -1) {
                codeStream.generateImplicitConversion(i9);
            }
            codeStream.store(this.elementVariable.binding, false);
            codeStream.addVisibleLocalVariable(this.elementVariable.binding);
            int i10 = this.postCollectionInitStateIndex;
            if (i10 != -1) {
                codeStream.addDefinitelyAssignedVariables(blockScope, i10);
            }
        }
        if (!z) {
            this.action.generateCode(this.scope, codeStream);
        }
        codeStream.removeVariable(this.elementVariable.binding);
        int i11 = this.postCollectionInitStateIndex;
        if (i11 != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i11);
        }
        BranchLabel branchLabel4 = this.continueLabel;
        if (branchLabel4 != null) {
            branchLabel4.place();
            int i12 = codeStream.position;
            int i13 = this.kind;
            if (i13 == 0) {
                if (!z || this.elementVariable.binding.resolvedPosition >= 0) {
                    codeStream.iinc(this.indexVariable.resolvedPosition, 1);
                }
                branchLabel2.place();
                codeStream.load(this.indexVariable);
                codeStream.load(this.maxVariable);
                codeStream.if_icmplt(branchLabel);
            } else if (i13 == 1 || i13 == 2) {
                branchLabel2.place();
                codeStream.load(this.indexVariable);
                codeStream.invokeJavaUtilIteratorHasNext();
                codeStream.ifne(branchLabel);
            }
            codeStream.recordPositionsFrom(i12, this.elementVariable.sourceStart);
        }
        int i14 = this.kind;
        if (i14 == 0) {
            codeStream.removeVariable(this.indexVariable);
            codeStream.removeVariable(this.maxVariable);
            codeStream.removeVariable(this.collectionVariable);
        } else if (i14 == 1 || i14 == 2) {
            codeStream.removeVariable(this.indexVariable);
        }
        codeStream.exitUserScope(this.scope);
        int i15 = this.mergedInitStateIndex;
        if (i15 != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i15);
            codeStream.addDefinitelyAssignedVariables(blockScope, this.mergedInitStateIndex);
        }
        this.breakLabel.place();
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer).append("for (");
        this.elementVariable.printAsExpression(0, stringBuffer);
        stringBuffer.append(" : ");
        Expression expression = this.collection;
        if (expression != null) {
            expression.print(0, stringBuffer).append(") ");
        } else {
            stringBuffer.append(')');
        }
        if (this.action == null) {
            stringBuffer.append(';');
        } else {
            stringBuffer.append('\n');
            this.action.printStatement(i + 1, stringBuffer);
        }
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
        TypeBinding[] typeBindingArrTypeVariables;
        TypeBinding typeBinding;
        BlockScope blockScope2 = new BlockScope(blockScope);
        this.scope = blockScope2;
        this.elementVariable.resolve(blockScope2);
        TypeBinding typeBinding2 = this.elementVariable.type.resolvedType;
        Expression expression = this.collection;
        ArrayBinding arrayBindingCreateArrayType = null;
        TypeBinding typeBindingResolveType = expression == null ? null : expression.resolveType(blockScope);
        if (typeBinding2 != null && typeBindingResolveType != null) {
            boolean z = this.scope.compilerOptions().targetJDK == ClassFileConstants.JDK1_4;
            if (typeBindingResolveType.isCapture() && (typeBinding = ((CaptureBinding) typeBindingResolveType).firstBound) != null && typeBinding.isArrayType()) {
                typeBindingResolveType = typeBinding;
            }
            if (typeBindingResolveType.isArrayType()) {
                this.kind = 0;
                TypeBinding typeBindingElementsType = ((ArrayBinding) typeBindingResolveType).elementsType();
                this.collectionElementType = typeBindingElementsType;
                if (!typeBindingElementsType.isCompatibleWith(typeBinding2) && !this.scope.isBoxingCompatibleWith(this.collectionElementType, typeBinding2)) {
                    this.scope.problemReporter().notCompatibleTypesErrorInForeach(this.collection, this.collectionElementType, typeBinding2);
                } else if (this.collectionElementType.needsUncheckedConversion(typeBinding2)) {
                    this.scope.problemReporter().unsafeElementTypeConversion(this.collection, this.collectionElementType, typeBinding2);
                }
                int i = this.collectionElementType.f626id;
                if (typeBinding2.isBaseType()) {
                    this.collection.computeConversion(this.scope, typeBindingResolveType, typeBindingResolveType);
                    if (!this.collectionElementType.isBaseType()) {
                        int i2 = this.scope.environment().computeBoxingType(this.collectionElementType).f626id;
                        this.elementVariableImplicitWidening = 1024;
                        if (typeBinding2.isBaseType()) {
                            this.elementVariableImplicitWidening |= (typeBinding2.f626id << 4) + i2;
                            this.scope.problemReporter().autoboxing(this.collection, this.collectionElementType, typeBinding2);
                        }
                    } else {
                        this.elementVariableImplicitWidening = (typeBinding2.f626id << 4) + i;
                    }
                } else if (this.collectionElementType.isBaseType()) {
                    this.collection.computeConversion(this.scope, typeBindingResolveType, typeBindingResolveType);
                    int i3 = this.scope.environment().computeBoxingType(this.collectionElementType).f626id;
                    this.elementVariableImplicitWidening = (i << 4) | 512 | i;
                    this.scope.problemReporter().autoboxing(this.collection, this.collectionElementType, typeBinding2);
                } else {
                    arrayBindingCreateArrayType = blockScope.createArrayType(typeBinding2, 1);
                    this.collection.computeConversion(this.scope, arrayBindingCreateArrayType, typeBindingResolveType);
                }
            } else if (typeBindingResolveType instanceof ReferenceBinding) {
                ReferenceBinding referenceBinding = (ReferenceBinding) typeBindingResolveType;
                ReferenceBinding referenceBindingFindSuperTypeOriginatingFrom = referenceBinding.findSuperTypeOriginatingFrom(38, false);
                if (referenceBindingFindSuperTypeOriginatingFrom == null && z) {
                    referenceBindingFindSuperTypeOriginatingFrom = referenceBinding.findSuperTypeOriginatingFrom(59, false);
                }
                if (referenceBindingFindSuperTypeOriginatingFrom != null) {
                    TypeBinding typeBindingErasure = typeBindingResolveType.erasure();
                    this.iteratorReceiverType = typeBindingErasure;
                    if (z) {
                        if (((ReferenceBinding) typeBindingErasure).findSuperTypeOriginatingFrom(59, false) == null) {
                            this.iteratorReceiverType = referenceBindingFindSuperTypeOriginatingFrom;
                            this.collection.computeConversion(this.scope, referenceBindingFindSuperTypeOriginatingFrom, typeBindingResolveType);
                        } else {
                            this.collection.computeConversion(this.scope, typeBindingResolveType, typeBindingResolveType);
                        }
                    } else if (((ReferenceBinding) typeBindingErasure).findSuperTypeOriginatingFrom(38, false) == null) {
                        this.iteratorReceiverType = referenceBindingFindSuperTypeOriginatingFrom;
                        this.collection.computeConversion(this.scope, referenceBindingFindSuperTypeOriginatingFrom, typeBindingResolveType);
                    } else {
                        this.collection.computeConversion(this.scope, typeBindingResolveType, typeBindingResolveType);
                    }
                    int iKind = referenceBindingFindSuperTypeOriginatingFrom.kind();
                    if (iKind == 260) {
                        typeBindingArrTypeVariables = ((ParameterizedTypeBinding) referenceBindingFindSuperTypeOriginatingFrom).arguments;
                    } else if (iKind == 1028) {
                        this.kind = 1;
                        ReferenceBinding javaLangObject = this.scope.getJavaLangObject();
                        this.collectionElementType = javaLangObject;
                        if (!javaLangObject.isCompatibleWith(typeBinding2) && !this.scope.isBoxingCompatibleWith(this.collectionElementType, typeBinding2)) {
                            this.scope.problemReporter().notCompatibleTypesErrorInForeach(this.collection, this.collectionElementType, typeBinding2);
                        }
                    } else if (iKind == 2052) {
                        typeBindingArrTypeVariables = referenceBindingFindSuperTypeOriginatingFrom.typeVariables();
                    }
                    if (typeBindingArrTypeVariables.length == 1) {
                        this.kind = 2;
                        TypeBinding typeBinding3 = typeBindingArrTypeVariables[0];
                        this.collectionElementType = typeBinding3;
                        if (!typeBinding3.isCompatibleWith(typeBinding2) && !this.scope.isBoxingCompatibleWith(this.collectionElementType, typeBinding2)) {
                            this.scope.problemReporter().notCompatibleTypesErrorInForeach(this.collection, this.collectionElementType, typeBinding2);
                        } else if (this.collectionElementType.needsUncheckedConversion(typeBinding2)) {
                            this.scope.problemReporter().unsafeElementTypeConversion(this.collection, this.collectionElementType, typeBinding2);
                        }
                        int i4 = this.collectionElementType.f626id;
                        if (typeBinding2.isBaseType()) {
                            if (!this.collectionElementType.isBaseType()) {
                                int i5 = this.scope.environment().computeBoxingType(this.collectionElementType).f626id;
                                this.elementVariableImplicitWidening = 1024;
                                if (typeBinding2.isBaseType()) {
                                    this.elementVariableImplicitWidening |= (typeBinding2.f626id << 4) + i5;
                                }
                            } else {
                                this.elementVariableImplicitWidening = (typeBinding2.f626id << 4) + i4;
                            }
                        } else if (this.collectionElementType.isBaseType()) {
                            this.elementVariableImplicitWidening = i4 | (i4 << 4) | 512;
                        }
                    }
                }
            }
            int i6 = this.kind;
            if (i6 == 0) {
                LocalVariableBinding localVariableBinding = new LocalVariableBinding(SecretIndexVariableName, (TypeBinding) TypeBinding.INT, 0, false);
                this.indexVariable = localVariableBinding;
                this.scope.addLocalVariable(localVariableBinding);
                this.indexVariable.setConstant(Constant.NotAConstant);
                LocalVariableBinding localVariableBinding2 = new LocalVariableBinding(SecretMaxVariableName, (TypeBinding) TypeBinding.INT, 0, false);
                this.maxVariable = localVariableBinding2;
                this.scope.addLocalVariable(localVariableBinding2);
                this.maxVariable.setConstant(Constant.NotAConstant);
                if (arrayBindingCreateArrayType == null) {
                    this.collectionVariable = new LocalVariableBinding(SecretCollectionVariableName, typeBindingResolveType, 0, false);
                } else {
                    this.collectionVariable = new LocalVariableBinding(SecretCollectionVariableName, (TypeBinding) arrayBindingCreateArrayType, 0, false);
                }
                this.scope.addLocalVariable(this.collectionVariable);
                this.collectionVariable.setConstant(Constant.NotAConstant);
            } else if (i6 == 1 || i6 == 2) {
                LocalVariableBinding localVariableBinding3 = new LocalVariableBinding(SecretIteratorVariableName, (TypeBinding) this.scope.getJavaUtilIterator(), 0, false);
                this.indexVariable = localVariableBinding3;
                this.scope.addLocalVariable(localVariableBinding3);
                this.indexVariable.setConstant(Constant.NotAConstant);
            } else if (z) {
                this.scope.problemReporter().invalidTypeForCollectionTarget14(this.collection);
            } else {
                this.scope.problemReporter().invalidTypeForCollection(this.collection);
            }
        }
        Statement statement = this.action;
        if (statement != null) {
            statement.resolve(this.scope);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.elementVariable.traverse(aSTVisitor, this.scope);
            Expression expression = this.collection;
            if (expression != null) {
                expression.traverse(aSTVisitor, this.scope);
            }
            Statement statement = this.action;
            if (statement != null) {
                statement.traverse(aSTVisitor, this.scope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }
}
