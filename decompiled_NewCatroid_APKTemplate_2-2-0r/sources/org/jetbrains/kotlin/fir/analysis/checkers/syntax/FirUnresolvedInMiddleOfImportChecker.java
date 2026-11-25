package org.jetbrains.kotlin.fir.analysis.checkers.syntax;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.FirSourceUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirErrorImport;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirImport;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedParentInImport;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: FirUnresolvedInMiddleOfImportChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J(\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J \u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\f\u0010\u0017\u001a\u00020\u0018*\u00020\u0011H\u0002¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/syntax/FirUnresolvedInMiddleOfImportChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/syntax/FirDeclarationSyntaxChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "Lorg/jetbrains/kotlin/psi/KtFile;", "()V", "checkPsiOrLightTree", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "isClassIdPointingToEnumEntry", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "processErrorImport", PsiKeyword.IMPORT, "Lorg/jetbrains/kotlin/fir/declarations/FirErrorImport;", "getOutermostClassName", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirUnresolvedInMiddleOfImportChecker extends FirDeclarationSyntaxChecker<FirFile, KtFile> {
    public static final FirUnresolvedInMiddleOfImportChecker INSTANCE = new FirUnresolvedInMiddleOfImportChecker();

    private FirUnresolvedInMiddleOfImportChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirSyntaxChecker
    public void checkPsiOrLightTree(FirFile element, KtSourceElement source, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        for (FirImport firImport : element.getImports()) {
            if (firImport instanceof FirErrorImport) {
                processErrorImport((FirErrorImport) firImport, context, reporter);
            }
        }
    }

    private final void processErrorImport(FirErrorImport firErrorImport, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source;
        ConeDiagnostic diagnostic = firErrorImport.getDiagnostic();
        if (!(diagnostic instanceof ConeUnresolvedParentInImport) || (source = firErrorImport.getSource()) == null) {
            return;
        }
        FirSymbolProvider symbolProvider = FirSymbolProviderKt.getSymbolProvider(context.getSession());
        ClassId parentClassId = ((ConeUnresolvedParentInImport) diagnostic).getParentClassId();
        if (firErrorImport.getIsAllUnder() && isClassIdPointingToEnumEntry(parentClassId, symbolProvider)) {
            KtDiagnosticFactory1<Name> cannot_all_under_import_from_singleton = FirErrors.INSTANCE.getCANNOT_ALL_UNDER_IMPORT_FROM_SINGLETON();
            Name shortClassName = parentClassId.getShortClassName();
            Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, cannot_all_under_import_from_singleton, shortClassName, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            return;
        }
        int i = !firErrorImport.getIsAllUnder() ? 1 : 0;
        ClassId parentClassId2 = parentClassId.getParentClassId();
        while (parentClassId2 != null && symbolProvider.getClassLikeSymbolByClassId(parentClassId2) == null) {
            parentClassId2 = parentClassId2.getParentClassId();
            i++;
        }
        KtSourceElement sourceForImportSegment = FirSourceUtilsKt.getSourceForImportSegment(firErrorImport, i);
        if (sourceForImportSegment == null) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, sourceForImportSegment, FirErrors.INSTANCE.getUNRESOLVED_IMPORT(), getOutermostClassName(parentClassId), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }

    private final String getOutermostClassName(ClassId classId) {
        List<Name> listPathSegments = classId.getRelativeClassName().pathSegments();
        Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
        String strAsString = ((Name) CollectionsKt.first((List) listPathSegments)).asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    private final boolean isClassIdPointingToEnumEntry(ClassId classId, FirSymbolProvider symbolProvider) {
        ClassId parentClassId = classId.getParentClassId();
        if (parentClassId == null) {
            return false;
        }
        FirClassLikeSymbol<?> classLikeSymbolByClassId = symbolProvider.getClassLikeSymbolByClassId(parentClassId);
        FirRegularClassSymbol firRegularClassSymbol = classLikeSymbolByClassId instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) classLikeSymbolByClassId : null;
        if (firRegularClassSymbol == null) {
            return false;
        }
        FirRegularClassSymbol firRegularClassSymbol2 = firRegularClassSymbol.getClassKind() == ClassKind.ENUM_CLASS ? firRegularClassSymbol : null;
        if (firRegularClassSymbol2 == null) {
            return false;
        }
        Collection<FirEnumEntrySymbol> collectionCollectEnumEntries = DeclarationUtilsKt.collectEnumEntries(firRegularClassSymbol2);
        if ((collectionCollectEnumEntries instanceof Collection) && collectionCollectEnumEntries.isEmpty()) {
            return false;
        }
        Iterator<T> it = collectionCollectEnumEntries.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(((FirEnumEntrySymbol) it.next()).getCallableId().getCallableName(), classId.getShortClassName())) {
                return true;
            }
        }
        return false;
    }
}
