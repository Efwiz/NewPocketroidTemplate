package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class CombinedBinaryExpression extends BinaryExpression {
    public static final int ARITY_MAX_MAX = 160;
    public static final int ARITY_MAX_MIN = 20;
    public static int defaultArityMaxStartingValue = 20;
    public int arity;
    public int arityMax;
    public BinaryExpression[] referencesTable;

    public CombinedBinaryExpression(Expression expression, Expression expression2, int i, int i2) {
        super(expression, expression2, i);
        initArity(expression, i2);
    }

    public CombinedBinaryExpression(CombinedBinaryExpression combinedBinaryExpression) {
        super(combinedBinaryExpression);
        initArity(combinedBinaryExpression.left, combinedBinaryExpression.arity);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.BinaryExpression, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        BinaryExpression[] binaryExpressionArr = this.referencesTable;
        if (binaryExpressionArr == null) {
            return super.analyseCode(blockScope, flowContext, flowInfo);
        }
        try {
            BinaryExpression binaryExpression = binaryExpressionArr[0];
            if (binaryExpression.resolvedType.f626id != 11) {
                binaryExpression.left.checkNPE(blockScope, flowContext, flowInfo);
            }
            UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits = binaryExpression.left.analyseCode(blockScope, flowContext, flowInfo).unconditionalInits();
            int i = this.arity;
            for (int i2 = 0; i2 < i; i2++) {
                BinaryExpression binaryExpression2 = this.referencesTable[i2];
                if (binaryExpression2.resolvedType.f626id != 11) {
                    binaryExpression2.right.checkNPE(blockScope, flowContext, unconditionalFlowInfoUnconditionalInits);
                }
                unconditionalFlowInfoUnconditionalInits = binaryExpression2.right.analyseCode(blockScope, flowContext, unconditionalFlowInfoUnconditionalInits).unconditionalInits();
            }
            if (this.resolvedType.f626id != 11) {
                this.right.checkNPE(blockScope, flowContext, unconditionalFlowInfoUnconditionalInits);
            }
            return this.right.analyseCode(blockScope, flowContext, unconditionalFlowInfoUnconditionalInits).unconditionalInits();
        } finally {
            flowContext.recordAbruptExit();
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.BinaryExpression, org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateOptimizedStringConcatenation(BlockScope blockScope, CodeStream codeStream, int i) {
        if (this.referencesTable == null) {
            super.generateOptimizedStringConcatenation(blockScope, codeStream, i);
            return;
        }
        if (((this.bits & ASTNode.OperatorMASK) >> 6) == 14 && (this.bits & 15) == 11) {
            if (this.constant != Constant.NotAConstant) {
                codeStream.generateConstant(this.constant, this.implicitConversion);
                codeStream.invokeStringConcatenationAppendForType(this.implicitConversion & 15);
                return;
            }
            BinaryExpression binaryExpression = this.referencesTable[0];
            int i2 = codeStream.position;
            int i3 = this.arity - 1;
            while (i3 >= 0) {
                binaryExpression = this.referencesTable[i3];
                if (binaryExpression.constant != Constant.NotAConstant) {
                    codeStream.generateConstant(binaryExpression.constant, binaryExpression.implicitConversion);
                    codeStream.invokeStringConcatenationAppendForType(binaryExpression.implicitConversion & 15);
                    break;
                }
                i3--;
            }
            int i4 = i3 + 1;
            if (i4 == 0) {
                binaryExpression.left.generateOptimizedStringConcatenation(blockScope, codeStream, binaryExpression.left.implicitConversion & 15);
            }
            while (i4 < this.arity) {
                BinaryExpression binaryExpression2 = this.referencesTable[i4];
                codeStream.recordPositionsFrom(i2, binaryExpression2.left.sourceStart);
                int i5 = codeStream.position;
                binaryExpression2.right.generateOptimizedStringConcatenation(blockScope, codeStream, binaryExpression2.right.implicitConversion & 15);
                codeStream.recordPositionsFrom(i5, binaryExpression2.right.sourceStart);
                i4++;
            }
            codeStream.recordPositionsFrom(i2, this.left.sourceStart);
            int i6 = codeStream.position;
            this.right.generateOptimizedStringConcatenation(blockScope, codeStream, this.right.implicitConversion & 15);
            codeStream.recordPositionsFrom(i6, this.right.sourceStart);
            return;
        }
        super.generateOptimizedStringConcatenation(blockScope, codeStream, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.BinaryExpression, org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateOptimizedStringConcatenationCreation(BlockScope blockScope, CodeStream codeStream, int i) {
        if (this.referencesTable == null) {
            super.generateOptimizedStringConcatenationCreation(blockScope, codeStream, i);
            return;
        }
        if (((this.bits & ASTNode.OperatorMASK) >> 6) == 14 && (this.bits & 15) == 11 && this.constant == Constant.NotAConstant) {
            int i2 = codeStream.position;
            BinaryExpression[] binaryExpressionArr = this.referencesTable;
            int i3 = this.arity;
            BinaryExpression binaryExpression = binaryExpressionArr[i3 - 1];
            int i4 = i3 - 1;
            while (i4 >= 0) {
                binaryExpression = this.referencesTable[i4];
                if (((binaryExpression.bits & ASTNode.OperatorMASK) >> 6) == 14 && (binaryExpression.bits & 15) == 11) {
                    if (binaryExpression.constant != Constant.NotAConstant) {
                        codeStream.newStringContatenation();
                        codeStream.dup();
                        codeStream.ldc(binaryExpression.constant.stringValue());
                        codeStream.invokeStringConcatenationStringConstructor();
                        break;
                    }
                    i4--;
                } else {
                    binaryExpression.generateOptimizedStringConcatenationCreation(blockScope, codeStream, binaryExpression.implicitConversion & 15);
                    break;
                }
            }
            int i5 = i4 + 1;
            if (i5 == 0) {
                binaryExpression.left.generateOptimizedStringConcatenationCreation(blockScope, codeStream, binaryExpression.left.implicitConversion & 15);
            }
            while (i5 < this.arity) {
                BinaryExpression binaryExpression2 = this.referencesTable[i5];
                codeStream.recordPositionsFrom(i2, binaryExpression2.left.sourceStart);
                int i6 = codeStream.position;
                binaryExpression2.right.generateOptimizedStringConcatenation(blockScope, codeStream, binaryExpression2.right.implicitConversion & 15);
                codeStream.recordPositionsFrom(i6, binaryExpression2.right.sourceStart);
                i5++;
            }
            codeStream.recordPositionsFrom(i2, this.left.sourceStart);
            int i7 = codeStream.position;
            this.right.generateOptimizedStringConcatenation(blockScope, codeStream, this.right.implicitConversion & 15);
            codeStream.recordPositionsFrom(i7, this.right.sourceStart);
            return;
        }
        super.generateOptimizedStringConcatenationCreation(blockScope, codeStream, i);
    }

    private void initArity(Expression expression, int i) {
        this.arity = i;
        if (i > 1) {
            BinaryExpression[] binaryExpressionArr = new BinaryExpression[i];
            this.referencesTable = binaryExpressionArr;
            int i2 = i - 1;
            binaryExpressionArr[i2] = (BinaryExpression) expression;
            while (i2 > 0) {
                BinaryExpression[] binaryExpressionArr2 = this.referencesTable;
                binaryExpressionArr2[i2 - 1] = (BinaryExpression) binaryExpressionArr2[i2].left;
                i2--;
            }
            return;
        }
        this.arityMax = defaultArityMaxStartingValue;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.BinaryExpression, org.eclipse.jdt.internal.compiler.ast.OperatorExpression
    public StringBuffer printExpressionNoParenthesis(int i, StringBuffer stringBuffer) {
        if (this.referencesTable == null) {
            return super.printExpressionNoParenthesis(i, stringBuffer);
        }
        String strOperatorToString = operatorToString();
        for (int i2 = this.arity - 1; i2 >= 0; i2--) {
            stringBuffer.append('(');
        }
        StringBuffer stringBufferPrintExpression = this.referencesTable[0].left.printExpression(i, stringBuffer);
        int i3 = this.arity;
        for (int i4 = 0; i4 < i3; i4++) {
            stringBufferPrintExpression.append(StringUtil.SPACE).append(strOperatorToString).append(StringUtil.SPACE);
            stringBufferPrintExpression = this.referencesTable[i4].right.printExpression(0, stringBufferPrintExpression);
            stringBufferPrintExpression.append(')');
        }
        stringBufferPrintExpression.append(StringUtil.SPACE).append(strOperatorToString).append(StringUtil.SPACE);
        return this.right.printExpression(0, stringBufferPrintExpression);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.BinaryExpression, org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        BinaryExpression[] binaryExpressionArr = this.referencesTable;
        if (binaryExpressionArr == null) {
            return super.resolveType(blockScope);
        }
        BinaryExpression binaryExpression = binaryExpressionArr[0];
        if (binaryExpression.left instanceof CastExpression) {
            binaryExpression.left.bits |= 32;
        }
        binaryExpression.left.resolveType(blockScope);
        int i = this.arity;
        for (int i2 = 0; i2 < i; i2++) {
            this.referencesTable[i2].nonRecursiveResolveTypeUpwards(blockScope);
        }
        nonRecursiveResolveTypeUpwards(blockScope);
        return this.resolvedType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.BinaryExpression, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (this.referencesTable == null) {
            super.traverse(aSTVisitor, blockScope);
            return;
        }
        if (aSTVisitor.visit(this, blockScope)) {
            int i = this.arity - 1;
            while (i >= 0) {
                if (!aSTVisitor.visit(this.referencesTable[i], blockScope)) {
                    aSTVisitor.endVisit(this.referencesTable[i], blockScope);
                    break;
                }
                i--;
            }
            int i2 = i + 1;
            if (i2 == 0) {
                this.referencesTable[0].left.traverse(aSTVisitor, blockScope);
            }
            int i3 = this.arity;
            while (i2 < i3) {
                this.referencesTable[i2].right.traverse(aSTVisitor, blockScope);
                aSTVisitor.endVisit(this.referencesTable[i2], blockScope);
                i2++;
            }
            this.right.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    public void tuneArityMax() {
        int i = this.arityMax;
        if (i < 160) {
            this.arityMax = i * 2;
        }
    }
}
