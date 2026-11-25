package org.jetbrains.kotlin.fir.declarations;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationLevelValue;

/* JADX INFO: compiled from: DeprecationsProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0007HÆ\u0003J'\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00052\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\fHÖ\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FutureApiDeprecationInfo;", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;", "deprecationLevel", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", "propagatesToOverrides", "", "sinceVersion", "Lorg/jetbrains/kotlin/config/ApiVersion;", "(Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;ZLorg/jetbrains/kotlin/config/ApiVersion;)V", "getDeprecationLevel", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", CrashHianalyticsData.MESSAGE, "", "getMessage", "()Ljava/lang/String;", "getPropagatesToOverrides", "()Z", "getSinceVersion", "()Lorg/jetbrains/kotlin/config/ApiVersion;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class FutureApiDeprecationInfo extends DeprecationInfo {
    private final DeprecationLevelValue deprecationLevel;
    private final boolean propagatesToOverrides;
    private final ApiVersion sinceVersion;

    public static /* synthetic */ FutureApiDeprecationInfo copy$default(FutureApiDeprecationInfo futureApiDeprecationInfo, DeprecationLevelValue deprecationLevelValue, boolean z, ApiVersion apiVersion, int i, Object obj) {
        if ((i & 1) != 0) {
            deprecationLevelValue = futureApiDeprecationInfo.deprecationLevel;
        }
        if ((i & 2) != 0) {
            z = futureApiDeprecationInfo.propagatesToOverrides;
        }
        if ((i & 4) != 0) {
            apiVersion = futureApiDeprecationInfo.sinceVersion;
        }
        return futureApiDeprecationInfo.copy(deprecationLevelValue, z, apiVersion);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final DeprecationLevelValue getDeprecationLevel() {
        return this.deprecationLevel;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getPropagatesToOverrides() {
        return this.propagatesToOverrides;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final ApiVersion getSinceVersion() {
        return this.sinceVersion;
    }

    public final FutureApiDeprecationInfo copy(DeprecationLevelValue deprecationLevel, boolean propagatesToOverrides, ApiVersion sinceVersion) {
        Intrinsics.checkNotNullParameter(deprecationLevel, "deprecationLevel");
        Intrinsics.checkNotNullParameter(sinceVersion, "sinceVersion");
        return new FutureApiDeprecationInfo(deprecationLevel, propagatesToOverrides, sinceVersion);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FutureApiDeprecationInfo)) {
            return false;
        }
        FutureApiDeprecationInfo futureApiDeprecationInfo = (FutureApiDeprecationInfo) other;
        return this.deprecationLevel == futureApiDeprecationInfo.deprecationLevel && this.propagatesToOverrides == futureApiDeprecationInfo.propagatesToOverrides && Intrinsics.areEqual(this.sinceVersion, futureApiDeprecationInfo.sinceVersion);
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
    public String getMessage() {
        return null;
    }

    public int hashCode() {
        return (((this.deprecationLevel.hashCode() * 31) + Boolean.hashCode(this.propagatesToOverrides)) * 31) + this.sinceVersion.hashCode();
    }

    public String toString() {
        return "FutureApiDeprecationInfo(deprecationLevel=" + this.deprecationLevel + ", propagatesToOverrides=" + this.propagatesToOverrides + ", sinceVersion=" + this.sinceVersion + ')';
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
    public DeprecationLevelValue getDeprecationLevel() {
        return this.deprecationLevel;
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
    public boolean getPropagatesToOverrides() {
        return this.propagatesToOverrides;
    }

    public final ApiVersion getSinceVersion() {
        return this.sinceVersion;
    }

    public FutureApiDeprecationInfo(DeprecationLevelValue deprecationLevel, boolean z, ApiVersion sinceVersion) {
        Intrinsics.checkNotNullParameter(deprecationLevel, "deprecationLevel");
        Intrinsics.checkNotNullParameter(sinceVersion, "sinceVersion");
        this.deprecationLevel = deprecationLevel;
        this.propagatesToOverrides = z;
        this.sinceVersion = sinceVersion;
    }
}
