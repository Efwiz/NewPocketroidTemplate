package org.jetbrains.kotlin.fir.declarations;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationLevelValue;

/* JADX INFO: compiled from: DeprecationsProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/SinceKotlinInfo;", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationAnnotationInfo;", "sinceVersion", "Lorg/jetbrains/kotlin/config/ApiVersion;", "(Lorg/jetbrains/kotlin/config/ApiVersion;)V", "getSinceVersion", "()Lorg/jetbrains/kotlin/config/ApiVersion;", "computeDeprecationInfo", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SinceKotlinInfo extends DeprecationAnnotationInfo {
    private final ApiVersion sinceVersion;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SinceKotlinInfo(ApiVersion sinceVersion) {
        super(null);
        Intrinsics.checkNotNullParameter(sinceVersion, "sinceVersion");
        this.sinceVersion = sinceVersion;
    }

    public final ApiVersion getSinceVersion() {
        return this.sinceVersion;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.DeprecationAnnotationInfo
    public DeprecationInfo computeDeprecationInfo(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        return this.sinceVersion.compareTo(languageVersionSettings.getApiVersion()) <= 0 ? null : new FutureApiDeprecationInfo(DeprecationLevelValue.HIDDEN, true, this.sinceVersion);
    }
}
