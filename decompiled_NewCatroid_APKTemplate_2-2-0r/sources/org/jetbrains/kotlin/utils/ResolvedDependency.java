package org.jetbrains.kotlin.utils;

import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ResolvedDependencies.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u000e\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00070\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0002\u0010\rJ\b\u0010 \u001a\u00020\u0013H\u0016R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00070\t¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001f¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/utils/ResolvedDependency;", "", "id", "Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;", "visibleAsFirstLevelDependency", "", "selectedVersion", "Lorg/jetbrains/kotlin/utils/ResolvedDependencyVersion;", "requestedVersionsByIncomingDependencies", "", "artifactPaths", "", "Lorg/jetbrains/kotlin/utils/ResolvedDependencyArtifactPath;", "(Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;ZLorg/jetbrains/kotlin/utils/ResolvedDependencyVersion;Ljava/util/Map;Ljava/util/Set;)V", "getArtifactPaths", "()Ljava/util/Set;", "getId", "()Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;", "moduleIdWithVersion", "", "getModuleIdWithVersion", "()Ljava/lang/String;", "getRequestedVersionsByIncomingDependencies", "()Ljava/util/Map;", "getSelectedVersion", "()Lorg/jetbrains/kotlin/utils/ResolvedDependencyVersion;", "setSelectedVersion", "(Lorg/jetbrains/kotlin/utils/ResolvedDependencyVersion;)V", "getVisibleAsFirstLevelDependency", "()Z", "setVisibleAsFirstLevelDependency", "(Z)V", "toString", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolvedDependency {
    private final Set<ResolvedDependencyArtifactPath> artifactPaths;
    private final ResolvedDependencyId id;
    private final Map<ResolvedDependencyId, ResolvedDependencyVersion> requestedVersionsByIncomingDependencies;
    private ResolvedDependencyVersion selectedVersion;
    private boolean visibleAsFirstLevelDependency;

    public ResolvedDependency(ResolvedDependencyId id2, boolean z, ResolvedDependencyVersion selectedVersion, Map<ResolvedDependencyId, ResolvedDependencyVersion> requestedVersionsByIncomingDependencies, Set<ResolvedDependencyArtifactPath> artifactPaths) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(selectedVersion, "selectedVersion");
        Intrinsics.checkNotNullParameter(requestedVersionsByIncomingDependencies, "requestedVersionsByIncomingDependencies");
        Intrinsics.checkNotNullParameter(artifactPaths, "artifactPaths");
        this.id = id2;
        this.visibleAsFirstLevelDependency = z;
        this.selectedVersion = selectedVersion;
        this.requestedVersionsByIncomingDependencies = requestedVersionsByIncomingDependencies;
        this.artifactPaths = artifactPaths;
    }

    public /* synthetic */ ResolvedDependency(ResolvedDependencyId resolvedDependencyId, boolean z, ResolvedDependencyVersion resolvedDependencyVersion, Map map, Set set, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(resolvedDependencyId, (i & 2) != 0 ? true : z, resolvedDependencyVersion, map, set);
    }

    public final ResolvedDependencyId getId() {
        return this.id;
    }

    public final boolean getVisibleAsFirstLevelDependency() {
        return this.visibleAsFirstLevelDependency;
    }

    public final void setVisibleAsFirstLevelDependency(boolean z) {
        this.visibleAsFirstLevelDependency = z;
    }

    public final ResolvedDependencyVersion getSelectedVersion() {
        return this.selectedVersion;
    }

    public final void setSelectedVersion(ResolvedDependencyVersion resolvedDependencyVersion) {
        Intrinsics.checkNotNullParameter(resolvedDependencyVersion, "<set-?>");
        this.selectedVersion = resolvedDependencyVersion;
    }

    public final Map<ResolvedDependencyId, ResolvedDependencyVersion> getRequestedVersionsByIncomingDependencies() {
        return this.requestedVersionsByIncomingDependencies;
    }

    public final Set<ResolvedDependencyArtifactPath> getArtifactPaths() {
        return this.artifactPaths;
    }

    public final String getModuleIdWithVersion() {
        return this.id.withVersion(this.selectedVersion);
    }

    public String toString() {
        return getModuleIdWithVersion();
    }
}
