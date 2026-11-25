package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.impl.FirPrimaryConstructor;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirErrorExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;

/* JADX INFO: compiled from: FirUnsupportedArrayLiteralChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\tH\u0002J\u0010\u0010\u000e\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirUnsupportedArrayLiteralChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirArrayLiteralChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "isInsideAnnotationCall", "", "isInsideAnnotationClass", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirUnsupportedArrayLiteralChecker extends FirExpressionChecker<FirArrayLiteral> {
    public static final FirUnsupportedArrayLiteralChecker INSTANCE = new FirUnsupportedArrayLiteralChecker();

    private FirUnsupportedArrayLiteralChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirArrayLiteral expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (isInsideAnnotationCall(expression, context)) {
            return;
        }
        if (context.getCallsOrAssignments().isEmpty() && isInsideAnnotationClass(context)) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getUNSUPPORTED(), "Collection literals outside of annotations", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }

    private final boolean isInsideAnnotationCall(FirArrayLiteral expression, CheckerContext context) {
        List<FirExpression> arguments;
        List<FirAnnotationContainer> listListOf;
        FirStatement firStatement = (FirStatement) CollectionsKt.lastOrNull((List) context.getCallsOrAssignments());
        if (firStatement == null) {
            return false;
        }
        if (firStatement instanceof FirFunctionCall) {
            FirRegularClassSymbol regularClassSymbol = FirHelpersKt.toRegularClassSymbol(((FirFunctionCall) firStatement).getTypeRef(), context.getSession());
            if ((regularClassSymbol != null ? regularClassSymbol.getClassKind() : null) != ClassKind.ANNOTATION_CLASS) {
                return false;
            }
            arguments = ((FirCall) firStatement).getArgumentList().getArguments();
        } else {
            if (!(firStatement instanceof FirAnnotationCall)) {
                return false;
            }
            arguments = ((FirCall) firStatement).getArgumentList().getArguments();
        }
        List<FirExpression> list = arguments;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        for (FirExpression firExpression : list) {
            if (firExpression instanceof FirVarargArgumentsExpression) {
                List<FirExpression> arguments2 = ((FirVarargArgumentsExpression) firExpression).getArguments();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments2, 10));
                Iterator<T> it = arguments2.iterator();
                while (it.hasNext()) {
                    arrayList.add(FirExpressionUtilKt.unwrapArgument((FirExpression) it.next()));
                }
                listListOf = arrayList;
            } else {
                listListOf = CollectionsKt.listOf(FirExpressionUtilKt.unwrapArgument(firExpression));
            }
            for (FirAnnotationContainer firAnnotationContainer : listListOf) {
                if (!Intrinsics.areEqual(firAnnotationContainer, expression) && (!(firAnnotationContainer instanceof FirErrorExpression) || !Intrinsics.areEqual(((FirErrorExpression) firAnnotationContainer).getExpression(), expression))) {
                    if (firAnnotationContainer instanceof FirArrayLiteral) {
                        List<FirExpression> arguments3 = ((FirCall) firAnnotationContainer).getArgumentList().getArguments();
                        if (!(arguments3 instanceof Collection) || !arguments3.isEmpty()) {
                            Iterator<T> it2 = arguments3.iterator();
                            while (it2.hasNext()) {
                                if (Intrinsics.areEqual(FirExpressionUtilKt.unwrapArgument((FirExpression) it2.next()), expression)) {
                                }
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    private final boolean isInsideAnnotationClass(CheckerContext context) {
        for (FirDeclaration firDeclaration : CollectionsKt.asReversed(context.getContainingDeclarations())) {
            if (firDeclaration instanceof FirRegularClass) {
                FirRegularClass firRegularClass = (FirRegularClass) firDeclaration;
                if (!firRegularClass.getStatus().isCompanion()) {
                    return firRegularClass.getClassKind() == ClassKind.ANNOTATION_CLASS;
                }
            } else if (!(firDeclaration instanceof FirValueParameter) && !(firDeclaration instanceof FirPrimaryConstructor)) {
                return false;
            }
        }
        return false;
    }
}
