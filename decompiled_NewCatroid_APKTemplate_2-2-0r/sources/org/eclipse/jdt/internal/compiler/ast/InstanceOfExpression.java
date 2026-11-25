package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BaseTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class InstanceOfExpression extends OperatorExpression {
    public Expression expression;
    public TypeReference type;

    public InstanceOfExpression(Expression expression, TypeReference typeReference) {
        this.expression = expression;
        this.type = typeReference;
        typeReference.bits |= 1073741824;
        this.bits |= 1984;
        this.sourceStart = expression.sourceStart;
        this.sourceEnd = typeReference.sourceEnd;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        FieldBinding fieldBindingLastFieldBinding;
        LocalVariableBinding localVariableBinding = this.expression.localVariableBinding();
        if (localVariableBinding != null && (localVariableBinding.type.tagBits & 2) == 0) {
            UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits = this.expression.analyseCode(blockScope, flowContext, flowInfo).unconditionalInits();
            FlowInfo flowInfoCopy = unconditionalFlowInfoUnconditionalInits.copy();
            flowInfoCopy.markAsComparedEqualToNonNull(localVariableBinding);
            flowContext.recordUsingNullReference(blockScope, localVariableBinding, this.expression, 1025, unconditionalFlowInfoUnconditionalInits);
            return FlowInfo.conditional(flowInfoCopy, unconditionalFlowInfoUnconditionalInits.copy());
        }
        if ((this.expression instanceof Reference) && blockScope.compilerOptions().enableSyntacticNullAnalysisForFields && (fieldBindingLastFieldBinding = ((Reference) this.expression).lastFieldBinding()) != null && (fieldBindingLastFieldBinding.type.tagBits & 2) == 0) {
            flowContext.recordNullCheckedFieldReference((Reference) this.expression, 1);
        }
        return this.expression.analyseCode(blockScope, flowContext, flowInfo).unconditionalInits();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i = codeStream.position;
        this.expression.generateCode(blockScope, codeStream, true);
        TypeReference typeReference = this.type;
        codeStream.instance_of(typeReference, typeReference.resolvedType);
        if (z) {
            codeStream.generateImplicitConversion(this.implicitConversion);
        } else {
            codeStream.pop();
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.OperatorExpression
    public StringBuffer printExpressionNoParenthesis(int i, StringBuffer stringBuffer) {
        this.expression.printExpression(i, stringBuffer).append(" instanceof ");
        return this.type.print(0, stringBuffer);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        this.constant = Constant.NotAConstant;
        TypeBinding typeBindingResolveType = this.expression.resolveType(blockScope);
        TypeBinding typeBindingResolveType2 = this.type.resolveType(blockScope, true);
        if (typeBindingResolveType != null && typeBindingResolveType2 != null && this.type.hasNullTypeAnnotation(TypeReference.AnnotationPosition.ANY) && (!typeBindingResolveType.isCompatibleWith(typeBindingResolveType2) || NullAnnotationMatching.analyse(typeBindingResolveType2, typeBindingResolveType, -1).isAnyMismatch())) {
            blockScope.problemReporter().nullAnnotationUnsupportedLocation(this.type);
        }
        if (typeBindingResolveType == null || typeBindingResolveType2 == null) {
            return null;
        }
        if (!typeBindingResolveType2.isReifiable()) {
            blockScope.problemReporter().illegalInstanceOfGenericType(typeBindingResolveType2, this);
        } else if (typeBindingResolveType2.isValidBinding() && ((typeBindingResolveType != TypeBinding.NULL && typeBindingResolveType.isBaseType()) || !checkCastTypesCompatibility(blockScope, typeBindingResolveType2, typeBindingResolveType, null))) {
            blockScope.problemReporter().notCompatibleTypesError(this, typeBindingResolveType, typeBindingResolveType2);
        }
        BaseTypeBinding baseTypeBinding = TypeBinding.BOOLEAN;
        this.resolvedType = baseTypeBinding;
        return baseTypeBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void tagAsUnnecessaryCast(Scope scope, TypeBinding typeBinding) {
        if (this.expression.resolvedType != TypeBinding.NULL) {
            scope.problemReporter().unnecessaryInstanceof(this, typeBinding);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.expression.traverse(aSTVisitor, blockScope);
            this.type.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }
}
