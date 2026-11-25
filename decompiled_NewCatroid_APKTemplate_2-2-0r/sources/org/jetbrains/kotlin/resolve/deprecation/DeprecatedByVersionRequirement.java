package org.jetbrains.kotlin.resolve.deprecation;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirement;

/* JADX INFO: compiled from: Deprecation.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0080\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\fHÖ\u0001R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/resolve/deprecation/DeprecatedByVersionRequirement;", "Lorg/jetbrains/kotlin/resolve/deprecation/DescriptorBasedDeprecationInfo;", "versionRequirement", "Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement;", "target", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "(Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)V", "deprecationLevel", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", "getDeprecationLevel", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", CrashHianalyticsData.MESSAGE, "", "getMessage", "()Ljava/lang/String;", "getTarget", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getVersionRequirement", "()Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class DeprecatedByVersionRequirement extends DescriptorBasedDeprecationInfo {
    private final DeclarationDescriptor target;
    private final VersionRequirement versionRequirement;

    /* JADX INFO: compiled from: Deprecation.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DeprecationLevel.values().length];
            try {
                iArr[DeprecationLevel.WARNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DeprecationLevel.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DeprecationLevel.HIDDEN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static /* synthetic */ DeprecatedByVersionRequirement copy$default(DeprecatedByVersionRequirement deprecatedByVersionRequirement, VersionRequirement versionRequirement, DeclarationDescriptor declarationDescriptor, int i, Object obj) {
        if ((i & 1) != 0) {
            versionRequirement = deprecatedByVersionRequirement.versionRequirement;
        }
        if ((i & 2) != 0) {
            declarationDescriptor = deprecatedByVersionRequirement.target;
        }
        return deprecatedByVersionRequirement.copy(versionRequirement, declarationDescriptor);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final VersionRequirement getVersionRequirement() {
        return this.versionRequirement;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final DeclarationDescriptor getTarget() {
        return this.target;
    }

    public final DeprecatedByVersionRequirement copy(VersionRequirement versionRequirement, DeclarationDescriptor target) {
        Intrinsics.checkNotNullParameter(versionRequirement, "versionRequirement");
        Intrinsics.checkNotNullParameter(target, "target");
        return new DeprecatedByVersionRequirement(versionRequirement, target);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeprecatedByVersionRequirement)) {
            return false;
        }
        DeprecatedByVersionRequirement deprecatedByVersionRequirement = (DeprecatedByVersionRequirement) other;
        return Intrinsics.areEqual(this.versionRequirement, deprecatedByVersionRequirement.versionRequirement) && Intrinsics.areEqual(this.target, deprecatedByVersionRequirement.target);
    }

    public int hashCode() {
        return (this.versionRequirement.hashCode() * 31) + this.target.hashCode();
    }

    public String toString() {
        return "DeprecatedByVersionRequirement(versionRequirement=" + this.versionRequirement + ", target=" + this.target + ')';
    }

    public final VersionRequirement getVersionRequirement() {
        return this.versionRequirement;
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DescriptorBasedDeprecationInfo
    public DeclarationDescriptor getTarget() {
        return this.target;
    }

    public DeprecatedByVersionRequirement(VersionRequirement versionRequirement, DeclarationDescriptor target) {
        Intrinsics.checkNotNullParameter(versionRequirement, "versionRequirement");
        Intrinsics.checkNotNullParameter(target, "target");
        this.versionRequirement = versionRequirement;
        this.target = target;
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
    public DeprecationLevelValue getDeprecationLevel() {
        int i = WhenMappings.$EnumSwitchMapping$0[this.versionRequirement.getLevel().ordinal()];
        if (i == 1) {
            return DeprecationLevelValue.WARNING;
        }
        if (i == 2) {
            return DeprecationLevelValue.ERROR;
        }
        if (i == 3) {
            return DeprecationLevelValue.HIDDEN;
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
    public String getMessage() {
        String message = this.versionRequirement.getMessage();
        Integer errorCode = this.versionRequirement.getErrorCode();
        if (message == null && errorCode == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (message != null) {
            sb.append(message);
            if (errorCode != null) {
                sb.append(" (error code " + errorCode + ')');
            }
        } else {
            sb.append("Error code " + errorCode);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
