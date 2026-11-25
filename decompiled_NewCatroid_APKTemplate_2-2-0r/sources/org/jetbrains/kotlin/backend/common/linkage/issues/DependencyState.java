package org.jetbrains.kotlin.backend.common.linkage.issues;

import com.huawei.hms.mlsdk.common.internal.client.event.MonitorResult;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KotlinIrLinkerIssues.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0082\b\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0015\u0010\u000b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\b2\b\u0010\r\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\t¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/DependencyState;", "", "conflictReason", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/PotentialConflictReason;", "(Lorg/jetbrains/kotlin/backend/common/linkage/issues/PotentialConflictReason;)V", "getConflictReason", "()Lorg/jetbrains/kotlin/backend/common/linkage/issues/PotentialConflictReason;", "isConflicting", "", "()Z", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final /* data */ class DependencyState {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final DependencyState SUCCESS = new DependencyState(null);
    private final PotentialConflictReason conflictReason;

    public static /* synthetic */ DependencyState copy$default(DependencyState dependencyState, PotentialConflictReason potentialConflictReason, int i, Object obj) {
        if ((i & 1) != 0) {
            potentialConflictReason = dependencyState.conflictReason;
        }
        return dependencyState.copy(potentialConflictReason);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final PotentialConflictReason getConflictReason() {
        return this.conflictReason;
    }

    public final DependencyState copy(PotentialConflictReason conflictReason) {
        return new DependencyState(conflictReason);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof DependencyState) && Intrinsics.areEqual(this.conflictReason, ((DependencyState) other).conflictReason);
    }

    public int hashCode() {
        PotentialConflictReason potentialConflictReason = this.conflictReason;
        if (potentialConflictReason == null) {
            return 0;
        }
        return potentialConflictReason.hashCode();
    }

    public String toString() {
        return "DependencyState(conflictReason=" + this.conflictReason + ')';
    }

    public DependencyState(PotentialConflictReason potentialConflictReason) {
        this.conflictReason = potentialConflictReason;
    }

    public final PotentialConflictReason getConflictReason() {
        return this.conflictReason;
    }

    public final boolean isConflicting() {
        return this.conflictReason != null;
    }

    /* JADX INFO: compiled from: KotlinIrLinkerIssues.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/DependencyState$Companion;", "", "()V", MonitorResult.SUCCESS, "Lorg/jetbrains/kotlin/backend/common/linkage/issues/DependencyState;", "getSUCCESS", "()Lorg/jetbrains/kotlin/backend/common/linkage/issues/DependencyState;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final DependencyState getSUCCESS() {
            return DependencyState.SUCCESS;
        }
    }
}
