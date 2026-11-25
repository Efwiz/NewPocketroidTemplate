package org.eclipse.jdt.internal.compiler.flow;

import java.util.ArrayList;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.FakedTrackingVariable;
import org.eclipse.jdt.internal.compiler.ast.LabeledStatement;
import org.eclipse.jdt.internal.compiler.ast.LambdaExpression;
import org.eclipse.jdt.internal.compiler.ast.NullAnnotationMatching;
import org.eclipse.jdt.internal.compiler.ast.Reference;
import org.eclipse.jdt.internal.compiler.ast.SingleNameReference;
import org.eclipse.jdt.internal.compiler.ast.SubRoutineStatement;
import org.eclipse.jdt.internal.compiler.ast.ThrowStatement;
import org.eclipse.jdt.internal.compiler.ast.TryStatement;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.CatchParameterBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.lookup.VariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class FlowContext implements TypeConstants {
    public static final int ASSIGN_TO_NONNULL = 128;
    public static final int CAN_ONLY_NON_NULL = 2;
    public static final int CAN_ONLY_NULL = 1;
    public static final int CAN_ONLY_NULL_NON_NULL = 0;
    public static final int CHECK_MASK = 255;
    public static final int CONTEXT_MASK = -61696;
    public static final int DEFER_NULL_DIAGNOSTIC = 1;
    public static final int EXIT_RESOURCE = 2048;
    public static final int HIDE_NULL_COMPARISON_WARNING = 4096;
    public static final int HIDE_NULL_COMPARISON_WARNING_MASK = 61440;
    public static final int INSIDE_NEGATION = 4;
    public static final int IN_ASSIGNMENT = 768;
    public static final int IN_COMPARISON_NON_NULL = 512;
    public static final int IN_COMPARISON_NULL = 256;
    public static final int IN_INSTANCEOF = 1024;
    public static final int IN_UNBOXING = 16;
    public static final int MAY_NULL = 3;
    public static final FlowContext NotContinuableContext = new FlowContext(null, null);
    public static final int PREEMPT_NULL_DIAGNOSTIC = 2;
    public ASTNode associatedNode;
    public int conditionalLevel;
    public FlowInfo initsOnFinally;
    private Reference[] nullCheckedFieldReferences;
    public FlowContext parent;
    public TypeBinding[][] providedExpectedTypes = null;
    public int tagBits;
    private int[] timesToLiveForNullCheckInfo;

    public BranchLabel breakLabel() {
        return null;
    }

    public BranchLabel continueLabel() {
        return null;
    }

    public FlowContext getInitializationContext() {
        return null;
    }

    protected boolean internalRecordNullityMismatch(Expression expression, TypeBinding typeBinding, FlowInfo flowInfo, int i, TypeBinding typeBinding2, int i2) {
        return false;
    }

    public boolean isBreakable() {
        return false;
    }

    public boolean isContinuable() {
        return false;
    }

    public boolean isNonReturningContext() {
        return false;
    }

    public boolean isSubRoutine() {
        return false;
    }

    public char[] labelName() {
        return null;
    }

    public void recordBreakFrom(FlowInfo flowInfo) {
    }

    public void recordBreakTo(FlowContext flowContext) {
    }

    public void recordContinueFrom(FlowContext flowContext, FlowInfo flowInfo) {
    }

    public boolean recordExitAgainstResource(BlockScope blockScope, FlowInfo flowInfo, FakedTrackingVariable fakedTrackingVariable, ASTNode aSTNode) {
        return false;
    }

    protected boolean recordFinalAssignment(VariableBinding variableBinding, Reference reference) {
        return true;
    }

    protected void recordNullReference(LocalVariableBinding localVariableBinding, ASTNode aSTNode, int i, FlowInfo flowInfo) {
    }

    public void recordReturnFrom(UnconditionalFlowInfo unconditionalFlowInfo) {
    }

    void removeFinalAssignmentIfAny(Reference reference) {
    }

    public SubRoutineStatement subroutine() {
        return null;
    }

    public FlowContext(FlowContext flowContext, ASTNode aSTNode) {
        this.conditionalLevel = -1;
        this.nullCheckedFieldReferences = null;
        this.timesToLiveForNullCheckInfo = null;
        this.parent = flowContext;
        this.associatedNode = aSTNode;
        if (flowContext != null) {
            if ((flowContext.tagBits & 3) != 0) {
                this.tagBits |= 1;
            }
            this.initsOnFinally = flowContext.initsOnFinally;
            this.conditionalLevel = flowContext.conditionalLevel;
            this.nullCheckedFieldReferences = flowContext.nullCheckedFieldReferences;
            this.timesToLiveForNullCheckInfo = flowContext.timesToLiveForNullCheckInfo;
        }
    }

    public void recordNullCheckedFieldReference(Reference reference, int i) {
        Reference[] referenceArr = this.nullCheckedFieldReferences;
        if (referenceArr == null) {
            Reference[] referenceArr2 = new Reference[2];
            referenceArr2[0] = reference;
            this.nullCheckedFieldReferences = referenceArr2;
            this.timesToLiveForNullCheckInfo = new int[]{i, -1};
            return;
        }
        int length = referenceArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            Reference[] referenceArr3 = this.nullCheckedFieldReferences;
            if (referenceArr3[i2] == null) {
                referenceArr3[i2] = reference;
                this.timesToLiveForNullCheckInfo[i2] = i;
                return;
            }
        }
        Reference[] referenceArr4 = this.nullCheckedFieldReferences;
        int i3 = length + 2;
        Reference[] referenceArr5 = new Reference[i3];
        this.nullCheckedFieldReferences = referenceArr5;
        System.arraycopy(referenceArr4, 0, referenceArr5, 0, length);
        int[] iArr = this.timesToLiveForNullCheckInfo;
        int[] iArr2 = new int[i3];
        this.timesToLiveForNullCheckInfo = iArr2;
        System.arraycopy(iArr, 0, iArr2, 0, length);
        this.nullCheckedFieldReferences[length] = reference;
        this.timesToLiveForNullCheckInfo[length] = i;
    }

    public void extendTimeToLiveForNullCheckedField(int i) {
        if (this.timesToLiveForNullCheckInfo == null) {
            return;
        }
        int i2 = 0;
        while (true) {
            int[] iArr = this.timesToLiveForNullCheckInfo;
            if (i2 >= iArr.length) {
                return;
            }
            int i3 = iArr[i2];
            if (i3 > 0) {
                iArr[i2] = i3 + i;
            }
            i2++;
        }
    }

    public void expireNullCheckedFieldInfo() {
        if (this.nullCheckedFieldReferences == null) {
            return;
        }
        int i = 0;
        while (true) {
            Reference[] referenceArr = this.nullCheckedFieldReferences;
            if (i >= referenceArr.length) {
                return;
            }
            int[] iArr = this.timesToLiveForNullCheckInfo;
            int i2 = iArr[i] - 1;
            iArr[i] = i2;
            if (i2 == 0) {
                referenceArr[i] = null;
            }
            i++;
        }
    }

    public boolean isNullcheckedFieldAccess(Reference reference) {
        Reference[] referenceArr = this.nullCheckedFieldReferences;
        if (referenceArr == null) {
            return false;
        }
        int length = referenceArr.length;
        for (int i = 0; i < length; i++) {
            Reference reference2 = this.nullCheckedFieldReferences[i];
            if (reference2 != null && reference2.isEquivalent(reference)) {
                return true;
            }
        }
        return false;
    }

    public void checkExceptionHandlers(TypeBinding typeBinding, ASTNode aSTNode, FlowInfo flowInfo, BlockScope blockScope) {
        checkExceptionHandlers(typeBinding, aSTNode, flowInfo, blockScope, false);
    }

    public void checkExceptionHandlers(TypeBinding typeBinding, ASTNode aSTNode, FlowInfo flowInfo, BlockScope blockScope, boolean z) {
        FlowInfo flowInfo2;
        if (blockScope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_7 && (aSTNode instanceof ThrowStatement)) {
            Expression expression = ((ThrowStatement) aSTNode).exception;
            LocalVariableBinding localVariableBinding = expression.localVariableBinding();
            if ((expression instanceof SingleNameReference) && (localVariableBinding instanceof CatchParameterBinding) && localVariableBinding.isEffectivelyFinal()) {
                checkExceptionHandlers(((CatchParameterBinding) localVariableBinding).getPreciseTypes(), aSTNode, flowInfo, blockScope);
                return;
            }
        }
        ArrayList arrayList = null;
        for (FlowContext localParent = this; localParent != null; localParent = localParent.getLocalParent()) {
            SubRoutineStatement subRoutineStatementSubroutine = localParent.subroutine();
            if (subRoutineStatementSubroutine != null && subRoutineStatementSubroutine.isSubRoutineEscaping()) {
                return;
            }
            if (localParent instanceof ExceptionHandlingFlowContext) {
                ExceptionHandlingFlowContext exceptionHandlingFlowContext = (ExceptionHandlingFlowContext) localParent;
                ReferenceBinding[] referenceBindingArr = exceptionHandlingFlowContext.handledExceptions;
                if (referenceBindingArr != Binding.NO_EXCEPTIONS) {
                    int i = 0;
                    boolean z2 = false;
                    for (int length = referenceBindingArr.length; i < length; length = length) {
                        ReferenceBinding referenceBinding = referenceBindingArr[i];
                        int iCompareTypes = referenceBinding == null ? -1 : Scope.compareTypes(typeBinding, referenceBinding);
                        if (arrayList == null || referenceBinding == null || iCompareTypes == 0) {
                            flowInfo2 = flowInfo;
                        } else {
                            int size = arrayList.size();
                            for (int i2 = 0; i2 < size; i2++) {
                                ((LoopingFlowContext) arrayList.get(i2)).recordCatchContextOfEscapingException(exceptionHandlingFlowContext, referenceBinding, flowInfo);
                            }
                            flowInfo2 = FlowInfo.DEAD_END;
                        }
                        if (iCompareTypes == -1) {
                            exceptionHandlingFlowContext.recordHandlingException(referenceBinding, flowInfo2.unconditionalInits(), typeBinding, typeBinding, aSTNode, z2);
                            z2 = true;
                        } else if (iCompareTypes == 1) {
                            exceptionHandlingFlowContext.recordHandlingException(referenceBinding, flowInfo2.unconditionalInits(), typeBinding, referenceBinding, aSTNode, false);
                        }
                        i++;
                    }
                    if (z2) {
                        return;
                    }
                }
                if (exceptionHandlingFlowContext.isMethodContext) {
                    if (!typeBinding.isUncheckedException(false)) {
                        boolean z3 = exceptionHandlingFlowContext instanceof ExceptionInferenceFlowContext;
                        if (exceptionHandlingFlowContext.associatedNode instanceof AbstractMethodDeclaration) {
                            AbstractMethodDeclaration abstractMethodDeclaration = (AbstractMethodDeclaration) exceptionHandlingFlowContext.associatedNode;
                            if (abstractMethodDeclaration.isConstructor() && abstractMethodDeclaration.binding.declaringClass.isAnonymousType()) {
                                z3 = true;
                            }
                        }
                        if (!z3) {
                            break;
                        }
                        exceptionHandlingFlowContext.mergeUnhandledException(typeBinding);
                        return;
                    }
                    return;
                }
            } else if (localParent instanceof LoopingFlowContext) {
                if (arrayList == null) {
                    arrayList = new ArrayList(5);
                }
                arrayList.add(localParent);
            }
            localParent.recordReturnFrom(flowInfo.unconditionalInits());
            if (!z && (localParent instanceof InsideSubRoutineFlowContext)) {
                ASTNode aSTNode2 = localParent.associatedNode;
                if (aSTNode2 instanceof TryStatement) {
                    flowInfo.addInitializationsFrom(((TryStatement) aSTNode2).subRoutineInits);
                }
            }
        }
        if (z) {
            blockScope.problemReporter().unhandledExceptionFromAutoClose(typeBinding, aSTNode);
        } else {
            blockScope.problemReporter().unhandledException(typeBinding, aSTNode);
        }
    }

    public void checkExceptionHandlers(TypeBinding[] typeBindingArr, ASTNode aSTNode, FlowInfo flowInfo, BlockScope blockScope) {
        int length;
        TypeBinding typeBinding;
        int i;
        ReferenceBinding referenceBinding;
        int i2;
        boolean[] zArr;
        int i3;
        ReferenceBinding[] referenceBindingArr;
        FlowInfo flowInfo2;
        if (typeBindingArr == null || (length = typeBindingArr.length) == 0) {
            return;
        }
        TypeBinding[] typeBindingArr2 = new TypeBinding[length];
        int i4 = 0;
        System.arraycopy(typeBindingArr, 0, typeBindingArr2, 0, length);
        TypeBinding typeBinding2 = null;
        FlowContext localParent = this;
        ArrayList arrayList = null;
        int i5 = length;
        while (localParent != null) {
            SubRoutineStatement subRoutineStatementSubroutine = localParent.subroutine();
            if (subRoutineStatementSubroutine != null && subRoutineStatementSubroutine.isSubRoutineEscaping()) {
                return;
            }
            if (localParent instanceof ExceptionHandlingFlowContext) {
                ExceptionHandlingFlowContext exceptionHandlingFlowContext = (ExceptionHandlingFlowContext) localParent;
                ReferenceBinding[] referenceBindingArr2 = exceptionHandlingFlowContext.handledExceptions;
                if (referenceBindingArr2 != Binding.NO_EXCEPTIONS) {
                    int length2 = referenceBindingArr2.length;
                    boolean[] zArr2 = new boolean[length];
                    int i6 = i4;
                    while (i6 < length2) {
                        ReferenceBinding referenceBinding2 = referenceBindingArr2[i6];
                        int i7 = i4;
                        while (i7 < length) {
                            TypeBinding typeBinding3 = typeBindingArr2[i7];
                            if (typeBinding3 != null) {
                                int iCompareTypes = referenceBinding2 == null ? -1 : Scope.compareTypes(typeBinding3, referenceBinding2);
                                if (arrayList == null || referenceBinding2 == null || iCompareTypes == 0) {
                                    flowInfo2 = flowInfo;
                                } else {
                                    int i8 = 0;
                                    for (int size = arrayList.size(); i8 < size; size = size) {
                                        ((LoopingFlowContext) arrayList.get(i8)).recordCatchContextOfEscapingException(exceptionHandlingFlowContext, referenceBinding2, flowInfo);
                                        i8++;
                                    }
                                    flowInfo2 = FlowInfo.DEAD_END;
                                }
                                if (iCompareTypes == -1) {
                                    i2 = i6;
                                    zArr = zArr2;
                                    i3 = length2;
                                    referenceBindingArr = referenceBindingArr2;
                                    i = i7;
                                    referenceBinding = referenceBinding2;
                                    exceptionHandlingFlowContext.recordHandlingException(referenceBinding2, flowInfo2.unconditionalInits(), typeBinding3, typeBinding3, aSTNode, zArr[i]);
                                    if (!zArr[i]) {
                                        zArr[i] = true;
                                        i5--;
                                    }
                                } else if (iCompareTypes != 1) {
                                    i2 = i6;
                                    zArr = zArr2;
                                    i3 = length2;
                                    referenceBindingArr = referenceBindingArr2;
                                    i = i7;
                                    referenceBinding = referenceBinding2;
                                } else {
                                    i = i7;
                                    referenceBinding = referenceBinding2;
                                    i2 = i6;
                                    zArr = zArr2;
                                    i3 = length2;
                                    referenceBindingArr = referenceBindingArr2;
                                    exceptionHandlingFlowContext.recordHandlingException(referenceBinding2, flowInfo2.unconditionalInits(), typeBinding3, referenceBinding, aSTNode, false);
                                }
                            } else {
                                i = i7;
                                referenceBinding = referenceBinding2;
                                i2 = i6;
                                zArr = zArr2;
                                i3 = length2;
                                referenceBindingArr = referenceBindingArr2;
                            }
                            i7 = i + 1;
                            referenceBinding2 = referenceBinding;
                            i6 = i2;
                            zArr2 = zArr;
                            length2 = i3;
                            referenceBindingArr2 = referenceBindingArr;
                            typeBinding2 = null;
                            i4 = 0;
                        }
                        i6++;
                    }
                    for (int i9 = i4; i9 < length; i9++) {
                        if (zArr2[i9]) {
                            typeBindingArr2[i9] = typeBinding2;
                        }
                    }
                }
                if (exceptionHandlingFlowContext.isMethodContext) {
                    for (int i10 = 0; i10 < length; i10++) {
                        TypeBinding typeBinding4 = typeBindingArr2[i10];
                        if (typeBinding4 != null && typeBinding4.isUncheckedException(false)) {
                            typeBindingArr2[i10] = null;
                        }
                    }
                    boolean z = exceptionHandlingFlowContext instanceof ExceptionInferenceFlowContext;
                    if (exceptionHandlingFlowContext.associatedNode instanceof AbstractMethodDeclaration) {
                        AbstractMethodDeclaration abstractMethodDeclaration = (AbstractMethodDeclaration) exceptionHandlingFlowContext.associatedNode;
                        if (abstractMethodDeclaration.isConstructor() && abstractMethodDeclaration.binding.declaringClass.isAnonymousType()) {
                            z = true;
                        }
                    }
                    if (!z) {
                        break;
                    }
                    for (int i11 = 0; i11 < length; i11++) {
                        TypeBinding typeBinding5 = typeBindingArr2[i11];
                        if (typeBinding5 != null) {
                            exceptionHandlingFlowContext.mergeUnhandledException(typeBinding5);
                        }
                    }
                    return;
                }
                i4 = 0;
                typeBinding = null;
            } else {
                typeBinding = typeBinding2;
                if (localParent instanceof LoopingFlowContext) {
                    if (arrayList == null) {
                        arrayList = new ArrayList(5);
                    }
                    arrayList.add(localParent);
                }
            }
            if (i5 == 0) {
                return;
            }
            localParent.recordReturnFrom(flowInfo.unconditionalInits());
            if (localParent instanceof InsideSubRoutineFlowContext) {
                ASTNode aSTNode2 = localParent.associatedNode;
                if (aSTNode2 instanceof TryStatement) {
                    flowInfo.addInitializationsFrom(((TryStatement) aSTNode2).subRoutineInits);
                }
            }
            localParent = localParent.getLocalParent();
            typeBinding2 = typeBinding;
        }
        for (int i12 = 0; i12 < length; i12++) {
            TypeBinding typeBinding6 = typeBindingArr2[i12];
            if (typeBinding6 != null) {
                int i13 = 0;
                while (true) {
                    if (i13 < i12) {
                        if (TypeBinding.equalsEquals(typeBindingArr2[i13], typeBinding6)) {
                            break;
                        } else {
                            i13++;
                        }
                    } else {
                        blockScope.problemReporter().unhandledException(typeBinding6, aSTNode);
                        break;
                    }
                }
            }
        }
    }

    public FlowInfo getInitsForFinalBlankInitializationCheck(TypeBinding typeBinding, FlowInfo flowInfo) {
        FlowContext localParent = this;
        do {
            if (localParent instanceof InitializationFlowContext) {
                InitializationFlowContext initializationFlowContext = (InitializationFlowContext) localParent;
                if (TypeBinding.equalsEquals(((TypeDeclaration) initializationFlowContext.associatedNode).binding, typeBinding)) {
                    return flowInfo;
                }
                flowInfo = initializationFlowContext.initsBeforeContext;
                localParent = initializationFlowContext.initializationParent;
            } else if (localParent instanceof ExceptionHandlingFlowContext) {
                ExceptionHandlingFlowContext exceptionHandlingFlowContext = (ExceptionHandlingFlowContext) localParent;
                localParent = exceptionHandlingFlowContext.initializationParent == null ? exceptionHandlingFlowContext.parent : exceptionHandlingFlowContext.initializationParent;
            } else {
                localParent = localParent.getLocalParent();
            }
        } while (localParent != null);
        return null;
    }

    public FlowContext getTargetContextForBreakLabel(char[] cArr) {
        FlowContext flowContext = null;
        for (FlowContext localParent = this; localParent != null; localParent = localParent.getLocalParent()) {
            if (localParent.isNonReturningContext()) {
                flowContext = localParent;
            }
            char[] cArrLabelName = localParent.labelName();
            if (cArrLabelName != null && CharOperation.equals(cArrLabelName, cArr)) {
                ((LabeledStatement) localParent.associatedNode).bits |= 64;
                return flowContext == null ? localParent : flowContext;
            }
        }
        return null;
    }

    public FlowContext getTargetContextForContinueLabel(char[] cArr) {
        FlowContext flowContext = null;
        FlowContext flowContext2 = null;
        for (FlowContext localParent = this; localParent != null; localParent = localParent.getLocalParent()) {
            if (localParent.isNonReturningContext()) {
                flowContext2 = localParent;
            } else if (localParent.isContinuable()) {
                flowContext = localParent;
            }
            char[] cArrLabelName = localParent.labelName();
            if (cArrLabelName != null && CharOperation.equals(cArrLabelName, cArr)) {
                ((LabeledStatement) localParent.associatedNode).bits |= 64;
                if (flowContext == null || localParent.associatedNode.concreteStatement() != flowContext.associatedNode) {
                    return NotContinuableContext;
                }
                return flowContext2 == null ? flowContext : flowContext2;
            }
        }
        return null;
    }

    public FlowContext getTargetContextForDefaultBreak() {
        FlowContext flowContext = null;
        for (FlowContext localParent = this; localParent != null; localParent = localParent.getLocalParent()) {
            if (localParent.isNonReturningContext()) {
                flowContext = localParent;
            }
            if (localParent.isBreakable() && localParent.labelName() == null) {
                return flowContext == null ? localParent : flowContext;
            }
        }
        return null;
    }

    public FlowContext getTargetContextForDefaultContinue() {
        FlowContext flowContext = null;
        for (FlowContext localParent = this; localParent != null; localParent = localParent.getLocalParent()) {
            if (localParent.isNonReturningContext()) {
                flowContext = localParent;
            }
            if (localParent.isContinuable()) {
                return flowContext == null ? localParent : flowContext;
            }
        }
        return null;
    }

    public FlowContext getLocalParent() {
        ASTNode aSTNode = this.associatedNode;
        if ((aSTNode instanceof AbstractMethodDeclaration) || (aSTNode instanceof TypeDeclaration) || (aSTNode instanceof LambdaExpression)) {
            return null;
        }
        return this.parent;
    }

    public String individualToString() {
        return "Flow context";
    }

    public FlowInfo initsOnBreak() {
        return FlowInfo.DEAD_END;
    }

    public UnconditionalFlowInfo initsOnReturn() {
        return FlowInfo.DEAD_END;
    }

    public void markFinallyNullStatus(LocalVariableBinding localVariableBinding, int i) {
        int i2;
        FlowInfo flowInfo = this.initsOnFinally;
        if (flowInfo == null || (i2 = this.conditionalLevel) == -1) {
            return;
        }
        if (i2 == 0) {
            flowInfo.markNullStatus(localVariableBinding, i);
            return;
        }
        UnconditionalFlowInfo unconditionalFlowInfoUnconditionalCopy = flowInfo.unconditionalCopy();
        unconditionalFlowInfoUnconditionalCopy.markNullStatus(localVariableBinding, i);
        this.initsOnFinally = this.initsOnFinally.mergedWith(unconditionalFlowInfoUnconditionalCopy);
    }

    public void mergeFinallyNullInfo(FlowInfo flowInfo) {
        int i;
        FlowInfo flowInfo2 = this.initsOnFinally;
        if (flowInfo2 == null || (i = this.conditionalLevel) == -1) {
            return;
        }
        if (i == 0) {
            flowInfo2.addNullInfoFrom(flowInfo);
        } else {
            this.initsOnFinally = flowInfo2.mergedWith(flowInfo.unconditionalCopy());
        }
    }

    public void recordAbruptExit() {
        FlowContext flowContext;
        int i = this.conditionalLevel;
        if (i > -1) {
            this.conditionalLevel = i + 1;
            if ((this instanceof ExceptionHandlingFlowContext) || (flowContext = this.parent) == null) {
                return;
            }
            flowContext.recordAbruptExit();
        }
    }

    protected void recordProvidedExpectedTypes(TypeBinding typeBinding, TypeBinding typeBinding2, int i) {
        int i2 = 5;
        if (i == 0) {
            this.providedExpectedTypes = new TypeBinding[5][];
        } else {
            TypeBinding[][] typeBindingArr = this.providedExpectedTypes;
            if (typeBindingArr == null) {
                while (i2 <= i) {
                    i2 *= 2;
                }
                this.providedExpectedTypes = new TypeBinding[i2][];
            } else if (i >= typeBindingArr.length) {
                int length = typeBindingArr.length;
                TypeBinding[][] typeBindingArr2 = new TypeBinding[i * 2][];
                this.providedExpectedTypes = typeBindingArr2;
                System.arraycopy(typeBindingArr, 0, typeBindingArr2, 0, length);
            }
        }
        this.providedExpectedTypes[i] = new TypeBinding[]{typeBinding, typeBinding2};
    }

    public void recordUnboxing(Scope scope, Expression expression, int i, FlowInfo flowInfo) {
        checkUnboxing(scope, expression, flowInfo);
    }

    protected void checkUnboxing(Scope scope, Expression expression, FlowInfo flowInfo) {
        FlowContext flowContext;
        int iNullStatus = expression.nullStatus(flowInfo, this);
        if ((iNullStatus & 2) != 0) {
            scope.problemReporter().nullUnboxing(expression, expression.resolvedType);
            return;
        }
        if ((iNullStatus & 16) != 0) {
            scope.problemReporter().potentialNullUnboxing(expression, expression.resolvedType);
        } else if ((iNullStatus & 4) == 0 && (flowContext = this.parent) != null) {
            flowContext.recordUnboxing(scope, expression, 1, flowInfo);
        }
    }

    public void recordSettingFinal(VariableBinding variableBinding, Reference reference, FlowInfo flowInfo) {
        if ((flowInfo.tagBits & 1) == 0) {
            for (FlowContext localParent = this; localParent != null && localParent.recordFinalAssignment(variableBinding, reference); localParent = localParent.getLocalParent()) {
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0035  */
    /* JADX WARN: Code duplicated, block: B:22:0x003b A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:23:0x003d  */
    /* JADX WARN: Code duplicated, block: B:25:0x0041  */
    /* JADX WARN: Code duplicated, block: B:27:0x0050  */
    /* JADX WARN: Code duplicated, block: B:29:0x005f  */
    /* JADX WARN: Code duplicated, block: B:31:0x0065 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:32:0x0066  */
    /* JADX WARN: Code duplicated, block: B:34:0x0075  */
    /* JADX WARN: Code duplicated, block: B:36:0x0079 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:37:0x007b  */
    /* JADX WARN: Code duplicated, block: B:39:0x007f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:41:0x0083  */
    /* JADX WARN: Code duplicated, block: B:43:0x008b  */
    /* JADX WARN: Code duplicated, block: B:45:0x0093  */
    /* JADX WARN: Code duplicated, block: B:47:0x0097  */
    /* JADX WARN: Code duplicated, block: B:53:0x00b3  */
    /* JADX WARN: Code duplicated, block: B:55:0x00b7  */
    /* JADX WARN: Code duplicated, block: B:61:0x00c8  */
    /* JADX WARN: Code duplicated, block: B:64:0x00d7  */
    /* JADX WARN: Code duplicated, block: B:66:0x00dd  */
    /* JADX WARN: Code duplicated, block: B:68:0x00e1  */
    /* JADX WARN: Code duplicated, block: B:70:0x00e4  */
    /* JADX WARN: Code duplicated, block: B:76:0x00f5  */
    /* JADX WARN: Code duplicated, block: B:78:0x00f9  */
    /* JADX WARN: Code duplicated, block: B:82:0x0106  */
    /* JADX WARN: Code duplicated, block: B:84:0x010c A[RETURN] */
    public void recordUsingNullReference(Scope scope, LocalVariableBinding localVariableBinding, ASTNode aSTNode, int i, FlowInfo flowInfo) {
        Expression expression;
        int i2;
        int i3;
        if ((flowInfo.tagBits & 3) != 0 || flowInfo.isDefinitelyUnknown(localVariableBinding)) {
            return;
        }
        int i4 = i | (this.tagBits & 4096);
        int i5 = (-61441) & i4;
        if (i5 != 3) {
            if (i5 == 769 || i5 == 1025) {
                expression = (Expression) aSTNode;
                if (flowInfo.isDefinitelyNull(localVariableBinding)) {
                    i3 = i4 & CONTEXT_MASK;
                    if (i3 != 256) {
                        if ((i4 & 255) != 1 && (expression.implicitConversion & 1024) != 0) {
                            scope.problemReporter().localVariableNullReference(localVariableBinding, expression);
                            return;
                        }
                        if ((i4 & 4096) == 0) {
                            scope.problemReporter().localVariableRedundantCheckOnNull(localVariableBinding, expression);
                        }
                        flowInfo.initsWhenFalse().setReachMode(2);
                        return;
                    }
                    if (i3 != 512) {
                        if ((i4 & 255) != 1 && (expression.implicitConversion & 1024) != 0) {
                            scope.problemReporter().localVariableNullReference(localVariableBinding, expression);
                            return;
                        } else {
                            scope.problemReporter().localVariableNullComparedToNonNull(localVariableBinding, expression);
                            flowInfo.initsWhenTrue().setReachMode(2);
                            return;
                        }
                    }
                    if (i3 != 768) {
                        scope.problemReporter().localVariableRedundantNullAssignment(localVariableBinding, expression);
                        return;
                    } else if (i3 == 1024) {
                        scope.problemReporter().localVariableNullInstanceof(localVariableBinding, expression);
                        return;
                    }
                } else if (flowInfo.isPotentiallyNull(localVariableBinding)) {
                    i2 = i4 & CONTEXT_MASK;
                    if (i2 == 256) {
                        if ((i4 & 255) == 1 && (expression.implicitConversion & 1024) != 0) {
                            scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, expression);
                            return;
                        }
                    } else if (i2 == 512 && (i4 & 255) == 1 && (expression.implicitConversion & 1024) != 0) {
                        scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, expression);
                        return;
                    }
                } else if (flowInfo.cannotBeDefinitelyNullOrNonNull(localVariableBinding)) {
                    return;
                }
            } else if (i5 == 256) {
                if (flowInfo.isDefinitelyNonNull(localVariableBinding)) {
                    if (i5 == 512) {
                        if ((i4 & 4096) == 0) {
                            scope.problemReporter().localVariableRedundantCheckOnNonNull(localVariableBinding, aSTNode);
                        }
                        flowInfo.initsWhenFalse().setReachMode(2);
                        return;
                    } else {
                        scope.problemReporter().localVariableNonNullComparedToNull(localVariableBinding, aSTNode);
                        flowInfo.initsWhenTrue().setReachMode(2);
                        return;
                    }
                }
                if (flowInfo.cannotBeDefinitelyNullOrNonNull(localVariableBinding)) {
                    return;
                }
                expression = (Expression) aSTNode;
                if (flowInfo.isDefinitelyNull(localVariableBinding)) {
                    i3 = i4 & CONTEXT_MASK;
                    if (i3 != 256) {
                        if ((i4 & 255) != 1) {
                        }
                        if ((i4 & 4096) == 0) {
                            scope.problemReporter().localVariableRedundantCheckOnNull(localVariableBinding, expression);
                        }
                        flowInfo.initsWhenFalse().setReachMode(2);
                        return;
                    }
                    if (i3 != 512) {
                        if ((i4 & 255) != 1) {
                        }
                        scope.problemReporter().localVariableNullComparedToNonNull(localVariableBinding, expression);
                        flowInfo.initsWhenTrue().setReachMode(2);
                        return;
                    } else if (i3 != 768) {
                        scope.problemReporter().localVariableRedundantNullAssignment(localVariableBinding, expression);
                        return;
                    } else if (i3 == 1024) {
                        scope.problemReporter().localVariableNullInstanceof(localVariableBinding, expression);
                        return;
                    }
                } else if (flowInfo.isPotentiallyNull(localVariableBinding)) {
                    i2 = i4 & CONTEXT_MASK;
                    if (i2 == 256) {
                        if (i2 == 512) {
                            scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, expression);
                            return;
                        }
                    } else if ((i4 & 255) == 1) {
                        scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, expression);
                        return;
                    }
                } else if (flowInfo.cannotBeDefinitelyNullOrNonNull(localVariableBinding)) {
                    return;
                }
            } else if (i5 == 257) {
                expression = (Expression) aSTNode;
                if (flowInfo.isDefinitelyNull(localVariableBinding)) {
                    i3 = i4 & CONTEXT_MASK;
                    if (i3 != 256) {
                        if ((i4 & 255) != 1) {
                        }
                        if ((i4 & 4096) == 0) {
                            scope.problemReporter().localVariableRedundantCheckOnNull(localVariableBinding, expression);
                        }
                        flowInfo.initsWhenFalse().setReachMode(2);
                        return;
                    }
                    if (i3 != 512) {
                        if ((i4 & 255) != 1) {
                        }
                        scope.problemReporter().localVariableNullComparedToNonNull(localVariableBinding, expression);
                        flowInfo.initsWhenTrue().setReachMode(2);
                        return;
                    } else if (i3 != 768) {
                        scope.problemReporter().localVariableRedundantNullAssignment(localVariableBinding, expression);
                        return;
                    } else if (i3 == 1024) {
                        scope.problemReporter().localVariableNullInstanceof(localVariableBinding, expression);
                        return;
                    }
                } else if (flowInfo.isPotentiallyNull(localVariableBinding)) {
                    i2 = i4 & CONTEXT_MASK;
                    if (i2 == 256) {
                        if (i2 == 512) {
                            scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, expression);
                            return;
                        }
                    } else if ((i4 & 255) == 1) {
                        scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, expression);
                        return;
                    }
                } else if (flowInfo.cannotBeDefinitelyNullOrNonNull(localVariableBinding)) {
                    return;
                }
            } else {
                if (i5 != 512) {
                    if (i5 == 513) {
                    }
                } else {
                    if (flowInfo.isDefinitelyNonNull(localVariableBinding)) {
                        if (i5 == 512) {
                            if ((i4 & 4096) == 0) {
                                scope.problemReporter().localVariableRedundantCheckOnNonNull(localVariableBinding, aSTNode);
                            }
                            flowInfo.initsWhenFalse().setReachMode(2);
                            return;
                        } else {
                            scope.problemReporter().localVariableNonNullComparedToNull(localVariableBinding, aSTNode);
                            flowInfo.initsWhenTrue().setReachMode(2);
                            return;
                        }
                    }
                    if (flowInfo.cannotBeDefinitelyNullOrNonNull(localVariableBinding)) {
                        return;
                    }
                }
                expression = (Expression) aSTNode;
                if (flowInfo.isDefinitelyNull(localVariableBinding)) {
                    i3 = i4 & CONTEXT_MASK;
                    if (i3 != 256) {
                        if ((i4 & 255) != 1) {
                        }
                        if ((i4 & 4096) == 0) {
                            scope.problemReporter().localVariableRedundantCheckOnNull(localVariableBinding, expression);
                        }
                        flowInfo.initsWhenFalse().setReachMode(2);
                        return;
                    }
                    if (i3 != 512) {
                        if ((i4 & 255) != 1) {
                        }
                        scope.problemReporter().localVariableNullComparedToNonNull(localVariableBinding, expression);
                        flowInfo.initsWhenTrue().setReachMode(2);
                        return;
                    } else if (i3 != 768) {
                        scope.problemReporter().localVariableRedundantNullAssignment(localVariableBinding, expression);
                        return;
                    } else if (i3 == 1024) {
                        scope.problemReporter().localVariableNullInstanceof(localVariableBinding, expression);
                        return;
                    }
                } else if (flowInfo.isPotentiallyNull(localVariableBinding)) {
                    i2 = i4 & CONTEXT_MASK;
                    if (i2 == 256) {
                        if (i2 == 512) {
                            scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, expression);
                            return;
                        }
                    } else if ((i4 & 255) == 1) {
                        scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, expression);
                        return;
                    }
                } else if (flowInfo.cannotBeDefinitelyNullOrNonNull(localVariableBinding)) {
                    return;
                }
            }
        } else if (flowInfo.isDefinitelyNull(localVariableBinding)) {
            scope.problemReporter().localVariableNullReference(localVariableBinding, aSTNode);
            return;
        } else if (flowInfo.isPotentiallyNull(localVariableBinding)) {
            if (localVariableBinding.type.isFreeTypeVariable()) {
                scope.problemReporter().localVariableFreeTypeVariableReference(localVariableBinding, aSTNode);
                return;
            } else {
                scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, aSTNode);
                return;
            }
        }
        FlowContext flowContext = this.parent;
        if (flowContext != null) {
            flowContext.recordUsingNullReference(scope, localVariableBinding, aSTNode, i4, flowInfo);
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        FlowContext flowContext = this;
        int i = 0;
        while (true) {
            flowContext = flowContext.parent;
            if (flowContext == null) {
                break;
            }
            i++;
        }
        int i2 = i + 1;
        FlowContext[] flowContextArr = new FlowContext[i2];
        FlowContext flowContext2 = this;
        for (int i3 = i; i3 >= 0; i3--) {
            flowContextArr[i3] = flowContext2;
            flowContext2 = flowContext2.parent;
        }
        for (int i4 = 0; i4 < i; i4++) {
            for (int i5 = 0; i5 < i4; i5++) {
                stringBuffer.append('\t');
            }
            stringBuffer.append(flowContextArr[i4].individualToString()).append('\n');
        }
        stringBuffer.append('*');
        for (int i6 = 0; i6 < i2; i6++) {
            stringBuffer.append('\t');
        }
        stringBuffer.append(individualToString()).append('\n');
        return stringBuffer.toString();
    }

    public void recordNullityMismatch(BlockScope blockScope, Expression expression, TypeBinding typeBinding, TypeBinding typeBinding2, FlowInfo flowInfo, int i, NullAnnotationMatching nullAnnotationMatching) {
        if (typeBinding == null) {
            return;
        }
        if (expression.localVariableBinding() != null) {
            for (FlowContext flowContext = this; flowContext != null; flowContext = flowContext.parent) {
                if (flowContext.internalRecordNullityMismatch(expression, typeBinding, flowInfo, i, typeBinding2, ((this.tagBits & 4096) == 0 ? 0 : 4096) | 128)) {
                    return;
                }
            }
        }
        if (nullAnnotationMatching != null) {
            blockScope.problemReporter().nullityMismatchingTypeAnnotation(expression, typeBinding, typeBinding2, nullAnnotationMatching);
        } else {
            blockScope.problemReporter().nullityMismatch(expression, typeBinding, typeBinding2, i, blockScope.environment().getNonNullAnnotationName());
        }
    }
}
