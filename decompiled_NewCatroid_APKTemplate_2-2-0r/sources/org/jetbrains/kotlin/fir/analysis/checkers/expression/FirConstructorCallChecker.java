package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;

/* JADX INFO: compiled from: FirConstructorCallChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirConstructorCallChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirFunctionCallChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirConstructorCallChecker extends FirExpressionChecker<FirFunctionCall> {
    public static final FirConstructorCallChecker INSTANCE = new FirConstructorCallChecker();

    private FirConstructorCallChecker() {
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0079  */
    /* JADX WARN: Code duplicated, block: B:29:0x0083  */
    /* JADX WARN: Code duplicated, block: B:36:0x00a4  */
    /* JADX WARN: Code duplicated, block: B:41:0x0098 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:42:0x008d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:45:0x007d A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirFunctionCall expression, CheckerContext context, DiagnosticReporter reporter) {
        FirRegularClassSymbol regularClassSymbol;
        List<FirDeclaration> containingDeclarations;
        Iterator<T> it;
        FirDeclaration firDeclaration;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirConstructorSymbol resolvedConstructorSymbol$default = FirReferenceUtilsKt.toResolvedConstructorSymbol$default(expression.getCalleeReference(), false, 1, null);
        if (resolvedConstructorSymbol$default == null || (regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(FirTypeUtilsKt.getConeType(resolvedConstructorSymbol$default.getResolvedReturnTypeRef()), context.getSession())) == null) {
            return;
        }
        if (regularClassSymbol.getClassKind() == ClassKind.ANNOTATION_CLASS) {
            List<FirStatement> callsOrAssignments = context.getCallsOrAssignments();
            if ((callsOrAssignments instanceof Collection) && callsOrAssignments.isEmpty()) {
                containingDeclarations = context.getContainingDeclarations();
                if (!(containingDeclarations instanceof Collection)) {
                    it = containingDeclarations.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            firDeclaration = (FirDeclaration) it.next();
                            if (firDeclaration instanceof FirRegularClass) {
                            }
                        } else if (!context.get$languageVersionSettings().supportsFeature(LanguageFeature.InstantiationOfAnnotationClasses)) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getANNOTATION_CLASS_CONSTRUCTOR_CALL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        }
                    }
                } else {
                    it = containingDeclarations.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            firDeclaration = (FirDeclaration) it.next();
                            if (firDeclaration instanceof FirRegularClass) {
                            }
                        } else if (!context.get$languageVersionSettings().supportsFeature(LanguageFeature.InstantiationOfAnnotationClasses)) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getANNOTATION_CLASS_CONSTRUCTOR_CALL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        }
                    }
                }
            } else {
                Iterator<T> it2 = callsOrAssignments.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (((FirStatement) it2.next()) instanceof FirAnnotation) {
                        }
                    } else {
                        containingDeclarations = context.getContainingDeclarations();
                        if (!(containingDeclarations instanceof Collection) && containingDeclarations.isEmpty()) {
                            if (!context.get$languageVersionSettings().supportsFeature(LanguageFeature.InstantiationOfAnnotationClasses)) {
                                KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getANNOTATION_CLASS_CONSTRUCTOR_CALL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                            }
                        } else {
                            it = containingDeclarations.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    firDeclaration = (FirDeclaration) it.next();
                                    if ((firDeclaration instanceof FirRegularClass) || ((FirRegularClass) firDeclaration).getClassKind() != ClassKind.ANNOTATION_CLASS) {
                                    }
                                } else if (!context.get$languageVersionSettings().supportsFeature(LanguageFeature.InstantiationOfAnnotationClasses)) {
                                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getANNOTATION_CLASS_CONSTRUCTOR_CALL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (regularClassSymbol.getClassKind() == ClassKind.ENUM_CLASS) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getENUM_CLASS_CONSTRUCTOR_CALL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }
}
