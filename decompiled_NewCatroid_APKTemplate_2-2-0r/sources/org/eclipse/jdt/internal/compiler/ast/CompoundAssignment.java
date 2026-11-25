package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class CompoundAssignment extends Assignment implements OperatorIds {
    public int operator;
    public int preAssignImplicitConversion;

    public boolean checkCastCompatibility() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Assignment, org.eclipse.jdt.internal.compiler.ast.Expression
    public int nullStatus(FlowInfo flowInfo, FlowContext flowContext) {
        return 4;
    }

    public boolean restrainUsageToNumericTypes() {
        return false;
    }

    public CompoundAssignment(Expression expression, Expression expression2, int i, int i2) {
        super(expression, expression2, i2);
        expression.bits &= -8193;
        expression.bits |= 65536;
        this.operator = i;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Assignment, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        LocalVariableBinding localVariableBinding;
        if (this.resolvedType.f626id != 11) {
            this.lhs.checkNPE(blockScope, flowContext, flowInfo);
            flowContext.recordAbruptExit();
        }
        UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits = ((Reference) this.lhs).analyseAssignment(blockScope, flowContext, flowInfo, this, true).unconditionalInits();
        if (this.resolvedType.f626id == 11 && (localVariableBinding = this.lhs.localVariableBinding()) != null) {
            unconditionalFlowInfoUnconditionalInits.markAsDefinitelyNonNull(localVariableBinding);
            flowContext.markFinallyNullStatus(localVariableBinding, 4);
        }
        return unconditionalFlowInfoUnconditionalInits;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Assignment, org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i = codeStream.position;
        ((Reference) this.lhs).generateCompoundAssignment(blockScope, codeStream, this.expression, this.operator, this.preAssignImplicitConversion, z);
        if (z) {
            codeStream.generateImplicitConversion(this.implicitConversion);
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    public String operatorToString() {
        int i = this.operator;
        if (i == 2) {
            return "&=";
        }
        if (i == 3) {
            return "|=";
        }
        if (i != 19) {
            switch (i) {
                case 8:
                    return "^=";
                case 9:
                    return "/=";
                case 10:
                    return "<<=";
                default:
                    switch (i) {
                        case 13:
                            return "-=";
                        case 14:
                            return "+=";
                        case 15:
                            return "*=";
                        case 16:
                            return "%=";
                        case 17:
                            return ">>=";
                        default:
                            return "unknown operator";
                    }
            }
        }
        return ">>>=";
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Assignment
    public StringBuffer printExpressionNoParenthesis(int i, StringBuffer stringBuffer) {
        this.lhs.printExpression(i, stringBuffer).append(StringUtil.SPACE).append(operatorToString()).append(StringUtil.SPACE);
        return this.expression.printExpression(0, stringBuffer);
    }

    /* JADX WARN: Code duplicated, block: B:29:0x006e  */
    @Override // org.eclipse.jdt.internal.compiler.ast.Assignment, org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        TypeBinding typeBindingComputeBoxingType;
        TypeBinding typeBindingComputeBoxingType2;
        boolean z;
        this.constant = Constant.NotAConstant;
        if (!(this.lhs instanceof Reference) || this.lhs.isThis()) {
            blockScope.problemReporter().expressionShouldBeAVariable(this.lhs);
            return null;
        }
        boolean z2 = this.expression instanceof CastExpression;
        if (z2) {
            this.expression.bits |= 32;
        }
        TypeBinding typeBindingResolveType = this.lhs.resolveType(blockScope);
        TypeBinding typeBindingResolveType2 = this.expression.resolveType(blockScope);
        if (typeBindingResolveType == null || typeBindingResolveType2 == null) {
            return null;
        }
        LookupEnvironment lookupEnvironmentEnvironment = blockScope.environment();
        if (blockScope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_5) {
            if (typeBindingResolveType.isBaseType() || typeBindingResolveType2.f626id == 11 || typeBindingResolveType2.f626id == 12) {
                typeBindingComputeBoxingType2 = typeBindingResolveType;
                z = false;
            } else {
                typeBindingComputeBoxingType2 = lookupEnvironmentEnvironment.computeBoxingType(typeBindingResolveType);
                if (TypeBinding.notEquals(typeBindingComputeBoxingType2, typeBindingResolveType)) {
                    z = true;
                } else {
                    typeBindingComputeBoxingType2 = typeBindingResolveType;
                    z = false;
                }
            }
            typeBindingComputeBoxingType = (typeBindingResolveType2.isBaseType() || typeBindingComputeBoxingType2.f626id == 11 || typeBindingComputeBoxingType2.f626id == 12) ? typeBindingResolveType2 : lookupEnvironmentEnvironment.computeBoxingType(typeBindingResolveType2);
        } else {
            typeBindingComputeBoxingType = typeBindingResolveType2;
            typeBindingComputeBoxingType2 = typeBindingResolveType;
            z = false;
        }
        if (restrainUsageToNumericTypes() && !typeBindingComputeBoxingType2.isNumericType()) {
            blockScope.problemReporter().operatorOnlyValidOnNumericType(this, typeBindingComputeBoxingType2, typeBindingComputeBoxingType);
            return null;
        }
        int i = typeBindingComputeBoxingType2.f626id;
        int i2 = typeBindingComputeBoxingType.f626id;
        if (i > 15 || i2 > 15) {
            if (i != 11) {
                blockScope.problemReporter().invalidOperator(this, typeBindingComputeBoxingType2, typeBindingComputeBoxingType);
                return null;
            }
            i2 = 1;
        }
        int[][] iArr = OperatorExpression.OperatorSignatures;
        int i3 = this.operator;
        int i4 = i << 4;
        int i5 = iArr[i3][i4 + i2];
        if (i5 == 0) {
            blockScope.problemReporter().invalidOperator(this, typeBindingComputeBoxingType2, typeBindingComputeBoxingType);
            return null;
        }
        if (i3 == 14) {
            if (i == 1 && blockScope.compilerOptions().complianceLevel < ClassFileConstants.JDK1_7) {
                blockScope.problemReporter().invalidOperator(this, typeBindingComputeBoxingType2, typeBindingComputeBoxingType);
                return null;
            }
            if ((typeBindingComputeBoxingType2.isNumericType() || i == 5) && !typeBindingComputeBoxingType.isNumericType()) {
                blockScope.problemReporter().invalidOperator(this, typeBindingComputeBoxingType2, typeBindingComputeBoxingType);
                return null;
            }
        }
        int i6 = i5 & 15;
        TypeBinding typeBindingWellKnownType = TypeBinding.wellKnownType(blockScope, i6);
        if (checkCastCompatibility() && typeBindingResolveType.f626id != 11 && typeBindingWellKnownType.f626id != 11 && !checkCastTypesCompatibility(blockScope, typeBindingResolveType, typeBindingWellKnownType, null)) {
            blockScope.problemReporter().invalidOperator(this, typeBindingResolveType, typeBindingComputeBoxingType);
            return null;
        }
        this.lhs.computeConversion(blockScope, TypeBinding.wellKnownType(blockScope, (i5 >>> 16) & 15), typeBindingResolveType);
        this.expression.computeConversion(blockScope, TypeBinding.wellKnownType(blockScope, (i5 >>> 8) & 15), typeBindingResolveType2);
        this.preAssignImplicitConversion = (z ? 512 : 0) | i4 | i6;
        if (z) {
            blockScope.problemReporter().autoboxing(this, typeBindingComputeBoxingType2, typeBindingResolveType);
        }
        if (z2) {
            CastExpression.checkNeedForArgumentCasts(blockScope, this.operator, i5, this.lhs, typeBindingResolveType.f626id, false, this.expression, typeBindingResolveType2.f626id, true);
        }
        this.resolvedType = typeBindingResolveType;
        return typeBindingResolveType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Assignment, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.lhs.traverse(aSTVisitor, blockScope);
            this.expression.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }
}
