package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ArrayReference extends Reference {
    public Expression position;
    public Expression receiver;

    public ArrayReference(Expression expression, Expression expression2) {
        this.receiver = expression;
        this.position = expression2;
        this.sourceStart = expression.sourceStart;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public FlowInfo analyseAssignment(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, Assignment assignment, boolean z) {
        int iNullStatus;
        flowContext.recordAbruptExit();
        if (assignment.expression == null) {
            return analyseCode(blockScope, flowContext, flowInfo);
        }
        FlowInfo flowInfoAnalyseCode = assignment.expression.analyseCode(blockScope, flowContext, analyseCode(blockScope, flowContext, flowInfo).unconditionalInits());
        if ((this.resolvedType.tagBits & 72057594037927936L) != 0 && (iNullStatus = assignment.expression.nullStatus(flowInfoAnalyseCode, flowContext)) != 4) {
            blockScope.problemReporter().nullityMismatch(this, assignment.expression.resolvedType, this.resolvedType, iNullStatus, blockScope.environment().getNonNullAnnotationName());
        }
        return flowInfoAnalyseCode;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        this.receiver.checkNPE(blockScope, flowContext, flowInfo, 1);
        FlowInfo flowInfoAnalyseCode = this.position.analyseCode(blockScope, flowContext, this.receiver.analyseCode(blockScope, flowContext, flowInfo));
        this.position.checkNPEbyUnboxing(blockScope, flowContext, flowInfoAnalyseCode);
        flowContext.recordAbruptExit();
        return flowInfoAnalyseCode;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference, org.eclipse.jdt.internal.compiler.ast.Expression
    public boolean checkNPE(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, int i) {
        if ((this.resolvedType.tagBits & 36028797018963968L) != 0) {
            blockScope.problemReporter().arrayReferencePotentialNullReference(this);
            return true;
        }
        return super.checkNPE(blockScope, flowContext, flowInfo, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public void generateAssignment(BlockScope blockScope, CodeStream codeStream, Assignment assignment, boolean z) {
        int i = codeStream.position;
        this.receiver.generateCode(blockScope, codeStream, true);
        Expression expression = this.receiver;
        if ((expression instanceof CastExpression) && ((CastExpression) expression).innermostCastedExpression().resolvedType == TypeBinding.NULL) {
            codeStream.checkcast(this.receiver.resolvedType);
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
        this.position.generateCode(blockScope, codeStream, true);
        assignment.expression.generateCode(blockScope, codeStream, true);
        codeStream.arrayAtPut(this.resolvedType.f626id, z);
        if (z) {
            codeStream.generateImplicitConversion(assignment.implicitConversion);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i = codeStream.position;
        this.receiver.generateCode(blockScope, codeStream, true);
        Expression expression = this.receiver;
        if ((expression instanceof CastExpression) && ((CastExpression) expression).innermostCastedExpression().resolvedType == TypeBinding.NULL) {
            codeStream.checkcast(this.receiver.resolvedType);
        }
        this.position.generateCode(blockScope, codeStream, true);
        codeStream.arrayAt(this.resolvedType.f626id);
        if (z) {
            codeStream.generateImplicitConversion(this.implicitConversion);
        } else {
            boolean z2 = (this.implicitConversion & 1024) != 0;
            if (z2) {
                codeStream.generateImplicitConversion(this.implicitConversion);
            }
            int i2 = (z2 ? postConversionType(blockScope) : this.resolvedType).f626id;
            if (i2 == 7 || i2 == 8) {
                codeStream.pop2();
            } else {
                codeStream.pop();
            }
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public void generateCompoundAssignment(BlockScope blockScope, CodeStream codeStream, Expression expression, int i, int i2, boolean z) {
        this.receiver.generateCode(blockScope, codeStream, true);
        Expression expression2 = this.receiver;
        if ((expression2 instanceof CastExpression) && ((CastExpression) expression2).innermostCastedExpression().resolvedType == TypeBinding.NULL) {
            codeStream.checkcast(this.receiver.resolvedType);
        }
        this.position.generateCode(blockScope, codeStream, true);
        codeStream.dup2();
        codeStream.arrayAt(this.resolvedType.f626id);
        int i3 = (this.implicitConversion & 255) >> 4;
        if (i3 == 0 || i3 == 1 || i3 == 11) {
            codeStream.generateStringConcatenationAppend(blockScope, null, expression);
        } else {
            codeStream.generateImplicitConversion(this.implicitConversion);
            if (expression == IntLiteral.One) {
                codeStream.generateConstant(expression.constant, this.implicitConversion);
            } else {
                expression.generateCode(blockScope, codeStream, true);
            }
            codeStream.sendOperator(i, i3);
            codeStream.generateImplicitConversion(i2);
        }
        codeStream.arrayAtPut(this.resolvedType.f626id, z);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public void generatePostIncrement(BlockScope blockScope, CodeStream codeStream, CompoundAssignment compoundAssignment, boolean z) {
        this.receiver.generateCode(blockScope, codeStream, true);
        Expression expression = this.receiver;
        if ((expression instanceof CastExpression) && ((CastExpression) expression).innermostCastedExpression().resolvedType == TypeBinding.NULL) {
            codeStream.checkcast(this.receiver.resolvedType);
        }
        this.position.generateCode(blockScope, codeStream, true);
        codeStream.dup2();
        codeStream.arrayAt(this.resolvedType.f626id);
        if (z) {
            int i = this.resolvedType.f626id;
            if (i == 7 || i == 8) {
                codeStream.dup2_x2();
            } else {
                codeStream.dup_x2();
            }
        }
        codeStream.generateImplicitConversion(this.implicitConversion);
        codeStream.generateConstant(compoundAssignment.expression.constant, this.implicitConversion);
        codeStream.sendOperator(compoundAssignment.operator, this.implicitConversion & 15);
        codeStream.generateImplicitConversion(compoundAssignment.preAssignImplicitConversion);
        codeStream.arrayAtPut(this.resolvedType.f626id, false);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        this.receiver.printExpression(0, stringBuffer).append('[');
        return this.position.printExpression(0, stringBuffer).append(']');
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        this.constant = Constant.NotAConstant;
        Expression expression = this.receiver;
        if ((expression instanceof CastExpression) && (((CastExpression) expression).innermostCastedExpression() instanceof NullLiteral)) {
            this.receiver.bits |= 32;
        }
        TypeBinding typeBindingResolveType = this.receiver.resolveType(blockScope);
        if (typeBindingResolveType != null) {
            this.receiver.computeConversion(blockScope, typeBindingResolveType, typeBindingResolveType);
            if (typeBindingResolveType.isArrayType()) {
                TypeBinding typeBindingElementsType = ((ArrayBinding) typeBindingResolveType).elementsType();
                if ((this.bits & 8192) == 0) {
                    typeBindingElementsType = typeBindingElementsType.capture(blockScope, this.sourceStart, this.sourceEnd);
                }
                this.resolvedType = typeBindingElementsType;
            } else {
                blockScope.problemReporter().referenceMustBeArrayTypeAt(typeBindingResolveType, this);
            }
        }
        TypeBinding typeBindingResolveTypeExpecting = this.position.resolveTypeExpecting(blockScope, TypeBinding.INT);
        if (typeBindingResolveTypeExpecting != null) {
            this.position.computeConversion(blockScope, TypeBinding.INT, typeBindingResolveTypeExpecting);
        }
        return this.resolvedType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.receiver.traverse(aSTVisitor, blockScope);
            this.position.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }
}
