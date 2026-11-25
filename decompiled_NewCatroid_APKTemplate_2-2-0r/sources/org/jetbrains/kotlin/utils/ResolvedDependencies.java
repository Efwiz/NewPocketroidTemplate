package org.jetbrains.kotlin.utils;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ResolvedDependencies.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0006HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/utils/ResolvedDependencies;", "", "modules", "", "Lorg/jetbrains/kotlin/utils/ResolvedDependency;", "sourceCodeModuleId", "Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;", "(Ljava/util/Collection;Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;)V", "getModules", "()Ljava/util/Collection;", "getSourceCodeModuleId", "()Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "Companion", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ResolvedDependencies {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ResolvedDependencies EMPTY = new ResolvedDependencies(kotlin.collections.CollectionsKt.emptyList(), ResolvedDependencyId.INSTANCE.getDEFAULT_SOURCE_CODE_MODULE_ID());
    private final Collection<ResolvedDependency> modules;
    private final ResolvedDependencyId sourceCodeModuleId;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ResolvedDependencies copy$default(ResolvedDependencies resolvedDependencies, Collection collection, ResolvedDependencyId resolvedDependencyId, int i, Object obj) {
        if ((i & 1) != 0) {
            collection = resolvedDependencies.modules;
        }
        if ((i & 2) != 0) {
            resolvedDependencyId = resolvedDependencies.sourceCodeModuleId;
        }
        return resolvedDependencies.copy(collection, resolvedDependencyId);
    }

    public final Collection<ResolvedDependency> component1() {
        return this.modules;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ResolvedDependencyId getSourceCodeModuleId() {
        return this.sourceCodeModuleId;
    }

    public final ResolvedDependencies copy(Collection<ResolvedDependency> modules, ResolvedDependencyId sourceCodeModuleId) {
        Intrinsics.checkNotNullParameter(modules, "modules");
        Intrinsics.checkNotNullParameter(sourceCodeModuleId, "sourceCodeModuleId");
        return new ResolvedDependencies(modules, sourceCodeModuleId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ResolvedDependencies)) {
            return false;
        }
        ResolvedDependencies resolvedDependencies = (ResolvedDependencies) other;
        return Intrinsics.areEqual(this.modules, resolvedDependencies.modules) && Intrinsics.areEqual(this.sourceCodeModuleId, resolvedDependencies.sourceCodeModuleId);
    }

    public int hashCode() {
        return (this.modules.hashCode() * 31) + this.sourceCodeModuleId.hashCode();
    }

    public String toString() {
        return "ResolvedDependencies(modules=" + this.modules + ", sourceCodeModuleId=" + this.sourceCodeModuleId + ')';
    }

    public ResolvedDependencies(Collection<ResolvedDependency> modules, ResolvedDependencyId sourceCodeModuleId) {
        Intrinsics.checkNotNullParameter(modules, "modules");
        Intrinsics.checkNotNullParameter(sourceCodeModuleId, "sourceCodeModuleId");
        this.modules = modules;
        this.sourceCodeModuleId = sourceCodeModuleId;
    }

    public final Collection<ResolvedDependency> getModules() {
        return this.modules;
    }

    public final ResolvedDependencyId getSourceCodeModuleId() {
        return this.sourceCodeModuleId;
    }

    /* JADX INFO: compiled from: ResolvedDependencies.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/utils/ResolvedDependencies$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/utils/ResolvedDependencies;", "getEMPTY", "()Lorg/jetbrains/kotlin/utils/ResolvedDependencies;", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ResolvedDependencies getEMPTY() {
            return ResolvedDependencies.EMPTY;
        }
    }
}
