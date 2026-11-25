package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.impl.IntConstant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.parser.ScannerHelper;

/* JADX INFO: loaded from: classes14.dex */
public class IntLiteral extends NumberLiteral {
    private char[] reducedForm;
    public int value;
    private static final char[] HEXA_MIN_VALUE = "0x80000000".toCharArray();
    private static final char[] HEXA_MINUS_ONE_VALUE = "0xffffffff".toCharArray();
    private static final char[] OCTAL_MIN_VALUE = "020000000000".toCharArray();
    private static final char[] OCTAL_MINUS_ONE_VALUE = "037777777777".toCharArray();
    private static final char[] DECIMAL_MIN_VALUE = "2147483648".toCharArray();
    private static final char[] DECIMAL_MAX_VALUE = "2147483647".toCharArray();
    public static final IntLiteral One = new IntLiteral(new char[]{ExternalAnnotationProvider.NONNULL}, null, 0, 0, 1, IntConstant.fromValue(1));

    public static IntLiteral buildIntLiteral(char[] cArr, int i, int i2) {
        char[] cArrRemovePrefixZerosAndUnderscores = removePrefixZerosAndUnderscores(cArr, false);
        int length = cArrRemovePrefixZerosAndUnderscores.length;
        if (length == 10) {
            if (CharOperation.equals(cArrRemovePrefixZerosAndUnderscores, HEXA_MIN_VALUE)) {
                if (cArrRemovePrefixZerosAndUnderscores == cArr) {
                    cArrRemovePrefixZerosAndUnderscores = null;
                }
                return new IntLiteralMinValue(cArr, cArrRemovePrefixZerosAndUnderscores, i, i2);
            }
        } else if (length == 12 && CharOperation.equals(cArrRemovePrefixZerosAndUnderscores, OCTAL_MIN_VALUE)) {
            if (cArrRemovePrefixZerosAndUnderscores == cArr) {
                cArrRemovePrefixZerosAndUnderscores = null;
            }
            return new IntLiteralMinValue(cArr, cArrRemovePrefixZerosAndUnderscores, i, i2);
        }
        if (cArrRemovePrefixZerosAndUnderscores == cArr) {
            cArrRemovePrefixZerosAndUnderscores = null;
        }
        return new IntLiteral(cArr, cArrRemovePrefixZerosAndUnderscores, i, i2);
    }

    IntLiteral(char[] cArr, char[] cArr2, int i, int i2) {
        super(cArr, i, i2);
        this.reducedForm = cArr2;
    }

    IntLiteral(char[] cArr, char[] cArr2, int i, int i2, int i3, Constant constant) {
        super(cArr, i, i2);
        this.reducedForm = cArr2;
        this.value = i3;
        this.constant = constant;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Literal
    public void computeConstant() {
        int i;
        char[] cArr = this.reducedForm;
        if (cArr == null) {
            cArr = this.source;
        }
        int length = cArr.length;
        int i2 = 0;
        if (cArr[0] != '0') {
            i = 10;
        } else {
            if (length == 1) {
                this.constant = IntConstant.fromValue(0);
                return;
            }
            char c = cArr[1];
            if (c == 'x' || c == 'X') {
                i = 16;
                i2 = 2;
            } else if (c == 'b' || c == 'B') {
                i2 = 2;
                i = 2;
            } else {
                i2 = 1;
                i = 8;
            }
        }
        if (i == 2) {
            if (length - 2 > 32) {
                return;
            }
            computeValue(cArr, length, i, i2);
            return;
        }
        if (i == 8) {
            if (length <= 12) {
                if (length != 12 || cArr[i2] <= '4') {
                    if (CharOperation.equals(cArr, OCTAL_MINUS_ONE_VALUE)) {
                        this.constant = IntConstant.fromValue(-1);
                        return;
                    } else {
                        computeValue(cArr, length, i, i2);
                        return;
                    }
                }
                return;
            }
            return;
        }
        if (i != 10) {
            if (i == 16 && length <= 10) {
                if (CharOperation.equals(cArr, HEXA_MINUS_ONE_VALUE)) {
                    this.constant = IntConstant.fromValue(-1);
                    return;
                } else {
                    computeValue(cArr, length, i, i2);
                    return;
                }
            }
            return;
        }
        char[] cArr2 = DECIMAL_MAX_VALUE;
        if (length <= cArr2.length) {
            if (length != cArr2.length || CharOperation.compareTo(cArr, cArr2) <= 0) {
                computeValue(cArr, length, i, i2);
            }
        }
    }

    private void computeValue(char[] cArr, int i, int i2, int i3) {
        int i4 = 0;
        while (i3 < i) {
            int i5 = i3 + 1;
            int iDigit = ScannerHelper.digit(cArr[i3], i2);
            if (iDigit < 0) {
                return;
            }
            i4 = (i4 * i2) + iDigit;
            i3 = i5;
        }
        this.constant = IntConstant.fromValue(i4);
    }

    public IntLiteral convertToMinValue() {
        if (((this.bits & ASTNode.ParenthesizedMASK) >> 21) != 0) {
            return this;
        }
        char[] cArr = this.reducedForm;
        if (cArr == null) {
            cArr = this.source;
        }
        return (cArr.length == 10 && CharOperation.equals(cArr, DECIMAL_MIN_VALUE)) ? new IntLiteralMinValue(this.source, this.reducedForm, this.sourceStart, this.sourceEnd) : this;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i = codeStream.position;
        if (z) {
            codeStream.generateConstant(this.constant, this.implicitConversion);
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Literal
    public TypeBinding literalType(BlockScope blockScope) {
        return TypeBinding.INT;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        aSTVisitor.visit(this, blockScope);
        aSTVisitor.endVisit(this, blockScope);
    }
}
