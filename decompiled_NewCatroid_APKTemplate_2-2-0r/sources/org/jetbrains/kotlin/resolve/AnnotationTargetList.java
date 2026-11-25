package org.jetbrains.kotlin.resolve;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;

/* JADX INFO: compiled from: AnnotationTargetLists.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B3\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/AnnotationTargetList;", "", "defaultTargets", "", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "canBeSubstituted", "onlyWithUseSiteTarget", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getCanBeSubstituted", "()Ljava/util/List;", "getDefaultTargets", "getOnlyWithUseSiteTarget", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationTargetList {
    private final List<KotlinTarget> canBeSubstituted;
    private final List<KotlinTarget> defaultTargets;
    private final List<KotlinTarget> onlyWithUseSiteTarget;

    /* JADX WARN: Multi-variable type inference failed */
    public AnnotationTargetList(List<? extends KotlinTarget> defaultTargets, List<? extends KotlinTarget> canBeSubstituted, List<? extends KotlinTarget> onlyWithUseSiteTarget) {
        Intrinsics.checkNotNullParameter(defaultTargets, "defaultTargets");
        Intrinsics.checkNotNullParameter(canBeSubstituted, "canBeSubstituted");
        Intrinsics.checkNotNullParameter(onlyWithUseSiteTarget, "onlyWithUseSiteTarget");
        this.defaultTargets = defaultTargets;
        this.canBeSubstituted = canBeSubstituted;
        this.onlyWithUseSiteTarget = onlyWithUseSiteTarget;
    }

    public final List<KotlinTarget> getDefaultTargets() {
        return this.defaultTargets;
    }

    public /* synthetic */ AnnotationTargetList(List list, List list2, List list3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, (i & 2) != 0 ? CollectionsKt.emptyList() : list2, (i & 4) != 0 ? CollectionsKt.emptyList() : list3);
    }

    public final List<KotlinTarget> getCanBeSubstituted() {
        return this.canBeSubstituted;
    }

    public final List<KotlinTarget> getOnlyWithUseSiteTarget() {
        return this.onlyWithUseSiteTarget;
    }
}
