package org.jetbrains.kotlin.incremental;

import com.google.android.gms.cast.HlsSegmentFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: BuildDiffsStorage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0007HÆ\u0003J'\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00052\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/incremental/BuildDifference;", "", HlsSegmentFormat.TS, "", "isIncremental", "", "dirtyData", "Lorg/jetbrains/kotlin/incremental/DirtyData;", "(JZLorg/jetbrains/kotlin/incremental/DirtyData;)V", "getDirtyData", "()Lorg/jetbrains/kotlin/incremental/DirtyData;", "()Z", "getTs", "()J", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class BuildDifference {
    private final DirtyData dirtyData;
    private final boolean isIncremental;
    private final long ts;

    public static /* synthetic */ BuildDifference copy$default(BuildDifference buildDifference, long j, boolean z, DirtyData dirtyData, int i, Object obj) {
        if ((i & 1) != 0) {
            j = buildDifference.ts;
        }
        if ((i & 2) != 0) {
            z = buildDifference.isIncremental;
        }
        if ((i & 4) != 0) {
            dirtyData = buildDifference.dirtyData;
        }
        return buildDifference.copy(j, z, dirtyData);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final long getTs() {
        return this.ts;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getIsIncremental() {
        return this.isIncremental;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final DirtyData getDirtyData() {
        return this.dirtyData;
    }

    public final BuildDifference copy(long ts, boolean isIncremental, DirtyData dirtyData) {
        Intrinsics.checkNotNullParameter(dirtyData, "dirtyData");
        return new BuildDifference(ts, isIncremental, dirtyData);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BuildDifference)) {
            return false;
        }
        BuildDifference buildDifference = (BuildDifference) other;
        return this.ts == buildDifference.ts && this.isIncremental == buildDifference.isIncremental && Intrinsics.areEqual(this.dirtyData, buildDifference.dirtyData);
    }

    public int hashCode() {
        return (((Long.hashCode(this.ts) * 31) + Boolean.hashCode(this.isIncremental)) * 31) + this.dirtyData.hashCode();
    }

    public String toString() {
        return "BuildDifference(ts=" + this.ts + ", isIncremental=" + this.isIncremental + ", dirtyData=" + this.dirtyData + ')';
    }

    public BuildDifference(long j, boolean z, DirtyData dirtyData) {
        Intrinsics.checkNotNullParameter(dirtyData, "dirtyData");
        this.ts = j;
        this.isIncremental = z;
        this.dirtyData = dirtyData;
    }

    public final DirtyData getDirtyData() {
        return this.dirtyData;
    }

    public final long getTs() {
        return this.ts;
    }

    public final boolean isIncremental() {
        return this.isIncremental;
    }
}
