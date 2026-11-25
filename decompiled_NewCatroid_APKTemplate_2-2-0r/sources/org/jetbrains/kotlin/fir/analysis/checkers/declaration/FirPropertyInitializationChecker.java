package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.LinkedHashSet;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.visitors.FirVisitorVoid;

/* JADX INFO: compiled from: FirPropertyInitializationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyInitializationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirPropertyInitializationChecker extends FirDeclarationChecker<FirRegularClass> {
    public static final FirPropertyInitializationChecker INSTANCE = new FirPropertyInitializationChecker();

    private FirPropertyInitializationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirRegularClass declaration, final CheckerContext context, final DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        FirVisitorVoid firVisitorVoid = new FirVisitorVoid() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirPropertyInitializationChecker$check$visitor$1
            @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
            public void visitAnonymousFunction(FirAnonymousFunction anonymousFunction) {
                Intrinsics.checkNotNullParameter(anonymousFunction, "anonymousFunction");
            }

            @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
            public void visitConstructor(FirConstructor constructor) {
                Intrinsics.checkNotNullParameter(constructor, "constructor");
            }

            @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
            public void visitEnumEntry(FirEnumEntry enumEntry) {
                Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
            }

            @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
            public void visitPropertyAccessor(FirPropertyAccessor propertyAccessor) {
                Intrinsics.checkNotNullParameter(propertyAccessor, "propertyAccessor");
            }

            @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
            public void visitRegularClass(FirRegularClass regularClass) {
                Intrinsics.checkNotNullParameter(regularClass, "regularClass");
            }

            @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
            public void visitSimpleFunction(FirSimpleFunction simpleFunction) {
                Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
            }

            @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
            /* JADX INFO: renamed from: visitElement */
            public void mo6787visitElement(FirElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                element.acceptChildren(this);
            }

            @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
            public void visitVariableAssignment(FirVariableAssignment variableAssignment) {
                Intrinsics.checkNotNullParameter(variableAssignment, "variableAssignment");
                variableAssignment.acceptChildren(this);
                FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(variableAssignment);
                FirCallableSymbol resolvedCallableSymbol$default = calleeReference != null ? FirReferenceUtilsKt.toResolvedCallableSymbol$default(calleeReference, false, 1, null) : null;
                FirPropertySymbol firPropertySymbol = resolvedCallableSymbol$default instanceof FirPropertySymbol ? (FirPropertySymbol) resolvedCallableSymbol$default : null;
                if (firPropertySymbol != null && linkedHashSet.contains(firPropertySymbol)) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, variableAssignment.getLValue().getSource(), FirErrors.INSTANCE.getINITIALIZATION_BEFORE_DECLARATION(), firPropertySymbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
            }
        };
        for (FirDeclaration firDeclaration : CollectionsKt.asReversed(declaration.getDeclarations())) {
            if (!linkedHashSet.isEmpty()) {
                firDeclaration.accept(firVisitorVoid);
            }
            if (firDeclaration instanceof FirProperty) {
                linkedHashSet.add(((FirProperty) firDeclaration).getSymbol());
            }
        }
    }
}
