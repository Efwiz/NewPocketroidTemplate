package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirAnnotationArgumentsResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0003"}, d2 = {"classIdsToCheck", "", "Lorg/jetbrains/kotlin/name/ClassId;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirAnnotationArgumentsResolveTransformerKt {
    private static final Set<ClassId> classIdsToCheck = SetsKt.setOf((Object[]) new ClassId[]{StandardClassIds.INSTANCE.getDeprecationLevel(), StandardClassIds.INSTANCE.getAnnotationTarget()});
}
