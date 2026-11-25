package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirIntersectionCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;

/* JADX INFO: compiled from: FirAbstractSuperCallChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirAbstractSuperCallChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAbstractSuperCallChecker extends FirExpressionChecker<FirQualifiedAccessExpression> {
    public static final FirAbstractSuperCallChecker INSTANCE = new FirAbstractSuperCallChecker();

    private FirAbstractSuperCallChecker() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        Object obj;
        FirRegularClass firRegularClass;
        FirCallableSymbol<?> resolvedCallableSymbol;
        FirClassLikeSymbol<?> symbol;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (FirHelpersKt.explicitReceiverIsNotSuperReference(expression)) {
            return;
        }
        Iterator it = CollectionsKt.asReversed(context.getContainingDeclarations()).iterator();
        while (true) {
            obj = null;
            if (!it.hasNext()) {
                firRegularClass = null;
                break;
            }
            FirDeclaration firDeclaration = (FirDeclaration) it.next();
            if (!(firDeclaration instanceof FirRegularClass)) {
                firDeclaration = null;
            }
            firRegularClass = (FirRegularClass) firDeclaration;
            if (firRegularClass != null && firRegularClass != null) {
                break;
            }
        }
        FirRegularClass firRegularClass2 = firRegularClass;
        if (firRegularClass2 == null || firRegularClass2.getClassKind() != ClassKind.CLASS || (resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol(expression)) == 0) {
            return;
        }
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(resolvedCallableSymbol);
        FirClassLikeSymbol<?> symbol2 = coneClassLikeLookupTagContainingClassLookupTag != null ? LookupTagUtilsKt.toSymbol(coneClassLikeLookupTagContainingClassLookupTag, context.getSession()) : null;
        FirRegularClassSymbol firRegularClassSymbol = symbol2 instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol2 : null;
        if (firRegularClassSymbol != null && firRegularClassSymbol.getResolvedStatus().getModality() == Modality.ABSTRACT) {
            if (resolvedCallableSymbol.getResolvedStatus().getModality() == Modality.ABSTRACT) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getCalleeReference().getSource(), FirErrors.INSTANCE.getABSTRACT_SUPER_CALL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            if (resolvedCallableSymbol instanceof FirIntersectionCallableSymbol) {
                for (Object obj2 : ((FirIntersectionCallableSymbol) resolvedCallableSymbol).getIntersections()) {
                    ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag2 = ClassMembersKt.containingClassLookupTag((FirCallableSymbol<?>) obj2);
                    if (((coneClassLikeLookupTagContainingClassLookupTag2 == null || (symbol = LookupTagUtilsKt.toSymbol(coneClassLikeLookupTagContainingClassLookupTag2, context.getSession())) == null) ? null : FirHelpersKt.getClassKind(symbol)) != ClassKind.INTERFACE) {
                        obj = obj2;
                        break;
                    }
                }
                FirCallableSymbol firCallableSymbol = (FirCallableSymbol) obj;
                if (firCallableSymbol == null || firCallableSymbol.getResolvedStatus().getModality() != Modality.ABSTRACT) {
                    return;
                }
                if (context.get$languageVersionSettings().supportsFeature(LanguageFeature.ForbidSuperDelegationToAbstractFakeOverride)) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getCalleeReference().getSource(), FirErrors.INSTANCE.getABSTRACT_SUPER_CALL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                } else {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getCalleeReference().getSource(), FirErrors.INSTANCE.getABSTRACT_SUPER_CALL_WARNING(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
        }
    }
}
