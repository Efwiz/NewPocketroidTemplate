package org.jetbrains.kotlin.resolve.deprecation;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: DeprecationInfo.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0007HÆ\u0003J)\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00052\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0007HÖ\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/resolve/deprecation/SimpleDeprecationInfo;", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;", "deprecationLevel", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", "propagatesToOverrides", "", CrashHianalyticsData.MESSAGE, "", "(Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;ZLjava/lang/String;)V", "getDeprecationLevel", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", "getMessage", "()Ljava/lang/String;", "getPropagatesToOverrides", "()Z", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SimpleDeprecationInfo extends DeprecationInfo {
    private final DeprecationLevelValue deprecationLevel;
    private final String message;
    private final boolean propagatesToOverrides;

    public static /* synthetic */ SimpleDeprecationInfo copy$default(SimpleDeprecationInfo simpleDeprecationInfo, DeprecationLevelValue deprecationLevelValue, boolean z, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            deprecationLevelValue = simpleDeprecationInfo.deprecationLevel;
        }
        if ((i & 2) != 0) {
            z = simpleDeprecationInfo.propagatesToOverrides;
        }
        if ((i & 4) != 0) {
            str = simpleDeprecationInfo.message;
        }
        return simpleDeprecationInfo.copy(deprecationLevelValue, z, str);
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
    public final String getMessage() {
        return this.message;
    }

    public final SimpleDeprecationInfo copy(DeprecationLevelValue deprecationLevel, boolean propagatesToOverrides, String message) {
        Intrinsics.checkNotNullParameter(deprecationLevel, "deprecationLevel");
        return new SimpleDeprecationInfo(deprecationLevel, propagatesToOverrides, message);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SimpleDeprecationInfo)) {
            return false;
        }
        SimpleDeprecationInfo simpleDeprecationInfo = (SimpleDeprecationInfo) other;
        return this.deprecationLevel == simpleDeprecationInfo.deprecationLevel && this.propagatesToOverrides == simpleDeprecationInfo.propagatesToOverrides && Intrinsics.areEqual(this.message, simpleDeprecationInfo.message);
    }

    public int hashCode() {
        int iHashCode = ((this.deprecationLevel.hashCode() * 31) + Boolean.hashCode(this.propagatesToOverrides)) * 31;
        String str = this.message;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        return "SimpleDeprecationInfo(deprecationLevel=" + this.deprecationLevel + ", propagatesToOverrides=" + this.propagatesToOverrides + ", message=" + this.message + ')';
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
    public DeprecationLevelValue getDeprecationLevel() {
        return this.deprecationLevel;
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
    public boolean getPropagatesToOverrides() {
        return this.propagatesToOverrides;
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
    public String getMessage() {
        return this.message;
    }

    public SimpleDeprecationInfo(DeprecationLevelValue deprecationLevel, boolean z, String str) {
        Intrinsics.checkNotNullParameter(deprecationLevel, "deprecationLevel");
        this.deprecationLevel = deprecationLevel;
        this.propagatesToOverrides = z;
        this.message = str;
    }
}
