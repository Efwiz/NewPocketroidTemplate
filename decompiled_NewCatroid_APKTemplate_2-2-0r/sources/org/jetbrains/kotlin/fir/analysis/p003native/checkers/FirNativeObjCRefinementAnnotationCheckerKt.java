package org.jetbrains.kotlin.fir.analysis.p003native.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirNativeObjCRefinementAnnotationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0005H\u0000¨\u0006\u0006"}, d2 = {"findMetaAnnotations", "Lorg/jetbrains/kotlin/fir/analysis/native/checkers/ObjCExportMetaAnnotations;", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "checkers.native"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirNativeObjCRefinementAnnotationCheckerKt {
    public static final ObjCExportMetaAnnotations findMetaAnnotations(List<? extends FirAnnotation> list, FirSession session) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirAnnotation firAnnotation = null;
        FirAnnotation firAnnotation2 = null;
        for (FirAnnotation firAnnotation3 : list) {
            ClassId annotationClassId = FirAnnotationUtilsKt.toAnnotationClassId(firAnnotation3, session);
            if (Intrinsics.areEqual(annotationClassId, FirNativeObjCRefinementChecker.INSTANCE.getHidesFromObjCClassId())) {
                firAnnotation = firAnnotation3;
            } else if (Intrinsics.areEqual(annotationClassId, FirNativeObjCRefinementChecker.INSTANCE.getRefinesInSwiftClassId())) {
                firAnnotation2 = firAnnotation3;
            }
            if (firAnnotation != null && firAnnotation2 != null) {
                break;
            }
        }
        return new ObjCExportMetaAnnotations(firAnnotation, firAnnotation2);
    }
}
