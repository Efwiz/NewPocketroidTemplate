package org.jetbrains.kotlin.fir.analysis.js.checkers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.expression.FirJsDefinedExternallyCallChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.expression.FirJsDynamicCallChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.expression.FirJsExternalArgumentCallChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.expression.FirJsModuleGetClassCallChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.expression.FirJsModuleQualifiedAccessChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.expression.FirJsNativeRttiChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.expression.FirJsQualifierChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.expression.FirJsReifiedExternalChecker;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;

/* JADX INFO: compiled from: JsExpressionCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R$\u0010\u0003\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR$\u0010\n\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000b0\u0005j\u0002`\f0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\tR$\u0010\u000e\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000f0\u0005j\u0002`\u00100\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\tR$\u0010\u0012\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00130\u0005j\u0002`\u00140\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\tR$\u0010\u0016\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00170\u0005j\u0002`\u00180\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\tR$\u0010\u001a\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u001b0\u0005j\u0002`\u001c0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\t¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/JsExpressionCheckers;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/ExpressionCheckers;", "()V", "annotationCallCheckers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirAnnotationCallChecker;", "getAnnotationCallCheckers", "()Ljava/util/Set;", "basicExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirBasicExpressionChecker;", "getBasicExpressionCheckers", "callCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirCallChecker;", "getCallCheckers", "functionCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirFunctionCallChecker;", "getFunctionCallCheckers", "getClassCallCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirGetClassCallChecker;", "getGetClassCallCheckers", "qualifiedAccessExpressionCheckers", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "getQualifiedAccessExpressionCheckers", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsExpressionCheckers extends ExpressionCheckers {
    public static final JsExpressionCheckers INSTANCE = new JsExpressionCheckers();

    private JsExpressionCheckers() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirAnnotationCall>> getAnnotationCallCheckers() {
        return SetsKt.setOf(FirJsQualifierChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirStatement>> getBasicExpressionCheckers() {
        return SetsKt.setOf((Object[]) new FirExpressionChecker[]{FirJsDefinedExternallyCallChecker.INSTANCE, FirJsNativeRttiChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirFunctionCall>> getFunctionCallCheckers() {
        return SetsKt.setOf(FirJsReifiedExternalChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirCall>> getCallCheckers() {
        return SetsKt.setOf(FirJsExternalArgumentCallChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirGetClassCall>> getGetClassCallCheckers() {
        return SetsKt.setOf(FirJsModuleGetClassCallChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirExpressionChecker<FirQualifiedAccessExpression>> getQualifiedAccessExpressionCheckers() {
        return SetsKt.setOf((Object[]) new FirExpressionChecker[]{FirJsModuleQualifiedAccessChecker.INSTANCE, FirJsDynamicCallChecker.INSTANCE});
    }
}
