package org.jetbrains.kotlin.fir.resolve.inference;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveKt;

/* JADX INFO: compiled from: FirBuilderInferenceSession.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0002\u001a\u00020\u0003*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"BUILDER_INFERENCE_ANNOTATION_CLASS_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "hasBuilderInferenceAnnotation", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirBuilderInferenceSessionKt {
    private static final ClassId BUILDER_INFERENCE_ANNOTATION_CLASS_ID;

    static {
        ClassId classId = ClassId.topLevel(AnnotationsForResolveKt.getBUILDER_INFERENCE_ANNOTATION_FQ_NAME());
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        BUILDER_INFERENCE_ANNOTATION_CLASS_ID = classId;
    }

    public static final boolean hasBuilderInferenceAnnotation(FirDeclaration firDeclaration, FirSession session) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return FirAnnotationUtilsKt.hasAnnotation(firDeclaration, BUILDER_INFERENCE_ANNOTATION_CLASS_ID, session);
    }
}
