package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirModifierList;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: compiled from: FirMemberFunctionsChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J(\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirMemberFunctionsChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirSimpleFunctionChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkFunction", "containingDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "function", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirMemberFunctionsChecker extends FirDeclarationChecker<FirSimpleFunction> {
    public static final FirMemberFunctionsChecker INSTANCE = new FirMemberFunctionsChecker();

    private FirMemberFunctionsChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirSimpleFunction declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        List<FirDeclaration> containingDeclarations = context.getContainingDeclarations();
        Object obj = null;
        if (containingDeclarations instanceof List) {
            int size = containingDeclarations.size() - 1;
            if (size >= 0) {
                while (true) {
                    int i = size - 1;
                    obj = containingDeclarations.get(size);
                    if (obj instanceof FirClass) {
                        obj = obj;
                        break;
                    } else if (i < 0) {
                        break;
                    } else {
                        size = i;
                    }
                }
            }
        } else {
            for (Object obj2 : CollectionsKt.reversed(containingDeclarations)) {
                if (obj2 instanceof FirClass) {
                    obj = obj2;
                    break;
                }
            }
        }
        FirClass firClass = (FirClass) obj;
        if (firClass == null) {
            return;
        }
        checkFunction(firClass, declaration, context, reporter);
    }

    private final void checkFunction(FirClass containingDeclaration, FirSimpleFunction function, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source = function.getSource();
        if (source == null || (source.getKind() instanceof KtFakeSourceElementKind)) {
            return;
        }
        FirNamedFunctionSymbol symbol = function.getSymbol();
        FirModifierList modifierList = FirKeywordUtilsKt.getModifierList(source);
        KtModifierKeywordToken ABSTRACT_KEYWORD = KtTokens.ABSTRACT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(ABSTRACT_KEYWORD, "ABSTRACT_KEYWORD");
        boolean zContains = FirKeywordUtilsKt.contains(modifierList, ABSTRACT_KEYWORD);
        FirSimpleFunction firSimpleFunction = function;
        if (firSimpleFunction.getStatus().getModality() == Modality.ABSTRACT || zContains) {
            if (containingDeclaration instanceof FirRegularClass) {
                FirRegularClass firRegularClass = (FirRegularClass) containingDeclaration;
                if (firRegularClass.getStatus().getModality() != Modality.ABSTRACT && firRegularClass.getStatus().getModality() != Modality.SEALED && firRegularClass.getClassKind() != ClassKind.ENUM_CLASS) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getABSTRACT_FUNCTION_IN_NON_ABSTRACT_CLASS(), symbol, firRegularClass.getSymbol(), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                }
            }
            if (function.getBody() != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getABSTRACT_FUNCTION_WITH_BODY(), symbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
        boolean zIsInsideExpectClass = DeclarationUtilsKt.isInsideExpectClass(containingDeclaration, context);
        boolean zIsInsideExternalClass = DeclarationUtilsKt.isInsideExternalClass(containingDeclaration, context);
        KtModifierKeywordToken OPEN_KEYWORD = KtTokens.OPEN_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OPEN_KEYWORD, "OPEN_KEYWORD");
        boolean zContains2 = FirKeywordUtilsKt.contains(modifierList, OPEN_KEYWORD);
        if (function.getBody() != null) {
            return;
        }
        if (containingDeclaration.getClassKind() == ClassKind.INTERFACE) {
            if (Visibilities.INSTANCE.isPrivate(firSimpleFunction.getStatus().getVisibility())) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getPRIVATE_FUNCTION_WITH_NO_BODY(), symbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
            if (zIsInsideExpectClass || zContains || !zContains2) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getREDUNDANT_OPEN_IN_INTERFACE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return;
        }
        if (zIsInsideExpectClass || zContains || firSimpleFunction.getStatus().isExternal() || zIsInsideExternalClass) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getNON_ABSTRACT_FUNCTION_WITH_NO_BODY(), symbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }
}
