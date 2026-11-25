package org.jetbrains.kotlin.fir.declarations;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationLevelValue;
import org.jetbrains.kotlin.resolve.deprecation.SimpleDeprecationInfo;

/* JADX INFO: compiled from: DeprecationsProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B5\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\f¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/DeprecatedSinceKotlinInfo;", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationAnnotationInfo;", "warningVersion", "Lorg/jetbrains/kotlin/config/ApiVersion;", "errorVersion", "hiddenVersion", CrashHianalyticsData.MESSAGE, "", "propagatesToOverride", "", "(Lorg/jetbrains/kotlin/config/ApiVersion;Lorg/jetbrains/kotlin/config/ApiVersion;Lorg/jetbrains/kotlin/config/ApiVersion;Ljava/lang/String;Z)V", "getErrorVersion", "()Lorg/jetbrains/kotlin/config/ApiVersion;", "getHiddenVersion", "getMessage", "()Ljava/lang/String;", "getPropagatesToOverride", "()Z", "getWarningVersion", "computeDeprecationInfo", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeprecatedSinceKotlinInfo extends DeprecationAnnotationInfo {
    private final ApiVersion errorVersion;
    private final ApiVersion hiddenVersion;
    private final String message;
    private final boolean propagatesToOverride;
    private final ApiVersion warningVersion;

    public final ApiVersion getWarningVersion() {
        return this.warningVersion;
    }

    public final ApiVersion getErrorVersion() {
        return this.errorVersion;
    }

    public final ApiVersion getHiddenVersion() {
        return this.hiddenVersion;
    }

    public final String getMessage() {
        return this.message;
    }

    public final boolean getPropagatesToOverride() {
        return this.propagatesToOverride;
    }

    public DeprecatedSinceKotlinInfo(ApiVersion apiVersion, ApiVersion apiVersion2, ApiVersion apiVersion3, String str, boolean z) {
        super(null);
        this.warningVersion = apiVersion;
        this.errorVersion = apiVersion2;
        this.hiddenVersion = apiVersion3;
        this.message = str;
        this.propagatesToOverride = z;
    }

    private static final DeprecationLevelValue computeDeprecationInfo$takeLevelIfDeprecated(ApiVersion apiVersion, LanguageVersionSettings languageVersionSettings, DeprecationLevelValue deprecationLevelValue) {
        if (apiVersion.compareTo(languageVersionSettings.getApiVersion()) <= 0) {
            return deprecationLevelValue;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.DeprecationAnnotationInfo
    public DeprecationInfo computeDeprecationInfo(LanguageVersionSettings languageVersionSettings) {
        DeprecationLevelValue deprecationLevelValueComputeDeprecationInfo$takeLevelIfDeprecated;
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        ApiVersion apiVersion = this.hiddenVersion;
        if (apiVersion == null || (deprecationLevelValueComputeDeprecationInfo$takeLevelIfDeprecated = computeDeprecationInfo$takeLevelIfDeprecated(apiVersion, languageVersionSettings, DeprecationLevelValue.HIDDEN)) == null) {
            ApiVersion apiVersion2 = this.errorVersion;
            deprecationLevelValueComputeDeprecationInfo$takeLevelIfDeprecated = apiVersion2 != null ? computeDeprecationInfo$takeLevelIfDeprecated(apiVersion2, languageVersionSettings, DeprecationLevelValue.ERROR) : null;
            if (deprecationLevelValueComputeDeprecationInfo$takeLevelIfDeprecated == null) {
                ApiVersion apiVersion3 = this.warningVersion;
                deprecationLevelValueComputeDeprecationInfo$takeLevelIfDeprecated = apiVersion3 != null ? computeDeprecationInfo$takeLevelIfDeprecated(apiVersion3, languageVersionSettings, DeprecationLevelValue.WARNING) : null;
            }
        }
        return deprecationLevelValueComputeDeprecationInfo$takeLevelIfDeprecated != null ? new SimpleDeprecationInfo(deprecationLevelValueComputeDeprecationInfo$takeLevelIfDeprecated, this.propagatesToOverride, this.message) : null;
    }
}
