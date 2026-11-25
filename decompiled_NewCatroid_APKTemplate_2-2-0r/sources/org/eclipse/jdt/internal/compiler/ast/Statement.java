package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public abstract class Statement extends ASTNode {
    public static final int COMPLAINED_FAKE_REACHABLE = 1;
    public static final int COMPLAINED_UNREACHABLE = 2;
    public static final int NOT_COMPLAINED = 0;

    public abstract FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo);

    public void branchChainTo(BranchLabel branchLabel) {
    }

    public boolean completesByContinue() {
        return false;
    }

    public boolean doesNotCompleteNormally() {
        return false;
    }

    public abstract void generateCode(BlockScope blockScope, CodeStream codeStream);

    public TypeBinding invocationTargetType() {
        return null;
    }

    public boolean isEmptyBlock() {
        return false;
    }

    public boolean isValidJavaStatement() {
        return true;
    }

    public abstract StringBuffer printStatement(int i, StringBuffer stringBuffer);

    public abstract void resolve(BlockScope blockScope);

    protected static boolean isKnowDeadCodePattern(Expression expression) {
        if (expression instanceof UnaryExpression) {
            expression = ((UnaryExpression) expression).expression;
        }
        return expression instanceof Reference;
    }

    protected void analyseArguments(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, MethodBinding methodBinding, Expression[] expressionArr) {
        int i;
        TypeBinding typeBinding;
        if (expressionArr != null) {
            if (blockScope.compilerOptions().sourceLevel < ClassFileConstants.JDK1_7 || !methodBinding.isPolymorphic()) {
                boolean zUsesNullTypeAnnotations = blockScope.environment().usesNullTypeAnnotations();
                int i2 = 0;
                boolean z = true;
                boolean z2 = methodBinding.parameterNonNullness != null;
                int length = methodBinding.parameters.length;
                if ((zUsesNullTypeAnnotations || z2) && methodBinding.isVarargs()) {
                    i = length - 1;
                    if (length == expressionArr.length) {
                        typeBinding = methodBinding.parameters[i];
                        TypeBinding typeBinding2 = expressionArr[i].resolvedType;
                        if (typeBinding2 != TypeBinding.NULL && (typeBinding.dimensions() != typeBinding2.dimensions() || !typeBinding2.isCompatibleWith(typeBinding))) {
                            z = false;
                        }
                    } else {
                        z = false;
                        typeBinding = null;
                    }
                    if (!z) {
                        length--;
                    }
                } else {
                    i = -1;
                    z = false;
                    typeBinding = null;
                }
                if (!zUsesNullTypeAnnotations) {
                    if (z2) {
                        while (i2 < length) {
                            if (methodBinding.parameterNonNullness[i2] == Boolean.TRUE) {
                                TypeBinding typeBinding3 = methodBinding.parameters[i2];
                                Expression expression = expressionArr[i2];
                                int iNullStatus = expression.nullStatus(flowInfo, flowContext);
                                if (iNullStatus != 4) {
                                    flowContext.recordNullityMismatch(blockScope, expression, expression.resolvedType, typeBinding3, flowInfo, iNullStatus, null);
                                }
                            }
                            i2++;
                        }
                        return;
                    }
                    return;
                }
                while (i2 < length) {
                    analyseOneArgument18(blockScope, flowContext, flowInfo, methodBinding.parameters[i2], expressionArr[i2], z2 ? methodBinding.parameterNonNullness[i2] : null, methodBinding.original().parameters[i2]);
                    i2++;
                }
                if (z || !(typeBinding instanceof ArrayBinding)) {
                    return;
                }
                TypeBinding typeBindingElementsType = ((ArrayBinding) typeBinding).elementsType();
                Boolean bool = z2 ? methodBinding.parameterNonNullness[i] : null;
                while (length < expressionArr.length) {
                    analyseOneArgument18(blockScope, flowContext, flowInfo, typeBindingElementsType, expressionArr[length], bool, methodBinding.original().parameters[i]);
                    length++;
                }
            }
        }
    }

    void analyseOneArgument18(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, TypeBinding typeBinding, Expression expression, Boolean bool, TypeBinding typeBinding2) {
        if ((expression instanceof ConditionalExpression) && expression.isPolyExpression()) {
            ConditionalExpression conditionalExpression = (ConditionalExpression) expression;
            conditionalExpression.internalAnalyseOneArgument18(blockScope, flowContext, typeBinding, conditionalExpression.valueIfTrue, flowInfo, conditionalExpression.ifTrueNullStatus, bool, typeBinding2);
            conditionalExpression.internalAnalyseOneArgument18(blockScope, flowContext, typeBinding, conditionalExpression.valueIfFalse, flowInfo, conditionalExpression.ifFalseNullStatus, bool, typeBinding2);
            return;
        }
        internalAnalyseOneArgument18(blockScope, flowContext, typeBinding, expression, flowInfo, expression.nullStatus(flowInfo, flowContext), bool, typeBinding2);
    }

    void internalAnalyseOneArgument18(BlockScope blockScope, FlowContext flowContext, TypeBinding typeBinding, Expression expression, FlowInfo flowInfo, int i, Boolean bool, TypeBinding typeBinding2) {
        TypeBinding typeBindingCreateAnnotatedType;
        int i2 = bool == Boolean.TRUE ? i : 0;
        TypeBinding typeBinding3 = typeBinding;
        NullAnnotationMatching nullAnnotationMatchingAnalyse = NullAnnotationMatching.analyse(typeBinding, expression.resolvedType, i);
        if (!nullAnnotationMatchingAnalyse.isAnyMismatch() && i2 != 0) {
            typeBinding3 = typeBinding2;
        }
        if (i2 == 2) {
            blockScope.problemReporter().nullityMismatchingTypeAnnotation(expression, expression.resolvedType, typeBinding3, nullAnnotationMatchingAnalyse);
            return;
        }
        if (nullAnnotationMatchingAnalyse.isAnyMismatch() || (i2 & 16) != 0) {
            if (typeBinding3.hasNullTypeAnnotations() || bool != Boolean.TRUE) {
                typeBindingCreateAnnotatedType = typeBinding3;
            } else {
                LookupEnvironment lookupEnvironmentEnvironment = blockScope.environment();
                typeBindingCreateAnnotatedType = lookupEnvironmentEnvironment.createAnnotatedType(typeBinding3, new AnnotationBinding[]{lookupEnvironmentEnvironment.getNonNullAnnotation()});
            }
            flowContext.recordNullityMismatch(blockScope, expression, expression.resolvedType, typeBindingCreateAnnotatedType, flowInfo, i, nullAnnotationMatchingAnalyse);
        }
    }

    protected void checkAgainstNullTypeAnnotation(BlockScope blockScope, TypeBinding typeBinding, Expression expression, FlowContext flowContext, FlowInfo flowInfo) {
        if ((expression instanceof ConditionalExpression) && expression.isPolyExpression()) {
            ConditionalExpression conditionalExpression = (ConditionalExpression) expression;
            internalCheckAgainstNullTypeAnnotation(blockScope, typeBinding, conditionalExpression.valueIfTrue, conditionalExpression.ifTrueNullStatus, flowContext, flowInfo);
            internalCheckAgainstNullTypeAnnotation(blockScope, typeBinding, conditionalExpression.valueIfFalse, conditionalExpression.ifFalseNullStatus, flowContext, flowInfo);
            return;
        }
        internalCheckAgainstNullTypeAnnotation(blockScope, typeBinding, expression, expression.nullStatus(flowInfo, flowContext), flowContext, flowInfo);
    }

    private void internalCheckAgainstNullTypeAnnotation(BlockScope blockScope, TypeBinding typeBinding, Expression expression, int i, FlowContext flowContext, FlowInfo flowInfo) {
        NullAnnotationMatching nullAnnotationMatchingAnalyse = NullAnnotationMatching.analyse(typeBinding, expression.resolvedType, null, null, i, expression, NullAnnotationMatching.CheckMode.COMPATIBLE);
        if (nullAnnotationMatchingAnalyse.isDefiniteMismatch()) {
            blockScope.problemReporter().nullityMismatchingTypeAnnotation(expression, expression.resolvedType, typeBinding, nullAnnotationMatchingAnalyse);
            return;
        }
        if (nullAnnotationMatchingAnalyse.wantToReport()) {
            nullAnnotationMatchingAnalyse.report(blockScope);
        }
        if (nullAnnotationMatchingAnalyse.isUnchecked()) {
            flowContext.recordNullityMismatch(blockScope, expression, expression.resolvedType, typeBinding, flowInfo, i, nullAnnotationMatchingAnalyse);
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [org.eclipse.jdt.internal.compiler.ast.Statement$1] */
    public boolean breaksOut(final char[] cArr) {
        return new ASTVisitor() { // from class: org.eclipse.jdt.internal.compiler.ast.Statement.1
            boolean breaksOut;

            @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
            public boolean visit(TypeDeclaration typeDeclaration, BlockScope blockScope) {
                return cArr != null;
            }

            @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
            public boolean visit(TypeDeclaration typeDeclaration, ClassScope classScope) {
                return cArr != null;
            }

            @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
            public boolean visit(LambdaExpression lambdaExpression, BlockScope blockScope) {
                return cArr != null;
            }

            @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
            public boolean visit(WhileStatement whileStatement, BlockScope blockScope) {
                return cArr != null;
            }

            @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
            public boolean visit(DoStatement doStatement, BlockScope blockScope) {
                return cArr != null;
            }

            @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
            public boolean visit(ForeachStatement foreachStatement, BlockScope blockScope) {
                return cArr != null;
            }

            @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
            public boolean visit(ForStatement forStatement, BlockScope blockScope) {
                return cArr != null;
            }

            @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
            public boolean visit(SwitchStatement switchStatement, BlockScope blockScope) {
                return cArr != null;
            }

            @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
            public boolean visit(BreakStatement breakStatement, BlockScope blockScope) {
                char[] cArr2 = cArr;
                if (cArr2 != null && !CharOperation.equals(cArr2, breakStatement.label)) {
                    return false;
                }
                this.breaksOut = true;
                return false;
            }

            public boolean breaksOut() {
                Statement.this.traverse(this, null);
                return this.breaksOut;
            }
        }.breaksOut();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [org.eclipse.jdt.internal.compiler.ast.Statement$2] */
    public boolean continuesAtOuterLabel() {
        return new ASTVisitor() { // from class: org.eclipse.jdt.internal.compiler.ast.Statement.2
            boolean continuesToLabel;

            @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
            public boolean visit(ContinueStatement continueStatement, BlockScope blockScope) {
                if (continueStatement.label == null) {
                    return false;
                }
                this.continuesToLabel = true;
                return false;
            }

            public boolean continuesAtOuterLabel() {
                Statement.this.traverse(this, null);
                return this.continuesToLabel;
            }
        }.continuesAtOuterLabel();
    }

    public int complainIfUnreachable(FlowInfo flowInfo, BlockScope blockScope, int i, boolean z) {
        if ((flowInfo.reachMode() & 3) == 0) {
            return i;
        }
        if ((flowInfo.reachMode() & 1) != 0) {
            this.bits &= Integer.MAX_VALUE;
        }
        if (flowInfo == FlowInfo.DEAD_END) {
            if (i < 2) {
                blockScope.problemReporter().unreachableCode(this);
                if (z) {
                    blockScope.checkUnclosedCloseables(flowInfo, null, null, null);
                }
            }
            return 2;
        }
        if (i < 1) {
            blockScope.problemReporter().fakeReachable(this);
            if (z) {
                blockScope.checkUnclosedCloseables(flowInfo, null, null, null);
            }
        }
        return 1;
    }

    public void generateArguments(MethodBinding methodBinding, Expression[] expressionArr, BlockScope blockScope, CodeStream codeStream) {
        if (!methodBinding.isVarargs()) {
            if (expressionArr != null) {
                for (Expression expression : expressionArr) {
                    expression.generateCode(blockScope, codeStream, true);
                }
                return;
            }
            return;
        }
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        int length = typeBindingArr.length;
        int i = length - 1;
        for (int i2 = 0; i2 < i; i2++) {
            expressionArr[i2].generateCode(blockScope, codeStream, true);
        }
        ArrayBinding arrayBinding = (ArrayBinding) typeBindingArr[i];
        ArrayBinding arrayBinding2 = (ArrayBinding) methodBinding.parameters[i].erasure();
        int i3 = arrayBinding.elementsType().f626id;
        int length2 = expressionArr == null ? 0 : expressionArr.length;
        if (length2 > length) {
            codeStream.generateInlinedValue(length2 - i);
            codeStream.newArray(arrayBinding2);
            for (int i4 = i; i4 < length2; i4++) {
                codeStream.dup();
                codeStream.generateInlinedValue(i4 - i);
                expressionArr[i4].generateCode(blockScope, codeStream, true);
                codeStream.arrayAtPut(i3, false);
            }
            return;
        }
        if (length2 == length) {
            TypeBinding typeBinding = expressionArr[i].resolvedType;
            if (typeBinding == TypeBinding.NULL || (arrayBinding.dimensions() == typeBinding.dimensions() && typeBinding.isCompatibleWith(arrayBinding2))) {
                expressionArr[i].generateCode(blockScope, codeStream, true);
                return;
            }
            codeStream.generateInlinedValue(1);
            codeStream.newArray(arrayBinding2);
            codeStream.dup();
            codeStream.generateInlinedValue(0);
            expressionArr[i].generateCode(blockScope, codeStream, true);
            codeStream.arrayAtPut(i3, false);
            return;
        }
        codeStream.generateInlinedValue(0);
        codeStream.newArray(arrayBinding2);
    }

    public boolean isBoxingCompatible(TypeBinding typeBinding, TypeBinding typeBinding2, Expression expression, Scope scope) {
        if (scope.isBoxingCompatibleWith(typeBinding, typeBinding2)) {
            return true;
        }
        if (!typeBinding.isBaseType() || typeBinding2.isBaseType() || typeBinding2.isTypeVariable() || scope.compilerOptions().sourceLevel < ClassFileConstants.JDK1_5) {
            return false;
        }
        return (typeBinding2.f626id == 26 || typeBinding2.f626id == 27 || typeBinding2.f626id == 28) && expression.isConstantValueOfTypeAssignableToType(typeBinding, scope.environment().computeBoxingType(typeBinding2));
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public StringBuffer print(int i, StringBuffer stringBuffer) {
        return printStatement(i, stringBuffer);
    }

    public Constant resolveCase(BlockScope blockScope, TypeBinding typeBinding, SwitchStatement switchStatement) {
        resolve(blockScope);
        return Constant.NotAConstant;
    }

    public TypeBinding expectedType() {
        return invocationTargetType();
    }

    public ExpressionContext getExpressionContext() {
        return ExpressionContext.VANILLA_CONTEXT;
    }

    protected MethodBinding findConstructorBinding(BlockScope blockScope, Invocation invocation, ReferenceBinding referenceBinding, TypeBinding[] typeBindingArr) {
        MethodBinding constructor = blockScope.getConstructor(referenceBinding, typeBindingArr, invocation);
        resolvePolyExpressionArguments(invocation, constructor, typeBindingArr, blockScope);
        return constructor;
    }
}
