package org.jetbrains.kotlin.resolve;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;

/* JADX INFO: compiled from: AnnotationTargetLists.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/UseSiteTargetsList;", "", "()V", "T_CONSTRUCTOR_PARAMETER", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "getT_CONSTRUCTOR_PARAMETER", "()Ljava/util/List;", "T_PROPERTY", "getT_PROPERTY", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UseSiteTargetsList {
    public static final UseSiteTargetsList INSTANCE = new UseSiteTargetsList();
    private static final List<AnnotationUseSiteTarget> T_CONSTRUCTOR_PARAMETER = CollectionsKt.listOf((Object[]) new AnnotationUseSiteTarget[]{AnnotationUseSiteTarget.CONSTRUCTOR_PARAMETER, AnnotationUseSiteTarget.PROPERTY, AnnotationUseSiteTarget.FIELD});
    private static final List<AnnotationUseSiteTarget> T_PROPERTY = CollectionsKt.listOf((Object[]) new AnnotationUseSiteTarget[]{AnnotationUseSiteTarget.PROPERTY, AnnotationUseSiteTarget.FIELD});

    private UseSiteTargetsList() {
    }

    public final List<AnnotationUseSiteTarget> getT_CONSTRUCTOR_PARAMETER() {
        return T_CONSTRUCTOR_PARAMETER;
    }

    public final List<AnnotationUseSiteTarget> getT_PROPERTY() {
        return T_PROPERTY;
    }
}
