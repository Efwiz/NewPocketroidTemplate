package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: FirQualifiedSupertypeExtendedByOtherSupertypeChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedSupertypeExtendedByOtherSupertypeChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirQualifiedSupertypeExtendedByOtherSupertypeChecker extends FirExpressionChecker<FirQualifiedAccessExpression> {
    public static final FirQualifiedSupertypeExtendedByOtherSupertypeChecker INSTANCE = new FirQualifiedSupertypeExtendedByOtherSupertypeChecker();

    private FirQualifiedSupertypeExtendedByOtherSupertypeChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        FirClass firClassFindClosestClassOrObject;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (context.get$languageVersionSettings().supportsFeature(LanguageFeature.QualifiedSupertypeMayBeExtendedByOtherSupertype)) {
            return;
        }
        FirReference calleeReference = expression.getCalleeReference();
        FirSuperReference firSuperReference = calleeReference instanceof FirSuperReference ? (FirSuperReference) calleeReference : null;
        if (firSuperReference != null) {
            if (!ReferenceUtilsKt.hadExplicitTypeInSource(firSuperReference)) {
                firSuperReference = null;
            }
            if (firSuperReference != null) {
                FirClassLikeSymbol<?> classLikeSymbol = FirHelpersKt.toClassLikeSymbol(firSuperReference.getSuperTypeRef(), context.getSession());
                FirRegularClassSymbol firRegularClassSymbolFullyExpandedClass = classLikeSymbol != null ? DeclarationUtilsKt.fullyExpandedClass(classLikeSymbol, context.getSession()) : null;
                FirRegularClassSymbol firRegularClassSymbol = firRegularClassSymbolFullyExpandedClass instanceof FirClassSymbol ? firRegularClassSymbolFullyExpandedClass : null;
                if (firRegularClassSymbol == null || (firClassFindClosestClassOrObject = FirHelpersKt.findClosestClassOrObject(context)) == null) {
                    return;
                }
                Iterator<FirTypeRef> it = firClassFindClosestClassOrObject.getSuperTypeRefs().iterator();
                int i = 0;
                FirRegularClassSymbol firRegularClassSymbol2 = null;
                while (it.hasNext()) {
                    FirClassLikeSymbol<?> classLikeSymbol2 = FirHelpersKt.toClassLikeSymbol(it.next(), context.getSession());
                    FirRegularClassSymbol firRegularClassSymbolFullyExpandedClass2 = classLikeSymbol2 != null ? DeclarationUtilsKt.fullyExpandedClass(classLikeSymbol2, context.getSession()) : null;
                    FirRegularClassSymbol firRegularClassSymbol3 = firRegularClassSymbolFullyExpandedClass2 instanceof FirClassSymbol ? firRegularClassSymbolFullyExpandedClass2 : null;
                    if (firRegularClassSymbol3 != null) {
                        boolean zIsSupertypeOf = FirHelpersKt.isSupertypeOf(firRegularClassSymbol, firRegularClassSymbol3, context.getSession());
                        if (Intrinsics.areEqual(firRegularClassSymbol, firRegularClassSymbol3) || zIsSupertypeOf) {
                            if (zIsSupertypeOf) {
                                firRegularClassSymbol2 = firRegularClassSymbol3;
                            }
                            i++;
                            if (i >= 2) {
                                break;
                            }
                        }
                    }
                }
                FirRegularClassSymbol firRegularClassSymbol4 = firRegularClassSymbol2;
                if (i < 2 || firRegularClassSymbol4 == null) {
                    return;
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firSuperReference.getSuperTypeRef().getSource(), FirErrors.INSTANCE.getQUALIFIED_SUPERTYPE_EXTENDED_BY_OTHER_SUPERTYPE(), firRegularClassSymbol4, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
    }
}
