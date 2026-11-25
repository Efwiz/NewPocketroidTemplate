package org.jetbrains.kotlin.fir.declarations;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirement;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationLevelValue;

/* JADX INFO: compiled from: DeprecationsProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u000e2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018HÖ\u0003J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J\t\u0010\u001b\u001a\u00020\nHÖ\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\t\u001a\u0004\u0018\u00010\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/RequireKotlinDeprecationInfo;", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;", "deprecationLevel", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", "versionRequirement", "Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement;", "(Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement;)V", "getDeprecationLevel", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", CrashHianalyticsData.MESSAGE, "", "getMessage", "()Ljava/lang/String;", "propagatesToOverrides", "", "getPropagatesToOverrides", "()Z", "getVersionRequirement", "()Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class RequireKotlinDeprecationInfo extends DeprecationInfo {
    private final DeprecationLevelValue deprecationLevel;
    private final VersionRequirement versionRequirement;

    public static /* synthetic */ RequireKotlinDeprecationInfo copy$default(RequireKotlinDeprecationInfo requireKotlinDeprecationInfo, DeprecationLevelValue deprecationLevelValue, VersionRequirement versionRequirement, int i, Object obj) {
        if ((i & 1) != 0) {
            deprecationLevelValue = requireKotlinDeprecationInfo.deprecationLevel;
        }
        if ((i & 2) != 0) {
            versionRequirement = requireKotlinDeprecationInfo.versionRequirement;
        }
        return requireKotlinDeprecationInfo.copy(deprecationLevelValue, versionRequirement);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final DeprecationLevelValue getDeprecationLevel() {
        return this.deprecationLevel;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final VersionRequirement getVersionRequirement() {
        return this.versionRequirement;
    }

    public final RequireKotlinDeprecationInfo copy(DeprecationLevelValue deprecationLevel, VersionRequirement versionRequirement) {
        Intrinsics.checkNotNullParameter(deprecationLevel, "deprecationLevel");
        Intrinsics.checkNotNullParameter(versionRequirement, "versionRequirement");
        return new RequireKotlinDeprecationInfo(deprecationLevel, versionRequirement);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RequireKotlinDeprecationInfo)) {
            return false;
        }
        RequireKotlinDeprecationInfo requireKotlinDeprecationInfo = (RequireKotlinDeprecationInfo) other;
        return this.deprecationLevel == requireKotlinDeprecationInfo.deprecationLevel && Intrinsics.areEqual(this.versionRequirement, requireKotlinDeprecationInfo.versionRequirement);
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
    public boolean getPropagatesToOverrides() {
        return false;
    }

    public int hashCode() {
        return (this.deprecationLevel.hashCode() * 31) + this.versionRequirement.hashCode();
    }

    public String toString() {
        return "RequireKotlinDeprecationInfo(deprecationLevel=" + this.deprecationLevel + ", versionRequirement=" + this.versionRequirement + ')';
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
    public DeprecationLevelValue getDeprecationLevel() {
        return this.deprecationLevel;
    }

    public final VersionRequirement getVersionRequirement() {
        return this.versionRequirement;
    }

    public RequireKotlinDeprecationInfo(DeprecationLevelValue deprecationLevel, VersionRequirement versionRequirement) {
        Intrinsics.checkNotNullParameter(deprecationLevel, "deprecationLevel");
        Intrinsics.checkNotNullParameter(versionRequirement, "versionRequirement");
        this.deprecationLevel = deprecationLevel;
        this.versionRequirement = versionRequirement;
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
    public String getMessage() {
        return this.versionRequirement.getMessage();
    }
}
