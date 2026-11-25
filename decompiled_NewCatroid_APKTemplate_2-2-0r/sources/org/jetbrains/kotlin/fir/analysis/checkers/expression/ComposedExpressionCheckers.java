package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.analysis.CheckersComponentInternal;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirClassReferenceExpression;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop;
import org.jetbrains.kotlin.fir.expressions.FirElvisExpression;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirInaccessibleReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirIntegerLiteralOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirLoop;
import org.jetbrains.kotlin.fir.expressions.FirLoopJump;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.fir.expressions.FirSafeCallExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirStringConcatenationCall;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirThrowExpression;
import org.jetbrains.kotlin.fir.expressions.FirTryExpression;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhileLoop;

/* JADX INFO: compiled from: ComposedExpressionCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000ì\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\bC\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0013\u0010¬\u0001\u001a\u00030\u00ad\u00012\u0007\u0010®\u0001\u001a\u00020\u0001H\u0007R\u001e\u0010\u0003\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\t0\u0005j\u0002`\n0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000b\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\f0\u0005j\u0002`\r0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000e\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000f0\u0005j\u0002`\u00100\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0011\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00120\u0005j\u0002`\u00130\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0014\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00150\u0005j\u0002`\u00160\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0017\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00180\u0005j\u0002`\u00190\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u001a\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u001b0\u0005j\u0002`\u001c0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u001d\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u001e0\u0005j\u0002`\u001f0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010 \u001a\u0016\u0012\u0012\u0012\u0010\u0012\b\u0012\u0006\u0012\u0002\b\u00030!0\u0005j\u0002`\"0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010#\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020$0\u0005j\u0002`%0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010&\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020'0\u0005j\u0002`(0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010)\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020*0\u0005j\u0002`+0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010,\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020-0\u0005j\u0002`.0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010/\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002000\u0005j\u0002`10\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u00102\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002030\u0005j\u0002`40\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u00105\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002060\u0005j\u0002`70\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u00108\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002090\u0005j\u0002`:0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010;\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020<0\u0005j\u0002`=0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010>\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020?0\u0005j\u0002`@0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010A\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020B0\u0005j\u0002`C0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010D\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020E0\u0005j\u0002`F0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010G\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020H0\u0005j\u0002`I0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010J\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020K0\u0005j\u0002`L0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010M\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020N0\u0005j\u0002`O0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010P\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020Q0\u0005j\u0002`R0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010S\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020T0\u0005j\u0002`U0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010V\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020W0\u0005j\u0002`X0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010Y\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020Z0\u0005j\u0002`[0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\\\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020]0\u0005j\u0002`^0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010_\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020`0\u0005j\u0002`a0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010b\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020c0\u0005j\u0002`d0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010e\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020f0\u0005j\u0002`g0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010h\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070i8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bj\u0010kR$\u0010l\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\t0\u0005j\u0002`\n0i8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bm\u0010kR$\u0010n\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\f0\u0005j\u0002`\r0i8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bo\u0010kR$\u0010p\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000f0\u0005j\u0002`\u00100i8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bq\u0010kR$\u0010r\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00120\u0005j\u0002`\u00130i8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bs\u0010kR$\u0010t\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00150\u0005j\u0002`\u00160i8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bu\u0010kR$\u0010v\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00180\u0005j\u0002`\u00190i8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bw\u0010kR$\u0010x\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u001b0\u0005j\u0002`\u001c0i8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\by\u0010kR$\u0010z\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u001e0\u0005j\u0002`\u001f0i8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b{\u0010kR(\u0010|\u001a\u0016\u0012\u0012\u0012\u0010\u0012\b\u0012\u0006\u0012\u0002\b\u00030!0\u0005j\u0002`\"0i8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b}\u0010kR$\u0010~\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020$0\u0005j\u0002`%0i8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u007f\u0010kR&\u0010\u0080\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020'0\u0005j\u0002`(0i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u0081\u0001\u0010kR&\u0010\u0082\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020*0\u0005j\u0002`+0i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u0083\u0001\u0010kR&\u0010\u0084\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020-0\u0005j\u0002`.0i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u0085\u0001\u0010kR&\u0010\u0086\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002000\u0005j\u0002`10i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u0087\u0001\u0010kR&\u0010\u0088\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002030\u0005j\u0002`40i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u0089\u0001\u0010kR&\u0010\u008a\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002060\u0005j\u0002`70i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u008b\u0001\u0010kR&\u0010\u008c\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002090\u0005j\u0002`:0i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u008d\u0001\u0010kR&\u0010\u008e\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020<0\u0005j\u0002`=0i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u008f\u0001\u0010kR&\u0010\u0090\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020?0\u0005j\u0002`@0i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u0091\u0001\u0010kR&\u0010\u0092\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020B0\u0005j\u0002`C0i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u0093\u0001\u0010kR&\u0010\u0094\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020E0\u0005j\u0002`F0i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u0095\u0001\u0010kR&\u0010\u0096\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020H0\u0005j\u0002`I0i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u0097\u0001\u0010kR&\u0010\u0098\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020K0\u0005j\u0002`L0i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u0099\u0001\u0010kR&\u0010\u009a\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020N0\u0005j\u0002`O0i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u009b\u0001\u0010kR&\u0010\u009c\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020Q0\u0005j\u0002`R0i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u009d\u0001\u0010kR&\u0010\u009e\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020T0\u0005j\u0002`U0i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u009f\u0001\u0010kR&\u0010 \u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020W0\u0005j\u0002`X0i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b¡\u0001\u0010kR&\u0010¢\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020Z0\u0005j\u0002`[0i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b£\u0001\u0010kR&\u0010¤\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020]0\u0005j\u0002`^0i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b¥\u0001\u0010kR&\u0010¦\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020`0\u0005j\u0002`a0i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b§\u0001\u0010kR&\u0010¨\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020c0\u0005j\u0002`d0i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b©\u0001\u0010kR&\u0010ª\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020f0\u0005j\u0002`g0i8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b«\u0001\u0010k¨\u0006®\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/ComposedExpressionCheckers;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/ExpressionCheckers;", "()V", "_annotationCallCheckers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirAnnotationCallChecker;", "_annotationCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirAnnotationChecker;", "_arrayLiteralCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirArrayLiteralChecker;", "_basicExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirBasicExpressionChecker;", "_blockCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirBlockChecker;", "_callCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirCallChecker;", "_callableReferenceAccessCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirCallableReferenceAccessChecker;", "_checkNotNullCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckNotNullCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirCheckNotNullCallChecker;", "_classReferenceExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirClassReferenceExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirClassReferenceExpressionChecker;", "_constExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirConstExpressionChecker;", "_doWhileLoopCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirDoWhileLoop;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirDoWhileLoopChecker;", "_elvisExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirElvisExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirElvisExpressionChecker;", "_equalityOperatorCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirEqualityOperatorCallChecker;", "_functionCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirFunctionCallChecker;", "_getClassCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirGetClassCallChecker;", "_inaccessibleReceiverCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirInaccessibleReceiverExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirInaccessibleReceiverChecker;", "_integerLiteralOperatorCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirIntegerLiteralOperatorCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirIntegerLiteralOperatorCallChecker;", "_logicExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirLogicExpressionChecker;", "_loopExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirLoop;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirLoopExpressionChecker;", "_loopJumpCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirLoopJump;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirLoopJumpChecker;", "_propertyAccessExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirPropertyAccessExpressionChecker;", "_qualifiedAccessExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "_resolvedQualifierCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirResolvedQualifierChecker;", "_returnExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirReturnExpressionChecker;", "_safeCallExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirSafeCallExpressionChecker;", "_stringConcatenationCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirStringConcatenationCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirStringConcatenationCallChecker;", "_thisReceiverExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirThisReceiverExpressionChecker;", "_throwExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirThrowExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirThrowExpressionChecker;", "_tryExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirTryExpressionChecker;", "_typeOperatorCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirTypeOperatorCallChecker;", "_variableAssignmentCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirVariableAssignmentChecker;", "_whenExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirWhenExpressionChecker;", "_whileLoopCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirWhileLoop;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirWhileLoopChecker;", "annotationCallCheckers", "", "getAnnotationCallCheckers", "()Ljava/util/Set;", "annotationCheckers", "getAnnotationCheckers", "arrayLiteralCheckers", "getArrayLiteralCheckers", "basicExpressionCheckers", "getBasicExpressionCheckers", "blockCheckers", "getBlockCheckers", "callCheckers", "getCallCheckers", "callableReferenceAccessCheckers", "getCallableReferenceAccessCheckers", "checkNotNullCallCheckers", "getCheckNotNullCallCheckers", "classReferenceExpressionCheckers", "getClassReferenceExpressionCheckers", "constExpressionCheckers", "getConstExpressionCheckers", "doWhileLoopCheckers", "getDoWhileLoopCheckers", "elvisExpressionCheckers", "getElvisExpressionCheckers", "equalityOperatorCallCheckers", "getEqualityOperatorCallCheckers", "functionCallCheckers", "getFunctionCallCheckers", "getClassCallCheckers", "getGetClassCallCheckers", "inaccessibleReceiverCheckers", "getInaccessibleReceiverCheckers", "integerLiteralOperatorCallCheckers", "getIntegerLiteralOperatorCallCheckers", "logicExpressionCheckers", "getLogicExpressionCheckers", "loopExpressionCheckers", "getLoopExpressionCheckers", "loopJumpCheckers", "getLoopJumpCheckers", "propertyAccessExpressionCheckers", "getPropertyAccessExpressionCheckers", "qualifiedAccessExpressionCheckers", "getQualifiedAccessExpressionCheckers", "resolvedQualifierCheckers", "getResolvedQualifierCheckers", "returnExpressionCheckers", "getReturnExpressionCheckers", "safeCallExpressionCheckers", "getSafeCallExpressionCheckers", "stringConcatenationCallCheckers", "getStringConcatenationCallCheckers", "thisReceiverExpressionCheckers", "getThisReceiverExpressionCheckers", "throwExpressionCheckers", "getThrowExpressionCheckers", "tryExpressionCheckers", "getTryExpressionCheckers", "typeOperatorCallCheckers", "getTypeOperatorCallCheckers", "variableAssignmentCheckers", "getVariableAssignmentCheckers", "whenExpressionCheckers", "getWhenExpressionCheckers", "whileLoopCheckers", "getWhileLoopCheckers", "register", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ComposedExpressionCheckers extends ExpressionCheckers {
    private final Set<FirExpressionChecker<FirStatement>> _basicExpressionCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirQualifiedAccessExpression>> _qualifiedAccessExpressionCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirCall>> _callCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirFunctionCall>> _functionCallCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirPropertyAccessExpression>> _propertyAccessExpressionCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirIntegerLiteralOperatorCall>> _integerLiteralOperatorCallCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirVariableAssignment>> _variableAssignmentCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirTryExpression>> _tryExpressionCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirWhenExpression>> _whenExpressionCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirLoop>> _loopExpressionCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirLoopJump>> _loopJumpCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirBinaryLogicExpression>> _logicExpressionCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirReturnExpression>> _returnExpressionCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirBlock>> _blockCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirAnnotation>> _annotationCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirAnnotationCall>> _annotationCallCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirCheckNotNullCall>> _checkNotNullCallCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirElvisExpression>> _elvisExpressionCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirGetClassCall>> _getClassCallCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirSafeCallExpression>> _safeCallExpressionCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirEqualityOperatorCall>> _equalityOperatorCallCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirStringConcatenationCall>> _stringConcatenationCallCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirTypeOperatorCall>> _typeOperatorCallCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirResolvedQualifier>> _resolvedQualifierCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirConstExpression<?>>> _constExpressionCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirCallableReferenceAccess>> _callableReferenceAccessCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirThisReceiverExpression>> _thisReceiverExpressionCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirWhileLoop>> _whileLoopCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirThrowExpression>> _throwExpressionCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirDoWhileLoop>> _doWhileLoopCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirArrayLiteral>> _arrayLiteralCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirClassReferenceExpression>> _classReferenceExpressionCheckers = new LinkedHashSet();
    private final Set<FirExpressionChecker<FirInaccessibleReceiverExpression>> _inaccessibleReceiverCheckers = new LinkedHashSet();

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirStatement>> getBasicExpressionCheckers() {
        return this._basicExpressionCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirQualifiedAccessExpression>> getQualifiedAccessExpressionCheckers() {
        return this._qualifiedAccessExpressionCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirCall>> getCallCheckers() {
        return this._callCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirFunctionCall>> getFunctionCallCheckers() {
        return this._functionCallCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirPropertyAccessExpression>> getPropertyAccessExpressionCheckers() {
        return this._propertyAccessExpressionCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirIntegerLiteralOperatorCall>> getIntegerLiteralOperatorCallCheckers() {
        return this._integerLiteralOperatorCallCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirVariableAssignment>> getVariableAssignmentCheckers() {
        return this._variableAssignmentCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirTryExpression>> getTryExpressionCheckers() {
        return this._tryExpressionCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirWhenExpression>> getWhenExpressionCheckers() {
        return this._whenExpressionCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirLoop>> getLoopExpressionCheckers() {
        return this._loopExpressionCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirLoopJump>> getLoopJumpCheckers() {
        return this._loopJumpCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirBinaryLogicExpression>> getLogicExpressionCheckers() {
        return this._logicExpressionCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirReturnExpression>> getReturnExpressionCheckers() {
        return this._returnExpressionCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirBlock>> getBlockCheckers() {
        return this._blockCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirAnnotation>> getAnnotationCheckers() {
        return this._annotationCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirAnnotationCall>> getAnnotationCallCheckers() {
        return this._annotationCallCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirCheckNotNullCall>> getCheckNotNullCallCheckers() {
        return this._checkNotNullCallCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirElvisExpression>> getElvisExpressionCheckers() {
        return this._elvisExpressionCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirGetClassCall>> getGetClassCallCheckers() {
        return this._getClassCallCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirSafeCallExpression>> getSafeCallExpressionCheckers() {
        return this._safeCallExpressionCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirEqualityOperatorCall>> getEqualityOperatorCallCheckers() {
        return this._equalityOperatorCallCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirStringConcatenationCall>> getStringConcatenationCallCheckers() {
        return this._stringConcatenationCallCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirTypeOperatorCall>> getTypeOperatorCallCheckers() {
        return this._typeOperatorCallCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirResolvedQualifier>> getResolvedQualifierCheckers() {
        return this._resolvedQualifierCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirConstExpression<?>>> getConstExpressionCheckers() {
        return this._constExpressionCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirCallableReferenceAccess>> getCallableReferenceAccessCheckers() {
        return this._callableReferenceAccessCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirThisReceiverExpression>> getThisReceiverExpressionCheckers() {
        return this._thisReceiverExpressionCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirWhileLoop>> getWhileLoopCheckers() {
        return this._whileLoopCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirThrowExpression>> getThrowExpressionCheckers() {
        return this._throwExpressionCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirDoWhileLoop>> getDoWhileLoopCheckers() {
        return this._doWhileLoopCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirArrayLiteral>> getArrayLiteralCheckers() {
        return this._arrayLiteralCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirClassReferenceExpression>> getClassReferenceExpressionCheckers() {
        return this._classReferenceExpressionCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirInaccessibleReceiverExpression>> getInaccessibleReceiverCheckers() {
        return this._inaccessibleReceiverCheckers;
    }

    @CheckersComponentInternal
    public final void register(ExpressionCheckers checkers) {
        Intrinsics.checkNotNullParameter(checkers, "checkers");
        CollectionsKt.addAll(this._basicExpressionCheckers, checkers.getBasicExpressionCheckers());
        CollectionsKt.addAll(this._qualifiedAccessExpressionCheckers, checkers.getQualifiedAccessExpressionCheckers());
        CollectionsKt.addAll(this._callCheckers, checkers.getCallCheckers());
        CollectionsKt.addAll(this._functionCallCheckers, checkers.getFunctionCallCheckers());
        CollectionsKt.addAll(this._propertyAccessExpressionCheckers, checkers.getPropertyAccessExpressionCheckers());
        CollectionsKt.addAll(this._integerLiteralOperatorCallCheckers, checkers.getIntegerLiteralOperatorCallCheckers());
        CollectionsKt.addAll(this._variableAssignmentCheckers, checkers.getVariableAssignmentCheckers());
        CollectionsKt.addAll(this._tryExpressionCheckers, checkers.getTryExpressionCheckers());
        CollectionsKt.addAll(this._whenExpressionCheckers, checkers.getWhenExpressionCheckers());
        CollectionsKt.addAll(this._loopExpressionCheckers, checkers.getLoopExpressionCheckers());
        CollectionsKt.addAll(this._loopJumpCheckers, checkers.getLoopJumpCheckers());
        CollectionsKt.addAll(this._logicExpressionCheckers, checkers.getLogicExpressionCheckers());
        CollectionsKt.addAll(this._returnExpressionCheckers, checkers.getReturnExpressionCheckers());
        CollectionsKt.addAll(this._blockCheckers, checkers.getBlockCheckers());
        CollectionsKt.addAll(this._annotationCheckers, checkers.getAnnotationCheckers());
        CollectionsKt.addAll(this._annotationCallCheckers, checkers.getAnnotationCallCheckers());
        CollectionsKt.addAll(this._checkNotNullCallCheckers, checkers.getCheckNotNullCallCheckers());
        CollectionsKt.addAll(this._elvisExpressionCheckers, checkers.getElvisExpressionCheckers());
        CollectionsKt.addAll(this._getClassCallCheckers, checkers.getGetClassCallCheckers());
        CollectionsKt.addAll(this._safeCallExpressionCheckers, checkers.getSafeCallExpressionCheckers());
        CollectionsKt.addAll(this._equalityOperatorCallCheckers, checkers.getEqualityOperatorCallCheckers());
        CollectionsKt.addAll(this._stringConcatenationCallCheckers, checkers.getStringConcatenationCallCheckers());
        CollectionsKt.addAll(this._typeOperatorCallCheckers, checkers.getTypeOperatorCallCheckers());
        CollectionsKt.addAll(this._resolvedQualifierCheckers, checkers.getResolvedQualifierCheckers());
        CollectionsKt.addAll(this._constExpressionCheckers, checkers.getConstExpressionCheckers());
        CollectionsKt.addAll(this._callableReferenceAccessCheckers, checkers.getCallableReferenceAccessCheckers());
        CollectionsKt.addAll(this._thisReceiverExpressionCheckers, checkers.getThisReceiverExpressionCheckers());
        CollectionsKt.addAll(this._whileLoopCheckers, checkers.getWhileLoopCheckers());
        CollectionsKt.addAll(this._throwExpressionCheckers, checkers.getThrowExpressionCheckers());
        CollectionsKt.addAll(this._doWhileLoopCheckers, checkers.getDoWhileLoopCheckers());
        CollectionsKt.addAll(this._arrayLiteralCheckers, checkers.getArrayLiteralCheckers());
        CollectionsKt.addAll(this._classReferenceExpressionCheckers, checkers.getClassReferenceExpressionCheckers());
        CollectionsKt.addAll(this._inaccessibleReceiverCheckers, checkers.getInaccessibleReceiverCheckers());
    }
}
