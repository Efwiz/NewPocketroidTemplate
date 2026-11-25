package org.jetbrains.kotlin.psi2ir.generators;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrStatementOriginImpl;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: compiled from: OperatorConventions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0000\u001a\u0012\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0015\u001a\u00020\u0016H\u0000\u001a\u0012\u0010\u0019\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0000\u001a\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0000\"\u001a\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004\"\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0004\"\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0004\"\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0004\"\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0004\"\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0004\"\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0004\"\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0004¨\u0006\u001b"}, d2 = {"AUGMENTED_ASSIGNMENTS", "", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "getAUGMENTED_ASSIGNMENTS", "()Ljava/util/Set;", "BINARY_BOOLEAN_OPERATORS", "getBINARY_BOOLEAN_OPERATORS", "COMPARISON_OPERATORS", "getCOMPARISON_OPERATORS", "EQUALITY_OPERATORS", "getEQUALITY_OPERATORS", "IDENTITY_OPERATORS", "getIDENTITY_OPERATORS", "INCREMENT_DECREMENT_OPERATORS", "getINCREMENT_DECREMENT_OPERATORS", "IN_OPERATORS", "getIN_OPERATORS", "OPERATORS_DESUGARED_TO_CALLS", "getOPERATORS_DESUGARED_TO_CALLS", "getInfixOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "ktOperator", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "getIrTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperator;", "getPostfixOperator", "getPrefixOperator", "ir.psi2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class OperatorConventionsKt {
    private static final Set<IrStatementOriginImpl> AUGMENTED_ASSIGNMENTS = SetsKt.setOf((Object[]) new IrStatementOriginImpl[]{IrStatementOrigin.PLUSEQ.INSTANCE, IrStatementOrigin.MINUSEQ.INSTANCE, IrStatementOrigin.MULTEQ.INSTANCE, IrStatementOrigin.DIVEQ.INSTANCE, IrStatementOrigin.PERCEQ.INSTANCE});
    private static final Set<IrStatementOriginImpl> OPERATORS_DESUGARED_TO_CALLS = SetsKt.setOf((Object[]) new IrStatementOriginImpl[]{IrStatementOrigin.PLUS.INSTANCE, IrStatementOrigin.MINUS.INSTANCE, IrStatementOrigin.MUL.INSTANCE, IrStatementOrigin.DIV.INSTANCE, IrStatementOrigin.PERC.INSTANCE, IrStatementOrigin.RANGE.INSTANCE, IrStatementOrigin.RANGE_UNTIL.INSTANCE, IrStatementOrigin.EXCL.INSTANCE, IrStatementOrigin.UMINUS.INSTANCE, IrStatementOrigin.UPLUS.INSTANCE});
    private static final Set<IrStatementOriginImpl> COMPARISON_OPERATORS = SetsKt.setOf((Object[]) new IrStatementOriginImpl[]{IrStatementOrigin.LT.INSTANCE, IrStatementOrigin.LTEQ.INSTANCE, IrStatementOrigin.GT.INSTANCE, IrStatementOrigin.GTEQ.INSTANCE});
    private static final Set<IrStatementOriginImpl> EQUALITY_OPERATORS = SetsKt.setOf((Object[]) new IrStatementOriginImpl[]{IrStatementOrigin.EQEQ.INSTANCE, IrStatementOrigin.EXCLEQ.INSTANCE});
    private static final Set<IrStatementOriginImpl> IDENTITY_OPERATORS = SetsKt.setOf((Object[]) new IrStatementOriginImpl[]{IrStatementOrigin.EQEQEQ.INSTANCE, IrStatementOrigin.EXCLEQEQ.INSTANCE});
    private static final Set<IrStatementOriginImpl> IN_OPERATORS = SetsKt.setOf((Object[]) new IrStatementOriginImpl[]{IrStatementOrigin.IN.INSTANCE, IrStatementOrigin.NOT_IN.INSTANCE});
    private static final Set<IrStatementOriginImpl> BINARY_BOOLEAN_OPERATORS = SetsKt.setOf((Object[]) new IrStatementOriginImpl[]{IrStatementOrigin.ANDAND.INSTANCE, IrStatementOrigin.OROR.INSTANCE});
    private static final Set<IrStatementOriginImpl> INCREMENT_DECREMENT_OPERATORS = SetsKt.setOf((Object[]) new IrStatementOriginImpl[]{IrStatementOrigin.PREFIX_INCR.INSTANCE, IrStatementOrigin.PREFIX_DECR.INSTANCE, IrStatementOrigin.POSTFIX_INCR.INSTANCE, IrStatementOrigin.POSTFIX_DECR.INSTANCE});

    public static final IrStatementOrigin getInfixOperator(IElementType ktOperator) {
        Intrinsics.checkNotNullParameter(ktOperator, "ktOperator");
        if (Intrinsics.areEqual(ktOperator, KtTokens.EQ)) {
            return IrStatementOrigin.EQ.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.PLUSEQ)) {
            return IrStatementOrigin.PLUSEQ.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.MINUSEQ)) {
            return IrStatementOrigin.MINUSEQ.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.MULTEQ)) {
            return IrStatementOrigin.MULTEQ.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.DIVEQ)) {
            return IrStatementOrigin.DIVEQ.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.PERCEQ)) {
            return IrStatementOrigin.PERCEQ.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.PLUS)) {
            return IrStatementOrigin.PLUS.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.MINUS)) {
            return IrStatementOrigin.MINUS.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.MUL)) {
            return IrStatementOrigin.MUL.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.DIV)) {
            return IrStatementOrigin.DIV.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.PERC)) {
            return IrStatementOrigin.PERC.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.RANGE)) {
            return IrStatementOrigin.RANGE.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.RANGE_UNTIL)) {
            return IrStatementOrigin.RANGE_UNTIL.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.LT)) {
            return IrStatementOrigin.LT.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.LTEQ)) {
            return IrStatementOrigin.LTEQ.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.GT)) {
            return IrStatementOrigin.GT.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.GTEQ)) {
            return IrStatementOrigin.GTEQ.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.EQEQ)) {
            return IrStatementOrigin.EQEQ.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.EXCLEQ)) {
            return IrStatementOrigin.EXCLEQ.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.EQEQEQ)) {
            return IrStatementOrigin.EQEQEQ.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.EXCLEQEQEQ)) {
            return IrStatementOrigin.EXCLEQEQ.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.IN_KEYWORD)) {
            return IrStatementOrigin.IN.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.NOT_IN)) {
            return IrStatementOrigin.NOT_IN.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.ANDAND)) {
            return IrStatementOrigin.ANDAND.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.OROR)) {
            return IrStatementOrigin.OROR.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.ELVIS)) {
            return IrStatementOrigin.ELVIS.INSTANCE;
        }
        return null;
    }

    public static final IrStatementOrigin getPrefixOperator(IElementType ktOperator) {
        Intrinsics.checkNotNullParameter(ktOperator, "ktOperator");
        if (Intrinsics.areEqual(ktOperator, KtTokens.PLUSPLUS)) {
            return IrStatementOrigin.PREFIX_INCR.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.MINUSMINUS)) {
            return IrStatementOrigin.PREFIX_DECR.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.EXCL)) {
            return IrStatementOrigin.EXCL.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.MINUS)) {
            return IrStatementOrigin.UMINUS.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.PLUS)) {
            return IrStatementOrigin.UPLUS.INSTANCE;
        }
        return null;
    }

    public static final IrStatementOrigin getPostfixOperator(IElementType ktOperator) {
        Intrinsics.checkNotNullParameter(ktOperator, "ktOperator");
        if (Intrinsics.areEqual(ktOperator, KtTokens.PLUSPLUS)) {
            return IrStatementOrigin.POSTFIX_INCR.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.MINUSMINUS)) {
            return IrStatementOrigin.POSTFIX_DECR.INSTANCE;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.EXCLEXCL)) {
            return IrStatementOrigin.EXCLEXCL.INSTANCE;
        }
        return null;
    }

    public static final IrTypeOperator getIrTypeOperator(IElementType ktOperator) {
        Intrinsics.checkNotNullParameter(ktOperator, "ktOperator");
        if (Intrinsics.areEqual(ktOperator, KtTokens.IS_KEYWORD)) {
            return IrTypeOperator.INSTANCEOF;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.NOT_IS)) {
            return IrTypeOperator.NOT_INSTANCEOF;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.AS_KEYWORD)) {
            return IrTypeOperator.CAST;
        }
        if (Intrinsics.areEqual(ktOperator, KtTokens.AS_SAFE)) {
            return IrTypeOperator.SAFE_CAST;
        }
        return null;
    }

    public static final Set<IrStatementOriginImpl> getAUGMENTED_ASSIGNMENTS() {
        return AUGMENTED_ASSIGNMENTS;
    }

    public static final Set<IrStatementOriginImpl> getOPERATORS_DESUGARED_TO_CALLS() {
        return OPERATORS_DESUGARED_TO_CALLS;
    }

    public static final Set<IrStatementOriginImpl> getCOMPARISON_OPERATORS() {
        return COMPARISON_OPERATORS;
    }

    public static final Set<IrStatementOriginImpl> getEQUALITY_OPERATORS() {
        return EQUALITY_OPERATORS;
    }

    public static final Set<IrStatementOriginImpl> getIDENTITY_OPERATORS() {
        return IDENTITY_OPERATORS;
    }

    public static final Set<IrStatementOriginImpl> getIN_OPERATORS() {
        return IN_OPERATORS;
    }

    public static final Set<IrStatementOriginImpl> getBINARY_BOOLEAN_OPERATORS() {
        return BINARY_BOOLEAN_OPERATORS;
    }

    public static final Set<IrStatementOriginImpl> getINCREMENT_DECREMENT_OPERATORS() {
        return INCREMENT_DECREMENT_OPERATORS;
    }
}
