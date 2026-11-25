package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirSealedSupertypeChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirSealedSupertypeChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkGlobalDeclaration", "checkLocalDeclaration", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSealedSupertypeChecker extends FirDeclarationChecker<FirClass> {
    public static final FirSealedSupertypeChecker INSTANCE = new FirSealedSupertypeChecker();

    private FirSealedSupertypeChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (FirDeclarationUtilKt.getClassId(declaration).isLocal()) {
            checkLocalDeclaration(declaration, context, reporter);
        } else {
            checkGlobalDeclaration(declaration, context, reporter);
        }
    }

    private final void checkGlobalDeclaration(FirClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        FqName packageFqName = FirDeclarationUtilKt.getClassId(declaration).getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        for (FirTypeRef firTypeRef : declaration.getSuperTypeRefs()) {
            ClassId classId = ConeTypeUtilsKt.getClassId(FirTypeUtilsKt.getConeType(firTypeRef));
            if (classId != null && !classId.isLocal()) {
                FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(context.getSession()).getClassLikeSymbolByClassId(classId);
                FirRegularClassSymbol firRegularClassSymbol = classLikeSymbolByClassId instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) classLikeSymbolByClassId : null;
                if (firRegularClassSymbol != null && firRegularClassSymbol.getResolvedStatus().getModality() == Modality.SEALED) {
                    if (firRegularClassSymbol.getOrigin() instanceof FirDeclarationOrigin.Java) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firTypeRef.getSource(), FirErrors.INSTANCE.getCLASS_INHERITS_JAVA_SEALED_CLASS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    } else {
                        FqName packageFqName2 = firRegularClassSymbol.getClassId().getPackageFqName();
                        Intrinsics.checkNotNullExpressionValue(packageFqName2, "getPackageFqName(...)");
                        if (!Intrinsics.areEqual(packageFqName2, packageFqName)) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, firTypeRef.getSource(), FirErrors.INSTANCE.getSEALED_INHERITOR_IN_DIFFERENT_PACKAGE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        }
                        if (!Intrinsics.areEqual(firRegularClassSymbol.getModuleData(), declaration.getModuleData())) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, firTypeRef.getSource(), FirErrors.INSTANCE.getSEALED_INHERITOR_IN_DIFFERENT_MODULE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        }
                    }
                }
            }
        }
    }

    private final void checkLocalDeclaration(FirClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        for (FirTypeRef firTypeRef : declaration.getSuperTypeRefs()) {
            ClassId classId = ConeTypeUtilsKt.getClassId(FirTypeUtilsKt.getConeType(firTypeRef));
            if (classId != null && !classId.isLocal()) {
                FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(context.getSession()).getClassLikeSymbolByClassId(classId);
                FirRegularClassSymbol firRegularClassSymbol = classLikeSymbolByClassId instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) classLikeSymbolByClassId : null;
                if (firRegularClassSymbol != null && firRegularClassSymbol.getResolvedStatus().getModality() == Modality.SEALED) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firTypeRef.getSource(), FirErrors.INSTANCE.getSEALED_SUPERTYPE_IN_LOCAL_CLASS(), declaration instanceof FirAnonymousObject ? "Anonymous object" : "Local class", firRegularClassSymbol.getClassKind(), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                    return;
                }
            }
        }
    }
}
