package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.BooleanConstant;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class UnaryExpression extends OperatorExpression {
    public Expression expression;
    public Constant optimizedBooleanConstant;

    public UnaryExpression(Expression expression, int i) {
        this.expression = expression;
        this.bits |= i << 6;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        this.expression.checkNPE(blockScope, flowContext, flowInfo);
        if (((this.bits & ASTNode.OperatorMASK) >> 6) == 11) {
            flowContext.tagBits ^= 4;
            FlowInfo flowInfoAsNegatedCondition = this.expression.analyseCode(blockScope, flowContext, flowInfo).asNegatedCondition();
            flowContext.tagBits ^= 4;
            return flowInfoAsNegatedCondition;
        }
        return this.expression.analyseCode(blockScope, flowContext, flowInfo);
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
            case 11:
                if (((this.expression.implicitConversion & 255) >> 4) == 5) {
                    Expression expression = this.expression;
                    BranchLabel branchLabel = new BranchLabel(codeStream);
                    expression.generateOptimizedBoolean(blockScope, codeStream, null, branchLabel, z);
                    if (z) {
                        codeStream.iconst_0();
                        if (branchLabel.forwardReferenceCount() > 0) {
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
                break;
            case 12:
                int i2 = (this.expression.implicitConversion & 255) >> 4;
                if (i2 == 7) {
                    this.expression.generateCode(blockScope, codeStream, z);
                    if (z) {
                        codeStream.ldc2_w(-1L);
                        codeStream.lxor();
                    }
                } else if (i2 == 10) {
                    this.expression.generateCode(blockScope, codeStream, z);
                    if (z) {
                        codeStream.iconst_m1();
                        codeStream.ixor();
                    }
                }
                break;
            case 13:
                if (this.constant != Constant.NotAConstant) {
                    if (z) {
                        switch ((this.expression.implicitConversion & 255) >> 4) {
                            case 7:
                                codeStream.generateInlinedValue(this.constant.longValue() * (-1));
                                break;
                            case 8:
                                codeStream.generateInlinedValue(this.constant.doubleValue() * (-1.0d));
                                break;
                            case 9:
                                codeStream.generateInlinedValue(this.constant.floatValue() * (-1.0f));
                                break;
                            case 10:
                                codeStream.generateInlinedValue(this.constant.intValue() * (-1));
                                break;
                        }
                    }
                } else {
                    this.expression.generateCode(blockScope, codeStream, z);
                    if (z) {
                        switch ((this.expression.implicitConversion & 255) >> 4) {
                            case 7:
                                codeStream.lneg();
                                break;
                            case 8:
                                codeStream.dneg();
                                break;
                            case 9:
                                codeStream.fneg();
                                break;
                            case 10:
                                codeStream.ineg();
                                break;
                        }
                    }
                }
                break;
            case 14:
                this.expression.generateCode(blockScope, codeStream, z);
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
        } else if (((this.bits & ASTNode.OperatorMASK) >> 6) == 11) {
            this.expression.generateOptimizedBoolean(blockScope, codeStream, branchLabel2, branchLabel, z);
        } else {
            super.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, z);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.OperatorExpression
    public StringBuffer printExpressionNoParenthesis(int i, StringBuffer stringBuffer) {
        stringBuffer.append(operatorToString()).append(StringUtil.SPACE);
        return this.expression.printExpression(0, stringBuffer);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        int i;
        Constant constantOptimizedBooleanConstant;
        Expression expression = this.expression;
        boolean z = expression instanceof CastExpression;
        if (z) {
            expression.bits |= 32;
        }
        TypeBinding typeBindingResolveType = this.expression.resolveType(blockScope);
        if (typeBindingResolveType == null) {
            this.constant = Constant.NotAConstant;
            return null;
        }
        int i2 = typeBindingResolveType.f626id;
        if (blockScope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_5 && !typeBindingResolveType.isBaseType()) {
            i2 = blockScope.environment().computeBoxingType(typeBindingResolveType).f626id;
        }
        if (i2 > 15) {
            this.constant = Constant.NotAConstant;
            blockScope.problemReporter().invalidOperator(this, typeBindingResolveType);
            return null;
        }
        int i3 = (this.bits & ASTNode.OperatorMASK) >> 6;
        if (i3 != 11) {
            i = i3 != 12 ? 13 : 10;
        } else {
            i = 0;
        }
        int i4 = OperatorSignatures[i][(i2 << 4) + i2];
        this.expression.computeConversion(blockScope, TypeBinding.wellKnownType(blockScope, 15 & (i4 >>> 16)), typeBindingResolveType);
        int i5 = i4 & 15;
        this.bits |= i5;
        switch (i5) {
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
                if (i2 != 0) {
                    blockScope.problemReporter().invalidOperator(this, typeBindingResolveType);
                }
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
        }
        if (this.expression.constant != Constant.NotAConstant) {
            this.constant = Constant.computeConstantOperation(this.expression.constant, i2, (this.bits & ASTNode.OperatorMASK) >> 6);
        } else {
            this.constant = Constant.NotAConstant;
            if (((this.bits & ASTNode.OperatorMASK) >> 6) == 11 && (constantOptimizedBooleanConstant = this.expression.optimizedBooleanConstant()) != Constant.NotAConstant) {
                this.optimizedBooleanConstant = BooleanConstant.fromValue(!constantOptimizedBooleanConstant.booleanValue());
            }
        }
        if (z) {
            CastExpression.checkNeedForArgumentCast(blockScope, i, i4, this.expression, i2);
        }
        return this.resolvedType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.expression.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }
}
