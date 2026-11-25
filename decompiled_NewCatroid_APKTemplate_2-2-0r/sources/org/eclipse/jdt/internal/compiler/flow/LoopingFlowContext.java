package org.eclipse.jdt.internal.compiler.flow;

import androidx.core.view.InputDeviceCompat;
import java.util.ArrayList;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.FakedTrackingVariable;
import org.eclipse.jdt.internal.compiler.ast.Reference;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.VariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class LoopingFlowContext extends SwitchFlowContext {
    int assignCount;
    Scope associatedScope;
    private LabelFlowContext[] breakTargetContexts;
    private int breakTargetsCount;
    public BranchLabel continueLabel;
    private ArrayList escapingExceptionCatchSites;
    Reference[] finalAssignments;
    VariableBinding[] finalVariables;
    public UnconditionalFlowInfo initsOnContinue;
    private LoopingFlowContext[] innerFlowContexts;
    private int innerFlowContextsCount;
    private UnconditionalFlowInfo[] innerFlowInfos;
    int[] nullCheckTypes;
    int nullCount;
    UnconditionalFlowInfo[] nullInfos;
    LocalVariableBinding[] nullLocals;
    ASTNode[] nullReferences;
    private UnconditionalFlowInfo upstreamNullFlowInfo;

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public boolean isContinuable() {
        return true;
    }

    private static class EscapingExceptionCatchSite {
        final ExceptionHandlingFlowContext catchingContext;
        final ReferenceBinding caughtException;
        final FlowInfo exceptionInfo;

        public EscapingExceptionCatchSite(ExceptionHandlingFlowContext exceptionHandlingFlowContext, ReferenceBinding referenceBinding, FlowInfo flowInfo) {
            this.catchingContext = exceptionHandlingFlowContext;
            this.caughtException = referenceBinding;
            this.exceptionInfo = flowInfo;
        }

        void simulateThrowAfterLoopBack(FlowInfo flowInfo) {
            this.catchingContext.recordHandlingException(this.caughtException, flowInfo.unconditionalCopy().addNullInfoFrom(this.exceptionInfo).unconditionalInits(), null, null, null, true);
        }
    }

    public LoopingFlowContext(FlowContext flowContext, FlowInfo flowInfo, ASTNode aSTNode, BranchLabel branchLabel, BranchLabel branchLabel2, Scope scope, boolean z) {
        super(flowContext, aSTNode, branchLabel, z);
        this.initsOnContinue = FlowInfo.DEAD_END;
        this.innerFlowContexts = null;
        this.innerFlowInfos = null;
        this.innerFlowContextsCount = 0;
        this.breakTargetContexts = null;
        this.breakTargetsCount = 0;
        this.assignCount = 0;
        this.escapingExceptionCatchSites = null;
        this.tagBits |= 2;
        this.continueLabel = branchLabel2;
        this.associatedScope = scope;
        this.upstreamNullFlowInfo = flowInfo.unconditionalCopy();
    }

    /* JADX WARN: Code duplicated, block: B:22:0x004d A[LOOP:1: B:20:0x004a->B:22:0x004d, LOOP_END] */
    public void complainOnDeferredFinalChecks(BlockScope blockScope, FlowInfo flowInfo) {
        FlowContext localParent;
        for (int i = 0; i < this.assignCount; i++) {
            VariableBinding variableBinding = this.finalVariables[i];
            if (variableBinding != null) {
                if (variableBinding instanceof FieldBinding) {
                    FieldBinding fieldBinding = (FieldBinding) variableBinding;
                    if (flowInfo.isPotentiallyAssigned(fieldBinding)) {
                        blockScope.problemReporter().duplicateInitializationOfBlankFinalField(fieldBinding, this.finalAssignments[i]);
                        for (localParent = getLocalParent(); localParent != null; localParent = localParent.getLocalParent()) {
                            localParent.removeFinalAssignmentIfAny(this.finalAssignments[i]);
                        }
                    }
                } else {
                    LocalVariableBinding localVariableBinding = (LocalVariableBinding) variableBinding;
                    if (flowInfo.isPotentiallyAssigned(localVariableBinding)) {
                        variableBinding.tagBits &= -2049;
                        if (variableBinding.isFinal()) {
                            blockScope.problemReporter().duplicateInitializationOfFinalLocal(localVariableBinding, this.finalAssignments[i]);
                            while (localParent != null) {
                                localParent.removeFinalAssignmentIfAny(this.finalAssignments[i]);
                            }
                        }
                    }
                }
            }
        }
    }

    public void complainOnDeferredNullChecks(BlockScope blockScope, FlowInfo flowInfo) {
        complainOnDeferredNullChecks(blockScope, flowInfo, true);
    }

    /* JADX WARN: Code duplicated, block: B:101:0x01b0  */
    /* JADX WARN: Code duplicated, block: B:104:0x01bf  */
    /* JADX WARN: Code duplicated, block: B:106:0x01c8  */
    /* JADX WARN: Code duplicated, block: B:120:0x0246  */
    /* JADX WARN: Code duplicated, block: B:122:0x024c  */
    /* JADX WARN: Code duplicated, block: B:160:0x02d5  */
    /* JADX WARN: Code duplicated, block: B:162:0x02db  */
    /* JADX WARN: Code duplicated, block: B:164:0x02e9  */
    /* JADX WARN: Code duplicated, block: B:166:0x02ed  */
    /* JADX WARN: Code duplicated, block: B:167:0x02f5  */
    /* JADX WARN: Code duplicated, block: B:189:0x0348  */
    /* JADX WARN: Code duplicated, block: B:191:0x0354  */
    /* JADX WARN: Code duplicated, block: B:193:0x0358  */
    /* JADX WARN: Code duplicated, block: B:195:0x035c  */
    /* JADX WARN: Code duplicated, block: B:198:0x0361  */
    /* JADX WARN: Code duplicated, block: B:199:0x0369  */
    /* JADX WARN: Code duplicated, block: B:201:0x0375  */
    /* JADX WARN: Code duplicated, block: B:203:0x037e  */
    /* JADX WARN: Code duplicated, block: B:206:0x038c  */
    /* JADX WARN: Code duplicated, block: B:207:0x0395  */
    /* JADX WARN: Code duplicated, block: B:209:0x039c  */
    /* JADX WARN: Code duplicated, block: B:212:0x03aa  */
    /* JADX WARN: Code duplicated, block: B:214:0x03b2  */
    /* JADX WARN: Code duplicated, block: B:215:0x03bb  */
    /* JADX WARN: Code duplicated, block: B:217:0x03c3  */
    /* JADX WARN: Code duplicated, block: B:219:0x03cb  */
    /* JADX WARN: Code duplicated, block: B:222:0x03d0  */
    /* JADX WARN: Code duplicated, block: B:224:0x03d9  */
    /* JADX WARN: Code duplicated, block: B:226:0x03de  */
    /* JADX WARN: Code duplicated, block: B:227:0x03e6  */
    /* JADX WARN: Code duplicated, block: B:229:0x03f1  */
    /* JADX WARN: Code duplicated, block: B:231:0x03f6  */
    /* JADX WARN: Code duplicated, block: B:232:0x03fe A[PHI: r7
      0x03fe: PHI (r7v10 boolean) = (r7v9 boolean), (r7v13 boolean) binds: [B:228:0x03ef, B:223:0x03d7] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:233:0x0403  */
    /* JADX WARN: Code duplicated, block: B:25:0x0076  */
    /* JADX WARN: Code duplicated, block: B:27:0x007c  */
    /* JADX WARN: Code duplicated, block: B:29:0x008a  */
    /* JADX WARN: Code duplicated, block: B:31:0x008e  */
    /* JADX WARN: Code duplicated, block: B:32:0x0096  */
    /* JADX WARN: Code duplicated, block: B:33:0x009e  */
    /* JADX WARN: Code duplicated, block: B:35:0x00a4  */
    /* JADX WARN: Code duplicated, block: B:37:0x00b2  */
    /* JADX WARN: Code duplicated, block: B:39:0x00b6  */
    /* JADX WARN: Code duplicated, block: B:40:0x00be  */
    /* JADX WARN: Code duplicated, block: B:43:0x00cb  */
    /* JADX WARN: Code duplicated, block: B:45:0x00d1  */
    /* JADX WARN: Code duplicated, block: B:47:0x00df  */
    /* JADX WARN: Code duplicated, block: B:49:0x00e3  */
    /* JADX WARN: Code duplicated, block: B:50:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:64:0x0118  */
    /* JADX WARN: Code duplicated, block: B:66:0x0121  */
    /* JADX WARN: Code duplicated, block: B:68:0x012f  */
    /* JADX WARN: Code duplicated, block: B:70:0x0133  */
    /* JADX WARN: Code duplicated, block: B:72:0x0137  */
    /* JADX WARN: Code duplicated, block: B:75:0x013d  */
    /* JADX WARN: Code duplicated, block: B:76:0x0145  */
    /* JADX WARN: Code duplicated, block: B:78:0x0152  */
    /* JADX WARN: Code duplicated, block: B:80:0x0157  */
    /* JADX WARN: Code duplicated, block: B:83:0x0166  */
    /* JADX WARN: Code duplicated, block: B:84:0x016e  */
    /* JADX WARN: Code duplicated, block: B:86:0x0173  */
    /* JADX WARN: Code duplicated, block: B:89:0x0182  */
    /* JADX WARN: Code duplicated, block: B:91:0x018a  */
    /* JADX WARN: Code duplicated, block: B:92:0x0192  */
    /* JADX WARN: Code duplicated, block: B:94:0x0198  */
    /* JADX WARN: Code duplicated, block: B:96:0x01a2  */
    /* JADX WARN: Code duplicated, block: B:99:0x01a7  */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x02c9, code lost:
    
        if (r5 != 513) goto L156;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:22:0x006a. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:23:0x006d. Please report as an issue. */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void complainOnDeferredNullChecks(org.eclipse.jdt.internal.compiler.lookup.BlockScope r28, org.eclipse.jdt.internal.compiler.flow.FlowInfo r29, boolean r30) {
        /*
            Method dump skipped, instruction units count: 1208
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jdt.internal.compiler.flow.LoopingFlowContext.complainOnDeferredNullChecks(org.eclipse.jdt.internal.compiler.lookup.BlockScope, org.eclipse.jdt.internal.compiler.flow.FlowInfo, boolean):void");
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public BranchLabel continueLabel() {
        return this.continueLabel;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.SwitchFlowContext, org.eclipse.jdt.internal.compiler.flow.FlowContext
    public String individualToString() {
        StringBuffer stringBuffer = new StringBuffer("Looping flow context[initsOnBreak - ");
        stringBuffer.append(this.initsOnBreak.toString()).append("][initsOnContinue - ");
        stringBuffer.append(this.initsOnContinue.toString()).append("][finalAssignments count - ");
        stringBuffer.append(this.assignCount).append("][nullReferences count - ");
        stringBuffer.append(this.nullCount).append(']');
        return stringBuffer.toString();
    }

    public boolean isContinuedTo() {
        return this.initsOnContinue != FlowInfo.DEAD_END;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public void recordBreakTo(FlowContext flowContext) {
        if (flowContext instanceof LabelFlowContext) {
            int i = this.breakTargetsCount;
            this.breakTargetsCount = i + 1;
            if (i == 0) {
                this.breakTargetContexts = new LabelFlowContext[2];
            } else {
                LabelFlowContext[] labelFlowContextArr = this.breakTargetContexts;
                if (i == labelFlowContextArr.length) {
                    LabelFlowContext[] labelFlowContextArr2 = new LabelFlowContext[i + 2];
                    this.breakTargetContexts = labelFlowContextArr2;
                    System.arraycopy(labelFlowContextArr, 0, labelFlowContextArr2, 0, i);
                }
            }
            this.breakTargetContexts[i] = (LabelFlowContext) flowContext;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public void recordContinueFrom(FlowContext flowContext, FlowInfo flowInfo) {
        if ((flowInfo.tagBits & 1) == 0) {
            if ((this.initsOnContinue.tagBits & 1) == 0) {
                this.initsOnContinue = this.initsOnContinue.mergedWith(flowInfo.unconditionalInitsWithoutSideEffect());
            } else {
                this.initsOnContinue = flowInfo.unconditionalCopy();
            }
            while (flowContext != this && !(flowContext instanceof LoopingFlowContext)) {
                flowContext = flowContext.parent;
            }
            if (flowContext == this) {
                this.upstreamNullFlowInfo.addPotentialNullInfoFrom(flowInfo.unconditionalInitsWithoutSideEffect());
                return;
            }
            LoopingFlowContext[] loopingFlowContextArr = this.innerFlowContexts;
            if (loopingFlowContextArr == null) {
                this.innerFlowContexts = new LoopingFlowContext[5];
                this.innerFlowInfos = new UnconditionalFlowInfo[5];
            } else {
                int i = this.innerFlowContextsCount;
                int length = loopingFlowContextArr.length;
                if (i == length - 1) {
                    int i2 = length + 5;
                    LoopingFlowContext[] loopingFlowContextArr2 = new LoopingFlowContext[i2];
                    this.innerFlowContexts = loopingFlowContextArr2;
                    System.arraycopy(loopingFlowContextArr, 0, loopingFlowContextArr2, 0, length);
                    UnconditionalFlowInfo[] unconditionalFlowInfoArr = this.innerFlowInfos;
                    UnconditionalFlowInfo[] unconditionalFlowInfoArr2 = new UnconditionalFlowInfo[i2];
                    this.innerFlowInfos = unconditionalFlowInfoArr2;
                    System.arraycopy(unconditionalFlowInfoArr, 0, unconditionalFlowInfoArr2, 0, length);
                }
            }
            LoopingFlowContext[] loopingFlowContextArr3 = this.innerFlowContexts;
            int i3 = this.innerFlowContextsCount;
            loopingFlowContextArr3[i3] = (LoopingFlowContext) flowContext;
            UnconditionalFlowInfo[] unconditionalFlowInfoArr3 = this.innerFlowInfos;
            this.innerFlowContextsCount = i3 + 1;
            unconditionalFlowInfoArr3[i3] = flowInfo.unconditionalInitsWithoutSideEffect();
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    protected boolean recordFinalAssignment(VariableBinding variableBinding, Reference reference) {
        if (variableBinding instanceof LocalVariableBinding) {
            Scope scope = ((LocalVariableBinding) variableBinding).declaringScope;
            do {
                scope = scope.parent;
                if (scope == null) {
                }
            } while (scope != this.associatedScope);
            return false;
        }
        int i = this.assignCount;
        if (i == 0) {
            this.finalAssignments = new Reference[5];
            this.finalVariables = new VariableBinding[5];
        } else {
            Reference[] referenceArr = this.finalAssignments;
            if (i == referenceArr.length) {
                Reference[] referenceArr2 = new Reference[i * 2];
                this.finalAssignments = referenceArr2;
                System.arraycopy(referenceArr, 0, referenceArr2, 0, i);
            }
            VariableBinding[] variableBindingArr = this.finalVariables;
            int i2 = this.assignCount;
            VariableBinding[] variableBindingArr2 = new VariableBinding[i2 * 2];
            this.finalVariables = variableBindingArr2;
            System.arraycopy(variableBindingArr, 0, variableBindingArr2, 0, i2);
        }
        Reference[] referenceArr3 = this.finalAssignments;
        int i3 = this.assignCount;
        referenceArr3[i3] = reference;
        VariableBinding[] variableBindingArr3 = this.finalVariables;
        this.assignCount = i3 + 1;
        variableBindingArr3[i3] = variableBinding;
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    protected void recordNullReference(LocalVariableBinding localVariableBinding, ASTNode aSTNode, int i, FlowInfo flowInfo) {
        int i2 = this.nullCount;
        if (i2 == 0) {
            this.nullLocals = new LocalVariableBinding[5];
            this.nullReferences = new ASTNode[5];
            this.nullCheckTypes = new int[5];
            this.nullInfos = new UnconditionalFlowInfo[5];
        } else {
            LocalVariableBinding[] localVariableBindingArr = this.nullLocals;
            if (i2 == localVariableBindingArr.length) {
                LocalVariableBinding[] localVariableBindingArr2 = new LocalVariableBinding[i2 * 2];
                this.nullLocals = localVariableBindingArr2;
                System.arraycopy(localVariableBindingArr, 0, localVariableBindingArr2, 0, i2);
                ASTNode[] aSTNodeArr = this.nullReferences;
                int i3 = this.nullCount;
                ASTNode[] aSTNodeArr2 = new ASTNode[i3 * 2];
                this.nullReferences = aSTNodeArr2;
                System.arraycopy(aSTNodeArr, 0, aSTNodeArr2, 0, i3);
                int[] iArr = this.nullCheckTypes;
                int i4 = this.nullCount;
                int[] iArr2 = new int[i4 * 2];
                this.nullCheckTypes = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, i4);
                UnconditionalFlowInfo[] unconditionalFlowInfoArr = this.nullInfos;
                int i5 = this.nullCount;
                UnconditionalFlowInfo[] unconditionalFlowInfoArr2 = new UnconditionalFlowInfo[i5 * 2];
                this.nullInfos = unconditionalFlowInfoArr2;
                System.arraycopy(unconditionalFlowInfoArr, 0, unconditionalFlowInfoArr2, 0, i5);
            }
        }
        LocalVariableBinding[] localVariableBindingArr3 = this.nullLocals;
        int i6 = this.nullCount;
        localVariableBindingArr3[i6] = localVariableBinding;
        this.nullReferences[i6] = aSTNode;
        this.nullCheckTypes[i6] = i;
        UnconditionalFlowInfo[] unconditionalFlowInfoArr3 = this.nullInfos;
        this.nullCount = i6 + 1;
        unconditionalFlowInfoArr3[i6] = flowInfo != null ? flowInfo.unconditionalCopy() : null;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public void recordUnboxing(Scope scope, Expression expression, int i, FlowInfo flowInfo) {
        if (i == 2) {
            super.recordUnboxing(scope, expression, i, flowInfo);
        } else {
            recordNullReference(null, expression, 16, flowInfo);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public boolean recordExitAgainstResource(BlockScope blockScope, FlowInfo flowInfo, FakedTrackingVariable fakedTrackingVariable, ASTNode aSTNode) {
        LocalVariableBinding localVariableBinding = fakedTrackingVariable.binding;
        if (flowInfo.isDefinitelyNonNull(localVariableBinding)) {
            return false;
        }
        if (flowInfo.isDefinitelyNull(localVariableBinding)) {
            blockScope.problemReporter().unclosedCloseable(fakedTrackingVariable, aSTNode);
            return true;
        }
        if (flowInfo.isPotentiallyNull(localVariableBinding)) {
            blockScope.problemReporter().potentiallyUnclosedCloseable(fakedTrackingVariable, aSTNode);
            return true;
        }
        recordNullReference(fakedTrackingVariable.binding, aSTNode, 2048, flowInfo);
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public void recordUsingNullReference(Scope scope, LocalVariableBinding localVariableBinding, ASTNode aSTNode, int i, FlowInfo flowInfo) {
        if ((flowInfo.tagBits & 3) != 0 || flowInfo.isDefinitelyUnknown(localVariableBinding)) {
            return;
        }
        int i2 = i | (this.tagBits & 4096);
        int i3 = (-61441) & i2;
        if (i3 != 3) {
            if (i3 != 769 && i3 != 1025) {
                if (i3 != 256) {
                    if (i3 != 257) {
                        if (i3 != 512) {
                            if (i3 != 513) {
                                return;
                            }
                        }
                    }
                }
                ASTNode aSTNode2 = (Expression) aSTNode;
                if (flowInfo.isDefinitelyNonNull(localVariableBinding)) {
                    if (i3 == 512) {
                        if ((this.tagBits & 4096) == 0) {
                            scope.problemReporter().localVariableRedundantCheckOnNonNull(localVariableBinding, aSTNode2);
                        }
                        flowInfo.initsWhenFalse().setReachMode(2);
                        return;
                    } else {
                        scope.problemReporter().localVariableNonNullComparedToNull(localVariableBinding, aSTNode2);
                        flowInfo.initsWhenTrue().setReachMode(2);
                        return;
                    }
                }
                if (flowInfo.isDefinitelyNull(localVariableBinding)) {
                    if (i3 == 256) {
                        if ((this.tagBits & 4096) == 0) {
                            scope.problemReporter().localVariableRedundantCheckOnNull(localVariableBinding, aSTNode2);
                        }
                        flowInfo.initsWhenFalse().setReachMode(2);
                        return;
                    } else {
                        scope.problemReporter().localVariableNullComparedToNonNull(localVariableBinding, aSTNode2);
                        flowInfo.initsWhenTrue().setReachMode(2);
                        return;
                    }
                }
                if (this.upstreamNullFlowInfo.isDefinitelyNonNull(localVariableBinding) && !flowInfo.isPotentiallyNull(localVariableBinding) && !flowInfo.isPotentiallyUnknown(localVariableBinding)) {
                    recordNullReference(localVariableBinding, aSTNode2, i2, flowInfo);
                    flowInfo.markAsDefinitelyNonNull(localVariableBinding);
                    return;
                } else {
                    if (flowInfo.cannotBeDefinitelyNullOrNonNull(localVariableBinding)) {
                        return;
                    }
                    if (flowInfo.isPotentiallyNonNull(localVariableBinding)) {
                        recordNullReference(localVariableBinding, aSTNode2, (i2 & InputDeviceCompat.SOURCE_ANY) | 2, flowInfo);
                        return;
                    } else if (flowInfo.isPotentiallyNull(localVariableBinding)) {
                        recordNullReference(localVariableBinding, aSTNode2, (i2 & InputDeviceCompat.SOURCE_ANY) | 1, flowInfo);
                        return;
                    } else {
                        recordNullReference(localVariableBinding, aSTNode2, i2, flowInfo);
                        return;
                    }
                }
            }
            Expression expression = (Expression) aSTNode;
            if (flowInfo.isPotentiallyNonNull(localVariableBinding) || flowInfo.isPotentiallyUnknown(localVariableBinding) || flowInfo.isProtectedNonNull(localVariableBinding)) {
                return;
            }
            if (flowInfo.isDefinitelyNull(localVariableBinding)) {
                int i4 = i2 & FlowContext.CONTEXT_MASK;
                if (i4 == 256) {
                    if ((i2 & 255) == 1 && (expression.implicitConversion & 1024) != 0) {
                        scope.problemReporter().localVariableNullReference(localVariableBinding, expression);
                        return;
                    }
                    if ((this.tagBits & 4096) == 0) {
                        scope.problemReporter().localVariableRedundantCheckOnNull(localVariableBinding, expression);
                    }
                    flowInfo.initsWhenFalse().setReachMode(2);
                    return;
                }
                if (i4 == 512) {
                    if ((i2 & 255) == 1 && (expression.implicitConversion & 1024) != 0) {
                        scope.problemReporter().localVariableNullReference(localVariableBinding, expression);
                        return;
                    } else {
                        scope.problemReporter().localVariableNullComparedToNonNull(localVariableBinding, expression);
                        flowInfo.initsWhenTrue().setReachMode(2);
                        return;
                    }
                }
                if (i4 == 768) {
                    scope.problemReporter().localVariableRedundantNullAssignment(localVariableBinding, expression);
                    return;
                } else if (i4 == 1024) {
                    scope.problemReporter().localVariableNullInstanceof(localVariableBinding, expression);
                    return;
                }
            } else if (flowInfo.isPotentiallyNull(localVariableBinding)) {
                int i5 = i2 & FlowContext.CONTEXT_MASK;
                if (i5 == 256) {
                    if ((i2 & 255) == 1 && (expression.implicitConversion & 1024) != 0) {
                        scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, expression);
                        return;
                    }
                } else if (i5 == 512 && (i2 & 255) == 1 && (expression.implicitConversion & 1024) != 0) {
                    scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, expression);
                    return;
                }
            }
            recordNullReference(localVariableBinding, expression, i2, flowInfo);
            return;
        }
        if (flowInfo.isDefinitelyNonNull(localVariableBinding)) {
            return;
        }
        if (flowInfo.isDefinitelyNull(localVariableBinding)) {
            scope.problemReporter().localVariableNullReference(localVariableBinding, aSTNode);
        } else if (flowInfo.isPotentiallyNull(localVariableBinding)) {
            scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, aSTNode);
        } else {
            recordNullReference(localVariableBinding, aSTNode, i2, flowInfo);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    void removeFinalAssignmentIfAny(Reference reference) {
        for (int i = 0; i < this.assignCount; i++) {
            Reference[] referenceArr = this.finalAssignments;
            if (referenceArr[i] == reference) {
                referenceArr[i] = null;
                this.finalVariables[i] = null;
                return;
            }
        }
    }

    public void simulateThrowAfterLoopBack(FlowInfo flowInfo) {
        ArrayList arrayList = this.escapingExceptionCatchSites;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ((EscapingExceptionCatchSite) this.escapingExceptionCatchSites.get(i)).simulateThrowAfterLoopBack(flowInfo);
            }
            this.escapingExceptionCatchSites = null;
        }
    }

    public void recordCatchContextOfEscapingException(ExceptionHandlingFlowContext exceptionHandlingFlowContext, ReferenceBinding referenceBinding, FlowInfo flowInfo) {
        if (this.escapingExceptionCatchSites == null) {
            this.escapingExceptionCatchSites = new ArrayList(5);
        }
        this.escapingExceptionCatchSites.add(new EscapingExceptionCatchSite(exceptionHandlingFlowContext, referenceBinding, flowInfo));
    }

    public boolean hasEscapingExceptions() {
        return this.escapingExceptionCatchSites != null;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    protected boolean internalRecordNullityMismatch(Expression expression, TypeBinding typeBinding, FlowInfo flowInfo, int i, TypeBinding typeBinding2, int i2) {
        recordProvidedExpectedTypes(typeBinding, typeBinding2, this.nullCount);
        recordNullReference(expression.localVariableBinding(), expression, i2, flowInfo);
        return true;
    }
}
