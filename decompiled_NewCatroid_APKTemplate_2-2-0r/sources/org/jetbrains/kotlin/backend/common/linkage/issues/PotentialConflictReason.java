package org.jetbrains.kotlin.backend.common.linkage.issues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.ResolvedDependencyId;
import org.jetbrains.kotlin.utils.ResolvedDependencyVersion;

/* JADX INFO: compiled from: KotlinIrLinkerIssues.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0002\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000f¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/PotentialConflictReason;", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/backend/common/linkage/issues/PotentialConflictKind;", "conflictingModuleId", "Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;", "requestedVersion", "Lorg/jetbrains/kotlin/utils/ResolvedDependencyVersion;", "selectedVersion", "(Lorg/jetbrains/kotlin/backend/common/linkage/issues/PotentialConflictKind;Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;Lorg/jetbrains/kotlin/utils/ResolvedDependencyVersion;Lorg/jetbrains/kotlin/utils/ResolvedDependencyVersion;)V", "getConflictingModuleId", "()Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;", "getKind", "()Lorg/jetbrains/kotlin/backend/common/linkage/issues/PotentialConflictKind;", "getRequestedVersion", "()Lorg/jetbrains/kotlin/utils/ResolvedDependencyVersion;", "getSelectedVersion", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class PotentialConflictReason {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ResolvedDependencyId conflictingModuleId;
    private final PotentialConflictKind kind;
    private final ResolvedDependencyVersion requestedVersion;
    private final ResolvedDependencyVersion selectedVersion;

    public PotentialConflictReason(PotentialConflictKind kind, ResolvedDependencyId conflictingModuleId, ResolvedDependencyVersion requestedVersion, ResolvedDependencyVersion selectedVersion) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(conflictingModuleId, "conflictingModuleId");
        Intrinsics.checkNotNullParameter(requestedVersion, "requestedVersion");
        Intrinsics.checkNotNullParameter(selectedVersion, "selectedVersion");
        this.kind = kind;
        this.conflictingModuleId = conflictingModuleId;
        this.requestedVersion = requestedVersion;
        this.selectedVersion = selectedVersion;
    }

    public final PotentialConflictKind getKind() {
        return this.kind;
    }

    public final ResolvedDependencyId getConflictingModuleId() {
        return this.conflictingModuleId;
    }

    public /* synthetic */ PotentialConflictReason(PotentialConflictKind potentialConflictKind, ResolvedDependencyId resolvedDependencyId, ResolvedDependencyVersion resolvedDependencyVersion, ResolvedDependencyVersion resolvedDependencyVersion2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(potentialConflictKind, resolvedDependencyId, (i & 4) != 0 ? ResolvedDependencyVersion.INSTANCE.getEMPTY() : resolvedDependencyVersion, (i & 8) != 0 ? ResolvedDependencyVersion.INSTANCE.getEMPTY() : resolvedDependencyVersion2);
    }

    public final ResolvedDependencyVersion getRequestedVersion() {
        return this.requestedVersion;
    }

    public final ResolvedDependencyVersion getSelectedVersion() {
        return this.selectedVersion;
    }

    public boolean equals(Object other) {
        if (other instanceof PotentialConflictReason) {
            PotentialConflictReason potentialConflictReason = (PotentialConflictReason) other;
            if (potentialConflictReason.kind == this.kind && Intrinsics.areEqual(potentialConflictReason.conflictingModuleId, this.conflictingModuleId)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.kind.hashCode() + (this.conflictingModuleId.hashCode() * 31);
    }

    /* JADX INFO: compiled from: KotlinIrLinkerIssues.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R!\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004*\b\u0012\u0004\u0012\u00020\u00050\u00048F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/PotentialConflictReason$Companion;", "", "()V", "mostSignificantConflictReasons", "", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/PotentialConflictReason;", "getMostSignificantConflictReasons", "(Ljava/util/Collection;)Ljava/util/Collection;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Collection<PotentialConflictReason> getMostSignificantConflictReasons(Collection<PotentialConflictReason> collection) {
            Intrinsics.checkNotNullParameter(collection, "<this>");
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj : collection) {
                PotentialConflictKind kind = ((PotentialConflictReason) obj).getKind();
                Object obj2 = linkedHashMap.get(kind);
                if (obj2 == null) {
                    obj2 = (List) new ArrayList();
                    linkedHashMap.put(kind, obj2);
                }
                ((List) obj2).add(obj);
            }
            PotentialConflictKind mostSignificantConflictKind = PotentialConflictKind.INSTANCE.getMostSignificantConflictKind(linkedHashMap.keySet());
            return mostSignificantConflictKind == null ? CollectionsKt.emptyList() : (Collection) MapsKt.getValue(linkedHashMap, mostSignificantConflictKind);
        }
    }
}
