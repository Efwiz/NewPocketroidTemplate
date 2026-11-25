package org.jetbrains.kotlin.fir.analysis.js.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.js.FirJsErrors;
import org.jetbrains.kotlin.fir.analysis.js.checkers.FirJsHelpersKt;
import org.jetbrains.kotlin.fir.analysis.js.checkers.FirJsModuleCheckUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.JsStandardClassIds;

/* JADX INFO: compiled from: FirJsModuleChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0014\u0010\r\u001a\u00020\u000e*\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0010H\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/declaration/FirJsModuleChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkSuperClass", "isEitherModuleOrNonModule", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsModuleChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirJsModuleChecker INSTANCE = new FirJsModuleChecker();

    private FirJsModuleChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirBasedSymbol<? extends FirDeclaration> symbol;
        FirFile containingFile;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        checkSuperClass(declaration, context, reporter);
        if ((declaration instanceof FirFile) || !isEitherModuleOrNonModule(declaration, context.getSession())) {
            return;
        }
        if ((declaration instanceof FirProperty) && ((FirProperty) declaration).getIsVar()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getJS_MODULE_PROHIBITED_ON_VAR(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        FirDeclaration firDeclarationClosestNonLocalWith = FirHelpersKt.closestNonLocalWith(context, declaration);
        if (firDeclarationClosestNonLocalWith == null || (symbol = firDeclarationClosestNonLocalWith.getSymbol()) == null) {
            return;
        }
        if (!FirJsHelpersKt.isNativeObject(symbol, context)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getJS_MODULE_PROHIBITED_ON_NON_NATIVE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        if (FirHelpersKt.isTopLevel(context) && (containingFile = context.getContainingFile()) != null && isEitherModuleOrNonModule(containingFile, context.getSession())) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getNESTED_JS_MODULE_PROHIBITED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final void checkSuperClass(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        ConeClassLikeType coneClassLikeTypeSuperClassNotAny;
        FirClassLikeSymbol<?> symbol;
        KtSourceElement source;
        Object obj = null;
        FirClass firClass = declaration instanceof FirClass ? (FirClass) declaration : null;
        if (firClass == null || (coneClassLikeTypeSuperClassNotAny = FirJsHelpersKt.superClassNotAny(firClass, context.getSession())) == null || (symbol = TypeUtilsKt.toSymbol(coneClassLikeTypeSuperClassNotAny, context.getSession())) == null) {
            return;
        }
        for (Object obj2 : firClass.getSuperTypeRefs()) {
            ConeKotlinType coneTypeOrNull = FirTypeUtilsKt.getConeTypeOrNull((FirTypeRef) obj2);
            if (Intrinsics.areEqual(coneTypeOrNull != null ? TypeUtilsKt.toSymbol(coneTypeOrNull, context.getSession()) : null, symbol)) {
                obj = obj2;
                break;
            }
        }
        FirTypeRef firTypeRef = (FirTypeRef) obj;
        FirClassLikeSymbol<?> firClassLikeSymbol = symbol;
        if (firTypeRef == null || (source = firTypeRef.getSource()) == null) {
            source = declaration.getSource();
        }
        FirJsModuleCheckUtilsKt.checkJsModuleUsage(firClassLikeSymbol, context, reporter, source);
    }

    private final boolean isEitherModuleOrNonModule(FirDeclaration firDeclaration, FirSession firSession) {
        return FirAnnotationUtilsKt.hasAnnotation(firDeclaration, JsStandardClassIds.Annotations.JsModule, firSession) || FirAnnotationUtilsKt.hasAnnotation(firDeclaration, JsStandardClassIds.Annotations.JsNonModule, firSession);
    }
}
