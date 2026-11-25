package org.jetbrains.kotlin.fir.analysis.js.checkers.declaration;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.js.FirJsErrors;
import org.jetbrains.kotlin.fir.analysis.js.checkers.FirJsHelpersKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.name.JsStandardClassIds;

/* JADX INFO: compiled from: FirJsNameChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/declaration/FirJsNameChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsNameChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirJsNameChecker INSTANCE = new FirJsNameChecker();

    private FirJsNameChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        boolean z = declaration instanceof FirProperty;
        if (z) {
            FirPropertyAccessorSymbol[] firPropertyAccessorSymbolArr = new FirPropertyAccessorSymbol[2];
            FirProperty firProperty = (FirProperty) declaration;
            FirPropertyAccessor getter = firProperty.getGetter();
            int i = 0;
            firPropertyAccessorSymbolArr[0] = getter != null ? getter.getSymbol() : null;
            FirPropertyAccessor setter = firProperty.getSetter();
            firPropertyAccessorSymbolArr[1] = setter != null ? setter.getSymbol() : null;
            List listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) firPropertyAccessorSymbolArr);
            List list = listListOfNotNull;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (FirJsHelpersKt.getJsName((FirPropertyAccessorSymbol) it.next(), context.getSession()) != null && (i = i + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
            if (i > 0 && i < listListOfNotNull.size()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getJS_NAME_IS_NOT_ON_ALL_ACCESSORS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
        FirAnnotation annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(declaration, JsStandardClassIds.Annotations.JsName, context.getSession());
        if (annotationByClassId == null) {
            return;
        }
        KtSourceElement source = annotationByClassId.getSource();
        if (source == null) {
            source = declaration.getSource();
        }
        if (FirHelpersKt.getAnnotationStringParameter(declaration.getSymbol(), JsStandardClassIds.Annotations.JsNative, context.getSession()) != null) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirJsErrors.INSTANCE.getJS_NAME_PROHIBITED_FOR_NAMED_NATIVE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        if (((declaration instanceof FirCallableDeclaration) && ((FirMemberDeclaration) declaration).getStatus().isOverride()) || ((declaration instanceof FirPropertyAccessor) && ((FirPropertyAccessor) declaration).getPropertySymbol().getRawStatus().isOverride())) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirJsErrors.INSTANCE.getJS_NAME_PROHIBITED_FOR_OVERRIDE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        if (declaration instanceof FirConstructor) {
            if (((FirConstructor) declaration).getIsPrimary()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirJsErrors.INSTANCE.getJS_NAME_ON_PRIMARY_CONSTRUCTOR_PROHIBITED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        } else if (declaration instanceof FirPropertyAccessor) {
            if (FirJsHelpersKt.getJsName(((FirPropertyAccessor) declaration).getPropertySymbol(), context.getSession()) != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirJsErrors.INSTANCE.getJS_NAME_ON_ACCESSOR_AND_PROPERTY(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        } else if (z && FirDeclarationUtilKt.isExtension((FirCallableDeclaration) declaration)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirJsErrors.INSTANCE.getJS_NAME_PROHIBITED_FOR_EXTENSION_PROPERTY(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }
}
