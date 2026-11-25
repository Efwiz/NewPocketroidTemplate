package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirFunInterfaceDeclarationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFunInterfaceDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirFunInterfaceDeclarationChecker extends FirDeclarationChecker<FirRegularClass> {
    public static final FirFunInterfaceDeclarationChecker INSTANCE = new FirFunInterfaceDeclarationChecker();

    private FirFunInterfaceDeclarationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirRegularClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirRegularClass firRegularClass = declaration;
        if (firRegularClass.getClassKind() == ClassKind.INTERFACE && declaration.getStatus().isFun()) {
            FirTypeScope firTypeScopeUnsubstitutedScope = FirHelpersKt.unsubstitutedScope(firRegularClass, context);
            FirRegularClassSymbol symbol = declaration.getSymbol();
            FirNamedFunctionSymbol firNamedFunctionSymbol = null;
            for (Name name2 : firTypeScopeUnsubstitutedScope.getCallableNames()) {
                FirTypeScope firTypeScope = firTypeScopeUnsubstitutedScope;
                List<FirNamedFunctionSymbol> functions = FirScopeKt.getFunctions(firTypeScope, name2);
                List<FirVariableSymbol<?>> properties = FirScopeKt.getProperties(firTypeScope, name2);
                for (FirNamedFunctionSymbol firNamedFunctionSymbol2 : functions) {
                    if (firNamedFunctionSymbol2.getResolvedStatus().getModality() == Modality.ABSTRACT) {
                        if (firNamedFunctionSymbol == null) {
                            firNamedFunctionSymbol = firNamedFunctionSymbol2;
                        } else {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getFUN_INTERFACE_WRONG_COUNT_OF_ABSTRACT_MEMBERS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        }
                    }
                }
                for (FirVariableSymbol<?> firVariableSymbol : properties) {
                    FirPropertySymbol firPropertySymbol = firVariableSymbol instanceof FirPropertySymbol ? (FirPropertySymbol) firVariableSymbol : null;
                    if (firPropertySymbol != null && firPropertySymbol.getResolvedStatus().getModality() == Modality.ABSTRACT) {
                        if (!Intrinsics.areEqual(FirHelpersKt.getContainingClassSymbol(firPropertySymbol, context.getSession()), symbol)) {
                            source = declaration.getSource();
                        } else {
                            source = firPropertySymbol.getSource();
                        }
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getFUN_INTERFACE_CANNOT_HAVE_ABSTRACT_PROPERTIES(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    }
                }
            }
            if (firNamedFunctionSymbol == null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getFUN_INTERFACE_WRONG_COUNT_OF_ABSTRACT_MEMBERS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            boolean z = FirHelpersKt.getContainingClassSymbol(firNamedFunctionSymbol, context.getSession()) == symbol;
            if (!firNamedFunctionSymbol.getTypeParameterSymbols().isEmpty()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, z ? firNamedFunctionSymbol.getSource() : declaration.getSource(), FirErrors.INSTANCE.getFUN_INTERFACE_ABSTRACT_METHOD_WITH_TYPE_PARAMETERS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            } else if (firNamedFunctionSymbol.getRawStatus().isSuspend() && !FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession()).supportsFeature(LanguageFeature.SuspendFunctionsInFunInterfaces)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, z ? firNamedFunctionSymbol.getSource() : declaration.getSource(), FirErrors.INSTANCE.getFUN_INTERFACE_WITH_SUSPEND_FUNCTION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            for (FirValueParameterSymbol firValueParameterSymbol : firNamedFunctionSymbol.getValueParameterSymbols()) {
                if (firValueParameterSymbol.getHasDefaultValue()) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, z ? firValueParameterSymbol.getSource() : declaration.getSource(), FirErrors.INSTANCE.getFUN_INTERFACE_ABSTRACT_METHOD_WITH_DEFAULT_VALUE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
        }
    }
}
