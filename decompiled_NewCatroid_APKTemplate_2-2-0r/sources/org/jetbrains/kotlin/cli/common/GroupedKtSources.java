package org.jetbrains.kotlin.cli.common;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: GroupedKtSources.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\"\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0018\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\t0\u0007¢\u0006\u0002\u0010\nJ\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u001b\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\t0\u0007HÆ\u0003JE\u0010\u0013\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u001a\b\u0002\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\t0\u0007HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\bHÖ\u0001R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR#\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\t0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/GroupedKtSources;", "", "platformSources", "", "Lorg/jetbrains/kotlin/KtSourceFile;", ModuleXmlParser.COMMON_SOURCES, "sourcesByModuleName", "", "", "", "(Ljava/util/Collection;Ljava/util/Collection;Ljava/util/Map;)V", "getCommonSources", "()Ljava/util/Collection;", "getPlatformSources", "getSourcesByModuleName", "()Ljava/util/Map;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class GroupedKtSources {
    private final Collection<KtSourceFile> commonSources;
    private final Collection<KtSourceFile> platformSources;
    private final Map<String, Set<KtSourceFile>> sourcesByModuleName;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ GroupedKtSources copy$default(GroupedKtSources groupedKtSources, Collection collection, Collection collection2, Map map, int i, Object obj) {
        if ((i & 1) != 0) {
            collection = groupedKtSources.platformSources;
        }
        if ((i & 2) != 0) {
            collection2 = groupedKtSources.commonSources;
        }
        if ((i & 4) != 0) {
            map = groupedKtSources.sourcesByModuleName;
        }
        return groupedKtSources.copy(collection, collection2, map);
    }

    public final Collection<KtSourceFile> component1() {
        return this.platformSources;
    }

    public final Collection<KtSourceFile> component2() {
        return this.commonSources;
    }

    public final Map<String, Set<KtSourceFile>> component3() {
        return this.sourcesByModuleName;
    }

    public final GroupedKtSources copy(Collection<? extends KtSourceFile> platformSources, Collection<? extends KtSourceFile> commonSources, Map<String, ? extends Set<? extends KtSourceFile>> sourcesByModuleName) {
        Intrinsics.checkNotNullParameter(platformSources, "platformSources");
        Intrinsics.checkNotNullParameter(commonSources, "commonSources");
        Intrinsics.checkNotNullParameter(sourcesByModuleName, "sourcesByModuleName");
        return new GroupedKtSources(platformSources, commonSources, sourcesByModuleName);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GroupedKtSources)) {
            return false;
        }
        GroupedKtSources groupedKtSources = (GroupedKtSources) other;
        return Intrinsics.areEqual(this.platformSources, groupedKtSources.platformSources) && Intrinsics.areEqual(this.commonSources, groupedKtSources.commonSources) && Intrinsics.areEqual(this.sourcesByModuleName, groupedKtSources.sourcesByModuleName);
    }

    public int hashCode() {
        return (((this.platformSources.hashCode() * 31) + this.commonSources.hashCode()) * 31) + this.sourcesByModuleName.hashCode();
    }

    public String toString() {
        return "GroupedKtSources(platformSources=" + this.platformSources + ", commonSources=" + this.commonSources + ", sourcesByModuleName=" + this.sourcesByModuleName + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public GroupedKtSources(Collection<? extends KtSourceFile> platformSources, Collection<? extends KtSourceFile> commonSources, Map<String, ? extends Set<? extends KtSourceFile>> sourcesByModuleName) {
        Intrinsics.checkNotNullParameter(platformSources, "platformSources");
        Intrinsics.checkNotNullParameter(commonSources, "commonSources");
        Intrinsics.checkNotNullParameter(sourcesByModuleName, "sourcesByModuleName");
        this.platformSources = platformSources;
        this.commonSources = commonSources;
        this.sourcesByModuleName = sourcesByModuleName;
    }

    public final Collection<KtSourceFile> getPlatformSources() {
        return this.platformSources;
    }

    public final Collection<KtSourceFile> getCommonSources() {
        return this.commonSources;
    }

    public final Map<String, Set<KtSourceFile>> getSourcesByModuleName() {
        return this.sourcesByModuleName;
    }
}
