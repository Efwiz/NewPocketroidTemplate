package org.jetbrains.kotlin.fir.analysis.checkers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.extended.ArrayEqualityCanBeReplacedWithEquals;
import org.jetbrains.kotlin.fir.analysis.checkers.extended.CanBeReplacedWithOperatorAssignmentChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.extended.EmptyRangeChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.extended.RedundantCallOfConversionMethod;
import org.jetbrains.kotlin.fir.analysis.checkers.extended.RedundantSingleExpressionStringTemplateChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.extended.UselessCallOnNotNullChecker;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirStringConcatenationCall;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;

/* JADX INFO: compiled from: ExtendedExpressionCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R$\u0010\u0003\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR$\u0010\n\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000b0\u0005j\u0002`\f0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\tR$\u0010\u000e\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000f0\u0005j\u0002`\u00100\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\tR$\u0010\u0012\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00130\u0005j\u0002`\u00140\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\tR$\u0010\u0016\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00170\u0005j\u0002`\u00180\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\t¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/ExtendedExpressionCheckers;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/ExpressionCheckers;", "()V", "basicExpressionCheckers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirBasicExpressionChecker;", "getBasicExpressionCheckers", "()Ljava/util/Set;", "functionCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirFunctionCallChecker;", "getFunctionCallCheckers", "qualifiedAccessExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "getQualifiedAccessExpressionCheckers", "stringConcatenationCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirStringConcatenationCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirStringConcatenationCallChecker;", "getStringConcatenationCallCheckers", "variableAssignmentCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirVariableAssignmentChecker;", "getVariableAssignmentCheckers", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExtendedExpressionCheckers extends ExpressionCheckers {
    public static final ExtendedExpressionCheckers INSTANCE = new ExtendedExpressionCheckers();

    private ExtendedExpressionCheckers() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirStatement>> getBasicExpressionCheckers() {
        return SetsKt.setOf(ArrayEqualityCanBeReplacedWithEquals.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirVariableAssignment>> getVariableAssignmentCheckers() {
        return SetsKt.setOf(CanBeReplacedWithOperatorAssignmentChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirQualifiedAccessExpression>> getQualifiedAccessExpressionCheckers() {
        return SetsKt.setOf((Object[]) new FirExpressionChecker[]{RedundantCallOfConversionMethod.INSTANCE, UselessCallOnNotNullChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirFunctionCall>> getFunctionCallCheckers() {
        return SetsKt.setOf(EmptyRangeChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirStringConcatenationCall>> getStringConcatenationCallCheckers() {
        return SetsKt.setOf(RedundantSingleExpressionStringTemplateChecker.INSTANCE);
    }
}
