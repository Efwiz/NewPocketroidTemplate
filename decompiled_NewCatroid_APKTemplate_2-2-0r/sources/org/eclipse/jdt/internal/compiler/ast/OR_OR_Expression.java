package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class OR_OR_Expression extends BinaryExpression {
    int mergedInitStateIndex;
    int rightInitStateIndex;

    @Override // org.eclipse.jdt.internal.compiler.ast.BinaryExpression, org.eclipse.jdt.internal.compiler.ast.Expression
    public boolean isCompactableOperation() {
        return false;
    }

    public OR_OR_Expression(Expression expression, Expression expression2, int i) {
        super(expression, expression2, i);
        this.rightInitStateIndex = -1;
        this.mergedInitStateIndex = -1;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.BinaryExpression, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        Constant constantOptimizedBooleanConstant = this.left.optimizedBooleanConstant();
        boolean z = constantOptimizedBooleanConstant != Constant.NotAConstant && constantOptimizedBooleanConstant.booleanValue();
        if (constantOptimizedBooleanConstant != Constant.NotAConstant && !constantOptimizedBooleanConstant.booleanValue()) {
            UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits = this.left.analyseCode(blockScope, flowContext, flowInfo).unconditionalInits();
            flowContext.expireNullCheckedFieldInfo();
            FlowInfo flowInfoAnalyseCode = this.right.analyseCode(blockScope, flowContext, unconditionalFlowInfoUnconditionalInits);
            flowContext.expireNullCheckedFieldInfo();
            this.mergedInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfoAnalyseCode);
            return flowInfoAnalyseCode;
        }
        FlowInfo flowInfoAnalyseCode2 = this.left.analyseCode(blockScope, flowContext, flowInfo);
        if ((flowContext.tagBits & 4) == 0) {
            flowContext.expireNullCheckedFieldInfo();
        }
        UnconditionalFlowInfo unconditionalFlowInfoUnconditionalCopy = flowInfoAnalyseCode2.initsWhenFalse().unconditionalCopy();
        this.rightInitStateIndex = blockScope.methodScope().recordInitializationStates(unconditionalFlowInfoUnconditionalCopy);
        int iReachMode = unconditionalFlowInfoUnconditionalCopy.reachMode();
        if (z && (unconditionalFlowInfoUnconditionalCopy.reachMode() & 3) == 0) {
            blockScope.problemReporter().fakeReachable(this.right);
            unconditionalFlowInfoUnconditionalCopy.setReachMode(1);
        }
        FlowInfo flowInfoAnalyseCode3 = this.right.analyseCode(blockScope, flowContext, unconditionalFlowInfoUnconditionalCopy);
        if ((flowContext.tagBits & 4) == 0) {
            flowContext.expireNullCheckedFieldInfo();
        }
        this.left.checkNPEbyUnboxing(blockScope, flowContext, flowInfo);
        this.right.checkNPEbyUnboxing(blockScope, flowContext, flowInfoAnalyseCode2.initsWhenFalse());
        FlowInfo flowInfoConditional = FlowInfo.conditional(flowInfoAnalyseCode2.initsWhenTrue().unconditionalCopy().addPotentialInitializationsFrom(flowInfoAnalyseCode3.unconditionalInitsWithoutSideEffect()).unconditionalInits().mergedWith(flowInfoAnalyseCode3.safeInitsWhenTrue().setReachMode(iReachMode).unconditionalInits()), flowInfoAnalyseCode3.initsWhenFalse());
        this.mergedInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfoConditional);
        return flowInfoConditional;
    }

    /* JADX WARN: Code duplicated, block: B:52:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:54:0x00c6  */
    /* JADX WARN: Code duplicated, block: B:57:0x00d7 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:58:0x00d9 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:60:0x00e8  */
    /* JADX WARN: Code duplicated, block: B:63:0x00f1  */
    /* JADX WARN: Code duplicated, block: B:65:0x00f7  */
    /* JADX WARN: Code duplicated, block: B:66:0x0106  */
    /* JADX WARN: Code duplicated, block: B:67:0x011b  */
    /* JADX WARN: Code duplicated, block: B:69:0x012b  */
    @Override // org.eclipse.jdt.internal.compiler.ast.BinaryExpression, org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i;
        int i2 = codeStream.position;
        if (this.constant != Constant.NotAConstant) {
            if (z) {
                codeStream.generateConstant(this.constant, this.implicitConversion);
            }
            codeStream.recordPositionsFrom(i2, this.sourceStart);
            return;
        }
        Constant constant = this.right.constant;
        if (constant != Constant.NotAConstant) {
            if (constant.booleanValue()) {
                this.left.generateCode(blockScope, codeStream, false);
                if (z) {
                    codeStream.iconst_1();
                }
            } else {
                this.left.generateCode(blockScope, codeStream, z);
            }
            int i3 = this.mergedInitStateIndex;
            if (i3 != -1) {
                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i3);
            }
            codeStream.generateImplicitConversion(this.implicitConversion);
            codeStream.recordPositionsFrom(i2, this.sourceStart);
            return;
        }
        BranchLabel branchLabel = new BranchLabel(codeStream);
        Constant constantOptimizedBooleanConstant = this.left.optimizedBooleanConstant();
        boolean z2 = constantOptimizedBooleanConstant != Constant.NotAConstant;
        boolean z3 = z2 && constantOptimizedBooleanConstant.booleanValue();
        Constant constantOptimizedBooleanConstant2 = this.right.optimizedBooleanConstant();
        boolean z4 = constantOptimizedBooleanConstant2 != Constant.NotAConstant;
        boolean z5 = z4 && constantOptimizedBooleanConstant2.booleanValue();
        if (z2) {
            this.left.generateCode(blockScope, codeStream, false);
            if (!z3) {
            }
            i = this.mergedInitStateIndex;
            if (i != -1) {
                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i);
            }
            if (z) {
                if (!z2 && z3) {
                    codeStream.iconst_1();
                    codeStream.recordPositionsFrom(codeStream.position, this.left.sourceEnd);
                } else {
                    if (!z4 && z5) {
                        codeStream.iconst_1();
                        codeStream.recordPositionsFrom(codeStream.position, this.left.sourceEnd);
                    } else {
                        codeStream.iconst_0();
                    }
                    if (branchLabel.forwardReferenceCount() > 0) {
                        if ((this.bits & 16) != 0) {
                            codeStream.generateImplicitConversion(this.implicitConversion);
                            codeStream.generateReturnBytecode(this);
                            branchLabel.place();
                            codeStream.iconst_1();
                        } else {
                            BranchLabel branchLabel2 = new BranchLabel(codeStream);
                            codeStream.goto_(branchLabel2);
                            codeStream.decrStackSize(1);
                            branchLabel.place();
                            codeStream.iconst_1();
                            branchLabel2.place();
                        }
                    } else {
                        branchLabel.place();
                    }
                }
                codeStream.generateImplicitConversion(this.implicitConversion);
                codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                return;
            }
            branchLabel.place();
        }
        this.left.generateOptimizedBoolean(blockScope, codeStream, branchLabel, null, true);
        int i4 = this.rightInitStateIndex;
        if (i4 != -1) {
            codeStream.addDefinitelyAssignedVariables(blockScope, i4);
        }
        if (z4) {
            this.right.generateCode(blockScope, codeStream, false);
        } else {
            this.right.generateOptimizedBoolean(blockScope, codeStream, branchLabel, null, z);
        }
        i = this.mergedInitStateIndex;
        if (i != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i);
        }
        if (z) {
            if (!z2) {
                if (!z4) {
                    codeStream.iconst_0();
                } else {
                    codeStream.iconst_0();
                }
                if (branchLabel.forwardReferenceCount() > 0) {
                    if ((this.bits & 16) != 0) {
                        codeStream.generateImplicitConversion(this.implicitConversion);
                        codeStream.generateReturnBytecode(this);
                        branchLabel.place();
                        codeStream.iconst_1();
                    } else {
                        BranchLabel branchLabel3 = new BranchLabel(codeStream);
                        codeStream.goto_(branchLabel3);
                        codeStream.decrStackSize(1);
                        branchLabel.place();
                        codeStream.iconst_1();
                        branchLabel3.place();
                    }
                } else {
                    branchLabel.place();
                }
            } else {
                if (!z4) {
                    codeStream.iconst_0();
                } else {
                    codeStream.iconst_0();
                }
                if (branchLabel.forwardReferenceCount() > 0) {
                    if ((this.bits & 16) != 0) {
                        codeStream.generateImplicitConversion(this.implicitConversion);
                        codeStream.generateReturnBytecode(this);
                        branchLabel.place();
                        codeStream.iconst_1();
                    } else {
                        BranchLabel branchLabel4 = new BranchLabel(codeStream);
                        codeStream.goto_(branchLabel4);
                        codeStream.decrStackSize(1);
                        branchLabel.place();
                        codeStream.iconst_1();
                        branchLabel4.place();
                    }
                } else {
                    branchLabel.place();
                }
            }
            codeStream.generateImplicitConversion(this.implicitConversion);
            codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            return;
        }
        branchLabel.place();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.BinaryExpression, org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateOptimizedBoolean(BlockScope blockScope, CodeStream codeStream, BranchLabel branchLabel, BranchLabel branchLabel2, boolean z) {
        if (this.constant != Constant.NotAConstant) {
            super.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, z);
            return;
        }
        Constant constant = this.right.constant;
        if (constant != Constant.NotAConstant && !constant.booleanValue()) {
            int i = codeStream.position;
            this.left.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, z);
            int i2 = this.mergedInitStateIndex;
            if (i2 != -1) {
                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i2);
            }
            codeStream.recordPositionsFrom(i, this.sourceStart);
            return;
        }
        Constant constantOptimizedBooleanConstant = this.left.optimizedBooleanConstant();
        boolean z2 = constantOptimizedBooleanConstant != Constant.NotAConstant;
        boolean z3 = z2 && constantOptimizedBooleanConstant.booleanValue();
        Constant constantOptimizedBooleanConstant2 = this.right.optimizedBooleanConstant();
        boolean z4 = constantOptimizedBooleanConstant2 != Constant.NotAConstant;
        boolean z5 = z4 && constantOptimizedBooleanConstant2.booleanValue();
        if (branchLabel2 == null) {
            if (branchLabel != null) {
                this.left.generateOptimizedBoolean(blockScope, codeStream, branchLabel, null, !z2);
                if (z3) {
                    if (z) {
                        codeStream.goto_(branchLabel);
                    }
                    codeStream.recordPositionsFrom(codeStream.position, this.left.sourceEnd);
                } else {
                    int i3 = this.rightInitStateIndex;
                    if (i3 != -1) {
                        codeStream.addDefinitelyAssignedVariables(blockScope, i3);
                    }
                    this.right.generateOptimizedBoolean(blockScope, codeStream, branchLabel, null, z && !z4);
                    if (z && z5) {
                        codeStream.goto_(branchLabel);
                        codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                    }
                }
            }
        } else if (branchLabel == null) {
            BranchLabel branchLabel3 = new BranchLabel(codeStream);
            this.left.generateOptimizedBoolean(blockScope, codeStream, branchLabel3, null, !z2);
            if (z3) {
                branchLabel3.place();
            } else {
                int i4 = this.rightInitStateIndex;
                if (i4 != -1) {
                    codeStream.addDefinitelyAssignedVariables(blockScope, i4);
                }
                this.right.generateOptimizedBoolean(blockScope, codeStream, null, branchLabel2, z && !z4);
                int i5 = codeStream.position;
                if (z && z4 && !z5) {
                    codeStream.goto_(branchLabel2);
                    codeStream.recordPositionsFrom(i5, this.sourceEnd);
                }
                branchLabel3.place();
            }
        }
        int i6 = this.mergedInitStateIndex;
        if (i6 != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i6);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.BinaryExpression, org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        TypeBinding typeBindingResolveType = super.resolveType(blockScope);
        Binding directBinding = Expression.getDirectBinding(this.left);
        if (directBinding != null && directBinding == Expression.getDirectBinding(this.right) && !(this.right instanceof Assignment)) {
            blockScope.problemReporter().comparingIdenticalExpressions(this);
        }
        return typeBindingResolveType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.BinaryExpression, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.left.traverse(aSTVisitor, blockScope);
            this.right.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }
}
