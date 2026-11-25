package org.jetbrains.kotlin.ir.linkage.partial;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: PartialLinkageConfig.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\b2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageConfig;", "", "mode", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageMode;", "logLevel", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageLogLevel;", "(Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageMode;Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageLogLevel;)V", "isEnabled", "", "()Z", "getLogLevel", "()Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageLogLevel;", "getMode", "()Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageMode;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "Companion", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class PartialLinkageConfig {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final PartialLinkageConfig DEFAULT = new PartialLinkageConfig(PartialLinkageMode.DISABLE, PartialLinkageLogLevel.ERROR);
    private static final CompilerConfigurationKey<PartialLinkageConfig> KEY;
    private final PartialLinkageLogLevel logLevel;
    private final PartialLinkageMode mode;

    public static /* synthetic */ PartialLinkageConfig copy$default(PartialLinkageConfig partialLinkageConfig, PartialLinkageMode partialLinkageMode, PartialLinkageLogLevel partialLinkageLogLevel, int i, Object obj) {
        if ((i & 1) != 0) {
            partialLinkageMode = partialLinkageConfig.mode;
        }
        if ((i & 2) != 0) {
            partialLinkageLogLevel = partialLinkageConfig.logLevel;
        }
        return partialLinkageConfig.copy(partialLinkageMode, partialLinkageLogLevel);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final PartialLinkageMode getMode() {
        return this.mode;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final PartialLinkageLogLevel getLogLevel() {
        return this.logLevel;
    }

    public final PartialLinkageConfig copy(PartialLinkageMode mode, PartialLinkageLogLevel logLevel) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(logLevel, "logLevel");
        return new PartialLinkageConfig(mode, logLevel);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PartialLinkageConfig)) {
            return false;
        }
        PartialLinkageConfig partialLinkageConfig = (PartialLinkageConfig) other;
        return this.mode == partialLinkageConfig.mode && this.logLevel == partialLinkageConfig.logLevel;
    }

    public int hashCode() {
        return (this.mode.hashCode() * 31) + this.logLevel.hashCode();
    }

    public String toString() {
        return "PartialLinkageConfig(mode=" + this.mode + ", logLevel=" + this.logLevel + ')';
    }

    public PartialLinkageConfig(PartialLinkageMode mode, PartialLinkageLogLevel logLevel) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(logLevel, "logLevel");
        this.mode = mode;
        this.logLevel = logLevel;
    }

    public final PartialLinkageLogLevel getLogLevel() {
        return this.logLevel;
    }

    public final PartialLinkageMode getMode() {
        return this.mode;
    }

    public final boolean isEnabled() {
        return this.mode.getIsEnabled();
    }

    /* JADX INFO: compiled from: PartialLinkageConfig.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001f\u0010\u0007\u001a\u0010\u0012\f\u0012\n \t*\u0004\u0018\u00010\u00040\u00040\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageConfig$Companion;", "", "()V", "DEFAULT", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageConfig;", "getDEFAULT", "()Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageConfig;", "KEY", "Lorg/jetbrains/kotlin/config/CompilerConfigurationKey;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getKEY", "()Lorg/jetbrains/kotlin/config/CompilerConfigurationKey;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final PartialLinkageConfig getDEFAULT() {
            return PartialLinkageConfig.DEFAULT;
        }

        public final CompilerConfigurationKey<PartialLinkageConfig> getKEY() {
            return PartialLinkageConfig.KEY;
        }
    }

    static {
        CompilerConfigurationKey<PartialLinkageConfig> compilerConfigurationKeyCreate = CompilerConfigurationKey.create("partial linkage configuration");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate, "create(...)");
        KEY = compilerConfigurationKeyCreate;
    }
}
