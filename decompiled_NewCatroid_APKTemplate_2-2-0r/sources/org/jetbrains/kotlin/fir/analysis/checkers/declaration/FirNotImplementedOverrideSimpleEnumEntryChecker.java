package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;

/* JADX INFO: compiled from: FirNotImplementedOverrideSimpleEnumEntryChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirNotImplementedOverrideSimpleEnumEntryChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNotImplementedOverrideSimpleEnumEntryChecker extends FirDeclarationChecker<FirClass> {
    public static final FirNotImplementedOverrideSimpleEnumEntryChecker INSTANCE = new FirNotImplementedOverrideSimpleEnumEntryChecker();

    private FirNotImplementedOverrideSimpleEnumEntryChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration.getClassKind() == ClassKind.ENUM_CLASS) {
            List<FirDeclaration> declarations = declaration.getDeclarations();
            ArrayList arrayList = new ArrayList();
            for (Object obj : declarations) {
                if (obj instanceof FirEnumEntry) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            for (Object obj2 : arrayList) {
                FirEnumEntry firEnumEntry = (FirEnumEntry) obj2;
                if (firEnumEntry.getInitializer() == null && firEnumEntry.getSource() != null) {
                    arrayList2.add(obj2);
                }
            }
            ArrayList<FirEnumEntry> arrayList3 = arrayList2;
            if (arrayList3.isEmpty()) {
                return;
            }
            FirTypeScope firTypeScopeUnsubstitutedScope = FirHelpersKt.unsubstitutedScope(declaration, context);
            final ArrayList arrayList4 = new ArrayList();
            FirContainingNamesAwareScopeKt.processAllCallables(firTypeScopeUnsubstitutedScope, new Function1<FirCallableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirNotImplementedOverrideSimpleEnumEntryChecker.check.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirCallableSymbol<?> firCallableSymbol) {
                    invoke2(firCallableSymbol);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirCallableSymbol<?> symbol) {
                    Intrinsics.checkNotNullParameter(symbol, "symbol");
                    if (symbol.getResolvedStatus().getModality() == Modality.ABSTRACT) {
                        arrayList4.add(symbol);
                    }
                }
            });
            if (arrayList4.isEmpty()) {
                return;
            }
            for (FirEnumEntry firEnumEntry2 : arrayList3) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firEnumEntry2.getSource(), FirErrors.INSTANCE.getABSTRACT_MEMBER_NOT_IMPLEMENTED_BY_ENUM_ENTRY(), firEnumEntry2.getSymbol(), arrayList4, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            }
        }
    }
}
