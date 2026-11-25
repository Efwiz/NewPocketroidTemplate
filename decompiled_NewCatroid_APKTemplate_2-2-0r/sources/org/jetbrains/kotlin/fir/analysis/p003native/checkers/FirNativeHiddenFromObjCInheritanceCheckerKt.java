package org.jetbrains.kotlin.fir.analysis.p003native.checkers;

import java.util.Iterator;
import kotlin.Metadata;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;

/* JADX INFO: compiled from: FirNativeHiddenFromObjCInheritanceChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010\u0000\u001a\u00020\u00012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u001c\u0010\u0006\u001a\u00020\u00012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002¨\u0006\u0007"}, d2 = {"checkContainingClassIsHidden", "", "classSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "checkIsHiddenFromObjC", "checkers.native"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirNativeHiddenFromObjCInheritanceCheckerKt {
    private static final boolean checkContainingClassIsHidden(FirClassLikeSymbol<?> firClassLikeSymbol, FirSession firSession) {
        FirClassLikeSymbol<?> containingClassSymbol = FirHelpersKt.getContainingClassSymbol(firClassLikeSymbol, firSession);
        if (containingClassSymbol == null) {
            return false;
        }
        if (checkIsHiddenFromObjC(containingClassSymbol, firSession)) {
            return true;
        }
        return checkContainingClassIsHidden(containingClassSymbol, firSession);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean checkIsHiddenFromObjC(FirClassLikeSymbol<?> firClassLikeSymbol, FirSession firSession) {
        Iterator<T> it = firClassLikeSymbol.getAnnotations().iterator();
        while (it.hasNext()) {
            FirClassLikeSymbol<?> annotationClassLikeSymbol = FirAnnotationUtilsKt.toAnnotationClassLikeSymbol((FirAnnotation) it.next(), firSession);
            if (annotationClassLikeSymbol != null && FirNativeObjCRefinementAnnotationCheckerKt.findMetaAnnotations(annotationClassLikeSymbol.getAnnotations(), firSession).getHidesFromObjCAnnotation() != null) {
                return true;
            }
        }
        return checkContainingClassIsHidden(firClassLikeSymbol, firSession);
    }
}
