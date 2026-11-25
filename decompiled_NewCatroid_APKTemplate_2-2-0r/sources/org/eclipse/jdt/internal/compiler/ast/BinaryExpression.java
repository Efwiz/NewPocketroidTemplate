package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class BinaryExpression extends OperatorExpression {
    public Expression left;
    public Constant optimizedBooleanConstant;
    public Expression right;

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public boolean isCompactableOperation() {
        return true;
    }

    public BinaryExpression(Expression expression, Expression expression2, int i) {
        this.left = expression;
        this.right = expression2;
        this.bits = (i << 6) | this.bits;
        this.sourceStart = expression.sourceStart;
        this.sourceEnd = expression2.sourceEnd;
    }

    public BinaryExpression(BinaryExpression binaryExpression) {
        this.left = binaryExpression.left;
        this.right = binaryExpression.right;
        this.bits = binaryExpression.bits;
        this.sourceStart = binaryExpression.sourceStart;
        this.sourceEnd = binaryExpression.sourceEnd;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        try {
            if (this.resolvedType.f626id == 11) {
                return this.right.analyseCode(blockScope, flowContext, this.left.analyseCode(blockScope, flowContext, flowInfo).unconditionalInits()).unconditionalInits();
            }
            this.left.checkNPE(blockScope, flowContext, flowInfo);
            UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits = this.left.analyseCode(blockScope, flowContext, flowInfo).unconditionalInits();
            if (((this.bits & ASTNode.OperatorMASK) >> 6) != 2) {
                flowContext.expireNullCheckedFieldInfo();
            }
            this.right.checkNPE(blockScope, flowContext, unconditionalFlowInfoUnconditionalInits);
            UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits2 = this.right.analyseCode(blockScope, flowContext, unconditionalFlowInfoUnconditionalInits).unconditionalInits();
            if (((this.bits & ASTNode.OperatorMASK) >> 6) != 2) {
                flowContext.expireNullCheckedFieldInfo();
            }
            return unconditionalFlowInfoUnconditionalInits2;
        } finally {
            flowContext.recordAbruptExit();
        }
    }

    public void computeConstant(BlockScope blockScope, int i, int i2) {
        if (this.left.constant != Constant.NotAConstant && this.right.constant != Constant.NotAConstant) {
            try {
                this.constant = Constant.computeConstantOperation(this.left.constant, i, (this.bits & ASTNode.OperatorMASK) >> 6, this.right.constant, i2);
            } catch (ArithmeticException unused) {
                this.constant = Constant.NotAConstant;
            }
        } else {
            this.constant = Constant.NotAConstant;
            optimizedBooleanConstant(i, (this.bits & ASTNode.OperatorMASK) >> 6, i2);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public Constant optimizedBooleanConstant() {
        Constant constant = this.optimizedBooleanConstant;
        return constant == null ? this.constant : constant;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i = codeStream.position;
        if (this.constant != Constant.NotAConstant) {
            if (z) {
                codeStream.generateConstant(this.constant, this.implicitConversion);
            }
            codeStream.recordPositionsFrom(i, this.sourceStart);
            return;
        }
        switch ((this.bits & ASTNode.OperatorMASK) >> 6) {
            case 2:
                int i2 = this.bits & 15;
                if (i2 == 5) {
                    generateLogicalAnd(blockScope, codeStream, z);
                } else if (i2 == 7) {
                    if (this.left.constant != Constant.NotAConstant && this.left.constant.typeID() == 7 && this.left.constant.longValue() == 0) {
                        this.right.generateCode(blockScope, codeStream, false);
                        if (z) {
                            codeStream.lconst_0();
                        }
                    } else if (this.right.constant != Constant.NotAConstant && this.right.constant.typeID() == 7 && this.right.constant.longValue() == 0) {
                        this.left.generateCode(blockScope, codeStream, false);
                        if (z) {
                            codeStream.lconst_0();
                        }
                    } else {
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.land();
                        }
                    }
                } else if (i2 == 10) {
                    if (this.left.constant != Constant.NotAConstant && this.left.constant.typeID() == 10 && this.left.constant.intValue() == 0) {
                        this.right.generateCode(blockScope, codeStream, false);
                        if (z) {
                            codeStream.iconst_0();
                        }
                    } else if (this.right.constant != Constant.NotAConstant && this.right.constant.typeID() == 10 && this.right.constant.intValue() == 0) {
                        this.left.generateCode(blockScope, codeStream, false);
                        if (z) {
                            codeStream.iconst_0();
                        }
                    } else {
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.iand();
                        }
                    }
                }
                break;
            case 3:
                int i3 = this.bits & 15;
                if (i3 == 5) {
                    generateLogicalOr(blockScope, codeStream, z);
                } else if (i3 == 7) {
                    if (this.left.constant != Constant.NotAConstant && this.left.constant.typeID() == 7 && this.left.constant.longValue() == 0) {
                        this.right.generateCode(blockScope, codeStream, z);
                    } else if (this.right.constant != Constant.NotAConstant && this.right.constant.typeID() == 7 && this.right.constant.longValue() == 0) {
                        this.left.generateCode(blockScope, codeStream, z);
                    } else {
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.lor();
                        }
                    }
                } else if (i3 == 10) {
                    if (this.left.constant != Constant.NotAConstant && this.left.constant.typeID() == 10 && this.left.constant.intValue() == 0) {
                        this.right.generateCode(blockScope, codeStream, z);
                    } else if (this.right.constant != Constant.NotAConstant && this.right.constant.typeID() == 10 && this.right.constant.intValue() == 0) {
                        this.left.generateCode(blockScope, codeStream, z);
                    } else {
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.ior();
                        }
                    }
                }
                break;
            case 4:
                BranchLabel branchLabel = new BranchLabel(codeStream);
                generateOptimizedLessThan(blockScope, codeStream, null, branchLabel, z);
                if (z) {
                    codeStream.iconst_1();
                    if ((this.bits & 16) != 0) {
                        codeStream.generateImplicitConversion(this.implicitConversion);
                        codeStream.generateReturnBytecode(this);
                        branchLabel.place();
                        codeStream.iconst_0();
                    } else {
                        BranchLabel branchLabel2 = new BranchLabel(codeStream);
                        codeStream.goto_(branchLabel2);
                        codeStream.decrStackSize(1);
                        branchLabel.place();
                        codeStream.iconst_0();
                        branchLabel2.place();
                    }
                }
                break;
            case 5:
                BranchLabel branchLabel3 = new BranchLabel(codeStream);
                generateOptimizedLessThanOrEqual(blockScope, codeStream, null, branchLabel3, z);
                if (z) {
                    codeStream.iconst_1();
                    if ((this.bits & 16) != 0) {
                        codeStream.generateImplicitConversion(this.implicitConversion);
                        codeStream.generateReturnBytecode(this);
                        branchLabel3.place();
                        codeStream.iconst_0();
                    } else {
                        BranchLabel branchLabel4 = new BranchLabel(codeStream);
                        codeStream.goto_(branchLabel4);
                        codeStream.decrStackSize(1);
                        branchLabel3.place();
                        codeStream.iconst_0();
                        branchLabel4.place();
                    }
                }
                break;
            case 6:
                BranchLabel branchLabel5 = new BranchLabel(codeStream);
                generateOptimizedGreaterThan(blockScope, codeStream, null, branchLabel5, z);
                if (z) {
                    codeStream.iconst_1();
                    if ((this.bits & 16) != 0) {
                        codeStream.generateImplicitConversion(this.implicitConversion);
                        codeStream.generateReturnBytecode(this);
                        branchLabel5.place();
                        codeStream.iconst_0();
                    } else {
                        BranchLabel branchLabel6 = new BranchLabel(codeStream);
                        codeStream.goto_(branchLabel6);
                        codeStream.decrStackSize(1);
                        branchLabel5.place();
                        codeStream.iconst_0();
                        branchLabel6.place();
                    }
                }
                break;
            case 7:
                BranchLabel branchLabel7 = new BranchLabel(codeStream);
                generateOptimizedGreaterThanOrEqual(blockScope, codeStream, null, branchLabel7, z);
                if (z) {
                    codeStream.iconst_1();
                    if ((this.bits & 16) != 0) {
                        codeStream.generateImplicitConversion(this.implicitConversion);
                        codeStream.generateReturnBytecode(this);
                        branchLabel7.place();
                        codeStream.iconst_0();
                    } else {
                        BranchLabel branchLabel8 = new BranchLabel(codeStream);
                        codeStream.goto_(branchLabel8);
                        codeStream.decrStackSize(1);
                        branchLabel7.place();
                        codeStream.iconst_0();
                        branchLabel8.place();
                    }
                }
                break;
            case 8:
                int i4 = this.bits & 15;
                if (i4 == 5) {
                    generateLogicalXor(blockScope, codeStream, z);
                } else if (i4 == 7) {
                    if (this.left.constant != Constant.NotAConstant && this.left.constant.typeID() == 7 && this.left.constant.longValue() == 0) {
                        this.right.generateCode(blockScope, codeStream, z);
                    } else if (this.right.constant != Constant.NotAConstant && this.right.constant.typeID() == 7 && this.right.constant.longValue() == 0) {
                        this.left.generateCode(blockScope, codeStream, z);
                    } else {
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.lxor();
                        }
                    }
                } else if (i4 == 10) {
                    if (this.left.constant != Constant.NotAConstant && this.left.constant.typeID() == 10 && this.left.constant.intValue() == 0) {
                        this.right.generateCode(blockScope, codeStream, z);
                    } else if (this.right.constant != Constant.NotAConstant && this.right.constant.typeID() == 10 && this.right.constant.intValue() == 0) {
                        this.left.generateCode(blockScope, codeStream, z);
                    } else {
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.ixor();
                        }
                    }
                }
                break;
            case 9:
                switch (this.bits & 15) {
                    case 7:
                        this.left.generateCode(blockScope, codeStream, true);
                        this.right.generateCode(blockScope, codeStream, true);
                        codeStream.ldiv();
                        if (!z) {
                            codeStream.pop2();
                        }
                        break;
                    case 8:
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.ddiv();
                        }
                        break;
                    case 9:
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.fdiv();
                        }
                        break;
                    case 10:
                        this.left.generateCode(blockScope, codeStream, true);
                        this.right.generateCode(blockScope, codeStream, true);
                        codeStream.idiv();
                        if (!z) {
                            codeStream.pop();
                        }
                        break;
                }
                break;
            case 10:
                int i5 = this.bits & 15;
                if (i5 == 7) {
                    this.left.generateCode(blockScope, codeStream, z);
                    this.right.generateCode(blockScope, codeStream, z);
                    if (z) {
                        codeStream.lshl();
                    }
                } else if (i5 == 10) {
                    this.left.generateCode(blockScope, codeStream, z);
                    this.right.generateCode(blockScope, codeStream, z);
                    if (z) {
                        codeStream.ishl();
                    }
                }
                break;
            case 13:
                switch (this.bits & 15) {
                    case 7:
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.lsub();
                        }
                        break;
                    case 8:
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.dsub();
                        }
                        break;
                    case 9:
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.fsub();
                        }
                        break;
                    case 10:
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.isub();
                        }
                        break;
                }
                break;
            case 14:
                switch (this.bits & 15) {
                    case 7:
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.ladd();
                        }
                        break;
                    case 8:
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.dadd();
                        }
                        break;
                    case 9:
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.fadd();
                        }
                        break;
                    case 10:
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.iadd();
                        }
                        break;
                    case 11:
                        codeStream.generateStringConcatenationAppend(blockScope, this.left, this.right);
                        if (!z) {
                            codeStream.pop();
                        }
                        break;
                }
                break;
            case 15:
                switch (this.bits & 15) {
                    case 7:
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.lmul();
                        }
                        break;
                    case 8:
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.dmul();
                        }
                        break;
                    case 9:
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.fmul();
                        }
                        break;
                    case 10:
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.imul();
                        }
                        break;
                }
                break;
            case 16:
                switch (this.bits & 15) {
                    case 7:
                        this.left.generateCode(blockScope, codeStream, true);
                        this.right.generateCode(blockScope, codeStream, true);
                        codeStream.lrem();
                        if (!z) {
                            codeStream.pop2();
                        }
                        break;
                    case 8:
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.drem();
                        }
                        break;
                    case 9:
                        this.left.generateCode(blockScope, codeStream, z);
                        this.right.generateCode(blockScope, codeStream, z);
                        if (z) {
                            codeStream.frem();
                        }
                        break;
                    case 10:
                        this.left.generateCode(blockScope, codeStream, true);
                        this.right.generateCode(blockScope, codeStream, true);
                        codeStream.irem();
                        if (!z) {
                            codeStream.pop();
                        }
                        break;
                }
                break;
            case 17:
                int i6 = this.bits & 15;
                if (i6 == 7) {
                    this.left.generateCode(blockScope, codeStream, z);
                    this.right.generateCode(blockScope, codeStream, z);
                    if (z) {
                        codeStream.lshr();
                    }
                } else if (i6 == 10) {
                    this.left.generateCode(blockScope, codeStream, z);
                    this.right.generateCode(blockScope, codeStream, z);
                    if (z) {
                        codeStream.ishr();
                    }
                }
                break;
            case 19:
                int i7 = this.bits & 15;
                if (i7 == 7) {
                    this.left.generateCode(blockScope, codeStream, z);
                    this.right.generateCode(blockScope, codeStream, z);
                    if (z) {
                        codeStream.lushr();
                    }
                } else if (i7 == 10) {
                    this.left.generateCode(blockScope, codeStream, z);
                    this.right.generateCode(blockScope, codeStream, z);
                    if (z) {
                        codeStream.iushr();
                    }
                }
                break;
        }
        if (z) {
            codeStream.generateImplicitConversion(this.implicitConversion);
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateOptimizedBoolean(BlockScope blockScope, CodeStream codeStream, BranchLabel branchLabel, BranchLabel branchLabel2, boolean z) {
        if (this.constant != Constant.NotAConstant && this.constant.typeID() == 5) {
            super.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, z);
        }
        switch ((this.bits & ASTNode.OperatorMASK) >> 6) {
            case 2:
                generateOptimizedLogicalAnd(blockScope, codeStream, branchLabel, branchLabel2, z);
                break;
            case 3:
                generateOptimizedLogicalOr(blockScope, codeStream, branchLabel, branchLabel2, z);
                break;
            case 4:
                generateOptimizedLessThan(blockScope, codeStream, branchLabel, branchLabel2, z);
                break;
            case 5:
                generateOptimizedLessThanOrEqual(blockScope, codeStream, branchLabel, branchLabel2, z);
                break;
            case 6:
                generateOptimizedGreaterThan(blockScope, codeStream, branchLabel, branchLabel2, z);
                break;
            case 7:
                generateOptimizedGreaterThanOrEqual(blockScope, codeStream, branchLabel, branchLabel2, z);
                break;
            case 8:
                generateOptimizedLogicalXor(blockScope, codeStream, branchLabel, branchLabel2, z);
                break;
            default:
                super.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, z);
                break;
        }
    }

    public void generateOptimizedGreaterThan(BlockScope blockScope, CodeStream codeStream, BranchLabel branchLabel, BranchLabel branchLabel2, boolean z) {
        int i = (this.left.implicitConversion & 255) >> 4;
        if (i == 10) {
            if (this.left.constant != Constant.NotAConstant && this.left.constant.intValue() == 0) {
                this.right.generateCode(blockScope, codeStream, z);
                if (z) {
                    if (branchLabel2 == null) {
                        if (branchLabel != null) {
                            codeStream.iflt(branchLabel);
                        }
                    } else if (branchLabel == null) {
                        codeStream.ifge(branchLabel2);
                    }
                }
                codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                return;
            }
            if (this.right.constant != Constant.NotAConstant && this.right.constant.intValue() == 0) {
                this.left.generateCode(blockScope, codeStream, z);
                if (z) {
                    if (branchLabel2 == null) {
                        if (branchLabel != null) {
                            codeStream.ifgt(branchLabel);
                        }
                    } else if (branchLabel == null) {
                        codeStream.ifle(branchLabel2);
                    }
                }
                codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                return;
            }
        }
        this.left.generateCode(blockScope, codeStream, z);
        this.right.generateCode(blockScope, codeStream, z);
        if (z) {
            if (branchLabel2 == null) {
                if (branchLabel != null) {
                    switch (i) {
                        case 7:
                            codeStream.lcmp();
                            codeStream.ifgt(branchLabel);
                            break;
                        case 8:
                            codeStream.dcmpl();
                            codeStream.ifgt(branchLabel);
                            break;
                        case 9:
                            codeStream.fcmpl();
                            codeStream.ifgt(branchLabel);
                            break;
                        case 10:
                            codeStream.if_icmpgt(branchLabel);
                            break;
                    }
                    codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                    return;
                }
                return;
            }
            if (branchLabel == null) {
                switch (i) {
                    case 7:
                        codeStream.lcmp();
                        codeStream.ifle(branchLabel2);
                        break;
                    case 8:
                        codeStream.dcmpl();
                        codeStream.ifle(branchLabel2);
                        break;
                    case 9:
                        codeStream.fcmpl();
                        codeStream.ifle(branchLabel2);
                        break;
                    case 10:
                        codeStream.if_icmple(branchLabel2);
                        break;
                }
                codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            }
        }
    }

    public void generateOptimizedGreaterThanOrEqual(BlockScope blockScope, CodeStream codeStream, BranchLabel branchLabel, BranchLabel branchLabel2, boolean z) {
        int i = (this.left.implicitConversion & 255) >> 4;
        if (i == 10) {
            if (this.left.constant != Constant.NotAConstant && this.left.constant.intValue() == 0) {
                this.right.generateCode(blockScope, codeStream, z);
                if (z) {
                    if (branchLabel2 == null) {
                        if (branchLabel != null) {
                            codeStream.ifle(branchLabel);
                        }
                    } else if (branchLabel == null) {
                        codeStream.ifgt(branchLabel2);
                    }
                }
                codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                return;
            }
            if (this.right.constant != Constant.NotAConstant && this.right.constant.intValue() == 0) {
                this.left.generateCode(blockScope, codeStream, z);
                if (z) {
                    if (branchLabel2 == null) {
                        if (branchLabel != null) {
                            codeStream.ifge(branchLabel);
                        }
                    } else if (branchLabel == null) {
                        codeStream.iflt(branchLabel2);
                    }
                }
                codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                return;
            }
        }
        this.left.generateCode(blockScope, codeStream, z);
        this.right.generateCode(blockScope, codeStream, z);
        if (z) {
            if (branchLabel2 == null) {
                if (branchLabel != null) {
                    switch (i) {
                        case 7:
                            codeStream.lcmp();
                            codeStream.ifge(branchLabel);
                            break;
                        case 8:
                            codeStream.dcmpl();
                            codeStream.ifge(branchLabel);
                            break;
                        case 9:
                            codeStream.fcmpl();
                            codeStream.ifge(branchLabel);
                            break;
                        case 10:
                            codeStream.if_icmpge(branchLabel);
                            break;
                    }
                    codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                    return;
                }
                return;
            }
            if (branchLabel == null) {
                switch (i) {
                    case 7:
                        codeStream.lcmp();
                        codeStream.iflt(branchLabel2);
                        break;
                    case 8:
                        codeStream.dcmpl();
                        codeStream.iflt(branchLabel2);
                        break;
                    case 9:
                        codeStream.fcmpl();
                        codeStream.iflt(branchLabel2);
                        break;
                    case 10:
                        codeStream.if_icmplt(branchLabel2);
                        break;
                }
                codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            }
        }
    }

    public void generateOptimizedLessThan(BlockScope blockScope, CodeStream codeStream, BranchLabel branchLabel, BranchLabel branchLabel2, boolean z) {
        int i = (this.left.implicitConversion & 255) >> 4;
        if (i == 10) {
            if (this.left.constant != Constant.NotAConstant && this.left.constant.intValue() == 0) {
                this.right.generateCode(blockScope, codeStream, z);
                if (z) {
                    if (branchLabel2 == null) {
                        if (branchLabel != null) {
                            codeStream.ifgt(branchLabel);
                        }
                    } else if (branchLabel == null) {
                        codeStream.ifle(branchLabel2);
                    }
                }
                codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                return;
            }
            if (this.right.constant != Constant.NotAConstant && this.right.constant.intValue() == 0) {
                this.left.generateCode(blockScope, codeStream, z);
                if (z) {
                    if (branchLabel2 == null) {
                        if (branchLabel != null) {
                            codeStream.iflt(branchLabel);
                        }
                    } else if (branchLabel == null) {
                        codeStream.ifge(branchLabel2);
                    }
                }
                codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                return;
            }
        }
        this.left.generateCode(blockScope, codeStream, z);
        this.right.generateCode(blockScope, codeStream, z);
        if (z) {
            if (branchLabel2 == null) {
                if (branchLabel != null) {
                    switch (i) {
                        case 7:
                            codeStream.lcmp();
                            codeStream.iflt(branchLabel);
                            break;
                        case 8:
                            codeStream.dcmpg();
                            codeStream.iflt(branchLabel);
                            break;
                        case 9:
                            codeStream.fcmpg();
                            codeStream.iflt(branchLabel);
                            break;
                        case 10:
                            codeStream.if_icmplt(branchLabel);
                            break;
                    }
                    codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                    return;
                }
                return;
            }
            if (branchLabel == null) {
                switch (i) {
                    case 7:
                        codeStream.lcmp();
                        codeStream.ifge(branchLabel2);
                        break;
                    case 8:
                        codeStream.dcmpg();
                        codeStream.ifge(branchLabel2);
                        break;
                    case 9:
                        codeStream.fcmpg();
                        codeStream.ifge(branchLabel2);
                        break;
                    case 10:
                        codeStream.if_icmpge(branchLabel2);
                        break;
                }
                codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            }
        }
    }

    public void generateOptimizedLessThanOrEqual(BlockScope blockScope, CodeStream codeStream, BranchLabel branchLabel, BranchLabel branchLabel2, boolean z) {
        int i = (this.left.implicitConversion & 255) >> 4;
        if (i == 10) {
            if (this.left.constant != Constant.NotAConstant && this.left.constant.intValue() == 0) {
                this.right.generateCode(blockScope, codeStream, z);
                if (z) {
                    if (branchLabel2 == null) {
                        if (branchLabel != null) {
                            codeStream.ifge(branchLabel);
                        }
                    } else if (branchLabel == null) {
                        codeStream.iflt(branchLabel2);
                    }
                }
                codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                return;
            }
            if (this.right.constant != Constant.NotAConstant && this.right.constant.intValue() == 0) {
                this.left.generateCode(blockScope, codeStream, z);
                if (z) {
                    if (branchLabel2 == null) {
                        if (branchLabel != null) {
                            codeStream.ifle(branchLabel);
                        }
                    } else if (branchLabel == null) {
                        codeStream.ifgt(branchLabel2);
                    }
                }
                codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                return;
            }
        }
        this.left.generateCode(blockScope, codeStream, z);
        this.right.generateCode(blockScope, codeStream, z);
        if (z) {
            if (branchLabel2 == null) {
                if (branchLabel != null) {
                    switch (i) {
                        case 7:
                            codeStream.lcmp();
                            codeStream.ifle(branchLabel);
                            break;
                        case 8:
                            codeStream.dcmpg();
                            codeStream.ifle(branchLabel);
                            break;
                        case 9:
                            codeStream.fcmpg();
                            codeStream.ifle(branchLabel);
                            break;
                        case 10:
                            codeStream.if_icmple(branchLabel);
                            break;
                    }
                    codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                    return;
                }
                return;
            }
            if (branchLabel == null) {
                switch (i) {
                    case 7:
                        codeStream.lcmp();
                        codeStream.ifgt(branchLabel2);
                        break;
                    case 8:
                        codeStream.dcmpg();
                        codeStream.ifgt(branchLabel2);
                        break;
                    case 9:
                        codeStream.fcmpg();
                        codeStream.ifgt(branchLabel2);
                        break;
                    case 10:
                        codeStream.if_icmpgt(branchLabel2);
                        break;
                }
                codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            }
        }
    }

    public void generateLogicalAnd(BlockScope blockScope, CodeStream codeStream, boolean z) {
        if ((this.left.implicitConversion & 15) == 5) {
            Constant constantOptimizedBooleanConstant = this.left.optimizedBooleanConstant();
            if (constantOptimizedBooleanConstant != Constant.NotAConstant) {
                if (constantOptimizedBooleanConstant.booleanValue()) {
                    this.left.generateCode(blockScope, codeStream, false);
                    this.right.generateCode(blockScope, codeStream, z);
                    return;
                }
                this.left.generateCode(blockScope, codeStream, false);
                this.right.generateCode(blockScope, codeStream, false);
                if (z) {
                    codeStream.iconst_0();
                }
                codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                return;
            }
            Constant constantOptimizedBooleanConstant2 = this.right.optimizedBooleanConstant();
            if (constantOptimizedBooleanConstant2 != Constant.NotAConstant) {
                if (constantOptimizedBooleanConstant2.booleanValue()) {
                    this.left.generateCode(blockScope, codeStream, z);
                    this.right.generateCode(blockScope, codeStream, false);
                    return;
                }
                this.left.generateCode(blockScope, codeStream, false);
                this.right.generateCode(blockScope, codeStream, false);
                if (z) {
                    codeStream.iconst_0();
                }
                codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                return;
            }
        }
        this.left.generateCode(blockScope, codeStream, z);
        this.right.generateCode(blockScope, codeStream, z);
        if (z) {
            codeStream.iand();
        }
        codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
    }

    public void generateLogicalOr(BlockScope blockScope, CodeStream codeStream, boolean z) {
        if ((this.left.implicitConversion & 15) == 5) {
            Constant constantOptimizedBooleanConstant = this.left.optimizedBooleanConstant();
            if (constantOptimizedBooleanConstant != Constant.NotAConstant) {
                if (constantOptimizedBooleanConstant.booleanValue()) {
                    this.left.generateCode(blockScope, codeStream, false);
                    this.right.generateCode(blockScope, codeStream, false);
                    if (z) {
                        codeStream.iconst_1();
                    }
                    codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                    return;
                }
                this.left.generateCode(blockScope, codeStream, false);
                this.right.generateCode(blockScope, codeStream, z);
                return;
            }
            Constant constantOptimizedBooleanConstant2 = this.right.optimizedBooleanConstant();
            if (constantOptimizedBooleanConstant2 != Constant.NotAConstant) {
                if (constantOptimizedBooleanConstant2.booleanValue()) {
                    this.left.generateCode(blockScope, codeStream, false);
                    this.right.generateCode(blockScope, codeStream, false);
                    if (z) {
                        codeStream.iconst_1();
                    }
                    codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                    return;
                }
                this.left.generateCode(blockScope, codeStream, z);
                this.right.generateCode(blockScope, codeStream, false);
                return;
            }
        }
        this.left.generateCode(blockScope, codeStream, z);
        this.right.generateCode(blockScope, codeStream, z);
        if (z) {
            codeStream.ior();
        }
        codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
    }

    public void generateLogicalXor(BlockScope blockScope, CodeStream codeStream, boolean z) {
        if ((this.left.implicitConversion & 15) == 5) {
            Constant constantOptimizedBooleanConstant = this.left.optimizedBooleanConstant();
            if (constantOptimizedBooleanConstant != Constant.NotAConstant) {
                if (constantOptimizedBooleanConstant.booleanValue()) {
                    this.left.generateCode(blockScope, codeStream, false);
                    if (z) {
                        codeStream.iconst_1();
                    }
                    this.right.generateCode(blockScope, codeStream, z);
                    if (z) {
                        codeStream.ixor();
                        codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                        return;
                    }
                    return;
                }
                this.left.generateCode(blockScope, codeStream, false);
                this.right.generateCode(blockScope, codeStream, z);
                return;
            }
            Constant constantOptimizedBooleanConstant2 = this.right.optimizedBooleanConstant();
            if (constantOptimizedBooleanConstant2 != Constant.NotAConstant) {
                if (constantOptimizedBooleanConstant2.booleanValue()) {
                    this.left.generateCode(blockScope, codeStream, z);
                    this.right.generateCode(blockScope, codeStream, false);
                    if (z) {
                        codeStream.iconst_1();
                        codeStream.ixor();
                        codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                        return;
                    }
                    return;
                }
                this.left.generateCode(blockScope, codeStream, z);
                this.right.generateCode(blockScope, codeStream, false);
                return;
            }
        }
        this.left.generateCode(blockScope, codeStream, z);
        this.right.generateCode(blockScope, codeStream, z);
        if (z) {
            codeStream.ixor();
        }
        codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
    }

    public void generateOptimizedLogicalAnd(BlockScope blockScope, CodeStream codeStream, BranchLabel branchLabel, BranchLabel branchLabel2, boolean z) {
        if ((this.left.implicitConversion & 15) == 5) {
            Constant constantOptimizedBooleanConstant = this.left.optimizedBooleanConstant();
            if (constantOptimizedBooleanConstant != Constant.NotAConstant) {
                if (constantOptimizedBooleanConstant.booleanValue()) {
                    this.left.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, false);
                    this.right.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, z);
                    return;
                }
                this.left.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, false);
                this.right.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, false);
                if (z && branchLabel2 != null) {
                    codeStream.goto_(branchLabel2);
                }
                codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                return;
            }
            Constant constantOptimizedBooleanConstant2 = this.right.optimizedBooleanConstant();
            if (constantOptimizedBooleanConstant2 != Constant.NotAConstant) {
                if (constantOptimizedBooleanConstant2.booleanValue()) {
                    this.left.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, z);
                    this.right.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, false);
                    return;
                }
                BranchLabel branchLabel3 = new BranchLabel(codeStream);
                this.left.generateOptimizedBoolean(blockScope, codeStream, branchLabel3, branchLabel2, false);
                branchLabel3.place();
                this.right.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, false);
                if (z && branchLabel2 != null) {
                    codeStream.goto_(branchLabel2);
                }
                codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                return;
            }
        }
        this.left.generateCode(blockScope, codeStream, z);
        this.right.generateCode(blockScope, codeStream, z);
        if (z) {
            codeStream.iand();
            if (branchLabel2 == null) {
                if (branchLabel != null) {
                    codeStream.ifne(branchLabel);
                }
            } else if (branchLabel == null) {
                codeStream.ifeq(branchLabel2);
            }
        }
        codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
    }

    public void generateOptimizedLogicalOr(BlockScope blockScope, CodeStream codeStream, BranchLabel branchLabel, BranchLabel branchLabel2, boolean z) {
        if ((this.left.implicitConversion & 15) == 5) {
            Constant constantOptimizedBooleanConstant = this.left.optimizedBooleanConstant();
            if (constantOptimizedBooleanConstant != Constant.NotAConstant) {
                if (constantOptimizedBooleanConstant.booleanValue()) {
                    this.left.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, false);
                    BranchLabel branchLabel3 = new BranchLabel(codeStream);
                    this.right.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel3, false);
                    branchLabel3.place();
                    if (z && branchLabel != null) {
                        codeStream.goto_(branchLabel);
                    }
                    codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                    return;
                }
                this.left.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, false);
                this.right.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, z);
                return;
            }
            Constant constantOptimizedBooleanConstant2 = this.right.optimizedBooleanConstant();
            if (constantOptimizedBooleanConstant2 != Constant.NotAConstant) {
                if (constantOptimizedBooleanConstant2.booleanValue()) {
                    BranchLabel branchLabel4 = new BranchLabel(codeStream);
                    this.left.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel4, false);
                    branchLabel4.place();
                    this.right.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, false);
                    if (z && branchLabel != null) {
                        codeStream.goto_(branchLabel);
                    }
                    codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                    return;
                }
                this.left.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, z);
                this.right.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, false);
                return;
            }
        }
        this.left.generateCode(blockScope, codeStream, z);
        this.right.generateCode(blockScope, codeStream, z);
        if (z) {
            codeStream.ior();
            if (branchLabel2 == null) {
                if (branchLabel != null) {
                    codeStream.ifne(branchLabel);
                }
            } else if (branchLabel == null) {
                codeStream.ifeq(branchLabel2);
            }
        }
        codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
    }

    public void generateOptimizedLogicalXor(BlockScope blockScope, CodeStream codeStream, BranchLabel branchLabel, BranchLabel branchLabel2, boolean z) {
        if ((this.left.implicitConversion & 15) == 5) {
            Constant constantOptimizedBooleanConstant = this.left.optimizedBooleanConstant();
            if (constantOptimizedBooleanConstant != Constant.NotAConstant) {
                if (constantOptimizedBooleanConstant.booleanValue()) {
                    this.left.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, false);
                    this.right.generateOptimizedBoolean(blockScope, codeStream, branchLabel2, branchLabel, z);
                    return;
                } else {
                    this.left.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, false);
                    this.right.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, z);
                    return;
                }
            }
            Constant constantOptimizedBooleanConstant2 = this.right.optimizedBooleanConstant();
            if (constantOptimizedBooleanConstant2 != Constant.NotAConstant) {
                if (constantOptimizedBooleanConstant2.booleanValue()) {
                    this.left.generateOptimizedBoolean(blockScope, codeStream, branchLabel2, branchLabel, z);
                    this.right.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, false);
                    return;
                } else {
                    this.left.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, z);
                    this.right.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, false);
                    return;
                }
            }
        }
        this.left.generateCode(blockScope, codeStream, z);
        this.right.generateCode(blockScope, codeStream, z);
        if (z) {
            codeStream.ixor();
            if (branchLabel2 == null) {
                if (branchLabel != null) {
                    codeStream.ifne(branchLabel);
                }
            } else if (branchLabel == null) {
                codeStream.ifeq(branchLabel2);
            }
        }
        codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateOptimizedStringConcatenation(BlockScope blockScope, CodeStream codeStream, int i) {
        if (((this.bits & ASTNode.OperatorMASK) >> 6) == 14 && (this.bits & 15) == 11) {
            if (this.constant != Constant.NotAConstant) {
                codeStream.generateConstant(this.constant, this.implicitConversion);
                codeStream.invokeStringConcatenationAppendForType(this.implicitConversion & 15);
                return;
            }
            int i2 = codeStream.position;
            Expression expression = this.left;
            expression.generateOptimizedStringConcatenation(blockScope, codeStream, expression.implicitConversion & 15);
            codeStream.recordPositionsFrom(i2, this.left.sourceStart);
            int i3 = codeStream.position;
            Expression expression2 = this.right;
            expression2.generateOptimizedStringConcatenation(blockScope, codeStream, expression2.implicitConversion & 15);
            codeStream.recordPositionsFrom(i3, this.right.sourceStart);
            return;
        }
        super.generateOptimizedStringConcatenation(blockScope, codeStream, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateOptimizedStringConcatenationCreation(BlockScope blockScope, CodeStream codeStream, int i) {
        if (((this.bits & ASTNode.OperatorMASK) >> 6) == 14 && (this.bits & 15) == 11) {
            if (this.constant != Constant.NotAConstant) {
                codeStream.newStringContatenation();
                codeStream.dup();
                codeStream.ldc(this.constant.stringValue());
                codeStream.invokeStringConcatenationStringConstructor();
                return;
            }
            int i2 = codeStream.position;
            Expression expression = this.left;
            expression.generateOptimizedStringConcatenationCreation(blockScope, codeStream, expression.implicitConversion & 15);
            codeStream.recordPositionsFrom(i2, this.left.sourceStart);
            int i3 = codeStream.position;
            Expression expression2 = this.right;
            expression2.generateOptimizedStringConcatenation(blockScope, codeStream, expression2.implicitConversion & 15);
            codeStream.recordPositionsFrom(i3, this.right.sourceStart);
            return;
        }
        super.generateOptimizedStringConcatenationCreation(blockScope, codeStream, i);
    }

    void nonRecursiveResolveTypeUpwards(BlockScope blockScope) {
        int i;
        int i2;
        TypeBinding typeBinding = this.left.resolvedType;
        Expression expression = this.right;
        boolean z = expression instanceof CastExpression;
        if (z) {
            expression.bits |= 32;
        }
        TypeBinding typeBindingResolveType = this.right.resolveType(blockScope);
        if (typeBinding == null || typeBindingResolveType == null) {
            this.constant = Constant.NotAConstant;
            return;
        }
        int i3 = typeBinding.f626id;
        int i4 = typeBindingResolveType.f626id;
        if (blockScope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_5) {
            if (!typeBinding.isBaseType() && i4 != 11 && i4 != 12) {
                i3 = blockScope.environment().computeBoxingType(typeBinding).f626id;
            }
            if (!typeBindingResolveType.isBaseType() && i3 != 11 && i3 != 12) {
                i4 = blockScope.environment().computeBoxingType(typeBindingResolveType).f626id;
            }
        }
        if (i3 <= 15 && i4 <= 15) {
            i2 = i3;
            i = i4;
        } else if (i3 == 11) {
            i2 = i3;
            i = 1;
        } else if (i4 != 11) {
            this.constant = Constant.NotAConstant;
            blockScope.problemReporter().invalidOperator(this, typeBinding, typeBindingResolveType);
            return;
        } else {
            i = i4;
            i2 = 1;
        }
        if (((this.bits & ASTNode.OperatorMASK) >> 6) == 14) {
            if (i2 == 11) {
                this.left.computeConversion(blockScope, typeBinding, typeBinding);
                if (typeBindingResolveType.isArrayType() && TypeBinding.equalsEquals(((ArrayBinding) typeBindingResolveType).elementsType(), TypeBinding.CHAR)) {
                    blockScope.problemReporter().signalNoImplicitStringConversionForCharArrayExpression(this.right);
                }
            }
            if (i == 11) {
                this.right.computeConversion(blockScope, typeBindingResolveType, typeBindingResolveType);
                if (typeBinding.isArrayType() && TypeBinding.equalsEquals(((ArrayBinding) typeBinding).elementsType(), TypeBinding.CHAR)) {
                    blockScope.problemReporter().signalNoImplicitStringConversionForCharArrayExpression(this.left);
                }
            }
        }
        int i5 = (this.bits & ASTNode.OperatorMASK) >> 6;
        int i6 = OperatorExpression.OperatorSignatures[i5][(i2 << 4) + i];
        this.left.computeConversion(blockScope, TypeBinding.wellKnownType(blockScope, (i6 >>> 16) & 15), typeBinding);
        this.right.computeConversion(blockScope, TypeBinding.wellKnownType(blockScope, 15 & (i6 >>> 8)), typeBindingResolveType);
        int i7 = i6 & 15;
        this.bits |= i7;
        switch (i7) {
            case 2:
                this.resolvedType = TypeBinding.CHAR;
                break;
            case 3:
                this.resolvedType = TypeBinding.BYTE;
                break;
            case 4:
            case 6:
            default:
                this.constant = Constant.NotAConstant;
                blockScope.problemReporter().invalidOperator(this, typeBinding, typeBindingResolveType);
                return;
            case 5:
                this.resolvedType = TypeBinding.BOOLEAN;
                break;
            case 7:
                this.resolvedType = TypeBinding.LONG;
                break;
            case 8:
                this.resolvedType = TypeBinding.DOUBLE;
                break;
            case 9:
                this.resolvedType = TypeBinding.FLOAT;
                break;
            case 10:
                this.resolvedType = TypeBinding.INT;
                break;
            case 11:
                this.resolvedType = blockScope.getJavaLangString();
                break;
        }
        Expression expression2 = this.left;
        boolean z2 = expression2 instanceof CastExpression;
        if (z2 || z) {
            CastExpression.checkNeedForArgumentCasts(blockScope, i5, i6, expression2, i2, z2, this.right, i, z);
        }
        computeConstant(blockScope, i2, i);
    }

    public void optimizedBooleanConstant(int i, int i2, int i3) {
        if (i2 != 0) {
            if (i2 != 1) {
                if (i2 != 2) {
                    if (i2 != 3 || i != 5 || i3 != 5) {
                        return;
                    }
                } else if (i != 5 || i3 != 5) {
                    return;
                }
            }
            Constant constantOptimizedBooleanConstant = this.left.optimizedBooleanConstant();
            if (constantOptimizedBooleanConstant != Constant.NotAConstant) {
                if (constantOptimizedBooleanConstant.booleanValue()) {
                    this.optimizedBooleanConstant = constantOptimizedBooleanConstant;
                    return;
                }
                Constant constantOptimizedBooleanConstant2 = this.right.optimizedBooleanConstant();
                if (constantOptimizedBooleanConstant2 != Constant.NotAConstant) {
                    this.optimizedBooleanConstant = constantOptimizedBooleanConstant2;
                    return;
                }
                return;
            }
            Constant constantOptimizedBooleanConstant3 = this.right.optimizedBooleanConstant();
            if (constantOptimizedBooleanConstant3 == Constant.NotAConstant || !constantOptimizedBooleanConstant3.booleanValue()) {
                return;
            }
            this.optimizedBooleanConstant = constantOptimizedBooleanConstant3;
            return;
        }
        Constant constantOptimizedBooleanConstant4 = this.left.optimizedBooleanConstant();
        if (constantOptimizedBooleanConstant4 != Constant.NotAConstant) {
            if (!constantOptimizedBooleanConstant4.booleanValue()) {
                this.optimizedBooleanConstant = constantOptimizedBooleanConstant4;
                return;
            }
            Constant constantOptimizedBooleanConstant5 = this.right.optimizedBooleanConstant();
            if (constantOptimizedBooleanConstant5 != Constant.NotAConstant) {
                this.optimizedBooleanConstant = constantOptimizedBooleanConstant5;
                return;
            }
            return;
        }
        Constant constantOptimizedBooleanConstant6 = this.right.optimizedBooleanConstant();
        if (constantOptimizedBooleanConstant6 == Constant.NotAConstant || constantOptimizedBooleanConstant6.booleanValue()) {
            return;
        }
        this.optimizedBooleanConstant = constantOptimizedBooleanConstant6;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.OperatorExpression
    public StringBuffer printExpressionNoParenthesis(int i, StringBuffer stringBuffer) {
        this.left.printExpression(i, stringBuffer).append(StringUtil.SPACE).append(operatorToString()).append(StringUtil.SPACE);
        return this.right.printExpression(0, stringBuffer);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        int i;
        int i2;
        Expression expression = this.left;
        boolean z = expression instanceof CastExpression;
        if (z) {
            expression.bits |= 32;
        }
        TypeBinding typeBindingResolveType = this.left.resolveType(blockScope);
        Expression expression2 = this.right;
        boolean z2 = expression2 instanceof CastExpression;
        if (z2) {
            expression2.bits |= 32;
        }
        TypeBinding typeBindingResolveType2 = this.right.resolveType(blockScope);
        if (typeBindingResolveType == null || typeBindingResolveType2 == null) {
            this.constant = Constant.NotAConstant;
            return null;
        }
        int i3 = typeBindingResolveType.f626id;
        int i4 = typeBindingResolveType2.f626id;
        if (blockScope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_5) {
            if (!typeBindingResolveType.isBaseType() && i4 != 11 && i4 != 12) {
                i3 = blockScope.environment().computeBoxingType(typeBindingResolveType).f626id;
            }
            if (!typeBindingResolveType2.isBaseType() && i3 != 11 && i3 != 12) {
                i4 = blockScope.environment().computeBoxingType(typeBindingResolveType2).f626id;
            }
        }
        if (i3 <= 15 && i4 <= 15) {
            i2 = i3;
            i = i4;
        } else if (i3 == 11) {
            i2 = i3;
            i = 1;
        } else {
            if (i4 != 11) {
                this.constant = Constant.NotAConstant;
                blockScope.problemReporter().invalidOperator(this, typeBindingResolveType, typeBindingResolveType2);
                return null;
            }
            i = i4;
            i2 = 1;
        }
        if (((this.bits & ASTNode.OperatorMASK) >> 6) == 14) {
            if (i2 == 11) {
                this.left.computeConversion(blockScope, typeBindingResolveType, typeBindingResolveType);
                if (typeBindingResolveType2.isArrayType() && TypeBinding.equalsEquals(((ArrayBinding) typeBindingResolveType2).elementsType(), TypeBinding.CHAR)) {
                    blockScope.problemReporter().signalNoImplicitStringConversionForCharArrayExpression(this.right);
                }
            }
            if (i == 11) {
                this.right.computeConversion(blockScope, typeBindingResolveType2, typeBindingResolveType2);
                if (typeBindingResolveType.isArrayType() && TypeBinding.equalsEquals(((ArrayBinding) typeBindingResolveType).elementsType(), TypeBinding.CHAR)) {
                    blockScope.problemReporter().signalNoImplicitStringConversionForCharArrayExpression(this.left);
                }
            }
        }
        int i5 = (this.bits & ASTNode.OperatorMASK) >> 6;
        int i6 = OperatorExpression.OperatorSignatures[i5][(i2 << 4) + i];
        this.left.computeConversion(blockScope, TypeBinding.wellKnownType(blockScope, (i6 >>> 16) & 15), typeBindingResolveType);
        this.right.computeConversion(blockScope, TypeBinding.wellKnownType(blockScope, 15 & (i6 >>> 8)), typeBindingResolveType2);
        int i7 = i6 & 15;
        this.bits |= i7;
        switch (i7) {
            case 2:
                this.resolvedType = TypeBinding.CHAR;
                break;
            case 3:
                this.resolvedType = TypeBinding.BYTE;
                break;
            case 4:
            case 6:
            default:
                this.constant = Constant.NotAConstant;
                blockScope.problemReporter().invalidOperator(this, typeBindingResolveType, typeBindingResolveType2);
                return null;
            case 5:
                this.resolvedType = TypeBinding.BOOLEAN;
                break;
            case 7:
                this.resolvedType = TypeBinding.LONG;
                break;
            case 8:
                this.resolvedType = TypeBinding.DOUBLE;
                break;
            case 9:
                this.resolvedType = TypeBinding.FLOAT;
                break;
            case 10:
                this.resolvedType = TypeBinding.INT;
                break;
            case 11:
                this.resolvedType = blockScope.getJavaLangString();
                break;
        }
        if (z || z2) {
            CastExpression.checkNeedForArgumentCasts(blockScope, i5, i6, this.left, i2, z, this.right, i, z2);
        }
        computeConstant(blockScope, i2, i);
        return this.resolvedType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.left.traverse(aSTVisitor, blockScope);
            this.right.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }
}
