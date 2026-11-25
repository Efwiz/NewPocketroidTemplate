package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.impl.LongConstant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.parser.ScannerHelper;

/* JADX INFO: loaded from: classes14.dex */
public class LongLiteral extends NumberLiteral {
    private char[] reducedForm;
    private static final char[] HEXA_MIN_VALUE = "0x8000000000000000L".toCharArray();
    private static final char[] HEXA_MINUS_ONE_VALUE = "0xffffffffffffffffL".toCharArray();
    private static final char[] OCTAL_MIN_VALUE = "01000000000000000000000L".toCharArray();
    private static final char[] OCTAL_MINUS_ONE_VALUE = "01777777777777777777777L".toCharArray();
    private static final char[] DECIMAL_MIN_VALUE = "9223372036854775808L".toCharArray();
    private static final char[] DECIMAL_MAX_VALUE = "9223372036854775807L".toCharArray();

    public static LongLiteral buildLongLiteral(char[] cArr, int i, int i2) {
        char[] cArrRemovePrefixZerosAndUnderscores = removePrefixZerosAndUnderscores(cArr, true);
        int length = cArrRemovePrefixZerosAndUnderscores.length;
        if (length == 19) {
            if (CharOperation.equals(cArrRemovePrefixZerosAndUnderscores, HEXA_MIN_VALUE)) {
                if (cArrRemovePrefixZerosAndUnderscores == cArr) {
                    cArrRemovePrefixZerosAndUnderscores = null;
                }
                return new LongLiteralMinValue(cArr, cArrRemovePrefixZerosAndUnderscores, i, i2);
            }
        } else if (length == 24 && CharOperation.equals(cArrRemovePrefixZerosAndUnderscores, OCTAL_MIN_VALUE)) {
            if (cArrRemovePrefixZerosAndUnderscores == cArr) {
                cArrRemovePrefixZerosAndUnderscores = null;
            }
            return new LongLiteralMinValue(cArr, cArrRemovePrefixZerosAndUnderscores, i, i2);
        }
        if (cArrRemovePrefixZerosAndUnderscores == cArr) {
            cArrRemovePrefixZerosAndUnderscores = null;
        }
        return new LongLiteral(cArr, cArrRemovePrefixZerosAndUnderscores, i, i2);
    }

    LongLiteral(char[] cArr, char[] cArr2, int i, int i2) {
        super(cArr, i, i2);
        this.reducedForm = cArr2;
    }

    public LongLiteral convertToMinValue() {
        if (((this.bits & ASTNode.ParenthesizedMASK) >> 21) != 0) {
            return this;
        }
        char[] cArr = this.reducedForm;
        if (cArr == null) {
            cArr = this.source;
        }
        return (cArr.length == 20 && CharOperation.equals(cArr, DECIMAL_MIN_VALUE, false)) ? new LongLiteralMinValue(this.source, this.reducedForm, this.sourceStart, this.sourceEnd) : this;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Literal
    public void computeConstant() {
        int i;
        int i2;
        char[] cArr = this.reducedForm;
        if (cArr == null) {
            cArr = this.source;
        }
        int length = cArr.length;
        int i3 = length - 1;
        if (cArr[0] == '0') {
            i = 1;
            if (i3 == 1) {
                this.constant = LongConstant.fromValue(0L);
                return;
            }
            char c = cArr[1];
            if (c == 'x' || c == 'X') {
                i2 = 16;
                i = 2;
            } else if (c == 'b' || c == 'B') {
                i = 2;
                i2 = 2;
            } else {
                i2 = 8;
            }
        } else {
            i = 0;
            i2 = 10;
        }
        if (i2 == 2) {
            if (length - 3 > 64) {
                return;
            }
            computeValue(cArr, i3, i2, i);
            return;
        }
        if (i2 == 8) {
            if (length <= 24) {
                if (length != 24 || cArr[i] <= '1') {
                    if (CharOperation.equals(cArr, OCTAL_MINUS_ONE_VALUE)) {
                        this.constant = LongConstant.fromValue(-1L);
                        return;
                    } else {
                        computeValue(cArr, i3, i2, i);
                        return;
                    }
                }
                return;
            }
            return;
        }
        if (i2 != 10) {
            if (i2 == 16 && length <= 19) {
                if (CharOperation.equals(cArr, HEXA_MINUS_ONE_VALUE)) {
                    this.constant = LongConstant.fromValue(-1L);
                    return;
                } else {
                    computeValue(cArr, i3, i2, i);
                    return;
                }
            }
            return;
        }
        char[] cArr2 = DECIMAL_MAX_VALUE;
        if (length <= cArr2.length) {
            if (length != cArr2.length || CharOperation.compareTo(cArr, cArr2, 0, i3) <= 0) {
                computeValue(cArr, i3, i2, i);
            }
        }
    }

    private void computeValue(char[] cArr, int i, int i2, int i3) {
        long j = 0;
        while (i3 < i) {
            int i4 = i3 + 1;
            int iDigit = ScannerHelper.digit(cArr[i3], i2);
            if (iDigit < 0) {
                return;
            }
            j = (j * ((long) i2)) + ((long) iDigit);
            i3 = i4;
        }
        this.constant = LongConstant.fromValue(j);
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
        return TypeBinding.LONG;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        aSTVisitor.visit(this, blockScope);
        aSTVisitor.endVisit(this, blockScope);
    }
}
