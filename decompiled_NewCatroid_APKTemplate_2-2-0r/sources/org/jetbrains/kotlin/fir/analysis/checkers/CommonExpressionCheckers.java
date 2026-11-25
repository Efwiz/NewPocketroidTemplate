package org.jetbrains.kotlin.fir.analysis.checkers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirInlineBodyQualifiedAccessExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirInlineBodyResolvedQualifierChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirInlineBodyVariableAssignmentChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirAbstractClassInstantiationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirAbstractSuperCallChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirAnnotationExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirAssignmentOperatorCallChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirAssignmentTypeMismatchChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirBreakOrContinueJumpsAcrossFunctionBoundaryChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirCallableReferenceChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirCastOperatorsChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirCatchParameterChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirClassLiteralChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirConflictsExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirConstructorCallChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirContractNotFirstStatementChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirConventionFunctionCallChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirDeprecatedQualifierChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirDeprecationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirDivisionByZeroChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirEqualityCompatibilityChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExhaustiveWhenChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionAnnotationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirForLoopChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirFunInterfaceConstructorReferenceChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirFunctionReturnTypeMismatchChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirLateinitIntrinsicApplicabilityChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirLogicExpressionTypeChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirLoopConditionChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirNamedVarargChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirNotNullAssertionChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirOptInAnnotationCallChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirOptInUsageAccessChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirOptInUsageQualifierChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirProjectionsOnNonClassTypeArgumentChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirPropertyAccessTypeArgumentsChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirProtectedConstructorNotInSuperCallChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirQualifiedSupertypeExtendedByOtherSupertypeChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirReassignmentAndInvisibleSetterChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirReceiverAccessBeforeSuperCallChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirRecursiveProblemChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirReifiedChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirReturnSyntaxAndLabelChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirSealedClassConstructorCallChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirSpreadOfNullableChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirStandaloneQualifierChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirSuperReferenceChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirSuperclassNotAccessibleFromInterfaceChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirSuspendCallChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirThrowExpressionTypeChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirTypeArgumentsNotAllowedExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirTypeArgumentsOfQualifierOfCallableReferenceChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirTypeParameterInQualifiedAccessChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirUnderscoreChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirUninitializedEnumChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirUnnecessarySafeCallChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirUnsupportedArrayLiteralChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirUpperBoundViolatedExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirUselessElvisChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirUselessTypeOperationCallChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirVisibilityQualifierChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirWhenConditionChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirWhenSubjectChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirCommaInWhenConditionChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirConfusingWhenBranchSyntaxChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirUnderscoredTypeArgumentSyntaxChecker;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirElvisExpression;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirInaccessibleReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirLoop;
import org.jetbrains.kotlin.fir.expressions.FirLoopJump;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.fir.expressions.FirSafeCallExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirThrowExpression;
import org.jetbrains.kotlin.fir.expressions.FirTryExpression;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;

/* JADX INFO: compiled from: CommonExpressionCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000À\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R$\u0010\u0003\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR$\u0010\n\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000b0\u0005j\u0002`\f0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\tR$\u0010\u000e\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000f0\u0005j\u0002`\u00100\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\tR$\u0010\u0012\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00130\u0005j\u0002`\u00140\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\tR$\u0010\u0016\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00170\u0005j\u0002`\u00180\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\tR$\u0010\u001a\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u001b0\u0005j\u0002`\u001c0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\tR$\u0010\u001e\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u001f0\u0005j\u0002` 0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\tR$\u0010\"\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020#0\u0005j\u0002`$0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b%\u0010\tR$\u0010&\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020'0\u0005j\u0002`(0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010\tR$\u0010*\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020+0\u0005j\u0002`,0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b-\u0010\tR$\u0010.\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020/0\u0005j\u0002`00\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b1\u0010\tR$\u00102\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002030\u0005j\u0002`40\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b5\u0010\tR$\u00106\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002070\u0005j\u0002`80\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b9\u0010\tR$\u0010:\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020;0\u0005j\u0002`<0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b=\u0010\tR$\u0010>\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020?0\u0005j\u0002`@0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bA\u0010\tR$\u0010B\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020C0\u0005j\u0002`D0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bE\u0010\tR$\u0010F\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020G0\u0005j\u0002`H0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bI\u0010\tR$\u0010J\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020K0\u0005j\u0002`L0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bM\u0010\tR$\u0010N\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020O0\u0005j\u0002`P0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bQ\u0010\tR$\u0010R\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020S0\u0005j\u0002`T0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bU\u0010\tR$\u0010V\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020W0\u0005j\u0002`X0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bY\u0010\tR$\u0010Z\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020[0\u0005j\u0002`\\0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b]\u0010\tR$\u0010^\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020_0\u0005j\u0002``0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\ba\u0010\tR$\u0010b\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020c0\u0005j\u0002`d0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\be\u0010\tR$\u0010f\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020g0\u0005j\u0002`h0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bi\u0010\t¨\u0006j"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/CommonExpressionCheckers;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/ExpressionCheckers;", "()V", "annotationCallCheckers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirAnnotationCallChecker;", "getAnnotationCallCheckers", "()Ljava/util/Set;", "arrayLiteralCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirArrayLiteralChecker;", "getArrayLiteralCheckers", "basicExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirBasicExpressionChecker;", "getBasicExpressionCheckers", "blockCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirBlockChecker;", "getBlockCheckers", "callCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirCallChecker;", "getCallCheckers", "callableReferenceAccessCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirCallableReferenceAccessChecker;", "getCallableReferenceAccessCheckers", "checkNotNullCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckNotNullCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirCheckNotNullCallChecker;", "getCheckNotNullCallCheckers", "elvisExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirElvisExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirElvisExpressionChecker;", "getElvisExpressionCheckers", "equalityOperatorCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirEqualityOperatorCallChecker;", "getEqualityOperatorCallCheckers", "functionCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirFunctionCallChecker;", "getFunctionCallCheckers", "getClassCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirGetClassCallChecker;", "getGetClassCallCheckers", "inaccessibleReceiverCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirInaccessibleReceiverExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirInaccessibleReceiverChecker;", "getInaccessibleReceiverCheckers", "logicExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirLogicExpressionChecker;", "getLogicExpressionCheckers", "loopExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirLoop;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirLoopExpressionChecker;", "getLoopExpressionCheckers", "loopJumpCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirLoopJump;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirLoopJumpChecker;", "getLoopJumpCheckers", "propertyAccessExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirPropertyAccessExpressionChecker;", "getPropertyAccessExpressionCheckers", "qualifiedAccessExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "getQualifiedAccessExpressionCheckers", "resolvedQualifierCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirResolvedQualifierChecker;", "getResolvedQualifierCheckers", "returnExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirReturnExpressionChecker;", "getReturnExpressionCheckers", "safeCallExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirSafeCallExpressionChecker;", "getSafeCallExpressionCheckers", "throwExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirThrowExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirThrowExpressionChecker;", "getThrowExpressionCheckers", "tryExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirTryExpressionChecker;", "getTryExpressionCheckers", "typeOperatorCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirTypeOperatorCallChecker;", "getTypeOperatorCallCheckers", "variableAssignmentCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirVariableAssignmentChecker;", "getVariableAssignmentCheckers", "whenExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirWhenExpressionChecker;", "getWhenExpressionCheckers", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CommonExpressionCheckers extends ExpressionCheckers {
    public static final CommonExpressionCheckers INSTANCE = new CommonExpressionCheckers();

    private CommonExpressionCheckers() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirAnnotationCall>> getAnnotationCallCheckers() {
        return SetsKt.setOf((Object[]) new FirExpressionChecker[]{FirAnnotationExpressionChecker.INSTANCE, FirOptInAnnotationCallChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirStatement>> getBasicExpressionCheckers() {
        return SetsKt.setOf((Object[]) new FirExpressionChecker[]{FirUnderscoreChecker.INSTANCE, FirExpressionAnnotationChecker.INSTANCE, FirDeprecationChecker.INSTANCE, FirRecursiveProblemChecker.INSTANCE, FirOptInUsageAccessChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirThrowExpression>> getThrowExpressionCheckers() {
        return SetsKt.setOf(FirThrowExpressionTypeChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirQualifiedAccessExpression>> getQualifiedAccessExpressionCheckers() {
        return SetsKt.setOf((Object[]) new FirExpressionChecker[]{FirCallableReferenceChecker.INSTANCE, FirSuperReferenceChecker.INSTANCE, FirSuperclassNotAccessibleFromInterfaceChecker.INSTANCE, FirAbstractSuperCallChecker.INSTANCE, FirQualifiedSupertypeExtendedByOtherSupertypeChecker.INSTANCE, FirProjectionsOnNonClassTypeArgumentChecker.INSTANCE, FirUpperBoundViolatedExpressionChecker.INSTANCE, FirTypeArgumentsNotAllowedExpressionChecker.INSTANCE, FirTypeParameterInQualifiedAccessChecker.INSTANCE, FirSealedClassConstructorCallChecker.INSTANCE, FirUninitializedEnumChecker.INSTANCE, FirFunInterfaceConstructorReferenceChecker.INSTANCE, FirReifiedChecker.INSTANCE, FirSuspendCallChecker.INSTANCE, FirLateinitIntrinsicApplicabilityChecker.INSTANCE, FirAbstractClassInstantiationChecker.INSTANCE, FirInlineBodyQualifiedAccessExpressionChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirCall>> getCallCheckers() {
        return SetsKt.setOf(FirNamedVarargChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirFunctionCall>> getFunctionCallCheckers() {
        return SetsKt.setOf((Object[]) new FirExpressionChecker[]{FirConventionFunctionCallChecker.INSTANCE, FirDivisionByZeroChecker.INSTANCE, FirConstructorCallChecker.INSTANCE, FirSpreadOfNullableChecker.INSTANCE, FirAssignmentOperatorCallChecker.INSTANCE, FirNamedVarargChecker.INSTANCE, FirUnderscoredTypeArgumentSyntaxChecker.INSTANCE, FirContractNotFirstStatementChecker.INSTANCE, FirProtectedConstructorNotInSuperCallChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirPropertyAccessExpression>> getPropertyAccessExpressionCheckers() {
        return SetsKt.setOf(FirPropertyAccessTypeArgumentsChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirTryExpression>> getTryExpressionCheckers() {
        return SetsKt.setOf(FirCatchParameterChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirVariableAssignment>> getVariableAssignmentCheckers() {
        return SetsKt.setOf((Object[]) new FirExpressionChecker[]{FirReassignmentAndInvisibleSetterChecker.INSTANCE, FirAssignmentTypeMismatchChecker.INSTANCE, FirInlineBodyVariableAssignmentChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirWhenExpression>> getWhenExpressionCheckers() {
        return SetsKt.setOf((Object[]) new FirExpressionChecker[]{FirExhaustiveWhenChecker.INSTANCE, FirWhenConditionChecker.INSTANCE, FirWhenSubjectChecker.INSTANCE, FirCommaInWhenConditionChecker.INSTANCE, FirConfusingWhenBranchSyntaxChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirLoop>> getLoopExpressionCheckers() {
        return SetsKt.setOf(FirLoopConditionChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirLoopJump>> getLoopJumpCheckers() {
        return SetsKt.setOf(FirBreakOrContinueJumpsAcrossFunctionBoundaryChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirBinaryLogicExpression>> getLogicExpressionCheckers() {
        return SetsKt.setOf(FirLogicExpressionTypeChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirReturnExpression>> getReturnExpressionCheckers() {
        return SetsKt.setOf((Object[]) new FirExpressionChecker[]{FirReturnSyntaxAndLabelChecker.INSTANCE, FirFunctionReturnTypeMismatchChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirBlock>> getBlockCheckers() {
        return SetsKt.setOf((Object[]) new FirExpressionChecker[]{FirForLoopChecker.INSTANCE, FirConflictsExpressionChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirCheckNotNullCall>> getCheckNotNullCallCheckers() {
        return SetsKt.setOf(FirNotNullAssertionChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirElvisExpression>> getElvisExpressionCheckers() {
        return SetsKt.setOf(FirUselessElvisChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirGetClassCall>> getGetClassCallCheckers() {
        return SetsKt.setOf(FirClassLiteralChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirSafeCallExpression>> getSafeCallExpressionCheckers() {
        return SetsKt.setOf(FirUnnecessarySafeCallChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirTypeOperatorCall>> getTypeOperatorCallCheckers() {
        return SetsKt.setOf((Object[]) new FirExpressionChecker[]{FirUselessTypeOperationCallChecker.INSTANCE, FirCastOperatorsChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirResolvedQualifier>> getResolvedQualifierCheckers() {
        return SetsKt.setOf((Object[]) new FirExpressionChecker[]{FirStandaloneQualifierChecker.INSTANCE, FirOptInUsageQualifierChecker.INSTANCE, FirDeprecatedQualifierChecker.INSTANCE, FirVisibilityQualifierChecker.INSTANCE, FirInlineBodyResolvedQualifierChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirEqualityOperatorCall>> getEqualityOperatorCallCheckers() {
        return SetsKt.setOf(FirEqualityCompatibilityChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirArrayLiteral>> getArrayLiteralCheckers() {
        return SetsKt.setOf(FirUnsupportedArrayLiteralChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirInaccessibleReceiverExpression>> getInaccessibleReceiverCheckers() {
        return SetsKt.setOf(FirReceiverAccessBeforeSuperCallChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirCallableReferenceAccess>> getCallableReferenceAccessCheckers() {
        return SetsKt.setOf(FirTypeArgumentsOfQualifierOfCallableReferenceChecker.INSTANCE);
    }
}
