package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.InitializationFlowContext;
import org.eclipse.jdt.internal.compiler.flow.InsideSubRoutineFlowContext;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ReturnStatement extends Statement {
    public Expression expression;
    private boolean implicitReturn;
    public int initStateIndex;
    public LocalVariableBinding saveValueVariable;
    public SubRoutineStatement[] subroutines;

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean doesNotCompleteNormally() {
        return true;
    }

    public ReturnStatement(Expression expression, int i, int i2) {
        this(expression, i, i2, false);
    }

    public ReturnStatement(Expression expression, int i, int i2, boolean z) {
        this.initStateIndex = -1;
        this.sourceStart = i;
        this.sourceEnd = i2;
        this.expression = expression;
        this.implicitReturn = z;
    }

    /* JADX WARN: Code duplicated, block: B:45:0x00b5  */
    /* JADX WARN: Code duplicated, block: B:47:0x00bb  */
    /* JADX WARN: Code duplicated, block: B:48:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:50:0x00c5  */
    /* JADX WARN: Code duplicated, block: B:52:0x00ce  */
    /* JADX WARN: Code duplicated, block: B:54:0x00d2  */
    /* JADX WARN: Code duplicated, block: B:56:0x00dd  */
    /* JADX WARN: Code duplicated, block: B:83:0x00e1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:84:0x00f1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:85:? A[LOOP:0: B:26:0x0069->B:85:?, LOOP_END, SYNTHETIC] */
    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        int i;
        ASTNode aSTNode;
        TryStatement tryStatement;
        Expression expression;
        FakedTrackingVariable closeTrackingVariable;
        Expression expression2 = this.expression;
        if ((expression2 instanceof FunctionalExpression) && (expression2.resolvedType == null || !this.expression.resolvedType.isValidBinding())) {
            flowContext.recordAbruptExit();
            return FlowInfo.DEAD_END;
        }
        MethodScope methodScope = blockScope.methodScope();
        Expression expression3 = this.expression;
        if (expression3 != null) {
            flowInfo = expression3.analyseCode(blockScope, flowContext, flowInfo);
            this.expression.checkNPEbyUnboxing(blockScope, flowContext, flowInfo);
            if (flowInfo.reachMode() == 0 && blockScope.compilerOptions().isAnnotationBasedNullAnalysisEnabled) {
                checkAgainstNullAnnotation(blockScope, flowContext, flowInfo);
            }
            if (blockScope.compilerOptions().analyseResourceLeaks && (closeTrackingVariable = FakedTrackingVariable.getCloseTrackingVariable(this.expression, flowInfo, flowContext)) != null) {
                if (methodScope != closeTrackingVariable.methodScope) {
                    closeTrackingVariable.markClosedInNestedMethod();
                }
                flowInfo = FakedTrackingVariable.markPassedToOutside(blockScope, this.expression, flowInfo, flowContext, true);
            }
        }
        this.initStateIndex = methodScope.recordInitializationStates(flowInfo);
        boolean zNeedValueStore = needValueStore();
        FlowContext localParent = flowContext;
        boolean z = true;
        int i2 = 0;
        boolean z2 = false;
        while (true) {
            SubRoutineStatement subRoutineStatementSubroutine = localParent.subroutine();
            if (subRoutineStatementSubroutine != null) {
                if (this.subroutines == null) {
                    this.subroutines = new SubRoutineStatement[5];
                }
                SubRoutineStatement[] subRoutineStatementArr = this.subroutines;
                if (i2 == subRoutineStatementArr.length) {
                    SubRoutineStatement[] subRoutineStatementArr2 = new SubRoutineStatement[i2 * 2];
                    this.subroutines = subRoutineStatementArr2;
                    System.arraycopy(subRoutineStatementArr, 0, subRoutineStatementArr2, 0, i2);
                }
                i = i2 + 1;
                this.subroutines[i2] = subRoutineStatementSubroutine;
                if (subRoutineStatementSubroutine.isSubRoutineEscaping()) {
                    this.bits |= 536870912;
                    z2 = false;
                    break;
                }
                if ((subRoutineStatementSubroutine instanceof TryStatement) && ((TryStatement) subRoutineStatementSubroutine).resources.length > 0) {
                    z = false;
                }
                i2 = i;
                localParent.recordReturnFrom(flowInfo.unconditionalInits());
                if (localParent instanceof InsideSubRoutineFlowContext) {
                    aSTNode = localParent.associatedNode;
                    if (aSTNode instanceof SynchronizedStatement) {
                        this.bits |= 1073741824;
                    } else if (aSTNode instanceof TryStatement) {
                        tryStatement = (TryStatement) aSTNode;
                        flowInfo.addInitializationsFrom(tryStatement.subRoutineInits);
                        if (zNeedValueStore) {
                            if (this.saveValueVariable == null) {
                                prepareSaveValueLocation(tryStatement);
                            }
                            this.initStateIndex = methodScope.recordInitializationStates(flowInfo);
                            z2 = true;
                        }
                    }
                } else if (localParent instanceof InitializationFlowContext) {
                    blockScope.problemReporter().cannotReturnInInitializer(this);
                    return FlowInfo.DEAD_END;
                }
                localParent = localParent.getLocalParent();
                if (localParent == null) {
                    i = i2;
                    break;
                }
            } else {
                localParent.recordReturnFrom(flowInfo.unconditionalInits());
                if (localParent instanceof InsideSubRoutineFlowContext) {
                    aSTNode = localParent.associatedNode;
                    if (aSTNode instanceof SynchronizedStatement) {
                        this.bits |= 1073741824;
                    } else if (aSTNode instanceof TryStatement) {
                        tryStatement = (TryStatement) aSTNode;
                        flowInfo.addInitializationsFrom(tryStatement.subRoutineInits);
                        if (zNeedValueStore) {
                            if (this.saveValueVariable == null) {
                                prepareSaveValueLocation(tryStatement);
                            }
                            this.initStateIndex = methodScope.recordInitializationStates(flowInfo);
                            z2 = true;
                        }
                    }
                } else if (localParent instanceof InitializationFlowContext) {
                    blockScope.problemReporter().cannotReturnInInitializer(this);
                    return FlowInfo.DEAD_END;
                }
                localParent = localParent.getLocalParent();
                if (localParent == null) {
                    i = i2;
                    break;
                }
            }
        }
        SubRoutineStatement[] subRoutineStatementArr3 = this.subroutines;
        if (subRoutineStatementArr3 != null && i != subRoutineStatementArr3.length) {
            SubRoutineStatement[] subRoutineStatementArr4 = new SubRoutineStatement[i];
            this.subroutines = subRoutineStatementArr4;
            System.arraycopy(subRoutineStatementArr3, 0, subRoutineStatementArr4, 0, i);
        }
        if (z2) {
            LocalVariableBinding localVariableBinding = this.saveValueVariable;
            if (localVariableBinding != null) {
                localVariableBinding.useFlag = 1;
            }
        } else {
            this.saveValueVariable = null;
            if ((this.bits & 1073741824) == 0 && (expression = this.expression) != null && TypeBinding.equalsEquals(expression.resolvedType, TypeBinding.BOOLEAN) && z) {
                this.expression.bits |= 16;
            }
        }
        blockScope.checkUnclosedCloseables(flowInfo, flowContext, this, blockScope);
        flowContext.recordAbruptExit();
        flowContext.expireNullCheckedFieldInfo();
        return FlowInfo.DEAD_END;
    }

    void checkAgainstNullAnnotation(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        int iNullStatus = this.expression.nullStatus(flowInfo, flowContext);
        boolean zUsesNullTypeAnnotations = blockScope.environment().usesNullTypeAnnotations();
        try {
            MethodBinding methodBindingReferenceMethodBinding = blockScope.methodScope().referenceMethodBinding();
            long j = zUsesNullTypeAnnotations ? methodBindingReferenceMethodBinding.returnType.tagBits : methodBindingReferenceMethodBinding.tagBits;
            if (zUsesNullTypeAnnotations) {
                checkAgainstNullTypeAnnotation(blockScope, methodBindingReferenceMethodBinding.returnType, this.expression, flowContext, flowInfo);
            } else {
                if (iNullStatus == 4 || (j & 72057594037927936L) == 0) {
                    return;
                }
                Expression expression = this.expression;
                flowContext.recordNullityMismatch(blockScope, expression, expression.resolvedType, methodBindingReferenceMethodBinding.returnType, flowInfo, iNullStatus, null);
            }
        } catch (NullPointerException unused) {
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
        boolean z;
        if ((this.bits & Integer.MIN_VALUE) == 0) {
            return;
        }
        int i = codeStream.position;
        if (needValueStore()) {
            this.expression.generateCode(blockScope, codeStream, needValue());
            generateStoreSaveValueIfNecessary(codeStream);
            z = true;
        } else {
            z = false;
        }
        if (this.subroutines != null) {
            Expression expression = this.expression;
            Object objReusableJSRTarget = expression == null ? TypeBinding.VOID : expression.reusableJSRTarget();
            int length = this.subroutines.length;
            for (int i2 = 0; i2 < length; i2++) {
                if (this.subroutines[i2].generateSubRoutineInvocation(blockScope, codeStream, objReusableJSRTarget, this.initStateIndex, this.saveValueVariable)) {
                    codeStream.recordPositionsFrom(i, this.sourceStart);
                    SubRoutineStatement.reenterAllExceptionHandlers(this.subroutines, i2, codeStream);
                    return;
                }
            }
        }
        LocalVariableBinding localVariableBinding = this.saveValueVariable;
        if (localVariableBinding != null) {
            codeStream.load(localVariableBinding);
        }
        Expression expression2 = this.expression;
        if (expression2 != null && !z) {
            expression2.generateCode(blockScope, codeStream, true);
            generateStoreSaveValueIfNecessary(codeStream);
        }
        generateReturnBytecode(codeStream);
        LocalVariableBinding localVariableBinding2 = this.saveValueVariable;
        if (localVariableBinding2 != null) {
            codeStream.removeVariable(localVariableBinding2);
        }
        int i3 = this.initStateIndex;
        if (i3 != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i3);
            codeStream.addDefinitelyAssignedVariables(blockScope, this.initStateIndex);
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
        SubRoutineStatement.reenterAllExceptionHandlers(this.subroutines, -1, codeStream);
    }

    public void generateReturnBytecode(CodeStream codeStream) {
        codeStream.generateReturnBytecode(this.expression);
    }

    public void generateStoreSaveValueIfNecessary(CodeStream codeStream) {
        LocalVariableBinding localVariableBinding = this.saveValueVariable;
        if (localVariableBinding != null) {
            codeStream.store(localVariableBinding, false);
            codeStream.addVariable(this.saveValueVariable);
        }
    }

    private boolean needValueStore() {
        Expression expression = this.expression;
        if (expression != null) {
            return (expression.constant == Constant.NotAConstant || (this.expression.implicitConversion & 512) != 0) && !(this.expression instanceof NullLiteral);
        }
        return false;
    }

    public boolean needValue() {
        return (this.saveValueVariable == null && (this.bits & 1073741824) == 0 && (this.bits & 536870912) != 0) ? false : true;
    }

    public void prepareSaveValueLocation(TryStatement tryStatement) {
        this.saveValueVariable = tryStatement.secretReturnValue;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer).append("return ");
        Expression expression = this.expression;
        if (expression != null) {
            expression.printExpression(0, stringBuffer);
        }
        return stringBuffer.append(';');
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
        TypeBinding typeBindingExpectedResultType;
        MethodScope methodScope = blockScope.methodScope();
        LambdaExpression lambdaExpression = methodScope.referenceContext instanceof LambdaExpression ? (LambdaExpression) methodScope.referenceContext : null;
        if (lambdaExpression != null) {
            typeBindingExpectedResultType = lambdaExpression.expectedResultType();
        } else if (methodScope.referenceContext instanceof AbstractMethodDeclaration) {
            MethodBinding methodBinding = ((AbstractMethodDeclaration) methodScope.referenceContext).binding;
            typeBindingExpectedResultType = methodBinding == null ? null : methodBinding.returnType;
        } else {
            typeBindingExpectedResultType = TypeBinding.VOID;
        }
        Expression expression = this.expression;
        if (expression != null) {
            expression.setExpressionContext(ExpressionContext.ASSIGNMENT_CONTEXT);
            this.expression.setExpectedType(typeBindingExpectedResultType);
            if (lambdaExpression != null && lambdaExpression.argumentsTypeElided()) {
                Expression expression2 = this.expression;
                if (expression2 instanceof CastExpression) {
                    expression2.bits |= 32;
                }
            }
        }
        if (typeBindingExpectedResultType == TypeBinding.VOID) {
            Expression expression3 = this.expression;
            if (expression3 == null) {
                if (lambdaExpression != null) {
                    lambdaExpression.returnsExpression(null, TypeBinding.VOID);
                    return;
                }
                return;
            }
            TypeBinding typeBindingResolveType = expression3.resolveType(blockScope);
            if (lambdaExpression != null) {
                lambdaExpression.returnsExpression(this.expression, typeBindingResolveType);
            }
            if ((this.implicitReturn && (typeBindingResolveType == TypeBinding.VOID || this.expression.statementExpression())) || typeBindingResolveType == null) {
                return;
            }
            blockScope.problemReporter().attemptToReturnNonVoidExpression(this, typeBindingResolveType);
            return;
        }
        Expression expression4 = this.expression;
        if (expression4 == null) {
            if (lambdaExpression != null) {
                lambdaExpression.returnsExpression(null, typeBindingExpectedResultType);
            }
            if (typeBindingExpectedResultType != null) {
                blockScope.problemReporter().shouldReturn(typeBindingExpectedResultType, this);
                return;
            }
            return;
        }
        TypeBinding typeBindingResolveType2 = expression4.resolveType(blockScope);
        if (lambdaExpression != null) {
            lambdaExpression.returnsExpression(this.expression, typeBindingResolveType2);
        }
        if (typeBindingResolveType2 == null) {
            return;
        }
        if (typeBindingResolveType2 == TypeBinding.VOID) {
            blockScope.problemReporter().attemptToReturnVoidValue(this);
            return;
        }
        if (typeBindingExpectedResultType == null) {
            return;
        }
        if (TypeBinding.notEquals(typeBindingExpectedResultType, typeBindingResolveType2)) {
            blockScope.compilationUnitScope().recordTypeConversion(typeBindingExpectedResultType, typeBindingResolveType2);
        }
        if (this.expression.isConstantValueOfTypeAssignableToType(typeBindingResolveType2, typeBindingExpectedResultType) || typeBindingResolveType2.isCompatibleWith(typeBindingExpectedResultType, blockScope)) {
            this.expression.computeConversion(blockScope, typeBindingExpectedResultType, typeBindingResolveType2);
            if (typeBindingResolveType2.needsUncheckedConversion(typeBindingExpectedResultType)) {
                blockScope.problemReporter().unsafeTypeConversion(this.expression, typeBindingResolveType2, typeBindingExpectedResultType);
            }
            Expression expression5 = this.expression;
            if (expression5 instanceof CastExpression) {
                if ((expression5.bits & 16416) == 0) {
                    CastExpression.checkNeedForAssignedCast(blockScope, typeBindingExpectedResultType, (CastExpression) this.expression);
                    return;
                } else {
                    if (lambdaExpression == null || !lambdaExpression.argumentsTypeElided() || (this.expression.bits & 16384) == 0 || !TypeBinding.equalsEquals(((CastExpression) this.expression).expression.resolvedType, typeBindingExpectedResultType)) {
                        return;
                    }
                    blockScope.problemReporter().unnecessaryCast((CastExpression) this.expression);
                    return;
                }
            }
            return;
        }
        if (!isBoxingCompatible(typeBindingResolveType2, typeBindingExpectedResultType, this.expression, blockScope)) {
            if ((typeBindingExpectedResultType.tagBits & 128) == 0) {
                blockScope.problemReporter().typeMismatchError(typeBindingResolveType2, typeBindingExpectedResultType, this.expression, this);
            }
        } else {
            this.expression.computeConversion(blockScope, typeBindingExpectedResultType, typeBindingResolveType2);
            Expression expression6 = this.expression;
            if ((expression6 instanceof CastExpression) && (expression6.bits & 16416) == 0) {
                CastExpression.checkNeedForAssignedCast(blockScope, typeBindingExpectedResultType, (CastExpression) this.expression);
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        Expression expression;
        if (aSTVisitor.visit(this, blockScope) && (expression = this.expression) != null) {
            expression.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }
}
