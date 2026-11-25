package org.jetbrains.kotlin.fir.declarations;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationLevelValue;
import org.jetbrains.kotlin.resolve.deprecation.SimpleDeprecationInfo;

/* JADX INFO: compiled from: DeprecationsProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/DeprecatedInfo;", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationAnnotationInfo;", FirebaseAnalytics.Param.LEVEL, "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", "propagatesToOverride", "", CrashHianalyticsData.MESSAGE, "", "(Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;ZLjava/lang/String;)V", "getLevel", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", "getMessage", "()Ljava/lang/String;", "getPropagatesToOverride", "()Z", "computeDeprecationInfo", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeprecatedInfo extends DeprecationAnnotationInfo {
    private final DeprecationLevelValue level;
    private final String message;
    private final boolean propagatesToOverride;

    public final DeprecationLevelValue getLevel() {
        return this.level;
    }

    public final boolean getPropagatesToOverride() {
        return this.propagatesToOverride;
    }

    public final String getMessage() {
        return this.message;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeprecatedInfo(DeprecationLevelValue level, boolean z, String str) {
        super(null);
        Intrinsics.checkNotNullParameter(level, "level");
        this.level = level;
        this.propagatesToOverride = z;
        this.message = str;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.DeprecationAnnotationInfo
    public DeprecationInfo computeDeprecationInfo(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        return new SimpleDeprecationInfo(this.level, this.propagatesToOverride, this.message);
    }
}
