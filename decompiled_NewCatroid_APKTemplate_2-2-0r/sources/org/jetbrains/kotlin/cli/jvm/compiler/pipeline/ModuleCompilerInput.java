package org.jetbrains.kotlin.cli.jvm.compiler.pipeline;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.GroupedKtSources;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.modules.TargetId;
import org.jetbrains.kotlin.platform.TargetPlatform;

/* JADX INFO: compiled from: compilerPipelineData.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\u0002\u0010\u000eJ\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001e\u001a\u00020\nHÆ\u0003J\u000f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\r0\fHÆ\u0003JK\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fHÆ\u0001J\u0013\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010$\u001a\u00020%HÖ\u0001J\t\u0010&\u001a\u00020'HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/pipeline/ModuleCompilerInput;", "", "targetId", "Lorg/jetbrains/kotlin/modules/TargetId;", "groupedSources", "Lorg/jetbrains/kotlin/cli/common/GroupedKtSources;", "commonPlatform", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "friendFirModules", "", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "(Lorg/jetbrains/kotlin/modules/TargetId;Lorg/jetbrains/kotlin/cli/common/GroupedKtSources;Lorg/jetbrains/kotlin/platform/TargetPlatform;Lorg/jetbrains/kotlin/platform/TargetPlatform;Lorg/jetbrains/kotlin/config/CompilerConfiguration;Ljava/util/Collection;)V", "getCommonPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getConfiguration", "()Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "getFriendFirModules", "()Ljava/util/Collection;", "getGroupedSources", "()Lorg/jetbrains/kotlin/cli/common/GroupedKtSources;", "getPlatform", "getTargetId", "()Lorg/jetbrains/kotlin/modules/TargetId;", "component1", "component2", "component3", "component4", "component5", "component6", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ModuleCompilerInput {
    private final TargetPlatform commonPlatform;
    private final CompilerConfiguration configuration;
    private final Collection<FirModuleData> friendFirModules;
    private final GroupedKtSources groupedSources;
    private final TargetPlatform platform;
    private final TargetId targetId;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ModuleCompilerInput copy$default(ModuleCompilerInput moduleCompilerInput, TargetId targetId, GroupedKtSources groupedKtSources, TargetPlatform targetPlatform, TargetPlatform targetPlatform2, CompilerConfiguration compilerConfiguration, Collection collection, int i, Object obj) {
        if ((i & 1) != 0) {
            targetId = moduleCompilerInput.targetId;
        }
        if ((i & 2) != 0) {
            groupedKtSources = moduleCompilerInput.groupedSources;
        }
        GroupedKtSources groupedKtSources2 = groupedKtSources;
        if ((i & 4) != 0) {
            targetPlatform = moduleCompilerInput.commonPlatform;
        }
        TargetPlatform targetPlatform3 = targetPlatform;
        if ((i & 8) != 0) {
            targetPlatform2 = moduleCompilerInput.platform;
        }
        TargetPlatform targetPlatform4 = targetPlatform2;
        if ((i & 16) != 0) {
            compilerConfiguration = moduleCompilerInput.configuration;
        }
        CompilerConfiguration compilerConfiguration2 = compilerConfiguration;
        if ((i & 32) != 0) {
            collection = moduleCompilerInput.friendFirModules;
        }
        return moduleCompilerInput.copy(targetId, groupedKtSources2, targetPlatform3, targetPlatform4, compilerConfiguration2, collection);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final TargetId getTargetId() {
        return this.targetId;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final GroupedKtSources getGroupedSources() {
        return this.groupedSources;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final TargetPlatform getCommonPlatform() {
        return this.commonPlatform;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final TargetPlatform getPlatform() {
        return this.platform;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final CompilerConfiguration getConfiguration() {
        return this.configuration;
    }

    public final Collection<FirModuleData> component6() {
        return this.friendFirModules;
    }

    public final ModuleCompilerInput copy(TargetId targetId, GroupedKtSources groupedSources, TargetPlatform commonPlatform, TargetPlatform platform, CompilerConfiguration configuration, Collection<? extends FirModuleData> friendFirModules) {
        Intrinsics.checkNotNullParameter(targetId, "targetId");
        Intrinsics.checkNotNullParameter(groupedSources, "groupedSources");
        Intrinsics.checkNotNullParameter(commonPlatform, "commonPlatform");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(friendFirModules, "friendFirModules");
        return new ModuleCompilerInput(targetId, groupedSources, commonPlatform, platform, configuration, friendFirModules);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ModuleCompilerInput)) {
            return false;
        }
        ModuleCompilerInput moduleCompilerInput = (ModuleCompilerInput) other;
        return Intrinsics.areEqual(this.targetId, moduleCompilerInput.targetId) && Intrinsics.areEqual(this.groupedSources, moduleCompilerInput.groupedSources) && Intrinsics.areEqual(this.commonPlatform, moduleCompilerInput.commonPlatform) && Intrinsics.areEqual(this.platform, moduleCompilerInput.platform) && Intrinsics.areEqual(this.configuration, moduleCompilerInput.configuration) && Intrinsics.areEqual(this.friendFirModules, moduleCompilerInput.friendFirModules);
    }

    public int hashCode() {
        return (((((((((this.targetId.hashCode() * 31) + this.groupedSources.hashCode()) * 31) + this.commonPlatform.hashCode()) * 31) + this.platform.hashCode()) * 31) + this.configuration.hashCode()) * 31) + this.friendFirModules.hashCode();
    }

    public String toString() {
        return "ModuleCompilerInput(targetId=" + this.targetId + ", groupedSources=" + this.groupedSources + ", commonPlatform=" + this.commonPlatform + ", platform=" + this.platform + ", configuration=" + this.configuration + ", friendFirModules=" + this.friendFirModules + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ModuleCompilerInput(TargetId targetId, GroupedKtSources groupedSources, TargetPlatform commonPlatform, TargetPlatform platform, CompilerConfiguration configuration, Collection<? extends FirModuleData> friendFirModules) {
        Intrinsics.checkNotNullParameter(targetId, "targetId");
        Intrinsics.checkNotNullParameter(groupedSources, "groupedSources");
        Intrinsics.checkNotNullParameter(commonPlatform, "commonPlatform");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(friendFirModules, "friendFirModules");
        this.targetId = targetId;
        this.groupedSources = groupedSources;
        this.commonPlatform = commonPlatform;
        this.platform = platform;
        this.configuration = configuration;
        this.friendFirModules = friendFirModules;
    }

    public final TargetId getTargetId() {
        return this.targetId;
    }

    public final GroupedKtSources getGroupedSources() {
        return this.groupedSources;
    }

    public final TargetPlatform getCommonPlatform() {
        return this.commonPlatform;
    }

    public final TargetPlatform getPlatform() {
        return this.platform;
    }

    public final CompilerConfiguration getConfiguration() {
        return this.configuration;
    }

    public /* synthetic */ ModuleCompilerInput(TargetId targetId, GroupedKtSources groupedKtSources, TargetPlatform targetPlatform, TargetPlatform targetPlatform2, CompilerConfiguration compilerConfiguration, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(targetId, groupedKtSources, targetPlatform, targetPlatform2, compilerConfiguration, (i & 32) != 0 ? CollectionsKt.emptyList() : list);
    }

    public final Collection<FirModuleData> getFriendFirModules() {
        return this.friendFirModules;
    }
}
