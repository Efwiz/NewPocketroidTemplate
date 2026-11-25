package org.jetbrains.kotlin.fir.analysis.collectors.components;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.CheckersComponentKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirBreakExpression;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirCheckedSafeCallSubject;
import org.jetbrains.kotlin.fir.expressions.FirClassReferenceExpression;
import org.jetbrains.kotlin.fir.expressions.FirComparisonExpression;
import org.jetbrains.kotlin.fir.expressions.FirComponentCall;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirContinueExpression;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirDesugaredAssignmentValueReferenceExpression;
import org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop;
import org.jetbrains.kotlin.fir.expressions.FirElvisExpression;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirErrorAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirErrorExpression;
import org.jetbrains.kotlin.fir.expressions.FirErrorLoop;
import org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirImplicitInvokeCall;
import org.jetbrains.kotlin.fir.expressions.FirInaccessibleReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirIntegerLiteralOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirLambdaArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedErrorAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirResolvedReifiedParameterReference;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.fir.expressions.FirSafeCallExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirSpreadArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirStringConcatenationCall;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirThrowExpression;
import org.jetbrains.kotlin.fir.expressions.FirTryExpression;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenSubjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhileLoop;
import org.jetbrains.kotlin.fir.expressions.FirWrappedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirWrappedExpression;

/* JADX INFO: compiled from: ExpressionCheckersDiagnosticComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000â\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010!\u001a\u00020\n2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010$\u001a\u00020\n2\u0006\u0010%\u001a\u00020&2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010'\u001a\u00020\n2\u0006\u0010(\u001a\u00020)2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010*\u001a\u00020\n2\u0006\u0010+\u001a\u00020,2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010-\u001a\u00020\n2\u0006\u0010.\u001a\u00020/2\u0006\u0010\r\u001a\u00020\u000eH\u0016J$\u00100\u001a\u00020\n\"\u0004\b\u0000\u001012\f\u00102\u001a\b\u0012\u0004\u0012\u0002H1032\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u00104\u001a\u00020\n2\u0006\u00105\u001a\u0002062\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u00107\u001a\u00020\n2\u0006\u00108\u001a\u0002092\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010:\u001a\u00020\n2\u0006\u0010;\u001a\u00020<2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010=\u001a\u00020\n2\u0006\u0010>\u001a\u00020?2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010@\u001a\u00020\n2\u0006\u0010A\u001a\u00020B2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010C\u001a\u00020\n2\u0006\u0010D\u001a\u00020E2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010F\u001a\u00020\n2\u0006\u0010G\u001a\u00020H2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010I\u001a\u00020\n2\u0006\u0010J\u001a\u00020K2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010L\u001a\u00020\n2\u0006\u0010M\u001a\u00020N2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010O\u001a\u00020\n2\u0006\u0010P\u001a\u00020Q2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010R\u001a\u00020\n2\u0006\u0010S\u001a\u00020T2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010U\u001a\u00020\n2\u0006\u0010V\u001a\u00020W2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010X\u001a\u00020\n2\u0006\u0010Y\u001a\u00020Z2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010[\u001a\u00020\n2\u0006\u0010\\\u001a\u00020]2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010^\u001a\u00020\n2\u0006\u0010_\u001a\u00020`2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010a\u001a\u00020\n2\u0006\u0010b\u001a\u00020c2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010d\u001a\u00020\n2\u0006\u0010e\u001a\u00020f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010g\u001a\u00020\n2\u0006\u0010h\u001a\u00020i2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010j\u001a\u00020\n2\u0006\u0010k\u001a\u00020l2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010m\u001a\u00020\n2\u0006\u0010n\u001a\u00020o2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010p\u001a\u00020\n2\u0006\u0010q\u001a\u00020r2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010s\u001a\u00020\n2\u0006\u0010t\u001a\u00020u2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010v\u001a\u00020\n2\u0006\u0010w\u001a\u00020x2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010y\u001a\u00020\n2\u0006\u0010z\u001a\u00020{2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010|\u001a\u00020\n2\u0006\u0010}\u001a\u00020~2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001a\u0010\u007f\u001a\u00020\n2\b\u0010\u0080\u0001\u001a\u00030\u0081\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001b\u0010\u0082\u0001\u001a\u00020\n2\b\u0010\u0083\u0001\u001a\u00030\u0084\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001b\u0010\u0085\u0001\u001a\u00020\n2\b\u0010\u0086\u0001\u001a\u00030\u0087\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001b\u0010\u0088\u0001\u001a\u00020\n2\b\u0010\u0089\u0001\u001a\u00030\u008a\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001b\u0010\u008b\u0001\u001a\u00020\n2\b\u0010\u008c\u0001\u001a\u00030\u008d\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001b\u0010\u008e\u0001\u001a\u00020\n2\b\u0010\u008f\u0001\u001a\u00030\u0090\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001b\u0010\u0091\u0001\u001a\u00020\n2\b\u0010\u0092\u0001\u001a\u00030\u0093\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001b\u0010\u0094\u0001\u001a\u00020\n2\b\u0010\u0095\u0001\u001a\u00030\u0096\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001b\u0010\u0097\u0001\u001a\u00020\n2\b\u0010\u0098\u0001\u001a\u00030\u0099\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001b\u0010\u009a\u0001\u001a\u00020\n2\b\u0010\u009b\u0001\u001a\u00030\u009c\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001b\u0010\u009d\u0001\u001a\u00020\n2\b\u0010\u009e\u0001\u001a\u00030\u009f\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001b\u0010 \u0001\u001a\u00020\n2\b\u0010¡\u0001\u001a\u00030¢\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001b\u0010£\u0001\u001a\u00020\n2\b\u0010¤\u0001\u001a\u00030¥\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001b\u0010¦\u0001\u001a\u00020\n2\b\u0010§\u0001\u001a\u00030¨\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001b\u0010©\u0001\u001a\u00020\n2\b\u0010ª\u0001\u001a\u00030«\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0016J@\u0010¬\u0001\u001a\u00020\n\"\n\b\u0000\u0010\u00ad\u0001*\u00030®\u0001*\u0011\u0012\f\u0012\n\u0012\u0005\u0012\u0003H\u00ad\u00010°\u00010¯\u00012\u0007\u0010V\u001a\u0003H\u00ad\u00012\u0007\u0010±\u0001\u001a\u00020\u000eH\u0002¢\u0006\u0003\u0010²\u0001R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/collectors/components/ExpressionCheckersDiagnosticComponent;", "Lorg/jetbrains/kotlin/fir/analysis/collectors/components/AbstractDiagnosticCollectorComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/ExpressionCheckers;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/ExpressionCheckers;)V", "visitAnnotation", "", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "data", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "visitAnnotationCall", "annotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "visitArrayLiteral", "arrayLiteral", "Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;", "visitBinaryLogicExpression", "binaryLogicExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", "visitBlock", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "visitBreakExpression", "breakExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBreakExpression;", "visitCallableReferenceAccess", "callableReferenceAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "visitCheckNotNullCall", "checkNotNullCall", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckNotNullCall;", "visitCheckedSafeCallSubject", "checkedSafeCallSubject", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckedSafeCallSubject;", "visitClassReferenceExpression", "classReferenceExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirClassReferenceExpression;", "visitComparisonExpression", "comparisonExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;", "visitComponentCall", "componentCall", "Lorg/jetbrains/kotlin/fir/expressions/FirComponentCall;", "visitConstExpression", ExifInterface.GPS_DIRECTION_TRUE, "constExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "visitContinueExpression", "continueExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirContinueExpression;", "visitDelegatedConstructorCall", "delegatedConstructorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "visitDesugaredAssignmentValueReferenceExpression", "desugaredAssignmentValueReferenceExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirDesugaredAssignmentValueReferenceExpression;", "visitDoWhileLoop", "doWhileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirDoWhileLoop;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "visitElvisExpression", "elvisExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirElvisExpression;", "visitEqualityOperatorCall", "equalityOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;", "visitErrorAnnotationCall", "errorAnnotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorAnnotationCall;", "visitErrorExpression", "errorExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorExpression;", "visitErrorLoop", "errorLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorLoop;", "visitErrorResolvedQualifier", "errorResolvedQualifier", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorResolvedQualifier;", "visitExpression", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "visitFunctionCall", "functionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "visitGetClassCall", "getClassCall", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "visitImplicitInvokeCall", "implicitInvokeCall", "Lorg/jetbrains/kotlin/fir/expressions/FirImplicitInvokeCall;", "visitInaccessibleReceiverExpression", "inaccessibleReceiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirInaccessibleReceiverExpression;", "visitIntegerLiteralOperatorCall", "integerLiteralOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirIntegerLiteralOperatorCall;", "visitLambdaArgumentExpression", "lambdaArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirLambdaArgumentExpression;", "visitNamedArgumentExpression", "namedArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirNamedArgumentExpression;", "visitPropertyAccessExpression", "propertyAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "visitQualifiedAccessExpression", "qualifiedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "visitQualifiedErrorAccessExpression", "qualifiedErrorAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedErrorAccessExpression;", "visitResolvedQualifier", "resolvedQualifier", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "visitResolvedReifiedParameterReference", "resolvedReifiedParameterReference", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedReifiedParameterReference;", "visitReturnExpression", "returnExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "visitSafeCallExpression", "safeCallExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "visitSmartCastExpression", "smartCastExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "visitSpreadArgumentExpression", "spreadArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSpreadArgumentExpression;", "visitStringConcatenationCall", "stringConcatenationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirStringConcatenationCall;", "visitThisReceiverExpression", "thisReceiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "visitThrowExpression", "throwExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThrowExpression;", "visitTryExpression", "tryExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;", "visitTypeOperatorCall", "typeOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "visitVarargArgumentsExpression", "varargArgumentsExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirVarargArgumentsExpression;", "visitVariableAssignment", "variableAssignment", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "visitWhenExpression", "whenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "visitWhenSubjectExpression", "whenSubjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenSubjectExpression;", "visitWhileLoop", "whileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirWhileLoop;", "visitWrappedArgumentExpression", "wrappedArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWrappedArgumentExpression;", "visitWrappedExpression", "wrappedExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWrappedExpression;", "check", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "context", "(Ljava/util/Collection;Lorg/jetbrains/kotlin/fir/expressions/FirStatement;Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;)V"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExpressionCheckersDiagnosticComponent extends AbstractDiagnosticCollectorComponent {
    private final ExpressionCheckers checkers;

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitAnnotation(FirAnnotation firAnnotation, CheckerContext checkerContext) {
        visitAnnotation2(firAnnotation, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitAnnotationCall(FirAnnotationCall firAnnotationCall, CheckerContext checkerContext) {
        visitAnnotationCall2(firAnnotationCall, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitArrayLiteral(FirArrayLiteral firArrayLiteral, CheckerContext checkerContext) {
        visitArrayLiteral2(firArrayLiteral, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitBinaryLogicExpression(FirBinaryLogicExpression firBinaryLogicExpression, CheckerContext checkerContext) {
        visitBinaryLogicExpression2(firBinaryLogicExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitBlock(FirBlock firBlock, CheckerContext checkerContext) {
        visitBlock2(firBlock, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitBreakExpression(FirBreakExpression firBreakExpression, CheckerContext checkerContext) {
        visitBreakExpression2(firBreakExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitCallableReferenceAccess(FirCallableReferenceAccess firCallableReferenceAccess, CheckerContext checkerContext) {
        visitCallableReferenceAccess2(firCallableReferenceAccess, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitCheckNotNullCall(FirCheckNotNullCall firCheckNotNullCall, CheckerContext checkerContext) {
        visitCheckNotNullCall2(firCheckNotNullCall, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitCheckedSafeCallSubject(FirCheckedSafeCallSubject firCheckedSafeCallSubject, CheckerContext checkerContext) {
        visitCheckedSafeCallSubject2(firCheckedSafeCallSubject, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitClassReferenceExpression(FirClassReferenceExpression firClassReferenceExpression, CheckerContext checkerContext) {
        visitClassReferenceExpression2(firClassReferenceExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitComparisonExpression(FirComparisonExpression firComparisonExpression, CheckerContext checkerContext) {
        visitComparisonExpression2(firComparisonExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitComponentCall(FirComponentCall firComponentCall, CheckerContext checkerContext) {
        visitComponentCall2(firComponentCall, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitConstExpression(FirConstExpression firConstExpression, CheckerContext checkerContext) {
        visitConstExpression2(firConstExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitContinueExpression(FirContinueExpression firContinueExpression, CheckerContext checkerContext) {
        visitContinueExpression2(firContinueExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitDelegatedConstructorCall(FirDelegatedConstructorCall firDelegatedConstructorCall, CheckerContext checkerContext) {
        visitDelegatedConstructorCall2(firDelegatedConstructorCall, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitDesugaredAssignmentValueReferenceExpression(FirDesugaredAssignmentValueReferenceExpression firDesugaredAssignmentValueReferenceExpression, CheckerContext checkerContext) {
        visitDesugaredAssignmentValueReferenceExpression2(firDesugaredAssignmentValueReferenceExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitDoWhileLoop(FirDoWhileLoop firDoWhileLoop, CheckerContext checkerContext) {
        visitDoWhileLoop2(firDoWhileLoop, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.collectors.components.AbstractDiagnosticCollectorComponent, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitElement(FirElement firElement, CheckerContext checkerContext) {
        visitElement2(firElement, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitElvisExpression(FirElvisExpression firElvisExpression, CheckerContext checkerContext) {
        visitElvisExpression2(firElvisExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitEqualityOperatorCall(FirEqualityOperatorCall firEqualityOperatorCall, CheckerContext checkerContext) {
        visitEqualityOperatorCall2(firEqualityOperatorCall, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitErrorAnnotationCall(FirErrorAnnotationCall firErrorAnnotationCall, CheckerContext checkerContext) {
        visitErrorAnnotationCall2(firErrorAnnotationCall, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitErrorExpression(FirErrorExpression firErrorExpression, CheckerContext checkerContext) {
        visitErrorExpression2(firErrorExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitErrorLoop(FirErrorLoop firErrorLoop, CheckerContext checkerContext) {
        visitErrorLoop2(firErrorLoop, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitErrorResolvedQualifier(FirErrorResolvedQualifier firErrorResolvedQualifier, CheckerContext checkerContext) {
        visitErrorResolvedQualifier2(firErrorResolvedQualifier, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitExpression(FirExpression firExpression, CheckerContext checkerContext) {
        visitExpression2(firExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitFunctionCall(FirFunctionCall firFunctionCall, CheckerContext checkerContext) {
        visitFunctionCall2(firFunctionCall, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitGetClassCall(FirGetClassCall firGetClassCall, CheckerContext checkerContext) {
        visitGetClassCall2(firGetClassCall, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitImplicitInvokeCall(FirImplicitInvokeCall firImplicitInvokeCall, CheckerContext checkerContext) {
        visitImplicitInvokeCall2(firImplicitInvokeCall, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitInaccessibleReceiverExpression(FirInaccessibleReceiverExpression firInaccessibleReceiverExpression, CheckerContext checkerContext) {
        visitInaccessibleReceiverExpression2(firInaccessibleReceiverExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitIntegerLiteralOperatorCall(FirIntegerLiteralOperatorCall firIntegerLiteralOperatorCall, CheckerContext checkerContext) {
        visitIntegerLiteralOperatorCall2(firIntegerLiteralOperatorCall, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitLambdaArgumentExpression(FirLambdaArgumentExpression firLambdaArgumentExpression, CheckerContext checkerContext) {
        visitLambdaArgumentExpression2(firLambdaArgumentExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitNamedArgumentExpression(FirNamedArgumentExpression firNamedArgumentExpression, CheckerContext checkerContext) {
        visitNamedArgumentExpression2(firNamedArgumentExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitPropertyAccessExpression(FirPropertyAccessExpression firPropertyAccessExpression, CheckerContext checkerContext) {
        visitPropertyAccessExpression2(firPropertyAccessExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitQualifiedAccessExpression(FirQualifiedAccessExpression firQualifiedAccessExpression, CheckerContext checkerContext) {
        visitQualifiedAccessExpression2(firQualifiedAccessExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitQualifiedErrorAccessExpression(FirQualifiedErrorAccessExpression firQualifiedErrorAccessExpression, CheckerContext checkerContext) {
        visitQualifiedErrorAccessExpression2(firQualifiedErrorAccessExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitResolvedQualifier(FirResolvedQualifier firResolvedQualifier, CheckerContext checkerContext) {
        visitResolvedQualifier2(firResolvedQualifier, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitResolvedReifiedParameterReference(FirResolvedReifiedParameterReference firResolvedReifiedParameterReference, CheckerContext checkerContext) {
        visitResolvedReifiedParameterReference2(firResolvedReifiedParameterReference, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitReturnExpression(FirReturnExpression firReturnExpression, CheckerContext checkerContext) {
        visitReturnExpression2(firReturnExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitSafeCallExpression(FirSafeCallExpression firSafeCallExpression, CheckerContext checkerContext) {
        visitSafeCallExpression2(firSafeCallExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitSmartCastExpression(FirSmartCastExpression firSmartCastExpression, CheckerContext checkerContext) {
        visitSmartCastExpression2(firSmartCastExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitSpreadArgumentExpression(FirSpreadArgumentExpression firSpreadArgumentExpression, CheckerContext checkerContext) {
        visitSpreadArgumentExpression2(firSpreadArgumentExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitStringConcatenationCall(FirStringConcatenationCall firStringConcatenationCall, CheckerContext checkerContext) {
        visitStringConcatenationCall2(firStringConcatenationCall, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitThisReceiverExpression(FirThisReceiverExpression firThisReceiverExpression, CheckerContext checkerContext) {
        visitThisReceiverExpression2(firThisReceiverExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitThrowExpression(FirThrowExpression firThrowExpression, CheckerContext checkerContext) {
        visitThrowExpression2(firThrowExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitTryExpression(FirTryExpression firTryExpression, CheckerContext checkerContext) {
        visitTryExpression2(firTryExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitTypeOperatorCall(FirTypeOperatorCall firTypeOperatorCall, CheckerContext checkerContext) {
        visitTypeOperatorCall2(firTypeOperatorCall, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitVarargArgumentsExpression(FirVarargArgumentsExpression firVarargArgumentsExpression, CheckerContext checkerContext) {
        visitVarargArgumentsExpression2(firVarargArgumentsExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitVariableAssignment(FirVariableAssignment firVariableAssignment, CheckerContext checkerContext) {
        visitVariableAssignment2(firVariableAssignment, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitWhenExpression(FirWhenExpression firWhenExpression, CheckerContext checkerContext) {
        visitWhenExpression2(firWhenExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitWhenSubjectExpression(FirWhenSubjectExpression firWhenSubjectExpression, CheckerContext checkerContext) {
        visitWhenSubjectExpression2(firWhenSubjectExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitWhileLoop(FirWhileLoop firWhileLoop, CheckerContext checkerContext) {
        visitWhileLoop2(firWhileLoop, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitWrappedArgumentExpression(FirWrappedArgumentExpression firWrappedArgumentExpression, CheckerContext checkerContext) {
        visitWrappedArgumentExpression2(firWrappedArgumentExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitWrappedExpression(FirWrappedExpression firWrappedExpression, CheckerContext checkerContext) {
        visitWrappedExpression2(firWrappedExpression, checkerContext);
        return Unit.INSTANCE;
    }

    public /* synthetic */ ExpressionCheckersDiagnosticComponent(FirSession firSession, DiagnosticReporter diagnosticReporter, ExpressionCheckers expressionCheckers, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, diagnosticReporter, (i & 4) != 0 ? CheckersComponentKt.getCheckersComponent(firSession).getExpressionCheckers() : expressionCheckers);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExpressionCheckersDiagnosticComponent(FirSession session, DiagnosticReporter reporter, ExpressionCheckers checkers) {
        super(session, reporter);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(checkers, "checkers");
        this.checkers = checkers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.collectors.components.AbstractDiagnosticCollectorComponent
    /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
    public void visitElement2(FirElement element, CheckerContext data) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
        if (element instanceof FirExpression) {
            throw new IllegalStateException((Reflection.getOrCreateKotlinClass(element.getClass()).getSimpleName() + " should call parent checkers inside " + Reflection.getOrCreateKotlinClass(getClass()).getSimpleName()).toString());
        }
    }

    /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
    public void visitExpression2(FirExpression expression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBasicExpressionCheckers$checkers(), expression, data);
    }

    /* JADX INFO: renamed from: visitTypeOperatorCall, reason: avoid collision after fix types in other method */
    public void visitTypeOperatorCall2(FirTypeOperatorCall typeOperatorCall, CheckerContext data) {
        Intrinsics.checkNotNullParameter(typeOperatorCall, "typeOperatorCall");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllTypeOperatorCallCheckers$checkers(), typeOperatorCall, data);
    }

    /* JADX INFO: renamed from: visitConstExpression, reason: avoid collision after fix types in other method */
    public <T> void visitConstExpression2(FirConstExpression<T> constExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(constExpression, "constExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllConstExpressionCheckers$checkers(), constExpression, data);
    }

    /* JADX INFO: renamed from: visitAnnotation, reason: avoid collision after fix types in other method */
    public void visitAnnotation2(FirAnnotation annotation, CheckerContext data) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllAnnotationCheckers$checkers(), annotation, data);
    }

    /* JADX INFO: renamed from: visitAnnotationCall, reason: avoid collision after fix types in other method */
    public void visitAnnotationCall2(FirAnnotationCall annotationCall, CheckerContext data) {
        Intrinsics.checkNotNullParameter(annotationCall, "annotationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllAnnotationCallCheckers$checkers(), annotationCall, data);
    }

    /* JADX INFO: renamed from: visitErrorAnnotationCall, reason: avoid collision after fix types in other method */
    public void visitErrorAnnotationCall2(FirErrorAnnotationCall errorAnnotationCall, CheckerContext data) {
        Intrinsics.checkNotNullParameter(errorAnnotationCall, "errorAnnotationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllAnnotationCallCheckers$checkers(), errorAnnotationCall, data);
    }

    /* JADX INFO: renamed from: visitQualifiedAccessExpression, reason: avoid collision after fix types in other method */
    public void visitQualifiedAccessExpression2(FirQualifiedAccessExpression qualifiedAccessExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllQualifiedAccessExpressionCheckers$checkers(), qualifiedAccessExpression, data);
    }

    /* JADX INFO: renamed from: visitPropertyAccessExpression, reason: avoid collision after fix types in other method */
    public void visitPropertyAccessExpression2(FirPropertyAccessExpression propertyAccessExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(propertyAccessExpression, "propertyAccessExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllPropertyAccessExpressionCheckers$checkers(), propertyAccessExpression, data);
    }

    /* JADX INFO: renamed from: visitFunctionCall, reason: avoid collision after fix types in other method */
    public void visitFunctionCall2(FirFunctionCall functionCall, CheckerContext data) {
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllFunctionCallCheckers$checkers(), functionCall, data);
    }

    /* JADX INFO: renamed from: visitComponentCall, reason: avoid collision after fix types in other method */
    public void visitComponentCall2(FirComponentCall componentCall, CheckerContext data) {
        Intrinsics.checkNotNullParameter(componentCall, "componentCall");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllFunctionCallCheckers$checkers(), componentCall, data);
    }

    /* JADX INFO: renamed from: visitIntegerLiteralOperatorCall, reason: avoid collision after fix types in other method */
    public void visitIntegerLiteralOperatorCall2(FirIntegerLiteralOperatorCall integerLiteralOperatorCall, CheckerContext data) {
        Intrinsics.checkNotNullParameter(integerLiteralOperatorCall, "integerLiteralOperatorCall");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllIntegerLiteralOperatorCallCheckers$checkers(), integerLiteralOperatorCall, data);
    }

    /* JADX INFO: renamed from: visitImplicitInvokeCall, reason: avoid collision after fix types in other method */
    public void visitImplicitInvokeCall2(FirImplicitInvokeCall implicitInvokeCall, CheckerContext data) {
        Intrinsics.checkNotNullParameter(implicitInvokeCall, "implicitInvokeCall");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllFunctionCallCheckers$checkers(), implicitInvokeCall, data);
    }

    /* JADX INFO: renamed from: visitCallableReferenceAccess, reason: avoid collision after fix types in other method */
    public void visitCallableReferenceAccess2(FirCallableReferenceAccess callableReferenceAccess, CheckerContext data) {
        Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllCallableReferenceAccessCheckers$checkers(), callableReferenceAccess, data);
    }

    /* JADX INFO: renamed from: visitThisReceiverExpression, reason: avoid collision after fix types in other method */
    public void visitThisReceiverExpression2(FirThisReceiverExpression thisReceiverExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(thisReceiverExpression, "thisReceiverExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllThisReceiverExpressionCheckers$checkers(), thisReceiverExpression, data);
    }

    /* JADX INFO: renamed from: visitInaccessibleReceiverExpression, reason: avoid collision after fix types in other method */
    public void visitInaccessibleReceiverExpression2(FirInaccessibleReceiverExpression inaccessibleReceiverExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(inaccessibleReceiverExpression, "inaccessibleReceiverExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllInaccessibleReceiverCheckers$checkers(), inaccessibleReceiverExpression, data);
    }

    /* JADX INFO: renamed from: visitResolvedQualifier, reason: avoid collision after fix types in other method */
    public void visitResolvedQualifier2(FirResolvedQualifier resolvedQualifier, CheckerContext data) {
        Intrinsics.checkNotNullParameter(resolvedQualifier, "resolvedQualifier");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllResolvedQualifierCheckers$checkers(), resolvedQualifier, data);
    }

    /* JADX INFO: renamed from: visitErrorResolvedQualifier, reason: avoid collision after fix types in other method */
    public void visitErrorResolvedQualifier2(FirErrorResolvedQualifier errorResolvedQualifier, CheckerContext data) {
        Intrinsics.checkNotNullParameter(errorResolvedQualifier, "errorResolvedQualifier");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllResolvedQualifierCheckers$checkers(), errorResolvedQualifier, data);
    }

    /* JADX INFO: renamed from: visitWhenExpression, reason: avoid collision after fix types in other method */
    public void visitWhenExpression2(FirWhenExpression whenExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllWhenExpressionCheckers$checkers(), whenExpression, data);
    }

    /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
    public void visitWhileLoop2(FirWhileLoop whileLoop, CheckerContext data) {
        Intrinsics.checkNotNullParameter(whileLoop, "whileLoop");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllWhileLoopCheckers$checkers(), whileLoop, data);
    }

    /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
    public void visitDoWhileLoop2(FirDoWhileLoop doWhileLoop, CheckerContext data) {
        Intrinsics.checkNotNullParameter(doWhileLoop, "doWhileLoop");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllDoWhileLoopCheckers$checkers(), doWhileLoop, data);
    }

    /* JADX INFO: renamed from: visitErrorLoop, reason: avoid collision after fix types in other method */
    public void visitErrorLoop2(FirErrorLoop errorLoop, CheckerContext data) {
        Intrinsics.checkNotNullParameter(errorLoop, "errorLoop");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllLoopExpressionCheckers$checkers(), errorLoop, data);
    }

    /* JADX INFO: renamed from: visitBinaryLogicExpression, reason: avoid collision after fix types in other method */
    public void visitBinaryLogicExpression2(FirBinaryLogicExpression binaryLogicExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllLogicExpressionCheckers$checkers(), binaryLogicExpression, data);
    }

    /* JADX INFO: renamed from: visitArrayLiteral, reason: avoid collision after fix types in other method */
    public void visitArrayLiteral2(FirArrayLiteral arrayLiteral, CheckerContext data) {
        Intrinsics.checkNotNullParameter(arrayLiteral, "arrayLiteral");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllArrayLiteralCheckers$checkers(), arrayLiteral, data);
    }

    /* JADX INFO: renamed from: visitStringConcatenationCall, reason: avoid collision after fix types in other method */
    public void visitStringConcatenationCall2(FirStringConcatenationCall stringConcatenationCall, CheckerContext data) {
        Intrinsics.checkNotNullParameter(stringConcatenationCall, "stringConcatenationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllStringConcatenationCallCheckers$checkers(), stringConcatenationCall, data);
    }

    /* JADX INFO: renamed from: visitCheckNotNullCall, reason: avoid collision after fix types in other method */
    public void visitCheckNotNullCall2(FirCheckNotNullCall checkNotNullCall, CheckerContext data) {
        Intrinsics.checkNotNullParameter(checkNotNullCall, "checkNotNullCall");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllCheckNotNullCallCheckers$checkers(), checkNotNullCall, data);
    }

    /* JADX INFO: renamed from: visitElvisExpression, reason: avoid collision after fix types in other method */
    public void visitElvisExpression2(FirElvisExpression elvisExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(elvisExpression, "elvisExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllElvisExpressionCheckers$checkers(), elvisExpression, data);
    }

    /* JADX INFO: renamed from: visitSafeCallExpression, reason: avoid collision after fix types in other method */
    public void visitSafeCallExpression2(FirSafeCallExpression safeCallExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(safeCallExpression, "safeCallExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllSafeCallExpressionCheckers$checkers(), safeCallExpression, data);
    }

    /* JADX INFO: renamed from: visitTryExpression, reason: avoid collision after fix types in other method */
    public void visitTryExpression2(FirTryExpression tryExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(tryExpression, "tryExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllTryExpressionCheckers$checkers(), tryExpression, data);
    }

    /* JADX INFO: renamed from: visitClassReferenceExpression, reason: avoid collision after fix types in other method */
    public void visitClassReferenceExpression2(FirClassReferenceExpression classReferenceExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(classReferenceExpression, "classReferenceExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllClassReferenceExpressionCheckers$checkers(), classReferenceExpression, data);
    }

    /* JADX INFO: renamed from: visitGetClassCall, reason: avoid collision after fix types in other method */
    public void visitGetClassCall2(FirGetClassCall getClassCall, CheckerContext data) {
        Intrinsics.checkNotNullParameter(getClassCall, "getClassCall");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllGetClassCallCheckers$checkers(), getClassCall, data);
    }

    /* JADX INFO: renamed from: visitEqualityOperatorCall, reason: avoid collision after fix types in other method */
    public void visitEqualityOperatorCall2(FirEqualityOperatorCall equalityOperatorCall, CheckerContext data) {
        Intrinsics.checkNotNullParameter(equalityOperatorCall, "equalityOperatorCall");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllEqualityOperatorCallCheckers$checkers(), equalityOperatorCall, data);
    }

    /* JADX INFO: renamed from: visitVariableAssignment, reason: avoid collision after fix types in other method */
    public void visitVariableAssignment2(FirVariableAssignment variableAssignment, CheckerContext data) {
        Intrinsics.checkNotNullParameter(variableAssignment, "variableAssignment");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllVariableAssignmentCheckers$checkers(), variableAssignment, data);
    }

    /* JADX INFO: renamed from: visitReturnExpression, reason: avoid collision after fix types in other method */
    public void visitReturnExpression2(FirReturnExpression returnExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllReturnExpressionCheckers$checkers(), returnExpression, data);
    }

    /* JADX INFO: renamed from: visitBreakExpression, reason: avoid collision after fix types in other method */
    public void visitBreakExpression2(FirBreakExpression breakExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(breakExpression, "breakExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllLoopJumpCheckers$checkers(), breakExpression, data);
    }

    /* JADX INFO: renamed from: visitContinueExpression, reason: avoid collision after fix types in other method */
    public void visitContinueExpression2(FirContinueExpression continueExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(continueExpression, "continueExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllLoopJumpCheckers$checkers(), continueExpression, data);
    }

    /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
    public void visitBlock2(FirBlock block, CheckerContext data) {
        Intrinsics.checkNotNullParameter(block, "block");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBlockCheckers$checkers(), block, data);
    }

    /* JADX INFO: renamed from: visitDelegatedConstructorCall, reason: avoid collision after fix types in other method */
    public void visitDelegatedConstructorCall2(FirDelegatedConstructorCall delegatedConstructorCall, CheckerContext data) {
        Intrinsics.checkNotNullParameter(delegatedConstructorCall, "delegatedConstructorCall");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllCallCheckers$checkers(), delegatedConstructorCall, data);
    }

    /* JADX INFO: renamed from: visitThrowExpression, reason: avoid collision after fix types in other method */
    public void visitThrowExpression2(FirThrowExpression throwExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(throwExpression, "throwExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllThrowExpressionCheckers$checkers(), throwExpression, data);
    }

    /* JADX INFO: renamed from: visitVarargArgumentsExpression, reason: avoid collision after fix types in other method */
    public void visitVarargArgumentsExpression2(FirVarargArgumentsExpression varargArgumentsExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(varargArgumentsExpression, "varargArgumentsExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBasicExpressionCheckers$checkers(), varargArgumentsExpression, data);
    }

    /* JADX INFO: renamed from: visitWrappedExpression, reason: avoid collision after fix types in other method */
    public void visitWrappedExpression2(FirWrappedExpression wrappedExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(wrappedExpression, "wrappedExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBasicExpressionCheckers$checkers(), wrappedExpression, data);
    }

    /* JADX INFO: renamed from: visitWrappedArgumentExpression, reason: avoid collision after fix types in other method */
    public void visitWrappedArgumentExpression2(FirWrappedArgumentExpression wrappedArgumentExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(wrappedArgumentExpression, "wrappedArgumentExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBasicExpressionCheckers$checkers(), wrappedArgumentExpression, data);
    }

    /* JADX INFO: renamed from: visitSpreadArgumentExpression, reason: avoid collision after fix types in other method */
    public void visitSpreadArgumentExpression2(FirSpreadArgumentExpression spreadArgumentExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(spreadArgumentExpression, "spreadArgumentExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBasicExpressionCheckers$checkers(), spreadArgumentExpression, data);
    }

    /* JADX INFO: renamed from: visitNamedArgumentExpression, reason: avoid collision after fix types in other method */
    public void visitNamedArgumentExpression2(FirNamedArgumentExpression namedArgumentExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(namedArgumentExpression, "namedArgumentExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBasicExpressionCheckers$checkers(), namedArgumentExpression, data);
    }

    /* JADX INFO: renamed from: visitLambdaArgumentExpression, reason: avoid collision after fix types in other method */
    public void visitLambdaArgumentExpression2(FirLambdaArgumentExpression lambdaArgumentExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(lambdaArgumentExpression, "lambdaArgumentExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBasicExpressionCheckers$checkers(), lambdaArgumentExpression, data);
    }

    /* JADX INFO: renamed from: visitSmartCastExpression, reason: avoid collision after fix types in other method */
    public void visitSmartCastExpression2(FirSmartCastExpression smartCastExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(smartCastExpression, "smartCastExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBasicExpressionCheckers$checkers(), smartCastExpression, data);
    }

    /* JADX INFO: renamed from: visitWhenSubjectExpression, reason: avoid collision after fix types in other method */
    public void visitWhenSubjectExpression2(FirWhenSubjectExpression whenSubjectExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(whenSubjectExpression, "whenSubjectExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBasicExpressionCheckers$checkers(), whenSubjectExpression, data);
    }

    /* JADX INFO: renamed from: visitResolvedReifiedParameterReference, reason: avoid collision after fix types in other method */
    public void visitResolvedReifiedParameterReference2(FirResolvedReifiedParameterReference resolvedReifiedParameterReference, CheckerContext data) {
        Intrinsics.checkNotNullParameter(resolvedReifiedParameterReference, "resolvedReifiedParameterReference");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBasicExpressionCheckers$checkers(), resolvedReifiedParameterReference, data);
    }

    /* JADX INFO: renamed from: visitComparisonExpression, reason: avoid collision after fix types in other method */
    public void visitComparisonExpression2(FirComparisonExpression comparisonExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(comparisonExpression, "comparisonExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBasicExpressionCheckers$checkers(), comparisonExpression, data);
    }

    /* JADX INFO: renamed from: visitDesugaredAssignmentValueReferenceExpression, reason: avoid collision after fix types in other method */
    public void visitDesugaredAssignmentValueReferenceExpression2(FirDesugaredAssignmentValueReferenceExpression desugaredAssignmentValueReferenceExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(desugaredAssignmentValueReferenceExpression, "desugaredAssignmentValueReferenceExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBasicExpressionCheckers$checkers(), desugaredAssignmentValueReferenceExpression, data);
    }

    /* JADX INFO: renamed from: visitCheckedSafeCallSubject, reason: avoid collision after fix types in other method */
    public void visitCheckedSafeCallSubject2(FirCheckedSafeCallSubject checkedSafeCallSubject, CheckerContext data) {
        Intrinsics.checkNotNullParameter(checkedSafeCallSubject, "checkedSafeCallSubject");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBasicExpressionCheckers$checkers(), checkedSafeCallSubject, data);
    }

    /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
    public void visitErrorExpression2(FirErrorExpression errorExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(errorExpression, "errorExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBasicExpressionCheckers$checkers(), errorExpression, data);
    }

    /* JADX INFO: renamed from: visitQualifiedErrorAccessExpression, reason: avoid collision after fix types in other method */
    public void visitQualifiedErrorAccessExpression2(FirQualifiedErrorAccessExpression qualifiedErrorAccessExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(qualifiedErrorAccessExpression, "qualifiedErrorAccessExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBasicExpressionCheckers$checkers(), qualifiedErrorAccessExpression, data);
    }

    private final <E extends FirStatement> void check(Collection<? extends FirExpressionChecker<? super E>> collection, E e, CheckerContext checkerContext) {
        Iterator<? extends FirExpressionChecker<? super E>> it = collection.iterator();
        while (it.hasNext()) {
            it.next().check(e, checkerContext, getReporter());
        }
    }
}
