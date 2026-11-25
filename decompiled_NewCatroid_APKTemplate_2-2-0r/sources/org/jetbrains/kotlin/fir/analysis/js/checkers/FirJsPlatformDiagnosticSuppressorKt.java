package org.jetbrains.kotlin.fir.analysis.js.checkers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.JsStandardClassIds;

/* JADX INFO: compiled from: FirJsPlatformDiagnosticSuppressor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0002¨\u0006\u0005"}, d2 = {"isLexicallyInsideJsNative", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "checkers.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirJsPlatformDiagnosticSuppressorKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isLexicallyInsideJsNative(FirDeclaration firDeclaration, CheckerContext checkerContext) {
        Set<ClassId> set = JsStandardClassIds.Annotations.nativeAnnotations;
        if ((set instanceof Collection) && set.isEmpty()) {
            return false;
        }
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            if (FirHelpersKt.hasAnnotationOrInsideAnnotatedClass(firDeclaration, (ClassId) it.next(), checkerContext.getSession())) {
                return true;
            }
        }
        return false;
    }
}
