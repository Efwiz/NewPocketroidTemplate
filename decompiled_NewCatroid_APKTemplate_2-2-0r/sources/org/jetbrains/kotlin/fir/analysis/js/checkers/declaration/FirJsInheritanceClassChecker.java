package org.jetbrains.kotlin.fir.analysis.js.checkers.declaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.js.FirJsErrors;
import org.jetbrains.kotlin.fir.analysis.js.checkers.FirJsHelpersKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScopeKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirIntersectionOverrideFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;

/* JADX INFO: compiled from: FirJsInheritanceClassChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0016\u0010\f\u001a\u0004\u0018\u00010\r*\u00020\u00022\u0006\u0010\b\u001a\u00020\tH\u0002J\u0014\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0014\u0010\u0013\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/declaration/FirJsInheritanceClassChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "findFakeMethodOverridingExternalWithOptionalParams", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "isBuiltinFunctionalTypeOrSubtype", "", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "isSuspendFunctionTypeOrSubtype", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsInheritanceClassChecker extends FirDeclarationChecker<FirClass> {
    public static final FirJsInheritanceClassChecker INSTANCE = new FirJsInheritanceClassChecker();

    private FirJsInheritanceClassChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirNamedFunctionSymbol firNamedFunctionSymbolFindFakeMethodOverridingExternalWithOptionalParams;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (!FirJsHelpersKt.isEffectivelyExternal(declaration.getSymbol(), context) && (firNamedFunctionSymbolFindFakeMethodOverridingExternalWithOptionalParams = findFakeMethodOverridingExternalWithOptionalParams(declaration, context)) != null) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getOVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS_WITH_FAKE(), firNamedFunctionSymbolFindFakeMethodOverridingExternalWithOptionalParams, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
        if (context.get$languageVersionSettings().supportsFeature(LanguageFeature.JsAllowImplementingFunctionInterface)) {
            return;
        }
        List<ConeClassLikeType> superConeTypes = FirDeclarationUtilKt.getSuperConeTypes(declaration);
        if ((superConeTypes instanceof Collection) && superConeTypes.isEmpty()) {
            return;
        }
        for (ConeClassLikeType coneClassLikeType : superConeTypes) {
            FirJsInheritanceClassChecker firJsInheritanceClassChecker = INSTANCE;
            if (firJsInheritanceClassChecker.isBuiltinFunctionalTypeOrSubtype(coneClassLikeType, context.getSession()) && !firJsInheritanceClassChecker.isSuspendFunctionTypeOrSubtype(coneClassLikeType, context.getSession())) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getIMPLEMENTING_FUNCTION_INTERFACE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
        }
    }

    private final boolean isBuiltinFunctionalTypeOrSubtype(ConeClassLikeType coneClassLikeType, FirSession firSession) {
        return TypeComponentsKt.getTypeContext(firSession).isBuiltinFunctionTypeOrSubtype(coneClassLikeType);
    }

    private final boolean isSuspendFunctionTypeOrSubtype(ConeClassLikeType coneClassLikeType, final FirSession firSession) {
        return TypeComponentsKt.getTypeContext(firSession).isTypeOrSubtypeOf(coneClassLikeType, new Function1<ConeKotlinType, Boolean>() { // from class: org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsInheritanceClassChecker$isSuspendFunctionTypeOrSubtype$1$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ConeKotlinType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(FunctionalTypeUtilsKt.isSuspendOrKSuspendFunctionType(it, firSession));
            }
        });
    }

    private final FirNamedFunctionSymbol findFakeMethodOverridingExternalWithOptionalParams(FirClass firClass, CheckerContext checkerContext) {
        Object next;
        Collection<FirNamedFunctionSymbol> collectionCollectAllFunctions = FirContainingNamesAwareScopeKt.collectAllFunctions(FirHelpersKt.unsubstitutedScope(firClass.getSymbol(), checkerContext));
        ArrayList arrayList = new ArrayList();
        for (Object obj : collectionCollectAllFunctions) {
            if (obj instanceof FirIntersectionOverrideFunctionSymbol) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            FirIntersectionOverrideFunctionSymbol firIntersectionOverrideFunctionSymbol = (FirIntersectionOverrideFunctionSymbol) obj2;
            if (Intrinsics.areEqual(FirHelpersKt.getContainingClassSymbol(firIntersectionOverrideFunctionSymbol, checkerContext.getSession()), firClass.getSymbol()) && !firIntersectionOverrideFunctionSymbol.getIntersections().isEmpty()) {
                arrayList2.add(obj2);
            }
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            next = it.next();
            if (FirJsHelpersKt.isOverridingExternalWithOptionalParams((FirIntersectionOverrideFunctionSymbol) next, checkerContext)) {
                return (FirNamedFunctionSymbol) next;
            }
        }
        next = null;
        return (FirNamedFunctionSymbol) next;
    }
}
