package org.jetbrains.kotlin.fir.analysis.checkers.extended;

import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.psi.KtSafeQualifiedExpression;

/* JADX INFO: compiled from: RedundantCallOfConversionMethod.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0014\u0010\u0011\u001a\u00020\u0012*\u00020\u00132\u0006\u0010\u0014\u001a\u00020\bH\u0002R*\u0010\u0005\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/RedundantCallOfConversionMethod;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "()V", "targetClassMap", "Ljava/util/HashMap;", "", "Lorg/jetbrains/kotlin/name/ClassId;", "Lkotlin/collections/HashMap;", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "isRedundant", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "qualifiedClassId", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RedundantCallOfConversionMethod extends FirExpressionChecker<FirQualifiedAccessExpression> {
    public static final RedundantCallOfConversionMethod INSTANCE = new RedundantCallOfConversionMethod();
    private static final HashMap<String, ClassId> targetClassMap = MapsKt.hashMapOf(TuplesKt.to("toString", StandardClassIds.INSTANCE.getString()), TuplesKt.to("toDouble", StandardClassIds.INSTANCE.getDouble()), TuplesKt.to("toFloat", StandardClassIds.INSTANCE.getFloat()), TuplesKt.to("toLong", StandardClassIds.INSTANCE.getLong()), TuplesKt.to("toInt", StandardClassIds.INSTANCE.getInt()), TuplesKt.to("toChar", StandardClassIds.INSTANCE.getChar()), TuplesKt.to("toShort", StandardClassIds.INSTANCE.getShort()), TuplesKt.to("toByte", StandardClassIds.INSTANCE.getByte()), TuplesKt.to("toULong", StandardClassIds.INSTANCE.getULong()), TuplesKt.to(Namer.UINT_FROM_INT, StandardClassIds.INSTANCE.getUInt()), TuplesKt.to("toUShort", StandardClassIds.INSTANCE.getUShort()), TuplesKt.to("toUByte", StandardClassIds.INSTANCE.getUByte()));

    private RedundantCallOfConversionMethod() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        FirExpression explicitReceiver;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (expression instanceof FirFunctionCall) {
            String strAsString = ((FirFunctionCall) expression).getCalleeReference().getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            ClassId classId = targetClassMap.get(strAsString);
            if (classId == null || (explicitReceiver = expression.getExplicitReceiver()) == null || !isRedundant(explicitReceiver, classId)) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getREDUNDANT_CALL_OF_CONVERSION_METHOD(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x001e  */
    private final boolean isRedundant(FirExpression firExpression, ClassId classId) {
        ClassId classId2;
        if (firExpression instanceof FirConstExpression) {
            classId2 = ConeTypeUtilsKt.getClassId(FirTypeUtilsKt.getConeType(firExpression.getTypeRef()));
        } else if (FirTypeUtilsKt.getConeType(firExpression.getTypeRef()) instanceof ConeFlexibleType) {
            classId2 = null;
        } else {
            FirExpression firExpression2 = firExpression;
            PsiElement psi = UtilsKt.getPsi(firExpression2);
            if ((((psi != null ? psi.getMyParent() : null) instanceof KtSafeQualifiedExpression) || !((UtilsKt.getPsi(firExpression2) instanceof KtSafeQualifiedExpression) || ConeTypeUtilsKt.isMarkedNullable(FirTypeUtilsKt.getConeType(firExpression.getTypeRef())))) && !ConeTypeUtilsKt.isMarkedNullable(FirTypeUtilsKt.getConeType(firExpression.getTypeRef()))) {
                classId2 = ConeTypeUtilsKt.getClassId(FirTypeUtilsKt.getConeType(firExpression.getTypeRef()));
            } else {
                classId2 = null;
            }
        }
        return Intrinsics.areEqual(classId2, classId);
    }
}
