package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.codegen.ConstantPool;
import org.eclipse.jdt.internal.compiler.codegen.ExceptionLabel;
import org.eclipse.jdt.internal.compiler.codegen.MultiCatchExceptionLabel;
import org.eclipse.jdt.internal.compiler.codegen.StackMapFrameCodeStream;
import org.eclipse.jdt.internal.compiler.flow.ExceptionHandlingFlowContext;
import org.eclipse.jdt.internal.compiler.flow.FinallyFlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.InsideSubRoutineFlowContext;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.InvocationSite;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes14.dex */
public class TryStatement extends SubRoutineStatement {
    private static final int FINALLY_DOES_NOT_COMPLETE = 2;
    private static final int FINALLY_INLINE = 3;
    private static final int FINALLY_SUBROUTINE = 1;
    private static final int NO_FINALLY = 0;
    public LocalVariableBinding anyExceptionVariable;
    public Argument[] catchArguments;
    public Block[] catchBlocks;
    int[] catchExitInitStateIndexes;
    boolean[] catchExits;
    ReferenceBinding[] caughtExceptionTypes;
    private int[] caughtExceptionsCatchBlocks;
    private LocalVariableBinding caughtThrowableVariable;
    ExceptionLabel[] declaredExceptionLabels;
    public Block finallyBlock;
    int[] postResourcesInitStateIndexes;
    private LocalVariableBinding primaryExceptionVariable;
    private ExceptionLabel[] resourceExceptionLabels;
    public LocalVariableBinding returnAddressVariable;
    private BranchLabel[] reusableJSRSequenceStartLabels;
    private int[] reusableJSRStateIndexes;
    private Object[] reusableJSRTargets;
    BlockScope scope;
    public LocalVariableBinding secretReturnValue;
    public UnconditionalFlowInfo subRoutineInits;
    BranchLabel subRoutineStartLabel;
    public Block tryBlock;
    static final char[] SECRET_RETURN_ADDRESS_NAME = " returnAddress".toCharArray();
    static final char[] SECRET_ANY_HANDLER_NAME = " anyExceptionHandler".toCharArray();
    static final char[] SECRET_PRIMARY_EXCEPTION_VARIABLE_NAME = " primaryException".toCharArray();
    static final char[] SECRET_CAUGHT_THROWABLE_VARIABLE_NAME = " caughtThrowable".toCharArray();
    static final char[] SECRET_RETURN_VALUE_NAME = " returnValue".toCharArray();
    private static LocalDeclaration[] NO_RESOURCES = new LocalDeclaration[0];
    public LocalDeclaration[] resources = NO_RESOURCES;
    private int reusableJSRTargetsCount = 0;
    int mergedInitStateIndex = -1;
    int preTryInitStateIndex = -1;
    int postTryInitStateIndex = -1;
    int naturalExitMergeInitStateIndex = -1;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        FlowInfo flowInfoAddPotentialInitializationsFrom;
        FlowInfo flowInfo2;
        int i;
        this.preTryInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfo);
        LocalVariableBinding localVariableBinding = this.anyExceptionVariable;
        if (localVariableBinding != null) {
            localVariableBinding.useFlag = 1;
        }
        LocalVariableBinding localVariableBinding2 = this.primaryExceptionVariable;
        if (localVariableBinding2 != null) {
            localVariableBinding2.useFlag = 1;
        }
        LocalVariableBinding localVariableBinding3 = this.caughtThrowableVariable;
        if (localVariableBinding3 != null) {
            localVariableBinding3.useFlag = 1;
        }
        LocalVariableBinding localVariableBinding4 = this.returnAddressVariable;
        if (localVariableBinding4 != null) {
            localVariableBinding4.useFlag = 1;
        }
        int length = this.resources.length;
        if (length > 0) {
            this.postResourcesInitStateIndexes = new int[length];
        }
        if (this.subRoutineStartLabel == null) {
            if (flowContext instanceof FinallyFlowContext) {
                FinallyFlowContext finallyFlowContext = (FinallyFlowContext) flowContext;
                finallyFlowContext.outerTryContext = finallyFlowContext.tryContext;
            }
            ExceptionHandlingFlowContext exceptionHandlingFlowContext = new ExceptionHandlingFlowContext(flowContext, this, this.caughtExceptionTypes, this.caughtExceptionsCatchBlocks, null, this.scope, flowInfo);
            exceptionHandlingFlowContext.conditionalLevel = 0;
            FlowInfo flowInfoCopy = flowInfo.copy();
            int i2 = 0;
            while (i2 < length) {
                LocalDeclaration localDeclaration = this.resources[i2];
                FlowInfo flowInfoAnalyseCode = localDeclaration.analyseCode(blockScope, exceptionHandlingFlowContext, flowInfoCopy);
                this.postResourcesInitStateIndexes[i2] = blockScope.methodScope().recordInitializationStates(flowInfoAnalyseCode);
                LocalVariableBinding localVariableBinding5 = localDeclaration.binding;
                localVariableBinding5.useFlag = 1;
                if (localVariableBinding5.closeTracker != null) {
                    localVariableBinding5.closeTracker.withdraw();
                    localVariableBinding5.closeTracker = null;
                }
                MethodBinding methodBindingFindCloseMethod = findCloseMethod(localDeclaration, localVariableBinding5);
                if (methodBindingFindCloseMethod != null && methodBindingFindCloseMethod.isValidBinding() && methodBindingFindCloseMethod.returnType.f626id == 6) {
                    ReferenceBinding[] referenceBindingArr = methodBindingFindCloseMethod.thrownExceptions;
                    int length2 = referenceBindingArr.length;
                    int i3 = 0;
                    while (i3 < length2) {
                        exceptionHandlingFlowContext.checkExceptionHandlers(referenceBindingArr[i3], this.resources[i2], flowInfoAnalyseCode, blockScope, true);
                        i3++;
                        length2 = length2;
                        referenceBindingArr = referenceBindingArr;
                    }
                }
                i2++;
                flowInfoCopy = flowInfoAnalyseCode;
            }
            if (!this.tryBlock.isEmptyBlock()) {
                flowInfoCopy = this.tryBlock.analyseCode(blockScope, exceptionHandlingFlowContext, flowInfoCopy);
                if ((flowInfoCopy.tagBits & 1) != 0) {
                    this.bits |= 536870912;
                }
            }
            if (length > 0) {
                this.postTryInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfoCopy);
                for (int i4 = 0; i4 < length; i4++) {
                    flowInfoCopy.resetAssignmentInfo(this.resources[i4].binding);
                }
            }
            exceptionHandlingFlowContext.complainIfUnusedExceptionHandlers(this.scope, this);
            if (this.catchArguments != null) {
                int length3 = this.catchBlocks.length;
                this.catchExits = new boolean[length3];
                this.catchExitInitStateIndexes = new int[length3];
                for (int i5 = 0; i5 < length3; i5++) {
                    FlowInfo flowInfoPrepareCatchInfo = prepareCatchInfo(flowInfo, exceptionHandlingFlowContext, flowInfoCopy, i5);
                    flowContext.conditionalLevel++;
                    FlowInfo flowInfoAnalyseCode2 = this.catchBlocks[i5].analyseCode(blockScope, flowContext, flowInfoPrepareCatchInfo);
                    flowContext.conditionalLevel--;
                    this.catchExitInitStateIndexes[i5] = blockScope.methodScope().recordInitializationStates(flowInfoAnalyseCode2);
                    this.catchExits[i5] = (flowInfoAnalyseCode2.tagBits & 1) != 0;
                    flowInfoCopy = flowInfoCopy.mergedWith(flowInfoAnalyseCode2.unconditionalInits());
                }
            }
            this.mergedInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfoCopy);
            flowContext.mergeFinallyNullInfo(exceptionHandlingFlowContext.initsOnFinally);
            return flowInfoCopy;
        }
        int i6 = 0;
        InsideSubRoutineFlowContext insideSubRoutineFlowContext = new InsideSubRoutineFlowContext(flowContext, this);
        if (flowContext instanceof FinallyFlowContext) {
            insideSubRoutineFlowContext.outerTryContext = ((FinallyFlowContext) flowContext).tryContext;
        }
        ExceptionHandlingFlowContext exceptionHandlingFlowContext2 = new ExceptionHandlingFlowContext(insideSubRoutineFlowContext, this, this.caughtExceptionTypes, this.caughtExceptionsCatchBlocks, null, this.scope, flowInfo);
        insideSubRoutineFlowContext.initsOnFinally = exceptionHandlingFlowContext2.initsOnFinally;
        Block block = this.finallyBlock;
        FinallyFlowContext finallyFlowContext2 = new FinallyFlowContext(flowContext, this.finallyBlock, exceptionHandlingFlowContext2);
        UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits = block.analyseCode(blockScope, finallyFlowContext2, flowInfo.nullInfoLessUnconditionalCopy()).unconditionalInits();
        exceptionHandlingFlowContext2.conditionalLevel = 0;
        if (unconditionalFlowInfoUnconditionalInits == FlowInfo.DEAD_END) {
            this.bits |= 16384;
            this.scope.problemReporter().finallyMustCompleteNormally(this.finallyBlock);
        } else {
            FlowInfo flowInfoCopy2 = unconditionalFlowInfoUnconditionalInits.copy();
            this.tryBlock.scope.finallyInfo = flowInfoCopy2;
            if (this.catchBlocks != null) {
                int i7 = 0;
                while (true) {
                    Block[] blockArr = this.catchBlocks;
                    if (i7 >= blockArr.length) {
                        break;
                    }
                    blockArr[i7].scope.finallyInfo = flowInfoCopy2;
                    i7++;
                }
            }
        }
        this.subRoutineInits = unconditionalFlowInfoUnconditionalInits;
        FlowInfo flowInfoCopy3 = flowInfo.copy();
        int i8 = 0;
        while (i8 < length) {
            LocalDeclaration localDeclaration2 = this.resources[i8];
            FlowInfo flowInfoAnalyseCode3 = localDeclaration2.analyseCode(blockScope, exceptionHandlingFlowContext2, flowInfoCopy3);
            this.postResourcesInitStateIndexes[i8] = blockScope.methodScope().recordInitializationStates(flowInfoAnalyseCode3);
            LocalVariableBinding localVariableBinding6 = localDeclaration2.binding;
            boolean z = true;
            localVariableBinding6.useFlag = 1;
            if (localVariableBinding6.closeTracker != null) {
                localVariableBinding6.closeTracker.withdraw();
            }
            MethodBinding methodBindingFindCloseMethod2 = findCloseMethod(localDeclaration2, localVariableBinding6);
            if (methodBindingFindCloseMethod2 == null || !methodBindingFindCloseMethod2.isValidBinding()) {
                flowInfo2 = flowInfoAnalyseCode3;
                i = i8;
            } else {
                char c = 6;
                if (methodBindingFindCloseMethod2.returnType.f626id == 6) {
                    ReferenceBinding[] referenceBindingArr2 = methodBindingFindCloseMethod2.thrownExceptions;
                    int length4 = referenceBindingArr2.length;
                    while (i6 < length4) {
                        exceptionHandlingFlowContext2.checkExceptionHandlers(referenceBindingArr2[i6], this.resources[i8], flowInfoAnalyseCode3, blockScope, true);
                        i6++;
                        c = 6;
                        z = z;
                        length4 = length4;
                        referenceBindingArr2 = referenceBindingArr2;
                        flowInfoAnalyseCode3 = flowInfoAnalyseCode3;
                        i8 = i8;
                    }
                }
                flowInfo2 = flowInfoAnalyseCode3;
                i = i8;
            }
            i8 = i + 1;
            flowInfoCopy3 = flowInfo2;
            i6 = 0;
        }
        if (!this.tryBlock.isEmptyBlock()) {
            flowInfoCopy3 = this.tryBlock.analyseCode(blockScope, exceptionHandlingFlowContext2, flowInfoCopy3);
            if ((flowInfoCopy3.tagBits & 1) != 0) {
                this.bits |= 536870912;
            }
        }
        if (length > 0) {
            this.postTryInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfoCopy3);
            for (int i9 = i6; i9 < length; i9++) {
                flowInfoCopy3.resetAssignmentInfo(this.resources[i9].binding);
            }
        }
        exceptionHandlingFlowContext2.complainIfUnusedExceptionHandlers(this.scope, this);
        if (this.catchArguments != null) {
            int length5 = this.catchBlocks.length;
            this.catchExits = new boolean[length5];
            this.catchExitInitStateIndexes = new int[length5];
            for (int i10 = i6; i10 < length5; i10++) {
                FlowInfo flowInfoPrepareCatchInfo2 = prepareCatchInfo(flowInfo, exceptionHandlingFlowContext2, flowInfoCopy3, i10);
                insideSubRoutineFlowContext.conditionalLevel = 1;
                FlowInfo flowInfoAnalyseCode4 = this.catchBlocks[i10].analyseCode(blockScope, insideSubRoutineFlowContext, flowInfoPrepareCatchInfo2);
                this.catchExitInitStateIndexes[i10] = blockScope.methodScope().recordInitializationStates(flowInfoAnalyseCode4);
                this.catchExits[i10] = (flowInfoAnalyseCode4.tagBits & 1) != 0 ? true : i6;
                flowInfoCopy3 = flowInfoCopy3.mergedWith(flowInfoAnalyseCode4.unconditionalInits());
            }
        }
        if ((flowInfoCopy3.tagBits & 3) == 0) {
            flowInfoAddPotentialInitializationsFrom = flowInfo.unconditionalCopy().addPotentialInitializationsFrom(flowInfoCopy3).addPotentialInitializationsFrom(insideSubRoutineFlowContext.initsOnReturn);
        } else {
            flowInfoAddPotentialInitializationsFrom = insideSubRoutineFlowContext.initsOnReturn;
        }
        finallyFlowContext2.complainOnDeferredChecks(flowInfoAddPotentialInitializationsFrom.addNullInfoFrom(exceptionHandlingFlowContext2.initsOnFinally), blockScope);
        flowContext.mergeFinallyNullInfo(exceptionHandlingFlowContext2.initsOnFinally);
        this.naturalExitMergeInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfoCopy3);
        if (unconditionalFlowInfoUnconditionalInits == FlowInfo.DEAD_END) {
            this.mergedInitStateIndex = blockScope.methodScope().recordInitializationStates(unconditionalFlowInfoUnconditionalInits);
            return unconditionalFlowInfoUnconditionalInits;
        }
        FlowInfo flowInfoAddInitializationsFrom = flowInfoCopy3.addInitializationsFrom(unconditionalFlowInfoUnconditionalInits);
        this.mergedInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfoAddInitializationsFrom);
        return flowInfoAddInitializationsFrom;
    }

    private MethodBinding findCloseMethod(LocalDeclaration localDeclaration, LocalVariableBinding localVariableBinding) {
        TypeBinding typeBinding = localVariableBinding.type;
        if (typeBinding == null || !typeBinding.isValidBinding()) {
            return null;
        }
        ReferenceBinding referenceBinding = (ReferenceBinding) typeBinding;
        MethodBinding exactMethod = referenceBinding.getExactMethod(ConstantPool.Close, new TypeBinding[0], this.scope.compilationUnitScope());
        if (exactMethod != null) {
            return exactMethod;
        }
        return this.scope.compilationUnitScope().findMethod(referenceBinding, ConstantPool.Close, new TypeBinding[0], new InvocationSite.EmptyWithAstNode(localDeclaration), false);
    }

    private FlowInfo prepareCatchInfo(FlowInfo flowInfo, ExceptionHandlingFlowContext exceptionHandlingFlowContext, FlowInfo flowInfo2, int i) {
        FlowInfo flowInfoAddPotentialInitializationsFrom;
        if (isUncheckedCatchBlock(i)) {
            flowInfoAddPotentialInitializationsFrom = flowInfo.unconditionalCopy().addPotentialInitializationsFrom(exceptionHandlingFlowContext.initsOnException(i)).addPotentialInitializationsFrom(flowInfo2).addPotentialInitializationsFrom(exceptionHandlingFlowContext.initsOnReturn).addNullInfoFrom(exceptionHandlingFlowContext.initsOnFinally);
        } else {
            UnconditionalFlowInfo unconditionalFlowInfoInitsOnException = exceptionHandlingFlowContext.initsOnException(i);
            flowInfoAddPotentialInitializationsFrom = flowInfo.nullInfoLessUnconditionalCopy().addPotentialInitializationsFrom(unconditionalFlowInfoInitsOnException).addNullInfoFrom(unconditionalFlowInfoInitsOnException).addPotentialInitializationsFrom(flowInfo2.nullInfoLessUnconditionalCopy()).addPotentialInitializationsFrom(exceptionHandlingFlowContext.initsOnReturn.nullInfoLessUnconditionalCopy());
        }
        LocalVariableBinding localVariableBinding = this.catchArguments[i].binding;
        flowInfoAddPotentialInitializationsFrom.markAsDefinitelyAssigned(localVariableBinding);
        flowInfoAddPotentialInitializationsFrom.markAsDefinitelyNonNull(localVariableBinding);
        if (this.tryBlock.statements == null && this.resources == NO_RESOURCES) {
            flowInfoAddPotentialInitializationsFrom.setReachMode(1);
        }
        return flowInfoAddPotentialInitializationsFrom;
    }

    private boolean isUncheckedCatchBlock(int i) {
        int[] iArr = this.caughtExceptionsCatchBlocks;
        if (iArr == null) {
            return this.caughtExceptionTypes[i].isUncheckedException(true);
        }
        int length = iArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (this.caughtExceptionsCatchBlocks[i2] == i && this.caughtExceptionTypes[i2].isUncheckedException(true)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.SubRoutineStatement
    public ExceptionLabel enterAnyExceptionHandler(CodeStream codeStream) {
        if (this.subRoutineStartLabel == null) {
            return null;
        }
        return super.enterAnyExceptionHandler(codeStream);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.SubRoutineStatement
    public void enterDeclaredExceptionHandlers(CodeStream codeStream) {
        ExceptionLabel[] exceptionLabelArr = this.declaredExceptionLabels;
        int length = exceptionLabelArr == null ? 0 : exceptionLabelArr.length;
        for (int i = 0; i < length; i++) {
            this.declaredExceptionLabels[i].placeStart();
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.SubRoutineStatement
    public void exitAnyExceptionHandler() {
        if (this.subRoutineStartLabel == null) {
            return;
        }
        super.exitAnyExceptionHandler();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.SubRoutineStatement
    public void exitDeclaredExceptionHandlers(CodeStream codeStream) {
        ExceptionLabel[] exceptionLabelArr = this.declaredExceptionLabels;
        int length = exceptionLabelArr == null ? 0 : exceptionLabelArr.length;
        for (int i = 0; i < length; i++) {
            this.declaredExceptionLabels[i].placeEnd();
        }
    }

    private int finallyMode() {
        if (this.subRoutineStartLabel == null) {
            return 0;
        }
        if (isSubRoutineEscaping()) {
            return 2;
        }
        return this.scope.compilerOptions().inlineJsrBytecode ? 3 : 1;
    }

    /* JADX WARN: Code duplicated, block: B:126:0x02df  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v6, types: [org.eclipse.jdt.internal.compiler.codegen.ExceptionLabel] */
    /* JADX WARN: Type inference failed for: r11v8, types: [org.eclipse.jdt.internal.compiler.codegen.ExceptionLabel] */
    /* JADX WARN: Type inference failed for: r12v26, types: [org.eclipse.jdt.internal.compiler.codegen.ExceptionLabel] */
    /* JADX WARN: Type inference failed for: r12v27, types: [org.eclipse.jdt.internal.compiler.codegen.ExceptionLabel] */
    /* JADX WARN: Type inference failed for: r12v28, types: [org.eclipse.jdt.internal.compiler.codegen.MultiCatchExceptionLabel] */
    /* JADX WARN: Type inference failed for: r15v10, types: [org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding] */
    /* JADX WARN: Type inference failed for: r15v14 */
    /* JADX WARN: Type inference failed for: r15v9 */
    /* JADX WARN: Type inference failed for: r17v0, types: [org.eclipse.jdt.internal.compiler.codegen.ExceptionLabel] */
    /* JADX WARN: Type inference failed for: r21v0, types: [org.eclipse.jdt.internal.compiler.codegen.CodeStream] */
    /* JADX WARN: Type inference failed for: r8v50, types: [org.eclipse.jdt.internal.compiler.codegen.ExceptionLabel] */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1, types: [org.eclipse.jdt.internal.compiler.codegen.ExceptionLabel[]] */
    /* JADX WARN: Type inference failed for: r9v23, types: [org.eclipse.jdt.internal.compiler.codegen.ExceptionLabel[]] */
    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) throws Throwable {
        ?? r9;
        ExceptionLabel[] exceptionLabelArr;
        boolean z;
        BranchLabel branchLabel;
        int i;
        int i2;
        ?? exceptionLabel;
        if ((this.bits & Integer.MIN_VALUE) == 0) {
            return;
        }
        boolean z2 = codeStream instanceof StackMapFrameCodeStream;
        ExceptionLabel[] exceptionLabelArr2 = null;
        this.anyExceptionLabel = null;
        this.reusableJSRTargets = null;
        this.reusableJSRSequenceStartLabels = null;
        this.reusableJSRTargetsCount = 0;
        int i3 = codeStream.position;
        int iFinallyMode = finallyMode();
        Argument[] argumentArr = this.catchArguments;
        int length = argumentArr == null ? 0 : argumentArr.length;
        if (length > 0) {
            r9 = new ExceptionLabel[length];
            for (int i4 = 0; i4 < length; i4++) {
                Argument argument = this.catchArguments[i4];
                if ((argument.binding.tagBits & 4096) != 0) {
                    exceptionLabel = new MultiCatchExceptionLabel(codeStream, argument.binding.type);
                    exceptionLabel.initialize((UnionTypeReference) argument.type, argument.annotations);
                } else {
                    exceptionLabel = new ExceptionLabel(codeStream, argument.binding.type, argument.type, argument.annotations);
                }
                exceptionLabel.placeStart();
                r9[i4] = exceptionLabel;
            }
        } else {
            r9 = 0;
        }
        BranchLabel branchLabel2 = this.subRoutineStartLabel;
        if (branchLabel2 != null) {
            branchLabel2.initialize(codeStream);
            enterAnyExceptionHandler(codeStream);
        }
        try {
            this.declaredExceptionLabels = r9;
            int length2 = this.resources.length;
            if (length2 > 0) {
                try {
                    this.resourceExceptionLabels = new ExceptionLabel[length2 + 1];
                    codeStream.aconst_null();
                    codeStream.store(this.primaryExceptionVariable, false);
                    codeStream.addVariable(this.primaryExceptionVariable);
                    codeStream.aconst_null();
                    codeStream.store(this.caughtThrowableVariable, false);
                    codeStream.addVariable(this.caughtThrowableVariable);
                    for (int i5 = 0; i5 <= length2; i5++) {
                        this.resourceExceptionLabels[i5] = new ExceptionLabel(codeStream, null);
                        this.resourceExceptionLabels[i5].placeStart();
                        if (i5 < length2) {
                            this.resources[i5].generateCode(this.scope, codeStream);
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    exceptionLabelArr = exceptionLabelArr2;
                    this.declaredExceptionLabels = exceptionLabelArr;
                    this.resourceExceptionLabels = exceptionLabelArr;
                    throw th;
                }
            }
            this.tryBlock.generateCode(this.scope, codeStream);
            int i6 = 536870912;
            int i7 = -1;
            if (length2 > 0) {
                int i8 = length2;
                while (i8 >= 0) {
                    BranchLabel branchLabel3 = new BranchLabel(codeStream);
                    this.resourceExceptionLabels[i8].placeEnd();
                    ?? r15 = i8 > 0 ? this.resources[i8 - 1].binding : exceptionLabelArr2;
                    if ((this.bits & i6) == 0) {
                        if (i8 > 0) {
                            int i9 = codeStream.position;
                            int i10 = this.postTryInitStateIndex;
                            if (i10 != i7) {
                                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i10);
                                codeStream.addDefinitelyAssignedVariables(blockScope, this.postTryInitStateIndex);
                            }
                            codeStream.load(r15);
                            codeStream.ifnull(branchLabel3);
                            codeStream.load(r15);
                            codeStream.invokeAutoCloseableClose(r15.type);
                            codeStream.recordPositionsFrom(i9, this.tryBlock.sourceEnd);
                        }
                        codeStream.goto_(branchLabel3);
                    }
                    if (i8 > 0) {
                        int i11 = i8 - 1;
                        codeStream.removeNotDefinitelyAssignedVariables(blockScope, this.postResourcesInitStateIndexes[i11]);
                        codeStream.addDefinitelyAssignedVariables(blockScope, this.postResourcesInitStateIndexes[i11]);
                    } else {
                        codeStream.removeNotDefinitelyAssignedVariables(blockScope, this.preTryInitStateIndex);
                        codeStream.addDefinitelyAssignedVariables(blockScope, this.preTryInitStateIndex);
                    }
                    codeStream.pushExceptionOnStack(this.scope.getJavaLangThrowable());
                    this.resourceExceptionLabels[i8].place();
                    if (i8 == length2) {
                        codeStream.store(this.primaryExceptionVariable, false);
                    } else {
                        BranchLabel branchLabel4 = new BranchLabel(codeStream);
                        BranchLabel branchLabel5 = new BranchLabel(codeStream);
                        codeStream.store(this.caughtThrowableVariable, false);
                        codeStream.load(this.primaryExceptionVariable);
                        codeStream.ifnonnull(branchLabel4);
                        codeStream.load(this.caughtThrowableVariable);
                        codeStream.store(this.primaryExceptionVariable, false);
                        codeStream.goto_(branchLabel5);
                        branchLabel4.place();
                        codeStream.load(this.primaryExceptionVariable);
                        codeStream.load(this.caughtThrowableVariable);
                        codeStream.if_acmpeq(branchLabel5);
                        codeStream.load(this.primaryExceptionVariable);
                        codeStream.load(this.caughtThrowableVariable);
                        codeStream.invokeThrowableAddSuppressed();
                        branchLabel5.place();
                    }
                    if (i8 > 0) {
                        BranchLabel branchLabel6 = new BranchLabel(codeStream);
                        int i12 = codeStream.position;
                        codeStream.load(r15);
                        codeStream.ifnull(branchLabel6);
                        codeStream.load(r15);
                        codeStream.invokeAutoCloseableClose(r15.type);
                        codeStream.recordPositionsFrom(i12, this.tryBlock.sourceEnd);
                        codeStream.removeVariable(r15);
                        branchLabel6.place();
                    }
                    codeStream.load(this.primaryExceptionVariable);
                    codeStream.athrow();
                    branchLabel3.place();
                    i8--;
                    exceptionLabelArr2 = null;
                    i6 = 536870912;
                    i7 = -1;
                }
                codeStream.removeVariable(this.primaryExceptionVariable);
                codeStream.removeVariable(this.caughtThrowableVariable);
            }
            this.declaredExceptionLabels = exceptionLabelArr2;
            this.resourceExceptionLabels = exceptionLabelArr2;
            if (codeStream.position != i3) {
                BranchLabel branchLabel7 = new BranchLabel(codeStream);
                int i13 = 0;
                while (i13 < length) {
                    r9[i13].placeEnd();
                    i13++;
                    length = length;
                }
                if ((this.bits & 536870912) == 0) {
                    int i14 = codeStream.position;
                    if (iFinallyMode != 0) {
                        if (iFinallyMode != 1) {
                            if (iFinallyMode == 2) {
                                codeStream.goto_(this.subRoutineStartLabel);
                            } else {
                                if (iFinallyMode != 3) {
                                }
                                codeStream.recordPositionsFrom(i14, this.tryBlock.sourceEnd);
                            }
                        }
                        int i15 = this.naturalExitMergeInitStateIndex;
                        if (i15 != -1) {
                            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i15);
                            codeStream.addDefinitelyAssignedVariables(blockScope, this.naturalExitMergeInitStateIndex);
                        }
                        codeStream.goto_(branchLabel7);
                        z = true;
                        codeStream.recordPositionsFrom(i14, this.tryBlock.sourceEnd);
                    } else {
                        int i16 = this.naturalExitMergeInitStateIndex;
                        if (i16 != -1) {
                            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i16);
                            codeStream.addDefinitelyAssignedVariables(blockScope, this.naturalExitMergeInitStateIndex);
                        }
                        codeStream.goto_(branchLabel7);
                    }
                    z = false;
                    codeStream.recordPositionsFrom(i14, this.tryBlock.sourceEnd);
                } else {
                    z = false;
                }
                exitAnyExceptionHandler();
                if (this.catchArguments != null) {
                    branchLabel = new BranchLabel(codeStream);
                    int i17 = 0;
                    while (i17 < length) {
                        if (r9[i17].getCount() == 0) {
                            i = length;
                        } else {
                            enterAnyExceptionHandler(codeStream);
                            int i18 = this.preTryInitStateIndex;
                            if (i18 != -1) {
                                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i18);
                                codeStream.addDefinitelyAssignedVariables(blockScope, this.preTryInitStateIndex);
                            }
                            codeStream.pushExceptionOnStack(r9[i17].exceptionType);
                            r9[i17].place();
                            int i19 = codeStream.position;
                            LocalVariableBinding localVariableBinding = this.catchArguments[i17].binding;
                            i = length;
                            if (localVariableBinding.resolvedPosition != -1) {
                                codeStream.store(localVariableBinding, false);
                                localVariableBinding.recordInitializationStartPC(codeStream.position);
                                codeStream.addVisibleLocalVariable(localVariableBinding);
                            } else {
                                codeStream.pop();
                            }
                            codeStream.recordPositionsFrom(i19, this.catchArguments[i17].sourceStart);
                            this.catchBlocks[i17].generateCode(this.scope, codeStream);
                            exitAnyExceptionHandler();
                            if (!this.catchExits[i17]) {
                                if (iFinallyMode == 0) {
                                    i2 = this.naturalExitMergeInitStateIndex;
                                    if (i2 != -1) {
                                        codeStream.removeNotDefinitelyAssignedVariables(blockScope, i2);
                                        codeStream.addDefinitelyAssignedVariables(blockScope, this.naturalExitMergeInitStateIndex);
                                    }
                                    codeStream.goto_(branchLabel7);
                                } else if (iFinallyMode == 1) {
                                    z = true;
                                    i2 = this.naturalExitMergeInitStateIndex;
                                    if (i2 != -1) {
                                        codeStream.removeNotDefinitelyAssignedVariables(blockScope, i2);
                                        codeStream.addDefinitelyAssignedVariables(blockScope, this.naturalExitMergeInitStateIndex);
                                    }
                                    codeStream.goto_(branchLabel7);
                                } else if (iFinallyMode == 2) {
                                    codeStream.goto_(this.subRoutineStartLabel);
                                } else if (iFinallyMode == 3) {
                                    if (z2) {
                                        ((StackMapFrameCodeStream) codeStream).pushStateIndex(this.naturalExitMergeInitStateIndex);
                                    }
                                    int i20 = this.catchExitInitStateIndexes[i17];
                                    if (i20 != -1) {
                                        codeStream.removeNotDefinitelyAssignedVariables(blockScope, i20);
                                        codeStream.addDefinitelyAssignedVariables(blockScope, this.catchExitInitStateIndexes[i17]);
                                    }
                                    this.finallyBlock.generateCode(this.scope, codeStream);
                                    codeStream.goto_(branchLabel);
                                    if (z2) {
                                        ((StackMapFrameCodeStream) codeStream).popStateIndex();
                                    }
                                }
                            }
                        }
                        i17++;
                        length = i;
                    }
                } else {
                    branchLabel = null;
                }
                ExceptionLabel exceptionLabel2 = (z && iFinallyMode == 1) ? new ExceptionLabel(codeStream, null) : null;
                int i21 = codeStream.position;
                if (this.subRoutineStartLabel != null && this.anyExceptionLabel.getCount() != 0) {
                    codeStream.pushExceptionOnStack(this.scope.getJavaLangThrowable());
                    int i22 = this.preTryInitStateIndex;
                    if (i22 != -1) {
                        codeStream.removeNotDefinitelyAssignedVariables(blockScope, i22);
                        codeStream.addDefinitelyAssignedVariables(blockScope, this.preTryInitStateIndex);
                    }
                    placeAllAnyExceptionHandler();
                    if (exceptionLabel2 != null) {
                        exceptionLabel2.place();
                    }
                    if (iFinallyMode == 1) {
                        codeStream.store(this.anyExceptionVariable, false);
                        codeStream.jsr(this.subRoutineStartLabel);
                        codeStream.recordPositionsFrom(i21, this.finallyBlock.sourceStart);
                        int i23 = codeStream.position;
                        codeStream.throwAnyException(this.anyExceptionVariable);
                        codeStream.recordPositionsFrom(i23, this.finallyBlock.sourceEnd);
                        this.subRoutineStartLabel.place();
                        codeStream.pushExceptionOnStack(this.scope.getJavaLangThrowable());
                        int i24 = codeStream.position;
                        codeStream.store(this.returnAddressVariable, false);
                        codeStream.recordPositionsFrom(i24, this.finallyBlock.sourceStart);
                        this.finallyBlock.generateCode(this.scope, codeStream);
                        int i25 = codeStream.position;
                        codeStream.ret(this.returnAddressVariable.resolvedPosition);
                        codeStream.recordPositionsFrom(i25, this.finallyBlock.sourceEnd);
                    } else if (iFinallyMode == 2) {
                        codeStream.pop();
                        this.subRoutineStartLabel.place();
                        codeStream.recordPositionsFrom(i21, this.finallyBlock.sourceStart);
                        this.finallyBlock.generateCode(this.scope, codeStream);
                    } else if (iFinallyMode == 3) {
                        codeStream.store(this.anyExceptionVariable, false);
                        codeStream.addVariable(this.anyExceptionVariable);
                        codeStream.recordPositionsFrom(i21, this.finallyBlock.sourceStart);
                        this.finallyBlock.generateCode(blockScope, codeStream);
                        int i26 = codeStream.position;
                        codeStream.throwAnyException(this.anyExceptionVariable);
                        codeStream.removeVariable(this.anyExceptionVariable);
                        int i27 = this.preTryInitStateIndex;
                        if (i27 != -1) {
                            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i27);
                        }
                        this.subRoutineStartLabel.place();
                        codeStream.recordPositionsFrom(i26, this.finallyBlock.sourceEnd);
                    }
                    if (z) {
                        if (iFinallyMode == 1) {
                            branchLabel7.place();
                            int i28 = codeStream.position;
                            exceptionLabel2.placeStart();
                            codeStream.jsr(this.subRoutineStartLabel);
                            exceptionLabel2.placeEnd();
                            codeStream.recordPositionsFrom(i28, this.finallyBlock.sourceEnd);
                        } else if (iFinallyMode != 2) {
                            if (iFinallyMode == 3) {
                                if (z2) {
                                    ((StackMapFrameCodeStream) codeStream).pushStateIndex(this.naturalExitMergeInitStateIndex);
                                }
                                int i29 = this.naturalExitMergeInitStateIndex;
                                if (i29 != -1) {
                                    codeStream.removeNotDefinitelyAssignedVariables(blockScope, i29);
                                    codeStream.addDefinitelyAssignedVariables(blockScope, this.naturalExitMergeInitStateIndex);
                                }
                                branchLabel7.place();
                                this.finallyBlock.generateCode(this.scope, codeStream);
                                if (branchLabel != null) {
                                    int i30 = codeStream.position;
                                    codeStream.goto_(branchLabel);
                                    codeStream.recordPositionsFrom(i30, this.finallyBlock.sourceEnd);
                                }
                                if (z2) {
                                    ((StackMapFrameCodeStream) codeStream).popStateIndex();
                                }
                            } else {
                                branchLabel7.place();
                            }
                        }
                    }
                    if (branchLabel != null) {
                        branchLabel.place();
                    }
                } else {
                    branchLabel7.place();
                }
            } else if (this.subRoutineStartLabel != null) {
                this.finallyBlock.generateCode(this.scope, codeStream);
            }
            int i31 = this.mergedInitStateIndex;
            if (i31 != -1) {
                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i31);
                codeStream.addDefinitelyAssignedVariables(blockScope, this.mergedInitStateIndex);
            }
            codeStream.recordPositionsFrom(i3, this.sourceStart);
        } catch (Throwable th2) {
            th = th2;
            exceptionLabelArr = null;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.SubRoutineStatement
    public boolean generateSubRoutineInvocation(BlockScope blockScope, CodeStream codeStream, Object obj, int i, LocalVariableBinding localVariableBinding) {
        int length = this.resources.length;
        if (length > 0 && this.resourceExceptionLabels != null) {
            for (int i2 = length; i2 > 0; i2--) {
                this.resourceExceptionLabels[i2].placeEnd();
                LocalVariableBinding localVariableBinding2 = this.resources[i2 - 1].binding;
                BranchLabel branchLabel = new BranchLabel(codeStream);
                int i3 = codeStream.position;
                codeStream.load(localVariableBinding2);
                codeStream.ifnull(branchLabel);
                codeStream.load(localVariableBinding2);
                codeStream.invokeAutoCloseableClose(localVariableBinding2.type);
                codeStream.recordPositionsFrom(i3, this.tryBlock.sourceEnd);
                branchLabel.place();
            }
            while (length > 0) {
                this.resourceExceptionLabels[length].placeStart();
                length--;
            }
        }
        boolean z = codeStream instanceof StackMapFrameCodeStream;
        int iFinallyMode = finallyMode();
        if (iFinallyMode == 0) {
            exitDeclaredExceptionHandlers(codeStream);
            return false;
        }
        if (iFinallyMode == 2) {
            codeStream.goto_(this.subRoutineStartLabel);
            return true;
        }
        if (this.scope.compilerOptions().shareCommonFinallyBlocks && obj != null) {
            int i4 = this.reusableJSRTargetsCount;
            if (i4 > 0) {
                int i5 = 0;
                while (true) {
                    if (i5 < i4) {
                        Object obj2 = this.reusableJSRTargets[i5];
                        if (obj != obj2 && (!(obj instanceof Constant) || !(obj2 instanceof Constant) || !((Constant) obj).hasSameValue((Constant) obj2))) {
                            i5++;
                        } else if (this.reusableJSRStateIndexes[i5] == i || iFinallyMode != 3) {
                            codeStream.goto_(this.reusableJSRSequenceStartLabels[i5]);
                            return true;
                        }
                    }
                }
            } else {
                this.reusableJSRTargets = new Object[3];
                this.reusableJSRSequenceStartLabels = new BranchLabel[3];
                this.reusableJSRStateIndexes = new int[3];
            }
            int i6 = this.reusableJSRTargetsCount;
            Object[] objArr = this.reusableJSRTargets;
            if (i6 == objArr.length) {
                Object[] objArr2 = new Object[i6 * 2];
                this.reusableJSRTargets = objArr2;
                System.arraycopy(objArr, 0, objArr2, 0, i6);
                BranchLabel[] branchLabelArr = this.reusableJSRSequenceStartLabels;
                int i7 = this.reusableJSRTargetsCount;
                BranchLabel[] branchLabelArr2 = new BranchLabel[i7 * 2];
                this.reusableJSRSequenceStartLabels = branchLabelArr2;
                System.arraycopy(branchLabelArr, 0, branchLabelArr2, 0, i7);
                int[] iArr = this.reusableJSRStateIndexes;
                int i8 = this.reusableJSRTargetsCount;
                int[] iArr2 = new int[i8 * 2];
                this.reusableJSRStateIndexes = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, i8);
            }
            this.reusableJSRTargets[this.reusableJSRTargetsCount] = obj;
            BranchLabel branchLabel2 = new BranchLabel(codeStream);
            branchLabel2.place();
            int[] iArr3 = this.reusableJSRStateIndexes;
            int i9 = this.reusableJSRTargetsCount;
            iArr3[i9] = i;
            BranchLabel[] branchLabelArr3 = this.reusableJSRSequenceStartLabels;
            this.reusableJSRTargetsCount = i9 + 1;
            branchLabelArr3[i9] = branchLabel2;
        }
        if (iFinallyMode == 3) {
            if (z) {
                ((StackMapFrameCodeStream) codeStream).pushStateIndex(i);
            }
            exitAnyExceptionHandler();
            exitDeclaredExceptionHandlers(codeStream);
            this.finallyBlock.generateCode(blockScope, codeStream);
            if (z) {
                ((StackMapFrameCodeStream) codeStream).popStateIndex();
            }
        } else {
            codeStream.jsr(this.subRoutineStartLabel);
            exitAnyExceptionHandler();
            exitDeclaredExceptionHandlers(codeStream);
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.SubRoutineStatement
    public boolean isSubRoutineEscaping() {
        return (this.bits & 16384) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        int length = this.resources.length;
        printIndent(i, stringBuffer).append(PsiKeyword.TRY.concat(length == 0 ? "\n" : " ("));
        for (int i2 = 0; i2 < length; i2++) {
            this.resources[i2].printAsExpression(0, stringBuffer);
            if (i2 != length - 1) {
                stringBuffer.append(";\n");
                printIndent(i + 2, stringBuffer);
            }
        }
        if (length > 0) {
            stringBuffer.append(")\n");
        }
        int i3 = i + 1;
        this.tryBlock.printStatement(i3, stringBuffer);
        if (this.catchBlocks != null) {
            for (int i4 = 0; i4 < this.catchBlocks.length; i4++) {
                stringBuffer.append('\n');
                printIndent(i, stringBuffer).append("catch (");
                this.catchArguments[i4].print(0, stringBuffer).append(")\n");
                this.catchBlocks[i4].printStatement(i3, stringBuffer);
            }
        }
        if (this.finallyBlock != null) {
            stringBuffer.append('\n');
            printIndent(i, stringBuffer).append("finally\n");
            this.finallyBlock.printStatement(i3, stringBuffer);
        }
        return stringBuffer;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
        BlockScope blockScope2;
        this.scope = new BlockScope(blockScope);
        int length = this.resources.length;
        BlockScope blockScope3 = null;
        blockScope3 = null;
        blockScope3 = null;
        MethodBinding methodBinding = null;
        if (length > 0) {
            blockScope2 = new BlockScope(this.scope);
            LocalVariableBinding localVariableBinding = new LocalVariableBinding(SECRET_PRIMARY_EXCEPTION_VARIABLE_NAME, (TypeBinding) this.scope.getJavaLangThrowable(), 0, false);
            this.primaryExceptionVariable = localVariableBinding;
            blockScope2.addLocalVariable(localVariableBinding);
            this.primaryExceptionVariable.setConstant(Constant.NotAConstant);
            LocalVariableBinding localVariableBinding2 = new LocalVariableBinding(SECRET_CAUGHT_THROWABLE_VARIABLE_NAME, (TypeBinding) this.scope.getJavaLangThrowable(), 0, false);
            this.caughtThrowableVariable = localVariableBinding2;
            blockScope2.addLocalVariable(localVariableBinding2);
            this.caughtThrowableVariable.setConstant(Constant.NotAConstant);
        } else {
            blockScope2 = null;
        }
        for (int i = 0; i < length; i++) {
            this.resources[i].resolve(blockScope2);
            LocalVariableBinding localVariableBinding3 = this.resources[i].binding;
            if (localVariableBinding3 != null && localVariableBinding3.isValidBinding()) {
                localVariableBinding3.modifiers |= 16;
                localVariableBinding3.tagBits |= 8192;
                TypeBinding typeBinding = localVariableBinding3.type;
                if (typeBinding instanceof ReferenceBinding) {
                    if (typeBinding.findSuperTypeOriginatingFrom(62, false) == null && typeBinding.isValidBinding()) {
                        blockScope.problemReporter().resourceHasToImplementAutoCloseable(typeBinding, this.resources[i].type);
                        localVariableBinding3.type = new ProblemReferenceBinding(CharOperation.splitOn('.', typeBinding.shortReadableName()), null, 15);
                    }
                } else if (typeBinding != null) {
                    blockScope.problemReporter().resourceHasToImplementAutoCloseable(typeBinding, this.resources[i].type);
                    localVariableBinding3.type = new ProblemReferenceBinding(CharOperation.splitOn('.', typeBinding.shortReadableName()), null, 15);
                }
            }
        }
        if (blockScope2 == null) {
            blockScope2 = this.scope;
        }
        BlockScope blockScope4 = new BlockScope(blockScope2);
        Block block = this.finallyBlock;
        if (block != null) {
            if (block.isEmptyBlock()) {
                if ((this.finallyBlock.bits & 8) != 0) {
                    this.scope.problemReporter().undocumentedEmptyBlock(this.finallyBlock.sourceStart, this.finallyBlock.sourceEnd);
                }
            } else {
                BlockScope blockScope5 = new BlockScope(this.scope, false);
                MethodScope methodScope = this.scope.methodScope();
                if (!blockScope.compilerOptions().inlineJsrBytecode) {
                    LocalVariableBinding localVariableBinding4 = new LocalVariableBinding(SECRET_RETURN_ADDRESS_NAME, (TypeBinding) blockScope.getJavaLangObject(), 0, false);
                    this.returnAddressVariable = localVariableBinding4;
                    blockScope5.addLocalVariable(localVariableBinding4);
                    this.returnAddressVariable.setConstant(Constant.NotAConstant);
                }
                this.subRoutineStartLabel = new BranchLabel();
                LocalVariableBinding localVariableBinding5 = new LocalVariableBinding(SECRET_ANY_HANDLER_NAME, (TypeBinding) this.scope.getJavaLangThrowable(), 0, false);
                this.anyExceptionVariable = localVariableBinding5;
                blockScope5.addLocalVariable(localVariableBinding5);
                this.anyExceptionVariable.setConstant(Constant.NotAConstant);
                if (!methodScope.isInsideInitializer()) {
                    if (methodScope.referenceContext instanceof AbstractMethodDeclaration) {
                        methodBinding = ((AbstractMethodDeclaration) methodScope.referenceContext).binding;
                    } else if (methodScope.referenceContext instanceof LambdaExpression) {
                        methodBinding = ((LambdaExpression) methodScope.referenceContext).binding;
                    }
                    if (methodBinding != null) {
                        TypeBinding typeBinding2 = methodBinding.returnType;
                        if (typeBinding2.f626id != 6) {
                            LocalVariableBinding localVariableBinding6 = new LocalVariableBinding(SECRET_RETURN_VALUE_NAME, typeBinding2, 0, false);
                            this.secretReturnValue = localVariableBinding6;
                            blockScope5.addLocalVariable(localVariableBinding6);
                            this.secretReturnValue.setConstant(Constant.NotAConstant);
                        }
                    }
                }
                this.finallyBlock.resolveUsing(blockScope5);
                Argument[] argumentArr = this.catchArguments;
                blockScope5.shiftScopes = new BlockScope[argumentArr == null ? 1 : argumentArr.length + 1];
                blockScope5.shiftScopes[0] = blockScope4;
                blockScope3 = blockScope5;
            }
        }
        this.tryBlock.resolveUsing(blockScope4);
        if (this.catchBlocks != null) {
            int length2 = this.catchArguments.length;
            TypeBinding[] typeBindingArr = new TypeBinding[length2];
            int i2 = 0;
            boolean z = false;
            boolean z2 = false;
            while (i2 < length2) {
                BlockScope blockScope6 = new BlockScope(this.scope);
                if (blockScope3 != null) {
                    blockScope3.shiftScopes[i2 + 1] = blockScope6;
                }
                Argument argument = this.catchArguments[i2];
                z2 |= (argument.type.bits & 536870912) != 0;
                TypeBinding typeBindingResolveForCatch = argument.resolveForCatch(blockScope6);
                typeBindingArr[i2] = typeBindingResolveForCatch;
                if (typeBindingResolveForCatch == null) {
                    z = true;
                }
                this.catchBlocks[i2].resolveUsing(blockScope6);
                i2++;
                z = z;
            }
            if (z) {
                return;
            } else {
                verifyDuplicationAndOrder(length2, typeBindingArr, z2);
            }
        } else {
            this.caughtExceptionTypes = new ReferenceBinding[0];
        }
        if (blockScope3 != null) {
            this.scope.addSubscope(blockScope3);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            for (LocalDeclaration localDeclaration : this.resources) {
                localDeclaration.traverse(aSTVisitor, this.scope);
            }
            this.tryBlock.traverse(aSTVisitor, this.scope);
            if (this.catchArguments != null) {
                int length = this.catchBlocks.length;
                for (int i = 0; i < length; i++) {
                    this.catchArguments[i].traverse(aSTVisitor, this.scope);
                    this.catchBlocks[i].traverse(aSTVisitor, this.scope);
                }
            }
            Block block = this.finallyBlock;
            if (block != null) {
                block.traverse(aSTVisitor, this.scope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    protected void verifyDuplicationAndOrder(int i, TypeBinding[] typeBindingArr, boolean z) {
        if (z) {
            TypeBinding[][] typeBindingArr2 = new ReferenceBinding[i][];
            int i2 = 0;
            for (int i3 = 0; i3 < i; i3++) {
                TypeBinding typeBinding = typeBindingArr[i3];
                if (!(typeBinding instanceof ArrayBinding)) {
                    ReferenceBinding referenceBinding = (ReferenceBinding) typeBinding;
                    TypeReference typeReference = this.catchArguments[i3].type;
                    if ((536870912 & typeReference.bits) != 0) {
                        TypeReference[] typeReferenceArr = ((UnionTypeReference) typeReference).typeReferences;
                        int length = typeReferenceArr.length;
                        ReferenceBinding[] referenceBindingArr = new ReferenceBinding[length];
                        for (int i4 = 0; i4 < length; i4++) {
                            referenceBindingArr[i4] = (ReferenceBinding) typeReferenceArr[i4].resolvedType;
                        }
                        i2 += length;
                        typeBindingArr2[i3] = referenceBindingArr;
                    } else {
                        typeBindingArr2[i3] = new ReferenceBinding[]{referenceBinding};
                        i2++;
                    }
                }
            }
            this.caughtExceptionTypes = new ReferenceBinding[i2];
            this.caughtExceptionsCatchBlocks = new int[i2];
            int i5 = 0;
            for (int i6 = 0; i6 < i; i6++) {
                ReferenceBinding[] referenceBindingArr2 = typeBindingArr2[i6];
                if (referenceBindingArr2 != null) {
                    int length2 = referenceBindingArr2.length;
                    int i7 = 0;
                    while (i7 < length2) {
                        ReferenceBinding referenceBinding2 = referenceBindingArr2[i7];
                        this.caughtExceptionTypes[i5] = referenceBinding2;
                        int i8 = i5 + 1;
                        this.caughtExceptionsCatchBlocks[i5] = i6;
                        for (int i9 = 0; i9 < i6; i9++) {
                            TypeBinding[] typeBindingArr3 = typeBindingArr2[i9];
                            if (typeBindingArr3 != null) {
                                for (TypeBinding typeBinding2 : typeBindingArr3) {
                                    if (referenceBinding2.isCompatibleWith(typeBinding2)) {
                                        TypeReference typeReference2 = this.catchArguments[i6].type;
                                        if ((typeReference2.bits & 536870912) != 0) {
                                            typeReference2 = ((UnionTypeReference) typeReference2).typeReferences[i7];
                                        }
                                        this.scope.problemReporter().wrongSequenceOfExceptionTypesError(typeReference2, referenceBinding2, typeBinding2);
                                        i5 = i8;
                                        break;
                                    }
                                }
                            }
                        }
                        i7++;
                        i5 = i8;
                    }
                }
            }
            return;
        }
        this.caughtExceptionTypes = new ReferenceBinding[i];
        for (int i10 = 0; i10 < i; i10++) {
            TypeBinding typeBinding3 = typeBindingArr[i10];
            if (!(typeBinding3 instanceof ArrayBinding)) {
                this.caughtExceptionTypes[i10] = (ReferenceBinding) typeBinding3;
                for (int i11 = 0; i11 < i10; i11++) {
                    if (this.caughtExceptionTypes[i10].isCompatibleWith(typeBindingArr[i11])) {
                        this.scope.problemReporter().wrongSequenceOfExceptionTypesError(this.catchArguments[i10].type, this.caughtExceptionTypes[i10], typeBindingArr[i11]);
                    }
                }
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean doesNotCompleteNormally() {
        if (!this.tryBlock.doesNotCompleteNormally()) {
            Block block = this.finallyBlock;
            if (block != null) {
                return block.doesNotCompleteNormally();
            }
            return false;
        }
        if (this.catchBlocks == null) {
            return true;
        }
        int i = 0;
        while (true) {
            Block[] blockArr = this.catchBlocks;
            if (i >= blockArr.length) {
                return true;
            }
            if (!blockArr[i].doesNotCompleteNormally()) {
                Block block2 = this.finallyBlock;
                if (block2 != null) {
                    return block2.doesNotCompleteNormally();
                }
                return false;
            }
            i++;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean completesByContinue() {
        if (this.tryBlock.completesByContinue()) {
            Block block = this.finallyBlock;
            return block == null || !block.doesNotCompleteNormally() || this.finallyBlock.completesByContinue();
        }
        if (this.catchBlocks != null) {
            int i = 0;
            while (true) {
                Block[] blockArr = this.catchBlocks;
                if (i >= blockArr.length) {
                    break;
                }
                if (blockArr[i].completesByContinue()) {
                    Block block2 = this.finallyBlock;
                    return block2 == null || !block2.doesNotCompleteNormally() || this.finallyBlock.completesByContinue();
                }
                i++;
            }
        }
        Block block3 = this.finallyBlock;
        return block3 != null && block3.completesByContinue();
    }
}
