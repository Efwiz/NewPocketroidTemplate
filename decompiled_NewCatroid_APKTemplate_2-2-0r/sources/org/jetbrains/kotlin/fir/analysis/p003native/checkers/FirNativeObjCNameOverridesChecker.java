package org.jetbrains.kotlin.fir.analysis.p003native.checkers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.p002native.FirNativeErrors;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;

/* JADX INFO: compiled from: FirNativeObjCNameOverridesChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J2\u0010\u0005\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u001a\u0010\u0012\u001a\u00020\u0013*\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u00140\u0014H\u0002J\u001c\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u000f*\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010\f\u001a\u00020\rH\u0002¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/native/checkers/FirNativeObjCNameOverridesChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "firTypeScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "memberSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "declarationToReport", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "allNamesEquals", "", "", "Lorg/jetbrains/kotlin/fir/analysis/native/checkers/FirNativeObjCNameChecker$ObjCName;", "getFirstBaseSymbol", "checkers.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNativeObjCNameOverridesChecker extends FirDeclarationChecker<FirClass> {
    public static final FirNativeObjCNameOverridesChecker INSTANCE = new FirNativeObjCNameOverridesChecker();

    private FirNativeObjCNameOverridesChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(final FirClass declaration, final CheckerContext context, final DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        final FirTypeScope firTypeScopeUnsubstitutedScope = FirHelpersKt.unsubstitutedScope(declaration, context);
        FirTypeScope firTypeScope = firTypeScopeUnsubstitutedScope;
        FirContainingNamesAwareScopeKt.processAllFunctions(firTypeScope, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.native.checkers.FirNativeObjCNameOverridesChecker.check.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                invoke2(firNamedFunctionSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                FirNamedFunctionSymbol firNamedFunctionSymbol = symbol;
                if (ClassMembersKt.isIntersectionOverride(firNamedFunctionSymbol)) {
                    FirNativeObjCNameOverridesChecker.INSTANCE.check(firTypeScopeUnsubstitutedScope, firNamedFunctionSymbol, declaration, context, reporter);
                }
            }
        });
        FirContainingNamesAwareScopeKt.processAllProperties(firTypeScope, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.native.checkers.FirNativeObjCNameOverridesChecker.check.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                invoke2(firVariableSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirVariableSymbol<?> symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                FirVariableSymbol<?> firVariableSymbol = symbol;
                if (ClassMembersKt.isIntersectionOverride(firVariableSymbol)) {
                    FirNativeObjCNameOverridesChecker.INSTANCE.check(firTypeScopeUnsubstitutedScope, firVariableSymbol, declaration, context, reporter);
                }
            }
        });
    }

    public final void check(FirTypeScope firTypeScope, FirCallableSymbol<?> memberSymbol, FirDeclaration declarationToReport, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(firTypeScope, "firTypeScope");
        Intrinsics.checkNotNullParameter(memberSymbol, "memberSymbol");
        Intrinsics.checkNotNullParameter(declarationToReport, "declarationToReport");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        List<FirCallableSymbol<?>> listRetrieveDirectOverriddenOf = FirTypeScopeKt.retrieveDirectOverriddenOf(firTypeScope, memberSymbol);
        if (listRetrieveDirectOverriddenOf.isEmpty()) {
            return;
        }
        List<FirCallableSymbol<?>> list = listRetrieveDirectOverriddenOf;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(FirNativeObjCNameChecker.INSTANCE.getObjCNames(INSTANCE.getFirstBaseSymbol((FirCallableSymbol) it.next(), firTypeScope), context.getSession()));
        }
        if (allNamesEquals(arrayList)) {
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag((FirCallableSymbol<?>) it2.next());
            FirRegularClassSymbol firRegularClassSymbol = coneClassLikeLookupTagContainingClassLookupTag != null ? LookupTagUtilsKt.toFirRegularClassSymbol(coneClassLikeLookupTagContainingClassLookupTag, context.getSession()) : null;
            if (firRegularClassSymbol != null) {
                arrayList2.add(firRegularClassSymbol);
            }
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, declarationToReport.getSource(), FirNativeErrors.INSTANCE.getINCOMPATIBLE_OBJC_NAME_OVERRIDE(), declarationToReport.getSymbol(), arrayList2, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
    }

    private final FirCallableSymbol<?> getFirstBaseSymbol(FirCallableSymbol<?> firCallableSymbol, FirTypeScope firTypeScope) {
        List<FirCallableSymbol<?>> listRetrieveDirectOverriddenOf = FirTypeScopeKt.retrieveDirectOverriddenOf(firTypeScope, firCallableSymbol);
        return listRetrieveDirectOverriddenOf.isEmpty() ? firCallableSymbol : getFirstBaseSymbol((FirCallableSymbol) CollectionsKt.first((List) listRetrieveDirectOverriddenOf), firTypeScope);
    }

    private final boolean allNamesEquals(List<? extends List<FirNativeObjCNameChecker.ObjCName>> list) {
        List<FirNativeObjCNameChecker.ObjCName> list2 = list.get(0);
        int size = list.size();
        for (int i = 1; i < size; i++) {
            if (!Intrinsics.areEqual(list2, list.get(i))) {
                return false;
            }
        }
        return true;
    }
}
