package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.BooleanConstant;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BaseTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class EqualExpression extends BinaryExpression {
    @Override // org.eclipse.jdt.internal.compiler.ast.BinaryExpression, org.eclipse.jdt.internal.compiler.ast.Expression
    public boolean isCompactableOperation() {
        return false;
    }

    public EqualExpression(Expression expression, Expression expression2, int i) {
        super(expression, expression2, i);
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0050  */
    /* JADX WARN: Code duplicated, block: B:38:0x00bf  */
    private void checkNullComparison(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, FlowInfo flowInfo2, FlowInfo flowInfo3) {
        boolean zExpressionNonNullComparison;
        boolean zExpressionNonNullComparison2;
        int i;
        FieldBinding fieldBindingLastFieldBinding;
        FieldBinding fieldBindingLastFieldBinding2;
        int iNullStatus = this.right.nullStatus(flowInfo, flowContext);
        int iNullStatus2 = this.left.nullStatus(flowInfo, flowContext);
        boolean z = ((this.bits & ASTNode.OperatorMASK) >> 6) == 18;
        if ((flowContext.tagBits & 61440) != 0) {
            zExpressionNonNullComparison = false;
            zExpressionNonNullComparison2 = false;
        } else if (iNullStatus2 == 4 && iNullStatus == 2) {
            zExpressionNonNullComparison = blockScope.problemReporter().expressionNonNullComparison(this.left, z);
            zExpressionNonNullComparison2 = false;
        } else if (iNullStatus2 == 2 && iNullStatus == 4) {
            zExpressionNonNullComparison2 = blockScope.problemReporter().expressionNonNullComparison(this.right, z);
            zExpressionNonNullComparison = false;
        } else {
            zExpressionNonNullComparison = false;
            zExpressionNonNullComparison2 = false;
        }
        boolean z2 = z ^ ((flowContext.tagBits & 4) != 0);
        if (zExpressionNonNullComparison) {
            i = 2;
        } else {
            LocalVariableBinding localVariableBinding = this.left.localVariableBinding();
            if (localVariableBinding == null) {
                i = 2;
                if ((this.left instanceof Reference) && (((!z2 && iNullStatus == 2) || (z2 && iNullStatus == 4)) && blockScope.compilerOptions().enableSyntacticNullAnalysisForFields && (fieldBindingLastFieldBinding2 = ((Reference) this.left).lastFieldBinding()) != null && (fieldBindingLastFieldBinding2.type.tagBits & 2) == 0)) {
                    flowContext.recordNullCheckedFieldReference((Reference) this.left, 1);
                }
            } else if ((localVariableBinding.type.tagBits & 2) == 0) {
                i = 2;
                checkVariableComparison(blockScope, flowContext, flowInfo, flowInfo2, flowInfo3, localVariableBinding, iNullStatus, this.left);
            } else {
                i = 2;
            }
        }
        if (!zExpressionNonNullComparison2) {
            LocalVariableBinding localVariableBinding2 = this.right.localVariableBinding();
            if (localVariableBinding2 != null) {
                if ((localVariableBinding2.type.tagBits & 2) == 0) {
                    checkVariableComparison(blockScope, flowContext, flowInfo, flowInfo2, flowInfo3, localVariableBinding2, iNullStatus2, this.right);
                }
            } else if ((this.right instanceof Reference) && (((!z2 && iNullStatus2 == i) || (z2 && iNullStatus2 == 4)) && blockScope.compilerOptions().enableSyntacticNullAnalysisForFields && (fieldBindingLastFieldBinding = ((Reference) this.right).lastFieldBinding()) != null && (fieldBindingLastFieldBinding.type.tagBits & 2) == 0)) {
                flowContext.recordNullCheckedFieldReference((Reference) this.right, 1);
            }
        }
        if (zExpressionNonNullComparison || zExpressionNonNullComparison2) {
            if (z) {
                flowInfo2.setReachMode(i);
            } else {
                flowInfo3.setReachMode(i);
            }
        }
    }

    private void checkVariableComparison(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, FlowInfo flowInfo2, FlowInfo flowInfo3, LocalVariableBinding localVariableBinding, int i, Expression expression) {
        if (i != 2) {
            if (i != 4) {
                return;
            }
            if (((this.bits & ASTNode.OperatorMASK) >> 6) == 18) {
                flowContext.recordUsingNullReference(blockScope, localVariableBinding, expression, 513, flowInfo);
                flowInfo2.markAsComparedEqualToNonNull(localVariableBinding);
                return;
            } else {
                flowContext.recordUsingNullReference(blockScope, localVariableBinding, expression, 257, flowInfo);
                return;
            }
        }
        if (((this.bits & ASTNode.OperatorMASK) >> 6) == 18) {
            flowContext.recordUsingNullReference(blockScope, localVariableBinding, expression, 256, flowInfo);
            flowInfo2.markAsComparedEqualToNull(localVariableBinding);
            flowInfo3.markAsComparedEqualToNonNull(localVariableBinding);
        } else {
            flowContext.recordUsingNullReference(blockScope, localVariableBinding, expression, 512, flowInfo);
            flowInfo2.markAsComparedEqualToNonNull(localVariableBinding);
            flowInfo3.markAsComparedEqualToNull(localVariableBinding);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.BinaryExpression, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        FlowInfo flowInfoUnconditionalInits;
        if (((this.bits & ASTNode.OperatorMASK) >> 6) == 18) {
            if (this.left.constant != Constant.NotAConstant && this.left.constant.typeID() == 5) {
                if (this.left.constant.booleanValue()) {
                    flowInfoUnconditionalInits = this.right.analyseCode(blockScope, flowContext, flowInfo);
                } else {
                    flowInfoUnconditionalInits = this.right.analyseCode(blockScope, flowContext, flowInfo).asNegatedCondition();
                }
            } else if (this.right.constant != Constant.NotAConstant && this.right.constant.typeID() == 5) {
                if (this.right.constant.booleanValue()) {
                    flowInfoUnconditionalInits = this.left.analyseCode(blockScope, flowContext, flowInfo);
                } else {
                    flowInfoUnconditionalInits = this.left.analyseCode(blockScope, flowContext, flowInfo).asNegatedCondition();
                }
            } else {
                flowInfoUnconditionalInits = this.right.analyseCode(blockScope, flowContext, this.left.analyseCode(blockScope, flowContext, flowInfo).unconditionalInits()).unconditionalInits();
            }
        } else if (this.left.constant != Constant.NotAConstant && this.left.constant.typeID() == 5) {
            if (!this.left.constant.booleanValue()) {
                flowInfoUnconditionalInits = this.right.analyseCode(blockScope, flowContext, flowInfo);
            } else {
                flowInfoUnconditionalInits = this.right.analyseCode(blockScope, flowContext, flowInfo).asNegatedCondition();
            }
        } else if (this.right.constant != Constant.NotAConstant && this.right.constant.typeID() == 5) {
            if (!this.right.constant.booleanValue()) {
                flowInfoUnconditionalInits = this.left.analyseCode(blockScope, flowContext, flowInfo);
            } else {
                flowInfoUnconditionalInits = this.left.analyseCode(blockScope, flowContext, flowInfo).asNegatedCondition();
            }
        } else {
            flowInfoUnconditionalInits = this.right.analyseCode(blockScope, flowContext, this.left.analyseCode(blockScope, flowContext, flowInfo).unconditionalInits()).unconditionalInits();
        }
        if ((flowInfoUnconditionalInits instanceof UnconditionalFlowInfo) && (flowInfoUnconditionalInits.tagBits & 3) == 0) {
            flowInfoUnconditionalInits = FlowInfo.conditional(flowInfoUnconditionalInits.copy(), flowInfoUnconditionalInits.copy());
        }
        checkNullComparison(blockScope, flowContext, flowInfoUnconditionalInits, flowInfoUnconditionalInits.initsWhenTrue(), flowInfoUnconditionalInits.initsWhenFalse());
        return flowInfoUnconditionalInits;
    }

    public final void computeConstant(TypeBinding typeBinding, TypeBinding typeBinding2) {
        if (this.left.constant != Constant.NotAConstant && this.right.constant != Constant.NotAConstant) {
            this.constant = Constant.computeConstantOperationEQUAL_EQUAL(this.left.constant, typeBinding.f626id, this.right.constant, typeBinding2.f626id);
            if (((this.bits & ASTNode.OperatorMASK) >> 6) == 29) {
                this.constant = BooleanConstant.fromValue(!this.constant.booleanValue());
                return;
            }
            return;
        }
        this.constant = Constant.NotAConstant;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.BinaryExpression, org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i = codeStream.position;
        if (this.constant != Constant.NotAConstant) {
            if (z) {
                codeStream.generateConstant(this.constant, this.implicitConversion);
            }
            codeStream.recordPositionsFrom(i, this.sourceStart);
        } else {
            if ((this.left.implicitConversion & 15) == 5) {
                generateBooleanEqual(blockScope, codeStream, z);
            } else {
                generateNonBooleanEqual(blockScope, codeStream, z);
            }
            if (z) {
                codeStream.generateImplicitConversion(this.implicitConversion);
            }
            codeStream.recordPositionsFrom(i, this.sourceStart);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.BinaryExpression, org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateOptimizedBoolean(BlockScope blockScope, CodeStream codeStream, BranchLabel branchLabel, BranchLabel branchLabel2, boolean z) {
        if (this.constant != Constant.NotAConstant) {
            super.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, z);
            return;
        }
        if (((this.bits & ASTNode.OperatorMASK) >> 6) == 18) {
            if ((this.left.implicitConversion & 15) == 5) {
                generateOptimizedBooleanEqual(blockScope, codeStream, branchLabel, branchLabel2, z);
                return;
            } else {
                generateOptimizedNonBooleanEqual(blockScope, codeStream, branchLabel, branchLabel2, z);
                return;
            }
        }
        if ((this.left.implicitConversion & 15) == 5) {
            generateOptimizedBooleanEqual(blockScope, codeStream, branchLabel2, branchLabel, z);
        } else {
            generateOptimizedNonBooleanEqual(blockScope, codeStream, branchLabel2, branchLabel, z);
        }
    }

    public void generateBooleanEqual(BlockScope blockScope, CodeStream codeStream, boolean z) {
        boolean z2 = ((this.bits & ASTNode.OperatorMASK) >> 6) == 18;
        Constant constantOptimizedBooleanConstant = this.left.optimizedBooleanConstant();
        if (constantOptimizedBooleanConstant != Constant.NotAConstant) {
            Constant constantOptimizedBooleanConstant2 = this.right.optimizedBooleanConstant();
            if (constantOptimizedBooleanConstant2 != Constant.NotAConstant) {
                this.left.generateCode(blockScope, codeStream, false);
                this.right.generateCode(blockScope, codeStream, false);
                if (z) {
                    boolean zBooleanValue = constantOptimizedBooleanConstant.booleanValue();
                    boolean zBooleanValue2 = constantOptimizedBooleanConstant2.booleanValue();
                    if (z2) {
                        if (zBooleanValue == zBooleanValue2) {
                            codeStream.iconst_1();
                            return;
                        } else {
                            codeStream.iconst_0();
                            return;
                        }
                    }
                    if (zBooleanValue != zBooleanValue2) {
                        codeStream.iconst_1();
                        return;
                    } else {
                        codeStream.iconst_0();
                        return;
                    }
                }
                return;
            }
            if (constantOptimizedBooleanConstant.booleanValue() == z2) {
                this.left.generateCode(blockScope, codeStream, false);
                this.right.generateCode(blockScope, codeStream, z);
                return;
            }
            if (z) {
                BranchLabel branchLabel = new BranchLabel(codeStream);
                this.left.generateCode(blockScope, codeStream, false);
                this.right.generateOptimizedBoolean(blockScope, codeStream, null, branchLabel, z);
                codeStream.iconst_0();
                if ((this.bits & 16) != 0) {
                    codeStream.generateImplicitConversion(this.implicitConversion);
                    codeStream.generateReturnBytecode(this);
                    branchLabel.place();
                    codeStream.iconst_1();
                    return;
                }
                BranchLabel branchLabel2 = new BranchLabel(codeStream);
                codeStream.goto_(branchLabel2);
                codeStream.decrStackSize(1);
                branchLabel.place();
                codeStream.iconst_1();
                branchLabel2.place();
                return;
            }
            this.left.generateCode(blockScope, codeStream, false);
            this.right.generateCode(blockScope, codeStream, false);
            return;
        }
        Constant constantOptimizedBooleanConstant3 = this.right.optimizedBooleanConstant();
        if (constantOptimizedBooleanConstant3 != Constant.NotAConstant) {
            if (constantOptimizedBooleanConstant3.booleanValue() == z2) {
                this.left.generateCode(blockScope, codeStream, z);
                this.right.generateCode(blockScope, codeStream, false);
                return;
            }
            if (z) {
                BranchLabel branchLabel3 = new BranchLabel(codeStream);
                this.left.generateOptimizedBoolean(blockScope, codeStream, null, branchLabel3, z);
                this.right.generateCode(blockScope, codeStream, false);
                codeStream.iconst_0();
                if ((this.bits & 16) != 0) {
                    codeStream.generateImplicitConversion(this.implicitConversion);
                    codeStream.generateReturnBytecode(this);
                    branchLabel3.place();
                    codeStream.iconst_1();
                    return;
                }
                BranchLabel branchLabel4 = new BranchLabel(codeStream);
                codeStream.goto_(branchLabel4);
                codeStream.decrStackSize(1);
                branchLabel3.place();
                codeStream.iconst_1();
                branchLabel4.place();
                return;
            }
            this.left.generateCode(blockScope, codeStream, false);
            this.right.generateCode(blockScope, codeStream, false);
            return;
        }
        this.left.generateCode(blockScope, codeStream, z);
        this.right.generateCode(blockScope, codeStream, z);
        if (z) {
            if (z2) {
                BranchLabel branchLabel5 = new BranchLabel(codeStream);
                codeStream.if_icmpne(branchLabel5);
                codeStream.iconst_1();
                if ((this.bits & 16) != 0) {
                    codeStream.generateImplicitConversion(this.implicitConversion);
                    codeStream.generateReturnBytecode(this);
                    branchLabel5.place();
                    codeStream.iconst_0();
                    return;
                }
                BranchLabel branchLabel6 = new BranchLabel(codeStream);
                codeStream.goto_(branchLabel6);
                codeStream.decrStackSize(1);
                branchLabel5.place();
                codeStream.iconst_0();
                branchLabel6.place();
                return;
            }
            codeStream.ixor();
        }
    }

    public void generateOptimizedBooleanEqual(BlockScope blockScope, CodeStream codeStream, BranchLabel branchLabel, BranchLabel branchLabel2, boolean z) {
        if (this.left.constant != Constant.NotAConstant) {
            boolean zBooleanValue = this.left.constant.booleanValue();
            this.right.generateOptimizedBoolean(blockScope, codeStream, zBooleanValue ? branchLabel : branchLabel2, zBooleanValue ? branchLabel2 : branchLabel, z);
            return;
        }
        if (this.right.constant != Constant.NotAConstant) {
            boolean zBooleanValue2 = this.right.constant.booleanValue();
            this.left.generateOptimizedBoolean(blockScope, codeStream, zBooleanValue2 ? branchLabel : branchLabel2, zBooleanValue2 ? branchLabel2 : branchLabel, z);
            return;
        }
        this.left.generateCode(blockScope, codeStream, z);
        this.right.generateCode(blockScope, codeStream, z);
        int i = codeStream.position;
        if (z) {
            if (branchLabel2 == null) {
                if (branchLabel != null) {
                    codeStream.if_icmpeq(branchLabel);
                }
            } else if (branchLabel == null) {
                codeStream.if_icmpne(branchLabel2);
            }
        }
        codeStream.recordPositionsFrom(i, this.sourceEnd);
    }

    public void generateNonBooleanEqual(BlockScope blockScope, CodeStream codeStream, boolean z) {
        boolean z2 = ((this.bits & ASTNode.OperatorMASK) >> 6) == 18;
        if (((this.left.implicitConversion & 255) >> 4) == 10) {
            Constant constant = this.left.constant;
            if (constant != Constant.NotAConstant && constant.intValue() == 0) {
                this.right.generateCode(blockScope, codeStream, z);
                if (z) {
                    BranchLabel branchLabel = new BranchLabel(codeStream);
                    if (z2) {
                        codeStream.ifne(branchLabel);
                    } else {
                        codeStream.ifeq(branchLabel);
                    }
                    codeStream.iconst_1();
                    if ((this.bits & 16) != 0) {
                        codeStream.generateImplicitConversion(this.implicitConversion);
                        codeStream.generateReturnBytecode(this);
                        branchLabel.place();
                        codeStream.iconst_0();
                        return;
                    }
                    BranchLabel branchLabel2 = new BranchLabel(codeStream);
                    codeStream.goto_(branchLabel2);
                    codeStream.decrStackSize(1);
                    branchLabel.place();
                    codeStream.iconst_0();
                    branchLabel2.place();
                    return;
                }
                return;
            }
            Constant constant2 = this.right.constant;
            if (constant2 != Constant.NotAConstant && constant2.intValue() == 0) {
                this.left.generateCode(blockScope, codeStream, z);
                if (z) {
                    BranchLabel branchLabel3 = new BranchLabel(codeStream);
                    if (z2) {
                        codeStream.ifne(branchLabel3);
                    } else {
                        codeStream.ifeq(branchLabel3);
                    }
                    codeStream.iconst_1();
                    if ((this.bits & 16) != 0) {
                        codeStream.generateImplicitConversion(this.implicitConversion);
                        codeStream.generateReturnBytecode(this);
                        branchLabel3.place();
                        codeStream.iconst_0();
                        return;
                    }
                    BranchLabel branchLabel4 = new BranchLabel(codeStream);
                    codeStream.goto_(branchLabel4);
                    codeStream.decrStackSize(1);
                    branchLabel3.place();
                    codeStream.iconst_0();
                    branchLabel4.place();
                    return;
                }
                return;
            }
        }
        if (this.right instanceof NullLiteral) {
            if (this.left instanceof NullLiteral) {
                if (z) {
                    if (z2) {
                        codeStream.iconst_1();
                        return;
                    } else {
                        codeStream.iconst_0();
                        return;
                    }
                }
                return;
            }
            this.left.generateCode(blockScope, codeStream, z);
            if (z) {
                BranchLabel branchLabel5 = new BranchLabel(codeStream);
                if (z2) {
                    codeStream.ifnonnull(branchLabel5);
                } else {
                    codeStream.ifnull(branchLabel5);
                }
                codeStream.iconst_1();
                if ((this.bits & 16) != 0) {
                    codeStream.generateImplicitConversion(this.implicitConversion);
                    codeStream.generateReturnBytecode(this);
                    branchLabel5.place();
                    codeStream.iconst_0();
                    return;
                }
                BranchLabel branchLabel6 = new BranchLabel(codeStream);
                codeStream.goto_(branchLabel6);
                codeStream.decrStackSize(1);
                branchLabel5.place();
                codeStream.iconst_0();
                branchLabel6.place();
                return;
            }
            return;
        }
        if (this.left instanceof NullLiteral) {
            this.right.generateCode(blockScope, codeStream, z);
            if (z) {
                BranchLabel branchLabel7 = new BranchLabel(codeStream);
                if (z2) {
                    codeStream.ifnonnull(branchLabel7);
                } else {
                    codeStream.ifnull(branchLabel7);
                }
                codeStream.iconst_1();
                if ((this.bits & 16) != 0) {
                    codeStream.generateImplicitConversion(this.implicitConversion);
                    codeStream.generateReturnBytecode(this);
                    branchLabel7.place();
                    codeStream.iconst_0();
                    return;
                }
                BranchLabel branchLabel8 = new BranchLabel(codeStream);
                codeStream.goto_(branchLabel8);
                codeStream.decrStackSize(1);
                branchLabel7.place();
                codeStream.iconst_0();
                branchLabel8.place();
                return;
            }
            return;
        }
        this.left.generateCode(blockScope, codeStream, z);
        this.right.generateCode(blockScope, codeStream, z);
        if (z) {
            BranchLabel branchLabel9 = new BranchLabel(codeStream);
            if (z2) {
                switch ((this.left.implicitConversion & 255) >> 4) {
                    case 7:
                        codeStream.lcmp();
                        codeStream.ifne(branchLabel9);
                        break;
                    case 8:
                        codeStream.dcmpl();
                        codeStream.ifne(branchLabel9);
                        break;
                    case 9:
                        codeStream.fcmpl();
                        codeStream.ifne(branchLabel9);
                        break;
                    case 10:
                        codeStream.if_icmpne(branchLabel9);
                        break;
                    default:
                        codeStream.if_acmpne(branchLabel9);
                        break;
                }
            } else {
                switch ((this.left.implicitConversion & 255) >> 4) {
                    case 7:
                        codeStream.lcmp();
                        codeStream.ifeq(branchLabel9);
                        break;
                    case 8:
                        codeStream.dcmpl();
                        codeStream.ifeq(branchLabel9);
                        break;
                    case 9:
                        codeStream.fcmpl();
                        codeStream.ifeq(branchLabel9);
                        break;
                    case 10:
                        codeStream.if_icmpeq(branchLabel9);
                        break;
                    default:
                        codeStream.if_acmpeq(branchLabel9);
                        break;
                }
            }
            codeStream.iconst_1();
            if ((this.bits & 16) != 0) {
                codeStream.generateImplicitConversion(this.implicitConversion);
                codeStream.generateReturnBytecode(this);
                branchLabel9.place();
                codeStream.iconst_0();
                return;
            }
            BranchLabel branchLabel10 = new BranchLabel(codeStream);
            codeStream.goto_(branchLabel10);
            codeStream.decrStackSize(1);
            branchLabel9.place();
            codeStream.iconst_0();
            branchLabel10.place();
        }
    }

    public void generateOptimizedNonBooleanEqual(BlockScope blockScope, CodeStream codeStream, BranchLabel branchLabel, BranchLabel branchLabel2, boolean z) {
        int i = codeStream.position;
        Constant constant = this.right.constant;
        if (constant != Constant.NotAConstant && ((this.left.implicitConversion & 255) >> 4) == 10 && constant.intValue() == 0) {
            this.left.generateCode(blockScope, codeStream, z);
            if (z) {
                if (branchLabel2 == null) {
                    if (branchLabel != null) {
                        codeStream.ifeq(branchLabel);
                    }
                } else if (branchLabel == null) {
                    codeStream.ifne(branchLabel2);
                }
            }
            codeStream.recordPositionsFrom(i, this.sourceStart);
            return;
        }
        Constant constant2 = this.left.constant;
        if (constant2 != Constant.NotAConstant && ((this.left.implicitConversion & 255) >> 4) == 10 && constant2.intValue() == 0) {
            this.right.generateCode(blockScope, codeStream, z);
            if (z) {
                if (branchLabel2 == null) {
                    if (branchLabel != null) {
                        codeStream.ifeq(branchLabel);
                    }
                } else if (branchLabel == null) {
                    codeStream.ifne(branchLabel2);
                }
            }
            codeStream.recordPositionsFrom(i, this.sourceStart);
            return;
        }
        if (this.right instanceof NullLiteral) {
            if (!(this.left instanceof NullLiteral)) {
                this.left.generateCode(blockScope, codeStream, z);
                if (z) {
                    if (branchLabel2 == null) {
                        if (branchLabel != null) {
                            codeStream.ifnull(branchLabel);
                        }
                    } else if (branchLabel == null) {
                        codeStream.ifnonnull(branchLabel2);
                    }
                }
            } else if (z && branchLabel2 == null && branchLabel != null) {
                codeStream.goto_(branchLabel);
            }
            codeStream.recordPositionsFrom(i, this.sourceStart);
            return;
        }
        if (this.left instanceof NullLiteral) {
            this.right.generateCode(blockScope, codeStream, z);
            if (z) {
                if (branchLabel2 == null) {
                    if (branchLabel != null) {
                        codeStream.ifnull(branchLabel);
                    }
                } else if (branchLabel == null) {
                    codeStream.ifnonnull(branchLabel2);
                }
            }
            codeStream.recordPositionsFrom(i, this.sourceStart);
            return;
        }
        this.left.generateCode(blockScope, codeStream, z);
        this.right.generateCode(blockScope, codeStream, z);
        if (z) {
            if (branchLabel2 != null) {
                if (branchLabel == null) {
                    switch ((this.left.implicitConversion & 255) >> 4) {
                        case 7:
                            codeStream.lcmp();
                            codeStream.ifne(branchLabel2);
                            break;
                        case 8:
                            codeStream.dcmpl();
                            codeStream.ifne(branchLabel2);
                            break;
                        case 9:
                            codeStream.fcmpl();
                            codeStream.ifne(branchLabel2);
                            break;
                        case 10:
                            codeStream.if_icmpne(branchLabel2);
                            break;
                        default:
                            codeStream.if_acmpne(branchLabel2);
                            break;
                    }
                }
            } else if (branchLabel != null) {
                switch ((this.left.implicitConversion & 255) >> 4) {
                    case 7:
                        codeStream.lcmp();
                        codeStream.ifeq(branchLabel);
                        break;
                    case 8:
                        codeStream.dcmpl();
                        codeStream.ifeq(branchLabel);
                        break;
                    case 9:
                        codeStream.fcmpl();
                        codeStream.ifeq(branchLabel);
                        break;
                    case 10:
                        codeStream.if_icmpeq(branchLabel);
                        break;
                    default:
                        codeStream.if_acmpeq(branchLabel);
                        break;
                }
            }
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    /* JADX WARN: Code duplicated, block: B:33:0x008d  */
    @Override // org.eclipse.jdt.internal.compiler.ast.BinaryExpression, org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        TypeBinding typeBindingComputeBoxingType;
        TypeBinding typeBindingComputeBoxingType2;
        int i;
        boolean z = this.left instanceof CastExpression;
        if (z) {
            this.left.bits |= 32;
        }
        TypeBinding typeBindingResolveType = this.left.resolveType(blockScope);
        boolean z2 = this.right instanceof CastExpression;
        if (z2) {
            this.right.bits |= 32;
        }
        TypeBinding typeBindingResolveType2 = this.right.resolveType(blockScope);
        if (typeBindingResolveType == null || typeBindingResolveType2 == null) {
            this.constant = Constant.NotAConstant;
            return null;
        }
        CompilerOptions compilerOptions = blockScope.compilerOptions();
        if (compilerOptions.complainOnUninternedIdentityComparison && typeBindingResolveType2.hasTypeBit(16) && typeBindingResolveType.hasTypeBit(16)) {
            blockScope.problemReporter().uninternedIdentityComparison(this, typeBindingResolveType, typeBindingResolveType2, blockScope.referenceCompilationUnit());
        }
        if (compilerOptions.sourceLevel < ClassFileConstants.JDK1_5) {
            typeBindingComputeBoxingType = typeBindingResolveType;
            typeBindingComputeBoxingType2 = typeBindingResolveType2;
        } else if (typeBindingResolveType != TypeBinding.NULL && typeBindingResolveType.isBaseType()) {
            if (typeBindingResolveType2.isBaseType()) {
                typeBindingComputeBoxingType = typeBindingResolveType;
                typeBindingComputeBoxingType2 = typeBindingResolveType2;
            } else {
                typeBindingComputeBoxingType = typeBindingResolveType;
                typeBindingComputeBoxingType2 = blockScope.environment().computeBoxingType(typeBindingResolveType2);
            }
        } else if (typeBindingResolveType2 == TypeBinding.NULL || !typeBindingResolveType2.isBaseType()) {
            typeBindingComputeBoxingType = typeBindingResolveType;
            typeBindingComputeBoxingType2 = typeBindingResolveType2;
        } else {
            typeBindingComputeBoxingType2 = typeBindingResolveType2;
            typeBindingComputeBoxingType = blockScope.environment().computeBoxingType(typeBindingResolveType);
        }
        if (typeBindingComputeBoxingType.isBaseType() && typeBindingComputeBoxingType2.isBaseType()) {
            int i2 = typeBindingComputeBoxingType.f626id;
            int i3 = OperatorSignatures[18][(i2 << 4) + typeBindingComputeBoxingType2.f626id];
            this.left.computeConversion(blockScope, TypeBinding.wellKnownType(blockScope, (i3 >>> 16) & 15), typeBindingResolveType);
            this.right.computeConversion(blockScope, TypeBinding.wellKnownType(blockScope, (i3 >>> 8) & 15), typeBindingResolveType2);
            int i4 = i3 & 15;
            this.bits |= i4;
            if (i4 == 0) {
                this.constant = Constant.NotAConstant;
                blockScope.problemReporter().invalidOperator(this, typeBindingComputeBoxingType, typeBindingComputeBoxingType2);
                return null;
            }
            if (z || z2) {
                CastExpression.checkNeedForArgumentCasts(blockScope, 18, i3, this.left, typeBindingComputeBoxingType.f626id, z, this.right, typeBindingComputeBoxingType2.f626id, z2);
            }
            computeConstant(typeBindingComputeBoxingType, typeBindingComputeBoxingType2);
            Binding directBinding = Expression.getDirectBinding(this.left);
            if (directBinding != null && directBinding == Expression.getDirectBinding(this.right)) {
                if (i2 != 8 && i2 != 9 && !(this.right instanceof Assignment)) {
                    blockScope.problemReporter().comparingIdenticalExpressions(this);
                }
            } else if (this.constant != Constant.NotAConstant && (((i = (this.bits & ASTNode.OperatorMASK) >> 6) == 18 && this.constant == BooleanConstant.fromValue(true)) || (i == 29 && this.constant == BooleanConstant.fromValue(false)))) {
                blockScope.problemReporter().comparingIdenticalExpressions(this);
            }
            BaseTypeBinding baseTypeBinding = TypeBinding.BOOLEAN;
            this.resolvedType = baseTypeBinding;
            return baseTypeBinding;
        }
        if ((!typeBindingComputeBoxingType.isBaseType() || typeBindingComputeBoxingType == TypeBinding.NULL) && ((!typeBindingComputeBoxingType2.isBaseType() || typeBindingComputeBoxingType2 == TypeBinding.NULL) && (checkCastTypesCompatibility(blockScope, typeBindingComputeBoxingType, typeBindingComputeBoxingType2, null) || checkCastTypesCompatibility(blockScope, typeBindingComputeBoxingType2, typeBindingComputeBoxingType, null)))) {
            if (typeBindingComputeBoxingType2.f626id == 11 && typeBindingComputeBoxingType.f626id == 11) {
                computeConstant(typeBindingComputeBoxingType, typeBindingComputeBoxingType2);
            } else {
                this.constant = Constant.NotAConstant;
            }
            ReferenceBinding javaLangObject = blockScope.getJavaLangObject();
            this.left.computeConversion(blockScope, javaLangObject, typeBindingComputeBoxingType);
            this.right.computeConversion(blockScope, javaLangObject, typeBindingComputeBoxingType2);
            boolean z3 = (this.left.bits & 16384) != 0;
            boolean z4 = (this.right.bits & 16384) != 0;
            if (z3 || z4) {
                if (z3) {
                    typeBindingComputeBoxingType = ((CastExpression) this.left).expression.resolvedType;
                }
                if (z4) {
                    typeBindingComputeBoxingType2 = ((CastExpression) this.right).expression.resolvedType;
                }
                if (checkCastTypesCompatibility(blockScope, typeBindingComputeBoxingType, typeBindingComputeBoxingType2, null) || checkCastTypesCompatibility(blockScope, typeBindingComputeBoxingType2, typeBindingComputeBoxingType, null)) {
                    if (z3) {
                        blockScope.problemReporter().unnecessaryCast((CastExpression) this.left);
                    }
                    if (z4) {
                        blockScope.problemReporter().unnecessaryCast((CastExpression) this.right);
                    }
                }
            }
            Binding directBinding2 = Expression.getDirectBinding(this.left);
            if (directBinding2 != null && directBinding2 == Expression.getDirectBinding(this.right) && !(this.right instanceof Assignment)) {
                blockScope.problemReporter().comparingIdenticalExpressions(this);
            }
            BaseTypeBinding baseTypeBinding2 = TypeBinding.BOOLEAN;
            this.resolvedType = baseTypeBinding2;
            return baseTypeBinding2;
        }
        this.constant = Constant.NotAConstant;
        blockScope.problemReporter().notCompatibleTypesError(this, typeBindingComputeBoxingType, typeBindingComputeBoxingType2);
        return null;
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
