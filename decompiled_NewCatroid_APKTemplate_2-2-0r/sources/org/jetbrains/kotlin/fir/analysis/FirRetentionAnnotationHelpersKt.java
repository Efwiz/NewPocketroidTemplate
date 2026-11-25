package org.jetbrains.kotlin.fir.analysis;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirRetentionAnnotationHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u0012\u0010\u0007\u001a\u00020\b*\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006\u001a\f\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\u0001\u001a\u0012\u0010\u0007\u001a\u00020\b*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u0014\u0010\n\u001a\u0004\u0018\u00010\u0001*\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u0016\u0010\n\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0002¨\u0006\f"}, d2 = {"getAnnotationByClassId", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getRetention", "Lkotlin/annotation/AnnotationRetention;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "getRetentionAnnotation", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirRetentionAnnotationHelpersKt {

    /* JADX INFO: compiled from: FirRetentionAnnotationHelpers.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class EntriesMappings {
        public static final /* synthetic */ EnumEntries<AnnotationRetention> entries$0 = EnumEntriesKt.enumEntries(AnnotationRetention.values());
    }

    public static final AnnotationRetention getRetention(FirRegularClass firRegularClass, FirSession session) {
        AnnotationRetention retention;
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirAnnotation retentionAnnotation = getRetentionAnnotation(firRegularClass, session);
        return (retentionAnnotation == null || (retention = getRetention(retentionAnnotation)) == null) ? AnnotationRetention.RUNTIME : retention;
    }

    public static final AnnotationRetention getRetention(FirRegularClassSymbol firRegularClassSymbol, FirSession session) {
        AnnotationRetention retention;
        Intrinsics.checkNotNullParameter(firRegularClassSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirAnnotation retentionAnnotation = getRetentionAnnotation(firRegularClassSymbol, session);
        return (retentionAnnotation == null || (retention = getRetention(retentionAnnotation)) == null) ? AnnotationRetention.RUNTIME : retention;
    }

    public static final AnnotationRetention getRetention(FirAnnotation firAnnotation) {
        FirReference calleeReference;
        FirEnumEntrySymbol resolvedEnumEntrySymbol$default;
        CallableId callableId;
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        FirExpression firExpressionFindArgumentByName = FirAnnotationUtilsKt.findArgumentByName(firAnnotation, StandardClassIds.Annotations.ParameterNames.INSTANCE.getRetentionValue());
        AnnotationRetention annotationRetention = null;
        FirQualifiedAccessExpression firQualifiedAccessExpression = firExpressionFindArgumentByName instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) firExpressionFindArgumentByName : null;
        if (firQualifiedAccessExpression == null || (calleeReference = firQualifiedAccessExpression.getCalleeReference()) == null || (resolvedEnumEntrySymbol$default = FirReferenceUtilsKt.toResolvedEnumEntrySymbol$default(calleeReference, false, 1, null)) == null || (callableId = resolvedEnumEntrySymbol$default.getCallableId()) == null || !Intrinsics.areEqual(callableId.getClassId(), StandardClassIds.INSTANCE.getAnnotationRetention())) {
            return null;
        }
        for (AnnotationRetention annotationRetention2 : EntriesMappings.entries$0) {
            if (Intrinsics.areEqual(annotationRetention2.name(), callableId.getCallableName().asString())) {
                annotationRetention = annotationRetention2;
                break;
            }
        }
        return annotationRetention;
    }

    public static final FirAnnotation getRetentionAnnotation(FirDeclaration firDeclaration, FirSession session) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return FirAnnotationUtilsKt.getAnnotationByClassId(firDeclaration, StandardClassIds.Annotations.INSTANCE.getRetention(), session);
    }

    private static final FirAnnotation getRetentionAnnotation(FirRegularClassSymbol firRegularClassSymbol, FirSession firSession) {
        return getAnnotationByClassId(firRegularClassSymbol, StandardClassIds.Annotations.INSTANCE.getRetention(), firSession);
    }

    private static final FirAnnotation getAnnotationByClassId(FirRegularClassSymbol firRegularClassSymbol, ClassId classId, FirSession firSession) {
        Object next;
        Iterator<T> it = firRegularClassSymbol.getResolvedAnnotationsWithArguments().iterator();
        while (it.hasNext()) {
            next = it.next();
            if (Intrinsics.areEqual(FirAnnotationUtilsKt.toAnnotationClassId((FirAnnotation) next, firSession), classId)) {
                return (FirAnnotation) next;
            }
        }
        next = null;
        return (FirAnnotation) next;
    }
}
