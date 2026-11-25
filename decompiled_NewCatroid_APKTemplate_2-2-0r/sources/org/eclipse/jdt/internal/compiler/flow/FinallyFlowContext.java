package org.eclipse.jdt.internal.compiler.flow;

import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.Reference;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.VariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class FinallyFlowContext extends TryFlowContext {
    int assignCount;
    Reference[] finalAssignments;
    VariableBinding[] finalVariables;
    int[] nullCheckTypes;
    int nullCount;
    LocalVariableBinding[] nullLocals;
    ASTNode[] nullReferences;
    public FlowContext tryContext;

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public boolean isSubRoutine() {
        return true;
    }

    public FinallyFlowContext(FlowContext flowContext, ASTNode aSTNode, ExceptionHandlingFlowContext exceptionHandlingFlowContext) {
        super(flowContext, aSTNode);
        this.tryContext = exceptionHandlingFlowContext;
    }

    /* JADX WARN: Code duplicated, block: B:116:0x0213 A[LOOP:1: B:114:0x0210->B:116:0x0213, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:130:0x01c8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:138:0x01c8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:140:0x01c8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:141:0x01c8 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:144:0x01c8 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:153:0x01c8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:154:0x01c8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:40:0x00b0  */
    /* JADX WARN: Code duplicated, block: B:42:0x00b6  */
    /* JADX WARN: Code duplicated, block: B:44:0x00be  */
    /* JADX WARN: Code duplicated, block: B:46:0x00c2  */
    /* JADX WARN: Code duplicated, block: B:47:0x00cb  */
    /* JADX WARN: Code duplicated, block: B:48:0x00d4  */
    /* JADX WARN: Code duplicated, block: B:50:0x00e1  */
    /* JADX WARN: Code duplicated, block: B:52:0x00e8 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:53:0x00ea  */
    /* JADX WARN: Code duplicated, block: B:55:0x00ee A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:57:0x00f2  */
    /* JADX WARN: Code duplicated, block: B:58:0x00fb  */
    /* JADX WARN: Code duplicated, block: B:59:0x0104  */
    /* JADX WARN: Code duplicated, block: B:61:0x0108  */
    /* JADX WARN: Code duplicated, block: B:64:0x0116  */
    /* JADX WARN: Code duplicated, block: B:65:0x011f  */
    /* JADX WARN: Code duplicated, block: B:67:0x0123  */
    /* JADX WARN: Code duplicated, block: B:70:0x0131  */
    /* JADX WARN: Code duplicated, block: B:72:0x0139  */
    /* JADX WARN: Code duplicated, block: B:73:0x0142  */
    /* JADX WARN: Code duplicated, block: B:75:0x0148  */
    /* JADX WARN: Code duplicated, block: B:77:0x0151 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:79:0x0155  */
    /* JADX WARN: Code duplicated, block: B:81:0x015d  */
    /* JADX WARN: Code duplicated, block: B:84:0x016a  */
    /* JADX WARN: Code duplicated, block: B:86:0x0172  */
    public void complainOnDeferredChecks(FlowInfo flowInfo, BlockScope blockScope) {
        Expression expression;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        FlowContext localParent;
        for (int i6 = 0; i6 < this.assignCount; i6++) {
            VariableBinding variableBinding = this.finalVariables[i6];
            if (variableBinding != null) {
                if (variableBinding instanceof FieldBinding) {
                    FieldBinding fieldBinding = (FieldBinding) variableBinding;
                    if (flowInfo.isPotentiallyAssigned(fieldBinding)) {
                        blockScope.problemReporter().duplicateInitializationOfBlankFinalField(fieldBinding, this.finalAssignments[i6]);
                        for (localParent = getLocalParent(); localParent != null; localParent = localParent.getLocalParent()) {
                            localParent.removeFinalAssignmentIfAny(this.finalAssignments[i6]);
                        }
                    }
                } else {
                    LocalVariableBinding localVariableBinding = (LocalVariableBinding) variableBinding;
                    if (flowInfo.isPotentiallyAssigned(localVariableBinding)) {
                        variableBinding.tagBits &= -2049;
                        if (variableBinding.isFinal()) {
                            blockScope.problemReporter().duplicateInitializationOfFinalLocal(localVariableBinding, this.finalAssignments[i6]);
                            while (localParent != null) {
                                localParent.removeFinalAssignmentIfAny(this.finalAssignments[i6]);
                            }
                        }
                    }
                }
            }
        }
        if ((this.tagBits & 1) != 0) {
            for (int i7 = 0; i7 < this.nullCount; i7++) {
                ASTNode aSTNode = this.nullReferences[i7];
                int i8 = this.nullCheckTypes[i7] & (-61441);
                if (i8 == 16) {
                    checkUnboxing(blockScope, (Expression) aSTNode, flowInfo);
                } else if (i8 == 128) {
                    int iNullStatus = flowInfo.nullStatus(this.nullLocals[i7]);
                    if (iNullStatus != 4) {
                        this.parent.recordNullityMismatch(blockScope, (Expression) aSTNode, this.providedExpectedTypes[i7][0], this.providedExpectedTypes[i7][1], flowInfo, iNullStatus, null);
                    }
                } else {
                    this.parent.recordUsingNullReference(blockScope, this.nullLocals[i7], this.nullReferences[i7], this.nullCheckTypes[i7], flowInfo);
                }
            }
            return;
        }
        for (int i9 = 0; i9 < this.nullCount; i9++) {
            ASTNode aSTNode2 = this.nullReferences[i9];
            LocalVariableBinding localVariableBinding2 = this.nullLocals[i9];
            int i10 = this.nullCheckTypes[i9] & (-61441);
            if (i10 != 3) {
                if (i10 == 16) {
                    checkUnboxing(blockScope, (Expression) aSTNode2, flowInfo);
                } else if (i10 == 128) {
                    int iNullStatus2 = flowInfo.nullStatus(localVariableBinding2);
                    if (iNullStatus2 != 4) {
                        blockScope.problemReporter().nullityMismatch((Expression) aSTNode2, this.providedExpectedTypes[i9][0], this.providedExpectedTypes[i9][1], iNullStatus2, blockScope.environment().getNonNullAnnotationName());
                    }
                } else if (i10 == 769 || i10 == 1025) {
                    expression = (Expression) aSTNode2;
                    if (flowInfo.isDefinitelyNull(localVariableBinding2)) {
                        i3 = this.nullCheckTypes[i9];
                        i4 = (-61696) & i3;
                        if (i4 != 256) {
                            if (i4 != 512) {
                                if (i4 == 768) {
                                    blockScope.problemReporter().localVariableRedundantNullAssignment(localVariableBinding2, expression);
                                } else if (i4 == 1024) {
                                    blockScope.problemReporter().localVariableNullInstanceof(localVariableBinding2, expression);
                                }
                            } else if ((i3 & 255) != 1 && (expression.implicitConversion & 1024) != 0) {
                                blockScope.problemReporter().localVariableNullReference(localVariableBinding2, expression);
                            } else {
                                blockScope.problemReporter().localVariableNullComparedToNonNull(localVariableBinding2, expression);
                            }
                        } else if ((i3 & 255) != 1 && (expression.implicitConversion & 1024) != 0) {
                            blockScope.problemReporter().localVariableNullReference(localVariableBinding2, expression);
                        } else if ((this.nullCheckTypes[i9] & 4096) == 0) {
                            blockScope.problemReporter().localVariableRedundantCheckOnNull(localVariableBinding2, expression);
                        }
                    } else if (flowInfo.isPotentiallyNull(localVariableBinding2)) {
                        i = this.nullCheckTypes[i9];
                        i2 = (-61696) & i;
                        if (i2 == 256) {
                            this.nullReferences[i9] = null;
                            if ((i & 255) != 1 && (expression.implicitConversion & 1024) != 0) {
                                blockScope.problemReporter().localVariablePotentialNullReference(localVariableBinding2, expression);
                            }
                        } else if (i2 == 512) {
                            this.nullReferences[i9] = null;
                            if ((i & 255) != 1 && (expression.implicitConversion & 1024) != 0) {
                                blockScope.problemReporter().localVariablePotentialNullReference(localVariableBinding2, expression);
                            }
                        }
                    }
                } else if (i10 == 256) {
                    if (flowInfo.isDefinitelyNonNull(localVariableBinding2)) {
                        i5 = this.nullCheckTypes[i9];
                        if ((i5 & (-61441)) == 512) {
                            blockScope.problemReporter().localVariableNonNullComparedToNull(localVariableBinding2, aSTNode2);
                        } else if ((i5 & 4096) == 0) {
                            blockScope.problemReporter().localVariableRedundantCheckOnNonNull(localVariableBinding2, aSTNode2);
                        }
                    } else {
                        expression = (Expression) aSTNode2;
                        if (flowInfo.isDefinitelyNull(localVariableBinding2)) {
                            i3 = this.nullCheckTypes[i9];
                            i4 = (-61696) & i3;
                            if (i4 != 256) {
                                if (i4 != 512) {
                                    if (i4 == 768) {
                                        blockScope.problemReporter().localVariableRedundantNullAssignment(localVariableBinding2, expression);
                                    } else if (i4 == 1024) {
                                        blockScope.problemReporter().localVariableNullInstanceof(localVariableBinding2, expression);
                                    }
                                } else if ((i3 & 255) != 1) {
                                    blockScope.problemReporter().localVariableNullComparedToNonNull(localVariableBinding2, expression);
                                } else {
                                    blockScope.problemReporter().localVariableNullComparedToNonNull(localVariableBinding2, expression);
                                }
                            } else if ((i3 & 255) != 1) {
                                if ((this.nullCheckTypes[i9] & 4096) == 0) {
                                    blockScope.problemReporter().localVariableRedundantCheckOnNull(localVariableBinding2, expression);
                                }
                            } else if ((this.nullCheckTypes[i9] & 4096) == 0) {
                                blockScope.problemReporter().localVariableRedundantCheckOnNull(localVariableBinding2, expression);
                            }
                        } else if (flowInfo.isPotentiallyNull(localVariableBinding2)) {
                            i = this.nullCheckTypes[i9];
                            i2 = (-61696) & i;
                            if (i2 == 256) {
                                this.nullReferences[i9] = null;
                                if ((i & 255) != 1) {
                                }
                            } else if (i2 == 512) {
                                this.nullReferences[i9] = null;
                                if ((i & 255) != 1) {
                                }
                            }
                        }
                    }
                } else if (i10 == 257) {
                    expression = (Expression) aSTNode2;
                    if (flowInfo.isDefinitelyNull(localVariableBinding2)) {
                        i3 = this.nullCheckTypes[i9];
                        i4 = (-61696) & i3;
                        if (i4 != 256) {
                            if (i4 != 512) {
                                if (i4 == 768) {
                                    blockScope.problemReporter().localVariableRedundantNullAssignment(localVariableBinding2, expression);
                                } else if (i4 == 1024) {
                                    blockScope.problemReporter().localVariableNullInstanceof(localVariableBinding2, expression);
                                }
                            } else if ((i3 & 255) != 1) {
                                blockScope.problemReporter().localVariableNullComparedToNonNull(localVariableBinding2, expression);
                            } else {
                                blockScope.problemReporter().localVariableNullComparedToNonNull(localVariableBinding2, expression);
                            }
                        } else if ((i3 & 255) != 1) {
                            if ((this.nullCheckTypes[i9] & 4096) == 0) {
                                blockScope.problemReporter().localVariableRedundantCheckOnNull(localVariableBinding2, expression);
                            }
                        } else if ((this.nullCheckTypes[i9] & 4096) == 0) {
                            blockScope.problemReporter().localVariableRedundantCheckOnNull(localVariableBinding2, expression);
                        }
                    } else if (flowInfo.isPotentiallyNull(localVariableBinding2)) {
                        i = this.nullCheckTypes[i9];
                        i2 = (-61696) & i;
                        if (i2 == 256) {
                            this.nullReferences[i9] = null;
                            if ((i & 255) != 1) {
                            }
                        } else if (i2 == 512) {
                            this.nullReferences[i9] = null;
                            if ((i & 255) != 1) {
                            }
                        }
                    }
                } else if (i10 != 512) {
                    if (i10 == 513) {
                        expression = (Expression) aSTNode2;
                        if (flowInfo.isDefinitelyNull(localVariableBinding2)) {
                            i3 = this.nullCheckTypes[i9];
                            i4 = (-61696) & i3;
                            if (i4 != 256) {
                                if (i4 != 512) {
                                    if (i4 == 768) {
                                        blockScope.problemReporter().localVariableRedundantNullAssignment(localVariableBinding2, expression);
                                    } else if (i4 == 1024) {
                                        blockScope.problemReporter().localVariableNullInstanceof(localVariableBinding2, expression);
                                    }
                                } else if ((i3 & 255) != 1) {
                                    blockScope.problemReporter().localVariableNullComparedToNonNull(localVariableBinding2, expression);
                                } else {
                                    blockScope.problemReporter().localVariableNullComparedToNonNull(localVariableBinding2, expression);
                                }
                            } else if ((i3 & 255) != 1) {
                                if ((this.nullCheckTypes[i9] & 4096) == 0) {
                                    blockScope.problemReporter().localVariableRedundantCheckOnNull(localVariableBinding2, expression);
                                }
                            } else if ((this.nullCheckTypes[i9] & 4096) == 0) {
                                blockScope.problemReporter().localVariableRedundantCheckOnNull(localVariableBinding2, expression);
                            }
                        } else if (flowInfo.isPotentiallyNull(localVariableBinding2)) {
                            i = this.nullCheckTypes[i9];
                            i2 = (-61696) & i;
                            if (i2 == 256) {
                                this.nullReferences[i9] = null;
                                if ((i & 255) != 1) {
                                }
                            } else if (i2 == 512) {
                                this.nullReferences[i9] = null;
                                if ((i & 255) != 1) {
                                }
                            }
                        }
                    }
                } else if (flowInfo.isDefinitelyNonNull(localVariableBinding2)) {
                    i5 = this.nullCheckTypes[i9];
                    if ((i5 & (-61441)) == 512) {
                        blockScope.problemReporter().localVariableNonNullComparedToNull(localVariableBinding2, aSTNode2);
                    } else if ((i5 & 4096) == 0) {
                        blockScope.problemReporter().localVariableRedundantCheckOnNonNull(localVariableBinding2, aSTNode2);
                    }
                } else {
                    expression = (Expression) aSTNode2;
                    if (flowInfo.isDefinitelyNull(localVariableBinding2)) {
                        i3 = this.nullCheckTypes[i9];
                        i4 = (-61696) & i3;
                        if (i4 != 256) {
                            if (i4 != 512) {
                                if (i4 == 768) {
                                    blockScope.problemReporter().localVariableRedundantNullAssignment(localVariableBinding2, expression);
                                } else if (i4 == 1024) {
                                    blockScope.problemReporter().localVariableNullInstanceof(localVariableBinding2, expression);
                                }
                            } else if ((i3 & 255) != 1) {
                                blockScope.problemReporter().localVariableNullComparedToNonNull(localVariableBinding2, expression);
                            } else {
                                blockScope.problemReporter().localVariableNullComparedToNonNull(localVariableBinding2, expression);
                            }
                        } else if ((i3 & 255) != 1) {
                            if ((this.nullCheckTypes[i9] & 4096) == 0) {
                                blockScope.problemReporter().localVariableRedundantCheckOnNull(localVariableBinding2, expression);
                            }
                        } else if ((this.nullCheckTypes[i9] & 4096) == 0) {
                            blockScope.problemReporter().localVariableRedundantCheckOnNull(localVariableBinding2, expression);
                        }
                    } else if (flowInfo.isPotentiallyNull(localVariableBinding2)) {
                        i = this.nullCheckTypes[i9];
                        i2 = (-61696) & i;
                        if (i2 == 256) {
                            this.nullReferences[i9] = null;
                            if ((i & 255) != 1) {
                            }
                        } else if (i2 == 512) {
                            this.nullReferences[i9] = null;
                            if ((i & 255) != 1) {
                            }
                        }
                    }
                }
            } else if (flowInfo.isDefinitelyNull(localVariableBinding2)) {
                blockScope.problemReporter().localVariableNullReference(localVariableBinding2, aSTNode2);
            } else if (flowInfo.isPotentiallyNull(localVariableBinding2)) {
                blockScope.problemReporter().localVariablePotentialNullReference(localVariableBinding2, aSTNode2);
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public String individualToString() {
        StringBuffer stringBuffer = new StringBuffer("Finally flow context[finalAssignments count - ");
        stringBuffer.append(this.assignCount).append("][nullReferences count - ");
        stringBuffer.append(this.nullCount).append(']');
        return stringBuffer.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    protected boolean recordFinalAssignment(VariableBinding variableBinding, Reference reference) {
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

    /* JADX WARN: Code duplicated, block: B:100:0x0161  */
    /* JADX WARN: Code duplicated, block: B:102:0x016a  */
    /* JADX WARN: Code duplicated, block: B:104:0x0170 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:105:0x0172  */
    /* JADX WARN: Code duplicated, block: B:107:0x0176 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:109:0x017a  */
    /* JADX WARN: Code duplicated, block: B:111:0x0182  */
    /* JADX WARN: Code duplicated, block: B:113:0x018a  */
    /* JADX WARN: Code duplicated, block: B:115:0x018f  */
    /* JADX WARN: Code duplicated, block: B:121:0x01ab  */
    /* JADX WARN: Code duplicated, block: B:123:0x01b0  */
    /* JADX WARN: Code duplicated, block: B:129:0x01c1  */
    /* JADX WARN: Code duplicated, block: B:132:0x01d0  */
    /* JADX WARN: Code duplicated, block: B:134:0x01d6  */
    /* JADX WARN: Code duplicated, block: B:136:0x01dc  */
    /* JADX WARN: Code duplicated, block: B:138:0x01df  */
    /* JADX WARN: Code duplicated, block: B:144:0x01f1  */
    /* JADX WARN: Code duplicated, block: B:146:0x01f6  */
    /* JADX WARN: Code duplicated, block: B:91:0x0137  */
    /* JADX WARN: Code duplicated, block: B:93:0x013d A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:94:0x013f  */
    /* JADX WARN: Code duplicated, block: B:96:0x0143  */
    /* JADX WARN: Code duplicated, block: B:98:0x0152  */
    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public void recordUsingNullReference(Scope scope, LocalVariableBinding localVariableBinding, ASTNode aSTNode, int i, FlowInfo flowInfo) {
        Expression expression;
        int i2;
        int i3;
        if ((flowInfo.tagBits & 3) != 0 || flowInfo.isDefinitelyUnknown(localVariableBinding)) {
            return;
        }
        int i4 = i | (this.tagBits & 4096);
        int i5 = (-61441) & i4;
        if ((this.tagBits & 1) != 0) {
            if (i5 != 3) {
                if (i5 == 769 || i5 == 1025 || i5 == 256 || i5 == 257 || i5 == 512 || i5 == 513) {
                    Expression expression2 = (Expression) aSTNode;
                    if (flowInfo.cannotBeNull(localVariableBinding)) {
                        if (i5 == 512) {
                            if ((i4 & 4096) == 0) {
                                scope.problemReporter().localVariableRedundantCheckOnNonNull(localVariableBinding, expression2);
                            }
                            flowInfo.initsWhenFalse().setReachMode(2);
                            return;
                        } else {
                            if (i5 == 256) {
                                scope.problemReporter().localVariableNonNullComparedToNull(localVariableBinding, expression2);
                                flowInfo.initsWhenTrue().setReachMode(2);
                                return;
                            }
                            return;
                        }
                    }
                    if (flowInfo.canOnlyBeNull(localVariableBinding)) {
                        int i6 = (-61696) & i4;
                        if (i6 == 256) {
                            if ((i4 & 255) == 1 && (expression2.implicitConversion & 1024) != 0) {
                                scope.problemReporter().localVariableNullReference(localVariableBinding, expression2);
                                return;
                            }
                            if ((i4 & 4096) == 0) {
                                scope.problemReporter().localVariableRedundantCheckOnNull(localVariableBinding, expression2);
                            }
                            flowInfo.initsWhenFalse().setReachMode(2);
                            return;
                        }
                        if (i6 == 512) {
                            if ((i4 & 255) == 1 && (expression2.implicitConversion & 1024) != 0) {
                                scope.problemReporter().localVariableNullReference(localVariableBinding, expression2);
                                return;
                            } else {
                                scope.problemReporter().localVariableNullComparedToNonNull(localVariableBinding, expression2);
                                flowInfo.initsWhenTrue().setReachMode(2);
                                return;
                            }
                        }
                        if (i6 == 768) {
                            scope.problemReporter().localVariableRedundantNullAssignment(localVariableBinding, expression2);
                            return;
                        } else if (i6 == 1024) {
                            scope.problemReporter().localVariableNullInstanceof(localVariableBinding, expression2);
                            return;
                        }
                    } else if (flowInfo.isPotentiallyNull(localVariableBinding)) {
                        int i7 = (-61696) & i4;
                        if (i7 == 256) {
                            if ((i4 & 255) == 1 && (expression2.implicitConversion & 1024) != 0) {
                                scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, expression2);
                                return;
                            }
                        } else if (i7 == 512 && (i4 & 255) == 1 && (expression2.implicitConversion & 1024) != 0) {
                            scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, expression2);
                            return;
                        }
                    }
                }
            } else {
                if (flowInfo.cannotBeNull(localVariableBinding)) {
                    return;
                }
                if (flowInfo.canOnlyBeNull(localVariableBinding)) {
                    scope.problemReporter().localVariableNullReference(localVariableBinding, aSTNode);
                    return;
                }
            }
        } else if (i5 != 3) {
            if (i5 == 769 || i5 == 1025) {
                expression = (Expression) aSTNode;
                if (flowInfo.isDefinitelyNull(localVariableBinding)) {
                    i3 = (-61696) & i4;
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
                    i2 = (-61696) & i4;
                    if (i2 == 256) {
                        if ((i4 & 255) == 1 && (expression.implicitConversion & 1024) != 0) {
                            scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, expression);
                            return;
                        }
                    } else if (i2 == 512 && (i4 & 255) == 1 && (expression.implicitConversion & 1024) != 0) {
                        scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, expression);
                        return;
                    }
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
                expression = (Expression) aSTNode;
                if (flowInfo.isDefinitelyNull(localVariableBinding)) {
                    i3 = (-61696) & i4;
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
                    i2 = (-61696) & i4;
                    if (i2 == 256) {
                        if (i2 == 512) {
                            scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, expression);
                            return;
                        }
                    } else if ((i4 & 255) == 1) {
                        scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, expression);
                        return;
                    }
                }
            } else if (i5 == 257) {
                expression = (Expression) aSTNode;
                if (flowInfo.isDefinitelyNull(localVariableBinding)) {
                    i3 = (-61696) & i4;
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
                    i2 = (-61696) & i4;
                    if (i2 == 256) {
                        if (i2 == 512) {
                            scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, expression);
                            return;
                        }
                    } else if ((i4 & 255) == 1) {
                        scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, expression);
                        return;
                    }
                }
            } else {
                if (i5 != 512) {
                    if (i5 == 513) {
                    }
                } else if (flowInfo.isDefinitelyNonNull(localVariableBinding)) {
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
                expression = (Expression) aSTNode;
                if (flowInfo.isDefinitelyNull(localVariableBinding)) {
                    i3 = (-61696) & i4;
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
                    i2 = (-61696) & i4;
                    if (i2 == 256) {
                        if (i2 == 512) {
                            scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, expression);
                            return;
                        }
                    } else if ((i4 & 255) == 1) {
                        scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, expression);
                        return;
                    }
                }
            }
        } else if (flowInfo.isDefinitelyNull(localVariableBinding)) {
            scope.problemReporter().localVariableNullReference(localVariableBinding, aSTNode);
            return;
        } else if (flowInfo.isPotentiallyNull(localVariableBinding)) {
            scope.problemReporter().localVariablePotentialNullReference(localVariableBinding, aSTNode);
            return;
        } else if (flowInfo.isDefinitelyNonNull(localVariableBinding)) {
            return;
        }
        recordNullReference(localVariableBinding, aSTNode, i4, flowInfo);
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

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    protected void recordNullReference(LocalVariableBinding localVariableBinding, ASTNode aSTNode, int i, FlowInfo flowInfo) {
        int i2 = this.nullCount;
        if (i2 == 0) {
            this.nullLocals = new LocalVariableBinding[5];
            this.nullReferences = new ASTNode[5];
            this.nullCheckTypes = new int[5];
        } else {
            LocalVariableBinding[] localVariableBindingArr = this.nullLocals;
            if (i2 == localVariableBindingArr.length) {
                int i3 = i2 * 2;
                LocalVariableBinding[] localVariableBindingArr2 = new LocalVariableBinding[i3];
                this.nullLocals = localVariableBindingArr2;
                System.arraycopy(localVariableBindingArr, 0, localVariableBindingArr2, 0, i2);
                ASTNode[] aSTNodeArr = this.nullReferences;
                ASTNode[] aSTNodeArr2 = new ASTNode[i3];
                this.nullReferences = aSTNodeArr2;
                System.arraycopy(aSTNodeArr, 0, aSTNodeArr2, 0, this.nullCount);
                int[] iArr = this.nullCheckTypes;
                int[] iArr2 = new int[i3];
                this.nullCheckTypes = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, this.nullCount);
            }
        }
        LocalVariableBinding[] localVariableBindingArr3 = this.nullLocals;
        int i4 = this.nullCount;
        localVariableBindingArr3[i4] = localVariableBinding;
        this.nullReferences[i4] = aSTNode;
        int[] iArr3 = this.nullCheckTypes;
        this.nullCount = i4 + 1;
        iArr3[i4] = i;
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
    protected boolean internalRecordNullityMismatch(Expression expression, TypeBinding typeBinding, FlowInfo flowInfo, int i, TypeBinding typeBinding2, int i2) {
        if (i != 1 && ((this.tagBits & 1) == 0 || i == 2)) {
            return false;
        }
        recordProvidedExpectedTypes(typeBinding, typeBinding2, this.nullCount);
        recordNullReference(expression.localVariableBinding(), expression, i2, flowInfo);
        return true;
    }
}
