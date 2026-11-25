package org.jetbrains.kotlin.incremental;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: protoDifferenceUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B9\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006¢\u0006\u0002\u0010\nJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006HÆ\u0003J\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\t0\u0006HÆ\u0003J=\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00032\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0007HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\f¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/incremental/Difference;", "", "isClassAffected", "", "areSubclassesAffected", "changedMembersNames", "", "", "changedSupertypes", "Lorg/jetbrains/kotlin/name/FqName;", "(ZZLjava/util/Set;Ljava/util/Set;)V", "getAreSubclassesAffected", "()Z", "getChangedMembersNames", "()Ljava/util/Set;", "getChangedSupertypes", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class Difference {
    private final boolean areSubclassesAffected;
    private final Set<String> changedMembersNames;
    private final Set<FqName> changedSupertypes;
    private final boolean isClassAffected;

    public Difference() {
        this(false, false, null, null, 15, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Difference copy$default(Difference difference, boolean z, boolean z2, Set set, Set set2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = difference.isClassAffected;
        }
        if ((i & 2) != 0) {
            z2 = difference.areSubclassesAffected;
        }
        if ((i & 4) != 0) {
            set = difference.changedMembersNames;
        }
        if ((i & 8) != 0) {
            set2 = difference.changedSupertypes;
        }
        return difference.copy(z, z2, set, set2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final boolean getIsClassAffected() {
        return this.isClassAffected;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getAreSubclassesAffected() {
        return this.areSubclassesAffected;
    }

    public final Set<String> component3() {
        return this.changedMembersNames;
    }

    public final Set<FqName> component4() {
        return this.changedSupertypes;
    }

    public final Difference copy(boolean isClassAffected, boolean areSubclassesAffected, Set<String> changedMembersNames, Set<FqName> changedSupertypes) {
        Intrinsics.checkNotNullParameter(changedMembersNames, "changedMembersNames");
        Intrinsics.checkNotNullParameter(changedSupertypes, "changedSupertypes");
        return new Difference(isClassAffected, areSubclassesAffected, changedMembersNames, changedSupertypes);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Difference)) {
            return false;
        }
        Difference difference = (Difference) other;
        return this.isClassAffected == difference.isClassAffected && this.areSubclassesAffected == difference.areSubclassesAffected && Intrinsics.areEqual(this.changedMembersNames, difference.changedMembersNames) && Intrinsics.areEqual(this.changedSupertypes, difference.changedSupertypes);
    }

    public int hashCode() {
        return (((((Boolean.hashCode(this.isClassAffected) * 31) + Boolean.hashCode(this.areSubclassesAffected)) * 31) + this.changedMembersNames.hashCode()) * 31) + this.changedSupertypes.hashCode();
    }

    public String toString() {
        return "Difference(isClassAffected=" + this.isClassAffected + ", areSubclassesAffected=" + this.areSubclassesAffected + ", changedMembersNames=" + this.changedMembersNames + ", changedSupertypes=" + this.changedSupertypes + ')';
    }

    public Difference(boolean z, boolean z2, Set<String> changedMembersNames, Set<FqName> changedSupertypes) {
        Intrinsics.checkNotNullParameter(changedMembersNames, "changedMembersNames");
        Intrinsics.checkNotNullParameter(changedSupertypes, "changedSupertypes");
        this.isClassAffected = z;
        this.areSubclassesAffected = z2;
        this.changedMembersNames = changedMembersNames;
        this.changedSupertypes = changedSupertypes;
    }

    public final boolean isClassAffected() {
        return this.isClassAffected;
    }

    public final boolean getAreSubclassesAffected() {
        return this.areSubclassesAffected;
    }

    public /* synthetic */ Difference(boolean z, boolean z2, Set set, Set set2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2, (i & 4) != 0 ? SetsKt.emptySet() : set, (i & 8) != 0 ? SetsKt.emptySet() : set2);
    }

    public final Set<String> getChangedMembersNames() {
        return this.changedMembersNames;
    }

    public final Set<FqName> getChangedSupertypes() {
        return this.changedSupertypes;
    }
}
