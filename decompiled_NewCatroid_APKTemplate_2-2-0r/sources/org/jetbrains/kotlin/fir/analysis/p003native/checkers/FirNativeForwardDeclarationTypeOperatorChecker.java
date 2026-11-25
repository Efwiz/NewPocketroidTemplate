package org.jetbrains.kotlin.fir.analysis.p003native.checkers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.p002native.FirNativeErrors;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;
import org.jetbrains.kotlin.fir.resolve.SupertypeUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.NativeForwardDeclarationKind;

/* JADX INFO: compiled from: FirNativeForwardDeclarationTypeOperatorChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/native/checkers/FirNativeForwardDeclarationTypeOperatorChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirTypeOperatorCallChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNativeForwardDeclarationTypeOperatorChecker extends FirExpressionChecker<FirTypeOperatorCall> {
    public static final FirNativeForwardDeclarationTypeOperatorChecker INSTANCE = new FirNativeForwardDeclarationTypeOperatorChecker();

    /* JADX INFO: compiled from: FirNativeForwardDeclarationTypeOperatorChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FirOperation.values().length];
            try {
                iArr[FirOperation.AS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FirOperation.SAFE_AS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FirOperation.IS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FirOperation.NOT_IS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private FirNativeForwardDeclarationTypeOperatorChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirTypeOperatorCall expression, CheckerContext context, DiagnosticReporter reporter) {
        NativeForwardDeclarationKind nativeForwardDeclarationKindForwardDeclarationKindOrNull;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirTypeRef conversionTypeRef = expression.getConversionTypeRef();
        FirRegularClassSymbol regularClassSymbol = FirHelpersKt.toRegularClassSymbol(conversionTypeRef, context.getSession());
        if (regularClassSymbol == null || (nativeForwardDeclarationKindForwardDeclarationKindOrNull = FirNativeHelpersKt.forwardDeclarationKindOrNull(regularClassSymbol)) == null) {
            return;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[expression.getOperation().ordinal()];
        if (i != 1 && i != 2) {
            if (i == 3 || i == 4) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirNativeErrors.INSTANCE.getCANNOT_CHECK_FOR_FORWARD_DECLARATION(), FirTypeUtilsKt.getConeType(conversionTypeRef), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                return;
            }
            return;
        }
        FirTypeOperatorCall firTypeOperatorCall = expression;
        FirTypeRef typeRef = ((FirExpression) CollectionsKt.first((List) firTypeOperatorCall.getArgumentList().getArguments())).getTypeRef();
        FirRegularClassSymbol regularClassSymbol2 = FirHelpersKt.toRegularClassSymbol(typeRef, context.getSession());
        if (regularClassSymbol2 != null && regularClassSymbol2.getClassKind() == nativeForwardDeclarationKindForwardDeclarationKindOrNull.getClassKind() && Intrinsics.areEqual(regularClassSymbol2.getName(), regularClassSymbol.getName())) {
            List<ConeClassLikeType> listLookupSuperTypes = SupertypeUtilsKt.lookupSuperTypes(regularClassSymbol2, true, true, context.getSession());
            if (!(listLookupSuperTypes instanceof Collection) || !listLookupSuperTypes.isEmpty()) {
                Iterator<T> it = listLookupSuperTypes.iterator();
                while (it.hasNext()) {
                    if (Intrinsics.areEqual(ConeTypeUtilsKt.getClassId((ConeClassLikeType) it.next()), nativeForwardDeclarationKindForwardDeclarationKindOrNull.getMatchSuperClassId())) {
                        return;
                    }
                }
            }
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirNativeErrors.INSTANCE.getUNCHECKED_CAST_TO_FORWARD_DECLARATION(), FirTypeUtilsKt.getConeType(((FirExpression) CollectionsKt.first((List) firTypeOperatorCall.getArgumentList().getArguments())).getTypeRef()), FirTypeUtilsKt.getConeType(typeRef), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
    }
}
