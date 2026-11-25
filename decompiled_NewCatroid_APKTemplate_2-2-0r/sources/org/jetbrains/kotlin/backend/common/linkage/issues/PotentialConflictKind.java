package org.jetbrains.kotlin.backend.common.linkage.issues;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KotlinIrLinkerIssues.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0082\u0081\u0002\u0018\u0000 \u00062\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0006B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/PotentialConflictKind;", "", "(Ljava/lang/String;I)V", "UNKNOWN_SELECTED_VERSION", "REQUESTED_SELECTED_VERSIONS_MISMATCH", "BEHIND_CONFLICTING_DEPENDENCY", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
enum PotentialConflictKind {
    UNKNOWN_SELECTED_VERSION,
    REQUESTED_SELECTED_VERSIONS_MISMATCH,
    BEHIND_CONFLICTING_DEPENDENCY;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public static EnumEntries<PotentialConflictKind> getEntries() {
        return $ENTRIES;
    }

    /* JADX INFO: compiled from: KotlinIrLinkerIssues.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\b\u0012\u0004\u0012\u00020\u00040\u00058F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/PotentialConflictKind$Companion;", "", "()V", "mostSignificantConflictKind", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/PotentialConflictKind;", "", "getMostSignificantConflictKind", "(Ljava/util/Collection;)Lorg/jetbrains/kotlin/backend/common/linkage/issues/PotentialConflictKind;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final PotentialConflictKind getMostSignificantConflictKind(Collection<? extends PotentialConflictKind> collection) {
            Intrinsics.checkNotNullParameter(collection, "<this>");
            return (PotentialConflictKind) CollectionsKt.minOrNull((Iterable) collection);
        }
    }
}
