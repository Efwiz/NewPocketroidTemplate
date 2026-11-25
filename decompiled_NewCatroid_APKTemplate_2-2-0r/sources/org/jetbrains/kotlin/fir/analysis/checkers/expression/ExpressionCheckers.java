package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
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

/* JADX INFO: compiled from: ExpressionCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\bG\b&\u0018\u0000 \u008f\u00022\u00020\u0001:\u0002\u008f\u0002B\u0005¢\u0006\u0002\u0010\u0002R1\u0010\u0003\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b\u000b\u0010\f\u0012\u0004\b\b\u0010\u0002\u001a\u0004\b\t\u0010\nR1\u0010\r\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000e0\u0005j\u0002`\u000f0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b\u0012\u0010\f\u0012\u0004\b\u0010\u0010\u0002\u001a\u0004\b\u0011\u0010\nR1\u0010\u0013\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00140\u0005j\u0002`\u00150\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b\u0018\u0010\f\u0012\u0004\b\u0016\u0010\u0002\u001a\u0004\b\u0017\u0010\nR1\u0010\u0019\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u001a0\u0005j\u0002`\u001b0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b\u001e\u0010\f\u0012\u0004\b\u001c\u0010\u0002\u001a\u0004\b\u001d\u0010\nR1\u0010\u001f\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020 0\u0005j\u0002`!0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b$\u0010\f\u0012\u0004\b\"\u0010\u0002\u001a\u0004\b#\u0010\nR1\u0010%\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020&0\u0005j\u0002`'0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b*\u0010\f\u0012\u0004\b(\u0010\u0002\u001a\u0004\b)\u0010\nR1\u0010+\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020,0\u0005j\u0002`-0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b0\u0010\f\u0012\u0004\b.\u0010\u0002\u001a\u0004\b/\u0010\nR1\u00101\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002020\u0005j\u0002`30\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b6\u0010\f\u0012\u0004\b4\u0010\u0002\u001a\u0004\b5\u0010\nR1\u00107\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002080\u0005j\u0002`90\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b<\u0010\f\u0012\u0004\b:\u0010\u0002\u001a\u0004\b;\u0010\nR5\u0010=\u001a\u0016\u0012\u0012\u0012\u0010\u0012\b\u0012\u0006\u0012\u0002\b\u00030>0\u0005j\u0002`?0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\bB\u0010\f\u0012\u0004\b@\u0010\u0002\u001a\u0004\bA\u0010\nR1\u0010C\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020D0\u0005j\u0002`E0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\bH\u0010\f\u0012\u0004\bF\u0010\u0002\u001a\u0004\bG\u0010\nR1\u0010I\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020J0\u0005j\u0002`K0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\bN\u0010\f\u0012\u0004\bL\u0010\u0002\u001a\u0004\bM\u0010\nR1\u0010O\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020P0\u0005j\u0002`Q0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\bT\u0010\f\u0012\u0004\bR\u0010\u0002\u001a\u0004\bS\u0010\nR1\u0010U\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020V0\u0005j\u0002`W0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\bZ\u0010\f\u0012\u0004\bX\u0010\u0002\u001a\u0004\bY\u0010\nR1\u0010[\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\\0\u0005j\u0002`]0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b`\u0010\f\u0012\u0004\b^\u0010\u0002\u001a\u0004\b_\u0010\nR1\u0010a\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020b0\u0005j\u0002`c0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\bf\u0010\f\u0012\u0004\bd\u0010\u0002\u001a\u0004\be\u0010\nR1\u0010g\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020h0\u0005j\u0002`i0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\bl\u0010\f\u0012\u0004\bj\u0010\u0002\u001a\u0004\bk\u0010\nR1\u0010m\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020n0\u0005j\u0002`o0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\br\u0010\f\u0012\u0004\bp\u0010\u0002\u001a\u0004\bq\u0010\nR1\u0010s\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020t0\u0005j\u0002`u0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\bx\u0010\f\u0012\u0004\bv\u0010\u0002\u001a\u0004\bw\u0010\nR1\u0010y\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020z0\u0005j\u0002`{0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b~\u0010\f\u0012\u0004\b|\u0010\u0002\u001a\u0004\b}\u0010\nR6\u0010\u007f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030\u0080\u00010\u0005j\u0003`\u0081\u00010\u00048@X\u0081\u0084\u0002¢\u0006\u0015\n\u0005\b\u0084\u0001\u0010\f\u0012\u0005\b\u0082\u0001\u0010\u0002\u001a\u0005\b\u0083\u0001\u0010\nR7\u0010\u0085\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030\u0086\u00010\u0005j\u0003`\u0087\u00010\u00048@X\u0081\u0084\u0002¢\u0006\u0015\n\u0005\b\u008a\u0001\u0010\f\u0012\u0005\b\u0088\u0001\u0010\u0002\u001a\u0005\b\u0089\u0001\u0010\nR7\u0010\u008b\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030\u008c\u00010\u0005j\u0003`\u008d\u00010\u00048@X\u0081\u0084\u0002¢\u0006\u0015\n\u0005\b\u0090\u0001\u0010\f\u0012\u0005\b\u008e\u0001\u0010\u0002\u001a\u0005\b\u008f\u0001\u0010\nR7\u0010\u0091\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030\u0092\u00010\u0005j\u0003`\u0093\u00010\u00048@X\u0081\u0084\u0002¢\u0006\u0015\n\u0005\b\u0096\u0001\u0010\f\u0012\u0005\b\u0094\u0001\u0010\u0002\u001a\u0005\b\u0095\u0001\u0010\nR7\u0010\u0097\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030\u0098\u00010\u0005j\u0003`\u0099\u00010\u00048@X\u0081\u0084\u0002¢\u0006\u0015\n\u0005\b\u009c\u0001\u0010\f\u0012\u0005\b\u009a\u0001\u0010\u0002\u001a\u0005\b\u009b\u0001\u0010\nR7\u0010\u009d\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030\u009e\u00010\u0005j\u0003`\u009f\u00010\u00048@X\u0081\u0084\u0002¢\u0006\u0015\n\u0005\b¢\u0001\u0010\f\u0012\u0005\b \u0001\u0010\u0002\u001a\u0005\b¡\u0001\u0010\nR7\u0010£\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030¤\u00010\u0005j\u0003`¥\u00010\u00048@X\u0081\u0084\u0002¢\u0006\u0015\n\u0005\b¨\u0001\u0010\f\u0012\u0005\b¦\u0001\u0010\u0002\u001a\u0005\b§\u0001\u0010\nR7\u0010©\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030ª\u00010\u0005j\u0003`«\u00010\u00048@X\u0081\u0084\u0002¢\u0006\u0015\n\u0005\b®\u0001\u0010\f\u0012\u0005\b¬\u0001\u0010\u0002\u001a\u0005\b\u00ad\u0001\u0010\nR7\u0010¯\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030°\u00010\u0005j\u0003`±\u00010\u00048@X\u0081\u0084\u0002¢\u0006\u0015\n\u0005\b´\u0001\u0010\f\u0012\u0005\b²\u0001\u0010\u0002\u001a\u0005\b³\u0001\u0010\nR7\u0010µ\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030¶\u00010\u0005j\u0003`·\u00010\u00048@X\u0081\u0084\u0002¢\u0006\u0015\n\u0005\bº\u0001\u0010\f\u0012\u0005\b¸\u0001\u0010\u0002\u001a\u0005\b¹\u0001\u0010\nR7\u0010»\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030¼\u00010\u0005j\u0003`½\u00010\u00048@X\u0081\u0084\u0002¢\u0006\u0015\n\u0005\bÀ\u0001\u0010\f\u0012\u0005\b¾\u0001\u0010\u0002\u001a\u0005\b¿\u0001\u0010\nR7\u0010Á\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030Â\u00010\u0005j\u0003`Ã\u00010\u00048@X\u0081\u0084\u0002¢\u0006\u0015\n\u0005\bÆ\u0001\u0010\f\u0012\u0005\bÄ\u0001\u0010\u0002\u001a\u0005\bÅ\u0001\u0010\nR7\u0010Ç\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030È\u00010\u0005j\u0003`É\u00010\u00048@X\u0081\u0084\u0002¢\u0006\u0015\n\u0005\bÌ\u0001\u0010\f\u0012\u0005\bÊ\u0001\u0010\u0002\u001a\u0005\bË\u0001\u0010\nR&\u0010Í\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÎ\u0001\u0010\nR&\u0010Ï\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000e0\u0005j\u0002`\u000f0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÐ\u0001\u0010\nR&\u0010Ñ\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00140\u0005j\u0002`\u00150\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÒ\u0001\u0010\nR&\u0010Ó\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u001a0\u0005j\u0002`\u001b0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÔ\u0001\u0010\nR&\u0010Õ\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020 0\u0005j\u0002`!0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÖ\u0001\u0010\nR&\u0010×\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020&0\u0005j\u0002`'0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bØ\u0001\u0010\nR&\u0010Ù\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020,0\u0005j\u0002`-0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÚ\u0001\u0010\nR&\u0010Û\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002020\u0005j\u0002`30\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÜ\u0001\u0010\nR&\u0010Ý\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002080\u0005j\u0002`90\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÞ\u0001\u0010\nR*\u0010ß\u0001\u001a\u0016\u0012\u0012\u0012\u0010\u0012\b\u0012\u0006\u0012\u0002\b\u00030>0\u0005j\u0002`?0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bà\u0001\u0010\nR&\u0010á\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020D0\u0005j\u0002`E0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bâ\u0001\u0010\nR&\u0010ã\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020J0\u0005j\u0002`K0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bä\u0001\u0010\nR&\u0010å\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020P0\u0005j\u0002`Q0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bæ\u0001\u0010\nR&\u0010ç\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020V0\u0005j\u0002`W0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bè\u0001\u0010\nR&\u0010é\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\\0\u0005j\u0002`]0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bê\u0001\u0010\nR&\u0010ë\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020b0\u0005j\u0002`c0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bì\u0001\u0010\nR&\u0010í\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020h0\u0005j\u0002`i0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bî\u0001\u0010\nR&\u0010ï\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020n0\u0005j\u0002`o0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bð\u0001\u0010\nR&\u0010ñ\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020t0\u0005j\u0002`u0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bò\u0001\u0010\nR&\u0010ó\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020z0\u0005j\u0002`{0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bô\u0001\u0010\nR(\u0010õ\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030\u0080\u00010\u0005j\u0003`\u0081\u00010\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bö\u0001\u0010\nR(\u0010÷\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030\u0086\u00010\u0005j\u0003`\u0087\u00010\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bø\u0001\u0010\nR(\u0010ù\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030\u008c\u00010\u0005j\u0003`\u008d\u00010\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bú\u0001\u0010\nR(\u0010û\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030\u0092\u00010\u0005j\u0003`\u0093\u00010\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bü\u0001\u0010\nR(\u0010ý\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030\u0098\u00010\u0005j\u0003`\u0099\u00010\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bþ\u0001\u0010\nR(\u0010ÿ\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030\u009e\u00010\u0005j\u0003`\u009f\u00010\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0080\u0002\u0010\nR(\u0010\u0081\u0002\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030¤\u00010\u0005j\u0003`¥\u00010\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0082\u0002\u0010\nR(\u0010\u0083\u0002\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030ª\u00010\u0005j\u0003`«\u00010\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0084\u0002\u0010\nR(\u0010\u0085\u0002\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030°\u00010\u0005j\u0003`±\u00010\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0086\u0002\u0010\nR(\u0010\u0087\u0002\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030¶\u00010\u0005j\u0003`·\u00010\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0088\u0002\u0010\nR(\u0010\u0089\u0002\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030¼\u00010\u0005j\u0003`½\u00010\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008a\u0002\u0010\nR(\u0010\u008b\u0002\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030Â\u00010\u0005j\u0003`Ã\u00010\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008c\u0002\u0010\nR(\u0010\u008d\u0002\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0005\u0012\u00030È\u00010\u0005j\u0003`É\u00010\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008e\u0002\u0010\n¨\u0006\u0090\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/ExpressionCheckers;", "", "()V", "allAnnotationCallCheckers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirAnnotationCallChecker;", "getAllAnnotationCallCheckers$checkers$annotations", "getAllAnnotationCallCheckers$checkers", "()Ljava/util/Set;", "allAnnotationCallCheckers$delegate", "Lkotlin/Lazy;", "allAnnotationCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirAnnotationChecker;", "getAllAnnotationCheckers$checkers$annotations", "getAllAnnotationCheckers$checkers", "allAnnotationCheckers$delegate", "allArrayLiteralCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirArrayLiteralChecker;", "getAllArrayLiteralCheckers$checkers$annotations", "getAllArrayLiteralCheckers$checkers", "allArrayLiteralCheckers$delegate", "allBasicExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirBasicExpressionChecker;", "getAllBasicExpressionCheckers$checkers$annotations", "getAllBasicExpressionCheckers$checkers", "allBasicExpressionCheckers$delegate", "allBlockCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirBlockChecker;", "getAllBlockCheckers$checkers$annotations", "getAllBlockCheckers$checkers", "allBlockCheckers$delegate", "allCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirCallChecker;", "getAllCallCheckers$checkers$annotations", "getAllCallCheckers$checkers", "allCallCheckers$delegate", "allCallableReferenceAccessCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirCallableReferenceAccessChecker;", "getAllCallableReferenceAccessCheckers$checkers$annotations", "getAllCallableReferenceAccessCheckers$checkers", "allCallableReferenceAccessCheckers$delegate", "allCheckNotNullCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckNotNullCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirCheckNotNullCallChecker;", "getAllCheckNotNullCallCheckers$checkers$annotations", "getAllCheckNotNullCallCheckers$checkers", "allCheckNotNullCallCheckers$delegate", "allClassReferenceExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirClassReferenceExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirClassReferenceExpressionChecker;", "getAllClassReferenceExpressionCheckers$checkers$annotations", "getAllClassReferenceExpressionCheckers$checkers", "allClassReferenceExpressionCheckers$delegate", "allConstExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirConstExpressionChecker;", "getAllConstExpressionCheckers$checkers$annotations", "getAllConstExpressionCheckers$checkers", "allConstExpressionCheckers$delegate", "allDoWhileLoopCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirDoWhileLoop;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirDoWhileLoopChecker;", "getAllDoWhileLoopCheckers$checkers$annotations", "getAllDoWhileLoopCheckers$checkers", "allDoWhileLoopCheckers$delegate", "allElvisExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirElvisExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirElvisExpressionChecker;", "getAllElvisExpressionCheckers$checkers$annotations", "getAllElvisExpressionCheckers$checkers", "allElvisExpressionCheckers$delegate", "allEqualityOperatorCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirEqualityOperatorCallChecker;", "getAllEqualityOperatorCallCheckers$checkers$annotations", "getAllEqualityOperatorCallCheckers$checkers", "allEqualityOperatorCallCheckers$delegate", "allFunctionCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirFunctionCallChecker;", "getAllFunctionCallCheckers$checkers$annotations", "getAllFunctionCallCheckers$checkers", "allFunctionCallCheckers$delegate", "allGetClassCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirGetClassCallChecker;", "getAllGetClassCallCheckers$checkers$annotations", "getAllGetClassCallCheckers$checkers", "allGetClassCallCheckers$delegate", "allInaccessibleReceiverCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirInaccessibleReceiverExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirInaccessibleReceiverChecker;", "getAllInaccessibleReceiverCheckers$checkers$annotations", "getAllInaccessibleReceiverCheckers$checkers", "allInaccessibleReceiverCheckers$delegate", "allIntegerLiteralOperatorCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirIntegerLiteralOperatorCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirIntegerLiteralOperatorCallChecker;", "getAllIntegerLiteralOperatorCallCheckers$checkers$annotations", "getAllIntegerLiteralOperatorCallCheckers$checkers", "allIntegerLiteralOperatorCallCheckers$delegate", "allLogicExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirLogicExpressionChecker;", "getAllLogicExpressionCheckers$checkers$annotations", "getAllLogicExpressionCheckers$checkers", "allLogicExpressionCheckers$delegate", "allLoopExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirLoop;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirLoopExpressionChecker;", "getAllLoopExpressionCheckers$checkers$annotations", "getAllLoopExpressionCheckers$checkers", "allLoopExpressionCheckers$delegate", "allLoopJumpCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirLoopJump;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirLoopJumpChecker;", "getAllLoopJumpCheckers$checkers$annotations", "getAllLoopJumpCheckers$checkers", "allLoopJumpCheckers$delegate", "allPropertyAccessExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirPropertyAccessExpressionChecker;", "getAllPropertyAccessExpressionCheckers$checkers$annotations", "getAllPropertyAccessExpressionCheckers$checkers", "allPropertyAccessExpressionCheckers$delegate", "allQualifiedAccessExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "getAllQualifiedAccessExpressionCheckers$checkers$annotations", "getAllQualifiedAccessExpressionCheckers$checkers", "allQualifiedAccessExpressionCheckers$delegate", "allResolvedQualifierCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirResolvedQualifierChecker;", "getAllResolvedQualifierCheckers$checkers$annotations", "getAllResolvedQualifierCheckers$checkers", "allResolvedQualifierCheckers$delegate", "allReturnExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirReturnExpressionChecker;", "getAllReturnExpressionCheckers$checkers$annotations", "getAllReturnExpressionCheckers$checkers", "allReturnExpressionCheckers$delegate", "allSafeCallExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirSafeCallExpressionChecker;", "getAllSafeCallExpressionCheckers$checkers$annotations", "getAllSafeCallExpressionCheckers$checkers", "allSafeCallExpressionCheckers$delegate", "allStringConcatenationCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirStringConcatenationCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirStringConcatenationCallChecker;", "getAllStringConcatenationCallCheckers$checkers$annotations", "getAllStringConcatenationCallCheckers$checkers", "allStringConcatenationCallCheckers$delegate", "allThisReceiverExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirThisReceiverExpressionChecker;", "getAllThisReceiverExpressionCheckers$checkers$annotations", "getAllThisReceiverExpressionCheckers$checkers", "allThisReceiverExpressionCheckers$delegate", "allThrowExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirThrowExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirThrowExpressionChecker;", "getAllThrowExpressionCheckers$checkers$annotations", "getAllThrowExpressionCheckers$checkers", "allThrowExpressionCheckers$delegate", "allTryExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirTryExpressionChecker;", "getAllTryExpressionCheckers$checkers$annotations", "getAllTryExpressionCheckers$checkers", "allTryExpressionCheckers$delegate", "allTypeOperatorCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirTypeOperatorCallChecker;", "getAllTypeOperatorCallCheckers$checkers$annotations", "getAllTypeOperatorCallCheckers$checkers", "allTypeOperatorCallCheckers$delegate", "allVariableAssignmentCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirVariableAssignmentChecker;", "getAllVariableAssignmentCheckers$checkers$annotations", "getAllVariableAssignmentCheckers$checkers", "allVariableAssignmentCheckers$delegate", "allWhenExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirWhenExpressionChecker;", "getAllWhenExpressionCheckers$checkers$annotations", "getAllWhenExpressionCheckers$checkers", "allWhenExpressionCheckers$delegate", "allWhileLoopCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirWhileLoop;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirWhileLoopChecker;", "getAllWhileLoopCheckers$checkers$annotations", "getAllWhileLoopCheckers$checkers", "allWhileLoopCheckers$delegate", "annotationCallCheckers", "getAnnotationCallCheckers", "annotationCheckers", "getAnnotationCheckers", "arrayLiteralCheckers", "getArrayLiteralCheckers", "basicExpressionCheckers", "getBasicExpressionCheckers", "blockCheckers", "getBlockCheckers", "callCheckers", "getCallCheckers", "callableReferenceAccessCheckers", "getCallableReferenceAccessCheckers", "checkNotNullCallCheckers", "getCheckNotNullCallCheckers", "classReferenceExpressionCheckers", "getClassReferenceExpressionCheckers", "constExpressionCheckers", "getConstExpressionCheckers", "doWhileLoopCheckers", "getDoWhileLoopCheckers", "elvisExpressionCheckers", "getElvisExpressionCheckers", "equalityOperatorCallCheckers", "getEqualityOperatorCallCheckers", "functionCallCheckers", "getFunctionCallCheckers", "getClassCallCheckers", "getGetClassCallCheckers", "inaccessibleReceiverCheckers", "getInaccessibleReceiverCheckers", "integerLiteralOperatorCallCheckers", "getIntegerLiteralOperatorCallCheckers", "logicExpressionCheckers", "getLogicExpressionCheckers", "loopExpressionCheckers", "getLoopExpressionCheckers", "loopJumpCheckers", "getLoopJumpCheckers", "propertyAccessExpressionCheckers", "getPropertyAccessExpressionCheckers", "qualifiedAccessExpressionCheckers", "getQualifiedAccessExpressionCheckers", "resolvedQualifierCheckers", "getResolvedQualifierCheckers", "returnExpressionCheckers", "getReturnExpressionCheckers", "safeCallExpressionCheckers", "getSafeCallExpressionCheckers", "stringConcatenationCallCheckers", "getStringConcatenationCallCheckers", "thisReceiverExpressionCheckers", "getThisReceiverExpressionCheckers", "throwExpressionCheckers", "getThrowExpressionCheckers", "tryExpressionCheckers", "getTryExpressionCheckers", "typeOperatorCallCheckers", "getTypeOperatorCallCheckers", "variableAssignmentCheckers", "getVariableAssignmentCheckers", "whenExpressionCheckers", "getWhenExpressionCheckers", "whileLoopCheckers", "getWhileLoopCheckers", "Companion", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ExpressionCheckers {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ExpressionCheckers EMPTY = new ExpressionCheckers() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$Companion$EMPTY$1
    };
    private final Set<FirExpressionChecker<FirStatement>> basicExpressionCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirQualifiedAccessExpression>> qualifiedAccessExpressionCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirCall>> callCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirFunctionCall>> functionCallCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirPropertyAccessExpression>> propertyAccessExpressionCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirIntegerLiteralOperatorCall>> integerLiteralOperatorCallCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirVariableAssignment>> variableAssignmentCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirTryExpression>> tryExpressionCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirWhenExpression>> whenExpressionCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirLoop>> loopExpressionCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirLoopJump>> loopJumpCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirBinaryLogicExpression>> logicExpressionCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirReturnExpression>> returnExpressionCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirBlock>> blockCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirAnnotation>> annotationCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirAnnotationCall>> annotationCallCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirCheckNotNullCall>> checkNotNullCallCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirElvisExpression>> elvisExpressionCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirGetClassCall>> getClassCallCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirSafeCallExpression>> safeCallExpressionCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirEqualityOperatorCall>> equalityOperatorCallCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirStringConcatenationCall>> stringConcatenationCallCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirTypeOperatorCall>> typeOperatorCallCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirResolvedQualifier>> resolvedQualifierCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirConstExpression<?>>> constExpressionCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirCallableReferenceAccess>> callableReferenceAccessCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirThisReceiverExpression>> thisReceiverExpressionCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirWhileLoop>> whileLoopCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirThrowExpression>> throwExpressionCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirDoWhileLoop>> doWhileLoopCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirArrayLiteral>> arrayLiteralCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirClassReferenceExpression>> classReferenceExpressionCheckers = SetsKt.emptySet();
    private final Set<FirExpressionChecker<FirInaccessibleReceiverExpression>> inaccessibleReceiverCheckers = SetsKt.emptySet();

    /* JADX INFO: renamed from: allBasicExpressionCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allBasicExpressionCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirStatement>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allBasicExpressionCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirStatement>> invoke() {
            return this.this$0.getBasicExpressionCheckers();
        }
    });

    /* JADX INFO: renamed from: allQualifiedAccessExpressionCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allQualifiedAccessExpressionCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirQualifiedAccessExpression>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allQualifiedAccessExpressionCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirQualifiedAccessExpression>> invoke() {
            return SetsKt.plus((Set) this.this$0.getQualifiedAccessExpressionCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allCallCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allCallCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirCall>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allCallCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirCall>> invoke() {
            return SetsKt.plus((Set) this.this$0.getCallCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allFunctionCallCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allFunctionCallCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirFunctionCall>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allFunctionCallCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirFunctionCall>> invoke() {
            return SetsKt.plus(SetsKt.plus(SetsKt.plus((Set) this.this$0.getFunctionCallCheckers(), (Iterable) this.this$0.getQualifiedAccessExpressionCheckers()), (Iterable) this.this$0.getBasicExpressionCheckers()), (Iterable) this.this$0.getCallCheckers());
        }
    });

    /* JADX INFO: renamed from: allPropertyAccessExpressionCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allPropertyAccessExpressionCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirPropertyAccessExpression>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allPropertyAccessExpressionCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirPropertyAccessExpression>> invoke() {
            return SetsKt.plus(SetsKt.plus((Set) this.this$0.getPropertyAccessExpressionCheckers(), (Iterable) this.this$0.getQualifiedAccessExpressionCheckers()), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allIntegerLiteralOperatorCallCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allIntegerLiteralOperatorCallCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirIntegerLiteralOperatorCall>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allIntegerLiteralOperatorCallCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirIntegerLiteralOperatorCall>> invoke() {
            return SetsKt.plus(SetsKt.plus(SetsKt.plus(SetsKt.plus((Set) this.this$0.getIntegerLiteralOperatorCallCheckers(), (Iterable) this.this$0.getFunctionCallCheckers()), (Iterable) this.this$0.getQualifiedAccessExpressionCheckers()), (Iterable) this.this$0.getBasicExpressionCheckers()), (Iterable) this.this$0.getCallCheckers());
        }
    });

    /* JADX INFO: renamed from: allVariableAssignmentCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allVariableAssignmentCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirVariableAssignment>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allVariableAssignmentCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirVariableAssignment>> invoke() {
            return SetsKt.plus((Set) this.this$0.getVariableAssignmentCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allTryExpressionCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allTryExpressionCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirTryExpression>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allTryExpressionCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirTryExpression>> invoke() {
            return SetsKt.plus((Set) this.this$0.getTryExpressionCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allWhenExpressionCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allWhenExpressionCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirWhenExpression>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allWhenExpressionCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirWhenExpression>> invoke() {
            return SetsKt.plus((Set) this.this$0.getWhenExpressionCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allLoopExpressionCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allLoopExpressionCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirLoop>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allLoopExpressionCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirLoop>> invoke() {
            return SetsKt.plus((Set) this.this$0.getLoopExpressionCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allLoopJumpCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allLoopJumpCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirLoopJump>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allLoopJumpCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirLoopJump>> invoke() {
            return SetsKt.plus((Set) this.this$0.getLoopJumpCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allLogicExpressionCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allLogicExpressionCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirBinaryLogicExpression>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allLogicExpressionCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirBinaryLogicExpression>> invoke() {
            return SetsKt.plus((Set) this.this$0.getLogicExpressionCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allReturnExpressionCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allReturnExpressionCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirReturnExpression>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allReturnExpressionCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirReturnExpression>> invoke() {
            return SetsKt.plus((Set) this.this$0.getReturnExpressionCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allBlockCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allBlockCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirBlock>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allBlockCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirBlock>> invoke() {
            return SetsKt.plus((Set) this.this$0.getBlockCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allAnnotationCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allAnnotationCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirAnnotation>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allAnnotationCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirAnnotation>> invoke() {
            return SetsKt.plus((Set) this.this$0.getAnnotationCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allAnnotationCallCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allAnnotationCallCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirAnnotationCall>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allAnnotationCallCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirAnnotationCall>> invoke() {
            return SetsKt.plus(SetsKt.plus(SetsKt.plus((Set) this.this$0.getAnnotationCallCheckers(), (Iterable) this.this$0.getAnnotationCheckers()), (Iterable) this.this$0.getBasicExpressionCheckers()), (Iterable) this.this$0.getCallCheckers());
        }
    });

    /* JADX INFO: renamed from: allCheckNotNullCallCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allCheckNotNullCallCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirCheckNotNullCall>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allCheckNotNullCallCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirCheckNotNullCall>> invoke() {
            return SetsKt.plus(SetsKt.plus((Set) this.this$0.getCheckNotNullCallCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers()), (Iterable) this.this$0.getCallCheckers());
        }
    });

    /* JADX INFO: renamed from: allElvisExpressionCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allElvisExpressionCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirElvisExpression>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allElvisExpressionCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirElvisExpression>> invoke() {
            return SetsKt.plus((Set) this.this$0.getElvisExpressionCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allGetClassCallCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allGetClassCallCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirGetClassCall>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allGetClassCallCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirGetClassCall>> invoke() {
            return SetsKt.plus(SetsKt.plus((Set) this.this$0.getGetClassCallCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers()), (Iterable) this.this$0.getCallCheckers());
        }
    });

    /* JADX INFO: renamed from: allSafeCallExpressionCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allSafeCallExpressionCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirSafeCallExpression>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allSafeCallExpressionCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirSafeCallExpression>> invoke() {
            return SetsKt.plus((Set) this.this$0.getSafeCallExpressionCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allEqualityOperatorCallCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allEqualityOperatorCallCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirEqualityOperatorCall>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allEqualityOperatorCallCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirEqualityOperatorCall>> invoke() {
            return SetsKt.plus(SetsKt.plus((Set) this.this$0.getEqualityOperatorCallCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers()), (Iterable) this.this$0.getCallCheckers());
        }
    });

    /* JADX INFO: renamed from: allStringConcatenationCallCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allStringConcatenationCallCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirStringConcatenationCall>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allStringConcatenationCallCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirStringConcatenationCall>> invoke() {
            return SetsKt.plus(SetsKt.plus((Set) this.this$0.getStringConcatenationCallCheckers(), (Iterable) this.this$0.getCallCheckers()), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allTypeOperatorCallCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allTypeOperatorCallCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirTypeOperatorCall>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allTypeOperatorCallCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirTypeOperatorCall>> invoke() {
            return SetsKt.plus(SetsKt.plus((Set) this.this$0.getTypeOperatorCallCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers()), (Iterable) this.this$0.getCallCheckers());
        }
    });

    /* JADX INFO: renamed from: allResolvedQualifierCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allResolvedQualifierCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirResolvedQualifier>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allResolvedQualifierCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirResolvedQualifier>> invoke() {
            return SetsKt.plus((Set) this.this$0.getResolvedQualifierCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allConstExpressionCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allConstExpressionCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirConstExpression<?>>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allConstExpressionCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirConstExpression<?>>> invoke() {
            return SetsKt.plus((Set) this.this$0.getConstExpressionCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allCallableReferenceAccessCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allCallableReferenceAccessCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirCallableReferenceAccess>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allCallableReferenceAccessCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirCallableReferenceAccess>> invoke() {
            return SetsKt.plus(SetsKt.plus((Set) this.this$0.getCallableReferenceAccessCheckers(), (Iterable) this.this$0.getQualifiedAccessExpressionCheckers()), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allThisReceiverExpressionCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allThisReceiverExpressionCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirThisReceiverExpression>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allThisReceiverExpressionCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirThisReceiverExpression>> invoke() {
            return SetsKt.plus(SetsKt.plus((Set) this.this$0.getThisReceiverExpressionCheckers(), (Iterable) this.this$0.getQualifiedAccessExpressionCheckers()), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allWhileLoopCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allWhileLoopCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirWhileLoop>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allWhileLoopCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirWhileLoop>> invoke() {
            return SetsKt.plus(SetsKt.plus((Set) this.this$0.getWhileLoopCheckers(), (Iterable) this.this$0.getLoopExpressionCheckers()), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allThrowExpressionCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allThrowExpressionCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirThrowExpression>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allThrowExpressionCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirThrowExpression>> invoke() {
            return SetsKt.plus((Set) this.this$0.getThrowExpressionCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allDoWhileLoopCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allDoWhileLoopCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirDoWhileLoop>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allDoWhileLoopCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirDoWhileLoop>> invoke() {
            return SetsKt.plus(SetsKt.plus((Set) this.this$0.getDoWhileLoopCheckers(), (Iterable) this.this$0.getLoopExpressionCheckers()), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allArrayLiteralCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allArrayLiteralCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirArrayLiteral>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allArrayLiteralCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirArrayLiteral>> invoke() {
            return SetsKt.plus(SetsKt.plus((Set) this.this$0.getArrayLiteralCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers()), (Iterable) this.this$0.getCallCheckers());
        }
    });

    /* JADX INFO: renamed from: allClassReferenceExpressionCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allClassReferenceExpressionCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirClassReferenceExpression>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allClassReferenceExpressionCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirClassReferenceExpression>> invoke() {
            return SetsKt.plus((Set) this.this$0.getClassReferenceExpressionCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    /* JADX INFO: renamed from: allInaccessibleReceiverCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allInaccessibleReceiverCheckers = LazyKt.lazy(new Function0<Set<? extends FirExpressionChecker<? super FirInaccessibleReceiverExpression>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers$allInaccessibleReceiverCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirExpressionChecker<? super FirInaccessibleReceiverExpression>> invoke() {
            return SetsKt.plus((Set) this.this$0.getInaccessibleReceiverCheckers(), (Iterable) this.this$0.getBasicExpressionCheckers());
        }
    });

    @CheckersComponentInternal
    public static /* synthetic */ void getAllAnnotationCallCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllAnnotationCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllArrayLiteralCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllBasicExpressionCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllBlockCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllCallCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllCallableReferenceAccessCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllCheckNotNullCallCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllClassReferenceExpressionCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllConstExpressionCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllDoWhileLoopCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllElvisExpressionCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllEqualityOperatorCallCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllFunctionCallCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllGetClassCallCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllInaccessibleReceiverCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllIntegerLiteralOperatorCallCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllLogicExpressionCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllLoopExpressionCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllLoopJumpCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllPropertyAccessExpressionCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllQualifiedAccessExpressionCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllResolvedQualifierCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllReturnExpressionCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllSafeCallExpressionCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllStringConcatenationCallCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllThisReceiverExpressionCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllThrowExpressionCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllTryExpressionCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllTypeOperatorCallCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllVariableAssignmentCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllWhenExpressionCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllWhileLoopCheckers$checkers$annotations() {
    }

    /* JADX INFO: compiled from: ExpressionCheckers.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/ExpressionCheckers$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/ExpressionCheckers;", "getEMPTY", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/ExpressionCheckers;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ExpressionCheckers getEMPTY() {
            return ExpressionCheckers.EMPTY;
        }
    }

    public Set<FirExpressionChecker<FirStatement>> getBasicExpressionCheckers() {
        return this.basicExpressionCheckers;
    }

    public Set<FirExpressionChecker<FirQualifiedAccessExpression>> getQualifiedAccessExpressionCheckers() {
        return this.qualifiedAccessExpressionCheckers;
    }

    public Set<FirExpressionChecker<FirCall>> getCallCheckers() {
        return this.callCheckers;
    }

    public Set<FirExpressionChecker<FirFunctionCall>> getFunctionCallCheckers() {
        return this.functionCallCheckers;
    }

    public Set<FirExpressionChecker<FirPropertyAccessExpression>> getPropertyAccessExpressionCheckers() {
        return this.propertyAccessExpressionCheckers;
    }

    public Set<FirExpressionChecker<FirIntegerLiteralOperatorCall>> getIntegerLiteralOperatorCallCheckers() {
        return this.integerLiteralOperatorCallCheckers;
    }

    public Set<FirExpressionChecker<FirVariableAssignment>> getVariableAssignmentCheckers() {
        return this.variableAssignmentCheckers;
    }

    public Set<FirExpressionChecker<FirTryExpression>> getTryExpressionCheckers() {
        return this.tryExpressionCheckers;
    }

    public Set<FirExpressionChecker<FirWhenExpression>> getWhenExpressionCheckers() {
        return this.whenExpressionCheckers;
    }

    public Set<FirExpressionChecker<FirLoop>> getLoopExpressionCheckers() {
        return this.loopExpressionCheckers;
    }

    public Set<FirExpressionChecker<FirLoopJump>> getLoopJumpCheckers() {
        return this.loopJumpCheckers;
    }

    public Set<FirExpressionChecker<FirBinaryLogicExpression>> getLogicExpressionCheckers() {
        return this.logicExpressionCheckers;
    }

    public Set<FirExpressionChecker<FirReturnExpression>> getReturnExpressionCheckers() {
        return this.returnExpressionCheckers;
    }

    public Set<FirExpressionChecker<FirBlock>> getBlockCheckers() {
        return this.blockCheckers;
    }

    public Set<FirExpressionChecker<FirAnnotation>> getAnnotationCheckers() {
        return this.annotationCheckers;
    }

    public Set<FirExpressionChecker<FirAnnotationCall>> getAnnotationCallCheckers() {
        return this.annotationCallCheckers;
    }

    public Set<FirExpressionChecker<FirCheckNotNullCall>> getCheckNotNullCallCheckers() {
        return this.checkNotNullCallCheckers;
    }

    public Set<FirExpressionChecker<FirElvisExpression>> getElvisExpressionCheckers() {
        return this.elvisExpressionCheckers;
    }

    public Set<FirExpressionChecker<FirGetClassCall>> getGetClassCallCheckers() {
        return this.getClassCallCheckers;
    }

    public Set<FirExpressionChecker<FirSafeCallExpression>> getSafeCallExpressionCheckers() {
        return this.safeCallExpressionCheckers;
    }

    public Set<FirExpressionChecker<FirEqualityOperatorCall>> getEqualityOperatorCallCheckers() {
        return this.equalityOperatorCallCheckers;
    }

    public Set<FirExpressionChecker<FirStringConcatenationCall>> getStringConcatenationCallCheckers() {
        return this.stringConcatenationCallCheckers;
    }

    public Set<FirExpressionChecker<FirTypeOperatorCall>> getTypeOperatorCallCheckers() {
        return this.typeOperatorCallCheckers;
    }

    public Set<FirExpressionChecker<FirResolvedQualifier>> getResolvedQualifierCheckers() {
        return this.resolvedQualifierCheckers;
    }

    public Set<FirExpressionChecker<FirConstExpression<?>>> getConstExpressionCheckers() {
        return this.constExpressionCheckers;
    }

    public Set<FirExpressionChecker<FirCallableReferenceAccess>> getCallableReferenceAccessCheckers() {
        return this.callableReferenceAccessCheckers;
    }

    public Set<FirExpressionChecker<FirThisReceiverExpression>> getThisReceiverExpressionCheckers() {
        return this.thisReceiverExpressionCheckers;
    }

    public Set<FirExpressionChecker<FirWhileLoop>> getWhileLoopCheckers() {
        return this.whileLoopCheckers;
    }

    public Set<FirExpressionChecker<FirThrowExpression>> getThrowExpressionCheckers() {
        return this.throwExpressionCheckers;
    }

    public Set<FirExpressionChecker<FirDoWhileLoop>> getDoWhileLoopCheckers() {
        return this.doWhileLoopCheckers;
    }

    public Set<FirExpressionChecker<FirArrayLiteral>> getArrayLiteralCheckers() {
        return this.arrayLiteralCheckers;
    }

    public Set<FirExpressionChecker<FirClassReferenceExpression>> getClassReferenceExpressionCheckers() {
        return this.classReferenceExpressionCheckers;
    }

    public Set<FirExpressionChecker<FirInaccessibleReceiverExpression>> getInaccessibleReceiverCheckers() {
        return this.inaccessibleReceiverCheckers;
    }

    public final Set<FirExpressionChecker<FirStatement>> getAllBasicExpressionCheckers$checkers() {
        return (Set) this.allBasicExpressionCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirQualifiedAccessExpression>> getAllQualifiedAccessExpressionCheckers$checkers() {
        return (Set) this.allQualifiedAccessExpressionCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirCall>> getAllCallCheckers$checkers() {
        return (Set) this.allCallCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirFunctionCall>> getAllFunctionCallCheckers$checkers() {
        return (Set) this.allFunctionCallCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirPropertyAccessExpression>> getAllPropertyAccessExpressionCheckers$checkers() {
        return (Set) this.allPropertyAccessExpressionCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirIntegerLiteralOperatorCall>> getAllIntegerLiteralOperatorCallCheckers$checkers() {
        return (Set) this.allIntegerLiteralOperatorCallCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirVariableAssignment>> getAllVariableAssignmentCheckers$checkers() {
        return (Set) this.allVariableAssignmentCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirTryExpression>> getAllTryExpressionCheckers$checkers() {
        return (Set) this.allTryExpressionCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirWhenExpression>> getAllWhenExpressionCheckers$checkers() {
        return (Set) this.allWhenExpressionCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirLoop>> getAllLoopExpressionCheckers$checkers() {
        return (Set) this.allLoopExpressionCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirLoopJump>> getAllLoopJumpCheckers$checkers() {
        return (Set) this.allLoopJumpCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirBinaryLogicExpression>> getAllLogicExpressionCheckers$checkers() {
        return (Set) this.allLogicExpressionCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirReturnExpression>> getAllReturnExpressionCheckers$checkers() {
        return (Set) this.allReturnExpressionCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirBlock>> getAllBlockCheckers$checkers() {
        return (Set) this.allBlockCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirAnnotation>> getAllAnnotationCheckers$checkers() {
        return (Set) this.allAnnotationCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirAnnotationCall>> getAllAnnotationCallCheckers$checkers() {
        return (Set) this.allAnnotationCallCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirCheckNotNullCall>> getAllCheckNotNullCallCheckers$checkers() {
        return (Set) this.allCheckNotNullCallCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirElvisExpression>> getAllElvisExpressionCheckers$checkers() {
        return (Set) this.allElvisExpressionCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirGetClassCall>> getAllGetClassCallCheckers$checkers() {
        return (Set) this.allGetClassCallCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirSafeCallExpression>> getAllSafeCallExpressionCheckers$checkers() {
        return (Set) this.allSafeCallExpressionCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirEqualityOperatorCall>> getAllEqualityOperatorCallCheckers$checkers() {
        return (Set) this.allEqualityOperatorCallCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirStringConcatenationCall>> getAllStringConcatenationCallCheckers$checkers() {
        return (Set) this.allStringConcatenationCallCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirTypeOperatorCall>> getAllTypeOperatorCallCheckers$checkers() {
        return (Set) this.allTypeOperatorCallCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirResolvedQualifier>> getAllResolvedQualifierCheckers$checkers() {
        return (Set) this.allResolvedQualifierCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirConstExpression<?>>> getAllConstExpressionCheckers$checkers() {
        return (Set) this.allConstExpressionCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirCallableReferenceAccess>> getAllCallableReferenceAccessCheckers$checkers() {
        return (Set) this.allCallableReferenceAccessCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirThisReceiverExpression>> getAllThisReceiverExpressionCheckers$checkers() {
        return (Set) this.allThisReceiverExpressionCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirWhileLoop>> getAllWhileLoopCheckers$checkers() {
        return (Set) this.allWhileLoopCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirThrowExpression>> getAllThrowExpressionCheckers$checkers() {
        return (Set) this.allThrowExpressionCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirDoWhileLoop>> getAllDoWhileLoopCheckers$checkers() {
        return (Set) this.allDoWhileLoopCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirArrayLiteral>> getAllArrayLiteralCheckers$checkers() {
        return (Set) this.allArrayLiteralCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirClassReferenceExpression>> getAllClassReferenceExpressionCheckers$checkers() {
        return (Set) this.allClassReferenceExpressionCheckers.getValue();
    }

    public final Set<FirExpressionChecker<FirInaccessibleReceiverExpression>> getAllInaccessibleReceiverCheckers$checkers() {
        return (Set) this.allInaccessibleReceiverCheckers.getValue();
    }
}
