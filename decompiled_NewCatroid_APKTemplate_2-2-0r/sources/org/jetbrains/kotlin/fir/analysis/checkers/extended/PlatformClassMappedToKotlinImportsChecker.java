package org.jetbrains.kotlin.fir.analysis.checkers.extended;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirImport;
import org.jetbrains.kotlin.fir.declarations.FirResolvedImport;
import org.jetbrains.kotlin.fir.scopes.FirPlatformClassMapperKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: PlatformClassMappedToKotlinImportsChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/PlatformClassMappedToKotlinImportsChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFileChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PlatformClassMappedToKotlinImportsChecker extends FirDeclarationChecker<FirFile> {
    public static final PlatformClassMappedToKotlinImportsChecker INSTANCE = new PlatformClassMappedToKotlinImportsChecker();

    private PlatformClassMappedToKotlinImportsChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirFile declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirImport firImport;
        FqName importedFqName;
        ClassId resolvedParentClassId;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Iterator<T> it = declaration.getImports().iterator();
        while (it.hasNext() && (importedFqName = (firImport = (FirImport) it.next()).getImportedFqName()) != null && !importedFqName.isRoot()) {
            String strAsString = importedFqName.shortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            if (strAsString.length() == 0) {
                return;
            }
            FirResolvedImport firResolvedImport = firImport instanceof FirResolvedImport ? (FirResolvedImport) firImport : null;
            if (firResolvedImport == null || (resolvedParentClassId = firResolvedImport.getResolvedParentClassId()) == null) {
                resolvedParentClassId = ClassId.topLevel(importedFqName);
                Intrinsics.checkNotNullExpressionValue(resolvedParentClassId, "topLevel(...)");
            }
            if (!Intrinsics.areEqual(resolvedParentClassId.asSingleFqName(), importedFqName)) {
                return;
            }
            if (FirPlatformClassMapperKt.getPlatformClassMapper(context.getSession()).getCorrespondingKotlinClass(resolvedParentClassId) != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firImport.getSource(), FirErrors.INSTANCE.getPLATFORM_CLASS_MAPPED_TO_KOTLIN(), importedFqName, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
    }
}
